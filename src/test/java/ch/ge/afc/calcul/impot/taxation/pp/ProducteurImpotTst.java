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
package ch.ge.afc.calcul.impot.taxation.pp;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


import ch.ge.afc.calcul.impot.FournisseurAssietteCommunale;
import ch.ge.afc.calcul.impot.Souverainete;
import ch.ge.afc.calcul.impot.taxation.forimposition.ForCommunal;
import ch.ge.afc.calcul.impot.taxation.repart.Part;
import ch.ge.afc.calcul.impot.taxation.repart.Repartition;
import ch.ge.afc.calcul.lieu.FournisseurLieu;
import ch.ge.afc.calcul.lieu.ICommuneSuisse;

public class ProducteurImpotTst {

	protected FournisseurLieu fournisseurLieu = new FournisseurLieu();
	
	protected Set<EnfantACharge> creerEnfant(final int... ageEnfant) {
		Set<EnfantACharge> enfants = new HashSet<EnfantACharge>();
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
		SituationFamiliale situation = new SituationFamiliale() {

			@Override
			public Set<EnfantACharge> getEnfants() {
				return Collections.emptySet();
			}

			@Override
			public boolean isCouple() {
				return false;
			}

			/* (non-Javadoc)
			 * @see ch.ge.afc.calcul.impot.taxation.pp.SituationFamiliale#getPersonnesNecessiteuses()
			 */
			@Override
			public Set<PersonneACharge> getPersonnesNecessiteuses() {
				return Collections.emptySet();
			};
			
			
		};
		return situation;
	}
	
	protected SituationFamiliale creerSituationFamilleAvecEnfant(final int... ageEnfant) {
		SituationFamiliale situation = new SituationFamiliale() {

			@Override
			public Set<EnfantACharge> getEnfants() {
				return creerEnfant(ageEnfant);
			}

			@Override
			public boolean isCouple() {
				return true;
			};

			/* (non-Javadoc)
			 * @see ch.ge.afc.calcul.impot.taxation.pp.SituationFamiliale#getPersonnesNecessiteuses()
			 */
			@Override
			public Set<PersonneACharge> getPersonnesNecessiteuses() {
				return Collections.emptySet();
			};
			
		};
		return situation;
	}
	
	protected FournisseurAssietteCommunale creerAssietteCommunaleSurUneSeuleCommune(final int annee, final ICommuneSuisse commune) {
		return new FournisseurAssietteCommunale() {
			@Override
			public Map<ICommuneSuisse, Integer> getNbreJourDomicileSurCommune() {
				Map<ICommuneSuisse, Integer> map = new HashMap<ICommuneSuisse, Integer>();
				map.put(commune, 360);
				return map;
			}
			@Override
			public int getPeriodeFiscale() {return annee;}
			@Override
			public Repartition<ForCommunal> getRepartition() {
				Repartition<ForCommunal> repart = new Repartition<ForCommunal>();
				repart.ajouterPart(new ForCommunal(commune),new Part(BigDecimal.ONE));
				return repart;
			}
			
		};
	}

	protected FournisseurAssiettePeriodique creerAssiettes(final int periodeFiscale, final int montantImposable) {
		FournisseurAssiettePeriodique assietteFournisseur = new FournisseurAssiettePeriodique() {

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
				return new BigDecimal(montantImposable);
			}

			@Override
			public BigDecimal getMontantImposable() {
				return new BigDecimal(montantImposable);
			}
			
		};
		return assietteFournisseur;
	}
	
	
}
