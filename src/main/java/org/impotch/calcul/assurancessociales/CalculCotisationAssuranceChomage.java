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
	 * @return le montant de la cotisation
	 */
	BigDecimal calculCotisationAC(BigDecimal montantDeterminant);

	default BigDecimal calculCotisationAC(long montantDeterminant) {
		return calculCotisationAC(BigDecimal.valueOf(montantDeterminant));
	}

	/**
	 * Calcule la part employé de la cotisation à l'assurance chômage.
	 * @param montantDeterminant le salaire déterminant au sens de la législation sur l'AVS.
	 * @return la part due par le salarié de la cotisation à l'assurance chômage.
	 */
	BigDecimal calculPartSalarieeCotisationAssuranceChomage(BigDecimal montantDeterminant);

	default BigDecimal calculPartSalarieeCotisationAssuranceChomage(long montantDeterminant) {
		return calculPartSalarieeCotisationAssuranceChomage(BigDecimal.valueOf(montantDeterminant));
	}
}
