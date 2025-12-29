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
package org.impotch.calcul.assurancessociales;

import java.math.BigDecimal;

import org.impotch.calcul.ReglePeriodique;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class CalculExtremaRentesAVS extends ReglePeriodique {
	
	private static final BigDecimal RATIO_ENFANT = new BigDecimal("0.4");
	private static final BigDecimal RATIO_COUPLE = new BigDecimal("1.5");
	
	private BigDecimal renteSimpleMensuelleMinimum;
	
	/**
	 * @param annee
	 */
	public CalculExtremaRentesAVS(int annee) {
		super(annee);
	}

	public void setRenteSimpleMensuelleMinimum(BigDecimal renteMensuelleMinimum) {
		this.renteSimpleMensuelleMinimum = renteMensuelleMinimum;
	}

	private BigDecimal getMontantEnfantOuOrphelin() {
		return renteSimpleMensuelleMinimum.multiply(RATIO_ENFANT);
	}
	
	public ExtremaRenteAVS getRenteSimple() {
		return new ExtremaRenteAVS(renteSimpleMensuelleMinimum);
	}
	
	public ExtremaRenteAVS getRenteCouple(int nbreEnfant) {
		BigDecimal montantEnfant = new BigDecimal(nbreEnfant).multiply(getMontantEnfantOuOrphelin());
		BigDecimal montant = RATIO_COUPLE.multiply(renteSimpleMensuelleMinimum).add(montantEnfant);
		return new ExtremaRenteAVS(montant);
	}
	
	public ExtremaRenteAVS getRente(SituationAVS situation) {
    	BigDecimal coeff = BigDecimal.ZERO;
    	if (StatutAVS.SIMPLE.equals(situation.getStatutAVS())) {
    		coeff = BigDecimal.ONE;
    	} else if (StatutAVS.COUPLE.equals(situation.getStatutAVS())) {
    		coeff = new BigDecimal("1.5");
    	} else if (StatutAVS.VEUF.equals(situation.getStatutAVS())) {
    		coeff = new BigDecimal("0.8");
    	} else if (StatutAVS.ORPHELIN_SIMPLE.equals(situation.getStatutAVS())) {
    		coeff = new BigDecimal("0.4");
    	} else if (StatutAVS.ORPHELIN_DOUBLE.equals(situation.getStatutAVS())) {
    		coeff = new BigDecimal("0.6");
    	} 
    	if (situation.isComplementaireConjoint()) {
    		coeff = coeff.add(new BigDecimal("0.3"));
    	}
    	coeff = coeff.add(new BigDecimal(situation.getNombreOrphelin()).multiply(RATIO_ENFANT));
		return new ExtremaRenteAVS(coeff.multiply(renteSimpleMensuelleMinimum));
	}
	
}
