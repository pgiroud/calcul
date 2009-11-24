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
package ch.ge.afc.calcul.assurancessociales;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import ch.ge.afc.calcul.assurancessociales.ge.CalculCotisationsSocialesSalarieGE;

/**
 * Cette classe a la responsabilité de fournir les calculateurs de cotisations sociales.
 * 
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class Fournisseur implements FournisseurRegleCalculAssuranceSociale {
	
	/**************************************************/
	/****************** Attributs *********************/
	/**************************************************/

	private ConcurrentMap<Integer,CalculCotisationsSocialesSalarie> mapCalculateurCotisationAvsAiApgSalarie = new ConcurrentHashMap<Integer,CalculCotisationsSocialesSalarie>();
	private ConcurrentMap<Integer,CalculCotisationsSocialesSalarieGE> mapCalculateurCotisationAvsAiApgSalarieGE = new ConcurrentHashMap<Integer,CalculCotisationsSocialesSalarieGE>();
	
	private ConcurrentMap<Integer,CalculCotisationAvsAiApg> mapCalculateurCotisationAvsAiApgIndependant = new ConcurrentHashMap<Integer,CalculCotisationAvsAiApg>();
	
	private ConcurrentMap<Integer,CalculExtremaRentesAVS> mapCalculateurRentesAVS = new ConcurrentHashMap<Integer,CalculExtremaRentesAVS>();
	
	private Map<Integer,BigDecimal> renteSimpleMensuelleMinimumParAnnee;
	
    /**************************************************/
    /********** Accesseurs / Mutateurs ****************/
    /**************************************************/

	public void setRenteSimpleMensuelleMinimumParAnnee(Map<Integer,BigDecimal> rentes) {
		this.renteSimpleMensuelleMinimumParAnnee = rentes;
	}
	
    /**************************************************/
    /******************* Méthodes *********************/
    /**************************************************/

	//--------------- Salariés --------------------
	
	protected CalculCotisationsSocialesSalarie construireCalculateurSalarie(int annee) {
		CalculCotisationsSocialesSalarie.Constructeur constructeur = new CalculCotisationsSocialesSalarie.Constructeur();
		constructeur.tauxAvs("8.4 %").tauxAi("1.4 %").tauxApg("0.3 %")
			.tauxAC("2 %");
		
		// Décision du 27 juin 2007 du conseil fédéral (RO 2007 3667 Annexe 1)
		// la maximum passe de 106800 à 126000
		if (annee < 2008) constructeur.montantAnnuelMaxGainAssure(106800);
		else constructeur.montantAnnuelMaxGainAssure(126000);
		return constructeur.construire(annee);
	}
	
	public CalculCotisationsSocialesSalarie getCalculateurCotisationsSocialesSalarie(int annee) {
		if (!mapCalculateurCotisationAvsAiApgSalarie.containsKey(annee)) mapCalculateurCotisationAvsAiApgSalarie.putIfAbsent(annee, construireCalculateurSalarie(annee));
		return mapCalculateurCotisationAvsAiApgSalarie.get(annee);
	}

	protected CalculCotisationsSocialesSalarieGE construireCalculateurSalarieGE(int annee) {
		
		CalculCotisationsSocialesSalarieGE calculateur = null;
		if (annee < 2010) {
			calculateur = new CalculCotisationsSocialesSalarieGE(annee,"0.02 %",getCalculateurCotisationsSocialesSalarie(annee));
		} else {
			calculateur = new CalculCotisationsSocialesSalarieGE(annee,"0.045 %",getCalculateurCotisationsSocialesSalarie(annee));
		}
		return calculateur;
	}
	
	public CalculCotisationsSocialesSalarieGE getCalculateurCotisationsSocialesSalarieGE(int annee) {
		if (!mapCalculateurCotisationAvsAiApgSalarieGE.containsKey(annee)) mapCalculateurCotisationAvsAiApgSalarieGE.putIfAbsent(annee, construireCalculateurSalarieGE(annee));
		return mapCalculateurCotisationAvsAiApgSalarieGE.get(annee);
	}
	
	//--------------- Indépendants --------------------

	protected CalculCotisationAvsAiApg construireCalculateurCotisationAvsAiApgIndependant(int annee) {
		CalculCotisationAvsAiApgIndependant.Constructeur constructeur = new CalculCotisationAvsAiApgIndependant.Constructeur();
		constructeur.tauxAvs("7.8 %").tauxAi("1.4 %").tauxApg("0.3 %");
		if (annee <= 2008) {
			constructeur.cotisationAvsAiApgMinimum("445");
			constructeur.trancheBareme( "8900", "4.2 %");
			constructeur.trancheBareme("15900", "4.2 %");
			constructeur.trancheBareme("20100", "4.3 %");
			constructeur.trancheBareme("22300", "4.4 %");
			constructeur.trancheBareme("24500", "4.5 %");
			constructeur.trancheBareme("26700", "4.6 %");
			constructeur.trancheBareme("28900", "4.7 %");
			constructeur.trancheBareme("31100", "4.9 %");
			constructeur.trancheBareme("33300", "5.1 %");
			constructeur.trancheBareme("35500", "5.3 %");
			constructeur.trancheBareme("37700", "5.5 %");
			constructeur.trancheBareme("39900", "5.7 %");
			constructeur.trancheBareme("42100", "5.9 %");
			constructeur.trancheBareme("44300", "6.2 %");
			constructeur.trancheBareme("46500", "6.5 %");
			constructeur.trancheBareme("48700", "6.8 %");
			constructeur.trancheBareme("50900", "7.1 %");
			constructeur.trancheBareme("53100", "7.4 %");
			return constructeur.construire(annee);
			
		} else {
			constructeur.cotisationAvsAiApgMinimum("460");
			constructeur.trancheBareme( "9200", "4.2 %");
			constructeur.trancheBareme("16000", "4.2 %");
			constructeur.trancheBareme("20300", "4.3 %");
			constructeur.trancheBareme("22600", "4.4 %");
			constructeur.trancheBareme("24900", "4.5 %");
			constructeur.trancheBareme("27200", "4.6 %");
			constructeur.trancheBareme("29500", "4.7 %");
			constructeur.trancheBareme("31800", "4.9 %");
			constructeur.trancheBareme("34100", "5.1 %");
			constructeur.trancheBareme("36400", "5.3 %");
			constructeur.trancheBareme("38700", "5.5 %");
			constructeur.trancheBareme("41000", "5.7 %");
			constructeur.trancheBareme("43300", "5.9 %");
			constructeur.trancheBareme("45600", "6.2 %");
			constructeur.trancheBareme("47900", "6.5 %");
			constructeur.trancheBareme("50200", "6.8 %");
			constructeur.trancheBareme("52500", "7.1 %");
			constructeur.trancheBareme("54800", "7.4 %");
			return constructeur.construire(annee);
		}
	}
	
	
	public CalculCotisationAvsAiApg getCalculateurCotisationAvsAiApgIndependant(int annee) {
		if (!mapCalculateurCotisationAvsAiApgIndependant.containsKey(annee)) mapCalculateurCotisationAvsAiApgIndependant.putIfAbsent(annee, construireCalculateurCotisationAvsAiApgIndependant(annee));
		return mapCalculateurCotisationAvsAiApgIndependant.get(annee);
	}
	
	// --------- Calculateur des rentes minimales ou maximales
	
	public CalculExtremaRentesAVS getCalculateurExtremaRenteAVS(int annee) {
		if (!mapCalculateurRentesAVS.containsKey(annee)) mapCalculateurRentesAVS.putIfAbsent(annee, construireCalculateurExtremaRenteAVS(annee));
		return mapCalculateurRentesAVS.get(annee);
	}

	private CalculExtremaRentesAVS construireCalculateurExtremaRenteAVS(int annee) {
		CalculExtremaRentesAVS calculateur = new CalculExtremaRentesAVS(annee);
		calculateur.setRenteSimpleMensuelleMinimum(renteSimpleMensuelleMinimumParAnnee.get(annee));
		return calculateur;
	}
}
