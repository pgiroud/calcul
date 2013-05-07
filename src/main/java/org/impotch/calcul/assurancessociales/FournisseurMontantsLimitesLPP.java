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
package org.impotch.calcul.assurancessociales;

import java.math.BigDecimal;


class FournisseurMontantsLimitesLPP implements FournisseurMontantsLimitesPrevoyanceProfessionnelle {

    private BigDecimal renteAVSMensuelleMinimale;

    public void setRenteAVSMensuelleMinimale(BigDecimal renteAVSMensuelleMinimale) {
        this.renteAVSMensuelleMinimale = renteAVSMensuelleMinimale;
    }

    @Override
    public BigDecimal getSalaireMinimalAnnuel() {
        return BigDecimal.valueOf(18).multiply(renteAVSMensuelleMinimale);
    }

    @Override
    public BigDecimal getSalaireCoordonneMinimalAnnuel() {
        return BigDecimal.valueOf(3).multiply(renteAVSMensuelleMinimale);
    }

    @Override
    public BigDecimal getDeductionCoordination() {
        return BigDecimal.valueOf(21).multiply(renteAVSMensuelleMinimale);
    }

    @Override
    public BigDecimal getLimiteSuperieureSalaireAnnuel() {
        return BigDecimal.valueOf(4).multiply(getSalaireMinimalAnnuel());
    }
}
