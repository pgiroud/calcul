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

package org.impotch.calcul.impot.taxation.pp.federal.deduction;

import java.math.BigDecimal;

import org.impotch.calcul.ReglePeriodique;

import static java.math.BigDecimal.ZERO;
import static org.impotch.util.BigDecimalUtil.parse;
import static org.impotch.util.TypeArrondi.UNITE_SUP;

/**
 * Une déduction pour les couples dont les 2 membres exercent une activité lucrative.
 */
public class DeductionDoubleActivite extends ReglePeriodique implements IDeductionDoubleActivite {

    private BigDecimal taux;
    private BigDecimal plancher;
    private BigDecimal plafond;


    public DeductionDoubleActivite(int annee) {
        super(annee);
    }

    public void setTaux(String taux) {
        this.taux = parse(taux);
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
            return ZERO.max(plafond.min(plusPetitRevenu));
        } else {
            BigDecimal partPlusPetitRevenu = UNITE_SUP.arrondir(taux.multiply(plusPetitRevenu));
            BigDecimal deductionPossible = plancher.max(plafond.min(partPlusPetitRevenu));
            return plusPetitRevenu.min(deductionPossible);
        }
    }
}
