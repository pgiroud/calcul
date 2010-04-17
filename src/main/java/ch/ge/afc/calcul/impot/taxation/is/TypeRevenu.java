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
package ch.ge.afc.calcul.impot.taxation.is;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class TypeRevenu implements ITypeMatiereImposable {

	private Map<String,String> libelles = new HashMap<String,String>();

	public TypeRevenu() {
		libelles.put("fr", "XXXXX");
	}
	
	public void ajouterLibelle(String codeLangue, String libelle) {
		libelles.put(codeLangue, libelle);
	}
	
	@Override
	public String getLibelle(String codeLangue) {
		if (libelles.containsKey(codeLangue)) {
			return libelles.get(codeLangue);
		}
		return libelles.get("fr");
	}
	
	
}
