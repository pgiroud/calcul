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
package org.impotch.calcul.impot.taxation.pp.ge.deduction.rabais;

import java.math.BigDecimal;

import org.impotch.calcul.ReglePeriodique;
import org.impotch.calcul.assurancessociales.CalculExtremaRentesAVS;
import org.impotch.calcul.impot.Souverainete;
import org.impotch.calcul.impot.cantonal.ge.pp.avant2010.ProducteurRabaisImpot;
import org.impotch.calcul.impot.cantonal.ge.pp.avant2010.SituationFamilialeGE;
import org.impotch.calcul.impot.taxation.pp.EnfantACharge;
import org.impotch.calcul.impot.taxation.pp.PersonneACharge;
import org.impotch.calcul.impot.taxation.pp.RegleAgeEnfant;
import org.impotch.util.TypeArrondi;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class ProducteurBaseRabaisImpot extends ReglePeriodique implements ProducteurRabaisImpot {

	private RegleAgeEnfant regleAge;
	private CalculExtremaRentesAVS regleRenteMaxi;
	
	private BigDecimal montantParEpoux;
	private BigDecimal montantParDemiCharge;
	private BigDecimal demiMontantFraisGarde;
	
	private BigDecimal montantDeducDoubleActivitePourFaibleRevenu;
	private BigDecimal montantDeducDoubleActivite;
	private BigDecimal plafondFaibleRevenu;
	
	public ProducteurBaseRabaisImpot(int annee) {
		super(annee);
	}

	/**
	 * @return the regleAge
	 */
	protected RegleAgeEnfant getRegleAge() {
		return regleAge;
	}

	/**
	 * @param regleAge the regleAge to set
	 */
	public void setRegleAge(RegleAgeEnfant regleAge) {
		this.regleAge = regleAge;
	}


	
	protected CalculExtremaRentesAVS getRegleRenteMaxi() {
		return regleRenteMaxi;
	}

	public void setRegleRenteMaxi(CalculExtremaRentesAVS regleRenteMaxi) {
		this.regleRenteMaxi = regleRenteMaxi;
	}

	/**
	 * @return the montantParEpoux
	 */
	protected BigDecimal getMontantParEpoux() {
		return montantParEpoux;
	}

	/**
	 * @param montantParEpoux the montantParEpoux to set
	 */
	public void setMontantParEpoux(BigDecimal montantParEpoux) {
		this.montantParEpoux = montantParEpoux;
	}

	/**
	 * @return the montantParDemiCharge
	 */
	protected BigDecimal getMontantParDemiCharge() {
		return montantParDemiCharge;
	}

	/**
	 * @param montantParDemiCharge the montantParDemiCharge to set
	 */
	public void setMontantParDemiCharge(BigDecimal montantParDemiCharge) {
		this.montantParDemiCharge = montantParDemiCharge;
	}

	/**
	 * @return the demiMontantFraisGarde
	 */
	protected BigDecimal getDemiMontantFraisGarde() {
		return demiMontantFraisGarde;
	}

	/**
	 * @param demiMontantFraisGarde the demiMontantFraisGarde to set
	 */
	public void setDemiMontantFraisGarde(BigDecimal demiMontantFraisGarde) {
		this.demiMontantFraisGarde = demiMontantFraisGarde;
	}

	/**
	 * @return the montantDeducDoubleActivitePourFaibleRevenu
	 */
	protected BigDecimal getMontantDeducDoubleActivitePourFaibleRevenu() {
		return montantDeducDoubleActivitePourFaibleRevenu;
	}

	/**
	 * @param montantDeducDoubleActivitePourFaibleRevenu the montantDeducDoubleActivitePourFaibleRevenu to set
	 */
	public void setMontantDeducDoubleActivitePourFaibleRevenu(
			BigDecimal montantDeducDoubleActivitePourFaibleRevenu) {
		this.montantDeducDoubleActivitePourFaibleRevenu = montantDeducDoubleActivitePourFaibleRevenu;
	}

	/**
	 * @return the montantDeducDoubleActivite
	 */
	protected BigDecimal getMontantDeducDoubleActivite() {
		return montantDeducDoubleActivite;
	}

	/**
	 * @param montantDeducDoubleActivite the montantDeducDoubleActivite to set
	 */
	public void setMontantDeducDoubleActivite(BigDecimal montantDeducDoubleActivite) {
		this.montantDeducDoubleActivite = montantDeducDoubleActivite;
	}

	/**
	 * @return the plafondFaibleRevenu
	 */
	protected BigDecimal getPlafondFaibleRevenu() {
		return plafondFaibleRevenu;
	}

	/**
	 * @param plafondFaibleRevenu the plafondFaibleRevenu to set
	 */
	public void setPlafondFaibleRevenu(BigDecimal plafondFaibleRevenu) {
		this.plafondFaibleRevenu = plafondFaibleRevenu;
	}

	protected BigDecimal getDeductionDoubleActivite(FournisseurMontantRabaisImpot fournisseurAssiette) {
		if (0 <= getPlafondFaibleRevenu().compareTo(fournisseurAssiette.getRevenuBrutTotaux())) {
			return getMontantDeducDoubleActivitePourFaibleRevenu();
		} else {
			return getMontantDeducDoubleActivite();
		}
	}
	
	private boolean hasCharge(SituationFamilialeGE situation) {
		return !(situation.getEnfants().isEmpty() && situation.getPersonnesNecessiteuses().isEmpty());
	}
	
	protected boolean existeJeuneEnfantCharge(SituationFamilialeGE situation) {
		for (EnfantACharge enfant : situation.getEnfants()) {
			if (!enfant.isDemiPart(Souverainete.CH_CANTONALE_GE) && getRegleAge().isJeuneEnfant(enfant, this.getAnnee())) {
				return true;
			}
		}
		return false;
	}
	
	protected boolean existeJeuneEnfantDemiCharge(SituationFamilialeGE situation) {
		for (EnfantACharge enfant : situation.getEnfants()) {
			if (enfant.isDemiPart(Souverainete.CH_CANTONALE_GE) && getRegleAge().isJeuneEnfant(enfant, this.getAnnee())) {
				return true;
			}
		}
		return false;
	}
	
	protected BigDecimal produireMontantAdditionnelRenteAVSAI(FournisseurMontantRabaisImpot fournisseur) {
		if (null == fournisseur.getSituationAVS() || null == fournisseur.getMontantRenteAVSPercu()) return BigDecimal.ZERO;
		BigDecimal renteMaximum = getRegleRenteMaxi().getRente(fournisseur.getSituationAVS()).getMontantAnnuelMaximum();
		// Détermination du coefficient
		BigDecimal coefficient = null;
		BigDecimal rapport = fournisseur.getRevenuPourMontantAdditionnelRenteAVS().divide(renteMaximum,10,BigDecimal.ROUND_HALF_UP);
		if (rapport.compareTo(new BigDecimal(3)) > 0) coefficient = BigDecimal.ZERO;
		else if (rapport.compareTo(new BigDecimal(2)) > 0) coefficient = new BigDecimal("0.3");
		else if (rapport.compareTo(new BigDecimal("1.5")) > 0) coefficient = new BigDecimal("0.4");
		else coefficient = new BigDecimal("0.5");
		return fournisseur.getMontantRenteAVSPercu().min(TypeArrondi.UNITE_LA_PLUS_PROCHE.arrondirMontant(renteMaximum.multiply(coefficient)));
	}
	
	
	@Override
	public BigDecimal produireMontantDeterminantRabais(SituationFamilialeGE situation, FournisseurMontantRabaisImpot fournisseur) {
		BigDecimal montantRabais = BigDecimal.ZERO;
		if (situation.isCouple()) {
			montantRabais = getMontantParEpoux().add(getMontantParEpoux());
			if (fournisseur.hasDoubleActivite()) montantRabais = montantRabais.add(getDeductionDoubleActivite(fournisseur));
		} else {
			if (situation.isConjointFonctionnaireInternational()) {
				montantRabais = getMontantParEpoux();
			} else {
				if (hasCharge(situation)) {
					montantRabais = getMontantParEpoux().add(getMontantParEpoux());
				} else {
					montantRabais = getMontantParEpoux().add(getDemiMontantFraisGarde());
				}
			}
		}
		// On traite les charges
		for (PersonneACharge personne : situation.getEnfants()) {
			montantRabais = montantRabais.add(getMontantParDemiCharge());
			if (!personne.isDemiPart(Souverainete.CH_CANTONALE_GE)) {
				montantRabais = montantRabais.add(getMontantParDemiCharge());
			}
		}
		for (PersonneACharge personne : situation.getPersonnesNecessiteuses()) {
			montantRabais = montantRabais.add(getMontantParDemiCharge());
			if (!personne.isDemiPart(Souverainete.CH_CANTONALE_GE)) {
				montantRabais = montantRabais.add(getMontantParDemiCharge());
			}
		}
		// Les frais de garde
		if (existeJeuneEnfantCharge(situation)) {
			montantRabais = montantRabais.add(getDemiMontantFraisGarde());
			if (situation.isCoupleDomicilieGeneve()) {
				montantRabais = montantRabais.add(getDemiMontantFraisGarde());
			}
		}
		if (existeJeuneEnfantDemiCharge(situation)) {
			montantRabais = montantRabais.add(getDemiMontantFraisGarde());
		}
//		if (existeJeuneEnfant(situation)) {
//			montantRabais = montantRabais.add(getDemiMontantFraisGarde());
//			if ((!situation.isCouple() || situation.isCoupleDomicilieGeneve()) && !situation.isConjointFonctionnaireInternational()) {
//				montantRabais = montantRabais.add(getDemiMontantFraisGarde());
//			} 
//		}
		// Montant additionnel pour les rentiers AVS AI
		if (null != fournisseur.getSituationAVS()) {
			BigDecimal montantAdditionnelAVSAI = produireMontantAdditionnelRenteAVSAI(fournisseur);
			montantRabais = montantRabais.add(montantAdditionnelAVSAI);
		}
		
		return montantRabais;
	}

}
