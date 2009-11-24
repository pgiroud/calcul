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
package ch.ge.afc.calcul.impot.france;

import ch.ge.afc.calcul.bareme.Bareme;
import ch.ge.afc.calcul.bareme.BaremeTauxMarginalConstantParTranche;
import ch.ge.afc.util.TypeArrondi;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class FournisseurFrance {

	public Bareme getBaremeRevenu(int annee) {
		BaremeTauxMarginalConstantParTranche.Constructeur constructeur = new BaremeTauxMarginalConstantParTranche.Constructeur();
		if (2005 == annee) {
			constructeur.tranche( 4412, "  0 %");
			constructeur.tranche( 8677, "  6.83 %");
			constructeur.tranche(15224, " 19.14 %");
			constructeur.tranche(24731, " 28.26 %");
			constructeur.tranche(40241, " 37.38 %");
			constructeur.tranche(49624, " 42.62 %");
			constructeur.derniereTranche("48.09 %");
		} else if (2006 == annee) {
			constructeur.tranche( 5614, "  0 %");
			constructeur.tranche(11198, "  5.5 %");
			constructeur.tranche(24872, " 14 %");
			constructeur.tranche(66679, " 30 %");
			constructeur.derniereTranche("40 %");
		} else if (2007 == annee) {
			constructeur.tranche( 5687, "  0 %");
			constructeur.tranche(11344, "  5.5 %");
			constructeur.tranche(25195, " 14 %");
			constructeur.tranche(67546, " 30 %");
			constructeur.derniereTranche("40 %");
		} else if (2008 == annee) {
			constructeur.tranche( 5852, "  0 %");
			constructeur.tranche(11673, "  5.5 %");
			constructeur.tranche(25926, " 14 %");
			constructeur.tranche(69505, " 30 %");
			constructeur.derniereTranche("40 %");
		} else {
			throw new IllegalArgumentException("Pas de barème pour l'année " + annee);
		}
		constructeur.typeArrondi(TypeArrondi.FRANC_INF);
		return constructeur.construire();
	}
}
