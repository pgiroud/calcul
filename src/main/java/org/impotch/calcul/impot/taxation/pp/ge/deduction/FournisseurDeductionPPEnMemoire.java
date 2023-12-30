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
package org.impotch.calcul.impot.taxation.pp.ge.deduction;

import org.impotch.bareme.BaremeParTranche;
import org.impotch.calcul.impot.cantonal.ge.pp.ConstructeurBaremeDeductionBeneficiaireRenteAVSAI;
import org.impotch.calcul.impot.indexation.ge.FournisseurIndexGenevois;
import org.impotch.calcul.impot.indexation.ge.MontantIndexe;
import org.impotch.calcul.impot.taxation.pp.DeductionSociale;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class FournisseurDeductionPPEnMemoire implements FournisseurDeductionPP {

    private FournisseurIndexGenevois fournisseurIndexGenevois;

    private ConcurrentMap<Integer, DeductionSociale> deducSocialeCharge = new ConcurrentHashMap<Integer, DeductionSociale>();
    private ConcurrentMap<Integer, DeductionDoubleActivite> deducDoubleActivite = new ConcurrentHashMap<>();
    private ConcurrentMap<Integer, DeductionBeneficiaireRentesAVSAI> deducSocialeRentier = new ConcurrentHashMap<>();

    public FournisseurDeductionPPEnMemoire(FournisseurIndexGenevois fournisseurIndexGenevois) {
        this.fournisseurIndexGenevois = fournisseurIndexGenevois;
    }

    @Override
    public DeductionSociale getRegleDeductionSocialeCharge(int annee) {
        if (!deducSocialeCharge.containsKey(annee)) {
            DeductionSociale deduction = construireRegleDeductionSocialeCharge(annee);
            // Attention, la ConcurrentMap n'aime pas les nulls !!
            if (null != deduction) deducSocialeCharge.putIfAbsent(annee, deduction);
        }
        return deducSocialeCharge.get(annee);
    }

    private MontantIndexe construireMontantIndexe(int annee) {
        MontantIndexe mntIdx; // LIPP Art. 72 alinea 15
        if (annee < 2011) {
            mntIdx = new MontantIndexe(9_000, fournisseurIndexGenevois.getIndexateurQuadriennalBaseDecembre2005(2009));
        } else if (annee < 2017) {
            mntIdx= new MontantIndexe(10_000,fournisseurIndexGenevois.getIndexateurQuadriennalBaseDecembre2005(2009));
        } else if (annee < 2021) {
            // Incompréhensible, l’arrondi au franc près voudrait que ce soit 9981 CHF et non pas 9980 CHF !
            mntIdx= new MontantIndexe(9_980,fournisseurIndexGenevois.getIndexateurQuadriennalBaseDecembre2005(2017));
        } else {
            mntIdx= new MontantIndexe(13_000,fournisseurIndexGenevois.getIndexateurQuadriennalBaseDecembre2005(2021));  // RCEPF art. 12
        }
        return mntIdx;
    }

    private DeductionSociale construireRegleDeductionSocialeCharge(int annee) {
        if (annee < 2010) return null;
        if (annee > 2024) throw new IllegalArgumentException("Le montant des déductions sociales pour l'année '"
                + annee + "' doit être adapté !");
        DeductionChargeFamille deduction = new DeductionChargeFamille(annee);
        deduction.setMontantParCharge(construireMontantIndexe(annee).getMontantIndexe(annee));

//        if (2010 == annee) deduction.setMontantParCharge(9000);
//        else if (annee < 2013) deduction.setMontantParCharge(10000);
//        else if (annee < 2017) deduction.setMontantParCharge(10078);
//        else if (annee < 2021) deduction.setMontantParCharge(9980);
//        else if (annee < 2025) deduction.setMontantParCharge(13000);
        return deduction;
    }

    @Override
    public DeductionSociale getRegleDeductionDoubleActivite(int annee) {
        if (!deducDoubleActivite.containsKey(annee)) {
            DeductionDoubleActivite deduction = construireRegleDeductionDoubleActivite(annee);
            // Attention, la ConcurrentMap n'aime pas les nulls !!
            if (null != deduction) deducDoubleActivite.putIfAbsent(annee, deduction);
        }
        return deducDoubleActivite.get(annee);
    }

    private DeductionDoubleActivite construireRegleDeductionDoubleActivite(int annee) {
        MontantIndexe montantIndexe
                = (annee < 2021 ) ? // LIPP Art. 72 alinea 15
                new MontantIndexe(500,fournisseurIndexGenevois.getIndexateurQuadriennalBaseDecembre2005(2009))
                : new MontantIndexe(1000, fournisseurIndexGenevois.getIndexateurQuadriennalBaseDecembre2005(2021)); // RCEPF art. 9
        BigDecimal montantDeduction = montantIndexe.getMontantIndexe(annee);
        return new DeductionDoubleActivite(montantDeduction);
    }

    public DeductionBeneficiaireRentesAVSAI getDeductionBeneficiaireRenteAVSAI(int annee) {
        if (annee < 2010)
            throw new IllegalArgumentException("Cette déduction ne peut pas être utilisé pour l'année " + annee);
        if (!deducSocialeRentier.containsKey(annee)) {
            DeductionBeneficiaireRentesAVSAI deduction = construireRegleDeductionRentierAVSAI(annee);
            // Attention, la ConcurrentMap n'aime pas les nulls !!
            if (null != deduction) deducSocialeRentier.putIfAbsent(annee, deduction);
        }
        return deducSocialeRentier.get(annee);
    }

    protected DeductionBeneficiaireRentesAVSAI construireRegleDeductionRentierAVSAI(int annee) {
        return new DeductionRentierAVS(annee, construireBaremeDeductionBeneficiaireRentesAvsAi(annee), new BigDecimal("1.15"));
    }

    protected BaremeParTranche construireBaremeDeductionBeneficiaireRentesAvsAi(int annee) {
        ConstructeurBaremeDeductionBeneficiaireRenteAVSAI constructeur = new ConstructeurBaremeDeductionBeneficiaireRenteAVSAI();
        constructeur.indexateur(fournisseurIndexGenevois.getIndexateurQuadriennalBaseDecembre2005(annee));
        constructeur.validite(2010);
        return constructeur.construireBaremeSeul(annee);

//        ConstructeurBareme cons = new ConstructeurBareme();
//        // Voir le détail dans D 3 08.05: Règlement relatif à la compensation des effets de la progression à froid (RCEPF)
//        // Art. 6
//        if (annee < 2013) {
//            cons.premiereTranche(50000, 10000)
//                    .tranche(50000, 56700, 8000)
//                    .tranche(56700, 64000, 6000)
//                    .tranche(64000, 71500, 4000)
//                    .tranche(71500, 80000, 2000)
//                    .derniereTranche(80000, 0);
//        } else if (annee < 2017) {
//            cons.premiereTranche(57947, 10078)
//                    .tranche(57947, 65707, 8062)
//                    .tranche(65707, 74172, 6047)
//                    .tranche(74172, 82839, 4031)
//                    .tranche(82839, 92715, 2016)
//                    .derniereTranche(92715, 0);
//        } else if (annee < 2021) {
//            cons.premiereTranche(57388, 9981)
//                    .tranche(57388, 65073, 7984)
//                    .tranche(65073, 73457, 5988)
//                    .tranche(73457, 82040, 3992)
//                    .tranche(82040, 91821, 1996)
//                    .derniereTranche(91821, 0);
//        } else {
//            throw new IllegalArgumentException("le barème déduction pour rentes n'est pas définis pour année >= 2021 !!");
//        }
//        return cons.construireBaremeParTranche();
    }

}
