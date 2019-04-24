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
package org.impotch.calcul.impot.taxation.pp.ge.deduction.rabais;


import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;


public class ProducteurBaseRabaisImpot2009Test extends AbstractTestProducteurBaseRabaisImpot {

    private ProducteurBaseRabaisImpot producteur;

    public ProducteurBaseRabaisImpot2009Test() {
        super();
        initProducteurBaseRabaisImpot(2009, 15057, 3640, 52000,
                5200, 3559, 1369);
    }

    @Test
    public void celibataire_sans_charge() {
        // Barème source A0
        assertThat(calculRabais(creerCelibataireSansCharge())).isEqualTo(BigDecimal.valueOf(16426));
//        assertEquals("célibataire sans charge", new BigDecimal("16426"), producteur.produireMontantDeterminantRabais(creerCelibataireSansCharge(), this.creerFournisseurMontant()));
    }

    @Test
    public void celibataire_avec_charge() {
        assertThat(calculRabais(creerCelibataireAvecCharge(1, true))).isEqualTo(BigDecimal.valueOf(39970));
//        assertEquals("célibataire avec 1 charge moins de 12 ans", new BigDecimal("39970"), producteur.produireMontantDeterminantRabais(creerCelibataireAvecCharge(1, true), this.creerFournisseurMontant()));
    }

    @Test
    public void couple_sans_double_activite_sans_charge() {
        // Barème source B0
        assertThat(calculRabais(creerCoupleSansDoubleActiviteSansCharge())).isEqualTo(BigDecimal.valueOf(30114));
//        assertEquals("couple sans double activité et sans charge", new BigDecimal("30114"), producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteSansCharge(), this.creerFournisseurMontant()));
    }

    @Test
    public void couple_avec_1_grand_enfant_non_domicilié_GE() {
        // Barème source B1
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(1, false, false)))
                .isEqualTo(BigDecimal.valueOf(37232));
//        assertEquals("couple avec 1 grand enfant non domicilié GE", new BigDecimal("37232"), producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(1, false, false), this.creerFournisseurMontant()));
    }

    @Test
    public void couple_avec_1_petit_enfant_non_domicilié_GE() {
        // Barème source B2
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(1, true, false)))
                .isEqualTo(BigDecimal.valueOf(38601));
//        assertEquals("couple avec 1 petit enfant non domicilié GE", new BigDecimal("38601"), producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(1, true, false), this.creerFournisseurMontant()));
    }

    @Test
    public void couple_avec_1_petit_enfant_domicilié_GE() {
        // Barème source B3
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(1, true, true)))
                .isEqualTo(BigDecimal.valueOf(39970));
//        assertEquals("couple avec 1 petit enfant domicilié GE", new BigDecimal("39970"), producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(1, true, true), this.creerFournisseurMontant()));
    }

    @Test
    public void couple_avec_2_grand_enfants_non_domicilié_GE() {
        // Barème source B4
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(2, false, false)))
                .isEqualTo(BigDecimal.valueOf(44350));
//        assertEquals("couple avec 2 grand enfants non domicilié GE", new BigDecimal("44350"), producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(2, false, false), this.creerFournisseurMontant()));
    }

    @Test
    public void couple_avec_2_enfants_dont_au_moins_1_petit_non_domicilié_GE() {
        // Barème source B5
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(2, true, false)))
                .isEqualTo(BigDecimal.valueOf(45719));
//        assertEquals("couple avec 2 enfants dont au moins 1 petit non domicilié GE", new BigDecimal("45719"), producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(2, true, false), this.creerFournisseurMontant()));
    }

    @Test
    public void couple_avec_2_enfants_dont_au_moins_1_petit_domicilié_GE() {
        // Barème source B6
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(2, true, true)))
                .isEqualTo(BigDecimal.valueOf(47088));
//        assertEquals("couple avec 2 enfants dont au moins 1 petit domicilié GE", new BigDecimal("47088"), producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(2, true, true), this.creerFournisseurMontant()));
    }

    @Test
    public void couple_avec_3_grand_enfants_non_domicilié_GE() {
        // Barème source B7
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(3, false, false)))
                .isEqualTo(BigDecimal.valueOf(51468));
//        assertEquals("couple avec 3 grand enfants non domicilié GE", new BigDecimal("51468"), producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(3, false, false), this.creerFournisseurMontant()));
    }

    @Test
    public void couple_avec_3_enfants_dont_au_moins_1_petit_non_domicilié_GE() {
        // Barème source B8
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(3, true, false)))
                .isEqualTo(BigDecimal.valueOf(52837));
//        assertEquals("couple avec 3 enfants dont au moins 1 petit non domicilié GE", new BigDecimal("52837"), producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(3, true, false), this.creerFournisseurMontant()));
    }

    @Test
    public void couple_avec_3_enfants_dont_au_moins_1_petit_domicilié_GE() {
        // Barème source B9
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(3, true, true)))
                .isEqualTo(BigDecimal.valueOf(54206));
//        assertEquals("couple avec 3 enfants dont au moins 1 petit domicilié GE", new BigDecimal("54206"), producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(3, true, true), this.creerFournisseurMontant()));
    }

    @Test
    public void couple_avec_4_grand_enfants_non_domicilié_GE() {
        // Barème source B10
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(4, false, false)))
                .isEqualTo(BigDecimal.valueOf(58586));
//        assertEquals("couple avec 4 grand enfants non domicilié GE", new BigDecimal("58586"), producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(4, false, false), this.creerFournisseurMontant()));
    }

    @Test
    public void couple_avec_4_enfants_dont_au_moins_1_petit_non_domicilié_GE() {
        // Barème source B11
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(4, true, false)))
                .isEqualTo(BigDecimal.valueOf(59955));
//        assertEquals("couple avec 4 enfants dont au moins 1 petit non domicilié GE", new BigDecimal("59955"), producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(4, true, false), this.creerFournisseurMontant()));
    }

    @Test
    public void couple_avec_4_enfants_dont_au_moins_1_petit_domicilié_GE() {
        // Barème source B12
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(4, true, true)))
                .isEqualTo(BigDecimal.valueOf(61324));
//        assertEquals("couple avec 4 enfants dont au moins 1 petit domicilié GE", new BigDecimal("61324"), producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(4, true, true), this.creerFournisseurMontant()));
    }

    @Test
    public void couple_avec_5_grand_enfants_non_domicilié_GE() {
        // Barème source B13
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(5, false, false)))
                .isEqualTo(BigDecimal.valueOf(65704));
//        assertEquals("couple avec 5 grand enfants non domicilié GE", new BigDecimal("65704"), producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(5, false, false), this.creerFournisseurMontant()));
    }

    @Test
    public void couple_avec_5_enfants_dont_au_moins_1_petit_non_domicilié_GE() {
        // Barème source B14
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(5, true, false)))
                .isEqualTo(BigDecimal.valueOf(67073));
//        assertEquals("couple avec 5 enfants dont au moins 1 petit non domicilié GE", new BigDecimal("67073"), producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(5, true, false), this.creerFournisseurMontant()));
    }

    @Test
    public void couple_avec_5_enfants_dont_au_moins_1_petit_domicilié_GE() {
        // Barème source B15
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(5, true, true)))
                .isEqualTo(BigDecimal.valueOf(68442));
//        assertEquals("couple avec 5 enfants dont au moins 1 petit domicilié GE", new BigDecimal("68442"), producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(5, true, true), this.creerFournisseurMontant()));
    }

    @Test
    public void couple_dont_un_fonctionnaire_international_sans_charge() {
        // Barème source I0
        assertThat(calculRabais(creerCoupleDontUnFonctionnaireInternational(0, false)))
                .isEqualTo(BigDecimal.valueOf(15057));
//        assertEquals("couple dont un fonctionnaire international sans charge", new BigDecimal("15057"), producteur.produireMontantDeterminantRabais(creerCoupleDontUnFonctionnaireInternational(0, false), this.creerFournisseurMontant()));
    }

    @Test
    public void couple_dont_un_fonctionnaire_international_avec_1_grand_enfant() {
        // Barème source I1
        assertThat(calculRabais(creerCoupleDontUnFonctionnaireInternational(1, false)))
                .isEqualTo(BigDecimal.valueOf(18616));
//        assertEquals("couple avec 1 grand enfant", new BigDecimal("18616"), producteur.produireMontantDeterminantRabais(creerCoupleDontUnFonctionnaireInternational(1, false), this.creerFournisseurMontant()));
    }

    @Test
    public void couple_dont_un_fonctionnaire_international_avec_1_petit_enfant() {
        // Barème source I2
        assertThat(calculRabais(creerCoupleDontUnFonctionnaireInternational(1, true)))
                .isEqualTo(BigDecimal.valueOf(19985));
//        assertEquals("couple avec 1 petit enfant", new BigDecimal("19985"), producteur.produireMontantDeterminantRabais(creerCoupleDontUnFonctionnaireInternational(1, true), this.creerFournisseurMontant()));
    }

    @Test
    public void couple_dont_un_fonctionnaire_international_avec_2_grand_enfant() {
        // Barème source I3
        assertThat(calculRabais(creerCoupleDontUnFonctionnaireInternational(2, false)))
                .isEqualTo(BigDecimal.valueOf(22175));
//        assertEquals("couple avec 2 grand enfant", new BigDecimal("22175"), producteur.produireMontantDeterminantRabais(creerCoupleDontUnFonctionnaireInternational(2, false), this.creerFournisseurMontant()));
    }

    @Test
    public void couple_dont_un_fonctionnaire_international_avec_2_enfants_dont_au_moins_1_petit() {
        // Barème source I4
        assertThat(calculRabais(creerCoupleDontUnFonctionnaireInternational(2, true)))
                .isEqualTo(BigDecimal.valueOf(23544));
//        assertEquals("couple avec 2 enfant dont au moins 1 petit", new BigDecimal("23544"), producteur.produireMontantDeterminantRabais(creerCoupleDontUnFonctionnaireInternational(2, true), this.creerFournisseurMontant()));
    }

    @Test
    public void couple_dont_un_fonctionnaire_international_avec_3_grand_enfant() {
        // Barème source I5
        assertThat(calculRabais(creerCoupleDontUnFonctionnaireInternational(3, false)))
                .isEqualTo(BigDecimal.valueOf(25734));
//        assertEquals("couple avec 3 grand enfant", new BigDecimal("25734"), producteur.produireMontantDeterminantRabais(creerCoupleDontUnFonctionnaireInternational(3, false), this.creerFournisseurMontant()));
    }

    @Test
    public void couple_dont_un_fonctionnaire_international_avec_3_enfants_dont_au_moins_1_petit() {
        // Barème source I6
        assertThat(calculRabais(creerCoupleDontUnFonctionnaireInternational(3, true)))
                .isEqualTo(BigDecimal.valueOf(27103));
//        assertEquals("couple avec 3 enfant dont au moins 1 petit", new BigDecimal("27103"), producteur.produireMontantDeterminantRabais(creerCoupleDontUnFonctionnaireInternational(3, true), this.creerFournisseurMontant()));
    }

    @Test
    public void couple_dont_un_fonctionnaire_international_avec_4_grand_enfant() {
        // Barème source I7
        assertThat(calculRabais(creerCoupleDontUnFonctionnaireInternational(4, false)))
                .isEqualTo(BigDecimal.valueOf(29293));
//        assertEquals("couple avec 4 grand enfant", new BigDecimal("29293"), producteur.produireMontantDeterminantRabais(creerCoupleDontUnFonctionnaireInternational(4, false), this.creerFournisseurMontant()));
    }

    @Test
    public void couple_dont_un_fonctionnaire_international_avec_4_enfants_dont_au_moins_1_petit() {
        // Barème source I8
        assertThat(calculRabais(creerCoupleDontUnFonctionnaireInternational(4, true)))
                .isEqualTo(BigDecimal.valueOf(30662));
//        assertEquals("couple avec 4 enfant dont au moins 1 petit", new BigDecimal("30662"), producteur.produireMontantDeterminantRabais(creerCoupleDontUnFonctionnaireInternational(4, true), this.creerFournisseurMontant()));
    }

}
