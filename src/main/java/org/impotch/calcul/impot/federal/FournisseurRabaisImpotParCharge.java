package org.impotch.calcul.impot.federal;

import org.impotch.calcul.impot.taxation.pp.FournisseurMontantRabaisImpot;

import java.math.BigDecimal;

public class FournisseurRabaisImpotParCharge implements FournisseurMontantRabaisImpot {

    private final BigDecimal rabaisParCharge;

    public FournisseurRabaisImpotParCharge(int montantParCharge) {
        this.rabaisParCharge = BigDecimal.valueOf(montantParCharge);
    }

    public BigDecimal get() {
        return rabaisParCharge;
    }
}
