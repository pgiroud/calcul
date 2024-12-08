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
package org.impotch.calcul.impot.cantonal.ge.pp.avant2010;

import org.impotch.calcul.impot.taxation.pp.SituationFamiliale;

/**
 * Permet de calculer les rabais d'impôt dans des cas particulier.
 * 
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public interface SituationFamilialeGE extends SituationFamiliale {
	/**
	 * Indique si le couple est domicilié dans le cantonal de Genève.
	 * Utile pour déterminer les frais de garde.
	 * @return true si le couple est domicilié sur le cantonal de Genève
	 */
	boolean isCoupleDomicilieGeneve();
	
	/**
	 * Retourne vrai si le contribuable est le conjoint d'un fonctionnaire
	 * international auquel cas, les charges de famille sont comptées pour
	 * moitié dans le rabais d'impôt.
	 * @return true si le contribuable est le conjoint d'un fonctionnaire international.
	 */
	boolean isConjointFonctionnaireInternational();
}
