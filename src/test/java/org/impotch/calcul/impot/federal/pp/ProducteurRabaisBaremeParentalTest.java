package org.impotch.calcul.impot.federal.pp;

import org.impotch.calcul.impot.federal.FournisseurRegleImpotFederal;
import org.impotch.calcul.impot.taxation.pp.ProducteurImpot;
import org.impotch.calcul.impot.taxation.pp.ProducteurImpotTst;
import org.impotch.calcul.impot.taxation.pp.RecepteurUniqueImpot;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.impotch.calcul.impot.PeriodeFiscale.annee;
import static org.impotch.calcul.impot.federal.ContexteTest_CH.CTX_TST_CH;

public class ProducteurRabaisBaremeParentalTest extends ProducteurImpotTst {
    private FournisseurRegleImpotFederal constructeur = CTX_TST_CH.getFournisseurRegleImpotFederal();

    @Test
    public void rabaisImpot2026UneCharge() {
        ProducteurImpot prod = constructeur.producteurImpotsFederauxPP(2026);
        RecepteurUniqueImpot recepteur = new RecepteurUniqueImpot("RI");
        prod.produireImpot(
                this.creerSituationFamilleAvecEnfant(annee(2026),12),
                this.creerAssiettes(2026, 100000),
                recepteur);
        assertThat(recepteur.getValeur().getMontant()).isEqualTo("-263.00");
    }

    @Test
    public void rabaisImpot2026UneChargeDeterminantPlusGrandQueImposable() {
        ProducteurImpot prod = constructeur.producteurImpotsFederauxPP(2026);
        RecepteurUniqueImpot recepteur = new RecepteurUniqueImpot("RI");
        prod.produireImpot(
                this.creerSituationFamilleAvecEnfant(annee(2026),12),
                this.creerAssiettes(2026, 100000,200000),
                recepteur);
        assertThat(recepteur.getValeur().getMontant()).isEqualTo("-131.50");
    }

    @Test
    public void rabaisImpot2026UneChargeDeterminantPlusPetitQueImposable() {
        ProducteurImpot prod = constructeur.producteurImpotsFederauxPP(2026);
        RecepteurUniqueImpot recepteur = new RecepteurUniqueImpot("RI");
        prod.produireImpot(
                this.creerSituationFamilleAvecEnfant(annee(2026),12),
                this.creerAssiettes(2026, 400000,200000),
                recepteur);
        assertThat(recepteur.getValeur().getMontant()).isEqualTo("-263.00");
    }
}
