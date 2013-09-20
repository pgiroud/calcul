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
package org.impotch.calcul.impot.federal.dao;

import org.impotch.bareme.Bareme;
import org.junit.Test;

import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.*;

public class FournisseurBarIFDProtoTest {

    @Test
    public void test2012Seul() {
        Bareme bar = new FournisseurBarIFDProto().getBaremeImpotRevenuPersonnePhysiquePourPersonneSeule(2012);
        assertThat(bar.calcul(BigDecimal.valueOf(14500))).isEqualTo("0.00") ;
        assertThat(bar.calcul(BigDecimal.valueOf(31600))).isEqualTo("131.65") ;
        //assertThat(bar.calcul(BigDecimal.valueOf(41400))).isEqualTo("217.90");
    }
}
