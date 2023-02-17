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

import org.impotch.bareme.Bareme;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.impotch.calcul.impot.federal.ContexteTest_CH.CTX_TST_CH;
public class BaremeIFDPersonnePhysiqueTest {

    private FournisseurBaremeIFD fournisseur = CTX_TST_CH.getFournisseurBaremeIFD();

    @Test
    public void postNumerandoCelibataire2006() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPersonnePhysiquePourPersonneSeule(2006);
        assertThat(bareme.calcul(BigDecimal.valueOf(10000))).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(BigDecimal.valueOf(16800))).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(BigDecimal.valueOf(16900))).isEqualTo(new BigDecimal("25.40"));
        assertThat(bareme.calcul(BigDecimal.valueOf(23000))).isEqualTo(new BigDecimal("72.35"));
        assertThat(bareme.calcul(BigDecimal.valueOf(29800))).isEqualTo(new BigDecimal("124.70"));
        assertThat(bareme.calcul(BigDecimal.valueOf(35000))).isEqualTo(new BigDecimal("170.45"));
        assertThat(bareme.calcul(BigDecimal.valueOf(117000))).isEqualTo(new BigDecimal("4636.75"));
        assertThat(bareme.calcul(BigDecimal.valueOf(127100))).isEqualTo(new BigDecimal("5525.55"));
        assertThat(bareme.calcul(BigDecimal.valueOf(200000))).isEqualTo(new BigDecimal("14288.15"));
        assertThat(bareme.calcul(BigDecimal.valueOf(750000))).isEqualTo(new BigDecimal("86250.00"));
        assertThat(bareme.calcul(BigDecimal.valueOf(843600))).isEqualTo(new BigDecimal("97014.00"));
    }

    @Test
    public void postNumerandoMarie2006() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPersonnePhysiquePourFamille(2006);
        assertThat(bareme.calcul(BigDecimal.valueOf(10000))).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(BigDecimal.valueOf(23000))).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(BigDecimal.valueOf(35000))).isEqualTo(new BigDecimal("83.00"));
        assertThat(bareme.calcul(BigDecimal.valueOf(117000))).isEqualTo(new BigDecimal("3280.00"));
        assertThat(bareme.calcul(BigDecimal.valueOf(129300))).isEqualTo(new BigDecimal("4317.00"));
        assertThat(bareme.calcul(BigDecimal.valueOf(843600))).isEqualTo(new BigDecimal("97014.00"));
     }

    @Test
    public void postNumerandoCelibataire1996() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPersonnePhysiquePourPersonneSeule(1996);
        assertThat(bareme.calcul(BigDecimal.valueOf(10000))).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(BigDecimal.valueOf(16000))).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(BigDecimal.valueOf(16100))).isEqualTo(new BigDecimal("25.40"));
        assertThat(bareme.calcul(BigDecimal.valueOf(27900))).isEqualTo(new BigDecimal("116.25"));
        assertThat(bareme.calcul(BigDecimal.valueOf(37000))).isEqualTo(new BigDecimal("205.10"));
        assertThat(bareme.calcul(BigDecimal.valueOf(115900))).isEqualTo(new BigDecimal("4913.90"));
        assertThat(bareme.calcul(BigDecimal.valueOf(124300))).isEqualTo(new BigDecimal("5782.90"));
        assertThat(bareme.calcul(BigDecimal.valueOf(200000))).isEqualTo(new BigDecimal("15106.50"));
        assertThat(bareme.calcul(BigDecimal.valueOf(750000))).isEqualTo(new BigDecimal("86250.00"));
        assertThat(bareme.calcul(BigDecimal.valueOf(843600))).isEqualTo(new BigDecimal("97014.00"));
    }

    @Test
    public void praeNumerandoFamille2007() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPraeNumerandoPersonnePhysiquePourFamille(2007);
        assertThat(bareme.calcul(BigDecimal.valueOf(10000))).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(BigDecimal.valueOf(89600))).isEqualTo(new BigDecimal("1856.00"));
        assertThat(bareme.calcul(BigDecimal.valueOf(775800))).isEqualTo(new BigDecimal("89217.00"));
    }

    @Test
    public void praeNumerandoCelibataire2007() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPraeNumerandoPersonnePhysiquePourPersonneSeule(2007);
        assertThat(bareme.calcul(BigDecimal.valueOf(10000))).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(BigDecimal.valueOf(16800))).isEqualTo(new BigDecimal("32.30"));
        assertThat(bareme.calcul(BigDecimal.valueOf(39000))).isEqualTo(new BigDecimal("270.55"));
        assertThat(bareme.calcul(BigDecimal.valueOf(78100))).isEqualTo(new BigDecimal("1922.55"));
        assertThat(bareme.calcul(BigDecimal.valueOf(775900))).isEqualTo(new BigDecimal("89228.50"));
    }


    @Test
    public void postNumerandoCelibataire2011() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPersonnePhysiquePourPersonneSeule(2011);
        assertThat(bareme.calcul(BigDecimal.valueOf(20000))).isEqualTo(new BigDecimal("43.10"));
        assertThat(bareme.calcul(BigDecimal.valueOf(34000))).isEqualTo(new BigDecimal("153.65"));
        assertThat(bareme.calcul(BigDecimal.valueOf(55000))).isEqualTo(new BigDecimal("581.30"));
        assertThat(bareme.calcul(BigDecimal.valueOf(250300))).isEqualTo(new BigDecimal("20268.40"));
        assertThat(bareme.calcul(BigDecimal.valueOf(889400))).isEqualTo(new BigDecimal("102281.00"));
    }

    @Test
    public void praeNumerandoCelibataire2011() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPraeNumerandoPersonnePhysiquePourPersonneSeule(2011);
        assertThat(bareme.calcul(BigDecimal.valueOf(682100))).isEqualTo(new BigDecimal("78441.05"));
        assertThat(bareme.calcul(BigDecimal.valueOf(682200))).isEqualTo(new BigDecimal("78453.00"));
    }

    @Test
    public void postNumerandoMarie2011() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPersonnePhysiquePourFamille(2011);
        assertThat(bareme.calcul(BigDecimal.valueOf(889400))).isEqualTo(new BigDecimal("102281.00"));
        assertThat(bareme.calcul(BigDecimal.valueOf(889500))).isEqualTo(new BigDecimal("102292.50"));
    }

    @Test
    public void postNumerandoCelibataire2012() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPersonnePhysiquePourPersonneSeule(2012);
        assertThat(bareme.calcul(BigDecimal.valueOf(41600))).isEqualTo(new BigDecimal("223.15"));
        assertThat(bareme.calcul(BigDecimal.valueOf(755200))).isEqualTo(new BigDecimal("86848.00"));
    }

    @Test
    public void postNumerandoCelibataire2023AuLimiteDeTranche() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPersonnePhysiquePourPersonneSeule(2023);
        assertThat(bareme.calcul(BigDecimal.valueOf(14800))).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(BigDecimal.valueOf(32200))).isEqualTo(new BigDecimal("133.95"));
        assertThat(bareme.calcul(BigDecimal.valueOf(42200))).isEqualTo(new BigDecimal("221.95"));
        assertThat(bareme.calcul(BigDecimal.valueOf(56200))).isEqualTo(new BigDecimal("591.55"));
        assertThat(bareme.calcul(BigDecimal.valueOf(73900))).isEqualTo(new BigDecimal("1117.20"));
        assertThat(bareme.calcul(BigDecimal.valueOf(79600))).isEqualTo(new BigDecimal("1455.75"));
        assertThat(bareme.calcul(BigDecimal.valueOf(105500))).isEqualTo(new BigDecimal("3165.15"));
        assertThat(bareme.calcul(BigDecimal.valueOf(137200))).isEqualTo(new BigDecimal("5954.75"));
        assertThat(bareme.calcul(BigDecimal.valueOf(179400))).isEqualTo(new BigDecimal("10596.75"));
        assertThat(bareme.calcul(BigDecimal.valueOf(769600))).isEqualTo(new BigDecimal("88503.15"));
        assertThat(bareme.calcul(BigDecimal.valueOf(769700))).isEqualTo(new BigDecimal("88515.50"));
    }

    @Test
    public void postNumerandoCelibataire2012AuLimiteDeTranche() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPersonnePhysiquePourPersonneSeule(2012);
        assertThat(bareme.calcul(BigDecimal.valueOf(14500))).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(BigDecimal.valueOf(31600))).isEqualTo(new BigDecimal("131.65"));
        assertThat(bareme.calcul(BigDecimal.valueOf(41400))).isEqualTo(new BigDecimal("217.90"));
        assertThat(bareme.calcul(BigDecimal.valueOf(55200))).isEqualTo(new BigDecimal("582.20"));
        assertThat(bareme.calcul(BigDecimal.valueOf(72500))).isEqualTo(new BigDecimal("1096.00"));
        assertThat(bareme.calcul(BigDecimal.valueOf(78100))).isEqualTo(new BigDecimal("1428.60"));
        assertThat(bareme.calcul(BigDecimal.valueOf(103600))).isEqualTo(new BigDecimal("3111.60"));
        assertThat(bareme.calcul(BigDecimal.valueOf(134600))).isEqualTo(new BigDecimal("5839.60"));
        assertThat(bareme.calcul(BigDecimal.valueOf(176000))).isEqualTo(new BigDecimal("10393.60"));
        assertThat(bareme.calcul(BigDecimal.valueOf(755200))).isEqualTo(new BigDecimal("86848.00"));
    }

    @Test
    public void postNumerandoMarie2012() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPersonnePhysiquePourFamille(2012);
        assertThat(bareme.calcul(BigDecimal.valueOf(896000))).isEqualTo(new BigDecimal("103040.00"));
    }

    @Test
    public void prestationCapitalSource2016() {
        Bareme bareme = fournisseur.getBaremeImpotSourcePrestationCapital(2016);
        assertThat(bareme.calcul(BigDecimal.valueOf(74300))).isEqualTo(new BigDecimal("182.00"));
        assertThat(bareme.calcul(BigDecimal.valueOf(675000))).isEqualTo(new BigDecimal("14875.00"));
        assertThat(bareme.calcul(BigDecimal.valueOf(2_000_000))).isEqualTo(new BigDecimal("46000.00"));
    }

}
