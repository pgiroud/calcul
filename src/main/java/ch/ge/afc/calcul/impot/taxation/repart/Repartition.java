/**
 * This file is part of CalculImpotCH.
 *
 * CalculImpotCH is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * CalculImpotCH is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CalculImpotCH.  If not, see <http://www.gnu.org/licenses/>.
 */
package ch.ge.afc.calcul.impot.taxation.repart;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ch.ge.afc.calcul.impot.taxation.forimposition.ForImposition;
import ch.ge.afc.util.TypeArrondi;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class Repartition<T extends ForImposition> {
	
    private Map<T,Part> parts = new HashMap<T,Part>();

    private Repartition(Map<T,Part> inoParts) {
        for (T oFor : inoParts.keySet()) {
            parts.put(oFor,new Part(inoParts.get(oFor)));
        }
    }

    /**
     * Constructeur.
     */
    public Repartition() {
        super();
    }

    public boolean estVide() {
        return parts.isEmpty();
    }

    public boolean aPlusDunePart() {
        return !estVide() && parts.size() > 1;
    }

    public Set<T> getForImposition() {
        return new HashSet<T>(parts.keySet());
    }

    public BigDecimal getTotal() {
    	BigDecimal oTotal = BigDecimal.ZERO;
        for (Part oPart : parts.values()) {
            oTotal = oTotal.add(oPart.getMontant());
        }
        return oTotal;
    }

    public Part getPart(T inoFor) {
        for (T oFor : parts.keySet()) {
            if (oFor.equals(inoFor)) {
                return parts.get(oFor);
            }
        }
        return null;
    }

    private void recalculerTaux() {
        if (!parts.isEmpty()) {
            Part oPartPlusGrande = null;
            BigDecimal oTotalPart = getTotal();
            BigDecimal oTotalTaux = BigDecimal.ZERO;
            for (Part oPart : parts.values()) {
            	BigDecimal oTaux = oPart.getMontant().divide(oTotalPart,7, BigDecimal.ROUND_HALF_UP);
                oPart.setTaux(oTaux);
                oTotalTaux = oTotalTaux.add(oTaux);
                if (null == oPartPlusGrande || oPartPlusGrande.getMontant().abs().compareTo(oPart.getMontant().abs()) < 0) {
                    oPartPlusGrande = oPart;
                }
            }
            if (0 != BigDecimal.ONE.compareTo(oTotalTaux)) {
            	BigDecimal oDeltaTaux = BigDecimal.ONE.subtract(oTotalTaux);
                oPartPlusGrande.setTaux(oPartPlusGrande.getTaux().add(oDeltaTaux));
            }
        }
    }

    private void ajouterPartSansRecalcul(T inoFor, Part inoPart) {
        Part oPartExistante = null;
        for (T oFor : parts.keySet()) {
            if (oFor.equals(inoFor)) {
                oPartExistante = parts.get(oFor);
                oFor.merge(inoFor);
                break;
            }
        }
        if (null != oPartExistante) {
            oPartExistante.setMontant(oPartExistante.getMontant().add(inoPart.getMontant()));
        } else {
            parts.put(inoFor,new Part(inoPart));
        }
    }

    public void ajouterPart(T inoFor, Part inoPart) {
        // Souvent il n'y aura qu'une seule part, il est donc judicieux
        // de rendre le traitement plus performant
        if (0 == parts.size()) {
            parts.put(inoFor,inoPart);
        } else {
            ajouterPartSansRecalcul(inoFor,inoPart);
            recalculerTaux();
        }
    }

    public Repartition<T> ajouter(Repartition<T> inoReparts) {
        // Si une des 2 maps est vides, inutile de faire des calculs !!
        if (parts.isEmpty()) return new Repartition<T>(inoReparts.parts);
        if (inoReparts.parts.isEmpty()) return new Repartition<T>(parts);

        // Les 2 maps ne sont pas vides
        Repartition<T> oResultat = new Repartition<T>();
        for (T oForThis : parts.keySet()) {
            oResultat.ajouterPartSansRecalcul(oForThis,parts.get(oForThis));
        }
        for (T oForAutre : inoReparts.parts.keySet()) {
            oResultat.ajouterPartSansRecalcul(oForAutre,inoReparts.parts.get(oForAutre));
        }
        oResultat.recalculerTaux();
        return oResultat;
    }

    public Repartition<T> repartir(BigDecimal inoMontant) {
        return repartir(inoMontant, TypeArrondi.FRANC);
    }

    
    public Repartition<T> repartir(BigDecimal inoMontant, TypeArrondi inoTypeArrondi)
    {
        Repartition<T> oRepart = new Repartition<T>();
        if (this.estVide())
        {
            return oRepart;
        }
        else
        {
            if (1 == parts.size())
            {
                T oFor = parts.keySet().iterator().next();
                Part oPart = new Part(inoMontant);
                oRepart.ajouterPart(oFor,oPart);
                return oRepart;
            }
            else
            {
            	T oForPartPlusGrande = null;
                Part oPartPlusGrande = null;
                BigDecimal oTotalReparti = BigDecimal.ZERO;
                BigDecimal oTotalThis = this.getTotal();
                for (T oFor : parts.keySet())
                {
                    Part oPartThis = parts.get(oFor);
                    BigDecimal oMontantPart = inoMontant.multiply(oPartThis.getMontant()).divide(oTotalThis,10,BigDecimal.ROUND_HALF_UP);
                    oMontantPart = inoTypeArrondi.arrondirMontant(oMontantPart);
                    oTotalReparti = oTotalReparti.add(oMontantPart);
                    Part oPart = new Part(oMontantPart);
                    oRepart.ajouterPartSansRecalcul(oFor,oPart);
                    if (null == oPartPlusGrande || oPartPlusGrande.getMontant().compareTo(oMontantPart) < 0)
                    {
                        oPartPlusGrande = oPart;
                        oForPartPlusGrande = oFor;
                    }
                }
                BigDecimal oDelta = inoMontant.subtract(oTotalReparti);
                if (0 != oDelta.compareTo(BigDecimal.ZERO))
                {
                	oRepart.getPart(oForPartPlusGrande).setMontant(oPartPlusGrande.getMontant().add(oDelta));
                }
                oRepart.recalculerTaux();
                return oRepart;
            }
        }
    }
    
}
