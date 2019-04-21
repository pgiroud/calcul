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
package org.impotch.calcul.impot.cantonal.fr.pp;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.impotch.bareme.Bareme;
import org.impotch.bareme.BaremeTauxEffectifConstantParTranche;
import org.impotch.bareme.ConstructeurBareme;
import org.impotch.calcul.impot.taxation.pp.ProducteurImpotBaseProgressif;
import org.impotch.calcul.impot.cantonal.FournisseurCantonal;
import org.impotch.calcul.impot.taxation.pp.ProducteurImpot;
import org.impotch.calcul.impot.taxation.pp.famille.Splitting;
import org.impotch.calcul.util.ExplicationDetailleTexteBuilder;
import org.impotch.calcul.util.IExplicationDetailleeBuilder;
import org.impotch.util.TypeArrondi;

public class FournisseurCantonalFR extends FournisseurCantonal implements FournisseurRegleImpotCantonalFR {

    private ConcurrentMap<Integer, ProducteurImpot> producteurImpotsICRevenu = new ConcurrentHashMap<Integer, ProducteurImpot>();
    private ConcurrentMap<Integer, ProducteurImpot> producteurImpotsICFortune = new ConcurrentHashMap<Integer, ProducteurImpot>();

    /* (non-Javadoc)
     * @see FournisseurCantonal#construireBaremeRevenu(int)
     */
    @Override
    protected Bareme construireBaremeRevenu(int annee) {
        ConstructeurBaremeTauxEffectifLineaireParTranche cons = new ConstructeurBaremeTauxEffectifLineaireParTranche();
        if (annee > 2005 && annee < 2009) {
            cons.premiereTranche(               4900,   "0")
                    .tranche(           4900,   16600,  "1 %",      "0.0272 %")
                    .tranche(           16600,  29800,  "4.1707 %", "0.0155 %")
                    .tranche(           29800,  45800,  "6.2127 %", "0.0115 %")
                    .tranche(           45800,  60500,  "8.0484 %", "0.0072 %")
                    .tranche(           60500,  74100,  "9.1063 %", "0.0067 %")
                    .tranche(           74100,  142700, "10.0144 %","0.0036 %")
                    .tranche(           142700, 169900, "12.4826 %","0.0022 %")
                    .tranche(           169900, 196200, "13.0804 %","0.0016 %")
                    .derniereTranche(196200,            "13.5 %");
        } else {
            cons.premiereTranche(               5100,   "0")
                    .tranche(           5100,   17300,  "1 %",      "0.0261 %")
                    .tranche(           17300,  31000,  "4.1730 %", "0.0149 %")
                    .tranche(           31000,  47700,  "6.2104 %", "0.0110 %")
                    .tranche(           47700,  63000,  "8.0433 %", "0.0069 %")
                    .tranche(           63000,  76700,  "9.0986 %", "0.0065 %")
                    .tranche(           76700,  100900, "9.9862 %", "0.0036 %")
                    .tranche(           100900, 127200, "10.8571 %","0.0033 %")
                    .tranche(           127200, 154200, "11.7247 %","0.0030 %")
                    .tranche(           154200, 178900, "12.5340 %","0.0023 %")
                    .tranche(           178900, 203900, "13.1014 %","0.0016 %")
                    .derniereTranche(203900,            "13.5 %");
        }
        cons.typeArrondiSurChaqueTranche(TypeArrondi.CINQ_CTS);
        return cons.construire();
    }

    protected ProducteurImpot construireProducteurImpotsICRevenu(int annee) {
        ProducteurImpotBaseProgressif producteurImpotBase = new ProducteurImpotBaseProgressif();
        Splitting splitting = new SplittingFR((BaremeTauxEffectifLineaireParTranche) getBaremeRevenu(annee), "56 %");
        splitting.setTypeArrondi(TypeArrondi.CENT_FRANC_INF);
        producteurImpotBase.setStrategieProductionImpotFamille(splitting);

        producteurImpotBase.setTypeArrondiImposable(TypeArrondi.CENT_FRANC_INF);
        producteurImpotBase.setTypeArrondiDeterminant(TypeArrondi.CENT_FRANC_INF);
        producteurImpotBase.setTypeArrondiImpot(TypeArrondi.CINQ_CTS);


        String codeBeneficiaire = "CAN-FR";
        ProducteurImpot producteur = new ProducteurImpot("RCAN", codeBeneficiaire) {
            @Override
            protected IExplicationDetailleeBuilder createExplicationBuilder() {
                return FournisseurCantonalFR.this.getNewExplicationBuilder();
            }
        };
        producteur.setProducteurBase(producteurImpotBase);
        return producteur;
    }

    @Override
    public ProducteurImpot getProducteurImpotsICRevenu(int annee) {
        if (!producteurImpotsICRevenu.containsKey(annee))
            producteurImpotsICRevenu.putIfAbsent(annee,
                    construireProducteurImpotsICRevenu(annee));
        return producteurImpotsICRevenu.get(annee);
    }


    /* (non-Javadoc)
     * @see FournisseurCantonal#construireBaremeFortune(int)
     */
    @Override
    protected Bareme construireBaremeFortune(int annee) {
        ConstructeurBareme cons = new ConstructeurBareme().fermeAGauche();
        if (annee < 2009) {
            // valable de 2006 (au moins) à 2008 inclus
            cons.premiereTranche(15000, "0")
                    .tranche(15000,20100, "1 ‰")
                    .tranche(20100,30100, "1.25 ‰")
                    .tranche(30100,50100, "1.50 ‰")
                    .tranche(50100,75100, "1.75 ‰")
                    .tranche(75100,100100, "2.00 ‰")
                    .tranche(100100,150100, "2.25 ‰")
                    .tranche(150100,200100, "2.50 ‰")
                    .tranche(200100,300100, "2.60 ‰")
                    .tranche(300100,400100, "2.70 ‰")
                    .tranche(400100,500100, "2.80 ‰")
                    .tranche(500100,600100, "3.00 ‰")
                    .tranche(600100,700100, "3.10 ‰")
                    .tranche(700100,800100, "3.20 ‰")
                    .tranche(800100,900100, "3.30 ‰")
                    .tranche(900100,1000100, "3.40 ‰")
                    .derniereTranche(1000100,"3.50 ‰");
        } else {
            // Il s'agit des barèmes 2009
            cons.premiereTranche(20000, "0")
                    .tranche(20000,25100, "1 ‰")
                    .tranche(25100,35100, "1.25 ‰")
                    .tranche(35100,55100, "1.50 ‰")
                    .tranche(55100,85100, "1.75 ‰")
                    .tranche(85100,125100, "2.00 ‰")
                    .tranche(125100,175100, "2.25 ‰")
                    .tranche(175100,225100, "2.50 ‰")
                    .tranche(225100,325100, "2.60 ‰")
                    .tranche(325100,450100, "2.70 ‰")
                    .tranche(450100,550100, "2.80 ‰")
                    .tranche(550100,650100, "3.00 ‰")
                    .tranche(650100,775100, "3.10 ‰")
                    .tranche(775100,875100, "3.20 ‰")
                    .tranche(875100,975100, "3.30 ‰")
                    .tranche(975100,1100100, "3.40 ‰")
                    .derniereTranche(1100100,"3.50 ‰");
        }
        cons.typeArrondiSurChaqueTranche(TypeArrondi.CINQ_CTS);
        return cons.construireBaremeTauxEffectifConstantParTranche();
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
