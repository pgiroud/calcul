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
package org.impotch.calcul.impot.taxation.pp;

import org.impotch.calcul.impot.Souverainete;

public class Enfant implements EnfantACharge {

    public static Enfant creer(int anneeNaissance) {
		return new Enfant(anneeNaissance);
	}
	
	public static Enfant creerDemiPart(int anneeNaissance) {
		Enfant enfant = new Enfant(anneeNaissance);
		enfant.setDemiPart();
		return enfant;
	}
	
	private final int anneeNaissance;
	private boolean demiPart;
	
	private Enfant(int anneeNaissance) {
		this.anneeNaissance = anneeNaissance;
	}
	
	
	private void setDemiPart() {
		demiPart = true;
	}

	/* (non-Javadoc)
	 * @see Enfant#getAge()
	 */
	@Override
	public int getAge(int anneeFiscale) {
		return anneeFiscale - anneeNaissance;
	}


	/* (non-Javadoc)
	 * @see Enfant#isDemiPart(org.impotch.calcul.impot.Souverainete)
	 */
	@Override
	public boolean isDemiPart(Souverainete souverainete) {
		return demiPart;
	}
	
	
	
	
}
