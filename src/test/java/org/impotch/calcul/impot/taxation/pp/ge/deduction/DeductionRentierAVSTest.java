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
package org.impotch.calcul.impot.taxation.pp.ge.deduction;

import java.math.BigDecimal;

import org.impotch.bareme.BaremeParTranche;
import org.impotch.bareme.ConstructeurBareme;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.impotch.bareme.BaremeConstantParTranche;
import org.impotch.calcul.impot.taxation.pp.ge.deduction.DeductionRentierAVS;


public class DeductionRentierAVSTest {

	private DeductionRentierAVS regle;
	
	@Before
	public void init() {
		ConstructeurBareme cons = new ConstructeurBareme()
				.premiereTranche(50000, 10000)
				.tranche(50000,56700,  8000)
				.tranche(56700,64000,  6000)
				.tranche(64000,71500,  4000)
				.tranche(71500,80000,  2000)
				.derniereTranche(80000,0);
		BaremeParTranche bareme = cons.construireBaremeParTranche();
		regle = new DeductionRentierAVS(2009,bareme, new BigDecimal("1.15"));
	}
	
	private void deduction2Rentier(int revenu, int deduction) {
		assertEquals("Double rentier " + revenu,new BigDecimal(deduction),regle.getMontantDeduction(new BigDecimal(revenu), false, true));
	}
	
	@Test
	public void deductionDoubleRentier() {
		deduction2Rentier(10,11500);
		deduction2Rentier(1000,11500);
		deduction2Rentier(57500,11500);
		deduction2Rentier(57501,9200);
	}
}
