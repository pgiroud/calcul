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
package org.impotch.calcul.assurancessociales.ge;

import java.math.BigDecimal;

import org.impotch.calcul.ReglePeriodique;
import org.impotch.calcul.assurancessociales.*;

import static org.impotch.util.BigDecimalUtil.parse;
import static org.impotch.util.TypeArrondi.CINQ_CENTIEMES_LES_PLUS_PROCHES;
/**
 * Cette classe permet de calculer toutes les cotisations dues par le salarié pour les salaires perçus
 * sur le cantonal de Genève.
 * En plus des cotisations définies au plan fédéral {@link CalculCotisationsSocialesSalarie}, cette classe
 * calcule la cotisation pour assurance maternité et adoption.
 * 
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class CalculCotisationsSocialesSalarieGE extends ReglePeriodique implements CalculCotisationsAssuranceMaterniteAdoption,
		CalculCotisationAssuranceChomage, CalculCotisationAvsAiApg, CalculPartSalarieeCotisationAvsAiApg, CalculCotisationsAssuranceAccidentsNonProfessionnels {

    /**************************************************/
    /****************** Attributs *********************/
    /**************************************************/

	/**
	 * Le taux de cotisation à l'assurance maternité adoption
	 */
	private final BigDecimal taux;
	/**
	 * délégation à un calculateur fédéral 
	 */
	private final CalculCotisationsSocialesSalarie calculateurSuisse;
	
    /**************************************************/
    /*************** Constructeurs ********************/
    /**************************************************/


	/**
	 * Construit un calculateur des cotisations sociales pour les salaires perçus sur
	 * le cantonal de Genève.
	 *
	 * @param annee L'année pour laquelle ce calculateur est valide
	 * @param taux le taux de cotisation à l'assurance maternité adoption.
	 * @param nCalculateurSuisse le calculateur des cotisations fédérales.
	 */
	public CalculCotisationsSocialesSalarieGE(int annee, BigDecimal taux, CalculCotisationsSocialesSalarie nCalculateurSuisse) {
		super(annee);
		this.taux = taux;
		calculateurSuisse = nCalculateurSuisse;
	}

		/**
         * Construit un calculateur des cotisations sociales pour les salaires perçus sur
         * le cantonal de Genève.
         *
         * @param annee L'année pour laquelle ce calculateur est valide
         * @param nTaux le taux de cotisation à l'assurance maternité adoption.
         * @param nCalculateurSuisse le calculateur des cotisations fédérales.
         */
	public CalculCotisationsSocialesSalarieGE(int annee, String nTaux, CalculCotisationsSocialesSalarie nCalculateurSuisse) {
		super(annee);
		this.taux = parse(nTaux);
		calculateurSuisse = nCalculateurSuisse;
	}

    /**************************************************/
    /********** Accesseurs / Mutateurs ****************/
    /**************************************************/

	/**
	 * Retourne le calculateur des cotisations sociales définies au niveau fédéral.
	 * @return le calculateur fédéral.
	 */
	protected CalculCotisationsSocialesSalarie getCalculateurSuisse() {
		return calculateurSuisse;
	}

    /**************************************************/
    /******************* Méthodes *********************/
    /**************************************************/

	//-------- Implémentation de l'interface CalculCotisationsAssuranceMaterniteAdoption
	
	@Override
	public BigDecimal calculPartSalarieeAssuranceMaterniteAdoption(BigDecimal montantDeterminant) {
		BigDecimal cotisation = montantDeterminant.multiply(taux);
		return CINQ_CENTIEMES_LES_PLUS_PROCHES.arrondirMontant(cotisation);
	}

	//------ Implémentation de l'interface CalculCotisationsAssuranceAccidentsNonProfessionnels
	/**
	 * @param tauxCotisationAssuranceAccidentsNonProfessionnels
	 * @param montantDeterminant Montant déterminant sur lequel sera calculé la cotisation
	 * @return la part salariée de la cotisation à l'assurance contre les accidents non professionnels
	 * @see org.impotch.calcul.assurancessociales.CalculCotisationsSocialesSalarie#calculPartSalarieeCotisationAssuranceAccidentNonProfessionnel(java.math.BigDecimal, java.math.BigDecimal)
	 */
	public BigDecimal calculPartSalarieeCotisationAssuranceAccidentNonProfessionnel(
			BigDecimal tauxCotisationAssuranceAccidentsNonProfessionnels,
			BigDecimal montantDeterminant) {
		return calculateurSuisse.calculPartSalarieeCotisationAssuranceAccidentNonProfessionnel(
				tauxCotisationAssuranceAccidentsNonProfessionnels,
				montantDeterminant);
	}

	//----- Implémentation de l'interface CalculCotisationAssuranceChomage
	
	@Override
	public BigDecimal calculPartSalarieeCotisationAssuranceChomage(
			BigDecimal montantDeterminant) {
		return calculateurSuisse.calculPartSalarieeCotisationAssuranceChomage(montantDeterminant);
	}

	/**
	 * @param montantDeterminant Montant déterminant sur lequel sera calculé la cotisation
	 * @return le montant de la cotisation à l'assurance contre le chômage
	 * @see org.impotch.calcul.assurancessociales.CalculCotisationsSocialesSalarie#calculCotisationAC(java.math.BigDecimal)
	 */
	public BigDecimal calculCotisationAC(BigDecimal montantDeterminant) {
		return calculateurSuisse.calculCotisationAC(montantDeterminant);
	}

	//------ Implémentation de l'interface CalculCotisationAvsAiApg
	
	/**
	 * @param montantDeterminant Montant déterminant sur lequel sera calculé la cotisation
	 * @return le montant de la cotisatin à l'assurance invalidité.
	 * @see org.impotch.calcul.assurancessociales.CalculCotisationsSocialesSalarie#calculCotisationAi(java.math.BigDecimal)
	 */
	public BigDecimal calculCotisationAi(BigDecimal montantDeterminant) {
		return calculateurSuisse.calculCotisationAi(montantDeterminant);
	}

	/**
	 * @param montantDeterminant Montant déterminant sur lequel sera calculé la cotisation
	 * @return le montant de la cotisatin à l'assurance perte de gain
	 * @see org.impotch.calcul.assurancessociales.CalculCotisationsSocialesSalarie#calculCotisationApg(java.math.BigDecimal)
	 */
	public BigDecimal calculCotisationApg(BigDecimal montantDeterminant) {
		return calculateurSuisse.calculCotisationApg(montantDeterminant);
	}

	/**
	 * @param montantDeterminant Montant déterminant sur lequel sera calculé la cotisation
	 * @return le montant de la cotisatin à l'assurance vieillesse et survivant
	 * @see org.impotch.calcul.assurancessociales.CalculCotisationsSocialesSalarie#calculCotisationAvs(java.math.BigDecimal)
	 */
	public BigDecimal calculCotisationAvs(BigDecimal montantDeterminant) {
		return calculateurSuisse.calculCotisationAvs(montantDeterminant);
	}

	/**
	 * @param montantDeterminant Montant déterminant sur lequel sera calculé la cotisation
	 * @return La somme des cotisations à l'assurance vieillesse et survivant, à l'assurance invalidité et à l'assurance pour perte de gain.
	 * @see org.impotch.calcul.assurancessociales.CalculCotisationsSocialesSalarie#calculCotisationAvsAiApg(java.math.BigDecimal)
	 */
	public BigDecimal calculCotisationAvsAiApg(BigDecimal montantDeterminant) {
		return calculateurSuisse.calculCotisationAvsAiApg(montantDeterminant);
	}

	/**
	 * @param montantDeterminant Montant déterminant sur lequel sera calculé la cotisation
	 * @return  La part due par le salarié de la cotisation à l'assurances invalidité.
	 * @see org.impotch.calcul.assurancessociales.CalculCotisationsSocialesSalarie#calculPartSalarieeCotisationAi(java.math.BigDecimal)
	 */
	public BigDecimal calculPartSalarieeCotisationAi(
			BigDecimal montantDeterminant) {
		return calculateurSuisse
				.calculPartSalarieeCotisationAi(montantDeterminant);
	}

	/**
	 * @param montantDeterminant Montant déterminant sur lequel sera calculé la cotisation
	 * @return La part due par le salarié de la cotisation aux assurances contre la perte de gain.
	 * @see org.impotch.calcul.assurancessociales.CalculCotisationsSocialesSalarie#calculPartSalarieeCotisationApg(java.math.BigDecimal)
	 */
	public BigDecimal calculPartSalarieeCotisationApg(
			BigDecimal montantDeterminant) {
		return calculateurSuisse
				.calculPartSalarieeCotisationApg(montantDeterminant);
	}

	/**
	 * @param montantDeterminant Montant déterminant sur lequel sera calculé la cotisation
	 * @return La part due par le salarié de la cotisation aux assurances vieillesse et survivant
	 * @see org.impotch.calcul.assurancessociales.CalculCotisationsSocialesSalarie#calculPartSalarieeCotisationAvs(java.math.BigDecimal)
	 */
	public BigDecimal calculPartSalarieeCotisationAvs(
			BigDecimal montantDeterminant) {
		return calculateurSuisse
				.calculPartSalarieeCotisationAvs(montantDeterminant);
	}

	/**
	 * @param montantDeterminant  Montant déterminant sur lequel sera calculé la cotisation
	 * @return La part due par le salarié des cotisations aux assurances vieillesse et survivant, invalidité et perte de gain.
	 * @see org.impotch.calcul.assurancessociales.CalculCotisationsSocialesSalarie#calculPartSalarieeCotisationAvsAiApg(java.math.BigDecimal)
	 */
	public BigDecimal calculPartSalarieeCotisationAvsAiApg(
			BigDecimal montantDeterminant) {
		return calculateurSuisse
				.calculPartSalarieeCotisationAvsAiApg(montantDeterminant);
	}

	
	
}
