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

import org.impotch.calcul.impot.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Optional;

public class ProducteurImpotAvecRabais extends ProducteurImpot {

	final Logger logger = LoggerFactory.getLogger(ProducteurImpotAvecRabais.class);

	private ProducteurImpotBase producteurBaseRabais;

	private final String nomRabaisImpot;

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

		ImpotProduit impotProduit = new ImpotProduit.Cons(this.nomRabaisImpot,impot)
				.codeBeneficiaire(this.getCodeBeneficiaire())
				.baseCalcul(fournisseur.getMontantImposable())
				.cons();

		recepteur.ajouteImpot(impotProduit);
		return impot;
	}

    protected FournisseurAssiettePeriodique construireAssietteRabais(final FournisseurAssiettePeriodique assietteImpot) {
		FournisseurAssiettePeriodique fournisseur = new FournisseurAssiettePeriodique() {

			@Override
			public int getNombreJourPourAnnualisation() {
				return assietteImpot.getNombreJourPourAnnualisation();
			}
			@Override
			public PeriodeFiscale getPeriodeFiscale() {
				return assietteImpot.getPeriodeFiscale();
			}
			@Override
			public Optional<BigDecimal> getMontantDeterminant() {
				return Optional.of(((FournisseurAssiettePeriodiqueAvecRabais)assietteImpot).getMontantDeterminantRabais());
			}
			@Override
			public BigDecimal getMontantImposable() {
				BigDecimal montantDeterminantRabais = this.getMontantDeterminant().get();
				if (assietteImpot.getMontantDeterminant().isPresent()) {
					BigDecimal assietteImpotDeterminant = assietteImpot.getMontantDeterminant().get();

					if (0 == assietteImpot.getMontantImposable().compareTo(assietteImpotDeterminant)) return montantDeterminantRabais;
					else {
						return montantDeterminantRabais.multiply(assietteImpot.getMontantImposable()).divide(assietteImpotDeterminant,0, RoundingMode.HALF_UP);
					}
				} else {
					return montantDeterminantRabais;
				}
			}
			@Override
			public Optional<FournisseurAssietteCommunale> getFournisseurAssietteCommunale() {
				return Optional.empty();
			}

		};
		return fournisseur;
	}

	@Override
	protected BigDecimal produireImpotBase(SituationFamiliale situation,
			FournisseurAssiettePeriodique fournisseur, RecepteurImpot recepteur) {

		NumberFormat format = NumberFormat.getNumberInstance();
		format.setGroupingUsed(true);
		format.setMaximumFractionDigits(2);
		format.setMinimumFractionDigits(2);


		// Pour le rabais d'impôt, on produit tout d'abord un impôt de base
		// puis le rabais
		BigDecimal impotBase = super.produireImpotBase(situation, fournisseur, recepteur);
		logger.debug("Impôt base " + format.format(impotBase));

		FournisseurAssiettePeriodique assiettePourRabais = construireAssietteRabais(fournisseur);

		BigDecimal montantRabaisImpot = this.produireRabaisImpot(impotBase, situation, assiettePourRabais, recepteur);
		logger.debug("Rabais impôt " + format.format(montantRabaisImpot));

        return impotBase.add(montantRabaisImpot).max(BigDecimal.ZERO);
	}

}
