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


import org.impotch.bareme.BaremeParTranche;
import org.impotch.bareme.BaremeTauxMarginalConstantParTranche;
import org.impotch.calcul.impot.indexation.FournisseurIndicePeriodique;
import org.impotch.calcul.impot.indexation.Indexateur;
import org.impotch.calcul.impot.indexation.IndexateurPeriodique;
import org.impotch.util.TypeArrondi;

import java.math.BigDecimal;
import static org.impotch.calcul.impot.indexation.IndexateurPeriodique.unConstructeurIndexateurQuadriAnnuel;
import static org.impotch.calcul.impot.indexation.IndexateurPeriodique.unConstructeurIndexateurAnnuel;

/**
 * Created by GIROUDPA on 11.08.2014.
 */
public class IndexateurGenevois implements Indexateur {


    private FournisseurIndexGenevois fournisseurIndice;


    private IndexateurPeriodique indexateurQuadriennalLIPP5_D_3_16_2000;
    private IndexateurPeriodique indexateurAnnuelLIPP5_D_3_16_2000;
    private IndexateurPeriodique indexateurQuadriennalLIPP2009;
    private IndexateurPeriodique indexateurAnnuelLIPP2009;

    public IndexateurGenevois(FournisseurIndexGenevois fournisseurIndice) {
        super();
        this.fournisseurIndice = fournisseurIndice;
        renseignerIndexateur();
    }


    private void renseignerIndexateur() {
        indexateurQuadriennalLIPP5_D_3_16_2000 = unConstructeurIndexateurQuadriAnnuel(2001)
                .fournisseurIndice(fournisseurIndice.getFournisseurIndiceGEBaseMai1993()).cons();

        indexateurAnnuelLIPP5_D_3_16_2000 = unConstructeurIndexateurAnnuel(2001)
                .fournisseurIndice(fournisseurIndice.getFournisseurIndiceGEBaseMai1993()).cons();

        indexateurQuadriennalLIPP2009 = unConstructeurIndexateurQuadriAnnuel(2009)
                .fournisseurIndice(fournisseurIndice.getFournisseurIndiceGEBaseDecembre2005()).cons();

        indexateurAnnuelLIPP2009 = unConstructeurIndexateurAnnuel(2009)
                .fournisseurIndice(fournisseurIndice.getFournisseurIndiceGEBaseDecembre2005()).cons();
    }

    @Override
    public BigDecimal indexer(int anneeDebut, BigDecimal montantBase, int annee, TypeArrondi arrondi) {
        if (annee < 2001) throw new IllegalArgumentException("L'indexateur genvois n’est défini qu’à partir de 2001. Il faudrait compléter pour les années précédentes.");
        if (annee <= 2009) {
            return indexateurQuadriennalLIPP5_D_3_16_2000.indexer(anneeDebut, montantBase,annee,arrondi);
        }
        if (annee < 2024) {
            return indexateurQuadriennalLIPP2009.indexer(anneeDebut, montantBase,annee,arrondi);
        } else {
            // PL 13094-A Grand Conseil À partir de 2024, on indexe tous les ans
            return indexateurAnnuelLIPP2009.indexer(anneeDebut, montantBase,annee,arrondi);
        }
    }

    @Override
    public BaremeParTranche indexer(int anneeDebut, BaremeParTranche bareme, int annee, TypeArrondi arrondi) {
        if (annee < 2001) throw new IllegalArgumentException("L'indexateur genvois n’est défini qu’à partir de 2001. Il faudrait compléter pour les années précédentes.");
        if (annee <= 2009) {
            return indexateurQuadriennalLIPP5_D_3_16_2000.indexer(anneeDebut, bareme,annee,arrondi);
        }
        if (annee < 2024) {
            return indexateurQuadriennalLIPP2009.indexer(anneeDebut, bareme,annee,arrondi);
        } else {
            // PL 13094-A Grand Conseil À partir de 2024, on indexe tous les ans
            return indexateurAnnuelLIPP2009.indexer(anneeDebut, bareme,annee,arrondi);
        }
    }

    @Override
    public BaremeTauxMarginalConstantParTranche indexer(int anneeDebut, BaremeTauxMarginalConstantParTranche bareme, int annee, TypeArrondi arrondi) {
        if (annee < 2001) throw new IllegalArgumentException("L'indexateur genvois n’est défini qu’à partir de 2001. Il faudrait compléter pour les années précédentes.");
        if (annee <= 2009) {
            throw new IllegalStateException("Entre 2001 et 2009, les barèmes genevois n’était pas constant par tranche !!");
        }
        return indexateurAnnuelLIPP2009.indexer(anneeDebut, bareme,annee,arrondi);
    }

}
