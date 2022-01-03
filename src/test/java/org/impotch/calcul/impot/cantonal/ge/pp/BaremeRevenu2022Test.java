package org.impotch.calcul.impot.cantonal.ge.pp;

import org.impotch.bareme.Bareme;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.annotation.Resource;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(locations = {"/beansCH_GE.xml"})
public class BaremeRevenu2022Test {
    @Resource(name = "fournisseurRegleImpotCantonalGE")
    private FournisseurRegleImpotCantonalGE fournisseur;

    @Test
    public void borneBareme() {
        Bareme bareme = fournisseur.getBaremeRevenu(2022);
        assertThat(bareme.calcul(new BigDecimal("17697"))).isEqualTo(new BigDecimal("0.00"));
        assertThat(bareme.calcul(new BigDecimal("21322"))).isEqualTo(new BigDecimal("290.00"));
        assertThat(bareme.calcul(new BigDecimal("23454"))).isEqualTo(new BigDecimal("481.90"));
        assertThat(bareme.calcul(new BigDecimal("25586"))).isEqualTo(new BigDecimal("695.10"));
        assertThat(bareme.calcul(new BigDecimal("27719"))).isEqualTo(new BigDecimal("929.75"));
        assertThat(bareme.calcul(new BigDecimal("33049"))).isEqualTo(new BigDecimal("1569.35"));
        assertThat(bareme.calcul(new BigDecimal("37313"))).isEqualTo(new BigDecimal("2123.65"));
        assertThat(bareme.calcul(new BigDecimal("41578"))).isEqualTo(new BigDecimal("2720.75"));
        assertThat(bareme.calcul(new BigDecimal("45842"))).isEqualTo(new BigDecimal("3339.05"));
        assertThat(bareme.calcul(new BigDecimal("616206"))).isEqualTo(new BigDecimal("103088.75"));
    }


}
