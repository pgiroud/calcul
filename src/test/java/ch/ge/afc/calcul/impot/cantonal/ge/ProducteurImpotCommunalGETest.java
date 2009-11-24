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
package ch.ge.afc.calcul.impot.cantonal.ge;


import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.ge.afc.calcul.impot.FournisseurAssietteCommunale;
import ch.ge.afc.calcul.impot.Impot;
import ch.ge.afc.calcul.impot.RecepteurImpot;
import ch.ge.afc.calcul.impot.cantonal.ge.ProducteurImpotCommunalGE;
import ch.ge.afc.calcul.impot.cantonal.ge.param.FournisseurParametrageCommunaleGE;
import ch.ge.afc.calcul.impot.cantonal.ge.pp.ProducteurImpotCommunalGEPersPhysique;
import ch.ge.afc.calcul.impot.taxation.forimposition.ForCommunal;
import ch.ge.afc.calcul.impot.taxation.repart.Part;
import ch.ge.afc.calcul.impot.taxation.repart.Repartition;
import ch.ge.afc.calcul.lieu.FournisseurLieu;
import ch.ge.afc.calcul.lieu.ICommuneSuisse;
import ch.ge.afc.util.IExplicationDetailleeBuilder;
import ch.ge.afc.util.MockExplicationDetailleeBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/beans.xml")
public class ProducteurImpotCommunalGETest {

	private FournisseurLieu fournisseurLieu;

	@Resource(name = "fournisseurParamCommune")
	private FournisseurParametrageCommunaleGE fournisseur;
	
	
	@Before
	public void init() {
		fournisseurLieu = new FournisseurLieu();
	}
	
	@Test
	public void troisCommune() {
		FournisseurAssietteCommunale fournisseurAssiette = new FournisseurAssietteCommunale() {
			@Override
			public Map<ICommuneSuisse, Integer> getNbreJourDomicileSurCommune() {
				Map<ICommuneSuisse, Integer> map = new HashMap<ICommuneSuisse, Integer>();
				map.put(fournisseurLieu.getCommuneGeneve(), 360);
				return map;
			}
			@Override
			public int getPeriodeFiscale() {
				return 2007;
			}
			@Override
			public Repartition<ForCommunal> getRepartition() {
				Repartition<ForCommunal> repart = new Repartition<ForCommunal>();
				repart.ajouterPart(new ForCommunal(fournisseurLieu.getCommuneAireLaVille()), new Part(new BigDecimal(50301)));
				repart.ajouterPart(new ForCommunal(fournisseurLieu.getCommuneCarouge()), new Part(new BigDecimal(73343)));
				return repart;
			}
			
		};
		
		ProducteurImpotCommunalGE prod = new ProducteurImpotCommunalGEPersPhysique("PP","CTS") {
			@Override
			protected IExplicationDetailleeBuilder createExplicationBuilder() {
				return new MockExplicationDetailleeBuilder();
			}
			
		};
		prod.setFournisseurParametrage(fournisseur);
		
		RecepteurImpotsCommunaux recepteur = new RecepteurImpotsCommunaux();
		prod.produireImpot(new BigDecimal("15497.80"), fournisseurAssiette, recepteur);
		
		assertEquals("Part privilégiée",new BigDecimal("1833.40"),recepteur.getImpotPartPrivilegiee());
		assertEquals("Cts Add Aire-La-Ville",new BigDecimal("2332.80"),recepteur.getCtsAdd(String.valueOf(fournisseurLieu.getCommuneAireLaVille().getNumeroOFS())));
		assertEquals("Cts Add Carouge",new BigDecimal("2653.10"),recepteur.getCtsAdd(String.valueOf(fournisseurLieu.getCommuneCarouge().getNumeroOFS())));
	}
	
	@Test
	public void troisCommuneBis() {
		FournisseurAssietteCommunale fournisseurAssiette = new FournisseurAssietteCommunale() {
			@Override
			public Map<ICommuneSuisse, Integer> getNbreJourDomicileSurCommune() {
				Map<ICommuneSuisse, Integer> map = new HashMap<ICommuneSuisse, Integer>();
				map.put(fournisseurLieu.getCommuneCheneBougerie(), 360);
				return map;
			}
			@Override
			public int getPeriodeFiscale() {
				return 2009;
			}
			@Override
			public Repartition<ForCommunal> getRepartition() {
				Repartition<ForCommunal> repart = new Repartition<ForCommunal>();
				repart.ajouterPart(new ForCommunal(fournisseurLieu.getCommuneCheneBougerie()), new Part(new BigDecimal(30)));
				repart.ajouterPart(new ForCommunal(fournisseurLieu.getCommunePresinge()), new Part(new BigDecimal(20)));
				repart.ajouterPart(new ForCommunal(fournisseurLieu.getCommuneMeyrin()), new Part(new BigDecimal(50)));
				return repart;
			}
			
		};
		
		ProducteurImpotCommunalGE prod = new ProducteurImpotCommunalGEPersPhysique("PP","CTS") {
			@Override
			protected IExplicationDetailleeBuilder createExplicationBuilder() {
				return new MockExplicationDetailleeBuilder();
			}
			
		};
		prod.setFournisseurParametrage(fournisseur);
		
		RecepteurImpotsCommunaux recepteur = new RecepteurImpotsCommunaux();
		prod.produireImpot(new BigDecimal("8843.80"), fournisseurAssiette, recepteur);
		
		assertEquals("Part privilégiée",new BigDecimal("2387.85"),recepteur.getPartPrivilegiee());
		assertEquals("Part Chêne-Bougerie",new BigDecimal("1936.80"),recepteur.getPart(String.valueOf(fournisseurLieu.getCommuneCheneBougerie().getNumeroOFS())));
		assertEquals("Part Presinge",new BigDecimal("1291.20"),recepteur.getPart(String.valueOf(fournisseurLieu.getCommunePresinge().getNumeroOFS())));
		assertEquals("Part Meyrin",new BigDecimal("3227.95"),recepteur.getPart(String.valueOf(fournisseurLieu.getCommuneMeyrin().getNumeroOFS())));
		
		assertEquals("Impôt part privilégiée",new BigDecimal("811.85"),recepteur.getImpotPartPrivilegiee());
		assertEquals("Cts Add Chêne-Bougerie",new BigDecimal("658.50"),recepteur.getCtsAdd(String.valueOf(fournisseurLieu.getCommuneCheneBougerie().getNumeroOFS())));
		assertEquals("Cts Add Presinge",new BigDecimal("503.55"),recepteur.getCtsAdd(String.valueOf(fournisseurLieu.getCommunePresinge().getNumeroOFS())));
		assertEquals("Cts Add Meyrin",new BigDecimal("1452.60"),recepteur.getCtsAdd(String.valueOf(fournisseurLieu.getCommuneMeyrin().getNumeroOFS())));
	}
	
	private class RecepteurImpotsCommunaux implements RecepteurImpot {
		private BigDecimal partPrivilegiee;
		private BigDecimal impotPartPrivilegiee;
		private Map<String,BigDecimal> partRepart = new HashMap<String,BigDecimal>();
		private Map<String,BigDecimal> ctsadd = new HashMap<String,BigDecimal>();
		
		/* (non-Javadoc)
		 * @see ch.ge.afc.calcul.impot.RecepteurImpot#ajouteImpot(java.math.BigDecimal, java.lang.String, java.lang.String)
		 */
		@Override
		public void ajouteImpot(Impot impot) {
			if ("PP".equals(impot.getTypeImpot())) {
				partPrivilegiee = impot.getBaseCalcul();
				impotPartPrivilegiee = impot.getMontant();
			} else {
				partRepart.put(impot.getCodeBeneficiaire(), impot.getBaseCalcul());
				ctsadd.put(impot.getCodeBeneficiaire(), impot.getMontant());
			}
		}
		
		public BigDecimal getPartPrivilegiee() {
			return partPrivilegiee;
		}
		
		public BigDecimal getImpotPartPrivilegiee() {
			return impotPartPrivilegiee;
		}
		
		public BigDecimal getPart(String codeBeneficiaire) {
			return partRepart.get(codeBeneficiaire);
		}
		
		public BigDecimal getCtsAdd(String codeBeneficiaire) {
			return ctsadd.get(codeBeneficiaire);
		}
	}
 	
}
