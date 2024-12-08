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

import org.impotch.calcul.impot.FournisseurAssietteCommunale;
import org.impotch.calcul.impot.PeriodeFiscale;
import org.impotch.calcul.impot.taxation.pp.FournisseurAssiettePeriodique;

import java.math.BigDecimal;
import java.util.Optional;

class AssiettesPeriodiquesGE implements FournisseurAssiettePeriodiqueGE {

    private final FournisseurAssiettePeriodique assiettes;
    public BigDecimal rabaisDeterminant;

    public AssiettesPeriodiquesGE(FournisseurAssiettePeriodique assiettes) {
        this.assiettes = assiettes;
    }

    @Override
    public BigDecimal getMontantImposable() {
        return assiettes.getMontantImposable();
    }

    @Override
    public Optional<BigDecimal> getMontantDeterminant() {
        return assiettes.getMontantDeterminant();
    }

    @Override
    public PeriodeFiscale getPeriodeFiscale() {
        return assiettes.getPeriodeFiscale();
    }

    @Override
    public int getNombreJourPourAnnualisation() {
        return assiettes.getNombreJourPourAnnualisation();
    }

    @Override
    public Optional<FournisseurAssietteCommunale> getFournisseurAssietteCommunale() {
        return assiettes.getFournisseurAssietteCommunale();
    }

    @Override
    public BigDecimal getMontantDeterminantRabais() {
        return rabaisDeterminant;
    }
}
