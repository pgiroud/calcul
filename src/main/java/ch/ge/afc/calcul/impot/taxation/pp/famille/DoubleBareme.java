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

import ch.ge.afc.bareme.Bareme;
import ch.ge.afc.calcul.impot.taxation.pp.SituationFamiliale;
import ch.ge.afc.calcul.impot.taxation.pp.StrategieProductionImpotFamille;

public class DoubleBareme extends ImpositionFamilleSansAvantage implements StrategieProductionImpotFamille {
	
    /**************************************************/
    /***************** Attributs **********************/
    /**************************************************/
	
	private final Bareme baremeFamille;
	
    /**************************************************/
    /**************** Constructeurs *******************/
    /**************************************************/
	
	public DoubleBareme(Bareme baremeSeul, Bareme baremeFamille) {
		super(baremeSeul);
		this.baremeFamille = baremeFamille;
	}
	
    /**************************************************/
    /******* Accesseurs / Mutateurs *******************/
    /**************************************************/
	
	protected Bareme getBaremeFamille() {
		return baremeFamille;
	}
	
	/**************************************************/
    /******************* Méthodes *********************/
    /**************************************************/

	
	/* (non-Javadoc)
	 * @see ch.ge.afc.calcul.impot.taxation.pp.StrategieProductionImpotFamille#getBareme(ch.ge.afc.calcul.impot.taxation.pp.SituationFamiliale)
	 */
	@Override
	public Bareme getBareme(SituationFamiliale situation) {
		if (isFamille(situation)) return getBaremeFamille();
		else return super.getBareme(situation);
	}
	
}
