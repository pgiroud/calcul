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
package org.impotch.calcul.impot.cantonal.ge.pp;

import org.impotch.bareme.BaremeParTranche;
import org.impotch.calcul.impot.indexation.FournisseurIndicePeriodique;

import static org.impotch.calcul.impot.cantonal.ge.pp.ConstructeurBaremeParTrancheIndexe.unConstructeurDeBaremeParTrancheIndexee;
import static org.impotch.util.TypeArrondi.CINQ_CENTIEMES_LES_PLUS_PROCHES;

public class ConstructeurBaremeGEParTrancheIndexeeActuel implements ConstructeurBaremeGEParTrancheIndexee {

    private static final int ANNEE_REFERENCE_INDEXATION = 2009;

    private final FournisseurIndicePeriodique fournisseurIndicePeriodique;
    private final ConstructeurBaremeGEParTrancheIndexee constructeurPrecedent;

    private ConstructeurBaremeGEParTrancheIndexeeActuel(FournisseurIndicePeriodique fournisseurIndicePeriodique, ConstructeurBaremeGEParTrancheIndexee constructeurPrecedent) {
        this.fournisseurIndicePeriodique = fournisseurIndicePeriodique;
        this.constructeurPrecedent = constructeurPrecedent;
    }

    public static ConstructeurBaremeGEParTrancheIndexee unConstructeurBaremeGEActuel(FournisseurIndicePeriodique fournisseurIndicePeriodique, ConstructeurBaremeGEParTrancheIndexee constructeurPrecedent) {
        return new ConstructeurBaremeGEParTrancheIndexeeActuel(fournisseurIndicePeriodique,constructeurPrecedent);
    }


    private ConstructeurBaremeParTrancheIndexe constructeurBaremeRevenuLIPP2009() {
        return unConstructeurDeBaremeParTrancheIndexee()
                .valideDepuis(2010)
                .anneeReferenceRencherissement(ANNEE_REFERENCE_INDEXATION)
                .indexateur(fournisseurIndicePeriodique)
                .typeArrondiTranche(CINQ_CENTIEMES_LES_PLUS_PROCHES)
                .typeArrondiGlobal(CINQ_CENTIEMES_LES_PLUS_PROCHES)
                .jusqua(     17_493).taux(" 0 %")
                .puisJusqua( 21_076).taux(" 8 %")
                .puisJusqua( 23_184).taux(" 9 %")
                .puisJusqua( 25_291).taux("10 %")
                .puisJusqua( 27_399).taux("11 %")
                .puisJusqua( 32_668).taux("12 %")
                .puisJusqua( 36_883).taux("13 %")
                .puisJusqua( 41_099).taux("14 %")
                .puisJusqua( 45_314).taux("14.5 %")
                .puisJusqua( 72_713).taux("15 %")
                .puisJusqua(119_081).taux("15.5 %")
                .puisJusqua(160_179).taux("16 %")
                .puisJusqua(181_256).taux("16.5 %")
                .puisJusqua(259_238).taux("17 %")
                .puisJusqua(276_099).taux("17.5 %")
                .puisJusqua(388_857).taux("18 %")
                .puisJusqua(609_103).taux("18.5 %")
                .puis()             .taux("19 %");
    }

    private ConstructeurBaremeParTrancheIndexe constructeurBaremeRevenuLoi13402() {
        return unConstructeurDeBaremeParTrancheIndexee()
                .valideDepuis(2025)
                .anneeReferenceRencherissement(ANNEE_REFERENCE_INDEXATION)
                .indexateur(fournisseurIndicePeriodique)
                .typeArrondiTranche(CINQ_CENTIEMES_LES_PLUS_PROCHES)
                .typeArrondiGlobal(CINQ_CENTIEMES_LES_PLUS_PROCHES)
                .jusqua(     17_493).taux(" 0.00 %")
                .puisJusqua( 21_076).taux(" 7.30 %")
                .puisJusqua( 23_184).taux(" 8.20 %")
                .puisJusqua( 25_291).taux(" 9.10 %")
                .puisJusqua( 27_399).taux("10.00 %")
                .puisJusqua( 32_668).taux("10.90 %")
                .puisJusqua( 36_883).taux("11.30 %")
                .puisJusqua( 41_099).taux("12.30 %")
                .puisJusqua( 45_314).taux("12.80 %")
                .puisJusqua( 72_713).taux("13.20 %")
                .puisJusqua(119_081).taux("14.20 %")
                .puisJusqua(160_179).taux("15.00 %")
                .puisJusqua(181_256).taux("15.60 %")
                .puisJusqua(259_238).taux("15.80 %")
                .puisJusqua(276_099).taux("16.00 %")
                .puisJusqua(388_857).taux("16.80 %")
                .puisJusqua(609_103).taux("17.60 %")
                .puis()             .taux("18.00 %");
    }

    @Override
    public ConstructeurBaremeParTrancheIndexe constructeurBaremeRevenu(int annee) {
        if (annee < 2010) throw new IllegalArgumentException("Les barèmes de l’année " + annee + " ne sont pas produits par cette méthode !!");
        if (annee < 2025) return constructeurBaremeRevenuLIPP2009();
        else return constructeurBaremeRevenuLoi13402();
    }

    @Override
    public ConstructeurBaremeParTrancheIndexe constructeurBaremeFortune() {
            BaremeParTranche bareme2009 = constructeurPrecedent
                    .constructeurBaremeFortune()
                    .construire(ANNEE_REFERENCE_INDEXATION);

            return unConstructeurDeBaremeParTrancheIndexee(bareme2009)
                    .valideDepuis(2010)
                    .indexateur(fournisseurIndicePeriodique)
                    .anneeReferenceRencherissement(ANNEE_REFERENCE_INDEXATION);
    }

    @Override
    public ConstructeurBaremeParTrancheIndexe constructeurBaremeFortuneSupplementaire() {
            BaremeParTranche bareme2009 = constructeurPrecedent
                    .constructeurBaremeFortuneSupplementaire()
                    .construire(ANNEE_REFERENCE_INDEXATION);

            return unConstructeurDeBaremeParTrancheIndexee(bareme2009)
                    .valideDepuis(2010)
                    .indexateur(fournisseurIndicePeriodique)
                    .anneeReferenceRencherissement(ANNEE_REFERENCE_INDEXATION);
    }
}
