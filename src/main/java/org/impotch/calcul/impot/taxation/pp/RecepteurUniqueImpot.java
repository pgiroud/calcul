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

import org.impotch.calcul.impot.Impot;
import org.impotch.calcul.impot.RecepteurImpot;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class RecepteurUniqueImpot implements RecepteurImpot {

	private Impot valeur;
	private final String codeImpot;
	
	public RecepteurUniqueImpot(String codeImpot) {
		this.codeImpot = codeImpot;
	}
	
	@Override
	public void ajouteImpot(Impot impot) {
		if (codeImpot.equals(impot.getTypeImpot())) {
			valeur = impot;
		}
	}

	/**
	 * @return the valeur
	 */
	public Impot getValeur() {
		return valeur;
	}

}
