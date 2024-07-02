//
// Questo file � stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.0 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andr� persa durante la ricompilazione dello schema di origine. 
// Generato il: 2022.04.12 alle 10:57:37 AM CEST 
//


/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.flusso.emurps.tracciato.bean.output;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.opencsv.bean.CsvBindAndSplitByPosition;
import com.opencsv.bean.CsvRecurse;

import it.mds.sdk.flusso.emurps.parser.regole.split.TextDiagnosiSecondaria;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://flussi.mds.it/flsProSoc}DiagnosiPrincipale" minOccurs="0"/&gt;
 *         &lt;element ref="{http://flussi.mds.it/flsProSoc}DiagnosiSecondaria" maxOccurs="4" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "diagnosiPrincipale",
    "diagnosiSecondaria"
})
@XmlRootElement(name = "Diagnosi")
public class Diagnosi {

    @XmlElement(name = "DiagnosiPrincipale")
    @CsvRecurse
    protected DiagnosiPrincipale diagnosiPrincipale;
    @XmlElement(name = "DiagnosiSecondaria")
    @CsvBindAndSplitByPosition(position=28, elementType = DiagnosiSecondaria.class, converter = TextDiagnosiSecondaria.class)
    protected List<DiagnosiSecondaria> diagnosiSecondaria;

    /**
     * Recupera il valore della propriet� diagnosiPrincipale.
     * 
     * @return
     *     possible object is
     *     {@link DiagnosiPrincipale }
     *     
     */
    public DiagnosiPrincipale getDiagnosiPrincipale() {
        return diagnosiPrincipale;
    }

    /**
     * Imposta il valore della propriet� diagnosiPrincipale.
     * 
     * @param value
     *     allowed object is
     *     {@link DiagnosiPrincipale }
     *     
     */
    public void setDiagnosiPrincipale(DiagnosiPrincipale value) {
//    	if (StringUtils.isBlank(value.getValue()))
//    		value = null;
        this.diagnosiPrincipale = value;
    }

    /**
     * Gets the value of the diagnosiSecondaria property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the diagnosiSecondaria property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDiagnosiSecondaria().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DiagnosiSecondaria }
     * 
     * 
     */
    public List<DiagnosiSecondaria> getDiagnosiSecondaria() {
//        if (diagnosiSecondaria == null) {
//            diagnosiSecondaria = new ArrayList<DiagnosiSecondaria>();
//        }
        return this.diagnosiSecondaria;
    }

	public void setDiagnosiSecondaria(List<DiagnosiSecondaria> diagnosiSecondaria) {
		this.diagnosiSecondaria = diagnosiSecondaria;
	}
    

}
