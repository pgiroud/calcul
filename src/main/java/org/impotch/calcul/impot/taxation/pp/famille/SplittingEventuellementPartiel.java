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
package org.impotch.calcul.impot.taxation.pp.famille;

import org.impotch.bareme.Bareme;
import org.impotch.calcul.impot.taxation.pp.SituationFamiliale;
import org.impotch.util.BigDecimalUtil;

import java.math.BigDecimal;

public class SplittingEventuellementPartiel extends Splitting {

    private final BigDecimal tauxSplittingPartiel;


    public SplittingEventuellementPartiel(Bareme bareme, String taux, String tauxSplittingPartiel) {
        super(bareme, taux);
        this.tauxSplittingPartiel = BigDecimalUtil.parse(tauxSplittingPartiel);
    }

    private boolean estCandidatAuSplittingPartiel(SituationFamiliale situation) {
        return situation.getEnfants().stream()
                .filter(enfant -> enfant.estEnGardeAlterneeStricte())
                .findAny().isPresent();
    }

    private BigDecimal produireImpotDeterminantGardeAlternee(BigDecimal determinantArrondi, BigDecimal imposableArrondi) {
        return this.produireImpotAvecSplitting(determinantArrondi,imposableArrondi,tauxSplittingPartiel);
    }

    @Override
    public BigDecimal produireImpotAnnuel(SituationFamiliale situation, BigDecimal determinantArrondi, BigDecimal imposableArrondi) {
        if (estCandidatAuSplittingPartiel(situation)) {
            BigDecimal impotDeterminant = this.produireImpotDeterminantGardeAlternee(determinantArrondi,imposableArrondi);
            return this.determineImpot(imposableArrondi, determinantArrondi, impotDeterminant);
        } else {
            return super.produireImpotAnnuel(situation, determinantArrondi, imposableArrondi);
        }
    }
}
