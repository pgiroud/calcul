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
import java.util.stream.IntStream;

import org.impotch.calcul.assurancessociales.SituationAVS;
import org.impotch.calcul.assurancessociales.StatutAVS;
import org.impotch.calcul.assurancessociales.ge.param.FournisseurParametrageAnnuelAssurancesSocialesGenevoises;
import org.impotch.calcul.impot.Souverainete;
import org.impotch.calcul.impot.cantonal.ge.pp.avant2010.ConstructeurSituationFamilialeGE;
import org.impotch.calcul.impot.cantonal.ge.pp.avant2010.SituationFamilialeGE;
import org.impotch.calcul.impot.taxation.pp.RegleAgeEnfant;
import org.impotch.calcul.assurancessociales.Fournisseur;

import static org.impotch.calcul.impot.cantonal.ge.pp.avant2010.ConstructeurSituationFamilialeGE.unePersonneSeule;
import static org.impotch.calcul.impot.cantonal.ge.pp.avant2010.ConstructeurSituationFamilialeGE.unCouple;
/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
class AbstractTestProducteurBaseRabaisImpot {

	private ProducteurBaseRabaisImpot producteur;

	protected void initProducteurBaseRabaisImpot(int annee, int montantParEpoux, int deducDoubleActivite,
												 int plafondFaibleRevenu, int deducDoubleActivitePourFaibleRevenu,
												 int montantParDemiCharge, int demiMontantFraisGarde) {
		this.producteur = construireProducteur(annee,montantParEpoux,deducDoubleActivite,plafondFaibleRevenu
				,deducDoubleActivitePourFaibleRevenu,montantParDemiCharge,demiMontantFraisGarde);
	}

	protected RegleAgeEnfant contruireRegleAge() {
		RegleAgeEnfant regleAge = new RegleAgeEnfant();
		regleAge.setAgeLimiteJeuneEnfant(12);
		regleAge.setAgeMajorite(18);
		regleAge.setAgeLimiteJeuneAdulte(25);
		return regleAge;
	}
	
	private ProducteurBaseRabaisImpot construireProducteur(int annee, int montantParEpoux, int deducDoubleActivite, int plafondFaibleRevenu, int deducDoubleActivitePourFaibleRevenu, int montantParDemiCharge, int demiMontantFraisGarde) {
		ProducteurBaseRabaisImpot producteur = new ProducteurBaseRabaisImpot(annee);
		producteur.setMontantParEpoux(new BigDecimal(montantParEpoux));
		producteur.setMontantDeducDoubleActivite(new BigDecimal(deducDoubleActivite));
		producteur.setPlafondFaibleRevenu(new BigDecimal(plafondFaibleRevenu));
		producteur.setMontantDeducDoubleActivitePourFaibleRevenu(new BigDecimal(deducDoubleActivitePourFaibleRevenu));
		producteur.setMontantParDemiCharge(new BigDecimal(montantParDemiCharge));
		producteur.setDemiMontantFraisGarde(new BigDecimal(demiMontantFraisGarde));
		
		
		producteur.setRegleAge(contruireRegleAge());


		Fournisseur fournisseurCotSociale = new Fournisseur(FournisseurParametrageAnnuelAssurancesSocialesGenevoises.enMemoire());
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


	protected BigDecimal calculRabais(SituationFamilialeGE situation) {
		FournisseurMontantRabaisImpot fournisseur = creerFournisseurMontant();
		return producteur.produireMontantDeterminantRabais(situation, fournisseur);
	}

	protected BigDecimal montantAdditionnelRenteAVSAI(StatutAVS statut, boolean isComplementaireEpouse, int nombreOrphelin
			, final int renteAVSPercu, final int revenuPourMontantAdditionnelRenteAVS) {
		SituationAVS situation = new SituationAVS(statut,isComplementaireEpouse,nombreOrphelin);
		FournisseurMontantRabaisImpot fournisseur = creerFournisseurMontant(situation
				,BigDecimal.valueOf(renteAVSPercu),BigDecimal.valueOf(revenuPourMontantAdditionnelRenteAVS));
		return producteur.produireMontantAdditionnelRenteAVSAI(fournisseur);
	}

	/**
	 * Retourne une situation familiale pour un célibataire sans charge.
	 * @return une situation familiale pour un célibataire sans charge.
	 */
	public SituationFamilialeGE creerCelibataireSansCharge() {
		return unePersonneSeule().fournir();
	}

	/**
	 * Retourne une situation familiale pour un couple dont un seul des membres a une activité lucrative.
	 * 
	 * @param nbreEnfant Le nombre d'enfant
	 * @param auMoinsUnPetit Si true, spécifie qu'au moins un des enfants est un petit (susceptible d'être gardé)
	 * @param domicilieGE indique si le couple est domicilié dans le cantonal de Genève
	 * @return une situation familiale pour un couple
	 */
	public SituationFamilialeGE creerCoupleSansDoubleActiviteAvecEnfant(
			int nbreEnfant, boolean auMoinsUnPetit, boolean domicilieGE, int periodeFiscale) {
		ConstructeurSituationFamilialeGE constructeur = unCouple();
		if (domicilieGE) constructeur.domicilieGE();
		if (auMoinsUnPetit) {
			constructeur.enfant().age(6).aFin(periodeFiscale);
			IntStream.rangeClosed(1,nbreEnfant-1).forEach(
					n -> constructeur.enfant().age(14).aFin(periodeFiscale)
			);
		} else {
			IntStream.rangeClosed(1,nbreEnfant).forEach(
					n -> constructeur.enfant().age(14).aFin(periodeFiscale)
			);
		}
		return constructeur.fournir();
	}

	/**
	 * Retourne une situation familiale pour un couple dont un seul des membres a une activité lucrative et n'ayant pas de charges.
	 * 
	 * @return une situation familiale pour un couple dont un seul des membres a une activité lucrative et n'ayant pas de charges
	 */
	public SituationFamilialeGE creerCoupleSansDoubleActiviteSansCharge() {
		return unCouple().fournir();
	}

	/**
	 * Retourne une situation familiale pour un couple dont un des membres est fonctionnaire international.
	 * @param nbreEnfant le nombre total d'enfant
	 * @param auMoinsUnPetit  si true, indique qu'au moins un des enfants est petit i.e. susceptible d'être gardé.
	 * @return une situation familiale pour couple avec un fonctionnaire international.
	 */
	public SituationFamilialeGE creerCoupleDontUnFonctionnaireInternational(
			int nbreEnfant, boolean auMoinsUnPetit, int periodeFiscale) {
		ConstructeurSituationFamilialeGE constructeur = unCouple().conjointFonctionnaireInternational();
		if (auMoinsUnPetit) {
			constructeur.enfant().demiPart(Souverainete.CH_CANTONALE_GE).age(6).aFin(periodeFiscale);
			IntStream.rangeClosed(1,nbreEnfant-1).forEach(
					n -> constructeur.enfant().demiPart(Souverainete.CH_CANTONALE_GE).age(14).aFin(periodeFiscale)
			);
		} else {
			IntStream.rangeClosed(1,nbreEnfant).forEach(
					n -> constructeur.enfant().demiPart(Souverainete.CH_CANTONALE_GE).age(14).aFin(periodeFiscale)
			);
		}
		return constructeur.fournir();
	}

	public SituationFamilialeGE creerCelibataireAvecCharge(int nbreEnfant,
			boolean auMoinsUnPetit, int periodeFiscale) {
		ConstructeurSituationFamilialeGE constructeur = unePersonneSeule()
				.domicilieGE();
		if (auMoinsUnPetit) {
			constructeur.enfant().age(6).aFin(periodeFiscale);
			IntStream.rangeClosed(1,nbreEnfant-1).forEach(
					n -> constructeur.enfant().age(14).aFin(periodeFiscale)
			);
		} else {
			IntStream.rangeClosed(0,nbreEnfant-1).forEach(
					n -> constructeur.enfant().age(14).aFin(periodeFiscale)
			);
		}
		return constructeur.fournir();
	}
	
	
}
