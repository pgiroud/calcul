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
package org.impotch.calcul.impot.federal.param;
import org.impotch.bareme.Bareme;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.impotch.calcul.impot.federal.ContexteTest_CH.CTX_TST_CH;

public class BaremePrestationEnCapitalTest {

    private FournisseurBaremeIFD fournisseur = CTX_TST_CH.getFournisseurBaremeIFD();


    @Test
    public void existePasChuteDeConstantinople() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                () -> fournisseur.getBaremeImpotSourcePrestationCapital(1453)
        );
    }

    @Test
    public void existe2008() {
        Optional<Bareme> bareme = fournisseur.getBaremeImpotSourcePrestationCapital(2008);
        assertThat(bareme).isNotEmpty();
    }
}
