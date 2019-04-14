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
package org.impotch.calcul.impot.federal.dao;

import org.impotch.bareme.Bareme;
import org.impotch.bareme.ConstructeurBaremeTauxMarginal;
import org.impotch.util.TypeArrondi;

public class FournisseurBaremeIFDEnMemoire implements FournisseurBaremeIFD {

    private IFDPostNumerando postNumerando = new IFDPostNumerando();
    private IFDPraeNumerando praeNumerando = new IFDPraeNumerando();

    @Override
    public Bareme getBaremeImpotRevenuPersonnePhysiquePourPersonneSeule(int annee) {
        return postNumerando.personneSeule(annee);
    }


    @Override
    public Bareme getBaremeImpotRevenuPersonnePhysiquePourFamille(int annee) {
        return postNumerando.famille(annee);
    }

    @Override
    public Bareme getBaremeImpotRevenuPraeNumerandoPersonnePhysiquePourPersonneSeule(int annee) {
        return praeNumerando.personneSeule(annee);
    }

    @Override
    public Bareme getBaremeImpotRevenuPraeNumerandoPersonnePhysiquePourFamille(int annee) {
        return praeNumerando.famille(annee);
    }

    /**
     * Barèmes définis dans l'ordonnance sur l'impôt à la source (appendice 3)
     * @param annee l'année durant laquelle est perçue la prestation
     * @return le barème permettant de déterminer l'impôt du.
     */
    @Override
    public Bareme getBaremeImpotSourcePrestationCapital(int annee) {
        ConstructeurBaremeISPrestationCapital cons = new ConstructeurBaremeISPrestationCapital();
        if (2020 <= annee) throw new RuntimeException("Barème pas encore défini pour l'année " + annee);
        if (2012 <= annee && 2019 >= annee) {
            cons
                    .surLesPremier(25000).taux("0 %")
                    .surLesProchains(25000).taux("0.20 %")
                    .surLesProchains(25000).taux("0.55 %")
                    .surLesProchains(25000).taux("0.90 %")
                    .surLesProchains(25000).taux("1.25 %")
                    .surLesProchains(25000).taux("2.00 %")
                    .etFinalementTaux("2.60 %")
                    .tauxEffectifMax("2.30 %");
        } else if (2011 == annee) {
            cons
                    .surLesPremier(25000).taux("0 %")
                    .surLesProchains(25000).taux("0.20 %")
                    .surLesProchains(25000).taux("0.55 %")
                    .surLesProchains(25000).taux("0.90 %")
                    .surLesProchains(25000).taux("1.30 %")
                    .surLesProchains(25000).taux("2.05 %")
                    .etFinalementTaux("2.60 %")
                    .tauxEffectifMax("2.30 %");
        } else if (2007 <= annee  && annee <= 2010) {
            cons
                    .surLesPremier(25000).taux("0 %")
                    .surLesProchains(25000).taux("0.25 %")
                    .surLesProchains(25000).taux("0.65 %")
                    .surLesProchains(25000).taux("1.10 %")
                    .surLesProchains(25000).taux("1.70 %")
                    .etFinalementTaux("2.60 %")
                    .tauxEffectifMax("2.30 %");
        } else if (2002 <= annee && annee <= 2006) {
            cons
                    .surLesPremier(25000).taux("0 %")
                    .surLesProchains(25000).taux("0.25 %")
                    .surLesProchains(25000).taux("0.75 %")
                    .surLesProchains(25000).taux("1.20 %")
                    .surLesProchains(25000).taux("2.15 %")
                    .etFinalementTaux("2.60 %")
                    .tauxEffectifMax("2.30 %");
        } else {
            throw new RuntimeException("Le barème pas défini pour l'année " + annee);
        }
        return cons.construire();
    }

}
