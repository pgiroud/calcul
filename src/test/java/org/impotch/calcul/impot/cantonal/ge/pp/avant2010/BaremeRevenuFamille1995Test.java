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

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
@ContextConfiguration(locations = {"/beansCH_GE.xml","/beansAssurancesSociales.xml"})
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
public class BaremeRevenuFamille1995Test {

    private TestContextManager testContextManager;

    @Resource(name = "fournisseurRegleImpotCantonalGE")
    private FournisseurRegleImpotCantonalGE fournisseur;

    private BaremeFamille baremeCouple;

    private int revenu;
    private BigDecimal impot;

    @Before
    public void setUp() throws Exception {
        this.testContextManager = new TestContextManager(getClass());
        this.testContextManager.prepareTestInstance(this);
        baremeCouple = (BaremeFamille)fournisseur.getBaremeRevenuFamille(1995);
        baremeCouple.setArrondi(TypeArrondi.DIXIEME_CT);
    }

    public BaremeRevenuFamille1995Test(int revenu, BigDecimal impot) {
        this.revenu = revenu;
        this.impot = impot;
    }


    @Parameterized.Parameters
    public static Collection<Object []> data() throws IOException {
        ChargeurFichierEconometre chargeur = new ChargeurFichierEconometre();
        chargeur.setFichier(new ClassPathResource("ge/BASEIMP.CSV"));
        return Arrays.asList(chargeur.charger(1995,true));
    }

    @Test
    public void couple() {
        //TypeArrondi arrond = TypeArrondi.CINQ_CTS;
        assertThat(baremeCouple.calcul(BigDecimal.valueOf(revenu))).isEqualTo(impot);
    }



}
