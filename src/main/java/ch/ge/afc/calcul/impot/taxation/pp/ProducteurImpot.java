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
package ch.ge.afc.calcul.impot.taxation.pp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.ge.afc.calcul.impot.FournisseurAssiette;
import ch.ge.afc.calcul.impot.ImpotProduit;
import ch.ge.afc.calcul.impot.ProducteurImpotCommunal;
import ch.ge.afc.calcul.impot.ProducteurImpotDerive;
import ch.ge.afc.calcul.impot.RecepteurImpot;
import ch.ge.afc.calcul.impot.taxation.pp.annualisation.StrategieAnnualisationComptable;
import static ch.ge.afc.util.BigDecimalUtil.isStrictementPositif;
import ch.ge.afc.util.IExplicationDetailleeBuilder;
import ch.ge.afc.util.TypeArrondi;

public abstract class ProducteurImpot {
	
	/**************************************************/
	/****************** Attributs *********************/
	/**************************************************/

	final Logger logger = LoggerFactory.getLogger(ProducteurImpot.class);

	private TypeArrondi typeArrondiImposable	= TypeArrondi.FRANC;
	private TypeArrondi typeArrondiDeterminant	= TypeArrondi.FRANC;
	private TypeArrondi typeArrondiImpot		= TypeArrondi.CINQ_CTS;
	
	private List<ProducteurImpotDerive> producteursDerives = new ArrayList<ProducteurImpotDerive>();
	private ProducteurImpotCommunal producteurImpotCommunal;
//	private ProducteurImpotParoissial producteurImpotParoissial;
	private StrategieProductionImpotFamille impositionFamille;
	/**
	 * Par défaut, la stratégie d'annualisation utilise un calendrier comptable à 360 jours.
	 */
	private StrategieAnnualisation stratAnnualisation = new StrategieAnnualisationComptable();
	
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
	
	/**
	 * Précise le type d'arrondi à effectuer sur les assiettes imposables.
	 * En règle générale, on utilise des arrondis à la centaine inférieure pour le
	 * revenu et le bénéfice et au mille francs inférieur pour la fortune ou le capital.
	 */
	public void setTypeArrondiImposable(TypeArrondi type) {
		typeArrondiImposable = type;
	}
	
	/**
	 * Retourne le type d'arrondi à effectuer sur les assiettes imposables.
	 * @return le type d'arrondi.
	 */
	protected TypeArrondi getTypeArrondiImposable() {
		return typeArrondiImposable;
	}
	
	/**
	 * Précise le type d'arrondi à effectuer sur les assiettes déterminantes.
	 * En règle générale, on utilise des arrondis à la centaine inférieure pour le
	 * revenu et le bénéfice et au mille francs inférieur pour la fortune ou le capital.
	 */
	public void setTypeArrondiDeterminant(TypeArrondi type) {
		typeArrondiDeterminant = type;
	}
	
	/**
	 * Retourne le type d'arrondi à effectuer sur les assiettes déterminantes.
	 * @return le type d'arrondi.
	 */
	protected TypeArrondi getTypeArrondiDeterminant() {
		return typeArrondiDeterminant;
	}
	
	/**
	 * Spécifie le type d'arrondi à appliquer à l'impôt calculé. Par défaut, l'arrondi se fait aux 5 centimes
	 * les plus proches.
	 * 
	 * @param type le type d'arrondi sur le montant d'impôt.
	 */
	public void setTypeArrondiImpot(TypeArrondi type) {
		typeArrondiImpot = type;
	}
	
	/**
	 * Retourne le type d'arrondi sur le montant d'impôt.
	 * @return le type d'arrondi sur le montant d'impôt.
	 */
	protected TypeArrondi getTypeArrondiImpot() {
		return typeArrondiImpot;
	}
	
	/**
	 * Précise la stratégie d'imposition à appliquer aux familles. En effet plusieurs systèmes sont en vigueur :
	 * <ul>
	 * 	<li>Splitting : canton de Fribourg, de Neuchâtel, ...</li>
	 * 	<li>double barème : IFD, canton de Genève, ...</li>
	 * 	<li>quotient familial : canton de Vaud, France, ...</li>
	 * </ul>
	 * @param strategie la stratégie à appliquer.
	 */
	public void setStrategieProductionImpotFamille(StrategieProductionImpotFamille strategie) {
		impositionFamille = strategie;
	}
	
	/**
	 * Retourne la stratégie d'imposition à appliquer aux familles.
	 * @return la stratégie d'imposition à appliquer aux familles.
	 */
	protected StrategieProductionImpotFamille getStrategieImpositionFamille() {
		return impositionFamille;
	}
	
	/**
	 * Précise la stratégie d'annualisation lors de la production d'impôt.
	 * Par défaut, la stratégie est une stratégie avec calendrier comptable à 360 jours. 
	 * @param strategie la stratégie
	 */
	public void setStrategieAnnualisation(StrategieAnnualisation strategie) {
		stratAnnualisation = strategie; 
	}
	
	/**
	 * Retourne la stratégie d'annualisation pour produire l'impôt.
	 * @return la stratégie d'annualisation pour produire l'impôt.
	 */
	protected StrategieAnnualisation getStrategieAnnualisation() {
		return stratAnnualisation;
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
		BigDecimal determinant = getTypeArrondiDeterminant().arrondirMontant(fournisseur.getMontantDeterminant());
		BigDecimal imposable = getTypeArrondiImposable().arrondirMontant(fournisseur.getMontantImposable());
		if (!isStrictementPositif(determinant) || !isStrictementPositif(imposable)) return BigDecimal.ZERO;
		
		
		BigDecimal impotAnnuel = getStrategieImpositionFamille().produireImpotAnnuel(situation,determinant,imposable);
		BigDecimal impot = getStrategieAnnualisation().annualiseImpot(impotAnnuel, fournisseur.getNombreJourPourAnnualisation());
		impot = getTypeArrondiImpot().arrondirMontant(impot);
		ImpotProduit impotProduit = new ImpotProduit(nomImpotProduit,impot);
		impotProduit.setCodeBeneficiaire(codeBeneficiaire);
		impotProduit.setBaseCalcul(imposable);
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
