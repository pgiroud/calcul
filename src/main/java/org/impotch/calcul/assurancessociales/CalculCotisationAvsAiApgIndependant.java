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
package org.impotch.calcul.assurancessociales;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.impotch.calcul.ReglePeriodique;
import org.impotch.util.BigDecimalUtil;
import org.impotch.util.TypeArrondi;

/**
 * Cette classe permet le calcul du montant des cotisations des assurances
 * sociales suisses suivantes pour les indépendants :
 * <ul>
 * <li>l'assurance vieillesse et survivant AVS ;</li>
 * <li>l'assurance invalidité AI ;</li>
 * <li>l'assurance contre les pertes de gains APG.</li>
 * </ul>
 * En général, les caisses de compensations retiennent des frais de gestion (un
 * pourcentage environ égal à 2,5 % des cotisations). Ces frais ne sont pas
 * compris dans les montants calculés et devront donc être ajoutés aux montants
 * ci-dessous.
 * 
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 */
class CalculCotisationAvsAiApgIndependant extends ReglePeriodique
		implements CalculCotisationAvsAiApg {

	/**************************************************/
	/****************** Attributs *********************/
	/**************************************************/

	private final BigDecimal montantCotisationMinimumAvsAiApg;
	private final BigDecimal tauxAvs;
	private final BigDecimal tauxAi;
	private final BigDecimal tauxApg;
	private List<TrancheBareme> bareme;

    /**************************************************/
    /***************** Constructeurs ******************/
    /**************************************************/
			
	/**
	 * Construit un calculateur pour les indépendants.
	 * @param iniAnnee l'année de validité
	 * @param nTauxAvs le taux AVS
	 * @param nTauxAi le taux AI
	 * @param nTauxApg le taux APG
	 * @param nMontantMinimum le montant minimum des cotisations.
	 */
	private CalculCotisationAvsAiApgIndependant(int iniAnnee,
			BigDecimal nTauxAvs, BigDecimal nTauxAi, BigDecimal nTauxApg,
			BigDecimal nMontantMinimum) {

		super(iniAnnee);
		this.tauxAvs = nTauxAvs;
		this.tauxAi = nTauxAi;
		this.tauxApg = nTauxApg;
		this.montantCotisationMinimumAvsAiApg = nMontantMinimum;
	}

    /**************************************************/
    /******* Accesseurs / Mutateurs *******************/
    /**************************************************/
	
	void setTrancheBareme(List<TrancheBareme> tranches) {
		this.bareme = tranches;
	}
	
	
	private BigDecimal getTauxAvs() {
		return tauxAvs;
	}

	private BigDecimal getTauxAi() {
		return tauxAi;
	}

	private BigDecimal getTauxApg() {
		return tauxApg;
	}

    /**************************************************/
    /******************* Méthodes *********************/
    /**************************************************/
	
	private BigDecimal arrondirTaux(BigDecimal taux) {
		BigDecimal precision = new BigDecimal("0.00001");
		BigDecimal normalise = taux.divide(precision, 10,
				BigDecimal.ROUND_HALF_UP);
		BigDecimal arrondiEntier = normalise.setScale(0,
				BigDecimal.ROUND_HALF_UP);
		return arrondiEntier.multiply(precision).setScale(5);
	}

	private BigDecimal getTauxAi(BigDecimal montantDeterminant) {
		if (isTauxDegressif(montantDeterminant)) {
			BigDecimal taux = getTauxAi().multiply(
					getTauxAvs(montantDeterminant)).divide(getTauxAvs(), 5,
					BigDecimal.ROUND_HALF_UP);
			return arrondirTaux(taux);
		} else {
			if (isInferieurMinimum(montantDeterminant))
				return null;
			return getTauxAi();
		}
	}

	private BigDecimal getTauxApg(BigDecimal montantDeterminant) {
		if (isTauxDegressif(montantDeterminant)) {
			return arrondirTaux(getTauxApg().multiply(
					getTauxAvs(montantDeterminant)).divide(getTauxAvs(), 5,
					BigDecimal.ROUND_HALF_UP));
		} else {
			if (isInferieurMinimum(montantDeterminant))
				return null;
			return getTauxApg();
		}
	}

	public BigDecimal calculCotisationAi(BigDecimal montantDeterminant) {
		if (isInferieurMinimum(montantDeterminant))
			return getCotisationMinimumAi();
		return TypeArrondi.CINQ_CTS.arrondirMontant(getTauxAi(
				montantDeterminant).multiply(montantDeterminant));
	}

	public BigDecimal calculCotisationApg(BigDecimal montantDeterminant) {
		if (isInferieurMinimum(montantDeterminant))
			return getCotisationMinimumApg();
		return TypeArrondi.CINQ_CTS.arrondirMontant(getTauxApg(
				montantDeterminant).multiply(montantDeterminant));
	}

	public BigDecimal calculCotisationAvs(BigDecimal montantDeterminant) {
		if (isInferieurMinimum(montantDeterminant))
			return getCotisationMinimumAvs();
		return calculCotisationAvsAiApg(montantDeterminant).subtract(
				calculCotisationAi(montantDeterminant))
		.subtract(calculCotisationApg(montantDeterminant));
	}

	public BigDecimal calculCotisationAvsAiApg(BigDecimal montantDeterminant) {
		if (isInferieurMinimum(montantDeterminant))
			return getCotisationMinimumAvsAiApg();
		return TypeArrondi.CINQ_CTS.arrondirMontant(getTauxTotal(
				montantDeterminant).multiply(montantDeterminant));
	}

	private BigDecimal getCotisationMinimumAvsAiApg() {
		return montantCotisationMinimumAvsAiApg;
	}

	private BigDecimal getCotisationMinimumAvs() {
		return montantCotisationMinimumAvsAiApg.subtract(
				getCotisationMinimumAi()).subtract(getCotisationMinimumApg());
	}

	private BigDecimal getCotisationMinimumAi() {
		return TypeArrondi.CINQ_CTS
				.arrondirMontant(montantCotisationMinimumAvsAiApg.multiply(
						this.getTauxAi()).divide(this.getTauxTotal(), 10,
						BigDecimal.ROUND_HALF_UP));
	}

	private BigDecimal getCotisationMinimumApg() {
		return TypeArrondi.CINQ_CTS
				.arrondirMontant(montantCotisationMinimumAvsAiApg.multiply(
						this.getTauxApg()).divide(this.getTauxTotal(), 10,
						BigDecimal.ROUND_HALF_UP));
	}

	private BigDecimal getTauxTotal() {
		BigDecimal total = tauxAvs;
		total = total.add(tauxAi);
		total = total.add(tauxApg);
		return total;
	}

	private boolean isTauxDegressif(BigDecimal montantDeterminant) {
		return 0 > montantDeterminant
				.compareTo(bareme.get(bareme.size() - 1).abscisse)
				&& 0 <= montantDeterminant.compareTo(bareme.get(0).abscisse);
	}

	private BigDecimal getTauxAvs(BigDecimal montantDeterminant) {
		for (TrancheBareme tranche : bareme) {
			if (0 < tranche.abscisse.compareTo(montantDeterminant)) {
				return tranche.ordonnee;
			}
		}
		if (isInferieurMinimum(montantDeterminant))
			return null;
		return getTauxAvs();
	}

	private boolean isInferieurMinimum(BigDecimal montantDeterminant) {
		return 0 > montantDeterminant.compareTo(bareme.get(0).abscisse);
	}

	BigDecimal getTauxTotal(BigDecimal montantDeterminant) {
		if (isInferieurMinimum(montantDeterminant))
			return null;
		if (!isTauxDegressif(montantDeterminant)) {
			return getTauxTotal();
		} else {
			BigDecimal total = getTauxAvs(montantDeterminant);
			total = total.add(getTauxAi(montantDeterminant));
			total = total.add(getTauxApg(montantDeterminant));
			return total;
		}
	}

	private static class TrancheBareme {
		private final BigDecimal abscisse;
		private final BigDecimal ordonnee;

		private TrancheBareme(BigDecimal nAbscisse, BigDecimal nOrdonnee) {
			this.abscisse = nAbscisse;
			this.ordonnee = nOrdonnee;
		}

	}

    /**************************************************/
    /************** Classes internes ******************/
    /**************************************************/
	
	public static class Constructeur {
		
		private BigDecimal montantCotisationMinimumAvsAiApg;
		private BigDecimal tauxAvs;
		private BigDecimal tauxAi;
		private BigDecimal tauxApg;
		private List<TrancheBareme> bareme = new ArrayList<TrancheBareme>();
		
				
		public Constructeur tauxAvs(String taux) {
			try {
				this.tauxAvs = BigDecimalUtil.parseTaux(taux);
			} catch (NumberFormatException nfe) {
				throw new IllegalArgumentException(nfe);
			}
			return this;
		}
		
		public Constructeur tauxAi(String taux) {
			try {
				this.tauxAi = BigDecimalUtil.parseTaux(taux);
			} catch (NumberFormatException nfe) {
				throw new IllegalArgumentException(nfe);
			}
			return this;
		}
		
		public Constructeur tauxApg(String taux) {
			try {
				this.tauxApg = BigDecimalUtil.parseTaux(taux);
			} catch (NumberFormatException nfe) {
				throw new IllegalArgumentException(nfe);
			}
			return this;
		}
		
		public Constructeur cotisationAvsAiApgMinimum(String montant) {
			try {
				this.montantCotisationMinimumAvsAiApg = new BigDecimal(montant);
			} catch (NumberFormatException nfe) {
				throw new IllegalArgumentException(nfe);
			}
			return this;
		}
		
		public Constructeur trancheBareme(String montant, String taux) {
			BigDecimal montantBD = null;
			BigDecimal tauxBD = null;
			try {
				montantBD = new BigDecimal(montant);
				tauxBD = BigDecimalUtil.parseTaux(taux);
			} catch (NumberFormatException nfe) {
				throw new IllegalArgumentException(nfe);
			}
			bareme.add(new TrancheBareme(montantBD,tauxBD));
			return this;
		}
		
		public CalculCotisationAvsAiApgIndependant construire(int annee) {
			CalculCotisationAvsAiApgIndependant calculateur = new CalculCotisationAvsAiApgIndependant(annee,
					tauxAvs, tauxAi,
					tauxApg, montantCotisationMinimumAvsAiApg);
			calculateur.setTrancheBareme(bareme);
			return calculateur;
		}
	}
}
