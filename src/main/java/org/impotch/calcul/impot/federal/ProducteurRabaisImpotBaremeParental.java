package org.impotch.calcul.impot.federal;

import org.impotch.calcul.ReglePeriodique;

import org.impotch.calcul.impot.Souverainete;
import org.impotch.calcul.impot.taxation.pp.FournisseurMontantRabaisImpot;
import org.impotch.calcul.impot.taxation.pp.ProducteurRabaisImpot;
import org.impotch.calcul.impot.taxation.pp.SituationFamiliale;
import org.impotch.util.TypeArrondi;


import java.math.BigDecimal;
import java.util.stream.Stream;

import static java.math.BigDecimal.ZERO;

public class ProducteurRabaisImpotBaremeParental extends ReglePeriodique
        implements ProducteurRabaisImpot<SituationFamiliale,FournisseurMontantRabaisImpot> {

    private static final Souverainete CH = Souverainete.CH_FEDERALE;

    private final BigDecimal rabaisParCharge;
    private final TypeArrondi typeArrondiImpot = TypeArrondi.VINGTIEME_LE_PLUS_PROCHE;

    public ProducteurRabaisImpotBaremeParental(int annee, int montantParCharge) {
        super(annee);
        this.rabaisParCharge = BigDecimal.valueOf(montantParCharge);
    }

    @Override
    public BigDecimal produireMontantDeterminantRabais(SituationFamiliale situation, FournisseurMontantRabaisImpot fournisseurAssiette) {
        BigDecimal charges = Stream.concat(situation.getPersonnesNecessiteuses().stream(),situation.getEnfants().stream())
                .map(p -> p.part(CH)).reduce(ZERO,BigDecimal::add);
        return typeArrondiImpot.arrondir(charges.multiply(rabaisParCharge));
    }
}
