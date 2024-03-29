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
package org.impotch.calcul.impot.taxation.pp;


import org.impotch.calcul.impot.ConstructeurAssiettesCommunales;
import org.impotch.calcul.impot.PeriodeFiscale;
import org.impotch.calcul.lieu.ICommuneSuisse;

import java.math.BigDecimal;

public class ConstructeurAssiettePeriodique {

    public static final ConstructeurAssiettePeriodique unConstructeurAssiette(PeriodeFiscale periodeFiscale) {
        return new ConstructeurAssiettePeriodique(periodeFiscale);
    }

    public static final ConstructeurAssiettePeriodique unConstructeurAssiette(int anneeFiscale) {
        return unConstructeurAssiette(PeriodeFiscale.annee(anneeFiscale));
    }


    private final AssiettePeridodique assiettes;
    private ConstructeurAssiettesCommunales consCommunales;

    public ConstructeurAssiettePeriodique(PeriodeFiscale periodeFiscale) {
        assiettes = new AssiettePeridodique(periodeFiscale);
        consCommunales = ConstructeurAssiettesCommunales.unConstructeurAssiettesCommunales(periodeFiscale);
    }

    public ConstructeurAssiettePeriodique imposable(BigDecimal montant) {
        assiettes.montantimposable = montant;
        return this;
    }

    public ConstructeurAssiettePeriodique imposable(int montant) {
        return imposable(BigDecimal.valueOf(montant));
    }



    public ConstructeurAssiettePeriodique determinant(BigDecimal montant) {
        assiettes.montantDeterminant = montant;
        return this;
    }

    public ConstructeurAssiettePeriodique determinant(int montant) {
        return determinant(BigDecimal.valueOf(montant));
    }


    public ConstructeurAssiettePeriodique imposableEtDeterminant(BigDecimal montant) {
        return imposable(montant).determinant(montant);
    }


    public ConstructeurAssiettePeriodique uniqueCommune(ICommuneSuisse commune) {
        consCommunales.uniqueCommune(commune);
        return this;
    }

    public ConstructeurAssiettePeriodique uniqueCommuneDomicile(ICommuneSuisse commune) {
        consCommunales.uniqueCommuneDomicile(commune);
        return this;
    }

    public ConstructeurAssiettePeriodique communeDomicile(ICommuneSuisse commune, int nbreJour) {
        consCommunales.communeDomicile(commune, nbreJour);
        return this;
    }

    public ConstructeurAssiettePeriodique part(ICommuneSuisse commune, BigDecimal montant) {
        consCommunales.part(commune, montant);
        return this;
    }

    public FournisseurAssiettePeriodique fournir() {
        assiettes.fournisseurAssietteCommunale = consCommunales.fournir();
        return assiettes;
    }


}
