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


import java.math.BigDecimal;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.impotch.bareme.Bareme;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/beansCH_GE.xml"})
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
public class BaremeRevenu2010Test {

	@Resource(name = "fournisseurRegleImpotCantonalGE")
	private FournisseurRegleImpotCantonalGE fournisseur;

	@Test
	public void baremeSource() {
		Bareme bareme = fournisseur.getBaremeRevenu(2010);
		assertEquals("Pour 17753 francs",new BigDecimal("18.10"),bareme.calcul(new BigDecimal("17753")));
	}
	
	@Test
	public void borneBareme() {
		Bareme bareme = fournisseur.getBaremeRevenu(2010);
		assertEquals("Pour 17527 francs",new BigDecimal("0.00"),bareme.calcul(new BigDecimal("17527")));
		assertEquals("Pour 21117 francs",new BigDecimal("287.20"),bareme.calcul(new BigDecimal("21117")));
		assertEquals("Pour 23229 francs",new BigDecimal("477.30"),bareme.calcul(new BigDecimal("23229")));
		assertEquals("Pour 25340 francs",new BigDecimal("688.40"),bareme.calcul(new BigDecimal("25340")));
		assertEquals("Pour 27452 francs",new BigDecimal("920.70"),bareme.calcul(new BigDecimal("27452")));
		assertEquals("Pour 32731 francs",new BigDecimal("1554.20"),bareme.calcul(new BigDecimal("32731")));
		assertEquals("Pour 36955 francs",new BigDecimal("2103.30"),bareme.calcul(new BigDecimal("36955")));
		assertEquals("Pour 41179 francs",new BigDecimal("2694.65"),bareme.calcul(new BigDecimal("41179")));
		assertEquals("Pour 45402 francs",new BigDecimal("3307.00"),bareme.calcul(new BigDecimal("45402")));
		assertEquals("Pour 72854 francs",new BigDecimal("7424.80"),bareme.calcul(new BigDecimal("72854")));
		assertEquals("Pour 119312 francs",new BigDecimal("14625.80"),bareme.calcul(new BigDecimal("119312")));
		assertEquals("Pour 160490 francs",new BigDecimal("21214.30"),bareme.calcul(new BigDecimal("160490")));
		assertEquals("Pour 181608 francs",new BigDecimal("24698.75"),bareme.calcul(new BigDecimal("181608")));
		assertEquals("Pour 259742 francs",new BigDecimal("37981.55"),bareme.calcul(new BigDecimal("259742")));
		assertEquals("Pour 276636 francs",new BigDecimal("40938.00"),bareme.calcul(new BigDecimal("276636")));
		assertEquals("Pour 389613 francs",new BigDecimal("61273.85"),bareme.calcul(new BigDecimal("389613")));
		assertEquals("Pour 610287 francs",new BigDecimal("102098.55"),bareme.calcul(new BigDecimal("610287")));
		assertEquals("Pour 1001944 francs",new BigDecimal("176513.40"),bareme.calcul(new BigDecimal("1001944")));
		assertEquals("Pour 2003887 francs",new BigDecimal("366882.55"),bareme.calcul(new BigDecimal("2003887")));
	}
	
	@Test
	public void montantImpot() {
		Bareme bareme = fournisseur.getBaremeRevenu(2010);
		assertEquals("Pour 100000 francs",new BigDecimal("11632.45"),bareme.calcul(new BigDecimal("100000")));
		assertEquals("Pour 88935 francs",new BigDecimal("3171.40"),bareme.calcul(new BigDecimal("44467")));
		
	}
}
