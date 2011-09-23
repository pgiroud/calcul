package ch.ge.afc.calcul.impot.federal.dao;

import ch.ge.afc.bareme.Bareme;

/**
 * Created by IntelliJ IDEA.
 * User: patrick
 * Date: 15/09/11
 * Time: 21:49
 * To change this template use File | Settings | File Templates.
 */
public interface FournisseurBaremeIFD {
    /**
     * Retourne le barème de l'impôt sur le revenu des personnes physiques postnumerando IFD pour personne seul.
     * Ce barème est décrit dans la LIFD article 214 alinea 1.
     * Ces barèmes incluent le seuillage à 25 francs tel que décrit dans l'alinea 3 de l'article 214 de la LIFD.
     * @param annee l'année pour laquelle on désire ce barème
     * @return le barème permettant de calculer l'impôt à partir du revenu pour une personne seule.
     */
    Bareme getBaremeImpotRevenuPersonnePhysiquePourPersonneSeule(int annee);


    /**
     * Retourne le barème de l'impôt sur le revenu des personnes physiques postnumerando IFD pour les familles (marié ou mono-parentale).
     * Ce barème est décrit dans la LIFD article 214 alinea 2.
     * Ces barèmes incluent le seuillage à 25 francs tel que décrit dans l'alinea 3 de l'article 214 de la LIFD.
     * @param annee l'année pour laquelle on désire ce barème
     * @return le barème permettant de calculer l'impôt à partir du revenu pour des personnes mariées ou des familles mono-parentales.
     */
    Bareme getBaremeImpotRevenuPersonnePhysiquePourFamille(int annee);

    Bareme getBaremeImpotRevenuPraeNumerandoPersonnePhysiquePourPersonneSeule(int annee);
    Bareme getBaremeImpotRevenuPraeNumerandoPersonnePhysiquePourFamille(int annee);

    Bareme getBaremeImpotSourcePrestationCapital(int annee);

}
