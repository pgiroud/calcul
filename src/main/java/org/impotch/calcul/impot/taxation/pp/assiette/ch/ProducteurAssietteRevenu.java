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
package org.impotch.calcul.impot.taxation.pp.assiette.ch;

import org.impotch.calcul.impot.FournisseurAssietteCommunale;
import org.impotch.calcul.impot.taxation.pp.FournisseurAssiettePeriodique;
import org.impotch.calcul.impot.taxation.repart.Repartition;
import org.impotch.calcul.lieu.IEtatTerritoire;

import java.math.BigDecimal;

/**
 * Created by patrick on 12/02/15.
 */
public class ProducteurAssietteRevenu {



    public FournisseurAssiettePeriodique produireAssiette() {

        FournisseurAssiettePeriodique fournisseur = new FournisseurAssiettePeriodique() {
            @Override
            public int getPeriodeFiscale() {
                return 2015;
            }

            @Override
            public int getNombreJourPourAnnualisation() {
                return 360;
            }

            @Override
            public FournisseurAssietteCommunale getFournisseurAssietteCommunale() {
                return null;
            }

            @Override
            public BigDecimal getMontantImposable() {
                return null;
            }

            @Override
            public BigDecimal getMontantDeterminant() {
                return null;
            }
        }   ;
        return fournisseur;

    }
}
