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
package org.impotch.calcul.impot.taxation.pp.federal.deduction;

import org.impotch.calcul.ReglePeriodique;
import org.impotch.util.BigDecimalUtil;
import org.impotch.util.TypeArrondi;

import java.math.BigDecimal;

public class DeductionDoubleActivite extends ReglePeriodique implements IDeductionDoubleActivite {

    private BigDecimal taux;
    private BigDecimal plancher;
    private BigDecimal plafond;


    public DeductionDoubleActivite(int annee) {
        super(annee);
    }

    public void setTaux(String taux) {
        this.taux = BigDecimalUtil.parseTaux(taux);
    }

    public void setPlancher(int plancher) {
        this.plancher = BigDecimal.valueOf(plancher);
    }

    public void setPlafond(int plafond) {
        this.plafond = BigDecimal.valueOf(plafond);
    }

    @Override
    public BigDecimal getDeduction(BigDecimal premierRevenuNet, BigDecimal secondRevenuNet) {
        BigDecimal plusPetitRevenu = premierRevenuNet.min(secondRevenuNet);
        if (this.getAnnee() <= 2007) {
            return BigDecimal.ZERO.max(plafond.min(plusPetitRevenu));
        } else {
            BigDecimal partPlusPetitRevenu = TypeArrondi.FRANC_SUP.arrondirMontant(taux.multiply(plusPetitRevenu));
            BigDecimal deductionPossible = plancher.max(plafond.min(partPlusPetitRevenu));
            return plusPetitRevenu.min(deductionPossible);
        }
    }
}
