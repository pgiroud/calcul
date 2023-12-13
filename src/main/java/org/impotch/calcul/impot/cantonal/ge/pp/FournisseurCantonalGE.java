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
package org.impotch.calcul.impot.cantonal.ge.pp;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.impotch.bareme.*;
import org.impotch.calcul.assurancessociales.FournisseurRegleCalculAssuranceSociale;
import org.impotch.calcul.impot.cantonal.ge.pp.avant2010.*;
import org.impotch.calcul.impot.indexation.ge.FournisseurIndexGenevois;
import org.impotch.calcul.impot.indexation.ge.FournisseurIndexGenevoisEnMemoire;
import org.impotch.calcul.impot.taxation.pp.ProducteurImpotBaseProgressif;
import org.impotch.calcul.impot.ProducteurImpotDerivePourcent;
import org.impotch.calcul.impot.cantonal.FournisseurCantonal;
import org.impotch.calcul.impot.cantonal.ge.ProducteurImpotCommunalGE;
import org.impotch.calcul.impot.cantonal.ge.param.FournisseurParametrageCommunaleGE;
import org.impotch.calcul.impot.taxation.pp.*;
import org.impotch.calcul.impot.taxation.pp.famille.Splitting;
import org.impotch.calcul.impot.taxation.pp.famille.SplittingEventuellementPartiel;
import org.impotch.calcul.impot.taxation.pp.ge.deduction.rabais.ProducteurBaseRabaisImpot;
import org.impotch.calcul.util.ExplicationDetailleTexteBuilder;
import org.impotch.calcul.util.IExplicationDetailleeBuilder;
import org.impotch.util.TypeArrondi;
import org.impotch.util.math.Fonction;
import org.impotch.util.math.integration.MethodeIntegration;
import org.impotch.util.math.integration.MethodeIntegrationPointMilieu;

import static org.impotch.calcul.impot.ProducteurImpotDerivePourcent.unConsProducteurImpotDerive;
import static org.impotch.calcul.impot.cantonal.ge.pp.ConstructeurBaremeGEParTrancheIndexeeActuel.unConstructeurBaremeGEActuel;
import static org.impotch.calcul.impot.cantonal.ge.pp.ConstructeurBaremeParTrancheIndexe.unConstructeurDeBaremeParTrancheIndexee;
import static org.impotch.calcul.impot.cantonal.ge.pp.avant2010.ConstructeurBaremeGEParTrancheIndexeeEntre2001et2009.unConstructeurBaremeGEEntre2001et2009;
import static org.impotch.calcul.impot.cantonal.ge.pp.avant2010.ConstructeurBaremeRevenuAvecFormuleUniversite.unConstructeurBaremeRevenuAvecFormuleUniversite;
import static org.impotch.calcul.impot.taxation.pp.ProducteurImpotBaseProgressif.unProducteurImpotBaseProgressif;

public class FournisseurCantonalGE extends FournisseurCantonal implements FournisseurRegleImpotCantonalGE {


    private final static TypeArrondi ARRONDI_ASSIETTE = TypeArrondi.UNITE_LA_PLUS_PROCHE;
    private final static TypeArrondi ARRONDI_IMPOT = TypeArrondi.CINQ_CENTIEMES_LES_PLUS_PROCHES;

    private static final String CODE_CANTON_GE = "CAN-GE";
    private ConstructeurBaremeGEParTrancheIndexee constructeurBaremeEntre2001et2009;
    private ConstructeurBaremeGEParTrancheIndexee constructeurBaremeActuel;

    private ConstructeurBaremeRevenuAvecFormuleUniversite constructeurBaremeRevenuAvecFormuleUniversite;

    private FournisseurRegleCalculAssuranceSociale fournisseurRegleCalculCotisationAssuranceSociale;

    private FournisseurParametrageCommunaleGE fournisseurParamCommunaux;

    private final ConcurrentMap<Integer, Bareme> mapBaremeRevenuMarie = new ConcurrentHashMap<>();

    private ConstructeurBaremeParTrancheIndexe constructeurBaremeFortune;
    private ConstructeurBaremeParTrancheIndexe constructeurBaremeFortuneApres2009;

    private ConstructeurBaremeParTrancheIndexe constructeurBaremeFortuneSupplementaire;
    private ConstructeurBaremeParTrancheIndexe constructeurBaremeFortuneSupplementaireApres2009;
    private ConcurrentMap<Integer, Bareme> baremesFortuneSupplementaire = new ConcurrentHashMap<>();

    private ConcurrentMap<Integer, ProducteurImpot> producteurImpotsICCRevenu = new ConcurrentHashMap<>();
    private ConcurrentMap<Integer, ProducteurImpot> producteurImpotsICCFortune = new ConcurrentHashMap<>();
    private ConcurrentMap<Integer, ProducteurImpot> producteurImpotsICCFortuneSupplementaire = new ConcurrentHashMap<>();

    private ConcurrentMap<Integer, ProducteurRabaisImpot> producteursRabaisImpot = new ConcurrentHashMap<Integer, ProducteurRabaisImpot>();

    public FournisseurCantonalGE(FournisseurRegleCalculAssuranceSociale regleAssurance, FournisseurIndexGenevois fournisseurIndexGenevois) {
        this.fournisseurRegleCalculCotisationAssuranceSociale = regleAssurance;

        constructeurBaremeEntre2001et2009 = unConstructeurBaremeGEEntre2001et2009(fournisseurIndexGenevois.getFournisseurIndiceGEBaseMai1993());
        constructeurBaremeRevenuAvecFormuleUniversite = unConstructeurBaremeRevenuAvecFormuleUniversite(fournisseurIndexGenevois.getFournisseurIndiceGEBaseMai1993());

        constructeurBaremeActuel = unConstructeurBaremeGEActuel(fournisseurIndexGenevois.getFournisseurIndiceGEBaseDecembre2005(),constructeurBaremeEntre2001et2009);
    }

   /**
     * @return the fournisseurParamCommunaux
     */
    public FournisseurParametrageCommunaleGE getFournisseurParamCommunaux() {
        return fournisseurParamCommunaux;
    }

    /**
     * @param fournisseurParamCommunaux the fournisseurParamCommunaux to set
     */
    public void setFournisseurParamCommunaux(
            FournisseurParametrageCommunaleGE fournisseurParamCommunaux) {
        this.fournisseurParamCommunaux = fournisseurParamCommunaux;
    }







    protected Bareme construireBaremeRevenu(int annee) {
        if (annee < 2010) {
            return constructeurBaremeRevenuAvecFormuleUniversite.construireBaremeRevenu(annee);
        } else {
            return constructeurBaremeActuel.constructeurBaremeRevenu().construire(annee);
        }
    }

    public Bareme getBaremeRevenuFamille(int annee) {
        if (annee >= 2010) throw new IllegalArgumentException("Depuis 2010, Genève n’utilise plus le double barème " +
                "mais la méthode de splitting, cet appel avec année = " + annee + " n’a pas de sens !!");
        if (!mapBaremeRevenuMarie.containsKey(annee))
            mapBaremeRevenuMarie.putIfAbsent(annee,
                    constructeurBaremeRevenuAvecFormuleUniversite.construireBaremeRevenuMarie(annee));
        return mapBaremeRevenuMarie.get(annee);
    }



    private ConstructeurBaremeGEParTrancheIndexee choisirConstructeurBaremeGEParTrancheIndexee(int annee) {
        return (annee >= 2010) ? constructeurBaremeActuel : constructeurBaremeEntre2001et2009;
    }

    protected BaremeParTranche construireBaremeFortune(
            int annee) {
        return choisirConstructeurBaremeGEParTrancheIndexee(
                annee).constructeurBaremeFortune().construire(annee);
    }

    public Bareme getBaremeFortuneSupplementaire(int annee) {
        if (!baremesFortuneSupplementaire.containsKey(annee)) {
            baremesFortuneSupplementaire.putIfAbsent(annee,
                    construireBaremeFortuneSupplementaire(annee));
        }
        return baremesFortuneSupplementaire.get(annee);
    }

    private BaremeParTranche construireBaremeFortuneSupplementaire(
            int annee) {
        return choisirConstructeurBaremeGEParTrancheIndexee(
                annee).constructeurBaremeFortuneSupplementaire().construire(annee);
    }

    public ProducteurImpot getProducteurImpotsICCRevenu(int annee) {
        if (!producteurImpotsICCRevenu.containsKey(annee))
            producteurImpotsICCRevenu.putIfAbsent(annee,
                    construireProducteurImpotsICCRevenu(annee));
        return producteurImpotsICCRevenu.get(annee);
    }

    public ProducteurImpot getProducteurImpotsICCFortune(int annee) {
        if (!producteurImpotsICCFortune.containsKey(annee))
            producteurImpotsICCFortune.putIfAbsent(annee,
                    construireProducteurImpotsICCFortune(annee));
        return producteurImpotsICCFortune.get(annee);
    }

    public ProducteurImpot getProducteurImpotsICCFortuneSupplementaire(int annee) {
        if (!producteurImpotsICCFortuneSupplementaire.containsKey(annee))
            producteurImpotsICCFortuneSupplementaire.putIfAbsent(annee,
                    construireProducteurImpotsICCFortuneSupplementaire(annee));
        return producteurImpotsICCFortuneSupplementaire.get(annee);
    }

    protected RegleAgeEnfant contruireRegleAge() {
        RegleAgeEnfant regleAge = new RegleAgeEnfant();
        regleAge.setAgeLimiteJeuneEnfant(12);
        regleAge.setAgeMajorite(18);
        regleAge.setAgeLimiteJeuneAdulte(25);
        return regleAge;
    }

    public ProducteurRabaisImpot getProducteurRabaisImpot(int annee) {
        if (annee >= 2010 || annee < 2001)
            throw new IllegalArgumentException("Le rabais d'impôt est un processus défini uniquement entre les années 2001 et 2009 incluses !!");
        if (!producteursRabaisImpot.containsKey(annee)) {
            producteursRabaisImpot.putIfAbsent(annee, construireProducteurRabaisImpot(annee));
        }
        return producteursRabaisImpot.get(annee);
    }


    private ProducteurRabaisImpot construireProducteurRabaisImpot(int annee) {
        ProducteurBaseRabaisImpot producteur = new ProducteurBaseRabaisImpot(annee);
        if (annee < 2005) {
            producteur.setMontantParEpoux(new BigDecimal(13750));
            producteur.setMontantDeducDoubleActivite(new BigDecimal(3500));
            producteur.setPlafondFaibleRevenu(new BigDecimal(50000));
            producteur.setMontantDeducDoubleActivitePourFaibleRevenu(new BigDecimal(5000));
            producteur.setMontantParDemiCharge(new BigDecimal(3250));
            producteur.setDemiMontantFraisGarde(new BigDecimal(1250));
        } else if (annee < 2009) {
            producteur.setMontantParEpoux(new BigDecimal(14288));
            producteur.setMontantDeducDoubleActivite(new BigDecimal(3640));
            producteur.setPlafondFaibleRevenu(new BigDecimal(52000));
            producteur.setMontantDeducDoubleActivitePourFaibleRevenu(new BigDecimal(5200));
            producteur.setMontantParDemiCharge(new BigDecimal(3377));
            producteur.setDemiMontantFraisGarde(new BigDecimal(1299));
        } else {
            producteur.setMontantParEpoux(new BigDecimal(15057));
            producteur.setMontantDeducDoubleActivite(new BigDecimal(3850));
            producteur.setPlafondFaibleRevenu(new BigDecimal(55000));
            producteur.setMontantDeducDoubleActivitePourFaibleRevenu(new BigDecimal(5500));
            producteur.setMontantParDemiCharge(new BigDecimal(3559));
            producteur.setDemiMontantFraisGarde(new BigDecimal(1369));
        }
        producteur.setRegleAge(contruireRegleAge());
        producteur.setRegleRenteMaxi(fournisseurRegleCalculCotisationAssuranceSociale.getCalculateurExtremaRenteAVS(annee));
        return producteur;
    }

    private StrategieProductionImpotFamille impositionFamiliale(int annee) {
        if (annee < 2010) return new DoubleBaremeGE(getBaremeRevenu(annee), getBaremeRevenuFamille(annee));
        if (annee < 2024) return new Splitting(getBaremeRevenu(annee), "50 %");
        else return new SplittingEventuellementPartiel(getBaremeRevenu(annee),"50 %","55,56 %"); // https://ge.ch/grandconseil/data/texte/PL13254A.pdf
    }

    public ProducteurImpotBase construireImpotCantonalBaseRevenu(int annee) {
        return unProducteurImpotBaseProgressif(impositionFamiliale(annee))
                .arrondiAssiettes(ARRONDI_ASSIETTE)
                .arrondiImpot(ARRONDI_IMPOT)
                .construire();
    }

    public ProducteurImpotBase construireImpotCantonalBasePC(int annee) {
        return unProducteurImpotBaseProgressif(impositionFamiliale(annee))
                .arrondiAssiettes(ARRONDI_ASSIETTE)
                .arrondiImpot(ARRONDI_IMPOT)
                .construireUnCinquieme();
    }

    /**
     * Complète le producteur d'impôts de base avec des impôts dérivés
     * @param producteur le producteur à compléter
     * @param annee les taux pourraient varier par année fiscale (cela fut le cas pour l'IN 111)
     * @param arrondi On précise le type d’arrondi. On ne prend pas le type par défaut pour répondre aux besoins de l’impôt à la source.
     */
    private void completerProducteurImpotsCantonaux(ProducteurImpot producteur, int annee, TypeArrondi arrondi) {

        producteur.ajouteProducteurDerive(
                unConsProducteurImpotDerive("RIBR")
                        .taux("-12 %")
                        .beneficiaire(CODE_CANTON_GE)
                        .arrondi(arrondi)
                        .explic("Réduction de {1,number,percent} sur impôt de base sur revenu {0,number,#,##0.00}")
                        .explic("{2,number,#,##0.00}").cons()
        );


        producteur.ajouteProducteurDerive(
                unConsProducteurImpotDerive("CAR")
                        .taux("47.5 %")
                        .beneficiaire(CODE_CANTON_GE)
                        .arrondi(arrondi)
                        .producteurDerive(
                                unConsProducteurImpotDerive("RCAR")
                                        .taux("-12 %")
                                        .beneficiaire(CODE_CANTON_GE)
                                        .arrondi(arrondi)
                                        .explic("Réduction de {1,number,percent} sur cts add. cantonaux sur revenu {0,number,#,##0.00}")
                                        .explic("{2,number,#,##0.00}")
                                        .cons()
                       )
                        .explic("CA Revenu :")
                        .explic("Total impôt de base revenu ({0,number,#,##0.00}) * {1,number,percent}")
                        .explic("{2,number,#,##0.00}").cons()
        );

        producteur.ajouteProducteurDerive(
                unConsProducteurImpotDerive("ADR")
                        .taux("1 %")
                        .beneficiaire(CODE_CANTON_GE)
                        .arrondi(arrondi)
                        .explic("CA Aide à domicile Revenu :")
                        .explic("Total impôt de base revenu ({0,number,#,##0.00}) * {1,number,percent}")
                        .explic("{2,number,#,##0.00}")
                        .cons()
                );
    }


    public ProducteurImpot construireProducteurImpotsCantonauxPC(int annee) {

        ProducteurImpotBase producteurImpotBase = construireImpotCantonalBasePC(annee);
        ProducteurImpot producteur;
        if (annee < 2010) {
            ProducteurImpotGEAvecRabais prodRabais = new ProducteurImpotGEAvecRabais("IBR", "RI", CODE_CANTON_GE);
            prodRabais.setProducteurBaseRabais(producteurImpotBase);
            producteur = prodRabais;
        } else {
            producteur = new ProducteurImpot("IBR", CODE_CANTON_GE);
        }
        producteur.setProducteurBase(producteurImpotBase);
        completerProducteurImpotsCantonaux(producteur, annee, ARRONDI_IMPOT);
        return producteur;
    }

    private ProducteurImpot construireProducteurImpotCantonalBaseRevenu(int annee) {
        ProducteurImpotBase producteurImpotBase = construireImpotCantonalBaseRevenu(annee);
        ProducteurImpot producteur;
        if (annee < 2010) {
            ProducteurImpotGEAvecRabais prodRabais = new ProducteurImpotGEAvecRabais("IBR", "RI", CODE_CANTON_GE);
            prodRabais.setProducteurBaseRabais(producteurImpotBase);
            producteur = prodRabais;
        } else {
            producteur = new ProducteurImpot("IBR", CODE_CANTON_GE);
        }
        producteur.setProducteurBase(producteurImpotBase);
        return producteur;
    }

    @Override
    public ProducteurImpot construireProducteurImpotsCantonauxRevenu(int annee, TypeArrondi arrondi) {

        ProducteurImpot producteur = construireProducteurImpotCantonalBaseRevenu(annee);
        completerProducteurImpotsCantonaux(producteur,  annee, arrondi);
        return producteur;

    }

    private ProducteurImpot construireProducteurImpotsICCRevenu(int annee) {
        ProducteurImpot producteur = construireProducteurImpotsCantonauxRevenu(annee,ARRONDI_IMPOT);

        // On ajoute ensuite les impôts communaux
        ProducteurImpotCommunalGE prodComm = new ProducteurImpotCommunalGEPersPhysique("PPR", "COR");
        prodComm.setFournisseurParametrage(this.getFournisseurParamCommunaux());
        producteur.setProducteurImpotCommunal(prodComm);
        return producteur;
    }

    private ProducteurImpot construireProducteurImpotsICCFortune(int annee) {
        String codeBeneficiaire = "CAN-GE";
        ProducteurImpot producteur = new ProducteurImpot("IBF", codeBeneficiaire);
        producteur.setProducteurBase(
                unProducteurImpotBaseProgressif(getBaremeFortune(annee))
                        .arrondiAssiettes(ARRONDI_ASSIETTE)
                        .arrondiImpot(ARRONDI_IMPOT)
                        .construire());
        producteur.ajouteProducteurDerive(
                unConsProducteurImpotDerive("CAF")
                        .taux("47.5 %")
                        .beneficiaire(codeBeneficiaire)
                        .arrondi(ARRONDI_IMPOT)
                        .explic("CA Fortune :")
                        .explic("Total impôt de base fortune ({0,number,#,##0.00}) * {1,number,percent}")
                        .explic("{2,number,#,##0.00}")
                        .cons());
        producteur.ajouteProducteurDerive(
                unConsProducteurImpotDerive("ADF")
                        .taux("1 %")
                        .beneficiaire(codeBeneficiaire)
                        .arrondi(ARRONDI_IMPOT)
                        .explic("CA Aide à domicile Fortune :")
                        .explic("Total impôt de base fortune ({0,number,#,##0.00}) * {1,number,percent}")
                        .explic("{2,number,#,##0.00}")
                        .cons()
        );

        // On ajoute ensuite les impôts communaux
        ProducteurImpotCommunalGE prodComm = new ProducteurImpotCommunalGEPersPhysique("PPF", "COF");
        prodComm.setFournisseurParametrage(this.getFournisseurParamCommunaux());
        producteur.setProducteurImpotCommunal(prodComm);
        return producteur;
    }

    private ProducteurImpot construireProducteurImpotsICCFortuneSupplementaire(int annee) {
        String codeBeneficiaire = "CAN-GE";
        ProducteurImpot producteur = new ProducteurImpot("ISF", codeBeneficiaire);
        producteur.setProducteurBase(
                unProducteurImpotBaseProgressif(getBaremeFortuneSupplementaire(annee))
                        .arrondiAssiettes(ARRONDI_ASSIETTE)
                        .arrondiImpot(ARRONDI_IMPOT)
                        .construire()
        );
        return producteur;
    }

}
