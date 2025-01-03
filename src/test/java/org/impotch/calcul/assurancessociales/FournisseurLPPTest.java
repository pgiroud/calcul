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

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.impotch.calcul.assurancessociales.ContexteTestAssurancesSociales.CTX_TST_AS;

public class FournisseurLPPTest {

    private FournisseurRegleCalculAssuranceSociale fournisseurRegleCalculAssuranceSociale = CTX_TST_AS.getFournisseurRegleCalculAssuranceSociale();


    @Test
    public void test2011() {
        FournisseurMontantsLimitesPrevoyanceProfessionnelle fournisseur = fournisseurRegleCalculAssuranceSociale.getFournisseurMontantsLimitesPrevoyanceProfessionnelle(2011);
        assertThat(fournisseur.salaireMinimalAnnuel()).isEqualTo("20880");
        assertThat(fournisseur.salaireCoordonnéMinimalAnnuel()).isEqualTo("3480");
        assertThat(fournisseur.deductionCoordination()).isEqualTo("24360");
        assertThat(fournisseur.limiteSupérieureSalaireCoordonnéAnnuel()).isEqualTo("83520");
    }

    @Test
    public void test2013() {
        FournisseurMontantsLimitesPrevoyanceProfessionnelle fournisseur = fournisseurRegleCalculAssuranceSociale.getFournisseurMontantsLimitesPrevoyanceProfessionnelle(2013);
        assertThat(fournisseur.salaireMinimalAnnuel()).isEqualTo("21060");
        assertThat(fournisseur.salaireCoordonnéMinimalAnnuel()).isEqualTo("3510");
        assertThat(fournisseur.deductionCoordination()).isEqualTo("24570");
        assertThat(fournisseur.limiteSupérieureSalaireCoordonnéAnnuel()).isEqualTo("84240");
    }

    @Test
    public void test2015() {
        FournisseurMontantsLimitesPrevoyanceProfessionnelle fournisseur = fournisseurRegleCalculAssuranceSociale.getFournisseurMontantsLimitesPrevoyanceProfessionnelle(2015);
        assertThat(fournisseur.salaireMinimalAnnuel()).isEqualTo("21150");
        assertThat(fournisseur.salaireCoordonnéMinimalAnnuel()).isEqualTo("3525");
        assertThat(fournisseur.deductionCoordination()).isEqualTo("24675");
        assertThat(fournisseur.limiteSupérieureSalaireCoordonnéAnnuel()).isEqualTo("84600");
    }

    @Test
    public void test2017() {
        // Pas de changement
        FournisseurMontantsLimitesPrevoyanceProfessionnelle fournisseur = fournisseurRegleCalculAssuranceSociale.getFournisseurMontantsLimitesPrevoyanceProfessionnelle(2017);
        assertThat(fournisseur.salaireMinimalAnnuel()).isEqualTo("21150");
        assertThat(fournisseur.salaireCoordonnéMinimalAnnuel()).isEqualTo("3525");
        assertThat(fournisseur.deductionCoordination()).isEqualTo("24675");
        assertThat(fournisseur.limiteSupérieureSalaireCoordonnéAnnuel()).isEqualTo("84600");
    }

    @Test
    public void test2019() {
        // Voir RO 2018 3537 Ordonnance sur la prévoyance professionnelle vieillesse, survivants et invalidité (OPP 2)
        // https://www.fedlex.admin.ch/eli/oc/2018/578/fr
        FournisseurMontantsLimitesPrevoyanceProfessionnelle fournisseur = fournisseurRegleCalculAssuranceSociale.getFournisseurMontantsLimitesPrevoyanceProfessionnelle(2019);
        assertThat(fournisseur.salaireMinimalAnnuel()).isEqualTo("21330");
        assertThat(fournisseur.salaireCoordonnéMinimalAnnuel()).isEqualTo("3555");
        assertThat(fournisseur.deductionCoordination()).isEqualTo("24885");
        assertThat(fournisseur.limiteSupérieureSalaireCoordonnéAnnuel()).isEqualTo("85320");
    }

    @Test
    public void test2021() {
        // Voir RO 2020 4621 Ordonnance sur la prévoyance professionnelle vieillesse, survivants et invalidité (OPP 2)
        // https://www.fedlex.admin.ch/eli/oc/2020/824/fr
        FournisseurMontantsLimitesPrevoyanceProfessionnelle fournisseur = fournisseurRegleCalculAssuranceSociale.getFournisseurMontantsLimitesPrevoyanceProfessionnelle(2021);
        assertThat(fournisseur.salaireMinimalAnnuel()).isEqualTo("21510");
        assertThat(fournisseur.salaireCoordonnéMinimalAnnuel()).isEqualTo("3585");
        assertThat(fournisseur.deductionCoordination()).isEqualTo("25095");
        assertThat(fournisseur.limiteSupérieureSalaireCoordonnéAnnuel()).isEqualTo("86040");
    }

    @Test
    public void test2023() {
        // Voir RO 2022 609 Ordonnance sur la prévoyance professionnelle vieillesse, survivants et invalidité (OPP 2)
        // https://www.fedlex.admin.ch/eli/oc/2022/609/fr
        FournisseurMontantsLimitesPrevoyanceProfessionnelle fournisseur = fournisseurRegleCalculAssuranceSociale.getFournisseurMontantsLimitesPrevoyanceProfessionnelle(2023);
        assertThat(fournisseur.salaireMinimalAnnuel()).isEqualTo("22050");
        assertThat(fournisseur.salaireCoordonnéMinimalAnnuel()).isEqualTo("3675");
        assertThat(fournisseur.deductionCoordination()).isEqualTo("25725");
        assertThat(fournisseur.limiteSupérieureSalaireCoordonnéAnnuel()).isEqualTo("88200");
    }

    @Test
    public void test2025() {
        // Voir RO 2024 469 Ordonnance sur la prévoyance professionnelle vieillesse, survivants et invalidité (OPP 2)
        // https://www.fedlex.admin.ch/eli/oc/2024/469/fr
        FournisseurMontantsLimitesPrevoyanceProfessionnelle fournisseur = fournisseurRegleCalculAssuranceSociale.getFournisseurMontantsLimitesPrevoyanceProfessionnelle(2025);
        assertThat(fournisseur.salaireMinimalAnnuel()).isEqualTo("22680");
        assertThat(fournisseur.salaireCoordonnéMinimalAnnuel()).isEqualTo("3780");
        assertThat(fournisseur.deductionCoordination()).isEqualTo("26460");
        assertThat(fournisseur.limiteSupérieureSalaireCoordonnéAnnuel()).isEqualTo("90720");
    }
}
