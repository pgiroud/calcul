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

import java.math.BigDecimal;
import java.util.*;


import org.impotch.calcul.impot.FournisseurAssietteCommunale;
import org.impotch.calcul.impot.Souverainete;
import org.impotch.calcul.impot.taxation.forimposition.ForCommunal;
import org.impotch.calcul.impot.taxation.repart.Part;
import org.impotch.calcul.impot.taxation.repart.Repartition;
import org.impotch.calcul.lieu.FournisseurLieu;
import org.impotch.calcul.lieu.ICommuneSuisse;

public class ProducteurImpotTst {

	protected FournisseurLieu fournisseurLieu = new FournisseurLieu();
	
	protected Set<EnfantACharge> creerEnfant(final int... ageEnfant) {
		Set<EnfantACharge> enfants = new HashSet<>();
		for (final int age : ageEnfant) {
			enfants.add(new EnfantACharge(){

				@Override
				public int getAge(int anneeFiscale) {
					return age;
				}

				@Override
				public boolean isDemiPart(Souverainete souverainete) {
					return false;
				}
				
			});
		}
		return enfants;
	}
	
	protected SituationFamiliale creerSituationCelibataireSansEnfant() {
		return new SituationFamiliale() {

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
		};
	}
	
	protected SituationFamiliale creerSituationFamilleAvecEnfant(final int... ageEnfant) {
		return new SituationFamiliale() {

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
				return creerEnfant(ageEnfant);
			}

			/* (non-Javadoc)
			 * @see SituationFamiliale#getPersonnesNecessiteuses()
			 */
			@Override
			public Set<PersonneACharge> getPersonnesNecessiteuses() {
				return Collections.emptySet();
			};
			
		};
	}
	
	protected FournisseurAssietteCommunale creerAssietteCommunaleSurUneSeuleCommune(final int annee, final ICommuneSuisse commune) {
		return new FournisseurAssietteCommunale() {
			@Override
			public Map<ICommuneSuisse, Integer> getNbreJourDomicileSurCommune() {
				Map<ICommuneSuisse, Integer> map = new HashMap<>();
				map.put(commune, 360);
				return map;
			}
			@Override
			public int getPeriodeFiscale() {return annee;}
			@Override
			public Repartition<ForCommunal> getRepartition() {
				Repartition<ForCommunal> repart = new Repartition<>();
				repart.ajouterPart(new ForCommunal(commune),new Part(BigDecimal.ONE));
				return repart;
			}
			
		};
	}

	protected FournisseurAssiettePeriodique creerAssiettes(final int periodeFiscale, final int montantImposable) {
		return 	creerAssiettes(periodeFiscale,montantImposable,montantImposable);
	}

	protected FournisseurAssiettePeriodique creerAssiettes(final int periodeFiscale, final int montantImposable, final int montantDeterminant) {
		return new FournisseurAssiettePeriodique() {

			@Override
			public FournisseurAssietteCommunale getFournisseurAssietteCommunale() {
				return ProducteurImpotTst.this.creerAssietteCommunaleSurUneSeuleCommune(periodeFiscale,fournisseurLieu.getCommuneGeneve());
			}

			@Override
			public int getNombreJourPourAnnualisation() {
				return 360;
			}

			@Override
			public int getPeriodeFiscale() {
				return periodeFiscale;
			}

			@Override
			public BigDecimal getMontantDeterminant() {
				return new BigDecimal(montantDeterminant);
			}

			@Override
			public BigDecimal getMontantImposable() {
				return new BigDecimal(montantImposable);
			}
			
		};
	}
	
	
}
