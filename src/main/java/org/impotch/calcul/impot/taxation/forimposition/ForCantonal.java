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
package org.impotch.calcul.impot.taxation.forimposition;

import org.impotch.calcul.lieu.ICanton;
import org.impotch.calcul.lieu.ICommuneSuisse;



/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class ForCantonal extends ForImposition<ICanton> {

	private final ICanton canton;
	
    /**
     * Constructeur.
     */
    public ForCantonal(ICommuneSuisse communeSuisse) {
        super();
        canton = communeSuisse.getCanton();
        this.ajouterCommune(communeSuisse);
    }

	@Override
	public ICanton getLieu() {
		return canton;
	}

    public String getCode() {
        return canton.getCodeIso2();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ForCantonal)) return false;
        ForCantonal oForCanton = (ForCantonal)obj;
        return getCode().equals(oForCanton.getCode());
    }

    @Override
    public int hashCode() {
        return getCode().hashCode();
    }
	
}
