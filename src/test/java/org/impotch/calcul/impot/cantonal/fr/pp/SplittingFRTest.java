package org.impotch.calcul.impot.cantonal.fr.pp;


import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.impotch.calcul.impot.cantonal.fr.pp.BaremeTauxEffectifLineaireParTranche;
import org.impotch.calcul.impot.cantonal.fr.pp.FournisseurRegleImpotCantonalFR;
import org.impotch.calcul.impot.cantonal.fr.pp.SplittingFR;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.impotch.bareme.Bareme;
import org.impotch.calcul.impot.taxation.pp.EnfantACharge;
import org.impotch.calcul.impot.taxation.pp.PersonneACharge;
import org.impotch.calcul.impot.taxation.pp.SituationFamiliale;
import org.impotch.util.TypeArrondi;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/beans.xml")
public class SplittingFRTest {

	@Resource(name = "fournisseurRegleImpotCantonalFR")
	private FournisseurRegleImpotCantonalFR fournisseur;

	@Test
	public void splitting2009() {
		Bareme bareme = fournisseur.getBaremeRevenu(2009);
		SplittingFR splitting = new SplittingFR((BaremeTauxEffectifLineaireParTranche)bareme,"56 %");
		splitting.setTypeArrondi(TypeArrondi.CENT_FRANC_INF);
		
		testSplitting(splitting,5100,"51.00");
		testSplitting(splitting,5200,"52.00");
		testSplitting(splitting,5300,"53.00");
		testSplitting(splitting,5400,"54.00");
		testSplitting(splitting,5500,"55.00");
		testSplitting(splitting,5600,"56.00");
		testSplitting(splitting,5700,"57.00");
		testSplitting(splitting,5800,"58.00");
		testSplitting(splitting,5900,"59.00");
		testSplitting(splitting,6000,"60.00");
		testSplitting(splitting,6100,"61.00");
		testSplitting(splitting,6200,"62.00");
		testSplitting(splitting,6300,"63.00");
		testSplitting(splitting,6400,"64.00");
		testSplitting(splitting,6500,"65.00");
		testSplitting(splitting,6600,"66.00");
		testSplitting(splitting,6700,"67.00");
		testSplitting(splitting,6800,"68.00");
		testSplitting(splitting,6900,"69.00");
		testSplitting(splitting,7000,"70.00");
		testSplitting(splitting,7100,"71.00");
		testSplitting(splitting,7200,"72.00");
		testSplitting(splitting,7300,"73.00");
		testSplitting(splitting,7400,"74.00");
		testSplitting(splitting,7500,"75.00");
		testSplitting(splitting,7600,"76.00");
		testSplitting(splitting,7700,"77.00");
		testSplitting(splitting,7800,"78.00");
		testSplitting(splitting,7900,"79.00");
		testSplitting(splitting,8000,"80.00");
		testSplitting(splitting,8100,"81.00");
		testSplitting(splitting,8200,"82.00");
		testSplitting(splitting,8300,"83.00");
		testSplitting(splitting,8400,"84.00");
		testSplitting(splitting,8500,"85.00");
		testSplitting(splitting,8600,"86.00");
		testSplitting(splitting,8700,"87.00");
		testSplitting(splitting,8800,"88.00");
		testSplitting(splitting,8900,"89.00");
		testSplitting(splitting,9000,"90.00");
		testSplitting(splitting,9100,"91.00");
		testSplitting(splitting,9200,"92.00");
		testSplitting(splitting,9300,"95.45");
		testSplitting(splitting,9400,"96.45");
		testSplitting(splitting,9500,"99.95");
		testSplitting(splitting,9600,"101.00");
		testSplitting(splitting,9700,"104.60");
		testSplitting(splitting,9800,"105.65");
		testSplitting(splitting,9900,"109.35");
		testSplitting(splitting,10000,"113.05");
		testSplitting(splitting,10100,"114.20");
		testSplitting(splitting,10200,"117.95");
		testSplitting(splitting,10300,"119.15");
		testSplitting(splitting,10400,"123.00");
		testSplitting(splitting,10500,"124.20");
		testSplitting(splitting,10600,"128.15");
		testSplitting(splitting,10700,"129.35");
		testSplitting(splitting,10800,"133.35");
		testSplitting(splitting,10900,"137.45");
		testSplitting(splitting,11000,"138.70");
		testSplitting(splitting,11100,"142.85");
		testSplitting(splitting,11200,"144.15");
		testSplitting(splitting,11300,"148.40");
		testSplitting(splitting,11400,"149.70");
		testSplitting(splitting,11500,"154.00");
		testSplitting(splitting,11600,"155.35");
		testSplitting(splitting,11700,"159.75");
		testSplitting(splitting,11800,"164.20");
		testSplitting(splitting,11900,"165.60");
		testSplitting(splitting,12000,"170.10");
		testSplitting(splitting,12100,"171.55");
		testSplitting(splitting,12200,"176.15");
		testSplitting(splitting,12300,"177.60");
		testSplitting(splitting,12400,"182.25");
		testSplitting(splitting,12500,"187.00");
		testSplitting(splitting,12600,"188.50");
		testSplitting(splitting,12700,"193.30");
		testSplitting(splitting,12800,"194.80");
		testSplitting(splitting,12900,"199.70");
		testSplitting(splitting,13000,"201.25");
		testSplitting(splitting,13100,"206.20");
		testSplitting(splitting,13200,"207.80");
		testSplitting(splitting,13300,"212.85");
		testSplitting(splitting,13400,"217.95");
		testSplitting(splitting,13500,"219.55");
		testSplitting(splitting,13600,"224.75");
		testSplitting(splitting,13700,"226.40");
		testSplitting(splitting,13800,"231.65");
		testSplitting(splitting,13900,"233.35");
		testSplitting(splitting,14000,"238.65");
		testSplitting(splitting,14100,"240.35");
		testSplitting(splitting,14200,"245.75");
		testSplitting(splitting,14300,"251.25");
		testSplitting(splitting,14400,"253.00");
		testSplitting(splitting,14500,"258.55");
		testSplitting(splitting,14600,"260.30");
		testSplitting(splitting,14700,"265.95");
		testSplitting(splitting,14800,"267.75");
		testSplitting(splitting,14900,"273.45");
		testSplitting(splitting,15000,"279.20");
		testSplitting(splitting,15100,"281.05");
		testSplitting(splitting,15200,"286.90");
		testSplitting(splitting,15300,"288.75");
		testSplitting(splitting,15400,"294.70");
		testSplitting(splitting,15500,"296.60");
		testSplitting(splitting,15600,"302.60");
		testSplitting(splitting,15700,"304.50");
		testSplitting(splitting,15800,"310.60");
		testSplitting(splitting,15900,"316.70");
		testSplitting(splitting,16000,"318.70");
		testSplitting(splitting,16100,"324.90");
		testSplitting(splitting,16200,"326.90");
		testSplitting(splitting,16300,"333.15");
		testSplitting(splitting,16400,"335.20");
		testSplitting(splitting,16500,"341.55");
		testSplitting(splitting,16600,"343.65");
		testSplitting(splitting,16700,"350.05");
		testSplitting(splitting,16800,"356.55");
		testSplitting(splitting,16900,"358.65");
		testSplitting(splitting,17000,"365.25");
		testSplitting(splitting,17100,"367.40");
		testSplitting(splitting,17200,"374.00");
		testSplitting(splitting,17300,"376.20");
		testSplitting(splitting,17400,"382.90");
		testSplitting(splitting,17500,"389.65");
		testSplitting(splitting,17600,"391.90");
		testSplitting(splitting,17700,"398.75");
		testSplitting(splitting,17800,"401.00");
		testSplitting(splitting,17900,"407.90");
		testSplitting(splitting,18000,"410.20");
		testSplitting(splitting,18100,"417.20");
		testSplitting(splitting,18200,"419.50");
		testSplitting(splitting,18300,"426.60");
		testSplitting(splitting,18400,"433.70");
		testSplitting(splitting,18500,"436.10");
		testSplitting(splitting,18600,"443.30");
		testSplitting(splitting,18700,"445.70");
		testSplitting(splitting,18800,"452.95");
		testSplitting(splitting,18900,"455.40");
		testSplitting(splitting,19000,"462.75");
		testSplitting(splitting,19100,"465.20");
		testSplitting(splitting,19200,"472.65");
		testSplitting(splitting,19300,"480.15");
		testSplitting(splitting,19400,"482.60");
	}
	
	@Test
	public void sansSplitting2009() {
		Bareme bareme = fournisseur.getBaremeRevenu(2009);
		SplittingFR splitting = new SplittingFR((BaremeTauxEffectifLineaireParTranche)bareme,"56 %");
		splitting.setTypeArrondi(TypeArrondi.CENT_FRANC_INF);
		
		testSansSplitting(splitting,5100,"51.00");
		testSansSplitting(splitting,5200,"53.35");
		testSansSplitting(splitting,5300,"55.75");
		testSansSplitting(splitting,5400,"58.25");
		testSansSplitting(splitting,5500,"60.75");
		testSansSplitting(splitting,5600,"63.30");
		testSansSplitting(splitting,5700,"65.95");
		testSansSplitting(splitting,5800,"68.60");
		testSansSplitting(splitting,5900,"71.30");
		testSansSplitting(splitting,6000,"74.10");
		testSansSplitting(splitting,6100,"76.90");
		testSansSplitting(splitting,6200,"79.80");
		testSansSplitting(splitting,6300,"82.75");
		testSansSplitting(splitting,6400,"85.70");
		testSansSplitting(splitting,6500,"88.75");
		testSansSplitting(splitting,6600,"91.85");
		testSansSplitting(splitting,6700,"95.00");
		testSansSplitting(splitting,6800,"98.15");
		testSansSplitting(splitting,6900,"101.40");
		testSansSplitting(splitting,7000,"104.70");
		testSansSplitting(splitting,7100,"108.05");
		testSansSplitting(splitting,7200,"111.45");
		testSansSplitting(splitting,7300,"114.90");
		testSansSplitting(splitting,7400,"118.40");
		testSansSplitting(splitting,7500,"122.00");
		testSansSplitting(splitting,7600,"125.60");
		testSansSplitting(splitting,7700,"129.25");
		testSansSplitting(splitting,7800,"132.95");
		testSansSplitting(splitting,7900,"136.75");
		testSansSplitting(splitting,8000,"140.55");
		testSansSplitting(splitting,8100,"144.40");
		testSansSplitting(splitting,8200,"148.35");
		testSansSplitting(splitting,8300,"152.30");
		testSansSplitting(splitting,8400,"156.35");
		testSansSplitting(splitting,8500,"160.45");
		testSansSplitting(splitting,8600,"164.55");
		testSansSplitting(splitting,8700,"168.75");
		testSansSplitting(splitting,8800,"173.00");
		testSansSplitting(splitting,8900,"177.25");
		testSansSplitting(splitting,9000,"181.60");
		testSansSplitting(splitting,9100,"186.00");
		testSansSplitting(splitting,9200,"190.45");
		testSansSplitting(splitting,9300,"194.95");
		testSansSplitting(splitting,9400,"199.50");
		testSansSplitting(splitting,9500,"204.10");
		testSansSplitting(splitting,9600,"208.75");
		testSansSplitting(splitting,9700,"213.45");
		testSansSplitting(splitting,9800,"218.20");
		testSansSplitting(splitting,9900,"223.05");
		testSansSplitting(splitting,10000,"227.90");
		testSansSplitting(splitting,10100,"232.80");
		testSansSplitting(splitting,10200,"237.75");
		testSansSplitting(splitting,10300,"242.80");
		testSansSplitting(splitting,10400,"247.85");
		testSansSplitting(splitting,10500,"253.00");
		testSansSplitting(splitting,10600,"258.15");
		testSansSplitting(splitting,10700,"263.40");
		testSansSplitting(splitting,10800,"268.65");
		testSansSplitting(splitting,10900,"274.00");
		testSansSplitting(splitting,11000,"279.40");
		testSansSplitting(splitting,11100,"284.85");
		testSansSplitting(splitting,11200,"290.30");
		testSansSplitting(splitting,11300,"295.85");
		testSansSplitting(splitting,11400,"301.45");
		testSansSplitting(splitting,11500,"307.10");
		testSansSplitting(splitting,11600,"312.80");
		testSansSplitting(splitting,11700,"318.55");
		testSansSplitting(splitting,11800,"324.35");
		testSansSplitting(splitting,11900,"330.20");
		testSansSplitting(splitting,12000,"336.10");
		testSansSplitting(splitting,12100,"342.05");
		testSansSplitting(splitting,12200,"348.10");
		testSansSplitting(splitting,12300,"354.15");
		testSansSplitting(splitting,12400,"360.25");
		testSansSplitting(splitting,12500,"366.45");
		testSansSplitting(splitting,12600,"372.65");
		testSansSplitting(splitting,12700,"378.90");
		testSansSplitting(splitting,12800,"385.25");
		testSansSplitting(splitting,12900,"391.60");
		testSansSplitting(splitting,13000,"398.05");
		testSansSplitting(splitting,13100,"404.55");
		testSansSplitting(splitting,13200,"411.05");
		testSansSplitting(splitting,13300,"417.65");
		testSansSplitting(splitting,13400,"424.30");
		testSansSplitting(splitting,13500,"430.95");
		testSansSplitting(splitting,13600,"437.70");
		testSansSplitting(splitting,13700,"444.50");
		testSansSplitting(splitting,13800,"451.35");
		testSansSplitting(splitting,13900,"458.25");
		testSansSplitting(splitting,14000,"465.20");
		testSansSplitting(splitting,14100,"472.20");
		testSansSplitting(splitting,14200,"479.25");
		testSansSplitting(splitting,14300,"486.35");
		testSansSplitting(splitting,14400,"493.55");
		testSansSplitting(splitting,14500,"500.75");
		testSansSplitting(splitting,14600,"508.00");
		testSansSplitting(splitting,14700,"515.30");
		testSansSplitting(splitting,14800,"522.70");
		testSansSplitting(splitting,14900,"530.10");
		testSansSplitting(splitting,15000,"537.60");
		testSansSplitting(splitting,15100,"545.10");
		testSansSplitting(splitting,15200,"552.70");
		testSansSplitting(splitting,15300,"560.30");
		testSansSplitting(splitting,15400,"568.00");
		testSansSplitting(splitting,15500,"575.75");
		testSansSplitting(splitting,15600,"583.50");
		testSansSplitting(splitting,15700,"591.35");
		testSansSplitting(splitting,15800,"599.25");
		testSansSplitting(splitting,15900,"607.20");
		testSansSplitting(splitting,16000,"615.20");
		testSansSplitting(splitting,16100,"623.25");
		testSansSplitting(splitting,16200,"631.35");
		testSansSplitting(splitting,16300,"639.50");
		testSansSplitting(splitting,16400,"647.70");
		testSansSplitting(splitting,16500,"655.95");
		testSansSplitting(splitting,16600,"664.25");
		testSansSplitting(splitting,16700,"672.60");
		testSansSplitting(splitting,16800,"681.00");
		testSansSplitting(splitting,16900,"689.50");
		testSansSplitting(splitting,17000,"698.00");
		testSansSplitting(splitting,17100,"706.55");
		testSansSplitting(splitting,17200,"715.20");
		testSansSplitting(splitting,17300,"721.95");
		testSansSplitting(splitting,17400,"728.70");
		testSansSplitting(splitting,17500,"735.50");
		testSansSplitting(splitting,17600,"742.30");
		testSansSplitting(splitting,17700,"749.15");
		testSansSplitting(splitting,17800,"756.05");
		testSansSplitting(splitting,17900,"762.95");
		testSansSplitting(splitting,18000,"769.90");
		testSansSplitting(splitting,18100,"776.90");
		testSansSplitting(splitting,18200,"783.90");
		testSansSplitting(splitting,18300,"790.95");
		testSansSplitting(splitting,18400,"798.00");
		testSansSplitting(splitting,18500,"805.10");
		testSansSplitting(splitting,18600,"812.20");
		testSansSplitting(splitting,18700,"819.35");
		testSansSplitting(splitting,18800,"826.55");
		testSansSplitting(splitting,18900,"833.75");
		testSansSplitting(splitting,19000,"841.00");
		testSansSplitting(splitting,19100,"848.25");
		testSansSplitting(splitting,19200,"855.55");
		testSansSplitting(splitting,19300,"862.90");
		testSansSplitting(splitting,19400,"870.25");
		
	}
	
	
	private void testSplitting(SplittingFR splitting, int assiette, String montantAttendu) {
		BigDecimal assietteBG = new BigDecimal(assiette);
		BigDecimal impot = splitting.produireImpotAnnuel(getSituation(true), assietteBG, assietteBG);
		impot = TypeArrondi.CINQ_CTS.arrondirMontant(impot);
		assertEquals("Splitting",new BigDecimal(montantAttendu),impot);
	}
	
	private void testSansSplitting(SplittingFR splitting, int assiette, String montantAttendu) {
		BigDecimal assietteBG = new BigDecimal(assiette);
		BigDecimal impot = splitting.produireImpotAnnuel(getSituation(false), assietteBG, assietteBG);
		impot = TypeArrondi.CINQ_CTS.arrondirMontant(impot);
		assertEquals("Splitting",new BigDecimal(montantAttendu),impot);
	}
	
	private SituationFamiliale getSituation(final boolean famille) {
		return new SituationFamiliale() {

			@Override
			public Set<EnfantACharge> getEnfants() {return Collections.emptySet();}

			@Override
			public Set<PersonneACharge> getPersonnesNecessiteuses() {return Collections.emptySet();}

			@Override
			public boolean isCouple() {return famille;}
			
		};
	}
	
}
