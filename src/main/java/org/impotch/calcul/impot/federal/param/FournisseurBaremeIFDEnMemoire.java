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
package org.impotch.calcul.impot.federal.param;

import org.impotch.bareme.Bareme;
import org.impotch.util.TypeArrondi;

import java.util.Optional;


public class FournisseurBaremeIFDEnMemoire implements FournisseurBaremeIFD {

    private final IFDPostNumerando postNumerando = new IFDPostNumerando();
    private final IFDPraeNumerando praeNumerando = new IFDPraeNumerando();

    @Override
    public Optional<Bareme> getBaremeImpotRevenuPersonnePhysiquePourPersonneSeule(int annee, TypeArrondi arrondiSurChaqueTranche) {
        return postNumerando.personneSeule(annee,arrondiSurChaqueTranche);
    }


    @Override
    public Optional<Bareme> getBaremeImpotRevenuPersonnePhysiquePourFamille(int annee, TypeArrondi arrondiSurChaqueTranche) {
        return postNumerando.famille(annee,arrondiSurChaqueTranche);
    }

    @Override
    public Bareme getBaremeImpotRevenuPraeNumerandoPersonnePhysiquePourPersonneSeule(int annee, TypeArrondi arrondiSurChaqueTranche) {
        return praeNumerando.personneSeule(annee,arrondiSurChaqueTranche);
    }

    @Override
    public Bareme getBaremeImpotRevenuPraeNumerandoPersonnePhysiquePourFamille(int annee, TypeArrondi arrondiSurChaqueTranche) {
        return praeNumerando.famille(annee,arrondiSurChaqueTranche);
    }

}
