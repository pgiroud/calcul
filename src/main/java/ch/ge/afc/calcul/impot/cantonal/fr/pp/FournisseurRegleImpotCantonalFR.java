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
package ch.ge.afc.calcul.impot.cantonal.fr.pp;

import ch.ge.afc.bareme.Bareme;
import ch.ge.afc.calcul.impot.taxation.pp.ProducteurImpot;

public interface FournisseurRegleImpotCantonalFR {
	Bareme getBaremeRevenu(int annee);
	Bareme getBaremeFortune(int annee);
	
	ProducteurImpot getProducteurImpotsICRevenu(int annee);
	ProducteurImpot getProducteurImpotsICFortune(int annee);
	
}
