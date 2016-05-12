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

import org.impotch.bareme.BaremeConstantParTranche;
import org.impotch.calcul.impot.indexation.Indexateur;
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
@ContextConfiguration(locations = {"/beansCH_GE.xml", "/beans-test.xml"})
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
public class ConstructeurBaremeDeductionBeneficiaireRenteAVSAITest {

    @Resource(name = "fournisseurRegleImpotCantonalGE")
    private FournisseurCantonalGE fournisseur;
    @Resource(name="indexateurGenevois2009")
    private Indexateur indexateur;
    private  ConstructeurBaremeDeductionBeneficiaireRenteAVSAI constructeur;


    @Before
    public void setUp() {
        constructeur = new ConstructeurBaremeDeductionBeneficiaireRenteAVSAI();
        constructeur.indexateur(indexateur);
        constructeur.validite(2010);    }

    @Test
    public void baremeSeul2014() {
        BaremeConstantParTranche bareme = constructeur.construireBaremeSeul(2014);
        assertThat(bareme.calcul(BigDecimal.valueOf(50000))).isEqualTo("10078");
        assertThat(bareme.calcul(BigDecimal.valueOf(100000))).isEqualTo("0");
    }

    @Test
    public void coupleDeuxRentes2014() {
        BaremeConstantParTranche bareme = constructeur.construireBaremeCoupleDeuxRentes(2014);
        assertThat(bareme.calcul(BigDecimal.valueOf(57947))).isEqualTo("11589");
        assertThat(bareme.calcul(BigDecimal.valueOf(57948))).isEqualTo("9272");
    }
}
