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
package org.impotch.calcul.assurancessociales;

import java.math.BigDecimal;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.impotch.calcul.assurancessociales.TypeAssuranceSociale.AVS;
import static org.impotch.calcul.assurancessociales.TypeAssuranceSociale.AI;
import static org.impotch.calcul.assurancessociales.TypeAssuranceSociale.APG;
import static org.impotch.calcul.assurancessociales.TypeAssuranceSociale.AVS_AI_APG;
import static org.impotch.util.TypeArrondi.VINGTIEME_LE_PLUS_PROCHE;

public class CalculCotisationAvsAiApgSalarie2009Test {

    private static final int ANNEE = 2009;

    private FournisseurRegleCalculCotisationsAssuranceSociale fournisseurRegle;

    @BeforeEach
    public void initialise() {
        fournisseurRegle = ContexteTestAssurancesSociales.CTX_TST_AS.fournisseurRegles();
    }

    private Function<BigDecimal,BigDecimal> regle(TypeAssuranceSociale type) {
        return fournisseurRegle.reglePartSalarie(ANNEE,type).orElseThrow();
    }

    @Test
    public void calculCotisationAi() {
        assertThat(regle(AI).apply(BigDecimal.valueOf(100_000))).isEqualByComparingTo(BigDecimal.valueOf(700));
    }

    @Test
    public void calculCotisationApg() {
        assertThat(regle(APG).apply(BigDecimal.valueOf(100_000))).isEqualByComparingTo(BigDecimal.valueOf(150));
    }

    @Test
    public void calculCotisationAvs() {
        assertThat(regle(AVS).apply(BigDecimal.valueOf(100_000))).isEqualByComparingTo(BigDecimal.valueOf(4200));
    }

    @Test
    public void calculCotisationAvsAiApg() {
        assertThat(regle(AVS_AI_APG).apply(BigDecimal.valueOf(100_000))).isEqualByComparingTo(BigDecimal.valueOf(5050));
    }

    @Test
    public void calculCotisationAvsAiApgFaibleMontantSansArrondi() {
        assertThat(regle(AVS_AI_APG).apply(BigDecimal.valueOf(4525))).isEqualByComparingTo(new BigDecimal("228.5125"));
    }

    @Test
    public void calculCotisationAvsAiApgFaibleMontantAvecArrondi() {
        assertThat(regle(AVS_AI_APG).andThen(VINGTIEME_LE_PLUS_PROCHE::arrondir).apply(BigDecimal.valueOf(4525))).isEqualByComparingTo(new BigDecimal("228.50"));
    }
}
