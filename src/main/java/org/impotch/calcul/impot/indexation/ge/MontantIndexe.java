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
package org.impotch.calcul.impot.indexation.ge;

import org.impotch.calcul.impot.indexation.Indexateur;


import java.math.BigDecimal;

public class MontantIndexe {

    private final Indexateur indexateur;
    private final BigDecimal montantBase;

    private MontantIndexe(BigDecimal montantBase, Indexateur indexateur) {
        this.montantBase = montantBase;
        this.indexateur = indexateur;
    }

    public MontantIndexe(int montantBase, Indexateur indexateur) {
        this(BigDecimal.valueOf(montantBase),indexateur);
    }

    public BigDecimal getMontantIndexe(int annee) {
        return indexateur.indexer(montantBase, annee);

    }
}
