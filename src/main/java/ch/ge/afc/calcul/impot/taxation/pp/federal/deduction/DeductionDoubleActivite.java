package ch.ge.afc.calcul.impot.taxation.pp.federal.deduction;

import ch.ge.afc.calcul.ReglePeriodique;
import ch.ge.afc.util.BigDecimalUtil;
import ch.ge.afc.util.TypeArrondi;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: patrick
 * Date: 06/05/12
 * Time: 07:41
 * To change this template use File | Settings | File Templates.
 */
public class DeductionDoubleActivite extends ReglePeriodique implements IDeductionDoubleActivite {

    private BigDecimal taux;
    private BigDecimal plancher;
    private BigDecimal plafond;


    public DeductionDoubleActivite(int annee) {
        super(annee);
    }

    public void setTaux(String taux) {
        this.taux = BigDecimalUtil.parseTaux(taux);
    }

    public void setPlancher(int plancher) {
        this.plancher = BigDecimal.valueOf(plancher);
    }

    public void setPlafond(int plafond) {
        this.plafond = BigDecimal.valueOf(plafond);
    }

    @Override
    public BigDecimal getDeduction(BigDecimal premierRevenuNet, BigDecimal secondRevenuNet) {
        BigDecimal plusPetitRevenu = premierRevenuNet.min(secondRevenuNet);
        BigDecimal partPlusPetitRevenu = TypeArrondi.FRANC_SUP.arrondirMontant(taux.multiply(plusPetitRevenu));
        BigDecimal deductionPossible = plancher.max(plafond.min(partPlusPetitRevenu));
        return plusPetitRevenu.min(deductionPossible);
    }
}
