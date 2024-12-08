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

import java.math.BigDecimal;
import java.util.Optional;

import org.impotch.calcul.impot.taxation.pp.FournisseurAssiettePeriodique;

/**
 * Dans le cadre du rabais d'impôt, il convient d'indiquer une assiette supplémentaire
 * qui permettra de calculer le montant du rabais d'impôt.
 * 
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public interface FournisseurAssiettePeriodiqueGE extends
		FournisseurAssiettePeriodique {
	
	/**
	 * Fournit le montant déterminant pour le calcul du rabais d'impôt.
	 * Il s'agit en quelque sorte d'un minimum vital dépendant de la
	 * composition de la famille.
	 * @return le montant déterminant utilisé pour calculer le rabais d'impôt.
	 */
	BigDecimal getMontantDeterminantRabais();
}
