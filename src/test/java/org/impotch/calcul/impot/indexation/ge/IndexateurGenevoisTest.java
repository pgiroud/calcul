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
package org.impotch.calcul.impot.indexation.ge;

import java.math.BigDecimal;


import org.impotch.util.TypeArrondi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class IndexateurGenevoisTest {

	// TODO PGI : Supprimer cette classe

	private final static int ANNEE_INDEXATION_LIPP_5_D_3_16 = 2001;
	private final static int ANNEE_INDEXATION_LIPP_D_3_08 = 2009;

	private IndexateurGenevois indexateur;


	@BeforeEach
	void initIndexateur() {
		indexateur = new IndexateurGenevois(new FournisseurIndexGenevoisEnMemoire());
	}

	@Test
	@DisplayName("Base montant époux")
	public void indexationBaseEpoux() {
		assertThat(indexateur.indexer(ANNEE_INDEXATION_LIPP_5_D_3_16, BigDecimal.valueOf(13750), 2001)).isEqualTo(BigDecimal.valueOf(13750));
	}

	@Test
	@DisplayName("2005 montant époux")
    public void indexation2005MontantEpoux() {
		assertThat(indexateur.indexer(ANNEE_INDEXATION_LIPP_5_D_3_16, BigDecimal.valueOf(13750), 2005)).isEqualTo(BigDecimal.valueOf(14288));
    }

	@Test
	@DisplayName("2009 montant époux")
    public void indexation2009MontantEpoux() {
		assertThat(indexateur.indexer(ANNEE_INDEXATION_LIPP_5_D_3_16,BigDecimal.valueOf(13750), 2009)).isEqualTo(BigDecimal.valueOf(15057));
    }

	@Test
	@DisplayName("Base déduc. double activité")
	public void indexationBaseDeducDoubleActivite() {
		assertThat(indexateur.indexer(ANNEE_INDEXATION_LIPP_5_D_3_16,BigDecimal.valueOf(3500), 2002)).isEqualTo(BigDecimal.valueOf(3500));
	}

	@Test
	@DisplayName("2006 déduc. double activité")
    public void indexation2006DeducDoubleActivite() {
		assertThat(indexateur.indexer(ANNEE_INDEXATION_LIPP_5_D_3_16,BigDecimal.valueOf(3500), 2006, TypeArrondi.DIZAINE_LA_PLUS_PROCHE)).isEqualTo(BigDecimal.valueOf(3640));
    }


	@Test
	@DisplayName("Base plafond faible revenu")
	public void indexationBasePlafondFaibleRevenu() {
		assertThat(indexateur.indexer(ANNEE_INDEXATION_LIPP_5_D_3_16,BigDecimal.valueOf(50000), 2003)).isEqualTo(BigDecimal.valueOf(50000));
	}

	@Test
	@DisplayName("2007 plafond faible revenu")
    public void indexation2007PlafondFaibleRevenu() {
		assertThat(indexateur.indexer(ANNEE_INDEXATION_LIPP_5_D_3_16,BigDecimal.valueOf(50000), 2007, TypeArrondi.MILLE_SUP)).isEqualTo(BigDecimal.valueOf(52000));
    }


	@Test
	@DisplayName("Base déduc. double activité pour faible revenu")
	public void indexationBaseDeducDoubleActivitePourFaibleRevenu() {
		assertThat(indexateur.indexer(ANNEE_INDEXATION_LIPP_5_D_3_16,BigDecimal.valueOf(5000), 2004)).isEqualTo(BigDecimal.valueOf(5000));
	}

	@Test
	@DisplayName("2008 déduc. double activité pour faible revenu")
    public void indexation2008DeducDoubleActivitePourFaibleRevenu() {
		assertThat(indexateur.indexer(ANNEE_INDEXATION_LIPP_5_D_3_16,BigDecimal.valueOf(5000), 2008, TypeArrondi.CENTAINE_SUP)).isEqualTo(BigDecimal.valueOf(5200));
    }

	@Test
	@DisplayName("2009 déduc. double activité pour faible revenu")
    public void indexation2009DeducDoubleActivitePourFaibleRevenu() {
		assertThat(indexateur.indexer(ANNEE_INDEXATION_LIPP_5_D_3_16,BigDecimal.valueOf(5000), 2009, TypeArrondi.CENTAINE_SUP)).isEqualTo(BigDecimal.valueOf(5500));
    }


	@Test
	@DisplayName("Base montant demi charge")
	public void indexationBaseMontantDemiCharge() {
		assertThat(indexateur.indexer(ANNEE_INDEXATION_LIPP_5_D_3_16,BigDecimal.valueOf(3250), 2001)).isEqualTo(BigDecimal.valueOf(3250));
	}

	@Test
	@DisplayName("2005 montant demi charge")
    public void indexation2005MontantDemiCharge() {
		assertThat(indexateur.indexer(ANNEE_INDEXATION_LIPP_5_D_3_16,BigDecimal.valueOf(3250), 2005)).isEqualTo(BigDecimal.valueOf(3377));
    }

	@Test
	@DisplayName("2009 montant demi charge")
    public void indexation2009MontantDemiCharge() {
		assertThat(indexateur.indexer(ANNEE_INDEXATION_LIPP_5_D_3_16, BigDecimal.valueOf(3250), 2009)).isEqualTo(BigDecimal.valueOf(3559));
    }

	@Test
	@DisplayName("Base demi montant frais garde")
	public void indexationBaseDemiMontantFraisGarde() {
		assertThat(indexateur.indexer(ANNEE_INDEXATION_LIPP_5_D_3_16, BigDecimal.valueOf(1250), 2003)).isEqualTo(BigDecimal.valueOf(1250));
	}

	@Test
	@DisplayName("2007 demi montant frais garde")
    public void indexation2007DemiMontantFraisGarde() {
		assertThat(indexateur.indexer(ANNEE_INDEXATION_LIPP_5_D_3_16, BigDecimal.valueOf(1250), 2007)).isEqualTo(BigDecimal.valueOf(1299));
    }

	@Test
	@DisplayName("2009 demi montant frais garde")
    public void indexation2009DemiMontantFraisGarde() {
		assertThat(indexateur.indexer(ANNEE_INDEXATION_LIPP_5_D_3_16, BigDecimal.valueOf(1250), 2009)).isEqualTo(BigDecimal.valueOf(1369));
    }


	@Test
	@DisplayName("2017 déduction double activité")
	public void indexation2017DeductionDoubleActivite() {
		assertThat(indexateur.indexer(ANNEE_INDEXATION_LIPP_D_3_08, BigDecimal.valueOf(500), 2017)).isEqualTo(BigDecimal.valueOf(499));
	}

	@Test
	@DisplayName("2019 déduction double activité")
	public void indexation2019DeductionDoubleActivite() {
		assertThat(indexateur.indexer(ANNEE_INDEXATION_LIPP_D_3_08, BigDecimal.valueOf(500), 2019)).isEqualTo(BigDecimal.valueOf(499));
	}

}
