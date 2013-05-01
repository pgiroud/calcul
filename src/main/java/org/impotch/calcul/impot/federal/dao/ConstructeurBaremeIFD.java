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
package org.impotch.calcul.impot.federal.dao;

import org.impotch.bareme.BaremeTauxMarginalConstantParTranche;
import org.impotch.bareme.TrancheBareme;

import java.math.BigDecimal;

class ConstructeurBaremeIFD extends BaremeTauxMarginalConstantParTranche.Constructeur {

    private BigDecimal taux = BigDecimal.ZERO;
    private BigDecimal montantImpotEnDebutTranche = BigDecimal.ZERO;


    public ConstructeurBaremeIFD jusqua(int montant) {
        tranches.add(new TrancheBaremeIFD(new BigDecimal(montant),taux,montantImpotEnDebutTranche));
        return this;
    }

    public ConstructeurBaremeIFD pour(int montant) {
        tranches.add(new TrancheBaremeIFD(new BigDecimal(montant),taux,montantImpotEnDebutTranche));
        return this;
    }

    public ConstructeurBaremeIFD a(String montant) {
        montantImpotEnDebutTranche = new BigDecimal(montant);
        return this;
    }

    public ConstructeurBaremeIFD etPar100FrancsEnPlus(String montant) {
        taux = new BigDecimal(montant).movePointLeft(2);
        return this;
    }

    @Override
    public BaremeTauxMarginalConstantParTranche construire() {
        tranches.add(new TrancheBaremeIFD(null,taux,montantImpotEnDebutTranche));
        return super.construire();
    }

    protected static class TrancheBaremeIFD extends TrancheBareme {

        private final BigDecimal montantImpotEnDebutTranche;

        protected TrancheBaremeIFD(BigDecimal montantImposableMax, BigDecimal taux, BigDecimal montantImpotEnDebutTranche) {
            super(montantImposableMax,taux);
            this.montantImpotEnDebutTranche = montantImpotEnDebutTranche;
        }

        private boolean isDansLaTranche(BigDecimal pMontantImposableMaxTranchePrecedente, BigDecimal montantImposable) {
            boolean estPlusGrandQueBorneInf = pMontantImposableMaxTranchePrecedente.compareTo(montantImposable) <= 0;
            boolean estPlusPetitQueBorneSup = true;
            if (null != this.getMontantMaxTranche()) {
                estPlusPetitQueBorneSup = this.getMontantMaxTranche().compareTo(montantImposable) > 0;
            }
            return estPlusGrandQueBorneInf && estPlusPetitQueBorneSup;
        }

        @Override
        public BigDecimal calcul(BigDecimal pMontantImposableMaxTranchePrecedente, BigDecimal pMontantImposable) {
            if (isDansLaTranche(pMontantImposableMaxTranchePrecedente,pMontantImposable)) {
                BigDecimal montantImposableDansLaTranche = pMontantImposable.subtract(pMontantImposableMaxTranchePrecedente);
                BigDecimal impotDansLatranche = montantImposableDansLaTranche.multiply(this.getTauxOuMontant());
                return montantImpotEnDebutTranche.add(impotDansLatranche);
            } else {
                return BigDecimal.ZERO;
            }
        }

    }

}
