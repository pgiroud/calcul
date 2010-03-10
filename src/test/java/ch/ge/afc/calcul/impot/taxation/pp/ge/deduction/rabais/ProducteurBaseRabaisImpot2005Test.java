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
package ch.ge.afc.calcul.impot.taxation.pp.ge.deduction.rabais;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import ch.ge.afc.calcul.impot.taxation.pp.ge.deduction.rabais.ProducteurBaseRabaisImpot;


/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class ProducteurBaseRabaisImpot2005Test extends AbstractTestProducteurBaseRabaisImpot {
	private ProducteurBaseRabaisImpot producteur;

	public ProducteurBaseRabaisImpot2005Test() {
		super();
		producteur = super.construireProducteur(2005, 14288, 3640, 52000, 5200, 3377, 1299);
	}

	@Test
	public void celibataireSansCharge() {
		// Barème source A0
		assertEquals("célibataire sans charge",new BigDecimal("15587"),producteur.produireMontantDeterminantRabais(creerCelibataireSansCharge(),this.creerFournisseurMontant()));
	}

	@Test
	public void coupleSansDoubleActiviteSansCharge() {
		// Barème source B0
		assertEquals("couple sans double activité et sans charge",new BigDecimal("28576"),producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteSansCharge(),this.creerFournisseurMontant())); 
	}
	
	@Test
	public void coupleSansDoubleActiviteAvecEnfant() {
		// Barème source B1 B2 B3
		assertEquals("couple avec 1 grand enfant non domicilié GE",new BigDecimal("35330"),producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(1,false,false),this.creerFournisseurMontant()));
		assertEquals("couple avec 1 petit enfant non domicilié GE",new BigDecimal("36629"),producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(1,true,false),this.creerFournisseurMontant()));
		assertEquals("couple avec 1 petit enfant domicilié GE",new BigDecimal("37928"),producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(1,true,true),this.creerFournisseurMontant()));

		// Barème source B4 B5 B6
		assertEquals("couple avec 2 grand enfants non domicilié GE",new BigDecimal("42084"),producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(2,false,false),this.creerFournisseurMontant()));
		assertEquals("couple avec 2 enfants dont au moins 1 petit non domicilié GE",new BigDecimal("43383"),producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(2,true,false),this.creerFournisseurMontant()));
		assertEquals("couple avec 2 enfants dont au moins 1 petit domicilié GE",new BigDecimal("44682"),producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(2,true,true),this.creerFournisseurMontant()));

		// Barème source B7 B8 B9
		assertEquals("couple avec 3 grand enfants non domicilié GE",new BigDecimal("48838"),producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(3,false,false),this.creerFournisseurMontant()));
		assertEquals("couple avec 3 enfants dont au moins 1 petit non domicilié GE",new BigDecimal("50137"),producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(3,true,false),this.creerFournisseurMontant()));
		assertEquals("couple avec 3 enfants dont au moins 1 petit domicilié GE",new BigDecimal("51436"),producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(3,true,true),this.creerFournisseurMontant()));

		// Barème source B10 B11 B12
		assertEquals("couple avec 4 grand enfants non domicilié GE",new BigDecimal("55592"),producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(4,false,false),this.creerFournisseurMontant()));
		assertEquals("couple avec 4 enfants dont au moins 1 petit non domicilié GE",new BigDecimal("56891"),producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(4,true,false),this.creerFournisseurMontant()));
		assertEquals("couple avec 4 enfants dont au moins 1 petit domicilié GE",new BigDecimal("58190"),producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(4,true,true),this.creerFournisseurMontant()));

		// Barème source B13 B14 B15
		assertEquals("couple avec 5 grand enfants non domicilié GE",new BigDecimal("62346"),producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(5,false,false),this.creerFournisseurMontant()));
		assertEquals("couple avec 5 enfants dont au moins 1 petit non domicilié GE",new BigDecimal("63645"),producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(5,true,false),this.creerFournisseurMontant()));
		assertEquals("couple avec 5 enfants dont au moins 1 petit domicilié GE",new BigDecimal("64944"),producteur.produireMontantDeterminantRabais(creerCoupleSansDoubleActiviteAvecEnfant(5,true,true),this.creerFournisseurMontant()));
	}

	@Test
	public void coupleDontUnFonctionnaireInternational() {
		// Barème source I0
		assertEquals("couple dont un fonctionnaire international sans charge",new BigDecimal("14288"),producteur.produireMontantDeterminantRabais(creerCoupleDontUnFonctionnaireInternational(0,false),this.creerFournisseurMontant()));
		// Barème source I1 I2
		assertEquals("couple avec 1 grand enfant",new BigDecimal("17665"),producteur.produireMontantDeterminantRabais(creerCoupleDontUnFonctionnaireInternational(1,false),this.creerFournisseurMontant()));
		assertEquals("couple avec 1 petit enfant",new BigDecimal("18964"),producteur.produireMontantDeterminantRabais(creerCoupleDontUnFonctionnaireInternational(1,true),this.creerFournisseurMontant()));
		// Barème source I3 I4
		assertEquals("couple avec 2 grand enfant",new BigDecimal("21042"),producteur.produireMontantDeterminantRabais(creerCoupleDontUnFonctionnaireInternational(2,false),this.creerFournisseurMontant()));
		assertEquals("couple avec 2 enfant dont au moins 1 petit",new BigDecimal("22341"),producteur.produireMontantDeterminantRabais(creerCoupleDontUnFonctionnaireInternational(2,true),this.creerFournisseurMontant()));
		// Barème source I5 I6
		assertEquals("couple avec 3 grand enfant",new BigDecimal("24419"),producteur.produireMontantDeterminantRabais(creerCoupleDontUnFonctionnaireInternational(3,false),this.creerFournisseurMontant()));
		assertEquals("couple avec 3 enfant dont au moins 1 petit",new BigDecimal("25718"),producteur.produireMontantDeterminantRabais(creerCoupleDontUnFonctionnaireInternational(3,true),this.creerFournisseurMontant()));
		// Barème source I7 I8
		assertEquals("couple avec 4 grand enfant",new BigDecimal("27796"),producteur.produireMontantDeterminantRabais(creerCoupleDontUnFonctionnaireInternational(4,false),this.creerFournisseurMontant()));
		assertEquals("couple avec 4 enfant dont au moins 1 petit",new BigDecimal("29095"),producteur.produireMontantDeterminantRabais(creerCoupleDontUnFonctionnaireInternational(4,true),this.creerFournisseurMontant()));
	}

}
