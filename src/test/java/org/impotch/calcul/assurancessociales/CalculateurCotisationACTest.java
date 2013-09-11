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

import org.junit.Test;
import static org.fest.assertions.api.Assertions.assertThat;



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

    @Test
    public void calculAC2014() {
        CalculateurCotisationAC calculateur = new CalculateurCotisationAC(2014,126000,"2.2 %","1 %");
        assertThat(calculPartSalariee(calculateur,10)).isEqualByComparingTo("0.1");
        assertThat(calculPartSalariee(calculateur,120000)).isEqualByComparingTo("1320");
        assertThat(calculPartSalariee(calculateur,126000)).isEqualByComparingTo("1386");
        assertThat(calculPartSalariee(calculateur,500000)).isEqualByComparingTo("3256");
    }
}
