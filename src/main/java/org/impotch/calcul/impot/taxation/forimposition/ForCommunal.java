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
package org.impotch.calcul.impot.taxation.forimposition;

import org.impotch.calcul.lieu.ICommuneSuisse;


/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class ForCommunal extends ForImposition<ICommuneSuisse> {

    public static ForCommunal forCommunal(ICommuneSuisse commune) {
        return new ForCommunal(commune);
    }

	private final ICommuneSuisse commune;
	
	private ForCommunal(ICommuneSuisse commune) {
		super();
		this.commune = commune;
	}

	@Override
	public ICommuneSuisse getLieu() {
		return commune;
	}

	
    public String getCode() {
        return String.valueOf(commune.getNumeroOFS());
    }

    @Override
    public int hashCode() {
        return getCode().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ForCommunal)) return false;
        ForCommunal oForCommunal = (ForCommunal)obj;
        return getCode().equals(oForCommunal.getCode());
    }
	
}
