package org.impotch.calcul.assurancessociales;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

public interface FournisseurRegleCalculCotisationsAssuranceSociale {
    Optional<Function<BigDecimal, BigDecimal>> regle(int annee, TypeAssuranceSociale type);

    default Optional<Function<BigDecimal, BigDecimal>> reglePartSalarie(int annee, TypeAssuranceSociale type) {
        return regle(annee,type).map(regle -> {
            return regle.andThen(r -> r.divide(BigDecimal.TWO));
        });
    }
}
