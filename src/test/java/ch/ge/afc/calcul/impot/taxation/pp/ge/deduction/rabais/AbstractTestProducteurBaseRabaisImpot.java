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
package ch.ge.afc.calcul.impot.taxation.pp.ge.deduction.rabais;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import ch.ge.afc.calcul.assurancessociales.SituationAVS;
import ch.ge.afc.calcul.impot.cantonal.ge.pp.avant2010.ConstructeurSituationFamilialeGE;
import ch.ge.afc.calcul.impot.cantonal.ge.pp.avant2010.SituationFamilialeGE;
import ch.ge.afc.calcul.impot.taxation.pp.RegleAgeEnfant;
import ch.ge.afc.calcul.impot.taxation.pp.ge.deduction.rabais.FournisseurMontantRabaisImpot;
import ch.ge.afc.calcul.impot.taxation.pp.ge.deduction.rabais.ProducteurBaseRabaisImpot;
import ch.ge.afc.calcul.assurancessociales.Fournisseur;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
class AbstractTestProducteurBaseRabaisImpot {

	private ConstructeurSituationFamilialeGE constructeurSituation = new ConstructeurSituationFamilialeGE();
	
	protected RegleAgeEnfant contruireRegleAge() {
		RegleAgeEnfant regleAge = new RegleAgeEnfant();
		regleAge.setAgeLimiteJeuneEnfant(12);
		regleAge.setAgeMajorite(18);
		regleAge.setAgeLimiteJeuneAdulte(25);
		return regleAge;
	}
	
	protected ProducteurBaseRabaisImpot construireProducteur(int annee, int montantParEpoux, int deducDoubleActivite, int plafondFaibleRevenu, int deducDoubleActivitePourFaibleRevenu, int montantParDemiCharge, int demiMontantFraisGarde) {
		ProducteurBaseRabaisImpot producteur = new ProducteurBaseRabaisImpot(annee);
		producteur.setMontantParEpoux(new BigDecimal(montantParEpoux));
		producteur.setMontantDeducDoubleActivite(new BigDecimal(deducDoubleActivite));
		producteur.setPlafondFaibleRevenu(new BigDecimal(plafondFaibleRevenu));
		producteur.setMontantDeducDoubleActivitePourFaibleRevenu(new BigDecimal(deducDoubleActivitePourFaibleRevenu));
		producteur.setMontantParDemiCharge(new BigDecimal(montantParDemiCharge));
		producteur.setDemiMontantFraisGarde(new BigDecimal(demiMontantFraisGarde));
		
		
		producteur.setRegleAge(contruireRegleAge());
		
		
		Fournisseur fournisseurCotSociale = new Fournisseur();
		Map<Integer,BigDecimal> rentesMinimumParAnnee = new HashMap<Integer,BigDecimal>();
		rentesMinimumParAnnee.put(2008, new BigDecimal("1105"));
		
		fournisseurCotSociale.setRenteSimpleMensuelleMinimumParAnnee(rentesMinimumParAnnee);
		if (annee > 2006) producteur.setRegleRenteMaxi(fournisseurCotSociale.getCalculateurExtremaRenteAVS(annee));
		return producteur;
	}
	
	protected FournisseurMontantRabaisImpot creerFournisseurMontant() {
		return creerFournisseurMontant(null,null,null);
	}
	
	
	protected FournisseurMontantRabaisImpot creerFournisseurMontant(final SituationAVS situation, final BigDecimal renteAVSPercu, final BigDecimal revenuPourMontantAdditionnelRenteAVS) {
		FournisseurMontantRabaisImpot fournisseur = new FournisseurMontantRabaisImpot() {

			@Override
			public BigDecimal getMontantRenteAVSPercu() {
				return renteAVSPercu;
			}
			@Override
			public BigDecimal getRevenuPourMontantAdditionnelRenteAVS() {
				return revenuPourMontantAdditionnelRenteAVS;
			}
			@Override
			public SituationAVS getSituationAVS() {
				return situation;
			}
			@Override
			public BigDecimal getRevenuBrutTotaux() {
				return new BigDecimal("100000");
			}
			@Override
			public boolean hasDoubleActivite() {
				return false;
			}
			
		};
		return fournisseur;
	}

	/**
	 * Retourne une situation familiale pour un célibataire sans charge.
	 * @return une situation familiale pour un célibataire sans charge.
	 * @see ch.ge.afc.calcul.impot.cantonal.ge.pp.avant2010.ConstructeurSituationFamilialeGE#creerCelibataireSansCharge()
	 */
	public SituationFamilialeGE creerCelibataireSansCharge() {
		return constructeurSituation.creerCelibataireSansCharge();
	}

	/**
	 * Retourne une situation familiale pour un couple dont un seul des membres a une activité lucrative.
	 * 
	 * @param nbreEnfant Le nombre d'enfant
	 * @param auMoinsUnPetit Si true, spécifie qu'au moins un des enfants est un petit (susceptible d'être gardé)
	 * @param domicilieGE indique si le couple est domicilié dans le canton de Genève
	 * @return une situation familiale pour un couple
	 * @see ch.ge.afc.calcul.impot.cantonal.ge.pp.avant2010.ConstructeurSituationFamilialeGE#creerCoupleSansDoubleActiviteAvecEnfant(int, boolean, boolean)
	 */
	public SituationFamilialeGE creerCoupleSansDoubleActiviteAvecEnfant(
			int nbreEnfant, boolean auMoinsUnPetit, boolean domicilieGE) {
		return constructeurSituation.creerCoupleSansDoubleActiviteAvecEnfant(
				nbreEnfant, auMoinsUnPetit, domicilieGE);
	}

	/**
	 * Retourne une situation familiale pour un couple dont un seul des membres a une activité lucrative et n'ayant pas de charges.
	 * 
	 * @return une situation familiale pour un couple dont un seul des membres a une activité lucrative et n'ayant pas de charges
	 * @see ch.ge.afc.calcul.impot.cantonal.ge.pp.avant2010.ConstructeurSituationFamilialeGE#creerCoupleSansDoubleActiviteSansCharge()
	 */
	public SituationFamilialeGE creerCoupleSansDoubleActiviteSansCharge() {
		return constructeurSituation.creerCoupleSansDoubleActiviteSansCharge();
	}

	/**
	 * Retourne une situation familiale pour un couple dont un des membres est fonctionnaire international.
	 * @param nbreEnfant le nombre total d'enfant
	 * @param auMoinsUnPetit  si true, indique qu'au moins un des enfants est petit i.e. susceptible d'être gardé.
	 * @return une situation familiale pour couple avec un fonctionnaire international.
	 * @see ch.ge.afc.calcul.impot.cantonal.ge.pp.avant2010.ConstructeurSituationFamilialeGE#creerCoupleDontUnFonctionnaireInternational(int, boolean)
	 */
	public SituationFamilialeGE creerCoupleDontUnFonctionnaireInternational(
			int nbreEnfant, boolean auMoinsUnPetit) {
		return constructeurSituation
				.creerCoupleDontUnFonctionnaireInternational(nbreEnfant,
						auMoinsUnPetit);
	}

	public SituationFamilialeGE creerCelibataireAvecCharge(int nbreEnfant,
			boolean auMoinsUnPetit) {
		return constructeurSituation.creerCelibataireAvecCharge(nbreEnfant,
				auMoinsUnPetit);
	}
	
	
}
