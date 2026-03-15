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
package org.impotch.calcul.impot.cantonal.ge.param;

import java.math.BigDecimal;

import org.impotch.calcul.impot.cantonal.ge.param.dao.ParametreCommunalDao;
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
    public int getNombreResidentAu31decembre(int annee, ICommuneSuisse commune) {
        if (!"GE".equals(commune.getCanton().getCodeIso2())) throw new IllegalArgumentException("La commune " + commune + " n'est pas une commune genevoise !");
        return dao.getNombreResidentAu31decembre(annee, commune.getNumeroOFS());
    }
}
