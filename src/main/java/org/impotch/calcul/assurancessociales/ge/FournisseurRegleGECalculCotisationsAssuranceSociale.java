package org.impotch.calcul.assurancessociales.ge;

import org.impotch.calcul.assurancessociales.FournisseurRegleCalculCotisationsAssuranceSociale;
import org.impotch.calcul.assurancessociales.TypeAssuranceSociale;
import org.impotch.calcul.assurancessociales.ge.param.FournisseurParametrageAnnuelAssurancesSocialesGenevoises;
import org.impotch.calcul.assurancessociales.ge.param.ParametrageAnnuelCotisationsSocialesGenevoises;
import org.impotch.util.BigDecimalUtil;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

public class FournisseurRegleGECalculCotisationsAssuranceSociale implements FournisseurRegleCalculCotisationsAssuranceSociale {

    private final FournisseurRegleCalculCotisationsAssuranceSociale fournisseurCH;
    private final FournisseurParametrageAnnuelAssurancesSocialesGenevoises fournisseurParametrage;

    public FournisseurRegleGECalculCotisationsAssuranceSociale(FournisseurRegleCalculCotisationsAssuranceSociale fournisseurCH, FournisseurParametrageAnnuelAssurancesSocialesGenevoises fournisseurParametrage) {
        this.fournisseurCH = fournisseurCH;
        this.fournisseurParametrage = fournisseurParametrage;
    }

    private static BigDecimal decimal(String taux) {
        return BigDecimalUtil.parse(taux);
    }

    private Optional<Function<BigDecimal, BigDecimal>> regle(ParametrageAnnuelCotisationsSocialesGenevoises param, TypeAssuranceSociale type) {
        switch (type) {
            case AMat -> {
                return Optional.of(r -> r.multiply(decimal(param.tauxAssuranceMaternite())));
            }
            default -> {return fournisseurCH.regle(param.annee(), type);}
        }
    }

    @Override
    public Optional<Function<BigDecimal, BigDecimal>> regle(int annee, TypeAssuranceSociale type) {
        return fournisseurParametrage.parametrage(annee).flatMap(param -> regle(param, type));
    }
}
