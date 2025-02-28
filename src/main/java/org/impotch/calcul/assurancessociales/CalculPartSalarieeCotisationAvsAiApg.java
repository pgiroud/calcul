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
package org.impotch.calcul.assurancessociales;

import java.math.BigDecimal;
import org.impotch.util.TypeArrondi;

import static org.impotch.util.TypeArrondi.CINQ_CENTIEMES_LES_PLUS_PROCHES;

public interface CalculPartSalarieeCotisationAvsAiApg {
	
    BigDecimal calculPartSalarieeCotisationAvsAiApg(BigDecimal montantDeterminant, TypeArrondi arrondi);

    default BigDecimal calculPartSalarieeCotisationAvsAiApg(BigDecimal montantDeterminant) {
        return calculPartSalarieeCotisationAvsAiApg(montantDeterminant,CINQ_CENTIEMES_LES_PLUS_PROCHES);
    }

    BigDecimal calculPartSalarieeCotisationAvs(BigDecimal montantDeterminant, TypeArrondi arrondi);

    default BigDecimal calculPartSalarieeCotisationAvs(BigDecimal montantDeterminant) {
        return calculPartSalarieeCotisationAvs(montantDeterminant,CINQ_CENTIEMES_LES_PLUS_PROCHES);
    }

    BigDecimal calculPartSalarieeCotisationAi(BigDecimal montantDeterminant, TypeArrondi arrondi);

    default BigDecimal calculPartSalarieeCotisationAi(BigDecimal montantDeterminant) {
        return calculPartSalarieeCotisationAi(montantDeterminant,CINQ_CENTIEMES_LES_PLUS_PROCHES);
    }

    BigDecimal calculPartSalarieeCotisationApg(BigDecimal montantDeterminant, TypeArrondi arrondi);

    default BigDecimal calculPartSalarieeCotisationApg(BigDecimal montantDeterminant) {
        return calculPartSalarieeCotisationApg(montantDeterminant,CINQ_CENTIEMES_LES_PLUS_PROCHES);
    }
}
