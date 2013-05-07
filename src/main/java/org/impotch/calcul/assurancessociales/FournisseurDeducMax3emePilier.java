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

import org.impotch.util.TypeArrondi;

import java.math.BigDecimal;

class FournisseurDeducMax3emePilier implements FournisseurDeductionMaxPilier3a {

    private FournisseurMontantsLimitesPrevoyanceProfessionnelle fournisseurMontantsLimitesPrevoyanceProfessionnelle;

    void setFournisseurMontantsLimitesPrevoyanceProfessionnelle(FournisseurMontantsLimitesPrevoyanceProfessionnelle fournisseurMontantsLimitesPrevoyanceProfessionnelle) {
        this.fournisseurMontantsLimitesPrevoyanceProfessionnelle = fournisseurMontantsLimitesPrevoyanceProfessionnelle;
    }

    @Override
    public BigDecimal getDeductionMaximaleAvecLPP() {
        BigDecimal salaireLimiteSuperieur = fournisseurMontantsLimitesPrevoyanceProfessionnelle.getLimiteSuperieureSalaireAnnuel();
        return TypeArrondi.FRANC.arrondirMontant(salaireLimiteSuperieur.multiply(BigDecimal.valueOf(8)).movePointLeft(2));
    }

    @Override
    public BigDecimal getDeductionMaximaleSansLPP() {
        BigDecimal salaireLimiteSuperieur = fournisseurMontantsLimitesPrevoyanceProfessionnelle.getLimiteSuperieureSalaireAnnuel();
        return TypeArrondi.FRANC.arrondirMontant(salaireLimiteSuperieur.multiply(BigDecimal.valueOf(40)).movePointLeft(2));
    }
}
