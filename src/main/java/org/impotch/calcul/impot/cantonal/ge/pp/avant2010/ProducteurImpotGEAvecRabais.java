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
package org.impotch.calcul.impot.cantonal.ge.pp.avant2010;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import org.impotch.calcul.impot.PeriodeFiscale;
import org.impotch.calcul.impot.taxation.pp.FournisseurAssiettePeriodique;
import org.impotch.calcul.impot.taxation.pp.ProducteurImpotAvecRabais;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.impotch.calcul.impot.FournisseurAssietteCommunale;

public class ProducteurImpotGEAvecRabais extends ProducteurImpotAvecRabais {

	final Logger logger = LoggerFactory.getLogger(ProducteurImpotGEAvecRabais.class);

	public ProducteurImpotGEAvecRabais(String nomImpotProduit, String nomRabaisImpot, String codeBeneficiaire) {
		super(nomImpotProduit,nomRabaisImpot,codeBeneficiaire);
	}

	@Override
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
				return Optional.of(((FournisseurAssiettePeriodiqueGE)assietteImpot).getMontantDeterminantRabais());
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

}
