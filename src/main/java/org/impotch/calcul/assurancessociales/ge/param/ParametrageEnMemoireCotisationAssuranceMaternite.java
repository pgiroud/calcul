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
package org.impotch.calcul.assurancessociales.ge.param;


/**
 * Created by patrick on 18/01/15.
 */
public class ParametrageEnMemoireCotisationAssuranceMaternite implements ParametrageCotisationAssuranceMaternite {

    public static Constructeur unConstructeur(int annee) {
        return new Constructeur(annee);
    }
    public static Constructeur unConstructeur(ParametrageCotisationAssuranceMaternite parametrage) {
        return new Constructeur(parametrage);
    }

    private final int annee;
    private final String tauxCotisationAssuranceMaternite;

    private ParametrageEnMemoireCotisationAssuranceMaternite(int annee, String tauxCotisationAssuranceMaternite) {
        this.annee = annee;
        this.tauxCotisationAssuranceMaternite = tauxCotisationAssuranceMaternite;
    }

    @Override
    public int annee() {
        return annee;
    }

    @Override
    public String tauxAssuranceMaternite() {
        return tauxCotisationAssuranceMaternite;
    }


    public static class Constructeur {
        private final int annee;
        private String tauxCotisationAssuranceMaternite;

        public Constructeur(int annee) {
            this.annee = annee;
        }

        public Constructeur(ParametrageCotisationAssuranceMaternite parametrage) {
            this.annee = parametrage.annee();;
            this.tauxCotisationAssuranceMaternite = parametrage.tauxAssuranceMaternite();
        }

        public Constructeur tauxCotisationAssuranceMaternite(String taux) {
            this.tauxCotisationAssuranceMaternite = taux;
            return this;
        }

        public ParametrageCotisationAssuranceMaternite cons() {
            return new ParametrageEnMemoireCotisationAssuranceMaternite(this.annee,this.tauxCotisationAssuranceMaternite);
        }
    }
}
