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

public class BaremeFortune2009Test {

	private FournisseurRegleImpotCantonalFR fournisseur = CTX_TST_CH_FR.getFournisseurRegleImpotCantonalFR();
	
	private Bareme bareme;

	
	@BeforeEach
	public void setUp() throws Exception {
		bareme = fournisseur.getBaremeFortune(2009);
	}

	private void calcul(int montantImposable, String attendu) {
		assertThat(bareme.calcul(BigDecimal.valueOf(montantImposable))).isEqualTo(new BigDecimal(attendu));
	}

	@Test
	public void calcul() {
		calcul(85000,"148.75");
	}	
}
