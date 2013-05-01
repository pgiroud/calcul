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
package org.impotch.calcul.impot.france;

import java.math.BigDecimal;

import org.impotch.bareme.Bareme;
import org.impotch.calcul.ReglePeriodique;
import org.impotch.util.BigDecimalUtil;
import org.impotch.util.TypeArrondi;

public class RegleCalculImpot extends ReglePeriodique {

	private Bareme bareme;
	
	private BigDecimal plafondParDemiPart;
	
	private BigDecimal montantDecote;
	
	public RegleCalculImpot(int annee) {
		super(annee);
	}

	public void setMontantDecote(BigDecimal montantDecote) {
		this.montantDecote = montantDecote;
	}
	
	public void setBareme(Bareme bareme) {
		this.bareme = bareme;
	}
	
	protected BigDecimal decote(BigDecimal montantImpot) {
		if (0 >= montantDecote.compareTo(montantImpot)) return montantImpot;
		BigDecimal deduction = montantDecote.subtract(montantImpot).divide(BigDecimalUtil.DEUX,2,BigDecimal.ROUND_HALF_UP);
		deduction = TypeArrondi.FRANC_INF.arrondirMontant(deduction);
		return montantImpot.subtract(deduction);
	}
	
	public BigDecimal produireImpot(BigDecimal nbrePart, BigDecimal montantImposable) {
		return montantImposable;
	}
}
