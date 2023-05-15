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
/**
 * This file is part of impotch/calcul.
 * <p>
 * impotch/calcul is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 * <p>
 * impotch/calcul is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with impotch/calcul.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.impotch.calcul.impot.federal.dao;

import org.impotch.bareme.Bareme;
import static org.impotch.calcul.impot.federal.dao.ConstructeurBaremeIFD.unBaremeIFD;

class IFDPraeNumerando {


    /************************************************************************/
    /****** Barèmes Prae numerando (art. 36 de l'ancienne LIFD) *************/
    /************************************************************************/

    /* ---------------------- Personne seule -------------------------------*/

    public Bareme personneSeule(int annee) {
        if (annee > 2013)
            throw new IllegalArgumentException("Les barèmes praenumerando ne sont plus valables après 2013 !");
        if (2012 == annee || 2013 == annee) {
            return personneSeule2012();
        }
        if (2011 == annee) {
            return personneSeule2011();
        }
        if (2007 <= annee && 2010 >= annee) {
            return personneSeule2007();
        }
        if (1997 <= annee && 2006 >= annee) {
            return personneSeule1997();
        }
        throw new IllegalArgumentException("Le barème IFD praenumerando pour les familles n'est pas défini pour l'année " + annee);
    }

    /**
     * Adapté à l'indice de juin 2011: 161.9
     * barème 2011 étiré de 0.6 %
     * @return Constructeur pour les périodes fiscales 2012 et 2013
     */
    private Bareme personneSeule2012() {
        return unBaremeIFD()
                .jusqua(13200).a("0.00").etPar100FrancsEnPlus("0.77")
                .pour(28700).a("119.35").etPar100FrancsEnPlus("0.88")
                .pour(37600).a("197.65").etPar100FrancsEnPlus("2.64")
                .pour(50100).a("527.65").etPar100FrancsEnPlus("2.97")
                .pour(65800).a("993.90").etPar100FrancsEnPlus("5.94")
                .pour(70900).a("1296.80").etPar100FrancsEnPlus("6.60")
                .pour(94100).a("2828.00").etPar100FrancsEnPlus("8.80")
                .pour(122300).a("5309.60").etPar100FrancsEnPlus("11.00")
                .pour(159900).a("9445.60").etPar100FrancsEnPlus("13.20")
                .tauxEffectifMax("11.5 %").construire();
    }


    /**
     * Adapté à l'indice de juin 2010: 161.0
     * barème 2007 étiré de 4.1 %
     * @return Constructeur pour la période fiscale 2011
     */
    private Bareme personneSeule2011() {
        return unBaremeIFD()
                .jusqua(13100).a("0.00").etPar100FrancsEnPlus("0.77")
                .pour(28600).a("119.35").etPar100FrancsEnPlus("0.88")
                .pour(37400).a("196.75").etPar100FrancsEnPlus("2.64")
                .pour(49900).a("526.75").etPar100FrancsEnPlus("2.97")
                .pour(65500).a("990.05").etPar100FrancsEnPlus("5.94")
                .pour(70500).a("1287.05").etPar100FrancsEnPlus("6.60")
                .pour(93500).a("2805.05").etPar100FrancsEnPlus("8.80")
                .pour(121600).a("5277.85").etPar100FrancsEnPlus("11.00")
                .pour(159000).a("9391.85").etPar100FrancsEnPlus("13.20")
                .tauxEffectifMax("11.5 %").construire();
    }

    /**
     * Adapté à l'indice de décembre 2005: 154,6
     * barème 1993 étiré de 8,6 %
     * @return Constructeur pour les périodes fiscales 2007, 2008, 2009, 2010
     */
    private Bareme personneSeule2007() {
        return unBaremeIFD()
                .jusqua(12600).a("0.00").etPar100FrancsEnPlus("0.77")
                .pour(27400).a("113.95").etPar100FrancsEnPlus("0.88")
                .pour(35900).a("188.75").etPar100FrancsEnPlus("2.64")
                .pour(47900).a("505.55").etPar100FrancsEnPlus("2.97")
                .pour(62900).a("951.05").etPar100FrancsEnPlus("5.94")
                .pour(67700).a("1236.15").etPar100FrancsEnPlus("6.60")
                .pour(89800).a("2694.75").etPar100FrancsEnPlus("8.80")
                .pour(116800).a("5070.75").etPar100FrancsEnPlus("11.00")
                .pour(152700).a("9019.75").etPar100FrancsEnPlus("13.20")
                .tauxEffectifMax("11.5 %").construire();
    }

    private static final int[][] TRANCHES_SEUL_PRAE = new int[][]{
            {13200, 28700, 37600, 50100, 65800, 70900, 94100, 122300, 159900, 685900},   // 2012
            {13100, 28600, 37400, 49900, 65500, 70500, 93500, 121600, 159000, 682100}, // 2011
            {12600, 27400, 35900, 47900, 62900, 67700, 89800, 116800, 152700, 655000},  // 2007
            {11600, 25200, 33000, 44000, 57800, 62300, 82600, 107400, 140400, 603000}}; // 1997

    private Bareme personneSeule1997() {
        throw new RuntimeException("La méthode n'est pas finalisée !!");
//        return new ConstructeurBaremeIFD()
//        .jusqua(0).a("0.00").etPar100FrancsEnPlus("0.77")
//        .pour(0).a(".").etPar100FrancsEnPlus("0.88")
//        .pour(0).a(".").etPar100FrancsEnPlus("2.64")
//        .pour(0).a(".").etPar100FrancsEnPlus("2.97")
//        .pour(0).a(".").etPar100FrancsEnPlus("5.94")
//        .pour(0).a(".").etPar100FrancsEnPlus("6.60")
//        .pour(0).a(".").etPar100FrancsEnPlus("8.80")
//        .pour(0).a(".").etPar100FrancsEnPlus("11.00")
//        .pour(0).a(".").etPar100FrancsEnPlus("13.20")
//                .tauxEffectifMax("11.5 %").construire();
    }

    /* --------------------------- Famille -------------------------------*/

    public Bareme famille(int annee) {
        if (annee > 2013)
            throw new IllegalArgumentException("Les barèmes praenumerando ne sont plus valables après 2013 !");
        if (2012 == annee || 2013 == annee) {
            return famille2012();
        }
        if (2011 == annee) {
            return famille2011();
        }
        if (2007 <= annee && 2010 >= annee) {
            return famille2007();
        }
        if (1997 <= annee && 2006 >= annee) {
            return famille1997();
        }
        throw new IllegalArgumentException("Le barème IFD praenumerando pour les familles n'est pas défini pour l'année " + annee);
    }

    private static final int[][] TRANCHES_FAMILLE_PRAE = new int[][]{
            {25700, 46200, 53000, 68400, 82000, 93900, 104200, 112800, 119600, 124700, 128200, 129900, 131600, 813400}, // 2012
            {25500, 45800, 52600, 67900, 81500, 93300, 103500, 112000, 118800, 123800, 127300, 129000, 130700, 807800}, // 2011
            {24500, 44000, 50500, 65200, 78200, 89600, 99400, 107600, 114100, 118900, 122200, 123900, 125600, 775900}, // 2007
            {22600, 40500, 46500, 60000, 72000, 82500, 91600, 99100, 105100, 109600, 112700, 114200, 115700, 715500}}; // 1997

    private Bareme famille2012() {
        throw new RuntimeException("La méthode n'est pas finalisée !!");
//        return new ConstructeurBaremeIFD()
//        .jusqua(0).a("0.00").etPar100FrancsEnPlus("1.00")
//        .pour(0).a(".00").etPar100FrancsEnPlus("2.00")
//        .pour(0).a(".00").etPar100FrancsEnPlus("3.00")
//        .pour(0).a(".00").etPar100FrancsEnPlus("4.00")
//        .pour(0).a(".00").etPar100FrancsEnPlus("5.00")
//        .pour(0).a(".00").etPar100FrancsEnPlus("6.00")
//        .pour(0).a(".00").etPar100FrancsEnPlus("7.00")
//        .pour(0).a(".00").etPar100FrancsEnPlus("8.00")
//        .pour(0).a(".00").etPar100FrancsEnPlus("9.00")
//        .pour(0).a(".00").etPar100FrancsEnPlus("10.00")
//        .pour(0).a(".00").etPar100FrancsEnPlus("11.00")
//        .pour(0).a(".00").etPar100FrancsEnPlus("12.00")
//        .pour(0).a(".00").etPar100FrancsEnPlus("13.00")
//                .tauxEffectifMax("11.5 %").construire();
    }

    private Bareme famille2011() {
        throw new RuntimeException("La méthode n'est pas finalisée !!");
//        return new ConstructeurBaremeIFD()
//        .jusqua(0).a("0.00").etPar100FrancsEnPlus("1.00")
//        .pour(0).a(".00").etPar100FrancsEnPlus("2.00")
//        .pour(0).a(".00").etPar100FrancsEnPlus("3.00")
//        .pour(0).a(".00").etPar100FrancsEnPlus("4.00")
//        .pour(0).a(".00").etPar100FrancsEnPlus("5.00")
//        .pour(0).a(".00").etPar100FrancsEnPlus("6.00")
//        .pour(0).a(".00").etPar100FrancsEnPlus("7.00")
//        .pour(0).a(".00").etPar100FrancsEnPlus("8.00")
//        .pour(0).a(".00").etPar100FrancsEnPlus("9.00")
//        .pour(0).a(".00").etPar100FrancsEnPlus("10.00")
//        .pour(0).a(".00").etPar100FrancsEnPlus("11.00")
//        .pour(0).a(".00").etPar100FrancsEnPlus("12.00")
//        .pour(0).a(".00").etPar100FrancsEnPlus("13.00")
//                .tauxEffectifMax("11.5 %").construire();
    }

    private Bareme famille2007() {
        return unBaremeIFD()
                .jusqua(24500).a("0.00").etPar100FrancsEnPlus("1.00")
                .pour(44000).a("195.00").etPar100FrancsEnPlus("2.00")
                .pour(50500).a("325.00").etPar100FrancsEnPlus("3.00")
                .pour(65200).a("766.00").etPar100FrancsEnPlus("4.00")
                .pour(78200).a("1286.00").etPar100FrancsEnPlus("5.00")
                .pour(89600).a("1856.00").etPar100FrancsEnPlus("6.00")
                .pour(99400).a("2444.00").etPar100FrancsEnPlus("7.00")
                .pour(107600).a("3018.00").etPar100FrancsEnPlus("8.00")
                .pour(114100).a("3538.00").etPar100FrancsEnPlus("9.00")
                .pour(118900).a("3970.00").etPar100FrancsEnPlus("10.00")
                .pour(122200).a("4300.00").etPar100FrancsEnPlus("11.00")
                .pour(123900).a("4487.00").etPar100FrancsEnPlus("12.00")
                .pour(125600).a("4691.00").etPar100FrancsEnPlus("13.00")
                .tauxEffectifMax("11.5 %").construire();
    }

    private Bareme famille1997() {
        throw new RuntimeException("La méthode n'est pas finalisée !!");
//        return new ConstructeurBaremeIFD()
//        .jusqua(0).a("0.00").etPar100FrancsEnPlus("1.00")
//        .pour(0).a(".00").etPar100FrancsEnPlus("2.00")
//        .pour(0).a(".00").etPar100FrancsEnPlus("3.00")
//        .pour(0).a(".00").etPar100FrancsEnPlus("4.00")
//        .pour(0).a(".00").etPar100FrancsEnPlus("5.00")
//        .pour(0).a(".00").etPar100FrancsEnPlus("6.00")
//        .pour(0).a(".00").etPar100FrancsEnPlus("7.00")
//        .pour(0).a(".00").etPar100FrancsEnPlus("8.00")
//        .pour(0).a(".00").etPar100FrancsEnPlus("9.00")
//        .pour(0).a(".00").etPar100FrancsEnPlus("10.00")
//        .pour(0).a(".00").etPar100FrancsEnPlus("11.00")
//        .pour(0).a(".00").etPar100FrancsEnPlus("12.00")
//        .pour(0).a(".00").etPar100FrancsEnPlus("13.00")
//                .tauxEffectifMax("11.5 %").construire();
    }
}
