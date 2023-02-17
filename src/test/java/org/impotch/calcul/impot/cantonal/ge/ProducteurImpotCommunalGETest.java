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
package org.impotch.calcul.impot.cantonal.ge;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.impotch.calcul.impot.Impot;
import org.impotch.calcul.impot.FournisseurAssietteCommunale;
import org.impotch.calcul.impot.RecepteurImpot;
import org.impotch.calcul.impot.cantonal.ge.param.FournisseurParametrageCommunaleGE;
import org.impotch.calcul.impot.cantonal.ge.pp.ProducteurImpotCommunalGEPersPhysique;
import org.impotch.calcul.impot.taxation.forimposition.ForCommunal;
import org.impotch.calcul.impot.taxation.repart.Part;
import org.impotch.calcul.impot.taxation.repart.Repartition;
import org.impotch.calcul.lieu.FournisseurLieu;
import org.impotch.calcul.lieu.ICommuneSuisse;
import org.impotch.calcul.util.IExplicationDetailleeBuilder;
import org.impotch.calcul.util.MockExplicationDetailleeBuilder;

import static org.assertj.core.api.Assertions.assertThat;

public class ProducteurImpotCommunalGETest {

    private FournisseurLieu fournisseurLieu;

    private FournisseurParametrageCommunaleGE fournisseur = ContexteTestCH_GE.CTX_TST_CH_GE.getFournisseurParametrageCommunaleGE();


    @BeforeEach
    public void init() {
        fournisseurLieu = new FournisseurLieu();
    }

    @Test
    public void troisCommune() {
        FournisseurAssietteCommunale fournisseurAssiette = new FournisseurAssietteCommunale() {
            @Override
            public Map<ICommuneSuisse, Integer> getNbreJourDomicileSurCommune() {
                Map<ICommuneSuisse, Integer> map = new HashMap<ICommuneSuisse, Integer>();
                map.put(fournisseurLieu.getCommuneGeneve(), 360);
                return map;
            }

            @Override
            public int getPeriodeFiscale() {
                return 2007;
            }

            @Override
            public Repartition<ForCommunal> getRepartition() {
                Repartition<ForCommunal> repart = new Repartition<ForCommunal>();
                repart.ajouterPart(new ForCommunal(fournisseurLieu.getCommuneAireLaVille()), new Part(new BigDecimal(50301)));
                repart.ajouterPart(new ForCommunal(fournisseurLieu.getCommuneCarouge()), new Part(new BigDecimal(73343)));
                return repart;
            }

        };

        ProducteurImpotCommunalGE prod = new ProducteurImpotCommunalGEPersPhysique("PP", "CTS") {
            @Override
            protected IExplicationDetailleeBuilder createExplicationBuilder() {
                return new MockExplicationDetailleeBuilder();
            }

        };
        prod.setFournisseurParametrage(fournisseur);

        RecepteurImpotsCommunaux recepteur = new RecepteurImpotsCommunaux();
        prod.produireImpot(new BigDecimal("15497.80"), fournisseurAssiette, recepteur);

        assertThat(recepteur.getImpotPartPrivilegiee()).withFailMessage("Part privilégiée").isEqualTo(new BigDecimal("1833.40"));

        String noOFSAireLaVille = String.valueOf(fournisseurLieu.getCommuneAireLaVille().getNumeroOFS());
        assertThat(recepteur.getCtsAdd(noOFSAireLaVille)).withFailMessage("Cts Add Aire-La-Ville").isEqualTo(new BigDecimal("2332.80"));

        String noOFSCarouge = String.valueOf(fournisseurLieu.getCommuneCarouge().getNumeroOFS());
        assertThat(recepteur.getCtsAdd(noOFSCarouge)).withFailMessage("Cts Add Carouge").isEqualTo(new BigDecimal("2653.10"));
    }

    @Test
    public void troisCommuneBis() {
        FournisseurAssietteCommunale fournisseurAssiette = new FournisseurAssietteCommunale() {
            @Override
            public Map<ICommuneSuisse, Integer> getNbreJourDomicileSurCommune() {
                Map<ICommuneSuisse, Integer> map = new HashMap<ICommuneSuisse, Integer>();
                map.put(fournisseurLieu.getCommuneCheneBougerie(), 360);
                return map;
            }

            @Override
            public int getPeriodeFiscale() {
                return 2009;
            }

            @Override
            public Repartition<ForCommunal> getRepartition() {
                Repartition<ForCommunal> repart = new Repartition<ForCommunal>();
                repart.ajouterPart(new ForCommunal(fournisseurLieu.getCommuneCheneBougerie()), new Part(new BigDecimal(30)));
                repart.ajouterPart(new ForCommunal(fournisseurLieu.getCommunePresinge()), new Part(new BigDecimal(20)));
                repart.ajouterPart(new ForCommunal(fournisseurLieu.getCommuneMeyrin()), new Part(new BigDecimal(50)));
                return repart;
            }

        };

        ProducteurImpotCommunalGE prod = new ProducteurImpotCommunalGEPersPhysique("PP", "CTS") {
            @Override
            protected IExplicationDetailleeBuilder createExplicationBuilder() {
                return new MockExplicationDetailleeBuilder();
            }

        };
        prod.setFournisseurParametrage(fournisseur);

        RecepteurImpotsCommunaux recepteur = new RecepteurImpotsCommunaux();
        prod.produireImpot(new BigDecimal("8843.80"), fournisseurAssiette, recepteur);

        assertThat(recepteur.getPartPrivilegiee())
                .withFailMessage("Part privilégiée").isEqualTo(new BigDecimal("2387.85"));
        assertThat(recepteur.getImpotPartPrivilegiee())
                .withFailMessage("Impôt Part privilégiée").isEqualTo(new BigDecimal("811.85"));

        String noOFSCheneBougerie = String.valueOf(fournisseurLieu.getCommuneCheneBougerie().getNumeroOFS());
        assertThat(recepteur.getPart(noOFSCheneBougerie))
                .withFailMessage("Part Chêne-Bougerie").isEqualTo(new BigDecimal("1936.80"));
        assertThat(recepteur.getCtsAdd(noOFSCheneBougerie))
                .withFailMessage("Cts Add Chêne-Bougerie").isEqualTo(new BigDecimal("658.50"));

        String noOFSPresinge = String.valueOf(fournisseurLieu.getCommunePresinge().getNumeroOFS());
        assertThat(recepteur.getPart(noOFSPresinge))
                .withFailMessage("Part Presinge").isEqualTo(new BigDecimal("1291.20"));
        assertThat(recepteur.getCtsAdd(noOFSPresinge))
                .withFailMessage("Cts Add Presinge").isEqualTo(new BigDecimal("503.55"));

        String noOFSMeyrin = String.valueOf(fournisseurLieu.getCommuneMeyrin().getNumeroOFS());
        assertThat(recepteur.getPart(noOFSMeyrin))
                .withFailMessage("Part Meyrin").isEqualTo(new BigDecimal("3227.95"));
        assertThat(recepteur.getCtsAdd(noOFSMeyrin))
                .withFailMessage("Cts Add Meyrin").isEqualTo(new BigDecimal("1452.60"));

    }

    private class RecepteurImpotsCommunaux implements RecepteurImpot {
        private BigDecimal partPrivilegiee;
        private BigDecimal impotPartPrivilegiee;
        private Map<String, BigDecimal> partRepart = new HashMap<String, BigDecimal>();
        private Map<String, BigDecimal> ctsadd = new HashMap<String, BigDecimal>();

        /* (non-Javadoc)
         * @see org.impotch.calcul.impot.RecepteurImpot#ajouteImpot(java.math.BigDecimal, java.lang.String, java.lang.String)
         */
        @Override
        public void ajouteImpot(Impot impot) {
            if ("PP".equals(impot.getTypeImpot())) {
                partPrivilegiee = impot.getBaseCalcul();
                impotPartPrivilegiee = impot.getMontant();
            } else {
                partRepart.put(impot.getCodeBeneficiaire(), impot.getBaseCalcul());
                ctsadd.put(impot.getCodeBeneficiaire(), impot.getMontant());
            }
        }

        public BigDecimal getPartPrivilegiee() {
            return partPrivilegiee;
        }

        public BigDecimal getImpotPartPrivilegiee() {
            return impotPartPrivilegiee;
        }

        public BigDecimal getPart(String codeBeneficiaire) {
            return partRepart.get(codeBeneficiaire);
        }

        public BigDecimal getCtsAdd(String codeBeneficiaire) {
            return ctsadd.get(codeBeneficiaire);
        }
    }

}
