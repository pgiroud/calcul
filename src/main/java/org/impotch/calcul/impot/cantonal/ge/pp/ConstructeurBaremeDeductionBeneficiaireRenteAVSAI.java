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
package org.impotch.calcul.impot.cantonal.ge.pp;

import org.impotch.bareme.BaremeConstantParTranche;
import org.impotch.bareme.BaremeParTranche;
import org.impotch.bareme.ConstructeurBareme;
import org.impotch.calcul.impot.indexation.FournisseurIndicePeriodique;
import org.impotch.calcul.impot.indexation.Indexateur;
import org.impotch.util.TypeArrondi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * Created by patrick on 12/06/14.
 */
public class ConstructeurBaremeDeductionBeneficiaireRenteAVSAI {

    private static final BigDecimal rapportEntreDeductionSeuleEtCouple = new BigDecimal("1.15");

    /**************************************************/
    /****************** Attributs *********************/
    /**************************************************/

    final Logger logger = LoggerFactory.getLogger(ConstructeurBaremeDeductionBeneficiaireRenteAVSAI.class);

    private Integer anneeMinimumValidite;
    private Integer anneeMaximumValidite;
    private int anneeReference;
    private Indexateur indexateur;
    private BaremeParTranche baremeBaseSeul;
    private BaremeParTranche baremeBaseCoupleUneSeuleRente;
    private BaremeParTranche baremeBaseCoupleDeuxRentes;


    public ConstructeurBaremeDeductionBeneficiaireRenteAVSAI() {
        baremeBaseSeul = construireBaremeBaseSeul();
        baremeBaseCoupleUneSeuleRente = construireBaremeBaseCoupleUneSeuleRente();
        baremeBaseCoupleDeuxRentes = construireBaremeBaseCoupleDeuxRentes();
    }

    private BaremeParTranche construireBaremeBaseSeul() {
        ConstructeurBareme cons = new ConstructeurBareme();
        cons.premiereTranche(50000, 10000);
        cons.tranche(50000,56700, 8000);
        cons.tranche(56700,64000, 6000);
        cons.tranche(64000,71500, 4000);
        cons.tranche(71500, 80000, 2000);
        cons.derniereTranche(80000,0);
        return cons.construireBaremeParTranche();
    }

    private BaremeParTranche construireBaremeBaseCoupleUneSeuleRente() {
        BaremeParTranche bareme = construireBaremeBaseSeul();
        return bareme.homothetie(rapportEntreDeductionSeuleEtCouple, TypeArrondi.CENT_FRANC);
    }

    private BaremeParTranche construireBaremeBaseCoupleDeuxRentes() {
        BaremeParTranche bareme = construireBaremeBaseCoupleUneSeuleRente();
        return bareme.homothetieValeur(rapportEntreDeductionSeuleEtCouple, TypeArrondi.FRANC);
    }

    public ConstructeurBaremeDeductionBeneficiaireRenteAVSAI indexateur(Indexateur indexateur) {
        this.indexateur = indexateur;
        return this;
    }

    public ConstructeurBaremeDeductionBeneficiaireRenteAVSAI validite(int anneeMinimum) {
        this.anneeMinimumValidite = anneeMinimum;
        return this;
    }

    public ConstructeurBaremeDeductionBeneficiaireRenteAVSAI validite(int anneeMinimum, int anneeMaximum) {
        this.anneeMinimumValidite = anneeMinimum;
        this.anneeMaximumValidite = anneeMaximum;
        return this;
    }

    private void verificationPrecondition(int annee) {
        if (null != anneeMinimumValidite && annee < anneeMinimumValidite) throw new IllegalArgumentException("L'année du barème ne peut être inférieure à " + anneeMinimumValidite);
        if (null != anneeMaximumValidite && annee > anneeMaximumValidite) throw new IllegalArgumentException("L'année du barème ne peut être supérieure à " + anneeMaximumValidite);

        logger.debug("Construction barème indexé " + annee);
    }

    private BaremeParTranche construireBareme(int annee, BaremeParTranche base) {
        return indexateur.indexer(base,annee);
    }

    public BaremeParTranche construireBaremeSeul(int annee) {
        return construireBareme(annee,baremeBaseSeul);
    }

    public BaremeParTranche construireBaremeCoupleUneSeuleRente(int annee) {
        return construireBareme(annee,baremeBaseCoupleUneSeuleRente);
    }

    public BaremeParTranche construireBaremeCoupleDeuxRentes(int annee) {
        return construireBareme(annee,baremeBaseCoupleDeuxRentes);
    }

}
