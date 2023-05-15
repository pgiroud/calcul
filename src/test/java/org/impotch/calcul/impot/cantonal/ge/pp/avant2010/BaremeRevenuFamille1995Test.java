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

import org.impotch.calcul.impot.cantonal.ge.pp.ChargeurFichierEconometre;
import org.impotch.calcul.impot.cantonal.ge.pp.FournisseurRegleImpotCantonalGE;
import org.impotch.util.TypeArrondi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.impotch.calcul.impot.cantonal.ge.ContexteTestCH_GE.CTX_TST_CH_GE;

public class BaremeRevenuFamille1995Test {


    private FournisseurRegleImpotCantonalGE fournisseur = CTX_TST_CH_GE.getFournisseurRegleImpotCantonalGE();

    private BaremeFamille baremeCouple;

    @BeforeEach
    public void setUp() throws Exception {
        baremeCouple = (BaremeFamille)fournisseur.getBaremeRevenuFamille(1995);
        baremeCouple.setArrondi(TypeArrondi.MILLIEME_LE_PLUS_PROCHE);
    }

    @ParameterizedTest
    @MethodSource
    public void couple(ChargeurFichierEconometre.Point point) {
        assertThat(baremeCouple.calcul(BigDecimal.valueOf(point.revenu))).isEqualTo(point.impot);
    }

    private static Stream<ChargeurFichierEconometre.Point> couple() throws IOException {
        ChargeurFichierEconometre chargeur = new ChargeurFichierEconometre();
        return chargeur.points(1995, true);
    }


}
