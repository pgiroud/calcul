package org.impotch.calcul.impot.federal.param;

import org.impotch.bareme.Bareme;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.OptionalInt;

public class FournisseurParametrageAnnuelIFDEnMemoire implements FournisseurParametrageAnnuelIFD {



    private final FournisseurBaremeIFD baremes = new FournisseurBaremeIFDEnMemoire();

    @Override
    public Bareme getBaremeImpotRevenuPersonnePhysiquePourPersonneSeule(int annee) {
        return baremes.getBaremeImpotRevenuPersonnePhysiquePourPersonneSeule(annee);
    }

    @Override
    public Bareme getBaremeImpotRevenuPersonnePhysiquePourFamille(int annee) {
        return baremes.getBaremeImpotRevenuPersonnePhysiquePourFamille(annee);
    }

    @Override
    public Bareme getBaremeImpotRevenuPraeNumerandoPersonnePhysiquePourPersonneSeule(int annee) {
        return baremes.getBaremeImpotRevenuPraeNumerandoPersonnePhysiquePourPersonneSeule(annee);
    }

    @Override
    public Bareme getBaremeImpotRevenuPraeNumerandoPersonnePhysiquePourFamille(int annee) {
        return baremes.getBaremeImpotRevenuPraeNumerandoPersonnePhysiquePourFamille(annee);
    }

    @Override
    public OptionalInt rabaisImpotCharge(int annee) {
        if (2011 > annee) return OptionalInt.empty();
        if (2011 == annee) return OptionalInt.of(250); // Loi fédérale sur les allégements fiscaux
        // en faveur des familles avec enfants du 25 septembre 2009
        // Art. 214, al. 2 2bis
        if (2012 == annee // RO 2011 4503
                || 2013 == annee
                || 2014 == annee // https://www.fedlex.admin.ch/eli/cc/2013/589/fr RO 2013 589 Art. 2 alinea 3
                || 2015 == annee
                || 2016 == annee
                || 2017 == annee
                || 2018 == annee
                || 2019 == annee
                || 2020 == annee
                || 2021 == annee
                || 2022 == annee) return OptionalInt.of(251); // https://www.fedlex.admin.ch/eli/cc/2013/589/fr RO 2013 589 Art. 2 alinea 3
        if (2023 == annee) return OptionalInt.of(255); // https://www.fedlex.admin.ch/eli/cc/2022/575/fr RO 2022 575 Art.2 alinea 3
        if (2024 == annee) return OptionalInt.of(259); // https://www.fedlex.admin.ch/eli/cc/2023/493/fr RO 2023 493 Art.2 alinea 3
        if (2025 == annee) return OptionalInt.of(263); // https://www.fedlex.admin.ch/eli/oc/2024/479/fr RO 2024 479 Art.2 alinea 3
        throw new IllegalArgumentException("La progression à froid est inconnue pour l’année " + annee);
    }
}
