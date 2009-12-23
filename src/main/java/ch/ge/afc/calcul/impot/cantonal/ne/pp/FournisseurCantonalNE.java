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
package ch.ge.afc.calcul.impot.cantonal.ne.pp;

import ch.ge.afc.bareme.Bareme;
import ch.ge.afc.bareme.BaremeTxMarginalEtEffectifParTranche;
import ch.ge.afc.calcul.impot.cantonal.FournisseurCantonal;
import ch.ge.afc.util.TypeArrondi;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class FournisseurCantonalNE extends FournisseurCantonal implements
		FournisseurRegleImpotCantonalNE {

	/**
	 * 
	 */
	public FournisseurCantonalNE() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see ch.ge.afc.calcul.impot.cantonal.FournisseurCantonal#construireBaremeFortune(int)
	 */
	@Override
	protected Bareme construireBaremeFortune(int annee) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ch.ge.afc.calcul.impot.cantonal.FournisseurCantonal#construireBaremeRevenu(int)
	 */
	@Override
	protected Bareme construireBaremeRevenu(int annee) {
		BaremeTxMarginalEtEffectifParTranche.Constructeur constructeur = new BaremeTxMarginalEtEffectifParTranche.Constructeur();
		constructeur.tranche(   5000,  "0");
		constructeur.tranche(  10000,  "2 %");
		constructeur.tranche(  15000,  "4 %");
		constructeur.tranche(  20000,  "8 %");
		constructeur.tranche(  30000, "12 %");
		constructeur.tranche(  40000, "13 %");
		constructeur.tranche(  55000, "14 %");
		constructeur.tranche(  75000, "15 %");
		constructeur.tranche( 110000, "16 %");
		constructeur.tranche( 150000, "17 %");
		constructeur.tranche( 180000, "18 %");
		constructeur.derniereTranche( "14.5 %");
		constructeur.typeArrondi(TypeArrondi.CINQ_CTS_INF).seuil(25);
		return constructeur.construire();
	}

}
