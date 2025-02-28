package org.impotch.calcul.assurancessociales;

import org.impotch.calcul.assurancessociales.param.FournisseurParametrageSuisseAnnuel;
import org.impotch.calcul.assurancessociales.param.ParametrageParticipationHautRevenuAC;
import org.impotch.calcul.assurancessociales.param.ParametrageSuisseAnnuel;
import org.impotch.util.BigDecimalUtil;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

import static org.impotch.calcul.assurancessociales.RegleCHCalculCotisationAssuranceChomage.unCalculateurAC;

public class FournisseurRegleCHCalculCotisationsAssuranceSociale implements FournisseurRegleCalculCotisationsAssuranceSociale {

    private final FournisseurParametrageSuisseAnnuel fournisseurParametrage;

    public FournisseurRegleCHCalculCotisationsAssuranceSociale(FournisseurParametrageSuisseAnnuel fournisseurParametrage) {
        this.fournisseurParametrage = fournisseurParametrage;
    }

    private static BigDecimal decimal(String... taux) {
        return Arrays.stream(taux).map(BigDecimalUtil::parse).reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    private Optional<Function<BigDecimal, BigDecimal>> regle(ParametrageSuisseAnnuel param, TypeAssuranceSociale type) {
        switch (type) {
            case AVS:
                return Optional.of(r -> r.multiply(decimal(param.tauxAVS())));
            case AI:
                return Optional.of(r -> r.multiply(decimal(param.tauxAI())));
            case APG:
                return Optional.of(r -> r.multiply(decimal(param.tauxAPG())));
            case AVS_AI_APG:
                return Optional.of(r -> r.multiply(decimal(param.tauxAVS(),param.tauxAI(),param.tauxAPG())));
            case AC: {
                RegleCHCalculCotisationAssuranceChomage.ConstructeurRegleCotisationAC constructeur
                        = unCalculateurAC(param.montantMaximumDuGainAssure(), param.tauxAC());
                param.participationHautRevenu().ifPresent(p -> {
                     constructeur.tauxParticipationHautRevenu(p.taux());
                     p.ratioAvecLeMontantMaxAssureLimitantLaParticipation()
                             .ifPresent(constructeur::ratioEntreMontantAnnuelMaximumEtLimiteHautRevenu);});
                return Optional.of(r -> constructeur.construire().caculCotisationAssuranceChomage(r));
            }
            // Pas de règle fédérale pour l’assurance maternité ni pour l’assurance accidents non professionnels
            default:
                return Optional.empty();
        }
    }

    @Override
    public Optional<Function<BigDecimal, BigDecimal>> regle(int annee, TypeAssuranceSociale type) {
        return fournisseurParametrage.parametrage(annee).flatMap(param -> regle(param, type));
    }
}
