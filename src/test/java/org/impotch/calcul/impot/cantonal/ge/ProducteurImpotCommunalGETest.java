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


import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.impotch.calcul.impot.Impot;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.impotch.calcul.impot.FournisseurAssietteCommunale;
import org.impotch.calcul.impot.RecepteurImpot;
import org.impotch.calcul.impot.cantonal.ge.param.FournisseurParametrageCommunaleGE;
import org.impotch.calcul.impot.cantonal.ge.pp.ProducteurImpotCommunalGEPersPhysique;
import org.impotch.calcul.impot.taxation.forimposition.ForCommunal;
import org.impotch.calcul.impot.taxation.repart.Part;
import org.impotch.calcul.impot.taxation.repart.Repartition;
import org.impotch.calcul.lieu.FournisseurLieu;
import org.impotch.calcul.lieu.ICommuneSuisse;
import org.impotch.calcul.util.IExplicationDetailleeBuilder;
import org.impotch.calcul.util.MockExplicationDetailleeBuilder;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/beansCH_GE.xml"})
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
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
		 * @see org.impotch.calcul.impot.RecepteurImpot#ajouteImpot(java.math.BigDecimal, java.lang.String, java.lang.String)
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
