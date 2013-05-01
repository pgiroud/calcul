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
package org.impotch.calcul.impot.federal.pp.source;


import java.math.BigDecimal;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;

import org.impotch.calcul.impot.federal.FournisseurRegleImpotFederal;
import org.impotch.calcul.impot.federal.pp.source.CalculateurImpotSourcePrestationCapitalIFD;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/beans.xml")
public class CalculateurPrestationCapitalTest {

	@Resource(name = "fournisseurRegleImpotFederal")
	private FournisseurRegleImpotFederal constructeur;
	
	@Test
	public void test2009JusquaCentVingCinqMille() {
		CalculateurImpotSourcePrestationCapitalIFD calculateur = constructeur.getCalculateurImpotSourcePrestationCapitalIFD(2009);
		assertEquals("PC pour 1000 francs", new BigDecimal("0.00"),calculateur.calcul(new BigDecimal("1000")));
		assertEquals("PC pour 2000 francs", new BigDecimal("0.00"),calculateur.calcul(new BigDecimal("2000")));
		assertEquals("PC pour 3000 francs", new BigDecimal("0.00"),calculateur.calcul(new BigDecimal("3000")));
		assertEquals("PC pour 4000 francs", new BigDecimal("0.00"),calculateur.calcul(new BigDecimal("4000")));
		assertEquals("PC pour 5000 francs", new BigDecimal("0.00"),calculateur.calcul(new BigDecimal("5000")));
		assertEquals("PC pour 6000 francs", new BigDecimal("0.00"),calculateur.calcul(new BigDecimal("6000")));
		assertEquals("PC pour 7000 francs", new BigDecimal("0.00"),calculateur.calcul(new BigDecimal("7000")));
		assertEquals("PC pour 8000 francs", new BigDecimal("0.00"),calculateur.calcul(new BigDecimal("8000")));
		assertEquals("PC pour 9000 francs", new BigDecimal("0.00"),calculateur.calcul(new BigDecimal("9000")));
		assertEquals("PC pour 10000 francs", new BigDecimal("0.00"),calculateur.calcul(new BigDecimal("10000")));
		assertEquals("PC pour 11000 francs", new BigDecimal("0.00"),calculateur.calcul(new BigDecimal("11000")));
		assertEquals("PC pour 12000 francs", new BigDecimal("0.00"),calculateur.calcul(new BigDecimal("12000")));
		assertEquals("PC pour 13000 francs", new BigDecimal("0.00"),calculateur.calcul(new BigDecimal("13000")));
		assertEquals("PC pour 14000 francs", new BigDecimal("0.00"),calculateur.calcul(new BigDecimal("14000")));
		assertEquals("PC pour 15000 francs", new BigDecimal("0.00"),calculateur.calcul(new BigDecimal("15000")));
		assertEquals("PC pour 16000 francs", new BigDecimal("0.00"),calculateur.calcul(new BigDecimal("16000")));
		assertEquals("PC pour 17000 francs", new BigDecimal("0.00"),calculateur.calcul(new BigDecimal("17000")));
		assertEquals("PC pour 18000 francs", new BigDecimal("0.00"),calculateur.calcul(new BigDecimal("18000")));
		assertEquals("PC pour 19000 francs", new BigDecimal("0.00"),calculateur.calcul(new BigDecimal("19000")));
		assertEquals("PC pour 20000 francs", new BigDecimal("0.00"),calculateur.calcul(new BigDecimal("20000")));
		assertEquals("PC pour 21000 francs", new BigDecimal("0.00"),calculateur.calcul(new BigDecimal("21000")));
		assertEquals("PC pour 22000 francs", new BigDecimal("0.00"),calculateur.calcul(new BigDecimal("22000")));
		assertEquals("PC pour 23000 francs", new BigDecimal("0.00"),calculateur.calcul(new BigDecimal("23000")));
		assertEquals("PC pour 24000 francs", new BigDecimal("0.00"),calculateur.calcul(new BigDecimal("24000")));
		assertEquals("PC pour 25000 francs", new BigDecimal("0.00"),calculateur.calcul(new BigDecimal("25000")));
		assertEquals("PC pour 26000 francs", new BigDecimal("2.50"),calculateur.calcul(new BigDecimal("26000")));
		assertEquals("PC pour 27000 francs", new BigDecimal("5.00"),calculateur.calcul(new BigDecimal("27000")));
		assertEquals("PC pour 28000 francs", new BigDecimal("7.50"),calculateur.calcul(new BigDecimal("28000")));
		assertEquals("PC pour 29000 francs", new BigDecimal("10.00"),calculateur.calcul(new BigDecimal("29000")));
		assertEquals("PC pour 30000 francs", new BigDecimal("12.50"),calculateur.calcul(new BigDecimal("30000")));
		assertEquals("PC pour 31000 francs", new BigDecimal("15.00"),calculateur.calcul(new BigDecimal("31000")));
		assertEquals("PC pour 32000 francs", new BigDecimal("17.50"),calculateur.calcul(new BigDecimal("32000")));
		assertEquals("PC pour 33000 francs", new BigDecimal("20.00"),calculateur.calcul(new BigDecimal("33000")));
		assertEquals("PC pour 34000 francs", new BigDecimal("22.50"),calculateur.calcul(new BigDecimal("34000")));
		assertEquals("PC pour 35000 francs", new BigDecimal("25.00"),calculateur.calcul(new BigDecimal("35000")));
		assertEquals("PC pour 36000 francs", new BigDecimal("27.50"),calculateur.calcul(new BigDecimal("36000")));
		assertEquals("PC pour 37000 francs", new BigDecimal("30.00"),calculateur.calcul(new BigDecimal("37000")));
		assertEquals("PC pour 38000 francs", new BigDecimal("32.50"),calculateur.calcul(new BigDecimal("38000")));
		assertEquals("PC pour 39000 francs", new BigDecimal("35.00"),calculateur.calcul(new BigDecimal("39000")));
		assertEquals("PC pour 40000 francs", new BigDecimal("37.50"),calculateur.calcul(new BigDecimal("40000")));
		assertEquals("PC pour 41000 francs", new BigDecimal("40.00"),calculateur.calcul(new BigDecimal("41000")));
		assertEquals("PC pour 42000 francs", new BigDecimal("42.50"),calculateur.calcul(new BigDecimal("42000")));
		assertEquals("PC pour 43000 francs", new BigDecimal("45.00"),calculateur.calcul(new BigDecimal("43000")));
		assertEquals("PC pour 44000 francs", new BigDecimal("47.50"),calculateur.calcul(new BigDecimal("44000")));
		assertEquals("PC pour 45000 francs", new BigDecimal("50.00"),calculateur.calcul(new BigDecimal("45000")));
		assertEquals("PC pour 46000 francs", new BigDecimal("52.50"),calculateur.calcul(new BigDecimal("46000")));
		assertEquals("PC pour 47000 francs", new BigDecimal("55.00"),calculateur.calcul(new BigDecimal("47000")));
		assertEquals("PC pour 48000 francs", new BigDecimal("57.50"),calculateur.calcul(new BigDecimal("48000")));
		assertEquals("PC pour 49000 francs", new BigDecimal("60.00"),calculateur.calcul(new BigDecimal("49000")));
		assertEquals("PC pour 50000 francs", new BigDecimal("62.50"),calculateur.calcul(new BigDecimal("50000")));
		assertEquals("PC pour 51000 francs", new BigDecimal("69.00"),calculateur.calcul(new BigDecimal("51000")));
		assertEquals("PC pour 52000 francs", new BigDecimal("75.50"),calculateur.calcul(new BigDecimal("52000")));
		assertEquals("PC pour 53000 francs", new BigDecimal("82.00"),calculateur.calcul(new BigDecimal("53000")));
		assertEquals("PC pour 54000 francs", new BigDecimal("88.50"),calculateur.calcul(new BigDecimal("54000")));
		assertEquals("PC pour 55000 francs", new BigDecimal("95.00"),calculateur.calcul(new BigDecimal("55000")));
		assertEquals("PC pour 56000 francs", new BigDecimal("101.50"),calculateur.calcul(new BigDecimal("56000")));
		assertEquals("PC pour 57000 francs", new BigDecimal("108.00"),calculateur.calcul(new BigDecimal("57000")));
		assertEquals("PC pour 58000 francs", new BigDecimal("114.50"),calculateur.calcul(new BigDecimal("58000")));
		assertEquals("PC pour 59000 francs", new BigDecimal("121.00"),calculateur.calcul(new BigDecimal("59000")));
		assertEquals("PC pour 60000 francs", new BigDecimal("127.50"),calculateur.calcul(new BigDecimal("60000")));
		assertEquals("PC pour 61000 francs", new BigDecimal("134.00"),calculateur.calcul(new BigDecimal("61000")));
		assertEquals("PC pour 62000 francs", new BigDecimal("140.50"),calculateur.calcul(new BigDecimal("62000")));
		assertEquals("PC pour 63000 francs", new BigDecimal("147.00"),calculateur.calcul(new BigDecimal("63000")));
		assertEquals("PC pour 64000 francs", new BigDecimal("153.50"),calculateur.calcul(new BigDecimal("64000")));
		assertEquals("PC pour 65000 francs", new BigDecimal("160.00"),calculateur.calcul(new BigDecimal("65000")));
		assertEquals("PC pour 66000 francs", new BigDecimal("166.50"),calculateur.calcul(new BigDecimal("66000")));
		assertEquals("PC pour 67000 francs", new BigDecimal("173.00"),calculateur.calcul(new BigDecimal("67000")));
		assertEquals("PC pour 68000 francs", new BigDecimal("179.50"),calculateur.calcul(new BigDecimal("68000")));
		assertEquals("PC pour 69000 francs", new BigDecimal("186.00"),calculateur.calcul(new BigDecimal("69000")));
		assertEquals("PC pour 70000 francs", new BigDecimal("192.50"),calculateur.calcul(new BigDecimal("70000")));
		assertEquals("PC pour 71000 francs", new BigDecimal("199.00"),calculateur.calcul(new BigDecimal("71000")));
		assertEquals("PC pour 72000 francs", new BigDecimal("205.50"),calculateur.calcul(new BigDecimal("72000")));
		assertEquals("PC pour 73000 francs", new BigDecimal("212.00"),calculateur.calcul(new BigDecimal("73000")));
		assertEquals("PC pour 74000 francs", new BigDecimal("218.50"),calculateur.calcul(new BigDecimal("74000")));
		assertEquals("PC pour 75000 francs", new BigDecimal("225.00"),calculateur.calcul(new BigDecimal("75000")));
		assertEquals("PC pour 76000 francs", new BigDecimal("236.00"),calculateur.calcul(new BigDecimal("76000")));
		assertEquals("PC pour 77000 francs", new BigDecimal("247.00"),calculateur.calcul(new BigDecimal("77000")));
		assertEquals("PC pour 78000 francs", new BigDecimal("258.00"),calculateur.calcul(new BigDecimal("78000")));
		assertEquals("PC pour 79000 francs", new BigDecimal("269.00"),calculateur.calcul(new BigDecimal("79000")));
		assertEquals("PC pour 80000 francs", new BigDecimal("280.00"),calculateur.calcul(new BigDecimal("80000")));
		assertEquals("PC pour 81000 francs", new BigDecimal("291.00"),calculateur.calcul(new BigDecimal("81000")));
		assertEquals("PC pour 82000 francs", new BigDecimal("302.00"),calculateur.calcul(new BigDecimal("82000")));
		assertEquals("PC pour 83000 francs", new BigDecimal("313.00"),calculateur.calcul(new BigDecimal("83000")));
		assertEquals("PC pour 84000 francs", new BigDecimal("324.00"),calculateur.calcul(new BigDecimal("84000")));
		assertEquals("PC pour 85000 francs", new BigDecimal("335.00"),calculateur.calcul(new BigDecimal("85000")));
		assertEquals("PC pour 86000 francs", new BigDecimal("346.00"),calculateur.calcul(new BigDecimal("86000")));
		assertEquals("PC pour 87000 francs", new BigDecimal("357.00"),calculateur.calcul(new BigDecimal("87000")));
		assertEquals("PC pour 88000 francs", new BigDecimal("368.00"),calculateur.calcul(new BigDecimal("88000")));
		assertEquals("PC pour 89000 francs", new BigDecimal("379.00"),calculateur.calcul(new BigDecimal("89000")));
		assertEquals("PC pour 90000 francs", new BigDecimal("390.00"),calculateur.calcul(new BigDecimal("90000")));
		assertEquals("PC pour 91000 francs", new BigDecimal("401.00"),calculateur.calcul(new BigDecimal("91000")));
		assertEquals("PC pour 92000 francs", new BigDecimal("412.00"),calculateur.calcul(new BigDecimal("92000")));
		assertEquals("PC pour 93000 francs", new BigDecimal("423.00"),calculateur.calcul(new BigDecimal("93000")));
		assertEquals("PC pour 94000 francs", new BigDecimal("434.00"),calculateur.calcul(new BigDecimal("94000")));
		assertEquals("PC pour 95000 francs", new BigDecimal("445.00"),calculateur.calcul(new BigDecimal("95000")));
		assertEquals("PC pour 96000 francs", new BigDecimal("456.00"),calculateur.calcul(new BigDecimal("96000")));
		assertEquals("PC pour 97000 francs", new BigDecimal("467.00"),calculateur.calcul(new BigDecimal("97000")));
		assertEquals("PC pour 98000 francs", new BigDecimal("478.00"),calculateur.calcul(new BigDecimal("98000")));
		assertEquals("PC pour 99000 francs", new BigDecimal("489.00"),calculateur.calcul(new BigDecimal("99000")));
		assertEquals("PC pour 100000 francs", new BigDecimal("500.00"),calculateur.calcul(new BigDecimal("100000")));
		assertEquals("PC pour 101000 francs", new BigDecimal("517.00"),calculateur.calcul(new BigDecimal("101000")));
		assertEquals("PC pour 102000 francs", new BigDecimal("534.00"),calculateur.calcul(new BigDecimal("102000")));
		assertEquals("PC pour 103000 francs", new BigDecimal("551.00"),calculateur.calcul(new BigDecimal("103000")));
		assertEquals("PC pour 104000 francs", new BigDecimal("568.00"),calculateur.calcul(new BigDecimal("104000")));
		assertEquals("PC pour 105000 francs", new BigDecimal("585.00"),calculateur.calcul(new BigDecimal("105000")));
		assertEquals("PC pour 106000 francs", new BigDecimal("602.00"),calculateur.calcul(new BigDecimal("106000")));
		assertEquals("PC pour 107000 francs", new BigDecimal("619.00"),calculateur.calcul(new BigDecimal("107000")));
		assertEquals("PC pour 108000 francs", new BigDecimal("636.00"),calculateur.calcul(new BigDecimal("108000")));
		assertEquals("PC pour 109000 francs", new BigDecimal("653.00"),calculateur.calcul(new BigDecimal("109000")));
		assertEquals("PC pour 110000 francs", new BigDecimal("670.00"),calculateur.calcul(new BigDecimal("110000")));
		assertEquals("PC pour 111000 francs", new BigDecimal("687.00"),calculateur.calcul(new BigDecimal("111000")));
		assertEquals("PC pour 112000 francs", new BigDecimal("704.00"),calculateur.calcul(new BigDecimal("112000")));
		assertEquals("PC pour 113000 francs", new BigDecimal("721.00"),calculateur.calcul(new BigDecimal("113000")));
		assertEquals("PC pour 114000 francs", new BigDecimal("738.00"),calculateur.calcul(new BigDecimal("114000")));
		assertEquals("PC pour 115000 francs", new BigDecimal("755.00"),calculateur.calcul(new BigDecimal("115000")));
		assertEquals("PC pour 116000 francs", new BigDecimal("772.00"),calculateur.calcul(new BigDecimal("116000")));
		assertEquals("PC pour 117000 francs", new BigDecimal("789.00"),calculateur.calcul(new BigDecimal("117000")));
		assertEquals("PC pour 118000 francs", new BigDecimal("806.00"),calculateur.calcul(new BigDecimal("118000")));
		assertEquals("PC pour 119000 francs", new BigDecimal("823.00"),calculateur.calcul(new BigDecimal("119000")));
		assertEquals("PC pour 120000 francs", new BigDecimal("840.00"),calculateur.calcul(new BigDecimal("120000")));
		assertEquals("PC pour 121000 francs", new BigDecimal("857.00"),calculateur.calcul(new BigDecimal("121000")));
		assertEquals("PC pour 122000 francs", new BigDecimal("874.00"),calculateur.calcul(new BigDecimal("122000")));
		assertEquals("PC pour 123000 francs", new BigDecimal("891.00"),calculateur.calcul(new BigDecimal("123000")));
		assertEquals("PC pour 124000 francs", new BigDecimal("908.00"),calculateur.calcul(new BigDecimal("124000")));
		assertEquals("PC pour 125000 francs", new BigDecimal("925.00"),calculateur.calcul(new BigDecimal("125000")));
	}
	
	@Test
	public void test2009EntreCentVingCinqEtSeptCentSeptanteCinqMille() {
		CalculateurImpotSourcePrestationCapitalIFD calculateur = constructeur.getCalculateurImpotSourcePrestationCapitalIFD(2009);
		assertEquals("PC pour 675000 francs",new BigDecimal("15225.00"),calculateur.calcul(new BigDecimal("675000")));
	}
	
	@Test
	public void test2009ApresSeptCentSeptanteCinqMille() {
		CalculateurImpotSourcePrestationCapitalIFD calculateur = constructeur.getCalculateurImpotSourcePrestationCapitalIFD(2009);
		assertEquals("PC pour 2'000'000 francs",new BigDecimal("46000.00"),calculateur.calcul(new BigDecimal("2000000")));
	}
}
