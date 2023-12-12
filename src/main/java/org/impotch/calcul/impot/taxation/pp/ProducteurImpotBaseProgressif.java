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
package org.impotch.calcul.impot.taxation.pp;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.impotch.util.BigDecimalUtil.isStrictementPositif;

import org.impotch.bareme.Bareme;
import org.impotch.calcul.impot.taxation.pp.annualisation.StrategieAnnualisationComptable;
import org.impotch.calcul.impot.taxation.pp.famille.ImpositionFamilleSansAvantage;
import org.impotch.util.TypeArrondi;


/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class ProducteurImpotBaseProgressif implements ProducteurImpotBase {

	private static final BigDecimal UN_CINQUIEME = new BigDecimal("0.2");

	public static ProducteurImpotBaseProgressif.Constructeur unProducteurImpotBaseProgressif(Bareme bareme) {
		return new Constructeur(bareme);
	}

	public static ProducteurImpotBaseProgressif.Constructeur unProducteurImpotBaseProgressif(StrategieProductionImpotFamille strategie) {
		return new Constructeur(strategie);
	}

	/**************************************************/
	/****************** Attributs *********************/
	/**************************************************/

	final Logger logger = LoggerFactory.getLogger(ProducteurImpotBaseProgressif.class);

	private TypeArrondi typeArrondiImposable;
	private TypeArrondi typeArrondiDeterminant;
	private TypeArrondi typeArrondiImpot;
	
	private StrategieProductionImpotFamille impositionFamille;
	private StrategieAnnualisation stratAnnualisation;

    private BigDecimal partBase = null;
	
    /**************************************************/
    /**************** Constructeurs *******************/
    /**************************************************/
	
	private ProducteurImpotBaseProgressif() {
	}
	
    /**************************************************/
    /******* Accesseurs / Mutateurs *******************/
    /**************************************************/

	/**
	 * Précise le type d'arrondi à effectuer sur les assiettes imposables.
	 * En règle générale, on utilise des arrondis à la centaine inférieure pour le
	 * revenu et le bénéfice et au mille francs inférieur pour la fortune ou le capital.
	 */
	private void setTypeArrondiImposable(TypeArrondi type) {
		typeArrondiImposable = type;
	}
	
	/**
	 * Retourne le type d'arrondi à effectuer sur les assiettes imposables.
	 * @return le type d'arrondi.
	 */
	protected TypeArrondi getTypeArrondiImposable() {
		return typeArrondiImposable;
	}
	
	/**
	 * Précise le type d'arrondi à effectuer sur les assiettes déterminantes.
	 * En règle générale, on utilise des arrondis à la centaine inférieure pour le
	 * revenu et le bénéfice et au mille francs inférieur pour la fortune ou le capital.
	 */
	private void setTypeArrondiDeterminant(TypeArrondi type) {
		typeArrondiDeterminant = type;
	}
	
	/**
	 * Retourne le type d'arrondi à effectuer sur les assiettes déterminantes.
	 * @return le type d'arrondi.
	 */
	protected TypeArrondi getTypeArrondiDeterminant() {
		return typeArrondiDeterminant;
	}
	
	/**
	 * Spécifie le type d'arrondi à appliquer à l'impôt calculé. Par défaut, l'arrondi se fait aux 5 centimes
	 * les plus proches.
	 * 
	 * @param type le type d'arrondi sur le montant d'impôt.
	 */
	private void setTypeArrondiImpot(TypeArrondi type) {
		typeArrondiImpot = type;
	}
	
	/**
	 * Retourne le type d'arrondi sur le montant d'impôt.
	 * @return le type d'arrondi sur le montant d'impôt.
	 */
	protected TypeArrondi getTypeArrondiImpot() {
		return typeArrondiImpot;
	}
	
	/**
	 * Précise la stratégie d'imposition à appliquer aux familles. En effet plusieurs systèmes sont en vigueur :
	 * <ul>
	 * 	<li>Splitting : cantonal de Fribourg, de Neuchâtel, ...</li>
	 * 	<li>double barème : IFD, cantonal de Genève, ...</li>
	 * 	<li>quotient familial : cantonal de Vaud, France, ...</li>
	 * </ul>
	 * @param strategie la stratégie à appliquer.
	 */
	private void setStrategieProductionImpotFamille(StrategieProductionImpotFamille strategie) {
		impositionFamille = strategie;
	}
	
	/**
	 * Retourne la stratégie d'imposition à appliquer aux familles.
	 * @return la stratégie d'imposition à appliquer aux familles.
	 */
	protected StrategieProductionImpotFamille getStrategieImpositionFamille() {
		return impositionFamille;
	}
	
	/**
	 * Précise la stratégie d'annualisation lors de la production d'impôt.
	 * Par défaut, la stratégie est une stratégie avec calendrier comptable à 360 jours. 
	 * @param strategie la stratégie
	 */
	private void setStrategieAnnualisation(StrategieAnnualisation strategie) {
		stratAnnualisation = strategie; 
	}
	
	/**
	 * Retourne la stratégie d'annualisation pour produire l'impôt.
	 * @return la stratégie d'annualisation pour produire l'impôt.
	 */
	protected StrategieAnnualisation getStrategieAnnualisation() {
		return stratAnnualisation;
	}

    public void setPartBase(BigDecimal partBase) {
        this.partBase = partBase;
    }

	/**************************************************/
    /******************* Méthodes *********************/
    /**************************************************/

	public BigDecimal produireImpotBase(SituationFamiliale situation, FournisseurAssiettePeriodique fournisseur) {
		BigDecimal impot = null;
		if (null == fournisseur.getMontantDeterminant()) {
			BigDecimal imposable = getTypeArrondiImposable().arrondirMontant(fournisseur.getMontantImposable());
			BigDecimal impotAnnuel = getStrategieImpositionFamille().produireImpotAnnuelAuTauxMaximal(situation,imposable);
			impot = getStrategieAnnualisation().annualiseImpot(impotAnnuel, fournisseur.getNombreJourPourAnnualisation());
		} else {
			BigDecimal determinant = getTypeArrondiDeterminant().arrondirMontant(fournisseur.getMontantDeterminant());
			BigDecimal imposable = getTypeArrondiImposable().arrondirMontant(fournisseur.getMontantImposable());
			if (!isStrictementPositif(determinant) || !isStrictementPositif(imposable)) return BigDecimal.ZERO;

			BigDecimal impotAnnuel = getStrategieImpositionFamille().produireImpotAnnuel(situation,determinant,imposable);

			impot = getStrategieAnnualisation().annualiseImpot(impotAnnuel, fournisseur.getNombreJourPourAnnualisation());
		}
		impot = getTypeArrondiImpot().arrondirMontant(impot);
		if (null != partBase) {
			impot = getTypeArrondiImpot().arrondirMontant(partBase.multiply(impot));
		}
		return impot;
	}



	// Construction
	public static class Constructeur {


		private final StrategieProductionImpotFamille strategieImpositionFamiliale;
		private TypeArrondi typeArrondiImposable	= TypeArrondi.UNITE_LA_PLUS_PROCHE;
		private TypeArrondi typeArrondiDeterminant	= TypeArrondi.UNITE_LA_PLUS_PROCHE;
		private TypeArrondi typeArrondiImpot		= TypeArrondi.CINQ_CENTIEMES_LES_PLUS_PROCHES;
		private StrategieAnnualisation stratAnnualisation = new StrategieAnnualisationComptable();


		private Constructeur(Bareme bareme) {
			this(new ImpositionFamilleSansAvantage(bareme));
		}

		private Constructeur(StrategieProductionImpotFamille strategie) {
			this.strategieImpositionFamiliale = strategie;
		}



		public Constructeur arrondiAssiettes(TypeArrondi typeArrondi) {
			return arrondiImposable(typeArrondi).arrondiDeterminant(typeArrondi);
		}

		public Constructeur arrondiImposable(TypeArrondi typeArrondi) {
			this.typeArrondiImposable = typeArrondi;
			return this;
		}

		public Constructeur arrondiDeterminant(TypeArrondi typeArrondi) {
			this.typeArrondiDeterminant = typeArrondi;
			return this;
		}

		public Constructeur arrondiImpot(TypeArrondi typeArrondi) {
			this.typeArrondiImpot= typeArrondi;
			return this;
		}

		public Constructeur annualisationImpot(StrategieAnnualisation strategie) {
			this.stratAnnualisation = strategie;
			return this;
		}

		public ProducteurImpotBase construireUnCinquieme() {
			ProducteurImpotBaseProgressif prod = construire();
			prod.setPartBase(UN_CINQUIEME);
			return prod;
		}

		public ProducteurImpotBaseProgressif construire() {
			ProducteurImpotBaseProgressif prod = new ProducteurImpotBaseProgressif();
			prod.setStrategieProductionImpotFamille(strategieImpositionFamiliale);
			prod.setTypeArrondiImposable(typeArrondiImposable);
			prod.setTypeArrondiDeterminant(typeArrondiDeterminant);
			prod.setTypeArrondiImpot(typeArrondiImpot);
			prod.setStrategieAnnualisation(stratAnnualisation);
			return prod;
		}
	}
}
