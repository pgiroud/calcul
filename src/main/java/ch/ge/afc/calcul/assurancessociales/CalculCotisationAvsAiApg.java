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
package ch.ge.afc.calcul.assurancessociales;

import java.math.BigDecimal;

/**
 * Calcule les cotisations à l'assurance vieillesse et survivants, à l'assurance
 * invalidité et les allocations pour perte de gains.
 * 
 * Les valeurs retournées, dans le cas des salariées est la somme des parts dues par l'employeur et l'employé.
 * 
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public interface CalculCotisationAvsAiApg {
	
	/**
	 * Calcule globalement le montant des cotisations à l'assurance vieillesse et survivants, à l'assurance
	 * invalidité et les allocations pour perte de gains.
	 * @param montantDeterminant le salaire déterminant au sens de la loi sur l'AVS.
	 * @return le montant global (part employeur + part employé) des 3 cotisations AVS, AI et APG.
	 */
    BigDecimal calculCotisationAvsAiApg(BigDecimal montantDeterminant);

    /**
     * Retourne uniquement le montant de la cotisation à l'assurance vieillesse et survivant.
     * @param montantDeterminant le salaire déterminant au sens de la loi sur l'AVS.
     * @return le montant de la cotisation à l'AVS (part employé + part employeur).
     */
    BigDecimal calculCotisationAvs(BigDecimal montantDeterminant);

    /**
     * Retourne uniquement le montant de la cotisation à l'assurance invalidité.
     * @param montantDeterminant le salaire déterminant au sens de la loi sur l'AVS.
     * @return le montant de la cotisation à l'AI (part employé + part employeur).
     */
    BigDecimal calculCotisationAi(BigDecimal montantDeterminant);

    /**
     * Retourne uniquement le montant de la cotisation pour les allocations en cas de perte de gains.
     * @param montantDeterminant le salaire déterminant au sens de la loi sur l'AVS.
     * @return le montant de la cotisation à l'APG (part employé + part employeur).
     */
    BigDecimal calculCotisationApg(BigDecimal montantDeterminant);

}
