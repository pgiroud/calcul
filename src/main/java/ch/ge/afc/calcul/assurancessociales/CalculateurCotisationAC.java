package ch.ge.afc.calcul.assurancessociales;

import java.math.BigDecimal;

import ch.ge.afc.bareme.BaremeTauxMarginalConstantParTranche;
import ch.ge.afc.calcul.ReglePeriodique;
import ch.ge.afc.util.TypeArrondi;

public class CalculateurCotisationAC extends ReglePeriodique implements
		CalculCotisationAssuranceChomage {

	private final BaremeTauxMarginalConstantParTranche bareme;
	
	
    /**************************************************/
    /*************** Constructeurs ********************/
    /**************************************************/

	CalculateurCotisationAC(int annee, 
			int nMontantAnnuelMaximumGainAssure,
			String nTauxCotisationAssuranceChomage) {
		super(annee);
		BaremeTauxMarginalConstantParTranche.Constructeur constructeur = new BaremeTauxMarginalConstantParTranche.Constructeur();
		constructeur.tranche(nMontantAnnuelMaximumGainAssure, nTauxCotisationAssuranceChomage);
		constructeur.derniereTranche("0");
		constructeur.typeArrondiSurChaqueTranche(TypeArrondi.CINQ_CTS);
		bareme = constructeur.construire();
	}

	CalculateurCotisationAC(int annee, 
			int nMontantAnnuelMaximumGainAssure,String ratioEntreMontantAnnuelMaximumEtLimiteHautRevenu,
			String nTauxCotisationAssuranceChomage, String tauxParticipationHautRevenu) {
		super(annee);
		BaremeTauxMarginalConstantParTranche.Constructeur constructeur = new BaremeTauxMarginalConstantParTranche.Constructeur();
		constructeur.tranche(nMontantAnnuelMaximumGainAssure, nTauxCotisationAssuranceChomage);
		int limiteHautRevenu = BigDecimal.valueOf(nMontantAnnuelMaximumGainAssure).multiply(new BigDecimal(ratioEntreMontantAnnuelMaximumEtLimiteHautRevenu)).intValue();
		constructeur.tranche(limiteHautRevenu,tauxParticipationHautRevenu);
		constructeur.derniereTranche("0");
		constructeur.typeArrondiSurChaqueTranche(TypeArrondi.CINQ_CTS);
		bareme = constructeur.construire();
	}

	
	
	
	@Override
	public BigDecimal calculCotisationAC(BigDecimal montantDeterminant) {
		return bareme.calcul(montantDeterminant);
	}

	@Override
	public BigDecimal calculPartSalarieeCotisationAssuranceChomage(
			BigDecimal montantDeterminant) {
		return TypeArrondi.CINQ_CTS.arrondirMontant(calculCotisationAC(montantDeterminant).divide(BigDecimal.valueOf(2)));
	}

}
