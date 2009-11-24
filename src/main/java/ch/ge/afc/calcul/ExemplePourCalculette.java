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
package ch.ge.afc.calcul;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import ch.ge.afc.calcul.impot.FournisseurAssietteCommunale;
import ch.ge.afc.calcul.impot.RecepteurImpot;
import ch.ge.afc.calcul.impot.Souverainete;
import ch.ge.afc.calcul.impot.cantonal.ge.pp.FournisseurRegleImpotCantonalGE;
import ch.ge.afc.calcul.impot.cantonal.ge.pp.avant2010.SituationFamilialeGE;
import ch.ge.afc.calcul.impot.taxation.forimposition.ForCommunal;
import ch.ge.afc.calcul.impot.taxation.pp.EnfantACharge;
import ch.ge.afc.calcul.impot.taxation.pp.FournisseurAssiettePeriodique;
import ch.ge.afc.calcul.impot.taxation.pp.PersonneACharge;
import ch.ge.afc.calcul.impot.taxation.pp.ProducteurImpot;
import ch.ge.afc.calcul.impot.taxation.pp.RecepteurImpotSomme;
import ch.ge.afc.calcul.impot.taxation.repart.Part;
import ch.ge.afc.calcul.impot.taxation.repart.Repartition;
import ch.ge.afc.calcul.lieu.ICanton;
import ch.ge.afc.calcul.lieu.ICommuneSuisse;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class ExemplePourCalculette {

	private static int annee;
	private static SituationFamilialeGE situation;
	private static FournisseurAssiettePeriodique assietteRevenu;
	private static FournisseurAssiettePeriodique assietteFortune;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClassPathXmlApplicationContext resources = new ClassPathXmlApplicationContext("beans.xml");
		FournisseurRegleImpotCantonalGE fournisseur = (FournisseurRegleImpotCantonalGE)resources.getBean("fournisseurRegleImpotCantonalGE");
		annee = 2010;
		renseignerSituation();
		renseignerAssietteRevenu();
		renseignerAssietteFortune();
		
		RecepteurImpotSomme recepteur = null;
		long temps = 0;
		int taille = 100;
		for (int i= 0; i < taille; i++) {
			recepteur = new RecepteurImpotSomme();
			Date dateDebut = new Date();
			produireImpots(fournisseur.getProducteurImpotsICCRevenu(annee),assietteRevenu,recepteur);
			BigDecimal impotRevenu = recepteur.getValeur();
			System.out.println("Impôt revenu : " + impotRevenu);
			produireImpots(fournisseur.getProducteurImpotsICCFortune(annee),assietteFortune,recepteur);
			BigDecimal impotFortune = recepteur.getValeur().subtract(impotRevenu);
			System.out.println("Impôt fortune : " + impotFortune);
			produireImpots(fournisseur.getProducteurImpotsICCFortuneSupplementaire(annee),assietteFortune,recepteur);
			BigDecimal impotFortuneSupplementaire = recepteur.getValeur().subtract(impotRevenu).subtract(impotFortune);
			System.out.println("Impôt fortune supplémentaire : " + impotFortuneSupplementaire);
			Date dateFin = new Date();
			temps += dateFin.getTime() - dateDebut.getTime();
		}
		System.out.println("Temps total : " + temps + " ms");
		double tempsDbl = (double)temps / (double)taille;
		System.out.println("Impôt total : " + recepteur.getValeur() + " calculé en " + tempsDbl + " ms");
		
	}

	private static void renseignerSituation() {
		situation = new SituationFamilialeGE() {
			public boolean isConjointFonctionnaireInternational() {return false;}
			public boolean isCoupleDomicilieGeneve() {return true;}
			public Set<EnfantACharge> getEnfants() {
				// 1 enfant à charge
				EnfantACharge enfant = new EnfantACharge() {
					public boolean isDemiPart(Souverainete souverainete) {return false;}
					public int getAge(int anneeFiscale) {return 6;}
				};
				return Collections.singleton(enfant);
			}
			public Set<PersonneACharge> getPersonnesNecessiteuses() {return Collections.emptySet();}
			public boolean isCouple() {return true;}
		};
	}

	private static ICanton getCantonGeneve() {
		ICanton canton = new ICanton() {
			public String getNom() {return "Canton de Genève";}
			public int getNumeroOFS() {return 9025;}
			public String getCodeIso2() {return "GE";}
		};
		return canton;
	}
	
	private static ICommuneSuisse getCommuneCarouge() {
		ICommuneSuisse commune = new ICommuneSuisse(){
			public ICanton getCanton() {return getCantonGeneve();}
			public String getNom() {return "Carouge";}
			public int getNumeroOFS() {return 6608;}
		};
		return commune;
	}
	
	
	private static FournisseurAssietteCommunale getAssietteCommunale() {
		FournisseurAssietteCommunale assiette = new FournisseurAssietteCommunale() {
			public Map<ICommuneSuisse, Integer> getNbreJourDomicileSurCommune() {
				Map<ICommuneSuisse, Integer> map = new HashMap<ICommuneSuisse, Integer>();
				map.put(getCommuneCarouge(), 360);
				return map;
			}
			public int getPeriodeFiscale() {return annee;}
			public Repartition<ForCommunal> getRepartition() {
				Repartition<ForCommunal> repart = new Repartition<ForCommunal>();
				repart.ajouterPart(new ForCommunal(getCommuneCarouge()), new Part(BigDecimal.ONE));
				return repart;
			}
		};
		return assiette;
	}
	
	
	private static void renseignerAssietteRevenu() {
		// Attention, si on a choisit une annee < 2010, il faut utiliser un FournisseurAssiettePeriodiqueGE
		assietteRevenu = new FournisseurAssiettePeriodique(){
			public BigDecimal getMontantDeterminant() {
				// Tout localisé dans le canton GE et assujettissement sur une année
				return getMontantImposable();
			}
			public BigDecimal getMontantImposable() {return new BigDecimal(100000);}
			public FournisseurAssietteCommunale getFournisseurAssietteCommunale() {return getAssietteCommunale();}
			public int getNombreJourPourAnnualisation() {return 360;}
			public int getPeriodeFiscale() {return annee;}
		};
		
	}

	private static void renseignerAssietteFortune() {
		assietteFortune = new FournisseurAssiettePeriodique(){
			public BigDecimal getMontantDeterminant() {
				// Tout localisé dans le canton GE et assujettissement sur une année
				return getMontantImposable();
			}
			public BigDecimal getMontantImposable() {return new BigDecimal(1000000);}
			public FournisseurAssietteCommunale getFournisseurAssietteCommunale() {return getAssietteCommunale();}
			public int getNombreJourPourAnnualisation() {return 360;}
			public int getPeriodeFiscale() {return annee;}
		};
	}
	
	
	private static void produireImpots(ProducteurImpot producteur, FournisseurAssiettePeriodique assiette, RecepteurImpot recepteur) {
		producteur.produireImpot(situation, assiette, recepteur);
	}
	
}
