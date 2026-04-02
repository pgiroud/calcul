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

package org.impotch.calcul.impot.federal;

import org.impotch.calcul.impot.taxation.pp.ProducteurImpot;
import org.impotch.util.TypeArrondi;

import static org.impotch.util.TypeArrondi.VINGTIEME_INF;

public interface FournisseurRegleImpotFederal {

    /// Retourne un producteur d’impôt fédéral sur le revenu des personnes physiques.
    /// Attention, il s’agit bien d’impôt produit et non pas d’impôt perçu.
    /// L’alinea 3 de l’article 36 de la LIFD *Les montants d’impôt inférieurs à 25 francs ne sont pas perçus.* n’est donc pas appliqué
    /// @param annee L’année fiscale pour laquelle cette production est valide
    /// @return le producteur d’impôt valable pour l’année
    default ProducteurImpot producteurImpotsFederauxPP(int annee) {
        return producteurImpotsFederauxPP(annee, VINGTIEME_INF);
    }


     /// Retourne un producteur d’impôt fédéral sur le revenu des personnes physiques.
     /// Attention, il s’agit bien d’impôt produit et non pas d’impôt perçu.
     /// L’alinea 3 de l’article 36 de la LIFD *Les montants d’impôt inférieurs à 25 francs ne sont pas perçus.* n’est donc pas appliqué
     /// @param annee L’année fiscale pour laquelle cette production est valide
     /// @param arrondiSurChaqueTrancheBareme Les calculs sur les tranches du barème à taux marginal sont arrondis. On peut donc spécifié l’arrondi
     /// @return le producteur d’impôt valable pour l’année
    ProducteurImpot producteurImpotsFederauxPP(int annee,
                                               TypeArrondi arrondiSurChaqueTrancheBareme);
}
