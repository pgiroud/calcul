/*
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
package org.impotch.calcul.impot.federal.param;

import org.impotch.bareme.*;
import org.impotch.util.BigDecimalUtil;
import org.impotch.util.TypeArrondi;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.impotch.bareme.ConstructeurBareme.unBaremeATauxMarginal;

class ConstructeurBaremeIFD {

    // 3 sujets : arrondi avant d’entrer dans le barème, arrondi sur calcul de tranche (sauf PC), taux effective max atteint


    private static final TypeArrondi ARRONDI_SUR_CHAQUE_TRANCHE = TypeArrondi.CINQ_CENTIEMES_INF;

    public static ConstructeurBaremeIFD unBaremeIFD() {
        return new ConstructeurBaremeIFD(TypeArrondi.CENTAINE_INF, false);
    }

    public static ConstructeurBaremeIFD unBaremeIFDPrestationEnCapitalImposeeSource() {
        return new ConstructeurBaremeIFD(TypeArrondi.MILLE_INF, true);
    }

    private final BigDecimal precisionArrondiSurAssiette;
    private final ConstructeurBareme cons;

    private BigDecimal taux = BigDecimal.ZERO;
    private BigDecimal debutTranche = BigDecimal.ZERO;
    private BigDecimal valeurEnDebutTranche = BigDecimal.ZERO;
    private BigDecimal tauxEffectifMax;



    private ConstructeurBaremeIFD(TypeArrondi arrondiSurAssiette, boolean source) {
        super();
        precisionArrondiSurAssiette = arrondiSurAssiette.precision();
        cons = unBaremeATauxMarginal()
                .fermeAGauche()
                .typeArrondiSurEntrant(arrondiSurAssiette)
                .typeArrondiSurChaqueTranche(ARRONDI_SUR_CHAQUE_TRANCHE);
                //.seuil(!(source) ? 25 : 0);
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
        cons.premiereTranche(finTranche,taux);
        initialiserTrancheSuivante(finTranche);
        return this;
    }




    public ConstructeurBaremeIFD pour(int montant) {
        controleAbsenceTauxEffectif();
        BigDecimal finTranche = BigDecimal.valueOf(montant);
        cons.tranche(debutTranche,finTranche,valeurEnDebutTranche, taux);
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

    private boolean tauxEffectifMaxDepasse(BigDecimal revenu, BigDecimal impot) {
        return impot.compareTo(revenu.multiply(tauxEffectifMax)) > 0;
    }

    private BigDecimal calculImpot(BigDecimal revenu) {
        BigDecimal revenuDansLaTranche = revenu.subtract(debutTranche);
        BigDecimal impotDansLaTranche = ARRONDI_SUR_CHAQUE_TRANCHE.arrondirMontant(taux.multiply(revenuDansLaTranche));
        return valeurEnDebutTranche.add(impotDansLaTranche);
    }

    private BigDecimal construireBorneSuperieurePourAtteinteTauxEffectif() {
        return Stream.iterate(debutTranche, rev -> rev.add(this.precisionArrondiSurAssiette))
                .filter(r -> tauxEffectifMaxDepasse(r,calculImpot(r)))
                .findFirst().orElseThrow();
    }

    public BaremeParTranche construire() {
        BigDecimal borneSuperieure = construireBorneSuperieurePourAtteinteTauxEffectif();
        cons.tranche(debutTranche,borneSuperieure,valeurEnDebutTranche, taux);
        debutTranche = borneSuperieure;
        taux = tauxEffectifMax;
        valeurEnDebutTranche = ARRONDI_SUR_CHAQUE_TRANCHE.arrondirMontant(taux.multiply(borneSuperieure));
        cons.derniereTranche(borneSuperieure,valeurEnDebutTranche,taux);
        return cons.construire();
    }


    // Prestation en capital

    public ConstructeurBaremeIFD taux(String taux) {
        this.taux =  BigDecimalUtil.parseTaux(taux);
        return this;
    }

    public ConstructeurBaremeIFD surLesProchains(int largeurTranche) {
        controleAbsenceTauxEffectif();
        BigDecimal finTranche = debutTranche.add(BigDecimal.valueOf(largeurTranche));
        cons.tranche(debutTranche,finTranche,valeurEnDebutTranche, taux);
        initialiserTrancheSuivante(finTranche);
        return this;
    }

    public ConstructeurBaremeIFD etFinalementTaux(String taux) {
        this.taux =  BigDecimalUtil.parseTaux(taux);
        return this;
    }

}
