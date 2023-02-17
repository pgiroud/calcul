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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.impotch.calcul.impot.cantonal.ge.ContexteTestCH_GE.CTX_TST_CH_GE;

public class BaremesRevenu2017Test {

    private FournisseurRegleImpotCantonalGE fournisseur = CTX_TST_CH_GE.getFournisseurRegleImpotCantonalGE();

    private Bareme bareme;

    @BeforeEach
    public void setUp() throws Exception {
        bareme = fournisseur.getBaremeRevenu(2017);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/ge/BasImp2017.csv", delimiter = '\t', numLinesToSkip = 1)
    public void baremeRevenu(int revenu, String impotSeul, String impotFamille) {
        BigDecimal impot = new BigDecimal(impotSeul.replace(',','.')).setScale(2);
        assertThat(bareme.calcul(new BigDecimal(revenu))).isEqualTo(impot);
    }

}
