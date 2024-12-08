/*
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

import org.impotch.bareme.Bareme;
import org.impotch.calcul.impot.taxation.pp.SituationFamiliale;
import org.impotch.calcul.impot.taxation.pp.famille.DoubleBareme;

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
	 * @throws ClassCastException si la situation familiale n'est pas du type @link{SituationFamilialeGE}
	 */
	@Override
	protected boolean isFamille(SituationFamiliale situation) {
		SituationFamilialeGE situationGE = (SituationFamilialeGE)situation;
		if (situationGE.isConjointFonctionnaireInternational()) return false;
		return super.isFamille(situation);
	}


	
}
