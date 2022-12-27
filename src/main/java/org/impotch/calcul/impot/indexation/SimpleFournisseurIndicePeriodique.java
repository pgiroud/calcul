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
package org.impotch.calcul.impot.indexation;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Fournit les indices par période fiscale.
 * 
 * L'implémentation est basée sur un simple dictionnaire dont les entrées sont les
 * années et les valeurs les indices.
 * 
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class SimpleFournisseurIndicePeriodique implements FournisseurIndicePeriodique {

	private Map<Integer,BigDecimal> indices = new HashMap<>();
	
	/**
	 * Spécifie les indices par année. Attention, il ne s'agit pas de moyenne annuel d'indices
	 * mensuels.
	 * @param nIndices les indices permettant l'indexation.
	 */
	public void setIndices(Map<Integer, BigDecimal> nIndices) {
		indices = nIndices;
	}



	@Override
	public BigDecimal getIndice(int periodeFiscale) {
		return indices.get(periodeFiscale);
	}

	public static class Constructeur {

		private final Map<Integer,BigDecimal> indices = new HashMap<>();
		private int annee = -1;

		public Constructeur pour(int annee) {
			this.annee = annee;
			return this;
		}

		public Constructeur valeur(String valeur) {
			if (-1 == annee) {
				throw new RuntimeException("Il faut absolument commencer par précise une année !");
			}
			// TODO ajouter exception si on met n'importe quoi dans la valeur!
			indices.put(annee,new BigDecimal(valeur));
			annee = -1;
			return this;
		}

		public SimpleFournisseurIndicePeriodique cons() {
			SimpleFournisseurIndicePeriodique four = new SimpleFournisseurIndicePeriodique();
			four.setIndices(indices);
			return four;
		}
	}

}
