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
package org.impotch.calcul.impot.cantonal.ge.pp.avant2010;

import java.util.*;

import org.impotch.calcul.impot.Souverainete;
import org.impotch.calcul.impot.taxation.pp.*;

import static org.impotch.calcul.impot.taxation.pp.ConstructeurSituationFamiliale.personneSeule;
import static org.impotch.calcul.impot.taxation.pp.ConstructeurSituationFamiliale.couple;

public class ConstructeurSituationFamilialeGE {

    public static ConstructeurSituationFamilialeGE unePersonneSeule() {
        return new ConstructeurSituationFamilialeGE(personneSeule());
    }

    public static ConstructeurSituationFamilialeGE unCouple() {
        return new ConstructeurSituationFamilialeGE(couple());
    }

    public static ConstructeurSituationFamilialeGE unConjointDeFonctionnaireInternational() {
        return new ConstructeurSituationFamilialeGE(true, couple());
    }

    private ConstructeurSituationFamiliale constructeurSituationFamiliale;
    private boolean coupleDomicilieGE;
    private final boolean conjointFonctionnaireInternational;
    // Les couples avec un fonctionnaires internationales sont considérés en couple pour IFD mais pas à l'ICC (pour rabais d’impôt)
    // On a donc besoin du contexte
    private boolean contexteIFD;

    public ConstructeurSituationFamilialeGE(ConstructeurSituationFamiliale constructeurSituationFamiliale) {
        this.constructeurSituationFamiliale = constructeurSituationFamiliale;
        conjointFonctionnaireInternational = false;
    }

    public ConstructeurSituationFamilialeGE(boolean conjointFonctionnaireInternational, ConstructeurSituationFamiliale constructeurSituationFamiliale) {
        this.conjointFonctionnaireInternational = conjointFonctionnaireInternational;
        this.constructeurSituationFamiliale = constructeurSituationFamiliale;
    }

    public ConstructeurSituationFamilialeGE ifd() {
        this.contexteIFD = true;
        return this;
    }

    public ConstructeurSituationFamilialeGE enfant() {
        ConstructeurSituationFamiliale cons = constructeurSituationFamiliale.enfant();
        if (this.conjointFonctionnaireInternational) {
            cons.demiPart(Souverainete.CH_CANTONALE_GE);
        }
        return this;
    }

    public ConstructeurSituationFamilialeGE age(int age) {
        constructeurSituationFamiliale.age(age);
        return this;    }

    public ConstructeurSituationFamilialeGE age(int age, int... ages) {
        constructeurSituationFamiliale.age(age, ages);
        return this;    }



    public ConstructeurSituationFamilialeGE aFin(int annee) {
        constructeurSituationFamiliale.aFin(annee);
        return this;
    }

    public ConstructeurSituationFamilialeGE jeuneEnfantFin(int annee) {
        constructeurSituationFamiliale.enfant().age(11).aFin(annee);
        return this;
    }

    public ConstructeurSituationFamilialeGE anneeNaissance(int annee) {
        constructeurSituationFamiliale.anneeNaissance(annee);
        return this;
    }

    public ConstructeurSituationFamilialeGE gardeAlterneeStricte() {
        constructeurSituationFamiliale.gardeAlterneeStricte();
        return this;
    }

    public ConstructeurSituationFamilialeGE personneNecessiteuse() {
        constructeurSituationFamiliale.personneNecessiteuse();
        return this;
    }

    public ConstructeurSituationFamilialeGE demiPart(Souverainete souverainete) {
        constructeurSituationFamiliale.demiPart(souverainete);
        return this;
    }

    public ConstructeurSituationFamilialeGE domicilieGE() {
        coupleDomicilieGE = true;
        return this;
    }

    public SituationFamilialeGE fournir() {
        SituationFamilialeGEImpositionFoyer situation = new SituationFamilialeGEImpositionFoyer(constructeurSituationFamiliale.fournir(),contexteIFD);
        situation.conjointFonctionnaireInternational = conjointFonctionnaireInternational;
        situation.coupleDomicilieGE = coupleDomicilieGE;
        return situation;
    }


    private static class SituationFamilialeGEImpositionFoyer implements SituationFamilialeGE {

        private final SituationFamiliale situation;
        private final boolean ifd;
        public boolean coupleDomicilieGE;
        public boolean conjointFonctionnaireInternational;

        public SituationFamilialeGEImpositionFoyer(SituationFamiliale situation, boolean ifd) {
            this.situation = situation;
            this.ifd = ifd;
        }

        @Override
        public boolean isCouple() {
            return SituationFamilialeGE.super.isCouple()
                && (ifd || !conjointFonctionnaireInternational);
        }

        @Override
        public boolean isCoupleDomicilieGeneve() {
            return coupleDomicilieGE;
        }

        @Override
        public boolean isConjointFonctionnaireInternational() {
            return conjointFonctionnaireInternational;
        }

        @Override
        public Contribuable getContribuable() {
            return situation.getContribuable();
        }

        @Override
        public Optional<Contribuable> getConjoint() {
            return situation.getConjoint();
        }

        @Override
        public List<EnfantACharge> getEnfants() {
            return situation.getEnfants();
        }

        @Override
        public List<PersonneACharge> getPersonnesNecessiteuses() {
            return situation.getPersonnesNecessiteuses();
        }
    }

}
