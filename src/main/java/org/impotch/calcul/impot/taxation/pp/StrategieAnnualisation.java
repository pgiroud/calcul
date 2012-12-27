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
package org.impotch.calcul.impot.taxation.pp;

import java.math.BigDecimal;

public interface StrategieAnnualisation {
	/**
	 * Cette méthode calcule l'impôt pour le nombre de jour donné. En général,
	 * si la période est plus petite qu'une année, l'impôt résultant sera inférieur
	 * à celui passé en paramètre.
	 * <p>
	 * Attention, le résultat de l'annualisation n'est pas arrondi.
	 * @param impotAnnuel l'impôt calculé pour une année
	 * @param nbreJour le nombre de jour
	 * @return l'impôt pour le nombre de jour.
	 */
	BigDecimal annualiseImpot(BigDecimal impotAnnuel, int nbreJour);
}
