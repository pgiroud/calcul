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
package org.impotch.calcul.impot.cantonal.ge.pp.avant2010;

import static org.impotch.bareme.Point.ORIGINE;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.impotch.bareme.Point;
import org.impotch.util.TypeArrondi;
import org.impotch.util.math.Fonction;
import org.impotch.util.math.integration.MethodeIntegration;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class DiscretisationBaremeMarie {

	private MethodeIntegration methodeIntegration;
	private Fonction tauxMarginal;
	private TypeArrondi arrondi = TypeArrondi.CINQ_CTS;
	
	private int largeurCourante = -1;
	private List<PasDiscretisation> pas = new ArrayList<PasDiscretisation>();
	private boolean listeTriee = true;
	
	public void setMethodeIntegration(MethodeIntegration methodeIntegration) {
		this.methodeIntegration = methodeIntegration;
	}

	public void setTauxMarginal(Fonction tauxMarginal) {
		this.tauxMarginal = tauxMarginal;
	}
	
	public void setArrondi(TypeArrondi arrondi) {
		this.arrondi = arrondi;
	}

	public DiscretisationBaremeMarie largeur(int largeur) {
		largeurCourante = largeur;
		return this;
	}
	
	public DiscretisationBaremeMarie jusqua(int montant) {
		pas.add(new PasDiscretisation(largeurCourante,montant));
		listeTriee = false;
		return this;
	}
	
	private BigDecimal integrer(BigDecimal x1, BigDecimal x2) {
		return BigDecimal.valueOf(methodeIntegration.integre(tauxMarginal, x1.doubleValue(), x2.doubleValue()));
	}
	
	public List<Point> obtenirPointsDiscretisation() {
		if (!listeTriee) {
			Collections.sort(pas);
			listeTriee = true;
		}
		List<Point> points = new LinkedList<Point>();
		Point pointPrecedent = ORIGINE;
		points.add(ORIGINE);
		int maxPrecedent = 0;
		for (PasDiscretisation pad : pas) {
			for (int abscisse = maxPrecedent + pad.largeur; abscisse <= pad.montantSup; abscisse += pad.largeur) {
				BigDecimal abscisseBd = new BigDecimal(abscisse);
				BigDecimal ordonnee = pointPrecedent.getOrdonnee().add(this.integrer(pointPrecedent.getAbscisse(), abscisseBd));
				points.add(new Point(abscisseBd,arrondi.arrondirMontant(ordonnee)));
				pointPrecedent = new Point(abscisseBd,ordonnee);
			}
			maxPrecedent = pad.montantSup;
		}
		return points;
	}
	
	private class PasDiscretisation implements Comparable<PasDiscretisation> {
		private final int largeur;
		private final int montantSup;
		
		public PasDiscretisation(int largeur, int montantSup) {
			this.largeur = largeur;
			this.montantSup = montantSup;
		}

		@Override
		public int compareTo(PasDiscretisation o) {
			if (this.montantSup < o.montantSup) return -1;
			if (this.montantSup > o.montantSup) return 1;
			return 0;
		}
		
		
	}
}
