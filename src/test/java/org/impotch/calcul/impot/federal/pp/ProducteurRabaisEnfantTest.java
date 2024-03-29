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
package org.impotch.calcul.impot.federal.pp;

import org.impotch.calcul.impot.PeriodeFiscale;
import org.impotch.calcul.impot.taxation.pp.ProducteurImpot;
import org.impotch.calcul.impot.taxation.pp.ProducteurImpotTst;
import org.impotch.calcul.impot.taxation.pp.RecepteurUniqueImpot;
import org.impotch.calcul.impot.federal.FournisseurRegleImpotFederal;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.impotch.calcul.impot.federal.ContexteTest_CH.CTX_TST_CH;


public class ProducteurRabaisEnfantTest extends ProducteurImpotTst {

    private FournisseurRegleImpotFederal constructeur = CTX_TST_CH.getFournisseurRegleImpotFederal();

    @Test
    public void montant_rabais_2010_pour_1_enfant_doit_être_null() {
        RecepteurUniqueImpot recepteur = new RecepteurUniqueImpot("RI");
        ProducteurImpot prod;
        prod= constructeur.getProducteurImpotsFederauxPP(2010);
        prod.produireImpot(this.creerSituationFamilleAvecEnfant(PeriodeFiscale.annee(2010),8),this.creerAssiettes(2011,100000),recepteur);
        assertThat(recepteur.getValeur()).isNull();
    }


    

    @Test
    public void montant_rabais_2011_pour_1_enfant() {
        RecepteurUniqueImpot recepteur = new RecepteurUniqueImpot("RI");
        ProducteurImpot prod;
        prod= constructeur.getProducteurImpotsFederauxPP(2011);
        prod.produireImpot(this.creerSituationFamilleAvecEnfant(PeriodeFiscale.annee(2011),8),this.creerAssiettes(2011,100000),recepteur);
        assertThat(recepteur.getValeur().getMontant()).isEqualTo(new BigDecimal("-250.00"));
    }
}
