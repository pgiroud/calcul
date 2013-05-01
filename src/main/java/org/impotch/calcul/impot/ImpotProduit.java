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
	public ImpotProduit(String typeImpot, BigDecimal montant) {
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
	public void setCodeBeneficiaire(String codeBeneficiaire) {
		this.codeBeneficiaire = codeBeneficiaire;
	}

	/**
	 * Précise la base de calcul ayant déterminé l'impôt.
	 * @param baseCalcul la base de calcul.
	 */
	public void setBaseCalcul(BigDecimal baseCalcul) {
		this.baseCalcul = baseCalcul;
	}

	/**
	 * Indique le taux effectif de l'impôt. Si cette méthode n'est pas appelée,
	 * le taux effectif fournit sera recalculé en fonction du montant d'impôt et de la base
	 * de calcul.
	 * @param tauxEffectif le taux effectif.
	 */
	public void setTauxEffectif(BigDecimal tauxEffectif) {
		this.tauxEffectif = tauxEffectif;
	}
	
	public void setExplicationCalcul(String explicationCalcul) {
		this.explicationCalcul = explicationCalcul;
	}

	public void setExplicationDetailleeCalcul(String explicationDetailleeCalcul) {
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

	@Override
	public BigDecimal getTauxEffectif() {
		if (null == tauxEffectif) {
			if (null == baseCalcul) {
				return null;
			} else {
				return montant.divide(baseCalcul,5,RoundingMode.HALF_UP);
			}
		} else {
			return tauxEffectif;
		}
	}

	@Override
	public String getExplicationCalcul() {
		return explicationCalcul;
	}

	@Override
	public String getExplicationDetailleeCalcul() {
		return explicationDetailleeCalcul;
	}
	
	
}
