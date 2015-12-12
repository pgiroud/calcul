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
		BaremeTauxMarginalConstantParTranche.Constructeur constructeur = new BaremeTauxMarginalConstantParTranche.Constructeur();
		constructeur.typeArrondiSurChaqueTranche(TypeArrondi.CINQ_CTS);
		constructeur.tranche(1000, "1 %");
		constructeur.tranche(2000, "2 %");
		constructeur.tranche(3000, "3 %");
		constructeur.tranche(4000, "4 %");
		constructeur.derniereTranche("5 %");
		splitting = new Splitting(constructeur.construire(),"50 %");
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
