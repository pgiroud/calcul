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
package org.impotch.calcul.impot.cantonal.fr.pp;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.impotch.calcul.impot.PeriodeFiscale;
import org.junit.jupiter.api.Test;

import org.impotch.calcul.impot.Impot;
import org.impotch.calcul.impot.taxation.pp.FournisseurAssiettePeriodique;
import org.impotch.calcul.impot.taxation.pp.ProducteurImpot;
import org.impotch.calcul.impot.taxation.pp.ProducteurImpotTst;
import org.impotch.calcul.impot.taxation.pp.RecepteurImpotSomme;
import org.impotch.calcul.impot.taxation.pp.RecepteurMultipleImpot;
import org.impotch.calcul.impot.taxation.pp.RecepteurUniqueImpot;
import org.impotch.calcul.lieu.ICommuneSuisse;

import static org.impotch.calcul.impot.ConstructeurAssiettesCommunales.unConstructeurAssiettesCommunales;
import static org.impotch.calcul.impot.taxation.pp.ConstructeurAssiettePeriodique.unConstructeurAssiette;

import static org.assertj.core.api.Assertions.assertThat;
import static org.impotch.calcul.impot.cantonal.fr.ContexteTestCH_FR.CTX_TST_CH_FR;
public class ProducteurImpotRevenu2007Test extends ProducteurImpotTst {

	private static final PeriodeFiscale PERIODE_FISCALE = PeriodeFiscale.annee(2007);

	private FournisseurRegleImpotCantonalFR fournisseur = CTX_TST_CH_FR.getFournisseurRegleImpotCantonalFR();
	private Map<String,String> libelleImpotTaxe = new HashMap<String,String>();

	public ProducteurImpotRevenu2007Test() {
		libelleImpotTaxe.put("RCAN","Impôt cantonal de base sur le revenu");
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
		assertThat(getValeur(recepteur,codeImpotTaxe)).isEqualTo(new BigDecimal(montantAttendu));
//		assertEquals(codeImpotTaxe + " : " + libelleImpotTaxe.get(codeImpotTaxe),new BigDecimal(montantAttendu),getValeur(recepteur,codeImpotTaxe));
	}
	
	private void verifierAbsenceImpot(RecepteurMultipleImpot recepteur, String codeImpotTaxe) {
		assertThat(getValeur(recepteur,codeImpotTaxe)).isNull();
//		assertNull(codeImpotTaxe + " : " + libelleImpotTaxe.get(codeImpotTaxe),getValeur(recepteur,codeImpotTaxe));
	}
	
	@Test
	public void permierTest() {
		ProducteurImpot producteur = fournisseur.getProducteurImpotsICRevenu(2007);
		RecepteurMultipleImpot recepteur = recepteur("RCAN");
		
		producteur.produireImpot(this.creerSituationFamilleAvecEnfant(PERIODE_FISCALE,11,13), this.creerAssiettes(2007, 91256, 92352,fournisseurLieu.getCommune(2321)), recepteur);
		verifierMontantImpot(recepteur,"RCAN",  "7721.00");
	}
	
	@Test
	public void tauxMinimum() {
		ProducteurImpot producteur = fournisseur.getProducteurImpotsICRevenu(2007);
		RecepteurMultipleImpot recepteur = recepteur("RCAN");
		
		producteur.produireImpot(this.creerSituationFamilleAvecEnfant(PERIODE_FISCALE,11,13), this.creerAssiettes(2007, 5500, 5500,fournisseurLieu.getCommune(2321)), recepteur);
		verifierMontantImpot(recepteur,"RCAN",  "55.00");
	}
	
	private FournisseurAssiettePeriodique creerAssiettes(final int annee, final int montantImposable, final int montantDeterminant, final ICommuneSuisse commune) {
		return unConstructeurAssiette(annee)
				.imposable(montantImposable)
				.determinant(montantDeterminant)
				.uniqueCommune(commune)
				.fournir();
	}
	
}
