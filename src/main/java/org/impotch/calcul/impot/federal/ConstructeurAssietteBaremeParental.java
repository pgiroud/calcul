package org.impotch.calcul.impot.federal;

import org.impotch.calcul.impot.taxation.pp.ConstructeurAssietteRabais;
import org.impotch.calcul.impot.taxation.pp.FournisseurAssiettePeriodique;

public class ConstructeurAssietteBaremeParental implements ConstructeurAssietteRabais {
    @Override
    public FournisseurAssiettePeriodique construireAssietteRabais(FournisseurAssiettePeriodique assietteImpot) {
        return assietteImpot;
    }
}
