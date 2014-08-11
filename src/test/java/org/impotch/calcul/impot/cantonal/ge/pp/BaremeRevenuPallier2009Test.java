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
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.impotch.bareme.BaremeTauxMarginalConstantParTranche;
import org.impotch.calcul.impot.cantonal.ge.pp.avant2010.ConstructeurBaremeIndexeTxMarginalConstantParTranche;
import org.impotch.calcul.impot.indexation.SimpleFournisseurIndicePeriodique;
import org.impotch.util.TypeArrondi;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/beans.xml")
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
public class BaremeRevenuPallier2009Test {

	private BaremeTauxMarginalConstantParTranche bareme;
	
	@Before
	public void setUp() throws Exception {
		SimpleFournisseurIndicePeriodique fournisseur = new SimpleFournisseurIndicePeriodique();
		Map<Integer,BigDecimal> indices = new HashMap<Integer,BigDecimal>();
		indices.put(2009,BigDecimal.ONE);
		fournisseur.setIndices(indices);
		ConstructeurBaremeIndexeTxMarginalConstantParTranche constructeur = new ConstructeurBaremeIndexeTxMarginalConstantParTranche();
		constructeur.validite(2009);
		constructeur.anneeReferenceRencherissement(2009);
		constructeur.indexateur(fournisseur);
		constructeur.tranche(17493, "0 %");
		constructeur.tranche(21076, "8 %");
		constructeur.tranche(23184, "9 %");
		constructeur.tranche(25291, "10 %");
		constructeur.tranche(27399, "11 %");
		constructeur.tranche(32668, "12 %");
		constructeur.tranche(36883, "13 %");
		constructeur.tranche(41099, "14 %");
		constructeur.tranche(45314, "14.5 %");
		constructeur.tranche(72713, "15 %");
		constructeur.tranche(119081, "15.5 %");
		constructeur.tranche(160179, "16 %");
		constructeur.tranche(181256, "16.5 %");
		constructeur.tranche(259238, "17 %");
		constructeur.tranche(276099, "17.5 %");
		constructeur.tranche(388857, "18 %");
		constructeur.tranche(609103, "18.5 %");
		constructeur.derniereTranche("19 %");
		bareme = constructeur.typeArrondiTranche(TypeArrondi.CINQ_CTS).construire(2009);
	}

	private void rev(int revenu, String impot) {
		assertEquals("Revenu de " + revenu, new BigDecimal(impot),bareme.calcul(new BigDecimal(revenu)));
	}

	@Test
	public void revenu2009() {
		rev(17493,"0.00");
		rev(17494,"0.10");
		rev(21076,"286.65");
		rev(23184,"476.35");
		rev(25291,"687.05");
		rev(26734,"845.80");
		rev(27399,"918.95");
		rev(32668,"1551.25");
		rev(36883,"2099.20");
		rev(41099,"2689.45");
		rev(45314,"3300.65");
		rev(72713,"7410.50");
		rev(119081,"14597.55");
		rev(160179,"21173.25");
		rev(181256,"24650.95");
		rev(243374,"35211.00");
		rev(259238,"37907.90");
		rev(276099,"40858.60");
		rev(388857,"61155.05");
		rev(609103,"101900.55");
		rev(743374,"127412.05");
		rev(1000000,"176171.00");
		rev(2000000,"366171.00");
	}
	
}
