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
package org.impotch.calcul.impot.cantonal.ge.pp;

import java.util.stream.IntStream;
import org.impotch.calcul.impot.taxation.pp.SituationFamiliale;

import static org.impotch.calcul.impot.taxation.pp.ConstructeurSituationFamiliale.personneSeule;
import static org.impotch.calcul.impot.taxation.pp.ConstructeurSituationFamiliale.couple;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class ConstructeurSituationFamiliale {

	public SituationFamiliale creerCelibataireSansCharge() {
		return personneSeule().fournir();
	}

	public SituationFamiliale creerPersonneSeuleAvecEnfants(final int nbreEnfant, final int periodeFiscale) {
		org.impotch.calcul.impot.taxation.pp.ConstructeurSituationFamiliale cons = personneSeule();
		IntStream.rangeClosed(1,nbreEnfant).forEach(
				n -> cons.enfant().age(14).aFin(periodeFiscale)
		);
		return cons.fournir();
	}

	public SituationFamiliale creerCoupleSansCharge() {
		return couple().fournir();
	}
	
	public SituationFamiliale creerCoupleAvecEnfant(final int nbreEnfant, int periodeFiscale) {
		org.impotch.calcul.impot.taxation.pp.ConstructeurSituationFamiliale cons = couple();
		IntStream.rangeClosed(1,nbreEnfant).forEach(
				n -> cons.enfant().age(14).aFin(periodeFiscale)
		);
		return cons.fournir();
	}

}
