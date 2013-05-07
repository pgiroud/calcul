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
package org.impotch.calcul.assurancessociales;

import org.impotch.calcul.assurancessociales.ge.CalculCotisationsSocialesSalarieGE;

public interface FournisseurRegleCalculAssuranceSociale {

	CalculCotisationsSocialesSalarie getCalculateurCotisationsSocialesSalarie(int annee);
	
    CalculCotisationsSocialesSalarie getCalculateurCotisationsSocialesSalarieISIFD(int annee);

	CalculCotisationsSocialesSalarieGE getCalculateurCotisationsSocialesSalarieGE(int annee);
	
    CalculCotisationsSocialesSalarieGE getCalculateurCotisationsSocialesSalarieGEIFD(int annee);

	CalculCotisationAvsAiApg getCalculateurCotisationAvsAiApgIndependant(int annee);
	
	CalculExtremaRentesAVS getCalculateurExtremaRenteAVS(int annee);

    FournisseurMontantsLimitesPrevoyanceProfessionnelle getFournisseurMontantsLimitesPrevoyanceProfessionnelle(int annee);

    FournisseurDeductionMaxPilier3a getFournisseurDeductionMaximale3ePilier(int annee);
}
