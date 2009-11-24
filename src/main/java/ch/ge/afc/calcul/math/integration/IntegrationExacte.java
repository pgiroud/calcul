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
public class IntegrationExacte implements MethodeIntegration {

	/* (non-Javadoc)
	 * @see ch.ge.afc.calcul.math.integration.MethodeIntegration#integre(ch.ge.afc.calcul.math.Fonction, double, double)
	 */
	@Override
	public double integre(Fonction f, double x1, double x2) {
		if (!(f instanceof Primitivable)) throw new IllegalArgumentException("Pour faire une intégration exacte, la fonction doit posséder une primitive");
		Fonction primitive = ((Primitivable)f).getFonctionPrimitive(); 
		return primitive.valeur(x2) - primitive.valeur(x1);
	}

}
