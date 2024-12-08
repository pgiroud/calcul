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
package org.impotch.calcul.impot.cantonal.ge.pp.avant2010;


import java.math.BigDecimal;

import org.impotch.calcul.impot.cantonal.ge.pp.FournisseurRegleImpotCantonalGE;
import org.impotch.calcul.impot.taxation.pp.FournisseurAssiettePeriodique;
import org.impotch.calcul.impot.taxation.pp.ProducteurImpot;
import org.impotch.calcul.impot.taxation.pp.RecepteurMultipleImpot;
import org.impotch.calcul.impot.taxation.pp.RecepteurUniqueImpot;
import org.impotch.calcul.impot.taxation.pp.SituationFamiliale;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.impotch.calcul.impot.cantonal.ge.ContexteTestCH_GE.CTX_TST_CH_GE;


public class ProducteurImpotAvecRabaisTest extends ProducteurImpotGEAvant2010 {

    private FournisseurRegleImpotCantonalGE fournisseur = CTX_TST_CH_GE.getFournisseurRegleImpotCantonalGE();


    private void testFamilleAvecEnfant(final int periodeFiscale, final int montantImposable, final String montantImpot, int montantDeterminantRabais, String montantRabaisImpot, int agePremierEnfant, final int... ageEnfantsSuivants) {
        ProducteurImpot producteurGERevenu = fournisseur.getProducteurImpotsICCRevenu(periodeFiscale);
        SituationFamiliale situation = creerSituationFamilleAvecEnfant(agePremierEnfant, ageEnfantsSuivants);
        FournisseurAssiettePeriodique fournisseur = this.creerAssiettesAvecRabais(periodeFiscale, montantImposable, montantDeterminantRabais);
        RecepteurUniqueImpot recepteurIBR = new RecepteurUniqueImpot("IBR");
        RecepteurUniqueImpot recepteurRI = new RecepteurUniqueImpot("RI");

        RecepteurMultipleImpot recepteur = new RecepteurMultipleImpot();
        recepteur.ajouteRecepteur("IBR", recepteurIBR);
        recepteur.ajouteRecepteur("RI", recepteurRI);

        producteurGERevenu.produireImpot(situation, fournisseur, recepteur);
        assertThat(recepteurIBR.getValeur().getMontant()).isEqualTo(new BigDecimal(montantImpot));
        assertThat(recepteurRI.getValeur().getMontant()).isEqualTo(new BigDecimal(montantRabaisImpot));
    }

    private void testCelibataireSansEnfant(final int periodeFiscale, final int montantImposable, final String montantImpot, int montantDeterminantRabais, String montantRabais) {
        ProducteurImpot producteurGERevenu = fournisseur.getProducteurImpotsICCRevenu(periodeFiscale);
        SituationFamiliale situation = creerSituationCelibataireSansEnfant();
        FournisseurAssiettePeriodique fournisseur = this.creerAssiettesAvecRabais(periodeFiscale, montantImposable, montantDeterminantRabais);
        RecepteurUniqueImpot recepteurIBR = new RecepteurUniqueImpot("IBR");
        RecepteurUniqueImpot recepteurRI = new RecepteurUniqueImpot("RI");

        RecepteurMultipleImpot recepteur = new RecepteurMultipleImpot();
        recepteur.ajouteRecepteur("IBR", recepteurIBR);
        recepteur.ajouteRecepteur("RI", recepteurRI);

        producteurGERevenu.produireImpot(situation, fournisseur, recepteur);
        assertThat(recepteurIBR.getValeur().getMontant()).isEqualTo(new BigDecimal(montantImpot));
        assertThat(recepteurRI.getValeur().getMontant()).isEqualTo(new BigDecimal(montantRabais));
    }

    @Test
    public void testProduireImpotGERevenuFamilleAvecEnfant() {
        // Montant déterminant rabais = 2 * 15057 + 4 * 3559 + 1369 = 45719
        testFamilleAvecEnfant(2009, 100000, "11114.15", 45719, "-3768.55", 10, 6);
    }

    @Test
    public void testProduireImpotGERevenuCelibataireSansEnfant() {
        testCelibataireSansEnfant(2007, 60661, "7036.55", 15587, "-1065.75");
    }

}
