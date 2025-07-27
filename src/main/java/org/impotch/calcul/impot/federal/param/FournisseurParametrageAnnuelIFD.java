package org.impotch.calcul.impot.federal.param;

import java.util.OptionalInt;

public interface FournisseurParametrageAnnuelIFD extends FournisseurBaremeIFD {



    // Impôts
    OptionalInt rabaisImpotCharge(int annee);
}
