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
package org.impotch.calcul.assurancessociales.ge.param;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

public class FournisseurParametrageCotisationAssuranceMaterniteEnMemoireTest {

    @Test
    public void testPresence2025() {
        FournisseurParametrageCotisationAssuranceMaternite fournisseur = FournisseurParametrageCotisationAssuranceMaternite.enMemoire();
        assertThat(fournisseur.parametrage(2025)).isPresent();
    }

    @Test
    public void testAbsenceMoins52() { // Pas d'assurance maternité à Alésia !
        FournisseurParametrageCotisationAssuranceMaternite fournisseur = FournisseurParametrageCotisationAssuranceMaternite.enMemoire();
        assertThat(fournisseur.parametrage(-52)).isEmpty();
    }



}
