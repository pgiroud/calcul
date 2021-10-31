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

import java.math.BigDecimal;

import org.impotch.calcul.impot.taxation.pp.SituationFamiliale;
import org.impotch.calcul.impot.taxation.pp.famille.Splitting;
import org.impotch.bareme.BaremeTauxEffectifLineaireParTranche;

/**
 * Le splitting à Fribourg est intimememt lié au barème à taux effectif linéaire par tranche.
 * Voir taux minimum à 1%
 * 
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class SplittingFR extends Splitting {

	/**
	 * @param bareme
	 * @param taux
	 */
	public SplittingFR(BaremeTauxEffectifLineaireParTranche bareme, BigDecimal taux) {
		super(bareme, taux);
	}

	/**
	 * @param bareme
	 * @param taux
	 */
	public SplittingFR(BaremeTauxEffectifLineaireParTranche bareme, String taux) {
		super(bareme, taux);
	}

	protected BaremeTauxEffectifLineaireParTranche getBareme() {
		return (BaremeTauxEffectifLineaireParTranche)getBaremeSeul();
	}
	
	private BigDecimal getMontantImposableMinimum() {
		return getBareme().getMontantImposablePremiereTranche();
	}
	
	private BigDecimal getTauxMinimum() {
		return getBareme().getTauxMinimum();
	}
	
	protected BigDecimal getTauxFamille(BigDecimal determinantArrondi, BigDecimal imposableArrondi) {
		BigDecimal determinant = getTypeArrondi().arrondirMontant(determinantArrondi.multiply(getTauxSplitting()));
		if (0 <= getMontantImposableMinimum().compareTo(determinant)
				&& 0 >= getMontantImposableMinimum().compareTo(imposableArrondi)) {
			return getTauxMinimum();
		} else {
			return getBareme().getTaux(determinant);
		}
	}
	
	protected BigDecimal getTauxSeul(BigDecimal determinantArrondi) {
		return getBareme().getTaux(determinantArrondi);
	}
	
	@Override
	public BigDecimal produireImpotAnnuel(SituationFamiliale situation,
			BigDecimal determinantArrondi, BigDecimal imposableArrondi) {
		BigDecimal taux = null;
		if (isFamille(situation)) {
			taux = this.getTauxFamille(determinantArrondi, imposableArrondi);
		} else {
			taux = this.getTauxSeul(determinantArrondi);
		}
		return imposableArrondi.multiply(taux);
	}

}
