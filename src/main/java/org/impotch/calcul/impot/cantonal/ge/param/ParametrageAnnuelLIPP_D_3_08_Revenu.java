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
package org.impotch.calcul.impot.cantonal.ge.param;

import org.impotch.bareme.BaremeParTranche;

public interface ParametrageAnnuelLIPP_D_3_08_Revenu {
    int getAnnee();

    /**
     * Retourne le montant de la déduction sociale pour chaque charge de famille.
     * Ce montant est indiqué dans la loi LIPP D 3 18 à l'article 39
     * @return le montant de la déduction sociale pour chaque charge de famille
     */
    int deductionSocialeRevenuParChargeDeFamille();

    BaremeParTranche deductionSocialeBeneficiairesRentesAVSouAIPersonneSeule();
    /**
     * Retourne le montant de la déduction mentionnée à l’article 36
     * @return montant de la déduction mentionnée à l’article 36
     */
    int deductionDoubleActivite();

}
