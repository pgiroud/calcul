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
package org.impotch.calcul.impot.cantonal.ge.pp;

import java.math.BigDecimal;

import org.impotch.bareme.BaremeParTranche;
import org.impotch.bareme.ConstructeurBareme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.impotch.calcul.impot.indexation.FournisseurIndicePeriodique;
import org.impotch.util.TypeArrondi;

import static org.impotch.bareme.ConstructeurBareme.unBaremeATauxMarginal;

public class ConstructeurBaremeParTrancheIndexe {

	/**************************************************/
	/****************** Attributs *********************/
	/**************************************************/

	final Logger logger = LoggerFactory.getLogger(ConstructeurBaremeParTrancheIndexe.class);

	private Integer anneeMinimumValidite;
	private Integer anneeMaximumValidite;
	private int anneeReference;
	private FournisseurIndicePeriodique indexateur;

	private final ConstructeurBareme constructeur;

    /**************************************************/
    /**************** Constructeurs *******************/
    /**************************************************/

	private ConstructeurBaremeParTrancheIndexe() {
		super();
		constructeur = unBaremeATauxMarginal();
	}

	/**
	 * On recopie les tranches du barèmes en paramètres
	 */
	private ConstructeurBaremeParTrancheIndexe(BaremeParTranche bareme) {
		super();
		constructeur = unBaremeATauxMarginal(bareme);
	}

	public static ConstructeurBaremeParTrancheIndexe unConstructeurDeBaremeParTrancheIndexee() {
		return new ConstructeurBaremeParTrancheIndexe();
	}

	public static ConstructeurBaremeParTrancheIndexe unConstructeurDeBaremeParTrancheIndexee(BaremeParTranche bareme) {
		return new ConstructeurBaremeParTrancheIndexe(bareme);
	}

    /**************************************************/
    /******************* Méthodes *********************/
    /**************************************************/

	public ConstructeurBaremeParTrancheIndexe valideDepuis(int anneeMinimum) {
		this.anneeMinimumValidite = anneeMinimum;
		return this;
	}

	public ConstructeurBaremeParTrancheIndexe valideEntre(int anneeMinimum, int anneeMaximum) {
		this.anneeMinimumValidite = anneeMinimum;
		this.anneeMaximumValidite = anneeMaximum;
		return this;
	}

	public ConstructeurBaremeParTrancheIndexe valideJusqua(int anneeMaximum) {
		this.anneeMaximumValidite = anneeMaximum;
		return this;
	}



	private BigDecimal calculIndice(int annee)
	{
		return indexateur.getIndice(annee);
	}

	/**
	 * Précise l'année de référence pour prendre en compte le renchérissement.
	 * @param annee l'année de référence.
	 * @return le constructeur afin d'être chaîné.
	 */
	public ConstructeurBaremeParTrancheIndexe anneeReferenceRencherissement(int annee) {
		anneeReference = annee;
		return this;
	}


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
	public ConstructeurBaremeParTrancheIndexe indexateur(FournisseurIndicePeriodique indexateur) {
		this.indexateur = indexateur;
		return this;
	}

	// Délégation au constructeur de barème

	public ConstructeurBaremeParTrancheIndexe typeArrondiTranche(TypeArrondi type) {
		constructeur.typeArrondiSurChaqueTranche(type);
		return this;
	}

	public ConstructeurBaremeParTrancheIndexe typeArrondiGlobal(TypeArrondi type) {
		constructeur.typeArrondiGlobal(type);
		return this;
	}


	public ConstructeurBaremeParTrancheIndexe jusqua(int jusqua) {
		constructeur.jusqua(jusqua);
		return this;
	}

	public ConstructeurBaremeParTrancheIndexe puisJusqua(int jusqua) {
		constructeur.puisJusqua(jusqua);
		return this;
	}

	public ConstructeurBaremeParTrancheIndexe puis() {
		constructeur.puis();
		return this;
	}


	public ConstructeurBaremeParTrancheIndexe taux(String taux) {
		constructeur.taux(taux);
		return this;
	}

	/**
	 * Construit un barème pour une période fiscale donnée.
	 * 
	 * @param annee La période fiscale i.e. une année comprise entre 2001 et l'année courante
	 * @return le barème pour l'année fiscale
	 */
	public BaremeParTranche construire(int annee) {
		if (null != anneeMinimumValidite && annee < anneeMinimumValidite) throw new IllegalArgumentException("L'année du barème ne peut être inférieure à " + anneeMinimumValidite);
		if (null != anneeMaximumValidite && annee > anneeMaximumValidite) throw new IllegalArgumentException("L'année du barème ne peut être supérieure à " + anneeMaximumValidite);
		
		logger.debug("Construction barème indexé " + annee);
		
		BigDecimal indiceReference = calculIndice(anneeReference);
		BigDecimal indiceRencherissement = calculIndice(annee);
		BigDecimal rapportRencherissement = indiceRencherissement.divide(indiceReference, 15, BigDecimal.ROUND_HALF_UP);
		

		return constructeur.construire()
				.homothetie(rapportRencherissement, TypeArrondi.UNITE_LA_PLUS_PROCHE);

	}
	
}
