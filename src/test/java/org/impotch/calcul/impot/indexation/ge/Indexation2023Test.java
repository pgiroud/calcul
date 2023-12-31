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
package org.impotch.calcul.impot.indexation.ge;

import org.impotch.bareme.BaremeParTranche;
import org.impotch.calcul.impot.indexation.Indexateur;
import org.impotch.util.TypeArrondi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.impotch.bareme.ConstructeurBareme.unBareme;
import static org.impotch.bareme.BaremeParTrancheAssert.assertThat;


public class Indexation2023Test {

    private static final int ANNEE_INDEXATION_LIPP_D_3_08 = 2009;
    private static final int ANNEE = 2023;

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
    @DisplayName("Le montant minimal de la dépense prévu à l'article 14, alinéa 3, lettre a, de la loi s'élève à 404 651 francs.")
    public void indexationMontantMinimalDeLaDepense() {
        FournisseurIndexGenevois fournisseur = new FournisseurIndexGenevoisEnMemoire();
        Indexateur indexateur = new IndexateurGenevois(fournisseur);
        int anneeIndexationLoi11683 = 2016; // Voir loi 11683
        assertThat(indexateur.indexer(anneeIndexationLoi11683, BigDecimal.valueOf(400_000), ANNEE)).isEqualTo(BigDecimal.valueOf(404_651));
    }


    @Test
    @DisplayName("Le montant maximal de l'exonération de la solde des sapeurs-pompiers de milice prévu à l'article 27, lettre o, de la loi s'élève à 9 052 francs.")
    public void montantMaxExonerationSoldeSapeurPompier() {
        FournisseurIndexGenevois fournisseur = new FournisseurIndexGenevoisEnMemoire();
        Indexateur indexateur = new IndexateurGenevois(fournisseur);
        int anneeIndexationLoi10905 = 2014; // Voir lois 10905, 12884
        assertThat(indexateur.indexer(anneeIndexationLoi10905, BigDecimal.valueOf(9_000), ANNEE)).isEqualTo(BigDecimal.valueOf(9_052));
    }

    @Test
    @DisplayName("Déductions liées à l'exercice d'une activité lucrative dépendante (art. 29 de la loi)")
    public void plancherFraisForfaitaire() {
        FournisseurIndexGenevois fournisseur = new FournisseurIndexGenevoisEnMemoire();
        Indexateur indexateur = new IndexateurGenevois(fournisseur);
        assertThat(indexateur.indexer(ANNEE_INDEXATION_LIPP_D_3_08, BigDecimal.valueOf(600), ANNEE)).isEqualTo(BigDecimal.valueOf(609));
    }

    @Test
    @DisplayName("Déductions liées à l'exercice d'une activité lucrative dépendante (art. 29 de la loi)")
    public void plafondFraisForfaitaire() {
        FournisseurIndexGenevois fournisseur = new FournisseurIndexGenevoisEnMemoire();
        Indexateur indexateur = new IndexateurGenevois(fournisseur);
        assertThat(indexateur.indexer(ANNEE_INDEXATION_LIPP_D_3_08, BigDecimal.valueOf(1700), ANNEE)).isEqualTo(BigDecimal.valueOf(1725));
    }

    @Test
    @DisplayName("Le montant maximal de la déduction des primes d'assurances sur la vie et des intérêts échus des capitaux d'épargne prévu à l'article 31, lettre d, de la loi s'élève à : 3 348 francs pour les époux vivant en ménage commun")
    public void deductionPrevoyancePourEpouxVivantEnMenageCommun() {
        FournisseurIndexGenevois fournisseur = new FournisseurIndexGenevoisEnMemoire();
        Indexateur indexateur = new IndexateurGenevois(fournisseur);
        assertThat(indexateur.indexer(ANNEE_INDEXATION_LIPP_D_3_08, BigDecimal.valueOf(3300), ANNEE)).isEqualTo(BigDecimal.valueOf(3348));
    }

    @Test
    @DisplayName("Le montant maximal de la déduction des primes d'assurances sur la vie et des intérêts échus des capitaux d'épargne prévu à l'article 31, lettre d, de la loi s'élève à : 2 232 francs pour le contribuable célibataire, veuf, divorcé, séparé de corps ou de fait.")
    public void deductionPrevoyancePourContribuableSeul() {
        FournisseurIndexGenevois fournisseur = new FournisseurIndexGenevoisEnMemoire();
        Indexateur indexateur = new IndexateurGenevois(fournisseur);
        assertThat(indexateur.indexer(ANNEE_INDEXATION_LIPP_D_3_08, BigDecimal.valueOf(2200), ANNEE)).isEqualTo(BigDecimal.valueOf(2232));
    }

    @Test
    @DisplayName("Le montant maximal de la déduction des primes d'assurances sur la vie et des intérêts échus des capitaux d'épargne prévu à l'article 31, lettre d, de la loi s'élève à : 913 francs supplémentaires pour chaque charge de famille.")
    public void deductionPrevoyanceParCharge() {
        FournisseurIndexGenevois fournisseur = new FournisseurIndexGenevoisEnMemoire();
        Indexateur indexateur = new IndexateurGenevois(fournisseur);
        assertThat(indexateur.indexer(ANNEE_INDEXATION_LIPP_D_3_08, BigDecimal.valueOf(900), ANNEE)).isEqualTo(BigDecimal.valueOf(913));
    }

    @Test
    @DisplayName("Le montant maximal de la déduction des primes d'assurances sur la vie et des intérêts échus des capitaux d'épargne prévu à l'article 31, lettre d, de la loi s'élève à : 1 370 francs lorsque, au sein du couple, un seul des deux conjoints est affilié à une telle institution.")
    public void deductionPrevoyanceParChargeQuandUnSeulDesMembresDuCoupleCotise() {
        FournisseurIndexGenevois fournisseur = new FournisseurIndexGenevoisEnMemoire();
        Indexateur indexateur = new IndexateurGenevois(fournisseur);
        assertThat(indexateur.indexer(ANNEE_INDEXATION_LIPP_D_3_08, BigDecimal.valueOf(1350), ANNEE)).isEqualTo(BigDecimal.valueOf(1370));
    }

    @Test
    @DisplayName("Le montant maximal de la déduction pour frais de garde par enfant concerné prévu à l'article 35 de la loi s'élève à 25 048 francs.")
    public void deductionFraisGardeEnfants() {
        // Loi 12248 Pour une vrai déduction fiscale des frais de garde de nos enfants du 21 septembre 2018
        FournisseurIndexGenevois fournisseur = new FournisseurIndexGenevoisEnMemoire();
        Indexateur indexateur = new IndexateurGenevois(fournisseur);
        int anneeIndexationLoi12248 = 2019;
        assertThat(indexateur.indexer(anneeIndexationLoi12248, BigDecimal.valueOf(25000), ANNEE)).isEqualTo(BigDecimal.valueOf(25048));
    }

    @Test
    @DisplayName("La déduction en cas d'activité lucrative des deux conjoints prévue par l'article 36 de la loi s'élève à 1 000 francs.")
    public void deductionDoubleActivite() {
        // Loi 12314 Moins d’impôts pour les familles ! du 17 octobre 2019
        FournisseurIndexGenevois fournisseur = new FournisseurIndexGenevoisEnMemoire();
        Indexateur indexateur = new IndexateurGenevois(fournisseur);
        int anneeIndexationLoi12314 = 2021;
        assertThat(indexateur.indexer(anneeIndexationLoi12314, BigDecimal.valueOf(1000), ANNEE)).isEqualTo(BigDecimal.valueOf(1000));
    }

    @Test
    @DisplayName("Le montant maximal de la déduction à titre de mise sur les gains de loterie prévu par l'article 36A de la loi s'élève à 5 029 francs.")
    public void plafondGainsDeLoterie() {
        // Loi 12884 Gains réalisés à des jeux d’argent du 2 septembre 2022
        FournisseurIndexGenevois fournisseur = new FournisseurIndexGenevoisEnMemoire();
        Indexateur indexateur = new IndexateurGenevois(fournisseur);
        int anneeIndexationLoi12884 = 2014;
        assertThat(indexateur.indexer(anneeIndexationLoi12884, BigDecimal.valueOf(5000), ANNEE)).isEqualTo(BigDecimal.valueOf(5029));

    }

    @Test
    @DisplayName("Le montant maximal de la déduction pour les frais de formation et de formation continue à des fins professionnelles prévu par l'article 36B de la loi s'élève à 12 140 francs.")
    public void plafondFraisProfessionnelsFormation() {
        // Loi 11667 Déduction des frais de formation et de perfectionnement selon le droit fédéral harmonisé du 18 septembre 2015
        FournisseurIndexGenevois fournisseur = new FournisseurIndexGenevoisEnMemoire();
        Indexateur indexateur = new IndexateurGenevois(fournisseur);
        int anneeIndexationLoi11667 = 2016;
        assertThat(indexateur.indexer(anneeIndexationLoi11667, BigDecimal.valueOf(12_000), ANNEE)).isEqualTo(BigDecimal.valueOf(12_140));
    }
    @Test
    @DisplayName("La déduction pour charges de famille prévue à l'article 39, alinéa 1, de la loi est adaptée comme suit : 13 000 francs pour chaque charge de famille")
    public void deductionSocialesChargesFamilles() {
        // Loi 12314 Moins d’impôts pour les familles ! du 17 octobre 2019
        FournisseurIndexGenevois fournisseur = new FournisseurIndexGenevoisEnMemoire();
        Indexateur indexateur = new IndexateurGenevois(fournisseur);
        int anneeIndexationLoi12314 = 2021;
        assertThat(indexateur.indexer(anneeIndexationLoi12314, BigDecimal.valueOf(13_000), ANNEE)).isEqualTo(BigDecimal.valueOf(13_000));
    }

    @Test
    @DisplayName("La déduction pour charges de famille prévue à l'article 39, alinéa 1, de la loi est adaptée comme suit : lorsque le contribuable fait valoir pour la charge de famille une déduction pour frais de garde des enfants conformément à l'article 35 de la loi, ce montant est réduit à 10 000 francs;")
    public void deductionSocialesChargesFamillesSiFraisDeGarde() {
        // Loi 12314 Moins d’impôts pour les familles ! du 17 octobre 2019
        FournisseurIndexGenevois fournisseur = new FournisseurIndexGenevoisEnMemoire();
        Indexateur indexateur = new IndexateurGenevois(fournisseur);
        int anneeIndexationLoi12314 = 2021;
        assertThat(indexateur.indexer(anneeIndexationLoi12314, BigDecimal.valueOf(10_000), ANNEE)).isEqualTo(BigDecimal.valueOf(10_000));
    }

    @Test
    @DisplayName("La déduction pour charges de famille prévue à l'article 39, alinéa 1, de la loi est adaptée comme suit : 6 500 francs pour chaque demi-charge de famille")
    public void deductionSocialesDemiChargesFamilles() {
        // Loi 12314 Moins d’impôts pour les familles ! du 17 octobre 2019
        FournisseurIndexGenevois fournisseur = new FournisseurIndexGenevoisEnMemoire();
        Indexateur indexateur = new IndexateurGenevois(fournisseur);
        int anneeIndexationLoi12314 = 2021;
        assertThat(indexateur.indexer(anneeIndexationLoi12314, BigDecimal.valueOf(6500), ANNEE)).isEqualTo(BigDecimal.valueOf(6500));
    }

    @Test
    @DisplayName("La déduction pour charges de famille prévue à l'article 39, alinéa 1, de la loi est adaptée comme suit : lorsque le contribuable fait valoir pour la charge de famille une déduction pour frais de garde des enfants conformément à l'article 35 de la loi, ce montant est réduit à 5 000 francs.")
    public void deductionSocialesDemiChargesFamillesSiFraisDeGarde() {
        // Loi 12314 Moins d’impôts pour les familles ! du 17 octobre 2019
        FournisseurIndexGenevois fournisseur = new FournisseurIndexGenevoisEnMemoire();
        Indexateur indexateur = new IndexateurGenevois(fournisseur);
        int anneeIndexationLoi12314 = 2021;
        assertThat(indexateur.indexer(anneeIndexationLoi12314, BigDecimal.valueOf(5000), ANNEE)).isEqualTo(BigDecimal.valueOf(5000));
    }

    @Test
    @DisplayName("La déduction pour charges de famille prévue à l'article 39, alinéa 2, de la loi est adaptée comme suit : enfant mineur : gain annuel de 15 557 francs")
    public void revenuMaximalAnnuelEnfantMineurParCharge() {
        FournisseurIndexGenevois fournisseur = new FournisseurIndexGenevoisEnMemoire();
        Indexateur indexateur = new IndexateurGenevois(fournisseur);
        assertThat(indexateur.indexer(ANNEE_INDEXATION_LIPP_D_3_08, BigDecimal.valueOf(15_333), ANNEE)).isEqualTo(BigDecimal.valueOf(15_557));
    }

    @Test
    @DisplayName("La déduction pour charges de famille prévue à l'article 39, alinéa 2, de la loi est adaptée comme suit : gain annuel de 23 335 francs (demi-charge);")
    public void revenuMaximalAnnuelEnfantMineurParDemiCharge() {
        FournisseurIndexGenevois fournisseur = new FournisseurIndexGenevoisEnMemoire();
        Indexateur indexateur = new IndexateurGenevois(fournisseur);
        assertThat(indexateur.indexer(ANNEE_INDEXATION_LIPP_D_3_08, BigDecimal.valueOf(23_000), ANNEE)).isEqualTo(BigDecimal.valueOf(23_335));
    }

    @Test
    @DisplayName("La déduction pour charges de famille prévue à l'article 39, alinéa 2, de la loi est adaptée comme suit : enfant majeur, jusqu'à 25 ans, apprenti ou étudiant au sens de la loi : fortune de 88 776 francs")
    public void fortuneMaximaleEnfantMajeurJusqua25ansApprentiEtudiant() {
        FournisseurIndexGenevois fournisseur = new FournisseurIndexGenevoisEnMemoire();
        Indexateur indexateur = new IndexateurGenevois(fournisseur);
        assertThat(indexateur.indexer(ANNEE_INDEXATION_LIPP_D_3_08, BigDecimal.valueOf(87_500), ANNEE)).isEqualTo(BigDecimal.valueOf(88_776));
    }

    @Test
    @DisplayName("La déduction pour charges de famille prévue à l'article 39, alinéa 2, de la loi est adaptée comme suit : enfant majeur, jusqu'à 25 ans, apprenti ou étudiant au sens de la loi : revenu de 15 557 francs (charge entière)")
    public void revenuMaximalEnfantMajeurJusqua25ansApprentiEtudiantParCharge() {
        FournisseurIndexGenevois fournisseur = new FournisseurIndexGenevoisEnMemoire();
        Indexateur indexateur = new IndexateurGenevois(fournisseur);
        assertThat(indexateur.indexer(ANNEE_INDEXATION_LIPP_D_3_08, BigDecimal.valueOf(15_333), ANNEE)).isEqualTo(BigDecimal.valueOf(15_557));
    }

    @Test
    @DisplayName("La déduction pour charges de famille prévue à l'article 39, alinéa 2, de la loi est adaptée comme suit : enfant majeur, jusqu'à 25 ans, apprenti ou étudiant au sens de la loi : revenu de 23 335 francs (demi-charge);")
    public void revenuMaximalEnfantMajeurJusqua25ansApprentiEtudiantParDemiCharge() {
        FournisseurIndexGenevois fournisseur = new FournisseurIndexGenevoisEnMemoire();
        Indexateur indexateur = new IndexateurGenevois(fournisseur);
        assertThat(indexateur.indexer(ANNEE_INDEXATION_LIPP_D_3_08, BigDecimal.valueOf(23_000), ANNEE)).isEqualTo(BigDecimal.valueOf(23_335));
    }

    @Test
    @DisplayName("La déduction pour charges de famille prévue à l'article 39, alinéa 2, de la loi est adaptée comme suit : proche incapable de subvenir entièrement à ses besoins : fortune de 88 776 francs")
    public void fortuneMaximalePersonneNecessiteuse() {
        FournisseurIndexGenevois fournisseur = new FournisseurIndexGenevoisEnMemoire();
        Indexateur indexateur = new IndexateurGenevois(fournisseur);
        assertThat(indexateur.indexer(ANNEE_INDEXATION_LIPP_D_3_08, BigDecimal.valueOf(87_500), ANNEE)).isEqualTo(BigDecimal.valueOf(88_776));
    }

    @Test
    @DisplayName("La déduction pour charges de famille prévue à l'article 39, alinéa 2, de la loi est adaptée comme suit : proche incapable de subvenir entièrement à ses besoins : revenu de 15 557 francs (charge entière)")
    public void revenuMaximalePersonneNecessiteuseParCharge() {
        FournisseurIndexGenevois fournisseur = new FournisseurIndexGenevoisEnMemoire();
        Indexateur indexateur = new IndexateurGenevois(fournisseur);
        assertThat(indexateur.indexer(ANNEE_INDEXATION_LIPP_D_3_08, BigDecimal.valueOf(15_333), ANNEE)).isEqualTo(BigDecimal.valueOf(15_557));
    }

    @Test
    @DisplayName("La déduction pour charges de famille prévue à l'article 39, alinéa 2, de la loi est adaptée comme suit : proche incapable de subvenir entièrement à ses besoins : revenu de 23 335 francs (demi-charge)." )
    public void revenuMaximalePersonneNecessiteuseParDemiCharge() {
        FournisseurIndexGenevois fournisseur = new FournisseurIndexGenevoisEnMemoire();
        Indexateur indexateur = new IndexateurGenevois(fournisseur);
        assertThat(indexateur.indexer(ANNEE_INDEXATION_LIPP_D_3_08, BigDecimal.valueOf(23_000), ANNEE)).isEqualTo(BigDecimal.valueOf(23_335));
    }

    @Test
    @DisplayName("La déduction pour bénéficiaire de rentes AVS ou AI à l'article 40, alinéa 1 couple avec un seul rentier" )
    public void deductionBeneficiairesRentesAVSouAICoupleAvecUnSeulRentier() {
        FournisseurIndexGenevois fournisseur = new FournisseurIndexGenevoisEnMemoire();
        Indexateur indexateur = new IndexateurGenevois(fournisseur);
        BaremeParTranche baremeAdapte = indexateur.indexer(ANNEE_INDEXATION_LIPP_D_3_08, construireBaremeArt40Alinea1LIPPUnSeulRentier(),ANNEE);
        BaremeParTranche baremeAttendu = unBareme()
                .jusqua(58_338).valeur(10_146)
                .de(58_338).a(66_150).valeur(8117)
                .de(66_150).a(74_673).valeur(6087)
                .de(74_673).a(83_398).valeur(4058)
                .de(83_398).a(93_341).valeur(2029)
                .plusDe(93_341).valeur(0).construire();
        assertThat(baremeAdapte).isEqualTo(baremeAttendu);
    }

    @Test
    @DisplayName("La déduction pour bénéficiaire de rentes AVS ou AI à l'article 40, alinéa 1 couple avec deux rentiers" )
    public void deductionBeneficiairesRentesAVSouAICoupleAvecDeuxRentiers() {
        FournisseurIndexGenevois fournisseur = new FournisseurIndexGenevoisEnMemoire();
        Indexateur indexateur = new IndexateurGenevois(fournisseur);
        BaremeParTranche baremeLIPP2009 = construireBaremeArt40Alinea1LIPPDeuxRentiers();
        BaremeParTranche baremeAdapte = indexateur.indexer(ANNEE_INDEXATION_LIPP_D_3_08, baremeLIPP2009,ANNEE);
        BaremeParTranche baremeAttendu = unBareme()
                .jusqua(58_338).valeur(11_668)
                .de(58_338).a(66_150).valeur(9334)
                .de(66_150).a(74_673).valeur(7001)
                .de(74_673).a(83_398).valeur(4667)
                .de(83_398).a(93_341).valeur(2334)
                .plusDe(93_341).valeur(0).construire();
        assertThat(baremeAdapte).isEqualTo(baremeAttendu);
    }

    @Test
    @DisplayName("La déduction pour bénéficiaire de rentes AVS ou AI à l'article 40, alinéa 3 personne seule" )
    public void deductionBeneficiairesRentesAVSouAIPersonneSeule() {
        FournisseurIndexGenevois fournisseur = new FournisseurIndexGenevoisEnMemoire();
        Indexateur indexateur = new IndexateurGenevois(fournisseur);
        BaremeParTranche baremeLIPP2009 = this.construireBaremeArt40Alinea3LIPP();
        BaremeParTranche baremeAdapte = indexateur.indexer(ANNEE_INDEXATION_LIPP_D_3_08, baremeLIPP2009, ANNEE);
        BaremeParTranche baremeAttendu = unBareme()
                .jusqua(50_729).valeur(10_146)
                .de(50_729).a(57_527).valeur(8117)
                .de(57_527).a(64_933).valeur(6087)
                .de(64_933).a(72_542).valeur(4058)
                .de(72_542).a(81_166).valeur(2029)
                .plusDe(81_166).valeur(0).construire();
        assertThat(baremeAdapte).isEqualTo(baremeAttendu);
    }

    @Test
    @DisplayName("Le montant déterminant pour l'imposition des bijoux et de l'argenterie prévu à l'article 47, lettre h, de la loi s'élève à 2 029 francs.")
    public void valeurAPartirDeLaquelleLesBijouxEtLArgenterieEstImposable() {
        FournisseurIndexGenevois fournisseur = new FournisseurIndexGenevoisEnMemoire();
        Indexateur indexateur = new IndexateurGenevois(fournisseur);
        assertThat(indexateur.indexer(ANNEE_INDEXATION_LIPP_D_3_08, BigDecimal.valueOf(2000), ANNEE)).isEqualTo(BigDecimal.valueOf(2029));
    }

    @Test
    @DisplayName("Les déductions sociales prévues à l'article 58, alinéa 1, de la loi sont adaptées comme suit : 83 398 francs pour le contribuable célibataire, veuf, séparé de corps ou de fait ou divorcé;")
    public void deductionsSocialesFortunePersonneSeule() {
        FournisseurIndexGenevois fournisseur = new FournisseurIndexGenevoisEnMemoire();
        Indexateur indexateur = new IndexateurGenevois(fournisseur);
        assertThat(indexateur.indexer(ANNEE_INDEXATION_LIPP_D_3_08, BigDecimal.valueOf(82_200), ANNEE)).isEqualTo(BigDecimal.valueOf(83_398));
    }

    @Test
    @DisplayName("Les déductions sociales prévues à l'article 58, alinéa 1, de la loi sont adaptées comme suit : 166 797 francs pour les ...")
    public void deductionsSocialesFortuneCouple() {
        FournisseurIndexGenevois fournisseur = new FournisseurIndexGenevoisEnMemoire();
        Indexateur indexateur = new IndexateurGenevois(fournisseur);
        assertThat(indexateur.indexer(ANNEE_INDEXATION_LIPP_D_3_08, BigDecimal.valueOf(164_400), ANNEE)).isEqualTo(BigDecimal.valueOf(166_797));
    }

    @Test
    @DisplayName("Les déductions sociales prévues à l'article 58, alinéa 1, de la loi sont adaptées comme suit : 41 699 francs pour chaque charge de famille")
    public void deductionsSocialesFortuneParCharge() {
        FournisseurIndexGenevois fournisseur = new FournisseurIndexGenevoisEnMemoire();
        Indexateur indexateur = new IndexateurGenevois(fournisseur);
        assertThat(indexateur.indexer(ANNEE_INDEXATION_LIPP_D_3_08, BigDecimal.valueOf(41_100), ANNEE)).isEqualTo(BigDecimal.valueOf(41_699));
    }

    @Test
    @DisplayName("Le montant maximal de la déduction sur les éléments de fortune investis dans l'exploitation commerciale, artisanale ou industrielle, prévu à l'article 58, alinéa 2, de la loi s'élève à 507 289 francs.")
    public void deductionsSocialesFortuneIndependants() {
        FournisseurIndexGenevois fournisseur = new FournisseurIndexGenevoisEnMemoire();
        Indexateur indexateur = new IndexateurGenevois(fournisseur);
        assertThat(indexateur.indexer(ANNEE_INDEXATION_LIPP_D_3_08, BigDecimal.valueOf(500_000), ANNEE)).isEqualTo(BigDecimal.valueOf(507_289));
    }

}
