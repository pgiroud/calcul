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
package org.impotch.calcul.impot.taxation.pp.ge.deduction;

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
public class DeductionChargeFamille extends ReglePeriodique implements DeductionSociale {

	private static final BigDecimal UN_DEMI = new BigDecimal("0.5");
	
	private BigDecimal montantCharge;
	
	public DeductionChargeFamille(int annee) {
		super(annee);
	}

	public void setMontantParCharge(BigDecimal montantDeduction) {
		montantCharge = montantDeduction;
	}
	
	protected BigDecimal getNombreCharge(SituationFamiliale situation) {
		BigDecimal nombre = BigDecimal.ZERO;
		for (EnfantACharge enfant : situation.getEnfants()) {
			nombre = nombre.add(UN_DEMI);
			if (!enfant.isDemiPart(Souverainete.CH_CANTONALE_GE)) nombre = nombre.add(UN_DEMI);
		}
		nombre = nombre.add(new BigDecimal(situation.getPersonnesNecessiteuses().size()));
		return nombre;
	}
	
	@Override
	public BigDecimal getMontantDeduction(SituationFamiliale situation) {
		BigDecimal nombreCharge = getNombreCharge(situation);
		return montantCharge.multiply(nombreCharge);
	}

    @Override
    public String getNom() {
        return "déduc. sociale charge famille";
    }
}
