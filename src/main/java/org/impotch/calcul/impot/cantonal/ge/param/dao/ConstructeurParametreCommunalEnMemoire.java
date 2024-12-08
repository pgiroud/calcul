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
package org.impotch.calcul.impot.cantonal.ge.param.dao;

import org.impotch.util.BigDecimalUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConstructeurParametreCommunalEnMemoire {

    public static ConstructeurParametreCommunalEnMemoire unConstructeur() {
        return new ConstructeurParametreCommunalEnMemoire();
    }

    ParametreCommunalEnMemoire param = new ParametreCommunalEnMemoire();
    private int anneeCourante;
    private int noCommuneCourant = -1;

    private List<Integer> anneePourResidentParCommune;

    private ClassLoader getClassLoader() {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        }
        catch (Throwable ex) {}
        if (cl == null) {
            cl = ConstructeurParametreCommunalEnMemoire.class.getClassLoader();
            if (cl == null) {
                try {
                    cl = ClassLoader.getSystemClassLoader();
                }
                catch (Throwable ex) {
                }
            }
        }
        return cl;
    }

    private ConstructeurParametreCommunalEnMemoire() {
    }

    public ConstructeurParametreCommunalEnMemoire partPrivilegieeEtCtsAdddepuisClassPath(String chemin) throws IOException {
        ClassLoader cl = getClassLoader();
        InputStream is = (cl != null ? cl.getResourceAsStream(chemin) : ClassLoader.getSystemResourceAsStream(chemin));
        try (Stream<String> lines = new BufferedReader(
                new InputStreamReader(is)).lines()) {
                lines.map(StructureEnregistrementPartPrivilegieeEtCtsAdd::new)
                    .forEach(this::traiterStructure);
        }
        return this;
    }



    public ConstructeurParametreCommunalEnMemoire residentAu31decembreDepuisClasspath(String chemin) throws IOException {
        ClassLoader cl = getClassLoader();
        InputStream is = (cl != null ? cl.getResourceAsStream(chemin) : ClassLoader.getSystemResourceAsStream(chemin));
        try (Stream<String> lines = new BufferedReader(
                new InputStreamReader(is)).lines()) {
            lines.map(StructureEnregistrementResidentAu31decembre::new)
                    .forEach(this::traiterStructure);
        }
        return this;
    }

    private void traiterStructure(StructureEnregistrementPartPrivilegieeEtCtsAdd struct) {
        annee(Integer.parseInt(struct.annee))
                .commune(Integer.parseInt(struct.noOFS))
                .partPrivilegiee(new BigDecimal(struct.part))
                .centimes(new BigDecimal(struct.cts));
    }

    private void traiterStructure(StructureEnregistrementResidentAu31decembre struct) {
        if(struct.noOFS.startsWith("N")) {
            // 1ère ligne d'entête
            anneePourResidentParCommune = struct.residentParAnnee;
        } else if (struct.noOFS.startsWith("90")) {
          // On ne fait rien, il s'agit du récapitulatif du canton
        } else {
            int noOFS = Integer.parseInt(struct.noOFS);
            int i = 0;
            for (Integer nbre : struct.residentParAnnee) {
                annee(anneePourResidentParCommune.get(i++)).commune(noOFS).nbreResident(nbre);
            }
        }
    }



    public ConstructeurParametreCommunalEnMemoire annee(int annee) {
        anneeCourante = annee;
        return this;
    }
    public ConstructeurParametreCommunalEnMemoire commune(int noOFS) {
        noCommuneCourant = noOFS;
        return this;
    }

    /**
     * D'après l'article 11C du Règlement d’application de diverses dispositions de la loi générale sur les contributions publiques
     * (<a href="https://silgeneve.ch/legis/program/books/RSG/htm/rsg_d3_05p04.htm">RDLCP</a>)
     * @param partPrivilegiee la part privilégiée telle que rédigé dans l’article par exemple "27 %"
     * @return ce constructeur bien utile pour dérouler le pattern Builder
     */
    public ConstructeurParametreCommunalEnMemoire partPrivilegiee(String partPrivilegiee) {
        return partPrivilegiee(BigDecimalUtil.parseTaux(partPrivilegiee));
     }

    public ConstructeurParametreCommunalEnMemoire partPrivilegiee(BigDecimal partPrivilegiee) {
        if (0 > noCommuneCourant) throw new RuntimeException("Il faut spécifier la commune avant d'indiquer la part privilégiée !");
        if (0 == anneeCourante) throw new RuntimeException("Il faut spécifier l'année courante avant d'indiquer la part privilégiée !");
        param.partPrivilegiee(anneeCourante,noCommuneCourant,partPrivilegiee);
        return this;
    }

    /**
     * Suivant l’arrêté approuvant le nombre des centimes additionnels à percevoir pour l’année 20AA par les communes du canton de Genève
     * (<a hlink="https://silgeneve.ch/legis/program/books/RSG/htm/rsg_d3_05p30.htm">ArCA-20AA</a>)
     * @param centimes les centimes additionnels, par exemple 48 pour 48 centimes additionnels
     * @return ce constructeur bien utile pour dérouler le pattern Builder
     */
    public ConstructeurParametreCommunalEnMemoire centimes(String centimes) {
        return centimes(new BigDecimal(centimes).movePointLeft(2));
    }


    public ConstructeurParametreCommunalEnMemoire centimes(BigDecimal centimes) {
        if (0 > noCommuneCourant) throw new RuntimeException("Il faut spécifier la commune avant d'indiquer la part privilégiée !");
        if (0 == anneeCourante) throw new RuntimeException("Il faut spécifier l'année courante avant d'indiquer la part privilégiée !");
        param.centimes(anneeCourante,noCommuneCourant,centimes);
        return this;
    }


    public ConstructeurParametreCommunalEnMemoire nbreResident(int nombre) {
        if (0 > noCommuneCourant) throw new RuntimeException("Il faut spécifier la commune avant d'indiquer le nombre de résident !");
        if (0 == anneeCourante) throw new RuntimeException("Il faut spécifier l'année courante avant d'indiquer le nombre de résident !");
        param.nbreResidentAu31Decembre(anneeCourante,noCommuneCourant,nombre);
        return this;
    }

    public ParametreCommunalDao cons() {
        return param;
    }

    static class StructureEnregistrementPartPrivilegieeEtCtsAdd {
        public final String annee;
        public final String noOFS;
        public final String part;
        public final String cts;

        public StructureEnregistrementPartPrivilegieeEtCtsAdd(String ligne) {
            String[] tokens = ligne.split("\t");
            annee = tokens[0];
            noOFS = tokens[1];
            part = tokens[2];
            cts = tokens[3];
        }
    }

    static class StructureEnregistrementResidentAu31decembre {
        public final String noOFS;
        public List<Integer> residentParAnnee;

        public StructureEnregistrementResidentAu31decembre(String ligne) {
            String[] tokens = ligne.split(";");
            noOFS = tokens[0];
            residentParAnnee = Arrays.stream(Arrays.copyOfRange(tokens,2,tokens.length-2)).map(Integer::parseInt).collect(Collectors.toUnmodifiableList());
        }
    }

}
