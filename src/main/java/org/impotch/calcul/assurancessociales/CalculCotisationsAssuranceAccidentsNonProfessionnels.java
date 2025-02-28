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
package org.impotch.calcul.assurancessociales;

import java.math.BigDecimal;
import org.impotch.util.TypeArrondi;

import static org.impotch.util.TypeArrondi.CINQ_CENTIEMES_LES_PLUS_PROCHES;

public interface CalculCotisationsAssuranceAccidentsNonProfessionnels {
	/**
	 * @param tauxCotisationAssuranceAccidentsNonProfessionnels
	 * @param montantDeterminant Montant déterminant sur lequel sera calculé la cotisation
	 * @param arrondi la méthode d’arrondi (en général, à l’unité près ou au vingtième près)
	 * @return la part salariée de la cotisation à l'assurance contre les accidents non professionnels
	 * @see org.impotch.calcul.assurancessociales.CalculCotisationsSocialesSalarie#calculPartSalarieeCotisationAssuranceAccidentNonProfessionnel(java.math.BigDecimal, java.math.BigDecimal)
	 */
	BigDecimal calculPartSalarieeCotisationAssuranceAccidentNonProfessionnel(
			BigDecimal tauxCotisationAssuranceAccidentsNonProfessionnels
			, BigDecimal montantDeterminant, TypeArrondi arrondi);


	/**
	 * @param tauxCotisationAssuranceAccidentsNonProfessionnels
	 * @param montantDeterminant Montant déterminant sur lequel sera calculé la cotisation
	 * @return la part salariée de la cotisation à l'assurance contre les accidents non professionnels arrondi aux cin centimes les plus proches.
	 * @see org.impotch.calcul.assurancessociales.CalculCotisationsSocialesSalarie#calculPartSalarieeCotisationAssuranceAccidentNonProfessionnel(java.math.BigDecimal, java.math.BigDecimal)
	 */	default BigDecimal calculPartSalarieeCotisationAssuranceAccidentNonProfessionnel(
			BigDecimal tauxCotisationAssuranceAccidentsNonProfessionnels, BigDecimal montantDeterminant) {
		return calculPartSalarieeCotisationAssuranceAccidentNonProfessionnel(tauxCotisationAssuranceAccidentsNonProfessionnels
				, montantDeterminant, CINQ_CENTIEMES_LES_PLUS_PROCHES);
	}
	
}
