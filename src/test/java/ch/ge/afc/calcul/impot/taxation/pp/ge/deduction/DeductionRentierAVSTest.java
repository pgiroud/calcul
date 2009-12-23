package ch.ge.afc.calcul.impot.taxation.pp.ge.deduction;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import ch.ge.afc.bareme.BaremeConstantParTranche;
import ch.ge.afc.calcul.impot.taxation.pp.ge.deduction.DeductionRentierAVS;


public class DeductionRentierAVSTest {

	private DeductionRentierAVS regle;
	
	@Before
	public void init() {
		BaremeConstantParTranche bareme = new BaremeConstantParTranche();
		bareme.ajouterTranche(50000, 10000);
		bareme.ajouterTranche(56700,  8000);
		bareme.ajouterTranche(64000,  6000);
		bareme.ajouterTranche(71500,  4000);
		bareme.ajouterTranche(80000,  2000);
		bareme.ajouterDerniereTranche(0);
		
		regle = new DeductionRentierAVS(2009,bareme, new BigDecimal("1.15"));
	}
	
	private void deduction2Rentier(int revenu, int deduction) {
		assertEquals("Double rentier " + revenu,new BigDecimal(deduction + ".00"),regle.getMontantDeduction(new BigDecimal(revenu), false, true));
	}
	
	@Test
	public void deductionDoubleRentier() {
		deduction2Rentier(10,11500);
		deduction2Rentier(1000,11500);
		deduction2Rentier(57500,11500);
		deduction2Rentier(57501,9200);
	}
}
