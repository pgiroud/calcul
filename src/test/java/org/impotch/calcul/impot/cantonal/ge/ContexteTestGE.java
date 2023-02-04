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
package org.impotch.calcul.impot.cantonal.ge;

import org.impotch.calcul.assurancessociales.Fournisseur;
import org.impotch.calcul.assurancessociales.FournisseurRegleCalculAssuranceSociale;
import org.impotch.calcul.impot.cantonal.ge.pp.FournisseurCantonalGE;
import org.impotch.calcul.impot.cantonal.ge.pp.FournisseurRegleImpotCantonalGE;
import org.impotch.calcul.impot.indexation.ge.FournisseurIndexGenevois;
import org.impotch.calcul.impot.indexation.ge.FournisseurIndexGenevoisEnMemoire;

public enum ContexteTestGE {


    CTX_TST_GE;

    private FournisseurIndexGenevois fournisseurIndex;
    private FournisseurRegleImpotCantonalGE fournisseurRegleImpotCantonalGE;
    private FournisseurRegleCalculAssuranceSociale fournisseurRegleCalculAssuranceSociale;

    ContexteTestGE() {
        fournisseurIndex = new FournisseurIndexGenevoisEnMemoire();
        fournisseurRegleCalculAssuranceSociale = new Fournisseur();
        fournisseurRegleImpotCantonalGE = new FournisseurCantonalGE(fournisseurRegleCalculAssuranceSociale);
    }

    public FournisseurIndexGenevois getFournisseurIndex() {
        return fournisseurIndex;
    }

    public FournisseurRegleImpotCantonalGE getFournisseurRegleImpotCantonalGE() {
        return fournisseurRegleImpotCantonalGE;
    }
}
