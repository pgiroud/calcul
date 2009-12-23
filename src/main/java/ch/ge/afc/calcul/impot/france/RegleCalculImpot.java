package ch.ge.afc.calcul.impot.france;

import java.math.BigDecimal;

import ch.ge.afc.bareme.Bareme;
import ch.ge.afc.calcul.ReglePeriodique;
import ch.ge.afc.util.BigDecimalUtil;
import ch.ge.afc.util.TypeArrondi;

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
