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
import org.impotch.util.TypeArrondi;

import static org.impotch.util.BigDecimalUtil.parse;

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
	public BigDecimal calculPartSalarieeAssuranceMaterniteAdoption(BigDecimal montantDeterminant, TypeArrondi arrondi) {
		BigDecimal cotisation = montantDeterminant.multiply(taux);
		return arrondi.arrondirMontant(cotisation);
	}

	//------ Implémentation de l'interface CalculCotisationsAssuranceAccidentsNonProfessionnels

	public BigDecimal calculPartSalarieeCotisationAssuranceAccidentNonProfessionnel(
			BigDecimal tauxCotisationAssuranceAccidentsNonProfessionnels,
			BigDecimal montantDeterminant, TypeArrondi arrondi) {
		return calculateurSuisse.calculPartSalarieeCotisationAssuranceAccidentNonProfessionnel(
				tauxCotisationAssuranceAccidentsNonProfessionnels,
				montantDeterminant,arrondi);
	}

	//----- Implémentation de l'interface CalculCotisationAssuranceChomage
	
	@Override
	public BigDecimal calculPartSalarieeCotisationAssuranceChomage(
			BigDecimal montantDeterminant, TypeArrondi arrondi) {
		return calculateurSuisse.calculPartSalarieeCotisationAssuranceChomage(montantDeterminant,arrondi);
	}

	public BigDecimal calculCotisationAC(BigDecimal montantDeterminant, TypeArrondi arrondi) {
		return calculateurSuisse.calculCotisationAC(montantDeterminant,arrondi);
	}

	//------ Implémentation de l'interface CalculCotisationAvsAiApg

	public BigDecimal calculCotisationAi(BigDecimal montantDeterminant, TypeArrondi arrondi) {
		return calculateurSuisse.calculCotisationAi(montantDeterminant,arrondi);
	}

	public BigDecimal calculCotisationApg(BigDecimal montantDeterminant, TypeArrondi arrondi) {
		return calculateurSuisse.calculCotisationApg(montantDeterminant, arrondi);
	}

	public BigDecimal calculCotisationAvs(BigDecimal montantDeterminant, TypeArrondi arrondi) {
		return calculateurSuisse.calculCotisationAvs(montantDeterminant, arrondi);
	}

	public BigDecimal calculCotisationAvsAiApg(BigDecimal montantDeterminant, TypeArrondi arrondi) {
		return calculateurSuisse.calculCotisationAvsAiApg(montantDeterminant, arrondi);
	}

	public BigDecimal calculPartSalarieeCotisationAi(
			BigDecimal montantDeterminant, TypeArrondi arrondi) {
		return calculateurSuisse
				.calculPartSalarieeCotisationAi(montantDeterminant, arrondi);
	}

	public BigDecimal calculPartSalarieeCotisationApg(
			BigDecimal montantDeterminant, TypeArrondi arrondi) {
		return calculateurSuisse
				.calculPartSalarieeCotisationApg(montantDeterminant, arrondi);
	}

	public BigDecimal calculPartSalarieeCotisationAvs(
			BigDecimal montantDeterminant, TypeArrondi arrondi) {
		return calculateurSuisse
				.calculPartSalarieeCotisationAvs(montantDeterminant, arrondi);
	}

	public BigDecimal calculPartSalarieeCotisationAvsAiApg(
			BigDecimal montantDeterminant, TypeArrondi arrondi) {
		return calculateurSuisse
				.calculPartSalarieeCotisationAvsAiApg(montantDeterminant, arrondi);
	}

	
	
}
