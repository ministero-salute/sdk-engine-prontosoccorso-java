//
// Questo file � stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.0 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andr� persa durante la ricompilazione dello schema di origine. 
// Generato il: 2022.04.12 alle 10:57:37 AM CEST 
//


/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.flusso.emurps.tracciato.bean.output;

import org.apache.commons.lang3.StringUtils;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvRecurse;

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
 *       &lt;choice&gt;
 *         &lt;element ref="{http://flussi.mds.it/flsProSoc}Nascita"/&gt;
 *         &lt;element name="Presunta" type="{http://flussi.mds.it/flsProSoc}etaPresunta"/&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "nascita",
    "presunta"
})
@XmlRootElement(name = "Eta")
public class Eta {

    @XmlElement(name = "Nascita")
    @CsvRecurse
    protected Nascita nascita;
    @XmlElement(name = "Presunta")
    @CsvBindByPosition(position = 18)
    protected String presunta;

    /**
     * Recupera il valore della propriet� nascita.
     * 
     * @return
     *     possible object is
     *     {@link Nascita }
     *     
     */
    public Nascita getNascita() {
        return nascita;
    }

    /**
     * Imposta il valore della propriet� nascita.
     * 
     * @param value
     *     allowed object is
     *     {@link Nascita }
     *     
     */
    public void setNascita(Nascita value) {      	 
        this.nascita = value;
    }

    /**
     * Recupera il valore della propriet� presunta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPresunta() {
        return presunta;
    }

    /**
     * Imposta il valore della propriet� presunta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPresunta(String value) {
    	if (StringUtils.isBlank(value))
    		value = null;
        this.presunta = value;
    }

}
