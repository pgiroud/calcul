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
package org.impotch.calcul.impot.cantonal.ne.pp;

import org.impotch.bareme.Bareme;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import javax.annotation.Resource;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/beansCH_NE.xml")
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
public class BaremeFortune2012Test {

    @Resource(name = "fournisseurRegleImpotCantonalNE")
    private FournisseurRegleImpotCantonalNE fournisseur;

    private Bareme bareme;

    @Before
    public void setUp() throws Exception {
        bareme = fournisseur.getBaremeFortune(2012);
    }

    @Test
    public void borne() {
        assertThat(bareme.calcul(BigDecimal.valueOf(50000))).isEqualTo("0.00");
        assertThat(bareme.calcul(BigDecimal.valueOf(200000))).isEqualTo("450.00");
        assertThat(bareme.calcul(BigDecimal.valueOf(350000))).isEqualTo("1050.00");
        assertThat(bareme.calcul(BigDecimal.valueOf(500000))).isEqualTo("1800.00");
    }
}
