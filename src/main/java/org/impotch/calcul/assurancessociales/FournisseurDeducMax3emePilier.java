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

import static org.impotch.util.TypeArrondi.UNITE_LA_PLUS_PROCHE;

class FournisseurDeducMax3emePilier implements FournisseurDeductionMaxPilier3a {

    private FournisseurMontantsLimitesPrevoyanceProfessionnelle fournisseurMontantsLimitesPrevoyanceProfessionnelle;

    void setFournisseurMontantsLimitesPrevoyanceProfessionnelle(FournisseurMontantsLimitesPrevoyanceProfessionnelle fournisseurMontantsLimitesPrevoyanceProfessionnelle) {
        this.fournisseurMontantsLimitesPrevoyanceProfessionnelle = fournisseurMontantsLimitesPrevoyanceProfessionnelle;
    }

    /**
     * D'après l'alinea 1 de l'article 7 de l'ordonnance
     * sur les déductions admises fiscalement
     * pour les cotisations versées à des formes
     * reconnues de prévoyance  OPP3, on peut déduire par année, jusqu'à 8 % du montant-limite supérieur fixé à l'art. 8, al. 1, LPP,
     * s'ils sont affiliés à une institution de prévoyance au sens de l'art. 80 LPP;
     * @return  la déduction maximale.
     */
    @Override
    public BigDecimal getDeductionMaximaleAvecLPP() {
        BigDecimal salaireLimiteSuperieur = fournisseurMontantsLimitesPrevoyanceProfessionnelle.getLimiteSuperieureSalaireAnnuel();
        return UNITE_LA_PLUS_PROCHE.arrondirMontant(salaireLimiteSuperieur.multiply(BigDecimal.valueOf(8)).movePointLeft(2));
    }

    /**
     * D'après l'alinea 2 de l'article 7 de l'ordonnance
     * sur les déductions admises fiscalement
     * pour les cotisations versées à des formes
     * reconnues de prévoyance  OPP3, on peut déduire par année, au maximum jusqu'à 40 % du montant-limite supérieur fixé
     * à l'art. 8, al. 1, LPP, s'ils ne sont pas affiliés à une institution de prévoyance au sens de l'art. 80 LPP.
     * @return la déduction maximale.
     */
    @Override
    public BigDecimal getDeductionMaximaleSansLPP() {
        BigDecimal salaireLimiteSuperieur = fournisseurMontantsLimitesPrevoyanceProfessionnelle.getLimiteSuperieureSalaireAnnuel();
        return UNITE_LA_PLUS_PROCHE.arrondirMontant(salaireLimiteSuperieur.multiply(BigDecimal.valueOf(40)).movePointLeft(2));
    }
}
