/*
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
import java.math.RoundingMode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.impotch.util.TypeArrondi.CENT_MILLIEME_LE_PLUS_PROCHE;
import static org.impotch.util.BigDecimalUtil.parse;

import static org.impotch.calcul.assurancessociales.ContexteTestAssurancesSociales.CTX_TST_AS;
public class CalculCotisationAvsAiApgIndependant2025Test {

	
//	private CalculCotisationAvsAiApgIndependant calculateur2009;
//    private CalculCotisationAvsAiApgIndependant calculateur2011;
//    private CalculCotisationAvsAiApgIndependant calculateur2013;
//    private CalculCotisationAvsAiApgIndependant calculateur2016;
    private CalculCotisationAvsAiApgIndependant calculateur2025;


    @BeforeEach
	public void initialise() throws Exception {
//		calculateur2008 = (CalculCotisationAvsAiApgIndependant)CTX_TST_AS.getFournisseurRegleCalculAssuranceSociale().getCalculateurCotisationAvsAiApgIndependant(2008);
//		calculateur2009 = (CalculCotisationAvsAiApgIndependant)CTX_TST_AS.getFournisseurRegleCalculAssuranceSociale().getCalculateurCotisationAvsAiApgIndependant(2009);
//        calculateur2011 = (CalculCotisationAvsAiApgIndependant)CTX_TST_AS.getFournisseurRegleCalculAssuranceSociale().getCalculateurCotisationAvsAiApgIndependant(2011);
//        calculateur2013 = (CalculCotisationAvsAiApgIndependant)CTX_TST_AS.getFournisseurRegleCalculAssuranceSociale().getCalculateurCotisationAvsAiApgIndependant(2013);
//        calculateur2016 = (CalculCotisationAvsAiApgIndependant)CTX_TST_AS.getFournisseurRegleCalculAssuranceSociale().getCalculateurCotisationAvsAiApgIndependant(2016);
        calculateur2025 = (CalculCotisationAvsAiApgIndependant)CTX_TST_AS.getFournisseurRegleCalculAssuranceSociale().getCalculateurCotisationAvsAiApgIndependant(2025);
    }

    @Test
    public void avs2025_cotisationMinimale() {
        assertThat(calculateur2025.calculCotisationAvs(BigDecimal.ZERO)).isEqualTo("435.00");
        assertThat(calculateur2025.calculCotisationAvs(BigDecimal.valueOf(5000))).isEqualTo("435.00");
        assertThat(calculateur2025.calculCotisationAvs(BigDecimal.valueOf(10099))).isEqualTo("435.00");

        BigDecimal cotisationPremiereTranche = calculateur2025.calculCotisationAvs(BigDecimal.valueOf(10100));
        assertThat(cotisationPremiereTranche).isNotEqualTo(new BigDecimal("435.00"));
    }

    @Test
    public void ai2025_cotisationMinimale() {
        assertThat(calculateur2025.calculCotisationAi(BigDecimal.ZERO)).isEqualTo("70.00");
        assertThat(calculateur2025.calculCotisationAi(BigDecimal.valueOf(5000))).isEqualTo("70.00");
        assertThat(calculateur2025.calculCotisationAi(BigDecimal.valueOf(10099))).isEqualTo("70.00");

        BigDecimal cotisationPremiereTranche = calculateur2025.calculCotisationAi(BigDecimal.valueOf(10100));
        assertThat(cotisationPremiereTranche).isNotEqualTo(new BigDecimal("70.00"));
    }

    @Test
    public void apg2025_cotisationMinimale() {
        assertThat(calculateur2025.calculCotisationApg(BigDecimal.ZERO)).isEqualTo("25.00");
        assertThat(calculateur2025.calculCotisationApg(BigDecimal.valueOf(5000))).isEqualTo("25.00");
        assertThat(calculateur2025.calculCotisationApg(BigDecimal.valueOf(10099))).isEqualTo("25.00");

        BigDecimal cotisationPremiereTranche = calculateur2025.calculCotisationApg(BigDecimal.valueOf(10100));
        assertThat(cotisationPremiereTranche).isNotEqualTo(new BigDecimal("25.00"));
    }

    @Test
    public void tauxTotalAvsAiApg_2025() {
        assertThat(tauxTotalAvsAiApg(10_100)).isEqualTo(parse( "5.371 %"));
        assertThat(tauxTotalAvsAiApg(17_600)).isEqualTo(parse( "5.494 %"));
        assertThat(tauxTotalAvsAiApg(23_000)).isEqualTo(parse( "5.617 %"));
        assertThat(tauxTotalAvsAiApg(25_500)).isEqualTo(parse( "5.741 %"));
        assertThat(tauxTotalAvsAiApg(28_000)).isEqualTo(parse( "5.864 %"));
        assertThat(tauxTotalAvsAiApg(30_500)).isEqualTo(parse( "5.987 %"));
        assertThat(tauxTotalAvsAiApg(33_000)).isEqualTo(parse( "6.235 %"));
        assertThat(tauxTotalAvsAiApg(58_000)).isEqualTo(parse( "9.321 %"));
        assertThat(tauxTotalAvsAiApg(60_500)).isEqualTo(parse("10.000 %"));
    }

    private BigDecimal tauxTotalAvsAiApg(int revenu) {
        BigDecimal rev = BigDecimal.valueOf(revenu);
        BigDecimal cotisation = calculateur2025.calculCotisationAvsAiApg(rev);
        return CENT_MILLIEME_LE_PLUS_PROCHE.arrondir(cotisation.divide(rev,15, RoundingMode.HALF_UP));
    }

//	private boolean compare(String montantAttendu, BigDecimal montantCalcule) {
//		return 0 == new BigDecimal(montantAttendu).compareTo(montantCalcule);
//	}
//

//
//	@Test
//	public void calculCotisationAvsAiApg() {
//        assertThat(calculateur2008.calculCotisationAvsAiApg(BigDecimal.valueOf(1000))).isEqualByComparingTo("445.00");
//        assertThat(calculateur2008.calculCotisationAvsAiApg(BigDecimal.valueOf(100000))).isEqualTo("9500.00");
//	}
//
//	@Test
//	public void calculCotisationAvsAiApg2009() {
//        assertThat(calculateur2009.calculCotisationAvsAiApg(BigDecimal.valueOf(1000))).isEqualByComparingTo("460.00");
//        assertThat(calculateur2009.calculCotisationAvsAiApg(BigDecimal.valueOf(100000))).isEqualTo("9500.00");
//	}

//
//	@Test
//	public void getTauxTotal2009(){
//		// 2009
//        assertThat(getTxTotal(calculateur2009,  1000)).isNull();
//        assertThat(getTxTotal(calculateur2009,  9199)).isNull();
//        TauxAssert.assertThat(getTxTotal(calculateur2009,  9200)).isEqualTo("5.116 %");
//        TauxAssert.assertThat(getTxTotal(calculateur2009, 10000)).isEqualTo("5.116 %");
//        TauxAssert.assertThat(getTxTotal(calculateur2009, 54800)).isEqualTo("9.5   %");
//	}
//
//    @Test
//    public void calculCotisationAvsAiApg2011() {
//        assertThat(calculateur2011.calculCotisationAvsAiApg(BigDecimal.valueOf(1000))).isEqualByComparingTo("475");
//        assertThat(calculateur2011.calculCotisationAvsAiApg(BigDecimal.valueOf(100000))).isEqualByComparingTo("9700");
//    }
//
//    @Test
//    public void getTauxTotal2011(){
//        assertThat(getTxTotal(calculateur2011,  1000)).isNull();
//        assertThat(getTxTotal(calculateur2011,  9299)).isNull();
//        TauxAssert.assertThat(getTxTotal(calculateur2011, 9300)).isEqualTo("5.223 %");
//        TauxAssert.assertThat(getTxTotal(calculateur2011,10000)).isEqualTo("5.223 %");
//        TauxAssert.assertThat(getTxTotal(calculateur2011,55700)).isEqualTo("9.7   %");
//    }
//
//    @Test
//    public void getTauxTotal2013() {
//        TauxAssert.assertThat(getTxTotal(calculateur2013, 21700)).isEqualTo("5.472 %");
//        TauxAssert.assertThat(getTxTotal(calculateur2013,100000)).isEqualTo("9.7   %");
//    }
//
//    @Test
//    public void tauxTotal2016() {
//        assertThat(getTxTotal(calculateur2016,  1000)).isNull();
//        assertThat(getTxTotal(calculateur2016,  9399)).isNull();
//        TauxAssert.assertThat(getTxTotal(calculateur2016, 9400)).isEqualTo("5.196 %");
//        TauxAssert.assertThat(getTxTotal(calculateur2016, 21900)).isEqualTo("5.444 %");
//    }

//    @Test
//    public void tauxTotal2025() {
//        assertThat(getTxTotal(calculateur2025,  1000)).isNull();
//        assertThat(getTxTotal(calculateur2025,  10099)).isNull();
//        TauxAssert.assertThat(getTxTotal(calculateur2025, 10100)).isEqualTo("5.371 %");
//        TauxAssert.assertThat(getTxTotal(calculateur2025, 17600)).isEqualTo("5.494 %");
//        TauxAssert.assertThat(getTxTotal(calculateur2025, 23000)).isEqualTo("5.617 %");
//        TauxAssert.assertThat(getTxTotal(calculateur2025, 25500)).isEqualTo("5.741 %");
//        TauxAssert.assertThat(getTxTotal(calculateur2025, 28000)).isEqualTo("5.864 %");
//        TauxAssert.assertThat(getTxTotal(calculateur2025, 30500)).isEqualTo("5.987 %");
//        TauxAssert.assertThat(getTxTotal(calculateur2025, 33000)).isEqualTo("6.235 %");
//        TauxAssert.assertThat(getTxTotal(calculateur2025, 58000)).isEqualTo("9.321 %");
//        TauxAssert.assertThat(getTxTotal(calculateur2025, 60500)).isEqualTo("10.0 %");
//
//    }
//
//    private static BigDecimal getTxTotal(CalculCotisationAvsAiApgIndependant calculateur, int revenuDeterminant) {
//        return calculateur.getTauxTotal(BigDecimal.valueOf(revenuDeterminant));
//    }
//

}
