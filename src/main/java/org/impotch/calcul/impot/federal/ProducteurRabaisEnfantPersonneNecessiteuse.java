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
package org.impotch.calcul.impot.federal;

import org.impotch.calcul.impot.Souverainete;
import org.impotch.util.TypeArrondi;
import org.impotch.calcul.impot.taxation.pp.EnfantACharge;
import org.impotch.calcul.impot.taxation.pp.FournisseurAssiettePeriodique;
import org.impotch.calcul.impot.taxation.pp.ProducteurImpotBase;
import org.impotch.calcul.impot.taxation.pp.SituationFamiliale;

import java.math.BigDecimal;
import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.ONE;
import static org.impotch.util.BigDecimalUtil.UN_DEMI;

public class ProducteurRabaisEnfantPersonneNecessiteuse implements ProducteurImpotBase {

    private BigDecimal montantRabaisParEnfant;
    private BigDecimal montantRabaisParPersonneNecessiteuse;

    private void setMontantRabaisParEnfantEtPersonneNecessiteuse(BigDecimal montant) {
        montantRabaisParEnfant = montant;
        montantRabaisParPersonneNecessiteuse = montant;
    }

    public void setMontantRabaisParEnfantEtPersonneNecessiteuse(int montant) {
        setMontantRabaisParEnfantEtPersonneNecessiteuse(BigDecimal.valueOf(montant));
    }


    @Override
    public BigDecimal produireImpotBase(SituationFamiliale situation, FournisseurAssiettePeriodique fournisseur) {
        BigDecimal nbreEnfant = ZERO;
        for (EnfantACharge enfant : situation.getEnfants()) {
            if (enfant.isDemiPart(Souverainete.CH_FEDERALE)) {
                nbreEnfant = nbreEnfant.add(UN_DEMI);
            } else {
                nbreEnfant = nbreEnfant.add(ONE);
            }
        }
        BigDecimal nbrePersonneNecessiteuse = BigDecimal.valueOf(situation.getPersonnesNecessiteuses().size());
        BigDecimal rabais = nbreEnfant.multiply(montantRabaisParEnfant);
        rabais = rabais.add(nbrePersonneNecessiteuse.multiply(montantRabaisParPersonneNecessiteuse));
        return TypeArrondi.CINQ_CENTIEMES_LES_PLUS_PROCHES.arrondirMontant(rabais);
    }
}
