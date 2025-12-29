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
package org.impotch.calcul.impot.indexation;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.impotch.bareme.BaremeParTranche;
import org.impotch.bareme.BaremeTauxMarginalConstantParTranche;
import org.impotch.util.TypeArrondi;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class IndexateurPeriodique implements Indexateur {

	public static IndexateurPeriodique.Constructeur unConstructeurIndexateurQuadriAnnuel(int anneeBaseIndexation) {
		return new Constructeur(anneeBaseIndexation,4);
	}

	public static IndexateurPeriodique.Constructeur unConstructeurIndexateurAnnuel(int anneeBaseIndexation) {
		return new Constructeur(anneeBaseIndexation,1);
	}


	private FournisseurIndicePeriodique fournisseurIndice;

	private final int anneeBaseIndexation;
    private final int nbPeriodeAnnuelle;
	
	private IndexateurPeriodique(int anneeBaseIndexation, int nbPeriodeAnnuelle) {
		super();
		this.anneeBaseIndexation = anneeBaseIndexation;
        this.nbPeriodeAnnuelle = nbPeriodeAnnuelle;
	}

	/**
	 * @return the fournisseurIndice
	 */
	protected FournisseurIndicePeriodique getFournisseurIndice() {
		return fournisseurIndice;
	}

	/**
	 * @param fournisseurIndice the fournisseurIndice to set
	 */
	private void setFournisseurIndice(FournisseurIndicePeriodique fournisseurIndice) {
		this.fournisseurIndice = fournisseurIndice;
	}
	

    private BigDecimal getIndiceDerniereRevalorisation(int annee) {
        int anneeIndice = getAnneeIndice(annee);
        return getFournisseurIndice().getIndice(anneeIndice);
    }

	private int getAnneeIndice(int annee) {
        return (1 == nbPeriodeAnnuelle) ? annee : anneeBaseIndexation + nbPeriodeAnnuelle * ((annee - anneeBaseIndexation)/ nbPeriodeAnnuelle);
    }
	

	/* (non-Javadoc)
	 * @see IndexateurMontant#indexer(java.math.BigDecimal, int, org.impotch.util.TypeArrondi)
	 */
	@Override
	public BigDecimal indexer(int periodeDebut, BigDecimal montantBase, int annee,
                              TypeArrondi arrondi) {
		BigDecimal indiceDebut = getFournisseurIndice().getIndice(periodeDebut);
        return (getAnneeIndice(annee) == periodeDebut) ?
                montantBase
                : arrondi.arrondir(montantBase.multiply(getIndiceDerniereRevalorisation(annee)).divide(indiceDebut,15, RoundingMode.HALF_UP));
	}

    private BigDecimal obtenirRapportRencherissement(int periodeDebut, int annee) {
		BigDecimal indiceDebut = getFournisseurIndice().getIndice(periodeDebut);
        return getIndiceDerniereRevalorisation(annee).divide(indiceDebut, 15, RoundingMode.HALF_UP);
    }

    @Override
    public BaremeParTranche indexer(int periodeDebut, BaremeParTranche bareme, int annee, TypeArrondi arrondi) {
        BigDecimal rapport = obtenirRapportRencherissement(periodeDebut, annee);
        BaremeParTranche baremeCible = bareme.homothetie(rapport,arrondi);
        baremeCible = baremeCible.homothetieValeur(rapport,arrondi);
        return baremeCible;
    }

    @Override
    public BaremeTauxMarginalConstantParTranche indexer(int periodeDebut, BaremeTauxMarginalConstantParTranche bareme, int annee, TypeArrondi arrondi) {
        BigDecimal rapport = obtenirRapportRencherissement(periodeDebut, annee);
        return bareme.homothetie(rapport, arrondi);
    }

	public static class Constructeur {

		private final int anneeBaseIndexation;
		private final int nbPeriode;

		private FournisseurIndicePeriodique fournisseurIndice;

		Constructeur(int anneeBaseIndexation, int nbPeriode) {
			this.anneeBaseIndexation = anneeBaseIndexation;
			this.nbPeriode = nbPeriode;
		}


		public Constructeur fournisseurIndice(FournisseurIndicePeriodique fournisseur) {
			this.fournisseurIndice = fournisseur;
			return this;
		}
		public IndexateurPeriodique cons() {
			IndexateurPeriodique indexateur = new IndexateurPeriodique(anneeBaseIndexation,nbPeriode);
			indexateur.setFournisseurIndice(fournisseurIndice);
			return indexateur;
		}
	}
}
