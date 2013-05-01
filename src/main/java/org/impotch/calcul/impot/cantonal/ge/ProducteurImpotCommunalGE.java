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
package org.impotch.calcul.impot.cantonal.ge;

import java.math.BigDecimal;
import java.text.MessageFormat;

import org.impotch.calcul.impot.FournisseurAssietteCommunale;
import org.impotch.calcul.impot.ImpotProduit;
import org.impotch.calcul.impot.ProducteurImpotCommunal;
import org.impotch.calcul.impot.RecepteurImpot;
import org.impotch.calcul.impot.cantonal.ge.param.FournisseurParametrageCommunaleGE;
import org.impotch.calcul.impot.taxation.forimposition.ForCommunal;
import org.impotch.calcul.impot.taxation.repart.Part;
import org.impotch.calcul.impot.taxation.repart.Repartition;
import org.impotch.calcul.lieu.ICommuneSuisse;
import org.impotch.util.BigDecimalUtil;
import org.impotch.calcul.util.IExplicationDetailleeBuilder;
import org.impotch.util.TypeArrondi;

/**
 * Classe chargée de produire les impôts communaux pour les communes du cantonal
 * de Genève.
 * Les impôts communaux genevois pour les personnes physiques ainsi que pour les personnes morales sont calculés à partir
 * de l'impôt cantonal de base. Cet impôt est découpé en 2 :
 * <ul> 
 * 	<li>une première part appelée <strong>part privilégiée</strong> affectée à la commune de domicile. Cette part est la part affectée au fond de péréquation pour les personnes morales.</li>
 * 	<li>une deuxième part qui sera elle-même découpée suivant la matière imposable localisée sur les diverses communes du cantonal.</li>
 * </ul>
 * 
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public abstract class ProducteurImpotCommunalGE implements ProducteurImpotCommunal {

	/**************************************************/
    /****************** Attributs *********************/
    /**************************************************/

	private final String codePartPrivilegiee;
	private final String codeCtsAdd;
	
	//---- Dépendances injectées
	private FournisseurParametrageCommunaleGE fournisseurParametre;
	
	/**************************************************/
    /*************** Constructeurs ********************/
    /**************************************************/

	/**
	 * Construit le producteur en spécifiant 2 codes : le premier pour la part
	 * privilégiée et le second pour les centimes additionnels issus de la répartition.
	 * @param codePartPrivilegiee le code de la part privilégiée.
	 * @param codeCtsAdd le code des centimes additionnels.
	 */
	public ProducteurImpotCommunalGE(String codePartPrivilegiee, String codeCtsAdd) {
		this.codePartPrivilegiee = codePartPrivilegiee;
		this.codeCtsAdd = codeCtsAdd;
	}
	
	/**************************************************/
    /******* Accesseurs / Mutateurs *******************/
    /**************************************************/
		
	public void setFournisseurParametrage(FournisseurParametrageCommunaleGE fournisseur) {
		fournisseurParametre = fournisseur;
	}
	
	protected FournisseurParametrageCommunaleGE getFournisseurParametrage() {
		return fournisseurParametre;
	}
	
	protected abstract IExplicationDetailleeBuilder createExplicationBuilder();


	/**************************************************/
    /******************* Méthodes *********************/
    /**************************************************/
	
	protected String getCodeBeneficiaire(ICommuneSuisse commune) {
		return String.valueOf(commune.getNumeroOFS());
	}
	
	protected String getCodePartPrivilegiee() {
		return codePartPrivilegiee;
	}

	/**
	 * Calcule la part privilégiée pour les PP et la part pour le fond de péréquation pour les PM.
	 * Produit les centimes additionnels sur cette part et retourne la part.
	 * @param recepteur Le recepteur d'impôt qui reçoit les centimes additionnels produits.
	 * @param montantCantonalBase le montant de l'impôt cantonal de base
	 * @param fournisseur le fournisseur d'assiette
	 * @return le montant de la part privilégiée (et non pas le montant des centimes additionnels sur cette part).
	 */
	protected abstract BigDecimal produireCtsAdditionnelsEtRetournePartPrivilegiee(RecepteurImpot recepteur, BigDecimal montantCantonalBase, FournisseurAssietteCommunale fournisseur);
	
	//-------------- Implémentation de l'interface ProducteurImpotCommunal
	
	public void produireImpot(BigDecimal montantCantonalBase, FournisseurAssietteCommunale fournisseur, RecepteurImpot recepteur) {
		if (BigDecimalUtil.isStrictementPositif(montantCantonalBase)) {
			BigDecimal partPrivilegiee = produireCtsAdditionnelsEtRetournePartPrivilegiee(recepteur,montantCantonalBase,fournisseur);
			// Reste les autres parts à traiter !!
			IExplicationDetailleeBuilder builder = this.createExplicationBuilder();
			BigDecimal partARepartir = montantCantonalBase;
			if (BigDecimalUtil.isStrictementPositif(partPrivilegiee)) {
				partARepartir = partARepartir.subtract(partPrivilegiee);
				builder.ajouter("Reste à répartir");
				builder.ajouter(MessageFormat.format("{0,number,#,##0.00} - {1,number,#,##0.00}",montantCantonalBase,partPrivilegiee));
				builder.ajouter(MessageFormat.format("{0,number,#,##0.00}",partARepartir));
				builder.nouvelleLigne();
			} 
			Repartition<ForCommunal> repartitionAssiette = fournisseur.getRepartition();
			Repartition<ForCommunal> repartition = repartitionAssiette.repartir(partARepartir,TypeArrondi.CINQ_CTS);
			for (ForCommunal forComm : repartition.getForImposition()) {
				Part part = repartition.getPart(forComm);
				ICommuneSuisse commune = forComm.getLieu();
				BigDecimal baseCalcul = part.getMontant();
				BigDecimal taux = fournisseurParametre.getTauxCentimes(fournisseur.getPeriodeFiscale(),commune);
				BigDecimal impot = TypeArrondi.CINQ_CTS.arrondirMontant(baseCalcul.multiply(taux));
				if (BigDecimalUtil.isStrictementPositif(impot)) {
					// Explication de calcul
					builder.ajouter("Part de la commune " + commune.getNom());
					BigDecimal partAssiettePourCommune = repartitionAssiette.getPart(new ForCommunal(commune)).getMontant();
					BigDecimal assietteTotal = repartitionAssiette.getTotal();
					builder.ajouter(MessageFormat.format("{0,number,#,##0.00} * {1,number,integer} / {2,number,integer}",partARepartir,partAssiettePourCommune,assietteTotal));
					builder.ajouter(MessageFormat.format("{0,number,#,##0.00}",baseCalcul));
					builder.nouvelleLigne();
					builder.ajouter("Centimes additionnels pour " + commune.getNom());
					builder.ajouter(MessageFormat.format("{0,number,#,##0.00} * {1,number,percent}",baseCalcul,taux));
					builder.ajouter(MessageFormat.format("{0,number,#,##0.00}",impot));

					ImpotProduit impotProduit = new ImpotProduit(codeCtsAdd,impot);
					impotProduit.setBaseCalcul(baseCalcul);
					impotProduit.setTauxEffectif(taux);
					impotProduit.setCodeBeneficiaire(getCodeBeneficiaire(commune));
					impotProduit.setExplicationCalcul(MessageFormat.format("Le taux {0,number,percent} est appliqué à la base de calcul {1,number,#,##0.00}.",taux,baseCalcul));
					impotProduit.setExplicationDetailleeCalcul(builder.getTexte());
					recepteur.ajouteImpot(impotProduit);
				}
			}
		}
	}
}
