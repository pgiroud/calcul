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
package ch.ge.afc.calcul.impot.taxation.pp.famille;


import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import ch.ge.afc.calcul.bareme.BaremeTauxMarginalConstantParTranche;
import ch.ge.afc.calcul.impot.taxation.pp.EnfantACharge;
import ch.ge.afc.calcul.impot.taxation.pp.PersonneACharge;
import ch.ge.afc.calcul.impot.taxation.pp.SituationFamiliale;
import ch.ge.afc.calcul.impot.taxation.pp.StrategieProductionImpotFamille;
import ch.ge.afc.util.TypeArrondi;

public class SplittingTest {

	private StrategieProductionImpotFamille splitting;
	
	@Before
	public void setUp() throws Exception {
		BaremeTauxMarginalConstantParTranche.Constructeur constructeur = new BaremeTauxMarginalConstantParTranche.Constructeur();
		constructeur.typeArrondi(TypeArrondi.CINQ_CTS);
		constructeur.tranche(1000, "1 %");
		constructeur.tranche(2000, "2 %");
		constructeur.tranche(3000, "3 %");
		constructeur.tranche(4000, "4 %");
		constructeur.derniereTranche("5 %");
		splitting = new Splitting(constructeur.construire(),"50 %");
	}

	private boolean equalsSansDecimale(BigDecimal montantAttendu, BigDecimal montantCalcule) {
		if (null == montantAttendu) return null == montantCalcule;
		if (null == montantCalcule) return false;
		return 0 == montantAttendu.compareTo(montantCalcule);
	}
	
	
	@Test
	public void testTransformeDeterminant() {
		assertTrue("Montant déterminant",equalsSansDecimale(new BigDecimal("1000"), splitting.transformeDeterminant(getFamille(),new BigDecimal("2000"))));
	}

	@Test
	public void testTransformeImpot() {
		BigDecimal impot = new BigDecimal(1000);
		assertTrue("Transfo Impot",equalsSansDecimale(impot,splitting.transformeImpotDeterminant(getFamille(),impot)));
	}
	
	private SituationFamiliale getFamille() {
		return new SituationFamiliale() {

			@Override
			public Set<EnfantACharge> getEnfants() {return Collections.emptySet();}

			@Override
			public Set<PersonneACharge> getPersonnesNecessiteuses() {return Collections.emptySet();}

			@Override
			public boolean isCouple() {return true;}
			
		};
	}
}
