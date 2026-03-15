/*
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
package org.impotch.calcul.assurancessociales;

import java.math.BigDecimal;
import org.impotch.util.TypeArrondi;

import static org.impotch.util.TypeArrondi.VINGTIEME_LE_PLUS_PROCHE;

public interface CalculPartSalarieeCotisationAvsAiApg {
	
    BigDecimal calculPartSalarieeCotisationAvsAiApg(BigDecimal montantDeterminant, TypeArrondi arrondi);

    default BigDecimal calculPartSalarieeCotisationAvsAiApg(BigDecimal montantDeterminant) {
        return calculPartSalarieeCotisationAvsAiApg(montantDeterminant, VINGTIEME_LE_PLUS_PROCHE);
    }

    BigDecimal calculPartSalarieeCotisationAvs(BigDecimal montantDeterminant, TypeArrondi arrondi);

    default BigDecimal calculPartSalarieeCotisationAvs(BigDecimal montantDeterminant) {
        return calculPartSalarieeCotisationAvs(montantDeterminant, VINGTIEME_LE_PLUS_PROCHE);
    }

    BigDecimal calculPartSalarieeCotisationAi(BigDecimal montantDeterminant, TypeArrondi arrondi);

    default BigDecimal calculPartSalarieeCotisationAi(BigDecimal montantDeterminant) {
        return calculPartSalarieeCotisationAi(montantDeterminant, VINGTIEME_LE_PLUS_PROCHE);
    }

    BigDecimal calculPartSalarieeCotisationApg(BigDecimal montantDeterminant, TypeArrondi arrondi);

    default BigDecimal calculPartSalarieeCotisationApg(BigDecimal montantDeterminant) {
        return calculPartSalarieeCotisationApg(montantDeterminant, VINGTIEME_LE_PLUS_PROCHE);
    }
}
