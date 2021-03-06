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

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;


import org.impotch.calcul.impot.FournisseurAssietteCommunale;
import org.impotch.calcul.impot.taxation.pp.*;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class ProducteurImpotGEAvant2010 extends ProducteurImpotTst {

	@Override
	protected SituationFamiliale creerSituationCelibataireSansEnfant() {
		SituationFamilialeGE situation = new SituationFamilialeGE() {

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
			 * @see org.impotch.calcul.impot.cantonal.ge.pp.SituationFamilialeGE#isConjointFonctionnaireInternational()
			 */
			@Override
			public boolean isConjointFonctionnaireInternational() {
				// TODO Auto-generated method stub
				return false;
			}

			/* (non-Javadoc)
			 * @see org.impotch.calcul.impot.cantonal.ge.pp.SituationFamilialeGE#isCoupleDomicilieGeneve()
			 */
			@Override
			public boolean isCoupleDomicilieGeneve() {
				// TODO Auto-generated method stub
				return false;
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
			};
			
			
		};
		return situation;
	}
	
	@Override
	protected SituationFamiliale creerSituationFamilleAvecEnfant(final int... ageEnfant) {
		SituationFamilialeGE situation = new SituationFamilialeGE() {

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
			 * @see org.impotch.calcul.impot.cantonal.ge.pp.SituationFamilialeGE#isConjointFonctionnaireInternational()
			 */
			@Override
			public boolean isConjointFonctionnaireInternational() {
				return false;
			}

			/* (non-Javadoc)
			 * @see org.impotch.calcul.impot.cantonal.ge.pp.SituationFamilialeGE#isCoupleDomicilieGeneve()
			 */
			@Override
			public boolean isCoupleDomicilieGeneve() {
				return false;
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
		return situation;
	}
	
	protected FournisseurAssiettePeriodique creerAssiettesAvecRabais(final int periodeFiscale, final int montantImposable, final int montantDeterminantRabais) {
		FournisseurAssiettePeriodiqueGE assietteFournisseur = new FournisseurAssiettePeriodiqueGE() {

			@Override
			public BigDecimal getMontantDeterminantRabais() {
				return new BigDecimal(montantDeterminantRabais);
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
				return new BigDecimal(montantImposable);
			}
			@Override
			public BigDecimal getMontantImposable() {
				return new BigDecimal(montantImposable);
			}
			@Override
			public FournisseurAssietteCommunale getFournisseurAssietteCommunale() {
				return ProducteurImpotGEAvant2010.this.creerAssietteCommunaleSurUneSeuleCommune(periodeFiscale,fournisseurLieu.getCommuneGeneve());
			}
			
		};
		return assietteFournisseur;
	}
	

}
