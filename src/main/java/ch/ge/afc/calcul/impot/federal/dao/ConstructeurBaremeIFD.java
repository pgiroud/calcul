package ch.ge.afc.calcul.impot.federal.dao;

import ch.ge.afc.bareme.BaremeTauxMarginalConstantParTranche;
import ch.ge.afc.bareme.TrancheBareme;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: patrick
 * Date: 16/10/11
 * Time: 20:35
 * To change this template use File | Settings | File Templates.
 */
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
