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

import org.impotch.bareme.Bareme;
import org.impotch.bareme.BaremeTauxMarginalConstantParTranche;
import org.impotch.util.TypeArrondi;

public class FournisseurBarIFDProto implements FournisseurBaremeIFD {

    @Override
    public Bareme getBaremeImpotRevenuPersonnePhysiquePourPersonneSeule(int annee) {
        BaremeTauxMarginalConstantParTranche.Constructeur constructeur = new BaremeTauxMarginalConstantParTranche.Constructeur();
        constructeur.tranche(14500,"0 %");
        constructeur.tranche(31600,"0.77 %");
        constructeur.tranche(41400,"0.88 %");
        constructeur.tranche(55200,"2.64 %");
        constructeur.tranche(72500,"2.97 %");
        constructeur.derniereTranche("11.5 %");
        return constructeur.typeArrondiSurChaqueTranche(TypeArrondi.CINQ_CTS_INF).construire();


//        private ConstructeurBaremeIFD getConstructeurBaremeImpotRevenuPersonnePhysiquePersonneSeule2012() {
//            ConstructeurBaremeIFD constructeur = new ConstructeurBaremeIFD();
//            constructeur.jusqua(14500).a("0.00").etPar100FrancsEnPlus("0.77");
//            constructeur.pour(31600).a("131.65").etPar100FrancsEnPlus("0.88");
//            constructeur.pour(41400).a("217.90").etPar100FrancsEnPlus("2.64");
//            constructeur.pour(55200).a("582.20").etPar100FrancsEnPlus("2.97");
//            constructeur.pour(72500).a("1096.00").etPar100FrancsEnPlus("5.94");
//            constructeur.pour(78100).a("1428.60").etPar100FrancsEnPlus("6.60");
//            constructeur.pour(103600).a("3111.60").etPar100FrancsEnPlus("8.80");
//            constructeur.pour(134600).a("5839.60").etPar100FrancsEnPlus("11.00");
//            constructeur.pour(176000).a("10393.60").etPar100FrancsEnPlus("13.20");
//            constructeur.pour(755200).a("86848.00").etPar100FrancsEnPlus("11.50");
//            return constructeur;
//        }
//
    }

    @Override
    public Bareme getBaremeImpotRevenuPersonnePhysiquePourFamille(int annee) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Bareme getBaremeImpotSourcePrestationCapital(int annee) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Bareme getBaremeImpotRevenuPraeNumerandoPersonnePhysiquePourPersonneSeule(int annee) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Bareme getBaremeImpotRevenuPraeNumerandoPersonnePhysiquePourFamille(int annee) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


}
