package ch.ge.afc.calcul.impot.cantonal.ge.pp;


import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import ch.ge.afc.calcul.impot.taxation.pp.ProducteurImpotBaseProgressif;
import ch.ge.afc.calcul.impot.cantonal.ge.pp.avant2010.ConstructeurBaremeIndexeTxMarginalConstantParTranche;
import ch.ge.afc.calcul.impot.cantonal.ge.pp.indexateur.SimpleFournisseurIndicePeriodiqueGE;
import ch.ge.afc.calcul.impot.taxation.pp.FournisseurAssiettePeriodique;
import ch.ge.afc.calcul.impot.taxation.pp.ProducteurImpot;
import ch.ge.afc.calcul.impot.taxation.pp.ProducteurImpotTst;
import ch.ge.afc.calcul.impot.taxation.pp.RecepteurUniqueImpot;
import ch.ge.afc.calcul.impot.taxation.pp.SituationFamiliale;
import ch.ge.afc.calcul.impot.taxation.pp.famille.Splitting;
import ch.ge.afc.util.ExplicationDetailleTexteBuilder;
import ch.ge.afc.util.IExplicationDetailleeBuilder;
import ch.ge.afc.util.TypeArrondi;

public class ProducteurImpotNouvelleLIPP2009Test extends ProducteurImpotTst {

	private ProducteurImpot producteur;

	@Before
	public void setUp() throws Exception {
		// Inutile de se préoccuper du renchérissement, le barème est celui de 2009 dans la loi.
		SimpleFournisseurIndicePeriodiqueGE fournisseur = new SimpleFournisseurIndicePeriodiqueGE();
		Map<Integer,BigDecimal> indices = new HashMap<Integer,BigDecimal>();
		indices.put(2009,BigDecimal.ONE);
		fournisseur.setIndices(indices);
		
		ConstructeurBaremeIndexeTxMarginalConstantParTranche constructeur = new ConstructeurBaremeIndexeTxMarginalConstantParTranche();
		constructeur.validite(2009);
		constructeur.anneeReferenceRencherissement(2009);
		constructeur.indexateur(fournisseur);
		constructeur.tranche(17493, "0 %");
		constructeur.tranche(21076, "8 %");
		constructeur.tranche(23184, "9 %");
		constructeur.tranche(25291, "10 %");
		constructeur.tranche(27399, "11 %");
		constructeur.tranche(32668, "12 %");
		constructeur.tranche(36883, "13 %");
		constructeur.tranche(41099, "14 %");
		constructeur.tranche(45314, "14.5 %");
		constructeur.tranche(72713, "15 %");
		constructeur.tranche(119081, "15.5 %");
		constructeur.tranche(160179, "16 %");
		constructeur.tranche(181256, "16.5 %");
		constructeur.tranche(259238, "17 %");
		constructeur.tranche(276099, "17.5 %");
		constructeur.tranche(388857, "18 %");
		constructeur.tranche(609103, "18.5 %");
		constructeur.derniereTranche("19 %");
		
		ProducteurImpotBaseProgressif producteurBase = new ProducteurImpotBaseProgressif();
		producteurBase.setStrategieProductionImpotFamille(new Splitting(constructeur.typeArrondiTranche(TypeArrondi.CINQ_CTS).construire(2009),"50 %"));
		
		
		producteur = new ProducteurImpot("IBR","CAN-GE"){
			@Override
			protected IExplicationDetailleeBuilder createExplicationBuilder() {return new ExplicationDetailleTexteBuilder();}
		};
		producteur.setProducteurBase(producteurBase);
	}

	private void marie(final int periodeFiscale, final int montantImposable, final String montantImpot, final int...ageEnfant) {
		SituationFamiliale situation = creerSituationFamilleAvecEnfant(ageEnfant);
		FournisseurAssiettePeriodique fournisseur = this.creerAssiettes(periodeFiscale, montantImposable);
		RecepteurUniqueImpot recepteurIBR = new RecepteurUniqueImpot("IBR");
		producteur.produireImpot(situation, fournisseur, recepteurIBR);
		assertEquals("Revenu de " + montantImposable,new BigDecimal(montantImpot),recepteurIBR.getValeur().getMontant());
	}
	
	@Test
	public void marie() {
		marie(2009,85000,"5785.20",8,6);
	}
	
}
