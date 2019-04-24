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


import org.impotch.bareme.Bareme;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(locations = {"/beansCH_GE.xml"})
public class BaremeFortuneTest {

    @Resource(name = "fournisseurRegleImpotCantonalGE")
    private FournisseurRegleImpotCantonalGE fournisseur;

    @Test
    public void fortune2007() {
        Bareme baremeFortune = fournisseur.getBaremeFortune(2007);
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(100000))).isEqualTo(new BigDecimal("175.00"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(120000))).isEqualTo(new BigDecimal("215.85"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(300000))).isEqualTo(new BigDecimal("662.50"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(3000000))).isEqualTo(new BigDecimal("11766.90"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(108318))).isEqualTo(new BigDecimal("189.55"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(216635))).isEqualTo(new BigDecimal("433.25"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(324953))).isEqualTo(new BigDecimal("731.10"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(433270))).isEqualTo(new BigDecimal("1056.05"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(649905))).isEqualTo(new BigDecimal("1760.10"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(866541))).isEqualTo(new BigDecimal("2518.35"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(1083176))).isEqualTo(new BigDecimal("3330.75"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(1299811))).isEqualTo(new BigDecimal("4197.30"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(1624764))).isEqualTo(new BigDecimal("5578.35"));
    }

    @Test
    public void fortune2008() {
        Bareme baremeFortune = fournisseur.getBaremeFortune(2008);
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(108601))).isEqualTo(new BigDecimal("190.05"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(217202))).isEqualTo(new BigDecimal("434.40"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(325803))).isEqualTo(new BigDecimal("733.05"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(434405))).isEqualTo(new BigDecimal("1058.85"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(651607))).isEqualTo(new BigDecimal("1764.75"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(868809))).isEqualTo(new BigDecimal("2524.95"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(1086011))).isEqualTo(new BigDecimal("3339.45"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(1303214))).isEqualTo(new BigDecimal("4208.25"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(1629017))).isEqualTo(new BigDecimal("5592.90"));
    }

    @Test
    public void fortuneSupplementaire2007() {
        Bareme bareme = fournisseur.getBaremeFortuneSupplementaire(2007);
        assertThat(bareme.calcul(BigDecimal.valueOf(100000))).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(BigDecimal.valueOf(120000))).isEqualTo(new BigDecimal("1.30"));
        assertThat(bareme.calcul(BigDecimal.valueOf(300000))).isEqualTo(new BigDecimal("23.65"));
        assertThat(bareme.calcul(BigDecimal.valueOf(3000000))).isEqualTo(new BigDecimal("2362.25"));
        assertThat(bareme.calcul(BigDecimal.valueOf(108318))).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(BigDecimal.valueOf(216635))).isEqualTo(new BigDecimal("12.20"));
        assertThat(bareme.calcul(BigDecimal.valueOf(324953))).isEqualTo(new BigDecimal("27.10"));
        assertThat(bareme.calcul(BigDecimal.valueOf(433270))).isEqualTo(new BigDecimal("59.60"));
        assertThat(bareme.calcul(BigDecimal.valueOf(649905))).isEqualTo(new BigDecimal("130.00"));
        assertThat(bareme.calcul(BigDecimal.valueOf(866541))).isEqualTo(new BigDecimal("243.75"));
        assertThat(bareme.calcul(BigDecimal.valueOf(1083176))).isEqualTo(new BigDecimal("365.60"));
        assertThat(bareme.calcul(BigDecimal.valueOf(1299811))).isEqualTo(new BigDecimal("538.90"));
        assertThat(bareme.calcul(BigDecimal.valueOf(1624764))).isEqualTo(new BigDecimal("815.10"));
        assertThat(bareme.calcul(BigDecimal.valueOf(3249527))).isEqualTo(new BigDecimal("2642.95"));
    }

    @Test
    public void fortuneSupplementaire2008() {
        Bareme bareme = fournisseur.getBaremeFortuneSupplementaire(2008);
        assertThat(bareme.calcul(BigDecimal.valueOf(108601))).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(BigDecimal.valueOf(217202))).isEqualTo(new BigDecimal("12.20"));
        assertThat(bareme.calcul(BigDecimal.valueOf(325803))).isEqualTo(new BigDecimal("27.15"));
        assertThat(bareme.calcul(BigDecimal.valueOf(434405))).isEqualTo(new BigDecimal("59.75"));
        assertThat(bareme.calcul(BigDecimal.valueOf(651607))).isEqualTo(new BigDecimal("130.35"));
        assertThat(bareme.calcul(BigDecimal.valueOf(868809))).isEqualTo(new BigDecimal("244.40"));
        assertThat(bareme.calcul(BigDecimal.valueOf(1086011))).isEqualTo(new BigDecimal("366.60"));
        assertThat(bareme.calcul(BigDecimal.valueOf(1303214))).isEqualTo(new BigDecimal("540.35"));
        assertThat(bareme.calcul(BigDecimal.valueOf(1629017))).isEqualTo(new BigDecimal("817.30"));
        assertThat(bareme.calcul(BigDecimal.valueOf(3258034))).isEqualTo(new BigDecimal("2649.95"));
    }

    @Test
    public void fortune2009() {
        Bareme baremeFortune = fournisseur.getBaremeFortune(2009);
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(111059))).isEqualTo(new BigDecimal("194.35"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(222117))).isEqualTo(new BigDecimal("444.25"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(333176))).isEqualTo(new BigDecimal("749.65"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(444234))).isEqualTo(new BigDecimal("1082.80"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(666352))).isEqualTo(new BigDecimal("1804.70"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(888469))).isEqualTo(new BigDecimal("2582.10"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(1110586))).isEqualTo(new BigDecimal("3415.05"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(1332703))).isEqualTo(new BigDecimal("4303.50"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(1388000))).isEqualTo(new BigDecimal("4538.50"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(1665879))).isEqualTo(new BigDecimal("5719.50"));
    }

    @Test
    public void fortuneSupplementaire2009() {
        Bareme bareme = fournisseur.getBaremeFortuneSupplementaire(2009);
        assertThat(bareme.calcul(BigDecimal.valueOf(111059))).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(BigDecimal.valueOf(222117))).isEqualTo(new BigDecimal("12.50"));
        assertThat(bareme.calcul(BigDecimal.valueOf(333176))).isEqualTo(new BigDecimal("27.75"));
        assertThat(bareme.calcul(BigDecimal.valueOf(444234))).isEqualTo(new BigDecimal("61.05"));
        assertThat(bareme.calcul(BigDecimal.valueOf(666352))).isEqualTo(new BigDecimal("133.25"));
        assertThat(bareme.calcul(BigDecimal.valueOf(888469))).isEqualTo(new BigDecimal("249.85"));
        assertThat(bareme.calcul(BigDecimal.valueOf(1110586))).isEqualTo(new BigDecimal("374.80"));
        assertThat(bareme.calcul(BigDecimal.valueOf(1332703))).isEqualTo(new BigDecimal("552.50"));
        assertThat(bareme.calcul(BigDecimal.valueOf(1388000))).isEqualTo(new BigDecimal("599.50"));
        assertThat(bareme.calcul(BigDecimal.valueOf(1665879))).isEqualTo(new BigDecimal("835.70"));
        assertThat(bareme.calcul(BigDecimal.valueOf(3331758))).isEqualTo(new BigDecimal("2709.80"));
    }

    @Test
    public void fortune2010() {
        Bareme baremeFortune = fournisseur.getBaremeFortune(2010);
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(111059))).isEqualTo(new BigDecimal("194.35"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(222117))).isEqualTo(new BigDecimal("444.15"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(333176))).isEqualTo(new BigDecimal("749.30"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(444234))).isEqualTo(new BigDecimal("1082.35"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(666352))).isEqualTo(new BigDecimal("1804.00"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(888469))).isEqualTo(new BigDecimal("2581.10"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(1110586))).isEqualTo(new BigDecimal("3413.55"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(1332703))).isEqualTo(new BigDecimal("4301.50"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(1665879))).isEqualTo(new BigDecimal("5716.85"));
    }

    @Test
    public void fortuneSupplementaire2010() {
        Bareme bareme = fournisseur.getBaremeFortuneSupplementaire(2010);
        assertThat(bareme.calcul(BigDecimal.valueOf(111059))).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(BigDecimal.valueOf(222117))).isEqualTo(new BigDecimal("12.45"));
        assertThat(bareme.calcul(BigDecimal.valueOf(333176))).isEqualTo(new BigDecimal("27.70"));
        assertThat(bareme.calcul(BigDecimal.valueOf(444234))).isEqualTo(new BigDecimal("60.90"));
        assertThat(bareme.calcul(BigDecimal.valueOf(666352))).isEqualTo(new BigDecimal("133.10"));
        assertThat(bareme.calcul(BigDecimal.valueOf(888469))).isEqualTo(new BigDecimal("249.50"));
        assertThat(bareme.calcul(BigDecimal.valueOf(1110586))).isEqualTo(new BigDecimal("374.35"));
        assertThat(bareme.calcul(BigDecimal.valueOf(1332703))).isEqualTo(new BigDecimal("551.55"));
        assertThat(bareme.calcul(BigDecimal.valueOf(1665879))).isEqualTo(new BigDecimal("834.65"));
        assertThat(bareme.calcul(BigDecimal.valueOf(3331758))).isEqualTo(new BigDecimal("2707.85"));
    }

    @Test
    public void fortune2018() {
        Bareme baremeFortune = fournisseur.getBaremeFortune(2018);
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(111167))).isEqualTo(new BigDecimal("194.55"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(222333))).isEqualTo(new BigDecimal("444.65"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(333500))).isEqualTo(new BigDecimal("750.35"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(444666))).isEqualTo(new BigDecimal("1083.85"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(667000))).isEqualTo(new BigDecimal("1806.45"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(889332))).isEqualTo(new BigDecimal("2584.60"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(1111665))).isEqualTo(new BigDecimal("3418.35"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(1333998))).isEqualTo(new BigDecimal("4307.70"));
        assertThat(baremeFortune.calcul(BigDecimal.valueOf(1667498))).isEqualTo(new BigDecimal("5725.10"));
    }

    @Test
    public void fortuneSupplementaire2018() {
        Bareme bareme = fournisseur.getBaremeFortuneSupplementaire(2018);
        assertThat(bareme.calcul(BigDecimal.valueOf(111167))).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(BigDecimal.valueOf(222333))).isEqualTo(new BigDecimal("12.50"));
        assertThat(bareme.calcul(BigDecimal.valueOf(333500))).isEqualTo(new BigDecimal("27.80"));
        assertThat(bareme.calcul(BigDecimal.valueOf(444666))).isEqualTo(new BigDecimal("61.15"));
        assertThat(bareme.calcul(BigDecimal.valueOf(667000))).isEqualTo(new BigDecimal("133.40"));
        assertThat(bareme.calcul(BigDecimal.valueOf(889332))).isEqualTo(new BigDecimal("250.10"));
        assertThat(bareme.calcul(BigDecimal.valueOf(1111665))).isEqualTo(new BigDecimal("375.15"));
        assertThat(bareme.calcul(BigDecimal.valueOf(1333998))).isEqualTo(new BigDecimal("553.00"));
        assertThat(bareme.calcul(BigDecimal.valueOf(1667498))).isEqualTo(new BigDecimal("836.50"));
        assertThat(bareme.calcul(BigDecimal.valueOf(3334996))).isEqualTo(new BigDecimal("2712.45"));
    }
}
