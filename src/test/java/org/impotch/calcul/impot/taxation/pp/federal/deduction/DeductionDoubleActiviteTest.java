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
package org.impotch.calcul.impot.taxation.pp.federal.deduction;

import org.junit.Before;
import org.junit.Test;
import org.impotch.calcul.impot.taxation.pp.federal.deduction.DeductionDoubleActivite;

import java.math.BigDecimal;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DeductionDoubleActiviteTest {

    private DeductionDoubleActivite deduction;

    @Before
    public void init() {
        deduction = new DeductionDoubleActivite(2008);
        deduction.setTaux("50 %");
        deduction.setPlancher(7600);
        deduction.setPlafond(12500);
    }

    @Test
    public void regle2008() {
        assertThat(deduction.getDeduction(BigDecimal.valueOf(3000),BigDecimal.valueOf(10000)),is(BigDecimal.valueOf(3000)));
        assertThat(deduction.getDeduction(BigDecimal.valueOf(10000),BigDecimal.valueOf(3000)),is(BigDecimal.valueOf(3000)));
        assertThat(deduction.getDeduction(BigDecimal.valueOf(7600),BigDecimal.valueOf(8000)),is(BigDecimal.valueOf(7600)));
        assertThat(deduction.getDeduction(BigDecimal.valueOf(7601),BigDecimal.valueOf(8000)),is(BigDecimal.valueOf(7600)));
        assertThat(deduction.getDeduction(BigDecimal.valueOf(15200),BigDecimal.valueOf(16000)),is(BigDecimal.valueOf(7600)));
        assertThat(deduction.getDeduction(BigDecimal.valueOf(15202),BigDecimal.valueOf(16000)),is(BigDecimal.valueOf(7601)));
        assertThat(deduction.getDeduction(BigDecimal.valueOf(20000),BigDecimal.valueOf(20000)),is(BigDecimal.valueOf(10000)));
        assertThat(deduction.getDeduction(BigDecimal.valueOf(25000),BigDecimal.valueOf(50000)),is(BigDecimal.valueOf(12500)));
        assertThat(deduction.getDeduction(BigDecimal.valueOf(50000),BigDecimal.valueOf(50000)),is(BigDecimal.valueOf(12500)));

    }
}