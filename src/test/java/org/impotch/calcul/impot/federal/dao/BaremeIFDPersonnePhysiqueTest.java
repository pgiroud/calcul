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
package org.impotch.calcul.impot.federal.dao;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.impotch.calcul.impot.federal.dao.FournisseurBaremeIFD;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.impotch.bareme.Bareme;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/beansCH_IFD.xml")
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
public class BaremeIFDPersonnePhysiqueTest {

	@Resource
	private FournisseurBaremeIFD fournisseur;
	
	@Test
	public void postNumerandoCelibataire2006() {
		Bareme bareme = fournisseur.getBaremeImpotRevenuPersonnePhysiquePourPersonneSeule(2006);
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
		Bareme bareme = fournisseur.getBaremeImpotRevenuPersonnePhysiquePourFamille(2006);
		assertEquals("Revenu de 10000",new BigDecimal("0.00"), bareme.calcul(new BigDecimal("10000")));
		assertEquals("Revenu de 23000",new BigDecimal("0.00"), bareme.calcul(new BigDecimal("23000")));
		assertEquals("Revenu de 35000",new BigDecimal("83.00"), bareme.calcul(new BigDecimal("35000")));
		assertEquals("Revenu de 117000",new BigDecimal("3280.00"), bareme.calcul(new BigDecimal("117000")));
		assertEquals("Revenu de 129300",new BigDecimal("4317.00"), bareme.calcul(new BigDecimal("129300")));
		assertEquals("Revenu de 843600",new BigDecimal("97014.00"), bareme.calcul(new BigDecimal("843600")));
	}

	@Test
	public void postNumerandoCelibataire1996() {
		Bareme bareme = fournisseur.getBaremeImpotRevenuPersonnePhysiquePourPersonneSeule(1996);
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
		Bareme bareme = fournisseur.getBaremeImpotRevenuPraeNumerandoPersonnePhysiquePourFamille(2007);
		assertEquals("Revenu de 10000",new BigDecimal("0.00"),bareme.calcul(new BigDecimal("10000")));
		assertEquals("Revenu de 89600",new BigDecimal("1856.00"),bareme.calcul(new BigDecimal("89600")));
		assertEquals("Revenu de 775800",new BigDecimal("89217.00"),bareme.calcul(new BigDecimal("775800")));
	}
	
	@Test
	public void praeNumerandoCelibataire2007() {
		Bareme bareme = fournisseur.getBaremeImpotRevenuPraeNumerandoPersonnePhysiquePourPersonneSeule(2007);
		assertEquals("Revenu de 10000",new BigDecimal("0.00"), bareme.calcul(new BigDecimal("10000")));
		assertEquals("Revenu de 16800",new BigDecimal("32.30"), bareme.calcul(new BigDecimal("16800")));
		assertEquals("Revenu de 39000",new BigDecimal("270.55"), bareme.calcul(new BigDecimal("39000")));
		assertEquals("Revenu de 78100",new BigDecimal("1922.55"), bareme.calcul(new BigDecimal("78100")));
		assertEquals("Revenu de 775900",new BigDecimal("89228.50"), bareme.calcul(new BigDecimal("775900")));
	}	
	
	
	@Test
	public void postNumerandoCelibataire2011() {
		Bareme bareme = fournisseur.getBaremeImpotRevenuPersonnePhysiquePourPersonneSeule(2011);
		assertEquals("Revenu de 20000",new BigDecimal("43.10"), bareme.calcul(new BigDecimal("20000")));
		assertEquals("Revenu de 34000",new BigDecimal("153.65"), bareme.calcul(new BigDecimal("34000")));
        assertEquals("Revenu de 55000",new BigDecimal("581.30"), bareme.calcul(new BigDecimal("55000")));
		assertEquals("Revenu de 250300",new BigDecimal("20268.40"), bareme.calcul(new BigDecimal("250300")));
		assertEquals("Revenu de 889400",new BigDecimal("102281.00"), bareme.calcul(new BigDecimal("889400")));
	}	
	
    @Test
    public void praeNumerandoCelibataire2011() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPraeNumerandoPersonnePhysiquePourPersonneSeule(2011);
        assertEquals("Revenu de 682100",new BigDecimal("78441.05"), bareme.calcul(new BigDecimal("682100")));
        assertEquals("Revenu de 682200",new BigDecimal("78453.00"), bareme.calcul(new BigDecimal("682200")));
    }

	@Test
	public void postNumerandoMarie2011() {
		Bareme bareme = fournisseur.getBaremeImpotRevenuPersonnePhysiquePourFamille(2011);
		assertEquals("Revenu de 889400",new BigDecimal("102281.00"), bareme.calcul(new BigDecimal("889400")));
		assertEquals("Revenu de 889500",new BigDecimal("102292.50"), bareme.calcul(new BigDecimal("889500")));
	}	

    @Test
    public void postNumerandoCelibataire2012() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPersonnePhysiquePourPersonneSeule(2012);
        assertEquals("Revenu de 41600",new BigDecimal("223.15"), bareme.calcul(new BigDecimal("41600")));
    }

    @Test
    public void postNumerandoMarie2012() {
        Bareme bareme = fournisseur.getBaremeImpotRevenuPersonnePhysiquePourFamille(2012);
        assertEquals("Revenu de 896000",new BigDecimal("103040.00"), bareme.calcul(new BigDecimal("896000")));
    }


}
