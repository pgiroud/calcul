/**
 * This file is part of impotch/calcul.
 *
 * impotch/calcul is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * impotch/calcul is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with impotch/calcul.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.impotch.calcul.impot.federal.dao;

import org.impotch.bareme.*;
import org.impotch.util.BigDecimalUtil;
import org.impotch.util.TypeArrondi;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

class ConstructeurBaremeIFD extends ConstructeurBaremeTauxMarginal {

    private static final TypeArrondi ARRONDI_SUR_CHAQUE_TRANCHE = TypeArrondi.CINQ_CTS_INF;

    private final TypeArrondi arrondiSurAssiette;
    private BigDecimal taux = BigDecimal.ZERO;
    private BigDecimal debutTranche = BigDecimal.ZERO;
    private BigDecimal valeurEnDebutTranche = BigDecimal.ZERO;
    private BigDecimal tauxEffectifMax;

    public ConstructeurBaremeIFD() {
        this(TypeArrondi.CENT_FRANC_INF);
    }

    public ConstructeurBaremeIFD(TypeArrondi arrondiSurAssiette) {
        super();
        fermeAGauche();
        typeArrondiSurChaqueTranche(ARRONDI_SUR_CHAQUE_TRANCHE);
        seuil(25);
        this.arrondiSurAssiette = arrondiSurAssiette;
    }

    private void controleAbsenceTauxEffectif() {
        if (null != tauxEffectifMax)
            throw new RuntimeException("Impossible de définir une nouvelle tranche après la spécification du taux effectif maximal");
    }

    private void initialiserTrancheSuivante(BigDecimal finTranche) {
        // Les valeurs pour la tranche suivante. On ne peut malheureusement pas utiliser la tranche construite car l'intervalle est ouvert
        // en fin de tranche. Ceci est nécessaire pour l'algo de calcul.
        // La valeur en début de tranche peut bien sûr être modifiée en cas de fixation d'arrondi (les 11.5 % doivent être atteints)
        valeurEnDebutTranche = valeurEnDebutTranche.add(ARRONDI_SUR_CHAQUE_TRANCHE.arrondirMontant(taux.multiply(finTranche.subtract(debutTranche))));
        debutTranche = finTranche;
    }

    public ConstructeurBaremeIFD jusqua(int montant) {
        controleAbsenceTauxEffectif();
        BigDecimal finTranche = BigDecimal.valueOf(montant);
        super.premiereTranche(finTranche,taux);
        initialiserTrancheSuivante(finTranche);
        return this;
    }

    @Override
    protected TrancheBareme construireTranche(Intervalle inter, BigDecimal montantOuTaux) {
        return new TrancheBaremeIFD(inter, montantOuTaux,valeurEnDebutTranche,arrondiSurAssiette);
    }

    public ConstructeurBaremeIFD pour(int montant) {
        controleAbsenceTauxEffectif();
        BigDecimal finTranche = BigDecimal.valueOf(montant);
        super.tranche(debutTranche,finTranche,taux);
        initialiserTrancheSuivante(finTranche);
        return this;
    }

    /**
     * On peut se demander pourquoi fixer le montant d'impôt en début de tranche !
     * La raison tient au fait que le taux effectif maximum de 11.5 % <b>doit</b>
     * être atteint et l'administration fédérale des contributions ajoute 5 centimes
     * sur certaines tranches (l'auteur n'a pas trouvé de règle permettant de déterminer cette
     * tranche malgré sollicitation de l'administration en question).
     *
     * De plus les barèmes dans la loi sont écrit en mentionnant ce montant d'impôt en début de tranche.
     * @param montant Il s'agit du montant d'impôt en début de tranche.
     */
    public ConstructeurBaremeIFD a(String montant) {
        valeurEnDebutTranche = new BigDecimal(montant);
        return this;
    }

    public ConstructeurBaremeIFD etPar100FrancsEnPlus(String montant) {
        taux = new BigDecimal(montant).movePointLeft(2);
        return this;
    }

    public ConstructeurBaremeIFD tauxEffectifMax(String taux) {
        this.tauxEffectifMax = BigDecimalUtil.parseTaux(taux);
        return this;
    }




    @Override
    public BaremeTauxMarginalConstantParTranche construire() {
        Intervalle intervalle = construireDernierIntervalle(debutTranche);
        ajouterTranche(new DerniereTrancheIFD(intervalle, taux, valeurEnDebutTranche, tauxEffectifMax, arrondiSurAssiette));
        return super.construire();
    }



    protected static class TrancheBaremeIFD extends TrancheBareme {

        private final BigDecimal montantImpotEnDebutTranche;
        private final TypeArrondi arrondiSurAssiette;

        protected TrancheBaremeIFD(Intervalle intervalle, BigDecimal taux, BigDecimal montantImpotEnDebutTranche, TypeArrondi arrondiSurAssiette) {
            super(intervalle, taux);
            this.montantImpotEnDebutTranche = montantImpotEnDebutTranche;
            this.arrondiSurAssiette = arrondiSurAssiette;
        }

        protected TypeArrondi getArrondiSurAssiette() {
            return arrondiSurAssiette;
        }

        @Override
        public BigDecimal calcul(BigDecimal montant) {
            if (this.getIntervalle().encadre(montant)) {
                BigDecimal montantArrondi = arrondiSurAssiette.arrondirMontant(montant);
                BigDecimal montantImposableDansLaTranche = montantArrondi;
                if (null != getIntervalle().getDebut()) {
                    montantImposableDansLaTranche = montantImposableDansLaTranche.subtract(getIntervalle().getDebut());
                }
                BigDecimal impotDansLatranche = montantImposableDansLaTranche.multiply(this.getTauxOuMontant());
                return montantImpotEnDebutTranche.add(impotDansLatranche);
            } else {
                return BigDecimal.ZERO;
            }
        }

        @Override
        public TrancheBareme homothetie(BigDecimal rapport, TypeArrondi typeArrondi) {
            // Première tranche
            Intervalle inter = this.getIntervalle();
            if (inter.isDebutMoinsInfini() && inter.isFinPlusInfini()) return this;
            if (inter.encadre(BigDecimal.ZERO)) {
                Intervalle newInter = new Intervalle.Cons().deMoinsInfini().a(inter.getFin().subtract(BigDecimal.valueOf(100))).exclus().intervalle();
                return new TrancheBaremeIFD(inter, getTauxOuMontant(), montantImpotEnDebutTranche, arrondiSurAssiette);
            } else {
                BigDecimal debutTranche = typeArrondi.arrondirMontant(inter.getDebut().multiply(rapport));
                BigDecimal montantImpotEtire = inter.longueur().multiply(this.getTauxOuMontant()).multiply(rapport);
                BigDecimal finTranche = debutTranche;
                BigDecimal impot = BigDecimal.ZERO;
                while (impot.compareTo(montantImpotEtire) < 0) {
                    finTranche = finTranche.add(BigDecimal.valueOf(100));
                    impot = impot.add(BigDecimal.valueOf(100).multiply(getTauxOuMontant()));
                }
                Intervalle newInter = new Intervalle.Cons().de(debutTranche).inclus().a(finTranche.subtract(BigDecimal.valueOf(100))).exclus().intervalle();
                return new TrancheBaremeIFD(newInter, getTauxOuMontant(), montantImpotEnDebutTranche, arrondiSurAssiette);
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            TrancheBaremeIFD that = (TrancheBaremeIFD) o;
            return montantImpotEnDebutTranche.equals(that.montantImpotEnDebutTranche) && arrondiSurAssiette == that.arrondiSurAssiette;
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), montantImpotEnDebutTranche, arrondiSurAssiette);
        }

        @Override
        public String toString() {
            return super.toString() + " à " + montantImpotEnDebutTranche + " CHF en début de tranche et arrondi = " + arrondiSurAssiette;
        }
    }

    protected static class DerniereTrancheIFD extends TrancheBaremeIFD {

        private final BigDecimal tauxEffectifMax;

        public DerniereTrancheIFD(Intervalle intervalle, BigDecimal taux, BigDecimal montantImpotEnDebutTranche, BigDecimal tauxEffectifMax, TypeArrondi arrondiSurAssiette) {
            super(intervalle, taux, montantImpotEnDebutTranche,arrondiSurAssiette);
            this.tauxEffectifMax = tauxEffectifMax;
        }

        @Override
        public BigDecimal calcul(BigDecimal montant) {
            BigDecimal impot = super.calcul(montant);
            if (BigDecimalUtil.isStrictementPositif(impot)) {
                BigDecimal tauxEffectif = impot.divide(montant, 15, RoundingMode.HALF_UP);
                if (tauxEffectifMax.compareTo(tauxEffectif) > 0) return impot;
                else return tauxEffectifMax.multiply(montant);
            } else {
                return BigDecimal.ZERO;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            DerniereTrancheIFD that = (DerniereTrancheIFD) o;
            return tauxEffectifMax.equals(that.tauxEffectifMax);
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), tauxEffectifMax);
        }

        @Override
        public TrancheBareme homothetie(BigDecimal rapport, TypeArrondi typeArrondi) {
            BigDecimal debutTranche = typeArrondi.arrondirMontant(getIntervalle().getDebut().multiply(rapport));
            Intervalle newInter = new Intervalle.Cons().de(debutTranche).inclus().aPlusInfini().intervalle();
            BigDecimal montantImpotDebutTranche = BigDecimal.ZERO;
            return new DerniereTrancheIFD(newInter,getTauxOuMontant(),montantImpotDebutTranche,this.tauxEffectifMax,this.getArrondiSurAssiette());
        }
    }

}
