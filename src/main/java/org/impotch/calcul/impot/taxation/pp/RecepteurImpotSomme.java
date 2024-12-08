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
package org.impotch.calcul.impot.taxation.pp;

import java.math.BigDecimal;

import org.impotch.calcul.impot.Impot;
import org.impotch.calcul.impot.RecepteurImpot;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class RecepteurImpotSomme implements RecepteurImpot {

    private static BigDecimal ZERO = new BigDecimal("0.00");

	private BigDecimal valeur = ZERO;
    private BigDecimal seuil = ZERO;

    public RecepteurImpotSomme() {
        this(ZERO);
    }


    public RecepteurImpotSomme(BigDecimal seuil) {
        this.seuil = seuil;
    }

    /* (non-Javadoc)
         * @see ch.ge.afc.calcul.impot.RecepteurImpot#ajouteImpot(java.math.BigDecimal, java.lang.String)
         */
	@Override
	public void ajouteImpot(Impot impot) {
		valeur = valeur.add(impot.getMontant());
	}

	/**
	 * @return the valeur
	 */
	public BigDecimal getValeur() {
        return (valeur.compareTo(seuil) >=  0)  ? valeur : ZERO;
	}


}
