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
package org.impotch.calcul.impot.federal;

import org.impotch.calcul.impot.federal.pp.source.CalculateurImpotSourcePrestationCapitalIFD;
import org.impotch.calcul.impot.taxation.pp.DeductionSociale;
import org.impotch.calcul.impot.taxation.pp.ProducteurImpot;
import org.impotch.calcul.impot.taxation.pp.federal.deduction.IDeductionDoubleActivite;

public interface FournisseurRegleImpotFederal {

	ProducteurImpot getProducteurImpotsFederauxPP(int annee);

    IDeductionDoubleActivite getDeductionDoubleActivite(int annee);

	DeductionSociale getRegleDeductionSocialeEnfant(int annee);
	
	DeductionSociale getRegleDeductionSocialeConjoint(int annee);
	
	CalculateurImpotSourcePrestationCapitalIFD getCalculateurImpotSourcePrestationCapitalIFD(int annee);
}