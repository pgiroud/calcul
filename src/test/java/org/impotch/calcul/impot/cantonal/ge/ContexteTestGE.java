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
package org.impotch.calcul.impot.cantonal.ge;

import org.impotch.calcul.assurancessociales.Fournisseur;
import org.impotch.calcul.assurancessociales.FournisseurRegleCalculAssuranceSociale;
import org.impotch.calcul.impot.cantonal.ge.param.FournisseurParamCommunalGE;
import org.impotch.calcul.impot.cantonal.ge.param.FournisseurParametrageCommunaleGE;
import org.impotch.calcul.impot.cantonal.ge.param.dao.ParametreCommunalDao;
import org.impotch.calcul.impot.cantonal.ge.param.dao.ConstructeurParametreCommunalEnMemoire;
import org.impotch.calcul.impot.cantonal.ge.pp.FournisseurCantonalGE;
import org.impotch.calcul.impot.cantonal.ge.pp.FournisseurRegleImpotCantonalGE;
import org.impotch.calcul.impot.indexation.ge.FournisseurIndexGenevois;
import org.impotch.calcul.impot.indexation.ge.FournisseurIndexGenevoisEnMemoire;
import org.impotch.calcul.lieu.FournisseurLieu;
import org.impotch.calcul.lieu.ICommuneSuisse;

import java.math.BigDecimal;

public enum ContexteTestGE {


    CTX_TST_GE;

    private FournisseurIndexGenevois fournisseurIndex;
    private FournisseurRegleImpotCantonalGE fournisseurRegleImpotCantonalGE;
    private FournisseurRegleCalculAssuranceSociale fournisseurRegleCalculAssuranceSociale;
    private FournisseurParametrageCommunaleGE fournisseurParametrageCommunaleGE;

    ContexteTestGE() {
        fournisseurIndex = new FournisseurIndexGenevoisEnMemoire();
        fournisseurRegleCalculAssuranceSociale = new Fournisseur();

        FournisseurParamCommunalGE paramComm = new FournisseurParamCommunalGE();
        paramComm.setDao(construireParametrageCommunal());
        fournisseurParametrageCommunaleGE = paramComm;

        FournisseurCantonalGE fournisseur = new FournisseurCantonalGE(fournisseurRegleCalculAssuranceSociale);
        fournisseur.setFournisseurParamCommunaux(paramComm);
        this.fournisseurRegleImpotCantonalGE = fournisseur;
    }

    private ParametreCommunalDao construireParametrageCommunal() {
        FournisseurLieu lieu = new FournisseurLieu();
        int geneve = 6621;
        int carouge = 6608;
        int airelaVille = 6601;
        int cheneBougerie = 6612;
        int meyrin = 6630;
        int presinge = 6635;

        ConstructeurParametreCommunalEnMemoire constructeur = new ConstructeurParametreCommunalEnMemoire();
        constructeur
                .annee(2007).commune(airelaVille).partPrivilegiee(new BigDecimal("0.74")).ctsAdd(new BigDecimal("0.5"))
                .annee(2007).commune(carouge).partPrivilegiee(new BigDecimal("0.29")).ctsAdd(new BigDecimal("0.39"))
                .annee(2007).commune(geneve).partPrivilegiee(new BigDecimal("0.26")).ctsAdd(new BigDecimal("0.455"))
                .annee(2009).commune(cheneBougerie).partPrivilegiee(new BigDecimal("0.27")).ctsAdd(new BigDecimal("0.34"))
                .annee(2009).commune(geneve).partPrivilegiee(new BigDecimal("0.26")).ctsAdd(new BigDecimal("0.455"))
                .annee(2009).commune(meyrin).partPrivilegiee(new BigDecimal("0.38")).ctsAdd(new BigDecimal("0.45"))
                .annee(2009).commune(presinge).partPrivilegiee(new BigDecimal("0.28")).ctsAdd(new BigDecimal("0.39"))
                .annee(2010).commune(geneve).partPrivilegiee(new BigDecimal("0.27")).ctsAdd(new BigDecimal("0.455"))
                .annee(2013).commune(geneve).partPrivilegiee(new BigDecimal("0.2")).ctsAdd(new BigDecimal("0.439"))
                .annee(2021).commune(geneve).partPrivilegiee(new BigDecimal("0.28")).ctsAdd(new BigDecimal("0.4549"));
        return constructeur.cons();
    }

    public FournisseurIndexGenevois getFournisseurIndex() {
        return fournisseurIndex;
    }

    public FournisseurRegleImpotCantonalGE getFournisseurRegleImpotCantonalGE() {
        return fournisseurRegleImpotCantonalGE;
    }

    public FournisseurParametrageCommunaleGE getFournisseurParametrageCommunaleGE() {
        return fournisseurParametrageCommunaleGE;
    }
}
