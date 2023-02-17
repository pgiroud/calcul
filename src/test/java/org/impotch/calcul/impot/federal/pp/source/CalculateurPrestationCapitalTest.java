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
package org.impotch.calcul.impot.federal.pp.source;


import java.math.BigDecimal;

import org.impotch.calcul.impot.federal.FournisseurRegleImpotFederal;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.impotch.calcul.impot.federal.ContexteTest_CH.CTX_TST_CH;

public class CalculateurPrestationCapitalTest {

    private FournisseurRegleImpotFederal constructeur = CTX_TST_CH.getFournisseurRegleImpotFederal();

    @Test
    public void test2009JusquaCentVingCinqMille() {
        CalculateurImpotSourcePrestationCapitalIFD calculateur = constructeur.getCalculateurImpotSourcePrestationCapitalIFD(2009);
        assertThat(calculateur.calcul(new BigDecimal("1000"))).isEqualTo(new BigDecimal("0.00"));
        assertThat(calculateur.calcul(new BigDecimal("2000"))).isEqualTo(new BigDecimal("0.00"));
        assertThat(calculateur.calcul(new BigDecimal("3000"))).isEqualTo(new BigDecimal("0.00"));
        assertThat(calculateur.calcul(new BigDecimal("4000"))).isEqualTo(new BigDecimal("0.00"));
        assertThat(calculateur.calcul(new BigDecimal("5000"))).isEqualTo(new BigDecimal("0.00"));
        assertThat(calculateur.calcul(new BigDecimal("6000"))).isEqualTo(new BigDecimal("0.00"));
        assertThat(calculateur.calcul(new BigDecimal("7000"))).isEqualTo(new BigDecimal("0.00"));
        assertThat(calculateur.calcul(new BigDecimal("8000"))).isEqualTo(new BigDecimal("0.00"));
        assertThat(calculateur.calcul(new BigDecimal("9000"))).isEqualTo(new BigDecimal("0.00"));
        assertThat(calculateur.calcul(new BigDecimal("10000"))).isEqualTo(new BigDecimal("0.00"));
        assertThat(calculateur.calcul(new BigDecimal("11000"))).isEqualTo(new BigDecimal("0.00"));
        assertThat(calculateur.calcul(new BigDecimal("12000"))).isEqualTo(new BigDecimal("0.00"));
        assertThat(calculateur.calcul(new BigDecimal("13000"))).isEqualTo(new BigDecimal("0.00"));
        assertThat(calculateur.calcul(new BigDecimal("14000"))).isEqualTo(new BigDecimal("0.00"));
        assertThat(calculateur.calcul(new BigDecimal("15000"))).isEqualTo(new BigDecimal("0.00"));
        assertThat(calculateur.calcul(new BigDecimal("16000"))).isEqualTo(new BigDecimal("0.00"));
        assertThat(calculateur.calcul(new BigDecimal("17000"))).isEqualTo(new BigDecimal("0.00"));
        assertThat(calculateur.calcul(new BigDecimal("18000"))).isEqualTo(new BigDecimal("0.00"));
        assertThat(calculateur.calcul(new BigDecimal("19000"))).isEqualTo(new BigDecimal("0.00"));
        assertThat(calculateur.calcul(new BigDecimal("20000"))).isEqualTo(new BigDecimal("0.00"));
        assertThat(calculateur.calcul(new BigDecimal("21000"))).isEqualTo(new BigDecimal("0.00"));
        assertThat(calculateur.calcul(new BigDecimal("22000"))).isEqualTo(new BigDecimal("0.00"));
        assertThat(calculateur.calcul(new BigDecimal("23000"))).isEqualTo(new BigDecimal("0.00"));
        assertThat(calculateur.calcul(new BigDecimal("24000"))).isEqualTo(new BigDecimal("0.00"));
        assertThat(calculateur.calcul(new BigDecimal("25000"))).isEqualTo(new BigDecimal("0.00"));
        assertThat(calculateur.calcul(new BigDecimal("26000"))).isEqualTo(new BigDecimal("2.50"));
        assertThat(calculateur.calcul(new BigDecimal("27000"))).isEqualTo(new BigDecimal("5.00"));
        assertThat(calculateur.calcul(new BigDecimal("28000"))).isEqualTo(new BigDecimal("7.50"));
        assertThat(calculateur.calcul(new BigDecimal("29000"))).isEqualTo(new BigDecimal("10.00"));
        assertThat(calculateur.calcul(new BigDecimal("30000"))).isEqualTo(new BigDecimal("12.50"));
        assertThat(calculateur.calcul(new BigDecimal("31000"))).isEqualTo(new BigDecimal("15.00"));
        assertThat(calculateur.calcul(new BigDecimal("32000"))).isEqualTo(new BigDecimal("17.50"));
        assertThat(calculateur.calcul(new BigDecimal("33000"))).isEqualTo(new BigDecimal("20.00"));
        assertThat(calculateur.calcul(new BigDecimal("34000"))).isEqualTo(new BigDecimal("22.50"));
        assertThat(calculateur.calcul(new BigDecimal("35000"))).isEqualTo(new BigDecimal("25.00"));
        assertThat(calculateur.calcul(new BigDecimal("36000"))).isEqualTo(new BigDecimal("27.50"));
        assertThat(calculateur.calcul(new BigDecimal("37000"))).isEqualTo(new BigDecimal("30.00"));
        assertThat(calculateur.calcul(new BigDecimal("38000"))).isEqualTo(new BigDecimal("32.50"));
        assertThat(calculateur.calcul(new BigDecimal("39000"))).isEqualTo(new BigDecimal("35.00"));
        assertThat(calculateur.calcul(new BigDecimal("40000"))).isEqualTo(new BigDecimal("37.50"));
        assertThat(calculateur.calcul(new BigDecimal("41000"))).isEqualTo(new BigDecimal("40.00"));
        assertThat(calculateur.calcul(new BigDecimal("42000"))).isEqualTo(new BigDecimal("42.50"));
        assertThat(calculateur.calcul(new BigDecimal("43000"))).isEqualTo(new BigDecimal("45.00"));
        assertThat(calculateur.calcul(new BigDecimal("44000"))).isEqualTo(new BigDecimal("47.50"));
        assertThat(calculateur.calcul(new BigDecimal("45000"))).isEqualTo(new BigDecimal("50.00"));
        assertThat(calculateur.calcul(new BigDecimal("46000"))).isEqualTo(new BigDecimal("52.50"));
        assertThat(calculateur.calcul(new BigDecimal("47000"))).isEqualTo(new BigDecimal("55.00"));
        assertThat(calculateur.calcul(new BigDecimal("48000"))).isEqualTo(new BigDecimal("57.50"));
        assertThat(calculateur.calcul(new BigDecimal("49000"))).isEqualTo(new BigDecimal("60.00"));
        assertThat(calculateur.calcul(new BigDecimal("50000"))).isEqualTo(new BigDecimal("62.50"));
        assertThat(calculateur.calcul(new BigDecimal("51000"))).isEqualTo(new BigDecimal("69.00"));
        assertThat(calculateur.calcul(new BigDecimal("52000"))).isEqualTo(new BigDecimal("75.50"));
        assertThat(calculateur.calcul(new BigDecimal("53000"))).isEqualTo(new BigDecimal("82.00"));
        assertThat(calculateur.calcul(new BigDecimal("54000"))).isEqualTo(new BigDecimal("88.50"));
        assertThat(calculateur.calcul(new BigDecimal("55000"))).isEqualTo(new BigDecimal("95.00"));
        assertThat(calculateur.calcul(new BigDecimal("56000"))).isEqualTo(new BigDecimal("101.50"));
        assertThat(calculateur.calcul(new BigDecimal("57000"))).isEqualTo(new BigDecimal("108.00"));
        assertThat(calculateur.calcul(new BigDecimal("58000"))).isEqualTo(new BigDecimal("114.50"));
        assertThat(calculateur.calcul(new BigDecimal("59000"))).isEqualTo(new BigDecimal("121.00"));
        assertThat(calculateur.calcul(new BigDecimal("60000"))).isEqualTo(new BigDecimal("127.50"));
        assertThat(calculateur.calcul(new BigDecimal("61000"))).isEqualTo(new BigDecimal("134.00"));
        assertThat(calculateur.calcul(new BigDecimal("62000"))).isEqualTo(new BigDecimal("140.50"));
        assertThat(calculateur.calcul(new BigDecimal("63000"))).isEqualTo(new BigDecimal("147.00"));
        assertThat(calculateur.calcul(new BigDecimal("64000"))).isEqualTo(new BigDecimal("153.50"));
        assertThat(calculateur.calcul(new BigDecimal("65000"))).isEqualTo(new BigDecimal("160.00"));
        assertThat(calculateur.calcul(new BigDecimal("66000"))).isEqualTo(new BigDecimal("166.50"));
        assertThat(calculateur.calcul(new BigDecimal("67000"))).isEqualTo(new BigDecimal("173.00"));
        assertThat(calculateur.calcul(new BigDecimal("68000"))).isEqualTo(new BigDecimal("179.50"));
        assertThat(calculateur.calcul(new BigDecimal("69000"))).isEqualTo(new BigDecimal("186.00"));
        assertThat(calculateur.calcul(new BigDecimal("70000"))).isEqualTo(new BigDecimal("192.50"));
        assertThat(calculateur.calcul(new BigDecimal("71000"))).isEqualTo(new BigDecimal("199.00"));
        assertThat(calculateur.calcul(new BigDecimal("72000"))).isEqualTo(new BigDecimal("205.50"));
        assertThat(calculateur.calcul(new BigDecimal("73000"))).isEqualTo(new BigDecimal("212.00"));
        assertThat(calculateur.calcul(new BigDecimal("74000"))).isEqualTo(new BigDecimal("218.50"));
        assertThat(calculateur.calcul(new BigDecimal("75000"))).isEqualTo(new BigDecimal("225.00"));
        assertThat(calculateur.calcul(new BigDecimal("76000"))).isEqualTo(new BigDecimal("236.00"));
        assertThat(calculateur.calcul(new BigDecimal("77000"))).isEqualTo(new BigDecimal("247.00"));
        assertThat(calculateur.calcul(new BigDecimal("78000"))).isEqualTo(new BigDecimal("258.00"));
        assertThat(calculateur.calcul(new BigDecimal("79000"))).isEqualTo(new BigDecimal("269.00"));
        assertThat(calculateur.calcul(new BigDecimal("80000"))).isEqualTo(new BigDecimal("280.00"));
        assertThat(calculateur.calcul(new BigDecimal("81000"))).isEqualTo(new BigDecimal("291.00"));
        assertThat(calculateur.calcul(new BigDecimal("82000"))).isEqualTo(new BigDecimal("302.00"));
        assertThat(calculateur.calcul(new BigDecimal("83000"))).isEqualTo(new BigDecimal("313.00"));
        assertThat(calculateur.calcul(new BigDecimal("84000"))).isEqualTo(new BigDecimal("324.00"));
        assertThat(calculateur.calcul(new BigDecimal("85000"))).isEqualTo(new BigDecimal("335.00"));
        assertThat(calculateur.calcul(new BigDecimal("86000"))).isEqualTo(new BigDecimal("346.00"));
        assertThat(calculateur.calcul(new BigDecimal("87000"))).isEqualTo(new BigDecimal("357.00"));
        assertThat(calculateur.calcul(new BigDecimal("88000"))).isEqualTo(new BigDecimal("368.00"));
        assertThat(calculateur.calcul(new BigDecimal("89000"))).isEqualTo(new BigDecimal("379.00"));
        assertThat(calculateur.calcul(new BigDecimal("90000"))).isEqualTo(new BigDecimal("390.00"));
        assertThat(calculateur.calcul(new BigDecimal("91000"))).isEqualTo(new BigDecimal("401.00"));
        assertThat(calculateur.calcul(new BigDecimal("92000"))).isEqualTo(new BigDecimal("412.00"));
        assertThat(calculateur.calcul(new BigDecimal("93000"))).isEqualTo(new BigDecimal("423.00"));
        assertThat(calculateur.calcul(new BigDecimal("94000"))).isEqualTo(new BigDecimal("434.00"));
        assertThat(calculateur.calcul(new BigDecimal("95000"))).isEqualTo(new BigDecimal("445.00"));
        assertThat(calculateur.calcul(new BigDecimal("96000"))).isEqualTo(new BigDecimal("456.00"));
        assertThat(calculateur.calcul(new BigDecimal("97000"))).isEqualTo(new BigDecimal("467.00"));
        assertThat(calculateur.calcul(new BigDecimal("98000"))).isEqualTo(new BigDecimal("478.00"));
        assertThat(calculateur.calcul(new BigDecimal("99000"))).isEqualTo(new BigDecimal("489.00"));
        assertThat(calculateur.calcul(new BigDecimal("100000"))).isEqualTo(new BigDecimal("500.00"));
        assertThat(calculateur.calcul(new BigDecimal("101000"))).isEqualTo(new BigDecimal("517.00"));
        assertThat(calculateur.calcul(new BigDecimal("102000"))).isEqualTo(new BigDecimal("534.00"));
        assertThat(calculateur.calcul(new BigDecimal("103000"))).isEqualTo(new BigDecimal("551.00"));
        assertThat(calculateur.calcul(new BigDecimal("104000"))).isEqualTo(new BigDecimal("568.00"));
        assertThat(calculateur.calcul(new BigDecimal("105000"))).isEqualTo(new BigDecimal("585.00"));
        assertThat(calculateur.calcul(new BigDecimal("106000"))).isEqualTo(new BigDecimal("602.00"));
        assertThat(calculateur.calcul(new BigDecimal("107000"))).isEqualTo(new BigDecimal("619.00"));
        assertThat(calculateur.calcul(new BigDecimal("108000"))).isEqualTo(new BigDecimal("636.00"));
        assertThat(calculateur.calcul(new BigDecimal("109000"))).isEqualTo(new BigDecimal("653.00"));
        assertThat(calculateur.calcul(new BigDecimal("110000"))).isEqualTo(new BigDecimal("670.00"));
        assertThat(calculateur.calcul(new BigDecimal("111000"))).isEqualTo(new BigDecimal("687.00"));
        assertThat(calculateur.calcul(new BigDecimal("112000"))).isEqualTo(new BigDecimal("704.00"));
        assertThat(calculateur.calcul(new BigDecimal("113000"))).isEqualTo(new BigDecimal("721.00"));
        assertThat(calculateur.calcul(new BigDecimal("114000"))).isEqualTo(new BigDecimal("738.00"));
        assertThat(calculateur.calcul(new BigDecimal("115000"))).isEqualTo(new BigDecimal("755.00"));
        assertThat(calculateur.calcul(new BigDecimal("116000"))).isEqualTo(new BigDecimal("772.00"));
        assertThat(calculateur.calcul(new BigDecimal("117000"))).isEqualTo(new BigDecimal("789.00"));
        assertThat(calculateur.calcul(new BigDecimal("118000"))).isEqualTo(new BigDecimal("806.00"));
        assertThat(calculateur.calcul(new BigDecimal("119000"))).isEqualTo(new BigDecimal("823.00"));
        assertThat(calculateur.calcul(new BigDecimal("120000"))).isEqualTo(new BigDecimal("840.00"));
        assertThat(calculateur.calcul(new BigDecimal("121000"))).isEqualTo(new BigDecimal("857.00"));
        assertThat(calculateur.calcul(new BigDecimal("122000"))).isEqualTo(new BigDecimal("874.00"));
        assertThat(calculateur.calcul(new BigDecimal("123000"))).isEqualTo(new BigDecimal("891.00"));
        assertThat(calculateur.calcul(new BigDecimal("124000"))).isEqualTo(new BigDecimal("908.00"));
        assertThat(calculateur.calcul(new BigDecimal("125000"))).isEqualTo(new BigDecimal("925.00"));
        assertThat(calculateur.calcul(new BigDecimal("675000"))).isEqualTo(new BigDecimal("15225.00"));
        assertThat(calculateur.calcul(new BigDecimal("2000000"))).isEqualTo(new BigDecimal("46000.00"));
    }
}
