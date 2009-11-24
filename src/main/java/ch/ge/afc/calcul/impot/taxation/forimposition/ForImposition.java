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
package ch.ge.afc.calcul.impot.taxation.forimposition;

import java.util.HashSet;
import java.util.Set;

import ch.ge.afc.calcul.lieu.ICommuneSuisse;
import ch.ge.afc.calcul.lieu.ILieuOFS;


/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public abstract class ForImposition<T extends ILieuOFS> {
	
    private Set<ICommuneSuisse> mListeCommune = new HashSet<ICommuneSuisse>();

    public boolean isEtranger() {
        return false;
    }

    protected void ajouterCommune(ICommuneSuisse inoCommune)
    {
        mListeCommune.add(inoCommune);       
    }

    public void merge(ForImposition<T> oFor)
    {
        mListeCommune.addAll(oFor.getCommune());
    }

    public Set<ICommuneSuisse> getCommune()
    {
        return mListeCommune;
    }

    public abstract T getLieu();

}
