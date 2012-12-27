/**
 * This file is part of CalculImpotCH.
 *
 * CalculImpotCH is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * CalculImpotCH is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CalculImpotCH.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.impotch.calcul.eco.indice.ge;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import org.impotch.calcul.eco.indice.ge.BaseIndiceGenevoisPrixConsommation;
import org.impotch.calcul.eco.indice.ge.FournisseurReevalueIndicePrixConsommation;

public class FournisseurReevalueIndicePrixConsommationTest {

	private FournisseurReevalueIndicePrixConsommation fournisseur;
	
	@Before
	public void init() throws Exception {
		fournisseur = new FournisseurReevalueIndicePrixConsommation();
	}

	@Test
	public void fournir() {
		Assert.assertEquals("Indice base mai 1993 pour juillet 1998",
				new BigDecimal("105.3"), fournisseur.fournir(
						BaseIndiceGenevoisPrixConsommation.MAI_1993, 1998, 6));
//		Assert.assertEquals("Indice base mai 1993 pour octobre 2000",
//				new BigDecimal("108.3"), fournisseur.fournir(
//						BaseIndiceGenevoisPrixConsommation.MAI_1993, 2000, 9));
		Assert.assertEquals("Indice base mai 1993 pour mai 2001",
				new BigDecimal("109.3"), fournisseur.fournir(
						BaseIndiceGenevoisPrixConsommation.MAI_1993, 2001, 4));
		Assert.assertEquals("Indice base mai 1993 pour dec 2003",
				new BigDecimal("111.2"), fournisseur.fournir(
						BaseIndiceGenevoisPrixConsommation.MAI_1993, 2003, 11));
		Assert.assertEquals("Indice base décembre 2005 pour janvier 2009",
				new BigDecimal("102.2"), fournisseur.fournir(
						BaseIndiceGenevoisPrixConsommation.DECEMBRE_2005, 2009,
						0));
	}

	@Test
	public void fournirListe() {
		List<BigDecimal> liste = fournisseur.fournir(
				BaseIndiceGenevoisPrixConsommation.MAI_1993, 2003, 8, 2004, 7);
		Assert.assertEquals("Base mai 1993 : Longueur de la liste", 12, liste
				.size());
		Assert.assertEquals("Base mai 1993 : Sept 2003",
				new BigDecimal("110.4"), liste.get(0));
		Assert.assertEquals("Base mai 1993 : Dec 2003",
				new BigDecimal("111.2"), liste.get(3));
		Assert.assertEquals("Base mai 1993 : Avril 2004", new BigDecimal(
				"112.4"), liste.get(7));
		Assert.assertEquals("Base mai 1993 : Aout 2004",
				new BigDecimal("112.1"), liste.get(11));

		liste = fournisseur.fournir(
				BaseIndiceGenevoisPrixConsommation.MAI_1993, 2000, 0, 2000, 11);
		Assert.assertEquals("Base mai 1993 : Longueur de la liste", 12, liste
				.size());
		Assert.assertEquals("Base mai 1993 : Jan 2000",
				new BigDecimal("107.0"), liste.get(0));
		Assert.assertEquals("Base mai 1993 : Avr 2000",
				new BigDecimal("107.4"), liste.get(3));
		Assert.assertEquals("Base mai 1993 : Sep 2000",
				new BigDecimal("108.5"), liste.get(8));
		Assert.assertEquals("Base mai 1993 : Dec 2000",
				new BigDecimal("108.4"), liste.get(11));

		liste = fournisseur.fournir(
				BaseIndiceGenevoisPrixConsommation.MAI_1993, 2000, 8, 2001, 7);
		Assert.assertEquals("Base mai 1993 : Longueur de la liste", 12, liste
				.size());
		Assert.assertEquals("Base mai 1993 : Sept 2000",
				new BigDecimal("108.5"), liste.get(0));
//		Assert.assertEquals("Base mai 1993 : Oct 2000",
//				new BigDecimal("108.3"), liste.get(1));
		Assert.assertEquals("Base mai 1993 : Nov 2000",
				new BigDecimal("108.5"), liste.get(2));
		Assert.assertEquals("Base mai 1993 : Dec 2000",
				new BigDecimal("108.4"), liste.get(3));
//		Assert.assertEquals("Base mai 1993 : Jan 2001",
//				new BigDecimal("108.3"), liste.get(4));
		Assert.assertEquals("Base mai 1993 : Fev 2001",
				new BigDecimal("108.2"), liste.get(5));
//		Assert.assertEquals("Base mai 1993 : Mar 2001",
//				new BigDecimal("108.5"), liste.get(6));
//		Assert.assertEquals("Base mai 1993 : Avr 2001",
//				new BigDecimal("108.6"), liste.get(7));
		Assert.assertEquals("Base mai 1993 : Mai 2001",
				new BigDecimal("109.3"), liste.get(8));
		Assert.assertEquals("Base mai 1993 : Jun 2001",
				new BigDecimal("109.5"), liste.get(9));
		Assert.assertEquals("Base mai 1993 : Jul 2001",
				new BigDecimal("109.4"), liste.get(10));
		Assert.assertEquals("Base mai 1993 : Aou 2001",
				new BigDecimal("108.9"), liste.get(11));

	}

	@Test
	public void moyenne() {
		Assert.assertEquals("Moyenne de sept 2003 à aout 2004", new BigDecimal(
				"111.5"), fournisseur.fournirMoyenne(
				BaseIndiceGenevoisPrixConsommation.MAI_1993, 2003, 8, 2004, 7));
		Assert
				.assertEquals("Moyenne de jan 2000 à dec. 2000",
						new BigDecimal("107.8"), fournisseur.fournirMoyenne(
								BaseIndiceGenevoisPrixConsommation.MAI_1993,
								2000, 0, 2000, 11));
		Assert.assertEquals("Moyenne de jan 2007 à dec 2007", new BigDecimal(
				"100.9"), fournisseur.fournirMoyenne(
				BaseIndiceGenevoisPrixConsommation.DECEMBRE_2005, 2007, 0,
				2007, 11));
	}

	@Test
	public void moyennePourBaremePP() {
		Assert.assertEquals("Moyenne de sept 1998 à aout 1999", new BigDecimal(
				"105.8"), fournisseur.fournirMoyenne(
				BaseIndiceGenevoisPrixConsommation.MAI_1993, 1998, 8, 1999, 7));
		Assert.assertEquals("Moyenne de sept 1999 à aout 2000", new BigDecimal(
				"107.2"), fournisseur.fournirMoyenne(
				BaseIndiceGenevoisPrixConsommation.MAI_1993, 1999, 8, 2000, 7));
//		Assert.assertEquals("Moyenne de sept 2000 à aout 2001", new BigDecimal(
//				"108.7"), fournisseur.fournirMoyenne(
//				BaseIndiceGenevoisPrixConsommation.MAI_1993, 2000, 8, 2001, 7));
		Assert.assertEquals("Moyenne de sept 2001 à aout 2002", new BigDecimal(
				"109.4"), fournisseur.fournirMoyenne(
				BaseIndiceGenevoisPrixConsommation.MAI_1993, 2001, 8, 2002, 7));
		Assert.assertEquals("Moyenne de sept 2002 à aout 2003", new BigDecimal(
				"110.5"), fournisseur.fournirMoyenne(
				BaseIndiceGenevoisPrixConsommation.MAI_1993, 2002, 8, 2003, 7));
		Assert.assertEquals("Moyenne de sept 2003 à aout 2004", new BigDecimal(
				"111.5"), fournisseur.fournirMoyenne(
				BaseIndiceGenevoisPrixConsommation.MAI_1993, 2003, 8, 2004, 7));
		Assert.assertEquals("Moyenne de sept 2004 à aout 2005", new BigDecimal(
				"113.2"), fournisseur.fournirMoyenne(
				BaseIndiceGenevoisPrixConsommation.MAI_1993, 2004, 8, 2005, 7));
		Assert.assertEquals("Moyenne de sept 2005 à aout 2006", new BigDecimal(
				"114.6"), fournisseur.fournirMoyenne(
				BaseIndiceGenevoisPrixConsommation.MAI_1993, 2005, 8, 2006, 7));
		Assert.assertEquals("Moyenne de sept 2006 à aout 2007", new BigDecimal(
				"114.9"), fournisseur.fournirMoyenne(
				BaseIndiceGenevoisPrixConsommation.MAI_1993, 2006, 8, 2007, 7));
		Assert.assertEquals("Moyenne de sept 2007 à aout 2008", new BigDecimal(
				"117.5"), fournisseur.fournirMoyenne(
				BaseIndiceGenevoisPrixConsommation.MAI_1993, 2007, 8, 2008, 7));
	}
}
