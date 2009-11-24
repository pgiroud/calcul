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
package ch.ge.afc.calcul.impot.taxation.pp.famille;

import java.math.BigDecimal;

import ch.ge.afc.calcul.bareme.Bareme;
import ch.ge.afc.calcul.impot.taxation.pp.SituationFamiliale;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class DoubleBaremeEtRabaisCharge extends DoubleBareme {

	private final BigDecimal rabaisParCharge;
	
	public DoubleBaremeEtRabaisCharge(Bareme baremeSeul, Bareme baremeFamille, BigDecimal rabaisParCharge) {
		super(baremeSeul, baremeFamille);
		this.rabaisParCharge = rabaisParCharge;
	}

	private int getNombreCharge(SituationFamiliale situation) {
		return situation.getEnfants().size() + situation.getPersonnesNecessiteuses().size();
	}
	
	@Override
	public BigDecimal transformeImpotDeterminant(SituationFamiliale situation,
			BigDecimal impot) {
		BigDecimal impotApresRabais = impot;
		int nombreCharge = getNombreCharge(situation);
		if (nombreCharge > 0) impotApresRabais = impotApresRabais.subtract(rabaisParCharge.multiply(new BigDecimal(nombreCharge)));
		return impotApresRabais;
	}

	
	
}
