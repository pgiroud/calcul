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
import static org.impotch.calcul.impot.federal.ContexteTest_CH.CTX_TST_CH;

public class Bareme2021PrestationEnCapitalTest {

    private FournisseurBaremeIFD fournisseur = CTX_TST_CH.getFournisseurBaremeIFD();

    @Test
    public void existe() {
        Optional<Bareme> bareme = fournisseur.getBaremeImpotSourcePrestationCapital(2021);
        assertThat(bareme).isNotEmpty();
    }

    @Test
    public void avantDerniereTranche() {
        Bareme bareme = fournisseur.getBaremeImpotSourcePrestationCapital(2021).get();
        assertThat(bareme.calcul(675_000)).isEqualTo(new BigDecimal("15150.00"));
    }

    @Test
    public void derniereTranche() {
        Bareme bareme = fournisseur.getBaremeImpotSourcePrestationCapital(2021).get();
        assertThat(bareme.calcul(1_100_000)).isEqualTo(new BigDecimal("25150.00"));
    }

}
