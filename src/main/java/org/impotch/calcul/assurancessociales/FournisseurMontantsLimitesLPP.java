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
package org.impotch.calcul.assurancessociales;

import java.math.BigDecimal;


class FournisseurMontantsLimitesLPP implements FournisseurMontantsLimitesPrevoyanceProfessionnelle {

    private final static BigDecimal TROIS = BigDecimal.valueOf(3);
    private final static BigDecimal QUATRE = BigDecimal.valueOf(4);
    private final static BigDecimal DIX_HUIT = BigDecimal.valueOf(18);


    private BigDecimal renteAVSMensuelleMinimale;

    public void setRenteAVSMensuelleMinimale(BigDecimal renteAVSMensuelleMinimale) {
        this.renteAVSMensuelleMinimale = renteAVSMensuelleMinimale;
    }

    @Override
    public BigDecimal salaireMinimalAnnuel() {
        return DIX_HUIT.multiply(renteAVSMensuelleMinimale);
    }

    @Override
    public BigDecimal salaireCoordonnéMinimalAnnuel() {
        return TROIS.multiply(renteAVSMensuelleMinimale);
    }

    @Override
    public BigDecimal limiteSupérieureSalaireCoordonnéAnnuel() {
        return QUATRE.multiply(salaireMinimalAnnuel());
    }
}
