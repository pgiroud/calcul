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
import org.impotch.util.TypeArrondi;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class ImpositionFamilleSansAvantage implements
        StrategieProductionImpotFamille {

    /**************************************************/
    /***************** Attributs **********************/
    /**************************************************/
	
	private final Bareme bareme;
	private TypeArrondi typeArrondiImpot = TypeArrondi.CINQ_CENTIEMES_LES_PLUS_PROCHES;
	
    /**************************************************/
    /**************** Constructeurs *******************/
    /**************************************************/
	
	public ImpositionFamilleSansAvantage(Bareme bareme) {
		this.bareme = bareme;
	}
	
	/**************************************************/
    /******************* Méthodes *********************/
    /**************************************************/

	public Bareme getBaremeSeul() {
		return bareme;
	}
 
	public void setTypeArrondiImpot(TypeArrondi type) {
		this.typeArrondiImpot = type;
	}
	
	
	@Override
	public BigDecimal produireImpotAnnuel(SituationFamiliale situation,
			BigDecimal determinantArrondi, BigDecimal imposableArrondi) {
		BigDecimal impotDeterminant = getBareme(situation).calcul(determinantArrondi);
		impotDeterminant = typeArrondiImpot.arrondirMontant(impotDeterminant);
		if (0 == imposableArrondi.compareTo(determinantArrondi)) return impotDeterminant;
		else return typeArrondiImpot.arrondirMontant(imposableArrondi.multiply(impotDeterminant).divide(determinantArrondi,10,BigDecimal.ROUND_HALF_UP));
	}

	protected Bareme getBareme(SituationFamiliale situation) {
		return bareme;
	}


	protected boolean hasCharge(SituationFamiliale situation) {
		return situation.getEnfants().size() > 0 || situation.getPersonnesNecessiteuses().size() > 0;
	}
	
	protected boolean isFamille(SituationFamiliale situation) {
		return situation.isCouple() || hasCharge(situation);
	}
}
