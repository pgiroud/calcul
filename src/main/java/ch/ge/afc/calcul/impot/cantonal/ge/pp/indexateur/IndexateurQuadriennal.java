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
package ch.ge.afc.calcul.impot.cantonal.ge.pp.indexateur;

import java.math.BigDecimal;

import ch.ge.afc.util.TypeArrondi;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class IndexateurQuadriennal implements IndexateurMontant {

	private FournisseurIndicePeriodiqueGE fournisseurIndice;
	private final int anneeBase;
	
	public IndexateurQuadriennal(int anneeBase) {
		super();
		this.anneeBase = anneeBase;
	}

	/**
	 * @return the fournisseurIndice
	 */
	protected FournisseurIndicePeriodiqueGE getFournisseurIndice() {
		return fournisseurIndice;
	}

	/**
	 * @param fournisseurIndice the fournisseurIndice to set
	 */
	public void setFournisseurIndice(FournisseurIndicePeriodiqueGE fournisseurIndice) {
		this.fournisseurIndice = fournisseurIndice;
	}

	protected int getAnneeBase() {
		return anneeBase;
	}

	
	
	/* (non-Javadoc)
	 * @see ch.ge.afc.calcul.impot.cantonal.ge.pp.indexateur.IndexateurMontant#indexerMontant(java.math.BigDecimal, int, int)
	 */
	@Override
	public BigDecimal indexerMontant(BigDecimal montantBase,
			int annee) {
		return indexerMontant(montantBase, annee, TypeArrondi.FRANC);
	}

	/* (non-Javadoc)
	 * @see ch.ge.afc.calcul.impot.cantonal.ge.pp.indexateur.IndexateurMontant#indexerMontant(java.math.BigDecimal, int, ch.ge.afc.util.TypeArrondi)
	 */
	@Override
	public BigDecimal indexerMontant(BigDecimal montantBase, int annee,
			TypeArrondi arrondi) {
		int anneeIndice = getAnneeBase() + 4 * ((annee - getAnneeBase())/4);
		if (anneeIndice == getAnneeBase()) return montantBase;
		else {
			BigDecimal indiceBase = getFournisseurIndice().getIndice(getAnneeBase());
			BigDecimal indiceDerniereRevalorisation = getFournisseurIndice().getIndice(anneeIndice);
			return arrondi.arrondirMontant(montantBase.multiply(indiceDerniereRevalorisation).divide(indiceBase,10,BigDecimal.ROUND_HALF_UP));
		}
	}


}
