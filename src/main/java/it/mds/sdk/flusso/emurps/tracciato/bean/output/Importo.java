//
// Questo file � stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.0 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andr� persa durante la ricompilazione dello schema di origine. 
// Generato il: 2022.04.12 alle 10:57:37 AM CEST 
//


/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.flusso.emurps.tracciato.bean.output;

import java.math.BigDecimal;

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
 *         &lt;element name="RegimeErogazione" type="{http://flussi.mds.it/flsProSoc}regimeErogazione" minOccurs="0"/&gt;
 *         &lt;element name="Lordo" type="{http://flussi.mds.it/flsProSoc}importo" minOccurs="0"/&gt;
 *         &lt;element name="Ticket" type="{http://flussi.mds.it/flsProSoc}importo" minOccurs="0"/&gt;
 *         &lt;element name="PosizioneAssistitoTicket" type="{http://flussi.mds.it/flsProSoc}posAssistitoTicket" minOccurs="0"/&gt;
 *         &lt;element name="CodiceEsenzione" type="{http://flussi.mds.it/flsProSoc}codiceEsenzioneTicket" minOccurs="0"/&gt;
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
    "regimeErogazione",
    "lordo",
    "ticket",
    "posizioneAssistitoTicket",
    "codiceEsenzione"
})
@XmlRootElement(name = "Importo")
public class Importo {

    @XmlElement(name = "RegimeErogazione")
    @CsvBindByPosition(position = 56)
    protected String regimeErogazione;
    @XmlElement(name = "Lordo")
    @CsvBindByPosition(position = 57)
    protected BigDecimal lordo;
    @XmlElement(name = "Ticket")
    @CsvBindByPosition(position = 58)
    protected BigDecimal ticket;
    @XmlElement(name = "PosizioneAssistitoTicket")
    @CsvBindByPosition(position = 59)
    protected String posizioneAssistitoTicket;
    @XmlElement(name = "CodiceEsenzione")
    @CsvBindByPosition(position = 60)
    protected String codiceEsenzione;

    /**
     * Recupera il valore della propriet� regimeErogazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegimeErogazione() {
        return regimeErogazione;
    }

    /**
     * Imposta il valore della propriet� regimeErogazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegimeErogazione(String value) {
    	if (StringUtils.isBlank(value))
    		value = null;
        this.regimeErogazione = value;
    }

    /**
     * Recupera il valore della propriet� lordo.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLordo() {
        return lordo;
    }

    /**
     * Imposta il valore della propriet� lordo.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLordo(BigDecimal value) {
        this.lordo = value;
    }

    /**
     * Recupera il valore della propriet� ticket.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTicket() {
        return ticket;
    }

    /**
     * Imposta il valore della propriet� ticket.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTicket(BigDecimal value) {
        this.ticket = value;
    }

    /**
     * Recupera il valore della propriet� posizioneAssistitoTicket.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPosizioneAssistitoTicket() {
        return posizioneAssistitoTicket;
    }

    /**
     * Imposta il valore della propriet� posizioneAssistitoTicket.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPosizioneAssistitoTicket(String value) {
    	if (StringUtils.isBlank(value))
    		value = null;
        this.posizioneAssistitoTicket = value;
    }

    /**
     * Recupera il valore della propriet� codiceEsenzione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceEsenzione() {
        return codiceEsenzione;
    }

    /**
     * Imposta il valore della propriet� codiceEsenzione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceEsenzione(String value) {
    	if (StringUtils.isBlank(value))
    		value = null;
        this.codiceEsenzione = value;
    }

}
