package ch.ge.afc.util;


import java.math.BigDecimal;

import org.junit.Test;

import static org.junit.Assert.*;


public class TypeArrondiTest {

	@Test
    public void arrondiCinqCentimes() {
        // Arrondi de 0 au 5 centimes le plus proche
        testArrondi(TypeArrondi.CINQ_CTS,"0","0.00");
        // Arrondi de 0.0001253 au 5 centimes le plus proche
        testArrondi(TypeArrondi.CINQ_CTS,"0.0001253","0.00");
        // Arrondi de -0.01 au 5 centimes le plus proche
        testArrondi(TypeArrondi.CINQ_CTS,"-0.01","0.00");
        // Arrondi de 0.25 au 5 centimes le plus proche
        testArrondi(TypeArrondi.CINQ_CTS,"0.25","0.25");
        // Arrondi de -0.25 au 5 centimes le plus proche
        testArrondi(TypeArrondi.CINQ_CTS,"-0.25","-0.25");
        // Arrondi de 1.0 au 5 centimes le plus proche
        testArrondi(TypeArrondi.CINQ_CTS,"1.0","1.00");
        // Arrondi de 1.24356 au 5 centimes le plus proche
        testArrondi(TypeArrondi.CINQ_CTS,"1.24356","1.25");
        // Arrondi de -1.24356 au 5 centimes le plus proche
        testArrondi(TypeArrondi.CINQ_CTS,"-1.24356","-1.25");
        // Arrondi de 2.5 au 5 centimes le plus proche
        testArrondi(TypeArrondi.CINQ_CTS,"2.5","2.50");
        // Arrondi de 10090 au 5 centimes le plus proche
        testArrondi(TypeArrondi.CINQ_CTS,"10090","10090.00");
        // Arrondi de 12875.243 au 5 centimes le plus proche
        testArrondi(TypeArrondi.CINQ_CTS,"12875.243","12875.25");
    }

	@Test
    public void arrondiCinqCentimesSuperieures() {
        // Arrondi de 0 au 5 centimes supérieur
        testArrondi(TypeArrondi.CINQ_CTS_SUP,"0","0.00");
        // Arrondi de 0.0001253 au 5 centimes supérieur
        testArrondi(TypeArrondi.CINQ_CTS_SUP,"0.0001253","0.05");
        // Arrondi de -0.0001253 au 5 centimes supérieur
        testArrondi(TypeArrondi.CINQ_CTS_SUP,"-0.0001253","-0.05");
        // Arrondi de 0.25 au 5 centimes supérieur
        testArrondi(TypeArrondi.CINQ_CTS_SUP,"0.25","0.25");
        // Arrondi de 1.0 au 5 centimes supérieur
        testArrondi(TypeArrondi.CINQ_CTS_SUP,"1.0","1.00");
        // Arrondi de 1.24356 au 5 centimes supérieur
        testArrondi(TypeArrondi.CINQ_CTS_SUP,"1.24356","1.25");
        // Arrondi de 2.5 au 5 centimes supérieur
        testArrondi(TypeArrondi.CINQ_CTS_SUP,"2.5","2.50");
        // Arrondi de 10090 au 5 centimes supérieur
        testArrondi(TypeArrondi.CINQ_CTS_SUP,"10090","10090.00");
        // Arrondi de 12875.243 au 5 centimes supérieur
        testArrondi(TypeArrondi.CINQ_CTS_SUP,"12875.243","12875.25");
    }


	@Test
    public void arrondiCinqCentimesInferieures() {
        // Arrondi de 0 au 5 centimes inférieur
        testArrondi(TypeArrondi.CINQ_CTS_INF,"0","0.00");
        // Arrondi de 0.0001253 au 5 centimes inférieur
        testArrondi(TypeArrondi.CINQ_CTS_INF,"0.0001253","0.00");
        // Arrondi de -0.0001253 au 5 centimes inférieur
        testArrondi(TypeArrondi.CINQ_CTS_INF,"-0.0001253","0.00");
        // Arrondi de 0.25 au 5 centimes inférieur
        testArrondi(TypeArrondi.CINQ_CTS_INF,"0.25","0.25");
        // Arrondi de 1.0 au 5 centimes inférieur
        testArrondi(TypeArrondi.CINQ_CTS_INF,"1.0","1.00");
        // Arrondi de 1.24356 au 5 centimes inférieur
        testArrondi(TypeArrondi.CINQ_CTS_INF,"1.24356","1.20");
        // Arrondi de -1.24356 au 5 centimes inférieur
        testArrondi(TypeArrondi.CINQ_CTS_INF,"-1.24356","-1.20");
        // Arrondi de 2.5 au 5 centimes inférieur
        testArrondi(TypeArrondi.CINQ_CTS_INF,"2.5","2.50");
        // Arrondi de 10090 au 5 centimes inférieur
        testArrondi(TypeArrondi.CINQ_CTS_INF,"10090","10090.00");
        // Arrondi de 12875.243 au 5 centimes inférieur
        testArrondi(TypeArrondi.CINQ_CTS_INF,"12875.243","12875.20");
    }

	@Test
    public void arrondiDixCentimes() {
        // Arrondi de 0 au 10 centimes le plus proche
        testArrondi(TypeArrondi.DIX_CTS,"0","0.00");
        // Arrondi de 0.0001253 au 10 centimes le plus proche
        testArrondi(TypeArrondi.DIX_CTS,"0.0001253","0.00");
        // Arrondi de 0.25 au 10 centimes le plus proche
        testArrondi(TypeArrondi.DIX_CTS,"0.25","0.30");
        // Arrondi de 1.0 au 10 centimes le plus proche
        testArrondi(TypeArrondi.DIX_CTS,"1.0","1.00");
        // Arrondi de 1.24356 au 10 centimes le plus proche
        testArrondi(TypeArrondi.DIX_CTS,"1.24356","1.20");
        // Arrondi de 2.5 au 10 centimes le plus proche
        testArrondi(TypeArrondi.DIX_CTS,"2.5","2.50");
        // Arrondi de 10090 au 10 centimes le plus proche
        testArrondi(TypeArrondi.DIX_CTS,"10090","10090.00");
        // Arrondi de 12875.243 au 10 centimes le plus proche
        testArrondi(TypeArrondi.DIX_CTS,"12875.243","12875.20");
    }

	@Test
    public void arrondiDixCentimesSuperieures() {
        // Arrondi de 0 au 10 centimes supérieur
        testArrondi(TypeArrondi.DIX_CTS_SUP,"0","0.00");
        // Arrondi de 0.0001253 au 10 centimes supérieur
        testArrondi(TypeArrondi.DIX_CTS_SUP,"0.0001253","0.10");
        // Arrondi de 0.25 au 10 centimes supérieur
        testArrondi(TypeArrondi.DIX_CTS_SUP,"0.25","0.30");
        // Arrondi de 1.0 au 10 centimes supérieur
        testArrondi(TypeArrondi.DIX_CTS_SUP,"1.0","1.00");
        // Arrondi de 1.24356 au 10 centimes supérieur
        testArrondi(TypeArrondi.DIX_CTS_SUP,"1.24356","1.30");
        // Arrondi de 2.5 au 10 centimes supérieur
        testArrondi(TypeArrondi.DIX_CTS_SUP,"2.5","2.50");
        // Arrondi de 10090 au 10 centimes supérieur
        testArrondi(TypeArrondi.DIX_CTS_SUP,"10090","10090.00");
        // Arrondi de 12875.243 au 10 centimes supérieur
        testArrondi(TypeArrondi.DIX_CTS_SUP,"12875.243","12875.30");
    }
    
    private void testArrondi(TypeArrondi inoTypeArrondi,String inoValeurAArrondi, String inoValeurAttendue) {
       assertEquals(new BigDecimal(inoValeurAttendue),inoTypeArrondi.arrondirMontant(new BigDecimal(inoValeurAArrondi)));
    }

}
