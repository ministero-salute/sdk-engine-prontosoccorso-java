//
// Questo file � stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.0 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andr� persa durante la ricompilazione dello schema di origine. 
// Generato il: 2022.04.12 alle 10:57:37 AM CEST 
//


/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.flusso.emurps.tracciato.bean.output;

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
 *         &lt;element name="Progressivo" type="{http://flussi.mds.it/flsProSoc}progRicovero"/&gt;
 *         &lt;element name="CodiceSpecialitaReparto" type="{http://flussi.mds.it/flsProSoc}specialitaReparto"/&gt;
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
    "progressivo",
    "codiceSpecialitaReparto"
})
@XmlRootElement(name = "Ricovero")
public class Ricovero {

    @XmlElement(name = "Progressivo", required = true)
    @CsvBindByPosition(position = 38)
    protected String progressivo;
    @XmlElement(name = "CodiceSpecialitaReparto", required = true)
    @CsvBindByPosition(position = 39)
    protected String codiceSpecialitaReparto;

    /**
     * Recupera il valore della propriet� progressivo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProgressivo() {
        return progressivo;
    }

    /**
     * Imposta il valore della propriet� progressivo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProgressivo(String value) {
        this.progressivo = value;
    }

    /**
     * Recupera il valore della propriet� codiceSpecialitaReparto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceSpecialitaReparto() {
        return codiceSpecialitaReparto;
    }

    /**
     * Imposta il valore della propriet� codiceSpecialitaReparto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceSpecialitaReparto(String value) {
        this.codiceSpecialitaReparto = value;
    }

}
