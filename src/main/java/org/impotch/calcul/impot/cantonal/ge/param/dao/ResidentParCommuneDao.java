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
package org.impotch.calcul.impot.cantonal.ge.param.dao;

import org.impotch.calcul.lieu.ICommuneSuisse;

import java.math.BigDecimal;
import java.util.Map;

public interface ResidentParCommuneDao {

    /**
     * Retourne le nombre de résident au 31 décembre de l'année fournie en paramètre dans la commune fournie en paramètre
     * @param annee L'année dqns le calendrier grégorien
     * @param noOFSCommune Le numéro de la commune genvoise tel que spécifié par l'Office Fédéral de la Statistique
     * @return le nombre de résident dans la commune au 31 décembre de l’année
     */
    int getNombreResidentAu31decembre(int annee, int noOFSCommune);


}
