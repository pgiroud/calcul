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
package org.impotch.calcul.impot.taxation.pp.ge.deduction.rabais;

import java.math.BigDecimal;

import org.impotch.calcul.assurancessociales.SituationAVS;
import org.impotch.calcul.assurancessociales.StatutAVS;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class RenteAVSRabaisImpot2008Test extends
        AbstractTestProducteurBaseRabaisImpot {

    public RenteAVSRabaisImpot2008Test() {
        super();
        initProducteurBaseRabaisImpot(2008, 14288, 3640, 52000
                , 5200, 3377, 1299);
    }

    private BigDecimal renteSimpleAVSAI(int revenuPourMontantAdditionnelRenteAVS) {
        return montantAdditionnelRenteAVSAI(StatutAVS.SIMPLE, false, 0
                , 100000, revenuPourMontantAdditionnelRenteAVS);
    }

    @Test
    public void renteSimpleAVSAI() {
        assertThat(renteSimpleAVSAI(21500)).isEqualTo(BigDecimal.valueOf(13260));
        assertThat(renteSimpleAVSAI(40000)).isEqualTo(BigDecimal.valueOf(10608));
        assertThat(renteSimpleAVSAI(53040)).isEqualTo(BigDecimal.valueOf(10608));
        assertThat(renteSimpleAVSAI(53041)).isEqualTo(BigDecimal.valueOf(7956));
        assertThat(renteSimpleAVSAI(79560)).isEqualTo(BigDecimal.valueOf(7956));
        assertThat(renteSimpleAVSAI(79561)).isEqualTo(BigDecimal.ZERO);
//
//
//        assertEquals("Exemple doc GE TAX", new BigDecimal("13260"), producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.SIMPLE, false, 0), new BigDecimal("100000"), new BigDecimal("21500"))));
//        assertEquals("Exemple doc GE TAX", new BigDecimal("10608"), producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.SIMPLE, false, 0), new BigDecimal("100000"), new BigDecimal("40000"))));
//        assertEquals("Exemple doc GE TAX", new BigDecimal("10608"), producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.SIMPLE, false, 0), new BigDecimal("100000"), new BigDecimal("53040"))));
//        assertEquals("Exemple doc GE TAX", new BigDecimal("7956"), producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.SIMPLE, false, 0), new BigDecimal("100000"), new BigDecimal("53041"))));
//        assertEquals("Exemple doc GE TAX", new BigDecimal("7956"), producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.SIMPLE, false, 0), new BigDecimal("100000"), new BigDecimal("79560"))));
//        assertEquals("Exemple doc GE TAX", new BigDecimal("0"), producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.SIMPLE, false, 0), new BigDecimal("100000"), new BigDecimal("79561"))));
    }

    private BigDecimal renteCoupleAVSAI(int revenuPourMontantAdditionnelRenteAVS) {
        return montantAdditionnelRenteAVSAI(StatutAVS.COUPLE, false, 0
                , 100000, revenuPourMontantAdditionnelRenteAVS);
    }

    @Test
    public void renteCoupleAVSAI() {
        assertThat(renteCoupleAVSAI(21500)).isEqualTo(BigDecimal.valueOf(19890));
        assertThat(renteCoupleAVSAI(60000)).isEqualTo(BigDecimal.valueOf(15912));
        assertThat(renteCoupleAVSAI(79560)).isEqualTo(BigDecimal.valueOf(15912));
        assertThat(renteCoupleAVSAI(79561)).isEqualTo(BigDecimal.valueOf(11934));
        assertThat(renteCoupleAVSAI(119340)).isEqualTo(BigDecimal.valueOf(11934));
        assertThat(renteCoupleAVSAI(119341)).isEqualTo(BigDecimal.ZERO);
        assertThat(renteCoupleAVSAI(200000)).isEqualTo(BigDecimal.ZERO);

//        assertEquals("Exemple doc GE TAX", new BigDecimal("19890"), producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.COUPLE, false, 0), new BigDecimal("100000"), new BigDecimal("21500"))));
//        assertEquals("Exemple doc GE TAX", new BigDecimal("15912"), producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.COUPLE, false, 0), new BigDecimal("100000"), new BigDecimal("60000"))));
//        assertEquals("Exemple doc GE TAX", new BigDecimal("15912"), producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.COUPLE, false, 0), new BigDecimal("100000"), new BigDecimal("79560"))));
//        assertEquals("Exemple doc GE TAX", new BigDecimal("11934"), producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.COUPLE, false, 0), new BigDecimal("100000"), new BigDecimal("79561"))));
//        assertEquals("Exemple doc GE TAX", new BigDecimal("11934"), producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.COUPLE, false, 0), new BigDecimal("100000"), new BigDecimal("119340"))));
//        assertEquals("Exemple doc GE TAX", new BigDecimal("0"), producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.COUPLE, false, 0), new BigDecimal("100000"), new BigDecimal("119341"))));
//        assertEquals("Exemple doc GE TAX", new BigDecimal("0"), producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.COUPLE, false, 0), new BigDecimal("100000"), new BigDecimal("200000"))));
    }

    private BigDecimal renteSimplePlusComplementaireEpouseAVSAI(int revenuPourMontantAdditionnelRenteAVS) {
        return montantAdditionnelRenteAVSAI(StatutAVS.SIMPLE, true, 0
                , 100000, revenuPourMontantAdditionnelRenteAVS);
    }

    @Test
    public void renteSimplePlusComplementaireEpouseAVSAI() {
        assertThat(renteSimplePlusComplementaireEpouseAVSAI(20000)).isEqualTo(BigDecimal.valueOf(17238));
        assertThat(renteSimplePlusComplementaireEpouseAVSAI(60000)).isEqualTo(BigDecimal.valueOf(13790));
        assertThat(renteSimplePlusComplementaireEpouseAVSAI(68952)).isEqualTo(BigDecimal.valueOf(13790));
        assertThat(renteSimplePlusComplementaireEpouseAVSAI(68953)).isEqualTo(BigDecimal.valueOf(10343));
        assertThat(renteSimplePlusComplementaireEpouseAVSAI(103428)).isEqualTo(BigDecimal.valueOf(10343));
        assertThat(renteSimplePlusComplementaireEpouseAVSAI(103429)).isEqualTo(BigDecimal.ZERO);
        assertThat(renteSimplePlusComplementaireEpouseAVSAI(200000)).isEqualTo(BigDecimal.ZERO);
//
//        assertEquals("Exemple doc GE TAX", new BigDecimal("17238"), producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.SIMPLE, true, 0), new BigDecimal("100000"), new BigDecimal("20000"))));
//        assertEquals("Exemple doc GE TAX", new BigDecimal("13790"), producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.SIMPLE, true, 0), new BigDecimal("100000"), new BigDecimal("60000"))));
//        assertEquals("Exemple doc GE TAX", new BigDecimal("13790"), producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.SIMPLE, true, 0), new BigDecimal("100000"), new BigDecimal("68952"))));
//        assertEquals("Exemple doc GE TAX", new BigDecimal("10343"), producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.SIMPLE, true, 0), new BigDecimal("100000"), new BigDecimal("68953"))));
//        assertEquals("Exemple doc GE TAX", new BigDecimal("10343"), producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.SIMPLE, true, 0), new BigDecimal("100000"), new BigDecimal("103428"))));
//        assertEquals("Exemple doc GE TAX", new BigDecimal("0"), producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.SIMPLE, true, 0), new BigDecimal("100000"), new BigDecimal("103429"))));
//        assertEquals("Exemple doc GE TAX", new BigDecimal("0"), producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.SIMPLE, true, 0), new BigDecimal("100000"), new BigDecimal("200000"))));
    }


    private BigDecimal renteVeufAVSAI(int revenuPourMontantAdditionnelRenteAVS) {
        return montantAdditionnelRenteAVSAI(StatutAVS.VEUF, false, 0
                , 100000, revenuPourMontantAdditionnelRenteAVS);
    }


    @Test
    public void montantAdditionnelRenteVeufAVSAI() {
        assertThat(renteVeufAVSAI(20000)).isEqualTo(BigDecimal.valueOf(10608));
        assertThat(renteVeufAVSAI(40000)).isEqualTo(BigDecimal.valueOf(8486));
        assertThat(renteVeufAVSAI(42432)).isEqualTo(BigDecimal.valueOf(8486));
        assertThat(renteVeufAVSAI(42433)).isEqualTo(BigDecimal.valueOf(6365));
        assertThat(renteVeufAVSAI(63648)).isEqualTo(BigDecimal.valueOf(6365));
        assertThat(renteVeufAVSAI(63649)).isEqualTo(BigDecimal.ZERO);
        assertThat(renteVeufAVSAI(200000)).isEqualTo(BigDecimal.ZERO);
//
//
//        assertEquals("Exemple doc GE TAX", new BigDecimal("10608"), producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.VEUF, false, 0), new BigDecimal("100000"), new BigDecimal("20000"))));
//        assertEquals("Exemple doc GE TAX", new BigDecimal("8486"), producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.VEUF, false, 0), new BigDecimal("100000"), new BigDecimal("40000"))));
//        assertEquals("Exemple doc GE TAX", new BigDecimal("8486"), producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.VEUF, false, 0), new BigDecimal("100000"), new BigDecimal("42432"))));
//        assertEquals("Exemple doc GE TAX", new BigDecimal("6365"), producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.VEUF, false, 0), new BigDecimal("100000"), new BigDecimal("42433"))));
//        assertEquals("Exemple doc GE TAX", new BigDecimal("6365"), producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.VEUF, false, 0), new BigDecimal("100000"), new BigDecimal("63648"))));
//        assertEquals("Exemple doc GE TAX", new BigDecimal("0"), producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.VEUF, false, 0), new BigDecimal("100000"), new BigDecimal("63649"))));
//        assertEquals("Exemple doc GE TAX", new BigDecimal("0"), producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.VEUF, false, 0), new BigDecimal("100000"), new BigDecimal("200000"))));
    }

    private BigDecimal renteOrphelinSimple(int revenuPourMontantAdditionnelRenteAVS) {
        return montantAdditionnelRenteAVSAI(StatutAVS.ORPHELIN_SIMPLE, false, 0
                , 100000, revenuPourMontantAdditionnelRenteAVS);
    }

    @Test
    public void montantAdditionnelRenteOrphelinSimple() {
        assertThat(renteOrphelinSimple(10000)).isEqualTo(BigDecimal.valueOf(5304));
        assertThat(renteOrphelinSimple(20000)).isEqualTo(BigDecimal.valueOf(4243));
        assertThat(renteOrphelinSimple(21216)).isEqualTo(BigDecimal.valueOf(4243));
        assertThat(renteOrphelinSimple(21217)).isEqualTo(BigDecimal.valueOf(3182));
        assertThat(renteOrphelinSimple(31824)).isEqualTo(BigDecimal.valueOf(3182));
        assertThat(renteOrphelinSimple(31825)).isEqualTo(BigDecimal.ZERO);
        assertThat(renteOrphelinSimple(200000)).isEqualTo(BigDecimal.ZERO);
//
//
//        assertEquals("Exemple doc GE TAX", new BigDecimal("5304"), producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.ORPHELIN_SIMPLE, false, 0), new BigDecimal("100000"), new BigDecimal("10000"))));
//        assertEquals("Exemple doc GE TAX", new BigDecimal("4243"), producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.ORPHELIN_SIMPLE, false, 0), new BigDecimal("100000"), new BigDecimal("20000"))));
//        assertEquals("Exemple doc GE TAX", new BigDecimal("4243"), producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.ORPHELIN_SIMPLE, false, 0), new BigDecimal("100000"), new BigDecimal("21216"))));
//        assertEquals("Exemple doc GE TAX", new BigDecimal("3182"), producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.ORPHELIN_SIMPLE, false, 0), new BigDecimal("100000"), new BigDecimal("21217"))));
//        assertEquals("Exemple doc GE TAX", new BigDecimal("3182"), producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.ORPHELIN_SIMPLE, false, 0), new BigDecimal("100000"), new BigDecimal("31824"))));
//        assertEquals("Exemple doc GE TAX", new BigDecimal("0"), producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.ORPHELIN_SIMPLE, false, 0), new BigDecimal("100000"), new BigDecimal("31825"))));
//        assertEquals("Exemple doc GE TAX", new BigDecimal("0"), producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.ORPHELIN_SIMPLE, false, 0), new BigDecimal("100000"), new BigDecimal("200000"))));
    }


    private BigDecimal renteOrphelinDouble(int revenuPourMontantAdditionnelRenteAVS) {
        return montantAdditionnelRenteAVSAI(StatutAVS.ORPHELIN_DOUBLE, false, 0
                , 100000, revenuPourMontantAdditionnelRenteAVS);
    }


    @Test
    public void montantAdditionnelRenteOrphelinDouble() {
        assertThat(renteOrphelinDouble(10000)).isEqualTo(BigDecimal.valueOf(7956));
        assertThat(renteOrphelinDouble(25000)).isEqualTo(BigDecimal.valueOf(6365));
        assertThat(renteOrphelinDouble(31824)).isEqualTo(BigDecimal.valueOf(6365));
        assertThat(renteOrphelinDouble(31825)).isEqualTo(BigDecimal.valueOf(4774));
        assertThat(renteOrphelinDouble(47736)).isEqualTo(BigDecimal.valueOf(4774));
        assertThat(renteOrphelinDouble(47737)).isEqualTo(BigDecimal.ZERO);
        assertThat(renteOrphelinDouble(200000)).isEqualTo(BigDecimal.ZERO);
//
//        assertEquals("Exemple doc GE TAX", new BigDecimal("7956"), producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.ORPHELIN_DOUBLE, false, 0), new BigDecimal("100000"), new BigDecimal("10000"))));
//        assertEquals("Exemple doc GE TAX", new BigDecimal("6365"), producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.ORPHELIN_DOUBLE, false, 0), new BigDecimal("100000"), new BigDecimal("25000"))));
//        assertEquals("Exemple doc GE TAX", new BigDecimal("6365"), producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.ORPHELIN_DOUBLE, false, 0), new BigDecimal("100000"), new BigDecimal("31824"))));
//        assertEquals("Exemple doc GE TAX", new BigDecimal("4774"), producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.ORPHELIN_DOUBLE, false, 0), new BigDecimal("100000"), new BigDecimal("31825"))));
//        assertEquals("Exemple doc GE TAX", new BigDecimal("4774"), producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.ORPHELIN_DOUBLE, false, 0), new BigDecimal("100000"), new BigDecimal("47736"))));
//        assertEquals("Exemple doc GE TAX", new BigDecimal("0"), producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.ORPHELIN_DOUBLE, false, 0), new BigDecimal("100000"), new BigDecimal("47737"))));
//        assertEquals("Exemple doc GE TAX", new BigDecimal("0"), producteur2008.produireMontantAdditionnelRenteAVSAI(this.creerFournisseurMontant(new SituationAVS(StatutAVS.ORPHELIN_DOUBLE, false, 0), new BigDecimal("100000"), new BigDecimal("200000"))));
    }

}
