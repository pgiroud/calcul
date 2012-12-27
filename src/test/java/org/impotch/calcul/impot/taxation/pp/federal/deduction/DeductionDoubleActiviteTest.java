package org.impotch.calcul.impot.taxation.pp.federal.deduction;

import org.junit.Before;
import org.junit.Test;
import org.impotch.calcul.impot.taxation.pp.federal.deduction.DeductionDoubleActivite;

import java.math.BigDecimal;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: patrick
 * Date: 10/05/12
 * Time: 19:45
 * To change this template use File | Settings | File Templates.
 */
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