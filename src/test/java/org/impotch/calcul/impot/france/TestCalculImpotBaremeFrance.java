package org.impotch.calcul.impot.france;


import java.math.BigDecimal;

import org.junit.Test;
import static org.junit.Assert.*;

import org.impotch.bareme.Bareme;
import org.impotch.calcul.impot.france.FournisseurFrance;

public class TestCalculImpotBaremeFrance {

	@Test
	public void calcul2007() {
		FournisseurFrance fournisseur = new FournisseurFrance();
		Bareme bareme = fournisseur.getBaremeRevenu(2007);
		assertEquals("Impôt 2007 pour revenu de 45000",new BigDecimal("8191.00"),bareme.calcul(new BigDecimal("45000")));
		assertEquals("Impôt 2007 pour revenu de 320611",new BigDecimal("116181.00"),bareme.calcul(new BigDecimal("320611")));
	}

	@Test
	public void calcul2008() {
		FournisseurFrance fournisseur = new FournisseurFrance();
		Bareme bareme = fournisseur.getBaremeRevenu(2008);
		assertEquals("Impôt 2008 pour revenu de 26200",new BigDecimal("2397.00"),bareme.calcul(new BigDecimal("26200")));
	}

}
