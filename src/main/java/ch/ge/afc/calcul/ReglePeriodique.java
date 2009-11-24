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
package ch.ge.afc.calcul;

/**
 * Une règle périodique est une règle définie pour une période. Dans notre
 * cadre, la période est nécessairement une année.
 * 
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 */
public abstract class ReglePeriodique {

	private final int periode;
	
	/**
	 * Construit la règle périodique en fournissant l'année
	 * @param annee la période
	 */
	public ReglePeriodique(int annee) {
		periode = annee;
	}
	
	/**
	 * Retourne la période.
	 * @return la période, une année
	 */
	public int getAnnee() {
		return periode;
	}
}
