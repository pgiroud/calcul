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
package org.impotch.calcul.impot.cantonal.ne.pp;

import org.impotch.bareme.Bareme;
import org.impotch.bareme.BaremeTxMarginalEtEffectifParTranche;
import org.impotch.calcul.impot.cantonal.FournisseurCantonal;
import org.impotch.util.TypeArrondi;

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

	/**
     * Barème en vigueur depuis 2001
	 * @see FournisseurCantonal#construireBaremeFortune(int)
	 */
	@Override
	protected Bareme construireBaremeFortune(int annee) {
        BaremeTxMarginalEtEffectifParTranche.Constructeur constructeur = new BaremeTxMarginalEtEffectifParTranche.Constructeur();
        constructeur.tranche(   50000,  "0");
        constructeur.tranche(  200000,  "3 ‰");
        constructeur.tranche(  350000,  "4 ‰");
        constructeur.tranche(  500000,  "5 ‰");
        constructeur.derniereTranche( "3.6 ‰");
        constructeur.typeArrondiSurChaqueTranche(TypeArrondi.CINQ_CTS_INF).seuil(25);
        return constructeur.construire();
	}

	/**
     * Barème en vigueur depuis 2008
	 * @see FournisseurCantonal#construireBaremeRevenu(int)
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
		constructeur.typeArrondiSurChaqueTranche(TypeArrondi.CINQ_CTS_INF).seuil(25);
		return constructeur.construire();
	}

}
