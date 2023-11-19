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
package org.impotch.calcul.impot.cantonal.ge.pp.avant2010;

import org.impotch.bareme.Bareme;
import org.impotch.bareme.BaremeDiscretiseEtInterpolationLineaire;
import org.impotch.bareme.BaremeTauxMarginalIntegrable;
import org.impotch.bareme.Point;
import org.impotch.calcul.impot.indexation.FournisseurIndicePeriodique;
import org.impotch.util.TypeArrondi;
import org.impotch.util.math.Fonction;
import org.impotch.util.math.integration.MethodeIntegration;
import org.impotch.util.math.integration.MethodeIntegrationPointMilieu;

import java.util.List;

public class ConstructeurBaremeRevenuAvecFormuleUniversite {

    private final FournisseurIndicePeriodique fournisseurIndicePeriodique;

    private ConstructeurBaremeRevenuAvecFormuleUniversite(FournisseurIndicePeriodique fournisseurIndicePeriodique) {
        this.fournisseurIndicePeriodique = fournisseurIndicePeriodique;
    }

    public static ConstructeurBaremeRevenuAvecFormuleUniversite unConstructeurBaremeRevenuAvecFormuleUniversite(FournisseurIndicePeriodique fournisseurIndicePeriodique) {
        return new ConstructeurBaremeRevenuAvecFormuleUniversite(fournisseurIndicePeriodique);
    }

    private TauxMarginalSeul construireTauxMarginal(int annee) {
        TauxMarginalSeul txMarginal = new TauxMarginalSeul(annee);
        txMarginal.setIndexateur(fournisseurIndicePeriodique);
        return txMarginal;
    }

    private TauxMarginalFamille construireTauxMarginalFamille(int annee) {
        TauxMarginalFamille txMarginal = new TauxMarginalFamille(annee);
        txMarginal.setTauxMarginalSeul(this.construireTauxMarginal(annee));
        return txMarginal;
    }

    public Bareme construireBaremeRevenu(int annee) {
        BaremeTauxMarginalIntegrable bareme = new BaremeTauxMarginalIntegrable();
        bareme.setTypeArrondi(TypeArrondi.CINQ_CENTIEMES_LES_PLUS_PROCHES);
        bareme.setTauxMarginal(construireTauxMarginal(annee));
        return bareme;
    }


    public Bareme construireBaremeRevenuMarie(int annee) {
        MethodeIntegration methode = new MethodeIntegrationPointMilieu();
        Fonction tauxMarginal = construireTauxMarginalFamille(annee);
        if (annee < 2001) {
            BaremeFamille bareme = new BaremeFamille();
            bareme.setMethodeIntegration(methode);
            bareme.setTauxMarginal(tauxMarginal);
            bareme.setArrondi(TypeArrondi.CINQ_CENTIEMES_LES_PLUS_PROCHES);
            return bareme;
        } else {
            DiscretisationBaremeMarie discretisateur = new DiscretisationBaremeMarie();
            discretisateur.setTauxMarginal(tauxMarginal);
            discretisateur.setMethodeIntegration(methode);
            if (2001 == annee || 2002 == annee) {
                discretisateur.largeur(200).jusqua(1000000);
                discretisateur.setArrondi(TypeArrondi.CENTIEME_LE_PLUS_PROCHE);
            } else {
                discretisateur.setArrondi(TypeArrondi.CINQ_CENTIEMES_LES_PLUS_PROCHES);
                if (2003 == annee) {
                    discretisateur.largeur(100).jusqua(30000)
                            .largeur(200).jusqua(50000)
                            .largeur(500).jusqua(80000)
                            .largeur(1000).jusqua(1000000);
                } else if (2004 == annee) {
                    discretisateur.largeur(100).jusqua(1000000);
                } else {
                    discretisateur.largeur(100).jusqua(300000);
                }
            }
            List<Point> points = discretisateur.obtenirPointsDiscretisation();
            BaremeDiscretiseEtInterpolationLineaire baremeDiscretise = new BaremeDiscretiseEtInterpolationLineaire();
            for (Point pt : points) {
                baremeDiscretise.ajouterPointDiscretisation(pt);
            }

            BaremeRevenuChoixSuivantMontant baremeAvecRaccord = new BaremeRevenuChoixSuivantMontant();
            if (annee < 2001) {
                baremeAvecRaccord.setLimiteBaremeFamille(2000000);
            } else if (annee < 2003) {
                baremeAvecRaccord.setLimiteBaremeFamille(1000000);
            } else {
                baremeAvecRaccord.setLimiteBaremeFamille(300000);
            }
            baremeAvecRaccord.setBaremeSeul(construireBaremeRevenu(annee));
            baremeAvecRaccord.setBaremeFamille(baremeDiscretise);
            return baremeAvecRaccord;
        }

    }
}
