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
package org.impotch.calcul.impot.taxation.pp.famille;

import org.impotch.bareme.Bareme;
import org.impotch.calcul.impot.taxation.pp.SituationFamiliale;
import org.impotch.calcul.impot.taxation.pp.StrategieProductionImpotFamille;

/**
 * En 2026, le système à double barème est celui qui est le plus répandu, avec huit cantons qui l'appliquent.
 * Il prévoit des tarifs différents pour les personnes mariées et pour les personnes non mariées.
 * Les couples mariés sont taxés ensemble, tandis que les non-mariés sont taxés séparément.
 * Un barème standard s’applique aux personnes célibataires, tandis qu'un barème plus favorable
 * est prévu pour les couples mariés afin de réduire les effets de progression.
 * Cela permet de prendre en considération la capacité économique des couples, toutes classes
 * de revenus confondues, et de déterminer la charge fiscale appropriée à chaque niveau de
 * revenu. En 2026, ce modèle s'applique en Argovie, à Berne, à Bâle-Ville, à Zurich, à Zoug,
 * à Lucerne, au Tessin et dans le Jura.
 */
public class DoubleBareme extends ImpositionFamille implements StrategieProductionImpotFamille {
	
    /**************************************************/
    /***************** Attributs **********************/
    /**************************************************/
	
	private final Bareme baremeFamille;
	
    /**************************************************/
    /**************** Constructeurs *******************/
    /**************************************************/
	
	public DoubleBareme(Bareme baremeSeul, Bareme baremeFamille) {
		super(baremeSeul);
		this.baremeFamille = baremeFamille;
	}
	
    /**************************************************/
    /******* Accesseurs / Mutateurs *******************/
    /**************************************************/
	
	protected Bareme getBaremeFamille() {
		return baremeFamille;
	}
	
	/**************************************************/
    /******************* Méthodes *********************/
    /**************************************************/

	
	/* (non-Javadoc)
	 * @see StrategieProductionImpotFamille#getBareme(SituationFamiliale)
	 */
	@Override
	public Bareme getBareme(SituationFamiliale situation) {
		if (isFamille(situation)) return getBaremeFamille();
		else return super.getBareme(situation);
	}
	
}
