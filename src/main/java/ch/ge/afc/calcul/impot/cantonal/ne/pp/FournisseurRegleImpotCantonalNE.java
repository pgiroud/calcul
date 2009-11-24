package ch.ge.afc.calcul.impot.cantonal.ne.pp;

import ch.ge.afc.calcul.bareme.Bareme;

public interface FournisseurRegleImpotCantonalNE {
	Bareme getBaremeRevenu(int annee);
	Bareme getBaremeFortune(int annee);
}
