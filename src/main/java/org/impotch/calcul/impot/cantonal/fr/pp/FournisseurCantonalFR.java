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
 * <p>
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
package org.impotch.calcul.impot.cantonal.fr.pp;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.impotch.bareme.Bareme;
import org.impotch.bareme.BaremeTauxEffectifLineaireParTranche;
import org.impotch.bareme.BaremeTauxEffectifParTranche;
import org.impotch.calcul.impot.taxation.pp.ProducteurImpotBase;
import org.impotch.calcul.impot.taxation.pp.ProducteurImpotBaseProgressif;
import org.impotch.calcul.impot.cantonal.FournisseurCantonal;
import org.impotch.calcul.impot.taxation.pp.ProducteurImpot;
import org.impotch.calcul.impot.taxation.pp.famille.Splitting;
import org.impotch.calcul.util.ExplicationDetailleTexteBuilder;
import org.impotch.calcul.util.IExplicationDetailleeBuilder;
import org.impotch.util.TypeArrondi;

import static org.impotch.bareme.ConstructeurBareme.unBaremeATauxEffectif;
import static org.impotch.calcul.impot.cantonal.fr.pp.SplittingFR.unSplittingFribourgeois;
import static org.impotch.calcul.impot.taxation.pp.ProducteurImpotBaseProgressif.unProducteurImpotBaseProgressif;


public class FournisseurCantonalFR extends FournisseurCantonal implements FournisseurRegleImpotCantonalFR {

    private final static TypeArrondi ARRONDI_ASSIETTE_REVENU = TypeArrondi.CENTAINE_INF;
    private final static TypeArrondi ARRONDI_IMPOT = TypeArrondi.CINQ_CENTIEMES_LES_PLUS_PROCHES;

    private ConcurrentMap<Integer, ProducteurImpot> producteurImpotsICRevenu = new ConcurrentHashMap<>();
    private ConcurrentMap<Integer, SplittingFR> splittingParAnnee = new ConcurrentHashMap<>();
    private ConcurrentMap<Integer, ProducteurImpot> producteurImpotsICFortune = new ConcurrentHashMap<>();

    private BaremeTauxEffectifParTranche construireBaremeRevenuEntre2006inclusEt2008inclus() {
        return
                (BaremeTauxEffectifParTranche)unBaremeATauxEffectif()
                        .typeArrondiSurChaqueTranche(TypeArrondi.CINQ_CENTIEMES_LES_PLUS_PROCHES)
                        .fermeAGauche()
                        .jusqua(4900).taux("0")
                        .de(4900).a(16600).taux("1 %").increment("0.000272 %")
                        .de(16600).a(29800).taux("4.1707 %").increment("0.000155 %")
                        .de(29800).a(45800).taux("6.2127 %").increment("0.000115 %")
                        .de(45800).a(60500).taux("8.0484 %").increment("0.000072 %")
                        .de(60500).a(74100).taux("9.1063 %").increment("0.000067 %")
                        .de(74100).a(142700).taux("10.0144 %").increment("0.000036 %")
                        .de(142700).a(169900).taux("12.4826 %").increment("0.000022 %")
                        .de(169900).a(196200).taux("13.0804 %").increment("0.000016 %")
                        .plusDe(196200).taux("13.5 %")
                        .construire();
    }

    private SplittingFR construireSplittingEntre2006inclusEt2008inclus() {
        return unSplittingFribourgeois(construireBaremeRevenuEntre2006inclusEt2008inclus(),"56 %")
                .montantImposableMinimum(4900)
                .tauxMinimum("1 %").construire();
    }

    private BaremeTauxEffectifParTranche construireBaremeRevenuAvant2005inclus() {
        return
                (BaremeTauxEffectifParTranche)unBaremeATauxEffectif()
                        .typeArrondiSurChaqueTranche(TypeArrondi.CINQ_CENTIEMES_LES_PLUS_PROCHES)
                        .fermeAGauche()
                        .jusqua(5100).taux("0")
                        .de(5100).a(17300).taux("1 %").increment("0.000261 %")
                        .de(17300).a(31000).taux("4.1730 %").increment("0.000149 %")
                        .de(31000).a(47700).taux("6.2104 %").increment("0.000110 %")
                        .de(47700).a(63000).taux("8.0433 %").increment("0.000069 %")
                        .de(63000).a(76700).taux("9.0986 %").increment("0.000065 %")
                        .de(76700).a(100900).taux("9.9862 %").increment("0.000036 %")
                        .de(100900).a(127200).taux("10.8571 %").increment("0.000033 %")
                        .de(127200).a(154200).taux("11.7247 %").increment("0.000030 %")
                        .de(154200).a(178900).taux("12.5340 %").increment("0.000023 %")
                        .de(178900).a(203900).taux("13.1014 %").increment("0.000016 %")
                        .plusDe(203900).taux("13.5 %")
                        .construire();

    }


    private SplittingFR construireSplittingAvant2005inclus() {
        return unSplittingFribourgeois(construireBaremeRevenuAvant2005inclus(),"56 %")
                .montantImposableMinimum(5100)
                .tauxMinimum("1 %").construire();
    }

    protected SplittingFR construireSplittingFR(int annee) {
        if (annee >= 2006 && annee <= 2008) {
            return construireSplittingEntre2006inclusEt2008inclus();
        } else {
            return construireSplittingAvant2005inclus();
        }
    }

    @Override
    protected Bareme construireBaremeRevenu(int annee) {
        if (annee >= 2006 && annee <= 2008) {
            return construireBaremeRevenuEntre2006inclusEt2008inclus();
        } else {
            return construireBaremeRevenuAvant2005inclus();
        }
    }

    protected ProducteurImpot construireProducteurImpotsICRevenu(int annee) {

        String codeBeneficiaire = "CAN-FR";
        ProducteurImpot producteur = new ProducteurImpot("RCAN", codeBeneficiaire);
        producteur.setProducteurBase(
                unProducteurImpotBaseProgressif(construireSplittingFR(annee))
                        .arrondiAssiettes(ARRONDI_ASSIETTE_REVENU)
                        .arrondiImpot(ARRONDI_IMPOT)
                        .construire()
        );
        return producteur;
    }

    @Override
    public ProducteurImpot getProducteurImpotsICRevenu(int annee) {
        if (!producteurImpotsICRevenu.containsKey(annee))
            producteurImpotsICRevenu.putIfAbsent(annee,
                    construireProducteurImpotsICRevenu(annee));
        return producteurImpotsICRevenu.get(annee);
    }

    private Bareme construireBaremeFortuneAvant2008inclus() {
        return unBaremeATauxEffectif().fermeAGauche()
                .jusqua(15000).taux("0")
                .de(15000).a(20100).taux("1 ‰")
                .de(20100).a(30100).taux("1.25 ‰")
                .de(30100).a(50100).taux("1.50 ‰")
                .de(50100).a(75100).taux("1.75 ‰")
                .de(75100).a(100100).taux("2.00 ‰")
                .de(100100).a(150100).taux("2.25 ‰")
                .de(150100).a(200100).taux("2.50 ‰")
                .de(200100).a(300100).taux("2.60 ‰")
                .de(300100).a(400100).taux("2.70 ‰")
                .de(400100).a(500100).taux("2.80 ‰")
                .de(500100).a(600100).taux("3.00 ‰")
                .de(600100).a(700100).taux("3.10 ‰")
                .de(700100).a(800100).taux("3.20 ‰")
                .de(800100).a(900100).taux("3.30 ‰")
                .de(900100).a(1000100).taux("3.40 ‰")
                .plusDe(1000100).taux("3.50 ‰")
                .typeArrondiSurChaqueTranche(TypeArrondi.CINQ_CENTIEMES_LES_PLUS_PROCHES).construire();
    }

    private Bareme construireBaremeFortuneApres2009inclus() {
        return unBaremeATauxEffectif().fermeAGauche()
                .jusqua(20000).taux("0")
                .de(20000).a(25100).taux("1 ‰")
                .de(25100).a(35100).taux("1.25 ‰")
                .de(35100).a(55100).taux("1.50 ‰")
                .de(55100).a(85100).taux("1.75 ‰")
                .de(85100).a(125100).taux("2.00 ‰")
                .de(125100).a(175100).taux("2.25 ‰")
                .de(175100).a(225100).taux("2.50 ‰")
                .de(225100).a(325100).taux("2.60 ‰")
                .de(325100).a(450100).taux("2.70 ‰")
                .de(450100).a(550100).taux("2.80 ‰")
                .de(550100).a(650100).taux("3.00 ‰")
                .de(650100).a(775100).taux("3.10 ‰")
                .de(775100).a(875100).taux("3.20 ‰")
                .de(875100).a(975100).taux("3.30 ‰")
                .de(975100).a(1100100).taux("3.40 ‰")
                .plusDe(1100100).taux("3.50 ‰")
                .typeArrondiSurChaqueTranche(TypeArrondi.CINQ_CENTIEMES_LES_PLUS_PROCHES).construire();

    }

    /* (non-Javadoc)
     * @see FournisseurCantonal#construireBaremeFortune(int)
     */
    @Override
    protected Bareme construireBaremeFortune(int annee) {
        if (annee < 2009) {
            // valable de 2006 (au moins) à 2008 inclus
            return construireBaremeFortuneAvant2008inclus();
        } else {
            return construireBaremeFortuneApres2009inclus();
        }
    }

    protected ProducteurImpot construireProducteurImpotsICFortune(int annee) {
        // TODO PGI
        return null;
    }

    @Override
    public ProducteurImpot getProducteurImpotsICFortune(int annee) {
        if (!producteurImpotsICFortune.containsKey(annee))
            producteurImpotsICFortune.putIfAbsent(annee,
                    construireProducteurImpotsICFortune(annee));
        return producteurImpotsICFortune.get(annee);
    }


    private IExplicationDetailleeBuilder getNewExplicationBuilder() {
        return new ExplicationDetailleTexteBuilder();
    }


}
