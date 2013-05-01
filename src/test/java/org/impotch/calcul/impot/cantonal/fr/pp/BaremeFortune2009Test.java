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


import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.impotch.calcul.impot.cantonal.fr.pp.FournisseurRegleImpotCantonalFR;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.impotch.bareme.Bareme;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/beans.xml")
public class BaremeFortune2009Test {

	@Resource(name = "fournisseurRegleImpotCantonalFR")
	private FournisseurRegleImpotCantonalFR fournisseur;
	
	private Bareme bareme;

	
	@Before
	public void setUp() throws Exception {
		bareme = fournisseur.getBaremeFortune(2009);
	}

	private void calcul(int montantImposable, String attendu) {
		String retour = bareme.calcul(new BigDecimal(montantImposable)).toPlainString();
	    assertEquals("Barème pour " + montantImposable,attendu,retour);
	}

	@Test
	public void calcul() {
		calcul(85000,"148.75");
	}	
}
