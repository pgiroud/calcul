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
package org.impotch.calcul.impot.federal;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.impotch.bareme.Bareme;
import org.impotch.calcul.impot.federal.dao.FournisseurBaremeIFD;
import org.impotch.calcul.impot.taxation.pp.ProducteurImpotAvecRabais;
import org.impotch.calcul.impot.taxation.pp.ProducteurImpotBaseProgressif;
import org.impotch.calcul.impot.federal.pp.source.CalculateurImpotSourcePrestationCapitalIFD;
import org.impotch.calcul.impot.taxation.pp.DeductionSociale;
import org.impotch.calcul.impot.taxation.pp.ProducteurImpot;
import org.impotch.calcul.impot.taxation.pp.famille.DoubleBareme;
import org.impotch.calcul.impot.taxation.pp.federal.deduction.DeductionDoubleActivite;
import org.impotch.calcul.impot.taxation.pp.federal.deduction.DeductionSocialeParEnfant;
import org.impotch.calcul.impot.taxation.pp.federal.deduction.DeductionSocialePourConjoints;
import org.impotch.calcul.impot.taxation.pp.federal.deduction.IDeductionDoubleActivite;
import org.impotch.calcul.util.ExplicationDetailleTexteBuilder;
import org.impotch.calcul.util.IExplicationDetailleeBuilder;
import org.impotch.util.TypeArrondi;

import javax.annotation.Resource;

public class Fournisseur implements FournisseurRegleImpotFederal {
	
	private ConcurrentMap<Integer, ProducteurImpot> producteurImpotsFederauxPP = new ConcurrentHashMap<Integer, ProducteurImpot>();
	private ConcurrentMap<Integer, ProducteurImpot> producteurImpotsPrestationCapital = new ConcurrentHashMap<Integer, ProducteurImpot>();
	private ConcurrentMap<Integer, ProducteurImpot> producteurImpotSourcePrestationCapital = new ConcurrentHashMap<Integer, ProducteurImpot>();

    private ConcurrentMap<Integer,IDeductionDoubleActivite> deducDoubleActivite = new ConcurrentHashMap<Integer, IDeductionDoubleActivite>();
	private ConcurrentMap<Integer,DeductionSociale> deducSocialeEnfant = new ConcurrentHashMap<Integer,DeductionSociale>(); 
	private ConcurrentMap<Integer,DeductionSociale> deducSocialeConjoint = new ConcurrentHashMap<Integer,DeductionSociale>(); 

    @Resource(name = "fournisseurBaremeIFD")
    private FournisseurBaremeIFD fournisseurBaremeIFD;

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
        return fournisseurBaremeIFD.getBaremeImpotRevenuPersonnePhysiquePourPersonneSeule(annee);
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
        Bareme bareme = 2011 > annee ? fournisseurBaremeIFD.getBaremeImpotRevenuPraeNumerandoPersonnePhysiquePourPersonneSeule(annee) : fournisseurBaremeIFD.getBaremeImpotRevenuPersonnePhysiquePourPersonneSeule(annee);
        return construireBaremePrestationCapital(bareme);
	}
	
	private Bareme getBaremeRevenuFamille(int annee) {
        return fournisseurBaremeIFD.getBaremeImpotRevenuPersonnePhysiquePourFamille(annee);
	}

	private Bareme getBaremePrestationCapitalFamille(int annee) {
        Bareme bareme =  2011 > annee ? fournisseurBaremeIFD.getBaremeImpotRevenuPraeNumerandoPersonnePhysiquePourFamille(annee) : fournisseurBaremeIFD.getBaremeImpotRevenuPersonnePhysiquePourFamille(annee);
        return construireBaremePrestationCapital(bareme);
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
			if (annee > 2022) {
				producteurRabais.setMontantRabaisParEnfantEtPersonneNecessiteuse(255);
			} else if (annee > 2011) {
                producteurRabais.setMontantRabaisParEnfantEtPersonneNecessiteuse(251);
            } else {
                producteurRabais.setMontantRabaisParEnfantEtPersonneNecessiteuse(250);
            }

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
		producteurImpotBase.setBareme(fournisseurBaremeIFD.getBaremeImpotSourcePrestationCapital(annee));

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

    public IDeductionDoubleActivite getDeductionDoubleActivite(int annee) {
        if (!deducDoubleActivite.containsKey(annee)) {
            deducDoubleActivite.putIfAbsent(annee, construireRegleDeductionDoubleActivite(annee));
        }
        return deducDoubleActivite.get(annee);
    }

    private IDeductionDoubleActivite construireRegleDeductionDoubleActivite(int annee) {
        DeductionDoubleActivite regle = new DeductionDoubleActivite(annee);
        if (annee <= 2007) {
            regle.setPlafond(7000);
        } else {
            regle.setTaux("50 %");
			if (annee >= 2023) {
				regle.setPlancher(8300);
				regle.setPlafond(13600);
			} else if (annee >= 2012) {
                regle.setPlancher(8100);
                regle.setPlafond(13400);
            } else {
                regle.setPlancher(7600);
                regle.setPlafond(12500);
            }
        }
        return regle;
    }

	public DeductionSociale getRegleDeductionSocialeEnfant(int annee) {
		if (!deducSocialeEnfant.containsKey(annee)) {
			deducSocialeEnfant.putIfAbsent(annee, construireRegleDeductionSocialeEnfant(annee));
		}
		return deducSocialeEnfant.get(annee);
	}

	protected DeductionSociale construireRegleDeductionSocialeEnfant(int annee) {
		DeductionSocialeParEnfant deduction = new DeductionSocialeParEnfant(annee);
		if (annee > 2022) {
			deduction.setDeductionSocialeParEnfant(6600);
		} else if (annee > 2011) {
			deduction.setDeductionSocialeParEnfant(6500);
		} else if (annee > 2010) {
			deduction.setDeductionSocialeParEnfant(6400);
		} else if (annee > 2005) {
			deduction.setDeductionSocialeParEnfant(6100);
		} else {
			deduction.setDeductionSocialeParEnfant(5600);
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
		if (annee > 2022) {
			deduction.setDeducConjointsIFD(2700);
		} else if (annee > 2010) {
			deduction.setDeducConjointsIFD(2600);
		} else {
			deduction.setDeducConjointsIFD(2500);
		}
		return deduction;
	}
	
	public CalculateurImpotSourcePrestationCapitalIFD getCalculateurImpotSourcePrestationCapitalIFD(int annee) {
		CalculateurImpotSourcePrestationCapitalIFD calculateur = new CalculateurImpotSourcePrestationCapitalIFD(annee);
		calculateur.setProducteurImpot(this.getProducteurImpotSourcePrestationCapital(annee));
		return calculateur;
	}
}
