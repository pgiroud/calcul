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
package org.impotch.calcul.impot.france;

import java.math.BigDecimal;

import org.impotch.calcul.ReglePeriodique;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class ReglePlafonnementQuotientFamilial extends ReglePeriodique {

	private BigDecimal plafondParDemiPart;
	
	public ReglePlafonnementQuotientFamilial(int annee) {
		super(annee);
	}

}