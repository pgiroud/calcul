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

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.impotch.calcul.impot.cantonal.ge.pp.FournisseurRegleImpotCantonalGE;
import org.impotch.calcul.impot.taxation.pp.FournisseurAssiettePeriodique;
import org.impotch.calcul.impot.taxation.pp.ProducteurImpot;
import org.impotch.calcul.impot.taxation.pp.RecepteurImpotSomme;
import org.impotch.calcul.impot.taxation.pp.RecepteurMultipleImpot;
import org.impotch.calcul.impot.taxation.pp.RecepteurUniqueImpot;
import org.impotch.calcul.impot.taxation.pp.SituationFamiliale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.impotch.calcul.impot.cantonal.ge.ContexteTestCH_GE.CTX_TST_CH_GE;

public class ProducteurImpotGETest extends ProducteurImpotGEAvant2010 {

    private FournisseurRegleImpotCantonalGE fournisseur = CTX_TST_CH_GE.getFournisseurRegleImpotCantonalGE();

    final Logger logger = LoggerFactory.getLogger(ProducteurImpotGETest.class);

    private RecepteurMultipleImpot recepteur(String... codesImpot) {
        RecepteurMultipleImpot recepteur = new RecepteurMultipleImpot();
        for (String code : codesImpot) {
            recepteur.ajouteRecepteur(code, new RecepteurUniqueImpot(code));
        }
        recepteur.ajouteRecepteur("TOTAL", new RecepteurImpotSomme());
        return recepteur;
    }

    private BigDecimal getValeur(RecepteurMultipleImpot recepteur, String code) {
        if ("TOTAL".equals(code)) {
            return ((RecepteurImpotSomme) recepteur.getRecepteur(code)).getValeur();
        } else {
            return ((RecepteurUniqueImpot) recepteur.getRecepteur(code)).getValeur().getMontant();
        }
    }

    @Test
    public void testRevenu2009() {
        int periodeFiscale = 2009;
        int montantImposable = 100000;
        ProducteurImpot producteurGERevenu = fournisseur.getProducteurImpotsICCRevenu(2009);
        SituationFamiliale situation = creerSituationCelibataireSansEnfant();

        FournisseurAssiettePeriodique fournisseurAssiette = this.creerAssiettesAvecRabais(periodeFiscale, montantImposable, 16426);

        RecepteurMultipleImpot recepteur = recepteur("IBR", "RI", "RIBR", "CAR", "RCAR", "ADR", "PPR", "COR");

        producteurGERevenu.produireImpot(situation, fournisseurAssiette, recepteur);
        assertThat(getValeur(recepteur, "IBR")).isEqualTo(new BigDecimal("12821.20"));
        assertThat(getValeur(recepteur, "RI")).isEqualTo(new BigDecimal("-1140.75"));
        assertThat(getValeur(recepteur, "RIBR")).isEqualTo(new BigDecimal("-1401.65"));
        assertThat(getValeur(recepteur, "CAR")).isEqualTo(new BigDecimal("5548.20"));
        assertThat(getValeur(recepteur, "RCAR")).isEqualTo(new BigDecimal("-665.80"));
        assertThat(getValeur(recepteur, "ADR")).isEqualTo(new BigDecimal("116.80"));
        // La part privilégiée de Genève est de 0.26, le taux de cts de 0.455
        assertThat(getValeur(recepteur, "PPR")).isEqualTo(new BigDecimal("1381.80"));
        assertThat(getValeur(recepteur, "COR")).isEqualTo(new BigDecimal("3932.80"));

        assertThat(getValeur(recepteur, "TOTAL")).isEqualTo(new BigDecimal("20592.60"));
    }

    @Test
    public void fortune2007() {
        int periodeFiscale = 2007;
        int montantImposable = 293000;
        SituationFamiliale situation = creerSituationCelibataireSansEnfant();
        FournisseurAssiettePeriodique fournisseurAssiette = this.creerAssiettes(periodeFiscale, montantImposable);

        RecepteurMultipleImpot recepteur = recepteur("IBF", "CAF", "ADF", "PPF", "COF");

        ProducteurImpot producteurGE = fournisseur.getProducteurImpotsICCFortune(periodeFiscale);
        producteurGE.produireImpot(situation, fournisseurAssiette, recepteur);
        assertThat(getValeur(recepteur, "IBF")).isEqualTo(new BigDecimal("643.25"));
        assertThat(getValeur(recepteur, "CAF")).isEqualTo(new BigDecimal("305.55"));
        assertThat(getValeur(recepteur, "ADF")).isEqualTo(new BigDecimal("6.45"));
        // La part privilégiée de Genève est de 0.26, le taux de cts de 0.455
        assertThat(getValeur(recepteur, "PPF")).isEqualTo(new BigDecimal("76.10"));
        assertThat(getValeur(recepteur, "COF")).isEqualTo(new BigDecimal("216.60"));

        assertThat(getValeur(recepteur, "TOTAL")).isEqualTo(new BigDecimal("1247.95"));
    }

    @Test
    public void fortuneSupplementaire2007() {
        int periodeFiscale = 2007;
        int montantImposable = 293000;
        ProducteurImpot producteurGE = fournisseur.getProducteurImpotsICCFortuneSupplementaire(periodeFiscale);
        SituationFamiliale situation = creerSituationCelibataireSansEnfant();

        FournisseurAssiettePeriodique fournisseurAssiette = this.creerAssiettes(periodeFiscale, montantImposable);
        RecepteurUniqueImpot recepteurISF = new RecepteurUniqueImpot("ISF");
        producteurGE.produireImpot(situation, fournisseurAssiette, recepteurISF);
        assertThat(recepteurISF.getValeur().getMontant()).isEqualTo(new BigDecimal("22.70"));
    }
}
