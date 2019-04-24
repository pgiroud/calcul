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
package org.impotch.calcul.impot.cantonal.fr.pp;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import org.impotch.bareme.Bareme;

import static org.assertj.core.api.Assertions.assertThat;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "/beansCH_FR.xml")
//@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
@SpringJUnitConfig(locations = "/beansCH_FR.xml")
public class BaremeRevenu2008Test {

	@Resource(name = "fournisseurRegleImpotCantonalFR")
	private FournisseurRegleImpotCantonalFR fournisseur;
	
	private Bareme bareme;
	
	@BeforeEach
	public void setUp() throws Exception {
		bareme = fournisseur.getBaremeRevenu(2008);
	}

	private void calcul(int montantImposable, String attendu) {
		assertThat(bareme.calcul(new BigDecimal(montantImposable))).isEqualTo(new BigDecimal(attendu));
//		String retour = bareme.calcul(new BigDecimal(montantImposable)).toPlainString();
//	    assertEquals("Barème pour " + montantImposable,attendu,retour);
	}
	
	@Test
	public void borneBareme() {
		calcul(4900,"49.00");
		calcul(16600,"692.35");
		calcul(29800,"1851.40");
		calcul(45800,"3686.15");
		calcul(60500,"5509.30");
		calcul(74100,"7420.65");
		calcul(142700,"17812.65");
		calcul(169900,"22223.60");
		calcul(196200,"26487.00");
	}
	
	@Test
	public void instructionDecla() {
		calcul(4900,"49.00");
		calcul(5000,"51.35");
		calcul(5500,"64.00");
		calcul(6000,"77.95");
		calcul(6500,"93.30");
		calcul(7000,"110.00");
		calcul(7500,"128.05");
		calcul(8000,"147.45");
		calcul(8500,"168.25");
		calcul(9000,"190.35");
		calcul(9500,"213.85");
		calcul(10000,"238.70");
		calcul(10500,"264.95");
		calcul(11000,"292.50");
		calcul(11500,"321.45");
		calcul(12000,"351.75");
		calcul(12500,"383.40");
		calcul(13000,"416.40");
		calcul(13500,"450.80");
		calcul(14000,"486.55");
		calcul(14500,"523.60");
		calcul(15000,"562.10");
		calcul(15500,"601.90");
		calcul(16000,"643.05");
		calcul(16500,"685.60");
		calcul(17000,"719.55");
		calcul(17500,"754.30");
		calcul(18000,"789.80");
		calcul(18500,"826.05");
		calcul(19000,"863.10");
		calcul(19500,"900.95");
		calcul(20000,"939.55");
		calcul(21000,"1019.05");
		calcul(22000,"1101.70");
		calcul(23000,"1187.40");
		calcul(24000,"1276.25");
		calcul(25000,"1368.20");
		calcul(26000,"1463.20");
		calcul(27000,"1561.35");
		calcul(28000,"1662.55");
		calcul(29000,"1766.90");
		calcul(30000,"1870.70");
		calcul(31000,"1968.70");
		calcul(32000,"2069.00");
		calcul(33000,"2171.65");
		calcul(34000,"2276.55");
		calcul(35000,"2383.75");
		calcul(36000,"2493.25");
		calcul(37000,"2605.05");
		calcul(38000,"2719.15");
		calcul(39000,"2835.55");
		calcul(40000,"2954.30");
		calcul(41000,"3075.30");
		calcul(42000,"3198.60");
		calcul(43000,"3324.20");
		calcul(44000,"3452.10");
		calcul(45000,"3582.30");
		calcul(46000,"3708.90");
		calcul(47000,"3823.35");
		calcul(48000,"3939.25");
		calcul(49000,"4056.60");
		calcul(50000,"4175.40");
		calcul(51000,"4295.65");
		calcul(52000,"4417.30");
		calcul(53000,"4540.40");
		calcul(54000,"4664.95");
		calcul(55000,"4790.95");
		calcul(56000,"4918.35");
		calcul(57000,"5047.25");
		calcul(58000,"5177.55");
		calcul(59000,"5309.30");
		calcul(60000,"5442.50");
		calcul(61000,"5575.30");
		calcul(62000,"5708.20");
		calcul(63000,"5842.50");
		calcul(64000,"5978.10");
		calcul(65000,"6115.05");
		calcul(66000,"6253.35");
		calcul(67000,"6393.00");
		calcul(68000,"6534.00");
		calcul(69000,"6676.30");
		calcul(70000,"6819.95");
		calcul(71000,"6964.95");
		calcul(72000,"7111.30");
		calcul(73000,"7258.95");
		calcul(74000,"7408.00");
		calcul(75000,"7535.10");
		calcul(76000,"7662.95");
		calcul(77000,"7791.50");
		calcul(78000,"7920.75");
		calcul(79000,"8050.75");
		calcul(80000,"8181.45");
		calcul(81000,"8312.85");
		calcul(82000,"8445.00");
		calcul(83000,"8577.90");
		calcul(84000,"8711.45");
		calcul(85000,"8845.80");
		calcul(86000,"8980.80");
		calcul(87000,"9116.55");
		calcul(88000,"9253.00");
		calcul(89000,"9390.20");
		calcul(90000,"9528.10");
		calcul(91000,"9666.75");
		calcul(92000,"9806.10");
		calcul(93000,"9946.15");
		calcul(94000,"10086.95");
		calcul(95000,"10228.45");
		calcul(96000,"10370.70");
		calcul(97000,"10513.65");
		calcul(98000,"10657.30");
		calcul(99000,"10801.70");
		calcul(100000,"10946.80");
		calcul(101000,"11092.65");
		calcul(102000,"11239.20");
		calcul(103000,"11386.45");
		calcul(104000,"11534.45");
		calcul(105000,"11683.15");
		calcul(106000,"11832.55");
		calcul(107000,"11982.70");
		calcul(108000,"12133.60");
		calcul(109000,"12285.15");
		calcul(110000,"12437.50");
		calcul(111000,"12590.50");
		calcul(112000,"12744.25");
		calcul(113000,"12898.70");
		calcul(114000,"13053.90");
		calcul(115000,"13209.80");
		calcul(116000,"13366.45");
		calcul(117000,"13523.80");
		calcul(118000,"13681.85");
		calcul(119000,"13840.65");
		calcul(120000,"14000.15");
		calcul(121000,"14160.40");
		calcul(122000,"14321.35");
		calcul(123000,"14483.00");
		calcul(124000,"14645.40");
		calcul(125000,"14808.50");
		calcul(126000,"14972.35");
		calcul(127000,"15136.90");
		calcul(128000,"15302.15");
		calcul(129000,"15468.15");
		calcul(130000,"15634.85");
		calcul(131000,"15802.25");
		calcul(132000,"15970.40");
		calcul(133000,"16139.30");
		calcul(134000,"16308.85");
		calcul(135000,"16479.20");
		calcul(136000,"16650.20");
		calcul(137000,"16821.95");
		calcul(138000,"16994.40");
		calcul(139000,"17167.60");
		calcul(140000,"17341.50");
		calcul(141000,"17516.15");
		calcul(142000,"17691.50");
		calcul(143000,"17859.55");
		calcul(144000,"18016.15");
		calcul(145000,"18173.15");
		calcul(146000,"18330.60");
		calcul(147000,"18488.50");
		calcul(148000,"18646.80");
		calcul(149000,"18805.60");
		calcul(150000,"18964.80");
		calcul(151000,"19124.45");
		calcul(152000,"19284.55");
		calcul(153000,"19445.10");
		calcul(154000,"19606.05");
		calcul(155000,"19767.45");
		calcul(156000,"19929.30");
		calcul(157000,"20091.60");
		calcul(158000,"20254.35");
		calcul(159000,"20417.50");
		calcul(160000,"20581.10");
		calcul(161000,"20745.15");
		calcul(162000,"20909.65");
		calcul(163000,"21074.60");
		calcul(164000,"21239.95");
		calcul(165000,"21405.80");
		calcul(166000,"21572.05");
		calcul(167000,"21738.70");
		calcul(168000,"21905.85");
		calcul(169000,"22073.45");
		calcul(170000,"22239.40");
		calcul(171000,"22397.60");
		calcul(172000,"22556.10");
		calcul(173000,"22714.90");
		calcul(174000,"22874.05");
		calcul(175000,"23033.50");
		calcul(176000,"23193.30");
		calcul(177000,"23353.40");
		calcul(178000,"23513.80");
		calcul(179000,"23674.55");
		calcul(180000,"23835.60");
		calcul(190000,"25463.80");
		calcul(200000,"27000.00");
		calcul(210000,"28350.00");
		calcul(220000,"29700.00");
		calcul(230000,"31050.00");
		calcul(240000,"32400.00");
		calcul(250000,"33750.00");
		calcul(260000,"35100.00");
		calcul(270000,"36450.00");
		calcul(280000,"37800.00");
		calcul(290000,"39150.00");
		calcul(300000,"40500.00");
		calcul(310000,"41850.00");
		calcul(320000,"43200.00");
		calcul(330000,"44550.00");
		calcul(340000,"45900.00");
		calcul(350000,"47250.00");
		calcul(350400,"47304.00");
	}
	
	
}
