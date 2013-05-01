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
package org.impotch.calcul.assurancessociales;

import java.math.BigDecimal;

import org.impotch.calcul.ReglePeriodique;
import org.impotch.util.TypeArrondi;

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
public class CalculCotisationsSocialesSalarie extends ReglePeriodique implements CalculCotisationAvsAiApg, CalculPartSalarieeCotisationAvsAiApg, CalculCotisationAssuranceChomage, CalculCotisationsAssuranceAccidentsNonProfessionnels {

    /**************************************************/
    /****************** Attributs *********************/
    /**************************************************/

	private CalculCotisationAvsAiApgSalarie calculateurAvsAiApg;
	private BigDecimal montantAnnuelMaximumGainAssure; 
	private CalculCotisationAssuranceChomage calculateurAC;
	
	
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

	protected void setCalculateurAC(CalculCotisationAssuranceChomage calculateurAC) {
		this.calculateurAC = calculateurAC;
	}
	
	private void setMontantAnnuelMaximumGainAssure(BigDecimal montant) {
		montantAnnuelMaximumGainAssure = montant;
	}



    /**************************************************/
    /******************* Méthodes *********************/
    /**************************************************/

	//------ Implémentation de l'interface CalculPartSalarieeCotisationAvsAiApg

	/* (non-Javadoc)
	 * @see org.impotch.calcul.assurancessociales.CalculPartSalarieeCotisationAvsAiApg#calculPartSalarieeCotisationAvsAiApg(java.math.BigDecimal)
	 */
	@Override
	public BigDecimal calculPartSalarieeCotisationAvsAiApg(
			BigDecimal montantDeterminant) {
		return calculateurAvsAiApg.calculPartSalarieeCotisationAvsAiApg(montantDeterminant);
	}

	/* (non-Javadoc)
	 * @see org.impotch.calcul.assurancessociales.CalculPartSalarieeCotisationAvsAiApg#calculPartSalarieeCotisationAvs(java.math.BigDecimal)
	 */
	@Override
	public BigDecimal calculPartSalarieeCotisationAvs(
			BigDecimal montantDeterminant) {
		return calculateurAvsAiApg.calculPartSalarieeCotisationAvs(montantDeterminant);
	}

	/* (non-Javadoc)
	 * @see org.impotch.calcul.assurancessociales.CalculPartSalarieeCotisationAvsAiApg#calculPartSalarieeCotisationAi(java.math.BigDecimal)
	 */
	@Override
	public BigDecimal calculPartSalarieeCotisationAi(
			BigDecimal montantDeterminant) {
		return calculateurAvsAiApg.calculPartSalarieeCotisationAi(montantDeterminant);
	}

	/* (non-Javadoc)
	 * @see ch.ge.afc.calcul.assurancessociales.CalculPartSalarieeCotisationAvsAiApg#calculPartSalarieeCotisationApg(java.math.BigDecimal)
	 */
	@Override
	public BigDecimal calculPartSalarieeCotisationApg(
			BigDecimal montantDeterminant) {
		return calculateurAvsAiApg.calculPartSalarieeCotisationApg(montantDeterminant);
	}

	//------ Implémentation de l'interface CalculCotisationAvsAiApg
	
	/**
	 * @param montantDeterminant Montant déterminant sur lequel seront calculés les cotisations
	 * @return  La somme des cotisations à l'assurance vieillesse et survivant, à l'assurance invalidité et à l'assurance pour perte de gain.
	 * @see CalculCotisationAvsAiApgSalarie#calculCotisationAvsAiApg(java.math.BigDecimal)
	 */
	public BigDecimal calculCotisationAvsAiApg(BigDecimal montantDeterminant) {
		return calculateurAvsAiApg.calculCotisationAvsAiApg(montantDeterminant);
	}

	/**
	 * @param montantDeterminant Montant déterminant sur lequel sera calculé la cotisation
	 * @return le montant de la cotisatin à l'assurance vieillesse et survivant
	 * @see CalculCotisationAvsAiApgSalarie#calculCotisationAvs(java.math.BigDecimal)
	 */
	public BigDecimal calculCotisationAvs(BigDecimal montantDeterminant) {
		return calculateurAvsAiApg.calculCotisationAvs(montantDeterminant);
	}

	/**
	 * @param montantDeterminant Montant déterminant sur lequel sera calculé la cotisation
	 * @return le montant de la cotisatin à l'assurance invalidité.
	 * @see CalculCotisationAvsAiApgSalarie#calculCotisationAi(java.math.BigDecimal)
	 */
	public BigDecimal calculCotisationAi(BigDecimal montantDeterminant) {
		return calculateurAvsAiApg.calculCotisationAi(montantDeterminant);
	}

	/**
	 * @param montantDeterminant Montant déterminant sur lequel sera calculé la cotisation
	 * @return le montant de la cotisatin à l'assurance perte de gain
	 * @see CalculCotisationAvsAiApgSalarie#calculCotisationApg(java.math.BigDecimal)
	 */
	public BigDecimal calculCotisationApg(BigDecimal montantDeterminant) {
		return calculateurAvsAiApg.calculCotisationApg(montantDeterminant);
	}

	//------- Implémentation de l'interface CalculCotisationAssuranceChomage ----------
	
	@Override
	public BigDecimal calculCotisationAC(BigDecimal montantDeterminant) {
		return calculateurAC.calculCotisationAC(montantDeterminant);
	}
	
	/* (non-Javadoc)
	 * @see org.impotch.calcul.assurancessociales.CalculCotisationAssuranceChomage#calculPartSalarieeCotisationAssuranceChomage(java.math.BigDecimal)
	 */
	@Override
	public BigDecimal calculPartSalarieeCotisationAssuranceChomage(
			BigDecimal montantDeterminant) {
		return calculateurAC.calculPartSalarieeCotisationAssuranceChomage(montantDeterminant);
	}

	//------- Implémentation de l'interface CalculCotisationsAssuranceAccidentsNonProfessionnels
	
	@Override
	public BigDecimal calculPartSalarieeCotisationAssuranceAccidentNonProfessionnel(BigDecimal tauxCotisationAssuranceAccidentsNonProfessionnels, BigDecimal montantDeterminant) {
		BigDecimal plafond = montantAnnuelMaximumGainAssure.min(montantDeterminant);
		BigDecimal cotisations =  plafond.multiply(tauxCotisationAssuranceAccidentsNonProfessionnels);
		return TypeArrondi.CINQ_CTS.arrondirMontant(cotisations);
	}

    /**************************************************/
    /************** Classes internes ******************/
    /**************************************************/
	


	public static class Constructeur {
		
		private String tauxAVS;
		private String tauxAI;
		private String tauxAPG;

		private int montantMax;
		private String tauxAC;
		private String ratioHautRevenu;
		private String tauxACContributionHautRevenu;
		
		/**
		 * Spécifie le taux de cotisation total (part employeur + part salariée).
		 * @param taux le taux de cotisation AVS
		 * @return ce construteur afin de chaîner les appels.
		 */
		public Constructeur tauxAvs(String taux) {
			tauxAVS = taux;
			return this;
		}

		/**
 		 * Spécifie le taux de cotisation total (part employeur + part salariée).
		 * @param taux le taux de cotisation à l'assurance invalidité.
		 * @return ce construteur afin de chaîner les appels.
		 */
		public Constructeur tauxAi(String taux) {
			tauxAI = taux;
			return this;
		}

		/**
		 * Spécifie le taux de cotisation total (part employeur + part salariée).
		 * @param taux le taux des allocations pour perte de gains.
		 * @return ce construteur afin de chaîner les appels.
		 */
		public Constructeur tauxApg(String taux) {
			tauxAPG = taux;
			return this;
		}

		public Constructeur montantAnnuelMaxGainAssure(int nMontantMax) {
			montantMax = nMontantMax;
			return this;
		}
		
		/**
		 * Spécifie le taux de cotisation total (part employeur + part salariée).
		 * @param taux le taux de la cotisation à l'assurance chômage.
		 * @return ce construteur afin de chaîner les appels.
		 */
		public Constructeur tauxAC(String taux) {
			this.tauxAC = taux;
			return this;
		}
		
		public Constructeur participationHautRevenuCotisationAC(String ratioEntreHautRevenuEtMntMaxAssure, String taux) {
			this.ratioHautRevenu = ratioEntreHautRevenuEtMntMaxAssure;
			this.tauxACContributionHautRevenu = taux;
			return this;
		}
		
		
		public CalculCotisationsSocialesSalarie construire(int annee) {
			CalculCotisationAvsAiApgSalarie.Constructeur constructeur = new CalculCotisationAvsAiApgSalarie.Constructeur();
			constructeur.tauxAvs(tauxAVS).tauxAi(tauxAI).tauxApg(tauxAPG);
			
			CalculateurCotisationAC calculateurAC;
			if (null != ratioHautRevenu) {
				calculateurAC = new CalculateurCotisationAC(annee,montantMax,ratioHautRevenu,tauxAC,tauxACContributionHautRevenu);
			} else {
				calculateurAC = new CalculateurCotisationAC(annee,montantMax,tauxAC);
			}
			
			CalculCotisationsSocialesSalarie calculateur = new CalculCotisationsSocialesSalarie(annee);
			calculateur.setMontantAnnuelMaximumGainAssure(new BigDecimal(montantMax));
			calculateur.setCalculateurAC(calculateurAC);
			calculateur.setCalculateurAvsAiApg(constructeur.construire(annee));
			return calculateur;
		}
	}
	
}
