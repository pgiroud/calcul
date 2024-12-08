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
package org.impotch.calcul.impot.cantonal.ge.pp;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.impotch.calcul.impot.indexation.FournisseurIndicePeriodique;

import org.impotch.bareme.Bareme;
import org.impotch.calcul.impot.indexation.SimpleFournisseurIndicePeriodique;
import org.impotch.util.TypeArrondi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.impotch.calcul.impot.cantonal.ge.pp.ConstructeurBaremeParTrancheIndexe.unConstructeurDeBaremeParTrancheIndexee;


public class BaremeRevenuPallier2009Test {

    private Bareme bareme;

    private FournisseurIndicePeriodique construireIndexateurSurBaseIndice() {
        SimpleFournisseurIndicePeriodique fournisseur = new SimpleFournisseurIndicePeriodique();
        Map<Integer, BigDecimal> indices = new HashMap();
        indices.put(2009, BigDecimal.ONE);
        fournisseur.setIndices(indices);
        return fournisseur;
    }

    @BeforeEach
    public void setUp() throws Exception {
        bareme = unConstructeurDeBaremeParTrancheIndexee()
                // Attention, ce barème n'a jamais été utilisé.
                // Il a simplement servi de base pour les barèmes 2010, 2011, ...
                .valideDepuis(2009)
                .anneeReferenceRencherissement(2009)
                .indexateur(construireIndexateurSurBaseIndice())
                .typeArrondiTranche(TypeArrondi.CINQ_CENTIEMES_LES_PLUS_PROCHES)
                .typeArrondiGlobal(TypeArrondi.CINQ_CENTIEMES_LES_PLUS_PROCHES)
                .jusqua(17493).taux("0 %")
                .puisJusqua(21076).taux("8 %")
                .puisJusqua(23184).taux("9 %")
                .puisJusqua(25291).taux("10 %")
                .puisJusqua(27399).taux("11 %")
                .puisJusqua(32668).taux("12 %")
                .puisJusqua(36883).taux("13 %")
                .puisJusqua(41099).taux("14 %")
                .puisJusqua(45314).taux("14.5 %")
                .puisJusqua(72713).taux("15 %")
                .puisJusqua(119081).taux("15.5 %")
                .puisJusqua(160179).taux("16 %")
                .puisJusqua(181256).taux("16.5 %")
                .puisJusqua(259238).taux("17 %")
                .puisJusqua(276099).taux("17.5 %")
                .puisJusqua( 388857).taux("18 %")
                .puisJusqua(609103).taux("18.5 %")
                .puis().taux("19 %")
                .construire(2009);
    }

    private void rev(int revenu, String impot) {
        assertThat(bareme.calcul(BigDecimal.valueOf(revenu))).isEqualTo(new BigDecimal(impot));
    }

    @Test
    public void revenu2009() {
        // barème non utilisé mais précisé dans la loi.
        rev(17493, "0.00");
        rev(17494, "0.10");
        rev(21076, "286.65");
        rev(23184, "476.35");
        rev(25291, "687.05");
        rev(26734, "845.80");
        rev(27399, "918.95");
        rev(32668, "1551.25");
        rev(36883, "2099.20");
        rev(41099, "2689.45");
        rev(45314, "3300.65");
        rev(72713, "7410.50");
        rev(119081, "14597.55");
        rev(160179, "21173.25");
        rev(181256, "24650.95");
        rev(243374, "35211.00");
        rev(259238, "37907.90");
        rev(276099, "40858.60");
        rev(388857, "61155.05");
        rev(609103, "101900.55");
        rev(743374, "127412.05");
        rev(1000000, "176171.00");
        rev(2000000, "366171.00");
    }

}
