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
package ch.ge.afc.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Énumération des types d'arrondi.
 * Un type d'arrondi sert à arrondir un décimal.
 */
public enum TypeArrondi {
	
	
    /**************************************************/
    /***********Instances de l'énumération ************/
    /**************************************************/

    /**
     * Arrondi au centime inférieur. Par exemple, 2.288 sera
     * arrondi à 2.28
     */
    CT_INF (BigDecimalUtil.UN_CENTIME,RoundingMode.DOWN),

    /**
     * Arrondi au centime le plus proche.
     */
    CT(BigDecimalUtil.UN_CENTIME,RoundingMode.HALF_UP),
    
    /**
     * Arrondi au centime supérieur.
     */
    CT_SUP (BigDecimalUtil.UN_CENTIME,RoundingMode.UP),
    
    /**
     * Arrondi aux cinq centimes inférieur. Par exemple, 2.28 sera
     * arrondi à 2.25
     */
    CINQ_CTS_INF (BigDecimalUtil.CINQ_CTS,RoundingMode.DOWN),


    /**
     * Arrondi aux cinq centimes supérieurs. Par exemple, 2.28 sera arrondi
     * à 2.30
     */
    CINQ_CTS_SUP (BigDecimalUtil.CINQ_CTS,RoundingMode.UP),


    /**
     * Arrondi aux cinq centimes les plus proches. Par exemple
     * <ul>
     *  <li>2.28 sera arrondi à 2.30</li>
     *  <li>2.27 sera arrondi à 2.25</li>
     *  <li>2.275 sera arrondi à 2.30</li>
     * </ul>
     */
    CINQ_CTS (BigDecimalUtil.CINQ_CTS,RoundingMode.HALF_UP),


    /**
     * Arrondi aux dix centimes inférieur
     */
    DIX_CTS_INF (BigDecimalUtil.DIX_CTS,RoundingMode.DOWN),


    /**
     * Arrondi aux dix centimes supérieurs
     */
    DIX_CTS_SUP (BigDecimalUtil.DIX_CTS,RoundingMode.UP),


    /**
     * Arrondi aux dix centimes les plus proches.
     */
    DIX_CTS (BigDecimalUtil.DIX_CTS,RoundingMode.HALF_UP),


    /**
     * Arrondi au franc inférieur.
     */
    FRANC_INF (BigDecimalUtil.UN,RoundingMode.DOWN),


    /**
     * Arrondi au franc supérieur.
     */
    FRANC_SUP (BigDecimalUtil.UN,RoundingMode.UP),


    /**
     * Arrondi au franc le plus proche
     */
    FRANC (BigDecimalUtil.UN,RoundingMode.HALF_UP),


    /**
     * Arrondi aux dix francs inférieurs
     */
    DIX_FRANC_INF (BigDecimalUtil.DIX,RoundingMode.DOWN),


    /**
     * Arrondi au dix francs supérieurs
     */
    DIX_FRANC_SUP (BigDecimalUtil.DIX,RoundingMode.UP),


    /**
     * Arrondi aux dix francs les plus proches.
     */
    DIX_FRANC (BigDecimalUtil.DIX,RoundingMode.HALF_UP),


    /**
     * Arrondi aux cents francs inférieurs
     */
    CENT_FRANC_INF (BigDecimalUtil.CENT,RoundingMode.DOWN),


    /**
     * Arrondi aux cents francs supérieurs
     */
    CENT_FRANC_SUP (BigDecimalUtil.CENT,RoundingMode.UP),


    /**
     * Arrondi aux cents francs les plus proches.
     */
    CENT_FRANC (BigDecimalUtil.CENT,RoundingMode.HALF_UP),

    /**
     * Arrondi aux milles francs inférieurs
     */
    MILLE_FRANC_INF (BigDecimalUtil.MILLE,RoundingMode.DOWN),


    /**
     * Arrondi aux milles francs supérieurs
     */
    MILLE_FRANC_SUP (BigDecimalUtil.MILLE,RoundingMode.UP),


    /**
     * Arrondi aux milles francs les plus proches.
     */
    MILLE_FRANC (BigDecimalUtil.MILLE,RoundingMode.HALF_UP);

    /**************************************************/
    /******************* Attributs ********************/
    /**************************************************/

    private final BigDecimal moPrecision;
    private final RoundingMode moMode;

    /**************************************************/
    /**************** Constructeurs *******************/
    /**************************************************/

    /**
     * Construction d'un type d'arrondi en fournissant une précision, par exemple 0.05 pour un arrondi
     * aux cinq centimes, et un mode d'arrondi (arrondi au plus près, à la valeur supérieure ou inférieure)
     * @param inoPrecision la précision : 0.05 pour un arrondi aux cinq centimes par exemple
     * @param inoMode le mode d'arrondi i.e. au plus près, à la valeur supérieure ou inférieure
     */
    TypeArrondi(BigDecimal inoPrecision, RoundingMode inoMode) {
        moPrecision = inoPrecision;
        moMode = inoMode;
    }

    /**************************************************/
    /******************* Méthodes *********************/
    /**************************************************/

    /**
     * Arrondi le montant fourni en paramètre. Attention, le type CsMontant étant immuable,
     * une nouvelle instance de CsMontant est retournée.
     * Si le montant à arrondir est négatif, on retourne la valeur absolue arrondie multipliée par -1.
     * La valeur retournée contient 2 chiffres après la virgule (qui peuvent être 00).
     * @param inoMontantAArrondir le montant à arrondir
     * @return Une nouvelle instance de CsMontant : le montant arrondi.
     */
    public BigDecimal arrondirMontant(BigDecimal inoMontantAArrondir) {
      if (null == inoMontantAArrondir) return null;  
      BigDecimal normalise = inoMontantAArrondir.divide(moPrecision, 10, moMode);
      return normalise.setScale(0, moMode).multiply(moPrecision).setScale(2);
    }

}
