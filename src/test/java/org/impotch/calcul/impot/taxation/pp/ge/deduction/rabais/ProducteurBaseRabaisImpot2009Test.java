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


import org.impotch.calcul.impot.cantonal.ge.pp.avant2010.SituationFamilialeGE;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;


public class ProducteurBaseRabaisImpot2009Test extends AbstractTestProducteurBaseRabaisImpot {

    private static final int PERIODE_FISCALE = 2009;

    private ProducteurBaseRabaisImpot producteur;

    public ProducteurBaseRabaisImpot2009Test() {
        super();
        initProducteurBaseRabaisImpot(2009, 15057, 3640, 52000,
                5200, 3559, 1369);
    }

    @Test
    public void celibataire_sans_charge() {
        // Barème source A0
        assertThat(calculRabais(creerCelibataireSansCharge()))
                .as("célibataire sans charge")
                .isEqualTo(BigDecimal.valueOf(16426));
    }

    @Test
    public void celibataire_avec_charge() {
        SituationFamilialeGE situation = creerCelibataireAvecCharge(1, true,PERIODE_FISCALE);
        BigDecimal rabais = calculRabais(situation);
        assertThat(rabais)
                .as("célibataire avec 1 charge moins de 12 ans")
                .isEqualTo(BigDecimal.valueOf(39970));
    }

    @Test
    public void couple_sans_double_activite_sans_charge() {
        // Barème source B0
        assertThat(calculRabais(creerCoupleSansDoubleActiviteSansCharge()))
                .as("couple sans double activité et sans charge")
                .isEqualTo(BigDecimal.valueOf(30114));
    }

    @Test
    public void couple_avec_1_grand_enfant_non_domicilié_GE() {
        // Barème source B1
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(1, false, false, PERIODE_FISCALE)))
                .as("couple avec 1 grand enfant non domicilié GE")
                .isEqualTo(BigDecimal.valueOf(37232));
    }

    @Test
    public void couple_avec_1_petit_enfant_non_domicilié_GE() {
        // Barème source B2
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(1, true, false, PERIODE_FISCALE)))
                .as("couple avec 1 petit enfant non domicilié GE")
                .isEqualTo(BigDecimal.valueOf(38601));
    }

    @Test
    public void couple_avec_1_petit_enfant_domicilié_GE() {
        // Barème source B3
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(1, true, true, PERIODE_FISCALE)))
                .as("couple avec 1 petit enfant domicilié GE")
                .isEqualTo(BigDecimal.valueOf(39970));
    }

    @Test
    public void couple_avec_2_grand_enfants_non_domicilié_GE() {
        // Barème source B4
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(2, false, false, PERIODE_FISCALE)))
                .as("couple avec 2 grand enfants non domicilié GE")
                .isEqualTo(BigDecimal.valueOf(44350));
    }

    @Test
    public void couple_avec_2_enfants_dont_au_moins_1_petit_non_domicilié_GE() {
        // Barème source B5
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(2, true, false, PERIODE_FISCALE)))
                .as("couple avec 2 enfants dont au moins 1 petit non domicilié GE")
                .isEqualTo(BigDecimal.valueOf(45719));
    }

    @Test
    public void couple_avec_2_enfants_dont_au_moins_1_petit_domicilié_GE() {
        // Barème source B6
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(2, true, true, PERIODE_FISCALE)))
                .as("couple avec 2 enfants dont au moins 1 petit domicilié GE")
                .isEqualTo(BigDecimal.valueOf(47088));
    }

    @Test
    public void couple_avec_3_grand_enfants_non_domicilié_GE() {
        // Barème source B7
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(3, false, false, PERIODE_FISCALE)))
                .as("couple avec 3 grand enfants non domicilié GE")
                .isEqualTo(BigDecimal.valueOf(51468));
    }

    @Test
    public void couple_avec_3_enfants_dont_au_moins_1_petit_non_domicilié_GE() {
        // Barème source B8
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(3, true, false, PERIODE_FISCALE)))
                .as("couple avec 3 enfants dont au moins 1 petit non domicilié GE")
                .isEqualTo(BigDecimal.valueOf(52837));
    }

    @Test
    public void couple_avec_3_enfants_dont_au_moins_1_petit_domicilié_GE() {
        // Barème source B9
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(3, true, true, PERIODE_FISCALE)))
                .as("couple avec 3 enfants dont au moins 1 petit domicilié GE")
                .isEqualTo(BigDecimal.valueOf(54206));
    }

    @Test
    public void couple_avec_4_grand_enfants_non_domicilié_GE() {
        // Barème source B10
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(4, false, false, PERIODE_FISCALE)))
                .as("couple avec 4 grand enfants non domicilié GE")
                .isEqualTo(BigDecimal.valueOf(58586));
    }

    @Test
    public void couple_avec_4_enfants_dont_au_moins_1_petit_non_domicilié_GE() {
        // Barème source B11
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(4, true, false, PERIODE_FISCALE)))
                .as("couple avec 4 enfants dont au moins 1 petit non domicilié GE")
                .isEqualTo(BigDecimal.valueOf(59955));
    }

    @Test
    public void couple_avec_4_enfants_dont_au_moins_1_petit_domicilié_GE() {
        // Barème source B12
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(4, true, true, PERIODE_FISCALE)))
                .as("couple avec 4 enfants dont au moins 1 petit domicilié GE")
                .isEqualTo(BigDecimal.valueOf(61324));
    }

    @Test
    public void couple_avec_5_grand_enfants_non_domicilié_GE() {
        // Barème source B13
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(5, false, false, PERIODE_FISCALE)))
                .as("couple avec 5 grand enfants non domicilié GE")
                .isEqualTo(BigDecimal.valueOf(65704));
    }

    @Test
    public void couple_avec_5_enfants_dont_au_moins_1_petit_non_domicilié_GE() {
        // Barème source B14
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(5, true, false, PERIODE_FISCALE)))
                .as("couple avec 5 enfants dont au moins 1 petit non domicilié GE")
                .isEqualTo(BigDecimal.valueOf(67073));
    }

    @Test
    public void couple_avec_5_enfants_dont_au_moins_1_petit_domicilié_GE() {
        // Barème source B15
        assertThat(calculRabais(creerCoupleSansDoubleActiviteAvecEnfant(5, true, true, PERIODE_FISCALE)))
                .as("couple avec 5 enfants dont au moins 1 petit domicilié GE")
                .isEqualTo(BigDecimal.valueOf(68442));
    }

    @Test
    public void couple_dont_un_fonctionnaire_international_sans_charge() {
        // Barème source I0
        assertThat(calculRabais(creerCoupleDontUnFonctionnaireInternational(0, false, PERIODE_FISCALE)))
                .as("couple dont un fonctionnaire international sans charge")
                .isEqualTo(BigDecimal.valueOf(15057));
    }

    @Test
    public void couple_dont_un_fonctionnaire_international_avec_1_grand_enfant() {
        // Barème source I1
        SituationFamilialeGE situation = creerCoupleDontUnFonctionnaireInternational(1, false, PERIODE_FISCALE);
        BigDecimal rabais = calculRabais(situation);
        assertThat(rabais)
                .as("couple avec un fonctionnaire international et avec 1 grand enfant")
                .isEqualTo(BigDecimal.valueOf(18616));
    }

    @Test
    public void couple_dont_un_fonctionnaire_international_avec_1_petit_enfant() {
        // Barème source I2
        assertThat(calculRabais(creerCoupleDontUnFonctionnaireInternational(1, true, PERIODE_FISCALE)))
                .as("couple avec un fonctionnaire international et avec 1 petit enfant")
                .isEqualTo(BigDecimal.valueOf(19985));
    }

    @Test
    public void couple_dont_un_fonctionnaire_international_avec_2_grand_enfant() {
        // Barème source I3
        assertThat(calculRabais(creerCoupleDontUnFonctionnaireInternational(2, false, PERIODE_FISCALE)))
                .as("couple avec un fonctionnaire international et avec 2 grand enfant")
                .isEqualTo(BigDecimal.valueOf(22175));
    }

    @Test
    public void couple_dont_un_fonctionnaire_international_avec_2_enfants_dont_au_moins_1_petit() {
        // Barème source I4
        assertThat(calculRabais(creerCoupleDontUnFonctionnaireInternational(2, true, PERIODE_FISCALE)))
                .as("couple avec un fonctionnaire international et avec 2 enfant dont au moins 1 petit")
                .isEqualTo(BigDecimal.valueOf(23544));
    }

    @Test
    public void couple_dont_un_fonctionnaire_international_avec_3_grand_enfant() {
        // Barème source I5
        assertThat(calculRabais(creerCoupleDontUnFonctionnaireInternational(3, false, PERIODE_FISCALE)))
                .as("couple avec un fonctionnaire international et avec 3 grand enfant")
                .isEqualTo(BigDecimal.valueOf(25734));
    }

    @Test
    public void couple_dont_un_fonctionnaire_international_avec_3_enfants_dont_au_moins_1_petit() {
        // Barème source I6
        SituationFamilialeGE situation = creerCoupleDontUnFonctionnaireInternational(3, true, PERIODE_FISCALE);
        BigDecimal rabais = calculRabais(situation);
        assertThat(rabais)
                .as("couple avec un fonctionnaire international et avec 3 enfant dont au moins 1 petit")
                .isEqualTo(BigDecimal.valueOf(27103));
    }

    @Test
    public void couple_dont_un_fonctionnaire_international_avec_4_grand_enfant() {
        // Barème source I7
        assertThat(calculRabais(creerCoupleDontUnFonctionnaireInternational(4, false, PERIODE_FISCALE)))
                .as("couple avec un fonctionnaire international et avec 4 grand enfant")
                .isEqualTo(BigDecimal.valueOf(29293));
    }

    @Test
    public void couple_dont_un_fonctionnaire_international_avec_4_enfants_dont_au_moins_1_petit() {
        // Barème source I8
        assertThat(calculRabais(creerCoupleDontUnFonctionnaireInternational(4, true, PERIODE_FISCALE)))
                .as("couple avec un fonctionnaire international et avec 4 enfant dont au moins 1 petit")
                .isEqualTo(BigDecimal.valueOf(30662));
    }

}
