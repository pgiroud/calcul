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
package org.impotch.calcul.impot.indexation;

import java.math.BigDecimal;
import java.util.Calendar;

import org.impotch.calcul.eco.indice.ge.BaseIndiceGenevoisPrixConsommation;
import org.impotch.calcul.eco.indice.ge.FournisseurIndicePrixConsommation;

public class FournisseurIndicePeriodiquePrixConsommation implements FournisseurIndicePeriodique {

	private FournisseurIndicePrixConsommation fournisseurIndice;
	private BaseIndiceGenevoisPrixConsommation base;
	
	public void setFournisseurIndice(FournisseurIndicePrixConsommation fournisseur) {
		this.fournisseurIndice = fournisseur;
	}

	
	/**
	 * @param base the base to set
	 */
	protected void setBase(BaseIndiceGenevoisPrixConsommation base) {
		this.base = base;
	}


	@Override
	public BigDecimal getIndice(int periodeFiscale) {
		return fournisseurIndice.fournirMoyenne(base, periodeFiscale - 2, Calendar.SEPTEMBER, periodeFiscale -1, Calendar.AUGUST);
	}

}
