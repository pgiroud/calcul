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

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.impotch.calcul.impot.cantonal.ge.ContexteTestCH_GE.CTX_TST_CH_GE;

public class Baremes2023Test {

    private FournisseurRegleImpotCantonalGE fournisseur = CTX_TST_CH_GE.getFournisseurRegleImpotCantonalGE();

    @Test
    public void revenu100_000() {
        Bareme bareme = fournisseur.getBaremeRevenu(2023);
        BigDecimal revenu = BigDecimal.valueOf(100000);
        BigDecimal impot = new BigDecimal("11516.15");
        assertThat(bareme.calcul(revenu)).isEqualTo(impot);
    }

    @Test
    public void revenuBorneBareme() {
        Bareme bareme = fournisseur.getBaremeRevenu(2023);
        assertThat(bareme.calcul(BigDecimal.valueOf(21752))).isEqualTo(new BigDecimal("295.85"));
        assertThat(bareme.calcul(BigDecimal.valueOf(23928))).isEqualTo(new BigDecimal("491.70"));
        assertThat(bareme.calcul(BigDecimal.valueOf(26102))).isEqualTo(new BigDecimal("709.10"));
        assertThat(bareme.calcul(BigDecimal.valueOf(28278))).isEqualTo(new BigDecimal("948.45"));
        assertThat(bareme.calcul(BigDecimal.valueOf(33716))).isEqualTo(new BigDecimal("1601.00"));
        assertThat(bareme.calcul(BigDecimal.valueOf(38066))).isEqualTo(new BigDecimal("2166.50"));
        assertThat(bareme.calcul(BigDecimal.valueOf(42417))).isEqualTo(new BigDecimal("2775.65"));
        assertThat(bareme.calcul(BigDecimal.valueOf(46767))).isEqualTo(new BigDecimal("3406.40"));
        assertThat(bareme.calcul(BigDecimal.valueOf(75045))).isEqualTo(new BigDecimal("7648.10"));
        assertThat(bareme.calcul(BigDecimal.valueOf(122900))).isEqualTo(new BigDecimal("15065.65"));
        assertThat(bareme.calcul(BigDecimal.valueOf(165316))).isEqualTo(new BigDecimal("21852.20"));
        assertThat(bareme.calcul(BigDecimal.valueOf(187069))).isEqualTo(new BigDecimal("25441.45"));
        assertThat(bareme.calcul(BigDecimal.valueOf(267552))).isEqualTo(new BigDecimal("39123.55"));
        assertThat(bareme.calcul(BigDecimal.valueOf(284953))).isEqualTo(new BigDecimal("42168.75"));
        assertThat(bareme.calcul(BigDecimal.valueOf(401328))).isEqualTo(new BigDecimal("63116.25"));
        assertThat(bareme.calcul(BigDecimal.valueOf(628637))).isEqualTo(new BigDecimal("105168.40"));
    }

    @Test
    public void fortuneBorneBareme() {
        Bareme bareme = fournisseur.getBaremeFortune(2023);
        assertThat(bareme.calcul(BigDecimal.valueOf(114_621))).isEqualTo(new BigDecimal("200.60"));
        assertThat(bareme.calcul(BigDecimal.valueOf(229_240))).isEqualTo(new BigDecimal("458.50"));
        assertThat(bareme.calcul(BigDecimal.valueOf(343_861))).isEqualTo(new BigDecimal("773.70"));
        assertThat(bareme.calcul(BigDecimal.valueOf(458_481))).isEqualTo(new BigDecimal("1117.55"));
        assertThat(bareme.calcul(BigDecimal.valueOf(687_722))).isEqualTo(new BigDecimal("1862.60"));
        assertThat(bareme.calcul(BigDecimal.valueOf(916_962))).isEqualTo(new BigDecimal("2664.95"));
        assertThat(bareme.calcul(BigDecimal.valueOf(1_146_202))).isEqualTo(new BigDecimal("3524.60"));
        assertThat(bareme.calcul(BigDecimal.valueOf(1_375_443))).isEqualTo(new BigDecimal("4441.55"));
        assertThat(bareme.calcul(BigDecimal.valueOf(1_719_304))).isEqualTo(new BigDecimal("5902.95"));
    }

    @Test
    public void fortuneSupplementaireBorneBareme() {
        Bareme bareme = fournisseur.getBaremeFortuneSupplementaire(2023);
        assertThat(bareme.calcul(BigDecimal.valueOf(114_621))).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(BigDecimal.valueOf(229_240))).isEqualTo(new BigDecimal("12.90"));
        assertThat(bareme.calcul(BigDecimal.valueOf(343_861))).isEqualTo(new BigDecimal("28.65"));
        assertThat(bareme.calcul(BigDecimal.valueOf(458_481))).isEqualTo(new BigDecimal("63.05"));
        assertThat(bareme.calcul(BigDecimal.valueOf(687_722))).isEqualTo(new BigDecimal("137.55"));
        assertThat(bareme.calcul(BigDecimal.valueOf(916_962))).isEqualTo(new BigDecimal("257.90"));
        assertThat(bareme.calcul(BigDecimal.valueOf(1_146_202))).isEqualTo(new BigDecimal("386.85"));
        assertThat(bareme.calcul(BigDecimal.valueOf(1_375_443))).isEqualTo(new BigDecimal("570.25"));
        assertThat(bareme.calcul(BigDecimal.valueOf(1_719_304))).isEqualTo(new BigDecimal("862.55"));
        assertThat(bareme.calcul(BigDecimal.valueOf(3_438_607))).isEqualTo(new BigDecimal("2796.75"));
    }


}
