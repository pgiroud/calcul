/*
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;
import static org.impotch.calcul.assurancessociales.param.ParametrageSuisseAnnuelEnMemoire.unConstructeur;

class FournisseurParametrageSuisseAnnuelEnMemoire implements FournisseurParametrageSuisseAnnuel {

    final Logger logger = LoggerFactory.getLogger(FournisseurParametrageSuisseAnnuelEnMemoire.class);

    public Optional<ParametrageSuisseAnnuel> parametrage(int annee) {
        return construireParametrageAnnuel(annee);
    }

    private Optional<ParametrageSuisseAnnuel> construireParametrageAnnuel(int annee) {
        String methodeName = "construireParametrage" + annee;
        try {
            Method methodeAnnuelle = FournisseurParametrageSuisseAnnuelEnMemoire.class.getDeclaredMethod(methodeName);
            return Optional.of((ParametrageSuisseAnnuel)methodeAnnuelle.invoke(this));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException nsme) {
            logger.error("Impossible d’invoquer la méthode " + methodeName + " de la classe " + this.getClass().getCanonicalName());
            logger.error("Il s'agit certainement d’un manque dans le paramétrage pour l’année " + annee);
            return Optional.empty();
        }
    }

    private ParametrageSuisseAnnuel construireParametrage2025() {
        // Adaptation de la rente mensuelle minimum : 1225 CHF --> 1260 CHF https://www.fedlex.admin.ch/eli/cc/2024/463/fr#art_3
        return unConstructeur(2025)
                // Source art.22 al. 1 de l’ Ordonnance sur l'assurance-accidents (OLAA) : https://www.fedlex.admin.ch/eli/cc/1983/38_38_38/fr#art_22
                .montantMaxAssure(148_200)
                // Source Art. 5 al. 1 de la loi sur l’assurance vieillesse et survivants (LAVS) : https://www.fedlex.admin.ch/eli/cc/63/837_843_843/fr#art_5
                .avs("8.7 %")
                // Source art.3 al.1 de la Loi fédérale sur l’assurance-invalidité (LAI) : https://www.fedlex.admin.ch/eli/cc/1959/827_857_845/fr#art_3
                .ai("1.4 %")
                // Source art.36 al.1 de l’ordonnance sur les allocations pour perte de gain (OAPG) https://www.fedlex.admin.ch/eli/cc/2005/187/fr#art_36
                .apg("0.5 %")
                // Source art.3 al. 2 de la Loi sur l’assurance chômage (LACI) : https://www.fedlex.admin.ch/eli/cc/1982/2184_2184_2184/fr#art_3
                .ac("2.2 %")
                // Source art. 3 al.1 de l’ordonnance sur les adaptations à l’évolution des salaires et des prix
                // dans le régime de l’AVS, de l’AI et des APG à partir de 2025
                // https://www.fedlex.admin.ch/eli/cc/2024/463/fr#art_3
                .renteMensuelleMinimum(1260)
                .cons();
    }

    private ParametrageSuisseAnnuel construireParametrage2024() {
        return unConstructeur(2024)
                .montantMaxAssure(148_200).avs("8.7 %").ai("1.4 %").apg("0.5 %")
                .ac("2.2 %")
                // Source art. 3 al.1 de l’ordonnance 23 sur les adaptations à l’évolution des salaires et des prix
                // dans le régime de l’AVS, de l’AI et des APG
                // https://www.fedlex.admin.ch/eli/cc/2022/604/fr
                .renteMensuelleMinimum(1225)
                .cons();
    }

    private ParametrageSuisseAnnuel construireParametrage2023() {
        // Suppression de la participation des hauts revenus dans l'assurance chômage
        return unConstructeur(2023)
                .montantMaxAssure(148_200).avs("8.7 %").ai("1.4 %").apg("0.5 %")
                .ac("2.2 %")
                // Source art. 3 al.1 de l’ordonnance 23 sur les adaptations à l’évolution des salaires et des prix
                // dans le régime de l’AVS, de l’AI et des APG
                // https://www.fedlex.admin.ch/eli/cc/2022/604/fr
                .renteMensuelleMinimum(1225)
                .cons();
    }
    private ParametrageSuisseAnnuel construireParametrage2022() {
        return unConstructeur(2022)
                .montantMaxAssure(148_200).avs("8.7 %").ai("1.4 %").apg("0.5 %")
                .ac("2.2 %").avecParticipationRevenuAuDelaDuMontantMaxAssure().taux("1 %")
                // Source art. 3 al.1 de l’ordonnance 21 sur les adaptations à l’évolution des salaires et des prix
                // dans le régime de l’AVS, de l’AI et des APG
                // https://www.fedlex.admin.ch/eli/cc/2020/819/fr
                .renteMensuelleMinimum(1195)
                .cons();
    }
    private ParametrageSuisseAnnuel construireParametrage2021() {
        // Augmentation taux APG : 0.45 -> 0.5
        return unConstructeur(2021)
                .montantMaxAssure(148_200).avs("8.7 %").ai("1.4 %").apg("0.5 %")
                .ac("2.2 %").avecParticipationRevenuAuDelaDuMontantMaxAssure().taux("1 %")
                // Source art. 3 al.1 de l’ordonnance 21 sur les adaptations à l’évolution des salaires et des prix
                // dans le régime de l’AVS, de l’AI et des APG
                // https://www.fedlex.admin.ch/eli/cc/2020/819/fr
                .renteMensuelleMinimum(1195)
                .cons();
    }
    private ParametrageSuisseAnnuel construireParametrage2020() {
        // Augmentation du taux AVS : 8.4 -> 8.7
        // Source RO 2019 2395 Loi fédérale relative à la réforme fiscale et au financement de l’AVS (RFFA)
        // https://www.bk.admin.ch/ch/f/pore/rf/cr/2018/20180201.html
        return unConstructeur(2020)
                .montantMaxAssure(148_200).avs("8.7 %").ai("1.4 %").apg("0.45 %")
                .ac("2.2 %").avecParticipationRevenuAuDelaDuMontantMaxAssure().taux("1 %")
                // Source art. 3 al.1 de l’ordonnance 20 sur les adaptations à l’évolution des salaires et des prix
                // dans le régime de l’AVS, de l’AI et des APG
                // https://www.fedlex.admin.ch/eli/cc/2019/677/fr
                .renteMensuelleMinimum(1185)
                .cons();
    }
    private ParametrageSuisseAnnuel construireParametrage2019() {
        return unConstructeur(2019)
                .montantMaxAssure(148_200).avs("8.4 %").ai("1.4 %").apg("0.45 %")
                .ac("2.2 %").avecParticipationRevenuAuDelaDuMontantMaxAssure().taux("1 %")
                // Source art. 3 al.1 de l’ordonnance 19 sur les adaptations à l’évolution des salaires et des prix
                // dans le régime de l’AVS, de l’AI et des APG
                // https://www.fedlex.admin.ch/eli/cc/2018/574/fr
                .renteMensuelleMinimum(1185)
                .cons();
    }
    private ParametrageSuisseAnnuel construireParametrage2018() {
        return unConstructeur(2018)
                .montantMaxAssure(148_200).avs("8.4 %").ai("1.4 %").apg("0.45 %")
                .ac("2.2 %").avecParticipationRevenuAuDelaDuMontantMaxAssure().taux("1 %")
                // Source art. 3 al.1 de l’ordonnance 15 sur les adaptations à l’évolution des salaires et des prix
                // dans le régime de l’AVS, de l’AI et des APG
                // https://www.fedlex.admin.ch/eli/cc/2014/576/fr
                .renteMensuelleMinimum(1175)
                .cons();
    }
    private ParametrageSuisseAnnuel construireParametrage2017() {
        return unConstructeur(2017)
                .montantMaxAssure(148_200).avs("8.4 %").ai("1.4 %").apg("0.45 %")
                .ac("2.2 %").avecParticipationRevenuAuDelaDuMontantMaxAssure().taux("1 %")
                // Source art. 3 al.1 de l’ordonnance 15 sur les adaptations à l’évolution des salaires et des prix
                // dans le régime de l’AVS, de l’AI et des APG
                // https://www.fedlex.admin.ch/eli/cc/2014/576/fr
                .renteMensuelleMinimum(1175)
                .cons();
    }
    private ParametrageSuisseAnnuel construireParametrage2016() {
        // Diminution taux APG : 0.5 -> 0.45
        // Augmentation du montant maximum assuré : 126'000 -> 148'200
        // Source RO 2014 4213 https://www.fedlex.admin.ch/eli/oc/2014/702/fr Ordonnance sur l'assurance-accidents (OLAA)
        return unConstructeur(2016)
                .montantMaxAssure(148_200).avs("8.4 %").ai("1.4 %").apg("0.45 %")
                .ac("2.2 %").avecParticipationRevenuAuDelaDuMontantMaxAssure().taux("1 %")
                // Source art. 3 al.1 de l’ordonnance 15 sur les adaptations à l’évolution des salaires et des prix
                // dans le régime de l’AVS, de l’AI et des APG
                // https://www.fedlex.admin.ch/eli/cc/2014/576/fr
                .renteMensuelleMinimum(1175)
                .cons();
    }
    private ParametrageSuisseAnnuel construireParametrage2015() {
        return unConstructeur(2015)
                .montantMaxAssure(126_000).avs("8.4 %").ai("1.4 %").apg("0.5 %")
                .ac("2.2 %").avecParticipationRevenuAuDelaDuMontantMaxAssure().taux("1 %")
                // Source art. 3 al.1 de l’ordonnance 15 sur les adaptations à l’évolution des salaires et des prix
                // dans le régime de l’AVS, de l’AI et des APG
                // https://www.fedlex.admin.ch/eli/cc/2014/576/fr
                .renteMensuelleMinimum(1175)
                .cons();
    }
    private ParametrageSuisseAnnuel construireParametrage2014() {
        // Suppression de la limite pour la participation des hauts revenus à la cotisation
        // Acceptation par l‘Assemblée fédérale, le 21 juin 2013, du déplafonnement du pourcent de solidarité
        return unConstructeur(2014)
                .montantMaxAssure(126_000).avs("8.4 %").ai("1.4 %").apg("0.5 %")
                .ac("2.2 %").avecParticipationRevenuAuDelaDuMontantMaxAssure().taux("1 %")
                // Source art. 3 al.1 de l’ordonnance 13 sur les adaptations à l’évolution des salaires et des prix
                // dans le régime de l’AVS, de l’AI et des APG
                // https://www.fedlex.admin.ch/eli/cc/2012/747/fr
                .renteMensuelleMinimum(1170)
                .cons();
    }
    private ParametrageSuisseAnnuel construireParametrage2013() {
        return unConstructeur(2013)
                .montantMaxAssure(126_000).avs("8.4 %").ai("1.4 %").apg("0.5 %")
                .ac("2.2 %")
                    .avecParticipationRevenuAuDelaDuMontantMaxAssure().taux("1 %")
                    .dansLaLimiteduMultipleDuMontantMaxAssure("2.5")
                // Source art. 3 al.1 de l’ordonnance 13 sur les adaptations à l’évolution des salaires et des prix
                // dans le régime de l’AVS, de l’AI et des APG
                // https://www.fedlex.admin.ch/eli/cc/2012/747/fr
                .renteMensuelleMinimum(1170)
                .cons();
    }
    private ParametrageSuisseAnnuel construireParametrage2012() {
        return unConstructeur(2012)
                .montantMaxAssure(126_000).avs("8.4 %").ai("1.4 %").apg("0.5 %")
                .ac("2.2 %")
                    .avecParticipationRevenuAuDelaDuMontantMaxAssure().taux("1 %")
                    .dansLaLimiteduMultipleDuMontantMaxAssure("2.5")
                // Source art. 3 al.1 de l’ordonnance 11 sur les adaptations à l’évolution des salaires et des prix
                // dans le régime de l’AVS, de l’AI et des APG
                // https://www.fedlex.admin.ch/eli/cc/2010/656/fr
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
                // Source art. 3 al.1 de l’ordonnance 11 sur les adaptations à l’évolution des salaires et des prix
                // dans le régime de l’AVS, de l’AI et des APG
                // https://www.fedlex.admin.ch/eli/cc/2010/656/fr
                .renteMensuelleMinimum(1160)
                .cons();
    }
    private ParametrageSuisseAnnuel construireParametrage2010() {
        return unConstructeur(2010)
                .montantMaxAssure(126_000).avs("8.4 %").ai("1.4 %").apg("0.3 %")
                .ac("2 %")
                // Source art. 3 al.1 de l’ordonnance 09 sur les adaptations à l’évolution des salaires et des prix
                // dans le régime de l’AVS, de l’AI et des APG
                // https://www.fedlex.admin.ch/eli/cc/2008/659/fr
                .renteMensuelleMinimum(1140)
                .cons();
    }
    private ParametrageSuisseAnnuel construireParametrage2009() {
        return unConstructeur(2009)
                .montantMaxAssure(126_000).avs("8.4 %").ai("1.4 %").apg("0.3 %")
                .ac("2 %")
                // Source art. 3 al.1 de l’ordonnance 09 sur les adaptations à l’évolution des salaires et des prix
                // dans le régime de l’AVS, de l’AI et des APG
                // https://www.fedlex.admin.ch/eli/cc/2008/659/fr
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
                // Source art. 3 al.1 de l’ordonnance 07 sur les adaptations à l’évolution des salaires et des prix
                // dans le régime de l’AVS, de l’AI et des APG
                // https://www.fedlex.admin.ch/eli/cc/2006/611/fr
                .renteMensuelleMinimum(1105)
                .cons();
    }
    private ParametrageSuisseAnnuel construireParametrage2007() {
        return unConstructeur(2007)
                .montantMaxAssure(106_800)
                .avs("8.4 %").ai("1.4 %").apg("0.3 %")
                .ac("2 %")
                // Source art. 3 al.1 de l’ordonnance 07 sur les adaptations à l’évolution des salaires et des prix
                // dans le régime de l’AVS, de l’AI et des APG
                // https://www.fedlex.admin.ch/eli/cc/2006/611/fr
                .renteMensuelleMinimum(1105)
                .cons();
    }

    private ParametrageSuisseAnnuel construireParametrage2006() {
        return unConstructeur(2006)
                .montantMaxAssure(106_800)
                .avs("8.4 %").ai("1.4 %").apg("0.3 %")
                .ac("2 %")
                // Source art. 3 al.1 de l’ordonnance 05 sur les adaptations à l’évolution des salaires et des prix
                // dans le régime de l’AVS, de l’AI et des APG
                // https://www.fedlex.admin.ch/eli/cc/2004/661/fr
                .renteMensuelleMinimum(1075)
                .cons();
    }

    private ParametrageSuisseAnnuel construireParametrage2005() {
        return unConstructeur(2005)
                .montantMaxAssure(106_800)
                .avs("8.4 %").ai("1.4 %").apg("0.3 %")
                .ac("2 %")
                // Source art. 3 al.1 de l’ordonnance 05 sur les adaptations à l’évolution des salaires et des prix
                // dans le régime de l’AVS, de l’AI et des APG
                // https://www.fedlex.admin.ch/eli/cc/2004/661/fr
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
