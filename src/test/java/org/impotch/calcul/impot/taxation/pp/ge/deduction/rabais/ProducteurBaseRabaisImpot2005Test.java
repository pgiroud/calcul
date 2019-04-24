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

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import org.impotch.calcul.impot.cantonal.ge.pp.avant2010.SituationFamilialeGE;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class ProducteurBaseRabaisImpot2005Test extends AbstractTestProducteurBaseRabaisImpot {

    public ProducteurBaseRabaisImpot2005Test() {
        super();
        initProducteurBaseRabaisImpot(2005, 14288, 3640, 52000,
                5200, 3377, 1299);
    }

    @Test
    public void celibataire_sans_charge() {
        // Barème source A0
        assertThat(calculRabais(creerCelibataireSansCharge())).isEqualTo(BigDecimal.valueOf(15587));
//		assertEquals("célibataire sans charge",new BigDecimal("15587"),producteur.produireMontantDeterminantRabais(creerCelibataireSansCharge(),this.creerFournisseurMontant()));
    }

    @Test
    public void couple_sans_double_activite_sans_charge() {
        // Barème source B0
        assertThat(calculRabais(creerCoupleSansDoubleActiviteSansCharge())).isEqualTo(BigDecimal.valueOf(28576));
//		assertEquals("couple sans double activité et sans charge",new BigDecimal("28576"),producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteSansCharge(),this.creerFournisseurMontant()));
    }

    @Test
    public void couple_avec_1_grand_enfant_non_domicilié_GE() {
        // Barème source B1
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(1, false, false)))
                .isEqualTo(BigDecimal.valueOf(35330));
    }

    @Test
    public void couple_avec_1_petit_enfant_non_domicilié_GE() {
        // Barème source B2
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(1, true, false)))
                .isEqualTo(BigDecimal.valueOf(36629));
    }

    @Test
    public void couple_avec_1_petit_enfant_domicilié_GE() {
        // Barème source B3
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(1, true, true)))
                .isEqualTo(BigDecimal.valueOf(37928));
    }

    @Test
    public void couple_avec_2_grand_enfants_non_domicilié_GE() {
        // Barème source B4
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(2, false, false)))
                .isEqualTo(BigDecimal.valueOf(42084));
    }

    @Test
    public void couple_avec_2_enfants_dont_au_moins_1_petit_non_domicilié_GE() {
        // Barème source B5
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(2, true, false)))
                .isEqualTo(BigDecimal.valueOf(43383));
    }

    @Test
    public void couple_avec_2_enfants_dont_au_moins_1_petit_domicilié_GE() {
        // Barème source B6
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(2, true, true)))
                .isEqualTo(BigDecimal.valueOf(44682));
    }

    @Test
    public void couple_avec_3_grand_enfants_non_domicilié_GE() {
        // Barème source B7
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(3, false, false)))
                .isEqualTo(BigDecimal.valueOf(48838));
    }

    @Test
    public void couple_avec_3_enfants_dont_au_moins_1_petit_non_domicilié_GE() {
        // Barème source B8
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(3, true, false)))
                .isEqualTo(BigDecimal.valueOf(50137));
    }

    @Test
    public void couple_avec_3_enfants_dont_au_moins_1_petit_domicilié_GE() {
        // Barème source B9
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(3, true, true)))
                .isEqualTo(BigDecimal.valueOf(51436));
    }

    @Test
    public void couple_avec_4_grand_enfants_non_domicilié_GE() {
        // Barème source B10
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(4, false, false)))
                .isEqualTo(BigDecimal.valueOf(55592));
    }

    @Test
    public void couple_avec_4_enfants_dont_au_moins_1_petit_non_domicilié_GE() {
        // Barème source B11
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(4, true, false)))
                .isEqualTo(BigDecimal.valueOf(56891));
    }

    @Test
    public void couple_avec_4_enfants_dont_au_moins_1_petit_domicilié_GE() {
        // Barème source B12
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(4, true, true)))
                .isEqualTo(BigDecimal.valueOf(58190));
    }

    @Test
    public void couple_avec_5_grand_enfants_non_domicilié_GE() {
        // Barème source B13
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(5, false, false)))
                .isEqualTo(BigDecimal.valueOf(62346));
    }

    @Test
    public void couple_avec_5_enfants_dont_au_moins_1_petit_non_domicilié_GE() {
        // Barème source B14
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(5, true, false)))
                .isEqualTo(BigDecimal.valueOf(63645));
    }

    @Test
    public void couple_avec_5_enfants_dont_au_moins_1_petit_domicilié_GE() {
        // Barème source B15
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(5, true, true)))
                .isEqualTo(BigDecimal.valueOf(64944));
    }

//    @Test
//    public void coupleSansDoubleActiviteAvecEnfant() {
        // Barème source B1 B2 B3
//        assertEquals("couple avec 1 grand enfant non domicilié GE", new BigDecimal("35330"), producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(1, false, false), this.creerFournisseurMontant()));
//        assertEquals("couple avec 1 petit enfant non domicilié GE", new BigDecimal("36629"), producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(1, true, false), this.creerFournisseurMontant()));
//        assertEquals("couple avec 1 petit enfant domicilié GE", new BigDecimal("37928"), producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(1, true, true), this.creerFournisseurMontant()));

        // Barème source B4 B5 B6
//        assertEquals("couple avec 2 grand enfants non domicilié GE", new BigDecimal("42084"), producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(2, false, false), this.creerFournisseurMontant()));
//        assertEquals("couple avec 2 enfants dont au moins 1 petit non domicilié GE", new BigDecimal("43383"), producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(2, true, false), this.creerFournisseurMontant()));
//        assertEquals("couple avec 2 enfants dont au moins 1 petit domicilié GE", new BigDecimal("44682"), producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(2, true, true), this.creerFournisseurMontant()));

        // Barème source B7 B8 B9
//        assertEquals("couple avec 3 grand enfants non domicilié GE", new BigDecimal("48838"), producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(3, false, false), this.creerFournisseurMontant()));
//        assertEquals("couple avec 3 enfants dont au moins 1 petit non domicilié GE", new BigDecimal("50137"), producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(3, true, false), this.creerFournisseurMontant()));
//        assertEquals("couple avec 3 enfants dont au moins 1 petit domicilié GE", new BigDecimal("51436"), producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(3, true, true), this.creerFournisseurMontant()));

        // Barème source B10 B11 B12
//        assertEquals("couple avec 4 grand enfants non domicilié GE", new BigDecimal("55592"), producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(4, false, false), this.creerFournisseurMontant()));
//        assertEquals("couple avec 4 enfants dont au moins 1 petit non domicilié GE", new BigDecimal("56891"), producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(4, true, false), this.creerFournisseurMontant()));
//        assertEquals("couple avec 4 enfants dont au moins 1 petit domicilié GE", new BigDecimal("58190"), producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(4, true, true), this.creerFournisseurMontant()));

        // Barème source B13 B14 B15
//        assertEquals("couple avec 5 grand enfants non domicilié GE", new BigDecimal("62346"), producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(5, false, false), this.creerFournisseurMontant()));
//        assertEquals("couple avec 5 enfants dont au moins 1 petit non domicilié GE", new BigDecimal("63645"), producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(5, true, false), this.creerFournisseurMontant()));
//        assertEquals("couple avec 5 enfants dont au moins 1 petit domicilié GE", new BigDecimal("64944"), producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(5, true, true), this.creerFournisseurMontant()));
//    }


    @Test
    public void couple_dont_un_fonctionnaire_international_sans_charge() {
        // Barème source I0
        assertThat(calculRabais(creerCoupleDontUnFonctionnaireInternational(0, false)))
                .isEqualTo(BigDecimal.valueOf(14288));
    }

    @Test
    public void couple_dont_un_fonctionnaire_international_avec_1_grand_enfant() {
        // Barème source I1
        assertThat(calculRabais(creerCoupleDontUnFonctionnaireInternational(1, false)))
                .isEqualTo(BigDecimal.valueOf(17665));

    }

    @Test
    public void couple_dont_un_fonctionnaire_international_avec_1_petit_enfant() {
        // Barème source I2
        assertThat(calculRabais(creerCoupleDontUnFonctionnaireInternational(1, true)))
                .isEqualTo(BigDecimal.valueOf(18964));

    }

    @Test
    public void couple_dont_un_fonctionnaire_international_avec_2_grand_enfant() {
        // Barème source I3
        assertThat(calculRabais(creerCoupleDontUnFonctionnaireInternational(2, false)))
                .isEqualTo(BigDecimal.valueOf(21042));
    }

    @Test
    public void couple_dont_un_fonctionnaire_international_avec_2_enfants_dont_au_moins_1_petit() {
        // Barème source I4
        assertThat(calculRabais(creerCoupleDontUnFonctionnaireInternational(2, true)))
                .isEqualTo(BigDecimal.valueOf(22341));
    }

    @Test
    public void couple_dont_un_fonctionnaire_international_avec_3_grand_enfant() {
        // Barème source I5
        assertThat(calculRabais(creerCoupleDontUnFonctionnaireInternational(3, false)))
                .isEqualTo(BigDecimal.valueOf(24419));
    }

    @Test
    public void couple_dont_un_fonctionnaire_international_avec_3_enfants_dont_au_moins_1_petit() {
        // Barème source I6
        assertThat(calculRabais(creerCoupleDontUnFonctionnaireInternational(3, true)))
                .isEqualTo(BigDecimal.valueOf(25718));
    }

    @Test
    public void couple_dont_un_fonctionnaire_international_avec_4_grand_enfant() {
        // Barème source I7
        assertThat(calculRabais(creerCoupleDontUnFonctionnaireInternational(4, false)))
                .isEqualTo(BigDecimal.valueOf(27796));
    }

    @Test
    public void couple_dont_un_fonctionnaire_international_avec_4_enfants_dont_au_moins_1_petit() {
        // Barème source I8
        assertThat(calculRabais(creerCoupleDontUnFonctionnaireInternational(4, true)))
                .isEqualTo(BigDecimal.valueOf(29095));
    }

//    @Test
//    public void coupleDontUnFonctionnaireInternational() {
        // Barème source I0
//        assertEquals("couple dont un fonctionnaire international sans charge", new BigDecimal("14288"), producteur.produireMontantDeterminantRabais(creerCoupleDontUnFonctionnaireInternational(0, false), this.creerFournisseurMontant()));
        // Barème source I1 I2
//        assertEquals("couple avec 1 grand enfant", new BigDecimal("17665"), producteur.produireMontantDeterminantRabais(creerCoupleDontUnFonctionnaireInternational(1, false), this.creerFournisseurMontant()));
//        assertEquals("couple avec 1 petit enfant", new BigDecimal("18964"), producteur.produireMontantDeterminantRabais(creerCoupleDontUnFonctionnaireInternational(1, true), this.creerFournisseurMontant()));
        // Barème source I3 I4
//        assertEquals("couple avec 2 grand enfant", new BigDecimal("21042"), producteur.produireMontantDeterminantRabais(creerCoupleDontUnFonctionnaireInternational(2, false), this.creerFournisseurMontant()));
//        assertEquals("couple avec 2 enfant dont au moins 1 petit", new BigDecimal("22341"), producteur.produireMontantDeterminantRabais(creerCoupleDontUnFonctionnaireInternational(2, true), this.creerFournisseurMontant()));
        // Barème source I5 I6
//        assertEquals("couple avec 3 grand enfant", new BigDecimal("24419"), producteur.produireMontantDeterminantRabais(creerCoupleDontUnFonctionnaireInternational(3, false), this.creerFournisseurMontant()));
//        assertEquals("couple avec 3 enfant dont au moins 1 petit", new BigDecimal("25718"), producteur.produireMontantDeterminantRabais(creerCoupleDontUnFonctionnaireInternational(3, true), this.creerFournisseurMontant()));
        // Barème source I7 I8
//        assertEquals("couple avec 4 grand enfant", new BigDecimal("27796"), producteur.produireMontantDeterminantRabais(creerCoupleDontUnFonctionnaireInternational(4, false), this.creerFournisseurMontant()));
//        assertEquals("couple avec 4 enfant dont au moins 1 petit", new BigDecimal("29095"), producteur.produireMontantDeterminantRabais(creerCoupleDontUnFonctionnaireInternational(4, true), this.creerFournisseurMontant()));
//    }

}
