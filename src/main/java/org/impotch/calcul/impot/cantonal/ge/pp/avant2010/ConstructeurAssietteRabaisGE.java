package org.impotch.calcul.impot.cantonal.ge.pp.avant2010;

import org.impotch.calcul.impot.FournisseurAssietteCommunale;
import org.impotch.calcul.impot.FournisseurAssiettePeriodiqueAvecRabais;
import org.impotch.calcul.impot.PeriodeFiscale;
import org.impotch.calcul.impot.taxation.pp.ConstructeurAssietteRabais;
import org.impotch.calcul.impot.taxation.pp.FournisseurAssiettePeriodique;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

public class ConstructeurAssietteRabaisGE implements ConstructeurAssietteRabais {

    @Override
    public FournisseurAssiettePeriodique construireAssietteRabais(FournisseurAssiettePeriodique assietteImpot) {
        FournisseurAssiettePeriodique fournisseur = new FournisseurAssiettePeriodique() {

            @Override
            public int getNombreJourPourAnnualisation() {
                return assietteImpot.getNombreJourPourAnnualisation();
            }
            @Override
            public PeriodeFiscale getPeriodeFiscale() {
                return assietteImpot.getPeriodeFiscale();
            }
            @Override
            public Optional<BigDecimal> getMontantDeterminant() {
                return Optional.of(((FournisseurAssiettePeriodiqueAvecRabais)assietteImpot).getMontantDeterminantRabais());
            }
            @Override
            public BigDecimal getMontantImposable() {
                BigDecimal montantDeterminantRabais = this.getMontantDeterminant().get();
                if (assietteImpot.getMontantDeterminant().isPresent()) {
                    BigDecimal assietteImpotDeterminant = assietteImpot.getMontantDeterminant().get();
                    if (0 < assietteImpot.getMontantImposable().compareTo(assietteImpotDeterminant))
                        return montantDeterminantRabais.multiply(assietteImpot.getMontantImposable()).divide(assietteImpotDeterminant,0, RoundingMode.HALF_UP);
                }
                return montantDeterminantRabais;
            }
            @Override
            public Optional<FournisseurAssietteCommunale> getFournisseurAssietteCommunale() {
                return Optional.empty();
            }

        };
        return fournisseur;
    }

}
