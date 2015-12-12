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
package org.impotch.calcul.impot.cantonal.ge.pp.avant2010;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.impotch.calcul.impot.Souverainete;
import org.impotch.calcul.impot.taxation.pp.Contribuable;
import org.impotch.calcul.impot.taxation.pp.EnfantACharge;
import org.impotch.calcul.impot.taxation.pp.PersonneACharge;

public class ConstructeurSituationFamilialeGE {

    public SituationFamilialeGE creerCelibataireSansCharge() {
        SituationFamilialeGE fournisseur = new SituationFamilialeGE() {

            @Override
            public Contribuable getContribuable() {
                return new Contribuable() {
                };
            }

            @Override
            public Optional<Contribuable> getConjoint() {
                return Optional.empty();
            }

            /* (non-Javadoc)
             * @see org.impotch.calcul.impot.cantonal.ge.pp.FournisseurAssietteRabaisImpot#isCoupleDomicilieGeneve()
			 */
            @Override
            public boolean isCoupleDomicilieGeneve() {
                return false;
            }

            /* (non-Javadoc)
             * @see SituationFamiliale#getEnfants()
             */
            @Override
            public Set<EnfantACharge> getEnfants() {
                return Collections.emptySet();
            }

            /* (non-Javadoc)
             * @see SituationFamiliale#getPersonnesNecessiteuses()
             */
            @Override
            public Set<PersonneACharge> getPersonnesNecessiteuses() {
                return Collections.emptySet();
            }

            /* (non-Javadoc)
             * @see org.impotch.calcul.impot.cantonal.ge.pp.FournisseurAssietteRabaisImpot#isConjointFonctionnaireInternational()
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

            @Override
            public Contribuable getContribuable() {
                return new Contribuable() {
                };
            }

            @Override
            public Optional<Contribuable> getConjoint() {
                return Optional.empty();
            }

            /* (non-Javadoc)
			 * @see org.impotch.calcul.impot.cantonal.ge.pp.FournisseurAssietteRabaisImpot#isCoupleDomicilieGeneve()
			 */
            @Override
            public boolean isCoupleDomicilieGeneve() {
                return true;
            }

            /* (non-Javadoc)
             * @see SituationFamiliale#getEnfants()
             */
            @Override
            public Set<EnfantACharge> getEnfants() {
                int[] age = new int[nbreEnfant];
                if (auMoinsUnPetit) age[0] = 6;
                else age[0] = 14;
                for (int i = 1; i < nbreEnfant; i++) age[i] = 14;
                return creerEnfant(false, age);
            }

            /* (non-Javadoc)
             * @see SituationFamiliale#getPersonnesNecessiteuses()
             */
            @Override
            public Set<PersonneACharge> getPersonnesNecessiteuses() {
                return Collections.emptySet();
            }

            /* (non-Javadoc)
             * @see org.impotch.calcul.impot.cantonal.ge.pp.FournisseurAssietteRabaisImpot#isConjointFonctionnaireInternational()
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

            /* (non-Javadoc)
			 * @see org.impotch.calcul.impot.cantonal.ge.pp.FournisseurAssietteRabaisImpot#isCoupleDomicilieGeneve()
			 */
            @Override
            public boolean isCoupleDomicilieGeneve() {
                return true;
            }

            /* (non-Javadoc)
             * @see SituationFamiliale#getEnfants()
             */
            @Override
            public Set<EnfantACharge> getEnfants() {
                return Collections.emptySet();
            }

            /* (non-Javadoc)
             * @see SituationFamiliale#getPersonnesNecessiteuses()
             */
            @Override
            public Set<PersonneACharge> getPersonnesNecessiteuses() {
                return Collections.emptySet();
            }

            /* (non-Javadoc)
             * @see org.impotch.calcul.impot.cantonal.ge.pp.FournisseurAssietteRabaisImpot#isConjointFonctionnaireInternational()
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
            enfants.add(new EnfantACharge() {

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
            enfants.add(new EnfantACharge() {

                @Override
                public int getAge(int anneeFiscale) {
                    return age;
                }

                @Override
                public boolean isDemiPart(Souverainete souverainete) {
                    if (Souverainete.CH_CANTONALE_GE.equals(souverainete)) return demiChargeICC;
                    return demiChargeIFD;
                }

            });
        }
        return enfants;
    }


    public SituationFamilialeGE creerCoupleSansDoubleActiviteAvecEnfant(final int nbreEnfant, final boolean auMoinsUnPetit, final boolean domicilieGE) {
        SituationFamilialeGE fournisseur = new SituationFamilialeGE() {

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

            /* (non-Javadoc)
		 * @see org.impotch.calcul.impot.cantonal.ge.pp.FournisseurAssietteRabaisImpot#isCoupleDomicilieGeneve()
		 */
            @Override
            public boolean isCoupleDomicilieGeneve() {
                return domicilieGE;
            }

            /* (non-Javadoc)
             * @see SituationFamiliale#getEnfants()
             */
            @Override
            public Set<EnfantACharge> getEnfants() {
                int[] age = new int[nbreEnfant];
                if (auMoinsUnPetit) age[0] = 6;
                else age[0] = 14;
                for (int i = 1; i < nbreEnfant; i++) age[i] = 14;
                return creerEnfant(false, age);
            }

            /* (non-Javadoc)
             * @see SituationFamiliale#getPersonnesNecessiteuses()
             */
            @Override
            public Set<PersonneACharge> getPersonnesNecessiteuses() {
                return Collections.emptySet();
            }

            /* (non-Javadoc)
             * @see org.impotch.calcul.impot.cantonal.ge.pp.FournisseurAssietteRabaisImpot#isConjointFonctionnaireInternational()
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

            /* (non-Javadoc)
			 * @see org.impotch.calcul.impot.cantonal.ge.pp.FournisseurAssietteRabaisImpot#isCoupleDomicilieGeneve()
			 */
            @Override
            public boolean isCoupleDomicilieGeneve() {
                return false;
            }

            /* (non-Javadoc)
             * @see SituationFamiliale#getEnfants()
             */
            @Override
            public Set<EnfantACharge> getEnfants() {
                if (0 >= nbreEnfant) return Collections.emptySet();
                int[] age = new int[nbreEnfant];
                if (auMoinsUnPetit) age[0] = 6;
                else age[0] = 14;
                for (int i = 1; i < nbreEnfant; i++) age[i] = 14;
                return creerEnfant(true, false, age);
            }

            /* (non-Javadoc)
             * @see SituationFamiliale#getPersonnesNecessiteuses()
             */
            @Override
            public Set<PersonneACharge> getPersonnesNecessiteuses() {
                return Collections.emptySet();
            }

            /* (non-Javadoc)
             * @see SituationFamiliale#isCouple()
             */
            @Override
            public boolean isCouple() {
                return false;
            }

            /* (non-Javadoc)
             * @see org.impotch.calcul.impot.cantonal.ge.pp.FournisseurAssietteRabaisImpot#isConjointFonctionnaireInternational()
             */
            @Override
            public boolean isConjointFonctionnaireInternational() {
                return true;
            }
        };
        return fournisseur;
    }


}
