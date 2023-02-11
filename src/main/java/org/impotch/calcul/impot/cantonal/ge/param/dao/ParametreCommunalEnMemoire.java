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

import org.impotch.calcul.lieu.ICommuneSuisse;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

class ParametreCommunalEnMemoire implements ParametreCommunalDao {

    private final Map<ParametreCommunalEnMemoire.CleParametre, BigDecimal> mapPartPrivilegiee = new HashMap<>();
    private final Map<ParametreCommunalEnMemoire.CleParametre,BigDecimal> mapCtsAdd = new HashMap<>();

    void partPrivilegiee(int annee, int noOFSCommune, BigDecimal part) {
        mapPartPrivilegiee.put(new CleParametre(annee, noOFSCommune), part);
    }

    void centimes(int annee, int noOFSCommune, BigDecimal taux) {
        mapCtsAdd.put(new CleParametre(annee, noOFSCommune), taux);
    }

    //-------- Implémentation de l'interface ParametreCommunalDao

    @Override
    public BigDecimal getPartPrivilegiee(int annee, int noOFSCommune) {
        BigDecimal part = mapPartPrivilegiee.get(new CleParametre(annee,noOFSCommune));
        if (null == part) throw new RuntimeException("La part privilégiée pour l’année " + annee + " et la commune " + noOFSCommune + " n'est pas chargée !");
        return part;
    }

    @Override
    public BigDecimal getTauxCentimes(int annee, int noOFSCommune) {
        BigDecimal taux = mapCtsAdd.get(new CleParametre(annee,noOFSCommune));
        if (null == taux) throw new RuntimeException("Le taux de centimes pour l’année " + annee + " et la commune " + noOFSCommune + " n'est pas chargée !");
        return taux;
    }


    @Override
    public Map<ICommuneSuisse, Integer> getRepartitionAuProrataDeLaPopulation(int annee) {
        return null;
    }

/**************************************************/
    /*********** Classes internes *********************/
    /**************************************************/


    private static class CleParametre {
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
