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

import org.impotch.calcul.impot.FournisseurAssietteCommunale;
import org.impotch.calcul.impot.PeriodeFiscale;

import java.math.BigDecimal;
import java.util.Optional;

class AssiettePeridodique implements FournisseurAssiettePeriodique {

    private final PeriodeFiscale periodeFiscale;
    public BigDecimal montantimposable;
    public BigDecimal montantDeterminant;
    public FournisseurAssietteCommunale fournisseurAssietteCommunale;

    public int nbreJourPourAnnualisation = 360;

    public AssiettePeridodique(PeriodeFiscale periodeFiscale) {
        this.periodeFiscale = periodeFiscale;
    }

    @Override
    public BigDecimal getMontantImposable() {
        return montantimposable;
    }

    @Override
    public Optional<BigDecimal> getMontantDeterminant() {
        return Optional.ofNullable(montantDeterminant);
    }

    @Override
    public PeriodeFiscale getPeriodeFiscale() {
        return periodeFiscale;
    }

    @Override
    public int getNombreJourPourAnnualisation() {
        return nbreJourPourAnnualisation;
    }

    @Override
    public Optional<FournisseurAssietteCommunale> getFournisseurAssietteCommunale() {
        return Optional.ofNullable(fournisseurAssietteCommunale);
    }
}
