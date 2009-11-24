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
package ch.ge.afc.calcul.impot.cantonal.ge.pp.avant2010;

import java.math.BigDecimal;
import java.util.Calendar;

import ch.ge.afc.calcul.bareme.Bareme;
import ch.ge.afc.calcul.impot.cantonal.ge.pp.indexateur.FournisseurIndicePeriodiqueGE;
import ch.ge.afc.util.BigDecimalUtil;
import ch.ge.afc.util.TypeArrondi;

/**
 * Cette classe permet de calculer le montant d'impôt sur le revenu des personnes
 * physiques seule suivant le barème défini dans l'annexe A de la 
 * <a href="doc-files/rsg_D3_16_002.htm">LIPP</a>.
 * 
 * Pour construire un tel barème, on utilisera la classe interne Constructeur
 * <pre>
 * BaremeRevenuSeul.Constructeur constructeur = new BaremeRevenuSeul.Constructeur();
 * constructeur.poids("64.7 %");
 * // Paramètre fonction célibataire
 * constructeur.tauxMarginalImpot("0.22 %","19 %").progressivite(6500.0, 350.618);
 * // Paramètre de rencherissement
 * constructeur.anneeReferenceRencherissement(2000).adaptationRencherissementReference(54824290.0);
 * constructeur.indexateur(getIndexateur());
 * </pre>
 * Ce barème est indexé toute les années pour tenir compte de la progression à froid. Afin d'injecter les différents
 * indices, on utilisera un indexateur.
 * @see ch.ge.afc.calcul.impot.cantonal.ge.pp.indexateur.FournisseurIndicePeriodiqueGE 
 */
strictfp class BaremeRevenuTauxMarginalContinu implements Bareme {

    /**************************************************/
    /****************** Attributs *********************/
    /**************************************************/

	private double tmin;
	private double tmax;
	private double a1;
	private double a2;
	private double poids;
	private double ct;

    /**************************************************/
    /******* Accesseurs / Mutateurs *******************/
    /**************************************************/
	
	/**
	 * Retourne l'adaptation au renchérissement (nommé <b>Ct</b> dans la documentation).
	 * @return l'adaptation au renchérissement.
	 */
	double getCt() {
		return ct;
	}
	
    /**************************************************/
    /******************* Méthodes *********************/
    /**************************************************/

	private double taux(double pMontantImposable, double pPuissance)
	{
		return tmin + (tmax-tmin)*(1.0 - (Math.pow((1.0 + pMontantImposable/ct),-pPuissance)));
	}
	
	/**
	 * Retourne le taux marginal en fonction d'un revenu déterminant.
	 * @param pMontantDeterminant Le revenu déterminant.
	 * @return le taux marginal
	 */
	double tauxMarginal(double pMontantDeterminant)
	{
		return  poids * taux(pMontantDeterminant,a1) +  (1 - poids) * taux(pMontantDeterminant,a2);
	}
	
	
	private double tauxIntegrerExact(double pMontantDeterminant, double pPuissance)
	{
		double ctDivUnMoinsA = ct / (1.0 - pPuissance);
		double tmaxMoinsTmin = tmax - tmin;
		return tmin * pMontantDeterminant 
			+ tmaxMoinsTmin * (pMontantDeterminant - ctDivUnMoinsA *Math.pow((1.0 + pMontantDeterminant/ct),1.0 - pPuissance))
			+ tmaxMoinsTmin * ctDivUnMoinsA;
	}
	
	/**
	 * Retourne le montant d'impôt en fournissant le revenu déterminant.
	 * @param pMontantDeterminant le montant déterminant. 
	 * @return le montant d'impôt.
	 */
	double calculImpot(double pMontantDeterminant) {
		return (poids * tauxIntegrerExact(pMontantDeterminant,a1) + (1-poids) * tauxIntegrerExact(pMontantDeterminant,a2));
	}
	
	/**
	 * Retourne le montant d'impôt en fournissant le revenu déterminant.
	 * @param revenuDeterminant le montant déterminant. 
	 * @return le montant d'impôt.
	 */
	@Override
	public BigDecimal calcul(BigDecimal revenuDeterminant) {
		double revDet = revenuDeterminant.doubleValue();
		double impot = calculImpot(revDet);
		return TypeArrondi.CINQ_CTS.arrondirMontant(BigDecimal.valueOf(impot));
	}
	
	/**
	 * Cette classe permet de construire un barème pour l'impôt sur le revenu
	 * d'une personne seule.
	 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
	 */
	public static class Constructeur {
		
		private double poids;
		private double tmin;
		private double tmax;
		private double a1;
		private double a2;

		private double c0;
		private int anneeReference;
		private FournisseurIndicePeriodiqueGE indexateur;
		
		/**
		 * Précise le poids le plus élevé des 2 termes entrant dans la composition du taux marginal.
		 * Pour l'année 2001, ce taux est de 64.7 % 
		 * @param taux le taux
		 * @return le constructeur afin d'être chaîné.
		 */
		public Constructeur poids(String taux) {
			this.poids = BigDecimalUtil.parseTaux(taux).doubleValue();
			return this;
		}
		
		/**
		 * Précise les taux minimum et maximum tels que décrit dans la formule.
		 * @param tauxMin le taux minimum (en 2001, 0.22 %)
		 * @param tauxMax le taux maximum (en 2001, 19 %)
		 * @return le constructeur afin d'être chaîné.
		 */
		public Constructeur tauxMarginalImpot(String tauxMin, String tauxMax) {
			this.tmin = BigDecimalUtil.parseTaux(tauxMin).doubleValue();
			this.tmax = BigDecimalUtil.parseTaux(tauxMax).doubleValue();
			return this;
		}
		/**
		 * Précise les constantes de progressivité nommées <b>a1</b> et <b>a2</b> dans la documentation.
		 * @param a1 la constante de progressivité nommée <b>a1</b> dans la documentation (en 2001, égale à 6500)
		 * @param a2 la constante de progressivité nommée <b>a2</b> dans la documentation (en 2001, égale à 350.618)
		 * @return le constructeur afin d'être chaîné.
		 */
		public Constructeur progressivite(double a1, double a2) {
			this.a1 = a1;
			this.a2 = a2;
			return this;
		}

		// -- Rencherissement
		
		/**
		 * Précise l'année de référence pour prendre en compte le renchérissement.
		 * @param annee l'année de référence. 
		 * @return le constructeur afin d'être chaîné.
		 */
		public Constructeur anneeReferenceRencherissement(int annee) {
			anneeReference = annee;
			return this;
		}
		
		/**
		 * Précise l'adaptation au renchérissement pour l'année de référence (nommée <b>C0</b> dans la documentation).
		 * @param parametre l'adaptation au renchérissement pour l'année de référence (en 2001, égale à 54824290)
		 * @return le constructeur afin d'être chaîné.
		 */
		public Constructeur adaptationRencherissementReference(double parametre) {
			this.c0 = parametre;
			return this;
		}
		
		/**
		 * Le barème étant indexés automatiquement tous les ans, on fournit un
		 * indexateur.
		 * <p>
		 * <strong>Remarque</strong> : l'indexation se fait sur 
		 * <a href="http://www.geneve.ch/statistique/statistiques/domaines/05/05_02/apercu.asp">l'indice de prix à la consommation du canton de Genève</a>.
		 * Il n'est cependant pas possible d'utiliser les indices à une décimale pour retrouver l'indexation.
		 * @param indexateur un indexateur.
		 * @return le constructeur afin d'être chaîné.
		 * @see FournisseurIndicePeriodiqueGEPrixConsommation
		 */
		public Constructeur indexateur(FournisseurIndicePeriodiqueGE indexateur) {
			this.indexateur = indexateur;
			return this;
		}
		
		private double calculIndice(int annee)
		{
			BigDecimal moyenne = indexateur.getIndice(annee);
			return moyenne.doubleValue();
		}

		/**
		 * Construit un barème pour une période fiscale donnée.
		 * 
		 * @param annee La période fiscale i.e. une année comprise entre 2001 et l'année courante
		 * @return le barème pour l'année fiscale
		 */
		public BaremeRevenuTauxMarginalContinu construire(int annee) {
			if (annee < 2001) throw new IllegalArgumentException("L'année du barème ne peut être inférieur à 2001");
			int anneeCourante = Calendar.getInstance().get(Calendar.YEAR);
			if (annee > anneeCourante) throw new IllegalArgumentException("L'année du barème ne peut pas être plus grande que l'année courante " + anneeCourante);
			BaremeRevenuTauxMarginalContinu bareme = new BaremeRevenuTauxMarginalContinu();
			bareme.poids = poids;
			bareme.a1 = this.a1;
			bareme.a2 = this.a2;
			bareme.tmin = this.tmin;
			bareme.tmax = this.tmax;

			double indiceReference = calculIndice(anneeReference);
			double indiceRencherissement = calculIndice(annee);
			bareme.ct = (c0 * indiceRencherissement) / indiceReference;
			
			return bareme;
		}
		
	}
}
