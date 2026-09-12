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
package org.impotch.calcul.impot.indexation.ge;

import org.impotch.calcul.impot.indexation.Indexateur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class Indexation2024Test {

    private static final int ANNEE_INDEXATION_LIPP_D_3_08 = 2009;
    private static final int ANNEE = 2024;

    private Indexateur indexateur;

    @BeforeEach
    public void initIndexateur() {
        FournisseurIndexGenevois fournisseur = new FournisseurIndexGenevoisEnMemoire();
        indexateur = new IndexateurGenevois(fournisseur);
    }

    private int indexer(int valeurBaseIndexation) {
        int i = indexateur.indexer(ANNEE_INDEXATION_LIPP_D_3_08, BigDecimal.valueOf(valeurBaseIndexation), ANNEE)
                .intValueExact();
        return i;
    }


    @Test
    @DisplayName("Déductions liées à l'exercice d'une activité lucrative dépendante (art. 29 de la loi)")
    public void plancherFraisForfaitaire() {
        assertThat(indexer(600)).isEqualTo(634);
    }

    @Test
    @DisplayName("Déductions liées à l'exercice d'une activité lucrative dépendante (art. 29 de la loi)")
    public void plafondFraisForfaitaire() {
        assertThat(indexer(1700)).isEqualTo(1796);
    }


}
