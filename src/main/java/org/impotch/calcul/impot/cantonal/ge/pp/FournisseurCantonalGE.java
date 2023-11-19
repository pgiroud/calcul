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
import org.impotch.calcul.impot.taxation.pp.ge.deduction.rabais.ProducteurBaseRabaisImpot;
import org.impotch.calcul.util.ExplicationDetailleTexteBuilder;
import org.impotch.calcul.util.IExplicationDetailleeBuilder;
import org.impotch.util.TypeArrondi;
import org.impotch.util.math.Fonction;
import org.impotch.util.math.integration.MethodeIntegration;
import org.impotch.util.math.integration.MethodeIntegrationPointMilieu;

import static org.impotch.calcul.impot.cantonal.ge.pp.ConstructeurBaremeGEParTrancheIndexeeActuel.unConstructeurBaremeGEActuel;
import static org.impotch.calcul.impot.cantonal.ge.pp.ConstructeurBaremeParTrancheIndexe.unConstructeurDeBaremeParTrancheIndexee;
import static org.impotch.calcul.impot.cantonal.ge.pp.avant2010.ConstructeurBaremeGEParTrancheIndexeeEntre2001et2009.unConstructeurBaremeGEEntre2001et2009;
import static org.impotch.calcul.impot.cantonal.ge.pp.avant2010.ConstructeurBaremeRevenuAvecFormuleUniversite.unConstructeurBaremeRevenuAvecFormuleUniversite;

public class FournisseurCantonalGE extends FournisseurCantonal implements FournisseurRegleImpotCantonalGE {

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


    private IExplicationDetailleeBuilder getNewExplicationBuilder() {
        return new ExplicationDetailleTexteBuilder();
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

    public ProducteurImpotBase construireImpotCantonalBaseRevenu(int annee) {
        ProducteurImpotBaseProgressif producteur = new ProducteurImpotBaseProgressif();
        StrategieProductionImpotFamille strategie = null;
        if (annee < 2010) {
            strategie = new DoubleBaremeGE(getBaremeRevenu(annee), getBaremeRevenuFamille(annee));
        } else {
            strategie = new Splitting(getBaremeRevenu(annee), "50 %");
        }
        producteur.setStrategieProductionImpotFamille(strategie);

        producteur.setTypeArrondiImposable(TypeArrondi.UNITE_LA_PLUS_PROCHE);
        producteur.setTypeArrondiDeterminant(TypeArrondi.UNITE_LA_PLUS_PROCHE);
        producteur.setTypeArrondiImpot(TypeArrondi.CINQ_CENTIEMES_LES_PLUS_PROCHES);
        return producteur;
    }

    public ProducteurImpotBase construireImpotCantonalBasePC(int annee) {
        ProducteurImpotBaseProgressif producteur = new ProducteurImpotBaseProgressif();
        producteur.setPartBase(new BigDecimal("0.2"));
        StrategieProductionImpotFamille strategie = null;
        if (annee < 2010) {
            strategie = new DoubleBaremeGE(getBaremeRevenu(annee), getBaremeRevenuFamille(annee));
        } else {
            strategie = new Splitting(getBaremeRevenu(annee), "50 %");
        }
        producteur.setStrategieProductionImpotFamille(strategie);

        producteur.setTypeArrondiImposable(TypeArrondi.UNITE_LA_PLUS_PROCHE);
        producteur.setTypeArrondiDeterminant(TypeArrondi.UNITE_LA_PLUS_PROCHE);
        producteur.setTypeArrondiImpot(TypeArrondi.CINQ_CENTIEMES_LES_PLUS_PROCHES);
        return producteur;
    }

    private void completerProducteurImpotsCantonaux(ProducteurImpot producteur, String codeBeneficiaire, int annee, TypeArrondi typeArrondi) {


        IExplicationDetailleeBuilder explication = getNewExplicationBuilder();
        explication.ajouter("Réduction de {1,number,percent} sur impôt de base sur revenu {0,number,#,##0.00}");
        explication.ajouter("{2,number,#,##0.00}");
        ProducteurImpotDerivePourcent prodRIBR = new ProducteurImpotDerivePourcent("RIBR", "-12 %", codeBeneficiaire);
        prodRIBR.setTypeArrondi(typeArrondi);
        prodRIBR.setExplicationDetailleePattern(explication.getTexte());
        producteur.ajouteProducteurDerive(prodRIBR);

        explication.reset();
        explication.ajouter("Réduction de {1,number,percent} sur cts add. cantonaux sur revenu {0,number,#,##0.00}");
        explication.ajouter("{2,number,#,##0.00}");
        ProducteurImpotDerivePourcent prodRCAR = new ProducteurImpotDerivePourcent("RCAR", "-12 %", codeBeneficiaire);
        prodRCAR.setTypeArrondi(typeArrondi);
        prodRCAR.setExplicationDetailleePattern(explication.getTexte());

        explication.reset();
        explication.ajouter("CA Revenu :");
        explication.ajouter("Total impôt de base revenu ({0,number,#,##0.00}) * {1,number,percent}");
        explication.ajouter("{2,number,#,##0.00}");
        ProducteurImpotDerivePourcent ctsAddCantonaux = new ProducteurImpotDerivePourcent("CAR", "47.5 %", codeBeneficiaire);
        ctsAddCantonaux.setTypeArrondi(typeArrondi);
        ctsAddCantonaux.setProducteurDerive(prodRCAR);
        ctsAddCantonaux.setExplicationDetailleePattern(explication.getTexte());
        producteur.ajouteProducteurDerive(ctsAddCantonaux);

        explication.reset();
        explication.ajouter("CA Aide à domicile Revenu :");
        explication.ajouter("Total impôt de base revenu ({0,number,#,##0.00}) * {1,number,percent}");
        explication.ajouter("{2,number,#,##0.00}");
        ProducteurImpotDerivePourcent prodADR = new ProducteurImpotDerivePourcent("ADR", "1 %", codeBeneficiaire);
        prodADR.setTypeArrondi(typeArrondi);
        prodADR.setExplicationDetailleePattern(explication.getTexte());
        producteur.ajouteProducteurDerive(prodADR);
    }


    public ProducteurImpot construireProducteurImpotsCantonauxPC(int annee) {
        String codeBeneficiaire = "CAN-GE";
        ProducteurImpotBase producteurImpotBase = construireImpotCantonalBasePC(annee);
        ProducteurImpot producteur;
        if (annee < 2010) {
            ProducteurImpotGEAvecRabais prodRabais = new ProducteurImpotGEAvecRabais("IBR", "RI", codeBeneficiaire) {
                @Override
                protected IExplicationDetailleeBuilder createExplicationBuilder() {
                    return FournisseurCantonalGE.this.getNewExplicationBuilder();
                }
            };
            prodRabais.setProducteurBaseRabais(producteurImpotBase);
            producteur = prodRabais;
        } else {
            producteur = new ProducteurImpot("IBR", codeBeneficiaire) {
                @Override
                protected IExplicationDetailleeBuilder createExplicationBuilder() {
                    return FournisseurCantonalGE.this.getNewExplicationBuilder();
                }
            };
        }
        producteur.setProducteurBase(producteurImpotBase);
        completerProducteurImpotsCantonaux(producteur, codeBeneficiaire, annee, TypeArrondi.CINQ_CENTIEMES_LES_PLUS_PROCHES);
        return producteur;
    }

    private ProducteurImpot construireProducteurImpotCantonalBaseRevenu(int annee, String codeBeneficiaire) {
        ProducteurImpotBase producteurImpotBase = construireImpotCantonalBaseRevenu(annee);
        ProducteurImpot producteur;
        if (annee < 2010) {
            ProducteurImpotGEAvecRabais prodRabais = new ProducteurImpotGEAvecRabais("IBR", "RI", codeBeneficiaire) {
                @Override
                protected IExplicationDetailleeBuilder createExplicationBuilder() {
                    return FournisseurCantonalGE.this.getNewExplicationBuilder();
                }
            };
            prodRabais.setProducteurBaseRabais(producteurImpotBase);
            producteur = prodRabais;
        } else {
            producteur = new ProducteurImpot("IBR", codeBeneficiaire) {
                @Override
                protected IExplicationDetailleeBuilder createExplicationBuilder() {
                    return FournisseurCantonalGE.this.getNewExplicationBuilder();
                }
            };
        }
        producteur.setProducteurBase(producteurImpotBase);
        return producteur;
    }

    public ProducteurImpot construireProducteurImpotsCantonauxRevenu(int annee, TypeArrondi typeArrondi) {
        String codeBeneficiaire = "CAN-GE";
        ProducteurImpot producteur = construireProducteurImpotCantonalBaseRevenu(annee, codeBeneficiaire);
        completerProducteurImpotsCantonaux(producteur, codeBeneficiaire, annee, typeArrondi);
        return producteur;

    }

    private ProducteurImpot construireProducteurImpotsICCRevenu(int annee) {
        ProducteurImpot producteur = construireProducteurImpotsCantonauxRevenu(annee, TypeArrondi.CINQ_CENTIEMES_LES_PLUS_PROCHES);

        // On ajoute ensuite les impôts communaux
        ProducteurImpotCommunalGE prodComm = new ProducteurImpotCommunalGEPersPhysique("PPR", "COR") {
            @Override
            protected IExplicationDetailleeBuilder createExplicationBuilder() {
                return FournisseurCantonalGE.this.getNewExplicationBuilder();
            }
        };
        prodComm.setFournisseurParametrage(this.getFournisseurParamCommunaux());
        producteur.setProducteurImpotCommunal(prodComm);
        return producteur;
    }

    private ProducteurImpot construireProducteurImpotsICCFortune(int annee) {
        ProducteurImpotBaseProgressif producteurImpotBase = new ProducteurImpotBaseProgressif();
        producteurImpotBase.setBareme(this.getBaremeFortune(annee));
        producteurImpotBase.setTypeArrondiImposable(TypeArrondi.UNITE_LA_PLUS_PROCHE);
        producteurImpotBase.setTypeArrondiDeterminant(TypeArrondi.UNITE_LA_PLUS_PROCHE);
        producteurImpotBase.setTypeArrondiImpot(TypeArrondi.CINQ_CENTIEMES_LES_PLUS_PROCHES);


        String codeBeneficiaire = "CAN-GE";
        ProducteurImpot producteur = new ProducteurImpot("IBF", codeBeneficiaire) {
            @Override
            protected IExplicationDetailleeBuilder createExplicationBuilder() {
                return FournisseurCantonalGE.this.getNewExplicationBuilder();
            }
        };
        producteur.setProducteurBase(producteurImpotBase);

        IExplicationDetailleeBuilder explication = getNewExplicationBuilder();
        explication.ajouter("CA Fortune :");
        explication.ajouter("Total impôt de base fortune ({0,number,#,##0.00}) * {1,number,percent}");
        explication.ajouter("{2,number,#,##0.00}");
        ProducteurImpotDerivePourcent prodCAF = new ProducteurImpotDerivePourcent("CAF", "47.5 %", codeBeneficiaire);
        prodCAF.setExplicationDetailleePattern(explication.getTexte());
        producteur.ajouteProducteurDerive(prodCAF);

        explication.reset();
        explication.ajouter("CA Aide à domicile Fortune :");
        explication.ajouter("Total impôt de base fortune ({0,number,#,##0.00}) * {1,number,percent}");
        explication.ajouter("{2,number,#,##0.00}");
        ProducteurImpotDerivePourcent prodADF = new ProducteurImpotDerivePourcent("ADF", "1 %", codeBeneficiaire);
        prodADF.setExplicationDetailleePattern(explication.getTexte());
        producteur.ajouteProducteurDerive(prodADF);

        // On ajoute ensuite les impôts communaux
        ProducteurImpotCommunalGE prodComm = new ProducteurImpotCommunalGEPersPhysique("PPF", "COF") {
            @Override
            protected IExplicationDetailleeBuilder createExplicationBuilder() {
                return FournisseurCantonalGE.this.getNewExplicationBuilder();
            }
        };
        prodComm.setFournisseurParametrage(this.getFournisseurParamCommunaux());
        producteur.setProducteurImpotCommunal(prodComm);
        return producteur;
    }

    private ProducteurImpot construireProducteurImpotsICCFortuneSupplementaire(int annee) {
        ProducteurImpotBaseProgressif producteurImpotBase = new ProducteurImpotBaseProgressif();
        producteurImpotBase.setBareme(this.getBaremeFortuneSupplementaire(annee));
        producteurImpotBase.setTypeArrondiImposable(TypeArrondi.UNITE_LA_PLUS_PROCHE);
        producteurImpotBase.setTypeArrondiDeterminant(TypeArrondi.UNITE_LA_PLUS_PROCHE);
        producteurImpotBase.setTypeArrondiImpot(TypeArrondi.CINQ_CENTIEMES_LES_PLUS_PROCHES);


        String codeBeneficiaire = "CAN-GE";
        ProducteurImpot producteur = new ProducteurImpot("ISF", codeBeneficiaire) {
            @Override
            protected IExplicationDetailleeBuilder createExplicationBuilder() {
                return FournisseurCantonalGE.this.getNewExplicationBuilder();
            }
        };
        producteur.setProducteurBase(producteurImpotBase);
        return producteur;
    }

}
