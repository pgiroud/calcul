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
package org.impotch.calcul.impot.federal.pp;

import org.impotch.calcul.impot.Impot;
import org.impotch.calcul.impot.federal.FournisseurRegleImpotFederal;
import org.impotch.calcul.impot.taxation.pp.*;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.impotch.calcul.impot.federal.ContexteTest_CH.CTX_TST_CH;

import static org.impotch.calcul.impot.PeriodeFiscale.annee;

public class ProducteurImpotIFDTest extends ProducteurImpotTst {


    private FournisseurRegleImpotFederal constructeur = CTX_TST_CH.getFournisseurRegleImpotFederal();
    private Map<String,String> libelleImpotTaxe = new HashMap<>();

    public ProducteurImpotIFDTest() {
        libelleImpotTaxe.put("IBR","Impôt fédéral de base sur le revenu");
        libelleImpotTaxe.put("RI","rabais sur l'impôt de base");
    }

    private RecepteurMultipleImpot recepteur(String... codesImpot) {
        RecepteurMultipleImpot recepteur = new RecepteurMultipleImpot();
        for (String code : codesImpot) {
            recepteur.ajouteRecepteur(code, new RecepteurUniqueImpot(code));
        }
        recepteur.ajouteRecepteur("TOTAL",new RecepteurImpotSomme());
        return recepteur;
    }

    private BigDecimal getValeur(RecepteurMultipleImpot recepteur, String code) {
        if ("TOTAL".equals(code)) {
            return ((RecepteurImpotSomme)recepteur.getRecepteur(code)).getValeur();
        } else {
            Impot valeur = ((RecepteurUniqueImpot)recepteur.getRecepteur(code)).getValeur();
            if (null == valeur) return null;
            return valeur.getMontant();
        }
    }

    private void verifierMontantImpot(RecepteurMultipleImpot recepteur, String codeImpotTaxe, String montantAttendu) {
        assertThat(getValeur(recepteur, codeImpotTaxe)).isEqualTo(new BigDecimal(montantAttendu));
    }


    @Test
    public void produireImpotIFD() {
        ProducteurImpot prod = constructeur.getProducteurImpotsFederauxPP(2013);
        RecepteurMultipleImpot recepteur = recepteur("IBR","RI");
        prod.produireImpot(this.creerSituationFamilleAvecEnfant(annee(2013), 12), this.creerAssiettes(2013, 52400), recepteur);
        // Impôt de base : 256 - 251 (rabais enfant) = 5 francs < 25 francs --> 0
        verifierMontantImpot(recepteur,"IBR",  "0.00");
    }

    @Test
    public void produireImpotIFD2021() {
        ProducteurImpot prod = constructeur.getProducteurImpotsFederauxPP(2021);
        RecepteurMultipleImpot recepteur = recepteur("IBR","RI");
        prod.produireImpot(this.creerSituationFamilleAvecEnfant(annee(2021),16,13),
                this.creerAssiettes(2021, 120400, 105300), recepteur);
        verifierMontantImpot(recepteur,"IBR",  "2000.90");
    }

    @Test
    public void produireImpotCouple1EnfantJusteAuDessousDuSeuil() {
        int periode = 2025;
        ProducteurImpot prod = constructeur.getProducteurImpotsFederauxPP(periode);
        RecepteurMultipleImpot recepteur = recepteur("IBR","RI");
        prod.produireImpot(this.creerSituationFamilleAvecEnfant(annee(periode),16),
                this.creerAssiettes(periode, 56_000, 55_999), recepteur);
        verifierMontantImpot(recepteur,"IBR",  "0.00");
    }

    @Test
    public void produireImpotCouple1EnfantJusteAuDessusDuSeuil() {
        int periode = 2025;
        ProducteurImpot prod = constructeur.getProducteurImpotsFederauxPP(periode);
        RecepteurMultipleImpot recepteur = recepteur("IBR","RI");
        prod.produireImpot(this.creerSituationFamilleAvecEnfant(annee(periode),16),
                this.creerAssiettes(periode, 56_000, 56_000), recepteur);
        verifierMontantImpot(recepteur,"IBR",  "26.00");
    }

}
