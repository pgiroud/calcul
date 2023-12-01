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
package org.impotch.calcul.impot.cantonal.ge.pp;

import org.impotch.bareme.Bareme;
import org.impotch.calcul.impot.cantonal.ge.pp.avant2010.ProducteurRabaisImpot;
import org.impotch.calcul.impot.taxation.pp.ProducteurImpot;
import org.impotch.util.TypeArrondi;

public interface FournisseurRegleImpotCantonalGE {
	Bareme getBaremeRevenu(int annee);
	Bareme getBaremeRevenuFamille(int annee);
	
	Bareme getBaremeFortune(int annee);
	Bareme getBaremeFortuneSupplementaire(int annee);
	
	ProducteurImpot construireProducteurImpotsCantonauxRevenu(int annee, TypeArrondi typeArrondi);
    ProducteurImpot construireProducteurImpotsCantonauxPC(int annee);
    ProducteurImpot getProducteurImpotsICCRevenu(int annee);
	ProducteurImpot getProducteurImpotsICCFortune(int annee);
	ProducteurImpot getProducteurImpotsICCFortuneSupplementaire(int annee);

	ProducteurRabaisImpot getProducteurRabaisImpot(int annee);
}
