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

import org.impotch.calcul.assurancessociales.ge.param.FournisseurParametrageAnnuelAssurancesSocialesGenevoises;
import org.impotch.calcul.assurancessociales.param.FournisseurParametrageSuisseAnnuel;

public enum ContexteTestAssurancesSociales {
    CTX_TST_AS;

    // TODO Old À supprimer
    private FournisseurRegleCalculAssuranceSociale fournisseurRegleCalculAssuranceSociale;

    private FournisseurRegleCalculCotisationsAssuranceSociale fournisseurRegle;

    ContexteTestAssurancesSociales() {
        fournisseurRegleCalculAssuranceSociale = new Fournisseur(FournisseurParametrageAnnuelAssurancesSocialesGenevoises.enMemoire());
    }

    @Deprecated
    public FournisseurRegleCalculAssuranceSociale getFournisseurRegleCalculAssuranceSociale() {
        return fournisseurRegleCalculAssuranceSociale;
    }

    public FournisseurRegleCalculCotisationsAssuranceSociale fournisseurRegles() {
        return new FournisseurRegleCHCalculCotisationsAssuranceSociale(FournisseurParametrageSuisseAnnuel.enMemoire());
    }

}
