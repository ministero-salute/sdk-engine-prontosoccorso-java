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

import org.apache.commons.lang3.StringUtils;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvRecurse;

import it.mds.sdk.libreriaregole.dtos.RecordDtoGenerico;
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
 *         &lt;element name="Identificativo" type="{http://flussi.mds.it/flsProSoc}idAccesso"/&gt;
 *         &lt;element ref="{http://flussi.mds.it/flsProSoc}Erogatore"/&gt;
 *         &lt;element ref="{http://flussi.mds.it/flsProSoc}Entrata"/&gt;
 *         &lt;element name="ModalitaArrivo" type="{http://flussi.mds.it/flsProSoc}modalitaArrivo"/&gt;
 *         &lt;element ref="{http://flussi.mds.it/flsProSoc}CentraleOperativa" minOccurs="0"/&gt;
 *         &lt;element name="ResponsabileInvio" type="{http://flussi.mds.it/flsProSoc}responsabileInvio"/&gt;
 *         &lt;element name="IstitutoProvenienza" type="{http://flussi.mds.it/flsProSoc}istitutoProvenienza" minOccurs="0"/&gt;
 *         &lt;element name="ProblemaPrincipale" type="{http://flussi.mds.it/flsProSoc}problemaPrincipale"/&gt;
 *         &lt;element name="Trauma" type="{http://flussi.mds.it/flsProSoc}trauma" minOccurs="0"/&gt;
 *         &lt;element name="Triage" type="{http://flussi.mds.it/flsProSoc}triageAccesso"/&gt;
 *         &lt;element ref="{http://flussi.mds.it/flsProSoc}Assistito"/&gt;
 *         &lt;element ref="{http://flussi.mds.it/flsProSoc}Importo" minOccurs="0"/&gt;
 *         &lt;element name="TipoTrasmissione" type="{http://flussi.mds.it/flsProSoc}tipoTrasmissione"/&gt;
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
    "identificativo",
    "erogatore",
    "entrata",
    "modalitaArrivo",
    "centraleOperativa",
    "responsabileInvio",
    "istitutoProvenienza",
    "problemaPrincipale",
    "trauma",
    "triage",
    "assistito",
    "importo",
    "tipoTrasmissione"
})
@XmlRootElement(name = "Accesso")
public class Accesso extends RecordDtoGenerico {
	
    @XmlElement(name = "Identificativo", required = true)
    @CsvBindByPosition(position = 0)
    protected String identificativo;
    @XmlElement(name = "Erogatore", required = true)
    @CsvRecurse
    protected Erogatore erogatore = new Erogatore();
    @XmlElement(name = "Entrata", required = true)
    @CsvRecurse
    protected Entrata entrata;
    @XmlElement(name = "ModalitaArrivo", required = true)
    @CsvBindByPosition(position = 4)
    protected String modalitaArrivo;
    @XmlElement(name = "CentraleOperativa")
    @CsvRecurse
    protected CentraleOperativa centraleOperativa;
    @XmlElement(name = "ResponsabileInvio", required = true)
    @CsvBindByPosition(position = 7)
    protected String responsabileInvio;
    @XmlElement(name = "IstitutoProvenienza")
    @CsvBindByPosition(position = 8)
    protected String istitutoProvenienza;
    @XmlElement(name = "ProblemaPrincipale", required = true)
    @CsvBindByPosition(position = 9)
    protected String problemaPrincipale;
    @XmlElement(name = "Trauma")
    @CsvBindByPosition(position = 10)
    protected String trauma;
    @XmlElement(name = "Triage", required = true)
    @CsvBindByPosition(position = 11)
    protected String triage;
    @XmlElement(name = "Assistito", required = true)
    @CsvRecurse
    protected Assistito assistito;
    @XmlElement(name = "Importo")
    @CsvRecurse
    protected Importo importo;
    @XmlElement(name = "TipoTrasmissione", required = true)
    @CsvBindByPosition(position = 61)
    protected String tipoTrasmissione;

    /**
     * Recupera il valore della propriet� identificativo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativo() {
        return identificativo;
    }

    /**
     * Imposta il valore della propriet� identificativo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativo(String value) {
        this.identificativo = value;
    }

    /**
     * Recupera il valore della propriet� erogatore.
     * 
     * @return
     *     possible object is
     *     {@link Erogatore }
     *     
     */
    public Erogatore getErogatore() {
        return erogatore;
    }

    /**
     * Imposta il valore della propriet� erogatore.
     * 
     * @param value
     *     allowed object is
     *     {@link Erogatore }
     *     
     */
    public void setErogatore(Erogatore value) {
        this.erogatore = value;
    }

    /**
     * Recupera il valore della propriet� entrata.
     * 
     * @return
     *     possible object is
     *     {@link Entrata }
     *     
     */
    public Entrata getEntrata() {
        return entrata;
    }

    /**
     * Imposta il valore della propriet� entrata.
     * 
     * @param value
     *     allowed object is
     *     {@link Entrata }
     *     
     */
    public void setEntrata(Entrata value) {
        this.entrata = value;
    }

    /**
     * Recupera il valore della propriet� modalitaArrivo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModalitaArrivo() {
        return modalitaArrivo;
    }

    /**
     * Imposta il valore della propriet� modalitaArrivo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModalitaArrivo(String value) {
        this.modalitaArrivo = value;
    }

    /**
     * Recupera il valore della propriet� centraleOperativa.
     * 
     * @return
     *     possible object is
     *     {@link CentraleOperativa }
     *     
     */
    public CentraleOperativa getCentraleOperativa() {
        return centraleOperativa;
    }

    /**
     * Imposta il valore della propriet� centraleOperativa.
     * 
     * @param value
     *     allowed object is
     *     {@link CentraleOperativa }
     *     
     */
    public void setCentraleOperativa(CentraleOperativa value) {
//   		 if (!Stream.of(
//           		value.identificativo,
//           		value.codiceMissione).allMatch(Objects::nonNull))
//       		value = null;
   		 
        this.centraleOperativa = value;
    }

    /**
     * Recupera il valore della propriet� responsabileInvio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResponsabileInvio() {
        return responsabileInvio;
    }

    /**
     * Imposta il valore della propriet� responsabileInvio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResponsabileInvio(String value) {
        this.responsabileInvio = value;
    }

    /**
     * Recupera il valore della propriet� istitutoProvenienza.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIstitutoProvenienza() {
    	if (StringUtils.isBlank(istitutoProvenienza))
    		return null;
        return istitutoProvenienza;
    }

    /**
     * Imposta il valore della propriet� istitutoProvenienza.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIstitutoProvenienza(String value) {
    	if (StringUtils.isBlank(value))
    		value = null;
        this.istitutoProvenienza = value;
    }

    /**
     * Recupera il valore della propriet� problemaPrincipale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProblemaPrincipale() {
        return problemaPrincipale;
    }

    /**
     * Imposta il valore della propriet� problemaPrincipale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProblemaPrincipale(String value) {
        this.problemaPrincipale = value;
    }

    /**
     * Recupera il valore della propriet� trauma.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrauma() {
        return trauma;
    }

    /**
     * Imposta il valore della propriet� trauma.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrauma(String value) {
    	if (StringUtils.isBlank(value))
    		value = null;
        this.trauma = value;
    }

    /**
     * Recupera il valore della propriet� triage.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTriage() {
        return triage;
    }

    /**
     * Imposta il valore della propriet� triage.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTriage(String value) {
        this.triage = value;
    }

    /**
     * Recupera il valore della propriet� assistito.
     * 
     * @return
     *     possible object is
     *     {@link Assistito }
     *     
     */
    public Assistito getAssistito() {
        return assistito;
    }

    /**
     * Imposta il valore della propriet� assistito.
     * 
     * @param value
     *     allowed object is
     *     {@link Assistito }
     *     
     */
    public void setAssistito(Assistito value) {
        this.assistito = value;
    }

    /**
     * Recupera il valore della propriet� importo.
     * 
     * @return
     *     possible object is
     *     {@link Importo }
     *     
     */
    public Importo getImporto() {
        return importo;
    }

    /**
     * Imposta il valore della propriet� importo.
     * 
     * @param value
     *     allowed object is
     *     {@link Importo }
     *     
     */
    public void setImporto(Importo value) {
//    	if (!Stream.of(value.regimeErogazione, value.lordo,value.ticket,
//    			value.posizioneAssistitoTicket,value.codiceEsenzione).allMatch(Objects::nonNull))
//    		value = null;
    	 
        this.importo = value;
    }

    /**
     * Recupera il valore della propriet� tipoTrasmissione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoTrasmissione() {
        return tipoTrasmissione;
    }

    /**
     * Imposta il valore della propriet� tipoTrasmissione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoTrasmissione(String value) {
        this.tipoTrasmissione = value;
    }

}
