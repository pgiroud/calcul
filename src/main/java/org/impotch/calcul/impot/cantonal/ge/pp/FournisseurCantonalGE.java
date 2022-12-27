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
import org.impotch.calcul.impot.indexation.ge.MontantIndexe;
import org.impotch.calcul.impot.indexation.Indexateur;
import org.impotch.calcul.impot.indexation.IndexateurPeriodique;
import org.impotch.calcul.impot.taxation.pp.ProducteurImpotBaseProgressif;
import org.impotch.calcul.impot.ProducteurImpotDerivePourcent;
import org.impotch.calcul.impot.cantonal.FournisseurCantonal;
import org.impotch.calcul.impot.cantonal.ge.ProducteurImpotCommunalGE;
import org.impotch.calcul.impot.cantonal.ge.param.FournisseurParametrageCommunaleGE;
import org.impotch.calcul.impot.indexation.FournisseurIndicePeriodique;
import org.impotch.calcul.impot.taxation.pp.*;
import org.impotch.calcul.impot.taxation.pp.famille.Splitting;
import org.impotch.calcul.impot.taxation.pp.ge.deduction.DeductionBeneficiaireRentesAVSAI;
import org.impotch.calcul.impot.taxation.pp.ge.deduction.DeductionChargeFamille;
import org.impotch.calcul.impot.taxation.pp.ge.deduction.DeductionDoubleActivite;
import org.impotch.calcul.impot.taxation.pp.ge.deduction.DeductionRentierAVS;
import org.impotch.calcul.impot.taxation.pp.ge.deduction.rabais.ProducteurBaseRabaisImpot;
import org.impotch.calcul.util.ExplicationDetailleTexteBuilder;
import org.impotch.calcul.util.IExplicationDetailleeBuilder;
import org.impotch.util.TypeArrondi;
import org.impotch.util.math.Fonction;
import org.impotch.util.math.integration.MethodeIntegration;
import org.impotch.util.math.integration.MethodeIntegrationPointMilieu;


import javax.annotation.Resource;

public class FournisseurCantonalGE extends FournisseurCantonal implements FournisseurRegleImpotCantonalGE {


    @Resource
    private FournisseurRegleCalculAssuranceSociale fournisseurRegleCalculCotisationAssuranceSociale;

    private FournisseurIndexGenevois fournisseurIndexGenevois = new FournisseurIndexGenevoisEnMemoire();

    private FournisseurParametrageCommunaleGE fournisseurParamCommunaux;

    private ConcurrentMap<Integer, Bareme> mapBaremeRevenuMarie = new ConcurrentHashMap<Integer, Bareme>();

    private ConstructeurBaremeIndexeTxMarginalConstantParTranche constructeurBaremeFortune;
    private ConstructeurBaremeIndexeTxMarginalConstantParTranche constructeurBaremeFortuneApres2009;

    private ConstructeurBaremeIndexeTxMarginalConstantParTranche constructeurBaremeFortuneSupplementaire;
    private ConstructeurBaremeIndexeTxMarginalConstantParTranche constructeurBaremeFortuneSupplementaireApres2009;
    private ConcurrentMap<Integer, Bareme> baremesFortuneSupplementaire = new ConcurrentHashMap<Integer, Bareme>();

    private ConcurrentMap<Integer, ProducteurImpot> producteurImpotsICCRevenu = new ConcurrentHashMap<Integer, ProducteurImpot>();
    private ConcurrentMap<Integer, ProducteurImpot> producteurImpotsICCFortune = new ConcurrentHashMap<Integer, ProducteurImpot>();
    private ConcurrentMap<Integer, ProducteurImpot> producteurImpotsICCFortuneSupplementaire = new ConcurrentHashMap<Integer, ProducteurImpot>();


    private ConcurrentMap<Integer, DeductionSociale> deducSocialeCharge = new ConcurrentHashMap<Integer, DeductionSociale>();
    private ConcurrentMap<Integer, DeductionDoubleActivite> deducDoubleActivite = new ConcurrentHashMap<>();
    private ConcurrentMap<Integer, DeductionBeneficiaireRentesAVSAI> deducSocialeRentier = new ConcurrentHashMap<Integer, DeductionBeneficiaireRentesAVSAI>();

    private ConcurrentMap<Integer, ProducteurRabaisImpot> producteursRabaisImpot = new ConcurrentHashMap<Integer, ProducteurRabaisImpot>();


    /**
     * @param fournisseurRegleCalculCotisationAssuranceSociale the fournisseurRegleCalculCotisationAssuranceSociale to set
     */
    public void setFournisseurRegleCalculCotisationAssuranceSociale(
            FournisseurRegleCalculAssuranceSociale fournisseurRegleCalculCotisationAssuranceSociale) {
        this.fournisseurRegleCalculCotisationAssuranceSociale = fournisseurRegleCalculCotisationAssuranceSociale;
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


    public Indexateur getIndexateurBaseMai1993(int anneeBaseIndexation) {
        return fournisseurIndexGenevois.getIndexateurBaseMai1993(anneeBaseIndexation);
    }

    public Indexateur getIndexateurBaseDec2005(int anneeBaseIndexation) {
        return fournisseurIndexGenevois.getIndexateurBaseDec2005(anneeBaseIndexation);
    }

    private IExplicationDetailleeBuilder getNewExplicationBuilder() {
        return new ExplicationDetailleTexteBuilder();
    }


    private TauxMarginalSeul construireTauxMarginal(int annee) {
        TauxMarginalSeul txMarginal = new TauxMarginalSeul(annee);
        txMarginal.setIndexateur(fournisseurIndexGenevois.getFournisseurIndiceGEBaseMai1993());
        return txMarginal;
    }

    protected Bareme construireBaremeRevenu(int annee) {
        if (annee < 2010) {
            BaremeTauxMarginalIntegrable bareme = new BaremeTauxMarginalIntegrable();
            bareme.setTypeArrondi(TypeArrondi.CINQ_CTS);
            bareme.setTauxMarginal(construireTauxMarginal(annee));
            return bareme;
        } else {
            return new ConstructeurBaremeIndexeTxMarginalConstantParTranche()
                    .valideDepuis(2010)
                    .anneeReferenceRencherissement(2009)
                    .indexateur(fournisseurIndexGenevois.getFournisseurIndiceGEBaseDecembre2005())
                    .premiereTranche(17493, "0 %")
                    .tranche(17493, 21076, "8 %")
                    .tranche(21076, 23184, "9 %")
                    .tranche(23184, 25291, "10 %")
                    .tranche(25291, 27399, "11 %")
                    .tranche(27399, 32668, "12 %")
                    .tranche(32668, 36883, "13 %")
                    .tranche(36883, 41099, "14 %")
                    .tranche(41099, 45314, "14.5 %")
                    .tranche(45314, 72713, "15 %")
                    .tranche(72713, 119081, "15.5 %")
                    .tranche(119081, 160179, "16 %")
                    .tranche(160179, 181256, "16.5 %")
                    .tranche(181256, 259238, "17 %")
                    .tranche(259238, 276099, "17.5 %")
                    .tranche(276099, 388857, "18 %")
                    .tranche(388857, 609103, "18.5 %")
                    .derniereTranche(609103, "19 %")
                    .typeArrondiTranche(TypeArrondi.CINQ_CTS).construire(annee);
        }
    }

    public Bareme getBaremeRevenuFamille(int annee) {
        if (!mapBaremeRevenuMarie.containsKey(annee))
            mapBaremeRevenuMarie.putIfAbsent(annee,
                    construireBaremeRevenuMarie(annee));
        return mapBaremeRevenuMarie.get(annee);
    }

    private TauxMarginalFamille construireTauxMarginalFamille(int annee) {
        TauxMarginalFamille txMarginal = new TauxMarginalFamille(annee);
        txMarginal.setTauxMarginalSeul(this.construireTauxMarginal(annee));
        return txMarginal;
    }

    private Bareme construireBaremeRevenuMarie(int annee) {
        if (annee < 2010) {
            MethodeIntegration methode = new MethodeIntegrationPointMilieu();
            Fonction tauxMarginal = construireTauxMarginalFamille(annee);
            if (annee < 2001) {
                BaremeFamille bareme = new BaremeFamille();
                bareme.setMethodeIntegration(methode);
                bareme.setTauxMarginal(tauxMarginal);
                bareme.setArrondi(TypeArrondi.CINQ_CTS);
                return bareme;
            } else {
                DiscretisationBaremeMarie discretisateur = new DiscretisationBaremeMarie();
                discretisateur.setTauxMarginal(tauxMarginal);
                discretisateur.setMethodeIntegration(methode);
                if (2001 == annee || 2002 == annee) {
                    discretisateur.largeur(200).jusqua(1000000);
                    discretisateur.setArrondi(TypeArrondi.CT);
                } else {
                    discretisateur.setArrondi(TypeArrondi.CINQ_CTS);
                    if (2003 == annee) {
                        discretisateur.largeur(100).jusqua(30000)
                                .largeur(200).jusqua(50000)
                                .largeur(500).jusqua(80000)
                                .largeur(1000).jusqua(1000000);
                    } else if (2004 == annee) {
                        discretisateur.largeur(100).jusqua(1000000);
                    } else {
                        discretisateur.largeur(100).jusqua(300000);
                    }
                }
                List<Point> points = discretisateur.obtenirPointsDiscretisation();
                BaremeDiscretiseEtInterpolationLineaire baremeDiscretise = new BaremeDiscretiseEtInterpolationLineaire();
                for (Point pt : points) {
                    baremeDiscretise.ajouterPointDiscretisation(pt);
                }

                BaremeRevenuChoixSuivantMontant baremeAvecRaccord = new BaremeRevenuChoixSuivantMontant();
                if (annee < 2001) {
                    baremeAvecRaccord.setLimiteBaremeFamille(2000000);
                } else if (annee < 2003) {
                    baremeAvecRaccord.setLimiteBaremeFamille(1000000);
                } else {
                    baremeAvecRaccord.setLimiteBaremeFamille(300000);
                }
                baremeAvecRaccord.setBaremeSeul(this.getBaremeRevenu(annee));
                baremeAvecRaccord.setBaremeFamille(baremeDiscretise);
                return baremeAvecRaccord;
            }
        }
        return null;
    }


    private ConstructeurBaremeIndexeTxMarginalConstantParTranche getConstructeurBaremeFortuneGenevois(
            int annee) {
        if (annee < 2010) {
            if (null == constructeurBaremeFortune) {
                ConstructeurBaremeIndexeTxMarginalConstantParTranche constructeur = new ConstructeurBaremeIndexeTxMarginalConstantParTranche()
                        .valideEntre(2001, 2009)
                        .indexateur(fournisseurIndexGenevois.getFournisseurIndiceGEBaseMai1993())
                        .anneeReferenceRencherissement(2000)
                        .premiereTranche(100000, "1.75 ‰")
                        .tranche(100000, 200000, "2.25 ‰")
                        .tranche(200000, 300000, "2.75 ‰")
                        .tranche(300000, 400000, "3 ‰")
                        .tranche(400000, 600000, "3.25 ‰")
                        .tranche(600000, 800000, "3.5 ‰")
                        .tranche(800000, 1000000, "3.75 ‰")
                        .tranche(1000000, 1200000, "4 ‰")
                        .tranche(1200000, 1500000, "4.25 ‰")
                        .derniereTranche(1500000, "4.5 ‰");
                constructeurBaremeFortune = constructeur;
            }
            return constructeurBaremeFortune;
        } else {
            // Après 2009, on change l'indexateur pour se baser sur les indices
            // dont la base est
            // décembre 2005.
            if (null == constructeurBaremeFortuneApres2009) {
                BaremeTauxMarginalConstantParTranche bareme2009 = construireBaremeFortune(2009);
                ConstructeurBaremeIndexeTxMarginalConstantParTranche constructeur = new ConstructeurBaremeIndexeTxMarginalConstantParTranche(
                        bareme2009)
                        .valideDepuis(2010)
                        .indexateur(fournisseurIndexGenevois.getFournisseurIndiceGEBaseDecembre2005())
                        .anneeReferenceRencherissement(2009);
                constructeurBaremeFortuneApres2009 = constructeur;
            }
            return constructeurBaremeFortuneApres2009;
        }
    }

    protected BaremeTauxMarginalConstantParTranche construireBaremeFortune(
            int annee) {
        BaremeTauxMarginalConstantParTranche bareme = getConstructeurBaremeFortuneGenevois(
                annee).typeArrondiTranche(TypeArrondi.CINQ_CTS).construire(annee);
        return bareme;
    }

    private ConstructeurBaremeIndexeTxMarginalConstantParTranche getConstructeurBaremeFortuneSupplementaire(
            int annee) {
        if (annee < 2010) {
            if (null == constructeurBaremeFortuneSupplementaire) {
                ConstructeurBaremeIndexeTxMarginalConstantParTranche constructeur = new ConstructeurBaremeIndexeTxMarginalConstantParTranche()
                        .valideEntre(2001, 2009)
                        .indexateur(fournisseurIndexGenevois.getFournisseurIndiceGEBaseMai1993())
                        .anneeReferenceRencherissement(2000)
                        .premiereTranche(100000, "0 ‰")
                        .tranche(100000, 200000, "0.1125 ‰")
                        .tranche(200000, 300000, "0.1375 ‰")
                        .tranche(300000, 400000, "0.3 ‰")
                        .tranche(400000, 600000, "0.325 ‰")
                        .tranche(600000, 800000, "0.525 ‰")
                        .tranche(800000, 1000000, "0.5625 ‰")
                        .tranche(1000000, 1200000, "0.8 ‰")
                        .tranche(1200000, 1500000, "0.85 ‰")
                        .tranche(1500000, 3000000, "1.125 ‰")
                        .derniereTranche(3000000, "1.35 ‰");
                constructeurBaremeFortuneSupplementaire = constructeur;
            }
            return constructeurBaremeFortuneSupplementaire;
        } else {
            if (null == constructeurBaremeFortuneSupplementaireApres2009) {
                BaremeTauxMarginalConstantParTranche bareme2009 = construireBaremeFortuneSupplementaire(2009);
                ConstructeurBaremeIndexeTxMarginalConstantParTranche constructeur = new ConstructeurBaremeIndexeTxMarginalConstantParTranche(bareme2009)
                        .valideDepuis(2010)
                        .indexateur(fournisseurIndexGenevois.getFournisseurIndiceGEBaseDecembre2005())
                        .anneeReferenceRencherissement(2009);
                constructeurBaremeFortuneSupplementaireApres2009 = constructeur;
            }
            return constructeurBaremeFortuneSupplementaireApres2009;
        }
    }

    public Bareme getBaremeFortuneSupplementaire(int annee) {
        if (!baremesFortuneSupplementaire.containsKey(annee)) {
            baremesFortuneSupplementaire.putIfAbsent(annee,
                    construireBaremeFortuneSupplementaire(annee));
        }
        return baremesFortuneSupplementaire.get(annee);
    }

    private BaremeTauxMarginalConstantParTranche construireBaremeFortuneSupplementaire(
            int annee) {
        return getConstructeurBaremeFortuneSupplementaire(annee).typeArrondiTranche(TypeArrondi.CINQ_CTS)
                .construire(annee);
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

        producteur.setTypeArrondiImposable(TypeArrondi.FRANC);
        producteur.setTypeArrondiDeterminant(TypeArrondi.FRANC);
        producteur.setTypeArrondiImpot(TypeArrondi.CINQ_CTS);
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

        producteur.setTypeArrondiImposable(TypeArrondi.FRANC);
        producteur.setTypeArrondiDeterminant(TypeArrondi.FRANC);
        producteur.setTypeArrondiImpot(TypeArrondi.CINQ_CTS);
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
        completerProducteurImpotsCantonaux(producteur, codeBeneficiaire, annee, TypeArrondi.CINQ_CTS);
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
        ProducteurImpot producteur = construireProducteurImpotsCantonauxRevenu(annee, TypeArrondi.CINQ_CTS);

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
        producteurImpotBase.setTypeArrondiImposable(TypeArrondi.FRANC);
        producteurImpotBase.setTypeArrondiDeterminant(TypeArrondi.FRANC);
        producteurImpotBase.setTypeArrondiImpot(TypeArrondi.CINQ_CTS);


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
        producteurImpotBase.setTypeArrondiImposable(TypeArrondi.FRANC);
        producteurImpotBase.setTypeArrondiDeterminant(TypeArrondi.FRANC);
        producteurImpotBase.setTypeArrondiImpot(TypeArrondi.CINQ_CTS);


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


    @Override
    public DeductionSociale getRegleDeductionSocialeCharge(int annee) {
        if (!deducSocialeCharge.containsKey(annee)) {
            DeductionSociale deduction = construireRegleDeductionSocialeCharge(annee);
            // Attention, la ConcurrentMap n'aime pas les nulls !!
            if (null != deduction) deducSocialeCharge.putIfAbsent(annee, deduction);
        }
        return deducSocialeCharge.get(annee);
    }

    private DeductionSociale construireRegleDeductionSocialeCharge(int annee) {
        if (annee < 2010) return null;
        if (annee > 2024) throw new IllegalArgumentException("Le montant des déductions sociales pour l'année '"
                + annee + "' doit être adapté !");
        DeductionChargeFamille deduction = new DeductionChargeFamille(annee);
        if (2010 == annee) deduction.setMontantParCharge(9000);
        else if (annee < 2013) deduction.setMontantParCharge(10000);
        else if (annee < 2017) deduction.setMontantParCharge(10078);
        else if (annee < 2021) deduction.setMontantParCharge(9980);
        else if (annee < 2025) deduction.setMontantParCharge(13000);
        return deduction;
    }

    @Override
    public DeductionSociale getRegleDeductionDoubleActivite(int annee) {
        if (!deducDoubleActivite.containsKey(annee)) {
            DeductionDoubleActivite deduction = construireRegleDeductionDoubleActivite(annee);
            // Attention, la ConcurrentMap n'aime pas les nulls !!
            if (null != deduction) deducDoubleActivite.putIfAbsent(annee, deduction);
        }
        return deducDoubleActivite.get(annee);
    }

    private DeductionDoubleActivite construireRegleDeductionDoubleActivite(int annee) {
        MontantIndexe montantIndexe
                = (annee < 2021 ) ? // LIPP Art. 72 alinea 15
                    new MontantIndexe(500,getIndexateurBaseDec2005(2009))
                    : new MontantIndexe(1000, getIndexateurBaseDec2005(2021));
        BigDecimal montantDeduction = montantIndexe.getMontantIndexe(annee);
        DeductionDoubleActivite deduction = new DeductionDoubleActivite(montantDeduction);
        return deduction;
    }

    public DeductionBeneficiaireRentesAVSAI getDeductionBeneficiaireRenteAVSAI(int annee) {
        if (annee < 2010)
            throw new IllegalArgumentException("Cette déduction ne peut pas être utilisé pour l'année " + annee);
        if (!deducSocialeRentier.containsKey(annee)) {
            DeductionBeneficiaireRentesAVSAI deduction = construireRegleDeductionRentierAVSAI(annee);
            // Attention, la ConcurrentMap n'aime pas les nulls !!
            if (null != deduction) deducSocialeRentier.putIfAbsent(annee, deduction);
        }
        return deducSocialeRentier.get(annee);
    }

    protected DeductionBeneficiaireRentesAVSAI construireRegleDeductionRentierAVSAI(int annee) {
        DeductionRentierAVS deduction = new DeductionRentierAVS(annee, construireBaremeDeductionBeneficiaireRentesAvsAi(annee), new BigDecimal("1.15"));
        return deduction;
    }

    protected BaremeParTranche construireBaremeDeductionBeneficiaireRentesAvsAi(int annee) {
        ConstructeurBareme cons = new ConstructeurBareme();
        // Voir le détail dans D 3 08.05: Règlement relatif à la compensation des effets de la progression à froid (RCEPF)
        // Art. 6
        if (annee < 2013) {
            cons.premiereTranche(50000, 10000)
                    .tranche(50000, 56700, 8000)
                    .tranche(56700, 64000, 6000)
                    .tranche(64000, 71500, 4000)
                    .tranche(71500, 80000, 2000)
                    .derniereTranche(80000, 0);
        } else if (annee < 2017) {
            cons.premiereTranche(57947, 10078)
                    .tranche(57947, 65707, 8062)
                    .tranche(65707, 74172, 6047)
                    .tranche(74172, 82839, 4031)
                    .tranche(82839, 92715, 2016)
                    .derniereTranche(92715, 0);
        } else if (annee < 2021) {
            cons.premiereTranche(57388, 9981)
                    .tranche(57388, 65073, 7984)
                    .tranche(65073, 73457, 5988)
                    .tranche(73457, 82040, 3992)
                    .tranche(82040, 91821, 1996)
                    .derniereTranche(91821, 0);
        } else {
            throw new IllegalArgumentException("le barème déduction pour rentes n'est pas définis pour année >= 2021 !!");
        }
        return cons.construireBaremeParTranche();
    }

}
