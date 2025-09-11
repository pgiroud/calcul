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
package org.impotch.calcul.impot.federal.param;

import java.math.BigDecimal;

import org.assertj.core.description.Description;
import org.impotch.bareme.Bareme;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.impotch.calcul.impot.federal.ContexteTest_CH.CTX_TST_CH;
public class BaremeIFDPersonnePhysiqueTest {

    private FournisseurBaremeIFD fournisseur = CTX_TST_CH.getFournisseurParamIFD();




    @Test
    public void postNumerandoCelibataire2006() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPersonnePhysiquePourPersonneSeule(2006);
        BaremeStr bar = new BaremeStr(bareme);

        assertThat(bar.pour( 10000)).as( "Valeur pour 10 000").isEqualTo(    "0.00");
        assertThat(bar.pour( 16800)).as( "Valeur pour 16 800").isEqualTo(   "24.60");
        assertThat(bar.pour( 16900)).as( "Valeur pour 16 900").isEqualTo(   "25.40");
        assertThat(bar.pour( 23000)).as( "Valeur pour 23 000").isEqualTo(   "72.35");
        assertThat(bar.pour( 29800)).as( "Valeur pour 29 800").isEqualTo(  "124.70");
        assertThat(bar.pour( 35000)).as( "Valeur pour 35 000").isEqualTo(  "170.45");
        assertThat(bar.pour(117000)).as("Valeur pour 117 000").isEqualTo( "4636.75");
        assertThat(bar.pour(127100)).as("Valeur pour 127 100").isEqualTo( "5525.55");
        assertThat(bar.pour(200000)).as("Valeur pour 200 000").isEqualTo("14288.15");
        assertThat(bar.pour(750000)).as("Valeur pour 750 000").isEqualTo("86250.00");
        assertThat(bar.pour(843600)).as("Valeur pour 843 600").isEqualTo("97014.00");
    }

    @Test
    public void postNumerandoMarie2006() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPersonnePhysiquePourFamille(2006);
        BaremeStr bar = new BaremeStr(bareme);

        assertThat(bar.pour(10000)).isEqualTo("0.00");
        assertThat(bar.pour(23000)).isEqualTo("0.00");
        assertThat(bar.pour(35000)).isEqualTo("83.00");
        assertThat(bar.pour(117000)).isEqualTo("3280.00");
        assertThat(bar.pour(129300)).isEqualTo("4317.00");
        assertThat(bar.pour(843600)).isEqualTo("97014.00");
     }

    @Test
    public void postNumerandoCelibataire1996() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPersonnePhysiquePourPersonneSeule(1996);
        BaremeStr bar = new BaremeStr(bareme);

        assertThat(bar.pour(10000)).isEqualTo("0.00");
        assertThat(bar.pour(16000)).isEqualTo("24.60");
        assertThat(bar.pour(16100)).isEqualTo("25.40");
        assertThat(bar.pour(27900)).isEqualTo("116.25");
        assertThat(bar.pour(37000)).isEqualTo("205.10");
        assertThat(bar.pour(115900)).isEqualTo("4913.90");
        assertThat(bar.pour(124300)).isEqualTo("5782.90");
        assertThat(bar.pour(200000)).isEqualTo("15106.50");
        assertThat(bar.pour(750000)).isEqualTo("86250.00");
        assertThat(bar.pour(843600)).isEqualTo("97014.00");
    }



    @Test
    public void praeNumerandoFamille2007() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPraeNumerandoPersonnePhysiquePourFamille(2007);
        BaremeStr bar = new BaremeStr(bareme);

        assertThat(bar.pour(10000)).isEqualTo("0.00");
        assertThat(bar.pour(89600)).isEqualTo("1856.00");
        assertThat(bar.pour(775800)).isEqualTo("89217.00");
    }

    @Test
    public void praeNumerandoCelibataire2007() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPraeNumerandoPersonnePhysiquePourPersonneSeule(2007);
        BaremeStr bar = new BaremeStr(bareme);

        assertThat(bar.pour(10000)).isEqualTo("0.00");
        assertThat(bar.pour(16800)).isEqualTo("32.30");
        assertThat(bar.pour(39000)).isEqualTo("270.55");
        assertThat(bar.pour(78100)).isEqualTo("1922.55");
        assertThat(bar.pour(775900)).isEqualTo("89228.50");
    }


    @Test
    public void postNumerandoCelibataire2011() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPersonnePhysiquePourPersonneSeule(2011);
        BaremeStr bar = new BaremeStr(bareme);

        assertThat(bar.pour(20000)).isEqualTo("43.10");
        assertThat(bar.pour(34000)).isEqualTo("153.65");
        assertThat(bar.pour(55000)).isEqualTo("581.30");
        assertThat(bar.pour(250300)).isEqualTo("20268.40");
        assertThat(bar.pour(889400)).isEqualTo("102281.00");
    }

    @Test
    public void praeNumerandoCelibataire2011() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPraeNumerandoPersonnePhysiquePourPersonneSeule(2011);
        BaremeStr bar = new BaremeStr(bareme);

        assertThat(bar.pour(682100)).isEqualTo("78441.05");
        assertThat(bar.pour(682200)).isEqualTo("78453.00");
    }

    @Test
    public void postNumerandoMarie2011() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPersonnePhysiquePourFamille(2011);
        BaremeStr bar = new BaremeStr(bareme);

        assertThat(bar.pour(889400)).isEqualTo("102281.00");
        assertThat(bar.pour(889500)).isEqualTo("102292.50");
    }

    @Test
    public void postNumerandoCelibataire2012() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPersonnePhysiquePourPersonneSeule(2012);
        BaremeStr bar = new BaremeStr(bareme);

        assertThat(bar.pour(41600)).isEqualTo("223.15");
        assertThat(bar.pour(755200)).isEqualTo("86848.00");
    }

    @Test
    public void postNumerandoCelibataire2023AvecAssietteNonArrondie() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPersonnePhysiquePourPersonneSeule(2023);
        BaremeStr bar = new BaremeStr(bareme);

        assertThat(bar.pour(32227)).isEqualTo("133.95");
    }

    @Test
    public void postNumerandoFamille2025AuLimiteDeTranche() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPersonnePhysiquePourFamille(2025);
        BaremeStr bar = new BaremeStr(bareme);

        assertThat(bar.pour(940_800)).isEqualTo("108191.00");
        assertThat(bar.pour(940_900)).isEqualTo("108203.50");
    }

    @Test
    public void postNumerandoFamille2024AuLimiteDeTranche() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPersonnePhysiquePourFamille(2024);
        BaremeStr bar = new BaremeStr(bareme);

        assertThat(bar.pour( 52_700)).isEqualTo(   "234.00");
        assertThat(bar.pour( 60_500)).isEqualTo(   "390.00");
        assertThat(bar.pour( 78_100)).isEqualTo(   "918.00");
        assertThat(bar.pour( 93_600)).isEqualTo(  "1538.00");
        assertThat(bar.pour(107_200)).isEqualTo(  "2218.00");
        assertThat(bar.pour(119_000)).isEqualTo(  "2926.00");
        assertThat(bar.pour(128_800)).isEqualTo(  "3612.00");
        assertThat(bar.pour(136_600)).isEqualTo(  "4236.00");
        assertThat(bar.pour(142_300)).isEqualTo(  "4749.00");
        assertThat(bar.pour(146_300)).isEqualTo(  "5149.00");
        assertThat(bar.pour(148_300)).isEqualTo(  "5369.00");
        assertThat(bar.pour(150_300)).isEqualTo(  "5609.00");
        assertThat(bar.pour(928_600)).isEqualTo("106788.00");
        assertThat(bar.pour(928_700)).isEqualTo("106800.50");

    }

    @Test
    public void postNumerandoCelibataire2025AuLimiteDeTranche() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPersonnePhysiquePourPersonneSeule(2025);
        BaremeStr bar = new BaremeStr(bareme);

        assertThat(bar.pour(15_200)).isEqualTo("0.00");
        assertThat(bar.pour(33_200)).isEqualTo("138.60");


        assertThat(bar.pour(793_300)).isEqualTo("91229.20");
        assertThat(bar.pour(793_400)).isEqualTo("91241.00");

    }

    @Test
    public void famille2025PourTestSeuillage1Enfant() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPersonnePhysiquePourFamille(2025);
        BaremeStr bar = new BaremeStr(bareme);
        // Rabais 2025 1 enfant = 263 CHF
        // On veut un montant d’impôt de base IB tel que (IB -263) < 25
        assertThat(bar.pour( 55_600)).isEqualTo(   "281.00");
    }

    @Test
    public void postNumerandoCelibataire2024AuLimiteDeTranche() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPersonnePhysiquePourPersonneSeule(2024);
        BaremeStr bar = new BaremeStr(bareme);

        assertThat(bar.pour(15000)).isEqualTo("0.00");
        assertThat(bar.pour(32800)).isEqualTo("137.05");
        assertThat(bar.pour(42900)).isEqualTo("225.90");
        assertThat(bar.pour(57200)).isEqualTo("603.40");
        assertThat(bar.pour(75200)).isEqualTo("1138.00");
        assertThat(bar.pour(81000)).isEqualTo("1482.50");
        assertThat(bar.pour(107400)).isEqualTo("3224.90");
        assertThat(bar.pour(139600)).isEqualTo("6058.50");
        assertThat(bar.pour(182600)).isEqualTo("10788.50");
        assertThat(bar.pour(783200)).isEqualTo("90067.70");
        assertThat(bar.pour(783300)).isEqualTo("90079.50");
    }

    @Test
    public void postNumerandoCelibataire2024() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPersonnePhysiquePourPersonneSeule(2024);
        BaremeStr bar = new BaremeStr(bareme);
        // Dans les tabelles fournies par l'Administration fédérale, devrait être 704.38
        // Mais l’IFD est arrondi aux cin centimes inférieurs
        assertThat(bar.pour(60600)).isEqualTo("704.35");
        assertThat(bar.pour(149500)).isEqualTo("7147.50");
    }

    @Test
    public void postNumerandoCelibataire2023AuLimiteDeTranche() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPersonnePhysiquePourPersonneSeule(2023);
        BaremeStr bar = new BaremeStr(bareme);

        assertThat(bar.pour(14800)).isEqualTo("0.00");
        assertThat(bar.pour(32200)).isEqualTo("133.95");
        assertThat(bar.pour(42200)).isEqualTo("221.95");
        assertThat(bar.pour(56200)).isEqualTo("591.55");
        assertThat(bar.pour(73900)).isEqualTo("1117.20");
        assertThat(bar.pour(79600)).isEqualTo("1455.75");
        assertThat(bar.pour(105500)).isEqualTo("3165.15");
        assertThat(bar.pour(137200)).isEqualTo("5954.75");
        assertThat(bar.pour(179400)).isEqualTo("10596.75");
        assertThat(bar.pour(769600)).isEqualTo("88503.15");
        assertThat(bar.pour(769700)).isEqualTo("88515.50");
    }

    @Test
    public void postNumerandoCelibataire2012AuLimiteDeTranche() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPersonnePhysiquePourPersonneSeule(2012);
        BaremeStr bar = new BaremeStr(bareme);

        assertThat(bar.pour(14500)).isEqualTo("0.00");
        assertThat(bar.pour(31600)).isEqualTo("131.65");
        assertThat(bar.pour(41400)).isEqualTo("217.90");
        assertThat(bar.pour(55200)).isEqualTo("582.20");
        assertThat(bar.pour(72500)).isEqualTo("1096.00");
        assertThat(bar.pour(78100)).isEqualTo("1428.60");
        assertThat(bar.pour(103600)).isEqualTo("3111.60");
        assertThat(bar.pour(134600)).isEqualTo("5839.60");
        assertThat(bar.pour(176000)).isEqualTo("10393.60");
        assertThat(bar.pour(755200)).isEqualTo("86848.00");
    }

    @Test
    public void postNumerandoMarie2012() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPersonnePhysiquePourFamille(2012);
        BaremeStr bar = new BaremeStr(bareme);

        assertThat(bar.pour(896000)).isEqualTo("103040.00");
    }

    @Test
    public void seul2026() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPersonnePhysiquePourPersonneSeule(2026);
        BaremeStr bar = new BaremeStr(bareme);

        assertThat(bar.pour(15_200)).isEqualTo("0.00");
        assertThat(bar.pour(33_200)).isEqualTo("138.60");
        assertThat(bar.pour(43_500)).isEqualTo("229.20");
        assertThat(bar.pour(58_000)).isEqualTo("612.00");
        assertThat(bar.pour(76_200)).isEqualTo("1152.55");
        assertThat(bar.pour(82_100)).isEqualTo("1502.95");
        assertThat(bar.pour(108_900)).isEqualTo("3271.75");
        assertThat(bar.pour(141_500)).isEqualTo("6140.55");
        assertThat(bar.pour(185_100)).isEqualTo("10936.55");
        assertThat(bar.pour(793_900)).isEqualTo("91298.15");
        assertThat(bar.pour(794_000)).isEqualTo("91310.00");
    }

    @Test
    public void famille2026() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPersonnePhysiquePourFamille(2026);
        BaremeStr bar = new BaremeStr(bareme);

        assertThat(bar.pour(29_700)).isEqualTo("0.00");
        assertThat(bar.pour(53_400)).isEqualTo("237.00");
        assertThat(bar.pour(61_300)).isEqualTo("395.00");
        assertThat(bar.pour(79_100)).isEqualTo("929.00");
        assertThat(bar.pour(94_900)).isEqualTo("1561.00");
        assertThat(bar.pour(108_700)).isEqualTo("2251.00");
        assertThat(bar.pour(120_600)).isEqualTo("2965.00");
        assertThat(bar.pour(130_500)).isEqualTo("3658.00");
        assertThat(bar.pour(138_400)).isEqualTo("4290.00");
        assertThat(bar.pour(144_300)).isEqualTo("4821.00");
        assertThat(bar.pour(148_300)).isEqualTo("5221.00");
        assertThat(bar.pour(150_400)).isEqualTo("5452.00");
        assertThat(bar.pour(152_400)).isEqualTo("5692.00");
        assertThat(bar.pour(941_300)).isEqualTo("108249.00");
        assertThat(bar.pour(941_400)).isEqualTo("108261.00");
    }

    private static class BaremeStr {
        private final Bareme bareme;

        public BaremeStr(Bareme bareme) {
            this.bareme = bareme;
        }

        public String pour(int revenu) {
            return bareme.calcul(BigDecimal.valueOf(revenu)).toString();
        }
    }


}
