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
 *         &lt;element name="Regione" type="{http://flussi.mds.it/flsProSoc}regione"/&gt;
 *         &lt;element name="Comune" type="{http://flussi.mds.it/flsProSoc}comune"/&gt;
 *         &lt;element name="ASL" type="{http://flussi.mds.it/flsProSoc}asl" minOccurs="0"/&gt;
 *         &lt;element name="StatoEstero" type="{http://flussi.mds.it/flsProSoc}stato" minOccurs="0"/&gt;
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
    "regione",
    "comune",
    "asl",
    "statoEstero"
})
@XmlRootElement(name = "Residenza")
public class Residenza {

    @XmlElement(name = "Regione", required = true)
    @CsvBindByPosition(position = 21)
    protected String regione;
    @XmlElement(name = "Comune", required = true)
    @CsvBindByPosition(position = 22)
    protected String comune;
    @XmlElement(name = "ASL")
    @CsvBindByPosition(position = 23)
    protected String asl;
    @XmlElement(name = "StatoEstero")
    @CsvBindByPosition(position = 24)
    protected String statoEstero;

    /**
     * Recupera il valore della propriet� regione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegione() {
        return regione;
    }

    /**
     * Imposta il valore della propriet� regione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegione(String value) {
        this.regione = value;
    }

    /**
     * Recupera il valore della propriet� comune.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComune() {
        return comune;
    }

    /**
     * Imposta il valore della propriet� comune.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComune(String value) {
        this.comune = value;
    }

    /**
     * Recupera il valore della propriet� asl.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAsl() {
        return asl;
    }

    /**
     * Imposta il valore della propriet� asl.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAsl(String value) {
    	if (StringUtils.isBlank(value))
    		value = null;
        this.asl = value;
    }

    /**
     * Recupera il valore della propriet� statoEstero.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatoEstero() {
        return statoEstero;
    }

    /**
     * Imposta il valore della propriet� statoEstero.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatoEstero(String value) {
    	if (StringUtils.isBlank(value))
    		value = null;
        this.statoEstero = value;
    }

}
