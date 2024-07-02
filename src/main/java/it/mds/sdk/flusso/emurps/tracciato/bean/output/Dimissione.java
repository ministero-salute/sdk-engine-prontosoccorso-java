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
import com.opencsv.bean.CsvRecurse;

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
 *         &lt;element name="EsitoTrattamento"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
 *               &lt;minInclusive value="1"/&gt;
 *               &lt;maxInclusive value="11"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="DataDest" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="OraDest" type="{http://flussi.mds.it/flsProSoc}ora" minOccurs="0"/&gt;
 *         &lt;element name="Data" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="Ora" type="{http://flussi.mds.it/flsProSoc}ora" minOccurs="0"/&gt;
 *         &lt;element name="Referto" type="{http://flussi.mds.it/flsProSoc}referto" minOccurs="0"/&gt;
 *         &lt;element name="LivelloAppropriatezzaAccesso" type="{http://flussi.mds.it/flsProSoc}livelloAppropriatezza" minOccurs="0"/&gt;
 *         &lt;element ref="{http://flussi.mds.it/flsProSoc}Ricovero" minOccurs="0"/&gt;
 *         &lt;element ref="{http://flussi.mds.it/flsProSoc}Trasferimento" minOccurs="0"/&gt;
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
    "esitoTrattamento",
    "dataDest",
    "oraDest",
    "data",
    "ora",
    "referto",
    "livelloAppropriatezzaAccesso",
    "ricovero",
    "trasferimento"
})
@XmlRootElement(name = "Dimissione")
public class Dimissione {

    @XmlElement(name = "EsitoTrattamento")
    @CsvBindByPosition(position = 31)
    protected int esitoTrattamento;
    @XmlElement(name = "DataDest")
    @XmlSchemaType(name = "date")
    @CsvBindByPosition(position = 32)
    @CsvDate("yyyy-MM-dd")
    @CsvDate("yyyy-MM-dd")
    protected Date dataDest;
    @XmlElement(name = "OraDest")
    @CsvBindByPosition(position = 33)
    protected String oraDest;
    @XmlElement(name = "Data")
    @XmlSchemaType(name = "date")
    @CsvBindByPosition(position = 34)
    @CsvDate("yyyy-MM-dd")
    protected Date data;
    @XmlElement(name = "Ora")
    @CsvBindByPosition(position = 35)
    protected String ora;
    @XmlElement(name = "Referto")
    @CsvBindByPosition(position = 36)
    protected String referto;
    @XmlElement(name = "LivelloAppropriatezzaAccesso")
    @CsvBindByPosition(position = 37)
    protected String livelloAppropriatezzaAccesso;
    @XmlElement(name = "Ricovero")
    @CsvRecurse
    protected Ricovero ricovero;
    @XmlElement(name = "Trasferimento")
    @CsvRecurse
    protected Trasferimento trasferimento;

    /**
     * Recupera il valore della propriet� esitoTrattamento.
     * 
     */
    public int getEsitoTrattamento() {
        return esitoTrattamento;
    }

    /**
     * Imposta il valore della propriet� esitoTrattamento.
     * 
     */
    public void setEsitoTrattamento(int value) {
        this.esitoTrattamento = value;
    }

    /**
     * Recupera il valore della propriet� dataDest.
     * 
     * @return
     *     possible object is
     *     {@link Date }
     *     
     */
    public Date getDataDest() {
        return dataDest;
    }

    /**
     * Imposta il valore della propriet� dataDest.
     * 
     * @param value
     *     allowed object is
     *     {@link Date }
     *     
     */
    public void setDataDest(Date value) {
        this.dataDest = value;
    }

    /**
     * Recupera il valore della propriet� oraDest.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOraDest() {
        return oraDest;
    }

    /**
     * Imposta il valore della propriet� oraDest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOraDest(String value) {
    	if (StringUtils.isBlank(value))
    		value = null;
        this.oraDest = value;
    }

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
    	if (StringUtils.isBlank(value))
    		value = null;
        this.ora = value;
    }

    /**
     * Recupera il valore della propriet� referto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferto() {
        return referto;
    }

    /**
     * Imposta il valore della propriet� referto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferto(String value) {
    	if (StringUtils.isBlank(value))
    		value = null;
        this.referto = value;
    }

    /**
     * Recupera il valore della propriet� livelloAppropriatezzaAccesso.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLivelloAppropriatezzaAccesso() {
        return livelloAppropriatezzaAccesso;
    }

    /**
     * Imposta il valore della propriet� livelloAppropriatezzaAccesso.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLivelloAppropriatezzaAccesso(String value) {
    	if (StringUtils.isBlank(value))
    		value = null;
        this.livelloAppropriatezzaAccesso = value;
    }

    /**
     * Recupera il valore della propriet� ricovero.
     * 
     * @return
     *     possible object is
     *     {@link Ricovero }
     *     
     */
    public Ricovero getRicovero() {
        return ricovero;
    }

    /**
     * Imposta il valore della propriet� ricovero.
     * 
     * @param value
     *     allowed object is
     *     {@link Ricovero }
     *     
     */
    public void setRicovero(Ricovero value) {
//     	 if (!Stream.of(
//         		value.progressivo,
//         		value.codiceSpecialitaReparto).allMatch(Objects::nonNull))
//     		value = null;
        this.ricovero = value;
    }

    /**
     * Recupera il valore della propriet� trasferimento.
     * 
     * @return
     *     possible object is
     *     {@link Trasferimento }
     *     
     */
    public Trasferimento getTrasferimento() {
        return trasferimento;
    }

    /**
     * Imposta il valore della propriet� trasferimento.
     * 
     * @param value
     *     allowed object is
     *     {@link Trasferimento }
     *     
     */
    public void setTrasferimento(Trasferimento value) {
//    	 if (!Stream.of(
//          		value.motivo,
//          		value.istituto).allMatch(Objects::nonNull))
//      		value = null;
        this.trasferimento = value;
    }

}
