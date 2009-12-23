/**
 * This file is part of CalculImpotCH.
 *
 * CalculImpotCH is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * CalculImpotCH is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CalculImpotCH.  If not, see <http://www.gnu.org/licenses/>.
 */
package ch.ge.afc.calcul.impot.cantonal.ge.pp;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.ge.afc.bareme.Bareme;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/beans.xml")
public class BaremeFortuneTest {

	@Resource(name = "fournisseurRegleImpotCantonalGE")
	private FournisseurRegleImpotCantonalGE fournisseur;
	
	@Test 
	public void fortune2007() {
		Bareme baremeFortune = fournisseur.getBaremeFortune(2007);
		assertEquals("Fortune de 100000",new BigDecimal("175.00"),baremeFortune.calcul(new BigDecimal("100000")));
		assertEquals("Fortune de 120000",new BigDecimal("215.85"),baremeFortune.calcul(new BigDecimal("120000")));
		assertEquals("Fortune de 300000",new BigDecimal("662.50"),baremeFortune.calcul(new BigDecimal("300000")));
		assertEquals("Fortune de 3000000",new BigDecimal("11766.90"),baremeFortune.calcul(new BigDecimal("3000000")));
				
		assertEquals("Fortune de 108318",new BigDecimal("189.55"),baremeFortune.calcul(new BigDecimal("108318")));
		assertEquals("Fortune de 216635",new BigDecimal("433.25"),baremeFortune.calcul(new BigDecimal("216635")));
		assertEquals("Fortune de 324953",new BigDecimal("731.10"),baremeFortune.calcul(new BigDecimal("324953")));
		assertEquals("Fortune de 433270",new BigDecimal("1056.05"),baremeFortune.calcul(new BigDecimal("433270")));
		assertEquals("Fortune de 649905",new BigDecimal("1760.10"),baremeFortune.calcul(new BigDecimal("649905")));
		assertEquals("Fortune de 866541",new BigDecimal("2518.35"),baremeFortune.calcul(new BigDecimal("866541")));
		assertEquals("Fortune de 1083176",new BigDecimal("3330.75"),baremeFortune.calcul(new BigDecimal("1083176")));
		assertEquals("Fortune de 1299811",new BigDecimal("4197.30"),baremeFortune.calcul(new BigDecimal("1299811")));
		assertEquals("Fortune de 1624764",new BigDecimal("5578.35"),baremeFortune.calcul(new BigDecimal("1624764")));
		
	}

	@Test 
	public void fortune2008() {
		Bareme baremeFortune = fournisseur.getBaremeFortune(2008);
		
		assertEquals("Fortune de 108601",new BigDecimal("190.05"),baremeFortune.calcul(new BigDecimal("108601")));
		assertEquals("Fortune de 217202",new BigDecimal("434.40"),baremeFortune.calcul(new BigDecimal("217202")));
		assertEquals("Fortune de 325803",new BigDecimal("733.05"),baremeFortune.calcul(new BigDecimal("325803")));
		assertEquals("Fortune de 434405",new BigDecimal("1058.85"),baremeFortune.calcul(new BigDecimal("434405")));
		assertEquals("Fortune de 651607",new BigDecimal("1764.75"),baremeFortune.calcul(new BigDecimal("651607")));
		assertEquals("Fortune de 868809",new BigDecimal("2524.95"),baremeFortune.calcul(new BigDecimal("868809")));
		assertEquals("Fortune de 1086011",new BigDecimal("3339.45"),baremeFortune.calcul(new BigDecimal("1086011")));
		assertEquals("Fortune de 1303214",new BigDecimal("4208.25"),baremeFortune.calcul(new BigDecimal("1303214")));
		assertEquals("Fortune de 1629017",new BigDecimal("5592.90"),baremeFortune.calcul(new BigDecimal("1629017")));
	}
	
	@Test 
	public void fortuneSupplementaire2007() {
		Bareme bareme = fournisseur.getBaremeFortuneSupplementaire(2007);
		assertEquals("Fortune de 100000",new BigDecimal("0.00"),bareme.calcul(new BigDecimal("100000")));
		assertEquals("Fortune de 120000",new BigDecimal("1.30"),bareme.calcul(new BigDecimal("120000")));
		assertEquals("Fortune de 300000",new BigDecimal("23.65"),bareme.calcul(new BigDecimal("300000")));
		assertEquals("Fortune de 3000000",new BigDecimal("2362.25"),bareme.calcul(new BigDecimal("3000000")));
		
		assertEquals("Fortune de 108318",new BigDecimal("0.00"),bareme.calcul(new BigDecimal("108318")));
		assertEquals("Fortune de 216635",new BigDecimal("12.20"),bareme.calcul(new BigDecimal("216635")));
		assertEquals("Fortune de 324953",new BigDecimal("27.10"),bareme.calcul(new BigDecimal("324953")));
		assertEquals("Fortune de 433270",new BigDecimal("59.60"),bareme.calcul(new BigDecimal("433270")));
		assertEquals("Fortune de 649905",new BigDecimal("130.00"),bareme.calcul(new BigDecimal("649905")));
		assertEquals("Fortune de 866541",new BigDecimal("243.75"),bareme.calcul(new BigDecimal("866541")));
		assertEquals("Fortune de 1083176",new BigDecimal("365.60"),bareme.calcul(new BigDecimal("1083176")));
		assertEquals("Fortune de 1299811",new BigDecimal("538.90"),bareme.calcul(new BigDecimal("1299811")));
		assertEquals("Fortune de 1624764",new BigDecimal("815.10"),bareme.calcul(new BigDecimal("1624764")));
		assertEquals("Fortune de 3249527",new BigDecimal("2642.95"),bareme.calcul(new BigDecimal("3249527")));
		
	}

	@Test 
	public void fortuneSupplementaire2008() {
		Bareme bareme = fournisseur.getBaremeFortuneSupplementaire(2008);
		assertEquals("Fortune de 108601",new BigDecimal("0.00"),bareme.calcul(new BigDecimal("108601")));
		assertEquals("Fortune de 217202",new BigDecimal("12.20"),bareme.calcul(new BigDecimal("217202")));
		assertEquals("Fortune de 325803",new BigDecimal("27.15"),bareme.calcul(new BigDecimal("325803")));
		assertEquals("Fortune de 434405",new BigDecimal("59.75"),bareme.calcul(new BigDecimal("434405")));
		assertEquals("Fortune de 651607",new BigDecimal("130.35"),bareme.calcul(new BigDecimal("651607")));
		assertEquals("Fortune de 868809",new BigDecimal("244.40"),bareme.calcul(new BigDecimal("868809")));
		assertEquals("Fortune de 1086011",new BigDecimal("366.60"),bareme.calcul(new BigDecimal("1086011")));
		assertEquals("Fortune de 1303214",new BigDecimal("540.35"),bareme.calcul(new BigDecimal("1303214")));
		assertEquals("Fortune de 1629017",new BigDecimal("817.30"),bareme.calcul(new BigDecimal("1629017")));
		assertEquals("Fortune de 3258034",new BigDecimal("2649.95"),bareme.calcul(new BigDecimal("3258034")));
	}
	
	@Test
	public void fortune2009() {
		Bareme baremeFortune = fournisseur.getBaremeFortune(2009);
		assertEquals("Fortune de 111059",new BigDecimal("194.35"),baremeFortune.calcul(new BigDecimal("111059")));
		assertEquals("Fortune de 222117",new BigDecimal("444.25"),baremeFortune.calcul(new BigDecimal("222117")));
		assertEquals("Fortune de 333176",new BigDecimal("749.65"),baremeFortune.calcul(new BigDecimal("333176")));
		assertEquals("Fortune de 444234",new BigDecimal("1082.80"),baremeFortune.calcul(new BigDecimal("444234")));
		assertEquals("Fortune de 666352",new BigDecimal("1804.70"),baremeFortune.calcul(new BigDecimal("666352")));
		assertEquals("Fortune de 888469",new BigDecimal("2582.10"),baremeFortune.calcul(new BigDecimal("888469")));
		assertEquals("Fortune de 1110586",new BigDecimal("3415.05"),baremeFortune.calcul(new BigDecimal("1110586")));
		assertEquals("Fortune de 1332703",new BigDecimal("4303.50"),baremeFortune.calcul(new BigDecimal("1332703")));
		assertEquals("Fortune de 1388000",new BigDecimal("4538.50"),baremeFortune.calcul(new BigDecimal("1388000")));
		assertEquals("Fortune de 1665879",new BigDecimal("5719.50"),baremeFortune.calcul(new BigDecimal("1665879")));
	}
	
	@Test
	public void fortuneSupplementaire2009() {
		Bareme bareme = fournisseur.getBaremeFortuneSupplementaire(2009);
		assertEquals("Fortune de 111059",new BigDecimal("0.00"),bareme.calcul(new BigDecimal("111059")));
		assertEquals("Fortune de 222117",new BigDecimal("12.50"),bareme.calcul(new BigDecimal("222117")));
		assertEquals("Fortune de 333176",new BigDecimal("27.75"),bareme.calcul(new BigDecimal("333176")));
		assertEquals("Fortune de 444234",new BigDecimal("61.05"),bareme.calcul(new BigDecimal("444234")));
		assertEquals("Fortune de 666352",new BigDecimal("133.25"),bareme.calcul(new BigDecimal("666352")));
		assertEquals("Fortune de 888469",new BigDecimal("249.85"),bareme.calcul(new BigDecimal("888469")));
		assertEquals("Fortune de 1110586",new BigDecimal("374.80"),bareme.calcul(new BigDecimal("1110586")));
		assertEquals("Fortune de 1332703",new BigDecimal("552.50"),bareme.calcul(new BigDecimal("1332703")));
		assertEquals("Fortune de 1388000",new BigDecimal("599.50"),bareme.calcul(new BigDecimal("1388000")));
		assertEquals("Fortune de 1665879",new BigDecimal("835.70"),bareme.calcul(new BigDecimal("1665879")));
		assertEquals("Fortune de 3331758",new BigDecimal("2709.80"),bareme.calcul(new BigDecimal("3331758")));
	}
	
	@Test
	public void fortune2010() {
		Bareme baremeFortune = fournisseur.getBaremeFortune(2010);
		assertEquals("Fortune de 111059",new BigDecimal("194.35"),baremeFortune.calcul(new BigDecimal("111059")));
		assertEquals("Fortune de 222117",new BigDecimal("444.15"),baremeFortune.calcul(new BigDecimal("222117")));
		assertEquals("Fortune de 333176",new BigDecimal("749.30"),baremeFortune.calcul(new BigDecimal("333176")));
		assertEquals("Fortune de 444234",new BigDecimal("1082.35"),baremeFortune.calcul(new BigDecimal("444234")));
		assertEquals("Fortune de 666352",new BigDecimal("1804.00"),baremeFortune.calcul(new BigDecimal("666352")));
		assertEquals("Fortune de 888469",new BigDecimal("2581.10"),baremeFortune.calcul(new BigDecimal("888469")));
		assertEquals("Fortune de 1110586",new BigDecimal("3413.55"),baremeFortune.calcul(new BigDecimal("1110586")));
		assertEquals("Fortune de 1332703",new BigDecimal("4301.50"),baremeFortune.calcul(new BigDecimal("1332703")));
		assertEquals("Fortune de 1665879",new BigDecimal("5716.85"),baremeFortune.calcul(new BigDecimal("1665879")));
	}
	
	@Test
	public void fortuneSupplementaire2010() {
		Bareme bareme = fournisseur.getBaremeFortuneSupplementaire(2010);
		assertEquals("Fortune de 111059",new BigDecimal("0.00"),bareme.calcul(new BigDecimal("111059")));
		assertEquals("Fortune de 222117",new BigDecimal("12.45"),bareme.calcul(new BigDecimal("222117")));
		assertEquals("Fortune de 333176",new BigDecimal("27.70"),bareme.calcul(new BigDecimal("333176")));
		assertEquals("Fortune de 444234",new BigDecimal("60.90"),bareme.calcul(new BigDecimal("444234")));
		assertEquals("Fortune de 666352",new BigDecimal("133.10"),bareme.calcul(new BigDecimal("666352")));
		assertEquals("Fortune de 888469",new BigDecimal("249.50"),bareme.calcul(new BigDecimal("888469")));
		assertEquals("Fortune de 1110586",new BigDecimal("374.35"),bareme.calcul(new BigDecimal("1110586")));
		assertEquals("Fortune de 1332703",new BigDecimal("551.55"),bareme.calcul(new BigDecimal("1332703")));
		assertEquals("Fortune de 1665879",new BigDecimal("834.65"),bareme.calcul(new BigDecimal("1665879")));
		assertEquals("Fortune de 3331758",new BigDecimal("2707.85"),bareme.calcul(new BigDecimal("3331758")));
	}
	
}
