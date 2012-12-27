package org.impotch.calcul.impot.cantonal.ne.pp;

import org.impotch.bareme.Bareme;

public interface FournisseurRegleImpotCantonalNE {
	Bareme getBaremeRevenu(int annee);
	Bareme getBaremeFortune(int annee);
}
