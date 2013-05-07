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
package org.impotch.calcul.assurancessociales;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/beans.xml")
public class FournisseurDeducMaxPilier3aTest {

    @Resource(name = "fournisseurRegleCalculAssuranceSociale")
    private FournisseurRegleCalculAssuranceSociale fournisseurRegleCalculAssuranceSociale;



    @Test
    public void test2013et2014() {
        FournisseurDeductionMaxPilier3a fournisseur = fournisseurRegleCalculAssuranceSociale.getFournisseurDeductionMaximale3ePilier(2013);
        assertThat(fournisseur.getDeductionMaximaleAvecLPP()).isEqualTo("6739");
        assertThat(fournisseur.getDeductionMaximaleSansLPP()).isEqualTo("33696");
        fournisseur = fournisseurRegleCalculAssuranceSociale.getFournisseurDeductionMaximale3ePilier(2014);
        assertThat(fournisseur.getDeductionMaximaleAvecLPP()).isEqualTo("6739");
        assertThat(fournisseur.getDeductionMaximaleSansLPP()).isEqualTo("33696");
    }

    @Test
    public void test2011et2012() {
        FournisseurDeductionMaxPilier3a fournisseur = fournisseurRegleCalculAssuranceSociale.getFournisseurDeductionMaximale3ePilier(2011);
        assertThat(fournisseur.getDeductionMaximaleAvecLPP()).isEqualTo("6682");
        assertThat(fournisseur.getDeductionMaximaleSansLPP()).isEqualTo("33408");
        fournisseur = fournisseurRegleCalculAssuranceSociale.getFournisseurDeductionMaximale3ePilier(2012);
        assertThat(fournisseur.getDeductionMaximaleAvecLPP()).isEqualTo("6682");
        assertThat(fournisseur.getDeductionMaximaleSansLPP()).isEqualTo("33408");
    }

    @Test
    public void test2009et2010() {
        FournisseurDeductionMaxPilier3a fournisseur = fournisseurRegleCalculAssuranceSociale.getFournisseurDeductionMaximale3ePilier(2009);
        assertThat(fournisseur.getDeductionMaximaleAvecLPP()).isEqualTo("6566");
        assertThat(fournisseur.getDeductionMaximaleSansLPP()).isEqualTo("32832");
        fournisseur = fournisseurRegleCalculAssuranceSociale.getFournisseurDeductionMaximale3ePilier(2010);
        assertThat(fournisseur.getDeductionMaximaleAvecLPP()).isEqualTo("6566");
        assertThat(fournisseur.getDeductionMaximaleSansLPP()).isEqualTo("32832");
    }

    @Test
    public void test2007et2008() {
        FournisseurDeductionMaxPilier3a fournisseur = fournisseurRegleCalculAssuranceSociale.getFournisseurDeductionMaximale3ePilier(2007);
        assertThat(fournisseur.getDeductionMaximaleAvecLPP()).isEqualTo("6365");
        assertThat(fournisseur.getDeductionMaximaleSansLPP()).isEqualTo("31824");
        fournisseur = fournisseurRegleCalculAssuranceSociale.getFournisseurDeductionMaximale3ePilier(2008);
        assertThat(fournisseur.getDeductionMaximaleAvecLPP()).isEqualTo("6365");
        assertThat(fournisseur.getDeductionMaximaleSansLPP()).isEqualTo("31824");
    }

    @Test
    public void test2005et2006() {
        FournisseurDeductionMaxPilier3a fournisseur = fournisseurRegleCalculAssuranceSociale.getFournisseurDeductionMaximale3ePilier(2005);
        assertThat(fournisseur.getDeductionMaximaleAvecLPP()).isEqualTo("6192");
        assertThat(fournisseur.getDeductionMaximaleSansLPP()).isEqualTo("30960");
        fournisseur = fournisseurRegleCalculAssuranceSociale.getFournisseurDeductionMaximale3ePilier(2006);
        assertThat(fournisseur.getDeductionMaximaleAvecLPP()).isEqualTo("6192");
        assertThat(fournisseur.getDeductionMaximaleSansLPP()).isEqualTo("30960");
    }

    @Test
    public void test2003et2004() {
        FournisseurDeductionMaxPilier3a fournisseur = fournisseurRegleCalculAssuranceSociale.getFournisseurDeductionMaximale3ePilier(2003);
        assertThat(fournisseur.getDeductionMaximaleAvecLPP()).isEqualTo("6077");
        assertThat(fournisseur.getDeductionMaximaleSansLPP()).isEqualTo("30384");
        fournisseur = fournisseurRegleCalculAssuranceSociale.getFournisseurDeductionMaximale3ePilier(2004);
        assertThat(fournisseur.getDeductionMaximaleAvecLPP()).isEqualTo("6077");
        assertThat(fournisseur.getDeductionMaximaleSansLPP()).isEqualTo("30384");
    }

    @Test
    public void test2001et2002() {
        FournisseurDeductionMaxPilier3a fournisseur = fournisseurRegleCalculAssuranceSociale.getFournisseurDeductionMaximale3ePilier(2001);
        assertThat(fournisseur.getDeductionMaximaleAvecLPP()).isEqualTo("5933");
        assertThat(fournisseur.getDeductionMaximaleSansLPP()).isEqualTo("29664");
        fournisseur = fournisseurRegleCalculAssuranceSociale.getFournisseurDeductionMaximale3ePilier(2002);
        assertThat(fournisseur.getDeductionMaximaleAvecLPP()).isEqualTo("5933");
        assertThat(fournisseur.getDeductionMaximaleSansLPP()).isEqualTo("29664");
    }
}