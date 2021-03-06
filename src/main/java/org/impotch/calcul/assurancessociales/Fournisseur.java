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
package org.impotch.calcul.assurancessociales;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.impotch.calcul.assurancessociales.ge.CalculCotisationsSocialesSalarieGE;
import org.impotch.calcul.assurancessociales.ge.param.ParametrageCotisationAssuranceMaternite;
import org.impotch.calcul.assurancessociales.ge.param.ParametrageEnMemoireCotisationAssuranceMaternite;


/**
 * Cette classe a la responsabilité de fournir les calculateurs de cotisations sociales.
 * 
 * @author <a href="mailto:patrick.giroud@etat.ge.ch">Patrick Giroud</a>
 *
 */
public class Fournisseur implements FournisseurRegleCalculAssuranceSociale {
	
	/**************************************************/
	/****************** Attributs *********************/
	/**************************************************/

	private ConcurrentMap<Integer,CalculCotisationsSocialesSalarie> mapCalculateurCotisationAvsAiApgSalarie = new ConcurrentHashMap<Integer,CalculCotisationsSocialesSalarie>();
    private ConcurrentMap<Integer,CalculCotisationsSocialesSalarie> mapCalculateurCotisationAvsAiApgSalarieISIFD = new ConcurrentHashMap<Integer,CalculCotisationsSocialesSalarie>();
	private ConcurrentMap<Integer,CalculCotisationsSocialesSalarieGE> mapCalculateurCotisationAvsAiApgSalarieGE = new ConcurrentHashMap<Integer,CalculCotisationsSocialesSalarieGE>();
    private ConcurrentMap<Integer,CalculCotisationsSocialesSalarieGE> mapCalculateurCotisationAvsAiApgSalarieGEIFD = new ConcurrentHashMap<Integer,CalculCotisationsSocialesSalarieGE>();
    private ConcurrentMap<Integer,FournisseurMontantsLimitesPrevoyanceProfessionnelle> mapFournisseurMontantsLimitesPrevoyanceProfessionnelle = new ConcurrentHashMap<Integer, FournisseurMontantsLimitesPrevoyanceProfessionnelle>();
    private ConcurrentMap<Integer,FournisseurDeductionMaxPilier3a> mapFournisseurDeducMaxPilier3a = new ConcurrentHashMap<Integer, FournisseurDeductionMaxPilier3a>();

	private ConcurrentMap<Integer,CalculCotisationAvsAiApg> mapCalculateurCotisationAvsAiApgIndependant = new ConcurrentHashMap<Integer,CalculCotisationAvsAiApg>();
	
	private ConcurrentMap<Integer,CalculExtremaRentesAVS> mapCalculateurRentesAVS = new ConcurrentHashMap<Integer,CalculExtremaRentesAVS>();
	
	private Map<Integer,BigDecimal> renteSimpleMensuelleMinimumParAnnee;

    private ParametrageCotisationAssuranceMaternite parametrageCotisationAssuranceMaternite = new ParametrageEnMemoireCotisationAssuranceMaternite();

    public Fournisseur() {
        renseignerRenteSimpleMensuelleMinimumParAnnee();
    }

    /**************************************************/
    /******************* Méthodes *********************/
    /**************************************************/

	//--------------- Salariés --------------------

    private String tauxAPG(int annee) {
        if (annee < 2011) return "0.3 %";
        if (annee < 2016) return "0.5 %";
        else return "0.45 %";
    }

    private CalculCotisationsSocialesSalarie.Constructeur obtenirNouveauConstructeur(int annee) {
        CalculCotisationsSocialesSalarie.Constructeur constructeur = new CalculCotisationsSocialesSalarie.Constructeur();

        constructeur.tauxAvs("8.4 %").tauxAi("1.4 %").tauxApg(tauxAPG(annee))
            .tauxAC("2 %");

        // La loi fédérale sur l'assurance-accidents stipule qu' «en règle générale,
        // au moins 92 pour cent, mais pas plus de 96 pour cent des travailleurs assurés soient
        // couverts pour le gain intégral». Lorsque les salaires augmentent, le montant maximum
        // du gain assuré doit être adapté de temps à autre, afin de respecter cette exigence.
        // Une adaptation régulière intervenant chaque année est exclue en raison de la masse de
        // formalités administratives qu'elle entraînerait. Depuis la dernière augmentation en 1991,
        // les salaires ont progressé d'environ 14 pour cent (indice des salaires nominaux OFS).
        // La limite minimale légale de couverture d'assurance étant atteinte, le Conseil fédéral
        // a décidé d'augmenter le montant maximum du gain assuré de 97'200 à 106'800 francs.
        // Cela correspond à une hausse de 10 pour cent
        if (annee < 2000) constructeur.montantAnnuelMaxGainAssure(97200);
        else if (annee < 2008) constructeur.montantAnnuelMaxGainAssure(106800);
        // Décision du 27 juin 2007 du conseil fédéral (RO 2007 3667 Annexe 1)
        // la maximum passe de 106800 à 126000
        else if (annee < 2016) constructeur.montantAnnuelMaxGainAssure(126000);
        else  constructeur.montantAnnuelMaxGainAssure(148200);

        if (annee > 2010) {
            constructeur.tauxAC("2.2 %");
            if (annee < 2014) {
                constructeur.participationHautRevenuCotisationAC("2.5", "1 %");
            } else {
                constructeur.participationHautRevenuCotisationAC("1 %");
            }
        }
        return constructeur;
    }

	protected CalculCotisationsSocialesSalarie construireCalculateurSalarie(int annee) {
		return obtenirNouveauConstructeur(annee).construire(annee);
	}

    protected CalculCotisationsSocialesSalarie construireCalculateurSalarieISIFD(int annee) {
        CalculCotisationsSocialesSalarie.Constructeur constructeur = obtenirNouveauConstructeur(annee);
		if (annee > 2010 && annee < 2014) {
			constructeur.tauxAC("2.5 %");
		}
		return constructeur.construire(annee);
	}

    @Override
	public CalculCotisationsSocialesSalarie getCalculateurCotisationsSocialesSalarie(int annee) {
		if (!mapCalculateurCotisationAvsAiApgSalarie.containsKey(annee)) mapCalculateurCotisationAvsAiApgSalarie.putIfAbsent(annee, construireCalculateurSalarie(annee));
		return mapCalculateurCotisationAvsAiApgSalarie.get(annee);
	}

    @Override
    public CalculCotisationsSocialesSalarie getCalculateurCotisationsSocialesSalarieISIFD(int annee) {
        if (!mapCalculateurCotisationAvsAiApgSalarieISIFD.containsKey(annee)) mapCalculateurCotisationAvsAiApgSalarieISIFD.putIfAbsent(annee, construireCalculateurSalarieISIFD(annee));
        return mapCalculateurCotisationAvsAiApgSalarieISIFD.get(annee);
    }

    /**
     * On décore le calculateur valable pour toute la Suisse en incorporant le calcul de l'assurance maternité
     * qui n'est en vigueur que pour le canton de Genève.
     * @param annee
     * @param calculateurSuisse
     * @return
     */
    private CalculCotisationsSocialesSalarieGE construireCalculateurSalarie(int annee, BigDecimal taux, CalculCotisationsSocialesSalarie calculateurSuisse) {
        return new CalculCotisationsSocialesSalarieGE(annee,taux,calculateurSuisse);
    }

	protected CalculCotisationsSocialesSalarieGE construireCalculateurSalarieGE(int annee) {
        BigDecimal taux = parametrageCotisationAssuranceMaternite.fournirTaux(annee);
		return  construireCalculateurSalarie(annee,taux,getCalculateurCotisationsSocialesSalarie(annee));
	}

    protected CalculCotisationsSocialesSalarieGE construireCalculateurSalarieGEIFD(int annee) {
        BigDecimal taux = parametrageCotisationAssuranceMaternite.fournirTaux(annee);
        return construireCalculateurSalarie(annee,taux,getCalculateurCotisationsSocialesSalarieISIFD(annee));
    }

    @Override
	public CalculCotisationsSocialesSalarieGE getCalculateurCotisationsSocialesSalarieGE(int annee) {
		if (!mapCalculateurCotisationAvsAiApgSalarieGE.containsKey(annee)) mapCalculateurCotisationAvsAiApgSalarieGE.putIfAbsent(annee, construireCalculateurSalarieGE(annee));
		return mapCalculateurCotisationAvsAiApgSalarieGE.get(annee);
	}
	
    public CalculCotisationsSocialesSalarieGE getCalculateurCotisationsSocialesSalarieGEIFD(int annee) {
        if (!mapCalculateurCotisationAvsAiApgSalarieGEIFD.containsKey(annee)) mapCalculateurCotisationAvsAiApgSalarieGEIFD.putIfAbsent(annee, construireCalculateurSalarieGEIFD(annee));
        return mapCalculateurCotisationAvsAiApgSalarieGEIFD.get(annee);
    }

	//--------------- Indépendants --------------------

	protected CalculCotisationAvsAiApg construireCalculateurCotisationAvsAiApgIndependant(int annee) {
		CalculCotisationAvsAiApgIndependant.Constructeur constructeur = new CalculCotisationAvsAiApgIndependant.Constructeur();
		constructeur.tauxAvs("7.8 %").tauxAi("1.4 %");
		if (annee < 2009) {
            constructeur.tauxApg("0.3 %");
			constructeur.cotisationAvsAiApgMinimum("445");
			constructeur.trancheBareme( "8900", "4.2 %");
			constructeur.trancheBareme("15900", "4.2 %");
			constructeur.trancheBareme("20100", "4.3 %");
			constructeur.trancheBareme("22300", "4.4 %");
			constructeur.trancheBareme("24500", "4.5 %");
			constructeur.trancheBareme("26700", "4.6 %");
			constructeur.trancheBareme("28900", "4.7 %");
			constructeur.trancheBareme("31100", "4.9 %");
			constructeur.trancheBareme("33300", "5.1 %");
			constructeur.trancheBareme("35500", "5.3 %");
			constructeur.trancheBareme("37700", "5.5 %");
			constructeur.trancheBareme("39900", "5.7 %");
			constructeur.trancheBareme("42100", "5.9 %");
			constructeur.trancheBareme("44300", "6.2 %");
			constructeur.trancheBareme("46500", "6.5 %");
			constructeur.trancheBareme("48700", "6.8 %");
			constructeur.trancheBareme("50900", "7.1 %");
			constructeur.trancheBareme("53100", "7.4 %");
			return constructeur.construire(annee);
			
		} else if (annee < 2011) {
            constructeur.tauxApg("0.3 %");
			constructeur.cotisationAvsAiApgMinimum("460");
			constructeur.trancheBareme( "9200", "4.2 %");
			constructeur.trancheBareme("16000", "4.2 %");
			constructeur.trancheBareme("20300", "4.3 %");
			constructeur.trancheBareme("22600", "4.4 %");
			constructeur.trancheBareme("24900", "4.5 %");
			constructeur.trancheBareme("27200", "4.6 %");
			constructeur.trancheBareme("29500", "4.7 %");
			constructeur.trancheBareme("31800", "4.9 %");
			constructeur.trancheBareme("34100", "5.1 %");
			constructeur.trancheBareme("36400", "5.3 %");
			constructeur.trancheBareme("38700", "5.5 %");
			constructeur.trancheBareme("41000", "5.7 %");
			constructeur.trancheBareme("43300", "5.9 %");
			constructeur.trancheBareme("45600", "6.2 %");
			constructeur.trancheBareme("47900", "6.5 %");
			constructeur.trancheBareme("50200", "6.8 %");
			constructeur.trancheBareme("52500", "7.1 %");
			constructeur.trancheBareme("54800", "7.4 %");
			return constructeur.construire(annee);
		} else if (annee < 2013) {
            constructeur.tauxApg("0.5 %");
            constructeur.cotisationAvsAiApgMinimum("475");
            constructeur.trancheBareme( "9300", "4.2 %");
            constructeur.trancheBareme("16900", "4.2 %");
            constructeur.trancheBareme("21200", "4.3 %");
            constructeur.trancheBareme("23500", "4.4 %");
            constructeur.trancheBareme("25800", "4.5 %");
            constructeur.trancheBareme("28100", "4.6 %");
            constructeur.trancheBareme("30400", "4.7 %");
            constructeur.trancheBareme("32700", "4.9 %");
            constructeur.trancheBareme("35000", "5.1 %");
            constructeur.trancheBareme("37300", "5.3 %");
            constructeur.trancheBareme("39600", "5.5 %");
            constructeur.trancheBareme("41900", "5.7 %");
            constructeur.trancheBareme("44200", "5.9 %");
            constructeur.trancheBareme("46500", "6.2 %");
            constructeur.trancheBareme("48800", "6.5 %");
            constructeur.trancheBareme("51100", "6.8 %");
            constructeur.trancheBareme("53400", "7.1 %");
            constructeur.trancheBareme("55700", "7.4 %");
            return constructeur.construire(annee);
        } else if (annee < 2015) {
            constructeur.tauxApg("0.5 %");
            constructeur.cotisationAvsAiApgMinimum("480");
            constructeur.trancheBareme( "9400", "4.2 %");
            constructeur.trancheBareme("17200", "4.2 %");
            constructeur.trancheBareme("21700", "4.3 %");
            constructeur.trancheBareme("24000", "4.4 %");
            constructeur.trancheBareme("26300", "4.5 %");
            constructeur.trancheBareme("28600", "4.6 %");
            constructeur.trancheBareme("30900", "4.7 %");
            constructeur.trancheBareme("33200", "4.9 %");
            constructeur.trancheBareme("35500", "5.1 %");
            constructeur.trancheBareme("37800", "5.3 %");
            constructeur.trancheBareme("40100", "5.5 %");
            constructeur.trancheBareme("42400", "5.7 %");
            constructeur.trancheBareme("44700", "5.9 %");
            constructeur.trancheBareme("47000", "6.2 %");
            constructeur.trancheBareme("49300", "6.5 %");
            constructeur.trancheBareme("51600", "6.8 %");
            constructeur.trancheBareme("53900", "7.1 %");
            constructeur.trancheBareme("56200", "7.4 %");
            return constructeur.construire(annee);
        } else if (annee < 2017) {
            constructeur.tauxApg("0.5 %");
            constructeur.cotisationAvsAiApgMinimum("480");
            constructeur.trancheBareme( "9400", "4.2 %");
            constructeur.trancheBareme("17200", "4.2 %");
            constructeur.trancheBareme("21900", "4.3 %");
            constructeur.trancheBareme("24200", "4.4 %");
            constructeur.trancheBareme("26500", "4.5 %");
            constructeur.trancheBareme("28800", "4.6 %");
            constructeur.trancheBareme("31100", "4.7 %");
            constructeur.trancheBareme("33400", "4.9 %");
            constructeur.trancheBareme("35700", "5.1 %");
            constructeur.trancheBareme("38000", "5.3 %");
            constructeur.trancheBareme("40300", "5.5 %");
            constructeur.trancheBareme("42600", "5.7 %");
            constructeur.trancheBareme("44900", "5.9 %");
            constructeur.trancheBareme("47200", "6.2 %");
            constructeur.trancheBareme("49500", "6.5 %");
            constructeur.trancheBareme("51800", "6.8 %");
            constructeur.trancheBareme("54100", "7.1 %");
            constructeur.trancheBareme("56400", "7.4 %");
            return constructeur.construire(annee);
        } else {
            throw new IllegalArgumentException("Le barème des cotisations AVS indépendants n'est pas connu pour l'année " + annee);
        }
	}
	
	
	public CalculCotisationAvsAiApg getCalculateurCotisationAvsAiApgIndependant(int annee) {
		if (!mapCalculateurCotisationAvsAiApgIndependant.containsKey(annee)) mapCalculateurCotisationAvsAiApgIndependant.putIfAbsent(annee, construireCalculateurCotisationAvsAiApgIndependant(annee));
		return mapCalculateurCotisationAvsAiApgIndependant.get(annee);
	}
	
	// --------- Calculateur des rentes minimales ou maximales

    private void renseignerRenteSimpleMensuelleMinimumParAnnee() {
        /** Conformément à l'article 33ter de la loi sur l'assurance-vieillesse
        et survivants (LAVS; RS 831.10), le Conseil fédéral procède en règle
        générale tous les deux ans à une adaptation des rentes AVS/AI à l'indice
        mixte. Cependant, une adaptation peut avoir lieu plus tôt, lorsque l'indice
        suisse des prix à la consommation a augmenté de plus de 4 pour cent
        au cours d'une année. Le renchérissement du mois de juin par rapport
        à l'année précédente est dans ce cas déterminant (art. 51ter du
        règlement sur l'assurance-vieillesse et survivants, RAVS; RS 831.101). */
        renteSimpleMensuelleMinimumParAnnee = new HashMap<>();
        renteSimpleMensuelleMinimumParAnnee.put(1993,BigDecimal.valueOf(940));
        renteSimpleMensuelleMinimumParAnnee.put(1994,BigDecimal.valueOf(940));
        renteSimpleMensuelleMinimumParAnnee.put(1995,BigDecimal.valueOf(970));
        renteSimpleMensuelleMinimumParAnnee.put(1996,BigDecimal.valueOf(970));
        renteSimpleMensuelleMinimumParAnnee.put(1997,BigDecimal.valueOf(995));
        renteSimpleMensuelleMinimumParAnnee.put(1998,BigDecimal.valueOf(995));
        renteSimpleMensuelleMinimumParAnnee.put(1999,BigDecimal.valueOf(1005));
        renteSimpleMensuelleMinimumParAnnee.put(2000,BigDecimal.valueOf(1005));
        renteSimpleMensuelleMinimumParAnnee.put(2001,BigDecimal.valueOf(1030));
        renteSimpleMensuelleMinimumParAnnee.put(2002,BigDecimal.valueOf(1030));
        renteSimpleMensuelleMinimumParAnnee.put(2003,BigDecimal.valueOf(1055));
        renteSimpleMensuelleMinimumParAnnee.put(2004,BigDecimal.valueOf(1055));
        renteSimpleMensuelleMinimumParAnnee.put(2005,BigDecimal.valueOf(1075));
        renteSimpleMensuelleMinimumParAnnee.put(2006,BigDecimal.valueOf(1075));
        renteSimpleMensuelleMinimumParAnnee.put(2007,BigDecimal.valueOf(1105));
        renteSimpleMensuelleMinimumParAnnee.put(2008,BigDecimal.valueOf(1105));
        renteSimpleMensuelleMinimumParAnnee.put(2009,BigDecimal.valueOf(1140));
        renteSimpleMensuelleMinimumParAnnee.put(2010,BigDecimal.valueOf(1140));
        renteSimpleMensuelleMinimumParAnnee.put(2011,BigDecimal.valueOf(1160));
        renteSimpleMensuelleMinimumParAnnee.put(2012,BigDecimal.valueOf(1160));
        renteSimpleMensuelleMinimumParAnnee.put(2013,BigDecimal.valueOf(1170));
        renteSimpleMensuelleMinimumParAnnee.put(2014,BigDecimal.valueOf(1170));
        renteSimpleMensuelleMinimumParAnnee.put(2015,BigDecimal.valueOf(1175));
        renteSimpleMensuelleMinimumParAnnee.put(2016,BigDecimal.valueOf(1175));
        renteSimpleMensuelleMinimumParAnnee.put(2017,BigDecimal.valueOf(1175));
        renteSimpleMensuelleMinimumParAnnee.put(2018,BigDecimal.valueOf(1175));
        renteSimpleMensuelleMinimumParAnnee.put(2019,BigDecimal.valueOf(1185));
        renteSimpleMensuelleMinimumParAnnee.put(2020,BigDecimal.valueOf(1185));
        renteSimpleMensuelleMinimumParAnnee.put(2021,BigDecimal.valueOf(1195));
        renteSimpleMensuelleMinimumParAnnee.put(2022,BigDecimal.valueOf(1195));
    }

	public CalculExtremaRentesAVS getCalculateurExtremaRenteAVS(int annee) {
		if (!mapCalculateurRentesAVS.containsKey(annee)) mapCalculateurRentesAVS.putIfAbsent(annee, construireCalculateurExtremaRenteAVS(annee));
		return mapCalculateurRentesAVS.get(annee);
	}

	private CalculExtremaRentesAVS construireCalculateurExtremaRenteAVS(int annee) {
		CalculExtremaRentesAVS calculateur = new CalculExtremaRentesAVS(annee);
		calculateur.setRenteSimpleMensuelleMinimum(renteSimpleMensuelleMinimumParAnnee.get(annee));
		return calculateur;
	}

    // ------------- Fournisseur montants LPP-------------------------

    @Override
    public FournisseurMontantsLimitesPrevoyanceProfessionnelle getFournisseurMontantsLimitesPrevoyanceProfessionnelle(int annee) {
        if (!mapFournisseurMontantsLimitesPrevoyanceProfessionnelle.containsKey(annee)) mapFournisseurMontantsLimitesPrevoyanceProfessionnelle.putIfAbsent(annee,construireFournisseurMontantsLimitesPrevoyanceProfessionnelle(annee));
        return mapFournisseurMontantsLimitesPrevoyanceProfessionnelle.get(annee);
    }

    private FournisseurMontantsLimitesPrevoyanceProfessionnelle construireFournisseurMontantsLimitesPrevoyanceProfessionnelle(int annee) {
        FournisseurMontantsLimitesLPP fournisseur = new FournisseurMontantsLimitesLPP();
        fournisseur.setRenteAVSMensuelleMinimale(renteSimpleMensuelleMinimumParAnnee.get(annee));
        return fournisseur;
    }

    //------------ 3e Pilier ----------------


    @Override
    public FournisseurDeductionMaxPilier3a getFournisseurDeductionMaximale3ePilier(int annee) {
        if (!mapFournisseurDeducMaxPilier3a.containsKey(annee))  mapFournisseurDeducMaxPilier3a.putIfAbsent(annee,construireFournisseurDeducMaxPilier3a(annee));
        return mapFournisseurDeducMaxPilier3a.get(annee);
    }

    private FournisseurDeductionMaxPilier3a construireFournisseurDeducMaxPilier3a(int annee) {
        FournisseurDeducMax3emePilier fournisseur = new FournisseurDeducMax3emePilier();
        fournisseur.setFournisseurMontantsLimitesPrevoyanceProfessionnelle(getFournisseurMontantsLimitesPrevoyanceProfessionnelle(annee));
        return fournisseur;
    }
}
