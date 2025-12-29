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
import java.util.Optional;

import org.impotch.util.TypeArrondi;
import org.impotch.calcul.ReglePeriodique;
import org.impotch.calcul.assurancessociales.param.ParametrageParticipationHautRevenuAC;
import org.impotch.calcul.assurancessociales.param.ParametrageSuisseAnnuel;
import org.impotch.util.BigDecimalUtil;
import org.impotch.util.StringUtil;

import static java.math.BigDecimal.ZERO;
import static org.impotch.calcul.assurancessociales.CalculateurCotisationAC.unCalculateur;
/**
 * Calcule les cotisations sociales des salariés. Les montants retournés sont ceux 
 * dus par l'employé. En général, il s'agit de la moitié du montant global. La seconde moitié
 * étant payée par l'employeur.
 * 
 * Les cotisations définies au niveau fédéral sont :
 * <ul>
 * 	<li>les cotisations AVS/AI/APG ;</li>
 * 	<li>les cotisations à l'assurance chômage ;</li>
 * 	<li>les cotisations aux allocations pour pertes de gains.</li>
 * </ul>
 * 
 * Certains cantons (cas de Genève) ajoute d'autres cotisations sociales à cette liste.
 * 
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class CalculCotisationsSocialesSalarie extends ReglePeriodique implements CalculCotisationAvsAiApg, CalculPartSalarieeCotisationAvsAiApg, CalculCotisationAssuranceChomage, CalculCotisationsAssuranceAccidentsNonProfessionnels, CalculCotisationsAssuranceMaterniteAdoption {

	public static Constructeur unConstructeurDeCotisationsSocialesSalarie(ParametrageSuisseAnnuel parametrage) {
		return new Constructeur(parametrage);
	}


    /**************************************************/
    /****************** Attributs *********************/
    /**************************************************/

	private CalculCotisationAvsAiApgSalarie calculateurAvsAiApg;
	private BigDecimal montantAnnuelMaximumGainAssure; 
	private CalculCotisationAssuranceChomage calculateurAC;
	/**
	 * Le taux de cotisation à l'assurance maternité adoption
	 */
	private BigDecimal tauxAssuranceMaterniteAdoption;

	
    /**************************************************/
    /*************** Constructeurs ********************/
    /**************************************************/

	private CalculCotisationsSocialesSalarie(int annee) {
		super(annee);
	}

    protected CalculCotisationsSocialesSalarie(CalculCotisationsSocialesSalarie origine) {
        super(origine.getAnnee());
        this.calculateurAvsAiApg = origine.calculateurAvsAiApg;
        this.calculateurAC = origine.calculateurAC;
        this.montantAnnuelMaximumGainAssure = origine.montantAnnuelMaximumGainAssure;
    }

    /**************************************************/
    /********** Accesseurs / Mutateurs ****************/
    /**************************************************/

	private void setCalculateurAvsAiApg(CalculCotisationAvsAiApgSalarie nCalculateurAvsAiApg) {
		calculateurAvsAiApg = nCalculateurAvsAiApg;
	}

	private void setCalculateurAC(CalculCotisationAssuranceChomage calculateurAC) {
		this.calculateurAC = calculateurAC;
	}
	
	private void setMontantAnnuelMaximumGainAssure(BigDecimal montant) {
		montantAnnuelMaximumGainAssure = montant;
	}

	private void setTauxAssuranceMaterniteAdoption(BigDecimal taux) {
		this.tauxAssuranceMaterniteAdoption = taux;
	}

    /**************************************************/
    /******************* Méthodes *********************/

	/**************************************************/



	//-------- Implémentation de l'interface CalculCotisationsAssuranceMaterniteAdoption

	@Override
	public BigDecimal calculPartSalarieeAssuranceMaterniteAdoption(BigDecimal montantDeterminant, TypeArrondi arrondi) {
		if (BigDecimalUtil.isStrictementPositif(this.tauxAssuranceMaterniteAdoption)) {
			BigDecimal cotisation = montantDeterminant.multiply(tauxAssuranceMaterniteAdoption);
			return arrondi.arrondirMontant(cotisation);
		} else {
			return ZERO;
		}
	}


	//------ Implémentation de l'interface CalculPartSalarieeCotisationAvsAiApg

	@Override
	public BigDecimal calculPartSalarieeCotisationAvsAiApg(
			BigDecimal montantDeterminant, TypeArrondi arrondi) {
		return calculateurAvsAiApg.calculPartSalarieeCotisationAvsAiApg(montantDeterminant,arrondi);
	}

	@Override
	public BigDecimal calculPartSalarieeCotisationAvs(
			BigDecimal montantDeterminant, TypeArrondi arrondi) {
		return calculateurAvsAiApg.calculPartSalarieeCotisationAvs(montantDeterminant, arrondi);
	}

	@Override
	public BigDecimal calculPartSalarieeCotisationAi(
			BigDecimal montantDeterminant, TypeArrondi arrondi) {
		return calculateurAvsAiApg.calculPartSalarieeCotisationAi(montantDeterminant, arrondi);
	}

	@Override
	public BigDecimal calculPartSalarieeCotisationApg(
			BigDecimal montantDeterminant, TypeArrondi arrondi) {
		return calculateurAvsAiApg.calculPartSalarieeCotisationApg(montantDeterminant, arrondi);
	}

	//------ Implémentation de l'interface CalculCotisationAvsAiApg
	
	/**
	 * @param montantDeterminant Montant déterminant sur lequel seront calculés les cotisations
	 * @return  La somme des cotisations à l'assurance vieillesse et survivant, à l'assurance invalidité et à l'assurance pour perte de gain.
	 * @see CalculCotisationAvsAiApgSalarie#calculCotisationAvsAiApg(java.math.BigDecimal)
	 */
	public BigDecimal calculCotisationAvsAiApg(BigDecimal montantDeterminant, TypeArrondi arrondi) {
		return calculateurAvsAiApg.calculCotisationAvsAiApg(montantDeterminant, arrondi);
	}

	/**
	 * @param montantDeterminant Montant déterminant sur lequel sera calculé la cotisation
	 * @return le montant de la cotisatin à l'assurance vieillesse et survivant
	 * @see CalculCotisationAvsAiApgSalarie#calculCotisationAvs(java.math.BigDecimal)
	 */
	public BigDecimal calculCotisationAvs(BigDecimal montantDeterminant, TypeArrondi arrondi) {
		return calculateurAvsAiApg.calculCotisationAvs(montantDeterminant, arrondi);
	}

	/**
	 * @param montantDeterminant Montant déterminant sur lequel sera calculé la cotisation
	 * @return le montant de la cotisatin à l'assurance invalidité.
	 * @see CalculCotisationAvsAiApgSalarie#calculCotisationAi(java.math.BigDecimal)
	 */
	public BigDecimal calculCotisationAi(BigDecimal montantDeterminant, TypeArrondi arrondi) {
		return calculateurAvsAiApg.calculCotisationAi(montantDeterminant, arrondi);
	}

	/**
	 * @param montantDeterminant Montant déterminant sur lequel sera calculé la cotisation
	 * @return le montant de la cotisatin à l'assurance perte de gain
	 * @see CalculCotisationAvsAiApgSalarie#calculCotisationApg(java.math.BigDecimal)
	 */
	public BigDecimal calculCotisationApg(BigDecimal montantDeterminant, TypeArrondi arrondi) {
		return calculateurAvsAiApg.calculCotisationApg(montantDeterminant, arrondi);
	}

	//------- Implémentation de l'interface CalculCotisationAssuranceChomage ----------
	
	@Override
	public BigDecimal calculCotisationAC(BigDecimal montantDeterminant, TypeArrondi arrondi) {
		return calculateurAC.calculCotisationAC(montantDeterminant,arrondi);
	}
	
	/* (non-Javadoc)
	 * @see org.impotch.calcul.assurancessociales.CalculCotisationAssuranceChomage#calculPartSalarieeCotisationAssuranceChomage(java.math.BigDecimal)
	 */
	@Override
	public BigDecimal calculPartSalarieeCotisationAssuranceChomage(
			BigDecimal montantDeterminant, TypeArrondi arrondi) {
		return calculateurAC.calculPartSalarieeCotisationAssuranceChomage(montantDeterminant,arrondi);
	}

	//------- Implémentation de l'interface CalculCotisationsAssuranceAccidentsNonProfessionnels
	
	@Override
	public BigDecimal calculPartSalarieeCotisationAssuranceAccidentNonProfessionnel(
			BigDecimal tauxCotisationAssuranceAccidentsNonProfessionnels
			, BigDecimal montantDeterminant, TypeArrondi arrondi) {
		BigDecimal plafond = montantAnnuelMaximumGainAssure.min(montantDeterminant);
		BigDecimal cotisations =  plafond.multiply(tauxCotisationAssuranceAccidentsNonProfessionnels);
		return arrondi.arrondirMontant(cotisations);
	}

    /**************************************************/
    /************** Classes internes ******************/
    /**************************************************/
	


	public static class Constructeur {

		private String tauxAssuranceMaterniteAdoption;
		private final ParametrageSuisseAnnuel parametrageSuisseAnnuel;

		public Constructeur(ParametrageSuisseAnnuel parametrageSuisseAnnuel) {
			this.parametrageSuisseAnnuel = parametrageSuisseAnnuel;

		}

		public Constructeur tauxAssuranceMaterniteAdoption(String taux) {
			this.tauxAssuranceMaterniteAdoption = taux;
			return this;
		}
		
		public CalculCotisationsSocialesSalarie construire(int annee) {
			CalculCotisationAvsAiApgSalarie.Constructeur constructeur = new CalculCotisationAvsAiApgSalarie.Constructeur();
			constructeur.tauxAvs(parametrageSuisseAnnuel.tauxAVS())
					.tauxAi(parametrageSuisseAnnuel.tauxAI())
					.tauxApg(parametrageSuisseAnnuel.tauxAPG());
			
			CalculCotisationsSocialesSalarie calculateur = new CalculCotisationsSocialesSalarie(annee);
			calculateur.setMontantAnnuelMaximumGainAssure(BigDecimal.valueOf(parametrageSuisseAnnuel.montantMaximumDuGainAssure()));

			Optional<ParametrageParticipationHautRevenuAC> participation = parametrageSuisseAnnuel.participationHautRevenu();
			if (participation.isPresent()) {
				ParametrageParticipationHautRevenuAC parametrageParticipation = participation.get();
				Optional<String> limitationDeLaParticipation = parametrageParticipation.ratioAvecLeMontantMaxAssureLimitantLaParticipation();
				if (limitationDeLaParticipation.isPresent()) {
					calculateur.setCalculateurAC(
							unCalculateur(annee, parametrageSuisseAnnuel.montantMaximumDuGainAssure(), parametrageSuisseAnnuel.tauxAC())
									.tauxParticipationHautRevenu(parametrageParticipation.taux())
									.ratioEntreMontantAnnuelMaximumEtLimiteHautRevenu(limitationDeLaParticipation.get()).construire());
				} else {
					calculateur.setCalculateurAC(
							unCalculateur(annee, parametrageSuisseAnnuel.montantMaximumDuGainAssure(), parametrageSuisseAnnuel.tauxAC())
									.tauxParticipationHautRevenu(parametrageParticipation.taux()).construire());
				}
			} else {
				calculateur.setCalculateurAC(
						unCalculateur(annee, parametrageSuisseAnnuel.montantMaximumDuGainAssure(), parametrageSuisseAnnuel.tauxAC()).construire());
			}

			calculateur.setCalculateurAvsAiApg(constructeur.construire(annee));
			if (StringUtil.hasText(this.tauxAssuranceMaterniteAdoption)) {
				calculateur.setTauxAssuranceMaterniteAdoption(BigDecimalUtil.parseTaux(tauxAssuranceMaterniteAdoption));
			}
			return calculateur;
		}
	}
	
}
