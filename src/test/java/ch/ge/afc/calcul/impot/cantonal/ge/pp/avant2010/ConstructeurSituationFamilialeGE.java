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
package ch.ge.afc.calcul.impot.cantonal.ge.pp.avant2010;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import ch.ge.afc.calcul.impot.Souverainete;
import ch.ge.afc.calcul.impot.cantonal.ge.pp.avant2010.SituationFamilialeGE;
import ch.ge.afc.calcul.impot.taxation.pp.EnfantACharge;
import ch.ge.afc.calcul.impot.taxation.pp.PersonneACharge;

public class ConstructeurSituationFamilialeGE {

	public SituationFamilialeGE creerCelibataireSansCharge() {
		SituationFamilialeGE fournisseur = new SituationFamilialeGE() {

			/* (non-Javadoc)
			 * @see ch.ge.afc.calcul.impot.cantonal.ge.pp.FournisseurAssietteRabaisImpot#isCoupleDomicilieGeneve()
			 */
			@Override
			public boolean isCoupleDomicilieGeneve() {
				return false;
			}

			/* (non-Javadoc)
			 * @see ch.ge.afc.calcul.impot.taxation.pp.SituationFamiliale#getEnfants()
			 */
			@Override
			public Set<EnfantACharge> getEnfants() {
				return Collections.emptySet();
			}

			/* (non-Javadoc)
			 * @see ch.ge.afc.calcul.impot.taxation.pp.SituationFamiliale#getPersonnesNecessiteuses()
			 */
			@Override
			public Set<PersonneACharge> getPersonnesNecessiteuses() {
				return Collections.emptySet();
			}

			/* (non-Javadoc)
			 * @see ch.ge.afc.calcul.impot.taxation.pp.SituationFamiliale#isCouple()
			 */
			@Override
			public boolean isCouple() {
				return false;
			}

			/* (non-Javadoc)
			 * @see ch.ge.afc.calcul.impot.cantonal.ge.pp.FournisseurAssietteRabaisImpot#isConjointFonctionnaireInternational()
			 */
			@Override
			public boolean isConjointFonctionnaireInternational() {
				// TODO Auto-generated method stub
				return false;
			}
			
			
			
		};
		return fournisseur;
	}
	
	
	public SituationFamilialeGE creerCelibataireAvecCharge(final int nbreEnfant, final boolean auMoinsUnPetit) {
		SituationFamilialeGE fournisseur = new SituationFamilialeGE() {

			/* (non-Javadoc)
			 * @see ch.ge.afc.calcul.impot.cantonal.ge.pp.FournisseurAssietteRabaisImpot#isCoupleDomicilieGeneve()
			 */
			@Override
			public boolean isCoupleDomicilieGeneve() {
				return true;
			}

			/* (non-Javadoc)
			 * @see ch.ge.afc.calcul.impot.taxation.pp.SituationFamiliale#getEnfants()
			 */
			@Override
			public Set<EnfantACharge> getEnfants() {
				int[] age = new int[nbreEnfant];
				if (auMoinsUnPetit) age[0] = 6;
				else age[0] = 14;
				for (int i = 1; i < nbreEnfant; i++) age[i] = 14;
				return creerEnfant(false,age);
			}

			/* (non-Javadoc)
			 * @see ch.ge.afc.calcul.impot.taxation.pp.SituationFamiliale#getPersonnesNecessiteuses()
			 */
			@Override
			public Set<PersonneACharge> getPersonnesNecessiteuses() {
				return Collections.emptySet();
			}

			/* (non-Javadoc)
			 * @see ch.ge.afc.calcul.impot.taxation.pp.SituationFamiliale#isCouple()
			 */
			@Override
			public boolean isCouple() {
				return false;
			}

			/* (non-Javadoc)
			 * @see ch.ge.afc.calcul.impot.cantonal.ge.pp.FournisseurAssietteRabaisImpot#isConjointFonctionnaireInternational()
			 */
			@Override
			public boolean isConjointFonctionnaireInternational() {
				// TODO Auto-generated method stub
				return false;
			}
			
			
			
		};
		return fournisseur;
	}
	
	public SituationFamilialeGE creerCoupleSansDoubleActiviteSansCharge() {
		SituationFamilialeGE fournisseur = new SituationFamilialeGE() {

			/* (non-Javadoc)
			 * @see ch.ge.afc.calcul.impot.cantonal.ge.pp.FournisseurAssietteRabaisImpot#isCoupleDomicilieGeneve()
			 */
			@Override
			public boolean isCoupleDomicilieGeneve() {
				return true;
			}

			/* (non-Javadoc)
			 * @see ch.ge.afc.calcul.impot.taxation.pp.SituationFamiliale#getEnfants()
			 */
			@Override
			public Set<EnfantACharge> getEnfants() {
				return Collections.emptySet();
			}

			/* (non-Javadoc)
			 * @see ch.ge.afc.calcul.impot.taxation.pp.SituationFamiliale#getPersonnesNecessiteuses()
			 */
			@Override
			public Set<PersonneACharge> getPersonnesNecessiteuses() {
				return Collections.emptySet();
			}

			/* (non-Javadoc)
			 * @see ch.ge.afc.calcul.impot.taxation.pp.SituationFamiliale#isCouple()
			 */
			@Override
			public boolean isCouple() {
				return true;
			}
			/* (non-Javadoc)
			 * @see ch.ge.afc.calcul.impot.cantonal.ge.pp.FournisseurAssietteRabaisImpot#isConjointFonctionnaireInternational()
			 */
			@Override
			public boolean isConjointFonctionnaireInternational() {
				// TODO Auto-generated method stub
				return false;
			}
		};
		return fournisseur;
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

	private Set<EnfantACharge> creerEnfant(final boolean demiChargeICC, final boolean demiChargeIFD, final int... ageEnfant) {
		Set<EnfantACharge> enfants = new HashSet<EnfantACharge>();
		for (final int age : ageEnfant) {
			enfants.add(new EnfantACharge(){

				@Override
				public int getAge(int anneeFiscale) {
					return age;
				}

				@Override
				public boolean isDemiPart(Souverainete souverainete) {
					if (Souverainete.CANTONALE.equals(souverainete)) return demiChargeICC;
					return demiChargeIFD;
				}
				
			});
		}
		return enfants;
	}

	
	
	public SituationFamilialeGE creerCoupleSansDoubleActiviteAvecEnfant(final int nbreEnfant, final boolean auMoinsUnPetit, final boolean domicilieGE) {
		SituationFamilialeGE fournisseur = new SituationFamilialeGE() {

		/* (non-Javadoc)
		 * @see ch.ge.afc.calcul.impot.cantonal.ge.pp.FournisseurAssietteRabaisImpot#isCoupleDomicilieGeneve()
		 */
		@Override
		public boolean isCoupleDomicilieGeneve() {
			return domicilieGE;
		}

		/* (non-Javadoc)
		 * @see ch.ge.afc.calcul.impot.taxation.pp.SituationFamiliale#getEnfants()
		 */
		@Override
		public Set<EnfantACharge> getEnfants() {
			int[] age = new int[nbreEnfant];
			if (auMoinsUnPetit) age[0] = 6;
			else age[0] = 14;
			for (int i = 1; i < nbreEnfant; i++) age[i] = 14;
			return creerEnfant(false,age);
		}

		/* (non-Javadoc)
		 * @see ch.ge.afc.calcul.impot.taxation.pp.SituationFamiliale#getPersonnesNecessiteuses()
		 */
		@Override
		public Set<PersonneACharge> getPersonnesNecessiteuses() {
			return Collections.emptySet();
		}

		/* (non-Javadoc)
		 * @see ch.ge.afc.calcul.impot.taxation.pp.SituationFamiliale#isCouple()
		 */
		@Override
		public boolean isCouple() {
			return true;
		}
		/* (non-Javadoc)
		 * @see ch.ge.afc.calcul.impot.cantonal.ge.pp.FournisseurAssietteRabaisImpot#isConjointFonctionnaireInternational()
		 */
		@Override
		public boolean isConjointFonctionnaireInternational() {
			// TODO Auto-generated method stub
			return false;
		}
	};
	return fournisseur;
	}
	
	public SituationFamilialeGE creerCoupleDontUnFonctionnaireInternational(final int nbreEnfant, final boolean auMoinsUnPetit) {
		SituationFamilialeGE fournisseur = new SituationFamilialeGE() {

			/* (non-Javadoc)
			 * @see ch.ge.afc.calcul.impot.cantonal.ge.pp.FournisseurAssietteRabaisImpot#isCoupleDomicilieGeneve()
			 */
			@Override
			public boolean isCoupleDomicilieGeneve() {
				return false;
			}

			/* (non-Javadoc)
			 * @see ch.ge.afc.calcul.impot.taxation.pp.SituationFamiliale#getEnfants()
			 */
			@Override
			public Set<EnfantACharge> getEnfants() {
				if (0 >= nbreEnfant) return Collections.emptySet();
				int[] age = new int[nbreEnfant];
				if (auMoinsUnPetit) age[0] = 6;
				else age[0] = 14;
				for (int i = 1; i < nbreEnfant; i++) age[i] = 14;
				return creerEnfant(true,false,age);
			}

			/* (non-Javadoc)
			 * @see ch.ge.afc.calcul.impot.taxation.pp.SituationFamiliale#getPersonnesNecessiteuses()
			 */
			@Override
			public Set<PersonneACharge> getPersonnesNecessiteuses() {
				return Collections.emptySet();
			}

			/* (non-Javadoc)
			 * @see ch.ge.afc.calcul.impot.taxation.pp.SituationFamiliale#isCouple()
			 */
			@Override
			public boolean isCouple() {
				return false;
			}
			/* (non-Javadoc)
			 * @see ch.ge.afc.calcul.impot.cantonal.ge.pp.FournisseurAssietteRabaisImpot#isConjointFonctionnaireInternational()
			 */
			@Override
			public boolean isConjointFonctionnaireInternational() {
				return true;
			}
		};
		return fournisseur;
	}
	
	
}
