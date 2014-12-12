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
package org.impotch.calcul.impot.cantonal.ge.pp;

import org.impotch.bareme.Bareme;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import javax.annotation.Resource;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/beans.xml")
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
public class Baremes2015Test {

    @Resource(name = "fournisseurRegleImpotCantonalGE")
    private FournisseurRegleImpotCantonalGE fournisseur;


    @Test
    public void borneBaremeRevenu() {
        Bareme bareme = fournisseur.getBaremeRevenu(2015);
        assertThat(bareme.calcul(BigDecimal.valueOf(17663))).isEqualTo("0.00");
        assertThat(bareme.calcul(BigDecimal.valueOf(21281))).isEqualTo("289.45");
        assertThat(bareme.calcul(BigDecimal.valueOf(23409))).isEqualTo("480.95");
        assertThat(bareme.calcul(BigDecimal.valueOf(25537))).isEqualTo("693.75");
        assertThat(bareme.calcul(BigDecimal.valueOf(27665))).isEqualTo("927.85");
        assertThat(bareme.calcul(BigDecimal.valueOf(32985))).isEqualTo("1566.25");
        assertThat(bareme.calcul(BigDecimal.valueOf(37241))).isEqualTo("2119.55");
        assertThat(bareme.calcul(BigDecimal.valueOf(41498))).isEqualTo("2715.55");
        assertThat(bareme.calcul(BigDecimal.valueOf(45754))).isEqualTo("3332.65");
        assertThat(bareme.calcul(BigDecimal.valueOf(73420))).isEqualTo("7482.55");
        assertThat(bareme.calcul(BigDecimal.valueOf(120238))).isEqualTo("14739.35");
        assertThat(bareme.calcul(BigDecimal.valueOf(161736))).isEqualTo("21379.05");
        assertThat(bareme.calcul(BigDecimal.valueOf(183017))).isEqualTo("24890.40");
        assertThat(bareme.calcul(BigDecimal.valueOf(261757))).isEqualTo("38276.20");
        assertThat(bareme.calcul(BigDecimal.valueOf(278782))).isEqualTo("41255.60");
        assertThat(bareme.calcul(BigDecimal.valueOf(392636))).isEqualTo("61749.30");
        assertThat(bareme.calcul(BigDecimal.valueOf(615022))).isEqualTo("102890.70");
    }

    @Test
    public void borneBaremeFortune() {
        Bareme bareme = fournisseur.getBaremeFortune(2015);
        assertThat(bareme.calcul(BigDecimal.valueOf(112138))).isEqualTo("196.25");
        assertThat(bareme.calcul(BigDecimal.valueOf(224276))).isEqualTo("448.55");
        assertThat(bareme.calcul(BigDecimal.valueOf(336414))).isEqualTo("756.95");
        assertThat(bareme.calcul(BigDecimal.valueOf(448551))).isEqualTo("1093.35");
        assertThat(bareme.calcul(BigDecimal.valueOf(672828))).isEqualTo("1822.25");
        assertThat(bareme.calcul(BigDecimal.valueOf(897103))).isEqualTo("2607.20");
        assertThat(bareme.calcul(BigDecimal.valueOf(1121379))).isEqualTo("3448.25");
        assertThat(bareme.calcul(BigDecimal.valueOf(1345654))).isEqualTo("4345.35");
        assertThat(bareme.calcul(BigDecimal.valueOf(1682068))).isEqualTo("5775.10");
    }

    @Test
    public void borneBaremeFortuneSupplementaire() {
        Bareme bareme = fournisseur.getBaremeFortuneSupplementaire(2015);
        assertThat(bareme.calcul(BigDecimal.valueOf(112138))).isEqualTo("0.00");
        assertThat(bareme.calcul(BigDecimal.valueOf(224276))).isEqualTo("12.60");
        assertThat(bareme.calcul(BigDecimal.valueOf(336414))).isEqualTo("28.00");
        assertThat(bareme.calcul(BigDecimal.valueOf(448551))).isEqualTo("61.65");
        assertThat(bareme.calcul(BigDecimal.valueOf(672828))).isEqualTo("134.55");
        assertThat(bareme.calcul(BigDecimal.valueOf(897103))).isEqualTo("252.30");
        assertThat(bareme.calcul(BigDecimal.valueOf(1121379))).isEqualTo("378.45");
        assertThat(bareme.calcul(BigDecimal.valueOf(1345654))).isEqualTo("557.85");
        assertThat(bareme.calcul(BigDecimal.valueOf(1682068))).isEqualTo("843.80");
        assertThat(bareme.calcul(BigDecimal.valueOf(3364137))).isEqualTo("2736.15");
    }


}
