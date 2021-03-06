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
package org.impotch.calcul.impot.cantonal.ge.param;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Map;

import org.impotch.calcul.impot.cantonal.ge.param.dao.ParametreCommunalDao;
import org.impotch.calcul.impot.taxation.forimposition.ForCommunal;
import org.impotch.calcul.impot.taxation.repart.Part;
import org.impotch.calcul.impot.taxation.repart.Repartition;
import org.impotch.calcul.lieu.ICommuneSuisse;

/**
 * Simple implémentation déléguant au dao.
 * 
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class FournisseurParamCommunalGE implements
		FournisseurParametrageCommunaleGE {

	/**************************************************/
    /****************** Attributs *********************/
    /**************************************************/
	
	private ParametreCommunalDao dao;
	
	/**************************************************/
    /******* Accesseurs / Mutateurs *******************/
    /**************************************************/
		
	public void setDao(ParametreCommunalDao dao) {
		this.dao = dao;
	}
	
	/**************************************************/
    /******************* Méthodes *********************/
    /**************************************************/

	//-------------- Implémentation de l'interface FournisseurParametrageCommunaleGE
	
	@Override
	public BigDecimal getPartPrivilegiee(int annee, ICommuneSuisse commune) {
		if (!"GE".equals(commune.getCanton().getCodeIso2())) throw new IllegalArgumentException("La commune " + commune + " n'est pas une commune genevoise !");
		return dao.getPartPrivilegiee(annee, commune.getNumeroOFS());
	}
	@Override
	public BigDecimal getTauxCentimes(int annee, ICommuneSuisse commune) {
		if (!"GE".equals(commune.getCanton().getCodeIso2())) throw new IllegalArgumentException("La commune " + commune + " n'est pas une commune genevoise !");
		return dao.getTauxCentimes(annee, commune.getNumeroOFS());
	}

    @Override
    public Repartition<ForCommunal> getRepartitionAuProrataDeLaPopulation(int annee) {
        int anneeCourante = Calendar.getInstance().get(Calendar.YEAR);
        if (annee > anneeCourante) throw new IllegalArgumentException("Impossible de déterminer la répartition pour l'année '" + annee + "' dans le futur !!");
        Map<ICommuneSuisse,Integer> population = dao.getRepartitionAuProrataDeLaPopulation(annee);
        Repartition<ForCommunal> repartition = new Repartition<ForCommunal>();
        for (ICommuneSuisse commune : population.keySet()) {
            int nbreResident = population.get(commune);
            repartition.ajouterPart(new ForCommunal(commune),new Part(BigDecimal.valueOf(nbreResident)));
        }
        return repartition;
    }
}
