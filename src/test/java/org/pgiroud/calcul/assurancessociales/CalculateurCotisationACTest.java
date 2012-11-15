package org.pgiroud.calcul.assurancessociales;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;
import static org.fest.assertions.api.Assertions.*;



public class CalculateurCotisationACTest {
	
	private BigDecimal calculPartSalariee(CalculateurCotisationAC calculateur, int montantDeterminant) {
		return calculateur.calculPartSalarieeCotisationAssuranceChomage(BigDecimal.valueOf(montantDeterminant));
	}
	
	@Test
	public void calculCotisationAC2010() {
		CalculateurCotisationAC calculateur = new CalculateurCotisationAC(2010,126000,"2 %");
        assertThat(calculPartSalariee(calculateur,10)).isEqualByComparingTo("0.1");
        assertThat(calculPartSalariee(calculateur,120000)).isEqualByComparingTo("1200");
        assertThat(calculPartSalariee(calculateur,126000)).isEqualByComparingTo("1260");
        assertThat(calculPartSalariee(calculateur,130000)).isEqualByComparingTo("1260");
        assertThat(calculPartSalariee(calculateur,500000)).isEqualByComparingTo("1260");
	}
	
	@Test
	public void calculCotisationAC2011() {
		CalculateurCotisationAC calculateur = new CalculateurCotisationAC(2011,126000,"2.5","2.2 %","1 %");
        assertThat(calculPartSalariee(calculateur,10)).isEqualByComparingTo("0.1");
        assertThat(calculPartSalariee(calculateur,120000)).isEqualByComparingTo("1320");
        assertThat(calculPartSalariee(calculateur,126000)).isEqualByComparingTo("1386");
        assertThat(calculPartSalariee(calculateur,130000)).isEqualByComparingTo("1406");
        assertThat(calculPartSalariee(calculateur,315000)).isEqualByComparingTo("2331");
        assertThat(calculPartSalariee(calculateur,500000)).isEqualByComparingTo("2331");
	}
}
