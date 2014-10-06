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
    private BaremeConstantParTranche baremeBaseSeul;
    private BaremeConstantParTranche baremeBaseCoupleUneSeuleRente;
    private BaremeConstantParTranche baremeBaseCoupleDeuxRentes;


    public ConstructeurBaremeDeductionBeneficiaireRenteAVSAI() {
        baremeBaseSeul = construireBaremeBaseSeul();
        baremeBaseCoupleUneSeuleRente = construireBaremeBaseCoupleUneSeuleRente();
        baremeBaseCoupleDeuxRentes = construireBaremeBaseCoupleDeuxRentes();
    }

    private BaremeConstantParTranche construireBaremeBaseSeul() {
        BaremeConstantParTranche bareme = new BaremeConstantParTranche();
        bareme.ajouterTranche(50000, 10000);
        bareme.ajouterTranche(56700, 8000);
        bareme.ajouterTranche(64000, 6000);
        bareme.ajouterTranche(71500, 4000);
        bareme.ajouterTranche(80000, 2000);
        bareme.ajouterDerniereTranche(0);
        return bareme;
    }

    private BaremeConstantParTranche construireBaremeBaseCoupleUneSeuleRente() {
        BaremeConstantParTranche bareme = construireBaremeBaseSeul();
        return bareme.homothetieTranche(rapportEntreDeductionSeuleEtCouple, TypeArrondi.CENT_FRANC);
    }

    private BaremeConstantParTranche construireBaremeBaseCoupleDeuxRentes() {
        BaremeConstantParTranche bareme = construireBaremeBaseCoupleUneSeuleRente();
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

    private BaremeConstantParTranche construireBareme(int annee, BaremeConstantParTranche base) {
        return indexateur.indexer(base,annee);
    }

    public BaremeConstantParTranche construireBaremeSeul(int annee) {
        return construireBareme(annee,baremeBaseSeul);
    }

    public BaremeConstantParTranche construireBaremeCoupleUneSeuleRente(int annee) {
        return construireBareme(annee,baremeBaseCoupleUneSeuleRente);
    }

    public BaremeConstantParTranche construireBaremeCoupleDeuxRentes(int annee) {
        return construireBareme(annee,baremeBaseCoupleDeuxRentes);
    }

}
