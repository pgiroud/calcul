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
package org.impotch.calcul.impot.cantonal.ge.param.dao;

import org.impotch.calcul.impot.taxation.forimposition.ForCommunal;
import org.impotch.calcul.impot.taxation.repart.Repartition;
import org.impotch.calcul.lieu.ICommuneSuisse;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;


public class ResidentParCommuneFichierTxtDaoTest {

    private ResidentParCommuneFichierTxtDao dao;

    @Before
    public void init() {
        dao = new ResidentParCommuneFichierTxtDao();
        dao.setCharsetName("UTF-8");
        dao.setFichier(new ClassPathResource("parametrage/ge/PopulationParCommuneGenevoise.csv"));
        try {
            dao.chargerResource();
        } catch(IOException ioe) {

        }
    }

    @Test
    public void meyrin2009() {
        Map<ICommuneSuisse,Integer> repart = dao.getRepartitionAuProrataDeLaPopulation(2009);
        for (ICommuneSuisse commune : repart.keySet()) {
            if (commune.getNom().equalsIgnoreCase("Meyrin")) {
                assertThat(repart.get(commune)).isEqualTo(21442);
            }
        }
    }

}
