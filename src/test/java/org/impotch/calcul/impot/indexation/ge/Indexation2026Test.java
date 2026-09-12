package org.impotch.calcul.impot.indexation.ge;

import org.impotch.bareme.BaremeParTranche;
import org.impotch.bareme.BaremeParTrancheAssert;
import org.impotch.calcul.impot.indexation.Indexateur;
import org.impotch.util.TypeArrondi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.impotch.bareme.ConstructeurBareme.unBareme;

public class Indexation2026Test {
    private static final int ANNEE_FISCALE = 2026;

    private static final int ANNEE_INDEXATION_LIPP_D_3_08 = 2009;
    private static final int ANNEE_INDEXATION_L10905 = 2014; // Voir lois 10905, 12884
    private static final int ANNEE_INDEXATION_L11683 = 2016;
    private static final int ANNEE_INDEXATION_L11667 = 2016; // Loi 11667 Déduction des frais de formation et de perfectionnement selon le droit fédéral harmonisé du 18 septembre 2015
    private static final int ANNEE_INDEXATION_L11685 = 2017;
    private static final int ANNEE_INDEXATION_L12248 = 2019; // Loi 12248 Pour une vrai déduction fiscale des frais de garde de nos enfants du 21 septembre 2018
    private static final int ANNEE_INDEXATION_L12314 = 2021; // Loi 12314 Moins d’impôts pour les familles ! du 17 octobre 2019
    private static final int ANNEE_INDEXATION_L12884 = 2023; // Loi 12884 Gains réalisés à des jeux d’argent du 2 septembre 2022


    private Indexateur indexateur;


    @BeforeEach
    public void initIndexateur() {
        FournisseurIndexGenevois fournisseur = new FournisseurIndexGenevoisEnMemoire();
        indexateur = new IndexateurGenevois(fournisseur);
    }

    private int indexer(int annee, int valeurBaseIndexation) {
        return indexateur.indexer(annee, BigDecimal.valueOf(valeurBaseIndexation), ANNEE_FISCALE)
                .intValueExact();
    }

    private int indexer(int valeurBaseIndexation) {
        return indexer(ANNEE_INDEXATION_LIPP_D_3_08,valeurBaseIndexation);
    }


    private BaremeParTranche construireBaremeArt40Alinea3LIPP() {
        return unBareme()
                .jusqua(50000).valeur(10000)
                .de(50000).a(56700).valeur(8000)
                .de(56700).a(64000).valeur(6000)
                .de(64000).a(71500).valeur(4000)
                .de(71500).a(80000).valeur(2000)
                .plusDe(80000).valeur(0).construire();
    }

    private BaremeParTranche construireBaremeArt40Alinea1LIPPUnSeulRentier() {
        return construireBaremeArt40Alinea3LIPP()
                .homothetie(new BigDecimal("1.15"), TypeArrondi.CENTAINE_INF);
    }

    private BaremeParTranche construireBaremeArt40Alinea1LIPPDeuxRentiers() {
        return construireBaremeArt40Alinea1LIPPUnSeulRentier()
                .homothetieValeur(new BigDecimal("1.15"), TypeArrondi.CENTAINE_INF);
    }



    // Règlement relatif à la compensation des effets de la progression à froid (RCEPF)

    @Test
    @DisplayName("Le montant minimal de la dépense prévu à l'article 14, alinéa 3, lettre a, de la loi s'élève à 426 357 francs.")
    public void indexationMontantMinimalDeLaDepense() {
        int valeurIndexee = indexer(ANNEE_INDEXATION_L11683,400_000);
        assertThat(valeurIndexee).isEqualTo(426_357);
    }


    @Test
    @DisplayName("Le montant maximal de l'exonération de la solde des sapeurs-pompiers de milice prévu à l'article 27, lettre o, de la loi s'élève à 9 538 francs.")
    public void montantMaxExonerationSoldeSapeurPompier() {
        int valeurIndexee = indexer(ANNEE_INDEXATION_L10905,9_000);
        assertThat(valeurIndexee).isEqualTo(9_538);
    }

    @Test
    @DisplayName("Montant maximal de la déduction pour les frais de déplacement liées à l'exercice d'une activité lucrative dépendante (art. 29 alinea 1 de la loi)")
    public void plafondFraisDeplacement() {
        int valeurIndexee = indexer(ANNEE_INDEXATION_L11685,500);
        assertThat(valeurIndexee).isEqualTo(536);
    }

    @Test
    @DisplayName("Déductions liées à l'exercice d'une activité lucrative dépendante (art. 29 alinea 2de la loi)")
    public void plancherFraisForfaitaire() {
        int valeurIndexee = indexer(600);
        assertThat(valeurIndexee).isEqualTo(641);
    }

    @Test
    @DisplayName("Déductions liées à l'exercice d'une activité lucrative dépendante (art. 29 alinea 2 de la loi)")
    public void plafondFraisForfaitaire() {
        int valeurIndexee = indexer(1700);
        assertThat(valeurIndexee).isEqualTo(1817);
    }

//            Art. 7        Déduction liée à l'exercice d'une activité lucrative dépendante accessoire (art. 29A de la loi)
//
//    Le montant de la déduction forfaitaire pour les frais professionnels prévu à l'article 29A, alinéa 1, de la loi s'élève au minimum à 810 francs et au maximum à 2 429 francs.
//
//
//


    @Test
    @DisplayName("Le montant maximal de la déduction des primes d'assurances sur la vie et des intérêts échus des capitaux d'épargne prévu à l'article 31, lettre d, de la loi s'élève à : 3 528 francs pour les époux vivant en ménage commun")
    public void deductionPrevoyancePourEpouxVivantEnMenageCommun() {
        int valeurIndexee = indexer(3300);
        assertThat(valeurIndexee).isEqualTo(3528);
    }

    @Test
    @DisplayName("Le montant maximal de la déduction des primes d'assurances sur la vie et des intérêts échus des capitaux d'épargne prévu à l'article 31, lettre d, de la loi s'élève à : 2 352 francs pour le contribuable célibataire, veuf, divorcé, séparé de corps ou de fait.")
    public void deductionPrevoyancePourContribuableSeul() {
        int valeurIndexee = indexer(2200);
        assertThat(valeurIndexee).isEqualTo(2352);
    }

    @Test
    @DisplayName("Le montant maximal de la déduction des primes d'assurances sur la vie et des intérêts échus des capitaux d'épargne prévu à l'article 31, lettre d, de la loi s'élève à : 962 francs supplémentaires pour chaque charge de famille.")
    public void deductionPrevoyanceParCharge() {
        int valeurIndexee = indexer(900);
        assertThat(valeurIndexee).isEqualTo(962);
    }

    @Test
    @DisplayName("Le montant maximal de la déduction des primes d'assurances sur la vie et des intérêts échus des capitaux d'épargne prévu à l'article 31, lettre d, de la loi s'élève à : 1 443 francs lorsque, au sein du couple, un seul des deux conjoints est affilié à une telle institution.")
    public void deductionPrevoyanceParChargeQuandUnSeulDesMembresDuCoupleCotise() {
        int valeurIndexee = indexer(1350);
        assertThat(valeurIndexee).isEqualTo(1443);
    }

    @Test
    @DisplayName("Le montant maximal de la déduction pour frais de garde par enfant concerné prévu à l'article 35 de la loi s'élève à 26 392 francs.")
    public void deductionFraisGardeEnfants() {
        int valeurIndexee = indexer(ANNEE_INDEXATION_L12248,25_000);
        assertThat(valeurIndexee).isEqualTo(26_392);
    }

    @Test
    @DisplayName("La déduction en cas d'activité lucrative des deux conjoints prévue par l'article 36 de la loi s'élève à 1 054 francs.")
    public void deductionDoubleActivite() {
        int valeurIndexee = indexer(ANNEE_INDEXATION_L12314,1000);
        assertThat(valeurIndexee).isEqualTo(1054);
    }

    @Test
    @DisplayName("Le montant maximal de la déduction à titre de mise sur les gains de loterie prévu par l'article 36A de la loi s'élève à 5 179 francs.")
    public void plafondGainsDeLoterie() {
        int valeurIndexee = indexer(ANNEE_INDEXATION_L12884,5000);
        assertThat(valeurIndexee).isEqualTo(5179);
    }

    @Test
    @DisplayName("Le montant maximal de la déduction pour les frais de formation et de formation continue à des fins professionnelles prévu par l'article 36B de la loi s'élève à 12 791 francs.")
    public void plafondFraisProfessionnelsFormation() {
        int valeurIndexee = indexer(ANNEE_INDEXATION_L11667,12_000);
        assertThat(valeurIndexee).isEqualTo(12_791);
    }

    @Test
    @DisplayName("La déduction pour charges de famille prévue à l'article 39, alinéa 1, de la loi est adaptée comme suit : 6 849 francs pour chaque demi-charge de famille")
    public void deductionSocialesDemiChargesFamilles() {
        int valeurIndexee = indexer(ANNEE_INDEXATION_L12314,6500);
        assertThat(valeurIndexee).isEqualTo(6849);
    }

    @Test
    @DisplayName("La déduction pour charges de famille prévue à l'article 39, alinéa 1, de la loi est adaptée comme suit : lorsque le contribuable fait valoir pour la charge de famille une déduction pour frais de garde des enfants conformément à l'article 35 de la loi, ce montant est réduit à 5 268 francs.")
    public void deductionSocialesDemiChargesFamillesSiFraisDeGarde() {
        int valeurIndexee = indexer(ANNEE_INDEXATION_L12314,5000);
        assertThat(valeurIndexee).isEqualTo(5268);
    }

    @Test
    @DisplayName("La déduction pour charges de famille prévue à l'article 39, alinéa 2, de la loi est adaptée comme suit : enfant mineur : gain annuel de 16 391 francs")
    public void revenuMaximalAnnuelEnfantMineurParCharge() {
        int valeurIndexee = indexer(15_333);
        assertThat(valeurIndexee).isEqualTo(16_391);
    }

    @Test
    @DisplayName("La déduction pour charges de famille prévue à l'article 39, alinéa 2, de la loi est adaptée comme suit : gain annuel de 24 587 francs (demi-charge);")
    public void revenuMaximalAnnuelEnfantMineurParDemiCharge() {
        int valeurIndexee = indexer(23_000);
        assertThat(valeurIndexee).isEqualTo(24_587);
    }

    @Test
    @DisplayName("La déduction pour charges de famille prévue à l'article 39, alinéa 2, de la loi est adaptée comme suit : enfant majeur, jusqu'à 25 ans, apprenti ou étudiant au sens de la loi : fortune de 93 537 francs")
    public void fortuneMaximaleEnfantMajeurJusqua25ansApprentiEtudiant() {
        int valeurIndexee = indexer(87_500);
        assertThat(valeurIndexee).isEqualTo(93_537);
    }

    @Test
    @DisplayName("La déduction pour charges de famille prévue à l'article 39, alinéa 2, de la loi est adaptée comme suit : enfant majeur, jusqu'à 25 ans, apprenti ou étudiant au sens de la loi : revenu de 16 391 francs (charge entière)")
    public void revenuMaximalEnfantMajeurJusqua25ansApprentiEtudiantParCharge() {
        revenuMaximalAnnuelEnfantMineurParCharge();
    }

    @Test
    @DisplayName("La déduction pour charges de famille prévue à l'article 39, alinéa 2, de la loi est adaptée comme suit : enfant majeur, jusqu'à 25 ans, apprenti ou étudiant au sens de la loi : revenu de 24 587 francs (demi-charge);")
    public void revenuMaximalEnfantMajeurJusqua25ansApprentiEtudiantParDemiCharge() {
        revenuMaximalAnnuelEnfantMineurParDemiCharge();
    }

    @Test
    @DisplayName("La déduction pour charges de famille prévue à l'article 39, alinéa 2, de la loi est adaptée comme suit : proche incapable de subvenir entièrement à ses besoins : fortune de 93 537 francs")
    public void fortuneMaximalePersonneNecessiteuse() {
        fortuneMaximaleEnfantMajeurJusqua25ansApprentiEtudiant();
    }

    @Test
    @DisplayName("La déduction pour charges de famille prévue à l'article 39, alinéa 2, de la loi est adaptée comme suit : proche incapable de subvenir entièrement à ses besoins : revenu de 16 391 francs (charge entière)")
    public void revenuMaximalePersonneNecessiteuseParCharge() {
        revenuMaximalAnnuelEnfantMineurParCharge();
    }

    @Test
    @DisplayName("La déduction pour charges de famille prévue à l'article 39, alinéa 2, de la loi est adaptée comme suit : proche incapable de subvenir entièrement à ses besoins : revenu de 24 587 francs (demi-charge)." )
    public void revenuMaximalePersonneNecessiteuseParDemiCharge() {
        revenuMaximalAnnuelEnfantMineurParDemiCharge();
    }

    @Test
    @DisplayName("La déduction pour bénéficiaire de rentes AVS ou AI à l'article 40, alinéa 1 couple avec un seul rentier" )
    public void deductionBeneficiairesRentesAVSouAICoupleAvecUnSeulRentier() {
        FournisseurIndexGenevois fournisseur = new FournisseurIndexGenevoisEnMemoire();
        Indexateur indexateur = new IndexateurGenevois(fournisseur);
        BaremeParTranche baremeAdapte = indexateur.indexer(ANNEE_INDEXATION_LIPP_D_3_08, construireBaremeArt40Alinea1LIPPUnSeulRentier(), ANNEE_FISCALE);
        BaremeParTranche baremeAttendu = unBareme()
                .jusqua(61_467).valeur(10_690)
                .de(61_467).a(69_699).valeur(8552)
                .de(69_699).a(78_678).valeur(6414)
                .de(78_678).a(87_872).valeur(4276)
                .de(87_872).a(98_348).valeur(2138)
                .plusDe(98_348).valeur(0).construire();
        BaremeParTrancheAssert.assertThat(baremeAdapte).isEqualTo(baremeAttendu);
    }

    @Test
    @DisplayName("La déduction pour bénéficiaire de rentes AVS ou AI à l'article 40, alinéa 1 couple avec deux rentiers" )
    public void deductionBeneficiairesRentesAVSouAICoupleAvecDeuxRentiers() {
        FournisseurIndexGenevois fournisseur = new FournisseurIndexGenevoisEnMemoire();
        Indexateur indexateur = new IndexateurGenevois(fournisseur);
        BaremeParTranche baremeLIPP2009 = construireBaremeArt40Alinea1LIPPDeuxRentiers();
        BaremeParTranche baremeAdapte = indexateur.indexer(ANNEE_INDEXATION_LIPP_D_3_08, baremeLIPP2009, ANNEE_FISCALE);
        BaremeParTranche baremeAttendu = unBareme()
                .jusqua(61_467).valeur(12_293)
                .de(61_467).a(69_699).valeur(9835)
                .de(69_699).a(78_678).valeur(7376)
                .de(78_678).a(87_872).valeur(4917)
                .de(87_872).a(98_348).valeur(2459)
                .plusDe(98_348).valeur(0).construire();
        BaremeParTrancheAssert.assertThat(baremeAdapte).isEqualTo(baremeAttendu);
    }

    @Test
    @DisplayName("La déduction pour bénéficiaire de rentes AVS ou AI à l'article 40, alinéa 3 personne seule" )
    public void deductionBeneficiairesRentesAVSouAIPersonneSeule() {
        FournisseurIndexGenevois fournisseur = new FournisseurIndexGenevoisEnMemoire();
        Indexateur indexateur = new IndexateurGenevois(fournisseur);
        BaremeParTranche baremeLIPP2009 = this.construireBaremeArt40Alinea3LIPP();
        BaremeParTranche baremeAdapte = indexateur.indexer(ANNEE_INDEXATION_LIPP_D_3_08, baremeLIPP2009, ANNEE_FISCALE);
        BaremeParTranche baremeAttendu = unBareme()
                .jusqua(53_450).valeur(10_690)
                .de(53_450).a(60_612).valeur(8552)
                .de(60_612).a(68_416).valeur(6414)
                .de(68_416).a(76_433).valeur(4276)
                .de(76_433).a(85_520).valeur(2138)
                .plusDe(85_520).valeur(0).construire();
        BaremeParTrancheAssert.assertThat(baremeAdapte).isEqualTo(baremeAttendu);
    }

    @Test
    @DisplayName("Le montant déterminant pour l'imposition des bijoux et de l'argenterie prévu à l'article 47, lettre h, de la loi s'élève à 2 138 francs.")
    public void valeurAPartirDeLaquelleLesBijouxEtLArgenterieEstImposable() {
        int valeurIndexee = indexer(2000);
        assertThat(valeurIndexee).isEqualTo(2138);
    }

    @Test
    @DisplayName("Les déductions sociales prévues à l'article 58, alinéa 1, de la loi sont adaptées comme suit : 87 872 francs pour le contribuable célibataire, veuf, séparé de corps ou de fait ou divorcé;")
    public void deductionsSocialesFortunePersonneSeule() {
        int valeurIndexee = indexer(82_200);
        assertThat(valeurIndexee).isEqualTo(87_872);
    }

    @Test
    @DisplayName("Les déductions sociales prévues à l'article 58, alinéa 1, de la loi sont adaptées comme suit : 175 743 francs pour les ...")
    public void deductionsSocialesFortuneCouple() {
        int valeurIndexee = indexer(164_400);
        assertThat(valeurIndexee).isEqualTo(175_743);
    }

    @Test
    @DisplayName("Les déductions sociales prévues à l'article 58, alinéa 1, de la loi sont adaptées comme suit : 43 936 francs pour chaque charge de famille")
    public void deductionsSocialesFortuneParCharge() {
        int valeurIndexee = indexer(41_100);
        assertThat(valeurIndexee).isEqualTo(43_936);
    }

    @Test
    @DisplayName("Le montant maximal de la déduction sur les éléments de fortune investis dans l'exploitation commerciale, artisanale ou industrielle, prévu à l'article 58, alinéa 2, de la loi s'élève à 534 500 francs.")
    public void deductionsSocialesFortuneIndependants() {
        int valeurIndexee = indexer(500_000);
        assertThat(valeurIndexee).isEqualTo(534_500);
    }
}
