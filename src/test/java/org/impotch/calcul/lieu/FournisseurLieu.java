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
package org.impotch.calcul.lieu;

import java.util.HashMap;
import java.util.Map;

public class FournisseurLieu {
	
	private Map<String,ICanton> cantons = new HashMap<String,ICanton>();
	private Map<Integer,ICommuneSuisse> communes = new HashMap<Integer,ICommuneSuisse>();

	public FournisseurLieu() {
		this.remplirCantonCommune();
	}
	
	
	private void remplirCanton(final String codeIso2, final String nom, final int numOFS) {
		cantons.put(codeIso2, new ICanton(){
			@Override
			public String getCodeIso2() {return codeIso2; }
			@Override
			public String getNom() {return nom;}
			@Override
			public int getNumeroOFS() {return numOFS;}
			
		});
	}
	
	private void remplirCommune(final String codeIso2Canton, final String nom, final int numOFS) {
		communes.put(numOFS,new ICommuneSuisse(){
			@Override
			public ICanton getCanton() {return cantons.get(codeIso2Canton);}
			@Override
			public String getNom() {return nom;}
			@Override
			public int getNumeroOFS() {return numOFS;}
		});
	}
	
	
	public ICanton getCanton(String code) {
		return cantons.get(code);
	}
	
	
	
	public ICommuneSuisse getCommune(int numOFS) {
		return communes.get(numOFS);
	}
	
	public ICommuneSuisse getCommuneGeneve() {
		return getCommune(6621);
	}
	
	public ICommuneSuisse getCommuneCarouge() {
		return getCommune(6608);
	}
	
	public ICommuneSuisse getCommuneAireLaVille() {
		return getCommune(6601);
	}
	
	public ICommuneSuisse getCommuneCheneBougerie() {
		return getCommune(6612);
	}
	
	public ICommuneSuisse getCommunePresinge() {
		return getCommune(6635);
	}
	
	public ICommuneSuisse getCommuneMeyrin() {
		return getCommune(6630);
	}
	
	private void remplirCantonCommune() {
		this.remplirCanton("GE","Genève",9025);
		this.remplirCommune("GE","Genève",6621);
		this.remplirCommune("GE","Carouge(GE)",6608);
		this.remplirCommune("GE","Aire-La-Ville",6601);
		this.remplirCommune("GE","Chêne-Bougerie",6612);
		this.remplirCommune("GE","Presinge",6635);
		this.remplirCommune("GE","Meyrin",6630);

		
		this.remplirCanton("VD","Vaud",9022);
		this.remplirCommune("VD","Lausanne",5586);
		
		this.remplirCanton("FR","Fribourg",9010);
		this.remplirCommune("FR","Fribourg",2196);
		this.remplirCommune("FR","Attalens",2321);
		
		this.remplirCanton("VS","Valais",9023);
		this.remplirCommune("VS","Sion",6266);
		
		
		this.remplirCanton("JU","Jura",9026);
		this.remplirCommune("JU","Delémont",6711);
		
		this.remplirCanton("BL","Bâle-Campagne",9013);
		this.remplirCommune("BL","Liestal",2829);
	}
	
}
