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
package org.impotch.calcul.impot.federal.param;

import org.impotch.bareme.Bareme;
import org.impotch.util.TypeArrondi;

import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * User: patrick
 * Date: 15/09/11
 * Time: 21:49
 * To change this template use File | Settings | File Templates.
 */
public interface FournisseurBaremeIFD {
    /**
     * Retourne le barème de l'impôt sur le revenu des personnes physiques postnumerando IFD pour personne seul.
     * Ce barème est décrit dans la LIFD article 214 alinea 1.
     * Ces barèmes incluent le seuillage à 25 francs tel que décrit dans l'alinea 3 de l'article 214 de la LIFD.
     * @param annee l'année pour laquelle on désire ce barème
     * @param arrondiSurChaqueTranche Pour l’impôt ordinaire, les arrondis sont au vingtième inférieur mais pour la source, au vingtième les plus proche
     * @return le barème permettant de calculer l'impôt à partir du revenu pour une personne seule.
     */
    Bareme getBaremeImpotRevenuPersonnePhysiquePourPersonneSeule(int annee, TypeArrondi arrondiSurChaqueTranche);


    /**
     * Retourne le barème de l'impôt sur le revenu des personnes physiques postnumerando IFD pour les familles (marié ou mono-parentale).
     * Ce barème est décrit dans la LIFD article 214 alinea 2.
     * Ces barèmes incluent le seuillage à 25 francs tel que décrit dans l'alinea 3 de l'article 214 de la LIFD.
     * @param annee l'année pour laquelle on désire ce barème
     * @param arrondiSurChaqueTranche Pour l’impôt ordinaire, les arrondis sont au vingtième inférieur mais pour la source, au vingtième les plus proche
     * @return le barème permettant de calculer l'impôt à partir du revenu pour des personnes mariées ou des familles mono-parentales.
     */
    Bareme getBaremeImpotRevenuPersonnePhysiquePourFamille(int annee, TypeArrondi arrondiSurChaqueTranche);

    Bareme getBaremeImpotRevenuPraeNumerandoPersonnePhysiquePourPersonneSeule(int annee, TypeArrondi arrondiSurChaqueTranche);
    Bareme getBaremeImpotRevenuPraeNumerandoPersonnePhysiquePourFamille(int annee, TypeArrondi arrondiSurChaqueTranche);

}
