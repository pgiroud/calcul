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
package org.impotch.calcul.impot.taxation.pp;

import org.impotch.bareme.Bareme;
import org.impotch.calcul.impot.taxation.pp.famille.DoubleBareme;
import org.impotch.calcul.impot.taxation.pp.famille.Splitting;

import java.math.BigDecimal;


public interface StrategieProductionImpotFamille {

	public static StrategieProductionImpotFamille doubleBareme(Bareme baremeSeul, Bareme baremeFamille) {
		return new DoubleBareme(baremeSeul,baremeFamille);
	}

	public static StrategieProductionImpotFamille splittingIntegral(Bareme bareme) {
		return new Splitting(bareme, "50 %");
	}

	BigDecimal produireImpotAnnuel(SituationFamiliale situation, BigDecimal determinantArrondi, BigDecimal imposableArrondi);
	BigDecimal produireImpotAnnuelAuTauxMaximal(SituationFamiliale situation, BigDecimal imposableArrondi);
}
