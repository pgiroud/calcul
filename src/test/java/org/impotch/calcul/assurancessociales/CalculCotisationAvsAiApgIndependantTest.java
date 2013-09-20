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
package org.impotch.calcul.assurancessociales;

import java.math.BigDecimal;
import javax.annotation.Resource;

import org.impotch.util.TauxAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/beans.xml")
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
public class CalculCotisationAvsAiApgIndependantTest {
	
	@Resource(name = "fournisseurRegleCalculAssuranceSociale")
	private FournisseurRegleCalculAssuranceSociale fournisseurRegleCalculCotisationAssuranceSociale;
	
	private CalculCotisationAvsAiApgIndependant calculateur2008;
	private CalculCotisationAvsAiApgIndependant calculateur2009;
    private CalculCotisationAvsAiApgIndependant calculateur2011;
    private CalculCotisationAvsAiApgIndependant calculateur2013;

    @Before
	public void initialise() throws Exception {
		calculateur2008 = (CalculCotisationAvsAiApgIndependant)fournisseurRegleCalculCotisationAssuranceSociale.getCalculateurCotisationAvsAiApgIndependant(2008);
		calculateur2009 = (CalculCotisationAvsAiApgIndependant)fournisseurRegleCalculCotisationAssuranceSociale.getCalculateurCotisationAvsAiApgIndependant(2009);
        calculateur2011 = (CalculCotisationAvsAiApgIndependant)fournisseurRegleCalculCotisationAssuranceSociale.getCalculateurCotisationAvsAiApgIndependant(2011);
        calculateur2013 = (CalculCotisationAvsAiApgIndependant)fournisseurRegleCalculCotisationAssuranceSociale.getCalculateurCotisationAvsAiApgIndependant(2013);
    }

	private boolean compare(String montantAttendu, BigDecimal montantCalcule) {
		return 0 == new BigDecimal(montantAttendu).compareTo(montantCalcule);
	}
	
	@Test
	public void calculCotisationAi() {
        assertThat(calculateur2008.calculCotisationAi(BigDecimal.valueOf(100000))).isEqualTo("1400.00");
	}

	@Test
	public void calculCotisationApg() {
        assertThat(calculateur2008.calculCotisationApg(BigDecimal.valueOf(100000))).isEqualTo("300.00");
	}

	@Test
	public void calculCotisationAvs() {
        assertThat(calculateur2008.calculCotisationAvs(BigDecimal.valueOf(100000))).isEqualTo("7800.00");
	}

	@Test
	public void calculCotisationAvsAiApg() {
        assertThat(calculateur2008.calculCotisationAvsAiApg(BigDecimal.valueOf(1000))).isEqualByComparingTo("445.00");
        assertThat(calculateur2008.calculCotisationAvsAiApg(BigDecimal.valueOf(100000))).isEqualTo("9500.00");
	}
	
	@Test
	public void calculCotisationAvsAiApg2009() {
        assertThat(calculateur2009.calculCotisationAvsAiApg(BigDecimal.valueOf(1000))).isEqualByComparingTo("460.00");
        assertThat(calculateur2009.calculCotisationAvsAiApg(BigDecimal.valueOf(100000))).isEqualTo("9500.00");
	}
	
	@Test
	public void getTauxTotal2008(){
		// 2008
        assertThat(getTxTotal(calculateur2008,  1000)).isNull();
        assertThat(getTxTotal(calculateur2008,  8899)).isNull();
        TauxAssert.assertThat(getTxTotal(calculateur2008, 8900)).isEqualTo("5.116 %");
        TauxAssert.assertThat(getTxTotal(calculateur2008, 10000)).isEqualTo("5.116 %");
        TauxAssert.assertThat(getTxTotal(calculateur2008, 15899)).isEqualTo("5.116 %");
        TauxAssert.assertThat(getTxTotal(calculateur2008, 15900)).isEqualTo("5.237 %");
        TauxAssert.assertThat(getTxTotal(calculateur2008, 16000)).isEqualTo("5.237 %");
        TauxAssert.assertThat(getTxTotal(calculateur2008, 21000)).isEqualTo("5.359 %");
        TauxAssert.assertThat(getTxTotal(calculateur2008, 23000)).isEqualTo("5.481 %");
        TauxAssert.assertThat(getTxTotal(calculateur2008, 25000)).isEqualTo("5.603 %");
        TauxAssert.assertThat(getTxTotal(calculateur2008, 27000)).isEqualTo("5.725 %");
        TauxAssert.assertThat(getTxTotal(calculateur2008, 30000)).isEqualTo("5.967 %");
        TauxAssert.assertThat(getTxTotal(calculateur2008, 32000)).isEqualTo("6.211 %");
        TauxAssert.assertThat(getTxTotal(calculateur2008, 34000)).isEqualTo("6.455 %");
        TauxAssert.assertThat(getTxTotal(calculateur2008, 36000)).isEqualTo("6.699 %");
        TauxAssert.assertThat(getTxTotal(calculateur2008, 38000)).isEqualTo("6.942 %");
        TauxAssert.assertThat(getTxTotal(calculateur2008, 40000)).isEqualTo("7.186 %");
        TauxAssert.assertThat(getTxTotal(calculateur2008, 43000)).isEqualTo("7.551 %");
        TauxAssert.assertThat(getTxTotal(calculateur2008, 45000)).isEqualTo("7.917 %");
        TauxAssert.assertThat(getTxTotal(calculateur2008, 48000)).isEqualTo("8.283 %");
        TauxAssert.assertThat(getTxTotal(calculateur2008, 50000)).isEqualTo("8.647 %");
        TauxAssert.assertThat(getTxTotal(calculateur2008, 51000)).isEqualTo("9.013 %");
        TauxAssert.assertThat(getTxTotal(calculateur2008, 53100)).isEqualTo("9.5   %");
	}
	
	@Test
	public void getTauxTotal2009(){
		// 2009
        assertThat(getTxTotal(calculateur2009,  1000)).isNull();
        assertThat(getTxTotal(calculateur2009,  9199)).isNull();
        TauxAssert.assertThat(getTxTotal(calculateur2009,  9200)).isEqualTo("5.116 %");
        TauxAssert.assertThat(getTxTotal(calculateur2009, 10000)).isEqualTo("5.116 %");
        TauxAssert.assertThat(getTxTotal(calculateur2009, 54800)).isEqualTo("9.5   %");
	}

    @Test
    public void calculCotisationAvsAiApg2011() {
        assertThat(calculateur2011.calculCotisationAvsAiApg(BigDecimal.valueOf(1000))).isEqualByComparingTo("475");
        assertThat(calculateur2011.calculCotisationAvsAiApg(BigDecimal.valueOf(100000))).isEqualByComparingTo("9700");
    }

    @Test
    public void getTauxTotal2011(){
        assertThat(getTxTotal(calculateur2011,  1000)).isNull();
        assertThat(getTxTotal(calculateur2011,  9299)).isNull();
        TauxAssert.assertThat(getTxTotal(calculateur2011, 9300)).isEqualTo("5.223 %");
        TauxAssert.assertThat(getTxTotal(calculateur2011,10000)).isEqualTo("5.223 %");
        TauxAssert.assertThat(getTxTotal(calculateur2011,55700)).isEqualTo("9.7   %");
    }

    @Test
    public void getTauxTotal2013() {
        TauxAssert.assertThat(getTxTotal(calculateur2013, 21700)).isEqualTo("5.472 %");
        TauxAssert.assertThat(getTxTotal(calculateur2013,100000)).isEqualTo("9.7   %");
    }

    private static BigDecimal getTxTotal(CalculCotisationAvsAiApgIndependant calculateur, int revenuDeterminant) {
        return calculateur.getTauxTotal(BigDecimal.valueOf(revenuDeterminant));
    }


}
