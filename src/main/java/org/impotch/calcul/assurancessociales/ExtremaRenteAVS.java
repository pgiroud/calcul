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
package org.impotch.calcul.assurancessociales;

import java.math.BigDecimal;

public class ExtremaRenteAVS {

	private static final BigDecimal NBRE_MOIS_ANNEE = new BigDecimal(12);
	private static final BigDecimal RATIO_MINIMUM_MAXIMUM = new BigDecimal(2);
	
	private final BigDecimal montantMensuelMinimum;
	
	
	public ExtremaRenteAVS(BigDecimal montantMensuelMinimum) {
		this.montantMensuelMinimum = montantMensuelMinimum;
	}
	
	public BigDecimal getMontantMensuelMinimum() {
		return montantMensuelMinimum;
	}
	
	public BigDecimal getMontantMensuelMaximum() {
		return RATIO_MINIMUM_MAXIMUM.multiply(montantMensuelMinimum);
	}
	
	public BigDecimal getMontantAnnuelMinimum() {
		return NBRE_MOIS_ANNEE.multiply(montantMensuelMinimum);
	}
	
	public BigDecimal getMontantAnnuelMaximum() {
		return NBRE_MOIS_ANNEE.multiply(montantMensuelMinimum).multiply(RATIO_MINIMUM_MAXIMUM);
	}

	
}
