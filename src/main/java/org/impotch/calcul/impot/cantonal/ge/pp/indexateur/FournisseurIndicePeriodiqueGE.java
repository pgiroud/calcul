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
package org.impotch.calcul.impot.cantonal.ge.pp.indexateur;

import java.math.BigDecimal;

/**
 * Fournit des indices périodiques. Dans le cadre fiscal, ces indices sont très souvent
 * des indices de prix à la consommation.
 * Ces indices sont en général définit tous les mois. Le propos de cette interface est de
 * fournir un indice périodique annuel (en procédant à une moyenne par exemple).
 * 
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public interface FournisseurIndicePeriodiqueGE {
	/**
	 * Retourne un indice pour la période (année) donnée
	 * @param periodeFiscale l'année fiscale
	 * @return un indice servant à l'indexation.
	 */
	BigDecimal getIndice(int periodeFiscale);
}
