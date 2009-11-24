package ch.ge.afc.calcul.impot.cantonal.ge.pp.avant2010;


import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.ge.afc.calcul.bareme.Bareme;
import ch.ge.afc.calcul.impot.cantonal.ge.pp.FournisseurRegleImpotCantonalGE;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/beans.xml")
public class BaremeRevenu2009Test {

	@Resource(name = "fournisseurRegleImpotCantonalGE")
	private FournisseurRegleImpotCantonalGE fournisseur;

	private Bareme baremeSeul2009;	
	
	@Before
	public void setUp() throws Exception {
		baremeSeul2009 = fournisseur.getBaremeRevenu(2009);
	}

	@Test
	public void bareme() {
		assertEquals(new BigDecimal("5486.85"),baremeSeul2009.calcul(new BigDecimal(50000)));
		assertEquals(new BigDecimal("12821.20"),baremeSeul2009.calcul(new BigDecimal(100000)));
	}	
}
