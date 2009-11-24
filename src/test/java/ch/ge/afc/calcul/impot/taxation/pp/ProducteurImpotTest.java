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

import org.junit.Ignore;

import ch.ge.afc.calcul.impot.FournisseurAssietteCommunale;
import ch.ge.afc.calcul.impot.Souverainete;
import ch.ge.afc.calcul.impot.cantonal.ge.pp.avant2010.FournisseurAssiettePeriodiqueGE;
import ch.ge.afc.calcul.impot.cantonal.ge.pp.avant2010.SituationFamilialeGE;
import ch.ge.afc.calcul.impot.taxation.forimposition.ForCommunal;
import ch.ge.afc.calcul.impot.taxation.repart.Part;
import ch.ge.afc.calcul.impot.taxation.repart.Repartition;
import ch.ge.afc.calcul.lieu.FournisseurLieu;
import ch.ge.afc.calcul.lieu.ICommuneSuisse;

@Ignore
public class ProducteurImpotTest {

	private FournisseurLieu fournisseurLieu = new FournisseurLieu();
	
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
		SituationFamilialeGE situation = new SituationFamilialeGE() {

			/* (non-Javadoc)
			 * @see ch.ge.afc.calcul.impot.cantonal.ge.pp.SituationFamilialeGE#isConjointFonctionnaireInternational()
			 */
			@Override
			public boolean isConjointFonctionnaireInternational() {
				// TODO Auto-generated method stub
				return false;
			}

			/* (non-Javadoc)
			 * @see ch.ge.afc.calcul.impot.cantonal.ge.pp.SituationFamilialeGE#isCoupleDomicilieGeneve()
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
		SituationFamilialeGE situation = new SituationFamilialeGE() {

			/* (non-Javadoc)
			 * @see ch.ge.afc.calcul.impot.cantonal.ge.pp.SituationFamilialeGE#isConjointFonctionnaireInternational()
			 */
			@Override
			public boolean isConjointFonctionnaireInternational() {
				return false;
			}

			/* (non-Javadoc)
			 * @see ch.ge.afc.calcul.impot.cantonal.ge.pp.SituationFamilialeGE#isCoupleDomicilieGeneve()
			 */
			@Override
			public boolean isCoupleDomicilieGeneve() {
				return false;
			}

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
				return ProducteurImpotTest.this.creerAssietteCommunaleSurUneSeuleCommune(periodeFiscale,fournisseurLieu.getCommuneGeneve());
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
	
	protected FournisseurAssiettePeriodique creerAssiettes(final int periodeFiscale, final int montantImposable, final int montantDeterminantRabais) {
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
				return ProducteurImpotTest.this.creerAssietteCommunaleSurUneSeuleCommune(periodeFiscale,fournisseurLieu.getCommuneGeneve());
			}
			
		};
		return assietteFournisseur;
	}
	
	
}
