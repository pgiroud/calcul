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
import org.impotch.calcul.impot.PeriodeFiscale;
import org.impotch.calcul.impot.Souverainete;
import org.impotch.calcul.impot.taxation.forimposition.ForCommunal;
import org.impotch.calcul.impot.taxation.repart.Part;
import org.impotch.calcul.impot.taxation.repart.Repartition;
import org.impotch.calcul.lieu.FournisseurLieu;
import org.impotch.calcul.lieu.ICommuneSuisse;

import static org.impotch.calcul.impot.taxation.pp.ConstructeurSituationFamiliale.personneSeule;
import static org.impotch.calcul.impot.taxation.pp.ConstructeurSituationFamiliale.couple;
import static org.impotch.calcul.impot.taxation.forimposition.ForCommunal.forCommunal;

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
		return personneSeule().fournir();
	}
	
	protected SituationFamiliale creerSituationFamilleAvecEnfant(PeriodeFiscale periode, final int... ageEnfant) {
		ConstructeurSituationFamiliale cons = couple();
		for (int a : ageEnfant) {
			cons.enfant().age(a).aFin(periode.annee());
		}
		return cons.fournir();
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
			public PeriodeFiscale getPeriodeFiscale() {return PeriodeFiscale.annee(annee);}
			@Override
			public Repartition<ForCommunal> getRepartition() {
				Repartition<ForCommunal> repart = new Repartition<>();
				repart.ajouterPart(forCommunal(commune),new Part(BigDecimal.ONE));
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
			public Optional<FournisseurAssietteCommunale> getFournisseurAssietteCommunale() {
				return Optional.of(ProducteurImpotTst.this.creerAssietteCommunaleSurUneSeuleCommune(periodeFiscale,fournisseurLieu.getCommuneGeneve()));
			}

			@Override
			public int getNombreJourPourAnnualisation() {
				return 360;
			}

			@Override
			public PeriodeFiscale getPeriodeFiscale() {
				return PeriodeFiscale.annee(periodeFiscale);
			}

			@Override
			public Optional<BigDecimal> getMontantDeterminant() {
				return Optional.of(BigDecimal.valueOf(montantDeterminant));
			}

			@Override
			public BigDecimal getMontantImposable() {
				return new BigDecimal(montantImposable);
			}
			
		};
	}
	
	
}
