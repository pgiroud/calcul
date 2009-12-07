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

import java.lang.reflect.Array;

/**
 * Construit un hashcode suivant l'algorithme décrit par Josh Bloch.
 * Typiquement, pour une classe 
 * <pre>
 * 	public class Bareme {
 * 		private int annee;
 * 		private Tranche[] tranches;
 * 		private TypeArrondi typeArrondi;
 * 		....
 * </pre>
 * on utilisera ce builder comme suit :
 * <pre>
 * 	int hashcode = new HashCodeBuilder().add(annee)
 * 						.add(tranches).add(typeArrondi).hash();
 * </pre>
 * 
 * 
 * Attention, il ne s'agit pas ici de construire des fonctions de hachage cryptographique ! 
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class HashCodeBuilder {

	private final int multiplier;
	private int hashcode;
	
	/**
	 * Construit une méthode de hachage avec les constantes indiquées
	 * dans le livre de J. Bloch à savoir : premier terme = 17 et multiplier = 37.
	 */
	public HashCodeBuilder() {
		this(17,37);
	}
	
	public HashCodeBuilder(int firstTerm, int multiplier) {
		this.multiplier = multiplier;
		hashcode = firstTerm;
	}
	
	
	private HashCodeBuilder increment(int value) {
		hashcode = multiplier * hashcode + value;
		return this;
	}
	

	public HashCodeBuilder add(boolean value) {
		return increment(value ? 1 : 0);
	}
	
	public HashCodeBuilder add(char value) {
		return increment((int)value);
	}
	
	public HashCodeBuilder add(int value) {
		// Les valeurs de type byte, short sont traitées par cette méthode
		return increment(value);
	}

	public HashCodeBuilder add(long value) {
		return increment((int)(value ^ (value >>> 32)));
	}

	public HashCodeBuilder add(float value) {
		return increment(Float.floatToIntBits(value));
	}
	
	public HashCodeBuilder add(double value) {
		return add(Double.doubleToLongBits(value));
	}
	
	private boolean isArray(Object value) {
		return value.getClass().isArray();
	}
	
	public HashCodeBuilder add(Object value) {
		if (null == value) {
			return add(0);
		} else {
			if (!isArray(value)) {
				return add(value.hashCode());
			} else {
	            int length = Array.getLength(value);
	            for (int i = 0; i < length; ++i) {
	                Object item = Array.get(value, i);
	                add(item);
	            }
	            return this;
			}
		}
	}
	
	public int hash() {
		return hashcode;
	}
}
