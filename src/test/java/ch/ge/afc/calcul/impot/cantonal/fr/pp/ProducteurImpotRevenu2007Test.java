package ch.ge.afc.calcul.impot.cantonal.fr.pp;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.ge.afc.calcul.impot.FournisseurAssietteCommunale;
import ch.ge.afc.calcul.impot.Impot;
import ch.ge.afc.calcul.impot.taxation.forimposition.ForCommunal;
import ch.ge.afc.calcul.impot.taxation.pp.FournisseurAssiettePeriodique;
import ch.ge.afc.calcul.impot.taxation.pp.ProducteurImpot;
import ch.ge.afc.calcul.impot.taxation.pp.ProducteurImpotTst;
import ch.ge.afc.calcul.impot.taxation.pp.RecepteurImpotSomme;
import ch.ge.afc.calcul.impot.taxation.pp.RecepteurMultipleImpot;
import ch.ge.afc.calcul.impot.taxation.pp.RecepteurUniqueImpot;
import ch.ge.afc.calcul.impot.taxation.repart.Part;
import ch.ge.afc.calcul.impot.taxation.repart.Repartition;
import ch.ge.afc.calcul.lieu.ICommuneSuisse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/beans.xml")
public class ProducteurImpotRevenu2007Test extends ProducteurImpotTst {

	@Resource(name = "fournisseurRegleImpotCantonalFR")
	private FournisseurRegleImpotCantonalFR fournisseur;
	private Map<String,String> libelleImpotTaxe = new HashMap<String,String>();

	public ProducteurImpotRevenu2007Test() {
		libelleImpotTaxe.put("RCAN","Impôt cantonal de base sur le revenu");
	}
	
	private RecepteurMultipleImpot recepteur(String... codesImpot) {
		RecepteurMultipleImpot recepteur = new RecepteurMultipleImpot();
		for (String code : codesImpot) {
			recepteur.ajouteRecepteur(code, new RecepteurUniqueImpot(code));
		}
		recepteur.ajouteRecepteur("TOTAL",new RecepteurImpotSomme());
		return recepteur;
	}
	
	private BigDecimal getValeur(RecepteurMultipleImpot recepteur, String code) {
		if ("TOTAL".equals(code)) {
			return ((RecepteurImpotSomme)recepteur.getRecepteur(code)).getValeur();
		} else {
			Impot valeur = ((RecepteurUniqueImpot)recepteur.getRecepteur(code)).getValeur();
			if (null == valeur) return null;
			return valeur.getMontant();
		}
	}
	
	private void verifierMontantImpot(RecepteurMultipleImpot recepteur, String codeImpotTaxe, String montantAttendu) {
		assertEquals(codeImpotTaxe + " : " + libelleImpotTaxe.get(codeImpotTaxe),new BigDecimal(montantAttendu),getValeur(recepteur,codeImpotTaxe));
	}
	
	private void verifierAbsenceImpot(RecepteurMultipleImpot recepteur, String codeImpotTaxe) {
		assertNull(codeImpotTaxe + " : " + libelleImpotTaxe.get(codeImpotTaxe),getValeur(recepteur,codeImpotTaxe));
	}
	
	@Test
	public void permierTest() {
		ProducteurImpot producteur = fournisseur.getProducteurImpotsICRevenu(2007);
		RecepteurMultipleImpot recepteur = recepteur("RCAN");
		
		producteur.produireImpot(this.creerSituationFamilleAvecEnfant(11,13), this.creerAssiettes(2007, 91256, 92352,fournisseurLieu.getCommune(2321)), recepteur);
		verifierMontantImpot(recepteur,"RCAN",  "7721.00");
	}
	
	@Test
	public void tauxMinimum() {
		ProducteurImpot producteur = fournisseur.getProducteurImpotsICRevenu(2007);
		RecepteurMultipleImpot recepteur = recepteur("RCAN");
		
		producteur.produireImpot(this.creerSituationFamilleAvecEnfant(11,13), this.creerAssiettes(2007, 5500, 5500,fournisseurLieu.getCommune(2321)), recepteur);
		verifierMontantImpot(recepteur,"RCAN",  "55.00");
	}
	
	private FournisseurAssiettePeriodique creerAssiettes(final int annee, final int montantImposable, final int montantDeterminant, final ICommuneSuisse commune) {
		FournisseurAssiettePeriodique fournisseur = new FournisseurAssiettePeriodique() {

			@Override
			public FournisseurAssietteCommunale getFournisseurAssietteCommunale() {
				FournisseurAssietteCommunale fournisseur = new FournisseurAssietteCommunale() {

					@Override
					public Map<ICommuneSuisse, Integer> getNbreJourDomicileSurCommune() {				
						Map<ICommuneSuisse, Integer> map = new HashMap<ICommuneSuisse, Integer>();
						map.put(commune, 360);
						return map;
					}

					@Override
					public int getPeriodeFiscale() { return annee; }

					@Override
					public Repartition<ForCommunal> getRepartition() {
						Repartition<ForCommunal> repart = new Repartition<ForCommunal>();
						repart.ajouterPart(new ForCommunal(commune),new Part(BigDecimal.ONE));
						return repart;
					}
						
				};
				return fournisseur;
			}

			@Override
			public int getNombreJourPourAnnualisation() { return 360; }

			@Override
			public int getPeriodeFiscale() {return annee; }

			@Override
			public BigDecimal getMontantDeterminant() {return new BigDecimal(montantDeterminant); }

			@Override
			public BigDecimal getMontantImposable() { return new BigDecimal(montantImposable); }
			
		};
		return fournisseur;
	}
	
}
