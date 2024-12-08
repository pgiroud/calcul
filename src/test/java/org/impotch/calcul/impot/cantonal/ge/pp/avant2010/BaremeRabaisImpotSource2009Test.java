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
/**
 * This file is part of impotch/calcul.
 * <p>
 * impotch/calcul is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 * <p>
 * impotch/calcul is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with impotch/calcul.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.impotch.calcul.impot.cantonal.ge.pp.avant2010;

import java.math.BigDecimal;


import org.impotch.bareme.Bareme;
import org.impotch.calcul.impot.cantonal.ge.pp.FournisseurRegleImpotCantonalGE;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.impotch.calcul.impot.cantonal.ge.ContexteTestCH_GE.CTX_TST_CH_GE;

public class BaremeRabaisImpotSource2009Test {

    private FournisseurRegleImpotCantonalGE fournisseur = CTX_TST_CH_GE.getFournisseurRegleImpotCantonalGE();

    private Bareme baremeSeul2009;
    private Bareme baremeFamille2009;

    @BeforeEach
    public void initialise() throws Exception {
        baremeSeul2009 = fournisseur.getBaremeRevenu(2009);
        baremeFamille2009 = fournisseur.getBaremeRevenuFamille(2009);
    }

    @Test
    public void rabaisImpotImpotSource() {
        assertThat(baremeSeul2009.calcul(BigDecimal.valueOf(16426))).isEqualTo(new BigDecimal("1140.75"));
        assertThat(baremeFamille2009.calcul(BigDecimal.valueOf(30114))).isEqualTo(new BigDecimal("1989.15"));
        assertThat(baremeFamille2009.calcul(BigDecimal.valueOf(37232))).isEqualTo(new BigDecimal("2769.05"));
        assertThat(baremeFamille2009.calcul(BigDecimal.valueOf(38601))).isEqualTo(new BigDecimal("2925.80"));
        assertThat(baremeFamille2009.calcul(BigDecimal.valueOf(39970))).isEqualTo(new BigDecimal("3084.40"));
        assertThat(baremeFamille2009.calcul(BigDecimal.valueOf(44350))).isEqualTo(new BigDecimal("3603.25"));
        assertThat(baremeFamille2009.calcul(BigDecimal.valueOf(45719))).isEqualTo(new BigDecimal("3768.55"));
        assertThat(baremeFamille2009.calcul(BigDecimal.valueOf(47088))).isEqualTo(new BigDecimal("3935.30"));
        assertThat(baremeFamille2009.calcul(BigDecimal.valueOf(51468))).isEqualTo(new BigDecimal("4477.20"));
        assertThat(baremeFamille2009.calcul(BigDecimal.valueOf(52837))).isEqualTo(new BigDecimal("4649.00"));
        assertThat(baremeFamille2009.calcul(BigDecimal.valueOf(54206))).isEqualTo(new BigDecimal("4821.85"));
        assertThat(baremeFamille2009.calcul(BigDecimal.valueOf(58586))).isEqualTo(new BigDecimal("5381.60"));
        assertThat(baremeFamille2009.calcul(BigDecimal.valueOf(59955))).isEqualTo(new BigDecimal("5558.50"));
        assertThat(baremeFamille2009.calcul(BigDecimal.valueOf(61324))).isEqualTo(new BigDecimal("5736.40"));
        assertThat(baremeFamille2009.calcul(BigDecimal.valueOf(65704))).isEqualTo(new BigDecimal("6311.30"));
        assertThat(baremeFamille2009.calcul(BigDecimal.valueOf(67073))).isEqualTo(new BigDecimal("6492.80"));
        assertThat(baremeFamille2009.calcul(BigDecimal.valueOf(68442))).isEqualTo(new BigDecimal("6675.15"));
        assertThat(baremeSeul2009.calcul(BigDecimal.valueOf(15057))).isEqualTo(new BigDecimal("994.60"));
        assertThat(baremeSeul2009.calcul(BigDecimal.valueOf(18616))).isEqualTo(new BigDecimal("1384.50"));
        assertThat(baremeSeul2009.calcul(BigDecimal.valueOf(19985))).isEqualTo(new BigDecimal("1542.20"));
        assertThat(baremeSeul2009.calcul(BigDecimal.valueOf(22175))).isEqualTo(new BigDecimal("1801.60"));
        assertThat(baremeSeul2009.calcul(BigDecimal.valueOf(23544))).isEqualTo(new BigDecimal("1967.65"));
        assertThat(baremeSeul2009.calcul(BigDecimal.valueOf(25734))).isEqualTo(new BigDecimal("2238.55"));
        assertThat(baremeSeul2009.calcul(BigDecimal.valueOf(27103))).isEqualTo(new BigDecimal("2410.75"));
        assertThat(baremeSeul2009.calcul(BigDecimal.valueOf(29293))).isEqualTo(new BigDecimal("2690.30"));
        assertThat(baremeSeul2009.calcul(BigDecimal.valueOf(30662))).isEqualTo(new BigDecimal("2867.25"));
    }

}
