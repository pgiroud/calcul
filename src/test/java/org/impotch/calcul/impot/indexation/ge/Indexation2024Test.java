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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class Indexation2024Test {

    private static final int ANNEE = 2024;

    @Test
    @DisplayName("Déductions liées à l'exercice d'une activité lucrative dépendante (art. 29 de la loi)")
    public void plancherFraisForfaitaire() {
        FournisseurIndexGenevois fournisseur = new FournisseurIndexGenevoisEnMemoire();
//        Indexateur indexateurBaseDec05 = fournisseur.getIndexateurQuadriennalBaseDecembre2005(2009);
//        assertThat(indexateurBaseDec05.indexer(BigDecimal.valueOf(600), ANNEE)).isEqualTo(BigDecimal.valueOf(634));
    }

    @Test
    @DisplayName("Déductions liées à l'exercice d'une activité lucrative dépendante (art. 29 de la loi)")
    public void plafondFraisForfaitaire() {
        FournisseurIndexGenevois fournisseur = new FournisseurIndexGenevoisEnMemoire();
//        Indexateur indexateurBaseDec05 = fournisseur.getIndexateurQuadriennalBaseDecembre2005(2009);
//        assertThat(indexateurBaseDec05.indexer(BigDecimal.valueOf(1700), ANNEE)).isEqualTo(BigDecimal.valueOf(1796));
    }


}
