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

import ch.ge.afc.calcul.bareme.Bareme;
import ch.ge.afc.calcul.impot.taxation.pp.SituationFamiliale;
import ch.ge.afc.calcul.impot.taxation.pp.StrategieProductionImpotFamille;

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
	
	@Override
	public Bareme getBareme(SituationFamiliale situation) {
		return bareme;
	}

	@Override
	public BigDecimal transformeDeterminant(SituationFamiliale situation, BigDecimal determinant) {
		return determinant;
	}

	@Override
	public BigDecimal transformeImpotDeterminant(SituationFamiliale situation, BigDecimal impot) {
		return impot;
	}

	protected boolean hasCharge(SituationFamiliale situation) {
		return situation.getEnfants().size() > 0 || situation.getPersonnesNecessiteuses().size() > 0;
	}
	
	protected boolean isFamille(SituationFamiliale situation) {
		return situation.isCouple() || hasCharge(situation);
	}
}
