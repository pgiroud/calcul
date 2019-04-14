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
import java.util.List;

import org.impotch.bareme.BaremeParTranche;
import org.impotch.bareme.BaremeTauxEffectifParTranche;
import org.impotch.bareme.TrancheBareme;
import org.impotch.util.BigDecimalUtil;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class BaremeTauxEffectifLineaireParTranche extends BaremeTauxEffectifParTranche {

	public BaremeTauxEffectifLineaireParTranche() {
		super();
	}

	public BaremeTauxEffectifLineaireParTranche(List<TrancheBareme> tranches) {
		super();
		setTranches(tranches);
	}

	public BigDecimal getMontantImposablePremiereTranche() {
		return getTranches().get(0).getIntervalle().getFin();
	}
	
	public BigDecimal getTauxMinimum() {
		for (TrancheBareme tranche : getTranches()) {
			if (0 < tranche.getTauxOuMontant().compareTo(ZERO)) return tranche.getTauxOuMontant();
		}
		throw new IllegalStateException("Il faut qu'il existe des tranches avant d'invoquer cette méthode !!");
	}
	
	/* (non-Javadoc)
	 * @see ch.ge.afc.calcul.impot.bareme.BaremeTauxEffectif#getTaux(java.math.BigDecimal)
	 */
	@Override
	public BigDecimal getTaux(BigDecimal assiette) {
		return getTranches().stream().map(t -> t.calcul(assiette)).reduce(BigDecimal.ZERO,BigDecimal::add);
	}

	@Override
	protected BaremeParTranche newBaremeParTranche() {
		return new BaremeTauxEffectifLineaireParTranche();
	}


}
