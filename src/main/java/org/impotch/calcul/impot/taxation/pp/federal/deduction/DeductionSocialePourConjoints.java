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
package org.impotch.calcul.impot.taxation.pp.federal.deduction;

import java.math.BigDecimal;

import org.impotch.calcul.ReglePeriodique;
import org.impotch.calcul.impot.taxation.pp.DeductionSociale;
import org.impotch.calcul.impot.taxation.pp.SituationFamiliale;

public class DeductionSocialePourConjoints  extends ReglePeriodique implements DeductionSociale  {
	
	private BigDecimal deducConjointsIFD = new BigDecimal("2500");
	
	
	public DeductionSocialePourConjoints(int annee) {
		super(annee);
		// TODO Auto-generated constructor stub
	}


	/**
	 * @param deducConjointsIFD the deducConjointsIFD to set
	 */
	public void setDeducConjointsIFD(BigDecimal deducConjointsIFD) {
		this.deducConjointsIFD = deducConjointsIFD;
	}


	public BigDecimal getMontantDeduction(SituationFamiliale situation) {
		if (situation.isCouple()) return deducConjointsIFD; 
		else return BigDecimal.ZERO;
	}
}
