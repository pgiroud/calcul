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
package org.impotch.calcul.impot.cantonal.ge.pp.avant2010;

import org.impotch.bareme.BaremeTauxMarginalIntegrable;
import org.impotch.calcul.impot.cantonal.ge.pp.ChargeurFichierEconometre;
import org.impotch.calcul.impot.cantonal.ge.pp.FournisseurRegleImpotCantonalGE;
import org.impotch.util.TypeArrondi;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
@ContextConfiguration(locations = {"/beansCH_GE.xml"})
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
public class BaremeRevenuSeul2000Test {

    private TestContextManager testContextManager;

    @Resource(name = "fournisseurRegleImpotCantonalGE")
    private FournisseurRegleImpotCantonalGE fournisseur;

    private BaremeTauxMarginalIntegrable bareme;

    private int revenu;
    private BigDecimal impot;

    @Before
    public void setUp() throws Exception {
        this.testContextManager = new TestContextManager(getClass());
        this.testContextManager.prepareTestInstance(this);
        bareme = (BaremeTauxMarginalIntegrable)fournisseur.getBaremeRevenu(2000);
        bareme.setTypeArrondi(TypeArrondi.DIXIEME_CT);
    }

    public BaremeRevenuSeul2000Test(int revenu, BigDecimal impot) {
        this.revenu = revenu;
        this.impot = impot;
    }


    @Parameterized.Parameters
    public static Collection<Object []> data() throws IOException {
        ChargeurFichierEconometre chargeur = new ChargeurFichierEconometre();
        chargeur.setFichier(new ClassPathResource("ge/BASEIMP.CSV"));
        List<Object []> objets = Arrays.asList(chargeur.charger(2000, false));
        // Il y a un arrondi distinct sur la 3ème décimal !
        objets.get(1633)[1] = new BigDecimal("299984.611"); // À la place de 299984.612
        return objets;
    }

    @Test
    public void test() {
        assertThat(bareme.calcul(BigDecimal.valueOf(revenu))).isEqualTo(impot);
    }

}
