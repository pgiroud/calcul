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
package org.impotch.calcul.assurancessociales;

import java.math.BigDecimal;

import org.impotch.calcul.ReglePeriodique;
import org.impotch.util.BigDecimalUtil;
import org.impotch.util.TypeArrondi;

/**
 * Calcule des cotisations à l'assurance vieillesse et survivant, à l'assurance invalidité
 * et aux allocations pour perte de gains pour un salarié.
 * 
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 */
class CalculCotisationAvsAiApgSalarie extends ReglePeriodique implements
		CalculCotisationAvsAiApg, CalculPartSalarieeCotisationAvsAiApg {

	/**************************************************/
	/****************** Attributs *********************/
	/**************************************************/

	private final BigDecimal tauxAVS;
	private final BigDecimal tauxAI;
	private final BigDecimal tauxAPG;
	private final BigDecimal tauxTotal;
	private final BigDecimal demiTauxAVS;
	private final BigDecimal demiTauxAI;
	private final BigDecimal demiTauxAPG;
	private final BigDecimal demiTauxTotal;

    /**************************************************/
    /***************** Constructeurs ******************/
    /**************************************************/
	
	/**
	 * 
	 */
	private CalculCotisationAvsAiApgSalarie(int annee, BigDecimal tauxAVS,
			BigDecimal tauxAI, BigDecimal tauxAPG) {
		super(annee);
		this.tauxAVS = tauxAVS;
		BigDecimal deux = new BigDecimal(2);
		this.demiTauxAVS = tauxAVS.divide(deux);
		this.tauxAI = tauxAI;
		this.demiTauxAI = tauxAI.divide(deux);
		this.tauxAPG = tauxAPG;
		this.demiTauxAPG = tauxAPG.divide(deux);
		this.tauxTotal = tauxAVS.add(tauxAI).add(tauxAPG);
		this.demiTauxTotal = demiTauxAVS.add(demiTauxAI).add(demiTauxAPG);
	}

    /**************************************************/
    /******* Accesseurs / Mutateurs *******************/
    /**************************************************/

	protected BigDecimal getTauxAVS() {
		return tauxAVS;
	}

	protected BigDecimal getTauxAI() {
		return tauxAI;
	}

	protected BigDecimal getTauxAPG() {
		return tauxAPG;
	}

    /**************************************************/
    /******************* Méthodes *********************/
    /**************************************************/
	
	//------ Implémentation de l'interface CalculCotisationAvsAiApg
	
	@Override
	public BigDecimal calculCotisationAi(BigDecimal montantDeterminant) {
		return TypeArrondi.CINQ_CTS.arrondirMontant(tauxAI
				.multiply(montantDeterminant));
	}

	@Override
	public BigDecimal calculCotisationApg(BigDecimal montantDeterminant) {
		return TypeArrondi.CINQ_CTS.arrondirMontant(tauxAPG
				.multiply(montantDeterminant));
	}

	@Override
	public BigDecimal calculCotisationAvs(BigDecimal montantDeterminant) {
		return TypeArrondi.CINQ_CTS.arrondirMontant(tauxAVS
				.multiply(montantDeterminant));
	}

	@Override
	public BigDecimal calculCotisationAvsAiApg(BigDecimal montantDeterminant) {
		return TypeArrondi.CINQ_CTS.arrondirMontant(tauxTotal
				.multiply(montantDeterminant));
	}

	//------ Implémentation de l'interface CalculPartSalarieeCotisationAvsAiApg
	
    /* (non-Javadoc)
	 * @see ch.ge.afc.calcul.assurancessociales.CalculPartSalarieeCotisationAvsAiApg#calculPartSalarieeCotisationAi(java.math.BigDecimal)
	 */
	@Override
	public BigDecimal calculPartSalarieeCotisationAi(
			BigDecimal montantDeterminant) {
		return TypeArrondi.CINQ_CTS.arrondirMontant(demiTauxAI
				.multiply(montantDeterminant));
	}

	/* (non-Javadoc)
	 * @see org.impotch.calcul.assurancessociales.CalculPartSalarieeCotisationAvsAiApg#calculPartSalarieeCotisationApg(java.math.BigDecimal)
	 */
	@Override
	public BigDecimal calculPartSalarieeCotisationApg(
			BigDecimal montantDeterminant) {
		return TypeArrondi.CINQ_CTS.arrondirMontant(demiTauxAPG
				.multiply(montantDeterminant));
	}

	/* (non-Javadoc)
	 * @see ch.ge.afc.calcul.assurancessociales.CalculPartSalarieeCotisationAvsAiApg#calculPartSalarieeCotisationAvs(java.math.BigDecimal)
	 */
	@Override
	public BigDecimal calculPartSalarieeCotisationAvs(
			BigDecimal montantDeterminant) {
		return TypeArrondi.CINQ_CTS.arrondirMontant(demiTauxAVS
				.multiply(montantDeterminant));
	}

	/* (non-Javadoc)
	 * @see org.impotch.calcul.assurancessociales.CalculPartSalarieeCotisationAvsAiApg#calculPartSalarieeCotisationAvsAiApg(java.math.BigDecimal)
	 */
	@Override
	public BigDecimal calculPartSalarieeCotisationAvsAiApg(
			BigDecimal montantDeterminant) {
		return TypeArrondi.CINQ_CTS.arrondirMontant(demiTauxTotal
				.multiply(montantDeterminant));
	}


	/**************************************************/
    /*************** Classes internes *****************/
    /**************************************************/
	
	public static class Constructeur {
		
		private BigDecimal tauxAVS;
		private BigDecimal tauxAI;
		private BigDecimal tauxAPG;

		/**
		 * Spécifie le taux de cotisation total (part employeur + part salariée).
		 * @param taux le taux de cotisation AVS
		 * @return ce construteur afin de chaîner les appels.
		 */
		public Constructeur tauxAvs(String taux) {
			try {
				this.tauxAVS = BigDecimalUtil.parseTaux(taux);
			} catch (NumberFormatException nfe) {
				throw new IllegalArgumentException(nfe);
			}
			return this;
		}
		
		/**
 		 * Spécifie le taux de cotisation total (part employeur + part salariée).
		 * @param taux le taux de cotisation à l'assurance invalidité.
		 * @return ce construteur afin de chaîner les appels.
		 */
		public Constructeur tauxAi(String taux) {
			try {
				this.tauxAI = BigDecimalUtil.parseTaux(taux);
			} catch (NumberFormatException nfe) {
				throw new IllegalArgumentException(nfe);
			}
			return this;
		}

		/**
		 * Spécifie le taux de cotisation total (part employeur + part salariée).
		 * @param taux le taux des allocations pour perte de gains.
		 * @return ce construteur afin de chaîner les appels.
		 */
		public Constructeur tauxApg(String taux) {
			try {
				this.tauxAPG = BigDecimalUtil.parseTaux(taux);
			} catch (NumberFormatException nfe) {
				throw new IllegalArgumentException(nfe);
			}
			return this;
		}

		public CalculCotisationAvsAiApgSalarie construire(int annee) {
			return new CalculCotisationAvsAiApgSalarie(annee,tauxAVS,tauxAI,tauxAPG);
		}
	}
}
