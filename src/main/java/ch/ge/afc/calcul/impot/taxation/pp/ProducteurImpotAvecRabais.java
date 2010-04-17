package ch.ge.afc.calcul.impot.taxation.pp;

import ch.ge.afc.calcul.impot.FournisseurAssietteCommunale;
import ch.ge.afc.calcul.impot.ImpotProduit;
import ch.ge.afc.calcul.impot.RecepteurImpot;
import ch.ge.afc.calcul.impot.cantonal.ge.pp.avant2010.FournisseurAssiettePeriodiqueGE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * Created by IntelliJ IDEA.
 * User: patrick
 * Date: 17 avr. 2010
 * Time: 20:47:21
 * To change this template use File | Settings | File Templates.
 */
public abstract class ProducteurImpotAvecRabais extends ProducteurImpot {

	final Logger logger = LoggerFactory.getLogger(ProducteurImpotAvecRabais.class);

	private ProducteurImpotBase producteurBaseRabais;

	private String nomRabaisImpot;

	public ProducteurImpotAvecRabais(String nomImpotProduit, String nomRabaisImpot, String codeBeneficiaire) {
		super(nomImpotProduit,codeBeneficiaire);
		this.nomRabaisImpot = nomRabaisImpot;
	}


	protected ProducteurImpotBase getProducteurBaseRabais() {
		return producteurBaseRabais;
	}


	public void setProducteurBaseRabais(ProducteurImpotBase producteurBaseRabais) {
		this.producteurBaseRabais = producteurBaseRabais;
	}


	protected BigDecimal produireRabaisImpot(BigDecimal montantImpotBase, SituationFamiliale situation, FournisseurAssiettePeriodique fournisseur, RecepteurImpot recepteur) {
		BigDecimal impot = getProducteurBaseRabais().produireImpotBase(situation, fournisseur);
		impot = impot.min(montantImpotBase).negate();

		ImpotProduit impotProduit = new ImpotProduit(this.nomRabaisImpot,impot);
		impotProduit.setCodeBeneficiaire(this.getCodeBeneficiaire());
		impotProduit.setBaseCalcul(fournisseur.getMontantImposable());
		recepteur.ajouteImpot(impotProduit);
		return impot;
	}

    protected FournisseurAssiettePeriodique construireAssietteRabais(final FournisseurAssiettePeriodique assietteImpot) {
        return assietteImpot;
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

		FournisseurAssiettePeriodique assiettePourRabais = construireAssietteRabais(fournisseur);

		BigDecimal montantRabaisImpot = this.produireRabaisImpot(impotBase, situation, assiettePourRabais, recepteur);
		logger.debug("ICC - Rabais impôt " + format.format(montantRabaisImpot));

		BigDecimal impotNet = impotBase.add(montantRabaisImpot).max(BigDecimal.ZERO);
		return impotNet;
	}

}
