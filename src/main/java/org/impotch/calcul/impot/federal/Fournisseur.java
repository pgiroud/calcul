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
package org.impotch.calcul.impot.federal;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.impotch.bareme.Bareme;
import org.impotch.calcul.impot.federal.param.FournisseurBaremeIFD;
import org.impotch.calcul.impot.taxation.pp.ProducteurImpotAvecRabais;
import org.impotch.calcul.impot.federal.pp.source.CalculateurImpotSourcePrestationCapitalIFD;
import org.impotch.calcul.impot.taxation.pp.ProducteurImpot;
import org.impotch.calcul.util.ExplicationDetailleTexteBuilder;
import org.impotch.calcul.util.IExplicationDetailleeBuilder;
import org.impotch.util.TypeArrondi;

import static org.impotch.calcul.impot.taxation.pp.ProducteurImpotBaseProgressif.unProducteurImpotBaseProgressif;
import static org.impotch.calcul.impot.taxation.pp.StrategieProductionImpotFamille.doubleBareme;

public class Fournisseur implements FournisseurRegleImpotFederal {

	private static final TypeArrondi ARRONDI_ASSIETTES = TypeArrondi.CENTAINE_INF;
	private static final TypeArrondi ARRONDI_IMPOT = TypeArrondi.CINQ_CENTIEMES_INF;


	private final ConcurrentMap<Integer, ProducteurImpot> producteurImpotsFederauxPP = new ConcurrentHashMap<>();
	private final ConcurrentMap<Integer, ProducteurImpot> producteurImpotsPrestationCapital = new ConcurrentHashMap<>();
	private final ConcurrentMap<Integer, ProducteurImpot> producteurImpotSourcePrestationCapital = new ConcurrentHashMap<>();

    private final FournisseurBaremeIFD fournisseurBaremeIFD;

	public Fournisseur(FournisseurBaremeIFD fournisseurBaremeIFD) {
		this.fournisseurBaremeIFD = fournisseurBaremeIFD;
	}

	public ProducteurImpot getProducteurImpotsFederauxPP(int annee) {
		if (!producteurImpotsFederauxPP.containsKey(annee))
			producteurImpotsFederauxPP.putIfAbsent(annee,
					construireProducteurImpotsFederauxPP(annee));
		return producteurImpotsFederauxPP.get(annee);
	}

//	public ProducteurImpot getProducteurImpotsPrestationCapital(int annee) {
//
//		if (!producteurImpotsPrestationCapital.containsKey(annee))
//			producteurImpotsPrestationCapital.putIfAbsent(annee,
//					construireProducteurImpotsPrestationCapital(annee));
//		return producteurImpotsPrestationCapital.get(annee);
//	}
//
//	public ProducteurImpot getProducteurImpotSourcePrestationCapital(int annee) {
//
//		if (!producteurImpotSourcePrestationCapital.containsKey(annee))
//			producteurImpotSourcePrestationCapital.putIfAbsent(annee,
//					construireProducteurImpotSourcePrestationCapital(annee));
//		return producteurImpotSourcePrestationCapital.get(annee);
//	}
	
	private Bareme getBaremeRevenu(int annee) {
        return fournisseurBaremeIFD.getBaremeImpotRevenuPersonnePhysiquePourPersonneSeule(annee);
	}

//	private Bareme construireBaremePrestationCapital(final Bareme bareme) {
//		return new Bareme() {
//
//			@Override
//			public BigDecimal calcul(BigDecimal assiette) {
//				BigDecimal impot = bareme.calcul(assiette);
//				return TypeArrondi.CINQ_CENTIEMES_LES_PLUS_PROCHES.arrondirMontant(impot.multiply(new BigDecimal("0.2")));
//			}
//
//		};
//	}
//
//	private Bareme getBaremePrestationCapital(int annee) {
////        Bareme bareme = 2011 > annee ? fournisseurBaremeIFD.getBaremeImpotRevenuPraeNumerandoPersonnePhysiquePourPersonneSeule(annee) : fournisseurBaremeIFD.getBaremeImpotRevenuPersonnePhysiquePourPersonneSeule(annee);
////        return construireBaremePrestationCapital(bareme);
//		return 2011 > annee ? fournisseurBaremeIFD.getBaremeImpotRevenuPraeNumerandoPersonnePhysiquePourPersonneSeule(annee) : fournisseurBaremeIFD.getBaremeImpotRevenuPersonnePhysiquePourPersonneSeule(annee);
//	}
	
	private Bareme getBaremeRevenuFamille(int annee) {
        return fournisseurBaremeIFD.getBaremeImpotRevenuPersonnePhysiquePourFamille(annee);
	}

	private Bareme getBaremePrestationCapitalFamille(int annee) {
//        Bareme bareme =  2011 > annee ? fournisseurBaremeIFD.getBaremeImpotRevenuPraeNumerandoPersonnePhysiquePourFamille(annee) : fournisseurBaremeIFD.getBaremeImpotRevenuPersonnePhysiquePourFamille(annee);
//        return construireBaremePrestationCapital(bareme);
		return  2011 > annee ? fournisseurBaremeIFD.getBaremeImpotRevenuPraeNumerandoPersonnePhysiquePourFamille(annee) : fournisseurBaremeIFD.getBaremeImpotRevenuPersonnePhysiquePourFamille(annee);
	}
		
	private IExplicationDetailleeBuilder getNewExplicationBuilder() {
		return new ExplicationDetailleTexteBuilder();
	}

	private int montantReductionImpotParEnfant(int annee) {
		if (2011 == annee) return 250; // Loi fédérale sur les allégements fiscaux
			// en faveur des familles avec enfants du 25 septembre 2009
			// Art. 214, al. 2 2bis
		if (2012 == annee // RO 2011 4503
				|| 2013 == annee
				|| 2014 == annee // https://www.fedlex.admin.ch/eli/cc/2013/589/fr RO 2013 589 Art. 2 alinea 3
				|| 2015 == annee
				|| 2016 == annee
				|| 2017 == annee
				|| 2018 == annee
				|| 2019 == annee
				|| 2020 == annee
				|| 2021 == annee
				|| 2022 == annee) return 251; // https://www.fedlex.admin.ch/eli/cc/2013/589/fr RO 2013 589 Art. 2 alinea 3
		if (2023 == annee) return 255; // https://www.fedlex.admin.ch/eli/cc/2022/575/fr RO 2022 575 Art.2 alinea 3
		if (2024 == annee) return 259; // https://www.fedlex.admin.ch/eli/cc/2023/493/fr RO 2023 493 Art.2 alinea 3
		if (2025 == annee) return 263; // https://www.fedlex.admin.ch/eli/oc/2024/479/fr RO 2024 479 Art.2 alinea 3
		throw new IllegalArgumentException("Le rabais d’impôt pour les enfants n’est pas connu pour l’année " + annee);
	}


	private ProducteurImpot construireProducteurImpotsFederauxPP(int annee) {
        ProducteurImpot producteur = null;
        if (annee < 2011) {
            producteur = new ProducteurImpot("IBR","");
        } else {
             ProducteurImpotAvecRabais prodAvecRabais = new ProducteurImpotAvecRabais("IBR","RI","");
            prodAvecRabais.setProducteurBaseRabais(new  ProducteurRabaisEnfantPersonneNecessiteuse(montantReductionImpotParEnfant(annee)));
            producteur = prodAvecRabais;
        }
		producteur.setProducteurBase(
				unProducteurImpotBaseProgressif(doubleBareme(getBaremeRevenu(annee), getBaremeRevenuFamille(annee)))
						.arrondiAssiettes(ARRONDI_ASSIETTES)
						.arrondiImpot(ARRONDI_IMPOT)
						.construire()
		);
		return producteur;
	}
	



}
