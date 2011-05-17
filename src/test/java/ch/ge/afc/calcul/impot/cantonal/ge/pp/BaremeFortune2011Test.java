package ch.ge.afc.calcul.impot.cantonal.ge.pp;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.ge.afc.bareme.Bareme;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/beans.xml")
public class BaremeFortune2011Test {
	@Resource(name = "fournisseurRegleImpotCantonalGE")
	private FournisseurRegleImpotCantonalGE fournisseur;

	@Test
	public void baremeSource() {
		Bareme bareme = fournisseur.getBaremeFortune(2011);
		assertEquals("Pour 1000000 francs",new BigDecimal("2994.50"),bareme.calcul(new BigDecimal("1000000")));
		bareme = fournisseur.getBaremeFortuneSupplementaire(2011);
		assertEquals("Pour 1000000 francs",new BigDecimal("310.70"),bareme.calcul(new BigDecimal("1000000")));
		
	}

}
