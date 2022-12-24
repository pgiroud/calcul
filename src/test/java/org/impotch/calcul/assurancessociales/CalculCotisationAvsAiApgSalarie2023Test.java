package org.impotch.calcul.assurancessociales;
import java.math.BigDecimal;

import javax.annotation.Resource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(locations = "/beansAssurancesSociales.xml")
public class CalculCotisationAvsAiApgSalarie2023Test {
    @Resource(name = "fournisseurRegleCalculAssuranceSociale")
    private FournisseurRegleCalculAssuranceSociale fournisseurRegleCalculCotisationAssuranceSociale;

    private CalculCotisationsSocialesSalarie calculateur;

    @BeforeEach
    public void initialise() throws Exception {
        calculateur = fournisseurRegleCalculCotisationAssuranceSociale.getCalculateurCotisationsSocialesSalarie(2023);
    }

    @Test
    public void calculCotisationAi() {
        assertThat(calculateur.calculPartSalarieeCotisationAi(BigDecimal.valueOf(100000))).isEqualByComparingTo(BigDecimal.valueOf(700));
    }

    @Test
    public void calculCotisationApg() {
        assertThat(calculateur.calculPartSalarieeCotisationApg(BigDecimal.valueOf(100000))).isEqualByComparingTo(BigDecimal.valueOf(250));
    }

    @Test
    public void calculCotisationAvs() {
        assertThat(calculateur.calculPartSalarieeCotisationAvs(BigDecimal.valueOf(100000))).isEqualByComparingTo(BigDecimal.valueOf(4350));
    }

    @Test
    public void calculCotisationAvsAiApg() {
        assertThat(calculateur.calculPartSalarieeCotisationAvsAiApg(BigDecimal.valueOf(100000))).isEqualByComparingTo(BigDecimal.valueOf(5300));
    }

}
