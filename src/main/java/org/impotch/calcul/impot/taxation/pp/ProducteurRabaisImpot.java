package org.impotch.calcul.impot.taxation.pp;

import java.math.BigDecimal;

public interface ProducteurRabaisImpot<S extends SituationFamiliale, F extends FournisseurMontantRabaisImpot> {
    BigDecimal produireMontantDeterminantRabais(S situation, F fournisseurAssiette);
}
