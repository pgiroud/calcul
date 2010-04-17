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
package ch.ge.afc.calcul.impot.cantonal.fr.pp;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import ch.ge.afc.bareme.Bareme;
import ch.ge.afc.bareme.BaremeTauxEffectifConstantParTranche;
import ch.ge.afc.calcul.impot.taxation.pp.ProducteurImpotBaseProgressif;
import ch.ge.afc.calcul.impot.cantonal.FournisseurCantonal;
import ch.ge.afc.calcul.impot.taxation.pp.ProducteurImpot;
import ch.ge.afc.calcul.impot.taxation.pp.famille.Splitting;
import ch.ge.afc.util.ExplicationDetailleTexteBuilder;
import ch.ge.afc.util.IExplicationDetailleeBuilder;
import ch.ge.afc.util.TypeArrondi;

public class FournisseurCantonalFR extends FournisseurCantonal implements FournisseurRegleImpotCantonalFR {

	private ConcurrentMap<Integer, ProducteurImpot> producteurImpotsICRevenu = new ConcurrentHashMap<Integer, ProducteurImpot>();
	private ConcurrentMap<Integer, ProducteurImpot> producteurImpotsICFortune = new ConcurrentHashMap<Integer, ProducteurImpot>();

	/* (non-Javadoc)
	 * @see ch.ge.afc.calcul.impot.cantonal.FournisseurCantonal#construireBaremeRevenu(int)
	 */
	@Override
	protected Bareme construireBaremeRevenu(int annee) {
		BaremeTauxEffectifLineaireParTranche bareme = new BaremeTauxEffectifLineaireParTranche();
		if (annee > 2005 && annee < 2009) {
			bareme.ajouterTranche(			  4900,		 "0",			"0");
			bareme.ajouterTranche(			 16600,		 "1 %",			"0.0272 %");
			bareme.ajouterTranche(			 29800, 	 "4.1707 %", 	"0.0155 %");
			bareme.ajouterTranche(			 45800, 	 "6.2127 %", 	"0.0115 %");
			bareme.ajouterTranche(			 60500, 	 "8.0484 %", 	"0.0072 %");
			bareme.ajouterTranche(			 74100, 	 "9.1063 %", 	"0.0067 %");
			bareme.ajouterTranche(			142700, 	"10.0144 %", 	"0.0036 %");
			bareme.ajouterTranche(			169900, 	"12.4826 %", 	"0.0022 %");
			bareme.ajouterTranche(			196200, 	"13.0804 %",	"0.0016 %");
			bareme.ajouterDerniereTranche("13.5 %");
		} else {
			bareme.ajouterTranche(			  5100,		 "0",			"0");
			bareme.ajouterTranche(			 17300,		 "1 %",			"0.0261 %");
			bareme.ajouterTranche(			 31000, 	 "4.1730 %", 	"0.0149 %");
			bareme.ajouterTranche(			 47700, 	 "6.2104 %", 	"0.0110 %");
			bareme.ajouterTranche(			 63000, 	 "8.0433 %", 	"0.0069 %");
			bareme.ajouterTranche(			 76700, 	 "9.0986 %", 	"0.0065 %");
			bareme.ajouterTranche(			100900, 	 "9.9862 %", 	"0.0036 %");
			bareme.ajouterTranche(			127200, 	"10.8571 %", 	"0.0033 %");
			bareme.ajouterTranche(			154200, 	"11.7247 %", 	"0.0030 %");
			bareme.ajouterTranche(			178900, 	"12.5340 %", 	"0.0023 %");
			bareme.ajouterTranche(			203900, 	"13.1014 %",	"0.0016 %");
			bareme.ajouterDerniereTranche("13.5 %");
		}
		bareme.setTypeArrondi(TypeArrondi.CINQ_CTS);
		return bareme;
	}

	protected ProducteurImpot construireProducteurImpotsICRevenu(int annee) {
		ProducteurImpotBaseProgressif producteurImpotBase = new ProducteurImpotBaseProgressif();
		Splitting splitting = new SplittingFR((BaremeTauxEffectifLineaireParTranche)getBaremeRevenu(annee),"56 %");
		splitting.setTypeArrondi(TypeArrondi.CENT_FRANC_INF);
		producteurImpotBase.setStrategieProductionImpotFamille(splitting);

		producteurImpotBase.setTypeArrondiImposable(TypeArrondi.CENT_FRANC_INF);
		producteurImpotBase.setTypeArrondiDeterminant(TypeArrondi.CENT_FRANC_INF);
		producteurImpotBase.setTypeArrondiImpot(TypeArrondi.CINQ_CTS);
		
		
		String codeBeneficiaire = "CAN-FR";
		ProducteurImpot producteur = new ProducteurImpot("RCAN",codeBeneficiaire){
			@Override
			protected IExplicationDetailleeBuilder createExplicationBuilder() {
				return FournisseurCantonalFR.this.getNewExplicationBuilder();
			}
		};
		producteur.setProducteurBase(producteurImpotBase);
		return producteur;
	}
	
	@Override
	public ProducteurImpot getProducteurImpotsICRevenu(int annee) {
		if (!producteurImpotsICRevenu.containsKey(annee))
			producteurImpotsICRevenu.putIfAbsent(annee,
					construireProducteurImpotsICRevenu(annee));
		return producteurImpotsICRevenu.get(annee);
	}
	

	
	/* (non-Javadoc)
	 * @see ch.ge.afc.calcul.impot.cantonal.FournisseurCantonal#construireBaremeFortune(int)
	 */
	@Override
	protected Bareme construireBaremeFortune(int annee) {
		BaremeTauxEffectifConstantParTranche bareme = new BaremeTauxEffectifConstantParTranche();
		bareme.setMontantMaxNonInclus();
		if (annee < 2009) {
			// valable de 2006 (au moins) à 2008 inclus
			bareme.ajouterTranche(15000, "0");
			bareme.ajouterTranche(20100, "1 ‰");
			bareme.ajouterTranche(30100, "1.25 ‰");
			bareme.ajouterTranche(50100, "1.50 ‰");
			bareme.ajouterTranche(75100, "1.75 ‰");
			bareme.ajouterTranche(100100, "2.00 ‰");
			bareme.ajouterTranche(150100, "2.25 ‰");
			bareme.ajouterTranche(200100, "2.50 ‰");
			bareme.ajouterTranche(300100, "2.60 ‰");
			bareme.ajouterTranche(400100, "2.70 ‰");
			bareme.ajouterTranche(500100, "2.80 ‰");
			bareme.ajouterTranche(600100, "3.00 ‰");
			bareme.ajouterTranche(700100, "3.10 ‰");
			bareme.ajouterTranche(800100, "3.20 ‰");
			bareme.ajouterTranche(900100, "3.30 ‰");
			bareme.ajouterTranche(1000100, "3.40 ‰");
			bareme.ajouterDerniereTranche("3.50 ‰");
		} else {
			// Il s'agit des barèmes 2009
			bareme.ajouterTranche(20000, "0");
			bareme.ajouterTranche(25100, "1 ‰");
			bareme.ajouterTranche(35100, "1.25 ‰");
			bareme.ajouterTranche(55100, "1.50 ‰");
			bareme.ajouterTranche(85100, "1.75 ‰");
			bareme.ajouterTranche(125100, "2.00 ‰");
			bareme.ajouterTranche(175100, "2.25 ‰");
			bareme.ajouterTranche(225100, "2.50 ‰");
			bareme.ajouterTranche(325100, "2.60 ‰");
			bareme.ajouterTranche(450100, "2.70 ‰");
			bareme.ajouterTranche(550100, "2.80 ‰");
			bareme.ajouterTranche(650100, "3.00 ‰");
			bareme.ajouterTranche(775100, "3.10 ‰");
			bareme.ajouterTranche(875100, "3.20 ‰");
			bareme.ajouterTranche(975100, "3.30 ‰");
			bareme.ajouterTranche(1100100, "3.40 ‰");
			bareme.ajouterDerniereTranche("3.50 ‰");
		}
		bareme.setTypeArrondi(TypeArrondi.CINQ_CTS);
		return bareme;
	}

	protected ProducteurImpot construireProducteurImpotsICFortune(int annee) {
		// TODO PGI
		return null;
	}
	
	@Override
	public ProducteurImpot getProducteurImpotsICFortune(int annee) {
		if (!producteurImpotsICFortune.containsKey(annee))
			producteurImpotsICFortune.putIfAbsent(annee,
					construireProducteurImpotsICFortune(annee));
		return producteurImpotsICFortune.get(annee);
	}

	
	private IExplicationDetailleeBuilder getNewExplicationBuilder() {
		return new ExplicationDetailleTexteBuilder();
	}

	

}
