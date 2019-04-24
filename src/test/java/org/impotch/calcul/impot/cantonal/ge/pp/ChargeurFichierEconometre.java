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
/**
 * This file is part of impotch/calcul.
 * <p>
 * impotch/calcul is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 * <p>
 * impotch/calcul is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with impotch/calcul.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.impotch.calcul.impot.cantonal.ge.pp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.stream.Stream;

public class ChargeurFichierEconometre {

    final Logger logger = LoggerFactory.getLogger(ChargeurFichierEconometre.class);

    private void verifieAnnee(int periode) {
        int anneeCouranteMoinsUn = Calendar.getInstance().get(Calendar.YEAR) - 1;
        if (periode < 1995 || periode > anneeCouranteMoinsUn) {
            logger.error("La période fiscale " + periode + " n'est pas définie !");
            throw new IllegalArgumentException("La période fiscale " + periode + " n'est pas définie !");
        }
    }


    public Stream<Point> points(int periode, boolean famille) throws IOException {
        this.verifieAnnee(periode);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(ClassLoader.getSystemResourceAsStream("ge/BASEIMP.CSV")));
        int indexImpot = 2 * (periode - 1995) + (famille ? 1 : 0) + 1;
        return reader.lines().skip(15).map(s -> point(s,indexImpot));
    }

    private Point point(String ligne, int indexImpot) {
            String[] tokens = ligne.split(";");
            String revenu = tokens[0].trim();
            revenu = revenu.substring(0, revenu.length() - 1);
            if (Character.isDigit(revenu.charAt(0))) {
                return new Point(revenu, tokens[indexImpot].trim());
            } else {
                throw new RuntimeException("Problème avec un revenu (1ère colonne) '" + revenu + "'");
            }
    }

    public static class Point {
        public final int revenu;
        public final BigDecimal impot;

        public Point(String revenu, String impot) {
            this.revenu = Integer.parseInt(revenu);
            this.impot = new BigDecimal(impot);
        }
    }

}
