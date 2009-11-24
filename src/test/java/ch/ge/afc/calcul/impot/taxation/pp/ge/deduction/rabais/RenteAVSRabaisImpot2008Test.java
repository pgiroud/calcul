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

import ch.ge.afc.calcul.assurancessociales.SituationAVS;
import ch.ge.afc.calcul.assurancessociales.StatutAVS;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class RenteAVSRabaisImpot2008Test extends
		AbstractTestProducteurBaseRabaisImpot {
	private ProducteurBaseRabaisImpot producteur2008;

	public RenteAVSRabaisImpot2008Test() {
		super();
		producteur2008 = super.construireProducteur(2008, 14288, 3640, 52000, 5200, 3377, 1299);
	}
	
	@Test
	public void montantAdditionnelRenteSimpleAVSAI() {
		assertEquals("Exemple doc GE TAX",new BigDecimal("13260.00"),producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.SIMPLE,false,0),new BigDecimal("100000"),new BigDecimal("21500"))));
		assertEquals("Exemple doc GE TAX",new BigDecimal("10608.00"),producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.SIMPLE,false,0),new BigDecimal("100000"),new BigDecimal("40000"))));
		assertEquals("Exemple doc GE TAX",new BigDecimal("10608.00"),producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.SIMPLE,false,0),new BigDecimal("100000"),new BigDecimal("53040"))));
		assertEquals("Exemple doc GE TAX",new BigDecimal("7956.00"),producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.SIMPLE,false,0),new BigDecimal("100000"),new BigDecimal("53041"))));
		assertEquals("Exemple doc GE TAX",new BigDecimal("7956.00"),producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.SIMPLE,false,0),new BigDecimal("100000"),new BigDecimal("79560"))));
		assertEquals("Exemple doc GE TAX",new BigDecimal("0.00"),producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.SIMPLE,false,0),new BigDecimal("100000"),new BigDecimal("79561"))));
	}
	
	@Test
	public void montantAdditionnelRenteCoupleAVSAI() {
		assertEquals("Exemple doc GE TAX",new BigDecimal("19890.00"),producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.COUPLE,false,0),new BigDecimal("100000"),new BigDecimal("21500"))));
		assertEquals("Exemple doc GE TAX",new BigDecimal("15912.00"),producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.COUPLE,false,0),new BigDecimal("100000"),new BigDecimal("60000"))));
		assertEquals("Exemple doc GE TAX",new BigDecimal("15912.00"),producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.COUPLE,false,0),new BigDecimal("100000"),new BigDecimal("79560"))));
		assertEquals("Exemple doc GE TAX",new BigDecimal("11934.00"),producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.COUPLE,false,0),new BigDecimal("100000"),new BigDecimal("79561"))));
		assertEquals("Exemple doc GE TAX",new BigDecimal("11934.00"),producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.COUPLE,false,0),new BigDecimal("100000"),new BigDecimal("119340"))));
		assertEquals("Exemple doc GE TAX",new BigDecimal("0.00"),producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.COUPLE,false,0),new BigDecimal("100000"),new BigDecimal("119341"))));
		assertEquals("Exemple doc GE TAX",new BigDecimal("0.00"),producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.COUPLE,false,0),new BigDecimal("100000"),new BigDecimal("200000"))));
	}
	
	@Test
	public void montantAdditionnelRenteSimplePlusComplementaireEpouseAVSAI() {
		assertEquals("Exemple doc GE TAX",new BigDecimal("17238.00"),producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.SIMPLE,true,0),new BigDecimal("100000"),new BigDecimal("20000"))));
		assertEquals("Exemple doc GE TAX",new BigDecimal("13790.00"),producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.SIMPLE,true,0),new BigDecimal("100000"),new BigDecimal("60000"))));
		assertEquals("Exemple doc GE TAX",new BigDecimal("13790.00"),producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.SIMPLE,true,0),new BigDecimal("100000"),new BigDecimal("68952"))));
		assertEquals("Exemple doc GE TAX",new BigDecimal("10343.00"),producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.SIMPLE,true,0),new BigDecimal("100000"),new BigDecimal("68953"))));
		assertEquals("Exemple doc GE TAX",new BigDecimal("10343.00"),producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.SIMPLE,true,0),new BigDecimal("100000"),new BigDecimal("103428"))));
		assertEquals("Exemple doc GE TAX",new BigDecimal("0.00"),producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.SIMPLE,true,0),new BigDecimal("100000"),new BigDecimal("103429"))));
		assertEquals("Exemple doc GE TAX",new BigDecimal("0.00"),producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.SIMPLE,true,0),new BigDecimal("100000"),new BigDecimal("200000"))));
	}

	@Test
	public void montantAdditionnelRenteVeufAVSAI() {
		assertEquals("Exemple doc GE TAX",new BigDecimal("10608.00"),producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.VEUF,false,0),new BigDecimal("100000"),new BigDecimal("20000"))));
		assertEquals("Exemple doc GE TAX",new BigDecimal("8486.00"),producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.VEUF,false,0),new BigDecimal("100000"),new BigDecimal("40000"))));
		assertEquals("Exemple doc GE TAX",new BigDecimal("8486.00"),producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.VEUF,false,0),new BigDecimal("100000"),new BigDecimal("42432"))));
		assertEquals("Exemple doc GE TAX",new BigDecimal("6365.00"),producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.VEUF,false,0),new BigDecimal("100000"),new BigDecimal("42433"))));
		assertEquals("Exemple doc GE TAX",new BigDecimal("6365.00"),producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.VEUF,false,0),new BigDecimal("100000"),new BigDecimal("63648"))));
		assertEquals("Exemple doc GE TAX",new BigDecimal("0.00"),producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.VEUF,false,0),new BigDecimal("100000"),new BigDecimal("63649"))));
		assertEquals("Exemple doc GE TAX",new BigDecimal("0.00"),producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.VEUF,false,0),new BigDecimal("100000"),new BigDecimal("200000"))));
	}

	@Test
	public void montantAdditionnelRenteOrphelinSimple() {
		assertEquals("Exemple doc GE TAX",new BigDecimal("5304.00"),producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.ORPHELIN_SIMPLE,false,0),new BigDecimal("100000"),new BigDecimal("10000"))));
		assertEquals("Exemple doc GE TAX",new BigDecimal("4243.00"),producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.ORPHELIN_SIMPLE,false,0),new BigDecimal("100000"),new BigDecimal("20000"))));
		assertEquals("Exemple doc GE TAX",new BigDecimal("4243.00"),producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.ORPHELIN_SIMPLE,false,0),new BigDecimal("100000"),new BigDecimal("21216"))));
		assertEquals("Exemple doc GE TAX",new BigDecimal("3182.00"),producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.ORPHELIN_SIMPLE,false,0),new BigDecimal("100000"),new BigDecimal("21217"))));
		assertEquals("Exemple doc GE TAX",new BigDecimal("3182.00"),producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.ORPHELIN_SIMPLE,false,0),new BigDecimal("100000"),new BigDecimal("31824"))));
		assertEquals("Exemple doc GE TAX",new BigDecimal("0.00"),producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.ORPHELIN_SIMPLE,false,0),new BigDecimal("100000"),new BigDecimal("31825"))));
		assertEquals("Exemple doc GE TAX",new BigDecimal("0.00"),producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.ORPHELIN_SIMPLE,false,0),new BigDecimal("100000"),new BigDecimal("200000"))));
	}

	@Test
	public void montantAdditionnelRenteOrphelinDouble() {
		assertEquals("Exemple doc GE TAX",new BigDecimal("7956.00"),producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.ORPHELIN_DOUBLE,false,0),new BigDecimal("100000"),new BigDecimal("10000"))));
		assertEquals("Exemple doc GE TAX",new BigDecimal("6365.00"),producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.ORPHELIN_DOUBLE,false,0),new BigDecimal("100000"),new BigDecimal("25000"))));
		assertEquals("Exemple doc GE TAX",new BigDecimal("6365.00"),producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.ORPHELIN_DOUBLE,false,0),new BigDecimal("100000"),new BigDecimal("31824"))));
		assertEquals("Exemple doc GE TAX",new BigDecimal("4774.00"),producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.ORPHELIN_DOUBLE,false,0),new BigDecimal("100000"),new BigDecimal("31825"))));
		assertEquals("Exemple doc GE TAX",new BigDecimal("4774.00"),producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.ORPHELIN_DOUBLE,false,0),new BigDecimal("100000"),new BigDecimal("47736"))));
		assertEquals("Exemple doc GE TAX",new BigDecimal("0.00"),producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.ORPHELIN_DOUBLE,false,0),new BigDecimal("100000"),new BigDecimal("47737"))));
		assertEquals("Exemple doc GE TAX",new BigDecimal("0.00"),producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.ORPHELIN_DOUBLE,false,0),new BigDecimal("100000"),new BigDecimal("200000"))));
	}
	
}
