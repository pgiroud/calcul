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


import java.math.BigDecimal;

import org.impotch.bareme.Bareme;
import static org.impotch.calcul.impot.cantonal.ge.ContexteTestCH_GE.CTX_TST_CH_GE;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BaremeFortune2011Test {
	private FournisseurRegleImpotCantonalGE fournisseur = CTX_TST_CH_GE.getFournisseurRegleImpotCantonalGE();

	@Test
	@DisplayName("Barème fortune 2011 pour 1 million")
	public void baremePour1million() {
		Bareme bareme = fournisseur.getBaremeFortune(2011);
		assertThat(bareme.calcul(BigDecimal.valueOf(1_000_000))).isEqualTo(new BigDecimal("2994.50"));
	}

	@Test
	@DisplayName("Barème fortune supplémentaire 2011 pour 1 million")
	public void baremeSupplementairePour1million() {
		Bareme bareme = fournisseur.getBaremeFortuneSupplementaire(2011);
		assertThat(bareme.calcul(BigDecimal.valueOf(1_000_000))).isEqualTo(new BigDecimal("310.70"));
	}

}
