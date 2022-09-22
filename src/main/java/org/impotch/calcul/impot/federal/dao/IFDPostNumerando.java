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

class IFDPostNumerando {

    /************************************************************************/
    /************** Barèmes Post numerando (art. 214 LIFD) ******************/
    /************************************************************************/


    /* ---------------------- Personne seule -------------------------------*/
    public Bareme personneSeule(int annee) {
        if (annee >= 2024)
            throw new IllegalArgumentException("Le barème IFD pour les personnes seules n'est pas encore défini pour l'année " + annee);
        if (2023 <= annee) {
            return personneSeule2023();
        }
        if (2012 <= annee && 2022 >= annee) {
            return personneSeule2012();
        }
        if (2011 == annee) {
            return personneSeule2011();
        }
        if (2006 <= annee && 2010 >= annee) {
            return personneSeule2006();
        }
        if (1996 <= annee && 2005 >= annee) {
            return personneSeule1996();
        }
        throw new IllegalArgumentException("Le barème IFD pour les personnes seules n'est pas défini pour l'année " + annee);
    }

    /**
     * Adapté à l'indice de juin 2022: 165,2
     * barème 2012 étiré de 2.04 %
     * @return Constructeur pour les périodes fiscales 2023
     */
    private Bareme personneSeule2023() {
        return new ConstructeurBaremeIFD()
                .jusqua(14800).a("0.00").etPar100FrancsEnPlus("0.77")
                .pour(32200).a("133.95").etPar100FrancsEnPlus("0.88")
                .pour(42200).a("221.95").etPar100FrancsEnPlus("2.64")
                .pour(56200).a("591.55").etPar100FrancsEnPlus("2.97")
                .pour(73900).a("1117.20").etPar100FrancsEnPlus("5.94")
                .pour(79_600).a("1455.75").etPar100FrancsEnPlus("6.60")
                .pour(105_500).a("3165.15").etPar100FrancsEnPlus("8.80")
                .pour(137_200).a("5954.75").etPar100FrancsEnPlus("11.00")
                .pour(179_400).a("10596.75").etPar100FrancsEnPlus("13.20")
                .tauxEffectifMax("11.5 %").construire();
    }

    /**
     * Adapté à l'indice de juin 2011: 161,9
     * barème 2011 étiré de 0.6 %
     * @return Constructeur pour les périodes fiscales 2012, 2013
     */
    private Bareme personneSeule2012() {
        return new ConstructeurBaremeIFD()
                .jusqua(14500).a("0.00").etPar100FrancsEnPlus("0.77")
                .pour(31600).a("131.65").etPar100FrancsEnPlus("0.88")
                .pour(41400).a("217.90").etPar100FrancsEnPlus("2.64")
                .pour(55200).a("582.20").etPar100FrancsEnPlus("2.97")
                .pour(72500).a("1096.00").etPar100FrancsEnPlus("5.94")
                .pour(78100).a("1428.60").etPar100FrancsEnPlus("6.60")
                .pour(103600).a("3111.60").etPar100FrancsEnPlus("8.80")
                .pour(134600).a("5839.60").etPar100FrancsEnPlus("11.00")
                .pour(176000).a("10393.60").etPar100FrancsEnPlus("13.20")
                .tauxEffectifMax("11.5 %").construire();
    }

    /**
     * Adapté à l'indice de juin 2010: 161,0
     * barème 2006 étiré de 5,2 %
     * @return Constructeur pour la période fiscale 2011
     */
    private Bareme personneSeule2011() {
        return new ConstructeurBaremeIFD()
                .jusqua(14400).a("0.00").etPar100FrancsEnPlus("0.77")
                .pour(31500).a("131.65").etPar100FrancsEnPlus("0.88")
                .pour(41200).a("217.00").etPar100FrancsEnPlus("2.64")
                .pour(55000).a("581.30").etPar100FrancsEnPlus("2.97")
                .pour(72200).a("1092.10").etPar100FrancsEnPlus("5.94")
                .pour(77700).a("1418.80").etPar100FrancsEnPlus("6.60")
                .pour(103000).a("3088.60").etPar100FrancsEnPlus("8.80")
                .pour(133900).a("5807.80").etPar100FrancsEnPlus("11.00")
                .pour(175000).a("10328.80").etPar100FrancsEnPlus("13.20")
                .tauxEffectifMax("11.5 %").construire();
    }

    /**
     * Adapté à l'indice de décembre 2004: 153,1
     * barème 1996 étiré de 7,6 %
     * @return Constructeur pour les périodes fiscales 2006, 2007, 2008, 2009, 2010
     */
    private Bareme personneSeule2006() {
        return new ConstructeurBaremeIFD()
                .jusqua(13600).a("0.00").etPar100FrancsEnPlus("0.77")
                .pour(29800).a("124.70").etPar100FrancsEnPlus("0.88")
                .pour(39000).a("205.65").etPar100FrancsEnPlus("2.64")
                .pour(52000).a("548.85").etPar100FrancsEnPlus("2.97")
                .pour(68300).a("1032.95").etPar100FrancsEnPlus("5.94")
                .pour(73600).a("1347.75").etPar100FrancsEnPlus("6.60")
                .pour(97700).a("2938.35").etPar100FrancsEnPlus("8.80")
                .pour(127100).a("5525.55").etPar100FrancsEnPlus("11.00")
                .pour(166200).a("9826.55").etPar100FrancsEnPlus("13.20")
                .tauxEffectifMax("11.5 %").construire();
    }

    /**
     * Adapté à l'indice de décembre 1995: 142,3
     * barème 1993 étiré de 8.5 %
     * @return Constructeur pour les périodes fiscales 1996, 1997, 1998, 1999, 2000, 2001, 2002, 2003, 2004 et 2005
     */
    private Bareme personneSeule1996() {
        return new ConstructeurBaremeIFD()
                .jusqua(12800).a("0.00").etPar100FrancsEnPlus("0.77")
                .pour(27900).a("116.25").etPar100FrancsEnPlus("0.88")
                .pour(36500).a("191.90").etPar100FrancsEnPlus("2.64")
                .pour(48600).a("511.30").etPar100FrancsEnPlus("2.97")
                .pour(63800).a("962.70").etPar100FrancsEnPlus("5.94")
                .pour(68800).a("1259.70").etPar100FrancsEnPlus("6.60")
                .pour(91100).a("2731.50").etPar100FrancsEnPlus("8.80")
                .pour(118400).a("5133.90").etPar100FrancsEnPlus("11.00")
                .pour(154700).a("9126.90").etPar100FrancsEnPlus("13.20")
                .tauxEffectifMax("11.5 %").construire();
    }


    /* --------------------------- Famille -------------------------------*/


    public Bareme famille(int annee) {
        if (annee >= 2023)
            throw new IllegalArgumentException("Le barème IFD pour les familles n'est pas encore défini pour l'année " + annee);
        if (2012 <= annee && 2022 >= annee) {
            return famille2012();
        }
        if (2011 == annee) {
            return famille2011();
        }
        if (2006 <= annee && 2010 >= annee) {
            return famille2006();
        }
        if (1996 <= annee && 2005 >= annee) {
            return famille1996();
        }
        throw new IllegalArgumentException("Le barème IFD pour les familles n'est pas défini pour l'année " + annee);
    }

    /**
     * Adapté à l'indice de juin 2011: 161,9
     * barème 2011 étiré de 0.6 %
     * @return Constructeur pour les périodes fiscales 2012, 2013
     */
    private Bareme famille2012() {
        return new ConstructeurBaremeIFD()
                .jusqua(28300).a("0.00").etPar100FrancsEnPlus("1.00")
                .pour(50900).a("226.00").etPar100FrancsEnPlus("2.00")
                .pour(58400).a("376.00").etPar100FrancsEnPlus("3.00")
                .pour(75300).a("883.00").etPar100FrancsEnPlus("4.00")
                .pour(90300).a("1483.00").etPar100FrancsEnPlus("5.00")
                .pour(103400).a("2138.00").etPar100FrancsEnPlus("6.00")
                .pour(114700).a("2816.00").etPar100FrancsEnPlus("7.00")
                .pour(124200).a("3481.00").etPar100FrancsEnPlus("8.00")
                .pour(131700).a("4081.00").etPar100FrancsEnPlus("9.00")
                .pour(137300).a("4585.00").etPar100FrancsEnPlus("10.00")
                .pour(141200).a("4975.00").etPar100FrancsEnPlus("11.00")
                .pour(143100).a("5184.00").etPar100FrancsEnPlus("12.00")
                .pour(145000).a("5412.00").etPar100FrancsEnPlus("13.00")
                .tauxEffectifMax("11.5 %").construire();
    }

    /**
     * Adapté à l'indice de juin 2010: 161,0
     * barème 2006 étiré de 5,2 %
     * @return Constructeur pour la période fiscale 2011
     */
    private Bareme famille2011() {
        return new ConstructeurBaremeIFD()
                .jusqua(28100).a("0.00").etPar100FrancsEnPlus("1.00")
                .pour(50400).a("223.00").etPar100FrancsEnPlus("2.00")
                .pour(57900).a("373.00").etPar100FrancsEnPlus("3.00")
                .pour(74700).a("877.00").etPar100FrancsEnPlus("4.00")
                .pour(89700).a("1477.00").etPar100FrancsEnPlus("5.00")
                .pour(102700).a("2127.00").etPar100FrancsEnPlus("6.00")
                .pour(113900).a("2799.00").etPar100FrancsEnPlus("7.00")
                .pour(123300).a("3457.00").etPar100FrancsEnPlus("8.00")
                .pour(130800).a("4057.00").etPar100FrancsEnPlus("9.00")
                .pour(136300).a("4552.00").etPar100FrancsEnPlus("10.00")
                .pour(140200).a("4942.00").etPar100FrancsEnPlus("11.00")
                .pour(142100).a("5151.00").etPar100FrancsEnPlus("12.00")
                .pour(144000).a("5379.00").etPar100FrancsEnPlus("13.00")
                .tauxEffectifMax("11.5 %").construire();
    }

    /**
     * Adapté à l'indice de décembre 2004: 153,1
     * barème 1996 étiré de 7,6 %
     * @return Constructeur pour les périodes fiscales 2006, 2007, 2008, 2009, 2010
     */
    private Bareme famille2006() {
        return new ConstructeurBaremeIFD()
                .jusqua(26700).a("0.00").etPar100FrancsEnPlus("1.00")
                .pour(47900).a("212.00").etPar100FrancsEnPlus("2.00")
                .pour(54900).a("352.00").etPar100FrancsEnPlus("3.00")
                .pour(70900).a("832.00").etPar100FrancsEnPlus("4.00")
                .pour(85100).a("1400.00").etPar100FrancsEnPlus("5.00")
                .pour(97400).a("2015.00").etPar100FrancsEnPlus("6.00")
                .pour(108100).a("2657.00").etPar100FrancsEnPlus("7.00")
                .pour(117000).a("3280.00").etPar100FrancsEnPlus("8.00")
                .pour(124000).a("3840.00").etPar100FrancsEnPlus("9.00")
                .pour(129300).a("4317.00").etPar100FrancsEnPlus("10.00")
                .pour(132900).a("4677.00").etPar100FrancsEnPlus("11.00")
                .pour(134700).a("4875.00").etPar100FrancsEnPlus("12.00")
                .pour(136500).a("5091.00").etPar100FrancsEnPlus("13.00")
                .tauxEffectifMax("11.5 %").construire();
    }

    /**
     * Adapté à l'indice de décembre 1995: 142.3
     * barème 1993 étiré de 8.5 %
     * @return Constructeur pour les périodes fiscales 1996 à 2005
     */
    private Bareme famille1996() {
        return new ConstructeurBaremeIFD()
                .jusqua(24900).a("0.00").etPar100FrancsEnPlus("1.00")
                .pour(44700).a("198.00").etPar100FrancsEnPlus("2.00")
                .pour(51300).a("330.00").etPar100FrancsEnPlus("3.00")
                .pour(66200).a("777.00").etPar100FrancsEnPlus("4.00")
                .pour(79400).a("1305.00").etPar100FrancsEnPlus("5.00")
                .pour(91000).a("1885.00").etPar100FrancsEnPlus("6.00")
                .pour(101000).a("2485.00").etPar100FrancsEnPlus("7.00")
                .pour(109300).a("3066.00").etPar100FrancsEnPlus("8.00")
                .pour(115900).a("3594.00").etPar100FrancsEnPlus("9.00")
                .pour(120900).a("4044.00").etPar100FrancsEnPlus("10.00")
                .pour(124300).a("4384.00").etPar100FrancsEnPlus("11.00")
                .pour(126000).a("4571.00").etPar100FrancsEnPlus("12.00")
                .pour(127700).a("4775.00").etPar100FrancsEnPlus("13.00")
                .tauxEffectifMax("11.5 %").construire();
    }

}
