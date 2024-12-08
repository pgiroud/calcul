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
package org.impotch.calcul.impot;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Impôt produit par une des règles de calcul.
 * 
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class ImpotProduit implements Impot {
	
	/**************************************************/
    /****************** Attributs *********************/
    /**************************************************/

	private final BigDecimal montant;
	private final String typeImpot; 
	
	private String codeBeneficiaire;
	private BigDecimal baseCalcul;
	private BigDecimal tauxEffectif;
	private String explicationCalcul;
	private String explicationDetailleeCalcul;
	
	/**************************************************/
    /*************** Constructeurs ********************/
    /**************************************************/

	/**
	 * Construit l'impôt en fournissant obligatoirement un type d'impôt et
	 * un montant.
	 * 
	 */
	private ImpotProduit(String typeImpot, BigDecimal montant) {
		this.typeImpot = typeImpot;
		this.montant = montant;
	}
	
	/**************************************************/
    /******* Accesseurs / Mutateurs *******************/
    /**************************************************/
		
	/**
	 * Précise le code du bénéficiaire de l'impôt.
	 * @param codeBeneficiaire Le code du bénéficiaire.
	 */
	private void setCodeBeneficiaire(String codeBeneficiaire) {
		this.codeBeneficiaire = codeBeneficiaire;
	}

	/**
	 * Précise la base de calcul ayant déterminé l'impôt.
	 * @param baseCalcul la base de calcul.
	 */
	private void setBaseCalcul(BigDecimal baseCalcul) {
		this.baseCalcul = baseCalcul;
	}

	/**
	 * Indique le taux effectif de l'impôt. Si cette méthode n'est pas appelée,
	 * le taux effectif fournit sera recalculé en fonction du montant d'impôt et de la base
	 * de calcul.
	 * @param tauxEffectif le taux effectif.
	 */
	private void setTauxEffectif(BigDecimal tauxEffectif) {
		this.tauxEffectif = tauxEffectif;
	}

	private void setExplicationCalcul(String explicationCalcul) {
		this.explicationCalcul = explicationCalcul;
	}

	private void setExplicationDetailleeCalcul(String explicationDetailleeCalcul) {
		this.explicationDetailleeCalcul = explicationDetailleeCalcul;
	}
	
	// ------------ Implémentation de l'interface Impot ---------
	
	@Override
	public BigDecimal getMontant() {
		return montant;
	}

	@Override
	public String getTypeImpot() {
		return typeImpot;
	}

	@Override
	public String getCodeBeneficiaire() {
		return codeBeneficiaire;
	}

	@Override
	public BigDecimal getBaseCalcul() {
		return baseCalcul;
	}

	private BigDecimal getTauxEffectifCalcule() {
		return null == baseCalcul ? null : montant.divide(baseCalcul,5,RoundingMode.HALF_UP);
	}

	@Override
	public BigDecimal getTauxEffectif() {
		return null != tauxEffectif ? tauxEffectif : getTauxEffectifCalcule();
	}

	@Override
	public String getExplicationCalcul() {
		return explicationCalcul;
	}

	@Override
	public String getExplicationDetailleeCalcul() {
		return explicationDetailleeCalcul;
	}
	

	public static class Cons {
		private final BigDecimal montant;
		private final String typeImpot;

		private String codeBeneficiaire;
		private BigDecimal baseCalcul;
		private BigDecimal tauxEffectif;
		private String explicationCalcul;
		private String explicationDetailleeCalcul;
		public Cons(String typeImpot, BigDecimal montant) {
			this.typeImpot = typeImpot;
			this.montant = montant;
		}

		public Cons codeBeneficiaire(String code) {
			this.codeBeneficiaire = code;
			return this;
		}

		public Cons baseCalcul(BigDecimal montant) {
			this.baseCalcul = montant;
			return this;
		}

		public Cons tauxEffectif(BigDecimal taux) {
			this.tauxEffectif = taux;
			return this;
		}

		public Cons explicationCalcul(String explications) {
			this.explicationCalcul = explications;
			return this;
		}

		public Cons explicationDetailleeCalcul(String explications) {
			this.explicationDetailleeCalcul = explications;
			return this;
		}

		public ImpotProduit cons() {
			ImpotProduit ip = new ImpotProduit(this.typeImpot,this.montant);
			ip.setCodeBeneficiaire(this.codeBeneficiaire);
			ip.setBaseCalcul(this.baseCalcul);
			ip.setTauxEffectif(this.tauxEffectif);
			ip.setExplicationCalcul(this.explicationCalcul);
			ip.setExplicationDetailleeCalcul(this.explicationDetailleeCalcul);
			return ip;
		}
	}

}
