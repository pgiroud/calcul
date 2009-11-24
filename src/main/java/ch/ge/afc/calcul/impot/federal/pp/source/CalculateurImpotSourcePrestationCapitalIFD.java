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
package ch.ge.afc.calcul.impot.federal.pp.source;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;

import ch.ge.afc.calcul.ReglePeriodique;
import ch.ge.afc.calcul.impot.FournisseurAssietteCommunale;
import ch.ge.afc.calcul.impot.taxation.pp.EnfantACharge;
import ch.ge.afc.calcul.impot.taxation.pp.FournisseurAssiettePeriodique;
import ch.ge.afc.calcul.impot.taxation.pp.PersonneACharge;
import ch.ge.afc.calcul.impot.taxation.pp.ProducteurImpot;
import ch.ge.afc.calcul.impot.taxation.pp.RecepteurImpotSomme;
import ch.ge.afc.calcul.impot.taxation.pp.SituationFamiliale;

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
			public Set<EnfantACharge> getEnfants() {return Collections.emptySet();}

			@Override
			public Set<PersonneACharge> getPersonnesNecessiteuses() {return Collections.emptySet();}

			@Override
			public boolean isCouple() {return true;}
			
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
