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
package org.impotch.calcul.impot.taxation.pp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.impotch.calcul.impot.FournisseurAssiette;
import org.impotch.calcul.impot.ProducteurImpotDerive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.impotch.calcul.impot.ImpotProduit;
import org.impotch.calcul.impot.ProducteurImpotCommunal;
import org.impotch.calcul.impot.RecepteurImpot;
import static org.impotch.util.BigDecimalUtil.isStrictementPositif;
import org.impotch.calcul.util.IExplicationDetailleeBuilder;

public abstract class ProducteurImpot {
	
	/**************************************************/
	/****************** Attributs *********************/
	/**************************************************/

	final Logger logger = LoggerFactory.getLogger(ProducteurImpot.class);

	private ProducteurImpotBase producteurBase;
	private List<ProducteurImpotDerive> producteursDerives = new ArrayList<ProducteurImpotDerive>();
	private ProducteurImpotCommunal producteurImpotCommunal;

	private final String nomImpotProduit;
	private final String codeBeneficiaire;
	
    /**************************************************/
    /**************** Constructeurs *******************/
    /**************************************************/
	
	public ProducteurImpot(String nomImpotProduit, String codeBeneficiaire) {
		this.nomImpotProduit = nomImpotProduit;
		this.codeBeneficiaire = codeBeneficiaire;
	}
	
    /**************************************************/
    /******* Accesseurs / Mutateurs *******************/
    /**************************************************/

	protected String getNomImpotProduit() {
		return nomImpotProduit;
	}
	
	protected String getCodeBeneficiaire() {
		return codeBeneficiaire;
	}
	
	
    protected ProducteurImpotBase getProducteurBase() {
		return producteurBase;
	}

	public void setProducteurBase(ProducteurImpotBase producteurBase) {
		this.producteurBase = producteurBase;
	}

	/**
	 * @return the producteurImpotCommunal
	 */
	protected ProducteurImpotCommunal getProducteurImpotCommunal() {
		return producteurImpotCommunal;
	}

	/**
	 * @param producteurImpotCommunal the producteurImpotCommunal to set
	 */
	public void setProducteurImpotCommunal(
			ProducteurImpotCommunal producteurImpotCommunal) {
		this.producteurImpotCommunal = producteurImpotCommunal;
	}

	protected abstract IExplicationDetailleeBuilder createExplicationBuilder();

	/**************************************************/
    /******************* Méthodes *********************/
    /**************************************************/

	public void ajouteProducteurDerive(ProducteurImpotDerive producteur) {
		producteursDerives.add(producteur);
	}
	
	protected void produireImpotsDerives(BigDecimal montantBase, FournisseurAssiette fournisseur, RecepteurImpot recepteur) {
		for (ProducteurImpotDerive producteur : producteursDerives) {
			producteur.produireImpot(montantBase, fournisseur, recepteur);
		}
	}
	
	protected BigDecimal produireImpotBase(SituationFamiliale situation, FournisseurAssiettePeriodique fournisseur, RecepteurImpot recepteur) {
		BigDecimal impot = getProducteurBase().produireImpotBase(situation, fournisseur);
		ImpotProduit impotProduit = new ImpotProduit(nomImpotProduit,impot);
		impotProduit.setCodeBeneficiaire(codeBeneficiaire);
		impotProduit.setBaseCalcul(fournisseur.getMontantImposable());
		recepteur.ajouteImpot(impotProduit);
		return impot;
	}
	
	/**
	 * Produit un impôt de base et éventuellement des impôts dérivés.
	 * @param situation la situation familiale (état civil, enfants, ...)
	 * @param fournisseur le fournisseur des assiettes (imposables et déterminants)
	 * @param recepteur le récepteur des impôts.
	 */
	public void produireImpot(SituationFamiliale situation, FournisseurAssiettePeriodique fournisseur, RecepteurImpot recepteur) {
		BigDecimal impotBase = produireImpotBase(situation, fournisseur, recepteur);
		
		
		if (isStrictementPositif(impotBase)) {
			produireImpotsDerives(impotBase,fournisseur, recepteur);
			if (null != getProducteurImpotCommunal()) {
				getProducteurImpotCommunal().produireImpot(impotBase, fournisseur.getFournisseurAssietteCommunale(), recepteur);
			}
		}
	}
}
