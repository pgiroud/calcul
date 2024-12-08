/*
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
package org.impotch.calcul.impot.cantonal.ge.pp.avant2010;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;


import org.impotch.calcul.impot.FournisseurAssietteCommunale;
import org.impotch.calcul.impot.PeriodeFiscale;
import org.impotch.calcul.impot.taxation.pp.*;
import org.impotch.calcul.lieu.FournisseurLieu;
import org.impotch.calcul.lieu.ICanton;
import org.impotch.calcul.lieu.ICommuneSuisse;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class ProducteurImpotGEAvant2010 extends ProducteurImpotTst {

	private FournisseurLieu fournisseurLieu = new FournisseurLieu();

	@Override
	protected SituationFamiliale creerSituationCelibataireSansEnfant() {
		return ConstructeurSituationFamilialeGE.unePersonneSeule().fournir();
	}

	protected SituationFamiliale creerSituationFamilleAvecEnfant(final int agePremierEnfant, final int... ageEnfantsSuivants) {
		return ConstructeurSituationFamilialeGE.unCouple()
				.enfant().age(agePremierEnfant, ageEnfantsSuivants).fournir();
	}
	
	protected FournisseurAssiettePeriodique creerAssiettesAvecRabais(final int periodeFiscale, final int montantImposable, final int montantDeterminantRabais) {
		return new ConstructeurAssiettePeriodiqueGEAvant2010(PeriodeFiscale.annee(periodeFiscale))
				.imposableEtDeterminant(montantImposable)
				.rabaisDeterminant(montantDeterminantRabais)
				.uniqueCommune(fournisseurLieu.getCommuneGeneve())
				.fournir();
	}
	

}
