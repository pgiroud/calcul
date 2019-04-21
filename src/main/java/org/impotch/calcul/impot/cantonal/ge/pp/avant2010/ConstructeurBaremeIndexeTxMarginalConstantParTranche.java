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
package org.impotch.calcul.impot.cantonal.ge.pp.avant2010;

import java.math.BigDecimal;

import org.impotch.bareme.ConstructeurBaremeTauxMarginal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.impotch.bareme.BaremeTauxMarginalConstantParTranche;
import org.impotch.calcul.impot.indexation.FournisseurIndicePeriodique;
import org.impotch.util.TypeArrondi;

public class ConstructeurBaremeIndexeTxMarginalConstantParTranche {

	/**************************************************/
	/****************** Attributs *********************/
	/**************************************************/

	final Logger logger = LoggerFactory.getLogger(ConstructeurBaremeIndexeTxMarginalConstantParTranche.class);

	private Integer anneeMinimumValidite;
	private Integer anneeMaximumValidite;
	private int anneeReference;
	private FournisseurIndicePeriodique indexateur;
	private ConstructeurBaremeTauxMarginal constructeur;
	private TypeArrondi typeArrondiTranche;
	
    /**************************************************/
    /**************** Constructeurs *******************/
    /**************************************************/

	public ConstructeurBaremeIndexeTxMarginalConstantParTranche() {
		super();
		constructeur = new ConstructeurBaremeTauxMarginal();
	}

	/**
	 * On recopie les tranches du barèmes en paramètres
	 */
	public ConstructeurBaremeIndexeTxMarginalConstantParTranche(BaremeTauxMarginalConstantParTranche bareme) {		
		super();
		constructeur = new ConstructeurBaremeTauxMarginal(bareme);
	}
	
    /**************************************************/
    /******************* Méthodes *********************/
    /**************************************************/

	/**
	 * Le barème étant indexés automatiquement tous les ans, on fournit un
	 * indexateur.
	 * <p>
	 * <strong>Remarque</strong> : l'indexation se fait sur 
	 * <a href="http://www.geneve.ch/statistique/statistiques/domaines/05/05_02/apercu.asp">l'indice de prix à la consommation du cantonal de Genève</a>.
	 * Il n'est cependant pas possible d'utiliser les indices à une décimale pour retrouver l'indexation.
	 * @param indexateur un indexateur.
	 * @return le constructeur afin d'être chaîné.
	 */
	public ConstructeurBaremeIndexeTxMarginalConstantParTranche indexateur(FournisseurIndicePeriodique indexateur) {
		this.indexateur = indexateur;
		return this;
	}
	
	public ConstructeurBaremeIndexeTxMarginalConstantParTranche typeArrondiTranche(TypeArrondi type) {
		this.typeArrondiTranche = type;
		return this;
	}
	
	private BigDecimal calculIndice(int annee)
	{
		return indexateur.getIndice(annee);
	}



	public ConstructeurBaremeIndexeTxMarginalConstantParTranche valideDepuis(int anneeMinimum) {
		this.anneeMinimumValidite = anneeMinimum;
		return this;
	}
	
	public ConstructeurBaremeIndexeTxMarginalConstantParTranche valideEntre(int anneeMinimum, int anneeMaximum) {
		this.anneeMinimumValidite = anneeMinimum;
		this.anneeMaximumValidite = anneeMaximum;
		return this;
	}

	public ConstructeurBaremeIndexeTxMarginalConstantParTranche valideJusqua(int anneeMaximum) {
		this.anneeMaximumValidite = anneeMaximum;
		return this;
	}

	/**
	 * Précise l'année de référence pour prendre en compte le renchérissement.
	 * @param annee l'année de référence. 
	 * @return le constructeur afin d'être chaîné.
	 */
	public ConstructeurBaremeIndexeTxMarginalConstantParTranche anneeReferenceRencherissement(int annee) {
		anneeReference = annee;
		return this;
	}

	public ConstructeurBaremeIndexeTxMarginalConstantParTranche premiereTranche(int jusqua, String taux) {
		constructeur.premiereTranche(jusqua, taux);
		return this;
	}


	public ConstructeurBaremeIndexeTxMarginalConstantParTranche tranche(int de, int a, String taux) {
		constructeur.tranche(de,a, taux);
		return this;
	}
	
	public ConstructeurBaremeIndexeTxMarginalConstantParTranche derniereTranche(int de, String taux) {
		constructeur.derniereTranche(de, taux);
		return this;
	}
	
	/**
	 * Construit un barème pour une période fiscale donnée.
	 * 
	 * @param annee La période fiscale i.e. une année comprise entre 2001 et l'année courante
	 * @return le barème pour l'année fiscale
	 */
	public BaremeTauxMarginalConstantParTranche construire(int annee) {
		if (null != anneeMinimumValidite && annee < anneeMinimumValidite) throw new IllegalArgumentException("L'année du barème ne peut être inférieure à " + anneeMinimumValidite);
		if (null != anneeMaximumValidite && annee > anneeMaximumValidite) throw new IllegalArgumentException("L'année du barème ne peut être supérieure à " + anneeMaximumValidite);
		
		logger.debug("Construction barème indexé " + annee);
		
		BigDecimal indiceReference = calculIndice(anneeReference);
		BigDecimal indiceRencherissement = calculIndice(annee);
		BigDecimal rapportRencherissement = indiceRencherissement.divide(indiceReference, 15, BigDecimal.ROUND_HALF_UP);
		
		constructeur.typeArrondiSurChaqueTranche(this.typeArrondiTranche);
		BaremeTauxMarginalConstantParTranche bareme = (BaremeTauxMarginalConstantParTranche)constructeur.construire();
		return (BaremeTauxMarginalConstantParTranche)bareme.homothetie(rapportRencherissement, TypeArrondi.FRANC);
	}
	
}
