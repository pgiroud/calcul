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
package ch.ge.afc.calcul.impot.cantonal;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import ch.ge.afc.calcul.bareme.Bareme;

public abstract class FournisseurCantonal {

	private ConcurrentMap<Integer, Bareme> mapBaremeRevenu = new ConcurrentHashMap<Integer, Bareme>();
	private ConcurrentMap<Integer, Bareme> baremesFortune = new ConcurrentHashMap<Integer, Bareme>();
	
	public Bareme getBaremeRevenu(int annee) {
		if (!mapBaremeRevenu.containsKey(annee))
			mapBaremeRevenu.putIfAbsent(annee,
					construireBaremeRevenu(annee));
		return mapBaremeRevenu.get(annee);
	}

	protected abstract Bareme construireBaremeRevenu(int annee);
	
	
	public Bareme getBaremeFortune(int annee) {
		if (!baremesFortune.containsKey(annee)) {
			baremesFortune.putIfAbsent(annee, construireBaremeFortune(annee));
		}
		return baremesFortune.get(annee);
	}

	protected abstract Bareme construireBaremeFortune(int annee);
}
