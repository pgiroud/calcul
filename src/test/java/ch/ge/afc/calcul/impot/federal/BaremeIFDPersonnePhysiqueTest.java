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
package ch.ge.afc.calcul.impot.federal;

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
public class BaremeIFDPersonnePhysiqueTest {

	@Resource(name = "fournisseurRegleImpotFederal")
	private FournisseurRegleImpotFederal constructeur;
	
	@Test
	public void postNumerandoCelibataire2006() {
		Bareme bareme = constructeur.getBaremeIFDPersonnePhysique(TypeBaremeIFDPersonnePhysique.SEUL_2006_POST);
		assertEquals("Revenu de 10000",new BigDecimal("0.00"), bareme.calcul(new BigDecimal("10000")));
		assertEquals("Revenu de 16800",new BigDecimal("0.00"), bareme.calcul(new BigDecimal("16800")));
		assertEquals("Revenu de 16900",new BigDecimal("25.40"), bareme.calcul(new BigDecimal("16900")));
		assertEquals("Revenu de 23000",new BigDecimal("72.35"), bareme.calcul(new BigDecimal("23000")));
		assertEquals("Revenu de 29800",new BigDecimal("124.70"), bareme.calcul(new BigDecimal("29800")));
		assertEquals("Revenu de 35000",new BigDecimal("170.45"), bareme.calcul(new BigDecimal("35000")));
		assertEquals("Revenu de 117000",new BigDecimal("4636.75"), bareme.calcul(new BigDecimal("117000")));
		assertEquals("Revenu de 127100",new BigDecimal("5525.55"), bareme.calcul(new BigDecimal("127100")));
		assertEquals("Revenu de 200000",new BigDecimal("14288.15"), bareme.calcul(new BigDecimal("200000")));
		assertEquals("Revenu de 750000",new BigDecimal("86250.00"), bareme.calcul(new BigDecimal("750000")));
		assertEquals("Revenu de 843600",new BigDecimal("97014.00"), bareme.calcul(new BigDecimal("843600")));
	}

	@Test
	public void postNumerandoMarie2006() {
		Bareme bareme = constructeur.getBaremeIFDPersonnePhysique(TypeBaremeIFDPersonnePhysique.FAMILLE_2006_POST);
		assertEquals("Revenu de 10000",new BigDecimal("0.00"), bareme.calcul(new BigDecimal("10000")));
		assertEquals("Revenu de 23000",new BigDecimal("0.00"), bareme.calcul(new BigDecimal("23000")));
		assertEquals("Revenu de 35000",new BigDecimal("83.00"), bareme.calcul(new BigDecimal("35000")));
		assertEquals("Revenu de 117000",new BigDecimal("3280.00"), bareme.calcul(new BigDecimal("117000")));
		assertEquals("Revenu de 129300",new BigDecimal("4317.00"), bareme.calcul(new BigDecimal("129300")));
		assertEquals("Revenu de 843600",new BigDecimal("97014.00"), bareme.calcul(new BigDecimal("843600")));
	}

	@Test
	public void postNumerandoCelibataire1996() {
		Bareme bareme = constructeur.getBaremeIFDPersonnePhysique(TypeBaremeIFDPersonnePhysique.SEUL_1996_POST);
		assertEquals("Revenu de 10000",new BigDecimal("0.00"), bareme.calcul(new BigDecimal("10000")));
		assertEquals("Revenu de 16000",new BigDecimal("0.00"), bareme.calcul(new BigDecimal("16000")));
		assertEquals("Revenu de 16100",new BigDecimal("25.40"), bareme.calcul(new BigDecimal("16100")));
		assertEquals("Revenu de 27900",new BigDecimal("116.25"), bareme.calcul(new BigDecimal("27900")));
		assertEquals("Revenu de 37000",new BigDecimal("205.10"), bareme.calcul(new BigDecimal("37000")));
		assertEquals("Revenu de 115900",new BigDecimal("4913.90"), bareme.calcul(new BigDecimal("115900")));
		assertEquals("Revenu de 124300",new BigDecimal("5782.90"), bareme.calcul(new BigDecimal("124300")));
		assertEquals("Revenu de 200000",new BigDecimal("15106.50"), bareme.calcul(new BigDecimal("200000")));
		assertEquals("Revenu de 750000",new BigDecimal("86250.00"), bareme.calcul(new BigDecimal("750000")));
		assertEquals("Revenu de 843600",new BigDecimal("97014.00"), bareme.calcul(new BigDecimal("843600")));
	}

	@Test
	public void praeNumerandoFamille2007() {
		Bareme bareme = constructeur.getBaremeIFDPersonnePhysique(TypeBaremeIFDPersonnePhysique.FAMILLE_2007_PRAE);
		assertEquals("Revenu de 10000",new BigDecimal("0.00"),bareme.calcul(new BigDecimal("10000")));
		assertEquals("Revenu de 89600",new BigDecimal("1856.00"),bareme.calcul(new BigDecimal("89600")));
		assertEquals("Revenu de 775800",new BigDecimal("89217.00"),bareme.calcul(new BigDecimal("775800")));
	}
}
