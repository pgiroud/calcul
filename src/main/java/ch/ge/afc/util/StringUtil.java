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

import java.text.DecimalFormat;

public class StringUtil {

	/**
	 * Une espace insécable.
	 */

	public static final String ESPACE_INSECABLE = "\u00A0";

	public static final String POUR_MILLE = "\u2030";

	/**
	 * Vérifie que la chaîne de caractère passée en paramètre contient du texte.
	 * Elle retourne <code>true</code> si la chaîne n'est pas <code>null<code>,
	 * sa <code>longueur est > 0</code>, et elle a au moins un caractère qui
	 * n'est pas une espace.
	 * <p>
	 * 
	 * <pre>
	 * StringUtil.hasText(null) = false
	 * StringUtil.hasText(&quot;&quot;) = false
	 * StringUtil.hasText(&quot; &quot;) = false
	 * StringUtil.hasText(&quot;12345&quot;) = true
	 * StringUtil.hasText(&quot; 12345 &quot;) = true
	 * </pre>
	 * 
	 * @param insStr
	 *            la chaîne de caractère à tester, peut être <code>null</code>
	 * @return <code>true</code> si la chaîne de caractère n'est pas null, sa
	 *         longueur > 0, et contient un caractère autre qu'une espace.
	 * @see java.lang.Character#isWhitespace
	 */
	public static boolean hasText(String insStr) {
		int strLen;
		if (insStr == null || (strLen = insStr.length()) == 0)
			return false;
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(insStr.charAt(i)))
				return true;
		}
		return false;
	}

	/**
	 * D'après <a href="http://www.reveenjoie-poesie.com/outils-linguistiques/Typographie_francaise.html"
	 * >la règle de typographie
	 * <p/>
	 * </a>, il faut placer une espace insécable avant le signe du pour mille.
	 * <p/>
	 * Par exemple, on voudra formatter 0.034534 en 34.53 pour mille en
	 * utilisant buildPourMilleFormat(5).
	 * 
	 * @param pNombreDecimalMaximum
	 *            Le nombre maximum de décimal
	 * @return Le format
	 */

	public static DecimalFormat buildPourMilleFormat(int pNombreDecimalMaximum)

	{
		StringBuffer buffer = new StringBuffer("###0.");
		int nbChiffreApresVirgule = pNombreDecimalMaximum - 3;
		for (int i = 0; i < nbChiffreApresVirgule; i++) {
			buffer.append('#');
		}
		DecimalFormat format = new DecimalFormat(buffer.toString()
				+ ESPACE_INSECABLE + POUR_MILLE);
		format.setDecimalSeparatorAlwaysShown(false);
		return format;
	}
	
	public static String getNomMois(int mois) {
		return new java.text.DateFormatSymbols().getMonths()[mois];
	}

}
