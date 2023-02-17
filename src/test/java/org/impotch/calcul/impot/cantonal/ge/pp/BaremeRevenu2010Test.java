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
package org.impotch.calcul.impot.cantonal.ge.pp;


import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

import org.impotch.bareme.Bareme;

import static org.assertj.core.api.Assertions.assertThat;
import static org.impotch.calcul.impot.cantonal.ge.ContexteTestCH_GE.CTX_TST_CH_GE;

public class BaremeRevenu2010Test {

    private FournisseurRegleImpotCantonalGE fournisseur = CTX_TST_CH_GE.getFournisseurRegleImpotCantonalGE();

    @Test
    public void baremeSource() {
        Bareme bareme = fournisseur.getBaremeRevenu(2010);
        assertThat(bareme.calcul(BigDecimal.valueOf(17753))).isEqualTo(new BigDecimal("18.10"));
    }

    @Test
    public void borneBareme() {
        Bareme bareme = fournisseur.getBaremeRevenu(2010);
        assertThat(bareme.calcul(BigDecimal.valueOf(17527))).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(BigDecimal.valueOf(21117))).isEqualTo(new BigDecimal("287.20"));
        assertThat(bareme.calcul(BigDecimal.valueOf(23229))).isEqualTo(new BigDecimal("477.30"));
        assertThat(bareme.calcul(BigDecimal.valueOf(25340))).isEqualTo(new BigDecimal("688.40"));
        assertThat(bareme.calcul(BigDecimal.valueOf(27452))).isEqualTo(new BigDecimal("920.70"));
        assertThat(bareme.calcul(BigDecimal.valueOf(32731))).isEqualTo(new BigDecimal("1554.20"));
        assertThat(bareme.calcul(BigDecimal.valueOf(36955))).isEqualTo(new BigDecimal("2103.30"));
        assertThat(bareme.calcul(BigDecimal.valueOf(41179))).isEqualTo(new BigDecimal("2694.65"));
        assertThat(bareme.calcul(BigDecimal.valueOf(45402))).isEqualTo(new BigDecimal("3307.00"));
        assertThat(bareme.calcul(BigDecimal.valueOf(72854))).isEqualTo(new BigDecimal("7424.80"));
        assertThat(bareme.calcul(BigDecimal.valueOf(119312))).isEqualTo(new BigDecimal("14625.80"));
        assertThat(bareme.calcul(BigDecimal.valueOf(160490))).isEqualTo(new BigDecimal("21214.30"));
        assertThat(bareme.calcul(BigDecimal.valueOf(181608))).isEqualTo(new BigDecimal("24698.75"));
        assertThat(bareme.calcul(BigDecimal.valueOf(259742))).isEqualTo(new BigDecimal("37981.55"));
        assertThat(bareme.calcul(BigDecimal.valueOf(276636))).isEqualTo(new BigDecimal("40938.00"));
        assertThat(bareme.calcul(BigDecimal.valueOf(389613))).isEqualTo(new BigDecimal("61273.85"));
        assertThat(bareme.calcul(BigDecimal.valueOf(610287))).isEqualTo(new BigDecimal("102098.55"));
        assertThat(bareme.calcul(BigDecimal.valueOf(1001944))).isEqualTo(new BigDecimal("176513.40"));
        assertThat(bareme.calcul(BigDecimal.valueOf(2003887))).isEqualTo(new BigDecimal("366882.55"));
    }

    @Test
    public void montantImpot() {
        Bareme bareme = fournisseur.getBaremeRevenu(2010);
        assertThat(bareme.calcul(BigDecimal.valueOf(100000))).isEqualTo(new BigDecimal("11632.45"));
        assertThat(bareme.calcul(BigDecimal.valueOf(44467))).isEqualTo(new BigDecimal("3171.40"));
    }
}
