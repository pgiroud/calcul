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
package org.impotch.calcul.impot.cantonal.ge.pp.avant2010;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.impotch.calcul.ReglePeriodique;
import org.impotch.util.math.Fonction;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class TauxMarginalFamille extends ReglePeriodique implements Fonction {

	/**************************************************/
	/****************** Attributs *********************/
	/**************************************************/

	final Logger logger = LoggerFactory.getLogger(TauxMarginalFamille.class);
	private TauxMarginalSeul tauxMarginalSeul;
	
	// Ces valeurs n'ont pas varié entre 2001 et 2009 !!
	private double qmin = 0.0;
	private double qmax = 1.0;
	private double b1 = 6000.0;
	private double b2 = 70.0;
    private double deductionPersonnelleSeul = 0.0;
    private double deductionPersonnelleCouple = 0.0;
	
    /**************************************************/
    /****************** Constructeurs  ****************/
    /**************************************************/
	
	public TauxMarginalFamille(int annee) {
		super(annee);
        if (annee < 2001) {
            b1 = 61982.0;
            b2 = 100000.0;
            deductionPersonnelleSeul = 10383.0;
            deductionPersonnelleCouple = 20662.0;
        }
	}

	
    /**************************************************/
    /******* Accesseurs / Mutateurs *******************/
    /**************************************************/
	
	public void setTauxMarginalSeul(TauxMarginalSeul tauxMarginalSeul) {
		assert this.getAnnee() == tauxMarginalSeul.getAnnee();
		this.tauxMarginalSeul = tauxMarginalSeul;
	}

    /**************************************************/
    /******************* Méthodes *********************/
    /**************************************************/
	
	/**
	 * Fournit le coefficient appliqué à l'écart entre le taux marginal du revenu total et le
	 * taux marginal de la moitié du revenu.
	 * Ce coefficient est compris entre 2 bornes.
	 * @see #qmin
	 * @see #qmax 
	 */
	private double splitting(double pMontantImposable) {
		double premierePuissance = 1.0 - (Math.pow((1.0 + pMontantImposable/(2 * tauxMarginalSeul.getCt())),-b1));
		return qmin + (qmax-qmin)*Math.pow(premierePuissance,b2);
	}
	
	//----------- Implémentation de l'interface Fonction -----------
	
	/**
	 * Retourne le taux marginal du barème marié. Le taux marginal du barème famille
	 * est basé sur le taux marginal du barème seul appliqué à la moitié du revenu imposable du contribuable marié. 
	 * Ce taux est majoré en proportion de l'écart qui le sépare du taux marginal du barème seul appliqué au revenu imposable total du contribuable marié.
	 * @param pMontantImposable le revenu imposable
	 * @return le taux marginal.
	 */
	@Override
	public double valeur(double pMontantImposable) {
		double tauxSeulMoitieRevenu = tauxMarginalSeul.valeur(pMontantImposable / 2.0);
		double ecartAvecTauxSeulRevenu = tauxMarginalSeul.valeur(pMontantImposable + deductionPersonnelleCouple - deductionPersonnelleSeul) - tauxSeulMoitieRevenu;
		double split = splitting(pMontantImposable);
		return  tauxSeulMoitieRevenu + split * ecartAvecTauxSeulRevenu;
	}

}
