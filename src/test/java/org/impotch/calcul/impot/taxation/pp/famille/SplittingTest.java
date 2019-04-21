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
package org.impotch.calcul.impot.taxation.pp.famille;


import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import org.impotch.bareme.ConstructeurBaremeTauxMarginal;
import org.impotch.calcul.impot.taxation.pp.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.impotch.bareme.BaremeTauxMarginalConstantParTranche;
import org.impotch.util.TypeArrondi;
import org.impotch.calcul.impot.taxation.pp.famille.Splitting;

public class SplittingTest {

	private StrategieProductionImpotFamille splitting;
	
	@Before
	public void setUp() throws Exception {
		ConstructeurBaremeTauxMarginal cons = new ConstructeurBaremeTauxMarginal();
		cons.typeArrondiSurChaqueTranche(TypeArrondi.CINQ_CTS)
			.premiereTranche(1000, "1 %")
			.tranche(1000,2000, "2 %")
			.tranche(2000,3000, "3 %")
			.tranche(3000,4000, "4 %")
			.derniereTranche(4000,"5 %");
		splitting = new Splitting(cons.construire(),"50 %");
	}

	
	@Test
	public void produireImpot() {
		BigDecimal impot = splitting.produireImpotAnnuel(getFamille(),new BigDecimal("2000"), new BigDecimal("2000"));
		impot = TypeArrondi.CINQ_CTS.arrondirMontant(impot);
		assertEquals("Transfo Impot",new BigDecimal("20.00"),impot);
	}
	
	private SituationFamiliale getFamille() {
		return new SituationFamiliale() {

            @Override
            public Contribuable getContribuable() {
                return new Contribuable() {
                };
            }

            @Override
            public Optional<Contribuable> getConjoint() {
                return Optional.of(new Contribuable() {
                });
            }

            @Override
			public Set<EnfantACharge> getEnfants() {return Collections.emptySet();}

			@Override
			public Set<PersonneACharge> getPersonnesNecessiteuses() {return Collections.emptySet();}

		};
	}
}
