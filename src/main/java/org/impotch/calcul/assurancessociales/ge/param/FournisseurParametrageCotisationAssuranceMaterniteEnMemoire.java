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
package org.impotch.calcul.assurancessociales.ge.param;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;
import static org.impotch.calcul.assurancessociales.ge.param.ParametrageEnMemoireCotisationAssuranceMaternite.unConstructeur;

public class FournisseurParametrageCotisationAssuranceMaterniteEnMemoire implements FournisseurParametrageCotisationAssuranceMaternite {

    final Logger logger = LoggerFactory.getLogger(FournisseurParametrageCotisationAssuranceMaterniteEnMemoire.class);

    @Override
    public Optional<ParametrageCotisationAssuranceMaternite> parametrage(int annee) {
        return construireParametrageAnnuel(annee);
    }

    private Optional<ParametrageCotisationAssuranceMaternite> construireParametrageAnnuel(int annee) {
        String methodeName = "construireParametrage" + annee;
        try {
            Method methodeAnnuelle = FournisseurParametrageCotisationAssuranceMaterniteEnMemoire.class.getDeclaredMethod(methodeName);
            return Optional.of((ParametrageCotisationAssuranceMaternite)methodeAnnuelle.invoke(this));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException nsme) {
            logger.error("Impossible d’invoquer la méthode " + methodeName + " de la classe " + this.getClass().getCanonicalName());
            logger.error("Il s'agit certainement d’un manque dans le paramétrage pour l’année " + annee);
            return Optional.empty();
        }
    }

    private ParametrageCotisationAssuranceMaternite construireParametrage2025() {
        return unConstructeur(2025)
                .tauxCotisationAssuranceMaternite("0.032 %")
                .cons();
    }

    private ParametrageCotisationAssuranceMaternite construireParametrage2024() {
        return unConstructeur(2024)
                .tauxCotisationAssuranceMaternite("0.038 %")
                .cons();
    }

    private ParametrageCotisationAssuranceMaternite construireParametrage2023() {
        return unConstructeur(2023)
                .tauxCotisationAssuranceMaternite("0.041 %")
                .cons();
    }

    private ParametrageCotisationAssuranceMaternite construireParametrage2022() {
        return unConstructeur(2022)
                .tauxCotisationAssuranceMaternite("0.043 %")
                .cons();
    }

    private ParametrageCotisationAssuranceMaternite construireParametrage2021() {
        return unConstructeur(2021)
                .tauxCotisationAssuranceMaternite("0.043 %")
                .cons();
    }

    private ParametrageCotisationAssuranceMaternite construireParametrage2020() {
        return unConstructeur(2020)
                .tauxCotisationAssuranceMaternite("0.046 %")
                .cons();
    }

    private ParametrageCotisationAssuranceMaternite construireParametrage2019() {
        return unConstructeur(2019)
                .tauxCotisationAssuranceMaternite("0.046 %")
                .cons();
    }

    private ParametrageCotisationAssuranceMaternite construireParametrage2018() {
        return unConstructeur(2018)
                .tauxCotisationAssuranceMaternite("0.046 %")
                .cons();
    }
    private ParametrageCotisationAssuranceMaternite construireParametrage2017() {
        return unConstructeur(2017)
                .tauxCotisationAssuranceMaternite("0.041 %")
                .cons();
    }

    private ParametrageCotisationAssuranceMaternite construireParametrage2016() {
        return unConstructeur(2016)
                .tauxCotisationAssuranceMaternite("0.041 %")
                .cons();
    }

    private ParametrageCotisationAssuranceMaternite construireParametrage2015() {
        return unConstructeur(2015)
                .tauxCotisationAssuranceMaternite("0.041 %")
                .cons();
    }


    private ParametrageCotisationAssuranceMaternite construireParametrage2014() {
        return unConstructeur(2014)
                .tauxCotisationAssuranceMaternite("0.041 %")
                .cons();
    }
    private ParametrageCotisationAssuranceMaternite construireParametrage2013() {
        return unConstructeur(2013)
                .tauxCotisationAssuranceMaternite("0.042 %")
                .cons();
    }

    private ParametrageCotisationAssuranceMaternite construireParametrage2012() {
        return unConstructeur(2012)
                .tauxCotisationAssuranceMaternite("0.045 %")
                .cons();
    }

    private ParametrageCotisationAssuranceMaternite construireParametrage2011() {
        return unConstructeur(2011)
                .tauxCotisationAssuranceMaternite("0.045 %")
                .cons();
    }

    private ParametrageCotisationAssuranceMaternite construireParametrage2010() {
        return unConstructeur(2010)
                .tauxCotisationAssuranceMaternite("0.045 %")
                .cons();
    }

    private ParametrageCotisationAssuranceMaternite construireParametrage2009() {
        return unConstructeur(2009)
                .tauxCotisationAssuranceMaternite("0.02 %")
                .cons();
    }

    private ParametrageCotisationAssuranceMaternite construireParametrage2008() {
        return unConstructeur(2008)
                .tauxCotisationAssuranceMaternite("0.02 %")
                .cons();
    }

    private ParametrageCotisationAssuranceMaternite construireParametrage2007() {
        return unConstructeur(2007)
                .tauxCotisationAssuranceMaternite("0.02 %")
                .cons();
    }

    private ParametrageCotisationAssuranceMaternite construireParametrage2006() {
        return unConstructeur(2006)
                .tauxCotisationAssuranceMaternite("0.02 %")
                .cons();
    }

    private ParametrageCotisationAssuranceMaternite construireParametrage2005() {
        return unConstructeur(2005)
                .tauxCotisationAssuranceMaternite("0.13 %")
                .cons();
    }

    private ParametrageCotisationAssuranceMaternite construireParametrage2004() {
        return unConstructeur(2004)
                .tauxCotisationAssuranceMaternite("0.13 %")
                .cons();
    }

    private ParametrageCotisationAssuranceMaternite construireParametrage2003() {
        return unConstructeur(2003)
                .tauxCotisationAssuranceMaternite("0.15 %")
                .cons();
    }





}
