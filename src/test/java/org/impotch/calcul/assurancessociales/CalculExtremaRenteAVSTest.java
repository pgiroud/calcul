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
package org.impotch.calcul.assurancessociales;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.impotch.calcul.assurancessociales.ContexteTestAssurancesSociales.CTX_TST_AS;

public class CalculExtremaRenteAVSTest {

	private FournisseurRegleCalculAssuranceSociale fournisseurRegleCalculAssuranceSociale = CTX_TST_AS.getFournisseurRegleCalculAssuranceSociale();
	
	private CalculExtremaRentesAVS calculateur;
	
	@BeforeEach
	public void initialise() throws Exception {
		calculateur = fournisseurRegleCalculAssuranceSociale.getCalculateurExtremaRenteAVS(2009);
	}

	@Test
	public void calculRenteSimpleMaximum() {
		assertThat(calculateur.getRenteSimple().getMontantAnnuelMaximum()).isEqualTo(BigDecimal.valueOf(27360));
		//Assert.assertEquals("Rente simple maximale annuelle", new BigDecimal("27360"), calculateur.getRenteSimple().getMontantAnnuelMaximum());
	}
}
