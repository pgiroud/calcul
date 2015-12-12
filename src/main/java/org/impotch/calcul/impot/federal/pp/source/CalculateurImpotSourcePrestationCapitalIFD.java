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
package org.impotch.calcul.impot.federal.pp.source;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import org.impotch.calcul.ReglePeriodique;
import org.impotch.calcul.impot.FournisseurAssietteCommunale;
import org.impotch.calcul.impot.taxation.pp.*;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class CalculateurImpotSourcePrestationCapitalIFD extends ReglePeriodique {
	private ProducteurImpot producteur;
	private SituationFamiliale situation;
	
	public CalculateurImpotSourcePrestationCapitalIFD(int annee) {
		super(annee);
		situation = creerSituation();
	}

	public void setProducteurImpot(ProducteurImpot producteur) {
		this.producteur = producteur;
	}
	
	public BigDecimal calcul(BigDecimal montantPrestation) {
		// On ne tient pas compte de la situation familiale, on prend toujours
		// le barème marié
		FournisseurAssiettePeriodique fournisseur = this.creerAssiettes(this.getAnnee(), montantPrestation);
		RecepteurImpotSomme recepteur = new RecepteurImpotSomme();
		
		producteur.produireImpot(situation, fournisseur, recepteur);
		return recepteur.getValeur();
		
	}
	
	private SituationFamiliale creerSituation() {
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
			public Set<EnfantACharge> getEnfants() {return Collections.emptySet();}

			@Override
			public Set<PersonneACharge> getPersonnesNecessiteuses() {return Collections.emptySet();}

		};
	}
	
	protected FournisseurAssiettePeriodique creerAssiettes(final int periodeFiscale, final BigDecimal montantImposable) {
		FournisseurAssiettePeriodique assietteFournisseur = new FournisseurAssiettePeriodique() {

			@Override
			public FournisseurAssietteCommunale getFournisseurAssietteCommunale() {
				return null;
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
				return montantImposable;
			}

			@Override
			public BigDecimal getMontantImposable() {
				return montantImposable;
			}
			
		};
		return assietteFournisseur;
	}

}
