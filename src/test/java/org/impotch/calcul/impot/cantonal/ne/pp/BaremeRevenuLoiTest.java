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
package org.impotch.calcul.impot.cantonal.ne.pp;

import java.math.BigDecimal;
import javax.annotation.Resource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.impotch.bareme.Bareme;

import static org.assertj.core.api.Assertions.assertThat;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "/beansCH_NE.xml")
//@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
@SpringJUnitConfig(locations = "/beansCH_NE.xml")
public class BaremeRevenuLoiTest {

	@Resource(name = "fournisseurRegleImpotCantonalNE")
	private FournisseurRegleImpotCantonalNE fournisseur;
	
	private Bareme bareme;
	
	@BeforeEach
	public void setUp() throws Exception {
		bareme = fournisseur.getBaremeRevenu(2008);
	}

	private void calcul(int montantImposable, String attendu) {
		assertThat(bareme.calcul(BigDecimal.valueOf(montantImposable))).isEqualTo(new BigDecimal(attendu));
//		String retour = bareme.calcul(new BigDecimal(montantImposable)).toPlainString();
//	    assertEquals("Barème pour " + montantImposable,attendu,retour);
	}
	
	@Test
	public void borneBareme() {
		calcul(5000,"0.00");
		calcul(10000,"100.00");
		calcul(15000,"300.00");
		calcul(20000,"700.00");
		calcul(30000,"1900.00");
		calcul(40000,"3200.00");
		calcul(55000,"5300.00");
		calcul(75000,"8300.00");
		calcul(110000,"13900.00");
		calcul(150000,"20700.00");
		calcul(180000,"26100.00");
	}

}
