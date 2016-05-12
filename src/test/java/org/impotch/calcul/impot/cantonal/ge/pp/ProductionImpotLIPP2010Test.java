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


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.impotch.calcul.impot.Impot;
import org.impotch.calcul.impot.taxation.pp.ProducteurImpot;
import org.impotch.calcul.impot.taxation.pp.ProducteurImpotTst;
import org.impotch.calcul.impot.taxation.pp.RecepteurImpotSomme;
import org.impotch.calcul.impot.taxation.pp.RecepteurMultipleImpot;
import org.impotch.calcul.impot.taxation.pp.RecepteurUniqueImpot;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/beansCH_GE.xml", "/beans-test.xml"})
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
public class ProductionImpotLIPP2010Test extends ProducteurImpotTst {

	@Resource(name = "fournisseurRegleImpotCantonalGE")
	private FournisseurRegleImpotCantonalGE fournisseur;
	private Map<String,String> libelleImpotTaxe = new HashMap<String,String>();
	
	public ProductionImpotLIPP2010Test() {
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
	public void revenuCelibataire() {
		ProducteurImpot producteur = fournisseur.getProducteurImpotsICCRevenu(2010);
		// Un exemple simple : 100'000 francs de revenu
		RecepteurMultipleImpot recepteur = recepteur("IBR","RIBR","CAR","RCAR","ADR","PPR","COR");
		producteur.produireImpot(this.creerSituationCelibataireSansEnfant(), this.creerAssiettes(2010, 100000), recepteur);
		verifierMontantImpot(recepteur,"IBR",  "11632.45");
		verifierMontantImpot(recepteur,"RIBR", "-1395.90");
		verifierMontantImpot(recepteur,"CAR",   "5525.40");
		verifierMontantImpot(recepteur,"RCAR",  "-663.05");
		verifierMontantImpot(recepteur,"ADR",    "116.30");
		verifierMontantImpot(recepteur,"PPR",   "1429.05");
		verifierMontantImpot(recepteur,"COR",   "3863.70");
		verifierMontantImpot(recepteur,"TOTAL","20507.95");
		
		// Petit revenu, montant impôt = 0
		recepteur = recepteur("IBR","RIBR","CAR","RCAR","ADR","PPR","COR");
		producteur.produireImpot(this.creerSituationCelibataireSansEnfant(), this.creerAssiettes(2010, 12351), recepteur);
		verifierMontantImpot(recepteur,"IBR",  "0.00");
		verifierAbsenceImpot(recepteur,"RIBR");
		verifierAbsenceImpot(recepteur,"CAR");
		verifierAbsenceImpot(recepteur,"RCAR");
		verifierAbsenceImpot(recepteur,"ADR");
		verifierAbsenceImpot(recepteur,"PPR");
		verifierAbsenceImpot(recepteur,"COR");
		verifierMontantImpot(recepteur,"TOTAL","0.00");
		
		// Grand revenu (1'543'987'863) , supérieur à la dernière tranche
		recepteur = recepteur("IBR","RIBR","CAR","RCAR","ADR","PPR","COR");
		producteur.produireImpot(this.creerSituationCelibataireSansEnfant(), this.creerAssiettes(2010, 1543987863), recepteur);
		verifierMontantImpot(recepteur,"IBR",  "293343838.00");
		verifierMontantImpot(recepteur,"RIBR", "-35201260.55");
		verifierMontantImpot(recepteur,"CAR",  "139338323.05");
		verifierMontantImpot(recepteur,"RCAR", "-16720598.75");
		verifierMontantImpot(recepteur,"ADR",    "2933438.40");
		verifierMontantImpot(recepteur,"PPR",   "36037290.50");
		verifierMontantImpot(recepteur,"COR",   "97434155.80");
		verifierMontantImpot(recepteur,"TOTAL","517165186.45");
		
		// À la limite d'une tranche par dessous
		recepteur = recepteur("IBR");
		producteur.produireImpot(this.creerSituationCelibataireSansEnfant(), this.creerAssiettes(2010, 36955), recepteur);
		verifierMontantImpot(recepteur,"IBR",  "2103.30");
		// À la limite d'une tranche par dessus
		recepteur = recepteur("IBR");
		producteur.produireImpot(this.creerSituationCelibataireSansEnfant(), this.creerAssiettes(2010, 36956), recepteur);
		verifierMontantImpot(recepteur,"IBR",  "2103.45");
	}
	
	@Test
	public void revenuMarie() {
		ProducteurImpot producteur = fournisseur.getProducteurImpotsICCRevenu(2010);
		RecepteurMultipleImpot recepteur = recepteur("IBR","RIBR","CAR","RCAR","ADR","PPR","COR");
		// Test sur un montant impaire pour voir les effets d'arrondi.
		producteur.produireImpot(this.creerSituationFamilleAvecEnfant(11), this.creerAssiettes(2010, 234527), recepteur);
		verifierMontantImpot(recepteur,"IBR",  "28616.50");
		verifierMontantImpot(recepteur,"RIBR", "-3434.00");
		verifierMontantImpot(recepteur,"CAR",  "13592.85");
		verifierMontantImpot(recepteur,"RCAR", "-1631.15");
		verifierMontantImpot(recepteur,"ADR",    "286.15");
		verifierMontantImpot(recepteur,"PPR",   "3515.55");
		verifierMontantImpot(recepteur,"COR",   "9504.95");
		verifierMontantImpot(recepteur,"TOTAL","50450.85");
	}
	
	@Test
	public void problemeCalculetteRTax() {
		ProducteurImpot producteur = fournisseur.getProducteurImpotsICCRevenu(2010);
		RecepteurMultipleImpot recepteur = recepteur("IBR","RIBR","CAR","RCAR","ADR","PPR","COR");
		// Test sur un montant impaire pour voir les effets d'arrondi.
		producteur.produireImpot(this.creerSituationFamilleAvecEnfant(11), this.creerAssiettes(2010, 88935), recepteur);
		verifierMontantImpot(recepteur,"IBR",  "6342.85");
//		verifierMontantImpot(recepteur,"RIBR", "-3434.00");
//		verifierMontantImpot(recepteur,"CAR",  "13592.85");
//		verifierMontantImpot(recepteur,"RCAR", "-1631.15");
//		verifierMontantImpot(recepteur,"ADR",    "286.15");
//		verifierMontantImpot(recepteur,"PPR",   "3385.35");
//		verifierMontantImpot(recepteur,"COR",   "9635.15");
//		verifierMontantImpot(recepteur,"TOTAL","50450.85");
	}
	
	@Test
	public void fortune() {
		ProducteurImpot producteur = fournisseur.getProducteurImpotsICCFortune(2010);
		RecepteurMultipleImpot recepteur = recepteur("IBF","CAF","ADF","PPF","COF");
		producteur.produireImpot(this.creerSituationFamilleAvecEnfant(11), this.creerAssiettes(2010, 1000000), recepteur);
		verifierMontantImpot(recepteur,"IBF","2998.85");
		verifierMontantImpot(recepteur,"CAF","1424.45");
		verifierMontantImpot(recepteur,"ADF",  "30.00");
		verifierMontantImpot(recepteur,"PPF", "368.40");
		verifierMontantImpot(recepteur,"COF","996.05");
	}
	
	@Test
	public void fortuneSupplementaire() {
		ProducteurImpot producteur = fournisseur.getProducteurImpotsICCFortuneSupplementaire(2010);
		RecepteurMultipleImpot recepteur = recepteur("ISF");
		producteur.produireImpot(this.creerSituationFamilleAvecEnfant(11), this.creerAssiettes(2010, 1000000), recepteur);
		verifierMontantImpot(recepteur,"ISF","312.15");
	}
}
