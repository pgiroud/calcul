/**
 * This file is part of CalculImpotCH.
 *
 * CalculImpotCH is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * CalculImpotCH is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CalculImpotCH.  If not, see <http://www.gnu.org/licenses/>.
 */
package ch.ge.afc.calcul.impot.cantonal.ge.param.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.TypeMismatchDataAccessException;

/**
 * Cette classe permet l'accès aux paramètres communaux en se basant sur
 * un fichier texte.
 * Les paramètres sont chargés en une fois {@link #chargerResource()} et stockés en mémoire.
 * 
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class ParametreCommunalFichierTxtDao implements ParametreCommunalDao {

	/**************************************************/
    /****************** Attributs *********************/
    /**************************************************/

	final Logger logger = LoggerFactory.getLogger(ParametreCommunalFichierTxtDao.class);
	private Resource fichier;
	private String charsetName;
	
	private Map<CleParametre,BigDecimal> mapPartPrivilegiee = new HashMap<CleParametre,BigDecimal>();
	private Map<CleParametre,BigDecimal> mapCtsAdd = new HashMap<CleParametre,BigDecimal>();
	
	
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

	protected void traiterLigneFichier(String... tokens) throws ParseException {
		int annee = Integer.parseInt(tokens[0]);
		int noOFS = Integer.parseInt(tokens[1]);
		CleParametre cle = new CleParametre(annee,noOFS);
		if (mapPartPrivilegiee.containsKey(cle)) throw new DataIntegrityViolationException("Il ne peut pas y avoir plusieurs entrées pour l'année " + annee + " et pour la commune N° " + noOFS);
		mapPartPrivilegiee.put(cle, new BigDecimal(tokens[2]));
		if (mapCtsAdd.containsKey(cle)) throw new DataIntegrityViolationException("Il ne peut pas y avoir plusieurs entrées pour l'année " + annee + " et pour la commune N° " + noOFS);
		mapCtsAdd.put(cle, new BigDecimal(tokens[3]));
	}
	
	/**
	 * Lit le fichier texte et stocke les informations en mémoire.
	 * 
	 * @throws IOException si une erreur survient durant la lecture
	 */
	@PostConstruct
	public void chargerResource() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(fichier.getInputStream(),charsetName));
		int numLigne = 1;
		String ligne = reader.readLine(); 
		while (null != ligne) {
			try {
				traiterLigneFichier(ligne.split("\t"));
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

	//-------- Implémentation de l'interface ParametreCommunalDao

	public BigDecimal getPartPrivilegiee(int annee, int noOFSCommune) {
		BigDecimal part = mapPartPrivilegiee.get(new CleParametre(annee,noOFSCommune)); 
		if (null == part) throw new EmptyResultDataAccessException(1);
		return part;
	}

	public BigDecimal getTauxCentimes(int annee, int noOFSCommune) {
		BigDecimal taux = mapCtsAdd.get(new CleParametre(annee,noOFSCommune));
		if (null == taux) throw new EmptyResultDataAccessException(1);
		return taux;
	}

	/**************************************************/
    /*********** Classes internes *********************/
    /**************************************************/

	
	private class CleParametre {
		private final int annee;
		private final int noOFS;
		
		public CleParametre(int annee, int noOFS) {
			if (annee < 2000) throw new IllegalArgumentException("L'année " + annee + " ne peut pas être inférieure à 2000");
			if (noOFS > 6700 || noOFS < 6600) throw new IllegalArgumentException("Le numéro OFS " + noOFS + " ne peut pas être plus petit que 6600 ou plus grand que 6700");
			this.annee = annee;
			this.noOFS = noOFS;
		}
		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof CleParametre)) return false;
			CleParametre cle = (CleParametre)obj;
			return this.annee == cle.annee && this.noOFS == cle.noOFS;
		}
		@Override
		public int hashCode() {
            int result = annee;
            result = 37 * result + noOFS;
            return result;
		}
	}
}
