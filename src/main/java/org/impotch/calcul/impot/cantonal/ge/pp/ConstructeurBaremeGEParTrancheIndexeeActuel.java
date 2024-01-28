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
package org.impotch.calcul.impot.cantonal.ge.pp;

import org.impotch.bareme.BaremeParTranche;
import org.impotch.calcul.impot.indexation.FournisseurIndicePeriodique;

import static org.impotch.calcul.impot.cantonal.ge.pp.ConstructeurBaremeParTrancheIndexe.unConstructeurDeBaremeParTrancheIndexee;
import static org.impotch.util.TypeArrondi.CINQ_CENTIEMES_LES_PLUS_PROCHES;

public class ConstructeurBaremeGEParTrancheIndexeeActuel implements ConstructeurBaremeGEParTrancheIndexee {

    private static final int ANNEE_REFERENCE_INDEXATION = 2009;
    private static final int DEBUT_VALIDITE = 2010;

    private final FournisseurIndicePeriodique fournisseurIndicePeriodique;
    private final ConstructeurBaremeGEParTrancheIndexee constructeurPrecedent;

    private ConstructeurBaremeGEParTrancheIndexeeActuel(FournisseurIndicePeriodique fournisseurIndicePeriodique, ConstructeurBaremeGEParTrancheIndexee constructeurPrecedent) {
        this.fournisseurIndicePeriodique = fournisseurIndicePeriodique;
        this.constructeurPrecedent = constructeurPrecedent;
    }

    public static ConstructeurBaremeGEParTrancheIndexee unConstructeurBaremeGEActuel(FournisseurIndicePeriodique fournisseurIndicePeriodique, ConstructeurBaremeGEParTrancheIndexee constructeurPrecedent) {
        return new ConstructeurBaremeGEParTrancheIndexeeActuel(fournisseurIndicePeriodique,constructeurPrecedent);
    }

    @Override
    public ConstructeurBaremeParTrancheIndexe constructeurBaremeRevenu() {
        return unConstructeurDeBaremeParTrancheIndexee()
                .valideDepuis(DEBUT_VALIDITE)
                .anneeReferenceRencherissement(ANNEE_REFERENCE_INDEXATION)
                .indexateur(fournisseurIndicePeriodique)
                .typeArrondiTranche(CINQ_CENTIEMES_LES_PLUS_PROCHES)
                .typeArrondiGlobal(CINQ_CENTIEMES_LES_PLUS_PROCHES)
                .jusqua(17493).taux(" 0 %")
                .puisJusqua(21076).taux(" 8 %")
                .puisJusqua(23184).taux(" 9 %")
                .puisJusqua(25291).taux("10 %")
                .puisJusqua(27399).taux("11 %")
                .puisJusqua(32668).taux("12 %")
                .puisJusqua(36883).taux("13 %")
                .puisJusqua(41099).taux("14 %")
                .puisJusqua(45314).taux("14.5 %")
                .puisJusqua(72713).taux("15 %")
                .puisJusqua(119081).taux("15.5 %")
                .puisJusqua(160179).taux("16 %")
                .puisJusqua(181256).taux("16.5 %")
                .puisJusqua(259238).taux("17 %")
                .puisJusqua(276099).taux("17.5 %")
                .puisJusqua(388857).taux("18 %")
                .puisJusqua(609103).taux("18.5 %")
                .puis().taux("19 %");
    }

    @Override
    public ConstructeurBaremeParTrancheIndexe constructeurBaremeFortune() {
            BaremeParTranche bareme2009 = constructeurPrecedent
                    .constructeurBaremeFortune()
                    .construire(ANNEE_REFERENCE_INDEXATION);

            return unConstructeurDeBaremeParTrancheIndexee(bareme2009)
                    .valideDepuis(DEBUT_VALIDITE)
                    .indexateur(fournisseurIndicePeriodique)
                    .anneeReferenceRencherissement(ANNEE_REFERENCE_INDEXATION);
    }

    @Override
    public ConstructeurBaremeParTrancheIndexe constructeurBaremeFortuneSupplementaire() {
            BaremeParTranche bareme2009 = constructeurPrecedent
                    .constructeurBaremeFortuneSupplementaire()
                    .construire(ANNEE_REFERENCE_INDEXATION);

            return unConstructeurDeBaremeParTrancheIndexee(bareme2009)
                    .valideDepuis(DEBUT_VALIDITE)
                    .indexateur(fournisseurIndicePeriodique)
                    .anneeReferenceRencherissement(ANNEE_REFERENCE_INDEXATION);
    }
}
