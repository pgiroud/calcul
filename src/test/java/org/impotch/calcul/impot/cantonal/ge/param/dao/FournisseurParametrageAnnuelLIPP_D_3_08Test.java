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
package org.impotch.calcul.impot.cantonal.ge.param.dao;

import org.impotch.calcul.impot.cantonal.ge.ContexteTestCH_GE;
import org.impotch.calcul.impot.cantonal.ge.param.FournisseurParametrageAnnuelLIPP_D_3_08_enMemoire;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.impotch.calcul.impot.cantonal.ge.ContexteTestCH_GE.CTX_TST_CH_GE;
public class FournisseurParametrageAnnuelLIPP_D_3_08Test {

    @Test
    public void tauxFraisProfessionnels2018() {
        String taux = CTX_TST_CH_GE.getParametrageLIPP().revenu(2018).tauxDeductionFraisProfessionnels();
        assertThat(taux).isEqualTo("3 %");
    }

    @Test
    public void plancherFraisProfessionnels2018() {
        int montant = CTX_TST_CH_GE.getParametrageLIPP().revenu(2018).plancherDeductionFraisProfessionnels();
        assertThat(montant).isEqualTo(599);
    }

    @Test
    public void plafondFraisProfessionnels2018() {
        int montant = CTX_TST_CH_GE.getParametrageLIPP().revenu(2018).plafondDeductionFraisProfessionnels();
        assertThat(montant).isEqualTo(1697);
    }

    @Test
    public void deductionSocialeRevenuCharges2010() {
        int montant = CTX_TST_CH_GE.getParametrageLIPP().revenu(2010).deductionSocialeRevenuParChargeDeFamille();
        assertThat(montant).isEqualTo(9000);
    }

    @Test
    public void deductionSocialeRevenuCharges2011() {
        int montant = CTX_TST_CH_GE.getParametrageLIPP().revenu(2011).deductionSocialeRevenuParChargeDeFamille();
        assertThat(montant).isEqualTo(10000);
    }

    @Test
    public void deductionSocialeRevenuCharges2013() {
        int montant = CTX_TST_CH_GE.getParametrageLIPP().revenu(2013).deductionSocialeRevenuParChargeDeFamille();
        assertThat(montant).isEqualTo(10078);
    }


    @Test
    public void deductionSocialeRevenuCharges2017() {
        int montant = CTX_TST_CH_GE.getParametrageLIPP().revenu(2017).deductionSocialeRevenuParChargeDeFamille();
        assertThat(montant).isEqualTo(9980);
    }

    @Test
    public void deductionSocialeRevenuCharges2021() {
        int montant = CTX_TST_CH_GE.getParametrageLIPP().revenu(2021).deductionSocialeRevenuParChargeDeFamille();
        assertThat(montant).isEqualTo(13000);
    }
}
