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

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ConstructeurParametreCommunalEnMemoire {

    ParametreCommunalEnMemoire param = new ParametreCommunalEnMemoire();
    private int anneeCourante;
    private int noCommuneCourant = -1;

    public ConstructeurParametreCommunalEnMemoire depuisFichier(String fichier) throws IOException {
        Files.lines(Paths.get(fichier)).map(StructureEnregistrement::new)
                .forEach(this::traiterStructure);
        return this;
    }

    private void traiterStructure(StructureEnregistrement struct) {
        annee(Integer.parseInt(struct.annee))
                .commune(Integer.parseInt(struct.noOFS))
                .partPrivilegiee(new BigDecimal(struct.part))
                .ctsAdd(new BigDecimal(struct.cts));
    }

    public ConstructeurParametreCommunalEnMemoire annee(int annee) {
        anneeCourante = annee;
        return this;
    }
    public ConstructeurParametreCommunalEnMemoire commune(int noOFS) {
        noCommuneCourant = noOFS;
        return this;
    }

    public ConstructeurParametreCommunalEnMemoire partPrivilegiee(BigDecimal partPrivilegiee) {
        if (0 > noCommuneCourant) throw new RuntimeException("Il faut spécifier la commune avant d'inidquer la part privilégiée !");
        if (0 == anneeCourante) throw new RuntimeException("Il faut spécifier l'année courante avant d'inidquer la part privilégiée !");
        param.partPrivilegiee(anneeCourante,noCommuneCourant,partPrivilegiee);
        return this;
    }

    public ConstructeurParametreCommunalEnMemoire ctsAdd(BigDecimal centimes) {
        if (0 > noCommuneCourant) throw new RuntimeException("Il faut spécifier la commune avant d'inidquer la part privilégiée !");
        if (0 == anneeCourante) throw new RuntimeException("Il faut spécifier l'année courante avant d'inidquer la part privilégiée !");
        param.centimes(anneeCourante,noCommuneCourant,centimes);
        return this;
    }

    public ParametreCommunalDao cons() {
        return param;
    }

    static class StructureEnregistrement {
        public final String annee;
        public final String noOFS;
        public final String part;
        public final String cts;

        public StructureEnregistrement(String ligne) {
            String[] tokens = ligne.split("\t");
            annee = tokens[0];
            noOFS = tokens[1];
            part = tokens[2];
            cts = tokens[3];
        }
    }

}
