package org.impotch.calcul.impot.cantonal.ge.pp;

import org.impotch.bareme.Bareme;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.impotch.calcul.impot.cantonal.ge.ContexteTestCH_GE.CTX_TST_CH_GE;
import static org.impotch.util.BigDecimalUtil.MILLE;
import static org.impotch.util.BigDecimalUtil.tx;

public class Baremes2027Test {

    private final static int PERIODE_FISCALE = 2027;
    private final static BigDecimal UN_MILLIARD = MILLE.multiply(MILLE); // 10^9
    private final static BigDecimal UN_TRILLION = UN_MILLIARD.multiply(UN_MILLIARD); // 10^18
    private final FournisseurRegleImpotCantonalGE fournisseur = CTX_TST_CH_GE.getFournisseurRegleImpotCantonalGE();

    @Test
    public void revenuBorneBareme() {
        Bareme bareme = fournisseur.getBaremeRevenu(PERIODE_FISCALE);
        assertThat(bareme.calcul(18_785)).isEqualTo("0.00");
        assertThat(bareme.calcul( 22_633)).isEqualTo(   "280.90");
        assertThat(bareme.calcul( 24_896)).isEqualTo(   "466.45");
        assertThat(bareme.calcul( 27_159)).isEqualTo(   "672.40");
        assertThat(bareme.calcul( 29_423)).isEqualTo(   "898.80");
        assertThat(bareme.calcul( 35_081)).isEqualTo(  "1515.50");
        assertThat(bareme.calcul( 39_607)).isEqualTo(  "2026.95");
        assertThat(bareme.calcul( 44_134)).isEqualTo(  "2583.75");
        assertThat(bareme.calcul( 48_661)).isEqualTo(  "3163.20");
        assertThat(bareme.calcul( 78_083)).isEqualTo(  "7046.90");
        assertThat(bareme.calcul(127_876)).isEqualTo( "14117.50");
        assertThat(bareme.calcul(172_010)).isEqualTo( "20737.60");
        assertThat(bareme.calcul(194_643)).isEqualTo( "24268.35");
        assertThat(bareme.calcul(278_385)).isEqualTo( "37499.60");
        assertThat(bareme.calcul(296_491)).isEqualTo( "40396.55");
        assertThat(bareme.calcul(417_577)).isEqualTo( "60739.00");
        assertThat(bareme.calcul(654_090)).isEqualTo("102365.30");
    }

    @Test
    public void fortuneBorneBareme() {
        Bareme bareme = fournisseur.getBaremeFortune(PERIODE_FISCALE);
        assertThat(bareme.calcul(119_262)).isEqualTo("177.70");
        assertThat(bareme.calcul(238_522)).isEqualTo("405.50");
        assertThat(bareme.calcul(357_784)).isEqualTo("684.55");
        assertThat(bareme.calcul(477_044)).isEqualTo("988.65");
        assertThat(bareme.calcul(715_568)).isEqualTo("1647.00");
        assertThat(bareme.calcul(954_090)).isEqualTo("2357.80");
        assertThat(bareme.calcul(1_192_612)).isEqualTo("3118.70");
        assertThat(bareme.calcul(1_431_134)).isEqualTo("3929.65");
        assertThat(bareme.calcul(1_788_918)).isEqualTo("5221.25");
    }

    @Test
    public void impotFortunePourEnormeFortune() {
        Bareme bareme = fournisseur.getBaremeFortune(PERIODE_FISCALE);
        BigDecimal impot = bareme.calcul(UN_TRILLION);
        BigDecimal tauxEffectif = impot.divide(UN_TRILLION,5, RoundingMode.HALF_UP);
        BigDecimal tauxAttendu = tx("3.83 ‰");
        assertThat(tauxEffectif).isEqualTo(tauxAttendu);
    }

    @Test
    public void fortuneSupplementaireBorneBareme() {
        Bareme bareme = fournisseur.getBaremeFortuneSupplementaire(PERIODE_FISCALE);
        assertThat(bareme.calcul(119_262)).isEqualTo("0.00");
        assertThat(bareme.calcul(238_522)).isEqualTo("11.40");
        assertThat(bareme.calcul(357_784)).isEqualTo("25.35");
        assertThat(bareme.calcul(477_044)).isEqualTo("55.75");
        assertThat(bareme.calcul(715_568)).isEqualTo("121.65");
        assertThat(bareme.calcul(954_090)).isEqualTo("228.10");
        assertThat(bareme.calcul(1_192_612)).isEqualTo("342.15");
        assertThat(bareme.calcul(1_431_134)).isEqualTo("504.35");
        assertThat(bareme.calcul(1_788_918)).isEqualTo("762.85");
        assertThat(bareme.calcul(3_577_835)).isEqualTo("2473.60");
    }

    @Test
    public void impotFortuneSupplementairePourEnormeFortune() {
        Bareme bareme = fournisseur.getBaremeFortuneSupplementaire(PERIODE_FISCALE);
        BigDecimal impot = bareme.calcul(UN_TRILLION);
        BigDecimal tauxEffectif = impot.divide(UN_TRILLION,7, RoundingMode.HALF_UP);
        BigDecimal tauxAttendu = tx("1.1475 ‰");
        assertThat(tauxEffectif).isEqualTo(tauxAttendu);
    }
}
