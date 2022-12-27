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
package org.impotch.calcul.impot.taxation.pp.ge.deduction;

import org.impotch.calcul.impot.taxation.pp.DeductionSociale;

public interface FournisseurDeductionPP {
    /**
     * Retourne la règle calculant la déduction sociale sur les charges (voir article 39 de la LIPP).
     *
     * @param annee Une année supérieure ou égale à 2010
     * @return la règle calculant la déduction sociale sur les charges.
     */
    DeductionSociale getRegleDeductionSocialeCharge(int annee);

    /**
     * Retourne la règle calculant la déduction en cas d'activité lucrative des 2 conjoints
     * @param annee L'année pour laquelle la règle va s'appliquer
     * @return la règle de calcul
     */
    DeductionSociale getRegleDeductionDoubleActivite(int annee);

    /**
     * Retourne la déduction sociale pour les bénéficiaires de rentes AVS ou AI.
     * Voir article 40 de la LIPP.
     * @param annee Une année supérieure ou égale à 2010
     * @return la règle de calcul des déductions sociales pour rentiers AVS AI
     */
    DeductionBeneficiaireRentesAVSAI getDeductionBeneficiaireRenteAVSAI(int annee);

}
