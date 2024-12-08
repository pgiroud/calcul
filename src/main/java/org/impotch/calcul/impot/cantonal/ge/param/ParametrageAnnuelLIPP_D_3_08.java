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

public class ParametrageAnnuelLIPP_D_3_08 implements ParametrageAnnuelLIPP_D_3_08_Revenu {

    private final int annee;
    private final int deductionDoubleActivite;
    private final ParametrageDeductionSocialeRevenu deducSocialesRevenu;

    public ParametrageAnnuelLIPP_D_3_08(int annee,
                                        int deductionDoubleActivite,
                                        ParametrageDeductionSocialeRevenu deducSocialesRevenu) {
        this.annee = annee;
        this.deductionDoubleActivite = deductionDoubleActivite;
        this.deducSocialesRevenu = deducSocialesRevenu;
    }

    @Override
    public int getAnnee() {
        return annee;
    }

    @Override
    public int deductionDoubleActivite() {
        return deductionDoubleActivite;
    }

    @Override
    public int deductionSocialeRevenuParChargeDeFamille() {
        return deducSocialesRevenu.parCharge();
    }

    @Override
    public BaremeParTranche deductionSocialeBeneficiairesRentesAVSouAIPersonneSeule() {
        return deducSocialesRevenu.rentierAVSAISeul();
    }
}
