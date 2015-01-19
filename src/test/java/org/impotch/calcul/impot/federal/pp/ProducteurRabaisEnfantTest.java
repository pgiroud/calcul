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
package org.impotch.calcul.impot.federal.pp;

import org.impotch.calcul.impot.taxation.pp.ProducteurImpot;
import org.impotch.calcul.impot.taxation.pp.ProducteurImpotTst;
import org.impotch.calcul.impot.taxation.pp.RecepteurUniqueImpot;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.impotch.calcul.impot.federal.FournisseurRegleImpotFederal;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import javax.annotation.Resource;
import java.math.BigDecimal;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

/**
 * Created by IntelliJ IDEA.
 * User: patrick
 * Date: 17 avr. 2010
 * Time: 21:19:55
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/beansCH_IFD.xml")
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
public class ProducteurRabaisEnfantTest extends ProducteurImpotTst {

    @Resource
    private FournisseurRegleImpotFederal constructeur;

    @Test
    public void pasRabaisAvant2011() {
        RecepteurUniqueImpot recepteur = new RecepteurUniqueImpot("RI");
        ProducteurImpot prod;
        prod= constructeur.getProducteurImpotsFederauxPP(2010);
        prod.produireImpot(this.creerSituationFamilleAvecEnfant(8),this.creerAssiettes(2011,100000),recepteur);
        assertNull("Montant rabais 2010 pour 1 enfant doit être null", recepteur.getValeur());
    }


    

    @Test
    public void unEnfant() {
        RecepteurUniqueImpot recepteur = new RecepteurUniqueImpot("RI");
        ProducteurImpot prod;
        prod= constructeur.getProducteurImpotsFederauxPP(2011);
        prod.produireImpot(this.creerSituationFamilleAvecEnfant(8),this.creerAssiettes(2011,100000),recepteur);
        assertEquals("Montant rabais 2011 pour 1 enfant", new BigDecimal("-250.00"),recepteur.getValeur().getMontant());
    }
}
