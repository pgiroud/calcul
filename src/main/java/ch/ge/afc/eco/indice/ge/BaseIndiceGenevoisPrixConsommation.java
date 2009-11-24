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
package ch.ge.afc.eco.indice.ge;

import java.util.Calendar;

public enum BaseIndiceGenevoisPrixConsommation {
	
	SEPTEMBRE_1966(Calendar.SEPTEMBER,1966), 
	SEPTEMBRE_1977(Calendar.SEPTEMBER,1977), 
	DECEMBRE_1992(Calendar.DECEMBER,1992),
	MAI_1993(Calendar.MAY,1993),
	MAI_2000(Calendar.MAY,2000),
	DECEMBRE_2005(Calendar.DECEMBER,2005);
	
	private final int annee;
	private final int mois;
	
	BaseIndiceGenevoisPrixConsommation(int mois, int annee) {
		this.annee = annee;
		this.mois = mois;
	}
	
	public int getMois() {
		return mois;
	}
	
	public int getAnnee() {
		return annee;
	}
	
	
}
