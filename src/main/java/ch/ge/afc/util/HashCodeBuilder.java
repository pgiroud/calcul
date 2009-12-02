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
package ch.ge.afc.util;

/**
 * Construit un hashcode suivant l'algorithme décrit par Josh Bloch.
 * 
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class HashCodeBuilder {

	private int hashcode = 17;
	
	private HashCodeBuilder increment(int valeur) {
		hashcode = 37 * hashcode + valeur;
		return this;
	}
	

	public HashCodeBuilder booleen(boolean valeur) {
		return increment(valeur ? 1 : 0);
	}
	
	public HashCodeBuilder entier(int valeur) {
		return increment(valeur);
	}

	public HashCodeBuilder entierLong(long valeur) {
		return increment((int)(valeur ^ (valeur >>> 32)));
	}

	public HashCodeBuilder simplePrecision(float valeur) {
		return increment(Float.floatToIntBits(valeur));
	}
	
	public HashCodeBuilder doublePrecision(double valeur) {
		return entierLong(Double.doubleToLongBits(valeur));
	}
	
	public HashCodeBuilder objet(Object valeur) {
		return entierLong(valeur.hashCode());
	}
	
	public int hash() {
		return hashcode;
	}
}
