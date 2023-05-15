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
package org.impotch.calcul.impot;

import java.math.BigDecimal;
import java.text.MessageFormat;

import org.impotch.calcul.impot.taxation.pp.RecepteurMultipleImpot;
import org.impotch.calcul.impot.taxation.pp.RecepteurUniqueImpot;
import org.impotch.util.BigDecimalUtil;
import org.impotch.util.TypeArrondi;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class ProducteurImpotDerivePourcent implements ProducteurImpotDerive {

	private final String nomImpot;
	private final BigDecimal taux;
	private final String codeBeneficiaire;
	private TypeArrondi typeArrondi = TypeArrondi.CINQ_CENTIEMES_LES_PLUS_PROCHES;
	private String explicationDetailleePattern;

	private ProducteurImpotDerive producteurDerive;
	
	public ProducteurImpotDerivePourcent(String nom, String taux, String codeBeneficiaire) {
        this(nom,BigDecimalUtil.parseTaux(taux),codeBeneficiaire);
	}

    public ProducteurImpotDerivePourcent(String nom, BigDecimal taux, String codeBeneficiaire) {
        this.nomImpot = nom;
        this.taux = taux;
        this.codeBeneficiaire = codeBeneficiaire;
    }

    /**
	 * @return the nomImpot
	 */
	protected String getNomImpot() {
		return nomImpot;
	}

	/**
	 * @param producteurDerive the producteurDerive to set
	 */
	public void setProducteurDerive(ProducteurImpotDerive producteurDerive) {
		this.producteurDerive = producteurDerive;
	}

	/**
	 * @return the typeArrondi
	 */
	protected TypeArrondi getTypeArrondi() {
		return typeArrondi;
	}

	/**
	 * @param typeArrondi the typeArrondi to set
	 */
	public void setTypeArrondi(TypeArrondi typeArrondi) {
		this.typeArrondi = typeArrondi;
	}

	protected BigDecimal calculMontant(BigDecimal montantBase, FournisseurAssiette fournisseur) {
        BigDecimal resultatNonArrondi = montantBase.multiply(taux);
        if (null == typeArrondi) {
            return resultatNonArrondi;
        }  else {
            return typeArrondi.arrondirMontant(resultatNonArrondi);
        }
	}
	
	public String getExplicationDetailleePattern() {
		return explicationDetailleePattern;
	}

	public void setExplicationDetailleePattern(String explicationDetailleePattern) {
		this.explicationDetailleePattern = explicationDetailleePattern;
	}

	/* (non-Javadoc)
	 * @see org.impotch.calcul.impot.ProducteurImpotDerive#produireImpot(java.math.BigDecimal, org.impotch.calcul.impot.FournisseurAssiette, org.impotch.calcul.impot.RecepteurImpot)
	 */
	@Override
	public void produireImpot(BigDecimal montantBase,
			FournisseurAssiette fournisseur, RecepteurImpot recepteur) {
		RecepteurMultipleImpot recepteurMultiple = new RecepteurMultipleImpot();
		recepteurMultiple.ajouteRecepteur("Initial", recepteur);
		
		RecepteurUniqueImpot recepteurImpotCourant = null;
		if (null != producteurDerive) {
			recepteurImpotCourant = new RecepteurUniqueImpot(this.getNomImpot());
			recepteurMultiple.ajouteRecepteur(this.getNomImpot(), recepteurImpotCourant);
		}
		
		BigDecimal montant = calculMontant(montantBase,fournisseur);
		
		ImpotProduit impotProduit = new ImpotProduit(nomImpot,montant);
		impotProduit.setCodeBeneficiaire(codeBeneficiaire);
		impotProduit.setBaseCalcul(montantBase);
		impotProduit.setTauxEffectif(taux);
		impotProduit.setExplicationCalcul(MessageFormat.format("Le taux {1,number,percent} est appliqué à la base de calcul {0,number}",montantBase,taux,montant));
		impotProduit.setExplicationDetailleeCalcul(MessageFormat.format(explicationDetailleePattern, montantBase,taux,montant));
		
		recepteurMultiple.ajouteImpot(impotProduit);

		if (null != producteurDerive) {
			producteurDerive.produireImpot(recepteurImpotCourant.getValeur().getMontant(), fournisseur, recepteur);
		}
		
	}
	
	
}
