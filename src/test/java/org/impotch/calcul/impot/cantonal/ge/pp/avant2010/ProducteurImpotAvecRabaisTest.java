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
package org.impotch.calcul.impot.cantonal.ge.pp.avant2010;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.impotch.calcul.impot.cantonal.ge.pp.FournisseurRegleImpotCantonalGE;
import org.impotch.calcul.impot.taxation.pp.FournisseurAssiettePeriodique;
import org.impotch.calcul.impot.taxation.pp.ProducteurImpot;
import org.impotch.calcul.impot.taxation.pp.RecepteurMultipleImpot;
import org.impotch.calcul.impot.taxation.pp.RecepteurUniqueImpot;
import org.impotch.calcul.impot.taxation.pp.SituationFamiliale;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/beansCH_GE.xml"})
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
public class ProducteurImpotAvecRabaisTest extends ProducteurImpotGEAvant2010 {

	@Resource(name = "fournisseurRegleImpotCantonalGE")
	private FournisseurRegleImpotCantonalGE fournisseur;


	private void testFamilleAvecEnfant(final int periodeFiscale, final int montantImposable, final String montantImpot , int montantDeterminantRabais, String montantRabaisImpot, final int...ageEnfant) {
		ProducteurImpot producteurGERevenu = fournisseur.getProducteurImpotsICCRevenu(periodeFiscale);
		SituationFamiliale situation = creerSituationFamilleAvecEnfant(ageEnfant);
		FournisseurAssiettePeriodique fournisseur = this.creerAssiettesAvecRabais(periodeFiscale, montantImposable, montantDeterminantRabais);
		RecepteurUniqueImpot recepteurIBR = new RecepteurUniqueImpot("IBR");
		RecepteurUniqueImpot recepteurRI = new RecepteurUniqueImpot("RI");
		
		RecepteurMultipleImpot recepteur = new RecepteurMultipleImpot();
		recepteur.ajouteRecepteur("IBR", recepteurIBR);
		recepteur.ajouteRecepteur("RI", recepteurRI);

		producteurGERevenu.produireImpot(situation, fournisseur, recepteur);
		assertEquals("Revenu de " + montantImposable,new BigDecimal(montantImpot),recepteurIBR.getValeur().getMontant());
		assertEquals("Revenu de " + montantImposable,new BigDecimal(montantRabaisImpot),recepteurRI.getValeur().getMontant());
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
		assertEquals("Revenu de " + montantImposable,new BigDecimal(montantImpot),recepteurIBR.getValeur().getMontant());
		assertEquals("Revenu de " + montantImposable,new BigDecimal(montantRabais),recepteurRI.getValeur().getMontant());
	}
	
	@Test
	public void testProduireImpotGERevenuFamilleAvecEnfant() {
		// Montant déterminant rabais = 2 * 15057 + 4 * 3559 + 1369 = 45719
		testFamilleAvecEnfant(2009,100000,"11114.15",45719,"-3768.55",10,6);
	}

	@Test
	public void testProduireImpotGERevenuCelibataireSansEnfant() {
		testCelibataireSansEnfant(2007,60661,"7036.55",15587,"-1065.75");
	}
	
}
