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
package org.impotch.calcul.eco.indice.ge;

import java.math.BigDecimal;
import java.util.List;

public interface FournisseurIndicePrixConsommation {
	
	BigDecimal fournir(BaseIndiceGenevoisPrixConsommation base, int annee, int mois);
	
	List<BigDecimal> fournir(BaseIndiceGenevoisPrixConsommation base, int anneeDebut, int moisDebut, int anneeFin, int moisFin);
	
	BigDecimal fournirMoyenne(BaseIndiceGenevoisPrixConsommation base, int anneeDebut, int moisDebut, int anneeFin, int moisFin);
}
