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
package org.impotch.calcul.impot.cantonal.ge.pp;


import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.impotch.bareme.Bareme;
import org.impotch.calcul.impot.indexation.FournisseurIndicePeriodique;
import org.junit.Before;
import org.junit.Test;

import org.impotch.calcul.impot.taxation.pp.ProducteurImpotBaseProgressif;
import org.impotch.calcul.impot.cantonal.ge.pp.avant2010.ConstructeurBaremeIndexeTxMarginalConstantParTranche;
import org.impotch.calcul.impot.indexation.SimpleFournisseurIndicePeriodique;
import org.impotch.calcul.impot.taxation.pp.FournisseurAssiettePeriodique;
import org.impotch.calcul.impot.taxation.pp.ProducteurImpot;
import org.impotch.calcul.impot.taxation.pp.ProducteurImpotTst;
import org.impotch.calcul.impot.taxation.pp.RecepteurUniqueImpot;
import org.impotch.calcul.impot.taxation.pp.SituationFamiliale;
import org.impotch.calcul.impot.taxation.pp.famille.Splitting;
import org.impotch.calcul.util.ExplicationDetailleTexteBuilder;
import org.impotch.calcul.util.IExplicationDetailleeBuilder;
import org.impotch.util.TypeArrondi;

public class ProducteurImpotNouvelleLIPP2009Test extends ProducteurImpotTst {

    private ProducteurImpot producteur;

    private FournisseurIndicePeriodique construireIndexateurSurBaseIndice() {
        // Inutile de se préoccuper du renchérissement, le barème est celui de 2009 dans la loi.
        SimpleFournisseurIndicePeriodique fournisseur = new SimpleFournisseurIndicePeriodique();
        Map<Integer, BigDecimal> indices = new HashMap();
        indices.put(2009, BigDecimal.ONE);
        fournisseur.setIndices(indices);
        return fournisseur;
    }


    @Before
    public void setUp() throws Exception {
        Bareme bareme = new ConstructeurBaremeIndexeTxMarginalConstantParTranche()
                // Attention, ce barème n'a jamais été utilisé.
                // Il a simplement servi de base pour les barèmes 2010, 2011, ...
                .valideDepuis(2009)
                .anneeReferenceRencherissement(2009)
                .indexateur(construireIndexateurSurBaseIndice())
                .premiereTranche(17493, "0 %")
                .tranche(17493, 21076, "8 %")
                .tranche(21076, 23184, "9 %")
                .tranche(23184, 25291, "10 %")
                .tranche(25291, 27399, "11 %")
                .tranche(27399, 32668, "12 %")
                .tranche(32668, 36883, "13 %")
                .tranche(36883, 41099, "14 %")
                .tranche(41099, 45314, "14.5 %")
                .tranche(45314, 72713, "15 %")
                .tranche(72713, 119081, "15.5 %")
                .tranche(119081, 160179, "16 %")
                .tranche(160179, 181256, "16.5 %")
                .tranche(181256, 259238, "17 %")
                .tranche(259238, 276099, "17.5 %")
                .tranche(276099, 388857, "18 %")
                .tranche(388857, 609103, "18.5 %")
                .derniereTranche(609103, "19 %")
                .typeArrondiTranche(TypeArrondi.CINQ_CTS).construire(2009);

        ProducteurImpotBaseProgressif producteurBase = new ProducteurImpotBaseProgressif();
        producteurBase.setStrategieProductionImpotFamille(new Splitting(bareme, "50 %"));


        producteur = new ProducteurImpot("IBR", "CAN-GE") {
            @Override
            protected IExplicationDetailleeBuilder createExplicationBuilder() {
                return new ExplicationDetailleTexteBuilder();
            }
        };
        producteur.setProducteurBase(producteurBase);
    }

    private void marie(final int periodeFiscale, final int montantImposable, final String montantImpot, final int... ageEnfant) {
        SituationFamiliale situation = creerSituationFamilleAvecEnfant(ageEnfant);
        FournisseurAssiettePeriodique fournisseur = this.creerAssiettes(periodeFiscale, montantImposable);
        RecepteurUniqueImpot recepteurIBR = new RecepteurUniqueImpot("IBR");
        producteur.produireImpot(situation, fournisseur, recepteurIBR);
        assertEquals("Revenu de " + montantImposable, new BigDecimal(montantImpot), recepteurIBR.getValeur().getMontant());
    }

    @Test
    public void marie() {
        marie(2009, 85000, "5785.20", 8, 6);
    }

}
