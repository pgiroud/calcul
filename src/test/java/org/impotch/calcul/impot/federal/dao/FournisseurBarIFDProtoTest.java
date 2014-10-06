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

import org.impotch.util.TypeArrondi;
import org.junit.Test;

import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;

public class FournisseurBarIFDProtoTest {

    @Test
    public void test2006SeulPostNumerando() {
        ConstructeurBaremeTxMarginalConstantParTranche constructeur = new ConstructeurBaremeTxMarginalConstantParTranche();
        constructeur.tranche( 10600,  "0 %");
        constructeur.tranche( 23100,  "0.77 %");
        constructeur.tranche( 30300,  "0.88 %");
        constructeur.tranche( 40400,  "2.64 %");
        constructeur.tranche( 53100,  "2.97 %");
        constructeur.tranche( 57200,  "5.94 %");
        constructeur.tranche( 75800,  "6.60 %");
        constructeur.tranche( 98500,  "8.80 %");
        constructeur.tranche(128800, "11.00 %");
        constructeur.derniereTranche("13.20 %");
        constructeur.atteintTauxEffectifMaximum("11.5 %",BigDecimal.valueOf(100));
        constructeur.typeArrondiSurChaqueTranche(TypeArrondi.CINQ_CTS_INF);

        BaremeTxMarginalConstantParTrancheEtTxEffectifBorneAtteint bar =  constructeur.construire();

        assertThat(bar.calcul(BigDecimal.valueOf( 10000))).isEqualTo("0.00");
        assertThat(bar.calcul(BigDecimal.valueOf( 10600))).isEqualTo("0.00");
        assertThat(bar.calcul(BigDecimal.valueOf( 23100))).isEqualTo("96.25");
        assertThat(bar.calcul(BigDecimal.valueOf( 30300))).isEqualTo("159.60");
        assertThat(bar.calcul(BigDecimal.valueOf( 40400))).isEqualTo("426.20");
        assertThat(bar.calcul(BigDecimal.valueOf( 53100))).isEqualTo("803.35");
        assertThat(bar.calcul(BigDecimal.valueOf( 57200))).isEqualTo("1046.85");
        assertThat(bar.calcul(BigDecimal.valueOf( 75800))).isEqualTo("2274.45");
        assertThat(bar.calcul(BigDecimal.valueOf( 98500))).isEqualTo("4272.05");
        assertThat(bar.calcul(BigDecimal.valueOf(128800))).isEqualTo("7605.05");
        assertThat(bar.calcul(BigDecimal.valueOf(552700))).isEqualTo("63559.85");
        assertThat(bar.calcul(BigDecimal.valueOf(552800))).isEqualTo("63572.00");
    }


    @Test
    public void test2006FamillePostNumerando() {
        ConstructeurBaremeTxMarginalConstantParTranche constructeur = new ConstructeurBaremeTxMarginalConstantParTranche();
        constructeur.tranche(  20700," 0 %");
        constructeur.tranche(  37200," 1 %");
        constructeur.tranche(  42700," 2 %");
        constructeur.tranche(  55000," 3 %");
        constructeur.tranche(  66000," 4 %");
        constructeur.tranche(  75600," 5 %");
        constructeur.tranche(  83900," 6 %");
        constructeur.tranche(  90800," 7 %");
        constructeur.tranche(  96300," 8 %");
        constructeur.tranche( 100400," 9 %");
        constructeur.tranche( 103300,"10 %");
        constructeur.tranche( 104700,"11 %");
        constructeur.tranche( 106100,"12 %");
        constructeur.derniereTranche("13 %");
        constructeur.atteintTauxEffectifMaximum("11.5 %",BigDecimal.valueOf(100));
        constructeur.typeArrondiSurChaqueTranche(TypeArrondi.CINQ_CTS_INF);
        BaremeTxMarginalConstantParTrancheEtTxEffectifBorneAtteint bar =  constructeur.construire();

        assertThat(bar.calcul(BigDecimal.valueOf(  20700))).isEqualTo("0.00");
        assertThat(bar.calcul(BigDecimal.valueOf(  37200))).isEqualTo("165.00");
        assertThat(bar.calcul(BigDecimal.valueOf(  42700))).isEqualTo("275.00");
        assertThat(bar.calcul(BigDecimal.valueOf(  55000))).isEqualTo("644.00");
        assertThat(bar.calcul(BigDecimal.valueOf(  66000))).isEqualTo("1084.00");
        assertThat(bar.calcul(BigDecimal.valueOf(  75600))).isEqualTo("1564.00");
        assertThat(bar.calcul(BigDecimal.valueOf(  83900))).isEqualTo("2062.00");
        assertThat(bar.calcul(BigDecimal.valueOf(  90800))).isEqualTo("2545.00");
        assertThat(bar.calcul(BigDecimal.valueOf(  96300))).isEqualTo("2985.00");
        assertThat(bar.calcul(BigDecimal.valueOf( 100400))).isEqualTo("3354.00");
        assertThat(bar.calcul(BigDecimal.valueOf( 103300))).isEqualTo("3644.00");
        assertThat(bar.calcul(BigDecimal.valueOf( 104700))).isEqualTo("3798.00");
        assertThat(bar.calcul(BigDecimal.valueOf( 106100))).isEqualTo("3966.00");
        assertThat(bar.calcul(BigDecimal.valueOf( 655100))).isEqualTo("75336.00");
        assertThat(bar.calcul(BigDecimal.valueOf( 655200))).isEqualTo("75348.00");
    }

    @Test
    public void test2011SeulPostNumerando() {
        ConstructeurBaremeTxMarginalConstantParTranche constructeur = new ConstructeurBaremeTxMarginalConstantParTranche();
        constructeur.tranche( 14400,  "0 %");
        constructeur.tranche( 31500,  "0.77 %");
        constructeur.tranche( 41200,  "0.88 %");
        constructeur.tranche( 55000,  "2.64 %");
        constructeur.tranche( 72200,  "2.97 %");
        constructeur.tranche( 77700,  "5.94 %");
        constructeur.tranche(103000,  "6.60 %");
        constructeur.tranche(133900,  "8.80 %");
        constructeur.tranche(175000, "11.00 %");
        constructeur.derniereTranche("13.20 %");
        constructeur.atteintTauxEffectifMaximum("11.5 %",BigDecimal.valueOf(100));
        constructeur.typeArrondiSurChaqueTranche(TypeArrondi.CINQ_CTS_INF);

        BaremeTxMarginalConstantParTrancheEtTxEffectifBorneAtteint bar =  constructeur.construire();
        assertThat(bar.calcul(BigDecimal.valueOf( 14400))).isEqualTo("0.00");
        assertThat(bar.calcul(BigDecimal.valueOf( 31500))).isEqualTo("131.65");
        assertThat(bar.calcul(BigDecimal.valueOf( 41200))).isEqualTo("217.00");
        assertThat(bar.calcul(BigDecimal.valueOf( 55000))).isEqualTo("581.30");
        assertThat(bar.calcul(BigDecimal.valueOf( 72200))).isEqualTo("1092.10");
        assertThat(bar.calcul(BigDecimal.valueOf( 77700))).isEqualTo("1418.80");
        assertThat(bar.calcul(BigDecimal.valueOf(103000))).isEqualTo("3088.60");
        assertThat(bar.calcul(BigDecimal.valueOf(133900))).isEqualTo("5807.80");
        assertThat(bar.calcul(BigDecimal.valueOf(175000))).isEqualTo("10328.80");
        assertThat(bar.calcul(BigDecimal.valueOf(751200))).isEqualTo("86387.20");
        assertThat(bar.calcul(BigDecimal.valueOf(751300))).isEqualTo("86399.50");
    }

    @Test
    public void test2011FamillePostNumerando() {
        ConstructeurBaremeTxMarginalConstantParTranche constructeur = new ConstructeurBaremeTxMarginalConstantParTranche();
        constructeur.tranche(  28100," 0 %");
        constructeur.tranche(  50400," 1 %");
        constructeur.tranche(  57900," 2 %");
        constructeur.tranche(  74700," 3 %");
        constructeur.tranche(  89700," 4 %");
        constructeur.tranche( 102700," 5 %");
        constructeur.tranche( 113900," 6 %");
        constructeur.tranche( 123300," 7 %");
        constructeur.tranche( 130800," 8 %");
        constructeur.tranche( 136300," 9 %");
        constructeur.tranche( 140200,"10 %");
        constructeur.tranche( 142100,"11 %");
        constructeur.tranche( 144000,"12 %");
        constructeur.derniereTranche("13 %");
        constructeur.atteintTauxEffectifMaximum("11.5 %",BigDecimal.valueOf(100));
        constructeur.typeArrondiSurChaqueTranche(TypeArrondi.CINQ_CTS_INF);
        BaremeTxMarginalConstantParTrancheEtTxEffectifBorneAtteint bar =  constructeur.construire();

        assertThat(bar.calcul(BigDecimal.valueOf(  28100))).isEqualTo("0.00");
        assertThat(bar.calcul(BigDecimal.valueOf(  50400))).isEqualTo("223.00");
        assertThat(bar.calcul(BigDecimal.valueOf(  57900))).isEqualTo("373.00");
        assertThat(bar.calcul(BigDecimal.valueOf(  74700))).isEqualTo("877.00");
        assertThat(bar.calcul(BigDecimal.valueOf(  89700))).isEqualTo("1477.00");
        assertThat(bar.calcul(BigDecimal.valueOf( 102700))).isEqualTo("2127.00");
        assertThat(bar.calcul(BigDecimal.valueOf( 113900))).isEqualTo("2799.00");
        assertThat(bar.calcul(BigDecimal.valueOf( 123300))).isEqualTo("3457.00");
        assertThat(bar.calcul(BigDecimal.valueOf( 130800))).isEqualTo("4057.00");
        assertThat(bar.calcul(BigDecimal.valueOf( 136300))).isEqualTo("4552.00");
        assertThat(bar.calcul(BigDecimal.valueOf( 140200))).isEqualTo("4942.00");
        assertThat(bar.calcul(BigDecimal.valueOf( 142100))).isEqualTo("5151.00");
        assertThat(bar.calcul(BigDecimal.valueOf( 144000))).isEqualTo("5379.00");
        assertThat(bar.calcul(BigDecimal.valueOf( 889400))).isEqualTo("102281.00");    }

    @Test
    public void test2012SeulPostNumerando() {
        ConstructeurBaremeTxMarginalConstantParTranche constructeur = new ConstructeurBaremeTxMarginalConstantParTranche();
        constructeur.tranche(14500,"0 %");
        constructeur.tranche(31600,"0.77 %");
        constructeur.tranche(41400,"0.88 %");
        constructeur.tranche(55200,"2.64 %");
        constructeur.tranche(72500,"2.97 %");
        constructeur.tranche(78100,"5.94 %");
        constructeur.tranche(103600,"6.60 %");
        constructeur.tranche(134600,"8.80 %");
        constructeur.tranche(176000,"11.00 %");
        constructeur.derniereTranche("13.20 %");
        constructeur.atteintTauxEffectifMaximum("11.5 %",BigDecimal.valueOf(100));
        constructeur.typeArrondiSurChaqueTranche(TypeArrondi.CINQ_CTS_INF);
        BaremeTxMarginalConstantParTrancheEtTxEffectifBorneAtteint bar =  constructeur.construire();

        bar.setNoTrancheDeltaApplicable(1);

        assertThat(bar.calcul(BigDecimal.valueOf(14500))).isEqualTo("0.00") ;
        assertThat(bar.calcul(BigDecimal.valueOf(31600))).isEqualTo("131.65") ;
        assertThat(bar.calcul(BigDecimal.valueOf(41400))).isEqualTo("217.90");

    }

    @Test
    public void test2012FamillePostNumerando() {
        ConstructeurBaremeTxMarginalConstantParTranche constructeur = new ConstructeurBaremeTxMarginalConstantParTranche();
        constructeur.tranche(  28300," 0 %");
        constructeur.tranche(  50900," 1 %");
        constructeur.tranche(  58400," 2 %");
        constructeur.tranche(  75300," 3 %");
        constructeur.tranche(  90300," 4 %");
        constructeur.tranche( 103400," 5 %");
        constructeur.tranche( 114700," 6 %");
        constructeur.tranche( 124200," 7 %");
        constructeur.tranche( 131700," 8 %");
        constructeur.tranche( 137300," 9 %");
        constructeur.tranche( 141200,"10 %");
        constructeur.tranche( 143100,"11 %");
        constructeur.tranche( 145000,"12 %");
        constructeur.derniereTranche("13 %");
        constructeur.atteintTauxEffectifMaximum("11.5 %",BigDecimal.valueOf(100));
        constructeur.typeArrondiSurChaqueTranche(TypeArrondi.CINQ_CTS_INF);
        BaremeTxMarginalConstantParTrancheEtTxEffectifBorneAtteint bar =  constructeur.construire();

        assertThat(bar.calcul(BigDecimal.valueOf(0))).isEqualTo("0.00") ;
        assertThat(bar.calcul(BigDecimal.valueOf(10000))).isEqualTo("0.00") ;
        assertThat(bar.calcul(BigDecimal.valueOf(28300))).isEqualTo("0.00") ;
        assertThat(bar.calcul(BigDecimal.valueOf(28400))).isEqualTo("1.00") ;
        assertThat(bar.calcul(BigDecimal.valueOf(50900))).isEqualTo("226.00");
        assertThat(bar.calcul(BigDecimal.valueOf(58400))).isEqualTo("376.00");
        assertThat(bar.calcul(BigDecimal.valueOf(75300))).isEqualTo("883.00");
        assertThat(bar.calcul(BigDecimal.valueOf(90300))).isEqualTo("1483.00");
        assertThat(bar.calcul(BigDecimal.valueOf(103400))).isEqualTo("2138.00");
        assertThat(bar.calcul(BigDecimal.valueOf(114700))).isEqualTo("2816.00");
        assertThat(bar.calcul(BigDecimal.valueOf(124200))).isEqualTo("3481.00");
        assertThat(bar.calcul(BigDecimal.valueOf(131700))).isEqualTo("4081.00");
        assertThat(bar.calcul(BigDecimal.valueOf(137300))).isEqualTo("4585.00");
        assertThat(bar.calcul(BigDecimal.valueOf(141200))).isEqualTo("4975.00");
        assertThat(bar.calcul(BigDecimal.valueOf(143100))).isEqualTo("5184.00");
        assertThat(bar.calcul(BigDecimal.valueOf(145000))).isEqualTo("5412.00");
        assertThat(bar.calcul(BigDecimal.valueOf(895800))).isEqualTo("103016.00");
        assertThat(bar.calcul(BigDecimal.valueOf(895900))).isEqualTo("103028.50");

    }
}
