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
package ch.ge.afc.calcul.bareme;


import java.math.BigDecimal;

import ch.ge.afc.util.TypeArrondi;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class TrancheBareme {

	/**************************************************/
	/****************** Attributs *********************/
	/**************************************************/

	private final BigDecimal montantImposableMax;
	private final BigDecimal tauxOuMontant;

	/**************************************************/
	/**************** Constructeurs *******************/
	/**************************************************/

	/**
	 * Construction d'une tranche de barème.
	 * Les seules informations utiles sont le montant maximum d'une tranche 
	 * et le taux de la tranche.
	 * @param montantImposableMax Le montant imposable maximum de la tranche (peut être l'infini) 
	 * @param tauxOuMontant Le taux ou le montant de la tranche
	 */
	protected TrancheBareme(BigDecimal montantImposableMax, BigDecimal tauxOuMontant) {
		super();
		this.montantImposableMax = montantImposableMax;
		this.tauxOuMontant = tauxOuMontant;
	}

    /**************************************************/
    /************* Accesseurs / Mutateurs *************/
    /**************************************************/

	protected BigDecimal getTauxOuMontant() {
		return tauxOuMontant;
	}
	
	/**
	 * Retourne le montant imposable maximum de la tranche.
	 * @return Le montant imposable maximum de la tranche.
	 */
	public BigDecimal getMontantMaxTranche() {
		return montantImposableMax;
	}

    /**************************************************/
    /******************* Méthodes *********************/
    /**************************************************/

	/**
	 * Une tranche peut être translatée. Translater une tranche consiste à
	 * multiplier le montant imposable maximum par le rapport de translation
	 * et à l'arrondir au francs le plus proche.
	 * @param pRapport Le rapport de translation.
	 * @return Une nouvelle tranche translatée.
	 */
	public TrancheBareme homothetie(BigDecimal pRapport, TypeArrondi typeArrondi) {
		BigDecimal inter = this.getMontantMaxTranche().multiply(pRapport);
		BigDecimal montantImposableMax = typeArrondi.arrondirMontant(inter);
		return new TrancheBareme(montantImposableMax,this.getTauxOuMontant());
	}

	public TrancheBareme homothetieValeur(BigDecimal pRapport, TypeArrondi typeArrondi) {
		BigDecimal inter = this.getTauxOuMontant().multiply(pRapport);
		BigDecimal tauxOuMontant = typeArrondi.arrondirMontant(inter);
		return new TrancheBareme(this.getMontantMaxTranche(),tauxOuMontant);
	}
	
	public BigDecimal calcul(BigDecimal pMontantImposableMaxTranchePrecedente, BigDecimal pMontantImposable) {
		return this.tauxOuMontant;
	}
	
	@Override
	public String toString() {
		return montantImposableMax + " " + tauxOuMontant;
	}

    /**************************************************/
    /************** Classes internes ******************/
    /**************************************************/
	
	public static class DerniereTrancheBareme extends TrancheBareme {

		public DerniereTrancheBareme(BigDecimal tauxOuMontant){
			super(null,tauxOuMontant);
		}
		
		public TrancheBareme homothetie(BigDecimal pRapport, TypeArrondi typeArrondi) {
			return this;
		}
		
		@Override
		public String toString() {
			return "+\u221E " + getTauxOuMontant();
		}
		
		
	}
	

}
