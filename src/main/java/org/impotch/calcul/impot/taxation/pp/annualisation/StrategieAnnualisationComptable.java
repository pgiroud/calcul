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
package org.impotch.calcul.impot.taxation.pp.annualisation;

import java.math.BigDecimal;

import org.impotch.calcul.impot.taxation.pp.StrategieAnnualisation;

public class StrategieAnnualisationComptable implements StrategieAnnualisation {

	/* (non-Javadoc)
	 * @see StrategieAnnualisation#annualiseImpot(java.math.BigDecimal)
	 */
	@Override
	public BigDecimal annualiseImpot(BigDecimal impotAnnuel, int nbreJour) {
		if (360 == nbreJour) return impotAnnuel;
		return impotAnnuel.multiply(new BigDecimal(nbreJour)).divide(new BigDecimal(360));
	}

	
}
