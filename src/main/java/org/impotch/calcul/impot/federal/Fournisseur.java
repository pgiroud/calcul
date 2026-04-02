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

package org.impotch.calcul.impot.federal;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.OptionalInt;

import org.impotch.bareme.Bareme;
import org.impotch.calcul.impot.federal.param.FournisseurParametrageAnnuelIFD;
import org.impotch.calcul.impot.taxation.pp.*;
import org.impotch.calcul.util.ExplicationDetailleTexteBuilder;
import org.impotch.calcul.util.IExplicationDetailleeBuilder;
import org.impotch.util.TypeArrondi;

import static org.impotch.util.TypeArrondi.CENTAINE_INF;
import static org.impotch.util.TypeArrondi.VINGTIEME_INF;

import static org.impotch.calcul.impot.taxation.pp.ProducteurImpotBaseProgressif.unProducteurImpotBaseProgressif;
import static org.impotch.calcul.impot.taxation.pp.StrategieProductionImpotFamille.doubleBareme;

public class Fournisseur implements FournisseurRegleImpotFederal {

	private static final TypeArrondi ARRONDI_ASSIETTES = CENTAINE_INF;
	private static final String CODE_CONFEDERATION_SUISSE = "CH";

    private final FournisseurParametrageAnnuelIFD fournisseurParametrageAnnuelIFD;

	public Fournisseur(FournisseurParametrageAnnuelIFD fournisseurParamIFD) {
		this.fournisseurParametrageAnnuelIFD = fournisseurParamIFD;
	}

	public ProducteurImpot producteurImpotsFederauxPP(int annee, TypeArrondi arrondiSurChaqueTranche) {
		return construireProducteurImpotsFederauxPP(annee,arrondiSurChaqueTranche);
	}

	private Optional<Bareme> getBaremeRevenu(int annee, TypeArrondi arrondiSurChaqueTranche) {
        return fournisseurParametrageAnnuelIFD.getBaremeImpotRevenuPersonnePhysiquePourPersonneSeule(annee,arrondiSurChaqueTranche);
	}

	private Optional<Bareme> getBaremeRevenuFamille(int annee, TypeArrondi arrondiSurChaqueTranche) {
        return fournisseurParametrageAnnuelIFD.getBaremeImpotRevenuPersonnePhysiquePourFamille(annee,arrondiSurChaqueTranche);
	}

	private Bareme getBaremePrestationCapitalFamille(int annee) {
		return  2011 > annee ?
		    fournisseurParametrageAnnuelIFD.getBaremeImpotRevenuPraeNumerandoPersonnePhysiquePourFamille(annee, VINGTIEME_INF)
		    : fournisseurParametrageAnnuelIFD.getBaremeImpotRevenuPersonnePhysiquePourFamille(annee, VINGTIEME_INF).orElseThrow();
	}
		
	private IExplicationDetailleeBuilder getNewExplicationBuilder() {
		return new ExplicationDetailleTexteBuilder();
	}

	private ProducteurRabaisImpotBaremeParental producteurImpotRabais(int annee, int rabais) {
		return new ProducteurRabaisImpotBaremeParental(annee, rabais);
	}

	private StrategieProductionImpotFamille impositionFamiliale(int annee, TypeArrondi arrondiSurChaqueTranche) {
		Bareme baremeRevenuSeul = getBaremeRevenu(annee,arrondiSurChaqueTranche).orElseThrow();
		Bareme baremeRevenuFamille = getBaremeRevenuFamille(annee,arrondiSurChaqueTranche).orElseThrow();
		return doubleBareme(baremeRevenuSeul, baremeRevenuFamille);
	}

	private ProducteurImpot construireProducteurImpotsFederauxPPAvecBaremeParental(int annee, TypeArrondi arrondiSurChaqueTranche, int rabaisParCharge) {
		ProducteurImpotAvecRabais producteur = new ProducteurImpotAvecRabais("IBR", "RI", CODE_CONFEDERATION_SUISSE);
		producteur.setProducteurBaseRabais(new ProducteurImpotBaseAdaptateur(producteurImpotRabais(annee,rabaisParCharge)));
		producteur.setProducteurBase(
				unProducteurImpotBaseProgressif(impositionFamiliale(annee,arrondiSurChaqueTranche))
						.arrondiAssiettes(ARRONDI_ASSIETTES)
						.arrondiImpot(arrondiSurChaqueTranche)
						.construire()
		);
		return producteur;
	}

	private ProducteurImpot construireProducteurImpotsFederauxPPAvantBaremeParental(int annee, TypeArrondi arrondiSurChaqueTranche) {
		ProducteurImpot producteur = new ProducteurImpot("IBR",CODE_CONFEDERATION_SUISSE);
		producteur.setProducteurBase(
				unProducteurImpotBaseProgressif(impositionFamiliale(annee,arrondiSurChaqueTranche))
						.arrondiAssiettes(ARRONDI_ASSIETTES)
						.arrondiImpot(arrondiSurChaqueTranche)
						.construire()
		);
		return producteur;
	}

	private ProducteurImpot construireProducteurImpotsFederauxPP(int annee, TypeArrondi arrondiSurChaqueTranche) {
		OptionalInt mntRabais = fournisseurParametrageAnnuelIFD.rabaisImpotCharge(annee);
		return mntRabais.isPresent() ?
				construireProducteurImpotsFederauxPPAvecBaremeParental(annee,arrondiSurChaqueTranche,mntRabais.orElseThrow())
				: construireProducteurImpotsFederauxPPAvantBaremeParental(annee,arrondiSurChaqueTranche);
	}
	
	private static class ProducteurImpotBaseAdaptateur implements ProducteurImpotBase {

		private final ProducteurRabaisImpotBaremeParental prodRabais;

		public ProducteurImpotBaseAdaptateur(ProducteurRabaisImpotBaremeParental prodRabais) {
			this.prodRabais = prodRabais;
		}

		@Override
		public BigDecimal produireImpotBase(SituationFamiliale situation, FournisseurAssiettePeriodique fournisseur) {
			return prodRabais.produireMontantDeterminantRabais(situation, new FournisseurMontantRabaisImpot() {});
		}
	}


}
