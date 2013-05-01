package org.impotch.calcul.impot.federal.dao;

import org.impotch.bareme.Bareme;
import org.junit.Test;

import java.math.BigDecimal;
import static org.fest.assertions.api.Assertions.*;

/**
 * Created with IntelliJ IDEA.
 * User: patrick
 * Date: 30/11/12
 * Time: 21:30
 * To change this template use File | Settings | File Templates.
 */
public class FournisseurBarIFDProtoTest {

    @Test
    public void test2012Seul() {
        Bareme bar = new FournisseurBarIFDProto().getBaremeImpotRevenuPersonnePhysiquePourPersonneSeule(2012);
        assertThat(bar.calcul(BigDecimal.valueOf(14500))).isEqualTo("0.00") ;
        assertThat(bar.calcul(BigDecimal.valueOf(31600))).isEqualTo("131.65") ;
        //assertThat(bar.calcul(BigDecimal.valueOf(41400))).isEqualTo("217.90");
    }
}
