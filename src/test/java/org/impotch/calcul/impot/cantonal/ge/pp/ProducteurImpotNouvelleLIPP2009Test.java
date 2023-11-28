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



import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.impotch.bareme.Bareme;
import org.impotch.calcul.impot.indexation.FournisseurIndicePeriodique;

import org.impotch.calcul.impot.taxation.pp.ProducteurImpotBaseProgressif;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.impotch.calcul.impot.cantonal.ge.pp.ConstructeurBaremeParTrancheIndexe.unConstructeurDeBaremeParTrancheIndexee;
import static org.impotch.calcul.impot.taxation.pp.ProducteurImpotBaseProgressif.unProducteurImpotBaseProgressif;
import static org.impotch.calcul.impot.taxation.pp.StrategieProductionImpotFamille.splittingIntegral;

public class ProducteurImpotNouvelleLIPP2009Test extends ProducteurImpotTst {

    private final static TypeArrondi ARRONDI_ASSIETTE = TypeArrondi.UNITE_LA_PLUS_PROCHE;
    private final static TypeArrondi ARRONDI_IMPOT = TypeArrondi.CINQ_CENTIEMES_LES_PLUS_PROCHES;

    private ProducteurImpot producteur;

    private FournisseurIndicePeriodique construireIndexateurSurBaseIndice() {
        // Inutile de se préoccuper du renchérissement, le barème est celui de 2009 dans la loi.
        SimpleFournisseurIndicePeriodique fournisseur = new SimpleFournisseurIndicePeriodique();
        Map<Integer, BigDecimal> indices = new HashMap();
        indices.put(2009, BigDecimal.ONE);
        fournisseur.setIndices(indices);
        return fournisseur;
    }


    @BeforeEach
    public void setUp() throws Exception {
        Bareme bareme = unConstructeurDeBaremeParTrancheIndexee()
                // Attention, ce barème n'a jamais été utilisé.
                // Il a simplement servi de base pour les barèmes 2010, 2011, ...
                .valideDepuis(2009)
                .anneeReferenceRencherissement(2009)
                .indexateur(construireIndexateurSurBaseIndice())
                .typeArrondiTranche(TypeArrondi.CINQ_CENTIEMES_LES_PLUS_PROCHES)
                .typeArrondiGlobal(TypeArrondi.CINQ_CENTIEMES_LES_PLUS_PROCHES)
                .jusqua(17493).taux("0 %")
                .puisJusqua(21076).taux("8 %")
                .puisJusqua(23184).taux("9 %")
                .puisJusqua(25291).taux("10 %")
                .puisJusqua(27399).taux("11 %")
                .puisJusqua(32668).taux("12 %")
                .puisJusqua(36883).taux("13 %")
                .puisJusqua(41099).taux("14 %")
                .puisJusqua(45314).taux("14.5 %")
                .puisJusqua(72713).taux("15 %")
                .puisJusqua(119081).taux("15.5 %")
                .puisJusqua(160179).taux("16 %")
                .puisJusqua(181256).taux("16.5 %")
                .puisJusqua(259238).taux("17 %")
                .puisJusqua(276099).taux("17.5 %")
                .puisJusqua( 388857).taux("18 %")
                .puisJusqua(609103).taux("18.5 %")
                .puis().taux("19 %")
                .construire(2009);

         producteur = new ProducteurImpot("IBR", "CAN-GE");
        producteur.setProducteurBase(
                unProducteurImpotBaseProgressif(splittingIntegral(bareme))
                        .arrondiAssiettes(ARRONDI_ASSIETTE)
                        .arrondiImpot(ARRONDI_IMPOT)
                        .construire()
        );
    }

    private void marie(final int periodeFiscale, final int montantImposable, final String montantImpot, final int... ageEnfant) {
        SituationFamiliale situation = creerSituationFamilleAvecEnfant(ageEnfant);
        FournisseurAssiettePeriodique fournisseur = this.creerAssiettes(periodeFiscale, montantImposable);
        RecepteurUniqueImpot recepteurIBR = new RecepteurUniqueImpot("IBR");
        producteur.produireImpot(situation, fournisseur, recepteurIBR);
        assertThat(recepteurIBR.getValeur().getMontant()).isEqualTo(new BigDecimal(montantImpot));
    }

    @Test
    public void marie() {
        marie(2009, 85000, "5785.20", 8, 6);
    }

}
