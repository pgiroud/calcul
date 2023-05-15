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
package org.impotch.calcul.impot.cantonal.ge.pp.avant2010;

import java.math.BigDecimal;

import org.impotch.bareme.Bareme;
import org.impotch.util.TypeArrondi;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class BaremeRevenuChoixSuivantMontant implements Bareme {

	private int limiteBaremeFamille;
	private BigDecimal constanteEntreMarieEtCelibataire;
	
	private Bareme baremeFamille;
	private Bareme baremeSeul;
	
	public void setBaremeFamille(Bareme baremeFamille) {
		this.baremeFamille = baremeFamille;
	}

	public void setBaremeSeul(Bareme baremeSeul) {
		this.baremeSeul = baremeSeul;
	}

	public void setLimiteBaremeFamille(int limiteBaremeFamille) {
		this.limiteBaremeFamille = limiteBaremeFamille;
	}

	private BigDecimal getConstanteEntreMarieEtCelibataire() {
		if (null == constanteEntreMarieEtCelibataire) {
			BigDecimal montantLimite = baremeFamille.calcul(new BigDecimal(limiteBaremeFamille));
			constanteEntreMarieEtCelibataire = montantLimite.subtract(baremeSeul.calcul(new BigDecimal(limiteBaremeFamille)));
		}
		return constanteEntreMarieEtCelibataire;
	}
	
	
	/* (non-Javadoc)
	 * @see ch.ge.afc.calcul.bareme.Bareme#calcul(java.math.BigDecimal)
	 */
	@Override
	public BigDecimal calcul(BigDecimal pAssiette) {
		if (null == pAssiette) return null;
		if (pAssiette.intValue() >= limiteBaremeFamille) {
			BigDecimal impot = getConstanteEntreMarieEtCelibataire().add(baremeSeul.calcul(pAssiette));
			return TypeArrondi.CINQ_CENTIEMES_LES_PLUS_PROCHES.arrondirMontant(impot);
		} else return baremeFamille.calcul(pAssiette);
	}

}
