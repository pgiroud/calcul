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
/**
 * This file is part of impotch/calcul.
 * <p>
 * impotch/calcul is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 * <p>
 * impotch/calcul is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with impotch/calcul.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.impotch.calcul.impot.cantonal.ge.pp;

import java.math.BigDecimal;

import org.impotch.bareme.Bareme;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.impotch.calcul.impot.cantonal.ge.ContexteTestGE.CTX_TST_GE;


public class BaremeRevenu2011Test {
    private FournisseurRegleImpotCantonalGE fournisseur = CTX_TST_GE.getFournisseurRegleImpotCantonalGE();

    @Test
    public void baremeSource() {
        Bareme bareme = fournisseur.getBaremeRevenu(2011);
        assertThat(bareme.calcul(new BigDecimal("17753"))).isEqualTo(new BigDecimal("9.90"));
        assertThat(bareme.calcul(new BigDecimal("17700"))).isEqualTo(new BigDecimal("5.70"));
        assertThat(bareme.calcul(new BigDecimal("17629"))).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(new BigDecimal("17630"))).isEqualTo(new BigDecimal("0.10"));
    }

    @Test
    public void borneBareme() {
        Bareme bareme = fournisseur.getBaremeRevenu(2011);
        assertThat(bareme.calcul(new BigDecimal("17527"))).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(new BigDecimal("21240"))).isEqualTo(new BigDecimal("288.90"));
        assertThat(bareme.calcul(new BigDecimal("23364"))).isEqualTo(new BigDecimal("480.05"));
        assertThat(bareme.calcul(new BigDecimal("25488"))).isEqualTo(new BigDecimal("692.45"));
        assertThat(bareme.calcul(new BigDecimal("27612"))).isEqualTo(new BigDecimal("926.10"));
        assertThat(bareme.calcul(new BigDecimal("2003887"))).isEqualTo(new BigDecimal("366801.85"));
    }


}
