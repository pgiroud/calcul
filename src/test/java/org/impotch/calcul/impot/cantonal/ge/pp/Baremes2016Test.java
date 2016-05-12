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
package org.impotch.calcul.impot.cantonal.ge.pp;

import org.impotch.bareme.Bareme;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
@ContextConfiguration(locations = {"/beansCH_GE.xml"})
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
public class Baremes2016Test {

    private TestContextManager testContextManager;


    @Resource(name = "fournisseurRegleImpotCantonalGE")
    private FournisseurRegleImpotCantonalGE fournisseur;

    private Bareme bareme;

    private int revenu;
    private BigDecimal impot;

    public Baremes2016Test(int revenu, BigDecimal impot) {
        this.revenu = revenu;
        this.impot = impot;
    }

    @Before
    public void setUp() throws Exception {
        this.testContextManager = new TestContextManager(getClass());
        this.testContextManager.prepareTestInstance(this);
        bareme = fournisseur.getBaremeRevenu(2016);
    }

    @Test
    public void baremeRevenu() {
        assertThat(bareme.calcul(BigDecimal.valueOf(revenu))).isEqualTo(impot);
    }


    @Parameterized.Parameters
    public static Collection<Object []> data() throws IOException {
        ChargeurFichierEconometre2016 chargeur = new ChargeurFichierEconometre2016();
        chargeur.setFichier(new ClassPathResource("ge/BasImp2016.csv"));
        return Arrays.asList(chargeur.charger(false));
    }

}
