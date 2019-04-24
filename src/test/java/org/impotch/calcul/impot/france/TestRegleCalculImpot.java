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
package org.impotch.calcul.impot.france;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class TestRegleCalculImpot {

	@Test
	public void decote() {
		RegleCalculImpot regle = new RegleCalculImpot(2008);
		regle.setMontantDecote(new BigDecimal("862"));
		assertThat(regle.decote(BigDecimal.valueOf(700))).isEqualTo(BigDecimal.valueOf(619));
//		assertEquals("Montant apres decote", new BigDecimal("619"),regle.decote(new BigDecimal("700")));
	}
}
