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

    private FournisseurIndicePeriodique fournisseurIndice;

    private final int anneeBaseIndexation;
    private final int anneeBase;
    private IndexateurPeriodique indexateurQuadriennal;
    private IndexateurPeriodique indexateurAnnuel;

    public IndexateurGenevois(int anneeBaseIndexation, int anneeBase, FournisseurIndicePeriodique fournisseurIndice) {
        super();
        this.anneeBaseIndexation = anneeBaseIndexation;
        this.fournisseurIndice = fournisseurIndice;
        this.anneeBase = anneeBase;
        renseignerIndexateur();
    }


    private void renseignerIndexateur() {
        indexateurQuadriennal = unConstructeurIndexateurQuadriAnnuel(anneeBaseIndexation)
                .anneeBase(anneeBase)
                .fournisseurIndice(fournisseurIndice).cons();

        indexateurAnnuel = unConstructeurIndexateurAnnuel(anneeBaseIndexation)
                .anneeBase(anneeBase)
                .fournisseurIndice(fournisseurIndice).cons();
    }

    @Override
    public BigDecimal indexer(BigDecimal montantBase, int annee, TypeArrondi arrondi) {
        // PL 13094-A Grand Conseil
        if (annee < 2024) {
            return indexateurQuadriennal.indexer(montantBase,annee,arrondi);
        } else {
            return indexateurAnnuel.indexer(montantBase,annee,arrondi);
        }
    }

    @Override
    public BaremeParTranche indexer(BaremeParTranche bareme, int annee, TypeArrondi arrondi) {
        return indexateurQuadriennal.indexer(bareme,annee,arrondi);
    }

    @Override
    public BaremeTauxMarginalConstantParTranche indexer(BaremeTauxMarginalConstantParTranche bareme, int annee, TypeArrondi arrondi) {
        return indexateurAnnuel.indexer(bareme,annee,arrondi);
    }



}
