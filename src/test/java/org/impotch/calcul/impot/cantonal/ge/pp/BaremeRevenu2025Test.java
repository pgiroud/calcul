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

public class BaremeRevenu2025Test {
    private FournisseurRegleImpotCantonalGE fournisseur = CTX_TST_CH_GE.getFournisseurRegleImpotCantonalGE();

    @Test
    public void borneBareme() {
        Bareme bareme = fournisseur.getBaremeRevenu(2025);
        assertThat(bareme.calcul(new BigDecimal("18649"))).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(new BigDecimal("22469"))).isEqualTo(new BigDecimal("278.85"));
        assertThat(bareme.calcul(new BigDecimal("24716"))).isEqualTo(new BigDecimal("463.10"));
        assertThat(bareme.calcul(new BigDecimal("26962"))).isEqualTo(new BigDecimal("667.50"));
        assertThat(bareme.calcul(new BigDecimal("29210"))).isEqualTo(new BigDecimal("892.30"));
        assertThat(bareme.calcul(new BigDecimal("34827"))).isEqualTo(new BigDecimal("1504.55"));
        assertThat(bareme.calcul(new BigDecimal("39320"))).isEqualTo(new BigDecimal("2012.25"));
        assertThat(bareme.calcul(new BigDecimal("43815"))).isEqualTo(new BigDecimal("2565.15"));
        assertThat(bareme.calcul(new BigDecimal("48309"))).isEqualTo(new BigDecimal("3140.40"));
        assertThat(bareme.calcul(new BigDecimal("77518"))).isEqualTo(new BigDecimal("6996.00"));
        assertThat(bareme.calcul(new BigDecimal("126950"))).isEqualTo(new BigDecimal("14015.35"));
        assertThat(bareme.calcul(new BigDecimal("170764"))).isEqualTo(new BigDecimal("20587.45"));
        assertThat(bareme.calcul(new BigDecimal("193234"))).isEqualTo(new BigDecimal("24092.75"));
        assertThat(bareme.calcul(new BigDecimal("276369"))).isEqualTo(new BigDecimal("37228.10"));
        assertThat(bareme.calcul(new BigDecimal("294345"))).isEqualTo(new BigDecimal("40104.25"));
        assertThat(bareme.calcul(new BigDecimal("414554"))).isEqualTo(new BigDecimal("60299.35"));
        assertThat(bareme.calcul(new BigDecimal("649355"))).isEqualTo(new BigDecimal("101624.35"));

    }

    @Test
    public void dansLaPremiereTranche() {
        Bareme bareme = fournisseur.getBaremeRevenu(2025);
        // (20_000 - 18_649) * 7,3 % = 98,623
        assertThat(bareme.calcul(BigDecimal.valueOf(20_000))).isEqualTo(new BigDecimal("98.60"));
    }

    @Test
    public void dansLaDeuxiemeTranche() {
        Bareme bareme = fournisseur.getBaremeRevenu(2025);
        // (22_469 - 18_649) * 7,3 %  + (24_000 - 22_469) * 8,2 % = 278.85 + 125,542 = 404,392
        assertThat(bareme.calcul(BigDecimal.valueOf(24_000))).isEqualTo(new BigDecimal("404.40"));
    }

    @Test
    public void zero() {
        Bareme bareme = fournisseur.getBaremeRevenu(2025);
        assertThat(bareme.calcul(BigDecimal.ZERO)).isEqualTo(new BigDecimal("0.00"));
    }

    @Test
    public void negatif() {
        Bareme bareme = fournisseur.getBaremeRevenu(2025);
        assertThat(bareme.calcul(BigDecimal.ONE.movePointRight(6).negate())).isEqualTo(new BigDecimal("0.00"));
    }

    @Test
    public void enormeNegatif() {
        Bareme bareme = fournisseur.getBaremeRevenu(2025);
        assertThat(bareme.calcul(BigDecimal.ONE.movePointRight(40).negate())).isEqualTo(new BigDecimal("0.00"));
    }

    @Test
    public void enorme() {
        Bareme bareme = fournisseur.getBaremeRevenu(2025);
        BigDecimal assiette = new BigDecimal("1000000000000000000000000000000000");
        BigDecimal impotTauxMax = new BigDecimal("179999999999999999999999999984740.45");
        BigDecimal impotCalcule = bareme.calcul(assiette);
        assertThat(impotCalcule).isEqualTo(impotTauxMax);
    }

}
