package ch.ge.afc.calcul.assurancessociales;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;


public class CalculateurCotisationACTest {
	
	private boolean compare(String montantAttendu, BigDecimal montantCalcule) {
		return 0 == new BigDecimal(montantAttendu).compareTo(montantCalcule);
	}
	
	@Test
	public void calculCotisationAC2010() {
		CalculateurCotisationAC calculateur = new CalculateurCotisationAC(2010,126000,"2 %");
		assertTrue("Comparaison pour 10 francs de montant déterminant", compare("0.1", calculateur.calculPartSalarieeCotisationAssuranceChomage(new BigDecimal("10"))));
		assertTrue("Comparaison pour 120000 francs de montant déterminant", compare("1200", calculateur.calculPartSalarieeCotisationAssuranceChomage(new BigDecimal("120000"))));
		assertTrue("Comparaison pour 126000 francs de montant déterminant", compare("1260", calculateur.calculPartSalarieeCotisationAssuranceChomage(new BigDecimal("126000"))));
		assertTrue("Comparaison pour 130000 francs de montant déterminant", compare("1260", calculateur.calculPartSalarieeCotisationAssuranceChomage(new BigDecimal("130000"))));
		assertTrue("Comparaison pour 500000 francs de montant déterminant", compare("1260", calculateur.calculPartSalarieeCotisationAssuranceChomage(new BigDecimal("500000"))));
	}
	
	@Test
	public void calculCotisationAC2011() {
		CalculateurCotisationAC calculateur = new CalculateurCotisationAC(2011,126000,"2.5","2.2 %","1 %");
		assertTrue("Comparaison pour 10 francs de montant déterminant", compare("0.1", calculateur.calculPartSalarieeCotisationAssuranceChomage(new BigDecimal("10"))));
		assertTrue("Comparaison pour 120000 francs de montant déterminant", compare("1320", calculateur.calculPartSalarieeCotisationAssuranceChomage(new BigDecimal("120000"))));
		assertTrue("Comparaison pour 126000 francs de montant déterminant", compare("1386", calculateur.calculPartSalarieeCotisationAssuranceChomage(new BigDecimal("126000"))));
		assertTrue("Comparaison pour 130000 francs de montant déterminant", compare("1406", calculateur.calculPartSalarieeCotisationAssuranceChomage(new BigDecimal("130000"))));
		assertTrue("Comparaison pour 315000 francs de montant déterminant", compare("2331", calculateur.calculPartSalarieeCotisationAssuranceChomage(new BigDecimal("315000"))));
		assertTrue("Comparaison pour 500000 francs de montant déterminant", compare("2331", calculateur.calculPartSalarieeCotisationAssuranceChomage(new BigDecimal("500000"))));
		
	}
}
