package org.impotch.calcul.impot.cantonal.ge.pp;

import org.impotch.bareme.Bareme;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.impotch.calcul.impot.cantonal.ge.ContexteTestCH_GE.CTX_TST_CH_GE;

public class BaremeRevenu2026Test {

    private final static int PERIODE_FISCALE = 2026;

    private FournisseurRegleImpotCantonalGE fournisseur = CTX_TST_CH_GE.getFournisseurRegleImpotCantonalGE();

    @Test
    public void borneBareme() {
        Bareme bareme = fournisseur.getBaremeRevenu(PERIODE_FISCALE);
        assertThat(bareme.calcul(BigDecimal.valueOf( 18_700))).isEqualTo(new BigDecimal(     "0.00"));
        assertThat(bareme.calcul(BigDecimal.valueOf( 22_530))).isEqualTo(new BigDecimal(   "279.60"));
        assertThat(bareme.calcul(BigDecimal.valueOf( 24_784))).isEqualTo(new BigDecimal(   "464.45"));
        assertThat(bareme.calcul(BigDecimal.valueOf( 27_036))).isEqualTo(new BigDecimal(   "669.40"));
        assertThat(bareme.calcul(BigDecimal.valueOf( 29_290))).isEqualTo(new BigDecimal(   "894.80"));
        assertThat(bareme.calcul(BigDecimal.valueOf( 34_922))).isEqualTo(new BigDecimal(  "1508.70"));
        assertThat(bareme.calcul(BigDecimal.valueOf( 39_428))).isEqualTo(new BigDecimal(  "2017.90"));
        assertThat(bareme.calcul(BigDecimal.valueOf( 43_935))).isEqualTo(new BigDecimal(  "2572.25"));
        assertThat(bareme.calcul(BigDecimal.valueOf( 48_441))).isEqualTo(new BigDecimal(  "3149.00"));
        assertThat(bareme.calcul(BigDecimal.valueOf( 77_730))).isEqualTo(new BigDecimal(  "7015.15"));
        assertThat(bareme.calcul(BigDecimal.valueOf(127_297))).isEqualTo(new BigDecimal( "14053.65"));
        assertThat(bareme.calcul(BigDecimal.valueOf(171_231))).isEqualTo(new BigDecimal( "20643.75"));
        assertThat(bareme.calcul(BigDecimal.valueOf(193_762))).isEqualTo(new BigDecimal( "24158.60"));
        assertThat(bareme.calcul(BigDecimal.valueOf(277_125))).isEqualTo(new BigDecimal( "37329.95"));
        assertThat(bareme.calcul(BigDecimal.valueOf(295_150))).isEqualTo(new BigDecimal( "40213.95"));
        assertThat(bareme.calcul(BigDecimal.valueOf(415_688))).isEqualTo(new BigDecimal( "60464.35"));
        assertThat(bareme.calcul(BigDecimal.valueOf(651_131))).isEqualTo(new BigDecimal("101902.30"));
    }



}
