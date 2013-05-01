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
package org.impotch.calcul.impot.cantonal.ge.pp.avant2010;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
import org.impotch.bareme.Bareme;
import org.impotch.calcul.impot.cantonal.ge.pp.FournisseurRegleImpotCantonalGE;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/beans.xml")
public class BaremeRabaisImpotSource2009Test {
	
	@Resource(name = "fournisseurRegleImpotCantonalGE")
	private FournisseurRegleImpotCantonalGE fournisseur;

	private Bareme baremeSeul2009;
	private Bareme baremeFamille2009;
	
	@Before
	public void initialise() throws Exception {
		baremeSeul2009 = fournisseur.getBaremeRevenu(2009);
		baremeFamille2009 = fournisseur.getBaremeRevenuFamille(2009);
	}

	@Test 
	public void rabaisImpotImpotSource() {
	    assertEquals("BarèmeA0	",new BigDecimal("1140.75"),baremeSeul2009.calcul(new BigDecimal("16426")));
	    assertEquals("BarèmeB0	",new BigDecimal("1989.15"),baremeFamille2009.calcul(new BigDecimal("30114")));
	    assertEquals("BarèmeB1	",new BigDecimal("2769.05"),baremeFamille2009.calcul(new BigDecimal("37232")));
	    assertEquals("BarèmeB2	",new BigDecimal("2925.80"),baremeFamille2009.calcul(new BigDecimal("38601")));
	    assertEquals("BarèmeB3	",new BigDecimal("3084.40"),baremeFamille2009.calcul(new BigDecimal("39970")));
	    assertEquals("BarèmeB4	",new BigDecimal("3603.25"),baremeFamille2009.calcul(new BigDecimal("44350")));
	    assertEquals("BarèmeB5	",new BigDecimal("3768.55"),baremeFamille2009.calcul(new BigDecimal("45719")));
	    assertEquals("BarèmeB6	",new BigDecimal("3935.30"),baremeFamille2009.calcul(new BigDecimal("47088")));
	    assertEquals("BarèmeB7	",new BigDecimal("4477.20"),baremeFamille2009.calcul(new BigDecimal("51468")));
	    assertEquals("BarèmeB8	",new BigDecimal("4649.00"),baremeFamille2009.calcul(new BigDecimal("52837")));
	    assertEquals("BarèmeB9	",new BigDecimal("4821.85"),baremeFamille2009.calcul(new BigDecimal("54206")));
	    assertEquals("BarèmeB10	",new BigDecimal("5381.60"),baremeFamille2009.calcul(new BigDecimal("58586")));
	    assertEquals("BarèmeB11	",new BigDecimal("5558.50"),baremeFamille2009.calcul(new BigDecimal("59955")));
	    assertEquals("BarèmeB12	",new BigDecimal("5736.40"),baremeFamille2009.calcul(new BigDecimal("61324")));
	    assertEquals("BarèmeB13	",new BigDecimal("6311.30"),baremeFamille2009.calcul(new BigDecimal("65704")));
	    assertEquals("BarèmeB14	",new BigDecimal("6492.80"),baremeFamille2009.calcul(new BigDecimal("67073")));
	    assertEquals("BarèmeB15	",new BigDecimal("6675.15"),baremeFamille2009.calcul(new BigDecimal("68442")));
	    assertEquals("BarèmeI0	",new BigDecimal("994.60"),baremeSeul2009.calcul(new BigDecimal("15057")));
	    assertEquals("BarèmeI1	",new BigDecimal("1384.50"),baremeSeul2009.calcul(new BigDecimal("18616")));
	    assertEquals("BarèmeI2	",new BigDecimal("1542.20"),baremeSeul2009.calcul(new BigDecimal("19985")));
	    assertEquals("BarèmeI3	",new BigDecimal("1801.60"),baremeSeul2009.calcul(new BigDecimal("22175")));
	    assertEquals("BarèmeI4	",new BigDecimal("1967.65"),baremeSeul2009.calcul(new BigDecimal("23544")));
	    assertEquals("BarèmeI5	",new BigDecimal("2238.55"),baremeSeul2009.calcul(new BigDecimal("25734")));
	    assertEquals("BarèmeI6	",new BigDecimal("2410.75"),baremeSeul2009.calcul(new BigDecimal("27103")));
	    assertEquals("BarèmeI7	",new BigDecimal("2690.30"),baremeSeul2009.calcul(new BigDecimal("29293")));
	    assertEquals("BarèmeI8	",new BigDecimal("2867.25"),baremeSeul2009.calcul(new BigDecimal("30662")));
	}

}
