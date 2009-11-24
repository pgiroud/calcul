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
package ch.ge.afc.calcul.math.integration;

import ch.ge.afc.calcul.math.Fonction;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class MethodeIntegrationPointMilieu implements MethodeIntegration {

//	private double pas = 1.0;
//	
//	protected void setPas(double pas) {
//		this.pas = pas;
//	}

	/* (non-Javadoc)
	 * @see ch.ge.afc.calcul.math.integration.MethodeIntegration#integre(ch.ge.afc.calcul.math.Fonction, double, double)
	 */
	@Override
	public double integre(Fonction f, double x1, double x2) {
		double somme = 0.0;
		for (double milieu = x1 + 0.5; milieu < x2 ; milieu += 1.0) {
			somme += f.valeur(milieu);
		}
		return somme;
	}

}
