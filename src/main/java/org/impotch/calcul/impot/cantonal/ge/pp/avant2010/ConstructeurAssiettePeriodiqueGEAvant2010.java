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
package org.impotch.calcul.impot.cantonal.ge.pp.avant2010;

import org.impotch.calcul.impot.PeriodeFiscale;
import org.impotch.calcul.impot.taxation.pp.ConstructeurAssiettePeriodique;
import org.impotch.calcul.impot.taxation.pp.FournisseurAssiettePeriodique;
import org.impotch.calcul.lieu.ICommuneSuisse;

import java.math.BigDecimal;

public class ConstructeurAssiettePeriodiqueGEAvant2010 {

    private final ConstructeurAssiettePeriodique cons;
    private BigDecimal rabaisDeterminant;

    public ConstructeurAssiettePeriodiqueGEAvant2010(PeriodeFiscale periodeFiscale) {
        cons = new ConstructeurAssiettePeriodique(periodeFiscale);
    }

    public ConstructeurAssiettePeriodiqueGEAvant2010 imposable(BigDecimal montant) {
        cons.imposable(montant);
        return this;
    }

    public ConstructeurAssiettePeriodiqueGEAvant2010 determinant(BigDecimal montant) {
        cons.determinant(montant);
        return this;
    }

    public ConstructeurAssiettePeriodiqueGEAvant2010 imposableEtDeterminant(BigDecimal montant) {
        cons.imposableEtDeterminant(montant);
        return this;
    }

    public ConstructeurAssiettePeriodiqueGEAvant2010 imposableEtDeterminant(int montant) {
        return imposableEtDeterminant(BigDecimal.valueOf(montant));
    }



    public ConstructeurAssiettePeriodiqueGEAvant2010 rabaisDeterminant(BigDecimal montant) {
        rabaisDeterminant = montant;
        return this;
    }

    public ConstructeurAssiettePeriodiqueGEAvant2010 rabaisDeterminant(int montant) {
        return rabaisDeterminant(BigDecimal.valueOf(montant));
    }

    public ConstructeurAssiettePeriodiqueGEAvant2010 uniqueCommuneDomicile(ICommuneSuisse commune) {
        cons.uniqueCommuneDomicile(commune);
        return this;
    }

    public ConstructeurAssiettePeriodiqueGEAvant2010 uniqueCommune(ICommuneSuisse commune) {
        cons.uniqueCommune(commune);
        return this;
    }

    public FournisseurAssiettePeriodiqueGE fournir() {
        AssiettesPeriodiquesGE assiettes = new AssiettesPeriodiquesGE(cons.fournir());
        assiettes.rabaisDeterminant = rabaisDeterminant;
        return assiettes;
    }
}
