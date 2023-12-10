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
package org.impotch.calcul.impot.taxation.pp.federal.deduction;

import org.impotch.calcul.impot.taxation.pp.DeductionSociale;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class FournisseurDeductionPPEnMemoire implements FournisseurDeductionPP {


    private final ConcurrentMap<Integer,IDeductionDoubleActivite> deducDoubleActivite = new ConcurrentHashMap<>();
    private final ConcurrentMap<Integer,DeductionSociale> deducSocialeEnfant = new ConcurrentHashMap<>();
    private final ConcurrentMap<Integer,DeductionSociale> deducSocialeConjoint = new ConcurrentHashMap<>();


    public IDeductionDoubleActivite getDeductionDoubleActivite(int annee) {
        if (!deducDoubleActivite.containsKey(annee)) {
            deducDoubleActivite.putIfAbsent(annee, construireRegleDeductionDoubleActivite(annee));
        }
        return deducDoubleActivite.get(annee);
    }

    private IDeductionDoubleActivite construireRegleDeductionDoubleActivite(int annee) {
        DeductionDoubleActivite regle = new DeductionDoubleActivite(annee);
        if (annee <= 2007) {
            regle.setPlafond(7000);
        } else {
            regle.setTaux("50 %");
            if (annee >= 2023) {
                regle.setPlancher(8300);
                regle.setPlafond(13600);
            } else if (annee >= 2012) {
                regle.setPlancher(8100);
                regle.setPlafond(13400);
            } else {
                regle.setPlancher(7600);
                regle.setPlafond(12500);
            }
        }
        return regle;
    }

    public DeductionSociale getRegleDeductionSocialeEnfant(int annee) {
        if (!deducSocialeEnfant.containsKey(annee)) {
            deducSocialeEnfant.putIfAbsent(annee, construireRegleDeductionSocialeEnfant(annee));
        }
        return deducSocialeEnfant.get(annee);
    }

    protected DeductionSociale construireRegleDeductionSocialeEnfant(int annee) {
        DeductionSocialeParEnfant deduction = new DeductionSocialeParEnfant(annee);
        if (annee > 2023) {
            deduction.setDeductionSocialeParEnfant(6700);
        } else if (annee > 2022) {
            deduction.setDeductionSocialeParEnfant(6600);
        } else if (annee > 2011) {
            deduction.setDeductionSocialeParEnfant(6500);
        } else if (annee > 2010) {
            deduction.setDeductionSocialeParEnfant(6400);
        } else if (annee > 2005) {
            deduction.setDeductionSocialeParEnfant(6100);
        } else {
            deduction.setDeductionSocialeParEnfant(5600);
        }
        return deduction;
    }



    public DeductionSociale getRegleDeductionSocialeConjoint(int annee) {
        if (annee < 2008) return null;
        if (!deducSocialeConjoint.containsKey(annee)) {
            deducSocialeConjoint.putIfAbsent(annee, construireRegleDeductionSocialeConjoint(annee));
        }
        return deducSocialeConjoint.get(annee);
    }

    protected DeductionSociale construireRegleDeductionSocialeConjoint(int annee) {
        DeductionSocialePourConjoints deduction = new DeductionSocialePourConjoints(annee);
        if (annee > 2023) {
            deduction.setDeducConjointsIFD(2800);
        } else if (annee > 2022) {
            deduction.setDeducConjointsIFD(2700);
        } else if (annee > 2010) {
            deduction.setDeducConjointsIFD(2600);
        } else {
            deduction.setDeducConjointsIFD(2500);
        }
        return deduction;
    }

}
