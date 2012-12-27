package org.impotch.calcul.impot.federal.dao;

import org.impotch.bareme.Bareme;
import org.impotch.bareme.BaremeTxMarginalEtEffectifParTranche;
import org.impotch.util.TypeArrondi;

/**
 * Created by IntelliJ IDEA.
 * User: patrick
 * Date: 15/09/11
 * Time: 21:50
 * To change this template use File | Settings | File Templates.
 */
public class FournisseurBaremeIFDEnMemoire implements FournisseurBaremeIFD {
    private static String[] TAUX_SEUL = new String[]{
            "0",    "0.77 %",   "0.88 %",   "2.64 %",   "2.97 %",   "5.94 %",   "6.60 %",   "8.80 %",   "11.00 %",  "13.20 %"};
    private static final int[][] TRANCHES_SEUL = new int[][]{
            {14500,  31600,      41400,      55200,      72500,      78100,      103600,     134600,     176000,     755200}, // 2012
            {14400,  31500,      41200,      55000,      72200,      77700,      103000,     133900,     175000,     751200}, // 2011
            {13600,  29800,      39000,      52000,      68300,      73600,      97700,      127100,     166200,     712400}, // 2006
            {12800,  27900,      36500,      48600,      63800,      68800,      91100,      118400,     154700,     664300}}; // 1996

    private static final int[][] TRANCHES_SEUL_PRAE = new int[][]{
            {13200,  28700,      37600,      50100,      65800,      70900,      94100,      122300,     159900,     685900},   // 2012
            {13100,  28600,      37400,      49900,      65500,      70500,      93500,      121600,     159000,     682100}, // 2011
            {12600,  27400,      35900,      47900,      62900,      67700,      89800,      116800,     152700,     655000},  // 2007
            {11600,  25200,      33000,      44000,      57800,      62300,      82600,      107400,     140400,     603000}}; // 1997


    private static String[] TAUX_FAMILLE = new String[]{
            "0",    "1.00 %",   "2.00 %",   "3.00 %",   "4.00 %",   "5.00 %",   "6.00 %",   "7.00 %",   "8.00 %",   "9.00 %",   "10.00 %",  "11.00 %",  "12.00 %",  "13.00 %"};
    private static final int[][] TRANCHES_FAMILLE = new int[][]{
            {28300,  50900,      58400,      75300,      90300,      103400,     114700,     124200,     131700,     137300,     141200,     143100,     145000,     895800}, // 2012
            {28100,  50400,      57900,      74700,      89700,      102700,     113900,     123300,     130800,     136300,     140200,     142100,     144000,     889400}, // 2011
            {26700,  47900,      54900,      70900,      85100,      97400,      108100,     117000,     124000,     129300,     132900,     134700,     136500,     843600}, // 2006
            {24900,  44700,      51300,      66200,      79400,      91000,      101000,     109300,     115900,     120900,     124300,     126000,     127700,     788400}}; // 1996

    private static final int[][] TRANCHES_FAMILLE_PRAE = new int[][] {
            {25700,  46200,      53000,      68400,      82000,      93900,     104200,      112800,     119600,     124700,     128200,     129900,     131600,     813400}, // 2012
            {25500,  45800,      52600,      67900,      81500,      93300,     103500,      112000,     118800,     123800,     127300,     129000,     130700,     807800}, // 2011
            {24500,  44000,      50500,      65200,      78200,      89600,     99400,       107600,     114100,     118900,     122200,     123900,     125600,     775900}, // 2007
            {22600,  40500,      46500,      60000,      72000,      82500,     91600,       99100,      105100,     109600,     112700,     114200,     115700,     715500}}; // 1997

    private static final String TAUX_EFFECTIF_MAXIMUM = "11.50 %";

    private int[] obtenirTranchesPostNumerando(int annee, int[][] tranches) {
        if (annee < 1996) throw new IllegalArgumentException("Le barème IFD postnumerando n'est pas défini pour l'année '" + annee + ", il n'est valable qu'à partir de 1996'.");
        if (annee < 2006) return tranches[tranches.length-1];
        if (annee < 2011) return tranches[tranches.length-2];
        if (annee - 2008 > tranches.length) throw new IllegalArgumentException("Le barème IFD postnumerando n'est pas encore défini pour l'année '" + annee + "'.");
        else return tranches[tranches.length + 2008 - annee];
    }

    private int[] obtenirTranchesPraeNumerando(int annee, int[][] tranches) {
        if (annee < 1997) throw new IllegalArgumentException("Le barème IFD praenumerando n'est pas défini pour l'année '" + annee + ", il n'est valable qu'à partir de 1997'.");
        if (annee < 2007) return tranches[tranches.length-1];
        if (annee < 2011) return tranches[tranches.length-2];
        if (annee - 2008 > tranches.length) throw new IllegalArgumentException("Le barème IFD praenumerando n'est pas encore défini pour l'année '" + annee + "'.");
        else return tranches[tranches.length + 2008 - annee];
    }

    /**
     * Tous les barèmes IFD revenu personne physique sont des barèmes à taux marginal jusqu'à la dernière tranche qui est exprimée
     * en taux effectif.
     * @param taux la liste des taux. On utilise des chaîne de caractère pour pouvoir écrire les pourcentages.
     * @param tranches la liste des tranches c.-à-d. les montants imposables pour lesquels il y a un changement de taux marginal.
     * @return Un barème permettant de calculer un impôt à partir d'une assiette.
     */
    private Bareme construireBareme(String[] taux, int[] tranches, int annee) {
        BaremeTxMarginalEtEffectifParTranche.Constructeur constructeur = new BaremeTxMarginalEtEffectifParTranche.Constructeur();
        for (int i = 0; i < taux.length; i++) {
            constructeur.tranche( tranches[i], taux[i]);
        }
        constructeur.derniereTranche(TAUX_EFFECTIF_MAXIMUM);
        if (annee < 2012) {
            constructeur.typeArrondiSurChaqueTranche(TypeArrondi.CINQ_CTS_INF);
        } else {
            constructeur.typeArrondiGlobal(TypeArrondi.CINQ_CTS_INF);
        }
        constructeur.seuil(25);
        return constructeur.construire();
    }

    private Bareme getBaremeImpotRevenuPersonnePhysiquePourPersonneSeuleNew(int annee) {
        ConstructeurBaremeIFD constructeur = null;
        if (2012 == annee || 2013 == annee) {
            constructeur = this.getConstructeurBaremeImpotRevenuPersonnePhysiquePersonneSeule2012();
        }
        if (2011 == annee) {
            constructeur = this.getConstructeurBaremeImpotRevenuPersonnePhysiquePersonneSeule2011();
        }
        constructeur.typeArrondiSurChaqueTranche(TypeArrondi.CINQ_CTS_INF);
        constructeur.seuil(25);
        return constructeur.construire();
    }

    @Override
    public Bareme getBaremeImpotRevenuPersonnePhysiquePourPersonneSeule(int annee) {
        if (2011 <= annee) {
            return getBaremeImpotRevenuPersonnePhysiquePourPersonneSeuleNew(annee);
        }
        int[] tranches = obtenirTranchesPostNumerando(annee,TRANCHES_SEUL);
        return construireBareme(TAUX_SEUL,tranches,annee);
    }

    public Bareme getBaremeImpotRevenuPersonnePhysiquePourFamilleNew(int annee) {
        ConstructeurBaremeIFD constructeur = null;
        if (2012 == annee) {
            constructeur = this.getConstructeurBaremeImpotRevenuPersonnePhysiqueFamille2012();
        }
        if (2011 == annee) {
            constructeur = this.getConstructeurBaremeImpotRevenuPersonnePhysiqueFamille2011();
        }
        constructeur.typeArrondiSurChaqueTranche(TypeArrondi.CINQ_CTS_INF);
        constructeur.seuil(25);
        return constructeur.construire();
    }


    @Override
    public Bareme getBaremeImpotRevenuPersonnePhysiquePourFamille(int annee) {
        if (2011 <= annee) {
            return getBaremeImpotRevenuPersonnePhysiquePourFamilleNew(annee);
        }
        int[] tranches = obtenirTranchesPostNumerando(annee,TRANCHES_FAMILLE);
        return construireBareme(TAUX_FAMILLE,tranches,annee);
    }

    @Override
    public Bareme getBaremeImpotRevenuPraeNumerandoPersonnePhysiquePourPersonneSeule(int annee) {
        int[] tranches = obtenirTranchesPraeNumerando(annee,TRANCHES_SEUL_PRAE);
        return construireBareme(TAUX_SEUL,tranches,annee);
    }

    @Override
    public Bareme getBaremeImpotRevenuPraeNumerandoPersonnePhysiquePourFamille(int annee) {
        int[] tranches = obtenirTranchesPraeNumerando(annee,TRANCHES_FAMILLE_PRAE);
        return construireBareme(TAUX_FAMILLE,tranches,annee);
    }

    @Override
    public Bareme getBaremeImpotSourcePrestationCapital(int annee) {
        BaremeTxMarginalEtEffectifParTranche.Constructeur constructeur = new BaremeTxMarginalEtEffectifParTranche.Constructeur();
        if (annee < 2011) {
            constructeur.tranche(  25000,  "0");
            constructeur.tranche(  50000,  "0.25 %");
            constructeur.tranche(  75000,  "0.65 %");
            constructeur.tranche( 100000,  "1.10 %");
            constructeur.tranche( 125000,  "1.70 %");
            constructeur.tranche( 775000,  "2.60 %");
        } else {
            constructeur.tranche(  25000,  "0");
            constructeur.tranche(  50000,  "0.20 %");
            constructeur.tranche(  75000,  "0.55 %");
            constructeur.tranche( 100000,  "0.90 %");
            constructeur.tranche( 125000,  "1.30 %");
            constructeur.tranche( 150000,  "2.05 %");
            constructeur.tranche( 890000,  "2.60 %");
        }
        constructeur.derniereTranche( "2.30 %");
        constructeur.typeArrondiSurChaqueTranche(TypeArrondi.CINQ_CTS_INF);
        return constructeur.construire();
    }

    /************************************************************************/
    /************** Barèmes Post numerando (art. 214 LIFD) ******************/
    /************************************************************************/


    private ConstructeurBaremeIFD getConstructeurBaremeImpotRevenuPersonnePhysiquePersonneSeule2012() {
        ConstructeurBaremeIFD constructeur = new ConstructeurBaremeIFD();
        constructeur.jusqua(14500).a("0.00").etPar100FrancsEnPlus("0.77");
        constructeur.pour(31600).a("131.65").etPar100FrancsEnPlus("0.88");
        constructeur.pour(41400).a("217.90").etPar100FrancsEnPlus("2.64");
        constructeur.pour(55200).a("582.20").etPar100FrancsEnPlus("2.97");
        constructeur.pour(72500).a("1096.00").etPar100FrancsEnPlus("5.94");
        constructeur.pour(78100).a("1428.60").etPar100FrancsEnPlus("6.60");
        constructeur.pour(103600).a("3111.60").etPar100FrancsEnPlus("8.80");
        constructeur.pour(134600).a("5839.60").etPar100FrancsEnPlus("11.00");
        constructeur.pour(176000).a("10393.60").etPar100FrancsEnPlus("13.20");
        constructeur.pour(755200).a("86848.00").etPar100FrancsEnPlus("11.50");
        return constructeur;
    }

    private ConstructeurBaremeIFD getConstructeurBaremeImpotRevenuPersonnePhysiqueFamille2012() {
        ConstructeurBaremeIFD constructeur = new ConstructeurBaremeIFD();
        constructeur.jusqua(28300).a("0.00").etPar100FrancsEnPlus("1.00");
        constructeur.pour(50900).a("226.00").etPar100FrancsEnPlus("2.00");
        constructeur.pour(58400).a("376.00").etPar100FrancsEnPlus("3.00");
        constructeur.pour(75300).a("883.00").etPar100FrancsEnPlus("4.00");
        constructeur.pour(90300).a("1483.00").etPar100FrancsEnPlus("5.00");
        constructeur.pour(103400).a("2138.00").etPar100FrancsEnPlus("6.00");
        constructeur.pour(114700).a("2816.00").etPar100FrancsEnPlus("7.00");
        constructeur.pour(124200).a("3481.00").etPar100FrancsEnPlus("8.00");
        constructeur.pour(131700).a("4081.00").etPar100FrancsEnPlus("9.00");
        constructeur.pour(137300).a("4585.00").etPar100FrancsEnPlus("10.00");
        constructeur.pour(141200).a("4975.00").etPar100FrancsEnPlus("11.00");
        constructeur.pour(143100).a("5184.00").etPar100FrancsEnPlus("12.00");
        constructeur.pour(145000).a("5412.00").etPar100FrancsEnPlus("13.00");
        constructeur.pour(895800).a("103016.00");
        constructeur.pour(895900).a("103028.50").etPar100FrancsEnPlus("11.50");
        return constructeur;
    }

    /**
     * Adapté à l'indice de juin 2010: 161,0
     * barème 2006 étiré de 5,2 %
     * @return  Constructeur pour la période fiscale 2011
     */
    private ConstructeurBaremeIFD getConstructeurBaremeImpotRevenuPersonnePhysiquePersonneSeule2011() {
        ConstructeurBaremeIFD constructeur = new ConstructeurBaremeIFD();
        constructeur.jusqua(14400).a("0.00").etPar100FrancsEnPlus("0.77");
        constructeur.pour(31500).a("131.65").etPar100FrancsEnPlus("0.88");
        constructeur.pour(41200).a("217.00").etPar100FrancsEnPlus("2.64");
        constructeur.pour(55000).a("581.30").etPar100FrancsEnPlus("2.97");
        constructeur.pour(72200).a("1092.10").etPar100FrancsEnPlus("5.94");
        constructeur.pour(77700).a("1418.80").etPar100FrancsEnPlus("6.60");
        constructeur.pour(103000).a("3088.60").etPar100FrancsEnPlus("8.80");
        constructeur.pour(133900).a("5807.80").etPar100FrancsEnPlus("11.00");
        constructeur.pour(175000).a("10328.80").etPar100FrancsEnPlus("13.20");
        constructeur.pour(751200).a("86387.20");
        constructeur.pour(751300).a("86399.50").etPar100FrancsEnPlus("11.50");
        return constructeur;
    }

    /**
     * Adapté à l'indice de juin 2010: 161,0
     * barème 2006 étiré de 5,2 %
     * @return  Constructeur pour la période fiscale 2011
     */
    private ConstructeurBaremeIFD getConstructeurBaremeImpotRevenuPersonnePhysiqueFamille2011() {
        ConstructeurBaremeIFD constructeur = new ConstructeurBaremeIFD();
        constructeur.jusqua(28100).a("0.00").etPar100FrancsEnPlus("1.00");
        constructeur.pour(50400).a("223.00").etPar100FrancsEnPlus("2.00");
        constructeur.pour(57900).a("373.00").etPar100FrancsEnPlus("3.00");
        constructeur.pour(74700).a("877.00").etPar100FrancsEnPlus("4.00");
        constructeur.pour(89700).a("1477.00").etPar100FrancsEnPlus("5.00");
        constructeur.pour(102700).a("2127.00").etPar100FrancsEnPlus("6.00");
        constructeur.pour(113900).a("2799.00").etPar100FrancsEnPlus("7.00");
        constructeur.pour(123300).a("3457.00").etPar100FrancsEnPlus("8.00");
        constructeur.pour(130800).a("4057.00").etPar100FrancsEnPlus("9.00");
        constructeur.pour(136300).a("4552.00").etPar100FrancsEnPlus("10.00");
        constructeur.pour(140200).a("4942.00").etPar100FrancsEnPlus("11.00");
        constructeur.pour(142100).a("5151.00").etPar100FrancsEnPlus("12.00");
        constructeur.pour(144000).a("5379.00").etPar100FrancsEnPlus("13.00");
        constructeur.pour(889400).a("102281.00").etPar100FrancsEnPlus("11.50");
        return constructeur;
    }

    /**
     * Adapté à l'indice de décembre 2004: 153,1
     * barème 1996 étiré de 7,6 %
     * @return Constructeur pour les périodes fiscales 2006, 2007, 2008, 2009, 2010
     */
    private ConstructeurBaremeIFD getConstructeurBaremeImpotRevenuPersonnePhysiquePersonneSeule2006() {
        ConstructeurBaremeIFD constructeur = new ConstructeurBaremeIFD();
        constructeur.jusqua(13600).a("0.00").etPar100FrancsEnPlus("0.77");
        constructeur.pour(29800).a("124.70").etPar100FrancsEnPlus("0.88");
        constructeur.pour(39000).a("205.65").etPar100FrancsEnPlus("2.64");
        constructeur.pour(52000).a("548.85").etPar100FrancsEnPlus("2.97");
        constructeur.pour(68300).a("1032.95").etPar100FrancsEnPlus("5.94");
        constructeur.pour(73600).a("1347.75").etPar100FrancsEnPlus("6.60");
        constructeur.pour(97700).a("2938.35").etPar100FrancsEnPlus("8.80");
        constructeur.pour(127100).a(" 5525.55").etPar100FrancsEnPlus("11.00");
        constructeur.pour(166200).a("9826.55").etPar100FrancsEnPlus("13.20");
        constructeur.pour(712400).a(" 81924.95").etPar100FrancsEnPlus("11.50");
        return constructeur;
    }

    /**
     * Adapté à l'indice de décembre 2004: 153,1
     * barème 1996 étiré de 7,6 %
     * @return Constructeur pour les périodes fiscales 2006, 2007, 2008, 2009, 2010
     */
    private ConstructeurBaremeIFD getConstructeurBaremeImpotRevenuPersonnePhysiqueFamille2006() {
        ConstructeurBaremeIFD constructeur = new ConstructeurBaremeIFD();
        constructeur.jusqua(26700).a("0.00").etPar100FrancsEnPlus("1.00");
        constructeur.pour(47900).a("212.00").etPar100FrancsEnPlus("2.00");
        constructeur.pour(54900).a("352.00").etPar100FrancsEnPlus("3.00");
        constructeur.pour(70900).a("832.00").etPar100FrancsEnPlus("4.00");
        constructeur.pour(85100).a("1400.00").etPar100FrancsEnPlus("5.00");
        constructeur.pour(97400).a("2015.00").etPar100FrancsEnPlus("6.00");
        constructeur.pour(108100).a("2657.00").etPar100FrancsEnPlus("7.00");
        constructeur.pour(117000).a("3280.00").etPar100FrancsEnPlus("8.00");
        constructeur.pour(124000).a("3840.00").etPar100FrancsEnPlus("9.00");
        constructeur.pour(129300).a("4317.00").etPar100FrancsEnPlus("10.00");
        constructeur.pour(132900).a("4677.00").etPar100FrancsEnPlus("11.00");
        constructeur.pour(134700).a("4875.00").etPar100FrancsEnPlus("12.00");
        constructeur.pour(136500).a("5091.00").etPar100FrancsEnPlus("13.00");
        constructeur.pour(843600).a("97014.00").etPar100FrancsEnPlus("11.50");
        return constructeur;
    }

    /************************************************************************/
    /*************** Barèmes Prae numerando (art. 36 LIFD) ******************/
    /************************************************************************/

    private void completerBaremeImpotRevenuPraeNumerandoPersonnePhysiquePourPersonneSeule2012(ConstructeurBaremeIFD constructeur) {

    }

    private void completerBaremeImpotRevenuPraeNumerandoPersonnePhysiquePourFamille2012(ConstructeurBaremeIFD constructeur) {

    }


//
//    constructeur.jusqua().a("0.00").etPar100FrancsEnPlus("0.77");
//    constructeur.pour().a("").etPar100FrancsEnPlus("0.88");
//    constructeur.pour().a("").etPar100FrancsEnPlus("2.64");
//    constructeur.pour().a("").etPar100FrancsEnPlus("2.97");
//    constructeur.pour().a("").etPar100FrancsEnPlus("5.94");
//    constructeur.pour().a("").etPar100FrancsEnPlus("6.60");
//    constructeur.pour().a("").etPar100FrancsEnPlus("8.80");
//    constructeur.pour().a("").etPar100FrancsEnPlus("11.00");
//    constructeur.pour().a("").etPar100FrancsEnPlus("13.20");
//    constructeur.pour().a("").etPar100FrancsEnPlus("11.50");
//
//
//    constructeur.jusqua().a("0.00").etPar100FrancsEnPlus("1.00");
//    constructeur.pour().a("").etPar100FrancsEnPlus("2.00");
//    constructeur.pour().a("").etPar100FrancsEnPlus("3.00");
//    constructeur.pour().a("").etPar100FrancsEnPlus("4.00");
//    constructeur.pour().a("").etPar100FrancsEnPlus("5.00");
//    constructeur.pour().a("").etPar100FrancsEnPlus("6.00");
//    constructeur.pour().a("").etPar100FrancsEnPlus("7.00");
//    constructeur.pour().a("").etPar100FrancsEnPlus("8.00");
//    constructeur.pour().a("").etPar100FrancsEnPlus("9.00");
//    constructeur.pour().a("").etPar100FrancsEnPlus("10.00");
//    constructeur.pour().a("").etPar100FrancsEnPlus("11.00");
//    constructeur.pour().a("").etPar100FrancsEnPlus("12.00");
//    constructeur.pour().a("").etPar100FrancsEnPlus("13.00");
//    constructeur.pour().a("").etPar100FrancsEnPlus("11.50");
//

}
