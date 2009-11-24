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
package ch.ge.afc.calcul.impot.cantonal.ge.pp.avant2010;

import java.math.BigDecimal;
import java.text.NumberFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.ge.afc.calcul.bareme.Bareme;
import ch.ge.afc.calcul.impot.FournisseurAssietteCommunale;
import ch.ge.afc.calcul.impot.ImpotProduit;
import ch.ge.afc.calcul.impot.RecepteurImpot;
import ch.ge.afc.calcul.impot.taxation.pp.FournisseurAssiettePeriodique;
import ch.ge.afc.calcul.impot.taxation.pp.ProducteurImpot;
import ch.ge.afc.calcul.impot.taxation.pp.SituationFamiliale;
import ch.ge.afc.util.BigDecimalUtil;

public abstract class ProducteurImpotAvecRabais extends ProducteurImpot {

	final Logger logger = LoggerFactory.getLogger(ProducteurImpotAvecRabais.class);

	private ProducteurRabaisImpot producteurMinimumVital; 
	private String nomRabaisImpot;
	
	private BigDecimal pourcentageReduction;
	
	public ProducteurImpotAvecRabais(String nomImpotProduit, String nomRabaisImpot, String codeBeneficiaire) {
		super(nomImpotProduit,codeBeneficiaire);
		this.nomRabaisImpot = nomRabaisImpot;
	}

	
	/**
	 * @return the pourcentageReduction
	 */
	protected BigDecimal getPourcentageReduction() {
		return pourcentageReduction;
	}


	/**
	 * @param pourcentageReduction the pourcentageReduction to set
	 */
	public void setPourcentageReduction(String pourcentageReduction) {
		this.pourcentageReduction = BigDecimalUtil.parseTaux(pourcentageReduction);
	}


	/* (non-Javadoc)
	 * @see ch.ge.afc.calcul.impot.taxation.pp.ProducteurImpot#produireImpotBase(ch.ge.afc.calcul.impot.taxation.pp.SituationFamiliale, ch.ge.afc.calcul.impot.taxation.pp.FournisseurAssiettePeriodique, ch.ge.afc.calcul.impot.RecepteurImpot)
	 */
	/**
	 * @return the producteurMinimumVital
	 */
	protected ProducteurRabaisImpot getProducteurMinimumVital() {
		return producteurMinimumVital;
	}


	/**
	 * @param producteurMinimumVital the producteurMinimumVital to set
	 */
	public void setProducteurMinimumVital(
			ProducteurRabaisImpot producteurMinimumVital) {
		this.producteurMinimumVital = producteurMinimumVital;
	}

	protected BigDecimal produireRabaisImpot(BigDecimal montantImpotBase, SituationFamiliale situation, FournisseurAssiettePeriodique fournisseur, RecepteurImpot recepteur) {
		BigDecimal determinant = getTypeArrondiDeterminant().arrondirMontant(fournisseur.getMontantDeterminant());
		determinant = getStrategieImpositionFamille().transformeDeterminant(situation,determinant);
		BigDecimal imposable = getTypeArrondiImposable().arrondirMontant(fournisseur.getMontantImposable());
		BigDecimal impotAnnuel = null;
		Bareme bareme = getStrategieImpositionFamille().getBareme(situation);
		impotAnnuel = super.produireImpot(situation, bareme, determinant,imposable);
		BigDecimal impot = getStrategieAnnualisation().annualiseImpot(impotAnnuel, fournisseur.getNombreJourPourAnnualisation());
		
		impot = getTypeArrondiImpot().arrondirMontant(impot).min(montantImpotBase).negate();
		
		ImpotProduit impotProduit = new ImpotProduit(this.nomRabaisImpot,impot);
		impotProduit.setCodeBeneficiaire(this.getCodeBeneficiaire());
		impotProduit.setBaseCalcul(imposable);
		recepteur.ajouteImpot(impotProduit);
		return impot;
	}
	
	private FournisseurAssiettePeriodique construireAssietteRabais(final FournisseurAssiettePeriodiqueGE assietteImpot) {
		FournisseurAssiettePeriodique fournisseur = new FournisseurAssiettePeriodique() {

			@Override
			public int getNombreJourPourAnnualisation() {
				return assietteImpot.getNombreJourPourAnnualisation();
			}
			@Override
			public int getPeriodeFiscale() {
				return assietteImpot.getPeriodeFiscale();
			}
			@Override
			public BigDecimal getMontantDeterminant() {
				return assietteImpot.getMontantDeterminantRabais();
			}
			@Override
			public BigDecimal getMontantImposable() {
				if (0 == assietteImpot.getMontantImposable().compareTo(assietteImpot.getMontantDeterminant())) return assietteImpot.getMontantDeterminantRabais();
				else {
					BigDecimal minimumImposable = assietteImpot.getMontantDeterminantRabais().multiply(assietteImpot.getMontantImposable()).divide(assietteImpot.getMontantDeterminant(),0,BigDecimal.ROUND_HALF_UP);
					return minimumImposable;
				}
			}
			@Override
			public FournisseurAssietteCommunale getFournisseurAssietteCommunale() {
				return null;
			}
			
		};
		return fournisseur;
	}

	
	
	@Override
	protected BigDecimal produireImpotBase(SituationFamiliale situation,
			FournisseurAssiettePeriodique fournisseur, RecepteurImpot recepteur) {
		
		if (!(fournisseur instanceof FournisseurAssiettePeriodiqueGE)) {
			logger.error("Pour produire le rabais d'impôt, il faut un fournisseur GE !!");
		}
		NumberFormat format = NumberFormat.getNumberInstance();
		format.setGroupingUsed(true);
		format.setMaximumFractionDigits(2);
		format.setMinimumFractionDigits(2);

		
		// Pour le rabais d'impôt, on produit tout d'abord un impôt de base
		// puis le rabais
		BigDecimal impotBase = super.produireImpotBase(situation, fournisseur, recepteur);
		logger.debug("ICC - Impôt base " + format.format(impotBase));
		

		BigDecimal montantRabaisImpot = this.produireRabaisImpot(impotBase, situation, construireAssietteRabais((FournisseurAssiettePeriodiqueGE)fournisseur), recepteur);
		logger.debug("ICC - Rabais impôt " + format.format(montantRabaisImpot));

		BigDecimal impotNet = impotBase.add(montantRabaisImpot).max(BigDecimal.ZERO);
		return impotNet;
	}

}
