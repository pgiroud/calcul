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

import java.math.BigDecimal;
import java.util.Map;

import org.impotch.calcul.lieu.ICommuneSuisse;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

/**
 * Cette classe fournit les paramètres communaux en se basant sur les données de la
 * Refonte AFC.
 * Les données ne sont pas stockées en mémoire. Un accès à la base de données est effectué à chaque
 * invocation de méthodes.
 * 
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class ParametreCommunalJdbcRTaxPPDao extends SimpleJdbcDaoSupport implements ParametreCommunalDao {

	/**************************************************/
    /*************** Champs statiques *****************/
    /**************************************************/

	private static final String FROM = "FROM RTX_T_PARAM_IMP_COMMUNAL PPC " 
	+	"JOIN RTX_T_CONTEXTE_FISCAL PPCF ON PPC.PIC_RTX_CONTEXTE_FISCAL_N_ID = PPCF.CFI_N_ID "
	+	"JOIN AFC_T_BENEFICIAIRE_LIEU BL ON PPC.PIC_BEL_BNF_N_ID = BL.BEL_BNF_N_ID "
	+	"JOIN LFI_T_LIEU_FISCAL LFI ON BL.BEL_LOC_N_ID = LFI.LOC_N_ID "
	+	"WHERE PPCF.CFI_C_PERIODE = :periode AND  LFI.LOC_N_NO_OFS = :noOFS"; 

	/**************************************************/
    /******************* Méthodes *********************/
    /**************************************************/
		
	//-------- Implémentation de l'interface ParametreCommunalDao
	
	@Override
	public BigDecimal getPartPrivilegiee(int annee, int noOFSCommune) {
		String sql = "SELECT PPC.PIC_N_PART_PRIVILEGIEE / 100 " + FROM;
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("periode", annee);
		param.addValue("noOFS", noOFSCommune);
		return this.getSimpleJdbcTemplate().queryForObject(sql, BigDecimal.class, param);
	}

	@Override
	public BigDecimal getTauxCentimes(int annee, int noOFSCommune) {
		String sql = "SELECT PPC.PIC_N_TAUX / 100 " + FROM;
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("periode", annee);
		param.addValue("noOFS", noOFSCommune);
		return this.getSimpleJdbcTemplate().queryForObject(sql, BigDecimal.class, param);
	}

    @Override
    public Map<ICommuneSuisse, Integer> getRepartitionAuProrataDeLaPopulation(int annee) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
