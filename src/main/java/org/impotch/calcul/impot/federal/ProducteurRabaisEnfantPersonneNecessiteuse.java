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

/**
 * Created by IntelliJ IDEA.
 * User: patrick
 * Date: 17 avr. 2010
 * Time: 20:58:54
 * To change this template use File | Settings | File Templates.
 */
public class ProducteurRabaisEnfantPersonneNecessiteuse implements ProducteurImpotBase {

    private BigDecimal montantRabaisParEnfant;
    private BigDecimal montantRabaisParPersonneNecessiteuse;

    public void setMontantRabaisParEnfantEtPersonneNecessiteuse(BigDecimal montant) {
        montantRabaisParEnfant = montant;
        montantRabaisParPersonneNecessiteuse = montant;
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
        return TypeArrondi.CINQ_CTS.arrondirMontant(rabais);
    }
}
