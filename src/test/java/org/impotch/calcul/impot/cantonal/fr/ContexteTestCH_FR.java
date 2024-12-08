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
package org.impotch.calcul.impot.cantonal.fr;

import org.impotch.calcul.impot.cantonal.fr.pp.FournisseurCantonalFR;
import org.impotch.calcul.impot.cantonal.fr.pp.FournisseurRegleImpotCantonalFR;

public enum ContexteTestCH_FR {
    CTX_TST_CH_FR;

    private FournisseurRegleImpotCantonalFR fournisseurRegleImpotCantonalFR;


    ContexteTestCH_FR() {
        fournisseurRegleImpotCantonalFR = new FournisseurCantonalFR();
    }

    public FournisseurRegleImpotCantonalFR getFournisseurRegleImpotCantonalFR() {
        return fournisseurRegleImpotCantonalFR;
    }
}
