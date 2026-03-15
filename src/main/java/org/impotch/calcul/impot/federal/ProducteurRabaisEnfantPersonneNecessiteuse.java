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

import java.math.BigDecimal;

import org.impotch.calcul.impot.taxation.pp.EnfantACharge;
import org.impotch.calcul.impot.taxation.pp.FournisseurAssiettePeriodique;
import org.impotch.calcul.impot.taxation.pp.ProducteurImpotBase;
import org.impotch.calcul.impot.taxation.pp.SituationFamiliale;

import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.ONE;

import static org.impotch.util.BigDecimalUtil.UN_DEMI;
import static org.impotch.util.TypeArrondi.VINGTIEME_LE_PLUS_PROCHE;
import static org.impotch.calcul.impot.Souverainete.CH_FEDERALE;

public class ProducteurRabaisEnfantPersonneNecessiteuse implements ProducteurImpotBase {

    private BigDecimal montantRabaisParEnfant;
    private BigDecimal montantRabaisParPersonneNecessiteuse;

    public ProducteurRabaisEnfantPersonneNecessiteuse(int montant) {
        this(BigDecimal.valueOf(montant),BigDecimal.valueOf(montant));
    }

    private ProducteurRabaisEnfantPersonneNecessiteuse(BigDecimal montantRabaisParEnfant, BigDecimal montantRabaisParPersonneNecessiteuse) {
        this.montantRabaisParEnfant = montantRabaisParEnfant;
        this.montantRabaisParPersonneNecessiteuse = montantRabaisParPersonneNecessiteuse;
    }

    private void setMontantRabaisParEnfantEtPersonneNecessiteuse(BigDecimal montant) {
        montantRabaisParEnfant = montant;
        montantRabaisParPersonneNecessiteuse = montant;
    }


    
    @Override
    public BigDecimal produireImpotBase(SituationFamiliale situation, FournisseurAssiettePeriodique fournisseur) {
        BigDecimal nbreEnfant = ZERO;
        for (EnfantACharge enfant : situation.getEnfants()) {
            if (enfant.isDemiPart(CH_FEDERALE)) {
                nbreEnfant = nbreEnfant.add(UN_DEMI);
            } else {
                nbreEnfant = nbreEnfant.add(ONE);
            }
        }
        BigDecimal nbrePersonneNecessiteuse = BigDecimal.valueOf(situation.getPersonnesNecessiteuses().size());
        BigDecimal rabais = nbreEnfant.multiply(montantRabaisParEnfant);
        rabais = rabais.add(nbrePersonneNecessiteuse.multiply(montantRabaisParPersonneNecessiteuse));
        return VINGTIEME_LE_PLUS_PROCHE.arrondir(rabais);
    }
}
