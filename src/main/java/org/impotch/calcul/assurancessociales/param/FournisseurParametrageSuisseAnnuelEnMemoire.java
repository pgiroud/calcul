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
package org.impotch.calcul.assurancessociales.param;

import java.util.Optional;
import static org.impotch.calcul.assurancessociales.param.ParametrageSuisseAnnuelEnMemoire.unConstructeur;

class FournisseurParametrageSuisseAnnuelEnMemoire implements FournisseurParametrageSuisseAnnuel {
    @Override
    public Optional<ParametrageSuisseAnnuel> parametrage(int annee) {
        return construireParametrageAnnuel(annee);
    }

    private Optional<ParametrageSuisseAnnuel> construireParametrageAnnuel(int annee) {
        if (2025 == annee) return Optional.of(construireParametrage2025());
        if (2024 == annee) return Optional.of(construireParametrage2024());
        if (2023 == annee) return Optional.of(construireParametrage2023());
        if (2022 == annee) return Optional.of(construireParametrage2022());
        if (2021 == annee) return Optional.of(construireParametrage2021());
        if (2020 == annee) return Optional.of(construireParametrage2020());
        if (2019 == annee) return Optional.of(construireParametrage2019());
        if (2018 == annee) return Optional.of(construireParametrage2018());
        if (2017 == annee) return Optional.of(construireParametrage2017());
        if (2016 == annee) return Optional.of(construireParametrage2016());
        if (2015 == annee) return Optional.of(construireParametrage2015());
        if (2014 == annee) return Optional.of(construireParametrage2014());
        if (2013 == annee) return Optional.of(construireParametrage2013());
        if (2012 == annee) return Optional.of(construireParametrage2012());
        if (2011 == annee) return Optional.of(construireParametrage2011());
        if (2010 == annee) return Optional.of(construireParametrage2010());
        if (2009 == annee) return Optional.of(construireParametrage2009());
        if (2008 == annee) return Optional.of(construireParametrage2008());
        if (2007 == annee) return Optional.of(construireParametrage2007());
        if (2006 == annee) return Optional.of(construireParametrage2006());
        if (2005 == annee) return Optional.of(construireParametrage2005());
        return Optional.empty();
    }

    private ParametrageSuisseAnnuel construireParametrage2025() {
        return unConstructeur(2025)
                .montantMaxAssure(148_200).avs("8.7 %").ai("1.4 %").apg("0.5 %")
                .ac("2.2 %")
                .renteMensuelleMinimum(1260)
                .cons();
    }

    private ParametrageSuisseAnnuel construireParametrage2024() {
        return unConstructeur(2024)
                .montantMaxAssure(148_200).avs("8.7 %").ai("1.4 %").apg("0.5 %")
                .ac("2.2 %")
                .renteMensuelleMinimum(1225)
                .cons();
    }

    private ParametrageSuisseAnnuel construireParametrage2023() {
        // Suppression de la participation des hauts revenus dans l'assurance chômage
        return unConstructeur(2023)
                .montantMaxAssure(148_200).avs("8.7 %").ai("1.4 %").apg("0.5 %")
                .ac("2.2 %")
                .renteMensuelleMinimum(1225)
                .cons();
    }
    private ParametrageSuisseAnnuel construireParametrage2022() {
        return unConstructeur(2022)
                .montantMaxAssure(148_200).avs("8.7 %").ai("1.4 %").apg("0.5 %")
                .ac("2.2 %").avecParticipationRevenuAuDelaDuMontantMaxAssure().taux("1 %")
                .renteMensuelleMinimum(1195)
                .cons();
    }
    private ParametrageSuisseAnnuel construireParametrage2021() {
        // Augmentation taux APG : 0.45 -> 0.5
        return unConstructeur(2021)
                .montantMaxAssure(148_200).avs("8.7 %").ai("1.4 %").apg("0.5 %")
                .ac("2.2 %").avecParticipationRevenuAuDelaDuMontantMaxAssure().taux("1 %")
                .renteMensuelleMinimum(1195)
                .cons();
    }
    private ParametrageSuisseAnnuel construireParametrage2020() {
        // Augmentation du taux AVS : 8.4 -> 8.7

        return unConstructeur(2020)
                .montantMaxAssure(148_200).avs("8.7 %").ai("1.4 %").apg("0.45 %")
                .ac("2.2 %").avecParticipationRevenuAuDelaDuMontantMaxAssure().taux("1 %")
                .renteMensuelleMinimum(1185)
                .cons();
    }
    private ParametrageSuisseAnnuel construireParametrage2019() {
        return unConstructeur(2019)
                .montantMaxAssure(148_200).avs("8.4 %").ai("1.4 %").apg("0.45 %")
                .ac("2.2 %").avecParticipationRevenuAuDelaDuMontantMaxAssure().taux("1 %")
                .renteMensuelleMinimum(1185)
                .cons();
    }
    private ParametrageSuisseAnnuel construireParametrage2018() {
        return unConstructeur(2018)
                .montantMaxAssure(148_200).avs("8.4 %").ai("1.4 %").apg("0.45 %")
                .ac("2.2 %").avecParticipationRevenuAuDelaDuMontantMaxAssure().taux("1 %")
                .renteMensuelleMinimum(1175)
                .cons();
    }
    private ParametrageSuisseAnnuel construireParametrage2017() {
        return unConstructeur(2017)
                .montantMaxAssure(148_200).avs("8.4 %").ai("1.4 %").apg("0.45 %")
                .ac("2.2 %").avecParticipationRevenuAuDelaDuMontantMaxAssure().taux("1 %")
                .renteMensuelleMinimum(1175)
                .cons();
    }
    private ParametrageSuisseAnnuel construireParametrage2016() {
        // Diminution taux APG : 0.5 -> 0.45
        // Augmentation du montant maximum assuré : 126'000 -> 148'200
        return unConstructeur(2016)
                .montantMaxAssure(148_200).avs("8.4 %").ai("1.4 %").apg("0.45 %")
                .ac("2.2 %").avecParticipationRevenuAuDelaDuMontantMaxAssure().taux("1 %")
                .renteMensuelleMinimum(1175)
                .cons();
    }
    private ParametrageSuisseAnnuel construireParametrage2015() {
        return unConstructeur(2015)
                .montantMaxAssure(126_000).avs("8.4 %").ai("1.4 %").apg("0.5 %")
                .ac("2.2 %").avecParticipationRevenuAuDelaDuMontantMaxAssure().taux("1 %")
                .renteMensuelleMinimum(1175)
                .cons();
    }
    private ParametrageSuisseAnnuel construireParametrage2014() {
        // Suppression de la limite pour la participation des hauts revenus
        return unConstructeur(2014)
                .montantMaxAssure(126_000).avs("8.4 %").ai("1.4 %").apg("0.5 %")
                .ac("2.2 %").avecParticipationRevenuAuDelaDuMontantMaxAssure().taux("1 %")
                .renteMensuelleMinimum(1170)
                .cons();
    }
    private ParametrageSuisseAnnuel construireParametrage2013() {
        return unConstructeur(2013)
                .montantMaxAssure(126_000).avs("8.4 %").ai("1.4 %").apg("0.5 %")
                .ac("2.2 %")
                    .avecParticipationRevenuAuDelaDuMontantMaxAssure().taux("1 %")
                    .dansLaLimiteduMultipleDuMontantMaxAssure("2.5")
                .renteMensuelleMinimum(1170)
                .cons();
    }
    private ParametrageSuisseAnnuel construireParametrage2012() {
        return unConstructeur(2012)
                .montantMaxAssure(126_000).avs("8.4 %").ai("1.4 %").apg("0.5 %")
                .ac("2.2 %")
                    .avecParticipationRevenuAuDelaDuMontantMaxAssure().taux("1 %")
                    .dansLaLimiteduMultipleDuMontantMaxAssure("2.5")
                .renteMensuelleMinimum(1160)
                .cons();
    }
    private ParametrageSuisseAnnuel construireParametrage2011() {
        // Augmentation taux APG : 0.3 % -> 0.5 %
        // Augmentation taux de l'assurance chômage : 2 % -> 2.2 %
        // et instauration d'une participation limitée des hauts revenus
        return unConstructeur(2011)
                .montantMaxAssure(126_000).avs("8.4 %").ai("1.4 %").apg("0.5 %")
                .ac("2.2 %")
                    .avecParticipationRevenuAuDelaDuMontantMaxAssure().taux("1 %")
                    .dansLaLimiteduMultipleDuMontantMaxAssure("2.5")
                .renteMensuelleMinimum(1160)
                .cons();
    }
    private ParametrageSuisseAnnuel construireParametrage2010() {
        return unConstructeur(2010)
                .montantMaxAssure(126_000).avs("8.4 %").ai("1.4 %").apg("0.3 %")
                .ac("2 %")
                .renteMensuelleMinimum(1140)
                .cons();
    }
    private ParametrageSuisseAnnuel construireParametrage2009() {
        return unConstructeur(2009)
                .montantMaxAssure(126_000).avs("8.4 %").ai("1.4 %").apg("0.3 %")
                .ac("2 %")
                .renteMensuelleMinimum(1140)
                .cons();
    }
    private ParametrageSuisseAnnuel construireParametrage2008() {
        // Décision du 27 juin 2007 du conseil fédéral (RO 2007 3667 Annexe 1)
        // la maximum passe de 106800 à 126000
        return unConstructeur(2008)
                .montantMaxAssure(126_000)
                .avs("8.4 %").ai("1.4 %").apg("0.3 %")
                .ac("2 %")
                .renteMensuelleMinimum(1105)
                .cons();
    }
    private ParametrageSuisseAnnuel construireParametrage2007() {
        return unConstructeur(2007)
                .montantMaxAssure(106_800)
                .avs("8.4 %").ai("1.4 %").apg("0.3 %")
                .ac("2 %")
                .renteMensuelleMinimum(1105)
                .cons();
    }

    private ParametrageSuisseAnnuel construireParametrage2006() {
        return unConstructeur(2006)
                .montantMaxAssure(106_800)
                .avs("8.4 %").ai("1.4 %").apg("0.3 %")
                .ac("2 %")
                .renteMensuelleMinimum(1075)
                .cons();
    }

    private ParametrageSuisseAnnuel construireParametrage2005() {
        return unConstructeur(2005)
                .montantMaxAssure(106_800)
                .avs("8.4 %").ai("1.4 %").apg("0.3 %")
                .ac("2 %")
                .renteMensuelleMinimum(1075)
                .cons();
    }

    // renteSimpleMensuelleMinimumParAnnee.put(1993,BigDecimal.valueOf(940));
    //    renteSimpleMensuelleMinimumParAnnee.put(1994,BigDecimal.valueOf(940));
    //    renteSimpleMensuelleMinimumParAnnee.put(1995,BigDecimal.valueOf(970));
    //    renteSimpleMensuelleMinimumParAnnee.put(1996,BigDecimal.valueOf(970));
    //    renteSimpleMensuelleMinimumParAnnee.put(1997,BigDecimal.valueOf(995));
    //    renteSimpleMensuelleMinimumParAnnee.put(1998,BigDecimal.valueOf(995));

    //    renteSimpleMensuelleMinimumParAnnee.put(2001,BigDecimal.valueOf(1030));
    //    renteSimpleMensuelleMinimumParAnnee.put(2002,BigDecimal.valueOf(1030));
    //    renteSimpleMensuelleMinimumParAnnee.put(2003,BigDecimal.valueOf(1055));
    //    renteSimpleMensuelleMinimumParAnnee.put(2004,BigDecimal.valueOf(1055));
    //    renteSimpleMensuelleMinimumParAnnee.put(2005,BigDecimal.valueOf(1075));
    //    renteSimpleMensuelleMinimumParAnnee.put(2006,BigDecimal.valueOf(1075));
    //    renteSimpleMensuelleMinimumParAnnee.put(2007,BigDecimal.valueOf(1105));
    //    renteSimpleMensuelleMinimumParAnnee.put(2008,BigDecimal.valueOf(1105));


    // montant max assuré = 97200 francs jusqu’à 1999 inclus puis à partir de 2000, 106800 francs
    private ParametrageSuisseAnnuel construireParametrage2000() {
        // La loi fédérale sur l'assurance-accidents stipule qu' «en règle générale,
        // au moins 92 pour cent, mais pas plus de 96 pour cent des travailleurs assurés soient
        // couverts pour le gain intégral». Lorsque les salaires augmentent, le montant maximum
        // du gain assuré doit être adapté de temps à autre, afin de respecter cette exigence.
        // Une adaptation régulière intervenant chaque année est exclue en raison de la masse de
        // formalités administratives qu'elle entraînerait. Depuis la dernière augmentation en 1991,
        // les salaires ont progressé d'environ 14 pour cent (indice des salaires nominaux OFS).
        // La limite minimale légale de couverture d'assurance étant atteinte, le Conseil fédéral
        // a décidé d'augmenter le montant maximum du gain assuré de 97'200 à 106'800 francs.
        // Cela correspond à une hausse de 10 pour cent
        return unConstructeur(2000)
                .montantMaxAssure(106_800) // À compléter
                .renteMensuelleMinimum(1005)
                .cons();
    }

    private ParametrageSuisseAnnuel construireParametrage1999() {
        return unConstructeur(1999)
                .montantMaxAssure(97_200) // À compléter
                .renteMensuelleMinimum(1005)
                .cons();
    }

    // Taux de cotisation AC depuis 1977 - Limite de revenu
//    01.04.1977 – 1979         0.8%            46'800
//    À partir de 1980          0.5%            46'800
//    À partir de 1982          0.3%            46'800
//    À partir de 1983          0.3%            69'900
//    À partir de 1984          0.6%            69'900
//    À partir de 1987          0.6%            81'600
//    À partir de 1990          0.4%            81'600
//    À partir de 1991          0.4%            97'200
//    À partir de 1993          2.0%            97'200
//    À partir de 1995          3.0%            97'200
//    À partir de 2000          3.0%            106'800
//    À partir de 2003          2.5%            106'800
//    À partir de 2004          2.0%            106'800
//    À partir de 2008          2.0%            126'000
//    À partir de 2011          2.2%            126'000
//    À partir de 2016          2.2% ...


}
