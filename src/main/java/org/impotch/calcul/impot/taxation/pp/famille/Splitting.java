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
