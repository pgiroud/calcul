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


import javax.annotation.PostConstruct;

import java.math.BigDecimal;

/**
 * Created by GIROUDPA on 11.08.2014.
 */
public class IndexateurGenevois implements Indexateur {

    private FournisseurIndicePeriodique fournisseurIndice;

    private final int anneeBase;
    private IndexateurPeriodique indexateurQuadriennal;
    private IndexateurPeriodique indexateurAnnuel;

    public IndexateurGenevois(int anneeBase) {
        this.anneeBase = anneeBase;
    }

    public void setFournisseurIndice(FournisseurIndicePeriodique fournisseurIndice) {
        this.fournisseurIndice = fournisseurIndice;
    }

    @PostConstruct
    private void renseignerIndexateur() {
        indexateurQuadriennal = new IndexateurPeriodique(anneeBase,4);
        indexateurQuadriennal.setFournisseurIndice(fournisseurIndice);
        indexateurAnnuel = new IndexateurPeriodique(anneeBase,1);
        indexateurAnnuel.setFournisseurIndice(fournisseurIndice);
    }

    @Override
    public BigDecimal indexer(BigDecimal montantBase, int annee, TypeArrondi arrondi) {
        return indexateurQuadriennal.indexer(montantBase,annee,arrondi);
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
