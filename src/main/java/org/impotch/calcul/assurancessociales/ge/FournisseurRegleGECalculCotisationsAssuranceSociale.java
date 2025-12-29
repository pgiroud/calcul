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
package org.impotch.calcul.assurancessociales.ge;

import org.impotch.calcul.assurancessociales.FournisseurRegleCalculCotisationsAssuranceSociale;
import org.impotch.calcul.assurancessociales.TypeAssuranceSociale;
import org.impotch.calcul.assurancessociales.ge.param.FournisseurParametrageAnnuelAssurancesSocialesGenevoises;
import org.impotch.calcul.assurancessociales.ge.param.ParametrageAnnuelCotisationsSocialesGenevoises;
import org.impotch.util.BigDecimalUtil;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

public class FournisseurRegleGECalculCotisationsAssuranceSociale implements FournisseurRegleCalculCotisationsAssuranceSociale {

    private final FournisseurRegleCalculCotisationsAssuranceSociale fournisseurCH;
    private final FournisseurParametrageAnnuelAssurancesSocialesGenevoises fournisseurParametrage;

    public FournisseurRegleGECalculCotisationsAssuranceSociale(FournisseurRegleCalculCotisationsAssuranceSociale fournisseurCH, FournisseurParametrageAnnuelAssurancesSocialesGenevoises fournisseurParametrage) {
        this.fournisseurCH = fournisseurCH;
        this.fournisseurParametrage = fournisseurParametrage;
    }

    private static BigDecimal decimal(String taux) {
        return BigDecimalUtil.parse(taux);
    }

    private Optional<Function<BigDecimal, BigDecimal>> regle(ParametrageAnnuelCotisationsSocialesGenevoises param, TypeAssuranceSociale type) {
        switch (type) {
            case AMat -> {
                return Optional.of(r -> r.multiply(decimal(param.tauxAssuranceMaternite())));
            }
            default -> {return fournisseurCH.regle(param.annee(), type);}
        }
    }

    @Override
    public Optional<Function<BigDecimal, BigDecimal>> regle(int annee, TypeAssuranceSociale type) {
        return fournisseurParametrage.parametrage(annee).flatMap(param -> regle(param, type));
    }
}
