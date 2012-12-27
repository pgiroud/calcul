package org.impotch.calcul.impot.france;

import java.math.BigDecimal;

import org.impotch.bareme.Bareme;
import org.impotch.calcul.ReglePeriodique;
import org.impotch.util.BigDecimalUtil;
import org.impotch.util.TypeArrondi;

public class RegleCalculImpot extends ReglePeriodique {

	private Bareme bareme;
	
	private BigDecimal plafondParDemiPart;
	
	private BigDecimal montantDecote;
	
	public RegleCalculImpot(int annee) {
		super(annee);
	}

	public void setMontantDecote(BigDecimal montantDecote) {
		this.montantDecote = montantDecote;
	}
	
	public void setBareme(Bareme bareme) {
		this.bareme = bareme;
	}
	
	protected BigDecimal decote(BigDecimal montantImpot) {
		if (0 >= montantDecote.compareTo(montantImpot)) return montantImpot;
		BigDecimal deduction = montantDecote.subtract(montantImpot).divide(BigDecimalUtil.DEUX,2,BigDecimal.ROUND_HALF_UP);
		deduction = TypeArrondi.FRANC_INF.arrondirMontant(deduction);
		return montantImpot.subtract(deduction);
	}
	
	public BigDecimal produireImpot(BigDecimal nbrePart, BigDecimal montantImposable) {
		return montantImposable;
	}
}
