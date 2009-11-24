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
package ch.ge.afc.calcul.impot.cantonal.ge.pp.indexateur;

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
public class SimpleFournisseurIndicePeriodiqueGE implements FournisseurIndicePeriodiqueGE {

	private Map<Integer,BigDecimal> indices = new HashMap<Integer,BigDecimal>(); 
	
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

}
