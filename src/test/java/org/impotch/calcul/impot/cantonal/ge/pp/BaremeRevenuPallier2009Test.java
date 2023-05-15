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
import org.impotch.calcul.impot.cantonal.ge.pp.avant2010.ConstructeurBaremeParTrancheIndexe;
import org.impotch.calcul.impot.indexation.SimpleFournisseurIndicePeriodique;
import org.impotch.util.TypeArrondi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


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
        bareme = new ConstructeurBaremeParTrancheIndexe()
                // Attention, ce barème n'a jamais été utilisé.
                // Il a simplement servi de base pour les barèmes 2010, 2011, ...
                .valideDepuis(2009)
                .anneeReferenceRencherissement(2009)
                .indexateur(construireIndexateurSurBaseIndice())
                .premiereTranche(17493, "0 %")
                .tranche(17493, 21076, "8 %")
                .tranche(21076, 23184, "9 %")
                .tranche(23184, 25291, "10 %")
                .tranche(25291, 27399, "11 %")
                .tranche(27399, 32668, "12 %")
                .tranche(32668, 36883, "13 %")
                .tranche(36883, 41099, "14 %")
                .tranche(41099, 45314, "14.5 %")
                .tranche(45314, 72713, "15 %")
                .tranche(72713, 119081, "15.5 %")
                .tranche(119081, 160179, "16 %")
                .tranche(160179, 181256, "16.5 %")
                .tranche(181256, 259238, "17 %")
                .tranche(259238, 276099, "17.5 %")
                .tranche(276099, 388857, "18 %")
                .tranche(388857, 609103, "18.5 %")
                .derniereTranche(609103, "19 %")
                .typeArrondiTranche(TypeArrondi.CINQ_CENTIEMES_LES_PLUS_PROCHES).construire(2009);
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
