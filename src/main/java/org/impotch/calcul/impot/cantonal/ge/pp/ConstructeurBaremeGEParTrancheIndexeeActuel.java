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

package org.impotch.calcul.impot.cantonal.ge.pp;

import org.impotch.bareme.BaremeParTranche;
import org.impotch.calcul.impot.indexation.FournisseurIndicePeriodique;

import static org.impotch.calcul.impot.cantonal.ge.pp.ConstructeurBaremeParTrancheIndexe.unConstructeurDeBaremeParTrancheIndexee;
import static org.impotch.util.TypeArrondi.VINGTIEME_LE_PLUS_PROCHE;

public class ConstructeurBaremeGEParTrancheIndexeeActuel implements ConstructeurBaremeGEParTrancheIndexee {

    private static final int ANNEE_REFERENCE_INDEXATION     = 2009;
    private static final int ENTREE_VIGUEUR_LIPP = 2010;
    private static final int ENTREE_VIGUEUR_LEFI_13030 = 2025; // loi du 4 novembre 2022 contestée jusqu’au TF
    private static final int ENTREE_VIGUEUR_LOI_13402 = 2025; // Renforcer le pouvoir d’achat et les recettes fiscales du 3 mai 2024

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
                .valideDepuis(ENTREE_VIGUEUR_LIPP)
                .anneeReferenceRencherissement(ANNEE_REFERENCE_INDEXATION)
                .indexateur(fournisseurIndicePeriodique)
                .typeArrondiTranche(VINGTIEME_LE_PLUS_PROCHE)
                .typeArrondiGlobal(VINGTIEME_LE_PLUS_PROCHE)
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
                .valideDepuis(ENTREE_VIGUEUR_LOI_13402)
                .anneeReferenceRencherissement(ANNEE_REFERENCE_INDEXATION)
                .indexateur(fournisseurIndicePeriodique)
                .typeArrondiTranche(VINGTIEME_LE_PLUS_PROCHE)
                .typeArrondiGlobal(VINGTIEME_LE_PLUS_PROCHE)
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
        if (annee < ENTREE_VIGUEUR_LIPP) throw new IllegalArgumentException("Les barèmes de l’année " + annee + " ne sont pas produits par cette méthode !!");
        if (annee < ENTREE_VIGUEUR_LOI_13402) return constructeurBaremeRevenuLIPP2009();
        else return constructeurBaremeRevenuLoi13402();
    }


    private ConstructeurBaremeParTrancheIndexe constructeurBaremeFortuneLEFI() {
            return unConstructeurDeBaremeParTrancheIndexee()
                    .valideDepuis(ENTREE_VIGUEUR_LEFI_13030)
                    .indexateur(fournisseurIndicePeriodique)
                    .anneeReferenceRencherissement(ANNEE_REFERENCE_INDEXATION)
                    .typeArrondiTranche(VINGTIEME_LE_PLUS_PROCHE)
                    .typeArrondiGlobal(VINGTIEME_LE_PLUS_PROCHE)
                    .jusqua(0).taux("0")
                    .puisJusqua(  111_059).taux("1.49 ‰")
                    .puisJusqua(  222_117).taux("1.91 ‰")
                    .puisJusqua(  333_176).taux("2.34 ‰")
                    .puisJusqua(  444_234).taux("2.55 ‰")
                    .puisJusqua(  666_352).taux("2.76 ‰")
                    .puisJusqua(  888_469).taux("2.98 ‰")
                    .puisJusqua(1_110_586).taux("3.19 ‰")
                    .puisJusqua(1_332_703).taux("3.4 ‰")
                    .puisJusqua(1_665_879).taux("3.61 ‰")
                    .puis()               .taux("3.83 ‰");
    }

    private BaremeParTranche baremeFortuneParTranche2009() {
        BaremeParTranche bareme2009 = constructeurPrecedent
                .constructeurBaremeFortune(ANNEE_REFERENCE_INDEXATION)
                .construire(ANNEE_REFERENCE_INDEXATION);
        return bareme2009;
    }

    @Override
    public ConstructeurBaremeParTrancheIndexe constructeurBaremeFortune(int annee) {
            if (annee < ENTREE_VIGUEUR_LEFI_13030) {
                BaremeParTranche bareme2009 = baremeFortuneParTranche2009();
                return unConstructeurDeBaremeParTrancheIndexee(bareme2009)
                        .valideEntre(ENTREE_VIGUEUR_LIPP,ENTREE_VIGUEUR_LEFI_13030-1)
                        .indexateur(fournisseurIndicePeriodique)
                        .anneeReferenceRencherissement(ANNEE_REFERENCE_INDEXATION);
            } else {
             return constructeurBaremeFortuneLEFI();
            }
    }

private ConstructeurBaremeParTrancheIndexe constructeurBaremeFortuneSupplementaireLEFI() {
    return unConstructeurDeBaremeParTrancheIndexee()
            .valideDepuis(ENTREE_VIGUEUR_LEFI_13030)
            .indexateur(fournisseurIndicePeriodique)
            .anneeReferenceRencherissement(ANNEE_REFERENCE_INDEXATION)
            .typeArrondiTranche(VINGTIEME_LE_PLUS_PROCHE)
            .typeArrondiGlobal(VINGTIEME_LE_PLUS_PROCHE)
            .jusqua(0).taux("0")
            .puisJusqua(  111_059).taux("0 ‰")
            .puisJusqua(  222_117).taux("0.0956 ‰")
            .puisJusqua(  333_176).taux("0.1169 ‰")
            .puisJusqua(  444_234).taux("0.2550 ‰")
            .puisJusqua(  666_352).taux("0.2763 ‰")
            .puisJusqua(  888_469).taux("0.4463 ‰")
            .puisJusqua(1_110_586).taux("0.4781 ‰")
            .puisJusqua(1_332_703).taux("0.68 ‰")
            .puisJusqua(1_665_879).taux("0.7225 ‰")
            .puisJusqua(3_331_758).taux("0.9563 ‰")
            .puis()               .taux("1.1475 ‰");
}


private BaremeParTranche baremeFortuneSupplementaireParTranche2009() {
        BaremeParTranche bareme2009 = constructeurPrecedent
                .constructeurBaremeFortuneSupplementaire(ANNEE_REFERENCE_INDEXATION)
                .construire(ANNEE_REFERENCE_INDEXATION);
        return bareme2009;
    }

    @Override
    public ConstructeurBaremeParTrancheIndexe constructeurBaremeFortuneSupplementaire(int annee) {

        if (annee < ENTREE_VIGUEUR_LEFI_13030) {
            BaremeParTranche bareme2009 = baremeFortuneSupplementaireParTranche2009();
            return unConstructeurDeBaremeParTrancheIndexee(bareme2009)
                    .valideEntre(ENTREE_VIGUEUR_LIPP, ENTREE_VIGUEUR_LEFI_13030 - 1)
                    .indexateur(fournisseurIndicePeriodique)
                    .anneeReferenceRencherissement(ANNEE_REFERENCE_INDEXATION);
        } else {
            return constructeurBaremeFortuneSupplementaireLEFI();
        }
    }
}
