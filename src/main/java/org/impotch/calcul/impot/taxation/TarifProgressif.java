package org.impotch.calcul.impot.taxation;

import org.impotch.calcul.impot.taxation.pp.SituationFamiliale;
import org.impotch.calcul.impot.taxation.pp.StrategieProductionImpotFamille;
import org.impotch.util.TypeArrondi;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;
import static org.impotch.util.BigDecimalUtil.isStrictementPositif;

public class TarifProgressif {

    private TypeArrondi typeArrondiDeterminant;
    private TypeArrondi typeArrondiImpot;
    private StrategieProductionImpotFamille impositionFamille;


    /**
     * Précise le type d'arrondi à effectuer sur les assiettes déterminantes.
     * En règle générale, on utilise des arrondis à la centaine inférieure pour le
     * revenu et le bénéfice et au mille francs inférieur pour la fortune ou le capital.
     */
    private void setTypeArrondiDeterminant(TypeArrondi type) {
        typeArrondiDeterminant = type;
    }

    /**
     * Retourne le type d'arrondi à effectuer sur les assiettes déterminantes.
     * @return le type d'arrondi.
     */
    protected TypeArrondi getTypeArrondiDeterminant() {
        return typeArrondiDeterminant;
    }

    /**
     * Spécifie le type d'arrondi à appliquer à l'impôt calculé. Par défaut, l'arrondi se fait aux 5 centimes
     * les plus proches.
     *
     * @param type le type d'arrondi sur le montant d'impôt.
     */
    private void setTypeArrondiImpot(TypeArrondi type) {
        typeArrondiImpot = type;
    }

    /**
     * Retourne le type d'arrondi sur le montant d'impôt.
     * @return le type d'arrondi sur le montant d'impôt.
     */
    protected TypeArrondi getTypeArrondiImpot() {
        return typeArrondiImpot;
    }

    /**
     * Précise la stratégie d'imposition à appliquer aux familles. En effet plusieurs systèmes sont en vigueur :
     * <ul>
     * 	<li>Splitting : cantonal de Fribourg, de Neuchâtel, ...</li>
     * 	<li>double barème : IFD, cantonal de Genève, ...</li>
     * 	<li>quotient familial : cantonal de Vaud, France, ...</li>
     * </ul>
     * @param strategie la stratégie à appliquer.
     */
    private void setStrategieProductionImpotFamille(StrategieProductionImpotFamille strategie) {
        impositionFamille = strategie;
    }

    /**
     * Retourne la stratégie d'imposition à appliquer aux familles.
     * @return la stratégie d'imposition à appliquer aux familles.
     */
    protected StrategieProductionImpotFamille getStrategieImpositionFamille() {
        return impositionFamille;
    }

    public BigDecimal calcul(SituationFamiliale situation, BigDecimal montant) {
        BigDecimal determinant = getTypeArrondiDeterminant().arrondir(montant);
        if (!isStrictementPositif(determinant)) return ZERO;
        return getStrategieImpositionFamille().produireImpotDeterminant(situation,determinant);
    }
}
