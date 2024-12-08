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
package org.impotch.calcul.impot.france;

import org.impotch.bareme.Bareme;
import org.impotch.util.TypeArrondi;

import static org.impotch.bareme.ConstructeurBareme.unBaremeATauxMarginal;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class FournisseurFrance {

	private Bareme getBaremeRevenu2018() {
		return unBaremeATauxMarginal()
				.jusqua(9965).taux(" 0 %")
				.puisJusqua(27520).taux(" 14 %")
				.puisJusqua(73780).taux(" 30 %")
				.puisJusqua(156245).taux("41 %")
				.puis().taux("45 %")
				.typeArrondiSurChaqueTranche(TypeArrondi.UNITE_INF)
				.construire();
	}

	private Bareme getBaremeRevenu2017() {
		return unBaremeATauxMarginal()
				           .jusqua(9807).taux(" 0 %")
				.puisJusqua(27086) .taux(" 14 %")
				.puisJusqua(72617) .taux(" 30 %")
				.puisJusqua(153783).taux("41 %")
				.puis()           .taux("45 %")
				.typeArrondiSurChaqueTranche(TypeArrondi.UNITE_INF)
				.construire();
	}

	private Bareme getBaremeRevenu2016() {
		return unBaremeATauxMarginal()
				.jusqua(9710).taux(" 0 %")
				.puisJusqua(26818).taux(" 14 %")
				.puisJusqua(71898).taux(" 30 %")
				.puisJusqua(152260).taux("41 %")
				.puis()                .taux("45 %")
				.typeArrondiSurChaqueTranche(TypeArrondi.UNITE_INF)
				.construire();
	}
	private Bareme getBaremeRevenu2015() {
		return unBaremeATauxMarginal()
				.jusqua(9700).taux(" 0 %")
				.puisJusqua(26791).taux(" 14 %")
				.puisJusqua(71826).taux(" 30 %")
				.puisJusqua(152108).taux("41 %")
				.puis()                .taux("45 %")
				.typeArrondiSurChaqueTranche(TypeArrondi.UNITE_INF)
				.construire();
	}
	private Bareme getBaremeRevenu2014() {
		return unBaremeATauxMarginal()
				.jusqua(9690).taux(" 0 %")
				.puisJusqua(26764).taux(" 14 %")
				.puisJusqua(71754).taux(" 30 %")
				.puisJusqua(151956).taux("41 %")
				.puis()                .taux("45 %")
				.typeArrondiSurChaqueTranche(TypeArrondi.UNITE_INF)
				.construire();
	}
	private Bareme getBaremeRevenu2013() {
		return unBaremeATauxMarginal()
				.jusqua(6011).taux(" 0 %")
				.puisJusqua(11991).taux("5.5 %")
				.puisJusqua(26631).taux(" 14 %")
				.puisJusqua(71397).taux(" 30 %")
				.puisJusqua(151200).taux("41 %")
				.puis()               .taux("45 %")
				.typeArrondiSurChaqueTranche(TypeArrondi.UNITE_INF)
				.construire();
	}
	private Bareme getBaremeRevenu2012() {
		return unBaremeATauxMarginal()
				.jusqua(5963).taux(" 0 %")
				.puisJusqua(11896).taux("5.5 %")
				.puisJusqua(26420).taux(" 14 %")
				.puisJusqua(70830).taux(" 30 %")
				.puisJusqua(150000).taux("41 %")
				.puis()                .taux("45 %")
				.typeArrondiSurChaqueTranche(TypeArrondi.UNITE_INF)
				.construire();
	}
	private Bareme getBaremeRevenu2010_2011() {
		return unBaremeATauxMarginal()
				.jusqua(5963).taux(" 0 %")
				.puisJusqua(11896).taux("5.5 %")
				.puisJusqua(26420).taux(" 14 %")
				.puisJusqua(70830).taux(" 30 %")
				.puis()                .taux("41 %")
				.typeArrondiSurChaqueTranche(TypeArrondi.UNITE_INF)
				.construire();
	}

	private Bareme getBaremeRevenu2009() {
		return unBaremeATauxMarginal()
				.jusqua(5875).taux(" 0 %")
				.puisJusqua(11720).taux("5.5 %")
				.puisJusqua(26030).taux(" 14 %")
				.puisJusqua(69783).taux(" 30 %")
				.puis()               .taux("40 %")
				.typeArrondiSurChaqueTranche(TypeArrondi.UNITE_INF)
				.construire();
	}

	private Bareme getBaremeRevenu2008() {
		return unBaremeATauxMarginal()
				.jusqua(5852).taux(" 0 %")
				.puisJusqua(11673).taux("5.5 %")
				.puisJusqua(25926).taux(" 14 %")
				.puisJusqua(69505).taux(" 30 %")
				.puis()                .taux("40 %")
				.typeArrondiSurChaqueTranche(TypeArrondi.UNITE_INF)
				.construire();
	}
	private Bareme getBaremeRevenu2007() {
			return unBaremeATauxMarginal()
				.jusqua(5687).taux(" 0 %")
				.puisJusqua(11344).taux("5.5 %")
				.puisJusqua(25195).taux(" 14 %")
				.puisJusqua(67546).taux(" 30 %")
				.puis()                .taux("40 %")
				.typeArrondiSurChaqueTranche(TypeArrondi.UNITE_INF)
				.construire();
	}
	private Bareme getBaremeRevenu2006() {
		return unBaremeATauxMarginal()
				.jusqua(5614).taux(" 0 %")
				.puisJusqua(11198).taux("5.5 %")
				.puisJusqua(24872).taux(" 14 %")
				.puisJusqua(66679).taux(" 30 %")
				.puis()                .taux("40 %")
				.typeArrondiSurChaqueTranche(TypeArrondi.UNITE_INF)
				.construire();
	}
	private Bareme getBaremeRevenu2005() {
			return unBaremeATauxMarginal()
				.jusqua(4412).taux(" 0 %")
				.puisJusqua(8677).taux("  6.83 %")
				.puisJusqua(15224).taux(" 19.14 %")
				.puisJusqua(24731).taux(" 28.26 %")
				.puisJusqua(40241).taux(" 37.38 %")
				.puisJusqua(49624).taux(" 42.62 %")
				.puis()                .taux("48.09 %")
				.typeArrondiSurChaqueTranche(TypeArrondi.UNITE_INF)
				.construire();
	}
	public Bareme getBaremeRevenu(int annee) {
		if (2018 == annee) {
			return getBaremeRevenu2018();
		} else if (2017 == annee) {
			return getBaremeRevenu2017();
		} else if (2016 == annee) {
			return getBaremeRevenu2016();
		} else if (2015 == annee) {
			return getBaremeRevenu2015();
		} else if (2014 == annee) {
			return getBaremeRevenu2014();
		} else if (2013 == annee) {
			return getBaremeRevenu2013();
		} else if (2012 == annee) {
			return getBaremeRevenu2012();
		} else if (2010 == annee || 2011 == annee) {
			return getBaremeRevenu2010_2011();
		} else if (2009 == annee) {
			return getBaremeRevenu2009();
		} else if (2008 == annee) {
			return getBaremeRevenu2008();
		} else if (2007 == annee) {
			return getBaremeRevenu2007();
		} else if (2006 == annee) {
			return getBaremeRevenu2006();
		} else if (2005 == annee) {
			return getBaremeRevenu2005();
		} else {
			throw new IllegalArgumentException("Pas de barème pour l'année " + annee);
		}
	}
}
