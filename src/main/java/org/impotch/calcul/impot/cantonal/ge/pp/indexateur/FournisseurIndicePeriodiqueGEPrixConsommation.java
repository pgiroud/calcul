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
package org.impotch.calcul.impot.cantonal.ge.pp.indexateur;

import java.math.BigDecimal;
import java.util.Calendar;

import org.impotch.calcul.eco.indice.ge.BaseIndiceGenevoisPrixConsommation;
import org.impotch.calcul.eco.indice.ge.FournisseurIndicePrixConsommation;

public class FournisseurIndicePeriodiqueGEPrixConsommation implements FournisseurIndicePeriodiqueGE {

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
