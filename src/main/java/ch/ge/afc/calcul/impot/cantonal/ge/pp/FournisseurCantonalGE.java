/**
 * This file is part of CalculImpotCH.
 *
 * CalculImpotCH is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * CalculImpotCH is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CalculImpotCH.  If not, see <http://www.gnu.org/licenses/>.
 */
package ch.ge.afc.calcul.impot.cantonal.ge.pp;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import ch.ge.afc.bareme.Bareme;
import ch.ge.afc.bareme.BaremeConstantParTranche;
import ch.ge.afc.bareme.BaremeDiscretiseEtInterpolationLineaire;
import ch.ge.afc.bareme.BaremeTauxMarginalConstantParTranche;
import ch.ge.afc.bareme.BaremeTauxMarginalIntegrable;
import ch.ge.afc.bareme.Point;
import ch.ge.afc.calcul.assurancessociales.FournisseurRegleCalculAssuranceSociale;
import ch.ge.afc.calcul.impot.ProducteurImpotDerivePourcent;
import ch.ge.afc.calcul.impot.cantonal.FournisseurCantonal;
import ch.ge.afc.calcul.impot.cantonal.ge.ProducteurImpotCommunalGE;
import ch.ge.afc.calcul.impot.cantonal.ge.param.FournisseurParametrageCommunaleGE;
import ch.ge.afc.calcul.impot.cantonal.ge.pp.avant2010.BaremeRevenuChoixSuivantMontant;
import ch.ge.afc.calcul.impot.cantonal.ge.pp.avant2010.ConstructeurBaremeIndexeTxMarginalConstantParTranche;
import ch.ge.afc.calcul.impot.cantonal.ge.pp.avant2010.DiscretisationBaremeMarie;
import ch.ge.afc.calcul.impot.cantonal.ge.pp.avant2010.DoubleBaremeGE;
import ch.ge.afc.calcul.impot.cantonal.ge.pp.avant2010.ProducteurImpotAvecRabais;
import ch.ge.afc.calcul.impot.cantonal.ge.pp.avant2010.ProducteurRabaisImpot;
import ch.ge.afc.calcul.impot.cantonal.ge.pp.avant2010.TauxMarginalFamille;
import ch.ge.afc.calcul.impot.cantonal.ge.pp.avant2010.TauxMarginalSeul;
import ch.ge.afc.calcul.impot.cantonal.ge.pp.indexateur.FournisseurIndicePeriodiqueGE;
import ch.ge.afc.calcul.impot.cantonal.ge.pp.indexateur.IndexateurMontant;
import ch.ge.afc.calcul.impot.cantonal.ge.pp.indexateur.IndexateurQuadriennal;
import ch.ge.afc.calcul.impot.taxation.pp.DeductionSociale;
import ch.ge.afc.calcul.impot.taxation.pp.ProducteurImpot;
import ch.ge.afc.calcul.impot.taxation.pp.RegleAgeEnfant;
import ch.ge.afc.calcul.impot.taxation.pp.StrategieProductionImpotFamille;
import ch.ge.afc.calcul.impot.taxation.pp.famille.ImpositionFamilleSansAvantage;
import ch.ge.afc.calcul.impot.taxation.pp.famille.Splitting;
import ch.ge.afc.calcul.impot.taxation.pp.ge.deduction.DeductionBeneficiaireRentesAVSAI;
import ch.ge.afc.calcul.impot.taxation.pp.ge.deduction.DeductionChargeFamille;
import ch.ge.afc.calcul.impot.taxation.pp.ge.deduction.DeductionRentierAVS;
import ch.ge.afc.calcul.impot.taxation.pp.ge.deduction.rabais.ProducteurBaseRabaisImpot;
import ch.ge.afc.util.ExplicationDetailleTexteBuilder;
import ch.ge.afc.util.IExplicationDetailleeBuilder;
import ch.ge.afc.util.TypeArrondi;
import ch.ge.afc.util.math.integration.MethodeIntegrationPointMilieu;

public class FournisseurCantonalGE extends FournisseurCantonal implements FournisseurRegleImpotCantonalGE {
	
	
	private FournisseurRegleCalculAssuranceSociale fournisseurRegleCalculCotisationAssuranceSociale;
	
	private FournisseurIndicePeriodiqueGE fournisseurIndiceBaseMai1993;
	private FournisseurIndicePeriodiqueGE fournisseurIndiceBaseDecembre2005;

	private IndexateurMontant indexateurBaseMai1993;
	
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

	
	private ConcurrentMap<Integer,DeductionSociale> deducSocialeCharge = new ConcurrentHashMap<Integer,DeductionSociale>(); 
	private ConcurrentMap<Integer,DeductionBeneficiaireRentesAVSAI> deducSocialeRentier = new ConcurrentHashMap<Integer,DeductionBeneficiaireRentesAVSAI>(); 

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

	public void setFournisseurIndiceBaseMai1993(FournisseurIndicePeriodiqueGE fournisseur) {
		fournisseurIndiceBaseMai1993 = fournisseur;
	}

	private FournisseurIndicePeriodiqueGE getFournisseurIndiceBaseMai1993() {
		return fournisseurIndiceBaseMai1993;
	}

	public void setFournisseurIndiceBaseDecembre2005(FournisseurIndicePeriodiqueGE fournisseur) {
		fournisseurIndiceBaseDecembre2005 = fournisseur;
	}

	private FournisseurIndicePeriodiqueGE getFournisseurIndiceBaseDecembre2005() {
		return fournisseurIndiceBaseDecembre2005;
	}

	public IndexateurMontant getIndexateurBaseMai1993(int anneeBaseIndexation) {
		if (null == indexateurBaseMai1993) {
			IndexateurQuadriennal indexateur = new IndexateurQuadriennal(anneeBaseIndexation);
			indexateur.setFournisseurIndice(getFournisseurIndiceBaseMai1993());
			indexateurBaseMai1993 = indexateur;
		}
		return indexateurBaseMai1993;
		
	}
	
	private IExplicationDetailleeBuilder getNewExplicationBuilder() {
		return new ExplicationDetailleTexteBuilder();
	}

	
	private TauxMarginalSeul construireTauxMarginal(int annee) {
		TauxMarginalSeul txMarginal = new TauxMarginalSeul(annee);
		txMarginal.setIndexateur(this.getFournisseurIndiceBaseMai1993());
		return txMarginal;
	}
	
	protected Bareme construireBaremeRevenu(int annee) {
		if (annee < 2010) {
			BaremeTauxMarginalIntegrable bareme = new BaremeTauxMarginalIntegrable();
			bareme.setTypeArrondi(TypeArrondi.CINQ_CTS);
			bareme.setTauxMarginal(construireTauxMarginal(annee));
			return bareme;
		} else {
			ConstructeurBaremeIndexeTxMarginalConstantParTranche constructeur = new ConstructeurBaremeIndexeTxMarginalConstantParTranche();
			constructeur.validite(2010);
			constructeur.anneeReferenceRencherissement(2009);
			constructeur.indexateur(getFournisseurIndiceBaseDecembre2005());
			constructeur.tranche(17493, "0 %");
			constructeur.tranche(21076, "8 %");
			constructeur.tranche(23184, "9 %");
			constructeur.tranche(25291, "10 %");
			constructeur.tranche(27399, "11 %");
			constructeur.tranche(32668, "12 %");
			constructeur.tranche(36883, "13 %");
			constructeur.tranche(41099, "14 %");
			constructeur.tranche(45314, "14.5 %");
			constructeur.tranche(72713, "15 %");
			constructeur.tranche(119081, "15.5 %");
			constructeur.tranche(160179, "16 %");
			constructeur.tranche(181256, "16.5 %");
			constructeur.tranche(259238, "17 %");
			constructeur.tranche(276099, "17.5 %");
			constructeur.tranche(388857, "18 %");
			constructeur.tranche(609103, "18.5 %");
			constructeur.derniereTranche("19 %");
			return constructeur.typeArrondiTranche(TypeArrondi.CINQ_CTS).construire(annee);
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
			DiscretisationBaremeMarie discretisateur = new DiscretisationBaremeMarie();
			discretisateur.setTauxMarginal(construireTauxMarginalFamille(annee));
			discretisateur.setMethodeIntegration(new MethodeIntegrationPointMilieu());
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
			if (annee < 2003) {
				baremeAvecRaccord.setLimiteBaremeFamille(1000000);
			} else {
				baremeAvecRaccord.setLimiteBaremeFamille(300000);
			}
			baremeAvecRaccord.setBaremeSeul(this.getBaremeRevenu(annee));
			baremeAvecRaccord.setBaremeFamille(baremeDiscretise);
			return baremeAvecRaccord;
		}
		return null;
	}

	
	
	private ConstructeurBaremeIndexeTxMarginalConstantParTranche getConstructeurBaremeFortuneGenevois(
			int annee) {
		if (annee < 2010) {
			if (null == constructeurBaremeFortune) {
				ConstructeurBaremeIndexeTxMarginalConstantParTranche constructeur = new ConstructeurBaremeIndexeTxMarginalConstantParTranche();
				constructeur.validite(2001, 2009);
				constructeur.indexateur(getFournisseurIndiceBaseMai1993());
				constructeur.anneeReferenceRencherissement(2000);
				constructeur.tranche(100000, "1.75 ‰");
				constructeur.tranche(200000, "2.25 ‰");
				constructeur.tranche(300000, "2.75 ‰");
				constructeur.tranche(400000, "3 ‰");
				constructeur.tranche(600000, "3.25 ‰");
				constructeur.tranche(800000, "3.5 ‰");
				constructeur.tranche(1000000, "3.75 ‰");
				constructeur.tranche(1200000, "4 ‰");
				constructeur.tranche(1500000, "4.25 ‰");
				constructeur.derniereTranche("4.5 ‰");
				constructeurBaremeFortune = constructeur;
			}
			return constructeurBaremeFortune;
		} else {
			// Après 2009, on change l'indexateur pour se basé sur les indices
			// dont la base est
			// décembre 2005.
			if (null == constructeurBaremeFortuneApres2009) {
				BaremeTauxMarginalConstantParTranche bareme2009 = construireBaremeFortune(2009);
				ConstructeurBaremeIndexeTxMarginalConstantParTranche constructeur = new ConstructeurBaremeIndexeTxMarginalConstantParTranche(
						bareme2009);
				constructeur.validite(2010);
				constructeur.indexateur(getFournisseurIndiceBaseDecembre2005());
				constructeur.anneeReferenceRencherissement(2009);
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
				ConstructeurBaremeIndexeTxMarginalConstantParTranche constructeur = new ConstructeurBaremeIndexeTxMarginalConstantParTranche();
				constructeur.validite(2001, 2009);
				constructeur.indexateur(getFournisseurIndiceBaseMai1993());
				constructeur.anneeReferenceRencherissement(2000);
				constructeur.tranche(100000, "0 ‰");
				constructeur.tranche(200000, "0.1125 ‰");
				constructeur.tranche(300000, "0.1375 ‰");
				constructeur.tranche(400000, "0.3 ‰");
				constructeur.tranche(600000, "0.325 ‰");
				constructeur.tranche(800000, "0.525 ‰");
				constructeur.tranche(1000000, "0.5625 ‰");
				constructeur.tranche(1200000, "0.8 ‰");
				constructeur.tranche(1500000, "0.85 ‰");
				constructeur.tranche(3000000, "1.125 ‰");
				constructeur.derniereTranche("1.35 ‰");
				constructeurBaremeFortuneSupplementaire = constructeur;
			}
			return constructeurBaremeFortuneSupplementaire;
		} else {
			if (null == constructeurBaremeFortuneSupplementaireApres2009) {
				BaremeTauxMarginalConstantParTranche bareme2009 = construireBaremeFortuneSupplementaire(2009);
				ConstructeurBaremeIndexeTxMarginalConstantParTranche constructeur = new ConstructeurBaremeIndexeTxMarginalConstantParTranche(
						bareme2009);
				constructeur.validite(2010);
				constructeur.indexateur(getFournisseurIndiceBaseDecembre2005());
				constructeur.anneeReferenceRencherissement(2009);
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
		if (annee >= 2010 || annee < 2001) throw new IllegalArgumentException("Le rabais d'impôt est un processus défini uniquement entre les années 2001 et 2009 incluses !!");
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
	
	public ProducteurImpot construireProducteurImpotsCantonauxRevenu(int annee) {
		ProducteurImpot producteur;
		String codeBeneficiaire = "CAN-GE";
		StrategieProductionImpotFamille strategie = null;
		if (annee < 2010) {
			ProducteurImpotAvecRabais prodRabais = new ProducteurImpotAvecRabais("IBR","RI",codeBeneficiaire){
				@Override
				protected IExplicationDetailleeBuilder createExplicationBuilder() {return FournisseurCantonalGE.this.getNewExplicationBuilder();}
			};
			strategie = new DoubleBaremeGE(getBaremeRevenu(annee), getBaremeRevenuFamille(annee));
			prodRabais.setProducteurMinimumVital(construireProducteurRabaisImpot(annee));
			producteur = prodRabais;
		} else {
			producteur = new ProducteurImpot("IBR",codeBeneficiaire){
				@Override
				protected IExplicationDetailleeBuilder createExplicationBuilder() {return FournisseurCantonalGE.this.getNewExplicationBuilder();}
			};
			strategie = new Splitting(getBaremeRevenu(annee),"50 %");
		}
		
		producteur.setStrategieProductionImpotFamille(strategie);

		producteur.setTypeArrondiImposable(TypeArrondi.FRANC);
		producteur.setTypeArrondiDeterminant(TypeArrondi.FRANC);
		producteur.setTypeArrondiImpot(TypeArrondi.CINQ_CTS);

		IExplicationDetailleeBuilder explication = getNewExplicationBuilder();
		explication.ajouter("Réduction de {1,number,percent} sur impôt de base sur revenu {0,number,#,##0.00}");
		explication.ajouter("{2,number,#,##0.00}");
		ProducteurImpotDerivePourcent prodRIBR = new ProducteurImpotDerivePourcent("RIBR","-12 %",codeBeneficiaire);
		prodRIBR.setExplicationDetailleePattern(explication.getTexte());
		producteur.ajouteProducteurDerive(prodRIBR);
		
		explication.reset();
		explication.ajouter("Réduction de {1,number,percent} sur cts add. cantonaux sur revenu {0,number,#,##0.00}");
		explication.ajouter("{2,number,#,##0.00}");
		ProducteurImpotDerivePourcent prodRCAR = new ProducteurImpotDerivePourcent("RCAR","-12 %",codeBeneficiaire);
		prodRCAR.setExplicationDetailleePattern(explication.getTexte());
		
		explication.reset();
		explication.ajouter("CA Revenu :");
		explication.ajouter("Total impôt de base revenu ({0,number,#,##0.00}) * {1,number,percent}");
		explication.ajouter("{2,number,#,##0.00}");
		ProducteurImpotDerivePourcent ctsAddCantonaux = new ProducteurImpotDerivePourcent("CAR","47.5 %",codeBeneficiaire);
		ctsAddCantonaux.setProducteurDerive(prodRCAR);
		ctsAddCantonaux.setExplicationDetailleePattern(explication.getTexte());
		producteur.ajouteProducteurDerive(ctsAddCantonaux);
		
		explication.reset();
		explication.ajouter("CA Aide à domicile Revenu :");
		explication.ajouter("Total impôt de base revenu ({0,number,#,##0.00}) * {1,number,percent}");
		explication.ajouter("{2,number,#,##0.00}");
		ProducteurImpotDerivePourcent prodADR = new ProducteurImpotDerivePourcent("ADR","1 %",codeBeneficiaire);
		prodADR.setExplicationDetailleePattern(explication.getTexte());
		producteur.ajouteProducteurDerive(prodADR);

		return producteur;

	}
	
	private ProducteurImpot construireProducteurImpotsICCRevenu(int annee) {
		ProducteurImpot producteur = construireProducteurImpotsCantonauxRevenu(annee);

		// On ajoute ensuite les impôts communaux
		ProducteurImpotCommunalGE prodComm = new ProducteurImpotCommunalGEPersPhysique("PPR","COR"){
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
		String codeBeneficiaire = "CAN-GE";
		ProducteurImpot producteur = new ProducteurImpot("IBF",codeBeneficiaire){
			@Override
			protected IExplicationDetailleeBuilder createExplicationBuilder() {return FournisseurCantonalGE.this.getNewExplicationBuilder();}
		};
		producteur.setStrategieProductionImpotFamille(new ImpositionFamilleSansAvantage(this.getBaremeFortune(annee)));
		producteur.setTypeArrondiImposable(TypeArrondi.FRANC);
		producteur.setTypeArrondiDeterminant(TypeArrondi.FRANC);
		producteur.setTypeArrondiImpot(TypeArrondi.CINQ_CTS);
		
		IExplicationDetailleeBuilder explication = getNewExplicationBuilder();
		explication.ajouter("CA Fortune :");
		explication.ajouter("Total impôt de base fortune ({0,number,#,##0.00}) * {1,number,percent}");
		explication.ajouter("{2,number,#,##0.00}");
		ProducteurImpotDerivePourcent prodCAF = new ProducteurImpotDerivePourcent("CAF","47.5 %",codeBeneficiaire);
		prodCAF.setExplicationDetailleePattern(explication.getTexte());
		producteur.ajouteProducteurDerive(prodCAF);

		explication.reset();
		explication.ajouter("CA Aide à domicile Fortune :");
		explication.ajouter("Total impôt de base fortune ({0,number,#,##0.00}) * {1,number,percent}");
		explication.ajouter("{2,number,#,##0.00}");
		ProducteurImpotDerivePourcent prodADF = new ProducteurImpotDerivePourcent("ADF","1 %",codeBeneficiaire);
		prodADF.setExplicationDetailleePattern(explication.getTexte());
		producteur.ajouteProducteurDerive(prodADF);

		// On ajoute ensuite les impôts communaux
		ProducteurImpotCommunalGE prodComm = new ProducteurImpotCommunalGEPersPhysique("PPF","COF") {
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
		String codeBeneficiaire = "CAN-GE";
		ProducteurImpot producteur = new ProducteurImpot("ISF",codeBeneficiaire){
			@Override
			protected IExplicationDetailleeBuilder createExplicationBuilder() {return FournisseurCantonalGE.this.getNewExplicationBuilder();}
		};
		producteur.setStrategieProductionImpotFamille(new ImpositionFamilleSansAvantage(this.getBaremeFortuneSupplementaire(annee)));
		producteur.setTypeArrondiImposable(TypeArrondi.FRANC);
		producteur.setTypeArrondiDeterminant(TypeArrondi.FRANC);
		producteur.setTypeArrondiImpot(TypeArrondi.CINQ_CTS);
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
		DeductionChargeFamille deduction = new DeductionChargeFamille(annee);
		if (2010 == annee) deduction.setMontantParCharge(new BigDecimal("9000"));
		else deduction.setMontantParCharge(new BigDecimal("10000"));
		return deduction;
	}

	public DeductionBeneficiaireRentesAVSAI getDeductionBeneficiaireRenteAVSAI(int annee) {
		if (annee < 2010) throw new IllegalArgumentException("Cette déduction ne peut pas être utilisé pour l'année " + annee); 
		if (!deducSocialeRentier.containsKey(annee)) {
			DeductionBeneficiaireRentesAVSAI deduction = construireRegleDeductionRentierAVSAI(annee);
			// Attention, la ConcurrentMap n'aime pas les nulls !!
			if (null != deduction) deducSocialeRentier.putIfAbsent(annee, deduction);
		}
		return deducSocialeRentier.get(annee);
	}
	
	protected DeductionBeneficiaireRentesAVSAI construireRegleDeductionRentierAVSAI(int annee) {
		DeductionRentierAVS deduction = new DeductionRentierAVS(annee,construireBaremeDeductionBeneficiaireRentesAvsAi(annee), new BigDecimal("1.15"));
		return deduction;
	}
	
	protected BaremeConstantParTranche construireBaremeDeductionBeneficiaireRentesAvsAi(int annee) {
		BaremeConstantParTranche bareme = new BaremeConstantParTranche();
		bareme.ajouterTranche(50000, 10000);
		bareme.ajouterTranche(56700,  8000);
		bareme.ajouterTranche(64000,  6000);
		bareme.ajouterTranche(71500,  4000);
		bareme.ajouterTranche(80000,  2000);
		bareme.ajouterDerniereTranche(0);
		return bareme;
	}
	
}
