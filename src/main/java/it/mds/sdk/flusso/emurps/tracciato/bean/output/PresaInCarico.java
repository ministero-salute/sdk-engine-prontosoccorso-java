//
// Questo file � stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.0 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andr� persa durante la ricompilazione dello schema di origine. 
// Generato il: 2022.04.12 alle 10:57:37 AM CEST 
//


/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.flusso.emurps.tracciato.bean.output;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
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
 *         &lt;element name="Data" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="Ora" type="{http://flussi.mds.it/flsProSoc}ora"/&gt;
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
    "data",
    "ora"
})
@XmlRootElement(name = "PresaInCarico")
public class PresaInCarico {

    @XmlElement(name = "Data", required = true)
    @XmlSchemaType(name = "date")
    @CsvBindByPosition(position = 25)
    @CsvDate("yyyy-MM-dd")
    protected Date data;
    @XmlElement(name = "Ora", required = true)
    @CsvBindByPosition(position = 26)
    protected String ora;

    /**
     * Recupera il valore della propriet� data.
     * 
     * @return
     *     possible object is
     *     {@link Date }
     *     
     */
    public Date getData() {
        return data;
    }

    /**
     * Imposta il valore della propriet� data.
     * 
     * @param value
     *     allowed object is
     *     {@link Date }
     *     
     */
    public void setData(Date value) {    	
        this.data = value;
    }

    /**
     * Recupera il valore della propriet� ora.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOra() {
        return ora;
    }

    /**
     * Imposta il valore della propriet� ora.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOra(String value) {
    	if (StringUtils.isEmpty(value))
    		value = null;
        this.ora = value;
    }

}
