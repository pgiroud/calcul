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
/**
 * Calcule la cotisation à l'assurance chômage. Le calcul est décrit dans la loi  
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public interface CalculCotisationAssuranceChomage {

	/**
	 * Calcule la cotisation à l'assurance chômage. Le montant retourné est la somme de la
	 * part employeur et de la part employé.
	 * @param montantDeterminant le salaire déterminant au sens de la législation sur l'AVS.
	 * @param arrondi la méthode d’arrondi (en général, à l’unité près ou au vingtième près)
	 * @return le montant de la cotisation
	 */
	BigDecimal calculCotisationAC(BigDecimal montantDeterminant, TypeArrondi arrondi);

	/**
	 * Calcule la cotisation à l'assurance chômage. Le montant retourné est la somme de la
	 * part employeur et de la part employé.
	 * @param montantDeterminant le salaire déterminant au sens de la législation sur l'AVS.
	 * @return le montant de la cotisation arrondi aux 5 centimes près
	 */
	default BigDecimal calculCotisationAC(BigDecimal montantDeterminant) {
		return calculCotisationAC(montantDeterminant,CINQ_CENTIEMES_LES_PLUS_PROCHES);
	}

	default BigDecimal calculCotisationAC(long montantDeterminant, TypeArrondi arrondi) {
		return calculCotisationAC(BigDecimal.valueOf(montantDeterminant), arrondi);
	}
	default BigDecimal calculCotisationAC(long montantDeterminant) {
		return calculCotisationAC(BigDecimal.valueOf(montantDeterminant));
	}

	/**
	 * Calcule la part employé de la cotisation à l'assurance chômage.
	 * @param montantDeterminant le salaire déterminant au sens de la législation sur l'AVS.
	 * @param arrondi la méthode d’arrondi (en général, à l’unité près ou au vingtième près)
	 * @return la part due par le salarié de la cotisation à l'assurance chômage.
	 */
	BigDecimal calculPartSalarieeCotisationAssuranceChomage(BigDecimal montantDeterminant, TypeArrondi arrondi);

	/**
	 * Calcule la part employé de la cotisation à l'assurance chômage.
	 * @param montantDeterminant le salaire déterminant au sens de la législation sur lb'AVS.
	 * @return la part due par le salarié de la cotisation à l'assurance chômage arrondi aux 5 centimes les plus proches
	 */
	default BigDecimal calculPartSalarieeCotisationAssuranceChomage(BigDecimal montantDeterminant) {
		return calculPartSalarieeCotisationAssuranceChomage(montantDeterminant,CINQ_CENTIEMES_LES_PLUS_PROCHES);
	}

	default BigDecimal calculPartSalarieeCotisationAssuranceChomage(long montantDeterminant, TypeArrondi arrondi) {
		return calculPartSalarieeCotisationAssuranceChomage(BigDecimal.valueOf(montantDeterminant), arrondi);
	}


	default BigDecimal calculPartSalarieeCotisationAssuranceChomage(long montantDeterminant) {
		return calculPartSalarieeCotisationAssuranceChomage(BigDecimal.valueOf(montantDeterminant));
	}


}
