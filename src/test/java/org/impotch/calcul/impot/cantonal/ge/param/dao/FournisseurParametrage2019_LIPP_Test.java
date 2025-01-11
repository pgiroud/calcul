package org.impotch.calcul.impot.cantonal.ge.param.dao;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.impotch.calcul.impot.cantonal.ge.ContexteTestCH_GE.CTX_TST_CH_GE;

public class FournisseurParametrage2019_LIPP_Test {

    private final static int ANNEE = 2019;

    @Test
    public void tauxFraisProfessionnels() {
        String taux = CTX_TST_CH_GE.getParametrageLIPP().revenu(ANNEE).tauxDeductionFraisProfessionnels();
        assertThat(taux).isEqualTo("3 %");
    }

    @Test
    public void plancherFraisProfessionnels() {
        int montant = CTX_TST_CH_GE.getParametrageLIPP().revenu(ANNEE).plancherDeductionFraisProfessionnels();
        assertThat(montant).isEqualTo(599);
    }

    @Test
    public void plafondFraisProfessionnels() {
        int montant = CTX_TST_CH_GE.getParametrageLIPP().revenu(ANNEE).plafondDeductionFraisProfessionnels();
        assertThat(montant).isEqualTo(1697);
    }

}
