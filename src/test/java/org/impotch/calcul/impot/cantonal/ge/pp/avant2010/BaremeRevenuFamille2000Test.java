/**
 * This file is part of CalculImpotCH.
 *
 * CalculImpotCH is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * CalculImpotCH is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CalculImpotCH.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.impotch.calcul.impot.cantonal.ge.pp.avant2010;

import org.impotch.bareme.Bareme;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

import static org.fest.assertions.api.Assertions.assertThat;


@RunWith(Parameterized.class)
@ContextConfiguration(locations = "/beans.xml")
public class BaremeRevenuFamille2000Test {

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
        baremeCouple = (BaremeFamille)fournisseur.getBaremeRevenuFamille(2000);
        baremeCouple.setArrondi(TypeArrondi.DIXIEME_CT);
    }

    public BaremeRevenuFamille2000Test(int revenu, BigDecimal impot) {
        this.revenu = revenu;
        this.impot = impot;
    }

    @Parameterized.Parameters
    public static Collection<Object []> data() throws IOException {
        ChargeurFichierEconometre chargeur = new ChargeurFichierEconometre();
        chargeur.setFichier(new ClassPathResource("ge/BASEIMP.CSV"));
        return Arrays.asList(chargeur.charger(2000, true));
    }

    @Test
    public void couple() {
        assertThat(baremeCouple.calcul(BigDecimal.valueOf(revenu))).isEqualTo(impot);
    }

}
