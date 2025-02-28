package org.impotch.calcul.assurancessociales;

import java.math.BigDecimal;

@FunctionalInterface
interface RegleCalculCotisationAssuranceChomage {
    BigDecimal caculCotisationAssuranceChomage(BigDecimal revenu);
}

