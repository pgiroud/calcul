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
package org.impotch.calcul.impot.federal.param;

import org.impotch.bareme.Bareme;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.impotch.calcul.impot.federal.ContexteTest_CH.CTX_TST_CH;

public class Bareme2025PrestationEnCapitalTest {

    private FournisseurBaremeIFD fournisseur = CTX_TST_CH.getFournisseurBaremeIFD();

    @Test
    public void existePersonneSeule() {
        Optional<Bareme> bareme = fournisseur.getBaremeImpotSourcePrestationCapital(2025);
        assertThat(bareme).isNotEmpty();
    }

    @Test
    public void exhaustifSelonPublicationPersonneSeule() {
        Bareme bareme = fournisseur.getBaremeImpotSourcePrestationCapital(2025).get();
        assertThat(bareme.calcul(0)).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(1_000)).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(2_000)).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(3_000)).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(4_000)).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(5_000)).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(6_000)).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(7_000)).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(8_000)).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(9_000)).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(10_000)).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(11_000)).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(12_000)).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(13_000)).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(14_000)).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(15_000)).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(16_000)).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(17_000)).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(18_000)).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(19_000)).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(20_000)).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(21_000)).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(22_000)).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(23_000)).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(24_000)).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(25_000)).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(26_000)).isEqualTo(new BigDecimal("3.50"));
        assertThat(bareme.calcul(26_000)).isEqualTo(new BigDecimal("3.50"));
        assertThat(bareme.calcul(27_000)).isEqualTo(new BigDecimal("7.00"));
        assertThat(bareme.calcul(28_000)).isEqualTo(new BigDecimal("10.50"));
        assertThat(bareme.calcul(29_000)).isEqualTo(new BigDecimal("14.00"));
        assertThat(bareme.calcul(30_000)).isEqualTo(new BigDecimal("17.50"));
        assertThat(bareme.calcul(31_000)).isEqualTo(new BigDecimal("21.00"));
        assertThat(bareme.calcul(32_000)).isEqualTo(new BigDecimal("24.50"));
        assertThat(bareme.calcul(33_000)).isEqualTo(new BigDecimal("28.00"));
        assertThat(bareme.calcul(34_000)).isEqualTo(new BigDecimal("31.50"));
        assertThat(bareme.calcul(35_000)).isEqualTo(new BigDecimal("35.00"));
        assertThat(bareme.calcul(36_000)).isEqualTo(new BigDecimal("38.50"));
        assertThat(bareme.calcul(37_000)).isEqualTo(new BigDecimal("42.00"));
        assertThat(bareme.calcul(38_000)).isEqualTo(new BigDecimal("45.50"));
        assertThat(bareme.calcul(39_000)).isEqualTo(new BigDecimal("49.00"));
        assertThat(bareme.calcul(40_000)).isEqualTo(new BigDecimal("52.50"));
        assertThat(bareme.calcul(41_000)).isEqualTo(new BigDecimal("56.00"));
        assertThat(bareme.calcul(42_000)).isEqualTo(new BigDecimal("59.50"));
        assertThat(bareme.calcul(43_000)).isEqualTo(new BigDecimal("63.00"));
        assertThat(bareme.calcul(44_000)).isEqualTo(new BigDecimal("66.50"));
        assertThat(bareme.calcul(45_000)).isEqualTo(new BigDecimal("70.00"));
        assertThat(bareme.calcul(46_000)).isEqualTo(new BigDecimal("73.50"));
        assertThat(bareme.calcul(47_000)).isEqualTo(new BigDecimal("77.00"));
        assertThat(bareme.calcul(48_000)).isEqualTo(new BigDecimal("80.50"));
        assertThat(bareme.calcul(49_000)).isEqualTo(new BigDecimal("84.00"));
        assertThat(bareme.calcul(50_000)).isEqualTo(new BigDecimal("87.50"));
        assertThat(bareme.calcul(51_000)).isEqualTo(new BigDecimal("93.00"));
        assertThat(bareme.calcul(52_000)).isEqualTo(new BigDecimal("98.50"));
        assertThat(bareme.calcul(53_000)).isEqualTo(new BigDecimal("104.00"));
        assertThat(bareme.calcul(54_000)).isEqualTo(new BigDecimal("109.50"));
        assertThat(bareme.calcul(55_000)).isEqualTo(new BigDecimal("115.00"));
        assertThat(bareme.calcul(56_000)).isEqualTo(new BigDecimal("120.50"));
        assertThat(bareme.calcul(57_000)).isEqualTo(new BigDecimal("126.00"));
        assertThat(bareme.calcul(58_000)).isEqualTo(new BigDecimal("131.50"));
        assertThat(bareme.calcul(59_000)).isEqualTo(new BigDecimal("137.00"));
        assertThat(bareme.calcul(60_000)).isEqualTo(new BigDecimal("142.50"));
        assertThat(bareme.calcul(61_000)).isEqualTo(new BigDecimal("148.00"));
        assertThat(bareme.calcul(62_000)).isEqualTo(new BigDecimal("153.50"));
        assertThat(bareme.calcul(63_000)).isEqualTo(new BigDecimal("159.00"));
        assertThat(bareme.calcul(64_000)).isEqualTo(new BigDecimal("164.50"));
        assertThat(bareme.calcul(65_000)).isEqualTo(new BigDecimal("170.00"));
        assertThat(bareme.calcul(66_000)).isEqualTo(new BigDecimal("175.50"));
        assertThat(bareme.calcul(67_000)).isEqualTo(new BigDecimal("181.00"));
        assertThat(bareme.calcul(68_000)).isEqualTo(new BigDecimal("186.50"));
        assertThat(bareme.calcul(69_000)).isEqualTo(new BigDecimal("192.00"));
        assertThat(bareme.calcul(70_000)).isEqualTo(new BigDecimal("197.50"));
        assertThat(bareme.calcul(71_000)).isEqualTo(new BigDecimal("203.00"));
        assertThat(bareme.calcul(72_000)).isEqualTo(new BigDecimal("208.50"));
        assertThat(bareme.calcul(73_000)).isEqualTo(new BigDecimal("214.00"));
        assertThat(bareme.calcul(74_000)).isEqualTo(new BigDecimal("219.50"));
        assertThat(bareme.calcul(75_000)).isEqualTo(new BigDecimal("225.00"));
        assertThat(bareme.calcul(76_000)).isEqualTo(new BigDecimal("237.50"));
        assertThat(bareme.calcul(77_000)).isEqualTo(new BigDecimal("250.00"));
        assertThat(bareme.calcul(78_000)).isEqualTo(new BigDecimal("262.50"));
        assertThat(bareme.calcul(79_000)).isEqualTo(new BigDecimal("275.00"));
        assertThat(bareme.calcul(80_000)).isEqualTo(new BigDecimal("287.50"));
        assertThat(bareme.calcul(81_000)).isEqualTo(new BigDecimal("300.00"));
        assertThat(bareme.calcul(82_000)).isEqualTo(new BigDecimal("312.50"));
        assertThat(bareme.calcul(83_000)).isEqualTo(new BigDecimal("325.00"));
        assertThat(bareme.calcul(84_000)).isEqualTo(new BigDecimal("337.50"));
        assertThat(bareme.calcul(85_000)).isEqualTo(new BigDecimal("350.00"));
        assertThat(bareme.calcul(86_000)).isEqualTo(new BigDecimal("362.50"));
        assertThat(bareme.calcul(87_000)).isEqualTo(new BigDecimal("375.00"));
        assertThat(bareme.calcul(88_000)).isEqualTo(new BigDecimal("387.50"));
        assertThat(bareme.calcul(89_000)).isEqualTo(new BigDecimal("400.00"));
        assertThat(bareme.calcul(90_000)).isEqualTo(new BigDecimal("412.50"));
        assertThat(bareme.calcul(91_000)).isEqualTo(new BigDecimal("425.00"));
        assertThat(bareme.calcul(92_000)).isEqualTo(new BigDecimal("437.50"));
        assertThat(bareme.calcul(93_000)).isEqualTo(new BigDecimal("450.00"));
        assertThat(bareme.calcul(94_000)).isEqualTo(new BigDecimal("462.50"));
        assertThat(bareme.calcul(95_000)).isEqualTo(new BigDecimal("475.00"));
        assertThat(bareme.calcul(96_000)).isEqualTo(new BigDecimal("487.50"));
        assertThat(bareme.calcul(97_000)).isEqualTo(new BigDecimal("500.00"));
        assertThat(bareme.calcul(98_000)).isEqualTo(new BigDecimal("512.50"));
        assertThat(bareme.calcul(99_000)).isEqualTo(new BigDecimal("525.00"));
        assertThat(bareme.calcul(100_000)).isEqualTo(new BigDecimal("537.50"));
        assertThat(bareme.calcul(101_000)).isEqualTo(new BigDecimal("553.50"));
        assertThat(bareme.calcul(102_000)).isEqualTo(new BigDecimal("569.50"));
        assertThat(bareme.calcul(103_000)).isEqualTo(new BigDecimal("585.50"));
        assertThat(bareme.calcul(104_000)).isEqualTo(new BigDecimal("601.50"));
        assertThat(bareme.calcul(105_000)).isEqualTo(new BigDecimal("617.50"));
        assertThat(bareme.calcul(106_000)).isEqualTo(new BigDecimal("633.50"));
        assertThat(bareme.calcul(107_000)).isEqualTo(new BigDecimal("649.50"));
        assertThat(bareme.calcul(108_000)).isEqualTo(new BigDecimal("665.50"));
        assertThat(bareme.calcul(109_000)).isEqualTo(new BigDecimal("681.50"));
        assertThat(bareme.calcul(110_000)).isEqualTo(new BigDecimal("697.50"));
        assertThat(bareme.calcul(111_000)).isEqualTo(new BigDecimal("713.50"));
        assertThat(bareme.calcul(112_000)).isEqualTo(new BigDecimal("729.50"));
        assertThat(bareme.calcul(113_000)).isEqualTo(new BigDecimal("745.50"));
        assertThat(bareme.calcul(114_000)).isEqualTo(new BigDecimal("761.50"));
        assertThat(bareme.calcul(115_000)).isEqualTo(new BigDecimal("777.50"));
        assertThat(bareme.calcul(116_000)).isEqualTo(new BigDecimal("793.50"));
        assertThat(bareme.calcul(117_000)).isEqualTo(new BigDecimal("809.50"));
        assertThat(bareme.calcul(118_000)).isEqualTo(new BigDecimal("825.50"));
        assertThat(bareme.calcul(119_000)).isEqualTo(new BigDecimal("841.50"));
        assertThat(bareme.calcul(120_000)).isEqualTo(new BigDecimal("857.50"));
        assertThat(bareme.calcul(121_000)).isEqualTo(new BigDecimal("873.50"));
        assertThat(bareme.calcul(122_000)).isEqualTo(new BigDecimal("889.50"));
        assertThat(bareme.calcul(123_000)).isEqualTo(new BigDecimal("905.50"));
        assertThat(bareme.calcul(124_000)).isEqualTo(new BigDecimal("921.50"));
        assertThat(bareme.calcul(125_000)).isEqualTo(new BigDecimal("937.50"));
        assertThat(bareme.calcul(126_000)).isEqualTo(new BigDecimal("957.00"));
        assertThat(bareme.calcul(127_000)).isEqualTo(new BigDecimal("976.50"));
        assertThat(bareme.calcul(128_000)).isEqualTo(new BigDecimal("996.00"));
        assertThat(bareme.calcul(129_000)).isEqualTo(new BigDecimal("1015.50"));
        assertThat(bareme.calcul(130_000)).isEqualTo(new BigDecimal("1035.00"));
        assertThat(bareme.calcul(131_000)).isEqualTo(new BigDecimal("1054.50"));
        assertThat(bareme.calcul(132_000)).isEqualTo(new BigDecimal("1074.00"));
        assertThat(bareme.calcul(133_000)).isEqualTo(new BigDecimal("1093.50"));
        assertThat(bareme.calcul(134_000)).isEqualTo(new BigDecimal("1113.00"));
        assertThat(bareme.calcul(135_000)).isEqualTo(new BigDecimal("1132.50"));
        assertThat(bareme.calcul(136_000)).isEqualTo(new BigDecimal("1152.00"));
        assertThat(bareme.calcul(137_000)).isEqualTo(new BigDecimal("1171.50"));
        assertThat(bareme.calcul(138_000)).isEqualTo(new BigDecimal("1191.00"));
        assertThat(bareme.calcul(139_000)).isEqualTo(new BigDecimal("1210.50"));
        assertThat(bareme.calcul(140_000)).isEqualTo(new BigDecimal("1230.00"));
        assertThat(bareme.calcul(141_000)).isEqualTo(new BigDecimal("1249.50"));
        assertThat(bareme.calcul(142_000)).isEqualTo(new BigDecimal("1269.00"));
        assertThat(bareme.calcul(143_000)).isEqualTo(new BigDecimal("1288.50"));
        assertThat(bareme.calcul(144_000)).isEqualTo(new BigDecimal("1308.00"));
        assertThat(bareme.calcul(145_000)).isEqualTo(new BigDecimal("1327.50"));
        assertThat(bareme.calcul(146_000)).isEqualTo(new BigDecimal("1347.00"));
        assertThat(bareme.calcul(147_000)).isEqualTo(new BigDecimal("1366.50"));
        assertThat(bareme.calcul(148_000)).isEqualTo(new BigDecimal("1386.00"));
        assertThat(bareme.calcul(149_000)).isEqualTo(new BigDecimal("1405.50"));
        assertThat(bareme.calcul(150_000)).isEqualTo(new BigDecimal("1425.00"));
    }

    @Test
    public void avantDerniereTranchePersonneSeule() {
        Bareme bareme = fournisseur.getBaremeImpotSourcePrestationCapital(2025).get();
        assertThat(bareme.calcul(675_000)).isEqualTo(new BigDecimal("15075.00"));
    }

    @Test
    public void derniereTranchePersonneSeule() {
        Bareme bareme = fournisseur.getBaremeImpotSourcePrestationCapital(2025).get();
        assertThat(bareme.calcul(1_100_000)).isEqualTo(new BigDecimal("25075.00"));
    }

    @Test
    public void existeCouple() {
        Optional<Bareme> bareme = fournisseur.getBaremeImpotSourcePrestationCapitalCouple(2025);
        assertThat(bareme).isNotEmpty();
    }

    @Test
    public void avantDerniereTrancheCouple() {
        Bareme bareme = fournisseur.getBaremeImpotSourcePrestationCapitalCouple(2025).get();
        assertThat(bareme.calcul(675_000)).isEqualTo(new BigDecimal("14737.50"));
    }

    @Test
    public void derniereTrancheCouple() {
        Bareme bareme = fournisseur.getBaremeImpotSourcePrestationCapitalCouple(2025).get();
        assertThat(bareme.calcul(1_100_000)).isEqualTo(new BigDecimal("25187.50"));
    }
}
