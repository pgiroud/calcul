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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/beansCH_GE.xml","/beansAssurancesSociales.xml" , "/beans-test.xml"})
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
public class ProducteurImpotLIPP2013Test extends ProducteurImpotTst {

    @Resource(name = "fournisseurRegleImpotCantonalGE")
    private FournisseurRegleImpotCantonalGE fournisseur;
    private Map<String,String> libelleImpotTaxe = new HashMap<String,String>();

    public ProducteurImpotLIPP2013Test() {
        libelleImpotTaxe.put("IBR","Impôt cantonal de base sur le revenu");
        libelleImpotTaxe.put("RIBR","réduction INN 111 sur l'impôt de base");
        libelleImpotTaxe.put("CAR","centimes additionnels cantonaux sur le revenu");
        libelleImpotTaxe.put("RCAR","réduction INN 111 sur les centimes additionnels cantonaux sur le revenu");
        libelleImpotTaxe.put("ADR","centime additionnel cantonal sur le revenu - Aide à domicile");
        libelleImpotTaxe.put("PPR","centimes additionnels communaux sur le revenu - Part privilégiée");
        libelleImpotTaxe.put("COR","centimes additionnels communaux sur le revenu");
        libelleImpotTaxe.put("TOTAL","Somme des impôts");
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
        assertEquals(codeImpotTaxe + " : " + libelleImpotTaxe.get(codeImpotTaxe),new BigDecimal(montantAttendu),getValeur(recepteur,codeImpotTaxe));
    }

    private void verifierAbsenceImpot(RecepteurMultipleImpot recepteur, String codeImpotTaxe) {
        assertNull(codeImpotTaxe + " : " + libelleImpotTaxe.get(codeImpotTaxe),getValeur(recepteur,codeImpotTaxe));
    }

    @Test
    public void faibleRevenuCelibataire() {
        ProducteurImpot producteur = fournisseur.getProducteurImpotsICCRevenu(2013);
        // Un exemple simple : 17700 francs de revenu
        RecepteurMultipleImpot recepteur = recepteur("IBR","RIBR","CAR","RCAR","ADR","PPR","COR");
        producteur.produireImpot(this.creerSituationCelibataireSansEnfant(), this.creerAssiettes(2013, 17700), recepteur);
        verifierMontantImpot(recepteur,"IBR",  "5.70");
        verifierMontantImpot(recepteur,"RIBR", "-0.70");
        verifierMontantImpot(recepteur,"CAR",   "2.70");
        verifierMontantImpot(recepteur,"RCAR",  "-0.30");
        verifierMontantImpot(recepteur,"ADR",    "0.05");
        verifierMontantImpot(recepteur,"PPR",   "0.50");
        verifierMontantImpot(recepteur,"COR",   "2.00");
        verifierMontantImpot(recepteur,"TOTAL","9.95");
    }

    @Test
    public void revenuPlusQueConfortable() {
        ProducteurImpot producteur = fournisseur.getProducteurImpotsICCRevenu(2013);
        RecepteurMultipleImpot recepteur = recepteur("IBR","RIBR","CAR","RCAR","ADR","PPR","COR");
        producteur.produireImpot(this.creerSituationCelibataireSansEnfant(), this.creerAssiettes(2013, 500000), recepteur);
        verifierMontantImpot(recepteur,"IBR",  "81632.50");
    }
}
