/**
 * This file is part of CalculImpotCH.
 *
 * CalculImpotCH is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * CalculImpotCH is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CalculImpotCH.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.impotch.calcul.impot.cantonal.ge.pp.avant2010;


import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.impotch.calcul.impot.cantonal.ge.pp.FournisseurRegleImpotCantonalGE;
import org.impotch.calcul.impot.taxation.pp.FournisseurAssiettePeriodique;
import org.impotch.calcul.impot.taxation.pp.ProducteurImpot;
import org.impotch.calcul.impot.taxation.pp.RecepteurImpotSomme;
import org.impotch.calcul.impot.taxation.pp.RecepteurMultipleImpot;
import org.impotch.calcul.impot.taxation.pp.RecepteurUniqueImpot;
import org.impotch.calcul.impot.taxation.pp.SituationFamiliale;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/beans.xml")
public class ProducteurImpotGETest extends ProducteurImpotGEAvant2010 {

	@Resource(name = "fournisseurRegleImpotCantonalGE")
	private FournisseurRegleImpotCantonalGE fournisseur;

	final Logger logger = LoggerFactory.getLogger(ProducteurImpotGETest.class);
	
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
			return ((RecepteurUniqueImpot)recepteur.getRecepteur(code)).getValeur().getMontant();
		}
	}
	
	@Test
	public void testRevenu2009() {
		int periodeFiscale = 2009;
		int montantImposable = 100000;
		ProducteurImpot producteurGERevenu = fournisseur.getProducteurImpotsICCRevenu(2009);
		SituationFamiliale situation = creerSituationCelibataireSansEnfant();
		
		FournisseurAssiettePeriodique fournisseurAssiette = this.creerAssiettesAvecRabais(periodeFiscale, montantImposable, 16426);

		RecepteurMultipleImpot recepteur = recepteur("IBR","RI","RIBR","CAR","RCAR","ADR","PPR","COR");
		
		producteurGERevenu.produireImpot(situation, fournisseurAssiette, recepteur);
		assertEquals("IBR : Impôt de base sur le revenu",new BigDecimal("12821.20"),getValeur(recepteur,"IBR"));
		// TODO PGI
		//logger.debug("Impôt de base sur le revenu\t" + )
		assertEquals("RI : rabais d'impôt",new BigDecimal("-1140.75"),getValeur(recepteur,"RI"));
		assertEquals("RIBR : réduction INN 111 sur l'impôt de base",new BigDecimal("-1401.65"),getValeur(recepteur,"RIBR"));
		assertEquals("CAR : centimes additionnels cantonaux sur le revenu",new BigDecimal("5548.20"),getValeur(recepteur,"CAR"));
		assertEquals("RCAR : réduction INN 111 sur les centimes additionnels cantonaux sur le revenu",new BigDecimal("-665.80"),getValeur(recepteur,"RCAR"));
		assertEquals("ADR : centime additionnel cantonal sur le revenu - Aide à domicile",new BigDecimal("116.80"),getValeur(recepteur,"ADR"));
		// La part privilégiée de Genève est de 0.26, le taux de cts de 0.455
		assertEquals("PPR : centimes additionnels communaux sur le revenu - Part privilégiée",new BigDecimal("1381.80"),getValeur(recepteur,"PPR"));
		assertEquals("COR : centimes additionnels communaux sur le revenu",new BigDecimal("3932.80"),getValeur(recepteur,"COR"));
		
		assertEquals("Somme",new BigDecimal("20592.60"),getValeur(recepteur,"TOTAL"));
	}
	
	@Test
	public void fortune2007() {
		int periodeFiscale = 2007;
		int montantImposable = 293000;
		SituationFamiliale situation = creerSituationCelibataireSansEnfant();
		FournisseurAssiettePeriodique fournisseurAssiette = this.creerAssiettes(periodeFiscale, montantImposable);

		RecepteurMultipleImpot recepteur = recepteur("IBF","CAF","ADF","PPF","COF");

		ProducteurImpot producteurGE = fournisseur.getProducteurImpotsICCFortune(periodeFiscale);
		producteurGE.produireImpot(situation, fournisseurAssiette, recepteur);
		assertEquals("IBF : Impôt de base sur la fortune",new BigDecimal("643.25"),getValeur(recepteur,"IBF"));
		assertEquals("CAF : centimes additionnels cantonaux sur la fortune",new BigDecimal("305.55"),getValeur(recepteur,"CAF"));
		assertEquals("ADF : centime additionnel cantonal sur la fortune - Aide à domicile",new BigDecimal("6.45"),getValeur(recepteur,"ADF"));
		// La part privilégiée de Genève est de 0.26, le taux de cts de 0.455
		assertEquals("PPF : centimes additionnels communaux sur la fortune - Part privilégiée",new BigDecimal("76.10"),getValeur(recepteur,"PPF"));
		assertEquals("COF : centimes additionnels communaux sur la fortune",new BigDecimal("216.60"),getValeur(recepteur,"COF"));
		
		assertEquals("Somme",new BigDecimal("1247.95"),getValeur(recepteur,"TOTAL"));
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
		assertEquals("ISF : Impôt supplémentaire sur la fortune",new BigDecimal("22.70"),recepteurISF.getValeur().getMontant());
	}
}
