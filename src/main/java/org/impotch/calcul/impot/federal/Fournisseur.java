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
import java.util.Optional;
import java.util.OptionalInt;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.impotch.bareme.Bareme;
import org.impotch.calcul.impot.federal.param.FournisseurParametrageAnnuelIFD;
import org.impotch.calcul.impot.taxation.pp.ProducteurImpot;
import org.impotch.calcul.impot.taxation.pp.StrategieProductionImpotFamille;
import org.impotch.calcul.util.ExplicationDetailleTexteBuilder;
import org.impotch.calcul.util.IExplicationDetailleeBuilder;
import org.impotch.util.TypeArrondi;

import static org.impotch.calcul.impot.taxation.pp.ProducteurImpotBaseProgressif.unProducteurImpotBaseProgressif;
import static org.impotch.calcul.impot.taxation.pp.StrategieProductionImpotFamille.doubleBareme;
import static org.impotch.calcul.impot.taxation.pp.StrategieProductionImpotFamille.doubleBaremeAvecRabaisCharge;

public class Fournisseur implements FournisseurRegleImpotFederal {

	private static final TypeArrondi ARRONDI_ASSIETTES = TypeArrondi.CENTAINE_INF;


	private final ConcurrentMap<Integer, ProducteurImpot> producteurImpotsFederauxPP = new ConcurrentHashMap<>();
	private final ConcurrentMap<Integer, ProducteurImpot> producteurImpotsPrestationCapital = new ConcurrentHashMap<>();
	private final ConcurrentMap<Integer, ProducteurImpot> producteurImpotSourcePrestationCapital = new ConcurrentHashMap<>();

    private final FournisseurParametrageAnnuelIFD fournisseurParametrageAnnuelIFD;

	public Fournisseur(FournisseurParametrageAnnuelIFD fournisseurParamIFD) {
		this.fournisseurParametrageAnnuelIFD = fournisseurParamIFD;
	}

	public ProducteurImpot producteurImpotsFederauxPP(int annee, TypeArrondi arrondiSurChaqueTranche, boolean avecSeuillage) {
		if (!producteurImpotsFederauxPP.containsKey(annee))
			producteurImpotsFederauxPP.putIfAbsent(annee,
					construireProducteurImpotsFederauxPP(annee,arrondiSurChaqueTranche,avecSeuillage));
		return producteurImpotsFederauxPP.get(annee);
	}

	private Optional<Bareme> getBaremeRevenu(int annee, TypeArrondi arrondiSurChaqueTranche) {
        return fournisseurParametrageAnnuelIFD.getBaremeImpotRevenuPersonnePhysiquePourPersonneSeule(annee,arrondiSurChaqueTranche);
	}

	private Optional<Bareme> getBaremeRevenuFamille(int annee, TypeArrondi arrondiSurChaqueTranche) {
        return fournisseurParametrageAnnuelIFD.getBaremeImpotRevenuPersonnePhysiquePourFamille(annee,arrondiSurChaqueTranche);
	}

	private Bareme getBaremePrestationCapitalFamille(int annee) {
		return  2011 > annee ? fournisseurParametrageAnnuelIFD.getBaremeImpotRevenuPraeNumerandoPersonnePhysiquePourFamille(annee, TypeArrondi.CINQ_CENTIEMES_INF)
				: fournisseurParametrageAnnuelIFD.getBaremeImpotRevenuPersonnePhysiquePourFamille(annee, TypeArrondi.CINQ_CENTIEMES_INF).orElseThrow();
	}
		
	private IExplicationDetailleeBuilder getNewExplicationBuilder() {
		return new ExplicationDetailleTexteBuilder();
	}

	private ProducteurImpot construireProducteurImpotsFederauxPP(int annee, TypeArrondi arrondiSurChaqueTranche, boolean avecSeuillage) {
		ProducteurImpot producteur = new ProducteurImpot("IBR","");
		StrategieProductionImpotFamille strat;
		OptionalInt mntRabais = fournisseurParametrageAnnuelIFD.rabaisImpotCharge(annee);

		Bareme baremeRevenuSeul = getBaremeRevenu(annee,arrondiSurChaqueTranche).orElseThrow();
		Bareme baremeRevenuFamille = getBaremeRevenuFamille(annee,arrondiSurChaqueTranche).orElseThrow();

		if (mntRabais.isPresent()) {
			strat = doubleBaremeAvecRabaisCharge(baremeRevenuSeul,baremeRevenuFamille,mntRabais.getAsInt());
		} else {
			strat = doubleBareme(baremeRevenuSeul, baremeRevenuFamille);
		}
		producteur.setProducteurBase(
				unProducteurImpotBaseProgressif(strat)
						.arrondiAssiettes(ARRONDI_ASSIETTES)
						.arrondiImpot(arrondiSurChaqueTranche)
						.seuilSurImpotDeterminant(avecSeuillage ? BigDecimal.valueOf(25) : BigDecimal.ZERO)
						.construire()
		);
		return producteur;
	}
	



}
