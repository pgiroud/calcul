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
package org.impotch.calcul.assurancessociales.ge.param;

import org.impotch.calcul.assurancessociales.param.FournisseurParametrageSuisseAnnuel;
import org.impotch.calcul.assurancessociales.param.ParametrageSuisseAnnuel;

import java.util.Optional;

class FournisseurParametrageGenevoisAnnuelEnMemoire implements FournisseurParametrageGenevoisAnnuel {

    FournisseurParametrageSuisseAnnuel fournisseurParametrageSuisseEnMemoire = FournisseurParametrageSuisseAnnuel.enMemoire();
    FournisseurParametrageCotisationAssuranceMaternite fournisseurParametrageCotisationAssuranceMaternite = FournisseurParametrageCotisationAssuranceMaternite.enMemoire();

    @Override
    public Optional<ParametrageGenevoisAnnuel> parametrage(int annee) {
        return construireParametrageAnnuel(annee);
    }

    private Optional<ParametrageGenevoisAnnuel> construireParametrageAnnuel(int annee) {
        if (fournisseurParametrageSuisseEnMemoire.parametrage(annee).isPresent()
        && fournisseurParametrageCotisationAssuranceMaternite.parametrage(annee).isPresent()) {
            ParametrageSuisseAnnuel parametrageSuisse =  fournisseurParametrageSuisseEnMemoire.parametrage(annee).get();
            ParametrageCotisationAssuranceMaternite parametrageCotisationAssuranceMaternite = fournisseurParametrageCotisationAssuranceMaternite.parametrage(annee).get();
            return Optional.of(new ParametrageGenevoisAnnuel(parametrageSuisse,parametrageCotisationAssuranceMaternite));
        }
        return Optional.empty();
    }

}
