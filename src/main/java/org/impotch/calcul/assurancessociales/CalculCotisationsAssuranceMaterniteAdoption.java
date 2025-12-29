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
import org.impotch.util.TypeArrondi;

import static org.impotch.util.TypeArrondi.CINQ_CENTIEMES_LES_PLUS_PROCHES;

/**
 * Calcule les cotisations pour l'assurance maternité et adoption. Ces assurances
 * sont un complément cantonal aux allocations pour pertes de gain.
 * Ces cotisations existent uniquement dans le cantonal de Genève. Elles sont décrites dans
 * la loi genevoise instituant une assurance en cas de maternité et d'adoption (LAMat) du
 * 21 avril 2005 - J 5 07.
 * 
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public interface CalculCotisationsAssuranceMaterniteAdoption {

	/**
	 * Calcule la part due par le salarié (en général la moitié des cotisations globales. L'autre moitié étant réglée par l'employeur) de
	 * des cotisations à l'assurance maternité et adoption.
	 * @param montantDeterminant le salaire déterminant au sens de la législation sur l'AVS.
	 * @param arrondi la méthode d’arrondi (en général, à l’unité près ou au vingtième près)
	 * @return la part due par le salarié de la cotisation à l'assurance maternité adoption.
	 */
	BigDecimal calculPartSalarieeAssuranceMaterniteAdoption(BigDecimal montantDeterminant, TypeArrondi arrondi);

	/**
	 * Calcule la part due par le salarié (en général la moitié des cotisations globales. L'autre moitié étant réglée par l'employeur) de
	 * des cotisations à l'assurance maternité et adoption.
	 * @param montantDeterminant le salaire déterminant au sens de la législation sur l'AVS. 
	 * @return la part due par le salarié de la cotisation à l'assurance maternité adoption. 
	 */
	default BigDecimal calculPartSalarieeAssuranceMaterniteAdoption(BigDecimal montantDeterminant) {
		return calculPartSalarieeAssuranceMaterniteAdoption(montantDeterminant,CINQ_CENTIEMES_LES_PLUS_PROCHES);
	}
}
