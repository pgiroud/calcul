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

/**
 * Fournit les montants prévus aux articles :
 * <ul>
 *     <li></li>
 * </ul>
 */
public interface FournisseurMontantsLimitesPrevoyanceProfessionnelle {
    BigDecimal salaireMinimalAnnuel();
    BigDecimal salaireCoordonnéMinimalAnnuel();

    default BigDecimal deductionCoordination() {
        return salaireMinimalAnnuel().add(salaireCoordonnéMinimalAnnuel());
    }

    BigDecimal limiteSupérieureSalaireCoordonnéAnnuel();

    /**
     * D’après <a href="https://www.fedlex.admin.ch/eli/cc/1983/797_797_797/fr#art_79_c"></>l’article 79c</a> de la LPP,
     * le salaire assurable du salarié ou le revenu assurable de l’indépendant selon le règlement de prévoyance est limité au décuple du montant limite supérieur selon l’art. 8, al. 1.
     * @return la limite des revenus de l’activité lucrative, salariée ou indépendante, assurables
     */
    default BigDecimal limiteDesRevenusActivitéLucrativeAssurables() {
        // Art. 79c de la LPP https://www.fedlex.admin.ch/eli/cc/1983/797_797_797/fr#art_79_c
        return BigDecimal.TEN.multiply(limiteSupérieureSalaireCoordonnéAnnuel());
    }
}
