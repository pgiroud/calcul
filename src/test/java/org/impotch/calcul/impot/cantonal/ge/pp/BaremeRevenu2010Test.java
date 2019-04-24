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

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import org.impotch.bareme.Bareme;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(locations = {"/beansCH_GE.xml"})
public class BaremeRevenu2010Test {

    @Resource(name = "fournisseurRegleImpotCantonalGE")
    private FournisseurRegleImpotCantonalGE fournisseur;

    @Test
    public void baremeSource() {
        Bareme bareme = fournisseur.getBaremeRevenu(2010);
        assertThat(bareme.calcul(BigDecimal.valueOf(17753))).isEqualTo(new BigDecimal("18.10"));
    }

    @Test
    public void borneBareme() {
        Bareme bareme = fournisseur.getBaremeRevenu(2010);
        assertThat(bareme.calcul(BigDecimal.valueOf(17527))).isEqualTo(new BigDecimal("0.00"));
        //        assertEquals("Pour 17527 francs", new BigDecimal("0.00"), bareme.calcul(new BigDecimal("17527")));
        assertThat(bareme.calcul(BigDecimal.valueOf(21117))).isEqualTo(new BigDecimal("287.20"));
        //        assertEquals("Pour 21117 francs", new BigDecimal("287.20"), bareme.calcul(new BigDecimal("21117")));
        assertThat(bareme.calcul(BigDecimal.valueOf(23229))).isEqualTo(new BigDecimal("477.30"));
        //        assertEquals("Pour 23229 francs", new BigDecimal("477.30"), bareme.calcul(new BigDecimal("23229")));
        assertThat(bareme.calcul(BigDecimal.valueOf(25340))).isEqualTo(new BigDecimal("688.40"));
        //        assertEquals("Pour 25340 francs", new BigDecimal("688.40"), bareme.calcul(new BigDecimal("25340")));
        assertThat(bareme.calcul(BigDecimal.valueOf(27452))).isEqualTo(new BigDecimal("920.70"));
        //        assertEquals("Pour 27452 francs", new BigDecimal("920.70"), bareme.calcul(new BigDecimal("27452")));
        assertThat(bareme.calcul(BigDecimal.valueOf(32731))).isEqualTo(new BigDecimal("1554.20"));
        //        assertEquals("Pour 32731 francs", new BigDecimal("1554.20"), bareme.calcul(new BigDecimal("32731")));
        assertThat(bareme.calcul(BigDecimal.valueOf(36955))).isEqualTo(new BigDecimal("2103.30"));
        //        assertEquals("Pour 36955 francs", new BigDecimal("2103.30"), bareme.calcul(new BigDecimal("36955")));
        assertThat(bareme.calcul(BigDecimal.valueOf(41179))).isEqualTo(new BigDecimal("2694.65"));
        //        assertEquals("Pour 41179 francs", new BigDecimal("2694.65"), bareme.calcul(new BigDecimal("41179")));
        assertThat(bareme.calcul(BigDecimal.valueOf(45402))).isEqualTo(new BigDecimal("3307.00"));
        //        assertEquals("Pour 45402 francs", new BigDecimal("3307.00"), bareme.calcul(new BigDecimal("45402")));
        assertThat(bareme.calcul(BigDecimal.valueOf(72854))).isEqualTo(new BigDecimal("7424.80"));
        //        assertEquals("Pour 72854 francs", new BigDecimal("7424.80"), bareme.calcul(new BigDecimal("72854")));
        assertThat(bareme.calcul(BigDecimal.valueOf(119312))).isEqualTo(new BigDecimal("14625.80"));
        //        assertEquals("Pour 119312 francs", new BigDecimal("14625.80"), bareme.calcul(new BigDecimal("119312")));
        assertThat(bareme.calcul(BigDecimal.valueOf(160490))).isEqualTo(new BigDecimal("21214.30"));
        //        assertEquals("Pour 160490 francs", new BigDecimal("21214.30"), bareme.calcul(new BigDecimal("160490")));
        assertThat(bareme.calcul(BigDecimal.valueOf(181608))).isEqualTo(new BigDecimal("24698.75"));
        //        assertEquals("Pour 181608 francs", new BigDecimal("24698.75"), bareme.calcul(new BigDecimal("181608")));
        assertThat(bareme.calcul(BigDecimal.valueOf(259742))).isEqualTo(new BigDecimal("37981.55"));
        //        assertEquals("Pour 259742 francs", new BigDecimal("37981.55"), bareme.calcul(new BigDecimal("259742")));
        assertThat(bareme.calcul(BigDecimal.valueOf(276636))).isEqualTo(new BigDecimal("40938.00"));
        //        assertEquals("Pour 276636 francs", new BigDecimal("40938.00"), bareme.calcul(new BigDecimal("276636")));
        assertThat(bareme.calcul(BigDecimal.valueOf(389613))).isEqualTo(new BigDecimal("61273.85"));
        //        assertEquals("Pour 389613 francs", new BigDecimal("61273.85"), bareme.calcul(new BigDecimal("389613")));
        assertThat(bareme.calcul(BigDecimal.valueOf(610287))).isEqualTo(new BigDecimal("102098.55"));
        //        assertEquals("Pour 610287 francs", new BigDecimal("102098.55"), bareme.calcul(new BigDecimal("610287")));
        assertThat(bareme.calcul(BigDecimal.valueOf(1001944))).isEqualTo(new BigDecimal("176513.40"));
        //        assertEquals("Pour 1001944 francs", new BigDecimal("176513.40"), bareme.calcul(new BigDecimal("1001944")));
        assertThat(bareme.calcul(BigDecimal.valueOf(2003887))).isEqualTo(new BigDecimal("366882.55"));
        //        assertEquals("Pour 2003887 francs", new BigDecimal("366882.55"), bareme.calcul(new BigDecimal("2003887")));
    }

    @Test
    public void montantImpot() {
        Bareme bareme = fournisseur.getBaremeRevenu(2010);
        assertThat(bareme.calcul(BigDecimal.valueOf(100000))).isEqualTo(new BigDecimal("11632.45"));
        //        assertEquals("Pour 100000 francs", new BigDecimal("11632.45"), bareme.calcul(new BigDecimal("100000")));
        assertThat(bareme.calcul(BigDecimal.valueOf(44467))).isEqualTo(new BigDecimal("3171.40"));
        //        assertEquals("Pour 88935 francs", new BigDecimal("3171.40"), bareme.calcul(new BigDecimal("44467")));

    }
}
