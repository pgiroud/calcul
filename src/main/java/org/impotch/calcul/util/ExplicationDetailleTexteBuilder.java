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
package org.impotch.calcul.util;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class ExplicationDetailleTexteBuilder implements IExplicationDetailleeBuilder {

	private StringBuilder builder = new StringBuilder();
	private StringBuilder ligne = null;
	
	@Override
	public void ajouter(String texte) {
		if (null == ligne) ligne = new StringBuilder(texte);
		else ligne.append("\t").append(texte);
	}

	@Override
	public void nouvelleLigne() {
		if (null != ligne) builder.append(ligne);
		builder.append("\n");
		ligne = null;
	}
	
	@Override
	public String getTexte() {
		if (null != ligne) nouvelleLigne();
		return builder.toString();
	}

	@Override
	public void reset() {
		builder = new StringBuilder();
		ligne = null;
	}
}
