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
package org.impotch.calcul.impot.indexation;

import java.math.BigDecimal;

import org.impotch.bareme.BaremeConstantParTranche;
import org.impotch.bareme.BaremeParTranche;
import org.impotch.bareme.BaremeTauxMarginalConstantParTranche;
import org.impotch.util.TypeArrondi;

public interface Indexateur {
	BigDecimal indexer(BigDecimal montantBase, int annee, TypeArrondi arrondi);
    BaremeParTranche indexer(BaremeParTranche bareme, int annee, TypeArrondi arrondi);
    BaremeTauxMarginalConstantParTranche indexer(BaremeTauxMarginalConstantParTranche bareme, int annee, TypeArrondi arrondi);



    default BigDecimal indexer(BigDecimal montantBase,
                              int annee) {
        return indexer(montantBase, annee, TypeArrondi.UNITE_LA_PLUS_PROCHE);
    }

    default BaremeParTranche indexer(BaremeParTranche bareme, int annee) {
        return indexer(bareme,annee,TypeArrondi.UNITE_LA_PLUS_PROCHE);
    }


    default BaremeTauxMarginalConstantParTranche indexer(BaremeTauxMarginalConstantParTranche bareme, int annee) {
        return indexer(bareme,annee,TypeArrondi.UNITE_LA_PLUS_PROCHE);
    }

}
