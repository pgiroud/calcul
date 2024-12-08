/*
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

import org.impotch.calcul.ReglePeriodique;
import org.impotch.calcul.impot.taxation.pp.*;

import static org.impotch.calcul.impot.taxation.pp.ConstructeurAssiettePeriodique.unConstructeurAssiette;
import static org.impotch.calcul.impot.taxation.pp.ConstructeurSituationFamiliale.couple;
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
		return couple().fournir();
	}
	
	protected FournisseurAssiettePeriodique creerAssiettes(final int periodeFiscale, final BigDecimal montantImposable) {
		return unConstructeurAssiette(periodeFiscale)
				.imposableEtDeterminant(montantImposable)
				.fournir();
	}

}
