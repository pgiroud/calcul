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
package org.impotch.calcul.impot.taxation.pp.ge.deduction;

import org.impotch.calcul.impot.taxation.pp.DeductionSociale;
import org.impotch.calcul.impot.taxation.pp.SituationFamiliale;
import org.impotch.calcul.impot.taxation.pp.TypeDeductionSociale;

import java.math.BigDecimal;

/**
 * Created by patrick on 14/04/16.
 */
public class DeductionDoubleActivite implements DeductionSociale {

    private final BigDecimal montantDeduction;

    public DeductionDoubleActivite(int montant) {
        montantDeduction = BigDecimal.valueOf(montant);
    }

    public DeductionDoubleActivite(BigDecimal montant) {
        montantDeduction = montant;
    }

    @Override
    public BigDecimal getMontantDeduction(SituationFamiliale situation) {
        if (situation.isCouple()) return montantDeduction;
        return BigDecimal.ZERO;
    }

    @Override
    public TypeDeductionSociale getType() {
        return TypeDeductionSociale.DOUBLE_ACTIVITE;
    }
}
