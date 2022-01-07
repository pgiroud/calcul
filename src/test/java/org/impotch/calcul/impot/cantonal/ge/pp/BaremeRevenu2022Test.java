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
package org.impotch.calcul.impot.cantonal.ge.pp;

import org.impotch.bareme.Bareme;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.annotation.Resource;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(locations = {"/beansCH_GE.xml"})
public class BaremeRevenu2022Test {
    @Resource(name = "fournisseurRegleImpotCantonalGE")
    private FournisseurRegleImpotCantonalGE fournisseur;

    @Test
    public void borneBareme() {
        Bareme bareme = fournisseur.getBaremeRevenu(2022);
        assertThat(bareme.calcul(new BigDecimal("17697"))).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(new BigDecimal("21322"))).isEqualTo(new BigDecimal("290.00"));
        assertThat(bareme.calcul(new BigDecimal("23454"))).isEqualTo(new BigDecimal("481.90"));
        assertThat(bareme.calcul(new BigDecimal("25586"))).isEqualTo(new BigDecimal("695.10"));
        assertThat(bareme.calcul(new BigDecimal("27719"))).isEqualTo(new BigDecimal("929.75"));
        assertThat(bareme.calcul(new BigDecimal("33049"))).isEqualTo(new BigDecimal("1569.35"));
        assertThat(bareme.calcul(new BigDecimal("37313"))).isEqualTo(new BigDecimal("2123.65"));
        assertThat(bareme.calcul(new BigDecimal("41578"))).isEqualTo(new BigDecimal("2720.75"));
        assertThat(bareme.calcul(new BigDecimal("45842"))).isEqualTo(new BigDecimal("3339.05"));
        assertThat(bareme.calcul(new BigDecimal("616206"))).isEqualTo(new BigDecimal("103088.75"));
    }


}
