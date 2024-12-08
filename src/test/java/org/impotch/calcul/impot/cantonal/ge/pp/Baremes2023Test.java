/*
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
        assertThat(bareme.calcul(100000)).isEqualTo("11516.15");
    }

    @Test
    public void revenuBorneBareme() {
        Bareme bareme = fournisseur.getBaremeRevenu(2023);
        assertThat(bareme.calcul(21752)).isEqualTo("295.85");
        assertThat(bareme.calcul(23928)).isEqualTo("491.70");
        assertThat(bareme.calcul(26102)).isEqualTo("709.10");
        assertThat(bareme.calcul(28278)).isEqualTo("948.45");
        assertThat(bareme.calcul(33716)).isEqualTo("1601.00");
        assertThat(bareme.calcul(38066)).isEqualTo("2166.50");
        assertThat(bareme.calcul(42417)).isEqualTo("2775.65");
        assertThat(bareme.calcul(46767)).isEqualTo("3406.40");
        assertThat(bareme.calcul(75045)).isEqualTo("7648.10");
        assertThat(bareme.calcul(122900)).isEqualTo("15065.65");
        assertThat(bareme.calcul(165316)).isEqualTo("21852.20");
        assertThat(bareme.calcul(187069)).isEqualTo("25441.45");
        assertThat(bareme.calcul(267552)).isEqualTo("39123.55");
        assertThat(bareme.calcul(284953)).isEqualTo("42168.75");
        assertThat(bareme.calcul(401328)).isEqualTo("63116.25");
        assertThat(bareme.calcul(628637)).isEqualTo("105168.40");
    }

    @Test
    public void fortuneBorneBareme() {
        Bareme bareme = fournisseur.getBaremeFortune(2023);
        assertThat(bareme.calcul(114_621)).isEqualTo("200.60");
        assertThat(bareme.calcul(229_240)).isEqualTo("458.50");
        assertThat(bareme.calcul(343_861)).isEqualTo("773.70");
        assertThat(bareme.calcul(458_481)).isEqualTo("1117.55");
        assertThat(bareme.calcul(687_722)).isEqualTo("1862.60");
        assertThat(bareme.calcul(916_962)).isEqualTo("2664.95");
        assertThat(bareme.calcul(1_146_202)).isEqualTo("3524.60");
        assertThat(bareme.calcul(1_375_443)).isEqualTo("4441.55");
        assertThat(bareme.calcul(1_719_304)).isEqualTo("5902.95");
    }

    @Test
    public void fortuneSupplementaireBorneBareme() {
        Bareme bareme = fournisseur.getBaremeFortuneSupplementaire(2023);
        assertThat(bareme.calcul(114_621)).isEqualTo("0.00");
        assertThat(bareme.calcul(229_240)).isEqualTo("12.90");
        assertThat(bareme.calcul(343_861)).isEqualTo("28.65");
        assertThat(bareme.calcul(458_481)).isEqualTo("63.05");
        assertThat(bareme.calcul(687_722)).isEqualTo("137.55");
        assertThat(bareme.calcul(916_962)).isEqualTo("257.90");
        assertThat(bareme.calcul(1_146_202)).isEqualTo("386.85");
        assertThat(bareme.calcul(1_375_443)).isEqualTo("570.25");
        assertThat(bareme.calcul(1_719_304)).isEqualTo("862.55");
        assertThat(bareme.calcul(3_438_607)).isEqualTo("2796.75");
    }


}
