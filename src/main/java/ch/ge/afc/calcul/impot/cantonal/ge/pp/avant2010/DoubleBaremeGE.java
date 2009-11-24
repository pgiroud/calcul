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

import ch.ge.afc.calcul.bareme.Bareme;
import ch.ge.afc.calcul.impot.taxation.pp.SituationFamiliale;
import ch.ge.afc.calcul.impot.taxation.pp.famille.DoubleBareme;

/**
 * La détermination du type de barème à appliquer doit prendre en compte
 * les conjoints de fonctionnaire internationaux.
 * 
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class DoubleBaremeGE extends DoubleBareme {

	public DoubleBaremeGE(Bareme baremeSeul, Bareme baremeFamille) {
		super(baremeSeul, baremeFamille);
	}

	/**
	 * Attention, la situation familiale fournie en paramètre doit être
	 * spécifique.
	 * @throws ClassCastException si la situation familiale n'est pas du type @link{ch.ge.afc.calcul.impot.cantonal.ge.pp.avant2010.SituationFamilialeGE} 
	 */
	@Override
	protected boolean isFamille(SituationFamiliale situation) {
		SituationFamilialeGE situationGE = (SituationFamilialeGE)situation;
		if (situationGE.isConjointFonctionnaireInternational()) return false;
		return super.isFamille(situation);
	}


	
}
