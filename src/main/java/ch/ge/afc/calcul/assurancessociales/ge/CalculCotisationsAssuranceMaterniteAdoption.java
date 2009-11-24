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
package ch.ge.afc.calcul.assurancessociales.ge;

import java.math.BigDecimal;

/**
 * Calcule les cotisations pour l'assurance maternité et adoption. Ces assurances
 * sont un complément cantonal aux allocations pour pertes de gain.
 * Ces cotisations existent uniquement dans le canton de Genève. Elles sont décrites dans
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
	 * @return la part due par le salarié de la cotisation à l'assurance maternité adoption. 
	 */
	BigDecimal calculPartSalarieeAssuranceMaterniteAdoption(BigDecimal montantDeterminant);
}
