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
package org.impotch.calcul.impot.cantonal.ge.param.dao;

import org.impotch.calcul.impot.taxation.forimposition.ForCommunal;
import org.impotch.calcul.impot.taxation.repart.Repartition;
import org.impotch.calcul.lieu.ICommuneSuisse;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Fournit les paramètres communaux nécessaires au calcul des impôts communaux.
 * 
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public interface ParametreCommunalDao extends ResidentParCommuneDao {
	
	/**
	 * Renvoie la part privilégiée. Il s'agit d'un nombre compris entre 0.2 et 0.8.
	 * Voir article 295A de la <a href="http://www.geneve.ch/legislation/rsg/f/rsg_d3_05.html">LCP</a>. 
	 * @param annee L'année pour laquelle on veut la part privilégiée.
	 * @param noOFSCommune Le numéro de commune défini par l'Office fédéral de la statistique.
	 * @return le taux de la part privilégiée
	 */
	BigDecimal getPartPrivilegiee(int annee, int noOFSCommune);
	
	/**
	 * Renvoie les centimes additionnels communaux.
	 * @param annee L'année pour laquelle on veut les centimes additionnels
	 * @param noOFSCommune Le numéro de commune défini par l'Office fédéral de la statistique.
	 * @return les centimes additionnels.
	 */
	BigDecimal getTauxCentimes(int annee, int noOFSCommune);
}
