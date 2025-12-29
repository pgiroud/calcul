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
package org.impotch.calcul.impot.taxation.repart;

/**
 * Il existe plusieurs façons de répartir lorsqu'on applique des arrondis.
 * Imaginons que nous voulions répartir 2 francs en 3 parts (1/5, 1/5, 3/5) avec arrondi au
 * franc près.
 * Dans une répartition classique, on obtient 2 part avec 0 franc (découpage en 1/5) et 1 part avec 2 francs (découpage au 3/5).
 * Dans une répartition seulement sur reste à répartir, on obtient 1 part avec 0 franc (découpage au 1/5) et 2 parts avec 1 franc (découpage 1/4, 3/4)!
 */
enum TypeCalculRepartition {
    CLASSIQUE,
    SEULEMENT_SUR_RESTE_A_REPARTIR;
}
