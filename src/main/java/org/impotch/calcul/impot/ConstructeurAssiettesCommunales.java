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
package org.impotch.calcul.impot;

import org.impotch.calcul.impot.taxation.forimposition.ForCommunal;
import org.impotch.calcul.impot.taxation.repart.Part;
import org.impotch.calcul.impot.taxation.repart.Repartition;
import org.impotch.calcul.lieu.ICommuneSuisse;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.impotch.calcul.impot.taxation.forimposition.ForCommunal.forCommunal;
public class ConstructeurAssiettesCommunales {

    public static ConstructeurAssiettesCommunales unConstructeurAssiettesCommunales(PeriodeFiscale periode) {
        return new ConstructeurAssiettesCommunales(periode);
    }

    public static ConstructeurAssiettesCommunales unConstructeurAssiettesCommunales(int anneeFiscale) {
        return unConstructeurAssiettesCommunales(PeriodeFiscale.annee(anneeFiscale));
    }

    private final PeriodeFiscale periode;
    private final Map<ICommuneSuisse, Integer> joursDomicile = new HashMap<>();
    private final Repartition<ForCommunal> repart = new Repartition<>();

    private ConstructeurAssiettesCommunales(PeriodeFiscale periode) {
        this.periode = periode;
    }

    public ConstructeurAssiettesCommunales uniqueCommune(ICommuneSuisse commune) {
        return uniqueCommuneDomicile(commune).part(commune,BigDecimal.ONE);
    }

    public ConstructeurAssiettesCommunales uniqueCommuneDomicile(ICommuneSuisse commune) {
        return communeDomicile(commune,360);
    }

    public ConstructeurAssiettesCommunales communeDomicile(ICommuneSuisse commune, int nbreJour) {
        joursDomicile.put(commune,nbreJour);
        return this;
    }

    public ConstructeurAssiettesCommunales part(ICommuneSuisse commune, BigDecimal montant) {
        repart.ajouterPart(forCommunal(commune),new Part(montant));
        return this;
    }

    public FournisseurAssietteCommunale fournir() {
        return new FournisseurAssietteCommunale() {
            @Override
            public PeriodeFiscale getPeriodeFiscale() {
                return periode;
            }

            @Override
            public Map<ICommuneSuisse, Integer> getNbreJourDomicileSurCommune() {
                return joursDomicile;
            }

            @Override
            public Repartition<ForCommunal> getRepartition() {
                return repart;
            }
        };
    }

}
