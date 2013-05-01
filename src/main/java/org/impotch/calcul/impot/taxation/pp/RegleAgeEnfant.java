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
package org.impotch.calcul.impot.taxation.pp;


/**
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class RegleAgeEnfant {

	private int ageMajorite;
	private int ageLimiteJeuneEnfant;
	private int ageLimiteJeuneAdulte;
		
    /**
	 * @return the ageMajorite
	 */
	public int getAgeMajorite() {
		return ageMajorite;
	}

	/**
	 * @param ageMajorite the ageMajorite to set
	 */
	public void setAgeMajorite(int ageMajorite) {
		this.ageMajorite = ageMajorite;
	}

	/**
	 * @return the ageLimiteJeuneEnfant
	 */
	public int getAgeLimiteJeuneEnfant() {
		return ageLimiteJeuneEnfant;
	}

	/**
	 * @param ageLimiteJeuneEnfant the ageLimiteJeuneEnfant to set
	 */
	public void setAgeLimiteJeuneEnfant(int ageLimiteJeuneEnfant) {
		this.ageLimiteJeuneEnfant = ageLimiteJeuneEnfant;
	}

	/**
	 * @return the ageLimiteJeuneAdulte
	 */
	public int getAgeLimiteJeuneAdulte() {
		return ageLimiteJeuneAdulte;
	}

	/**
	 * @param ageLimiteJeuneAdulte the ageLimiteJeuneAdulte to set
	 */
	public void setAgeLimiteJeuneAdulte(int ageLimiteJeuneAdulte) {
		this.ageLimiteJeuneAdulte = ageLimiteJeuneAdulte;
	}

	public boolean isBorn(EnfantACharge enfant, int anneeFiscale) {
        return 0 <= enfant.getAge(anneeFiscale);
    }

    private boolean isAgeLimite(EnfantACharge enfant, int ageLimite, int anneeFiscale) {
        return isBorn(enfant, anneeFiscale) && enfant.getAge(anneeFiscale) < ageLimite;
    }
    
    /**
     * Retourne vrai si, à la date passée en paramètre, l'enfant est considéré comme jeune adulte en
     * formation i.e. l'enfant est né à cette date, il n'est pas mineur à cette date et son âge est
     * strictement inférieur à l'âge limite (25 ans en 2006)
     * @param enfant L'enfant dont on veut savoir s'il est un jeune adulte en formation
     * @param anneeFiscale l'année fiscale à laquelle on veut savoir si l'enfant est un jeune adulte en formation
     * @return vrai si, pour l'année fiscale passée en paramètre, l'enfant est considéré comme jeune adulte en formation.
     */
    public boolean isJeuneAdulteFormation(EnfantACharge enfant, int anneeFiscale) {
        return isAgeLimite(enfant,ageLimiteJeuneAdulte,anneeFiscale) && !isMineur(enfant, anneeFiscale);
    }

    /**
     * Retourne vrai si, pour la période fiscale passée en paramètre, l'enfant est considéré comme mineur i.e.
     * l'enfant est né et son âge est
     * strictement inférieur à l'âge de la majorité (18 ans en 2006)
     * @param enfant L'enfant dont on veut savoir s'il est mineur
     * @param anneeFiscale l'année fiscale à laquelle on veut savoir si l'enfant est un mineur
     * @return vrai si, à la date passée en paramètre, l'enfant est considéré comme mineur.
     */
    public boolean isMineur(EnfantACharge enfant, int anneeFiscale) {
        return isAgeLimite(enfant,ageMajorite,anneeFiscale);
    }

    /**
     * Retourne vrai si, à la date passée en paramètre, l'enfant est considéré comme jeune enfant i.e. l'enfant est
     * né à cette date et son age est strictement inférieur à l'âge fourni en paramètre.
     * @param enfant L'enfant dont on veut savoir s'il est jeune i.e. s'il doit être gardé.
     * @param anneeFiscale l'année fiscale à laquelle on teste si l'enfant est un jeune enfant
     * @return vrai si, à la date passée en paramètre, l'enfant est considéré comme jeune enfant.
     */
    public boolean isJeuneEnfant(EnfantACharge enfant, int anneeFiscale) {
        return isAgeLimite(enfant, ageLimiteJeuneEnfant,anneeFiscale);
    }


	
	
}
