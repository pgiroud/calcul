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
package ch.ge.afc.calcul.impot;

import java.math.BigDecimal;

/**
 * Interface représentant l'impôt produit par une des règles de calcul.
 * 
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public interface Impot {
	/**
	 * Retourne la base ayant permis le calcul de l'impôt. En général, le montant d'impôt
	 * est égale à la base multiplié par le taux effectif.
	 * Attention cette base peut être nulle.
	 * @return la base de calcul
	 */
	BigDecimal getBaseCalcul();
	
	/**
	 * Le taux effectif de l'impôt. 
	 * Il peut être nul lorsque l'impôt n'est pas produit par l'application d'un taux.
	 * @return le taux effectif de l'impôt.
	 */
	BigDecimal getTauxEffectif();
	
	/**
	 * Retourne le montant de l'impôt. Ce montant peut être négatif dans le
	 * cas d'une réduction d'impôt.
	 * @return le montant de l'impôt.
	 */
	BigDecimal getMontant();
	
	/**
	 * Le type de l'impôt est un code permettant de le distinguer parmi un ensemble d'impôt.
	 * @return le type de l'impôt.
	 */
	String getTypeImpot();
	
	/**
	 * Pour un même type d'impôt, il peut exister plusieurs bénéficiaires. Ceci est par exemple le cas
	 * des impôts communaux.
	 * Pour les communes, le code contient en général le numéro OFS de la commune.
	 * @return le code du bénéficiaire de l'impôt.
	 */
	String getCodeBeneficiaire();
	
	/**
	 * Fournit une chaîne de caractère expliquant le calcul de l'impôt de manière succinte.
	 * @return une explication de calcul.
	 */
	String getExplicationCalcul();

	/**
	 * Fournit une chaîne de caractère expliquant le calcul de l'impôt de manière détaillée.
	 * @return une explication de calcul.
	 */
	String getExplicationDetailleeCalcul();
}
