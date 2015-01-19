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

import org.impotch.bareme.BaremeTauxMarginalConstantParTranche;
import org.impotch.bareme.TrancheBareme;
import org.impotch.util.BigDecimalUtil;
import org.impotch.util.TypeArrondi;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by patrick on 14/02/14.
 */
public class ConstructeurBaremeTxMarginalConstantParTranche extends BaremeTauxMarginalConstantParTranche.Constructeur {

    private BigDecimal tauxEffectifMaximum;
    private BigDecimal deltaTranche;

    public void atteintTauxEffectifMaximum(String tauxEffectifMaximum, BigDecimal deltaTranche) {
        this.tauxEffectifMaximum = BigDecimalUtil.parseTaux(tauxEffectifMaximum);
        this.deltaTranche = deltaTranche;
    }



    @Override
    public BaremeTxMarginalConstantParTrancheEtTxEffectifBorneAtteint construire() {
        BaremeTauxMarginalConstantParTranche baremeOriginal = super.construire();

        BaremeTxMarginalConstantParTrancheEtTxEffectifBorneAtteint bareme = new BaremeTxMarginalConstantParTrancheEtTxEffectifBorneAtteint();
        if (null != tauxEffectifMaximum) {
            List<TrancheBareme> tranchesConstruites = new ArrayList<>();
            for (int i =0; i < tranches.size()-1; i++) {
                TrancheBareme tranche = tranches.get(i);
                tranchesConstruites.add(BaremeTauxMarginalConstantParTranche.TrancheBaremeTxMarginal.construireTranche(tranche.getMontantMaxTranche(), tranche.getTauxOuMontant()));
            }
            BigDecimal abscisse = BigDecimal.ZERO;
            BigDecimal ordonnee = BigDecimal.ZERO;
            BigDecimal tauxEffectifCalcule = BigDecimal.ZERO;
            while (tauxEffectifCalcule.compareTo(tauxEffectifMaximum) < 0) {
                abscisse = abscisse.add(deltaTranche);
                ordonnee = baremeOriginal.calcul(abscisse);
                tauxEffectifCalcule = ordonnee.divide(abscisse,10, RoundingMode.HALF_UP);
            }
            abscisse = abscisse.subtract(deltaTranche);
            tranchesConstruites.add(BaremeTauxMarginalConstantParTranche.TrancheBaremeTxMarginal.construireTranche(abscisse, tranches.get(tranches.size() - 1).getTauxOuMontant()));
            tranchesConstruites.add(BaremeTauxMarginalConstantParTranche.TrancheBaremeTxMarginal.construireDerniereTranche(tauxEffectifMaximum));
            bareme.setTranches(tranchesConstruites);

            ordonnee = baremeOriginal.calcul(abscisse);
            BigDecimal ordonneeAvecTauxAtteint = abscisse.multiply(tauxEffectifMaximum);
            bareme.setDeltaPourAtteindreBorne(TypeArrondi.CINQ_CTS.arrondirMontant(ordonneeAvecTauxAtteint.subtract(ordonnee)));
            // TODO Reste à définir sur quelle tranche on affecte la différence !!

        } else {
            bareme.setTranches(tranches);
        }
        bareme.setTypeArrondiSurChaqueTranche(getTypeArrondiSurChaqueTranche());
        bareme.setTypeArrondiGlobal(getTypeArrondiGlobal());
        //bareme.setSeuil(getSeuil());
        return bareme;
    }
}
