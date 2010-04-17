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
package ch.ge.afc.calcul.impot.federal;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import ch.ge.afc.bareme.Bareme;
import ch.ge.afc.bareme.BaremeTxMarginalEtEffectifParTranche;
import ch.ge.afc.calcul.impot.taxation.pp.ProducteurImpotAvecRabais;
import ch.ge.afc.calcul.impot.taxation.pp.ProducteurImpotBaseProgressif;
import ch.ge.afc.calcul.impot.federal.pp.source.CalculateurImpotSourcePrestationCapitalIFD;
import ch.ge.afc.calcul.impot.taxation.pp.DeductionSociale;
import ch.ge.afc.calcul.impot.taxation.pp.ProducteurImpot;
import ch.ge.afc.calcul.impot.taxation.pp.famille.DoubleBareme;
import ch.ge.afc.calcul.impot.taxation.pp.federal.deduction.DeductionSocialeParEnfant;
import ch.ge.afc.calcul.impot.taxation.pp.federal.deduction.DeductionSocialePourConjoints;
import ch.ge.afc.util.ExplicationDetailleTexteBuilder;
import ch.ge.afc.util.IExplicationDetailleeBuilder;
import ch.ge.afc.util.TypeArrondi;

public class Fournisseur implements FournisseurRegleImpotFederal {
	
	private ConcurrentMap<TypeBaremeIFDPersonnePhysique,Bareme> mapBaremeIFDPersonnePhysique = new ConcurrentHashMap<TypeBaremeIFDPersonnePhysique,Bareme>();
	private ConcurrentMap<Integer, ProducteurImpot> producteurImpotsFederauxPP = new ConcurrentHashMap<Integer, ProducteurImpot>();
	private ConcurrentMap<Integer, ProducteurImpot> producteurImpotsPrestationCapital = new ConcurrentHashMap<Integer, ProducteurImpot>();
	private ConcurrentMap<Integer, ProducteurImpot> producteurImpotSourcePrestationCapital = new ConcurrentHashMap<Integer, ProducteurImpot>();
	private ConcurrentMap<Integer,DeductionSociale> deducSocialeEnfant = new ConcurrentHashMap<Integer,DeductionSociale>(); 
	private ConcurrentMap<Integer,DeductionSociale> deducSocialeConjoint = new ConcurrentHashMap<Integer,DeductionSociale>(); 
	
	/**
	 * Retourne le barème IFD pour les personnes physiques.
	 * @param code le code du barüeme
	 * @return un barème à taux marginal (même si la dernière tranche possède un taux effectif).
	 */
	public Bareme getBaremeIFDPersonnePhysique(TypeBaremeIFDPersonnePhysique code) {
		if (!mapBaremeIFDPersonnePhysique.containsKey(code)) mapBaremeIFDPersonnePhysique.putIfAbsent(code, construireBaremeIFDPersonnePhysique(code));
		return mapBaremeIFDPersonnePhysique.get(code);
	}

	private Bareme construireBaremeIFDPersonnePhysique(TypeBaremeIFDPersonnePhysique code) {
		BaremeTxMarginalEtEffectifParTranche.Constructeur constructeur = new BaremeTxMarginalEtEffectifParTranche.Constructeur();
		if (TypeBaremeIFDPersonnePhysique.SEUL_2006_POST.equals(code)) {
			constructeur.tranche(  13600,  "0");
			constructeur.tranche(  29800,  "0.77 %");
			constructeur.tranche(  39000,  "0.88 %");
			constructeur.tranche(  52000,  "2.64 %");
			constructeur.tranche(  68300,  "2.97 %");
			constructeur.tranche(  73600,  "5.94 %");
			constructeur.tranche(  97700,  "6.60 %");
			constructeur.tranche( 127100,  "8.80 %");
			constructeur.tranche( 166200, "11.00 %");
			constructeur.tranche( 712400, "13.20 %");
			constructeur.derniereTranche( "11.50 %");
		} else if (TypeBaremeIFDPersonnePhysique.FAMILLE_2006_POST.equals(code)){
			constructeur.tranche(  26700,  "0");
			constructeur.tranche(  47900,  "1.00 %");
			constructeur.tranche(  54900,  "2.00 %");
			constructeur.tranche(  70900,  "3.00 %");
			constructeur.tranche(  85100,  "4.00 %");
			constructeur.tranche(  97400,  "5.00 %");
			constructeur.tranche( 108100,  "6.00 %");
			constructeur.tranche( 117000,  "7.00 %");
			constructeur.tranche( 124000,  "8.00 %");
			constructeur.tranche( 129300,  "9.00 %");
			constructeur.tranche( 132900, "10.00 %");
			constructeur.tranche( 134700, "11.00 %");
			constructeur.tranche( 136500, "12.00 %");
			constructeur.tranche( 843600, "13.00 %");
			constructeur.derniereTranche("11.50 %");
		} else if (TypeBaremeIFDPersonnePhysique.FAMILLE_2007_PRAE.equals(code)) {
			constructeur.tranche(  24500,  "0");
			constructeur.tranche(  44000,  "1.00 %");
			constructeur.tranche(  50500,  "2.00 %");
			constructeur.tranche(  65200,  "3.00 %");
			constructeur.tranche(  78200,  "4.00 %");
			constructeur.tranche(  89600,  "5.00 %");
			constructeur.tranche(  99400,  "6.00 %");
			constructeur.tranche( 107600,  "7.00 %");
			constructeur.tranche( 114100,  "8.00 %");
			constructeur.tranche( 118900,  "9.00 %");
			constructeur.tranche( 122200, "10.00 %");
			constructeur.tranche( 123900, "11.00 %");
			constructeur.tranche( 125600, "12.00 %");
			constructeur.tranche( 775900, "13.00 %");
			constructeur.derniereTranche("11.50 %");
		} else if (TypeBaremeIFDPersonnePhysique.SEUL_1996_POST.equals(code)) {
			constructeur.tranche(  12800,  "0");
			constructeur.tranche(  27900,  "0.77 %");
			constructeur.tranche(  36500,  "0.88 %");
			constructeur.tranche(  48600,  "2.64 %");
			constructeur.tranche(  63800,  "2.97 %");
			constructeur.tranche(  68800,  "5.94 %");
			constructeur.tranche(  91100,  "6.60 %");
			constructeur.tranche( 118400,  "8.80 %");
			constructeur.tranche( 154700, "11.00 %");
			constructeur.tranche( 664300, "13.20 %");
			constructeur.derniereTranche( "11.50 %");
		} else if (TypeBaremeIFDPersonnePhysique.FAMILLE_1996_POST.equals(code)) {
			constructeur.tranche(  24900,  "0");
			constructeur.tranche(  44700,  "1.00 %");
			constructeur.tranche(  51300,  "2.00 %");
			constructeur.tranche(  66200,  "3.00 %");
			constructeur.tranche(  79400,  "4.00 %");
			constructeur.tranche(  91000,  "5.00 %");
			constructeur.tranche( 101000,  "6.00 %");
			constructeur.tranche( 109300,  "7.00 %");
			constructeur.tranche( 115900,  "8.00 %");
			constructeur.tranche( 120900,  "9.00 %");
			constructeur.tranche( 124300, "10.00 %");
			constructeur.tranche( 126000, "11.00 %");
			constructeur.tranche( 127700, "12.00 %");
			constructeur.tranche( 788400, "13.00 %");
			constructeur.derniereTranche( "11.50 %");
		}
		constructeur.typeArrondi(TypeArrondi.CINQ_CTS_INF).seuil(25);
		return constructeur.construire();
	}
	
	protected Bareme construireBaremeImpotSourcePrestationCapital(int annee) {
		BaremeTxMarginalEtEffectifParTranche.Constructeur constructeur = new BaremeTxMarginalEtEffectifParTranche.Constructeur();
		constructeur.tranche(  25000,  "0");
		constructeur.tranche(  50000,  "0.25 %");
		constructeur.tranche(  75000,  "0.65 %");
		constructeur.tranche( 100000,  "1.10 %");
		constructeur.tranche( 125000,  "1.70 %");
		constructeur.tranche( 775000,  "2.60 %");
		constructeur.derniereTranche( "2.30 %");
		constructeur.typeArrondi(TypeArrondi.CINQ_CTS_INF);
		return constructeur.construire();
	}
	
	public ProducteurImpot getProducteurImpotsFederauxPP(int annee) {
		if (!producteurImpotsFederauxPP.containsKey(annee))
			producteurImpotsFederauxPP.putIfAbsent(annee,
					construireProducteurImpotsFederauxPP(annee));
		return producteurImpotsFederauxPP.get(annee);
	}

	public ProducteurImpot getProducteurImpotsPrestationCapital(int annee) {
		
		if (!producteurImpotsPrestationCapital.containsKey(annee))
			producteurImpotsPrestationCapital.putIfAbsent(annee,
					construireProducteurImpotsPrestationCapital(annee));
		return producteurImpotsPrestationCapital.get(annee);
	}
	
	public ProducteurImpot getProducteurImpotSourcePrestationCapital(int annee) {
		
		if (!producteurImpotSourcePrestationCapital.containsKey(annee))
			producteurImpotSourcePrestationCapital.putIfAbsent(annee,
					construireProducteurImpotSourcePrestationCapital(annee));
		return producteurImpotSourcePrestationCapital.get(annee);
	}
	
	private Bareme getBaremeRevenu(int annee) {
		if (annee < 2006) return this.getBaremeIFDPersonnePhysique(TypeBaremeIFDPersonnePhysique.SEUL_1996_POST);
		else return this.getBaremeIFDPersonnePhysique(TypeBaremeIFDPersonnePhysique.SEUL_2006_POST);
	}

	private Bareme construireBaremePrestationCapital(final Bareme bareme) {
		return new Bareme() {

			@Override
			public BigDecimal calcul(BigDecimal assiette) {
				BigDecimal impot = bareme.calcul(assiette);
				return TypeArrondi.CINQ_CTS.arrondirMontant(impot.multiply(new BigDecimal("0.2")));
			}
			
		};
	}
	
	private Bareme getBaremePrestationCapital(int annee) {
		if (annee < 2007) return construireBaremePrestationCapital(this.getBaremeIFDPersonnePhysique(TypeBaremeIFDPersonnePhysique.SEUL_1997_PRAE));
		else return construireBaremePrestationCapital(this.getBaremeIFDPersonnePhysique(TypeBaremeIFDPersonnePhysique.SEUL_2007_PRAE));
	}
	
	private Bareme getBaremeRevenuFamille(int annee) {
		if (annee < 2006) return this.getBaremeIFDPersonnePhysique(TypeBaremeIFDPersonnePhysique.FAMILLE_1996_POST);
		else return this.getBaremeIFDPersonnePhysique(TypeBaremeIFDPersonnePhysique.FAMILLE_2006_POST);
	}

	private Bareme getBaremePrestationCapitalFamille(int annee) {
		if (annee < 2007) return construireBaremePrestationCapital(this.getBaremeIFDPersonnePhysique(TypeBaremeIFDPersonnePhysique.FAMILLE_1997_PRAE));
		else return construireBaremePrestationCapital(this.getBaremeIFDPersonnePhysique(TypeBaremeIFDPersonnePhysique.FAMILLE_2007_PRAE));
	}
		
	private IExplicationDetailleeBuilder getNewExplicationBuilder() {
		return new ExplicationDetailleTexteBuilder();
	}
	
	private ProducteurImpot construireProducteurImpotsFederauxPP(int annee) {
		ProducteurImpotBaseProgressif producteurImpotBase = new ProducteurImpotBaseProgressif();
		producteurImpotBase.setStrategieProductionImpotFamille(new DoubleBareme(getBaremeRevenu(annee), getBaremeRevenuFamille(annee)));

		producteurImpotBase.setTypeArrondiImposable(TypeArrondi.CENT_FRANC_INF);
		producteurImpotBase.setTypeArrondiDeterminant(TypeArrondi.CENT_FRANC_INF);
		producteurImpotBase.setTypeArrondiImpot(TypeArrondi.CINQ_CTS_INF);
		
		
        ProducteurImpot producteur = null;
        if (annee < 2011) {
            producteur = new ProducteurImpot("IBR",""){
                @Override
                protected IExplicationDetailleeBuilder createExplicationBuilder() {return Fournisseur.this.getNewExplicationBuilder();}
            };
        } else {
            ProducteurRabaisEnfantPersonneNecessiteuse producteurRabais = new  ProducteurRabaisEnfantPersonneNecessiteuse();
            producteurRabais.setMontantRabaisParEnfantEtPersonneNecessiteuse(BigDecimal.valueOf(250));

            ProducteurImpotAvecRabais prodAvecRabais = new ProducteurImpotAvecRabais("IBR","RI","") {
                @Override
                protected IExplicationDetailleeBuilder createExplicationBuilder() {return Fournisseur.this.getNewExplicationBuilder();}
            };
            prodAvecRabais.setProducteurBaseRabais(producteurRabais);
            producteur = prodAvecRabais;
        }
		producteur.setProducteurBase(producteurImpotBase);
		return producteur;
	}
	
	private ProducteurImpot construireProducteurImpotsPrestationCapital(int annee) {
		ProducteurImpotBaseProgressif producteurImpotBase = new ProducteurImpotBaseProgressif();
		producteurImpotBase.setStrategieProductionImpotFamille(new DoubleBareme(getBaremePrestationCapital(annee), getBaremePrestationCapitalFamille(annee)));

		producteurImpotBase.setTypeArrondiImposable(TypeArrondi.CENT_FRANC_INF);
		producteurImpotBase.setTypeArrondiDeterminant(TypeArrondi.CENT_FRANC_INF);
		producteurImpotBase.setTypeArrondiImpot(TypeArrondi.CINQ_CTS_INF);
		
		
		
		ProducteurImpot producteur = new ProducteurImpot("IBR",""){
			@Override
			protected IExplicationDetailleeBuilder createExplicationBuilder() {return Fournisseur.this.getNewExplicationBuilder();}
		};
		producteur.setProducteurBase(producteurImpotBase);
		return producteur;
	}
	
	
	
	private ProducteurImpot construireProducteurImpotSourcePrestationCapital(int annee) {
		ProducteurImpotBaseProgressif producteurImpotBase = new ProducteurImpotBaseProgressif();
		producteurImpotBase.setBareme(construireBaremeImpotSourcePrestationCapital(annee));

		producteurImpotBase.setTypeArrondiImposable(TypeArrondi.CENT_FRANC_INF);
		producteurImpotBase.setTypeArrondiDeterminant(TypeArrondi.CENT_FRANC_INF);
		producteurImpotBase.setTypeArrondiImpot(TypeArrondi.CINQ_CTS_INF);

		ProducteurImpot producteur = new ProducteurImpot("IBR",""){
			@Override
			protected IExplicationDetailleeBuilder createExplicationBuilder() {return Fournisseur.this.getNewExplicationBuilder();}
		};
		producteur.setProducteurBase(producteurImpotBase);
		return producteur;
	}
	
	public DeductionSociale getRegleDeductionSocialeEnfant(int annee) {
		if (!deducSocialeEnfant.containsKey(annee)) {
			deducSocialeEnfant.putIfAbsent(annee, construireRegleDeductionSocialeEnfant(annee));
		}
		return deducSocialeEnfant.get(annee);
	}

	protected DeductionSociale construireRegleDeductionSocialeEnfant(int annee) {
		DeductionSocialeParEnfant deduction = new DeductionSocialeParEnfant(annee);
		if (annee > 2005) {
			deduction.setDeductionSocialeParEnfant(new BigDecimal("6100"));
		} else {
			deduction.setDeductionSocialeParEnfant(new BigDecimal("5600"));
		}
		return deduction;
	}
	
	public DeductionSociale getRegleDeductionSocialeConjoint(int annee) {
		if (annee < 2008) return null;
		if (!deducSocialeConjoint.containsKey(annee)) {
			deducSocialeConjoint.putIfAbsent(annee, construireRegleDeductionSocialeConjoint(annee));
		}
		return deducSocialeConjoint.get(annee);
	}

	protected DeductionSociale construireRegleDeductionSocialeConjoint(int annee) {
		DeductionSocialePourConjoints deduction = new DeductionSocialePourConjoints(annee);
		deduction.setDeducConjointsIFD(new BigDecimal("2500"));
		return deduction;
	}
	
	public CalculateurImpotSourcePrestationCapitalIFD getCalculateurImpotSourcePrestationCapitalIFD(int annee) {
		CalculateurImpotSourcePrestationCapitalIFD calculateur = new CalculateurImpotSourcePrestationCapitalIFD(annee);
		calculateur.setProducteurImpot(this.getProducteurImpotSourcePrestationCapital(annee));
		return calculateur;
	}
}
