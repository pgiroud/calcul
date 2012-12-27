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
import org.impotch.calcul.impot.Souverainete;
import org.impotch.calcul.impot.taxation.pp.DeductionSociale;
import org.impotch.calcul.impot.taxation.pp.EnfantACharge;
import org.impotch.calcul.impot.taxation.pp.SituationFamiliale;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class DeductionSocialeParEnfant extends ReglePeriodique implements DeductionSociale {

	private BigDecimal deductionSocialeParEnfant = new BigDecimal("6100");
	

	
	public DeductionSocialeParEnfant(int annee) {
		super(annee);
	}



	/**
	 * Spécifie le montant que l'on peut déduire par enfant.
	 * @param deductionSocialeParEnfant le montant déductible (6'100 francs en 2008)
	 */
	public void setDeductionSocialeParEnfant(BigDecimal deductionSocialeParEnfant) {
		this.deductionSocialeParEnfant = deductionSocialeParEnfant;
	}



	public BigDecimal getMontantDeduction(SituationFamiliale situation) {
		BigDecimal deducSocialeEnfant = BigDecimal.ZERO; 
		for (EnfantACharge enfant : situation.getEnfants()) {
			deducSocialeEnfant = deducSocialeEnfant.add(enfant.isDemiPart(Souverainete.CH_FEDERALE) ? deductionSocialeParEnfant.divide(new BigDecimal(2), 0, BigDecimal.ROUND_HALF_UP) : deductionSocialeParEnfant);
		}
		return deducSocialeEnfant; 
	}
}
