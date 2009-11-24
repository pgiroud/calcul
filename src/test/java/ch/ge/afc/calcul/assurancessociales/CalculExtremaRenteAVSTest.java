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
package ch.ge.afc.calcul.assurancessociales;


import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/beans.xml")
public class CalculExtremaRenteAVSTest {

	@Resource(name = "fournisseurRegleCalculAssuranceSociale")
	private FournisseurRegleCalculAssuranceSociale fournisseurRegleCalculAssuranceSociale;
	
	private CalculExtremaRentesAVS calculateur;
	
	@Before
	public void initialise() throws Exception {
		calculateur = fournisseurRegleCalculAssuranceSociale.getCalculateurExtremaRenteAVS(2009);
	}

	@Test
	public void calculRenteSimpleMaximum() {
		assertEquals("Rente simple maximale annuelle", new BigDecimal("27360"),calculateur.getRenteSimple().getMontantAnnuelMaximum());
	}
}
