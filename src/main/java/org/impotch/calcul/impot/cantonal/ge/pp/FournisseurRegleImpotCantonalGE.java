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

import org.impotch.bareme.Bareme;
import org.impotch.calcul.impot.cantonal.ge.pp.avant2010.ProducteurRabaisImpot;
import org.impotch.calcul.impot.indexation.Indexateur;
import org.impotch.calcul.impot.taxation.pp.DeductionSociale;
import org.impotch.calcul.impot.taxation.pp.ProducteurImpot;
import org.impotch.calcul.impot.taxation.pp.ge.deduction.DeductionBeneficiaireRentesAVSAI;
import org.impotch.util.TypeArrondi;

public interface FournisseurRegleImpotCantonalGE {
	Bareme getBaremeRevenu(int annee);
	Bareme getBaremeRevenuFamille(int annee);
	
	Bareme getBaremeFortune(int annee);
	Bareme getBaremeFortuneSupplementaire(int annee);
	
	ProducteurImpot construireProducteurImpotsCantonauxRevenu(int annee, TypeArrondi typeArrondi);
    ProducteurImpot construireProducteurImpotsCantonauxPC(int annee);
    ProducteurImpot getProducteurImpotsICCRevenu(int annee);
	ProducteurImpot getProducteurImpotsICCFortune(int annee);
	ProducteurImpot getProducteurImpotsICCFortuneSupplementaire(int annee);
	
	Indexateur getIndexateurBaseMai1993(int anneeBaseIndexation);
	
	/**
	 * Retourne la règle calculant la déduction sociale sur les charges (voir article 39 de la LIPP).
	 * 
	 * @param annee Une année supérieure ou égale à 2010
	 * @return la règle calculant la déduction sociale sur les charges.
	 */
	DeductionSociale getRegleDeductionSocialeCharge(int annee);

	/**
	 * Retourne la règle calculant la déduction en cas d'activité lucrative des 2 conjoints
	 * @param annee L'année pour laquelle la règle va s'appliquer
	 * @return la règle de calcul
     */
	DeductionSociale getRegleDeductionDoubleActivite(int annee);

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
