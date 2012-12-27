package org.impotch.calcul.impot.taxation.pp.federal.deduction;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: patrick
 * Date: 10/05/12
 * Time: 19:35
 * To change this template use File | Settings | File Templates.
 */
public interface IDeductionDoubleActivite {
    BigDecimal getDeduction(BigDecimal premierRevenuNet, BigDecimal secondRevenuNet);
}
