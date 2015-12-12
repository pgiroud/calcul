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
package org.impotch.calcul.impot.cantonal.ge.pp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.dao.TypeMismatchDataAccessException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by patrick on 09/10/15.
 */
public class ChargeurFichierEconometre2016 {
    /**************************************************/
    /****************** Attributs *********************/
    /**************************************************/

    final Logger logger = LoggerFactory.getLogger(ChargeurFichierEconometre2016.class);
    private Resource fichier;


    /**************************************************/
    /**************** Constructeurs *******************/
    /**************************************************/

    public ChargeurFichierEconometre2016() {
        super();
    }

    /**************************************************/
    /******* Accesseurs / Mutateurs *******************/
    /**************************************************/

    public void setFichier(Resource fichier) {
        this.fichier = fichier;
    }


    /**************************************************/
    /******************* Méthodes *********************/
    /**************************************************/


    public Object[][] charger(boolean famille) throws IOException {
        int indexImpot = (famille ? 1 : 0) + 1;
        BufferedReader reader = new BufferedReader(new InputStreamReader(fichier.getInputStream()));
        int numLigne = 1;
        String ligne = reader.readLine();   // On saute la première ligne
        ligne = reader.readLine();
        List<Point> points = new LinkedList<Point>();
        while (null != ligne) {
            try {
                String[] tokens = ligne.split("\t");
                String revenu = tokens[0].trim();
                if (Character.isDigit(revenu.charAt(0))) {
                    points.add(new Point(revenu,tokens[indexImpot].trim()));
                }
            } catch (ParseException pe) {
                throw new TypeMismatchDataAccessException("Erreur de lecture dans la ressource " + fichier.getFilename() + " à la ligne " + numLigne,pe);
            } catch (NumberFormatException nfe) {
                throw new TypeMismatchDataAccessException("Erreur de lecture dans la ressource " + fichier.getFilename() + " à la ligne " + numLigne,nfe);
            }
            ligne = reader.readLine();
            numLigne++;
        }
        reader.close();
        Object[][] retour = new Object[points.size()][2];
        int i = 0;
        for (Point pt : points) {
            retour[i][0] = pt.revenu;
            retour[i++][1] = pt.impot;
        }
        return retour;
    }

    private static class Point {

        public final int revenu;
        public final BigDecimal impot;

        public Point(String revenu, String impot) throws ParseException {
            this.revenu = Integer.parseInt(revenu);
            this.impot = new BigDecimal(impot.replace(',','.')).setScale(2);
        }
    }
}
