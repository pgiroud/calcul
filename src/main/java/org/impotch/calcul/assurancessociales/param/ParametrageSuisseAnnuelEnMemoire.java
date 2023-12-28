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

public class ParametrageSuisseAnnuelEnMemoire implements ParametrageSuisseAnnuel {


    public static Constructeur unConstructeur(int annee) {
        return new Constructeur(annee);
    }

    private final int annee;
    private final int montantMaximumDuGainAssure;
    private final String tauxAVS;
    private final String tauxAI;
    private final String tauxAPG;
    private final String tauxAC;
    private final ParametrageParticipationHautRevenuAC participationHautRevenuAC;

    private int renteAVSMensuelleMinimum;


    private ParametrageSuisseAnnuelEnMemoire(int annee,
                                            int montantMaximumDuGainAssure,
                                            String tauxAVS,
                                            String tauxAI,
                                            String tauxAPG,
                                            String tauxAC,
                                             ParametrageParticipationHautRevenuAC participation,
                                             int renteAVSMensuelleMinimum) {
        this.annee = annee;
        this.montantMaximumDuGainAssure = montantMaximumDuGainAssure;
        this.tauxAVS = tauxAVS;
        this.tauxAI = tauxAI;
        this.tauxAPG = tauxAPG;
        this.tauxAC = tauxAC;
        this.participationHautRevenuAC = participation;
        this.renteAVSMensuelleMinimum = renteAVSMensuelleMinimum;
    }

    private ParametrageSuisseAnnuelEnMemoire(int annee,
                                             int montantMaximumDuGainAssure,
                                             String tauxAVS,
                                             String tauxAI,
                                             String tauxAPG,
                                             String tauxAC,
                                             int renteAVSMensuelleMinimum) {
        this.annee = annee;
        this.montantMaximumDuGainAssure = montantMaximumDuGainAssure;
        this.tauxAVS = tauxAVS;
        this.tauxAI = tauxAI;
        this.tauxAPG = tauxAPG;
        this.tauxAC = tauxAC;
        participationHautRevenuAC = null;
        this.renteAVSMensuelleMinimum = renteAVSMensuelleMinimum;
    }

    @Override
    public int annee() {
        return annee;
    }

    @Override
    public int montantMaximumDuGainAssure() {
        return montantMaximumDuGainAssure;
    }

    @Override
    public String tauxAVS() {
        return tauxAVS;
    }

    @Override
    public String tauxAI() {
        return tauxAI;
    }

    @Override
    public String tauxAPG() {
        return tauxAPG;
    }

    @Override
    public String tauxAC() {
        return tauxAC;
    }

    @Override
    public Optional<ParametrageParticipationHautRevenuAC> participationHautRevenu() {
        if (null == participationHautRevenuAC) return Optional.empty();
        else return Optional.of(participationHautRevenuAC);
    }

    @Override
    public int renteMensuelleMinimum() {
        return renteAVSMensuelleMinimum;
    }

    public static class Constructeur {
        private final int annee;

        private int montantMaxAssure;
        private String tauxAVS;
        private String tauxAI;
        private String tauxAPG;
        private String tauxAC;

        private boolean avecParticipationHautRevenuAC = false;

        private int renteMensuelleMinimum;

        private ParametrageParticipationHautRevenuAC participation;

        public Constructeur(int annee) {
            this.annee = annee;
        }

        public Constructeur(ParametrageSuisseAnnuel param) {
            this.annee = param.annee();
            this.montantMaxAssure = param.montantMaximumDuGainAssure();
            this.tauxAVS = param.tauxAVS();
            this.tauxAI = param.tauxAI();
            this.tauxAPG = param.tauxAPG();
            this.tauxAC = param.tauxAC();
            this.avecParticipationHautRevenuAC = avecParticipationRevenuAuDelaDuMontantMaxAssure().avecParticipationHautRevenuAC;
            if (param.participationHautRevenu().isPresent()) {
                this.participation = param.participationHautRevenu().orElse(null);
            }

            this.renteMensuelleMinimum = param.renteMensuelleMinimum();
        }

        public Constructeur montantMaxAssure(int montant) {
            montantMaxAssure = montant;
            return this;
        }

        /**
         * Spécifie le taux de cotisation à l’assurance vieillesse et survivant total (part employeur + part salariée).
         * @param taux le taux avec un symbole %, par exemple "5 %"
         * @return ce construteur afin de chaîner les appels.
         */
        public Constructeur avs(String taux) {
            tauxAVS = taux;
            return this;
        }

        /**
         * Spécifie le taux de cotisation à l'assurance invalidité total (part employeur + part salariée).
         * @param taux le taux avec un symbole %, par exemple "1.1 %"
         * @return ce construteur afin de chaîner les appels.
         */
        public Constructeur ai(String taux) {
            tauxAI = taux;
            return this;
        }

        /**
         * Spécifie le taux de cotisation total des assurances pour perte de gains  (part employeur + part salariée).
         * @param taux le taux avec un symbole %, par exemple "1 %"
         * @return ce construteur afin de chaîner les appels.
         */
        public Constructeur apg(String taux) {
            tauxAPG = taux;
            return this;
        }

        /**
         * Spécifie le taux total de cotisation à l'assurance chômage  (part employeur + part salariée).
         * @param taux le taux avec un symbole %, par exemple "1 %"
         * @return ce construteur afin de chaîner les appels.
         */
        public Constructeur ac(String taux) {
            tauxAC = taux;
            return this;
        }

        public Constructeur avecParticipationRevenuAuDelaDuMontantMaxAssure() {
            avecParticipationHautRevenuAC = true;
            return this;
        }

        public Constructeur taux(String taux) {
            if (avecParticipationHautRevenuAC) {
                participation = new ParametrageParticipationHautRevenuACEnMemoire(taux);
            } else throw new IllegalStateException("Il faut préciser sur quoi le taux s’applique !!");
            return this;
        }

        public Constructeur dansLaLimiteduMultipleDuMontantMaxAssure(String ratio) {
            if (avecParticipationHautRevenuAC) {
                participation = new ParametrageParticipationHautRevenuACEnMemoire(participation.taux(), ratio);
            } else throw new IllegalStateException("Il faut préciser sur quoi le ratio s’applique !!");
            return this;
        }

        public Constructeur renteMensuelleMinimum(int montant) {
            this.renteMensuelleMinimum = montant;
            return this;
        }

        public ParametrageSuisseAnnuel cons() {
            if (avecParticipationHautRevenuAC) {
                return new ParametrageSuisseAnnuelEnMemoire(annee,montantMaxAssure,tauxAVS,tauxAI,tauxAPG,
                        tauxAC,participation,renteMensuelleMinimum);
            } else {
                return new ParametrageSuisseAnnuelEnMemoire(annee,montantMaxAssure,tauxAVS,tauxAI,tauxAPG,
                        tauxAC,renteMensuelleMinimum);
            }
        }
    }

}
