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
package org.impotch.calcul.impot.taxation.is;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class Element {
	
	private ITypeMatiereImposable type;
	private BigDecimal montant;
	private Map<String,String> explications = new HashMap<String,String>();

	public ITypeMatiereImposable getType() {
		return type;
	}
	public BigDecimal getMontant() {
		return montant;
	}
	
	public String getExplications(String codeLangue) {
		if (explications.containsKey(codeLangue)) {
			return explications.get(codeLangue);
		} else {
			return "";
		}
	}
}
