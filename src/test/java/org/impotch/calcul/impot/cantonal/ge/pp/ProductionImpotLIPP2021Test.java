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

import org.impotch.calcul.impot.Impot;
import org.impotch.calcul.impot.taxation.pp.*;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.impotch.calcul.impot.cantonal.ge.ContexteTestGE.CTX_TST_GE;

public class ProductionImpotLIPP2021Test extends ProducteurImpotTst {
    private FournisseurRegleImpotCantonalGE fournisseur = CTX_TST_GE.getFournisseurRegleImpotCantonalGE();
    private Map<String, String> libelleImpotTaxe = new HashMap<>();

    public ProductionImpotLIPP2021Test() {
        libelleImpotTaxe.put("IBR", "Impôt cantonal de base sur le revenu");
        libelleImpotTaxe.put("RIBR", "réduction INN 111 sur l'impôt de base");
        libelleImpotTaxe.put("CAR", "centimes additionnels cantonaux sur le revenu");
        libelleImpotTaxe.put("RCAR", "réduction INN 111 sur les centimes additionnels cantonaux sur le revenu");
        libelleImpotTaxe.put("ADR", "centime additionnel cantonal sur le revenu - Aide à domicile");
        libelleImpotTaxe.put("PPR", "centimes additionnels communaux sur le revenu - Part privilégiée");
        libelleImpotTaxe.put("COR", "centimes additionnels communaux sur le revenu");
        libelleImpotTaxe.put("TOTAL", "Somme des impôts");
    }

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
            Impot valeur = ((RecepteurUniqueImpot) recepteur.getRecepteur(code)).getValeur();
            if (null == valeur) return null;
            return valeur.getMontant();
        }
    }

    private void verifierMontantImpot(RecepteurMultipleImpot recepteur, String codeImpotTaxe, String montantAttendu) {
        assertThat(getValeur(recepteur, codeImpotTaxe)).isEqualTo(new BigDecimal(montantAttendu));
    }

    private void verifierAbsenceImpot(RecepteurMultipleImpot recepteur, String codeImpotTaxe) {
        assertThat(getValeur(recepteur, codeImpotTaxe)).isNull();
    }

    @Test
    public void revenu() {
        ProducteurImpot producteur = fournisseur.getProducteurImpotsICCRevenu(2021);
        RecepteurMultipleImpot recepteur = recepteur("IBR", "RIBR", "CAR", "RCAR", "ADR", "PPR", "COR");
        // Test sur un montant impaire pour voir les effets d'arrondi.
        producteur.produireImpot(this.creerSituationFamilleAvecEnfant(16,13), this.creerAssiettes(2021, 103938, 88735), recepteur);
        verifierMontantImpot(recepteur, "IBR", "7298.85"); // dans un autre logiciel 7298.90
//        verifierMontantImpot(recepteur, "RIBR", "-3434.00");
//        verifierMontantImpot(recepteur, "CAR", "13592.85");
//        verifierMontantImpot(recepteur, "RCAR", "-1631.15");
//        verifierMontantImpot(recepteur, "ADR", "286.15");
//        verifierMontantImpot(recepteur, "PPR", "3515.55");
//        verifierMontantImpot(recepteur, "COR", "9504.95");
//        verifierMontantImpot(recepteur, "TOTAL", "50450.85");

    }
}
