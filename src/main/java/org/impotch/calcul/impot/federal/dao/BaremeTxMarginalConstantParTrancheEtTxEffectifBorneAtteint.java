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
package org.impotch.calcul.impot.federal.dao;

import org.impotch.bareme.BaremeTauxMarginalConstantParTranche;

import java.math.BigDecimal;
import java.util.OptionalInt;

/**
 * Created by patrick on 14/02/14.
 */
public class BaremeTxMarginalConstantParTrancheEtTxEffectifBorneAtteint extends BaremeTauxMarginalConstantParTranche {

    private BigDecimal deltaPourAtteindreBorne = BigDecimal.ZERO;
    private OptionalInt noTrancheDeltaApplicable = OptionalInt.empty();

    public void setDeltaPourAtteindreBorne(BigDecimal deltaPourAtteindreBorne) {
        this.deltaPourAtteindreBorne = deltaPourAtteindreBorne;
    }

    public void setNoTrancheDeltaApplicable(int noTrancheDeltaApplicable) {
        this.noTrancheDeltaApplicable = OptionalInt.of(noTrancheDeltaApplicable);
    }

    private boolean assiettePlusGrandeQuAjustement(BigDecimal assiette) {
        return assiette.compareTo(this.getTranches().get(noTrancheDeltaApplicable.getAsInt()).getMontantMaxTranche()) > 0;
    }

    @Override
    public BigDecimal calcul(BigDecimal assiette) {
        if (!noTrancheDeltaApplicable.isPresent()) {
            setNoTrancheDeltaApplicable(getTranches().size() - 2);
        }
        BigDecimal resultat = super.calcul(assiette);
        if (assiettePlusGrandeQuAjustement(assiette))  {
            return resultat.add(deltaPourAtteindreBorne);
        } else {
            return resultat;
        }
    }
}
