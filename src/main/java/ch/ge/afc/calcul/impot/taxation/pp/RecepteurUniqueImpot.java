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
package ch.ge.afc.calcul.impot.taxation.pp;

import ch.ge.afc.calcul.impot.Impot;
import ch.ge.afc.calcul.impot.RecepteurImpot;

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
