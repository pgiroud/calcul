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
/**
 * This file is part of impotch/calcul.
 * <p>
 * impotch/calcul is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 * <p>
 * impotch/calcul is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with impotch/calcul.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.impotch.calcul.impot.cantonal.ne.pp;

import org.impotch.bareme.Bareme;
import org.impotch.calcul.impot.cantonal.FournisseurCantonal;
import org.impotch.util.TypeArrondi;

import static org.impotch.bareme.ConstructeurBareme.unBaremeATauxMarginal;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class FournisseurCantonalNE extends FournisseurCantonal implements
        FournisseurRegleImpotCantonalNE {

    /**
     *
     */
    public FournisseurCantonalNE() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Barème en vigueur depuis 2001
     * @see FournisseurCantonal#construireBaremeFortune(int)
     */
    @Override
    protected Bareme construireBaremeFortune(int annee) {
        return unBaremeATauxMarginal()
                .typeArrondiSurChaqueTranche(TypeArrondi.CINQ_CENTIEMES_INF).seuil(25)
                .jusqua(50000).taux("0")
                .puisJusqua(200000).taux("3 ‰")
                .puisJusqua(350000).taux("4 ‰")
                .puisJusqua(500000).taux("5 ‰")
                .puis().taux("3.6 ‰")
                .construire();
    }

    /**
     * Barème en vigueur depuis 2008
     * @see FournisseurCantonal#construireBaremeRevenu(int)
     */
    @Override
    protected Bareme construireBaremeRevenu(int annee) {
        return unBaremeATauxMarginal()
                .typeArrondiSurChaqueTranche(TypeArrondi.CINQ_CENTIEMES_INF).seuil(25)                .jusqua(5000).taux("0")
                .puisJusqua( 10000).taux(" 2 %")
                .puisJusqua( 15000).taux(" 4 %")
                .puisJusqua( 20000).taux(" 8 %")
                .puisJusqua( 30000).taux("12 %")
                .puisJusqua( 40000).taux("13 %")
                .puisJusqua( 55000).taux("14 %")
                .puisJusqua( 75000).taux("15 %")
                .puisJusqua(110000).taux("16 %")
                .puisJusqua(150000).taux("17 %")
                .puisJusqua(180000).taux("18 %")
                .puis()                 .taux("14.5 %")
                .construire();
    }

}
