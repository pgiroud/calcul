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

import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.annotation.Resource;

import static org.assertj.core.api.Assertions.assertThat;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "/beansAssurancesSociales.xml")
//@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
@SpringJUnitConfig(locations = "/beansAssurancesSociales.xml")
public class FournisseurLPPTest {

    @Resource
    private FournisseurRegleCalculAssuranceSociale fournisseurRegleCalculAssuranceSociale;


    @Test
    public void test2011() {
        FournisseurMontantsLimitesPrevoyanceProfessionnelle fournisseur = fournisseurRegleCalculAssuranceSociale.getFournisseurMontantsLimitesPrevoyanceProfessionnelle(2011);
        assertThat(fournisseur.getSalaireMinimalAnnuel()).isEqualTo("20880");
        assertThat(fournisseur.getSalaireCoordonneMinimalAnnuel()).isEqualTo("3480");
        assertThat(fournisseur.getDeductionCoordination()).isEqualTo("24360");
        assertThat(fournisseur.getLimiteSuperieureSalaireAnnuel()).isEqualTo("83520");
    }

    @Test
    public void test2013() {
        FournisseurMontantsLimitesPrevoyanceProfessionnelle fournisseur = fournisseurRegleCalculAssuranceSociale.getFournisseurMontantsLimitesPrevoyanceProfessionnelle(2013);
        assertThat(fournisseur.getSalaireMinimalAnnuel()).isEqualTo("21060");
        assertThat(fournisseur.getSalaireCoordonneMinimalAnnuel()).isEqualTo("3510");
        assertThat(fournisseur.getDeductionCoordination()).isEqualTo("24570");
        assertThat(fournisseur.getLimiteSuperieureSalaireAnnuel()).isEqualTo("84240");
    }

    @Test
    public void test2015() {
        FournisseurMontantsLimitesPrevoyanceProfessionnelle fournisseur = fournisseurRegleCalculAssuranceSociale.getFournisseurMontantsLimitesPrevoyanceProfessionnelle(2015);
        assertThat(fournisseur.getSalaireMinimalAnnuel()).isEqualTo("21150");
        assertThat(fournisseur.getSalaireCoordonneMinimalAnnuel()).isEqualTo("3525");
        assertThat(fournisseur.getDeductionCoordination()).isEqualTo("24675");
        assertThat(fournisseur.getLimiteSuperieureSalaireAnnuel()).isEqualTo("84600");
    }


    @Test
    public void test2019() {
        FournisseurMontantsLimitesPrevoyanceProfessionnelle fournisseur = fournisseurRegleCalculAssuranceSociale.getFournisseurMontantsLimitesPrevoyanceProfessionnelle(2019);
        assertThat(fournisseur.getSalaireMinimalAnnuel()).isEqualTo("21330");
        assertThat(fournisseur.getSalaireCoordonneMinimalAnnuel()).isEqualTo("3555");
        assertThat(fournisseur.getDeductionCoordination()).isEqualTo("24885");
        assertThat(fournisseur.getLimiteSuperieureSalaireAnnuel()).isEqualTo("85320");
    }

}
