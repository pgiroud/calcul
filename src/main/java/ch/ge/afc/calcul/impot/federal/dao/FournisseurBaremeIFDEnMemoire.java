package ch.ge.afc.calcul.impot.federal.dao;

import ch.ge.afc.bareme.Bareme;
import ch.ge.afc.bareme.BaremeTxMarginalEtEffectifParTranche;
import ch.ge.afc.util.TypeArrondi;

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
    private Bareme construireBareme(String[] taux, int[] tranches) {
        BaremeTxMarginalEtEffectifParTranche.Constructeur constructeur = new BaremeTxMarginalEtEffectifParTranche.Constructeur();
        for (int i = 0; i < taux.length; i++) {
            constructeur.tranche( tranches[i], taux[i]);
        }
        constructeur.derniereTranche(TAUX_EFFECTIF_MAXIMUM);
        constructeur.typeArrondi(TypeArrondi.CINQ_CTS_INF).seuil(25);
        return constructeur.construire();
    }

    @Override
    public Bareme getBaremeImpotRevenuPersonnePhysiquePourPersonneSeule(int annee) {
        int[] tranches = obtenirTranchesPostNumerando(annee,TRANCHES_SEUL);
        return construireBareme(TAUX_SEUL,tranches);
    }

    @Override
    public Bareme getBaremeImpotRevenuPersonnePhysiquePourFamille(int annee) {
        int[] tranches = obtenirTranchesPostNumerando(annee,TRANCHES_FAMILLE);
        return construireBareme(TAUX_FAMILLE,tranches);
    }

    @Override
    public Bareme getBaremeImpotRevenuPraeNumerandoPersonnePhysiquePourPersonneSeule(int annee) {
        int[] tranches = obtenirTranchesPraeNumerando(annee,TRANCHES_SEUL_PRAE);
        return construireBareme(TAUX_SEUL,tranches);
    }

    @Override
    public Bareme getBaremeImpotRevenuPraeNumerandoPersonnePhysiquePourFamille(int annee) {
        int[] tranches = obtenirTranchesPraeNumerando(annee,TRANCHES_FAMILLE_PRAE);
        return construireBareme(TAUX_FAMILLE,tranches);
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
        constructeur.typeArrondi(TypeArrondi.CINQ_CTS_INF);
        return constructeur.construire();
    }

}
