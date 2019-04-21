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
import org.impotch.bareme.ConstructeurBaremeTauxMarginal;
import org.impotch.util.TypeArrondi;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class FournisseurFrance {

	public Bareme getBaremeRevenu(int annee) {
		ConstructeurBaremeTauxMarginal constructeur = new ConstructeurBaremeTauxMarginal();
		if (2018 == annee) {
			constructeur.premiereTranche(  9965, " 0 %");
			constructeur.tranche( 9965,27520," 14 %");
			constructeur.tranche( 27520,73780," 30 %");
			constructeur.tranche(73780,156245, "41 %");
			constructeur.derniereTranche(156245,"45 %");
		} else if (2017 == annee) {
			constructeur.premiereTranche(  9807, " 0 %");
			constructeur.tranche( 9807,27086," 14 %");
			constructeur.tranche( 27086,72617," 30 %");
			constructeur.tranche(72617,153783, "41 %");
			constructeur.derniereTranche(153783,"45 %");
		} else if (2016 == annee) {
			constructeur.premiereTranche(  9710, " 0 %");
			constructeur.tranche( 9710,26818," 14 %");
			constructeur.tranche( 26818,71898," 30 %");
			constructeur.tranche(71898,152260, "41 %");
			constructeur.derniereTranche(152260,"45 %");
		} else if (2015 == annee) {
			constructeur.premiereTranche(  9700, " 0 %");
			constructeur.tranche( 9700,26791," 14 %");
			constructeur.tranche( 26791,71826," 30 %");
			constructeur.tranche(71826,152108, "41 %");
			constructeur.derniereTranche(152108,"45 %");
		} else if (2014 == annee) {
			constructeur.premiereTranche(  9690, " 0 %");
			constructeur.tranche( 9690,26764," 14 %");
			constructeur.tranche( 26764,71754," 30 %");
			constructeur.tranche(71754,151956, "41 %");
			constructeur.derniereTranche(151956,"45 %");
		} else if (2013 == annee) {
			constructeur.premiereTranche(  6011, " 0 %");
			constructeur.tranche( 6011,11991,"  5.5 %");
			constructeur.tranche( 11991,26631," 14 %");
			constructeur.tranche( 26631,71397," 30 %");
			constructeur.tranche(71397,151200, "41 %");
			constructeur.derniereTranche(151200,"45 %");
		} else if (2012 == annee) {
			constructeur.premiereTranche(  5963, " 0 %");
			constructeur.tranche( 5963,11896,"  5.5 %");
			constructeur.tranche( 11896,26420," 14 %");
			constructeur.tranche( 26420,70830," 30 %");
			constructeur.tranche(70830,150000, "41 %");
			constructeur.derniereTranche(150000,"45 %");
		} else if (2010 == annee || 2011 == annee) {
			constructeur.premiereTranche( 5963, "  0 %");
			constructeur.tranche(5963,11896, "  5.5 %");
			constructeur.tranche(11896,26420, " 14 %");
			constructeur.tranche(26420,70830, " 30 %");
			constructeur.derniereTranche(70830,"41 %");
		} else if (2009 == annee) {
			constructeur.premiereTranche( 5875, "  0 %");
			constructeur.tranche(5875,11720, "  5.5 %");
			constructeur.tranche(11720,26030, " 14 %");
			constructeur.tranche(26030,69783, " 30 %");
			constructeur.derniereTranche(69783,"40 %");
		} else if (2008 == annee) {
			constructeur.premiereTranche( 5852, "  0 %");
			constructeur.tranche(5852,11673, "  5.5 %");
			constructeur.tranche(11673,25926, " 14 %");
			constructeur.tranche(25926,69505, " 30 %");
			constructeur.derniereTranche(69505,"40 %");
		} else if (2007 == annee) {
			constructeur.premiereTranche( 5687, "  0 %");
			constructeur.tranche(5687,11344, "  5.5 %");
			constructeur.tranche(11344,25195, " 14 %");
			constructeur.tranche(25195,67546, " 30 %");
			constructeur.derniereTranche(67546,"40 %");
		} else if (2006 == annee) {
			constructeur.premiereTranche( 5614, "  0 %");
			constructeur.tranche(5614,11198, "  5.5 %");
			constructeur.tranche(11198,24872, " 14 %");
			constructeur.tranche(24872,66679, " 30 %");
			constructeur.derniereTranche(66679,"40 %");
		} else if (2005 == annee) {
			constructeur.premiereTranche( 4412, "  0 %");
			constructeur.tranche( 4412,8677, "  6.83 %");
			constructeur.tranche(8677,15224, " 19.14 %");
			constructeur.tranche(15224,24731, " 28.26 %");
			constructeur.tranche(24731,40241, " 37.38 %");
			constructeur.tranche(40241,49624, " 42.62 %");
			constructeur.derniereTranche(49624,"48.09 %");
		} else {
			throw new IllegalArgumentException("Pas de barème pour l'année " + annee);
		}
		constructeur.typeArrondiSurChaqueTranche(TypeArrondi.FRANC_INF);
		return constructeur.construire();
	}
}
