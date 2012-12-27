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
package org.impotch.calcul.assurancessociales;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/beans.xml")
public class CalculCotisationAvsAiApgSalarieTest {

	@Resource(name = "fournisseurRegleCalculAssuranceSociale")
	private FournisseurRegleCalculAssuranceSociale fournisseurRegleCalculCotisationAssuranceSociale;
	
	private CalculCotisationsSocialesSalarie calculateur;
	
	@Before
	public void initialise() throws Exception {
		calculateur = fournisseurRegleCalculCotisationAssuranceSociale.getCalculateurCotisationsSocialesSalarie(2009);
	}

	@Test
	public void calculCotisationAi() {
		assertEquals("Calcul cotisation Ai", new BigDecimal("700.00"), calculateur.calculPartSalarieeCotisationAi(new BigDecimal("100000")));
	}

	@Test
	public void calculCotisationApg() {
		assertEquals("Calcul cotisation Apg", new BigDecimal("150.00"), calculateur.calculPartSalarieeCotisationApg(new BigDecimal("100000")));
	}

	@Test
	public void calculCotisationAvs() {
		assertEquals("Calcul cotisation Avs", new BigDecimal("4200.00"), calculateur.calculPartSalarieeCotisationAvs(new BigDecimal("100000")));
	}

	@Test
	public void calculCotisationAvsAiApg() {
		assertEquals("Calcul cotisation Avs/Ai/Apg", new BigDecimal("5050.00"), calculateur.calculPartSalarieeCotisationAvsAiApg(new BigDecimal("100000")));
		assertEquals("Calcul cotisation Avs/Ai/Apg", new BigDecimal("228.50"), calculateur.calculPartSalarieeCotisationAvsAiApg(new BigDecimal("4525")));
	}

}
