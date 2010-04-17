package ch.ge.afc.calcul.impot.federal;

import ch.ge.afc.calcul.impot.taxation.pp.ProducteurImpot;
import ch.ge.afc.calcul.impot.taxation.pp.ProducteurImpotTst;
import ch.ge.afc.calcul.impot.taxation.pp.RecepteurUniqueImpot;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
@ContextConfiguration(locations = "/beans.xml")
public class ProducteurRabaisEnfantTest extends ProducteurImpotTst {

    @Resource(name = "fournisseurRegleImpotFederal")
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
