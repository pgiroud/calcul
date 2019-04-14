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
package org.impotch.calcul.impot.cantonal.fr.pp;

import org.impotch.bareme.Bareme;
import org.impotch.bareme.ConstructeurBareme;
import org.impotch.bareme.Intervalle;
import org.impotch.bareme.TrancheBareme;
import org.impotch.util.BigDecimalUtil;

import java.math.BigDecimal;

public class ConstructeurBaremeTauxEffectifLineaireParTranche extends ConstructeurBareme {

    public ConstructeurBaremeTauxEffectifLineaireParTranche() {
        super();
        this.fermeAGauche();
    }

    protected TrancheBaremeLineaire construireTranche(Intervalle inter, BigDecimal montantOuTaux, BigDecimal incrementTaux) {
        return new TrancheBaremeLineaire(inter,montantOuTaux,incrementTaux);
    }



    protected TrancheBaremeLineaire construireTranche(BigDecimal montant, BigDecimal montantOuTaux, BigDecimal incrementTaux) {
        Intervalle inter = construireIntervalle(montant);
        TrancheBaremeLineaire tranche = construireTranche(inter,montantOuTaux,incrementTaux);
        montantMaxPrecedent = montant;
        return tranche;
    }



    public final ConstructeurBaremeTauxEffectifLineaireParTranche tranche(BigDecimal montant, BigDecimal taux, BigDecimal incrementTaux) {
        Intervalle intervalle = construireIntervalle(montant);
        if (tranches.size() > 0) {
            TrancheBareme derniereTranche = tranches.get(tranches.size()-1);
            if (0 == derniereTranche.getTauxOuMontant().compareTo(taux)) {
                Intervalle inter = intervalle.union(derniereTranche.getIntervalle());
                tranches.set(tranches.size()-1,construireTranche(inter,taux,incrementTaux));
                montantMaxPrecedent = montant;
            } else {
                tranches.add(construireTranche(montant,taux,incrementTaux));
            }
        } else {
            tranches.add(construireTranche(montant,taux,incrementTaux));
        }
        return this;
    }



    public final ConstructeurBaremeTauxEffectifLineaireParTranche tranche(int montant, String taux, String tauxEnPlusPar100Francs) {
        return this.tranche(BigDecimal.valueOf(montant), BigDecimalUtil.parseTaux(taux),BigDecimalUtil.parseTaux(tauxEnPlusPar100Francs).movePointLeft(2));
    }

    protected TrancheBaremeLineaire construireTranche(BigDecimal taux) {
        Intervalle.Cons cons = new Intervalle.Cons().de(montantMaxPrecedent).inclus();
        Intervalle intervalle = cons.aPlusInfini().intervalle();
        return new TrancheBaremeLineaire(intervalle,taux);
    }


    public Bareme construire() {
        BaremeTauxEffectifLineaireParTranche bareme = new BaremeTauxEffectifLineaireParTranche(tranches);
        bareme.setTypeArrondiGlobal(this.getTypeArrondiGlobal());
        bareme.setTypeArrondiSurChaqueTranche(this.getTypeArrondiSurChaqueTranche());
        return bareme;
    }

    protected static class TrancheBaremeLineaire extends TrancheBareme {

        private final BigDecimal pente;
        private final boolean isDerniere;

        public TrancheBaremeLineaire(Intervalle intervalle,
                                     BigDecimal taux, BigDecimal pente) {
            super(intervalle, taux);
            this.pente = pente;
            this.isDerniere = false;
        }

        public TrancheBaremeLineaire(Intervalle intervalle,
                                     BigDecimal taux) {
            super(intervalle, taux);
            this.pente = null;
            this.isDerniere = true;
        }


        /* (non-Javadoc)
         * @see ch.ge.afc.calcul.impot.bareme.TrancheBareme#calcul(java.math.BigDecimal, java.math.BigDecimal)
         */
        @Override
        public BigDecimal calcul(BigDecimal montant) {

            if (getIntervalle().encadre(montant) && BigDecimalUtil.isStrictementPositif(this.getTauxOuMontant())) {
                BigDecimal largeur = montant.subtract(getIntervalle().getDebut());
                if (isDerniere) {
                    return this.getTauxOuMontant();
                } else {
                    return this.getTauxOuMontant().add(pente.multiply(largeur));
                }
            }
            else return BigDecimal.ZERO;
        }

    }

}
