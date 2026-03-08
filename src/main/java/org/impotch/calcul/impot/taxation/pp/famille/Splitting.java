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
package org.impotch.calcul.impot.taxation.pp.famille;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.impotch.bareme.Bareme;
import org.impotch.calcul.impot.taxation.pp.SituationFamiliale;
import org.impotch.calcul.impot.taxation.pp.StrategieProductionImpotFamille;
import org.impotch.util.BigDecimalUtil;
import org.impotch.util.TypeArrondi;

/**
 * Splitting intégral :
 * Le splitting intégral (division intégrale) est un modèle prisé, sept cantons l'appliquant. Pour briser l'effet
 * de progression, les revenus additionnés des couples mariés sont divisés par deux. Le taux d'imposition le plus bas
 * est ensuite appliqué au revenu total. La pénalisation du mariage est ainsi totalement éliminée.
 * Lorsque les revenus des époux sont à peu près égaux, la charge fiscale est comparable à celle résultant d'une
 * imposition individuelle. En revanche, plus l’écart entre les deux revenus est important, plus l’avantage fiscal
 * par rapport aux concubins s’accroît.
 * En 2026, ce modèle est en vigueur en Argovie, à Appenzell Rhodes-Intérieures, à Bâle-Campagne, à Fribourg,
 * à Genève, à Saint-Gall et en Thurgovie.
 * Splitting partiel :
 * Sept cantons misent sur un splitting partiel. A la différence du splitting intégral, le facteur de division
 * se situe entre un et deux. Conséquences: la charge fiscale des couples mariés est légèrement plus élevée que
 * sous le régime du splitting intégral. En d'autres termes, la pénalisation du mariage est atténuée,
 * sans être éliminée pour autant.
 * Par rapport à un couple de concubins, un couple marié peut, selon la répartition des revenus et le facteur
 * de splitting retenu, supporter soit une charge fiscale supplémentaire, soit une charge inférieure.
 * Plus les revenus du couple sont répartis de manière équilibrée et plus le facteur de splitting est faible,
 * plus la charge fiscale par rapport à un couple non marié tend à être supérieure.
 * En 2026, Les Grisons, Schaffhouse, Soleure et Schwytz appliquent un facteur de splitting de 1,9, Niedwald 1,85,
 * Neuchâtel 1,82, tandis que c'est le facteur 1,6 qui est en vigueur à Glaris.
 */
public class Splitting extends ImpositionFamille implements StrategieProductionImpotFamille {

    /**************************************************/
    /***************** Attributs **********************/
    /**************************************************/
	
	private final BigDecimal tauxSplitting;
	private TypeArrondi typeArrondi = TypeArrondi.UNITE_INF;
	private TypeArrondi typeArrondiImpot = TypeArrondi.VINGTIEME_LE_PLUS_PROCHE;
	
    /**************************************************/
    /**************** Constructeurs *******************/
    /**************************************************/
	
	public Splitting(Bareme bareme, BigDecimal taux) {
		super(bareme);
		tauxSplitting = taux;
	}
	
	public Splitting(Bareme bareme, String taux) {
		this(bareme, BigDecimalUtil.parse(taux));
	}
	
    /**************************************************/
    /******* Accesseurs / Mutateurs *******************/
    /**************************************************/
	
	protected BigDecimal getTauxSplitting() {
		return tauxSplitting;
	}

	public void setTypeArrondi(TypeArrondi type) {
		this.typeArrondi = type;
	}
	
	protected TypeArrondi getTypeArrondi() {
		return this.typeArrondi;
	}
	
	protected Bareme getBareme() {
		return this.getBaremeSeul();
	}
	
	/**************************************************/
    /******************* Méthodes *********************/
    /**************************************************/

	protected BigDecimal produireImpotAvecSplitting(BigDecimal determinantArrondi, BigDecimal tauxSplitting) {
		BigDecimal determinantApresSplitting = typeArrondi.arrondir(tauxSplitting.multiply(determinantArrondi));
		BigDecimal impotDeterminantApresSplitting = getBareme().calcul(determinantApresSplitting);
		impotDeterminantApresSplitting = typeArrondiImpot.arrondir(impotDeterminantApresSplitting);
		if (0 == BigDecimal.ZERO.compareTo(impotDeterminantApresSplitting)) return impotDeterminantApresSplitting;
		else return typeArrondiImpot.arrondir(impotDeterminantApresSplitting.multiply(determinantArrondi).divide(determinantApresSplitting,10, RoundingMode.HALF_UP));

	}

	private BigDecimal produireImpotDeterminantFamille(BigDecimal determinantArrondi) {
		return produireImpotAvecSplitting(determinantArrondi,tauxSplitting);
	}
	
	
	private BigDecimal produireImpotDeterminantSeul(BigDecimal determinantArrondi) {
		BigDecimal impotDeterminant = getBareme().calcul(determinantArrondi);
		return typeArrondiImpot.arrondir(impotDeterminant);
	}

	@Override
	public BigDecimal produireImpotDeterminant(SituationFamiliale situation,
											   BigDecimal determinantArrondi) {
		return isFamille(situation) ?
				produireImpotDeterminantFamille(determinantArrondi) : produireImpotDeterminantSeul(determinantArrondi);
	}
}
