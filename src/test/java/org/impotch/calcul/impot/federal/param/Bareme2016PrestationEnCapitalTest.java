package org.impotch.calcul.impot.federal.param;

import org.impotch.bareme.Bareme;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.impotch.calcul.impot.federal.ContexteTest_CH.CTX_TST_CH;

public class Bareme2016PrestationEnCapitalTest {

    private FournisseurBaremeIFD fournisseur = CTX_TST_CH.getFournisseurBaremeIFD();

    @Test
    public void existe() {
        Optional<Bareme> bareme = fournisseur.getBaremeImpotSourcePrestationCapital(2016);
        assertThat(bareme).isNotEmpty();
    }


    @Test
    public void faibleMontant() {
        Bareme bareme = fournisseur.getBaremeImpotSourcePrestationCapital(2016).orElseThrow();
        assertThat(bareme.calcul(74_300)).isEqualTo(new BigDecimal("182.00"));
    }

    @Test
    public void avantDerniereTranche() {
        Bareme bareme = fournisseur.getBaremeImpotSourcePrestationCapital(2016).orElseThrow();
        assertThat(bareme.calcul(675_000)).isEqualTo(new BigDecimal("14875.00"));
    }

    @Test
    @Disabled
    public void derniereTranche() {
        Bareme bareme = fournisseur.getBaremeImpotSourcePrestationCapital(2016).orElseThrow();
        assertThat(bareme.calcul(2_000_000)).isEqualTo(new BigDecimal("46000.00"));
    }
}
