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
package org.impotch.calcul.impot.cantonal.ge.param;

import org.impotch.bareme.BaremeParTranche;
import org.impotch.calcul.impot.indexation.Indexateur;


import static org.impotch.bareme.ConstructeurBareme.unBareme;
import static org.impotch.util.TypeArrondi.CENTAINE_SUP;

public class FournisseurParametrageAnnuelLIPP_D_3_08_enMemoire implements FournisseurParametrageAnnuelLIPP_D_3_08 {

    private final Indexateur indexateur;

    public FournisseurParametrageAnnuelLIPP_D_3_08_enMemoire(Indexateur indexateur) {
        this.indexateur = indexateur;
    }

    @Override
    public ParametrageAnnuelLIPP_D_3_08_Revenu revenu(int annee) {
        return construireRevenu(annee);
    }

    private ParametrageAnnuelLIPP_D_3_08_Revenu construireRevenu(int annee) {
        ParametrageDeductionFraisProfessionnels parametrageDeductionFraisProfessionnels = construireParametrageDeductionFraisProfessionnels(annee);
        int deductionDoubleActivite = construireDeductionDoubleActivite(annee);
        ParametrageDeductionSocialeRevenu deducRevenu = construireParametrageDeductionSocialeRevenu(annee);
        return new ParametrageAnnuelLIPP_D_3_08(annee,parametrageDeductionFraisProfessionnels,deductionDoubleActivite,deducRevenu);
    }

    private ParametrageDeductionFraisProfessionnels construireParametrageDeductionFraisProfessionnels(int annee) {
        if (annee < 2001) throw new IllegalArgumentException("Le paramétrage pour l’année '" + annee + " n’existe pas !");
        if (annee < 2010) {
            int plancher = indexateur.indexer(2001,500,annee,CENTAINE_SUP);
            int plafond = indexateur.indexer(2001,1500,annee,CENTAINE_SUP);
            return new ParametrageDeductionFraisProfessionnels("3 %",plancher,plafond);
        }
        // Source LIPP art. 29 al. 2 : https://silgeneve.ch/legis/program/books/RSG/htm/rsg_d3_08.htm
        int plancher = indexateur.indexer(2009,600,annee);
        int plafond = indexateur.indexer(2009,1700,annee);
        return new ParametrageDeductionFraisProfessionnels("3 %",plancher,plafond);
    }

    private int construireDeductionDoubleActivite(int annee) {
        int montantLIPP;
        if (annee < 2021) {
            montantLIPP = 500;
            return indexateur.indexer(2009,montantLIPP,annee);
        } else {
            montantLIPP = 1000; // Loi 12314 Moins d’impôts pour les familles ! du 17 octobre 2019
            return indexateur.indexer(2021,montantLIPP,annee);
        }

    }

    private int montantDeductionSocialeParDemiCharge(int annee) {
        int montantLIPP;
        if (annee < 2011) {
            montantLIPP = 4500;
            return indexateur.indexer(2009,montantLIPP,annee);
        } else if (annee < 2021) {
            // Voir loi 10199 article 39 alinea 1 lettre a
            montantLIPP = 5000;
            return indexateur.indexer(2009,montantLIPP,annee);
        } else {
            // Loi 12314 Moins d’impôts pour les familles ! du 17 octobre 2019
            montantLIPP = 6500;
            return indexateur.indexer(2021,montantLIPP,annee);
        }
    }

    private int montantDeductionSocialeParCharge(int annee) {
        return 2 * montantDeductionSocialeParDemiCharge(annee);
    }

    private BaremeParTranche construireBaremeArt40Alinea3LIPP() {
        return unBareme()
                .jusqua(50000).valeur(10000)
                .de(50000).a(56700).valeur(8000)
                .de(56700).a(64000).valeur(6000)
                .de(64000).a(71500).valeur(4000)
                .de(71500).a(80000).valeur(2000)
                .plusDe(80000).valeur(0).construire();
    }

    private BaremeParTranche baremeRentierAVSAISeul(int annee) {
        BaremeParTranche baremeLIPP2009 = this.construireBaremeArt40Alinea3LIPP();
        BaremeParTranche baremeAdapte = indexateur.indexer(2009, baremeLIPP2009, annee);
        return baremeAdapte;
    }


    private ParametrageDeductionSocialeRevenu construireParametrageDeductionSocialeRevenu(int annee) {
        int montantDeductionSocialeParCharge = montantDeductionSocialeParCharge(annee);
        BaremeParTranche baremeRentierAVSAISeul = baremeRentierAVSAISeul(annee);
        return new ParametrageDeductionSocialeRevenu(montantDeductionSocialeParCharge,baremeRentierAVSAISeul);
    }


}
