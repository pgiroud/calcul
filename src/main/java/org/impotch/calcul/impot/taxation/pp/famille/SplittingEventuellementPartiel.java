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
import org.impotch.calcul.impot.taxation.pp.EnfantACharge;
import org.impotch.calcul.impot.taxation.pp.SituationFamiliale;
import org.impotch.calcul.impot.taxation.pp.StrategieProductionImpotFamille;
import org.impotch.util.BigDecimalUtil;

import java.math.BigDecimal;

/**
 * Spécialité genevoise
 * À partir du 1er janvier 2024, la législation genevoise a introduit le splitting partiel afin de rétablir
 * l’équité fiscale entre parents séparés ou divorcés qui partagent la prise en charge de leurs enfants.
 * L’objectif est de réduire l’écart de charge fiscale qui existait entre ces parents et les couples mariés ou
 * en partenariat enregistré, lorsque les deux parents assument à parts égales l’entretien, la garde et les frais
 * de leurs enfants.
 * Cette réforme est matérialisée par le projet de
 * <a href="https://ge.ch/grandconseil/data/texte/PL13254.pdf">loi 13254</a>.
 */
public class SplittingEventuellementPartiel implements StrategieProductionImpotFamille {

    private final BigDecimal tauxSplittingPartiel;
    private final Splitting splitting;


    public SplittingEventuellementPartiel(Splitting splitting, String tauxSplittingPartiel) {
        this.splitting = splitting;
        this.tauxSplittingPartiel = BigDecimalUtil.parse(tauxSplittingPartiel);
    }

    private boolean estCandidatAuSplittingPartiel(SituationFamiliale situation) {
        return situation.getEnfants().stream().anyMatch(EnfantACharge::estEnGardeAlterneeStricte);
    }

    private BigDecimal produireImpotDeterminantGardeAlternee(BigDecimal determinantArrondi) {
        return splitting.produireImpotAvecSplitting(determinantArrondi,tauxSplittingPartiel);
    }

    @Override
    public BigDecimal produireImpotDeterminant(SituationFamiliale situation, BigDecimal determinantArrondi) {
        return estCandidatAuSplittingPartiel(situation) ?
                produireImpotDeterminantGardeAlternee(determinantArrondi) :
                splitting.produireImpotDeterminant(situation, determinantArrondi);
    }

    @Override
    public BigDecimal produireImpotAuTauxMaximal(SituationFamiliale situation, BigDecimal imposableArrondi) {
        return splitting.produireImpotAuTauxMaximal(situation,imposableArrondi);
    }
}
