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
public class CalculCotisationAvsAiApgIndependantTest {
	
	@Resource(name = "fournisseurRegleCalculAssuranceSociale")
	private FournisseurRegleCalculAssuranceSociale fournisseurRegleCalculCotisationAssuranceSociale;
	
	private CalculCotisationAvsAiApgIndependant calculateur2008;
	private CalculCotisationAvsAiApgIndependant calculateur2009;
    private CalculCotisationAvsAiApgIndependant calculateur2011;

	@Before
	public void initialise() throws Exception {
		calculateur2008 = (CalculCotisationAvsAiApgIndependant)fournisseurRegleCalculCotisationAssuranceSociale.getCalculateurCotisationAvsAiApgIndependant(2008);
		calculateur2009 = (CalculCotisationAvsAiApgIndependant)fournisseurRegleCalculCotisationAssuranceSociale.getCalculateurCotisationAvsAiApgIndependant(2009);
        calculateur2011 = (CalculCotisationAvsAiApgIndependant)fournisseurRegleCalculCotisationAssuranceSociale.getCalculateurCotisationAvsAiApgIndependant(2011);
	}

	private boolean compare(String montantAttendu, BigDecimal montantCalcule) {
		return 0 == new BigDecimal(montantAttendu).compareTo(montantCalcule);
	}
	
	@Test
	public void calculCotisationAi() {
		assertTrue("Comparaison pour 100000 francs de montant déterminant", compare("1400", calculateur2008.calculCotisationAi(new BigDecimal("100000"))));
	}

	@Test
	public void calculCotisationApg() {
		assertTrue("Comparaison pour 100000 francs de montant déterminant", compare("300", calculateur2008.calculCotisationApg(new BigDecimal("100000"))));
	}

	@Test
	public void calculCotisationAvs() {
		assertTrue("Comparaison pour 100000 francs de montant déterminant", compare("7800", calculateur2008.calculCotisationAvs(new BigDecimal("100000"))));
	}

	@Test
	public void calculCotisationAvsAiApg() {
		assertTrue("Comparaison pour 1000 francs de montant déterminant", compare("445", calculateur2008.calculCotisationAvsAiApg(new BigDecimal("1000"))));
		assertTrue("Comparaison pour 100000 francs de montant déterminant", compare("9500", calculateur2008.calculCotisationAvsAiApg(new BigDecimal("100000"))));

	}
	
	@Test
	public void calculCotisationAvsAiApgApres2009() {
		assertTrue("Comparaison pour 1000 francs de montant déterminant", compare("460", calculateur2009.calculCotisationAvsAiApg(new BigDecimal("1000"))));
		assertTrue("Comparaison pour 100000 francs de montant déterminant", compare("9500", calculateur2009.calculCotisationAvsAiApg(new BigDecimal("100000"))));
	}
	
	@Test
	public void getTauxTotal(){
		// 2008
		assertNull("Taux total pour 1000 francs de montant déterminant", calculateur2008.getTauxTotal(new BigDecimal("1000")));
		assertNull("Taux total pour 8899 francs de montant déterminant", calculateur2008.getTauxTotal(new BigDecimal("8899")));
		assertTrue("Taux total pour 8900 francs de montant déterminant", compare("0.05116", calculateur2008.getTauxTotal(new BigDecimal("8900"))));
		assertTrue("Taux total pour 10000 francs de montant déterminant", compare("0.05116", calculateur2008.getTauxTotal(new BigDecimal("10000"))));
		assertTrue("Taux total pour 15899 francs de montant déterminant", compare("0.05116", calculateur2008.getTauxTotal(new BigDecimal("15899"))));
		assertTrue("Taux total pour 15900 francs de montant déterminant", compare("0.05237", calculateur2008.getTauxTotal(new BigDecimal("15900"))));
		assertTrue("Taux total pour 16000 francs de montant déterminant", compare("0.05237", calculateur2008.getTauxTotal(new BigDecimal("16000"))));
		assertTrue("Taux total pour 21000 francs de montant déterminant", compare("0.05359", calculateur2008.getTauxTotal(new BigDecimal("21000"))));
		assertTrue("Taux total pour 23000 francs de montant déterminant", compare("0.05481", calculateur2008.getTauxTotal(new BigDecimal("23000"))));
		assertTrue("Taux total pour 25000 francs de montant déterminant", compare("0.05603", calculateur2008.getTauxTotal(new BigDecimal("25000"))));
		assertTrue("Taux total pour 27000 francs de montant déterminant", compare("0.05725", calculateur2008.getTauxTotal(new BigDecimal("27000"))));
		assertTrue("Taux total pour 30000 francs de montant déterminant", compare("0.05967", calculateur2008.getTauxTotal(new BigDecimal("30000"))));
		assertTrue("Taux total pour 32000 francs de montant déterminant", compare("0.06211", calculateur2008.getTauxTotal(new BigDecimal("32000"))));
		assertTrue("Taux total pour 34000 francs de montant déterminant", compare("0.06455", calculateur2008.getTauxTotal(new BigDecimal("34000"))));
		assertTrue("Taux total pour 36000 francs de montant déterminant", compare("0.06699", calculateur2008.getTauxTotal(new BigDecimal("36000"))));
		assertTrue("Taux total pour 38000 francs de montant déterminant", compare("0.06942", calculateur2008.getTauxTotal(new BigDecimal("38000"))));
		assertTrue("Taux total pour 40000 francs de montant déterminant", compare("0.07186", calculateur2008.getTauxTotal(new BigDecimal("40000"))));
		assertTrue("Taux total pour 43000 francs de montant déterminant", compare("0.07551", calculateur2008.getTauxTotal(new BigDecimal("43000"))));
		assertTrue("Taux total pour 45000 francs de montant déterminant", compare("0.07917", calculateur2008.getTauxTotal(new BigDecimal("45000"))));
		assertTrue("Taux total pour 48000 francs de montant déterminant", compare("0.08283", calculateur2008.getTauxTotal(new BigDecimal("48000"))));
		assertTrue("Taux total pour 50000 francs de montant déterminant", compare("0.08647", calculateur2008.getTauxTotal(new BigDecimal("50000"))));
		assertTrue("Taux total pour 51000 francs de montant déterminant", compare("0.09013", calculateur2008.getTauxTotal(new BigDecimal("51000"))));
		assertTrue("Taux total pour 53100 francs de montant déterminant", compare("0.09500", calculateur2008.getTauxTotal(new BigDecimal("53100"))));
	}
	
	@Test
	public void getTauxTotalApres2009(){
		// 2009
		assertNull("Taux total pour 1000 francs de montant déterminant", calculateur2009.getTauxTotal(new BigDecimal("1000")));
		assertNull("Taux total pour 9199 francs de montant déterminant", calculateur2009.getTauxTotal(new BigDecimal("9199")));
		assertTrue("Taux total pour 9200 francs de montant déterminant", compare("0.05116", calculateur2009.getTauxTotal(new BigDecimal("9200"))));
		assertTrue("Taux total pour 10000 francs de montant déterminant", compare("0.05116", calculateur2009.getTauxTotal(new BigDecimal("10000"))));
		assertTrue("Taux total pour 54800 francs de montant déterminant", compare("0.09500", calculateur2009.getTauxTotal(new BigDecimal("54800"))));
		
	}

    @Test
    public void calculCotisationAvsAiApgApres2011() {
        assertTrue("Comparaison pour 1000 francs de montant déterminant", compare("475", calculateur2011.calculCotisationAvsAiApg(new BigDecimal("1000"))));
        assertTrue("Comparaison pour 100000 francs de montant déterminant", compare("9700", calculateur2011.calculCotisationAvsAiApg(new BigDecimal("100000"))));
    }

    @Test
    public void getTauxTotalApres2011(){
        // 2011
        assertNull("Taux total pour 1000 francs de montant déterminant", calculateur2011.getTauxTotal(new BigDecimal("1000")));
        assertNull("Taux total pour 9299 francs de montant déterminant", calculateur2011.getTauxTotal(new BigDecimal("9299")));
        assertTrue("Taux total pour 9300 francs de montant déterminant", compare("0.05223", calculateur2011.getTauxTotal(new BigDecimal("9300"))));
        assertTrue("Taux total pour 10000 francs de montant déterminant", compare("0.05223", calculateur2011.getTauxTotal(new BigDecimal("10000"))));
        assertTrue("Taux total pour 55700 francs de montant déterminant", compare("0.09700", calculateur2011.getTauxTotal(new BigDecimal("55700"))));

    }


}
