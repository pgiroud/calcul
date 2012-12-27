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
package org.impotch.calcul.assurancessociales;

/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 * 
 */
public class SituationAVS {
	
	private StatutAVS statutAVS;
	private boolean isComplementaireConjoint;
	private int nombreOrphelin;

	public SituationAVS(StatutAVS statutAVS, boolean isComplementaireEpouse,
			int nombreOrphelin) {
		this.statutAVS = statutAVS;
		this.isComplementaireConjoint = isComplementaireEpouse;
		this.nombreOrphelin = nombreOrphelin;
	}

	public StatutAVS getStatutAVS() {
		return statutAVS;
	}

	public void setStatutAVS(StatutAVS statutAVS) {
		this.statutAVS = statutAVS;
	}

	public boolean isComplementaireConjoint() {
		return isComplementaireConjoint;
	}

	public void setComplementaireConjoint(boolean isComplementaireConjoint) {
		this.isComplementaireConjoint = isComplementaireConjoint;
	}

	public int getNombreOrphelin() {
		return nombreOrphelin;
	}

	public void setNombreOrphelin(int nombreOrphelin) {
		this.nombreOrphelin = nombreOrphelin;
	}

}
