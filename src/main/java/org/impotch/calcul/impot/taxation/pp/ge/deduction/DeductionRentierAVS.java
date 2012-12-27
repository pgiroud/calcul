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

import org.impotch.bareme.BaremeConstantParTranche;
import org.impotch.calcul.ReglePeriodique;
import org.impotch.util.TypeArrondi;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class DeductionRentierAVS extends ReglePeriodique implements DeductionBeneficiaireRentesAVSAI {

	private BaremeConstantParTranche baremeSeul;
	private BaremeConstantParTranche baremeCoupleUnSeulRentier;
	private BaremeConstantParTranche baremeCoupleDeuxRentiers;
	
	public DeductionRentierAVS(int annee, BaremeConstantParTranche bareme, BigDecimal rapport) {
		super(annee);
		baremeSeul = bareme;
		baremeCoupleUnSeulRentier = bareme.homothetieTranche(rapport, TypeArrondi.CENT_FRANC_INF);
		baremeCoupleDeuxRentiers = baremeCoupleUnSeulRentier.homothetieValeur(rapport, TypeArrondi.CENT_FRANC_INF);
	}

	public BigDecimal getMontantDeduction(BigDecimal revenuNet, boolean isSeule, boolean doubleRentier) {
		if (isSeule) return baremeSeul.calcul(revenuNet);
		else {
			if (doubleRentier) return baremeCoupleDeuxRentiers.calcul(revenuNet);
			else return baremeCoupleUnSeulRentier.calcul(revenuNet);
		}
	}
}
