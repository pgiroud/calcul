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

import org.impotch.calcul.lieu.ICanton;
import org.impotch.calcul.lieu.ICommuneSuisse;
import org.impotch.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.TypeMismatchDataAccessException;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ResidentParCommuneFichierTxtDao implements ResidentParCommuneDao {

    /**************************************************/
    /****************** Attributs *********************/
    /**************************************************/

    final Logger logger = LoggerFactory.getLogger(ResidentParCommuneFichierTxtDao.class);
    private Resource fichier;
    private String charsetName;
    private Map<Integer,Map<ICommuneSuisse,Integer>> mapParAnnee = new HashMap<Integer,Map<ICommuneSuisse,Integer>>();
    private Map<Integer,Integer> indexAnneeDansLeFichier = new HashMap<Integer,Integer>();
    private final ICanton geneve = creerCantonGeneve();

    /**************************************************/
    /******* Accesseurs / Mutateurs *******************/
    /**************************************************/

    /**
     * Indique le fichier contenant les données.
     * @param fichier le fichier texte
     */
    public void setFichier(Resource fichier) {
        this.fichier = fichier;
    }

    /**
     * Précise le codage du fichier texte.
     * @param charsetName
     * @see java.nio.charset.Charset
     */
    public void setCharsetName(String charsetName) {
        this.charsetName = charsetName;
    }

    /**************************************************/
    /******************* Méthodes *********************/
    /**************************************************/

    protected void traiterPremiereLigneFichier(String... tokens) throws ParseException {
        // Il s'agit de la ligne d'entête. L'information importante est les années
        for (int i = 2; i < tokens.length; i++) {
            int annee = Integer.parseInt(tokens[i]);
            indexAnneeDansLeFichier.put(i,annee);
            mapParAnnee.put(annee,new HashMap<ICommuneSuisse,Integer>());
        }
    }

    private ICanton creerCantonGeneve() {
        ICanton geneve = new ICanton() {
            @Override
            public String getCodeIso2() { return "GE";}

            @Override
            public int getNumeroOFS() { return 9025;}

            @Override
            public String getNom() { return "Genève";}
        };
        return geneve;
    }

    private ICommuneSuisse creerCommune(final int noOFS, final String nom) {
        ICommuneSuisse commune = new ICommuneSuisse() {
            @Override
            public ICanton getCanton() { return geneve;}

            @Override
            public int getNumeroOFS() { return noOFS;}

            @Override
            public String getNom() { return nom;}
        };
        return commune;
    }

    protected void traiterLigneFichier(String... tokens) throws ParseException {
        int noOFS = Integer.parseInt(tokens[0]);
        if (noOFS < 8000) {
            String nom = tokens[1];
            ICommuneSuisse commune = creerCommune(noOFS,nom);
            for (int i = 2; i < tokens.length; i++) {
                // Attention, il pourrait bien y avoir des fusions de communes prochainement
                if (StringUtil.hasText(tokens[i])) {
                    Map<ICommuneSuisse,Integer> map = mapParAnnee.get(indexAnneeDansLeFichier.get(i));
                    map.put(commune,Integer.parseInt(tokens[i]));
                }
            }
        }
    }

    /**
     * Lit le fichier texte et stocke les informations en mémoire.
     *
     * @throws java.io.IOException si une erreur survient durant la lecture
     */
    @PostConstruct
    public void chargerResource() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(fichier.getInputStream(),charsetName));
        int numLigne = 1;
        String ligne = reader.readLine();
        try {
            traiterPremiereLigneFichier(ligne.split(";"));
        } catch (ParseException pe) {
            throw new TypeMismatchDataAccessException("Erreur de lecture dans la ressource " + fichier.getFilename() + " à la ligne " + numLigne,pe);
        } catch (NumberFormatException nfe) {
            throw new TypeMismatchDataAccessException("Erreur de lecture dans la ressource " + fichier.getFilename() + " à la ligne " + numLigne,nfe);
        }
        numLigne = 2;
        ligne = reader.readLine();
        while (null != ligne) {
            try {
                traiterLigneFichier(ligne.split(";"));
            } catch (ParseException pe) {
                throw new TypeMismatchDataAccessException("Erreur de lecture dans la ressource " + fichier.getFilename() + " à la ligne " + numLigne,pe);
            } catch (NumberFormatException nfe) {
                throw new TypeMismatchDataAccessException("Erreur de lecture dans la ressource " + fichier.getFilename() + " à la ligne " + numLigne,nfe);
            }
            ligne = reader.readLine();
            numLigne++;
        }
        reader.close();
    }

    public Map<ICommuneSuisse,Integer> getRepartitionAuProrataDeLaPopulation(int annee) {
        return Collections.unmodifiableMap(mapParAnnee.get(annee));
    }
}
