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
import java.util.LinkedList;
import java.util.List;

import org.impotch.calcul.impot.taxation.pp.RecepteurMultipleImpot;
import org.impotch.calcul.impot.taxation.pp.RecepteurUniqueImpot;
import org.impotch.calcul.util.ExplicationDetailleTexteBuilder;
import org.impotch.calcul.util.IExplicationDetailleeBuilder;
import org.impotch.util.BigDecimalUtil;
import org.impotch.util.StringUtil;
import org.impotch.util.TypeArrondi;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class ProducteurImpotDerivePourcent implements ProducteurImpotDerive {

	public static Constructeur unConsProducteurImpotDerive(String code) {
		return new Constructeur(code);
	}

	private final String nomImpot;
	private final BigDecimal taux;
	private final String codeBeneficiaire;
	private TypeArrondi typeArrondi = TypeArrondi.CINQ_CENTIEMES_LES_PLUS_PROCHES;
	private String explicationDetailleePattern;

	private ProducteurImpotDerive producteurDerive;
	
    private ProducteurImpotDerivePourcent(String nom, BigDecimal taux, String codeBeneficiaire) {
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
	private void setProducteurDerive(ProducteurImpotDerive producteurDerive) {
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
	private void setTypeArrondi(TypeArrondi typeArrondi) {
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

	protected String getExplicationDetailleePattern() {
		return explicationDetailleePattern;
	}

	private void setExplicationDetailleePattern(String explicationDetailleePattern) {
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
		
		ImpotProduit impotProduit = new ImpotProduit.Cons(nomImpot,montant)
				.codeBeneficiaire(codeBeneficiaire)
				.baseCalcul(montantBase)
				.tauxEffectif(taux)
				.explicationCalcul(MessageFormat.format("Le taux {1,number,percent} est appliqué à la base de calcul {0,number}",montantBase,taux,montant))
				.explicationDetailleeCalcul(MessageFormat.format(explicationDetailleePattern, montantBase,taux,montant))
				.cons();
		
		recepteurMultiple.ajouteImpot(impotProduit);

		if (null != producteurDerive) {
			producteurDerive.produireImpot(recepteurImpotCourant.getValeur().getMontant(), fournisseur, recepteur);
		}
		
	}
	
	public static class Constructeur {

		private final String nomImpot;
		private BigDecimal taux;
		private String codeBeneficiaire;
		private TypeArrondi typeArrondi = TypeArrondi.CINQ_CENTIEMES_LES_PLUS_PROCHES;
		private List<String> explications = new LinkedList<>();

		private ProducteurImpotDerive producteurDerive;

		public Constructeur(String nomImpot) {
			this.nomImpot = nomImpot;
		}

		public Constructeur taux(String tx) {
			return taux(BigDecimalUtil.parseTaux(tx));
		}

		public Constructeur taux(BigDecimal tx) {
			this.taux = tx;
			return this;
		}

		public Constructeur beneficiaire(String benef) {
			this.codeBeneficiaire = benef;
			return this;
		}

		public Constructeur arrondi(TypeArrondi arrondi) {
			this.typeArrondi = arrondi;
			return this;
		}

		public Constructeur producteurDerive(ProducteurImpotDerive producteurDerive) {
			this.producteurDerive = producteurDerive;
			return this;
		}

		public Constructeur explic(String explication) {
			explications.add(explication);
			return this;
		}
		private IExplicationDetailleeBuilder getNewExplicationBuilder() {
			return new ExplicationDetailleTexteBuilder();
		}

		private String construireExplicationDetailleePattern() {
			IExplicationDetailleeBuilder explication = getNewExplicationBuilder();
			for (String explic : explications) {
				explication.ajouter(explic);
			}
			return explication.getTexte();

		}

		public ProducteurImpotDerivePourcent cons() {
			if (null == taux) throw new IllegalStateException("Le taux d’un impot dérivé '" + nomImpot + "' doit être préciser  la construction ! ");
			if (!StringUtil.hasText(codeBeneficiaire)) throw new IllegalStateException("Le code .bénéficiaire d’un impot dérivé '" + nomImpot + "' doit être préciser  la construction ! ");

			ProducteurImpotDerivePourcent prod = new ProducteurImpotDerivePourcent(nomImpot,taux,codeBeneficiaire);
			prod.setTypeArrondi(typeArrondi);
			prod.setExplicationDetailleePattern(construireExplicationDetailleePattern());
			prod.setProducteurDerive(producteurDerive);
			return prod;
		}
	}


}
