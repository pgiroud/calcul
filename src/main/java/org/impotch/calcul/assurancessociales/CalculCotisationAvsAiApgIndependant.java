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
package org.impotch.calcul.assurancessociales;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.impotch.bareme.*;
import org.impotch.calcul.ReglePeriodique;
import org.impotch.util.TypeArrondi;

import static java.math.RoundingMode.HALF_UP;
import static org.impotch.bareme.ConstructeurBareme.unBareme;
import static org.impotch.bareme.ConstructeurBareme.unBaremeATauxEffectif;
import static org.impotch.util.BigDecimalUtil.parse;
/**
 * Cette classe permet le calcul du montant des cotisations des assurances
 * sociales suisses suivantes pour les indépendants :
 * <ul>
 * <li>l'assurance vieillesse et survivant AVS ;</li>
 * <li>l'assurance invalidité AI ;</li>
 * <li>l'assurance contre les pertes de gains APG.</li>
 * </ul>
 * En général, les caisses de compensations retiennent des frais de gestion (un
 * pourcentage environ égal à 2,5 % des cotisations). Ces frais ne sont pas
 * compris dans les montants calculés et devront donc être ajoutés aux montants
 * ci-dessous.
 * 
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 */
class CalculCotisationAvsAiApgIndependant extends ReglePeriodique
		implements CalculCotisationAvsAiApg {

	/**************************************************/
	/****************** Attributs *********************/
	/**************************************************/

	private Bareme baremeAVS;
	private Bareme baremeAI;
	private Bareme baremeAPG;
	private Bareme baremeAvsAiApg;

    /**************************************************/
    /***************** Constructeurs ******************/
    /**************************************************/
			
	/**
	 * Construit un calculateur pour les indépendants.
	 * @param iniAnnee l'année de validité
	 */
	private CalculCotisationAvsAiApgIndependant(int iniAnnee) {

		super(iniAnnee);

	}

    /**************************************************/
    /******* Accesseurs / Mutateurs *******************/
    /**************************************************/
	
	void setBaremeAVS(Bareme baremeAVS) {
		this.baremeAVS = baremeAVS;
	}

	void setBaremeAI(Bareme baremeAI) {
		this.baremeAI = baremeAI;
	}

	void setBaremeAPG(Bareme baremeAPG) {
		this.baremeAPG = baremeAPG;
	}

	void construireBaremeAvsAiApg() {
		assert null != baremeAVS;
		assert null != baremeAI;
		assert null != baremeAPG;
		this.baremeAvsAiApg = SommeBareme.somme(baremeAVS, baremeAI, baremeAPG);
	}

	/**************************************************/
    /******************* Méthodes *********************/
    /**************************************************/

	@Override
	public BigDecimal calculCotisationAvsAiApg(BigDecimal montantDeterminant, TypeArrondi arrondi) {
		return arrondi.arrondir(baremeAvsAiApg.calcul(montantDeterminant));
	}

	@Override
	public BigDecimal calculCotisationAvs(BigDecimal montantDeterminant, TypeArrondi arrondi) {
		return arrondi.arrondir(baremeAVS.calcul(montantDeterminant));
	}

	@Override
	public BigDecimal calculCotisationAi(BigDecimal montantDeterminant, TypeArrondi arrondi) {
		return arrondi.arrondir(baremeAI.calcul(montantDeterminant));
	}

	@Override
	public BigDecimal calculCotisationApg(BigDecimal montantDeterminant, TypeArrondi arrondi) {
		return arrondi.arrondir(baremeAPG.calcul(montantDeterminant));
	}

/**************************************************/
    /************** Classes internes ******************/
    /**************************************************/

	private static class SommeBareme implements Bareme {

		public static Bareme somme(Bareme... baremes) {
			return new SommeBareme(baremes);
		}

		private List<Bareme> baremes;

		public SommeBareme(Bareme... baremes) {
			this.baremes = Arrays.asList(baremes);
		}

		@Override
		public BigDecimal calcul(BigDecimal assiette) {
			return baremes.stream().map(b -> b.calcul(assiette)).reduce(BigDecimal.ZERO,BigDecimal::add);
		}

	}


	public static class Constructeur {

		private BigDecimal limiteInfArt8alinea1LAVS;
		private BigDecimal minimumAvs;
		private BigDecimal tauxMaxAvs;
		private ConstructeurBareme consBaremeTxEffectifAVS = unBaremeATauxEffectif().fermeAGauche();

		private BigDecimal minimumAi;
		private BigDecimal tauxAi;

		private BigDecimal minimumApg;
		private BigDecimal tauxApg;

		private boolean flagsAVS;
		private boolean flagsAI;
		private boolean flagsAPG;

		private void resetFlag() {
			flagsAVS = false;
			flagsAI = false;
			flagsAPG = false;
		}

		public Constructeur avs() {
			resetFlag();
			flagsAVS = true;
			return this;
		}

		public Constructeur ai() {
			resetFlag();
			flagsAI = true;
			return this;
		}

		public Constructeur apg() {
			resetFlag();
			flagsAPG = true;
			return this;
		}


		public Constructeur cotisationMinimale(int montant) {
			BigDecimal mnt = BigDecimal.valueOf(montant);
			if (flagsAVS) minimumAvs = mnt;
			else if (flagsAI) minimumAi = mnt;
			else if (flagsAPG) minimumApg = mnt;
			else throw new IllegalStateException("Vous ne pouvez pas appeler un minimum sans préciser de quelle assurance il s’agit !");
			return this;
		}

		public Constructeur taux(String taux) {
			BigDecimal tx = parse(taux);
			if (flagsAVS) {
				consBaremeTxEffectifAVS.taux(taux);
				tauxMaxAvs = tx; // ici, on ne s'embarasse pas et on fait l’hypothèse que le barème est construit dans l’ordre !
			}
			else if (flagsAI) tauxAi = tx;
			else if (flagsAPG) tauxApg = tx;
			else throw new IllegalStateException("Vous ne pouvez pas appeler un taux sans préciser de quelle assurance (ai ou apg) il s’agit !");
			return this;
		}

		
		public Constructeur jusqua(int revenuDeterminant) {
			limiteInfArt8alinea1LAVS = BigDecimal.valueOf(revenuDeterminant);
			consBaremeTxEffectifAVS.jusqua(revenuDeterminant);
			return this;
		}

		public Constructeur puisJusqua(int revenuDeterminant) {
			consBaremeTxEffectifAVS.puisJusqua(revenuDeterminant);
			return this;
		}

		public Constructeur puis() {
			consBaremeTxEffectifAVS.puis();
			return this;
		}

		private Bareme metALechellePuisCompose(BaremeParTranche baremeAvs, BigDecimal taux, BigDecimal minimum) {
			BigDecimal rapport = taux.divide(tauxMaxAvs, 5, HALF_UP);
			Bareme baremeMisALEchelle = baremeAvs.homothetieValeur(rapport,TypeArrondi.CENT_MILLIEME_LE_PLUS_PROCHE);

			ConstructeurBareme consBaremeMinimum =
					unBareme().fermeAGauche().jusqua(limiteInfArt8alinea1LAVS).valeur(minimum).puis().valeur(0);;

			return SommeBareme.somme(consBaremeMinimum.construire(), baremeMisALEchelle);
		}


		public CalculCotisationAvsAiApgIndependant construire(int annee) {
			CalculCotisationAvsAiApgIndependant calculateur = new CalculCotisationAvsAiApgIndependant(annee);
			Bareme baremeMinimumAVS = unBareme().fermeAGauche()
					.jusqua(limiteInfArt8alinea1LAVS).valeur(minimumAvs).puis().valeur(0).construire();

			BaremeParTranche baremeAVSProgressif = consBaremeTxEffectifAVS.construire();
			Bareme baremeAVSCompose = SommeBareme.somme(baremeMinimumAVS, baremeAVSProgressif);
			calculateur.setBaremeAVS(baremeAVSCompose);

			calculateur.setBaremeAI(metALechellePuisCompose(baremeAVSProgressif,tauxAi,minimumAi));
			calculateur.setBaremeAPG(metALechellePuisCompose(baremeAVSProgressif,tauxApg,minimumApg));

			calculateur.construireBaremeAvsAiApg();

			return calculateur;
		}
	}
}
