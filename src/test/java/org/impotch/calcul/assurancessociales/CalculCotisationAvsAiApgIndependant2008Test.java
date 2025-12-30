package org.impotch.calcul.assurancessociales;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.impotch.calcul.assurancessociales.ContexteTestAssurancesSociales.CTX_TST_AS;
import static org.impotch.util.BigDecimalUtil.parse;
import static org.impotch.util.TypeArrondi.CENT_MILLIEME_LE_PLUS_PROCHE;

public class CalculCotisationAvsAiApgIndependant2008Test {

	private CalculCotisationAvsAiApgIndependant calculateur2008;

    @BeforeEach
    public void initialise() {
		calculateur2008 = (CalculCotisationAvsAiApgIndependant)CTX_TST_AS.getFournisseurRegleCalculAssuranceSociale().getCalculateurCotisationAvsAiApgIndependant(2008);
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
	public void tauxTotalAvsAiApg_2008() {
		// Montant figurant dans la brochure publiée par l’AVS :
		// 	Modifications au 1er janvier 2007 dans le domaine des cotisations et des prestations
		assertThat(tauxTotalAvsAiApg( 8_900)).isEqualTo(parse("5.116 %"));
		assertThat(tauxTotalAvsAiApg(15_900)).isEqualTo(parse("5.237 %"));
		assertThat(tauxTotalAvsAiApg(20_100)).isEqualTo(parse("5.359 %"));
		assertThat(tauxTotalAvsAiApg(22_300)).isEqualTo(parse("5.481 %"));
		assertThat(tauxTotalAvsAiApg(24_500)).isEqualTo(parse("5.603 %"));
		assertThat(tauxTotalAvsAiApg(26_700)).isEqualTo(parse("5.725 %"));
		// assertThat(tauxTotalAvsAiApg(28_900)).isEqualTo(parse("5.967 %")); // actuellement 5.968
		assertThat(tauxTotalAvsAiApg(31_100)).isEqualTo(parse("6.211 %"));
		assertThat(tauxTotalAvsAiApg(33_300)).isEqualTo(parse("6.455 %"));
		assertThat(tauxTotalAvsAiApg(35_500)).isEqualTo(parse("6.699 %"));
		assertThat(tauxTotalAvsAiApg(37_700)).isEqualTo(parse("6.942 %"));
		assertThat(tauxTotalAvsAiApg(39_900)).isEqualTo(parse("7.186 %"));
		assertThat(tauxTotalAvsAiApg(42_100)).isEqualTo(parse("7.551 %"));
		assertThat(tauxTotalAvsAiApg(44_300)).isEqualTo(parse("7.917 %"));
		assertThat(tauxTotalAvsAiApg(46_500)).isEqualTo(parse("8.283 %"));
		assertThat(tauxTotalAvsAiApg(48_700)).isEqualTo(parse("8.647 %"));
		assertThat(tauxTotalAvsAiApg(50_900)).isEqualTo(parse("9.013 %"));
		assertThat(tauxTotalAvsAiApg(53_100)).isEqualTo(parse("9.500 %"));
	}

	private BigDecimal tauxTotalAvsAiApg(int revenu) {
		BigDecimal rev = BigDecimal.valueOf(revenu);
		BigDecimal cotisation = calculateur2008.calculCotisationAvsAiApg(rev);
		return CENT_MILLIEME_LE_PLUS_PROCHE.arrondir(cotisation.divide(rev,15, RoundingMode.HALF_UP));
	}

}
