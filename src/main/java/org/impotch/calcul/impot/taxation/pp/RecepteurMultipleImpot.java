/*
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
package org.impotch.calcul.impot.taxation.pp;

import java.util.HashMap;
import java.util.Map;

import org.impotch.calcul.impot.Impot;
import org.impotch.calcul.impot.RecepteurImpot;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class RecepteurMultipleImpot implements RecepteurImpot {

	private Map<String,RecepteurImpot> map = new HashMap<String,RecepteurImpot>();
	
	public void ajouteRecepteur(String nom, RecepteurImpot recepteur) {
		map.put(nom, recepteur);
	}
	
	public RecepteurImpot getRecepteur(String nom) {
		return map.get(nom);
	}
	
	@Override
	public void ajouteImpot(Impot impot) {
		for (RecepteurImpot recepteur : map.values()) {
			recepteur.ajouteImpot(impot);
		}
	}
	
}
