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
import org.impotch.bareme.BaremeTauxEffectifParTranche;
import org.impotch.util.BigDecimalUtil;
import org.impotch.util.TypeArrondi;

import static org.impotch.util.TypeArrondi.CENTAINE_INF;
/**
 * Le splitting à Fribourg est intimememt lié au barème à taux effectif linéaire par tranche.
 * Voir taux minimum à 1%
 * 
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class SplittingFR extends Splitting {

	private BigDecimal montantImposableMinimum;
	private BigDecimal tauxMinimum;

	public static ConstructeurSplittingFR unSplittingFribourgeois(BaremeTauxEffectifParTranche bareme, String taux) {
		return new ConstructeurSplittingFR(bareme,taux).arrondi(CENTAINE_INF);
	}

	private SplittingFR(BaremeTauxEffectifParTranche bareme, String taux) {
		super(bareme, taux);
	}

	@Override
	protected BaremeTauxEffectifParTranche getBareme() {
		return (BaremeTauxEffectifParTranche)super.getBareme();
	}


	protected BigDecimal getTauxFamille(BigDecimal determinantArrondi, BigDecimal imposableArrondi) {
		BigDecimal determinant = getTypeArrondi().arrondirMontant(determinantArrondi.multiply(getTauxSplitting()));
		if (0 <= montantImposableMinimum.compareTo(determinant)
				&& 0 >= montantImposableMinimum.compareTo(imposableArrondi)) {
			return tauxMinimum;
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
		BigDecimal taux = isFamille(situation) ?
				getTauxFamille(determinantArrondi, imposableArrondi)
				: getTauxSeul(determinantArrondi);
		return imposableArrondi.multiply(taux);
	}

	public static class ConstructeurSplittingFR {

		private final BaremeTauxEffectifParTranche bareme;
		private final String taux;

		private BigDecimal montantImposableMinimum;
		private BigDecimal tauxMinimum;
		private TypeArrondi arrondi;

		public ConstructeurSplittingFR(BaremeTauxEffectifParTranche bareme, String taux) {
			this.bareme = bareme;
			this.taux = taux;
		}

		public ConstructeurSplittingFR montantImposableMinimum(int montant) {
			montantImposableMinimum = BigDecimal.valueOf(montant);
			return this;
		}

		public ConstructeurSplittingFR tauxMinimum(String taux) {
			this.tauxMinimum = BigDecimalUtil.parse(taux);
			return this;
		}

		public ConstructeurSplittingFR arrondi(TypeArrondi arrondi) {
			this.arrondi = arrondi;
			return this;
		}

		public SplittingFR construire() {
			SplittingFR splitting = new SplittingFR(bareme,taux);
			splitting.montantImposableMinimum = this.montantImposableMinimum;
			splitting.tauxMinimum = this.tauxMinimum;
			splitting.setTypeArrondi(arrondi);
			return splitting;
		}

	}

}
