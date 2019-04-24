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
package org.impotch.calcul.impot.federal.dao;

import java.math.BigDecimal;

import javax.annotation.Resource;


import org.impotch.bareme.Bareme;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(locations = {"/beansCH_IFD.xml"})
public class BaremeIFDPersonnePhysiqueTest {

    @Resource
    private FournisseurBaremeIFD fournisseur;

    @Test
    public void postNumerandoCelibataire2006() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPersonnePhysiquePourPersonneSeule(2006);
        assertThat(bareme.calcul(BigDecimal.valueOf(10000))).isEqualTo(new BigDecimal("0.00"));
        //		assertEquals("Revenu de 10000",new BigDecimal("0.00"), bareme.calcul(new BigDecimal("10000")));
        assertThat(bareme.calcul(BigDecimal.valueOf(16800))).isEqualTo(new BigDecimal("0.00"));
        //		assertEquals("Revenu de 16800",new BigDecimal("0.00"), bareme.calcul(new BigDecimal("16800")));
        assertThat(bareme.calcul(BigDecimal.valueOf(16900))).isEqualTo(new BigDecimal("25.40"));
        //		assertEquals("Revenu de 16900",new BigDecimal("25.40"), bareme.calcul(new BigDecimal("16900")));
        assertThat(bareme.calcul(BigDecimal.valueOf(23000))).isEqualTo(new BigDecimal("72.35"));
        //		assertEquals("Revenu de 23000",new BigDecimal("72.35"), bareme.calcul(new BigDecimal("23000")));
        assertThat(bareme.calcul(BigDecimal.valueOf(29800))).isEqualTo(new BigDecimal("124.70"));
        //		assertEquals("Revenu de 29800",new BigDecimal("124.70"), bareme.calcul(new BigDecimal("29800")));
        assertThat(bareme.calcul(BigDecimal.valueOf(35000))).isEqualTo(new BigDecimal("170.45"));
        //		assertEquals("Revenu de 35000",new BigDecimal("170.45"), bareme.calcul(new BigDecimal("35000")));
        assertThat(bareme.calcul(BigDecimal.valueOf(117000))).isEqualTo(new BigDecimal("4636.75"));
        //		assertEquals("Revenu de 117000",new BigDecimal("4636.75"), bareme.calcul(new BigDecimal("117000")));
        assertThat(bareme.calcul(BigDecimal.valueOf(127100))).isEqualTo(new BigDecimal("5525.55"));
        //		assertEquals("Revenu de 127100",new BigDecimal("5525.55"), bareme.calcul(new BigDecimal("127100")));
        assertThat(bareme.calcul(BigDecimal.valueOf(200000))).isEqualTo(new BigDecimal("14288.15"));
        //		assertEquals("Revenu de 200000",new BigDecimal("14288.15"), bareme.calcul(new BigDecimal("200000")));
        assertThat(bareme.calcul(BigDecimal.valueOf(750000))).isEqualTo(new BigDecimal("86250.00"));
        //		assertEquals("Revenu de 750000",new BigDecimal("86250.00"), bareme.calcul(new BigDecimal("750000")));
        assertThat(bareme.calcul(BigDecimal.valueOf(843600))).isEqualTo(new BigDecimal("97014.00"));
        //		assertEquals("Revenu de 843600",new BigDecimal("97014.00"), bareme.calcul(new BigDecimal("843600")));
    }

    @Test
    public void postNumerandoMarie2006() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPersonnePhysiquePourFamille(2006);
        assertThat(bareme.calcul(BigDecimal.valueOf(10000))).isEqualTo(new BigDecimal("0.00"));
        //		assertEquals("Revenu de 10000",new BigDecimal("0.00"), bareme.calcul(new BigDecimal("10000")));
        assertThat(bareme.calcul(BigDecimal.valueOf(23000))).isEqualTo(new BigDecimal("0.00"));
        //		assertEquals("Revenu de 23000",new BigDecimal("0.00"), bareme.calcul(new BigDecimal("23000")));
        assertThat(bareme.calcul(BigDecimal.valueOf(35000))).isEqualTo(new BigDecimal("83.00"));
        //		assertEquals("Revenu de 35000",new BigDecimal("83.00"), bareme.calcul(new BigDecimal("35000")));
        assertThat(bareme.calcul(BigDecimal.valueOf(117000))).isEqualTo(new BigDecimal("3280.00"));
        //		assertEquals("Revenu de 117000",new BigDecimal("3280.00"), bareme.calcul(new BigDecimal("117000")));
        assertThat(bareme.calcul(BigDecimal.valueOf(129300))).isEqualTo(new BigDecimal("4317.00"));
        //		assertEquals("Revenu de 129300",new BigDecimal("4317.00"), bareme.calcul(new BigDecimal("129300")));
        assertThat(bareme.calcul(BigDecimal.valueOf(843600))).isEqualTo(new BigDecimal("97014.00"));
        //		assertEquals("Revenu de 843600",new BigDecimal("97014.00"), bareme.calcul(new BigDecimal("843600")));
    }

    @Test
    public void postNumerandoCelibataire1996() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPersonnePhysiquePourPersonneSeule(1996);
        assertThat(bareme.calcul(BigDecimal.valueOf(10000))).isEqualTo(new BigDecimal("0.00"));
        //		assertEquals("Revenu de 10000",new BigDecimal("0.00"), bareme.calcul(new BigDecimal("10000")));
        assertThat(bareme.calcul(BigDecimal.valueOf(16000))).isEqualTo(new BigDecimal("0.00"));
        //		assertEquals("Revenu de 16000",new BigDecimal("0.00"), bareme.calcul(new BigDecimal("16000")));
        assertThat(bareme.calcul(BigDecimal.valueOf(16100))).isEqualTo(new BigDecimal("25.40"));
        //		assertEquals("Revenu de 16100",new BigDecimal("25.40"), bareme.calcul(new BigDecimal("16100")));
        assertThat(bareme.calcul(BigDecimal.valueOf(27900))).isEqualTo(new BigDecimal("116.25"));
        //		assertEquals("Revenu de 27900",new BigDecimal("116.25"), bareme.calcul(new BigDecimal("27900")));
        assertThat(bareme.calcul(BigDecimal.valueOf(37000))).isEqualTo(new BigDecimal("205.10"));
        //		assertEquals("Revenu de 37000",new BigDecimal("205.10"), bareme.calcul(new BigDecimal("37000")));
        assertThat(bareme.calcul(BigDecimal.valueOf(115900))).isEqualTo(new BigDecimal("4913.90"));
        //		assertEquals("Revenu de 115900",new BigDecimal("4913.90"), bareme.calcul(new BigDecimal("115900")));
        assertThat(bareme.calcul(BigDecimal.valueOf(124300))).isEqualTo(new BigDecimal("5782.90"));
        //		assertEquals("Revenu de 124300",new BigDecimal("5782.90"), bareme.calcul(new BigDecimal("124300")));
        assertThat(bareme.calcul(BigDecimal.valueOf(200000))).isEqualTo(new BigDecimal("15106.50"));
        //		assertEquals("Revenu de 200000",new BigDecimal("15106.50"), bareme.calcul(new BigDecimal("200000")));
        assertThat(bareme.calcul(BigDecimal.valueOf(750000))).isEqualTo(new BigDecimal("86250.00"));
        //		assertEquals("Revenu de 750000",new BigDecimal("86250.00"), bareme.calcul(new BigDecimal("750000")));
        assertThat(bareme.calcul(BigDecimal.valueOf(843600))).isEqualTo(new BigDecimal("97014.00"));
        //		assertEquals("Revenu de 843600",new BigDecimal("97014.00"), bareme.calcul(new BigDecimal("843600")));
    }

    @Test
    public void praeNumerandoFamille2007() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPraeNumerandoPersonnePhysiquePourFamille(2007);
        assertThat(bareme.calcul(BigDecimal.valueOf(10000))).isEqualTo(new BigDecimal("0.00"));
        //		assertEquals("Revenu de 10000",new BigDecimal("0.00"),bareme.calcul(new BigDecimal("10000")));
        assertThat(bareme.calcul(BigDecimal.valueOf(89600))).isEqualTo(new BigDecimal("1856.00"));
        //		assertEquals("Revenu de 89600",new BigDecimal("1856.00"),bareme.calcul(new BigDecimal("89600")));
        assertThat(bareme.calcul(BigDecimal.valueOf(775800))).isEqualTo(new BigDecimal("89217.00"));
        //		assertEquals("Revenu de 775800",new BigDecimal("89217.00"),bareme.calcul(new BigDecimal("775800")));
    }

    @Test
    public void praeNumerandoCelibataire2007() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPraeNumerandoPersonnePhysiquePourPersonneSeule(2007);
        assertThat(bareme.calcul(BigDecimal.valueOf(10000))).isEqualTo(new BigDecimal("0.00"));
        //		assertEquals("Revenu de 10000",new BigDecimal("0.00"), bareme.calcul(new BigDecimal("10000")));
        assertThat(bareme.calcul(BigDecimal.valueOf(16800))).isEqualTo(new BigDecimal("32.30"));
        //		assertEquals("Revenu de 16800",new BigDecimal("32.30"), bareme.calcul(new BigDecimal("16800")));
        assertThat(bareme.calcul(BigDecimal.valueOf(39000))).isEqualTo(new BigDecimal("270.55"));
        //		assertEquals("Revenu de 39000",new BigDecimal("270.55"), bareme.calcul(new BigDecimal("39000")));
        assertThat(bareme.calcul(BigDecimal.valueOf(78100))).isEqualTo(new BigDecimal("1922.55"));
        //		assertEquals("Revenu de 78100",new BigDecimal("1922.55"), bareme.calcul(new BigDecimal("78100")));
        assertThat(bareme.calcul(BigDecimal.valueOf(775900))).isEqualTo(new BigDecimal("89228.50"));
        //		assertEquals("Revenu de 775900",new BigDecimal("89228.50"), bareme.calcul(new BigDecimal("775900")));
    }


    @Test
    public void postNumerandoCelibataire2011() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPersonnePhysiquePourPersonneSeule(2011);
        assertThat(bareme.calcul(BigDecimal.valueOf(20000))).isEqualTo(new BigDecimal("43.10"));
        //		assertEquals("Revenu de 20000",new BigDecimal("43.10"), bareme.calcul(new BigDecimal("20000")));
        assertThat(bareme.calcul(BigDecimal.valueOf(34000))).isEqualTo(new BigDecimal("153.65"));
        //		assertEquals("Revenu de 34000",new BigDecimal("153.65"), bareme.calcul(new BigDecimal("34000")));
        assertThat(bareme.calcul(BigDecimal.valueOf(55000))).isEqualTo(new BigDecimal("581.30"));
        //        assertEquals("Revenu de 55000",new BigDecimal("581.30"), bareme.calcul(new BigDecimal("55000")));
        assertThat(bareme.calcul(BigDecimal.valueOf(250300))).isEqualTo(new BigDecimal("20268.40"));
        //		assertEquals("Revenu de 250300",new BigDecimal("20268.40"), bareme.calcul(new BigDecimal("250300")));
        assertThat(bareme.calcul(BigDecimal.valueOf(889400))).isEqualTo(new BigDecimal("102281.00"));
        //		assertEquals("Revenu de 889400",new BigDecimal("102281.00"), bareme.calcul(new BigDecimal("889400")));
    }

    @Test
    public void praeNumerandoCelibataire2011() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPraeNumerandoPersonnePhysiquePourPersonneSeule(2011);
        assertThat(bareme.calcul(BigDecimal.valueOf(682100))).isEqualTo(new BigDecimal("78441.05"));
        //        assertEquals("Revenu de 682100",new BigDecimal("78441.05"), bareme.calcul(new BigDecimal("682100")));
        assertThat(bareme.calcul(BigDecimal.valueOf(682200))).isEqualTo(new BigDecimal("78453.00"));
        //        assertEquals("Revenu de 682200",new BigDecimal("78453.00"), bareme.calcul(new BigDecimal("682200")));
    }

    @Test
    public void postNumerandoMarie2011() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPersonnePhysiquePourFamille(2011);
        assertThat(bareme.calcul(BigDecimal.valueOf(889400))).isEqualTo(new BigDecimal("102281.00"));
        //		assertEquals("Revenu de 889400",new BigDecimal("102281.00"), bareme.calcul(new BigDecimal("889400")));
        assertThat(bareme.calcul(BigDecimal.valueOf(889500))).isEqualTo(new BigDecimal("102292.50"));
        //		assertEquals("Revenu de 889500",new BigDecimal("102292.50"), bareme.calcul(new BigDecimal("889500")));
    }

    @Test
    public void postNumerandoCelibataire2012() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPersonnePhysiquePourPersonneSeule(2012);
        assertThat(bareme.calcul(BigDecimal.valueOf(41600))).isEqualTo(new BigDecimal("223.15"));
        //        assertEquals("Revenu de 41600",new BigDecimal("223.15"), bareme.calcul(BigDecimal.valueOf(41600)));
        assertThat(bareme.calcul(BigDecimal.valueOf(755200))).isEqualTo(new BigDecimal("86848.00"));
        //        assertEquals("Revenu de 755200", new BigDecimal("86848.00"),bareme.calcul(BigDecimal.valueOf(755200)));
    }

    @Test
    public void postNumerandoMarie2012() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPersonnePhysiquePourFamille(2012);
        assertThat(bareme.calcul(BigDecimal.valueOf(896000))).isEqualTo(new BigDecimal("103040.00"));
        //        assertEquals("Revenu de 896000",new BigDecimal("103040.00"), bareme.calcul(new BigDecimal("896000")));
    }

    @Test
    public void prestationCapitalSource2016() {
        Bareme bareme = fournisseur.getBaremeImpotSourcePrestationCapital(2016);
        assertThat(bareme.calcul(BigDecimal.valueOf(74300))).isEqualTo(new BigDecimal("182.00"));
        assertThat(bareme.calcul(BigDecimal.valueOf(675000))).isEqualTo(new BigDecimal("14875.00"));
        assertThat(bareme.calcul(BigDecimal.valueOf(2_000_000))).isEqualTo(new BigDecimal("46000.00"));
    }

}
