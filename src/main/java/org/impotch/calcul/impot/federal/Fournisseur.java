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
		if (annee > 2023) {
			return 259;
		} else if (annee > 2022) {
			return 255;
		} else if (annee > 2011) {
			return 251;
		} else {
			return 250;
		}
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
