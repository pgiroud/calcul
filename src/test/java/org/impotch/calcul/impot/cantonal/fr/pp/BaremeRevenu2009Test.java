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
package org.impotch.calcul.impot.cantonal.fr.pp;


import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.impotch.bareme.Bareme;

import static org.assertj.core.api.Assertions.assertThat;
import static org.impotch.calcul.impot.cantonal.fr.ContexteTestCH_FR.CTX_TST_CH_FR;

public class BaremeRevenu2009Test {

	private FournisseurRegleImpotCantonalFR fournisseur = CTX_TST_CH_FR.getFournisseurRegleImpotCantonalFR();
	
	private Bareme bareme;
	
	@BeforeEach
	public void setUp() throws Exception {
		bareme = fournisseur.getBaremeRevenu(2009);
	}

	private void calcul(int montantImposable, String attendu) {
		assertThat(bareme.calcul(new BigDecimal(montantImposable))).isEqualTo(new BigDecimal(attendu));
//		String retour = bareme.calcul(new BigDecimal(montantImposable)).toPlainString();
//	    assertEquals("Barème pour " + montantImposable,attendu,retour);
	}
	
	@Test
	public void seul() {
		calcul(5000,"0.00");
		calcul(5100,"51.00");
		calcul(6100,"76.90");
		calcul(7800,"132.95");
		calcul(12000,"336.10");
		calcul(300000,"40500.00");
	}
}
