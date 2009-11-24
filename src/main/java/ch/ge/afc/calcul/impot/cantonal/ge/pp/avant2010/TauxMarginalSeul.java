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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.ge.afc.calcul.ReglePeriodique;
import ch.ge.afc.calcul.impot.cantonal.ge.pp.indexateur.FournisseurIndicePeriodiqueGE;
import ch.ge.afc.calcul.math.Fonction;
import ch.ge.afc.calcul.math.integration.Primitivable;

/**
 * Cette classe fournit le taux marginal de l'impôt sur le revenu des personnes
 * physiques seule suivant le barème défini dans l'annexe A de la 
 * <a href="doc-files/rsg_D3_16_002.htm">LIPP</a>.
 *	
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class TauxMarginalSeul extends ReglePeriodique implements Primitivable {

    /**************************************************/
    /****************** Attributs *********************/
    /**************************************************/
	
	final Logger logger = LoggerFactory.getLogger(TauxMarginalSeul.class);
	private FournisseurIndicePeriodiqueGE indexateur;

	private double ct;

	// Ces valeurs n'ont pas varié entre 2001 et 2009 !!	
	private double tmin 	= 0.0022;
	private double tmax 	= 0.19;
	private double a1 		= 6500.0;
	private double a2 		= 350.618;
	private double poids 	= 0.647;
	private double c0 		= 54824290.0;
	private int anneeReference = 2000;

    /**************************************************/
    /****************** Constructeurs  ****************/
    /**************************************************/
	
	public TauxMarginalSeul(int annee) {
		super(annee);
	}

	/**************************************************/
    /******* Accesseurs / Mutateurs *******************/
    /**************************************************/
	
	public void setIndexateur(FournisseurIndicePeriodiqueGE indexateur) {
		this.indexateur = indexateur;
		double indiceReference = calculIndice(anneeReference);
		double indiceRencherissement = calculIndice(this.getAnnee());
		ct = (c0 * indiceRencherissement) / indiceReference;
	}
	
	/**
	 * Retourne le taux minimum (nommé <b>tmin</b> dans la documentation). 
	 * @return le taux minimum.
	 */
	double getTauxMin() {
		return tmin;
	}
	
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

	private double calculIndice(int annee) {
		BigDecimal moyenne = indexateur.getIndice(annee);
		return moyenne.doubleValue();
	}

	private double taux(double pMontantImposable, double pPuissance) {
		return tmin + (tmax-tmin)*(1.0 - (Math.pow((1.0 + pMontantImposable/ct),-pPuissance)));
	}
	
	//----------- Implémentation de l'interface Fonction -----------

	/* (non-Javadoc)
	 * @see ch.ge.afc.calcul.math.Fonction#valeur(double)
	 */
	@Override
	public double valeur(double x) {
		return  poids * taux(x,a1) +  (1 - poids) * taux(x,a2);
	}

	//----------- Implémentation de l'interface Primitivable -----------

	@Override
	public Fonction getFonctionPrimitive() {
		return new Fonction() {
			
			private double primitiveTaux(double x, double puissance) {
				return tmax * x - (tmax-tmin)* ct * (Math.pow((1.0 + x/ct),1.0 -puissance)) /(1.0 - puissance);
			}
			
			@Override
			public double valeur(double x) {
				return  poids * primitiveTaux(x,a1) +  (1 - poids) * primitiveTaux(x,a2);
			}
			
		};
	}

	
}
