//
// Questo file � stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.0 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andr� persa durante la ricompilazione dello schema di origine. 
// Generato il: 2022.04.12 alle 10:57:37 AM CEST 
//


/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.flusso.emurps.tracciato.bean.output;

import java.util.Objects;
import java.util.stream.Stream;

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
 *         &lt;element ref="{http://flussi.mds.it/flsProSoc}PresaInCarico" minOccurs="0"/&gt;
 *         &lt;element ref="{http://flussi.mds.it/flsProSoc}Diagnosi" minOccurs="0"/&gt;
 *         &lt;element ref="{http://flussi.mds.it/flsProSoc}Prestazione" minOccurs="0"/&gt;
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
    "presaInCarico",
    "diagnosi",
    "prestazione"
})
@XmlRootElement(name = "Prestazioni")
public class Prestazioni {

    @XmlElement(name = "PresaInCarico")
    @CsvRecurse
    protected PresaInCarico presaInCarico;
    @XmlElement(name = "Diagnosi")
    @CsvRecurse
    protected Diagnosi diagnosi;
    @XmlElement(name = "Prestazione")
    @CsvRecurse
    protected Prestazione prestazione;

    /**
     * Recupera il valore della propriet� presaInCarico.
     * 
     * @return
     *     possible object is
     *     {@link PresaInCarico }
     *     
     */
    public PresaInCarico getPresaInCarico() {
        return presaInCarico;
    }

    /**
     * Imposta il valore della propriet� presaInCarico.
     * 
     * @param value
     *     allowed object is
     *     {@link PresaInCarico }
     *     
     */
    public void setPresaInCarico(PresaInCarico value) {
//    	    if (!Stream.of(
//              		value.data,
//              		value.ora).allMatch(Objects::nonNull))
//          		value = null;
        this.presaInCarico = value;
    }

    /**
     * Recupera il valore della propriet� diagnosi.
     * 
     * @return
     *     possible object is
     *     {@link Diagnosi }
     *     
     */
    public Diagnosi getDiagnosi() {
        return diagnosi;
    }

    /**
     * Imposta il valore della propriet� diagnosi.
     * 
     * @param value
     *     allowed object is
     *     {@link Diagnosi }
     *     
     */
    public void setDiagnosi(Diagnosi value) {
    	  
//   		 if (!Stream.of(
//           		value.diagnosiPrincipale,
//           		value.diagnosiSecondaria).allMatch(Objects::nonNull))
//       		value = null;
   		 
        this.diagnosi = value;
    }

    /**
     * Recupera il valore della propriet� prestazione.
     * 
     * @return
     *     possible object is
     *     {@link Prestazione }
     *     
     */
    public Prestazione getPrestazione() {
        return prestazione;
    }

    /**
     * Imposta il valore della propriet� prestazione.
     * 
     * @param value
     *     allowed object is
     *     {@link Prestazione }
     *     
     */
    public void setPrestazione(Prestazione value) {
//    	 if (!Stream.of(
//          		value.prestazionePrincipale,
//          		value.prestazioneSecondaria).allMatch(Objects::nonNull))
//      		value = null;
        this.prestazione = value;
    }

}
