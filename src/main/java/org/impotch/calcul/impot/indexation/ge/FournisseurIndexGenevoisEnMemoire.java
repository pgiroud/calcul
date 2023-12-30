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
package org.impotch.calcul.impot.indexation.ge;

import org.impotch.calcul.impot.indexation.FournisseurIndicePeriodique;
import org.impotch.calcul.impot.indexation.Indexateur;
import org.impotch.calcul.impot.indexation.IndexateurPeriodique;
import org.impotch.calcul.impot.indexation.SimpleFournisseurIndicePeriodique;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.impotch.calcul.impot.indexation.IndexateurPeriodique.unConstructeurIndexateurQuadriAnnuel;

public class FournisseurIndexGenevoisEnMemoire implements FournisseurIndexGenevois {

    final static Logger logger = LoggerFactory.getLogger(FournisseurIndexGenevoisEnMemoire.class);

    private final FournisseurIndicePeriodique fournisseurIndiceGEBaseMai1993;
    private final FournisseurIndicePeriodique fournisseurIndiceGEBaseDecembre2005;


    public FournisseurIndexGenevoisEnMemoire() {
        fournisseurIndiceGEBaseMai1993 = construireFournisseurIndiceGEBaseMai1993();
        fournisseurIndiceGEBaseDecembre2005 = construireFournisseurIndiceGEBaseDecembre2005();
    }

    private SimpleFournisseurIndicePeriodique construireFournisseurIndiceGEBaseMai1993() {
        return new SimpleFournisseurIndicePeriodique.Constructeur()
                .pour(1993).valeur("100.0")
                .pour(1995).valeur("100.9")
                .pour(1996).valeur("100.9")
                .pour(1997).valeur("100.9")
                .pour(1998).valeur("104.3")
                .pour(1999).valeur("105.3")
                .pour(2000).valeur("105.8")
                .pour(2001).valeur("107.3")
                .pour(2002).valeur("108.7")
                .pour(2003).valeur("109.4")
                .pour(2004).valeur("110.5")
                .pour(2005).valeur("111.5")
                .pour(2006).valeur("113.2")
                .pour(2007).valeur("114.6")
                .pour(2008).valeur("114.9")
                .pour(2009).valeur("117.5")
                .cons();
    }

    private SimpleFournisseurIndicePeriodique construireFournisseurIndiceGEBaseDecembre2005() {
        return new SimpleFournisseurIndicePeriodique.Constructeur()
                .pour(2009).valeur("102.9")
                .pour(2010).valeur("103.1")
                .pour(2011).valeur("103.7")
                .pour(2012).valeur("104.2")
                .pour(2013).valeur("103.7")
                .pour(2014).valeur("103.8")
                .pour(2015).valeur("103.9")
                .pour(2016).valeur("103.2")
                .pour(2017).valeur("102.7")
                .pour(2018).valeur("103.0")
                .pour(2019).valeur("104.2")
                .pour(2020).valeur("105.0")
                .pour(2021).valeur("104.4")
                .pour(2022).valeur("104.1")
                .pour(2023).valeur("106.2")
                .pour(2024).valeur("108.7")
                .cons();
    }

    @Override
    public FournisseurIndicePeriodique getFournisseurIndiceGEBaseMai1993() {
        return fournisseurIndiceGEBaseMai1993;
    }

     @Override
    public FournisseurIndicePeriodique getFournisseurIndiceGEBaseDecembre2005() {
        return fournisseurIndiceGEBaseDecembre2005;
    }

    @Override
    public Indexateur getIndexateurQuadriennalBaseMai1993(int anneeBase) {
        return unConstructeurIndexateurQuadriAnnuel(2001)
                .anneeBase(anneeBase)
                .fournisseurIndice(getFournisseurIndiceGEBaseMai1993()).cons();
    }

    @Override
    public Indexateur getIndexateurQuadriennalBaseDecembre2005(int anneeBase) {
        return unConstructeurIndexateurQuadriAnnuel(2009)
                .anneeBase(anneeBase)
                .fournisseurIndice(getFournisseurIndiceGEBaseDecembre2005()).cons();
    }

}
