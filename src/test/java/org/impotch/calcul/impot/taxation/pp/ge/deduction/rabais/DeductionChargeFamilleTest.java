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
package org.impotch.calcul.impot.taxation.pp.ge.deduction.rabais;

import org.impotch.calcul.impot.Souverainete;
import org.impotch.calcul.impot.indexation.ge.FournisseurIndexGenevoisEnMemoire;
import org.impotch.calcul.impot.taxation.pp.*;
import org.impotch.calcul.impot.taxation.pp.ge.deduction.FournisseurDeductionPP;
import org.impotch.calcul.impot.taxation.pp.ge.deduction.FournisseurDeductionPPEnMemoire;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.impotch.calcul.impot.cantonal.ge.ContexteTestCH_GE.CTX_TST_CH_GE;

public class DeductionChargeFamilleTest {

    private SituationFamiliale construireSituationCoupleAvecUneCharge() {
        return ConstructeurSituationFamiliale.couple()
                .enfant().age(10).fournir();
    }

    private FournisseurDeductionPP fournisseurDeductionPP =
            new FournisseurDeductionPPEnMemoire(CTX_TST_CH_GE.getParametrageLIPP());

    private void test(int annee, int montantAttendu) {
        SituationFamiliale situation = construireSituationCoupleAvecUneCharge();
        assertThat(fournisseurDeductionPP.getRegleDeductionSocialeCharge(annee).getMontantDeduction(situation))
                .isEqualTo(BigDecimal.valueOf(montantAttendu));
    }

    @Test
    public void test2010() {
        test(2010,9_000);
    }

    @Test
    public void test2011() {
        test(2011,10_000);
    }
    @Test
    public void test2012() {
        test(2012,10_000);
    }
    @Test
    public void test2013() {
        test(2013,10_078);
    }
    @Test
    public void test2014() {
        test(2014,10_078);
    }
    @Test
    public void test2015() {
        test(2015,10_078);
    }
    @Test
    public void test2016() {
        test(2016,10_078);
    }
    @Test
    public void test2017() {
        test(2017,9_980);
    }
    @Test
    public void test2018() {
        test(2018,9_980);
    }
    @Test
    public void test2019() {
        test(2019,9_980);
    }
    @Test
    public void test2020() {
        test(2020,9_980);
    }
    @Test
    public void test2021() {
        test(2021,13_000);
    }
    @Test
    public void test2022() {
        test(2022,13_000);
    }
    @Test
    public void test2023() {
        test(2023,13_000);
    }
    @Test
    public void test2024() {
        test(2024,13_536);
    }

}
