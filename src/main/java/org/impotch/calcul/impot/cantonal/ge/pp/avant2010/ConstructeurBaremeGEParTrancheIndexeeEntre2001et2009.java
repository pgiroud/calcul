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
package org.impotch.calcul.impot.cantonal.ge.pp.avant2010;

import org.impotch.calcul.impot.cantonal.ge.pp.ConstructeurBaremeGEParTrancheIndexee;
import org.impotch.calcul.impot.cantonal.ge.pp.ConstructeurBaremeParTrancheIndexe;
import org.impotch.calcul.impot.indexation.FournisseurIndicePeriodique;

import static org.impotch.util.TypeArrondi.CINQ_CENTIEMES_LES_PLUS_PROCHES;
import static org.impotch.calcul.impot.cantonal.ge.pp.ConstructeurBaremeParTrancheIndexe.unConstructeurDeBaremeParTrancheIndexee;

public class ConstructeurBaremeGEParTrancheIndexeeEntre2001et2009 implements ConstructeurBaremeGEParTrancheIndexee {

    private final FournisseurIndicePeriodique fournisseurIndicePeriodique;

    private ConstructeurBaremeGEParTrancheIndexeeEntre2001et2009(FournisseurIndicePeriodique fournisseurIndicePeriodique) {
        this.fournisseurIndicePeriodique = fournisseurIndicePeriodique;
    }

    public static ConstructeurBaremeGEParTrancheIndexee unConstructeurBaremeGEEntre2001et2009(FournisseurIndicePeriodique fournisseurIndicePeriodique) {
        return new ConstructeurBaremeGEParTrancheIndexeeEntre2001et2009(fournisseurIndicePeriodique);
    }

    @Override
    public ConstructeurBaremeParTrancheIndexe constructeurBaremeRevenu() {
        return null;
    }

    @Override
    public ConstructeurBaremeParTrancheIndexe constructeurBaremeFortune() {
        return unConstructeurDeBaremeParTrancheIndexee()
                .valideEntre(2001, 2009)
                .indexateur(fournisseurIndicePeriodique)
                .anneeReferenceRencherissement(2000)
                .typeArrondiTranche(CINQ_CENTIEMES_LES_PLUS_PROCHES)
                .typeArrondiGlobal(CINQ_CENTIEMES_LES_PLUS_PROCHES)
                .jusqua(0).taux("0")
                .puisJusqua(  100_000).taux("1.75 ‰")
                .puisJusqua(  200_000).taux("2.25 ‰")
                .puisJusqua(  300_000).taux("2.75 ‰")
                .puisJusqua(  400_000).taux("3 ‰")
                .puisJusqua(  600_000).taux("3.25 ‰")
                .puisJusqua(  800_000).taux("3.5 ‰")
                .puisJusqua(1_000_000).taux("3.75 ‰")
                .puisJusqua(1_200_000).taux("4 ‰")
                .puisJusqua(1_500_000).taux("4.25 ‰")
                .puis()               .taux("4.5 ‰");
    }

    @Override
    public ConstructeurBaremeParTrancheIndexe constructeurBaremeFortuneSupplementaire() {
        return unConstructeurDeBaremeParTrancheIndexee()
                .valideEntre(2001, 2009)
                .indexateur(fournisseurIndicePeriodique)
                .anneeReferenceRencherissement(2000)
                .typeArrondiTranche(CINQ_CENTIEMES_LES_PLUS_PROCHES)
                .typeArrondiGlobal(CINQ_CENTIEMES_LES_PLUS_PROCHES)
                .jusqua(      100_000).taux("0 ‰")
                .puisJusqua(  200_000).taux("0.1125 ‰")
                .puisJusqua(  300_000).taux("0.1375 ‰")
                .puisJusqua(  400_000).taux("0.3 ‰")
                .puisJusqua(  600_000).taux("0.325 ‰")
                .puisJusqua(  800_000).taux("0.525 ‰")
                .puisJusqua(1_000_000).taux("0.5625 ‰")
                .puisJusqua(1_200_000).taux("0.8 ‰")
                .puisJusqua(1_500_000).taux("0.85 ‰")
                .puisJusqua(3_000_000).taux("1.125 ‰")
                .puis()               .taux("1.35 ‰");
    }
}
