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
import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.impotch.calcul.assurancessociales.ContexteTestAssurancesSociales.CTX_TST_AS;

public class CalculCotisationAvsAiApgSalarie2023Test {

    private static final int ANNEE = 2023;

    private FournisseurRegleCalculCotisationsAssuranceSociale fournisseurRegle;

    @BeforeEach
    public void initialise() {
        fournisseurRegle = ContexteTestAssurancesSociales.CTX_TST_AS.fournisseurReglesSuisses();
    }

    private Function<BigDecimal,BigDecimal> regle(TypeAssuranceSociale type) {
        return fournisseurRegle.reglePartSalarie(ANNEE,type).orElseThrow();
    }

    @Test
    public void calculCotisationAi() {
        assertThat(regle(TypeAssuranceSociale.AI).apply(BigDecimal.valueOf(100000))).isEqualByComparingTo(BigDecimal.valueOf(700));
    }

    @Test
    public void calculCotisationApg() {
        assertThat(regle(TypeAssuranceSociale.APG).apply(BigDecimal.valueOf(100000))).isEqualByComparingTo(BigDecimal.valueOf(250));
    }

    @Test
    public void calculCotisationAvs() {
        assertThat(regle(TypeAssuranceSociale.AVS).apply(BigDecimal.valueOf(100000))).isEqualByComparingTo(BigDecimal.valueOf(4350));
    }

    @Test
    public void calculCotisationAvsAiApg() {
        assertThat(regle(TypeAssuranceSociale.AVS_AI_APG).apply(BigDecimal.valueOf(100000))).isEqualByComparingTo(BigDecimal.valueOf(5300));
    }

}
