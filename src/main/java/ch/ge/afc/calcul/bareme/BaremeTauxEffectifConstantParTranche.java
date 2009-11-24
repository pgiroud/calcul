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
package ch.ge.afc.calcul.bareme;

import java.math.BigDecimal;

import ch.ge.afc.util.BigDecimalUtil;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class BaremeTauxEffectifConstantParTranche extends
		BaremeTauxEffectifParTranche {

	/**************************************************/
	/****************** Attributs *********************/
	/**************************************************/

	private boolean montantMaxNonInclus;
	
    /**************************************************/
    /************* Accesseurs / Mutateurs *************/
    /**************************************************/

	public void setMontantMaxNonInclus() {
		montantMaxNonInclus = true;
	}
	
    /**************************************************/
    /******************* Méthodes *********************/
    /**************************************************/

	public void ajouterTranche(int montant, String taux) {
		getTranches().add(new TrancheBareme(new BigDecimal(montant),BigDecimalUtil.parseTaux(taux)));
	}
	
	public void ajouterDerniereTranche(String taux) {
		getTranches().add(new TrancheBareme.DerniereTrancheBareme(BigDecimalUtil.parseTaux(taux)));
	}
	
	/* (non-Javadoc)
	 * @see ch.ge.afc.calcul.impot.bareme.BaremeTauxEffectif#getTaux(java.math.BigDecimal)
	 */
	@Override
	public BigDecimal getTaux(BigDecimal assiette) {
		for (TrancheBareme tranche : getTranches()) {
			int comparaison = assiette.compareTo(tranche.getMontantMaxTranche());
			if ((montantMaxNonInclus && 0 > comparaison) 
					|| 
				(!montantMaxNonInclus && 0 >= comparaison)) {
				return tranche.getTauxOuMontant();
			}
		}
		return getTranches().get(getTranches().size()-1).getTauxOuMontant();
	}

	
	
}
