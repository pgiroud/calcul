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
package org.impotch.calcul.impot.federal;

import org.impotch.calcul.impot.federal.param.FournisseurParametrageAnnuelIFD;
import org.impotch.calcul.impot.federal.param.FournisseurParametrageAnnuelIFDEnMemoire;

public enum ContexteTest_CH {
    CTX_TST_CH;

    private FournisseurParametrageAnnuelIFD fournisseurParamIFD;
    private FournisseurRegleImpotFederal fournisseurRegleImpotFederal;


    ContexteTest_CH() {
        fournisseurParamIFD = new FournisseurParametrageAnnuelIFDEnMemoire();

        fournisseurRegleImpotFederal = new Fournisseur(fournisseurParamIFD);
    }

    public FournisseurParametrageAnnuelIFD getFournisseurParamIFD() {
        return fournisseurParamIFD;
    }

    public FournisseurRegleImpotFederal getFournisseurRegleImpotFederal() {
        return fournisseurRegleImpotFederal;
    }
}
