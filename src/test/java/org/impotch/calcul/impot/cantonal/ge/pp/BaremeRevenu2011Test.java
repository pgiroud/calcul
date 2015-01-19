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

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.impotch.bareme.Bareme;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/beansCH_GE.xml","/beansAssurancesSociales.xml"})
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
public class BaremeRevenu2011Test {
	@Resource(name = "fournisseurRegleImpotCantonalGE")
	private FournisseurRegleImpotCantonalGE fournisseur;

	@Test
	public void baremeSource() {
		Bareme bareme = fournisseur.getBaremeRevenu(2011);
		assertEquals("Pour 17753 francs",new BigDecimal("9.90"),bareme.calcul(new BigDecimal("17753")));
        assertEquals("Pour 17700 francs",new BigDecimal("5.70"),bareme.calcul(new BigDecimal("17700")));
        assertEquals("Pour 17629 francs",new BigDecimal("0.00"),bareme.calcul(new BigDecimal("17629")));
        assertEquals("Pour 17630 francs",new BigDecimal("0.10"),bareme.calcul(new BigDecimal("17630")));
	}
	
	@Test
	public void borneBareme() {
		Bareme bareme = fournisseur.getBaremeRevenu(2011);
		assertEquals("Pour 17629 francs",new BigDecimal("0.00"),bareme.calcul(new BigDecimal("17527")));
		assertEquals("Pour 21240 francs",new BigDecimal("288.90"),bareme.calcul(new BigDecimal("21240")));
		assertEquals("Pour 23364 francs",new BigDecimal("480.05"),bareme.calcul(new BigDecimal("23364")));
		assertEquals("Pour 25488 francs",new BigDecimal("692.45"),bareme.calcul(new BigDecimal("25488")));
		assertEquals("Pour 27612 francs",new BigDecimal("926.10"),bareme.calcul(new BigDecimal("27612")));
		assertEquals("Pour 2003887 francs",new BigDecimal("366801.85"),bareme.calcul(new BigDecimal("2003887")));
	}
	

}
