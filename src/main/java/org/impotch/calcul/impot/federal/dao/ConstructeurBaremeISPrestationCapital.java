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
import org.impotch.util.BigDecimalUtil;
import org.impotch.util.TypeArrondi;

import java.math.BigDecimal;
import java.text.NumberFormat;

class ConstructeurBaremeISPrestationCapital {

    private ConstructeurBaremeIFD cons = new ConstructeurBaremeIFD(TypeArrondi.MILLE_FRANC_INF);
    private int cumul;

    public ConstructeurBaremeISPrestationCapital() {
        super();
        // Pas de seuillage pour les prestations en capital
        cons.seuil(null);
    }

    public ConstructeurBaremeISPrestationCapital surLesPremier(int montant) {
        cumul = montant;
        return this;
    }

    public ConstructeurBaremeISPrestationCapital surLesProchains(int montant) {
        cumul += montant;
        return this;
    }

    public ConstructeurBaremeISPrestationCapital etFinalementTaux(String taux) {
        BigDecimal tauxBD = BigDecimalUtil.parseTaux(taux);
        String par100DePlus = TypeArrondi.CT.arrondirMontant(tauxBD.movePointRight(2)).toString();
        cons.etPar100FrancsEnPlus(par100DePlus);
        return this;
    }

    public ConstructeurBaremeISPrestationCapital taux(String taux) {
        BigDecimal tauxBD = BigDecimalUtil.parseTaux(taux);
        String par100DePlus = TypeArrondi.CT.arrondirMontant(tauxBD.movePointRight(2)).toString();
        cons.etPar100FrancsEnPlus(par100DePlus);
        cons.pour(cumul);
        return this;
    }

    public ConstructeurBaremeISPrestationCapital tauxEffectifMax(String taux) {
        cons.tauxEffectifMax(taux);
        return this;
    }

    public Bareme construire() {
        return cons.construire();
    }

}
