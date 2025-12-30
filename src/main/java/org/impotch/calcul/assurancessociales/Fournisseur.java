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
package org.impotch.calcul.assurancessociales;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.impotch.calcul.assurancessociales.ge.CalculCotisationsSocialesSalarieGE;

import org.impotch.calcul.assurancessociales.ge.param.FournisseurParametrageAnnuelAssurancesSocialesGenevoises;
import org.impotch.calcul.assurancessociales.ge.param.ParametrageAnnuelCotisationsSocialesGenevoises;

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

    private final FournisseurParametrageAnnuelAssurancesSocialesGenevoises fournisseurParametrage;

	private ConcurrentMap<Integer,CalculCotisationsSocialesSalarie> mapCalculateurCotisationAvsAiApgSalarie = new ConcurrentHashMap<Integer,CalculCotisationsSocialesSalarie>();
	private ConcurrentMap<Integer,CalculCotisationsSocialesSalarieGE> mapCalculateurCotisationAvsAiApgSalarieGE = new ConcurrentHashMap<Integer,CalculCotisationsSocialesSalarieGE>();
    private ConcurrentMap<Integer,CalculCotisationsSocialesSalarieGE> mapCalculateurCotisationAvsAiApgSalarieGEIFD = new ConcurrentHashMap<Integer,CalculCotisationsSocialesSalarieGE>();
    private ConcurrentMap<Integer,FournisseurMontantsLimitesPrevoyanceProfessionnelle> mapFournisseurMontantsLimitesPrevoyanceProfessionnelle = new ConcurrentHashMap<Integer, FournisseurMontantsLimitesPrevoyanceProfessionnelle>();
    private ConcurrentMap<Integer,FournisseurDeductionMaxPilier3a> mapFournisseurDeducMaxPilier3a = new ConcurrentHashMap<Integer, FournisseurDeductionMaxPilier3a>();

	private ConcurrentMap<Integer,CalculCotisationAvsAiApg> mapCalculateurCotisationAvsAiApgIndependant = new ConcurrentHashMap<Integer,CalculCotisationAvsAiApg>();
	
	private ConcurrentMap<Integer,CalculExtremaRentesAVS> mapCalculateurRentesAVS = new ConcurrentHashMap<Integer,CalculExtremaRentesAVS>();


    public Fournisseur(FournisseurParametrageAnnuelAssurancesSocialesGenevoises fournisseurParametrage) {
        this.fournisseurParametrage = fournisseurParametrage;
    }

    /**************************************************/
    /******************* Méthodes *********************/
    /**************************************************/

	//--------------- Salariés --------------------


    private CalculCotisationsSocialesSalarie.Constructeur obtenirNouveauConstructeur(int annee) {
        ParametrageAnnuelCotisationsSocialesGenevoises parametrage = fournisseurParametrage.parametrage(annee).orElseThrow();
        return new CalculCotisationsSocialesSalarie.Constructeur(parametrage);
    }

	protected CalculCotisationsSocialesSalarie construireCalculateurSalarie(int annee) {
		return obtenirNouveauConstructeur(annee).construire(annee);
	}


    @Override
	public CalculCotisationsSocialesSalarie getCalculateurCotisationsSocialesSalarie(int annee) {
		if (!mapCalculateurCotisationAvsAiApgSalarie.containsKey(annee)) mapCalculateurCotisationAvsAiApgSalarie.putIfAbsent(annee, construireCalculateurSalarie(annee));
		return mapCalculateurCotisationAvsAiApgSalarie.get(annee);
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
        if (fournisseurParametrage.parametrage(annee).isPresent()) {
            BigDecimal tauxAssuranceMaternite = new BigDecimal(fournisseurParametrage.parametrage(annee).get().tauxAssuranceMaternite());
            return construireCalculateurSalarie(annee,tauxAssuranceMaternite,getCalculateurCotisationsSocialesSalarie(annee));
        }
        throw new IllegalArgumentException("Le paramétrage n’existe pas pour l’année " + annee);
	}

    protected CalculCotisationsSocialesSalarieGE construireCalculateurSalarieGEIFD(int annee) {
        if (fournisseurParametrage.parametrage(annee).isPresent()) {
            BigDecimal tauxAssuranceMaternite = new BigDecimal(fournisseurParametrage.parametrage(annee).get().tauxAssuranceMaternite());
            return construireCalculateurSalarie(annee,tauxAssuranceMaternite, getOldCalculateurCotisationsSocialesSalarieISIFD(annee));
        }
        throw new IllegalArgumentException("Le paramétrage n’existe pas pour l’année " + annee);
    }

    @Override
	public CalculCotisationsSocialesSalarieGE getCalculateurCotisationsSocialesSalarieGE(int annee) {
		if (!mapCalculateurCotisationAvsAiApgSalarieGE.containsKey(annee)) mapCalculateurCotisationAvsAiApgSalarieGE.putIfAbsent(annee, construireCalculateurSalarieGE(annee));
		return mapCalculateurCotisationAvsAiApgSalarieGE.get(annee);
	}
	
    public CalculCotisationsSocialesSalarieGE getOldCalculateurCotisationsSocialesSalarieGEIFD(int annee) {
        if (!mapCalculateurCotisationAvsAiApgSalarieGEIFD.containsKey(annee)) mapCalculateurCotisationAvsAiApgSalarieGEIFD.putIfAbsent(annee, construireCalculateurSalarieGEIFD(annee));
        return mapCalculateurCotisationAvsAiApgSalarieGEIFD.get(annee);
    }

    @Override
    public CalculCotisationsSocialesSalarie getOldCalculateurCotisationsSocialesSalarieISIFD(int annee) {
        return null;
    }

//--------------- Indépendants --------------------

	protected CalculCotisationAvsAiApg construireCalculateurCotisationAvsAiApgIndependant(int annee) {
//		if (annee < 2009) {
//            CalculCotisationAvsAiApgIndependant.Constructeur constructeur = new CalculCotisationAvsAiApgIndependant.Constructeur();
//            constructeur.tauxAvs("7.8 %").tauxAi("1.4 %").tauxApg("0.3 %");
//			constructeur.cotisationAvsAiApgMinimum("445");
//			constructeur.trancheBareme( "8900", "4.2 %");
//			constructeur.trancheBareme("15900", "4.3 %");
//			constructeur.trancheBareme("20100", "4.4 %");
//			constructeur.trancheBareme("22300", "4.5 %");
//			constructeur.trancheBareme("24500", "4.6 %");
//			constructeur.trancheBareme("26700", "4.7 %");
//			constructeur.trancheBareme("28900", "4.9 %");
//			constructeur.trancheBareme("31100", "5.1 %");
//			constructeur.trancheBareme("33300", "5.3 %");
//			constructeur.trancheBareme("35500", "5.5 %");
//			constructeur.trancheBareme("37700", "5.7 %");
//			constructeur.trancheBareme("39900", "5.9 %");
//			constructeur.trancheBareme("42100", "6.2 %");
//			constructeur.trancheBareme("44300", "6.5 %");
//			constructeur.trancheBareme("46500", "6.8 %");
//			constructeur.trancheBareme("48700", "7.1 %");
//			constructeur.trancheBareme("50900", "7.4 %");
//			constructeur.trancheBareme("53100", "7.8 %");
//			return constructeur.construire(annee);
//
//		} else if (annee < 2011) {
//            CalculCotisationAvsAiApgIndependant.Constructeur constructeur = new CalculCotisationAvsAiApgIndependant.Constructeur();
//            constructeur.tauxAvs("7.8 %").tauxAi("1.4 %").tauxApg("0.3 %");
//			constructeur.cotisationAvsAiApgMinimum("460");
//			constructeur.trancheBareme( "9200", "4.2 %");
//			constructeur.trancheBareme("16000", "4.3 %");
//			constructeur.trancheBareme("20300", "4.4 %");
//			constructeur.trancheBareme("22600", "4.5 %");
//			constructeur.trancheBareme("24900", "4.6 %");
//			constructeur.trancheBareme("27200", "4.7 %");
//			constructeur.trancheBareme("29500", "4.9 %");
//			constructeur.trancheBareme("31800", "5.1 %");
//			constructeur.trancheBareme("34100", "5.3 %");
//			constructeur.trancheBareme("36400", "5.5 %");
//			constructeur.trancheBareme("38700", "5.7 %");
//			constructeur.trancheBareme("41000", "5.9 %");
//			constructeur.trancheBareme("43300", "6.2 %");
//			constructeur.trancheBareme("45600", "6.5 %");
//			constructeur.trancheBareme("47900", "6.8 %");
//			constructeur.trancheBareme("50200", "7.1 %");
//			constructeur.trancheBareme("52500", "7.4 %");
//			constructeur.trancheBareme("54800", "7.8 %");
//			return constructeur.construire(annee);
//		} else if (annee < 2013) {
//            CalculCotisationAvsAiApgIndependant.Constructeur constructeur = new CalculCotisationAvsAiApgIndependant.Constructeur();
//            constructeur.tauxAvs("7.8 %").tauxAi("1.4 %").tauxApg("0.5 %");
//            constructeur.cotisationAvsAiApgMinimum("475");
//            constructeur.trancheBareme( "9300", "4.2 %");
//            constructeur.trancheBareme("16900", "4.3 %");
//            constructeur.trancheBareme("21200", "4.4 %");
//            constructeur.trancheBareme("23500", "4.5 %");
//            constructeur.trancheBareme("25800", "4.6 %");
//            constructeur.trancheBareme("28100", "4.7 %");
//            constructeur.trancheBareme("30400", "4.9 %");
//            constructeur.trancheBareme("32700", "5.1 %");
//            constructeur.trancheBareme("35000", "5.3 %");
//            constructeur.trancheBareme("37300", "5.5 %");
//            constructeur.trancheBareme("39600", "5.7 %");
//            constructeur.trancheBareme("41900", "5.9 %");
//            constructeur.trancheBareme("44200", "6.2 %");
//            constructeur.trancheBareme("46500", "6.5 %");
//            constructeur.trancheBareme("48800", "6.8 %");
//            constructeur.trancheBareme("51100", "7.1 %");
//            constructeur.trancheBareme("53400", "7.4 %");
//            constructeur.trancheBareme("55700", "7.8 %");
//            return constructeur.construire(annee);
//        } else if (annee < 2015) {
//            CalculCotisationAvsAiApgIndependant.Constructeur constructeur = new CalculCotisationAvsAiApgIndependant.Constructeur();
//            constructeur.tauxAvs("7.8 %").tauxAi("1.4 %").tauxApg("0.5 %");
//            constructeur.cotisationAvsAiApgMinimum("480");
//            constructeur.trancheBareme( "9400", "4.2 %");
//            constructeur.trancheBareme("17200", "4.3 %");
//            constructeur.trancheBareme("21700", "4.4 %");
//            constructeur.trancheBareme("24000", "4.5 %");
//            constructeur.trancheBareme("26300", "4.6 %");
//            constructeur.trancheBareme("28600", "4.7 %");
//            constructeur.trancheBareme("30900", "4.9 %");
//            constructeur.trancheBareme("33200", "5.1 %");
//            constructeur.trancheBareme("35500", "5.3 %");
//            constructeur.trancheBareme("37800", "5.5 %");
//            constructeur.trancheBareme("40100", "5.7 %");
//            constructeur.trancheBareme("42400", "5.9 %");
//            constructeur.trancheBareme("44700", "6.2 %");
//            constructeur.trancheBareme("47000", "6.5 %");
//            constructeur.trancheBareme("49300", "6.8 %");
//            constructeur.trancheBareme("51600", "7.1 %");
//            constructeur.trancheBareme("53900", "7.4 %");
//            constructeur.trancheBareme("56200", "7.8 %");
//            return constructeur.construire(annee);
//        } else if (annee < 2016) {
//            CalculCotisationAvsAiApgIndependant.Constructeur constructeur = new CalculCotisationAvsAiApgIndependant.Constructeur();
//            constructeur.tauxAvs("7.8 %").tauxAi("1.4 %").tauxApg("0.5 %");
//            constructeur.cotisationAvsAiApgMinimum("480");
//            constructeur.trancheBareme( "9400", "4.2 %");
//            constructeur.trancheBareme("17200", "4.3 %");
//            constructeur.trancheBareme("21900", "4.4 %");
//            constructeur.trancheBareme("24200", "4.5 %");
//            constructeur.trancheBareme("26500", "4.6 %");
//            constructeur.trancheBareme("28800", "4.7 %");
//            constructeur.trancheBareme("31100", "4.9 %");
//            constructeur.trancheBareme("33400", "5.1 %");
//            constructeur.trancheBareme("35700", "5.3 %");
//            constructeur.trancheBareme("38000", "5.5 %");
//            constructeur.trancheBareme("40300", "5.7 %");
//            constructeur.trancheBareme("42600", "5.9 %");
//            constructeur.trancheBareme("44900", "6.2 %");
//            constructeur.trancheBareme("47200", "6.5 %");
//            constructeur.trancheBareme("49500", "6.8 %");
//            constructeur.trancheBareme("51800", "7.1 %");
//            constructeur.trancheBareme("54100", "7.4 %");
//            constructeur.trancheBareme("56400", "7.8 %");
//            return constructeur.construire(annee);
//        } else if (annee < 2019) {
//            CalculCotisationAvsAiApgIndependant.Constructeur constructeur = new CalculCotisationAvsAiApgIndependant.Constructeur();
//            constructeur.tauxAvs("7.8 %").tauxAi("1.4 %").tauxApg("0.45 %");
//            constructeur.cotisationAvsAiApgMinimum("478");
//            constructeur.trancheBareme( "9400", "4.2 %");
//            constructeur.trancheBareme("17200", "4.3 %");
//            constructeur.trancheBareme("21900", "4.4 %");
//            constructeur.trancheBareme("24200", "4.5 %");
//            constructeur.trancheBareme("26500", "4.6 %");
//            constructeur.trancheBareme("28800", "4.7 %");
//            constructeur.trancheBareme("31100", "4.9 %");
//            constructeur.trancheBareme("33400", "5.1 %");
//            constructeur.trancheBareme("35700", "5.3 %");
//            constructeur.trancheBareme("38000", "5.5 %");
//            constructeur.trancheBareme("40300", "5.7 %");
//            constructeur.trancheBareme("42600", "5.9 %");
//            constructeur.trancheBareme("44900", "6.2 %");
//            constructeur.trancheBareme("47200", "6.5 %");
//            constructeur.trancheBareme("49500", "6.8 %");
//            constructeur.trancheBareme("51800", "7.1 %");
//            constructeur.trancheBareme("54100", "7.4 %");
//            constructeur.trancheBareme("56400", "7.8 %");
//            return constructeur.construire(annee);
//        } else if (annee < 2020) {
//            CalculCotisationAvsAiApgIndependant.Constructeur constructeur = new CalculCotisationAvsAiApgIndependant.Constructeur();
//            constructeur.tauxAvs("7.8 %").tauxAi("1.4 %").tauxApg("0.45 %");
//            constructeur.cotisationAvsAiApgMinimum("482");
//            constructeur.trancheBareme( "9500", "4.2 %");
//            constructeur.trancheBareme("17300", "4.3 %");
//            constructeur.trancheBareme("20900", "4.4 %");
//            constructeur.trancheBareme("23300", "4.5 %");
//            constructeur.trancheBareme("25700", "4.6 %");
//            constructeur.trancheBareme("28100", "4.7 %");
//            constructeur.trancheBareme("30500", "4.9 %");
//            constructeur.trancheBareme("32900", "5.1 %");
//            constructeur.trancheBareme("35300", "5.3 %");
//            constructeur.trancheBareme("37700", "5.5 %");
//            constructeur.trancheBareme("40100", "5.7 %");
//            constructeur.trancheBareme("42500", "5.9 %");
//            constructeur.trancheBareme("44900", "6.2 %");
//            constructeur.trancheBareme("47300", "6.5 %");
//            constructeur.trancheBareme("49700", "6.8 %");
//            constructeur.trancheBareme("52100", "7.1 %");
//            constructeur.trancheBareme("54500", "7.4 %");
//            constructeur.trancheBareme("56900", "7.8 %");
//            return constructeur.construire(annee);
//        } else if (annee < 2021) {
//            return null;
//        } else if (annee < 2021) {
//            return null;
//        } else if (annee < 2021) {
//            return null;
//        } else if (annee < 2025) {
//            return null;
//        } else if (annee == 2025) {
//            // Article 21 du RAVS : https://www.fedlex.admin.ch/eli/cc/63/1185_1183_1185/fr#a21
//            CalculCotisationAvsAiApgIndependant.Constructeur constructeur = new CalculCotisationAvsAiApgIndependant.Constructeur();
//            constructeur.tauxAvs("8.1 %").tauxAi("1.4 %").tauxApg("0.5 %");
//            constructeur.cotisationAvsAiApgMinimum("530");
//            constructeur.trancheBareme( "10100", "4.35 %");
//            constructeur.trancheBareme("17600", "4.45 %");
//            constructeur.trancheBareme("23000", "4.55 %");
//            constructeur.trancheBareme("25500", "4.65 %");
//            constructeur.trancheBareme("28000", "4.75 %");
//            constructeur.trancheBareme("30500", "4.85 %");
//            constructeur.trancheBareme("33000", "5.05 %");
//            constructeur.trancheBareme("35500", "5.25 %");
//            constructeur.trancheBareme("38000", "5.45 %");
//            constructeur.trancheBareme("40500", "5.65 %");
//            constructeur.trancheBareme("43000", "5.85 %");
//            constructeur.trancheBareme("45500", "6.05 %");
//            constructeur.trancheBareme("48000", "6.35 %");
//            constructeur.trancheBareme("50500", "6.65 %");
//            constructeur.trancheBareme("53000", "6.95 %");
//            constructeur.trancheBareme("55500", "7.25 %");
//            constructeur.trancheBareme("58000", "7.55 %");
//            constructeur.trancheBareme("60500", "8.1 %");
//            return constructeur.construire(annee);
//        } else if (annee == 2026) {
//            CalculCotisationAvsAiApgIndependant.Constructeur constructeur = new CalculCotisationAvsAiApgIndependant.Constructeur();
//            constructeur.tauxAvs("8.1 %").tauxAi("1.4 %").tauxApg("0.5 %");
//            constructeur.cotisationAvsAiApgMinimum("530");
//            constructeur.trancheBareme( "9500", "4.2 %");
//            constructeur.trancheBareme("17300", "4.2 %");
//            constructeur.trancheBareme("20900", "4.3 %");
//            constructeur.trancheBareme("23300", "4.4 %");
//            constructeur.trancheBareme("25700", "4.5 %");
//            constructeur.trancheBareme("28100", "4.6 %");
//            constructeur.trancheBareme("30500", "4.7 %");
//            constructeur.trancheBareme("32900", "4.9 %");
//            constructeur.trancheBareme("35300", "5.1 %");
//            constructeur.trancheBareme("37700", "5.3 %");
//            constructeur.trancheBareme("40100", "5.5 %");
//            constructeur.trancheBareme("42500", "5.7 %");
//            constructeur.trancheBareme("44900", "5.9 %");
//            constructeur.trancheBareme("47300", "6.2 %");
//            constructeur.trancheBareme("49700", "6.5 %");
//            constructeur.trancheBareme("52100", "6.8 %");
//            constructeur.trancheBareme("54500", "7.1 %");
//            constructeur.trancheBareme("56900", "7.4 %");
//            return constructeur.construire(annee);
//        }
        if (2008 == annee) {
            return new CalculCotisationAvsAiApgIndependant.Constructeur()
                    .avs().cotisationMinimale(370) // Voir art. 2 al. 2 de l’O 07 du 22 sept. 2006 – RS 831.108
                    .jusqua(     8_900).taux("0")
                    .puisJusqua(15_900).taux("4.2 %")
                    .puisJusqua(20_100).taux("4.3 %")
                    .puisJusqua(22_300).taux("4.4 %")
                    .puisJusqua(24_500).taux("4.5 %")
                    .puisJusqua(26_700).taux("4.6 %")
                    .puisJusqua(28_900).taux("4.7 %")
                    .puisJusqua(31_100).taux("4.9 %")
                    .puisJusqua(33_300).taux("5.1 %")
                    .puisJusqua(35_500).taux("5.3 %")
                    .puisJusqua(37_700).taux("5.5 %")
                    .puisJusqua(39_900).taux("5.7 %")
                    .puisJusqua(42_100).taux("5.9 %")
                    .puisJusqua(44_300).taux("6.2 %")
                    .puisJusqua(46_500).taux("6.5 %")
                    .puisJusqua(48_700).taux("6.8 %")
                    .puisJusqua(50_900).taux("7.1 %")
                    .puisJusqua(53_100).taux("7.4 %")
                    .puis()                            .taux("7.8 %")


                    .ai()
                        .cotisationMinimale(62) // Voir article 6 https://www.fedlex.admin.ch/eli/cc/2006/611/fr
                        .taux("1.4 %")
                    .apg()
                        .cotisationMinimale(13) // Voir article 7 https://www.fedlex.admin.ch/eli/cc/2006/611/fr
                        .taux("0.3 %")
                    .construire(annee);
        }
        if (2025 == annee) {
            // Article 21 du RAVS : https://www.fedlex.admin.ch/eli/cc/63/1185_1183_1185/fr#a21
            return new CalculCotisationAvsAiApgIndependant.Constructeur()
                    .avs().cotisationMinimale(435)
                        .jusqua(    10_100).taux("0")
                        .puisJusqua(17_600).taux("4.35 %")
                        .puisJusqua(23_000).taux("4.45 %")
                        .puisJusqua(25_500).taux("4.55 %")
                        .puisJusqua(28_000).taux("4.65 %")
                        .puisJusqua(30_500).taux("4.75 %")
                        .puisJusqua(33_000).taux("4.85 %")
                        .puisJusqua(35_500).taux("5.05 %")
                        .puisJusqua(38_000).taux("5.25 %")
                        .puisJusqua(40_500).taux("5.45 %")
                        .puisJusqua(43_000).taux("5.65 %")
                        .puisJusqua(45_500).taux("5.85 %")
                        .puisJusqua(48_000).taux("6.05 %")
                        .puisJusqua(50_500).taux("6.35 %")
                        .puisJusqua(53_000).taux("6.65 %")
                        .puisJusqua(55_500).taux("6.95 %")
                        .puisJusqua(58_000).taux("7.25 %")
                        .puisJusqua(60_500).taux("7.55 %")
                        .puis()                           .taux("8.1 %")

                    .ai()
                        .cotisationMinimale(70) // Voir article 6 de l’ Ordonnance sur les adaptations à l’évolution des salaires et des prix dans le régime de l’AVS, de l’AI et des APG à partir de 2025 https://www.fedlex.admin.ch/eli/oc/2024/463/fr#art_6
                        .taux("1.4 %")
                    .apg()
                        .cotisationMinimale(25) // Voir article 9 de l’ Ordonnance sur les adaptations à l’évolution des salaires et des prix dans le régime de l’AVS, de l’AI et des APG à partir de 2025 https://www.fedlex.admin.ch/eli/oc/2024/463/fr#art_9
                        .taux("0.5 %")
                .construire(annee);
        } else {
            throw new IllegalArgumentException("Le barème des cotisations AVS indépendants n'est pas connu pour l'année " + annee);
        }
	}
	
	
	public CalculCotisationAvsAiApg getCalculateurCotisationAvsAiApgIndependant(int annee) {
		if (!mapCalculateurCotisationAvsAiApgIndependant.containsKey(annee)) mapCalculateurCotisationAvsAiApgIndependant.putIfAbsent(annee, construireCalculateurCotisationAvsAiApgIndependant(annee));
		return mapCalculateurCotisationAvsAiApgIndependant.get(annee);
	}
	
	// --------- Calculateur des rentes minimales ou maximales


	public CalculExtremaRentesAVS getCalculateurExtremaRenteAVS(int annee) {
		if (!mapCalculateurRentesAVS.containsKey(annee)) mapCalculateurRentesAVS.putIfAbsent(annee, construireCalculateurExtremaRenteAVS(annee));
		return mapCalculateurRentesAVS.get(annee);
	}

    private BigDecimal getRenteAVSMensuelleMinimale(int annee) {
        ParametrageAnnuelCotisationsSocialesGenevoises parametrage = fournisseurParametrage.parametrage(annee).orElseThrow();
        return BigDecimal.valueOf(parametrage.renteMensuelleMinimum());
    }

	private CalculExtremaRentesAVS construireCalculateurExtremaRenteAVS(int annee) {
		CalculExtremaRentesAVS calculateur = new CalculExtremaRentesAVS(annee);
		calculateur.setRenteSimpleMensuelleMinimum(getRenteAVSMensuelleMinimale(annee));
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
        fournisseur.setRenteAVSMensuelleMinimale(getRenteAVSMensuelleMinimale(annee));
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
