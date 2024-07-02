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
 *         &lt;element name="Motivo" type="{http://flussi.mds.it/flsProSoc}motivoTrasferimento"/&gt;
 *         &lt;element name="Istituto" type="{http://flussi.mds.it/flsProSoc}istituto"/&gt;
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
    "motivo",
    "istituto"
})
@XmlRootElement(name = "Trasferimento")
public class Trasferimento {

    @XmlElement(name = "Motivo", required = true)
    @CsvBindByPosition(position = 40)
    protected String motivo;
    @XmlElement(name = "Istituto", required = true)
    @CsvBindByPosition(position = 41)
    protected String istituto;

    /**
     * Recupera il valore della propriet� motivo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMotivo() {
        return motivo;
    }

    /**
     * Imposta il valore della propriet� motivo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMotivo(String value) {
        this.motivo = value;
    }

    /**
     * Recupera il valore della propriet� istituto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIstituto() {
        return istituto;
    }

    /**
     * Imposta il valore della propriet� istituto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIstituto(String value) {
        this.istituto = value;
    }

}
