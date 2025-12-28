/*
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
package org.impotch.calcul.impot.federal.param;

import org.impotch.bareme.Bareme;
import org.impotch.util.TypeArrondi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.impotch.calcul.impot.federal.param.ConstructeurBaremeIFD.unBaremeIFD;

class IFDPostNumerando {

    final Logger logger = LoggerFactory.getLogger(IFDPostNumerando.class);

    private Map<Integer,Integer> baseAnneeNonAdaptee = new HashMap<>();

    public IFDPostNumerando() {
        sansAdaptation(1996,2005);
        sansAdaptation(2006,2010);
        sansAdaptation(2012,2022);
        // À partir de 2023, l’adaptation est annuelle (à moins d’une inflation à 0)
    }

    private void sansAdaptation(int anneeBase, int derniereAnneeNonAdaptee) {
        IntStream.rangeClosed(anneeBase+1,derniereAnneeNonAdaptee).forEach(anneeNonAdaptee -> baseAnneeNonAdaptee.put(anneeNonAdaptee,anneeBase));
    }

    /************************************************************************/
    /************** Barèmes Post numerando (art. 214 LIFD) ******************/
    /************************************************************************/

    private Optional<Bareme> bareme(int annee, TypeArrondi arrondiSurChaqueTranche, String nomMethode) {
        int anneeBase = baseAnneeNonAdaptee.getOrDefault(annee,annee);
        String methodeName = nomMethode + anneeBase;
        ConstructeurBaremeIFD constructeurBaremeIFD = unBaremeIFD(arrondiSurChaqueTranche);
        try {
            Method methodeAnnuelle = IFDPostNumerando.class.getDeclaredMethod(methodeName,ConstructeurBaremeIFD.class);
            return Optional.of((Bareme)methodeAnnuelle.invoke(this,constructeurBaremeIFD));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException nsme) {
            logger.error("Impossible d’invoquer la méthode " + methodeName + " de la classe " + this.getClass().getCanonicalName());
            logger.error("Le barème IFD n'est pas encore défini pour l'année " + annee);
            return Optional.empty();
        }
    }


    public Optional<Bareme> personneSeule(int annee, TypeArrondi arrondiSurChaqueTranche) {
        return bareme(annee,arrondiSurChaqueTranche,"tranchesPersonneSeule");
    }


    public Optional<Bareme> famille(int annee, TypeArrondi arrondiSurChaqueTranche) {
        return bareme(annee,arrondiSurChaqueTranche,"tranchesFamille");
    }


    // Attention, on utilise ici de l’introspection pour découvrir les méthodes annuelles
    // Dans l’IDE, les méthodes ci-dessous sont indiquées comme non utilisées : ceci est faux !
    // Mode d’emploi :
    // Par année YYYY, ajouter 2 méthodes tranchesPersonneSeuleYYYY et tranchesFamilleYYYY

    // Canevas

    // ******************************************************************
    // ************************* YYYY ***********************************
    // ******************************************************************

//    private Bareme tranchesPersonneSeuleYYYY(ConstructeurBaremeIFD constructeurBaremeIFD) {
//        return constructeurBaremeIFD
//                .jusqua().a("0.00").etPar100FrancsEnPlus("0.77")
//                .pour().a("").etPar100FrancsEnPlus("0.88")
//                .pour().a("").etPar100FrancsEnPlus("2.64")
//                .pour().a("").etPar100FrancsEnPlus("2.97")
//                .pour().a("").etPar100FrancsEnPlus("5.94")
//                .pour().a("").etPar100FrancsEnPlus("6.60")
//                .pour().a("").etPar100FrancsEnPlus("8.80")
//                .pour().a("").etPar100FrancsEnPlus("11.00")
//                .pour().a("").etPar100FrancsEnPlus("13.20")
//                .tauxEffectifMax("11.5 %").construire();
//    }
//
//    private Bareme tranchesFamilleYYYY(ConstructeurBaremeIFD constructeurBaremeIFD) {
//        return constructeurBaremeIFD
//                .jusqua().a(   "0.00").etPar100FrancsEnPlus("1.00")
//                .pour(  ).a( "").etPar100FrancsEnPlus("2.00")
//                .pour(  ).a( "").etPar100FrancsEnPlus("3.00")
//                .pour(  ).a( ".00").etPar100FrancsEnPlus("4.00")
//                .pour(  ).a(".00").etPar100FrancsEnPlus("5.00")
//                .pour( ).a(".00").etPar100FrancsEnPlus("6.00")
//                .pour( ).a(".00").etPar100FrancsEnPlus("7.00")
//                .pour( ).a(".00").etPar100FrancsEnPlus("8.00")
//                .pour( ).a(".00").etPar100FrancsEnPlus("9.00")
//                .pour( ).a(".00").etPar100FrancsEnPlus("10.00")
//                .pour( ).a(".00").etPar100FrancsEnPlus("11.00")
//                .pour( ).a(".00").etPar100FrancsEnPlus("12.00")
//                .pour( ).a(".00").etPar100FrancsEnPlus("13.00")
//                .tauxEffectifMax("11.5 %").construire();
//    }
//



    // ******************************************************************
    // ************************* 2026 ***********************************
    // ******************************************************************

    /**
     *  Au 30 juin 2025, l'indice de référence s'élevait à 170,4 points, ce qui correspond à une augmentation
     *  de 0,06 pour cent par rapport à l'indice du 30 juin 2024.
     *  Voir <a href="https://www.estv.admin.ch/dam/estv/fr/dokumente/dbst/rundschreiben/dbst-rs-2-215-d-2025-fr.pdf.download.pdf/dbst-rs-2-215-d-2025-fr.pdf">lettre circulaire n 215</a>
     *  et l’ordonnance sur la progression à froid <a href="https://www.fedlex.admin.ch/eli/oc/2025/579/fr">RO 2025 579</a>
     *
     * Attention, il y a eu une erreur sur une tranche dans la circulaire publiée (pour 76200, il était indiqué un montant d’impôt de 1152.55 alors que le
     * montant doit être 1152.50). Voir erratum <a href="https://www.fedlex.admin.ch/eli/oc/2025/621/fr">RO 2025 621</a>
     * @return barème IFD pour personne seule valable dès 2025
     */
    private Bareme tranchesPersonneSeule2026(ConstructeurBaremeIFD constructeurBaremeIFD) {
        return constructeurBaremeIFD
                .jusqua(15_200).a(    "0.00").etPar100FrancsEnPlus( "0.77")
                .pour(  33_200).a(  "138.60").etPar100FrancsEnPlus( "0.88")
                .pour(  43_500).a(  "229.20").etPar100FrancsEnPlus( "2.64")
                .pour(  58_000).a(  "612.00").etPar100FrancsEnPlus( "2.97")
                .pour(  76_200).a( "1152.50").etPar100FrancsEnPlus( "5.94")
                .pour(  82_100).a( "1502.95").etPar100FrancsEnPlus( "6.60")
                .pour( 108_900).a( "3271.75").etPar100FrancsEnPlus( "8.80")
                .pour( 141_500).a( "6140.55").etPar100FrancsEnPlus("11.00")
                .pour( 185_100).a("10936.55").etPar100FrancsEnPlus("13.20")
                .tauxEffectifMax("11.5 %").construire();
    }

    private Bareme tranchesFamille2026(ConstructeurBaremeIFD constructeurBaremeIFD) {
        return constructeurBaremeIFD
                .jusqua(29_700).a(   "0.00").etPar100FrancsEnPlus( "1.00")
                .pour(  53_400).a( "237.00").etPar100FrancsEnPlus( "2.00")
                .pour(  61_300).a( "395.00").etPar100FrancsEnPlus( "3.00")
                .pour(  79_100).a( "929.00").etPar100FrancsEnPlus( "4.00")
                .pour(  94_900).a("1561.00").etPar100FrancsEnPlus( "5.00")
                .pour( 108_700).a("2251.00").etPar100FrancsEnPlus( "6.00")
                .pour( 120_600).a("2965.00").etPar100FrancsEnPlus( "7.00")
                .pour( 130_500).a("3658.00").etPar100FrancsEnPlus( "8.00")
                .pour( 138_400).a("4290.00").etPar100FrancsEnPlus( "9.00")
                .pour( 144_300).a("4821.00").etPar100FrancsEnPlus("10.00")
                .pour( 148_300).a("5221.00").etPar100FrancsEnPlus("11.00")
                .pour( 150_400).a("5452.00").etPar100FrancsEnPlus("12.00")
                .pour( 152_400).a("5692.00").etPar100FrancsEnPlus("13.00")
                .tauxEffectifMax("11.5 %").construire();
    }

    // ******************************************************************
    // ************************* 2025 ***********************************
    // ******************************************************************

    /**
     * Adapté à l'indice de juin 2024: 170,3
     * barème 2024 étiré de 1,31 %
     * Voir Ordonnance du département fédéral des finances RS 642.119.2
     * https://www.estv.admin.ch/dam/estv/fr/dokumente/dbst/rundschreiben/dbst-rs-2-210-d-2024-fr.pdf.download.pdf/dbst-rs-2-210-d-2024-fr.pdf
     * Attention, il y a une erreur sur une tranche dans la directive fédérale.
     * Se référer à l’article 36 de la LIFD en vigueur au 1er janvier 2025
     * @return barème IFD pour personne seule valable dès 2025
     */
    private Bareme tranchesPersonneSeule2025(ConstructeurBaremeIFD constructeurBaremeIFD) {
        return constructeurBaremeIFD
                .jusqua(15_200).a(    "0.00").etPar100FrancsEnPlus( "0.77")
                .pour(  33_200).a(  "138.60").etPar100FrancsEnPlus( "0.88")
                .pour(  43_500).a(  "229.20").etPar100FrancsEnPlus( "2.64")
                .pour(  58_000).a(  "612.00").etPar100FrancsEnPlus( "2.97")
                .pour(  76_100).a( "1149.55").etPar100FrancsEnPlus( "5.94")
                .pour(  82_000).a( "1500.00").etPar100FrancsEnPlus( "6.60")
                .pour( 108_800).a( "3268.80").etPar100FrancsEnPlus( "8.80")
                .pour( 141_500).a( "6146.40").etPar100FrancsEnPlus("11.00") // Attention ici, texte français faux dans la circulaire
                .pour( 184_900).a("10920.40").etPar100FrancsEnPlus("13.20")
                .tauxEffectifMax("11.5 %").construire();
    }

    /**
     * Adapté à l'indice de juin 2024: 170,3
     * barème 2024 étiré de 1,31 %
     * Voir Ordonnance du département fédéral des finances RS 642.119.2
     * https://www.estv.admin.ch/dam/estv/fr/dokumente/dbst/rundschreiben/dbst-rs-2-210-d-2024-fr.pdf.download.pdf/dbst-rs-2-210-d-2024-fr.pdf
     * @return barème IFD pour famille valable dès 2025
     */
    private Bareme tranchesFamille2025(ConstructeurBaremeIFD constructeurBaremeIFD) {
        return constructeurBaremeIFD
                .jusqua(29_700).a(   "0.00").etPar100FrancsEnPlus( "1.00")
                .pour(  53_400).a( "237.00").etPar100FrancsEnPlus( "2.00")
                .pour(  61_300).a( "395.00").etPar100FrancsEnPlus( "3.00")
                .pour(  79_100).a( "929.00").etPar100FrancsEnPlus( "4.00")
                .pour(  94_900).a("1561.00").etPar100FrancsEnPlus( "5.00")
                .pour( 108_600).a("2246.00").etPar100FrancsEnPlus( "6.00")
                .pour( 120_500).a("2960.00").etPar100FrancsEnPlus( "7.00")
                .pour( 130_500).a("3660.00").etPar100FrancsEnPlus( "8.00")
                .pour( 138_300).a("4284.00").etPar100FrancsEnPlus( "9.00")
                .pour( 144_200).a("4815.00").etPar100FrancsEnPlus("10.00")
                .pour( 148_200).a("5215.00").etPar100FrancsEnPlus("11.00")
                .pour( 150_300).a("5446.00").etPar100FrancsEnPlus("12.00")
                .pour( 152_300).a("5686.00").etPar100FrancsEnPlus("13.00")
                .tauxEffectifMax("11.5 %").construire();
    }

    // ******************************************************************
    // ************************* 2024 ***********************************
    // ******************************************************************

    /**
     * Adapté à l'indice de juin 2023: 168,1
     * barème 2023 étiré de 1,76 %
     * Voir Ordonnance du département fédéral des finances RS 642.119.2
     * https://lex.weblaw.ch/lex.php?norm_id=642.119.2&source=SR&lex_id=87133&q=
     * @return barème IFD pour personne seule valable dès 2024
     */
    private Bareme tranchesPersonneSeule2024(ConstructeurBaremeIFD constructeurBaremeIFD) {
        return constructeurBaremeIFD
                .jusqua(15_000).a(    "0.00").etPar100FrancsEnPlus( "0.77")
                .pour(  32_800).a(  "137.05").etPar100FrancsEnPlus( "0.88")
                .pour(  42_900).a(  "225.90").etPar100FrancsEnPlus( "2.64")
                .pour(  57_200).a(  "603.40").etPar100FrancsEnPlus( "2.97")
                .pour(  75_200).a( "1138.00").etPar100FrancsEnPlus( "5.94")
                .pour(  81_000).a( "1482.50").etPar100FrancsEnPlus( "6.60")
                .pour( 107_400).a( "3224.90").etPar100FrancsEnPlus( "8.80")
                .pour( 139_600).a( "6058.50").etPar100FrancsEnPlus("11.00")
                .pour( 182_600).a("10788.50").etPar100FrancsEnPlus("13.20")
                .tauxEffectifMax("11.5 %").construire();
    }

    /**
     * Adapté à l'indice de juin 2023: 168,1
     * barème 2023 étiré de 1,76 %
     * Voir Ordonnance du département fédéral des finances RS 642.119.2
     * https://lex.weblaw.ch/lex.php?norm_id=642.119.2&source=SR&lex_id=87133&q=
     * @return le barème IFD pour famille pour l’année 2024
     */
    private Bareme tranchesFamille2024(ConstructeurBaremeIFD constructeurBaremeIFD) {
        return constructeurBaremeIFD
                .jusqua(29_300).a(   "0.00").etPar100FrancsEnPlus( "1.00")
                .pour(  52_700).a( "234.00").etPar100FrancsEnPlus( "2.00")
                .pour(  60_500).a( "390.00").etPar100FrancsEnPlus( "3.00")
                .pour(  78_100).a( "918.00").etPar100FrancsEnPlus( "4.00")
                .pour(  93_600).a("1538.00").etPar100FrancsEnPlus( "5.00")
                .pour( 107_200).a("2218.00").etPar100FrancsEnPlus( "6.00")
                .pour( 119_000).a("2926.00").etPar100FrancsEnPlus( "7.00")
                .pour( 128_800).a("3612.00").etPar100FrancsEnPlus( "8.00")
                .pour( 136_600).a("4236.00").etPar100FrancsEnPlus( "9.00")
                .pour( 142_300).a("4749.00").etPar100FrancsEnPlus("10.00")
                .pour( 146_300).a("5149.00").etPar100FrancsEnPlus("11.00")
                .pour( 148_300).a("5369.00").etPar100FrancsEnPlus("12.00")
                .pour( 150_300).a("5609.00").etPar100FrancsEnPlus("13.00")
                .tauxEffectifMax("11.5 %").construire();
    }

    // ******************************************************************
    // ************************* 2023 ***********************************
    // ******************************************************************

    /**
     * Adapté à l'indice de juin 2022: 165,2
     * barème 2012 étiré de 2.04 %
     * @return Constructeur pour les périodes fiscales 2023
     */
    private Bareme tranchesPersonneSeule2023(ConstructeurBaremeIFD constructeurBaremeIFD) {
        return constructeurBaremeIFD
                .jusqua(14_800).a(    "0.00").etPar100FrancsEnPlus( "0.77")
                .pour(  32_200).a(  "133.95").etPar100FrancsEnPlus( "0.88")
                .pour(  42_200).a(  "221.95").etPar100FrancsEnPlus( "2.64")
                .pour(  56_200).a(  "591.55").etPar100FrancsEnPlus( "2.97")
                .pour(  73_900).a( "1117.20").etPar100FrancsEnPlus( "5.94")
                .pour(  79_600).a( "1455.75").etPar100FrancsEnPlus( "6.60")
                .pour( 105_500).a( "3165.15").etPar100FrancsEnPlus( "8.80")
                .pour( 137_200).a( "5954.75").etPar100FrancsEnPlus("11.00")
                .pour( 179_400).a("10596.75").etPar100FrancsEnPlus("13.20")
                .tauxEffectifMax("11.5 %").construire();
    }

    /**
     * Adapté à l'indice de juin 2022: 165.2
     * barème 2012 étiré de 2.04 %
     * @return barèmè pour la période fiscale >= 2023
     */
    private Bareme tranchesFamille2023(ConstructeurBaremeIFD constructeurBaremeIFD) {
        return constructeurBaremeIFD
                .jusqua(28_800).a(   "0.00").etPar100FrancsEnPlus( "1.00")
                .pour(  51_800).a( "230.00").etPar100FrancsEnPlus( "2.00")
                .pour(  59_400).a( "382.00").etPar100FrancsEnPlus( "3.00")
                .pour(  76_700).a( "901.00").etPar100FrancsEnPlus( "4.00")
                .pour(  92_000).a("1513.00").etPar100FrancsEnPlus( "5.00")
                .pour( 105_400).a("2183.00").etPar100FrancsEnPlus( "6.00")
                .pour( 116_900).a("2873.00").etPar100FrancsEnPlus( "7.00")
                .pour( 126_500).a("3545.00").etPar100FrancsEnPlus( "8.00")
                .pour( 134_200).a("4161.00").etPar100FrancsEnPlus( "9.00")
                .pour( 139_900).a("4674.00").etPar100FrancsEnPlus("10.00")
                .pour( 143_800).a("5064.00").etPar100FrancsEnPlus("11.00")
                .pour( 145_800).a("5284.00").etPar100FrancsEnPlus("12.00")
                .pour( 147_700).a("5512.00").etPar100FrancsEnPlus("13.00")
                .tauxEffectifMax("11.5 %").construire();
    }

    // ******************************************************************
    // ************************* 2012 ***********************************
    // ******************************************************************

    /**
     * Adapté à l'indice de juin 2011: 161,9
     * barème 2011 étiré de 0.6 %
     * @return Constructeur pour les périodes fiscales 2012, 2013
     */
    private Bareme tranchesPersonneSeule2012(ConstructeurBaremeIFD constructeurBaremeIFD) {
        return constructeurBaremeIFD
                .jusqua(14_500).a(    "0.00").etPar100FrancsEnPlus( "0.77")
                .pour(  31_600).a(  "131.65").etPar100FrancsEnPlus( "0.88")
                .pour(  41_400).a(  "217.90").etPar100FrancsEnPlus( "2.64")
                .pour(  55_200).a(  "582.20").etPar100FrancsEnPlus( "2.97")
                .pour(  72_500).a( "1096.00").etPar100FrancsEnPlus( "5.94")
                .pour(  78_100).a( "1428.60").etPar100FrancsEnPlus( "6.60")
                .pour( 103_600).a( "3111.60").etPar100FrancsEnPlus( "8.80")
                .pour( 134_600).a( "5839.60").etPar100FrancsEnPlus("11.00")
                .pour( 176_000).a("10393.60").etPar100FrancsEnPlus("13.20")
                .tauxEffectifMax("11.5 %").construire();
    }

    /**
     * Adapté à l'indice de juin 2011: 161,9
     * barème 2011 étiré de 0.6 %
     * @return Constructeur pour les périodes fiscales 2012, 2013
     */
    private Bareme tranchesFamille2012(ConstructeurBaremeIFD constructeurBaremeIFD) {
        return constructeurBaremeIFD
                .jusqua(28_300).a(   "0.00").etPar100FrancsEnPlus( "1.00")
                .pour(  50_900).a( "226.00").etPar100FrancsEnPlus( "2.00")
                .pour(  58_400).a( "376.00").etPar100FrancsEnPlus( "3.00")
                .pour(  75_300).a( "883.00").etPar100FrancsEnPlus( "4.00")
                .pour(  90_300).a("1483.00").etPar100FrancsEnPlus( "5.00")
                .pour( 103_400).a("2138.00").etPar100FrancsEnPlus( "6.00")
                .pour( 114_700).a("2816.00").etPar100FrancsEnPlus( "7.00")
                .pour( 124_200).a("3481.00").etPar100FrancsEnPlus( "8.00")
                .pour( 131_700).a("4081.00").etPar100FrancsEnPlus( "9.00")
                .pour( 137_300).a("4585.00").etPar100FrancsEnPlus("10.00")
                .pour( 141_200).a("4975.00").etPar100FrancsEnPlus("11.00")
                .pour( 143_100).a("5184.00").etPar100FrancsEnPlus("12.00")
                .pour( 145_000).a("5412.00").etPar100FrancsEnPlus("13.00")
                .tauxEffectifMax("11.5 %").construire();
    }

    // ******************************************************************
    // ************************* 2011 ***********************************
    // ******************************************************************

    /**
     * Adapté à l'indice de juin 2010: 161,0
     * barème 2006 étiré de 5,2 %
     * @return Constructeur pour la période fiscale 2011
     */
    private Bareme tranchesPersonneSeule2011(ConstructeurBaremeIFD constructeurBaremeIFD) {
        return constructeurBaremeIFD
                .jusqua(14_400).a(    "0.00").etPar100FrancsEnPlus( "0.77")
                .pour(  31_500).a(  "131.65").etPar100FrancsEnPlus( "0.88")
                .pour(  41_200).a(  "217.00").etPar100FrancsEnPlus( "2.64")
                .pour(  55_000).a(  "581.30").etPar100FrancsEnPlus( "2.97")
                .pour(  72_200).a( "1092.10").etPar100FrancsEnPlus( "5.94")
                .pour(  77_700).a( "1418.80").etPar100FrancsEnPlus( "6.60")
                .pour( 103_000).a( "3088.60").etPar100FrancsEnPlus( "8.80")
                .pour( 133_900).a( "5807.80").etPar100FrancsEnPlus("11.00")
                .pour( 175_000).a("10328.80").etPar100FrancsEnPlus("13.20")
                .tauxEffectifMax("11.5 %").construire();
    }

    /**
     * Adapté à l'indice de juin 2010: 161,0
     * barème 2006 étiré de 5,2 %
     * @return Constructeur pour la période fiscale 2011
     */
    private Bareme tranchesFamille2011(ConstructeurBaremeIFD constructeurBaremeIFD) {
        return constructeurBaremeIFD
                .jusqua(28_100).a(   "0.00").etPar100FrancsEnPlus( "1.00")
                .pour(  50_400).a( "223.00").etPar100FrancsEnPlus( "2.00")
                .pour(  57_900).a( "373.00").etPar100FrancsEnPlus( "3.00")
                .pour(  74_700).a( "877.00").etPar100FrancsEnPlus( "4.00")
                .pour(  89_700).a("1477.00").etPar100FrancsEnPlus( "5.00")
                .pour( 102_700).a("2127.00").etPar100FrancsEnPlus( "6.00")
                .pour( 113_900).a("2799.00").etPar100FrancsEnPlus( "7.00")
                .pour( 123_300).a("3457.00").etPar100FrancsEnPlus( "8.00")
                .pour( 130_800).a("4057.00").etPar100FrancsEnPlus( "9.00")
                .pour( 136_300).a("4552.00").etPar100FrancsEnPlus("10.00")
                .pour( 140_200).a("4942.00").etPar100FrancsEnPlus("11.00")
                .pour( 142_100).a("5151.00").etPar100FrancsEnPlus("12.00")
                .pour( 144_000).a("5379.00").etPar100FrancsEnPlus("13.00")
                .tauxEffectifMax("11.5 %").construire();
    }

    // ******************************************************************
    // ************************* 2006 ***********************************
    // ******************************************************************

    /**
     * Adapté à l'indice de décembre 2004: 153,1
     * barème 1996 étiré de 7,6 %
     * @return Constructeur pour les périodes fiscales 2006, 2007, 2008, 2009, 2010
     */
    private Bareme tranchesPersonneSeule2006(ConstructeurBaremeIFD constructeurBaremeIFD) {
        return constructeurBaremeIFD
                .jusqua(13_600).a(   "0.00").etPar100FrancsEnPlus( "0.77")
                .pour(  29_800).a( "124.70").etPar100FrancsEnPlus( "0.88")
                .pour(  39_000).a( "205.65").etPar100FrancsEnPlus( "2.64")
                .pour(  52_000).a( "548.85").etPar100FrancsEnPlus( "2.97")
                .pour(  68_300).a("1032.95").etPar100FrancsEnPlus( "5.94")
                .pour(  73_600).a("1347.75").etPar100FrancsEnPlus( "6.60")
                .pour(  97_700).a("2938.35").etPar100FrancsEnPlus( "8.80")
                .pour( 127_100).a("5525.55").etPar100FrancsEnPlus("11.00")
                .pour( 166_200).a("9826.55").etPar100FrancsEnPlus("13.20")
                .tauxEffectifMax("11.5 %").construire();
    }

    /**
     * Adapté à l'indice de décembre 2004: 153,1
     * barème 1996 étiré de 7,6 %
     * @return Constructeur pour les périodes fiscales 2006, 2007, 2008, 2009, 2010
     */
    private Bareme tranchesFamille2006(ConstructeurBaremeIFD constructeurBaremeIFD) {
        return constructeurBaremeIFD
                .jusqua(26_700).a(   "0.00").etPar100FrancsEnPlus( "1.00")
                .pour(  47_900).a( "212.00").etPar100FrancsEnPlus( "2.00")
                .pour(  54_900).a( "352.00").etPar100FrancsEnPlus( "3.00")
                .pour(  70_900).a( "832.00").etPar100FrancsEnPlus( "4.00")
                .pour(  85_100).a("1400.00").etPar100FrancsEnPlus( "5.00")
                .pour(  97_400).a("2015.00").etPar100FrancsEnPlus( "6.00")
                .pour( 108_100).a("2657.00").etPar100FrancsEnPlus( "7.00")
                .pour( 117_000).a("3280.00").etPar100FrancsEnPlus( "8.00")
                .pour( 124_000).a("3840.00").etPar100FrancsEnPlus( "9.00")
                .pour( 129_300).a("4317.00").etPar100FrancsEnPlus("10.00")
                .pour( 132_900).a("4677.00").etPar100FrancsEnPlus("11.00")
                .pour( 134_700).a("4875.00").etPar100FrancsEnPlus("12.00")
                .pour( 136_500).a("5091.00").etPar100FrancsEnPlus("13.00")
                .tauxEffectifMax("11.5 %").construire();
    }

    // ******************************************************************
    // ************************* 1996 ***********************************
    // ******************************************************************

    /**
     * Adapté à l'indice de décembre 1995: 142,3
     * barème 1993 étiré de 8.5 %
     * @return Constructeur pour les périodes fiscales 1996, 1997, 1998, 1999, 2000, 2001, 2002, 2003, 2004 et 2005
     */
    private Bareme tranchesPersonneSeule1996(ConstructeurBaremeIFD constructeurBaremeIFD) {
        return constructeurBaremeIFD
                .jusqua(12_800).a(   "0.00").etPar100FrancsEnPlus( "0.77")
                .pour(  27_900).a( "116.25").etPar100FrancsEnPlus( "0.88")
                .pour(  36_500).a( "191.90").etPar100FrancsEnPlus( "2.64")
                .pour(  48_600).a( "511.30").etPar100FrancsEnPlus( "2.97")
                .pour(  63_800).a( "962.70").etPar100FrancsEnPlus( "5.94")
                .pour(  68_800).a("1259.70").etPar100FrancsEnPlus( "6.60")
                .pour(  91_100).a("2731.50").etPar100FrancsEnPlus( "8.80")
                .pour( 118_400).a("5133.90").etPar100FrancsEnPlus("11.00")
                .pour( 154_700).a("9126.90").etPar100FrancsEnPlus("13.20")
                .tauxEffectifMax("11.5 %").construire();
    }

    /**
     * Adapté à l'indice de décembre 1995: 142.3
     * barème 1993 étiré de 8.5 %
     * @return Constructeur pour les périodes fiscales 1996 à 2005
     */
    private Bareme tranchesFamille1996(ConstructeurBaremeIFD constructeurBaremeIFD) {
        return constructeurBaremeIFD
                .jusqua(24_900).a(   "0.00").etPar100FrancsEnPlus( "1.00")
                .pour(  44_700).a( "198.00").etPar100FrancsEnPlus( "2.00")
                .pour(  51_300).a( "330.00").etPar100FrancsEnPlus( "3.00")
                .pour(  66_200).a( "777.00").etPar100FrancsEnPlus( "4.00")
                .pour(  79_400).a("1305.00").etPar100FrancsEnPlus( "5.00")
                .pour(  91_000).a("1885.00").etPar100FrancsEnPlus( "6.00")
                .pour( 101_000).a("2485.00").etPar100FrancsEnPlus( "7.00")
                .pour( 109_300).a("3066.00").etPar100FrancsEnPlus( "8.00")
                .pour( 115_900).a("3594.00").etPar100FrancsEnPlus( "9.00")
                .pour( 120_900).a("4044.00").etPar100FrancsEnPlus("10.00")
                .pour( 124_300).a("4384.00").etPar100FrancsEnPlus("11.00")
                .pour( 126_000).a("4571.00").etPar100FrancsEnPlus("12.00")
                .pour( 127_700).a("4775.00").etPar100FrancsEnPlus("13.00")
                .tauxEffectifMax("11.5 %").construire();
    }

}
