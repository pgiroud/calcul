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
/**
 * This file is part of impotch/calcul.
 * <p>
 * impotch/calcul is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 * <p>
 * impotch/calcul is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with impotch/calcul.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.impotch.calcul.assurancessociales;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "/beansAssurancesSociales.xml")
//@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
@SpringJUnitConfig(locations = "/beansAssurancesSociales.xml")
public class CalculCotisationAvsAiApgSalarieTest {

    @Resource(name = "fournisseurRegleCalculAssuranceSociale")
    private FournisseurRegleCalculAssuranceSociale fournisseurRegleCalculCotisationAssuranceSociale;

    private CalculCotisationsSocialesSalarie calculateur;

    @BeforeEach
    public void initialise() throws Exception {
        calculateur = fournisseurRegleCalculCotisationAssuranceSociale.getCalculateurCotisationsSocialesSalarie(2009);
    }

    @Test
    public void calculCotisationAi() {
        assertThat(calculateur.calculPartSalarieeCotisationAi(BigDecimal.valueOf(100000))).isEqualByComparingTo(BigDecimal.valueOf(700));
        //assertEquals("Calcul cotisation Ai", new BigDecimal("700.00"), );
    }

    @Test
    public void calculCotisationApg() {
        assertThat(calculateur.calculPartSalarieeCotisationApg(BigDecimal.valueOf(100000))).isEqualByComparingTo(BigDecimal.valueOf(150));
        //assertEquals("Calcul cotisation Apg", new BigDecimal("150.00"), calculateur.calculPartSalarieeCotisationApg(new BigDecimal("100000")));
    }

    @Test
    public void calculCotisationAvs() {
        assertThat(calculateur.calculPartSalarieeCotisationAvs(BigDecimal.valueOf(100000))).isEqualByComparingTo(BigDecimal.valueOf(4200));
        //assertEquals("Calcul cotisation Avs", new BigDecimal("4200.00"), calculateur.calculPartSalarieeCotisationAvs(new BigDecimal("100000")));
    }

    @Test
    public void calculCotisationAvsAiApg() {
        assertThat(calculateur.calculPartSalarieeCotisationAvsAiApg(BigDecimal.valueOf(100000))).isEqualByComparingTo(BigDecimal.valueOf(5050));
        assertThat(calculateur.calculPartSalarieeCotisationAvsAiApg(BigDecimal.valueOf(4525))).isEqualTo(new BigDecimal("228.50"));
//		assertEquals("Calcul cotisation Avs/Ai/Apg", new BigDecimal("5050.00"), calculateur.calculPartSalarieeCotisationAvsAiApg(new BigDecimal("100000")));
//		assertEquals("Calcul cotisation Avs/Ai/Apg", new BigDecimal("228.50"), calculateur.calculPartSalarieeCotisationAvsAiApg(new BigDecimal("4525")));
    }

}
