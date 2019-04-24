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
package org.impotch.calcul.impot.taxation.repart;

import org.impotch.calcul.impot.taxation.forimposition.ForCommunal;
import org.impotch.calcul.lieu.ICanton;
import org.impotch.calcul.lieu.ICommuneSuisse;
import org.impotch.util.TypeArrondi;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;


public class RepartitionToutesCommunesGETest {

    private Map<Integer,ForCommunal> forsCommunaux = new HashMap<Integer, ForCommunal>(45);

    private ICanton creerCantonGE() {
        return new ICanton() {
            @Override
            public String getCodeIso2() {
                return "GE";
            }

            @Override
            public int getNumeroOFS() {
                return 9026;
            }

            @Override
            public String getNom() {
                return "Genève";
            }
        };
    }

    private ForCommunal creerFor(final ICanton geneve, final int numOFS, final String nom) {
        ICommuneSuisse commune = new ICommuneSuisse() {
            @Override
            public ICanton getCanton() {
                return geneve;
            }

            @Override
            public int getNumeroOFS() {
                return numOFS;
            }

            @Override
            public String getNom() {
                return nom;
            }
        };
        ForCommunal forComm = new ForCommunal(commune);
        forsCommunaux.put(numOFS,forComm);
        return forComm;
    }

    private Part creerPart(int nbreResident) {
        return new Part(BigDecimal.valueOf(nbreResident));
    }

    private Repartition<ForCommunal> creerRepartitionToutesCommunesGESuivantPopulationFin2012() {
        ICanton geneve = creerCantonGE();
        Repartition<ForCommunal> repart = new Repartition<ForCommunal>();
        repart.ajouterPart(creerFor(geneve,6601,"Aire-la-Ville"),creerPart(1119));
        repart.ajouterPart(creerFor(geneve,6602,"Anières"),creerPart(2544));
        repart.ajouterPart(creerFor(geneve,6603,"Avully"),creerPart(1771));
        repart.ajouterPart(creerFor(geneve,6604,"Avusy"),creerPart(1453));
        repart.ajouterPart(creerFor(geneve,6605,"Bardonnex"),creerPart(2232));
        repart.ajouterPart(creerFor(geneve,6606,"Bellevue"),creerPart(3220));
        repart.ajouterPart(creerFor(geneve,6607,"Bernex"),creerPart(9798));
        repart.ajouterPart(creerFor(geneve,6608,"Carouge"),creerPart(20701));
        repart.ajouterPart(creerFor(geneve,6609,"Cartigny"),creerPart(872));
        repart.ajouterPart(creerFor(geneve,6610,"Céligny"),creerPart(632));
        repart.ajouterPart(creerFor(geneve,6611,"Chancy"),creerPart(1139));
        repart.ajouterPart(creerFor(geneve,6612,"Chêne-Bougeries"),creerPart(10608));
        repart.ajouterPart(creerFor(geneve,6613,"Chêne-Bourg"),creerPart(8155));
        repart.ajouterPart(creerFor(geneve,6614,"Choulex"),creerPart(1087));
        repart.ajouterPart(creerFor(geneve,6615,"Collex-Bossy"),creerPart(1660));
        repart.ajouterPart(creerFor(geneve,6616,"Collonge-Bellerive"),creerPart(7517));
        repart.ajouterPart(creerFor(geneve,6617,"Cologny"),creerPart(4898));
        repart.ajouterPart(creerFor(geneve,6618,"Confignon"),creerPart(4326));
        repart.ajouterPart(creerFor(geneve,6619,"Corsier"),creerPart(1922));
        repart.ajouterPart(creerFor(geneve,6620,"Dardagny"),creerPart(1515));
        repart.ajouterPart(creerFor(geneve,6621,"Genève"),creerPart(193150));
        repart.ajouterPart(creerFor(geneve,6622,"Genthod"),creerPart(2709));
        repart.ajouterPart(creerFor(geneve,6623,"Grand-Saconnex"),creerPart(11911));
        repart.ajouterPart(creerFor(geneve,6624,"Gy"),creerPart(478));
        repart.ajouterPart(creerFor(geneve,6625,"Hermance"),creerPart(974));
        repart.ajouterPart(creerFor(geneve,6626,"Jussy"),creerPart(1207));
        repart.ajouterPart(creerFor(geneve,6627,"Laconnex"),creerPart(613));
        repart.ajouterPart(creerFor(geneve,6628,"Lancy"),creerPart(29146));
        repart.ajouterPart(creerFor(geneve,6629,"Meinier"),creerPart(2024));
        repart.ajouterPart(creerFor(geneve,6630,"Meyrin"),creerPart(22221));
        repart.ajouterPart(creerFor(geneve,6631,"Onex"),creerPart(17943));
        repart.ajouterPart(creerFor(geneve,6632,"Perly-Certoux"),creerPart(3004));
        repart.ajouterPart(creerFor(geneve,6633,"Plan-les-Ouates"),creerPart(10309));
        repart.ajouterPart(creerFor(geneve,6634,"Pregny-Chambésy"),creerPart(3626));
        repart.ajouterPart(creerFor(geneve,6635,"Presinge"),creerPart(675));
        repart.ajouterPart(creerFor(geneve,6636,"Puplinge"),creerPart(2037));
        repart.ajouterPart(creerFor(geneve,6637,"Russin"),creerPart(490));
        repart.ajouterPart(creerFor(geneve,6638,"Satigny"),creerPart(3914));
        repart.ajouterPart(creerFor(geneve,6639,"Soral"),creerPart(725));
        repart.ajouterPart(creerFor(geneve,6640,"Thônex"),creerPart(13722));
        repart.ajouterPart(creerFor(geneve,6641,"Troinex"),creerPart(2251));
        repart.ajouterPart(creerFor(geneve,6642,"Vandoeuvres"),creerPart(2573));
        repart.ajouterPart(creerFor(geneve,6643,"Vernier"),creerPart(34322));
        repart.ajouterPart(creerFor(geneve,6644,"Versoix"),creerPart(13019));
        repart.ajouterPart(creerFor(geneve,6645,"Veyrier"),creerPart(10300));
        return repart;
    }

    private ForCommunal getFor(int numOFS) {
        return forsCommunaux.get(numOFS);
    }

    private void controlePresence(Repartition<ForCommunal> repart, int codeCommune, BigDecimal montantAttendu)
    {
        Part oPart = repart.getPart(getFor(codeCommune));
        if (null == oPart)
        {
            fail("La commune " + codeCommune + " n'a pas été trouvée dans la répartition");
        }
        else
        {
            assertThat(oPart.getMontant()).isEqualTo(montantAttendu);
        }
    }


    @Test
    public void testRepartSurToutesCommunes() {
        Repartition<ForCommunal> cle = creerRepartitionToutesCommunesGESuivantPopulationFin2012();
        Repartition<ForCommunal> repart = cle.repartirSurResteARepartir(new BigDecimal("0.60"), TypeArrondi.CINQ_CTS);
        // Carouge (GE) : 0.05
        controlePresence(repart,6608,new BigDecimal("0.05"));
        // Meyrin : 0.05
        controlePresence(repart,6630,new BigDecimal("0.05"));
        // Lancy : 0.05
        controlePresence(repart,6628,new BigDecimal("0.05"));
        // Vernier : 0.05
        controlePresence(repart,6643,new BigDecimal("0.05"));
        // Onex : 0.05
        controlePresence(repart,6631,new BigDecimal("0.05"));
        // Genève : 0.35
        controlePresence(repart,6621,new BigDecimal("0.35"));
    }
}
