package org.impotch.calcul.impot.cantonal.ge.pp;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.impotch.bareme.Bareme;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/beans.xml")
public class BaremeRevenu2011Test {
	@Resource(name = "fournisseurRegleImpotCantonalGE")
	private FournisseurRegleImpotCantonalGE fournisseur;

	@Test
	public void baremeSource() {
		Bareme bareme = fournisseur.getBaremeRevenu(2011);
		assertEquals("Pour 17753 francs",new BigDecimal("9.90"),bareme.calcul(new BigDecimal("17753")));
        assertEquals("Pour 17700 francs",new BigDecimal("5.70"),bareme.calcul(new BigDecimal("17700")));
        assertEquals("Pour 17629 francs",new BigDecimal("0.00"),bareme.calcul(new BigDecimal("17629")));
        assertEquals("Pour 17630 francs",new BigDecimal("0.10"),bareme.calcul(new BigDecimal("17630")));
	}
	
	@Test
	public void borneBareme() {
		Bareme bareme = fournisseur.getBaremeRevenu(2011);
		assertEquals("Pour 17629 francs",new BigDecimal("0.00"),bareme.calcul(new BigDecimal("17527")));
		assertEquals("Pour 21240 francs",new BigDecimal("288.90"),bareme.calcul(new BigDecimal("21240")));
		assertEquals("Pour 23364 francs",new BigDecimal("480.05"),bareme.calcul(new BigDecimal("23364")));
		assertEquals("Pour 25488 francs",new BigDecimal("692.45"),bareme.calcul(new BigDecimal("25488")));
		assertEquals("Pour 27612 francs",new BigDecimal("926.10"),bareme.calcul(new BigDecimal("27612")));
		assertEquals("Pour 2003887 francs",new BigDecimal("366801.85"),bareme.calcul(new BigDecimal("2003887")));
	}
	

}
