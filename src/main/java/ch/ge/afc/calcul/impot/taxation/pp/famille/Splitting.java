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
import ch.ge.afc.util.BigDecimalUtil;

public class Splitting extends ImpositionFamilleSansAvantage implements StrategieProductionImpotFamille {

    /**************************************************/
    /***************** Attributs **********************/
    /**************************************************/
	
	private final BigDecimal tauxSplitting;
	
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

	/**************************************************/
    /******************* Méthodes *********************/
    /**************************************************/

	@Override
	public BigDecimal transformeDeterminant(SituationFamiliale situation, BigDecimal determinant) {
		if (isFamille(situation)) return tauxSplitting.multiply(determinant);
		return super.transformeDeterminant(situation, determinant);
	}
	
	

}
