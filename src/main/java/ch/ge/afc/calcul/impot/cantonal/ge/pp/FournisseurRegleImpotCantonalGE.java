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
package ch.ge.afc.calcul.impot.cantonal.ge.pp;

import ch.ge.afc.calcul.bareme.Bareme;
import ch.ge.afc.calcul.impot.cantonal.ge.pp.avant2010.ProducteurRabaisImpot;
import ch.ge.afc.calcul.impot.cantonal.ge.pp.indexateur.IndexateurMontant;
import ch.ge.afc.calcul.impot.taxation.pp.DeductionSociale;
import ch.ge.afc.calcul.impot.taxation.pp.ProducteurImpot;
import ch.ge.afc.calcul.impot.taxation.pp.ge.deduction.DeductionBeneficiaireRentesAVSAI;

public interface FournisseurRegleImpotCantonalGE {
	Bareme getBaremeRevenu(int annee);
	Bareme getBaremeRevenuFamille(int annee);
	
	Bareme getBaremeFortune(int annee);
	Bareme getBaremeFortuneSupplementaire(int annee);
	
	ProducteurImpot construireProducteurImpotsCantonauxRevenu(int annee);
	ProducteurImpot getProducteurImpotsICCRevenu(int annee);
	ProducteurImpot getProducteurImpotsICCFortune(int annee);
	ProducteurImpot getProducteurImpotsICCFortuneSupplementaire(int annee);
	
	IndexateurMontant getIndexateurBaseMai1993(int anneeBaseIndexation);
	
	/**
	 * Retourne la règle calculant la déduction sociale sur les charges (voir article 39 de la LIPP).
	 * 
	 * @param annee Une année supérieure ou égale à 2010
	 * @return la règle calculant la déduction sociale sur les charges.
	 */
	DeductionSociale getRegleDeductionSocialeCharge(int annee);
	
	/**
	 * Retourne la déduction sociale pour les bénéficiaires de rentes AVS ou AI.
	 * Voir article 40 de la LIPP.
	 * @param annee Une année supérieure ou égale à 2010
	 * @return la règle de calcul des déductions sociales pour rentiers AVS AI
	 */
	DeductionBeneficiaireRentesAVSAI getDeductionBeneficiaireRenteAVSAI(int annee);
	
	/**
	 * Retourne le producteur des montants déterminants des rabais d'impôt suivant la
	 * composition familiale, la situation vis-à-vis de l'AVS, la double activité, ...
	 * @param annee Une année comprise entre 2001 et 2009 incluses
	 * @return le producteur des montants de rabais d'impôts
	 */
	ProducteurRabaisImpot getProducteurRabaisImpot(int annee);
	
}
