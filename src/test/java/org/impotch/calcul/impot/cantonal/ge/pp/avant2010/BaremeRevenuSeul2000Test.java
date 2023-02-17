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

import org.impotch.bareme.BaremeTauxMarginalIntegrable;
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

public class BaremeRevenuSeul2000Test {


    private FournisseurRegleImpotCantonalGE fournisseur = CTX_TST_CH_GE.getFournisseurRegleImpotCantonalGE();

    private BaremeTauxMarginalIntegrable bareme;

    private int revenu;
    private BigDecimal impot;

    @BeforeEach
    public void setUp() throws Exception {
        bareme = (BaremeTauxMarginalIntegrable)fournisseur.getBaremeRevenu(2000);
        bareme.setTypeArrondi(TypeArrondi.DIXIEME_CT);
    }

    @ParameterizedTest
    @MethodSource
    public void seul(ChargeurFichierEconometre.Point point) {
        assertThat(bareme.calcul(BigDecimal.valueOf(point.revenu))).isEqualTo(point.impot);
    }

    private static Stream<ChargeurFichierEconometre.Point> seul() throws IOException {
        ChargeurFichierEconometre chargeur = new ChargeurFichierEconometre();
        // Attention, devrait péter !!
//        // Il y a un arrondi distinct sur la 3ème décimal !
//        objets.get(1633)[1] = new BigDecimal("299984.611"); // À la place de 299984.612
        return chargeur.points(2000, false);
    }

}
