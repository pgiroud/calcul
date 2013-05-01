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

import org.impotch.bareme.Bareme;
import org.impotch.bareme.BaremeTauxMarginalConstantParTranche;
import org.impotch.util.TypeArrondi;

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
        } else if (2009 == annee) {
            constructeur.tranche( 5875, "  0 %");
            constructeur.tranche(11720, "  5.5 %");
            constructeur.tranche(26030, " 14 %");
            constructeur.tranche(69783, " 30 %");
            constructeur.derniereTranche("40 %");
        } else if (2010 == annee || 2011 == annee) {
            constructeur.tranche( 5963, "  0 %");
            constructeur.tranche(11896, "  5.5 %");
            constructeur.tranche(26420, " 14 %");
            constructeur.tranche(70830, " 30 %");
            constructeur.derniereTranche("41 %");
		} else if (2012 == annee) {
            constructeur.tranche(  5963, " 0 %");
            constructeur.tranche( 11896,"  5.5 %");
            constructeur.tranche( 26420," 14 %");
            constructeur.tranche( 70830," 30 %");
            constructeur.tranche(150000, "41 %");
            constructeur.derniereTranche("45 %");
        } else {
			throw new IllegalArgumentException("Pas de barème pour l'année " + annee);
		}
		constructeur.typeArrondiSurChaqueTranche(TypeArrondi.FRANC_INF);
		return constructeur.construire();
	}
}
