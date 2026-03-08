package org.impotch.calcul.impot.taxation.pp.famille;

import org.impotch.bareme.Bareme;
import org.impotch.calcul.impot.taxation.pp.SituationFamiliale;
import org.impotch.calcul.impot.taxation.pp.StrategieProductionImpotFamille;

import java.math.BigDecimal;

/**
 * Le canton de Vaud a un modèle particulier : le système du quotient familial.
 * Contrairement aux systèmes de splitting habituels, le revenu total de la famille n'est pas divisé par
 * un diviseur fixe, mais par un diviseur variable. Celui-ci dépend du nombre de personnes du ménage.
 * Les enfants sont donc aussi pris en compte, le diviseur étant augmenté d'un certain facteur pour chaque enfant.
 * À titre d’exemple, pour les couples mariés, le facteur de division est fixé à 1,8, auquel s’ajoute 0,5 par enfant.
 * Ainsi, un couple marié avec deux enfants bénéficie d’un facteur de splitting de 2,8.
 * Cette façon d’imposer la famille est aussi en vigueur en France.
 *
 */
public class QuotientFamilial extends ImpositionFamille implements StrategieProductionImpotFamille {

    public QuotientFamilial(Bareme bareme) {
        super(bareme);
    }

    @Override
    public BigDecimal produireImpotDeterminant(SituationFamiliale situation, BigDecimal determinantArrondi) {
        // TODO PGI
        // Avec plafonnement
        return null;
    }

}
