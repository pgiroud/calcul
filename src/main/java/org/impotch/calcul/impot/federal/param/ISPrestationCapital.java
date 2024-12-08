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
package org.impotch.calcul.impot.federal.param;

import org.impotch.bareme.Bareme;

import org.impotch.util.TypeArrondi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

import static org.impotch.bareme.ConstructeurBareme.unBaremeATauxMarginal;

class ISPrestationCapital {

    final Logger logger = LoggerFactory.getLogger(ISPrestationCapital.class);


    public ISPrestationCapital() {

    }




    /**
     * Source : Administration fédérale des contributions AFC
     * Chapitre 3 lettre a de l'annexe de l’<a href="https://www.fedlex.admin.ch/eli/cc/2018/274/fr">Ordonnance sur l’imposition à la source, OIS</a> du 11 avril 2018 État au 1er janvier 2025
     * Lettre-circulaire nº 211 : Barèmes 2025 de l’impôt à la source, annexes 5 et 6
     * <a href="https://www.estv.admin.ch/dam/estv/fr/dokumente/dbst/rundschreiben/dbst-rs-2-211-d-2024-fr.pdf.download.pdf/dbst-rs-2-211-d-2024-fr.pdf">Lien</a>
     * @return un barème permettant de calculer l’impôt sur une prestation en capital pour la période fiscale 2025
     */
    private Bareme personneSeule2025() {
        return unBaremeATauxMarginal()
                .jusqua(     25_000).taux("0 %")
                .puisJusqua( 50_000).taux("0.35 %")
                .puisJusqua( 75_000).taux("0.55 %")
                .puisJusqua(100_000).taux("1.25 %")
                .puisJusqua(125_000).taux("1.60 %")
                .puisJusqua(150_000).taux("1.95 %")
                .puisJusqua(750_000).taux("2.60 %")
                .puis()                   .taux("2.30 %")
                .construire();
    }

    private Bareme couple2025() {
        return unBaremeATauxMarginal()
                .jusqua(     25_000).taux("0 %")
                .puisJusqua( 50_000).taux("0.15 %")
                .puisJusqua( 75_000).taux("0.50 %")
                .puisJusqua(100_000).taux("0.80 %")
                .puisJusqua(125_000).taux("1.15 %")
                .puisJusqua(150_000).taux("1.75 %")
                .puisJusqua(900_000).taux("2.60 %")
                .puis()                   .taux("2.30 %")
                .construire();
    }

    // Source : https://www.fedlex.admin.ch/eli/cc/2018/274/fr
    private Bareme personneSeule2024() {
        return unBaremeATauxMarginal()
                .jusqua(     25_000).taux("0 %")
                .puisJusqua( 50_000).taux("0.35 %")
                .puisJusqua( 75_000).taux("0.55 %")
                .puisJusqua(100_000).taux("1.30 %")
                .puisJusqua(125_000).taux("1.60 %")
                .puisJusqua(150_000).taux("1.95 %")
                .puisJusqua(750_000).taux("2.60 %")
                .puis()                   .taux("2.30 %")
                .construire();
    }

    private Bareme couple2024() {
        return unBaremeATauxMarginal()
                .jusqua(     25_000).taux("0 %")
                .puisJusqua( 50_000).taux("0.15 %")
                .puisJusqua( 75_000).taux("0.50 %")
                .puisJusqua(100_000).taux("0.85 %")
                .puisJusqua(125_000).taux("1.20 %")
                .puisJusqua(150_000).taux("1.75 %")
                .puisJusqua(900_000).taux("2.60 %")
                .puis()                   .taux("2.30 %")
                .construire();
    }

    /**
     *
     * @return
     */
    private Bareme personneSeule2023() {
        return unBaremeATauxMarginal()
                .jusqua(     25_000).taux("0 %")
                .puisJusqua( 50_000).taux("0.35 %")
                .puisJusqua( 75_000).taux("0.60 %")
                .puisJusqua(100_000).taux("1.30 %")
                .puisJusqua(125_000).taux("1.70 %")
                .puisJusqua(150_000).taux("2.00 %")
                .puisJusqua(750_000).taux("2.60 %")
                .puis()                   .taux("2.30 %")
                .construire();
    }

    private Bareme couple2023() {
        return unBaremeATauxMarginal()
                .jusqua(     25_000).taux("0 %")
                .puisJusqua( 50_000).taux("0.20 %")
                .puisJusqua( 75_000).taux("0.50 %")
                .puisJusqua(100_000).taux("0.85 %")
                .puisJusqua(125_000).taux("1.20 %")
                .puisJusqua(150_000).taux("1.90 %")
                .puisJusqua(900_000).taux("2.60 %")
                .puis()                   .taux("2.30 %")
                .construire();
    }

    private Bareme personneSeule2021() {
        return unBaremeATauxMarginal()
                .jusqua(     25_000).taux("0 %")
                .puisJusqua( 50_000).taux("0.35 %")
                .puisJusqua( 75_000).taux("0.65 %")
                .puisJusqua(100_000).taux("1.30 %")
                .puisJusqua(125_000).taux("1.70 %")
                .puisJusqua(150_000).taux("2.00 %")
                .puisJusqua(750_000).taux("2.60 %")
                .puis().taux("2.30 %").construire();
    }

    private Bareme couple2021() {
        return unBaremeATauxMarginal()
                .jusqua(     25_000).taux("0 %")
                .puisJusqua( 50_000).taux("0.20 %")
                .puisJusqua( 75_000).taux("0.50 %")
                .puisJusqua(100_000).taux("0.90 %")
                .puisJusqua(125_000).taux("1.25 %")
                .puisJusqua(150_000).taux("2.00 %")
                .puisJusqua(900_000).taux("2.60 %")
                .puis()                   .taux("2.30 %")
                .construire();
    }

    // Source : https://www.fedlex.admin.ch/eli/cc/1993/3324_3324_3324/fr
    private Bareme personneSeule2012() {
        return unBaremeATauxMarginal().typeArrondiSurEntrant(TypeArrondi.MILLE_INF)
                .jusqua(     25_000).taux("0 %")
                .puisJusqua( 50_000).taux("0.20 %")
                .puisJusqua( 75_000).taux("0.55 %")
                .puisJusqua(100_000).taux("0.90 %")
                .puisJusqua(125_000).taux("1.25 %")
                .puisJusqua(150_000).taux("2.00 %")
                .puisJusqua(900_000).taux("2.60 %")
                .puis()                   .taux("2.30 %")
                .construire();
    }

    private Bareme personneSeule2011() {
        return unBaremeATauxMarginal()
                .jusqua(     25_000).taux("0 %")
                .puisJusqua( 50_000).taux("0.20 %")
                .puisJusqua( 75_000).taux("0.55 %")
                .puisJusqua(100_000).taux("0.90 %")
                .puisJusqua(125_000).taux("1.30 %")
                .puisJusqua(150_000).taux("2.05 %")
                .puisJusqua(890_000).taux("2.60 %")
                .puis()                   .taux("2.30 %")
                .construire();
    }

    private Bareme personneSeule2007() {
        return unBaremeATauxMarginal()
                .de(0).a(25_000).taux("0 %")
                .de(25_000).a(50_000).taux("0.25 %")
                .de(50_000).a(75_000).taux("0.65 %")
                .de(75_000).a(100_000).taux("1.10 %")
                .de(100_000).a(125_000).taux("1.70 %")
                .de(125_000).a(775_000).taux("2.60 %")
                .plusDe(775_000).taux("2.30 %").construire();
    }

    private Bareme personneSeule2002() {
        return unBaremeATauxMarginal()
                .de(0).a(25_000).taux("0 %")
                .de(25_000).a(50_000).taux("0.25 %")
                .de(50_000).a(75_000).taux("0.75 %")
                .de(75_000).a(100_000).taux("1.20 %")
                .de(100_000).a(125_000).taux("2.15 %")
                .de(125_000).a(775_000).taux("2.60 %")
                .plusDe(775_000).taux("2.30 %").construire();
    }








    public Optional<Bareme> personneSeule(int annee) {
        if (annee > 2025) {
            logger.error("Il s'agit certainement d’un manque dans le paramétrage pour l’année " + annee);
            return Optional.empty();
        }
        if (annee < 2002) {
            String message = "Le paramétrage en mémoire pour l’impôt fédéral perçu à la source sur les " +
                    "prestations en capital n’existe pas pour l’année " + annee + "!";
            logger.error(message);
            throw new IllegalArgumentException(message);
        }
        String methodeName = "personneSeule" + annee;
        try {
            Method methodeAnnuelle = ISPrestationCapital.class.getDeclaredMethod(methodeName);
            return Optional.of((Bareme) methodeAnnuelle.invoke(this));
        } catch (NoSuchMethodException nsme) {
            return personneSeule(annee - 1);
        } catch (IllegalAccessException | InvocationTargetException nsme) {
            logger.error("Impossible d’inoquer la méthode " + methodeName + " de la classe " + this.getClass().getCanonicalName());
            return Optional.empty();
        }
    }

    public Optional<Bareme> couple(int annee) {
        if (annee > 2025) {
            logger.error("Il s'agit certainement d’un manque dans le paramétrage pour l’année " + annee);
            return Optional.empty();
        }
        if (annee < 2002) {
            String message = "Le paramétrage en mémoire pour l’impôt fédéral perçu à la source sur les " +
                    "prestations en capital n’existe pas pour l’année " + annee + "!";
            logger.error(message);
            throw new IllegalArgumentException(message);
        }
        if (annee < 2021) return personneSeule(annee);
        String methodeName = "couple" + annee;
        try {
            Method methodeAnnuelle = ISPrestationCapital.class.getDeclaredMethod(methodeName);
            return Optional.of((Bareme) methodeAnnuelle.invoke(this));
        } catch (NoSuchMethodException nsme) {
            return couple(annee - 1);
        } catch (IllegalAccessException | InvocationTargetException nsme) {
            logger.error("Impossible d’inoquer la méthode " + methodeName + " de la classe " + this.getClass().getCanonicalName());
            return Optional.empty();
        }
    }

}