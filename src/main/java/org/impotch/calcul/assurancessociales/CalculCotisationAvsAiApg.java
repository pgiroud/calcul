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
    BigDecimal calculCotisationAvsAiApg(BigDecimal montantDeterminant, TypeArrondi arrondi);

    /**
	 * Calcule globalement le montant des cotisations à l'assurance vieillesse et survivants, à l'assurance
	 * invalidité et les allocations pour perte de gains.
	 * @param montantDeterminant le salaire déterminant au sens de la loi sur l'AVS.
	 * @return le montant global (part employeur + part employé) des 3 cotisations AVS, AI et APG.
	 */
    default BigDecimal calculCotisationAvsAiApg(BigDecimal montantDeterminant) {
        return calculCotisationAvsAiApg(montantDeterminant, CINQ_CENTIEMES_LES_PLUS_PROCHES);
    }

    /**
     * Retourne uniquement le montant de la cotisation à l'assurance vieillesse et survivant.
     * @param montantDeterminant le salaire déterminant au sens de la loi sur l'AVS.
     * @return le montant de la cotisation à l'AVS (part employé + part employeur).
     */
    BigDecimal calculCotisationAvs(BigDecimal montantDeterminant, TypeArrondi arrondi);

    /**
     * Retourne uniquement le montant de la cotisation à l'assurance vieillesse et survivant.
     * @param montantDeterminant le salaire déterminant au sens de la loi sur l'AVS.
     * @return le montant de la cotisation à l'AVS (part employé + part employeur).
     */
    default BigDecimal calculCotisationAvs(BigDecimal montantDeterminant) {
        return calculCotisationAvs(montantDeterminant,CINQ_CENTIEMES_LES_PLUS_PROCHES);
    }

    /**
     * Retourne uniquement le montant de la cotisation à l'assurance invalidité.
     * @param montantDeterminant le salaire déterminant au sens de la loi sur l'AVS.
     * @return le montant de la cotisation à l'AI (part employé + part employeur).
     */
    BigDecimal calculCotisationAi(BigDecimal montantDeterminant, TypeArrondi arrondi);

    /**
     * Retourne uniquement le montant de la cotisation à l'assurance invalidité.
     * @param montantDeterminant le salaire déterminant au sens de la loi sur l'AVS.
     * @return le montant de la cotisation à l'AI (part employé + part employeur).
     */
    default BigDecimal calculCotisationAi(BigDecimal montantDeterminant) {
        return calculCotisationAi(montantDeterminant, CINQ_CENTIEMES_LES_PLUS_PROCHES);
    }

    /**
     * Retourne uniquement le montant de la cotisation pour les allocations en cas de perte de gains.
     * @param montantDeterminant le salaire déterminant au sens de la loi sur l'AVS.
     * @return le montant de la cotisation à l'APG (part employé + part employeur).
     */
    BigDecimal calculCotisationApg(BigDecimal montantDeterminant, TypeArrondi arrondi);

    /**
     * Retourne uniquement le montant de la cotisation pour les allocations en cas de perte de gains.
     * @param montantDeterminant le salaire déterminant au sens de la loi sur l'AVS.
     * @return le montant de la cotisation à l'APG (part employé + part employeur).
     */
    default BigDecimal calculCotisationApg(BigDecimal montantDeterminant) {
        return calculCotisationApg(montantDeterminant,CINQ_CENTIEMES_LES_PLUS_PROCHES);
    }


}
