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
package org.impotch.calcul.assurancessociales;

import java.math.BigDecimal;

import org.impotch.bareme.Bareme;
import org.impotch.bareme.ConstructeurBareme;
import org.impotch.calcul.ReglePeriodique;
import org.impotch.util.TypeArrondi;

import static org.impotch.bareme.ConstructeurBareme.unBaremeATauxMarginal;
import static org.impotch.util.TypeArrondi.CINQ_CENTIEMES_LES_PLUS_PROCHES;

public class CalculateurCotisationAC extends ReglePeriodique implements
		CalculCotisationAssuranceChomage {

	private final Bareme bareme;
	

	public static ConstructeurCalculateurCotisationAC unCalculateur(int annee, int montantAnnuelMaximumGainAssure, String tauxCotisationAssuranceChomage) {
		return new ConstructeurCalculateurCotisationAC(annee, montantAnnuelMaximumGainAssure, tauxCotisationAssuranceChomage);
	}

    /**************************************************/
    /*************** Constructeurs ********************/
    /**************************************************/



	private CalculateurCotisationAC(int annee, Bareme bareme) {
		super(annee);
		this.bareme = bareme;
	}

	
	
	@Override
	public BigDecimal calculCotisationAC(BigDecimal montantDeterminant, TypeArrondi arrondi) {
		return arrondi.arrondirMontant(bareme.calcul(montantDeterminant));
	}

	@Override
	public BigDecimal calculPartSalarieeCotisationAssuranceChomage(
			BigDecimal montantDeterminant, TypeArrondi arrondi) {
		return arrondi.arrondirMontant(calculCotisationAC(montantDeterminant, arrondi).divide(BigDecimal.valueOf(2)));
	}

	public static class ConstructeurCalculateurCotisationAC {

		private final int annee;

		private final int montantAnnuelMaximumGainAssure;
		private final String tauxCotisationAssuranceChomage;


		private String tauxParticipationHautRevenu = "0";
		private String ratioEntreMontantAnnuelMaximumEtLimiteHautRevenu;


		public ConstructeurCalculateurCotisationAC(int annee, int montantAnnuelMaximumGainAssure, String tauxCotisationAssuranceChomage) {
			this.annee = annee;
			this.montantAnnuelMaximumGainAssure = montantAnnuelMaximumGainAssure;
			this.tauxCotisationAssuranceChomage = tauxCotisationAssuranceChomage;
		}


		public ConstructeurCalculateurCotisationAC tauxParticipationHautRevenu(String taux) {
			this.tauxParticipationHautRevenu = taux;
			return this;
		}

		public ConstructeurCalculateurCotisationAC ratioEntreMontantAnnuelMaximumEtLimiteHautRevenu(String ratio) {
			this.ratioEntreMontantAnnuelMaximumEtLimiteHautRevenu = ratio;
			return this;
		}

		private ConstructeurBareme initBareme() {
			return unBaremeATauxMarginal()
					.typeArrondiSurChaqueTranche(CINQ_CENTIEMES_LES_PLUS_PROCHES)
					.jusqua(0).taux("0")
					.puisJusqua(montantAnnuelMaximumGainAssure).taux(tauxCotisationAssuranceChomage);
		}


		private CalculateurCotisationAC construireSansRatio() {
			Bareme bareme = initBareme()
					.puis().taux(tauxParticipationHautRevenu)
					.construire();
			return new CalculateurCotisationAC(annee,bareme);
		}

		private int limiteHautRevenu() {
			return BigDecimal.valueOf(montantAnnuelMaximumGainAssure).multiply(new BigDecimal(ratioEntreMontantAnnuelMaximumEtLimiteHautRevenu)).intValue();
		}

		private CalculateurCotisationAC construireAvecRatio() {
			Bareme bareme = initBareme()
					.puisJusqua(limiteHautRevenu()).taux(tauxParticipationHautRevenu)
					.puis().taux("0")
					.construire();
			return new CalculateurCotisationAC(annee,bareme);
		}

		public CalculateurCotisationAC construire() {
			return (null != ratioEntreMontantAnnuelMaximumEtLimiteHautRevenu) ?  construireAvecRatio() : construireSansRatio();
		}
	}
}
