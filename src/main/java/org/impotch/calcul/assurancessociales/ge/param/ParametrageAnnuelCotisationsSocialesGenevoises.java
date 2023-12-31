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
package org.impotch.calcul.assurancessociales.ge.param;

import org.impotch.calcul.assurancessociales.param.ParametrageParticipationHautRevenuAC;
import org.impotch.calcul.assurancessociales.param.ParametrageSuisseAnnuel;

import java.util.Optional;

public class ParametrageAnnuelCotisationsSocialesGenevoises implements ParametrageSuisseAnnuel, ParametrageCotisationAssuranceMaternite {

    private final ParametrageSuisseAnnuel parametrageSuisse;
    private final ParametrageCotisationAssuranceMaternite parametrageSsuranceMaternite;

    public ParametrageAnnuelCotisationsSocialesGenevoises(ParametrageSuisseAnnuel parametrageSuisse, ParametrageCotisationAssuranceMaternite parametrageSsuranceMaternite) {
        this.parametrageSuisse = parametrageSuisse;
        this.parametrageSsuranceMaternite = parametrageSsuranceMaternite;
    }

    @Override
    public int annee() {
        return parametrageSuisse.annee();
    }

    @Override
    public int montantMaximumDuGainAssure() {
        return parametrageSuisse.montantMaximumDuGainAssure();
    }

    @Override
    public String tauxAVS() {
        return parametrageSuisse.tauxAVS();
    }

    @Override
    public String tauxAI() {
        return parametrageSuisse.tauxAI();
    }

    @Override
    public String tauxAPG() {
        return parametrageSuisse.tauxAPG();
    }

    @Override
    public String tauxAC() {
        return parametrageSuisse.tauxAC();
    }

    @Override
    public Optional<ParametrageParticipationHautRevenuAC> participationHautRevenu() {
        return parametrageSuisse.participationHautRevenu();
    }

    @Override
    public int renteMensuelleMinimum() {
        return parametrageSuisse.renteMensuelleMinimum();
    }

    @Override
    public String tauxAssuranceMaternite() {
        return parametrageSsuranceMaternite.tauxAssuranceMaternite();
    }
}
