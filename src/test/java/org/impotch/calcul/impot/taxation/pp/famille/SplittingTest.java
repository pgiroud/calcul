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
package org.impotch.calcul.impot.taxation.pp.famille;


import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;


import org.impotch.calcul.impot.taxation.pp.*;

import org.impotch.util.TypeArrondi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.impotch.bareme.ConstructeurBareme.unBaremeATauxMarginal;
import static org.impotch.util.TypeArrondi.CINQ_CENTIEMES_LES_PLUS_PROCHES;
public class SplittingTest {

    private StrategieProductionImpotFamille splitting;

    @BeforeEach
    public void setUp() throws Exception {
        splitting = new Splitting(unBaremeATauxMarginal()
                .typeArrondiSurChaqueTranche(CINQ_CENTIEMES_LES_PLUS_PROCHES)
                .jusqua(0).taux("0")
                .puisJusqua(1000).taux("1 %")
                .puisJusqua(2000).taux("2 %")
                .puisJusqua(3000).taux("3 %")
                .puisJusqua(4000).taux("4 %")
                .puis().taux("5 %")
                .construire(), "50 %");
    }


    @Test
    public void produireImpot() {
        BigDecimal impot = splitting.produireImpotAnnuel(getFamille(), new BigDecimal("2000"), new BigDecimal("2000"));
        impot = TypeArrondi.CINQ_CENTIEMES_LES_PLUS_PROCHES.arrondirMontant(impot);
        assertThat(impot).isEqualTo(new BigDecimal("20.00"));
//		assertEquals("Transfo Impot",new BigDecimal("20.00"),impot);
    }

    private SituationFamiliale getFamille() {
        return ConstructeurSituationFamiliale.couple().fournir();
    }
}
