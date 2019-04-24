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
package org.impotch.calcul.impot.france;

import java.math.BigDecimal;

import org.impotch.bareme.Bareme;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestCalculImpotBaremeFrance {

    @Test
    public void calcul2007() {
        FournisseurFrance fournisseur = new FournisseurFrance();
        Bareme bareme = fournisseur.getBaremeRevenu(2007);
        assertThat(bareme.calcul(BigDecimal.valueOf(45000))).isEqualTo(new BigDecimal("8191.00"));
        assertThat(bareme.calcul(BigDecimal.valueOf(320611))).isEqualTo(new BigDecimal("116181.00"));
        //		assertEquals("Impôt 2007 pour revenu de 45000",new BigDecimal("8191.00"),bareme.calcul(new BigDecimal("45000")));
//		assertEquals("Impôt 2007 pour revenu de 320611",new BigDecimal("116181.00"),bareme.calcul(new BigDecimal("320611")));
    }

    @Test
    public void calcul2008() {
        FournisseurFrance fournisseur = new FournisseurFrance();
        Bareme bareme = fournisseur.getBaremeRevenu(2008);
        assertThat(bareme.calcul(BigDecimal.valueOf(26200))).isEqualTo(new BigDecimal("2397.00"));
//		assertEquals("Impôt 2008 pour revenu de 26200",new BigDecimal("2397.00"),bareme.calcul(new BigDecimal("26200")));
    }

}
