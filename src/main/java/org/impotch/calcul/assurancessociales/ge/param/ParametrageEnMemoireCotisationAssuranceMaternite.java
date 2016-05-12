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

import org.impotch.util.BigDecimalUtil;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by patrick on 18/01/15.
 */
public class ParametrageEnMemoireCotisationAssuranceMaternite implements ParametrageCotisationAssuranceMaternite {

    private Map<Integer,BigDecimal> tauxParAnnee;
    private int premiereAnnee;
    private int derniereAnnee;

    public ParametrageEnMemoireCotisationAssuranceMaternite() {
        Map<Integer,String> taux = new HashMap<>();
        taux.put(2003,"0.15 %");
        taux.put(2004,"0.13 %");
        taux.put(2006,"0.02 %");
        taux.put(2010,"0.045 %");
        taux.put(2013,"0.042 %");
        taux.put(2014,"0.041 %");
        setTauxParAnnee(taux);
    }

/**************************************************/
    /********** Accesseurs / Mutateurs ****************/
    /**************************************************/

    private int anneeCourante() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    public void setTauxParAnnee(Map<Integer,String> taux) {
        List<Integer> annees = new ArrayList<>(taux.keySet());
        Collections.sort(annees);
        premiereAnnee = annees.get(0);
        derniereAnnee = Integer.max(1+anneeCourante(),annees.get(annees.size()-1));
        tauxParAnnee = new HashMap<>();
        BigDecimal dernierTaux = null;
        for (int i = premiereAnnee; i <= derniereAnnee; i++) {
            if (taux.containsKey(i)) {
                dernierTaux = BigDecimalUtil.parseTaux(taux.get(i));
            }
            tauxParAnnee.put(i,dernierTaux);
        }
    }

    /**************************************************/
    /******************* Méthodes *********************/
    /**************************************************/

    @Override
    public BigDecimal fournirTaux(int annee) {
        if (annee < premiereAnnee || annee > derniereAnnee) {
            throw new IllegalArgumentException("Le taux de cotisation à " +
                    "l'assurance maternité n'est pas défini pour l'année " + annee);
        }
        return tauxParAnnee.get(annee);
    }
}
