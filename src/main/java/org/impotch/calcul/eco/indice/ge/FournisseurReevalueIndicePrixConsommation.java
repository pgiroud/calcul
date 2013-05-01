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
package org.impotch.calcul.eco.indice.ge;

import java.math.BigDecimal;
import static java.math.BigDecimal.ZERO;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.impotch.util.BigDecimalUtil;
import org.impotch.util.StringUtil;

public class FournisseurReevalueIndicePrixConsommation implements
FournisseurIndicePrixConsommation {



	private String[] indicesMai1993 = new String[]{
			/* Année	Jan 		Fév			Mars		Avril 		Mai			Juin		Juillet		Août		Septembre	Octobre		Novembre	Décembre
			/* 1993 */													"100.0",	"100.0",	"99.9",		"100.4",	"100.3",	"100.4",	"100.8",	"100.9",
			/* 1994 */	"100.9",	"101.1",	"100.0",	"101.1",	"100.9",	"101.0",	"101.1",	"101.4",	"101.5",	"101.4",	"101.5",	"101.3",
			/* 1995 */	"102.3",	"103.1",	"102.9",	"103.0",	"103.0",	"103.3",	"103.3",	"103.5",	"103.4",	"103.2",	"103.2",	"103.3",
			/* 1996 */	"103.5",	"103.8",	"104.0",	"104.1",	"103.8",	"103.9",	"103.8",	"104.0",	"104.1",	"104.1",	"103.9",	"104.0",
			/* 1997 */ 	"104.4",	"104.6",	"104.5",	"104.3",	"104.2",	"104.4",	"104.3",	"104.6",	"104.7",	"105.0",	"105.1",	"105.2",
			/* 1998 */	"105.1",	"105.6",	"105.5",	"105.6",	"105.4",	"105.4",	"105.3",	"105.5",	"105.5",	"105.5",	"105.2",	"105.1",
			/* 1999 */ 	"105.3",	"105.7",	"105.8",	"106.2",	"106.0",	"106.1",	"106.2",	"106.5",	"106.6",	"106.5",	"106.5",	"106.9",
			/* 2000 */	"107.0",	"107.1",	"107.2",	"107.4",	"107.6"
	};	

	private String[] indicesMai2000 = new String[]{
			/* Année	Jan 		Fév			Mars		Avril 		Mai			Juin		Juillet		Août		Septembre	Octobre		Novembre	Décembre
			/* 2000 */													"100.0",	"100.3",	"100.4",	"100.3",	"100.8",	"100.7",	"100.8",	"100.7",
			/* 2001 */	"100.6",	"100.6",	"100.0",	"101.0",	"101.6",	"101.8",	"101.7",	"101.2",	"101.3",	"101.1",	"101.5",	"101.4",
			/* 2002 */	"101.5",	"101.4",	"101.5",	"102.2",	"102.4",	"102.4",	"101.9",	"101.8",	"102.0",	"102.4",	"102.4",	"102.4",
			/* 2003 */	"102.5",	"102.7",	"103.1",	"103.2",	"103.2",	"103.2",	"102.3",	"102.5",	"102.6",	"103.2",	"103.3",	"103.3",
			/* 2004 */	"103.0",	"103.0",	"103.1",	"104.5",	"104.8",	"104.9",	"103.9",	"104.2",	"104.2",	"105.0",	"105.1",	"105.0",
			/* 2005 */	"104.6",	"104.8",	"105.1",	"105.9",	"106.0",	"105.7",	"105.2",	"105.4",	"105.8",	"106.7",	"106.3",	"106.2"
	};

	private String[] indicesDec2005 = new String[]{
			/* Année	Jan 		Fév			Mars		Avril 		Mai			Juin		Juillet		Août		Septembre	Octobre		Novembre	Décembre
			/* 2005 */																																		"100.0",      
			/* 2006 */	"99.8",		"100.0",	"100.0",	"100.8",	"101.0",	"101.0",	"100.3",	"100.6",	"100.4",	"100.8",	"100.7",	"100.7",
			/* 2007 */ 	"99.7",		"99.9",		"100.0",	"101.1",	"101.1",	"101.2",	"100.6",	"100.6",	"100.7",	"101.6",	"102.1",	"102.4",
			/* 2008 */	"102.3",	"102.5",	"102.8",	"103.6",	"104.1",	"104.4",	"103.9",	"103.8",	"103.8",	"104.4",	"103.5",	"103.1",
			/* 2009 */	"102.2",	"102.4",	"102.1",    "103.0",	"103.3",	"103.3",	"102.6",	"103.0",	"103.0",	"103.6",	"103.8",	"103.6",
			/* 2010 */ 	"103.4",	"103.6",	"103.8",	"104.6",	"104.5",	"104",		"103.3",	"103.5"
	};

	private Map<BaseIndiceGenevoisPrixConsommation,String[]> mapIndices = new EnumMap<BaseIndiceGenevoisPrixConsommation,String[]>(BaseIndiceGenevoisPrixConsommation.class);



	public FournisseurReevalueIndicePrixConsommation() {
		super();
		construireMapIndices();
	}

	private void construireMapIndices() {
		mapIndices.put(BaseIndiceGenevoisPrixConsommation.MAI_1993,indicesMai1993);
		mapIndices.put(BaseIndiceGenevoisPrixConsommation.MAI_2000,indicesMai2000);
		mapIndices.put(BaseIndiceGenevoisPrixConsommation.DECEMBRE_2005,indicesDec2005);
	}

	private void validAnneeMoisSuivantBase(String prefix, BaseIndiceGenevoisPrixConsommation base,
			int annee, int mois) {
		if (annee < base.getAnnee()) throw new IllegalArgumentException(prefix + "L'année " + annee + " pour laquelle on veut l'indice ne peut pas être inférieure à l'année " + base.getAnnee() + " de l'indice ");
		else if (annee == base.getAnnee() && mois < base.getMois()) throw new IllegalArgumentException(prefix + "Le mois " + StringUtil.getNomMois(mois) + "/" + annee + " pour lequel on veut l'indice ne peut pas être inférieur au mois " +  StringUtil.getNomMois(base.getMois()) + "/" + base.getAnnee() + " de base de l'indice");
	}

	private BigDecimal arrondi(BigDecimal mnt) {
		BigDecimal normalise = mnt.setScale(1, RoundingMode.HALF_UP);
		return normalise;
	}

	@Override
	public BigDecimal fournir(BaseIndiceGenevoisPrixConsommation base,
			int annee, int mois) {
		validAnneeMoisSuivantBase("",base,annee,mois);
		BigDecimal multiplicateur = BigDecimalUtil.UN;
		for (BaseIndiceGenevoisPrixConsommation uneBase : mapIndices.keySet()) {
			if (0 <= uneBase.compareTo(base)) {
				int distance = 12 * (annee - uneBase.getAnnee()) + (mois - uneBase.getMois());
				String[] indices = mapIndices.get(uneBase);
				if (distance > indices.length) {
					multiplicateur = multiplicateur.multiply(new BigDecimal(indices[indices.length-1])).movePointLeft(2);
				} else {
					BigDecimal indice = new BigDecimal(indices[distance]);
					indice = indice.multiply(multiplicateur);
					return arrondi(indice);
				}
			}
		}
		return null;
	}

	private BigDecimal getIndice(String[] indices, BigDecimal multiplicateur, int position) {
		BigDecimal indice = new BigDecimal(indices[position]);
		return indice.multiply(multiplicateur);
	}

	private void execute(ExecuteSurIndice executeur, BaseIndiceGenevoisPrixConsommation base,
			int anneeDebut, int moisDebut, int anneeFin, int moisFin) {
		BigDecimal multiplicateur = BigDecimalUtil.UN;
		for (BaseIndiceGenevoisPrixConsommation uneBase : mapIndices.keySet()) {
			if (0 <= uneBase.compareTo(base)) {
				String[] indices = mapIndices.get(uneBase);

				int indexDebut = Math.max(0,12 * (anneeDebut - uneBase.getAnnee()) + (moisDebut - uneBase.getMois()));
				if (indexDebut < indices.length -1) {
					int indexFin = 12 * (anneeFin - uneBase.getAnnee()) + (moisFin - uneBase.getMois());
					if (indexFin >= indices.length -1) indexFin = indices.length - 2;
					if (indexFin >= 0) {
						for (int i = indexDebut; i <= indexFin; i++) executeur.execute(getIndice(indices,multiplicateur,i));
					}
				}
				multiplicateur = multiplicateur.multiply(new BigDecimal(indices[indices.length-1])).movePointLeft(2);
			}
		}
	}

	private List<BigDecimal> fournirOrdonne(BaseIndiceGenevoisPrixConsommation base,
			int anneeDebut, int moisDebut, int anneeFin, int moisFin) {
		Accumulateur executeur = new Accumulateur();
		execute(executeur,base,anneeDebut,moisDebut,anneeFin,moisFin);
		return executeur.getListe();
	}

	@Override
	public List<BigDecimal> fournir(BaseIndiceGenevoisPrixConsommation base,
			int anneeDebut, int moisDebut, int anneeFin, int moisFin) {
		validAnneeMoisSuivantBase("Début : ",base,anneeDebut,moisDebut);
		validAnneeMoisSuivantBase("Fin : ",base,anneeFin,moisFin);
		if (anneeDebut > anneeFin || (anneeDebut == anneeFin && moisDebut > moisFin)) return fournirOrdonne(base,anneeFin,moisFin,anneeDebut,moisDebut);
		else return fournirOrdonne(base,anneeDebut,moisDebut,anneeFin,moisFin);
	}

	private BigDecimal fournirMoyenneOrdonne(BaseIndiceGenevoisPrixConsommation base,
			int anneeDebut, int moisDebut, int anneeFin, int moisFin) {
		Somme executeur = new Somme();
		execute(executeur,base,anneeDebut,moisDebut,anneeFin,moisFin);
		int distanceEntreDebutEtFin = 1 + 12 * (anneeFin - anneeDebut) + (moisFin - moisDebut);
		return executeur.getSomme().divide(new BigDecimal(distanceEntreDebutEtFin),1,RoundingMode.HALF_UP);
	}

	@Override
	public BigDecimal fournirMoyenne(BaseIndiceGenevoisPrixConsommation base,
			int anneeDebut, int moisDebut, int anneeFin, int moisFin) {
		validAnneeMoisSuivantBase("Début : ",base,anneeDebut,moisDebut);
		validAnneeMoisSuivantBase("Fin : ",base,anneeFin,moisFin);
		if (anneeDebut > anneeFin || (anneeDebut == anneeFin && moisDebut > moisFin)) return fournirMoyenneOrdonne(base,anneeFin,moisFin,anneeDebut,moisDebut);
		else return fournirMoyenneOrdonne(base,anneeDebut,moisDebut,anneeFin,moisFin);
	}


	private interface ExecuteSurIndice {
		void execute(BigDecimal indice);
	}

	private class Accumulateur implements ExecuteSurIndice {
		private final List<BigDecimal> liste = new ArrayList<BigDecimal>();

		public void execute(BigDecimal indice) {
			liste.add(arrondi(indice));
		} 
		public List<BigDecimal> getListe() {
			return liste;
		}
	}

	private class Somme implements ExecuteSurIndice {
		private BigDecimal somme = ZERO;

		public void execute(BigDecimal indice) {
			somme = somme.add(indice);
		}

		public BigDecimal getSomme() {
			return arrondi(somme);
		}
	}
}
