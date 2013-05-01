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
package org.impotch.calcul.impot.cantonal.ge.pp.indexateur;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.impotch.calcul.impot.cantonal.ge.pp.FournisseurRegleImpotCantonalGE;
import org.impotch.util.TypeArrondi;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/beans.xml")
public class IndexateurQuadriennalTest {

	@Resource(name = "fournisseurRegleImpotCantonalGE")
	private FournisseurRegleImpotCantonalGE fournisseur;

	@Test
	public void indexationMontantRabaisImpot() {
		IndexateurMontant indexateur = fournisseur.getIndexateurBaseMai1993(2001);
		// Base
		assertEquals("Base montant époux ",new BigDecimal(13750),indexateur.indexerMontant(new BigDecimal(13750), 2001));
		assertEquals("2005 montant époux ",new BigDecimal("14288"),indexateur.indexerMontant(new BigDecimal(13750), 2005));
		assertEquals("2009 montant époux ",new BigDecimal("15057"),indexateur.indexerMontant(new BigDecimal(13750), 2009));

		assertEquals("Base déduc. double activité ",new BigDecimal(3500),indexateur.indexerMontant(new BigDecimal(3500), 2002));
		assertEquals("2005 déduc. double activité ",new BigDecimal("3640"),indexateur.indexerMontant(new BigDecimal(3500), 2006, TypeArrondi.DIX_FRANC));
//		assertEquals("2009 déduc. double activité ",new BigDecimal("3830.00"),indexateur.indexerMontant(new BigDecimal(3500), 2009, TypeArrondi.DIX_FRANC));

		assertEquals("Base plafond faible revenu ",new BigDecimal(50000),indexateur.indexerMontant(new BigDecimal(50000), 2003));
		assertEquals("2005 plafond faible revenu ",new BigDecimal("52000"),indexateur.indexerMontant(new BigDecimal(50000), 2007, TypeArrondi.MILLE_FRANC_SUP));
		assertEquals("2009 plafond faible revenu ",new BigDecimal("55000"),indexateur.indexerMontant(new BigDecimal(50000), 2010, TypeArrondi.MILLE_FRANC_SUP));

		assertEquals("Base déduc. double activité pour faible revenu",new BigDecimal(5000),indexateur.indexerMontant(new BigDecimal(5000), 2004));
		assertEquals("2005 déduc. double activité pour faible revenu",new BigDecimal("5200"),indexateur.indexerMontant(new BigDecimal(5000), 2008, TypeArrondi.CENT_FRANC_SUP));
		assertEquals("2009 déduc. double activité pour faible revenu",new BigDecimal("5500"),indexateur.indexerMontant(new BigDecimal(5000), 2009, TypeArrondi.CENT_FRANC_SUP));

		assertEquals("Base montant demi charge", new BigDecimal(3250), indexateur.indexerMontant(new BigDecimal(3250), 2001));
		assertEquals("2005 montant demi charge", new BigDecimal("3377"), indexateur.indexerMontant(new BigDecimal(3250), 2005));
		assertEquals("2009 montant demi charge", new BigDecimal("3559"), indexateur.indexerMontant(new BigDecimal(3250), 2009));

		assertEquals("Base demi montant frais garde", new BigDecimal(1250), indexateur.indexerMontant(new BigDecimal(1250), 2003));
		assertEquals("2005 demi montant frais garde", new BigDecimal("1299"), indexateur.indexerMontant(new BigDecimal(1250), 2007));
		assertEquals("2009 demi montant frais garde", new BigDecimal("1369"), indexateur.indexerMontant(new BigDecimal(1250), 2009));
		
	}
}
