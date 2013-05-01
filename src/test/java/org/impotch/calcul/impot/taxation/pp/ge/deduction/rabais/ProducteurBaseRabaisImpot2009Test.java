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
package org.impotch.calcul.impot.taxation.pp.ge.deduction.rabais;


import java.math.BigDecimal;

import org.junit.Test;

import static org.junit.Assert.*;


public class ProducteurBaseRabaisImpot2009Test extends AbstractTestProducteurBaseRabaisImpot {

	private ProducteurBaseRabaisImpot producteur;

	public ProducteurBaseRabaisImpot2009Test() {
		super();
		producteur = super.construireProducteur(2009, 15057, 3640, 52000, 5200, 3559, 1369);
	}
	
	@Test
	public void celibataireSansCharge() {
		// Barème source A0
		assertEquals("célibataire sans charge",new BigDecimal("16426"),producteur.produireMontantDeterminantRabais(creerCelibataireSansCharge(),this.creerFournisseurMontant()));
	}
	
	@Test
	public void celibataireAvecCharge() {
		assertEquals("célibataire avec 1 charge moins de 12 ans",new BigDecimal("39970"),producteur.produireMontantDeterminantRabais(creerCelibataireAvecCharge(1,true),this.creerFournisseurMontant()));
	}
	
	@Test
	public void coupleSansDoubleActiviteSansCharge() {
		// Barème source B0
		assertEquals("couple sans double activité et sans charge",new BigDecimal("30114"),producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteSansCharge(),this.creerFournisseurMontant()));	
	}
	
	@Test
	public void coupleSansDoubleActiviteAvecEnfant() {
		// Barème source B1 B2 B3
		assertEquals("couple avec 1 grand enfant non domicilié GE",new BigDecimal("37232"),producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(1,false,false),this.creerFournisseurMontant()));
		assertEquals("couple avec 1 petit enfant non domicilié GE",new BigDecimal("38601"),producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(1,true,false),this.creerFournisseurMontant()));
		assertEquals("couple avec 1 petit enfant domicilié GE",new BigDecimal("39970"),producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(1,true,true),this.creerFournisseurMontant()));

		// Barème source B4 B5 B6
		assertEquals("couple avec 2 grand enfants non domicilié GE",new BigDecimal("44350"),producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(2,false,false),this.creerFournisseurMontant()));
		assertEquals("couple avec 2 enfants dont au moins 1 petit non domicilié GE",new BigDecimal("45719"),producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(2,true,false),this.creerFournisseurMontant()));
		assertEquals("couple avec 2 enfants dont au moins 1 petit domicilié GE",new BigDecimal("47088"),producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(2,true,true),this.creerFournisseurMontant()));

		// Barème source B7 B8 B9
		assertEquals("couple avec 3 grand enfants non domicilié GE",new BigDecimal("51468"),producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(3,false,false),this.creerFournisseurMontant()));
		assertEquals("couple avec 3 enfants dont au moins 1 petit non domicilié GE",new BigDecimal("52837"),producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(3,true,false),this.creerFournisseurMontant()));
		assertEquals("couple avec 3 enfants dont au moins 1 petit domicilié GE",new BigDecimal("54206"),producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(3,true,true),this.creerFournisseurMontant()));

		// Barème source B10 B11 B12
		assertEquals("couple avec 4 grand enfants non domicilié GE",new BigDecimal("58586"),producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(4,false,false),this.creerFournisseurMontant()));
		assertEquals("couple avec 4 enfants dont au moins 1 petit non domicilié GE",new BigDecimal("59955"),producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(4,true,false),this.creerFournisseurMontant()));
		assertEquals("couple avec 4 enfants dont au moins 1 petit domicilié GE",new BigDecimal("61324"),producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(4,true,true),this.creerFournisseurMontant()));

		// Barème source B13 B14 B15
		assertEquals("couple avec 5 grand enfants non domicilié GE",new BigDecimal("65704"),producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(5,false,false),this.creerFournisseurMontant()));
		assertEquals("couple avec 5 enfants dont au moins 1 petit non domicilié GE",new BigDecimal("67073"),producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(5,true,false),this.creerFournisseurMontant()));
		assertEquals("couple avec 5 enfants dont au moins 1 petit domicilié GE",new BigDecimal("68442"),producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(5,true,true),this.creerFournisseurMontant()));
	}
	
	@Test
	public void coupleDontUnFonctionnaireInternational() {
		// Barème source I0
		assertEquals("couple dont un fonctionnaire international sans charge",new BigDecimal("15057"),producteur.produireMontantDeterminantRabais(creerCoupleDontUnFonctionnaireInternational(0,false),this.creerFournisseurMontant()));
		// Barème source I1 I2
		assertEquals("couple avec 1 grand enfant",new BigDecimal("18616"),producteur.produireMontantDeterminantRabais(creerCoupleDontUnFonctionnaireInternational(1,false),this.creerFournisseurMontant()));
		assertEquals("couple avec 1 petit enfant",new BigDecimal("19985"),producteur.produireMontantDeterminantRabais(creerCoupleDontUnFonctionnaireInternational(1,true),this.creerFournisseurMontant()));
		// Barème source I3 I4
		assertEquals("couple avec 2 grand enfant",new BigDecimal("22175"),producteur.produireMontantDeterminantRabais(creerCoupleDontUnFonctionnaireInternational(2,false),this.creerFournisseurMontant()));
		assertEquals("couple avec 2 enfant dont au moins 1 petit",new BigDecimal("23544"),producteur.produireMontantDeterminantRabais(creerCoupleDontUnFonctionnaireInternational(2,true),this.creerFournisseurMontant()));
		// Barème source I5 I6
		assertEquals("couple avec 3 grand enfant",new BigDecimal("25734"),producteur.produireMontantDeterminantRabais(creerCoupleDontUnFonctionnaireInternational(3,false),this.creerFournisseurMontant()));
		assertEquals("couple avec 3 enfant dont au moins 1 petit",new BigDecimal("27103"),producteur.produireMontantDeterminantRabais(creerCoupleDontUnFonctionnaireInternational(3,true),this.creerFournisseurMontant()));
		// Barème source I7 I8
		assertEquals("couple avec 4 grand enfant",new BigDecimal("29293"),producteur.produireMontantDeterminantRabais(creerCoupleDontUnFonctionnaireInternational(4,false),this.creerFournisseurMontant()));
		assertEquals("couple avec 4 enfant dont au moins 1 petit",new BigDecimal("30662"),producteur.produireMontantDeterminantRabais(creerCoupleDontUnFonctionnaireInternational(4,true),this.creerFournisseurMontant()));
	}

}
