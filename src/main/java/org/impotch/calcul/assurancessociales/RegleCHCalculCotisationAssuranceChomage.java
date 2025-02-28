package org.impotch.calcul.assurancessociales;

import org.impotch.bareme.Bareme;
import org.impotch.bareme.ConstructeurBareme;

import java.math.BigDecimal;

import static org.impotch.bareme.ConstructeurBareme.unBaremeATauxMarginal;
import static org.impotch.util.TypeArrondi.CINQ_CENTIEMES_LES_PLUS_PROCHES;



class RegleCHCalculCotisationAssuranceChomage implements RegleCalculCotisationAssuranceChomage {

    private final Bareme bareme;

    public static ConstructeurRegleCotisationAC unCalculateurAC(int montantAnnuelMaximumGainAssure, String tauxCotisationAssuranceChomage) {
        return new ConstructeurRegleCotisationAC(montantAnnuelMaximumGainAssure, tauxCotisationAssuranceChomage);
    }

    RegleCHCalculCotisationAssuranceChomage(Bareme bareme) {
        this.bareme = bareme;
    }

    @Override
    public BigDecimal caculCotisationAssuranceChomage(BigDecimal revenu) {
        return bareme.calcul(revenu);
    }

    public static class ConstructeurRegleCotisationAC {

        private final int montantAnnuelMaximumGainAssure;
        private final String tauxCotisationAssuranceChomage;


        private String tauxParticipationHautRevenu = "0";
        private String ratioEntreMontantAnnuelMaximumEtLimiteHautRevenu;


        public ConstructeurRegleCotisationAC(int montantAnnuelMaximumGainAssure, String tauxCotisationAssuranceChomage) {
            this.montantAnnuelMaximumGainAssure = montantAnnuelMaximumGainAssure;
            this.tauxCotisationAssuranceChomage = tauxCotisationAssuranceChomage;
        }


        public ConstructeurRegleCotisationAC tauxParticipationHautRevenu(String taux) {
            this.tauxParticipationHautRevenu = taux;
            return this;
        }

        public ConstructeurRegleCotisationAC ratioEntreMontantAnnuelMaximumEtLimiteHautRevenu(String ratio) {
            this.ratioEntreMontantAnnuelMaximumEtLimiteHautRevenu = ratio;
            return this;
        }

        private ConstructeurBareme initBareme() {
            return unBaremeATauxMarginal()
                    .typeArrondiSurChaqueTranche(CINQ_CENTIEMES_LES_PLUS_PROCHES)
                    .jusqua(0).taux("0")
                    .puisJusqua(montantAnnuelMaximumGainAssure).taux(tauxCotisationAssuranceChomage);
        }


        private RegleCHCalculCotisationAssuranceChomage construireSansRatio() {
            Bareme bareme = initBareme()
                    .puis().taux(tauxParticipationHautRevenu)
                    .construire();
            return new RegleCHCalculCotisationAssuranceChomage(bareme);
        }

        private int limiteHautRevenu() {
            return BigDecimal.valueOf(montantAnnuelMaximumGainAssure).multiply(new BigDecimal(ratioEntreMontantAnnuelMaximumEtLimiteHautRevenu)).intValue();
        }

        private RegleCHCalculCotisationAssuranceChomage construireAvecRatio() {
            Bareme bareme = initBareme()
                    .puisJusqua(limiteHautRevenu()).taux(tauxParticipationHautRevenu)
                    .puis().taux("0")
                    .construire();
            return new RegleCHCalculCotisationAssuranceChomage(bareme);
        }

        public RegleCHCalculCotisationAssuranceChomage construire() {
            return (null != ratioEntreMontantAnnuelMaximumEtLimiteHautRevenu) ? construireAvecRatio() : construireSansRatio();
        }
    }
}
