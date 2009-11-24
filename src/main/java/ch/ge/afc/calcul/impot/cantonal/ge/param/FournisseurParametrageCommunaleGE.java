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
package ch.ge.afc.calcul.impot.cantonal.ge.param;

import java.math.BigDecimal;

import ch.ge.afc.calcul.lieu.ICommuneSuisse;

/**
 * Fournit les paramètres communaux pour les communes du canton de Genève.
 * 
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public interface FournisseurParametrageCommunaleGE {
	
	/**
	 * Renvoie la part privilégiée. Il s'agit d'un nombre compris entre 0.2 et 0.8.
	 * Voir article 295A de la <a href="http://www.geneve.ch/legislation/rsg/f/rsg_d3_05.html">LCP</a>. 
	 * @param annee L'année pour laquelle on veut la part privilégiée.
	 * @param commune La commune genevoise.
	 * @return le taux de la part privilégiée
	 */
	BigDecimal getPartPrivilegiee(int annee, ICommuneSuisse commune);
	
	/**
	 * Renvoie les centimes additionnels communaux.
	 * @param annee L'année pour laquelle on veut les centimes additionnels
	 * @param commune La commune genevoise.
	 * @return les centimes additionnels.
	 */
	BigDecimal getTauxCentimes(int annee, ICommuneSuisse commune);
}
