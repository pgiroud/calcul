/**
 * This file is part of CalculImpotCH.
 *
 * CalculImpotCH is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * CalculImpotCH is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CalculImpotCH.  If not, see <http://www.gnu.org/licenses/>.
 */
package ch.ge.afc.calcul.impot.taxation.pp.famille;

import java.math.BigDecimal;

import ch.ge.afc.bareme.Bareme;
import ch.ge.afc.calcul.impot.taxation.pp.SituationFamiliale;
import ch.ge.afc.calcul.impot.taxation.pp.StrategieProductionImpotFamille;
import ch.ge.afc.util.BigDecimalUtil;
import ch.ge.afc.util.TypeArrondi;

public class Splitting extends ImpositionFamilleSansAvantage implements StrategieProductionImpotFamille {

    /**************************************************/
    /***************** Attributs **********************/
    /**************************************************/
	
	private final BigDecimal tauxSplitting;
	private TypeArrondi typeArrondi = TypeArrondi.FRANC_INF;
	private TypeArrondi typeArrondiImpot = TypeArrondi.CINQ_CTS;
	
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

	private BigDecimal produireImpotDeterminantFamille(BigDecimal determinantArrondi, BigDecimal imposableArrondi) {
		BigDecimal determinantApresSplitting = typeArrondi.arrondirMontant(tauxSplitting.multiply(determinantArrondi));
		BigDecimal impotDeterminantApresSplitting = getBareme().calcul(determinantApresSplitting);
		impotDeterminantApresSplitting = typeArrondiImpot.arrondirMontant(impotDeterminantApresSplitting);
		return typeArrondiImpot.arrondirMontant(impotDeterminantApresSplitting.multiply(determinantArrondi).divide(determinantApresSplitting,10,BigDecimal.ROUND_HALF_UP));
	}
	
	
	private BigDecimal produireImpotDeterminantSeul(BigDecimal determinantArrondi, BigDecimal imposableArrondi) {
		BigDecimal impotDeterminant = getBareme().calcul(determinantArrondi);
		return typeArrondiImpot.arrondirMontant(impotDeterminant);
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
		if (0 == imposableArrondi.compareTo(determinantArrondi)) return impotDeterminant;
		else return typeArrondiImpot.arrondirMontant(imposableArrondi.multiply(impotDeterminant).divide(determinantArrondi,10,BigDecimal.ROUND_HALF_UP));
	}
}
