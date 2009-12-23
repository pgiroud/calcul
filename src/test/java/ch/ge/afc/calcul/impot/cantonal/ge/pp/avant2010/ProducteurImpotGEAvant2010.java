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

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;


import ch.ge.afc.calcul.impot.FournisseurAssietteCommunale;
import ch.ge.afc.calcul.impot.taxation.pp.EnfantACharge;
import ch.ge.afc.calcul.impot.taxation.pp.FournisseurAssiettePeriodique;
import ch.ge.afc.calcul.impot.taxation.pp.PersonneACharge;
import ch.ge.afc.calcul.impot.taxation.pp.ProducteurImpotTst;
import ch.ge.afc.calcul.impot.taxation.pp.SituationFamiliale;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class ProducteurImpotGEAvant2010 extends ProducteurImpotTst {

	@Override
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
	
	@Override
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
