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

import java.math.BigDecimal;
import java.util.OptionalInt;

import org.impotch.bareme.Bareme;
import org.impotch.bareme.BaremeTauxMaximal;
import org.impotch.calcul.impot.Souverainete;
import org.impotch.calcul.impot.taxation.pp.EnfantACharge;
import org.impotch.calcul.impot.taxation.pp.SituationFamiliale;
import org.impotch.calcul.impot.taxation.pp.StrategieProductionImpotFamille;
import org.impotch.util.TypeArrondi;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.impotch.util.BigDecimalUtil.UN_DEMI;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class ImpositionFamille implements
        StrategieProductionImpotFamille {

    /**************************************************/
    /***************** Attributs **********************/
    /**************************************************/
	
	private final Bareme bareme;
	private TypeArrondi typeArrondiImpot = TypeArrondi.VINGTIEME_LE_PLUS_PROCHE;
	private OptionalInt rabaisParCharge = OptionalInt.empty();
	
    /**************************************************/
    /**************** Constructeurs *******************/
    /**************************************************/
	
	public ImpositionFamille(Bareme bareme) {
		this.bareme = bareme;
	}
	
	/**************************************************/
    /******************* Méthodes *********************/
    /**************************************************/

	public Bareme getBaremeSeul() {
		return bareme;
	}
 
	public void setTypeArrondiImpot(TypeArrondi type) {
		this.typeArrondiImpot = type;
	}
	
	public void setRabaisParCharge(int rabais) {
		rabaisParCharge = OptionalInt.of(rabais);
	}

	public BigDecimal produireRabais(SituationFamiliale situation, int rabaisParCharge) {
		BigDecimal nbreEnfant = ZERO;
		for (EnfantACharge enfant : situation.getEnfants()) {
			if (enfant.isDemiPart(Souverainete.CH_FEDERALE)) {
				nbreEnfant = nbreEnfant.add(UN_DEMI);
			} else {
				nbreEnfant = nbreEnfant.add(ONE);
			}
		}
		BigDecimal nbrePersonneNecessiteuse = BigDecimal.valueOf(situation.getPersonnesNecessiteuses().size());
		BigDecimal rabais = nbreEnfant.multiply(BigDecimal.valueOf(rabaisParCharge));
		rabais = rabais.add(nbrePersonneNecessiteuse.multiply(BigDecimal.valueOf(rabaisParCharge)));
		return typeArrondiImpot.arrondir(rabais);
	}

	@Override
	public BigDecimal produireImpotDeterminant(SituationFamiliale situation,
											   BigDecimal determinantArrondi) {
		BigDecimal impotDeterminant = getBareme(situation).calcul(determinantArrondi);
		if (rabaisParCharge.isPresent()) {
			impotDeterminant = ZERO.max(impotDeterminant.subtract(produireRabais(situation,rabaisParCharge.getAsInt())));
		}
		return typeArrondiImpot.arrondir(impotDeterminant);
	}

	@Override
	public BigDecimal produireImpotAuTauxMaximal(SituationFamiliale situation, BigDecimal imposableArrondi) {
		return typeArrondiImpot.arrondir(imposableArrondi.multiply(((BaremeTauxMaximal)getBareme(situation)).getTauxMaximum()));
	}

	protected Bareme getBareme(SituationFamiliale situation) {
		return bareme;
	}


	protected boolean hasCharge(SituationFamiliale situation) {
		return situation.getEnfants().size() > 0 || situation.getPersonnesNecessiteuses().size() > 0;
	}
	
	protected boolean isFamille(SituationFamiliale situation) {
		return situation.isCouple() || hasCharge(situation);
	}
}
