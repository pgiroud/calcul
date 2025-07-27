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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.impotch.calcul.assurancessociales.CalculateurCotisationAC.unCalculateur;


public class CalculateurCotisationACTest {

    private FournisseurRegleCalculCotisationsAssuranceSociale fournisseurRegle;

    @BeforeEach
    public void initialise() throws Exception {
        fournisseurRegle = ContexteTestAssurancesSociales.CTX_TST_AS.fournisseurReglesSuisses();
    }


    private Function<BigDecimal,BigDecimal> regle(int annee) {
        return fournisseurRegle.reglePartSalarie(annee,TypeAssuranceSociale.AC).orElseThrow();
    }

	@Test
	public void calculCotisationAC2010() {
        Function<BigDecimal,BigDecimal> regleCalcul = regle(2010);
        assertThat(regleCalcul.apply(BigDecimal.valueOf(10))).isEqualByComparingTo("0.1");
        assertThat(regleCalcul.apply(BigDecimal.valueOf(120000))).isEqualByComparingTo("1200");
        assertThat(regleCalcul.apply(BigDecimal.valueOf(126000))).isEqualByComparingTo("1260");
        assertThat(regleCalcul.apply(BigDecimal.valueOf(130000))).isEqualByComparingTo("1260");
        assertThat(regleCalcul.apply(BigDecimal.valueOf(500000))).isEqualByComparingTo("1260");
	}
	
	@Test
	public void calculCotisationAC2011() {
        Function<BigDecimal,BigDecimal> regleCalcul = regle(2011);
        assertThat(regleCalcul.apply(BigDecimal.valueOf(10))).isEqualByComparingTo("0.1");
        assertThat(regleCalcul.apply(BigDecimal.valueOf(120000))).isEqualByComparingTo("1320");
        assertThat(regleCalcul.apply(BigDecimal.valueOf(126000))).isEqualByComparingTo("1386");
        assertThat(regleCalcul.apply(BigDecimal.valueOf(130000))).isEqualByComparingTo("1406");
        assertThat(regleCalcul.apply(BigDecimal.valueOf(315000))).isEqualByComparingTo("2331");
        assertThat(regleCalcul.apply(BigDecimal.valueOf(500000))).isEqualByComparingTo("2331");
	}

    @Test
    public void calculAC2014() {
        Function<BigDecimal,BigDecimal> regleCalcul = regle(2014);
        assertThat(regleCalcul.apply(BigDecimal.valueOf(10))).isEqualByComparingTo("0.1");
        assertThat(regleCalcul.apply(BigDecimal.valueOf(120000))).isEqualByComparingTo("1320");
        assertThat(regleCalcul.apply(BigDecimal.valueOf(126000))).isEqualByComparingTo("1386");
        assertThat(regleCalcul.apply(BigDecimal.valueOf(500000))).isEqualByComparingTo("3256");
    }

    @Test
    public void calculAC2023() {
        Function<BigDecimal,BigDecimal> regleCalcul = regle(2023);
        assertThat(regleCalcul.apply(BigDecimal.valueOf(160000))).isEqualByComparingTo("1630.20");

    }
}
