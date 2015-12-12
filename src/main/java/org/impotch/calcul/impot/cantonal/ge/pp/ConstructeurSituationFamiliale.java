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

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.impotch.calcul.impot.Souverainete;
import org.impotch.calcul.impot.taxation.pp.Contribuable;
import org.impotch.calcul.impot.taxation.pp.EnfantACharge;
import org.impotch.calcul.impot.taxation.pp.PersonneACharge;
import org.impotch.calcul.impot.taxation.pp.SituationFamiliale;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class ConstructeurSituationFamiliale {

	public SituationFamiliale creerCelibataireSansCharge() {
		SituationFamiliale situation = new SituationFamiliale() {

            @Override
            public Contribuable getContribuable() {
                return new Contribuable() {
                };
            }

            @Override
            public Optional<Contribuable> getConjoint() {
                return Optional.empty();
            }

            @Override
			public Set<EnfantACharge> getEnfants() {return Collections.emptySet();}

			@Override
			public Set<PersonneACharge> getPersonnesNecessiteuses() {return Collections.emptySet();}

		};
		return situation;
	}
	
	public SituationFamiliale creerCoupleSansCharge() {
		SituationFamiliale situation = new SituationFamiliale() {

            @Override
            public Contribuable getContribuable() {
                return new Contribuable() {
                };
            }

            @Override
            public Optional<Contribuable> getConjoint() {
                return Optional.of(new Contribuable() {
                });
            }

			@Override
			public Set<EnfantACharge> getEnfants() {return Collections.emptySet();}

			@Override
			public Set<PersonneACharge> getPersonnesNecessiteuses() {return Collections.emptySet();}

		};
		return situation;
	}
	
	public SituationFamiliale creerCoupleAvecEnfant(final int nbreEnfant) {
		SituationFamiliale situation = new SituationFamiliale() {
            @Override
            public Contribuable getContribuable() {
                return new Contribuable() {
                };
            }

            @Override
            public Optional<Contribuable> getConjoint() {
                return Optional.of(new Contribuable() {
                });
            }

			@Override
			public Set<EnfantACharge> getEnfants() {
				int[] age = new int[nbreEnfant];
				for (int i = 0; i < nbreEnfant; i++) age[i] = 14;
				return creerEnfant(false,age);
			}
			
			@Override
			public Set<PersonneACharge> getPersonnesNecessiteuses() {return Collections.emptySet();}

		};
		return situation;
	}
	
	private Set<EnfantACharge> creerEnfant(final boolean demiCharge, final int... ageEnfant) {
		Set<EnfantACharge> enfants = new HashSet<EnfantACharge>();
		for (final int age : ageEnfant) {
			enfants.add(new EnfantACharge(){

				@Override
				public int getAge(int anneeFiscale) {
					return age;
				}

				@Override
				public boolean isDemiPart(Souverainete souverainete) {
					return demiCharge;
				}
				
			});
		}
		return enfants;
	}

	
}
