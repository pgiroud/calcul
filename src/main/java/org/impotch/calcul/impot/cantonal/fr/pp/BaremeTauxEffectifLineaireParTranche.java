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
package org.impotch.calcul.impot.cantonal.fr.pp;

import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;

import org.impotch.bareme.BaremeTauxEffectifParTranche;
import org.impotch.bareme.TrancheBareme;
import org.impotch.util.BigDecimalUtil;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class BaremeTauxEffectifLineaireParTranche extends BaremeTauxEffectifParTranche {

	public BigDecimal getMontantImposablePremiereTranche() {
		return getTranches().get(0).getMontantMaxTranche();
	}
	
	public BigDecimal getTauxMinimum() {
		for (TrancheBareme tranche : getTranches()) {
			if (0 < tranche.getTauxOuMontant().compareTo(ZERO)) return tranche.getTauxOuMontant();
		}
		throw new IllegalStateException("Il faut qu'il existe des tranches avant d'invoquer cette méthode !!");
	}
	
	public void ajouterTranche(int montant, String taux, String tauxEnPlusPar100Francs) {
		ajouterTranche(new BigDecimal(montant),
				BigDecimalUtil.parseTaux(taux),BigDecimalUtil.parseTaux(tauxEnPlusPar100Francs).movePointLeft(2));
	}
	
	private void ajouterTranche(BigDecimal montant, BigDecimal taux, BigDecimal tauxEnPlusPar100Francs) {
		getTranches().add(new TrancheBaremeLineaire(montant,taux,tauxEnPlusPar100Francs));
	}
	
	public void ajouterDerniereTranche(String taux) {
		getTranches().add(new TrancheBaremeLineaire.DerniereTrancheBaremeLineaire(BigDecimalUtil.parseTaux(taux)));
	}
	
	/* (non-Javadoc)
	 * @see ch.ge.afc.calcul.impot.bareme.BaremeTauxEffectif#getTaux(java.math.BigDecimal)
	 */
	@Override
	public BigDecimal getTaux(BigDecimal assiette) {
		BigDecimal resultat = ZERO;
		BigDecimal montantMaxTranchePrecedente = ZERO;
		for (TrancheBareme tranche : getTranches()) {
			resultat = resultat.add(tranche.calcul(montantMaxTranchePrecedente,assiette));
			montantMaxTranchePrecedente = tranche.getMontantMaxTranche();
		}
		return resultat;
	}
	
	protected static class TrancheBaremeLineaire extends TrancheBareme {

		private final BigDecimal pente;
		
		public TrancheBaremeLineaire(BigDecimal montantImposableMax,
				BigDecimal taux, BigDecimal pente) {
			super(montantImposableMax, taux);
			this.pente = pente;
		}

		@Override
		public boolean equals(Object obj) {
			// Attention, il n'est en général pas conseillé de surcharger
			// la méthode equals dans une sous-classe.
			// On se le permet ici car cette classe est sufisamment isolée.
			if (!(obj instanceof TrancheBaremeLineaire)) return false;
			TrancheBaremeLineaire tranche = (TrancheBaremeLineaire)obj;
			if (0 != pente.compareTo(tranche.pente)) return false;
			return super.equals(obj);
		}

		/* (non-Javadoc)
		 * @see ch.ge.afc.calcul.impot.bareme.TrancheBareme#calcul(java.math.BigDecimal, java.math.BigDecimal)
		 */
		@Override
		public BigDecimal calcul(
				BigDecimal montantImposableMaxTranchePrecedente,
				BigDecimal montantImposable) {
			if (0 <= montantImposable.compareTo(montantImposableMaxTranchePrecedente)
					&&
				0 > montantImposable.compareTo(this.getMontantMaxTranche())) {
				return this.getTauxOuMontant().add(pente.multiply(montantImposable.subtract(montantImposableMaxTranchePrecedente)));
			}
			return BigDecimal.ZERO;
		}

		protected static class DerniereTrancheBaremeLineaire extends DerniereTrancheBareme {
			 
			public DerniereTrancheBaremeLineaire(BigDecimal taux) {
				super(taux);
			}

			/* (non-Javadoc)
			 * @see BaremeTauxEffectifLineaireParTranche.TrancheBaremeLineaire#calcul(java.math.BigDecimal, java.math.BigDecimal)
			 */
			@Override
			public BigDecimal calcul(
					BigDecimal montantImposableMaxTranchePrecedente,
					BigDecimal montantImposable) {
				if (0 <= montantImposable.compareTo(montantImposableMaxTranchePrecedente)) {
					return this.getTauxOuMontant();
				}
				return BigDecimal.ZERO;
			}
			
			
		}
		
	}
	
}
