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

import java.math.BigDecimal;
import java.util.*;

import org.impotch.calcul.impot.taxation.forimposition.ForImposition;
import org.impotch.util.BigDecimalUtil;
import org.impotch.util.TypeArrondi;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class Repartition<T extends ForImposition> {
	
    private SortedSet<PartAvecFor<T>> liste = new TreeSet<PartAvecFor<T>>();

    private Repartition(Repartition<T> repart) {
        for (PartAvecFor<T> partAvecFor : repart.liste) {
            // On recrée la part pour ne pas modifier la liste initiale
            Part part = new Part(partAvecFor.getPart());
            liste.add(new PartAvecFor<T>(partAvecFor.getForImposition(),part));
        }
    }

    /**
     * Constructeur.
     */
    public Repartition() {
        super();
    }



    public boolean estVide() {
        return liste.isEmpty();
    }

    public boolean aPlusDunePart() {
        return !estVide() && liste.size() > 1;
    }

    public Set<T> getForImposition() {
        Set<T> fors = new HashSet<T>();
        for (PartAvecFor<T> partAvecFor : liste) {
            fors.add(partAvecFor.forImposition);
        }
        return fors;
    }

    public BigDecimal getTotal() {
    	BigDecimal total = BigDecimal.ZERO;
        for (PartAvecFor<T> partAvecFor : liste) {
            Part part = partAvecFor.getPart();
            total = total.add(part.getMontant());
        }
        return total;
    }

    public Part getPart(T inoFor) {
        for (PartAvecFor<T> partAvecFor : liste) {
            ForImposition unFor = partAvecFor.getForImposition();
            if (unFor.equals(inoFor)) {
                return partAvecFor.getPart();
            }
        }
        return null;
    }

    private void recalculerTaux() {
        if (!estVide()) {
            Part partPlusGrande = liste.last().getPart();
            BigDecimal totalPart = getTotal();
            if (BigDecimalUtil.isStrictementPositif(totalPart)) {
                BigDecimal totalTaux = BigDecimal.ZERO;
                for (PartAvecFor<T> partAvecFor : liste) {
                    Part part = partAvecFor.getPart();
                    BigDecimal taux = part.getMontant().divide(totalPart,7, BigDecimal.ROUND_HALF_UP);
                    part.setTaux(taux);
                    totalTaux = totalTaux.add(taux);
                }
                if (0 != BigDecimal.ONE.compareTo(totalTaux)) {
                    BigDecimal oDeltaTaux = BigDecimal.ONE.subtract(totalTaux);
                    partPlusGrande.setTaux(partPlusGrande.getTaux().add(oDeltaTaux));
                }
            }
        }
    }

    private void ajouterPartSansRecalcul(T inoFor, Part inoPart) {
        Part oPartExistante = null;
        for (PartAvecFor<T> partAvecFor : liste) {
            T unFor = partAvecFor.getForImposition();
            if (unFor.equals(inoFor)) {
                oPartExistante = partAvecFor.getPart();
                unFor.merge(inoFor);
                break;
            }
        }
        if (null != oPartExistante) {
            oPartExistante.setMontant(oPartExistante.getMontant().add(inoPart.getMontant()));
        } else {
            // Attention, on recopie bien la part pour ne pas modifier la part d'origine !
            Part part = new Part(inoPart);
            liste.add(new PartAvecFor<T>(inoFor, part));
        }
    }

    public void ajouterPart(T inoFor, Part inoPart) {
        // Souvent il n'y aura qu'une seule part, il est donc judicieux
        // de rendre le traitement plus performant
        if (estVide()) {
            liste.add(new PartAvecFor<T>(inoFor,inoPart));
        } else {
            ajouterPartSansRecalcul(inoFor,inoPart);
            recalculerTaux();
        }
    }


    public Repartition<T> ajouter(Repartition<T> inoReparts) {
        // Si une des listes est vides, inutile de faire des calculs !!
        if (estVide()) return new Repartition<T>(inoReparts);
        if (inoReparts.estVide()) return new Repartition<T>(this);

        // Les 2 maps ne sont pas vides
        Repartition<T> oResultat = new Repartition<T>();

        for (PartAvecFor<T> partAvecFor : liste) {
            oResultat.ajouterPartSansRecalcul(partAvecFor.getForImposition(),partAvecFor.getPart());
        }
        for (PartAvecFor<T> partAvecFor : inoReparts.liste) {
            oResultat.ajouterPartSansRecalcul(partAvecFor.getForImposition(),partAvecFor.getPart());
        }
        oResultat.recalculerTaux();
        return oResultat;
    }

    public Repartition<T> repartir(BigDecimal inoMontant) {
        return repartir(inoMontant, TypeArrondi.FRANC);
    }

    private Repartition<T> repartir(TypeCalculRepartition type, BigDecimal montantARepartir, TypeArrondi typeArrondi) {
        Repartition<T> oRepart = new Repartition<T>();
        if (this.aPlusDunePart()) {
            BigDecimal oTotalReparti = BigDecimal.ZERO;
            BigDecimal oTotalThis = this.getTotal();
            for (PartAvecFor<T> partAvecFor : liste) {
                Part oPartThis = partAvecFor.getPart();
                BigDecimal oMontantPart = montantARepartir.multiply(oPartThis.getMontant()).divide(oTotalThis,10,BigDecimal.ROUND_HALF_UP);
                oMontantPart = typeArrondi.arrondirMontant(oMontantPart);
                if (TypeCalculRepartition.SEULEMENT_SUR_RESTE_A_REPARTIR.equals(type)
                    && 0 == BigDecimal.ZERO.compareTo(oMontantPart)) {
                    oTotalThis = oTotalThis.subtract(oPartThis.getMontant());
                }
                oTotalReparti = oTotalReparti.add(oMontantPart);
                Part oPart = new Part(oMontantPart);
                oRepart.ajouterPartSansRecalcul(partAvecFor.getForImposition(),oPart);
            }
            BigDecimal oDelta = montantARepartir.subtract(oTotalReparti);
            if (0 != oDelta.compareTo(BigDecimal.ZERO)) {
                PartAvecFor<T> partAvecFor = oRepart.liste.last();
                Part oPartPlusGrande = partAvecFor.getPart();
                oPartPlusGrande.setMontant(oPartPlusGrande.getMontant().add(oDelta));
            }
            oRepart.recalculerTaux();
            return oRepart;
        } else if (estVide()) {
            return oRepart;
        } else {
            // cas avec 1 seule part
            T oFor = liste.iterator().next().getForImposition();
            Part oPart = new Part(montantARepartir);
            oRepart.ajouterPart(oFor,oPart);
            return oRepart;
        }
    }

    public Repartition<T> repartir(BigDecimal montantARepartir, TypeArrondi typeArrondi) {
        return repartir(TypeCalculRepartition.CLASSIQUE,montantARepartir,typeArrondi);
    }

    public Repartition<T> repartirSurResteARepartir(BigDecimal montantARepartir) {
        return repartirSurResteARepartir(montantARepartir,TypeArrondi.FRANC);
    }

    public Repartition<T> repartirSurResteARepartir(BigDecimal montantARepartir, TypeArrondi typeArrondi) {
        return repartir(TypeCalculRepartition.SEULEMENT_SUR_RESTE_A_REPARTIR,montantARepartir,typeArrondi);
    }

    private static class PartAvecFor<T extends ForImposition> implements Comparable<PartAvecFor<T>> {
        private final T forImposition;
        private final Part part;

        private PartAvecFor(T forImposition, Part part) {
            this.forImposition = forImposition;
            this.part = part;
        }

        public T getForImposition() {
            return forImposition;
        }

        public Part getPart() {
            return part;
        }

        @Override
        public int compareTo(PartAvecFor<T> o) {
            int comparaisonSurMontant = this.getPart().getMontant().compareTo(o.getPart().getMontant());
            int comparaisonSurLieu = this.forImposition.getLieu().getNumeroOFS() - o.forImposition.getLieu().getNumeroOFS();
            return (0 != comparaisonSurMontant) ? comparaisonSurMontant : comparaisonSurLieu;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            PartAvecFor that = (PartAvecFor) o;

            if (forImposition != null ? !forImposition.equals(that.forImposition) : that.forImposition != null)
                return false;
            if (part != null ? !part.equals(that.part) : that.part != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = forImposition != null ? forImposition.hashCode() : 0;
            result = 31 * result + (part != null ? part.hashCode() : 0);
            return result;
        }
    }
}
