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
package org.impotch.calcul.impot.taxation.pp.ge.deduction;

import org.impotch.calcul.impot.indexation.ge.FournisseurIndexGenevoisEnMemoire;
import org.impotch.calcul.impot.taxation.pp.Contribuable;
import org.impotch.calcul.impot.taxation.pp.EnfantACharge;
import org.impotch.calcul.impot.taxation.pp.PersonneACharge;
import org.impotch.calcul.impot.taxation.pp.SituationFamiliale;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class DeductionDoubleActiviteTest {

    private FournisseurDeductionPP fournisseurDeductionPP =
            new FournisseurDeductionPPEnMemoire(new FournisseurIndexGenevoisEnMemoire());


    private SituationFamiliale construireSituationCoupleAvecLes2Travaillant() {
        SituationFamiliale situation = new SituationFamiliale() {
            private final Contribuable contrib = new Contribuable() {};
            @Override
            public Contribuable getContribuable() {
                return contrib;
            }

            @Override
            public Optional<Contribuable> getConjoint() {
                return Optional.of(contrib);
            }

            @Override
            public Set<EnfantACharge> getEnfants() {
                return Collections.emptySet();
            }

            @Override
            public Set<PersonneACharge> getPersonnesNecessiteuses() {
                return Collections.emptySet();
            }
        };
        return situation;
    }

    private void test(int annee, int montantAttendu) {
        SituationFamiliale situation = construireSituationCoupleAvecLes2Travaillant();
        assertThat(fournisseurDeductionPP.getRegleDeductionDoubleActivite(annee).getMontantDeduction(situation))
                .isEqualTo(BigDecimal.valueOf(montantAttendu));
    }

    @Test
    public void test2009() {
        test(2009,500);
    }

    @Test
    public void test2010() {
        test(2010,500);
    }

    @Test
    public void test2011() {
        test(2011,500);
    }

    @Test
    public void test2012() {
        test(2012,500);
    }

    @Test
    public void test2013() {
        test(2013,504);
    }

    @Test
    public void test2014() {
        test(2014,504);
    }

    @Test
    public void test2015() {
        test(2015,504);
    }

    @Test
    public void test2016() {
        test(2016,504);
    }

    @Test
    public void test2017() {
        test(2017,499);
    }

    @Test
    public void test2018() {
        test(2018,499);
    }

    @Test
    public void test2019() {
        test(2019,499);
    }

    @Test
    public void test2020() {
        test(2020,499);
    }

    @Test
    public void test2021() {
        test(2021,1000);
    }

    @Test
    public void test2022() {
        test(2022,1000);
    }

    @Test
    public void test2023() {
        test(2023,1000);
    }

    @Test
    public void test2024() {
        test(2024,1000);
    }


}
