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
package org.impotch.calcul.impot.taxation.repart;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import org.impotch.calcul.impot.taxation.forimposition.ForCantonal;
import org.impotch.calcul.impot.taxation.repart.Part;
import org.impotch.calcul.impot.taxation.repart.Repartition;
import org.impotch.calcul.lieu.ICanton;
import org.impotch.calcul.lieu.ICommuneSuisse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class RepartitionTest {

	private Map<String,ICanton> cantons = new HashMap<String,ICanton>();
	private Map<String,ICommuneSuisse> communesCantons = new HashMap<String,ICommuneSuisse>();
	
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
		communesCantons.put(codeIso2Canton,new ICommuneSuisse(){
			@Override
			public ICanton getCanton() {return cantons.get(codeIso2Canton);}
			@Override
			public String getNom() {return nom;}
			@Override
			public int getNumeroOFS() {return numOFS;}
		});
	}
	
	@Before
	public void remplirCantonCommune() {
		this.remplirCanton("VD","Vaud",9022);
		this.remplirCommune("VD","Lausanne",5586);
		this.remplirCanton("FR","Fribourg",9010);
		this.remplirCommune("FR","Fribourg",2196);
		this.remplirCanton("VS","Valais",9023);
		this.remplirCommune("VS","Sion",6266);
		this.remplirCanton("GE","Genève",9025);
		this.remplirCommune("GE","Genève",6621);
		this.remplirCanton("JU","Jura",9026);
		this.remplirCommune("JU","Delémont",6711);
		this.remplirCanton("BL","Bâle-Campagne",9013);
		this.remplirCommune("BL","Liestal",2829);
	}
	
	
    private Repartition<ForCantonal> creerRepartitionCantonal(String insCodeCanton, int iMontant)
    {
        Repartition<ForCantonal> repart = new Repartition<ForCantonal>();
        repart.ajouterPart(getFor(insCodeCanton),new Part(new BigDecimal(iMontant)));
        return repart;
    }

    private void ajouterPart(Repartition<ForCantonal> inoRepart, String insCodeCanton, int iMontant)
    {
        inoRepart.ajouterPart(getFor(insCodeCanton),new Part(new BigDecimal(iMontant)));
    }

    private ForCantonal getFor(String insCodeCanton)
    {
        return new ForCantonal(communesCantons.get(insCodeCanton));
    }

    private void controlePresence(Repartition<ForCantonal> repart, String insCodeCanton, int iMontantAttendu)
    {
        Part oPart = repart.getPart(getFor(insCodeCanton));
        if (null == oPart) {
            fail("Le canton " + insCodeCanton + " n'a pas été trouvé dans la répartition");
        } else {
            assertThat(oPart.getMontant()).isEqualTo(BigDecimal.valueOf(iMontantAttendu));
        }
    }

    @Test
    public void testAjouterRepartSansIntersection()
    {
        Repartition<ForCantonal> repart1 = creerRepartitionCantonal("VD",1000);
        Repartition<ForCantonal> repart2 = creerRepartitionCantonal("FR",500);
        Repartition<ForCantonal> somme = repart1.ajouter(repart2);
        controlePresence(somme,"VD",1000);
        controlePresence(somme,"FR",500);
        somme = somme.ajouter(creerRepartitionCantonal("VS",5000));
        controlePresence(somme,"VD",1000);
        controlePresence(somme,"FR",500);
        controlePresence(somme,"VS",5000);
    }

    @Test
    public void testAjouterRepart()
    {
        Repartition<ForCantonal> repart1 = creerRepartitionCantonal("VD",1000);
        ajouterPart(repart1,"GE",3400);
        ajouterPart(repart1,"FR",200);
        ajouterPart(repart1,"JU",1300);
        Repartition<ForCantonal> repart2 = creerRepartitionCantonal("FR",500);
        Repartition<ForCantonal> somme = repart1.ajouter(repart2);
        controlePresence(somme,"VD",1000);
        controlePresence(somme,"GE",3400);
        controlePresence(somme,"FR",700);
        controlePresence(somme,"JU",1300);
        ajouterPart(repart2,"GE",3400);
        ajouterPart(repart2,"BL",200);
        somme = repart1.ajouter(repart2);
        controlePresence(somme,"VD",1000);
        controlePresence(somme,"GE",6800);
        controlePresence(somme,"FR",700);
        controlePresence(somme,"JU",1300);
        controlePresence(somme,"BL",200);
    }

    @Test
    public void testRepartirAvecDeltaArrondi() {
        Repartition<ForCantonal> cle = creerRepartitionCantonal("VD",30);
        ajouterPart(cle,"GE",20);
        ajouterPart(cle,"FR",50);
        Repartition<ForCantonal> repart = cle.repartir(new BigDecimal("659"));
        // Sur Vaud : arrondi_francs(659 * 30 %) = arrondi_francs(197.7) = 198
        // Sur Genève : arrondi_francs(659 * 20 %) = arrondi_francs(131.8) = 132
        // Sur Fribourg : arrondi_francs(659 * 50 %) = arrondi_francs(329.5) = 330
        // On somme chacune des parts : 198 + 132 + 330 = 660 d'où une différence d'arrondi de 1 franc
        // On répercute cette différence sur la plus grosse part : Fribourg 
        Part oPart = repart.getPart(getFor("FR"));
        assertThat(oPart.getMontant()).isEqualTo("329");
    }

    @Test
    public void testOrdrePart() {
        // On veut tester que l'ordre des parts n'a pas d'influence
        // en répartissant 1 franc entre 3 cantons (avec arrondi à 1 franc)
        Repartition<ForCantonal> cle = creerRepartitionCantonal("VD",30);
        ajouterPart(cle,"GE",30);
        ajouterPart(cle,"VS",30);
        Repartition<ForCantonal> repart = cle.repartir(BigDecimal.ONE);
        assertThat(repart.getPart(getFor("GE")).getMontant()).isEqualTo("1");
        assertThat(repart.getPart(getFor("VD")).getMontant()).isEqualTo("0");
        assertThat(repart.getPart(getFor("VS")).getMontant()).isEqualTo("0");
        // On change l'ordre des parts
        cle = creerRepartitionCantonal("GE",30);
        ajouterPart(cle,"VD",30);
        ajouterPart(cle,"VS",30);
        repart = cle.repartir(BigDecimal.ONE);
        assertThat(repart.getPart(getFor("GE")).getMontant()).isEqualTo("1");
        assertThat(repart.getPart(getFor("VD")).getMontant()).isEqualTo("0");
        assertThat(repart.getPart(getFor("VS")).getMontant()).isEqualTo("0");
    }

    @Test
    public void differenceEntreTypeCalculRepartition() {
        // Soit 2 franc à répartir en 3 parts  (arrondi au franc le plus proche)
        Repartition<ForCantonal> cle = creerRepartitionCantonal("VD",1);
        ajouterPart(cle,"GE",3);
        ajouterPart(cle,"VS",1);
        // Répartition classique : 2 parts à 0 franc et 1 part à 2 francs
        Repartition<ForCantonal> repart = cle.repartir(BigDecimal.valueOf(2));
        assertThat(repart.getPart(getFor("VD")).getMontant()).isEqualTo("0");
        assertThat(repart.getPart(getFor("VS")).getMontant()).isEqualTo("0");
        assertThat(repart.getPart(getFor("GE")).getMontant()).isEqualTo("2");
        // Répartition seulement sur reste à répartir : 1 part à 0 franc et 2 parts à 1 franc
        repart = cle.repartirSurResteARepartir(BigDecimal.valueOf(2));
        assertThat(repart.getPart(getFor("VD")).getMontant()).isEqualTo("0");
        assertThat(repart.getPart(getFor("VS")).getMontant()).isEqualTo("1");
        assertThat(repart.getPart(getFor("GE")).getMontant()).isEqualTo("1");
    }

    @Test
    public void testAvec2PartsAZero() {
        Repartition<ForCantonal> cle = creerRepartitionCantonal("VD",0);
        ajouterPart(cle,"GE",0);
        ajouterPart(cle,"VS",1);
        // Répartition classique : 2 parts à 0 franc et 1 part à 2 francs
        Repartition<ForCantonal> repart = cle.repartir(BigDecimal.valueOf(2));
        assertThat(repart.getPart(getFor("VD")).getMontant()).isEqualTo("0");
        assertThat(repart.getPart(getFor("GE")).getMontant()).isEqualTo("0");
        assertThat(repart.getPart(getFor("VS")).getMontant()).isEqualTo("2");
        // Répartition seulement sur reste à répartir : 2 parts à 0 franc et 1 part à 2 francs
        repart = cle.repartirSurResteARepartir(BigDecimal.valueOf(2));
        assertThat(repart.getPart(getFor("VD")).getMontant()).isEqualTo("0");
        assertThat(repart.getPart(getFor("GE")).getMontant()).isEqualTo("0");
        assertThat(repart.getPart(getFor("VS")).getMontant()).isEqualTo("2");

    }
    
}
