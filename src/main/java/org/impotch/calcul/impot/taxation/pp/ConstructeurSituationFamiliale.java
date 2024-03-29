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
package org.impotch.calcul.impot.taxation.pp;

import org.impotch.calcul.impot.Souverainete;

import java.util.*;
import java.util.stream.IntStream;

public class ConstructeurSituationFamiliale {

    public static ConstructeurSituationFamiliale personneSeule() {
        return new ConstructeurSituationFamiliale();
    }

    public static ConstructeurSituationFamiliale couple() {
        return new ConstructeurSituationFamiliale().enCouple();
    }

    private final SituationFamilialeImpositionFoyer uneSituationEnConstruction = new SituationFamilialeImpositionFoyer();
    private EnfantAChargeRattacheAuFoyer enfantEnConstruction;

    private PersonneNecessiteuseRattacheeAuFoyer personneNecessiteuseEnConstruction;

    private int age = -1;

    public ConstructeurSituationFamiliale() {
    }


    private ConstructeurSituationFamiliale enCouple() {
        uneSituationEnConstruction.conjoint = new Contribuable() {};
        return this;
    }

    private void ajouterEnfant(EnfantACharge enfant) {
        uneSituationEnConstruction.ajouterEnfant(enfant);
        enfantEnConstruction = null;
    }

    private void ajouterChargeEnConstruction() {
        if (age >= 0) throw new IllegalStateException();
        if (null != personneNecessiteuseEnConstruction) ajouterPersonneNecessiteuse(personneNecessiteuseEnConstruction);
        if (null != enfantEnConstruction) ajouterEnfant(enfantEnConstruction);
    }

    public ConstructeurSituationFamiliale enfant() {
        age = -1;
        ajouterChargeEnConstruction();
        enfantEnConstruction = new EnfantAChargeRattacheAuFoyer();
        return this;
    }

    public ConstructeurSituationFamiliale age(int age) {
        this.age = age;
        return this;
    }

    public ConstructeurSituationFamiliale age(int agePremierEnfant, int... ageEnfantsSuivants) {
        age(agePremierEnfant);
        for (int age : ageEnfantsSuivants) {
            enfant().age(age);
        }
        return this;
    }

    public ConstructeurSituationFamiliale aFin(int annee) {
        if (age < 0) throw new IllegalStateException();
        int anneeNaissance = annee - age;
        age = -1;
        return anneeNaissance(anneeNaissance);
    }

    public ConstructeurSituationFamiliale anneeNaissance(int annee) {
        if (null == enfantEnConstruction) throw new IllegalStateException();
        enfantEnConstruction.anneeNaissance(annee - age);
        return this;
    }

    public ConstructeurSituationFamiliale gardeAlterneeStricte() {
        if (null == enfantEnConstruction) throw new IllegalStateException();
        enfantEnConstruction.gardeAlterneeStricte();
        return this;
    }

    private void ajouterPersonneNecessiteuse(PersonneACharge personne) {
        uneSituationEnConstruction.ajouterPersonneNecessiteuse(personne);
        personneNecessiteuseEnConstruction = null;
    }

    public ConstructeurSituationFamiliale personneNecessiteuse() {
        ajouterChargeEnConstruction();
        personneNecessiteuseEnConstruction = new PersonneNecessiteuseRattacheeAuFoyer();
        return this;
    }



    public ConstructeurSituationFamiliale demiPart(Souverainete souverainete) {
        if (null != enfantEnConstruction) enfantEnConstruction.demiPart(souverainete);
        else if (null != personneNecessiteuseEnConstruction) personneNecessiteuseEnConstruction.demiPart(souverainete);
        else throw new IllegalStateException();
        return this;
    }

    public SituationFamiliale fournir() {
        age = -1;
        ajouterChargeEnConstruction();
        return uneSituationEnConstruction;
    }


    private static class SituationFamilialeImpositionFoyer implements SituationFamiliale {
        public Contribuable conjoint;
        private List<EnfantACharge> enfants = new ArrayList<>();
        private List<PersonneACharge> personnesNecessiteuses = new ArrayList<>();

        @Override
        public Contribuable getContribuable() {
            return new Contribuable(){};
        }


        @Override
        public Optional<Contribuable> getConjoint() {
            return Optional.ofNullable(conjoint);
        }

        public void ajouterEnfant(EnfantACharge enfant) {
            enfants.add(enfant);
        }

        @Override
        public List<EnfantACharge> getEnfants() {
            return enfants;
        }

        public void ajouterPersonneNecessiteuse(PersonneACharge personne) {
            this.personnesNecessiteuses.add(personne);
        }

        @Override
        public List<PersonneACharge> getPersonnesNecessiteuses() {
            return personnesNecessiteuses;
        }
    }

    private static class EnfantAChargeRattacheAuFoyer extends PersonneNecessiteuseRattacheeAuFoyer implements EnfantACharge {

        private int anneeNaissance;
        private boolean gardeAlterneeStricte;

        public void anneeNaissance(int annee) {
            anneeNaissance = annee;
        }

        public void gardeAlterneeStricte() {
            gardeAlterneeStricte = true;
        }

        @Override
        public int getAge(int anneeFiscale) {
            return anneeFiscale - anneeNaissance;
        }

        @Override
        public boolean estEnGardeAlterneeStricte() {
            return gardeAlterneeStricte;
        }

    }


    private static class PersonneNecessiteuseRattacheeAuFoyer implements PersonneACharge {

        private Set<Souverainete> demiParts = new HashSet<>();

        public void demiPart(Souverainete souverainete) {
            demiParts.add(souverainete);
        }

        @Override
        public boolean isDemiPart(Souverainete souverainete) {
            return demiParts.contains(souverainete);
        }
    }
}
