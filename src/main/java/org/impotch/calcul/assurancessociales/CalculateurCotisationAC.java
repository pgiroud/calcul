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
package org.impotch.calcul.assurancessociales;

import java.math.BigDecimal;

import org.impotch.bareme.Bareme;
import org.impotch.bareme.BaremeTauxMarginalConstantParTranche;
import org.impotch.bareme.ConstructeurBaremeTauxMarginal;
import org.impotch.calcul.ReglePeriodique;
import org.impotch.util.TypeArrondi;

public class CalculateurCotisationAC extends ReglePeriodique implements
		CalculCotisationAssuranceChomage {

	private final Bareme bareme;
	
	
    /**************************************************/
    /*************** Constructeurs ********************/
    /**************************************************/

	CalculateurCotisationAC(int annee, 
			int nMontantAnnuelMaximumGainAssure,
			String nTauxCotisationAssuranceChomage) {
		super(annee);
		ConstructeurBaremeTauxMarginal constructeur = new ConstructeurBaremeTauxMarginal();
		constructeur.tranche(nMontantAnnuelMaximumGainAssure, nTauxCotisationAssuranceChomage);
		constructeur.derniereTranche("0");
		constructeur.typeArrondiSurChaqueTranche(TypeArrondi.CINQ_CTS);
		bareme = constructeur.construire();
	}

	CalculateurCotisationAC(int annee, 
			int nMontantAnnuelMaximumGainAssure,String ratioEntreMontantAnnuelMaximumEtLimiteHautRevenu,
			String nTauxCotisationAssuranceChomage, String tauxParticipationHautRevenu) {
		super(annee);
		ConstructeurBaremeTauxMarginal constructeur = new ConstructeurBaremeTauxMarginal();
		constructeur.tranche(nMontantAnnuelMaximumGainAssure, nTauxCotisationAssuranceChomage);
        if (null != ratioEntreMontantAnnuelMaximumEtLimiteHautRevenu) {
            int limiteHautRevenu = BigDecimal.valueOf(nMontantAnnuelMaximumGainAssure).multiply(new BigDecimal(ratioEntreMontantAnnuelMaximumEtLimiteHautRevenu)).intValue();
            constructeur.tranche(limiteHautRevenu,tauxParticipationHautRevenu);
            constructeur.derniereTranche("0");
        } else {
            constructeur.derniereTranche(tauxParticipationHautRevenu);
        }
		constructeur.typeArrondiSurChaqueTranche(TypeArrondi.CINQ_CTS);
		bareme = constructeur.construire();
	}

    CalculateurCotisationAC(int annee,
                            int nMontantAnnuelMaximumGainAssure,
                            String nTauxCotisationAssuranceChomage, String tauxParticipationHautRevenu) {
        this(annee,nMontantAnnuelMaximumGainAssure,null,nTauxCotisationAssuranceChomage,tauxParticipationHautRevenu);
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
