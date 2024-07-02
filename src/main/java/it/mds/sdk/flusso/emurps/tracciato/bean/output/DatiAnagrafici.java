//
// Questo file � stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.0 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andr� persa durante la ricompilazione dello schema di origine. 
// Generato il: 2022.04.12 alle 10:57:37 AM CEST 
//


/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.flusso.emurps.tracciato.bean.output;

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
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://flussi.mds.it/flsProSoc}Eta"/&gt;
 *         &lt;element name="Genere" type="{http://flussi.mds.it/flsProSoc}genere"/&gt;
 *         &lt;element name="Cittadinanza" type="{http://flussi.mds.it/flsProSoc}stato"/&gt;
 *         &lt;element ref="{http://flussi.mds.it/flsProSoc}Residenza"/&gt;
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
    "eta",
    "genere",
    "cittadinanza",
    "residenza"
})
@XmlRootElement(name = "DatiAnagrafici")
public class DatiAnagrafici {

    @XmlElement(name = "Eta", required = true)
    @CsvRecurse
    protected Eta eta;
    @XmlElement(name = "Genere", required = true)
    @CsvBindByPosition(position = 19)
    protected String genere;
    @XmlElement(name = "Cittadinanza", required = true)
    @CsvBindByPosition(position = 20)
    protected String cittadinanza;
    @XmlElement(name = "Residenza", required = true)
    @CsvRecurse
    protected Residenza residenza;

    /**
     * Recupera il valore della propriet� eta.
     * 
     * @return
     *     possible object is
     *     {@link Eta }
     *     
     */
    public Eta getEta() {
        return eta;
    }

    /**
     * Imposta il valore della propriet� eta.
     * 
     * @param value
     *     allowed object is
     *     {@link Eta }
     *     
     */
    public void setEta(Eta value) {
        this.eta = value;
    }

    /**
     * Recupera il valore della propriet� genere.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGenere() {
        return genere;
    }

    /**
     * Imposta il valore della propriet� genere.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGenere(String value) {
        this.genere = value;
    }

    /**
     * Recupera il valore della propriet� cittadinanza.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCittadinanza() {
        return cittadinanza;
    }

    /**
     * Imposta il valore della propriet� cittadinanza.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCittadinanza(String value) {
        this.cittadinanza = value;
    }

    /**
     * Recupera il valore della propriet� residenza.
     * 
     * @return
     *     possible object is
     *     {@link Residenza }
     *     
     */
    public Residenza getResidenza() {
        return residenza;
    }

    /**
     * Imposta il valore della propriet� residenza.
     * 
     * @param value
     *     allowed object is
     *     {@link Residenza }
     *     
     */
    public void setResidenza(Residenza value) {
        this.residenza = value;
    }

}
