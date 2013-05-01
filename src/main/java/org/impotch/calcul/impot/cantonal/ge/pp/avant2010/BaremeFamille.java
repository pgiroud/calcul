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
package org.impotch.calcul.impot.cantonal.ge.pp.avant2010;

import org.impotch.bareme.Bareme;
import org.impotch.util.TypeArrondi;
import org.impotch.util.math.Fonction;
import org.impotch.util.math.integration.MethodeIntegration;

import java.math.BigDecimal;
import java.util.SortedMap;
import java.util.TreeMap;

public class BaremeFamille implements Bareme {

    private MethodeIntegration methodeIntegration;
    private Fonction tauxMarginal;
    private SortedMap<Long,Double> cache = new TreeMap<Long, Double>();
    private TypeArrondi arrondi = TypeArrondi.CINQ_CTS;

    public BaremeFamille() {
        cache.put(0L,0.0d);
    }

    public void setMethodeIntegration(MethodeIntegration methodeIntegration) {
        this.methodeIntegration = methodeIntegration;
    }

    public void setTauxMarginal(Fonction tauxMarginal) {
        this.tauxMarginal = tauxMarginal;
    }

    private BigDecimal integrer(BigDecimal x1, BigDecimal x2) {
        return BigDecimal.valueOf(methodeIntegration.integre(tauxMarginal, x1.doubleValue(), x2.doubleValue()));
    }

    public void setArrondi(TypeArrondi arrondi) {
        this.arrondi = arrondi;
    }

    private void chargerCacheJusqua(Long arrondiAuMillePres) {
        Long plusGrandeValeurCachee = cache.lastKey();
        Double valeur = cache.get(plusGrandeValeurCachee);
        while (plusGrandeValeurCachee < arrondiAuMillePres) {
            valeur += methodeIntegration.integre(tauxMarginal, plusGrandeValeurCachee, plusGrandeValeurCachee += 1000);
            cache.put(plusGrandeValeurCachee,valeur);
        }
    }

    private Double calculFlottant(BigDecimal revenu) {
        // On recherche dans le cache la valeur inférieure
        BigDecimal arrondiMilleFrancsInf = TypeArrondi.MILLE_FRANC_INF.arrondirMontant(revenu);
        Long arrondi = arrondiMilleFrancsInf.longValue();
        if (!cache.containsKey(arrondi)) {
            chargerCacheJusqua(arrondi);
        }
        return cache.get(arrondi) + methodeIntegration.integre(tauxMarginal, arrondi, revenu.doubleValue());
    }

    @Override
    public BigDecimal calcul(BigDecimal pAssiette) {
        return arrondi.arrondirMontant(new BigDecimal(calculFlottant(pAssiette)));
    }
}
