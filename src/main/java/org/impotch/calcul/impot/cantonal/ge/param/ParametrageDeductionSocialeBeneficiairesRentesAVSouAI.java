package org.impotch.calcul.impot.cantonal.ge.param;

import org.impotch.bareme.BaremeParTranche;

public record ParametrageDeductionSocialeBeneficiairesRentesAVSouAI(
        BaremeParTranche personneSeule,
        BaremeParTranche coupleUnSeulRentierOuPersonneSeuleAvecCharge,
        BaremeParTranche coupleDeuxRentiers
) {

}
