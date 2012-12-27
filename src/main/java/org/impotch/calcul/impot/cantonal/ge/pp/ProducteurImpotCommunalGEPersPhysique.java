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
package org.impotch.calcul.impot.cantonal.ge.pp;

import java.math.BigDecimal;
import java.text.MessageFormat;

import org.impotch.calcul.impot.FournisseurAssietteCommunale;
import org.impotch.calcul.impot.ImpotProduit;
import org.impotch.calcul.impot.RecepteurImpot;
import org.impotch.calcul.impot.cantonal.ge.ProducteurImpotCommunalGE;
import org.impotch.calcul.lieu.ICommuneSuisse;
import org.impotch.util.BigDecimalUtil;
import org.impotch.calcul.util.IExplicationDetailleeBuilder;
import org.impotch.util.TypeArrondi;

/**
 * Classe chargée de produire les impôts communaux des personnes physiques
 * pour les communes du cantonal de Genève.
 * Les impôts communaux genevois pour les personnes physiques sont calculés à partir
 * de l'impôt cantonal de base. Cet impôt est découpé en 2 :
 * <ul> 
 * 	<li>une première part appelée <strong>part privilégiée</strong> affectée à la commune de domicile ;</li>
 * 	<li>une deuxième part qui sera elle-même découpée suivant la matière imposable localisée sur les diverses communes du cantonal.</li>
 * </ul>
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public abstract class ProducteurImpotCommunalGEPersPhysique extends
		ProducteurImpotCommunalGE {

	/**************************************************/
    /*************** Constructeurs ********************/
    /**************************************************/

	/**
	 * Construit le producteur en spécifiant 2 codes : le premier pour la part
	 * privilégiée et le second pour les centimes additionnels issus de la répartition.
	 * @param codePartPrivilegiee le code de la part privilégiée.
	 * @param codeCtsAdd le code des centimes additionnels.
	 */
	public ProducteurImpotCommunalGEPersPhysique(String codePartPrivilegiee, String codeCtsAdd) {
		super(codePartPrivilegiee,codeCtsAdd);
	}
	
	/**************************************************/
    /******************* Méthodes *********************/
    /**************************************************/
	
	private ICommuneSuisse getDomicile(FournisseurAssietteCommunale fournisseur) {
		return fournisseur.getNbreJourDomicileSurCommune().keySet().iterator().next();
	}

	
	private String construireExplicationsCalculDetaillees(String nomDomicile, BigDecimal montantBase, BigDecimal tauxPartPrivil, BigDecimal partPrivil, BigDecimal tauxCtsAdd, BigDecimal impot){
		IExplicationDetailleeBuilder buider = this.createExplicationBuilder();
		buider.ajouter("Part privilégiée de la commune " + nomDomicile);
		buider.ajouter(MessageFormat.format("{0,number,#,##0.00} * {1,number,percent}",montantBase,tauxPartPrivil));
		buider.ajouter(MessageFormat.format("{0,number,#,##0.00}",partPrivil));
		buider.nouvelleLigne();
		buider.ajouter("Centimes additionnels sur la part privilégiée");
		buider.ajouter(MessageFormat.format("{0,number,#,##0.00} * {1,number,percent}", partPrivil, tauxCtsAdd));
		buider.ajouter(MessageFormat.format("{0,number,#,##0.00}", impot));
		return buider.getTexte();
	}
	
	protected BigDecimal produireCtsAdditionnelsEtRetournePartPrivilegiee(RecepteurImpot recepteur, BigDecimal montantCantonalBase, FournisseurAssietteCommunale fournisseur) {
		ICommuneSuisse domicile = getDomicile(fournisseur);
		BigDecimal partPrivilegiee = BigDecimal.ZERO;
		if ("GE".equals(domicile.getCanton().getCodeIso2())) {
			BigDecimal tauxPartPrivilegiee = getFournisseurParametrage().getPartPrivilegiee(fournisseur.getPeriodeFiscale(), domicile);
			partPrivilegiee = TypeArrondi.CINQ_CTS.arrondirMontant(montantCantonalBase.multiply(tauxPartPrivilegiee));
			BigDecimal taux = getFournisseurParametrage().getTauxCentimes(fournisseur.getPeriodeFiscale(), domicile);
			BigDecimal impot = TypeArrondi.CINQ_CTS.arrondirMontant(partPrivilegiee.multiply(taux));
			if (BigDecimalUtil.isStrictementPositif(impot)) {
				ImpotProduit impotProduit = new ImpotProduit(getCodePartPrivilegiee(),impot);
				impotProduit.setBaseCalcul(partPrivilegiee);
				impotProduit.setTauxEffectif(taux);
				impotProduit.setCodeBeneficiaire(getCodeBeneficiaire(domicile));
				impotProduit.setExplicationCalcul(MessageFormat.format("Le taux {0,number,percent} est appliqué à la base de calcul {1,number,#,##0.00}",taux,partPrivilegiee));
				impotProduit.setExplicationDetailleeCalcul(construireExplicationsCalculDetaillees(domicile.getNom(),montantCantonalBase,tauxPartPrivilegiee,partPrivilegiee,taux,impot));
				recepteur.ajouteImpot(impotProduit);
			}
		}
		return partPrivilegiee;
	}
}
