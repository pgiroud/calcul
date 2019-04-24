/**
 * This file is part of impotch/calcul.
 *
 * impotch/calcul is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * impotch/calcul is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with impotch/calcul.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * This file is part of impotch/calcul.
 * <p>
 * impotch/calcul is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 * <p>
 * impotch/calcul is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with impotch/calcul.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.impotch.calcul.eco.indice.ge;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class FournisseurReevalueIndicePrixConsommationTest {

    private FournisseurReevalueIndicePrixConsommation fournisseur;

    @BeforeEach
    public void init() throws Exception {
        fournisseur = new FournisseurReevalueIndicePrixConsommation();
    }

    @Test
    @DisplayName("Indice base mai 1993 pour juillet 1998")
    public void fournirBaseMai1993_juillet1998() {
        assertThat(fournisseur.fournir(BaseIndiceGenevoisPrixConsommation.MAI_1993, 1998, 6))
                .isEqualTo(new BigDecimal("105.3"));
    }

    @Test
    @DisplayName("Indice base mai 1993 pour mai 2001")
    public void fournirBaseMai1993_mai2001() {
        assertThat(fournisseur.fournir(BaseIndiceGenevoisPrixConsommation.MAI_1993, 2001, 4))
                .isEqualTo(new BigDecimal("109.3"));
    }

    @Test
    @DisplayName("Indice base mai 1993 pour dec 2003")
    public void fournirBaseMai1993_dec2003() {
        assertThat(fournisseur.fournir(BaseIndiceGenevoisPrixConsommation.MAI_1993, 2003, 11))
                .isEqualTo(new BigDecimal("111.2"));
    }

    @Test
    @DisplayName("Indice base décembre 2005 pour janvier 2009")
    public void fournirBaseDec2005_jan2009() {
        assertThat(fournisseur.fournir(BaseIndiceGenevoisPrixConsommation.DECEMBRE_2005, 2009, 0))
                .isEqualTo(new BigDecimal("102.2"));
    }


    @Test
    public void listeMai1993_deAout2003aJuillet2004() {
        List<BigDecimal> liste = fournisseur.fournir(
                BaseIndiceGenevoisPrixConsommation.MAI_1993, 2003, 8, 2004, 7);
        assertThat(liste).hasSize(12);
        assertThat(liste.get(0)).isEqualTo(new BigDecimal("110.4"));
        assertThat(liste.get(3)).isEqualTo(new BigDecimal("111.2"));
        assertThat(liste.get(7)).isEqualTo(new BigDecimal("112.4"));
        assertThat(liste.get(11)).isEqualTo(new BigDecimal("112.1"));
    }

    @Test
    public void listeMai1993_deJanvier2000aDecembre2000() {
        List<BigDecimal> liste = fournisseur.fournir(
                BaseIndiceGenevoisPrixConsommation.MAI_1993, 2000, 0, 2000, 11);
        assertThat(liste).hasSize(12);
        assertThat(liste.get(0)).isEqualTo(new BigDecimal("107.0"));
        assertThat(liste.get(3)).isEqualTo(new BigDecimal("107.4"));
        assertThat(liste.get(8)).isEqualTo(new BigDecimal("108.5"));
        assertThat(liste.get(11)).isEqualTo(new BigDecimal("108.4"));
    }

    @Test
    public void listeMai1993_deSept2000aAout2001() {
        List<BigDecimal> liste = fournisseur.fournir(
                BaseIndiceGenevoisPrixConsommation.MAI_1993, 2000, 8, 2001, 7);
        assertThat(liste).hasSize(12);
        assertThat(liste.get(0)).isEqualTo(new BigDecimal("108.5"));
        assertThat(liste.get(2)).isEqualTo(new BigDecimal("108.5"));
        assertThat(liste.get(3)).isEqualTo(new BigDecimal("108.4"));
        assertThat(liste.get(5)).isEqualTo(new BigDecimal("108.2"));
        assertThat(liste.get(8)).isEqualTo(new BigDecimal("109.3"));
        assertThat(liste.get(9)).isEqualTo(new BigDecimal("109.5"));
        assertThat(liste.get(10)).isEqualTo(new BigDecimal("109.4"));
        assertThat(liste.get(11)).isEqualTo(new BigDecimal("108.9"));
    }


    @Test
    public void moyenne() {
        // Moyenne de sept 2003 à aout 2004
        assertThat(fournisseur.fournirMoyenne(
                BaseIndiceGenevoisPrixConsommation.MAI_1993, 2003, 8, 2004, 7))
                .isEqualTo(new BigDecimal("111.5"));
        // Moyenne de jan 2000 à dec. 2000
        assertThat(fournisseur.fournirMoyenne(
                BaseIndiceGenevoisPrixConsommation.MAI_1993,
                2000, 0, 2000, 11))
                .isEqualTo(new BigDecimal("107.8"));
        // Moyenne de jan 2007 à dec 2007
        assertThat(fournisseur.fournirMoyenne(
                BaseIndiceGenevoisPrixConsommation.DECEMBRE_2005, 2007, 0,
                2007, 11)).isEqualTo(new BigDecimal("100.9"));
    }


    private BigDecimal fournirMoyenneBaseMai1993(int anneeDebut) {
        // moyenne de septembre de l'annee à août de l'annee + 1
        return fournisseur.fournirMoyenne(BaseIndiceGenevoisPrixConsommation.MAI_1993, anneeDebut, 8, anneeDebut + 1, 7);
    }

    @Test
    public void moyennePourBaremePP() {
        assertThat(this.fournirMoyenneBaseMai1993(1998)).isEqualTo(new BigDecimal("105.8"));
        assertThat(this.fournirMoyenneBaseMai1993(1999)).isEqualTo(new BigDecimal("107.2"));
        assertThat(this.fournirMoyenneBaseMai1993(2001)).isEqualTo(new BigDecimal("109.4"));
        assertThat(this.fournirMoyenneBaseMai1993(2002)).isEqualTo(new BigDecimal("110.5"));
        assertThat(this.fournirMoyenneBaseMai1993(2003)).isEqualTo(new BigDecimal("111.5"));
        assertThat(this.fournirMoyenneBaseMai1993(2004)).isEqualTo(new BigDecimal("113.2"));
        assertThat(this.fournirMoyenneBaseMai1993(2005)).isEqualTo(new BigDecimal("114.6"));
        assertThat(this.fournirMoyenneBaseMai1993(2006)).isEqualTo(new BigDecimal("114.9"));
        assertThat(this.fournirMoyenneBaseMai1993(2007)).isEqualTo(new BigDecimal("117.5"));
    }
}
