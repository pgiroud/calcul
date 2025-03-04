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

import org.impotch.bareme.Bareme;
import org.impotch.calcul.impot.taxation.pp.SituationFamiliale;
import org.impotch.calcul.impot.taxation.pp.StrategieProductionImpotFamille;
import org.impotch.util.BigDecimalUtil;
import org.impotch.util.TypeArrondi;

public class Splitting extends ImpositionFamilleSansAvantage implements StrategieProductionImpotFamille {

    /**************************************************/
    /***************** Attributs **********************/
    /**************************************************/
	
	private final BigDecimal tauxSplitting;
	private TypeArrondi typeArrondi = TypeArrondi.UNITE_INF;
	private TypeArrondi typeArrondiImpot = TypeArrondi.CINQ_CENTIEMES_LES_PLUS_PROCHES;
	
    /**************************************************/
    /**************** Constructeurs *******************/
    /**************************************************/
	
	public Splitting(Bareme bareme, BigDecimal taux) {
		super(bareme);
		tauxSplitting = taux;
	}
	
	public Splitting(Bareme bareme, String taux) {
		this(bareme, BigDecimalUtil.parseTaux(taux));
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

	protected BigDecimal produireImpotAvecSplitting(BigDecimal determinantArrondi, BigDecimal imposableArrondi, BigDecimal tauxSplitting) {
		BigDecimal determinantApresSplitting = typeArrondi.arrondirMontant(tauxSplitting.multiply(determinantArrondi));
		BigDecimal impotDeterminantApresSplitting = getBareme().calcul(determinantApresSplitting);
		impotDeterminantApresSplitting = typeArrondiImpot.arrondirMontant(impotDeterminantApresSplitting);
		if (0 == BigDecimal.ZERO.compareTo(impotDeterminantApresSplitting)) return impotDeterminantApresSplitting;
		else return typeArrondiImpot.arrondirMontant(impotDeterminantApresSplitting.multiply(determinantArrondi).divide(determinantApresSplitting,10,BigDecimal.ROUND_HALF_UP));

	}

	private BigDecimal produireImpotDeterminantFamille(BigDecimal determinantArrondi, BigDecimal imposableArrondi) {
		return produireImpotAvecSplitting(determinantArrondi,imposableArrondi,tauxSplitting);
	}
	
	
	private BigDecimal produireImpotDeterminantSeul(BigDecimal determinantArrondi, BigDecimal imposableArrondi) {
		BigDecimal impotDeterminant = getBareme().calcul(determinantArrondi);
		return typeArrondiImpot.arrondirMontant(impotDeterminant);
	}

	protected BigDecimal determineImpot(BigDecimal imposableArrondi, BigDecimal determinantArrondi, BigDecimal impotDeterminant) {
		if (0 == imposableArrondi.compareTo(determinantArrondi)) return impotDeterminant;
		else return typeArrondiImpot.arrondirMontant(imposableArrondi.multiply(impotDeterminant).divide(determinantArrondi,10,BigDecimal.ROUND_HALF_UP));
	}

	@Override
	public BigDecimal produireImpotAnnuel(SituationFamiliale situation,
			BigDecimal determinantArrondi, BigDecimal imposableArrondi) {
		BigDecimal impotDeterminant = null;
		if (isFamille(situation)) {
			impotDeterminant = this.produireImpotDeterminantFamille(determinantArrondi,imposableArrondi);
		} else {
			impotDeterminant = this.produireImpotDeterminantSeul(determinantArrondi, imposableArrondi);
		}
		return determineImpot(imposableArrondi,determinantArrondi,impotDeterminant);
	}
}
