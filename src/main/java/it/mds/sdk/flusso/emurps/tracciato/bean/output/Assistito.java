//
// Questo file � stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.0 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andr� persa durante la ricompilazione dello schema di origine. 
// Generato il: 2022.04.12 alle 10:57:37 AM CEST 
//


/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.flusso.emurps.tracciato.bean.output;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.opencsv.bean.CsvBindAndSplitByPosition;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import com.opencsv.bean.CsvRecurse;

import it.mds.sdk.flusso.emurps.parser.regole.Utilities;
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
 *         &lt;element name="CUNI" type="{http://flussi.mds.it/flsProSoc}Cuni"/&gt;
 *         &lt;element name="ValiditaCI" type="{http://flussi.mds.it/flsProSoc}validitaCI"/&gt;
 *         &lt;element name="TipologiaCI" type="{http://flussi.mds.it/flsProSoc}tipologiaCI"/&gt;
 *         &lt;element name="CodiceIstituzioneTEAM" type="{http://flussi.mds.it/flsProSoc}codIstituzioneTeam" minOccurs="0"/&gt;
 *         &lt;element ref="{http://flussi.mds.it/flsProSoc}DatiAnagrafici"/&gt;
 *         &lt;element ref="{http://flussi.mds.it/flsProSoc}Prestazioni"/&gt;
 *         &lt;element ref="{http://flussi.mds.it/flsProSoc}Dimissione"/&gt;
 *         &lt;element name="OBI" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="ProblemaClinicoPrincipale" minOccurs="0"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *                         &lt;minLength value="3"/&gt;
 *                         &lt;maxLength value="6"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="DataIngresso" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *                   &lt;element name="OraIngresso" type="{http://flussi.mds.it/flsProSoc}ora" minOccurs="0"/&gt;
 *                   &lt;element name="PrimaConsulenzaSpecDis" type="{http://flussi.mds.it/flsProSoc}specialitaReparto" minOccurs="0"/&gt;
 *                   &lt;element name="PrimaConsulenzaSpecNum" minOccurs="0"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *                         &lt;pattern value="1"/&gt;
 *                         &lt;pattern value="2"/&gt;
 *                         &lt;pattern value="3"/&gt;
 *                         &lt;pattern value="4"/&gt;
 *                         &lt;pattern value="5"/&gt;
 *                         &lt;pattern value="6"/&gt;
 *                         &lt;pattern value="7"/&gt;
 *                         &lt;pattern value="8"/&gt;
 *                         &lt;pattern value="9"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="SecondaConsulenzaSpecDis" type="{http://flussi.mds.it/flsProSoc}specialitaReparto" minOccurs="0"/&gt;
 *                   &lt;element name="SecondaConsulenzaSpecNum" minOccurs="0"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *                         &lt;pattern value="1"/&gt;
 *                         &lt;pattern value="2"/&gt;
 *                         &lt;pattern value="3"/&gt;
 *                         &lt;pattern value="4"/&gt;
 *                         &lt;pattern value="5"/&gt;
 *                         &lt;pattern value="6"/&gt;
 *                         &lt;pattern value="7"/&gt;
 *                         &lt;pattern value="8"/&gt;
 *                         &lt;pattern value="9"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="Esito" type="{http://flussi.mds.it/flsProSoc}esito" minOccurs="0"/&gt;
 *                   &lt;element name="PrestazionePrincipaleErogata" type="{http://flussi.mds.it/flsProSoc}codicePrestazione" minOccurs="0"/&gt;
 *                   &lt;element name="PrestazioneSecondariaErogata" type="{http://flussi.mds.it/flsProSoc}codicePrestazione" maxOccurs="4" minOccurs="0"/&gt;
 *                   &lt;element name="DiagnosiPrincipaleUscita" type="{http://flussi.mds.it/flsProSoc}codiceDiagnosi" minOccurs="0"/&gt;
 *                   &lt;element name="DiagnosiSecondariaUscita" type="{http://flussi.mds.it/flsProSoc}codiceDiagnosi" maxOccurs="4" minOccurs="0"/&gt;
 *                   &lt;element name="DataUscita" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *                   &lt;element name="OraUscita" type="{http://flussi.mds.it/flsProSoc}ora" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
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
    "cuni",
    "validitaCI",
    "tipologiaCI",
    "codiceIstituzioneTEAM",
    "datiAnagrafici",
    "prestazioni",
    "dimissione",
    "obi"
})
@XmlRootElement(name = "Assistito")
public class Assistito {

    @XmlElement(name = "CUNI", required = true)
    @CsvBindByPosition(position = 12)
    protected String cuni;
    @XmlElement(name = "ValiditaCI")
    @CsvBindByPosition(position = 13)
    protected int validitaCI;
    @XmlElement(name = "TipologiaCI")
    @CsvBindByPosition(position = 14)
    protected int tipologiaCI;
    @XmlElement(name = "CodiceIstituzioneTEAM")
    @CsvBindByPosition(position = 15)
    protected String codiceIstituzioneTEAM;
    @XmlElement(name = "DatiAnagrafici", required = true)
    @CsvRecurse
    protected DatiAnagrafici datiAnagrafici;
    @XmlElement(name = "Prestazioni", required = true)
    @CsvRecurse
    protected Prestazioni prestazioni;
    @XmlElement(name = "Dimissione", required = true)
    @CsvRecurse
    protected Dimissione dimissione;
    @XmlElement(name = "OBI")
    @CsvRecurse
    protected Assistito.OBI obi;

    /**
     * Recupera il valore della propriet� cuni.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCUNI() {
        return cuni;
    }

    /**
     * Imposta il valore della propriet� cuni.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCUNI(String value) {
        this.cuni = value;
    }

    /**
     * Recupera il valore della propriet� validitaCI.
     * 
     */
    public int getValiditaCI() {
        return validitaCI;
    }

    /**
     * Imposta il valore della propriet� validitaCI.
     * 
     */
    public void setValiditaCI(int value) {
        this.validitaCI = value;
    }

    /**
     * Recupera il valore della propriet� tipologiaCI.
     * 
     */
    public int getTipologiaCI() {
        return tipologiaCI;
    }

    /**
     * Imposta il valore della propriet� tipologiaCI.
     * 
     */
    public void setTipologiaCI(int value) {
        this.tipologiaCI = value;
    }

    /**
     * Recupera il valore della propriet� codiceIstituzioneTEAM.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceIstituzioneTEAM() {
        return codiceIstituzioneTEAM;
    }

    /**
     * Imposta il valore della propriet� codiceIstituzioneTEAM.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceIstituzioneTEAM(String value) {
    	if (StringUtils.isBlank(value))
    		value = null;
        this.codiceIstituzioneTEAM = value;
    }

    /**
     * Recupera il valore della propriet� datiAnagrafici.
     * 
     * @return
     *     possible object is
     *     {@link DatiAnagrafici }
     *     
     */
    public DatiAnagrafici getDatiAnagrafici() {
        return datiAnagrafici;
    }

    /**
     * Imposta il valore della propriet� datiAnagrafici.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiAnagrafici }
     *     
     */
    public void setDatiAnagrafici(DatiAnagrafici value) {
        this.datiAnagrafici = value;
    }

    /**
     * Recupera il valore della propriet� prestazioni.
     * 
     * @return
     *     possible object is
     *     {@link Prestazioni }
     *     
     */
    public Prestazioni getPrestazioni() {
        return prestazioni;
    }

    /**
     * Imposta il valore della propriet� prestazioni.
     * 
     * @param value
     *     allowed object is
     *     {@link Prestazioni }
     *     
     */
    public void setPrestazioni(Prestazioni value) {
        this.prestazioni = value;
    }

    /**
     * Recupera il valore della propriet� dimissione.
     * 
     * @return
     *     possible object is
     *     {@link Dimissione }
     *     
     */
    public Dimissione getDimissione() {
        return dimissione;
    }

    /**
     * Imposta il valore della propriet� dimissione.
     * 
     * @param value
     *     allowed object is
     *     {@link Dimissione }
     *     
     */
    public void setDimissione(Dimissione value) {
        this.dimissione = value;
    }

    /**
     * Recupera il valore della propriet� obi.
     * 
     * @return
     *     possible object is
     *     {@link Assistito.OBI }
     *     
     */
    public Assistito.OBI getOBI() {
        return obi;
    }

    /**
     * Imposta il valore della propriet� obi.
     * 
     * @param value
     *     allowed object is
     *     {@link Assistito.OBI }
     *     
     */
    public void setOBI(Assistito.OBI value) {
        
//        if (!Stream.of(
//        		value.problemaClinicoPrincipale,
//        		value.dataIngresso,
//        		value.oraIngresso,
//        		value.primaConsulenzaSpecDis,
//        		value.primaConsulenzaSpecNum,
//        		value.secondaConsulenzaSpecDis,
//        		value.secondaConsulenzaSpecNum,
//        		value.esito,
//        		value.prestazionePrincipaleErogata,
//        		value.prestazioneSecondariaErogata,
//        		value.diagnosiPrincipaleUscita,
//        		value.diagnosiSecondariaUscita,
//        		value.dataUscita,
//        		value.oraUscita).allMatch(Objects::nonNull) ) {
//    		value = null;
//        }
        this.obi = value;
    }


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
     *         &lt;element name="ProblemaClinicoPrincipale" minOccurs="0"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
     *               &lt;minLength value="3"/&gt;
     *               &lt;maxLength value="6"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="DataIngresso" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
     *         &lt;element name="OraIngresso" type="{http://flussi.mds.it/flsProSoc}ora" minOccurs="0"/&gt;
     *         &lt;element name="PrimaConsulenzaSpecDis" type="{http://flussi.mds.it/flsProSoc}specialitaReparto" minOccurs="0"/&gt;
     *         &lt;element name="PrimaConsulenzaSpecNum" minOccurs="0"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
     *               &lt;pattern value="1"/&gt;
     *               &lt;pattern value="2"/&gt;
     *               &lt;pattern value="3"/&gt;
     *               &lt;pattern value="4"/&gt;
     *               &lt;pattern value="5"/&gt;
     *               &lt;pattern value="6"/&gt;
     *               &lt;pattern value="7"/&gt;
     *               &lt;pattern value="8"/&gt;
     *               &lt;pattern value="9"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="SecondaConsulenzaSpecDis" type="{http://flussi.mds.it/flsProSoc}specialitaReparto" minOccurs="0"/&gt;
     *         &lt;element name="SecondaConsulenzaSpecNum" minOccurs="0"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
     *               &lt;pattern value="1"/&gt;
     *               &lt;pattern value="2"/&gt;
     *               &lt;pattern value="3"/&gt;
     *               &lt;pattern value="4"/&gt;
     *               &lt;pattern value="5"/&gt;
     *               &lt;pattern value="6"/&gt;
     *               &lt;pattern value="7"/&gt;
     *               &lt;pattern value="8"/&gt;
     *               &lt;pattern value="9"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="Esito" type="{http://flussi.mds.it/flsProSoc}esito" minOccurs="0"/&gt;
     *         &lt;element name="PrestazionePrincipaleErogata" type="{http://flussi.mds.it/flsProSoc}codicePrestazione" minOccurs="0"/&gt;
     *         &lt;element name="PrestazioneSecondariaErogata" type="{http://flussi.mds.it/flsProSoc}codicePrestazione" maxOccurs="4" minOccurs="0"/&gt;
     *         &lt;element name="DiagnosiPrincipaleUscita" type="{http://flussi.mds.it/flsProSoc}codiceDiagnosi" minOccurs="0"/&gt;
     *         &lt;element name="DiagnosiSecondariaUscita" type="{http://flussi.mds.it/flsProSoc}codiceDiagnosi" maxOccurs="4" minOccurs="0"/&gt;
     *         &lt;element name="DataUscita" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
     *         &lt;element name="OraUscita" type="{http://flussi.mds.it/flsProSoc}ora" minOccurs="0"/&gt;
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
        "problemaClinicoPrincipale",
        "dataIngresso",
        "oraIngresso",
        "primaConsulenzaSpecDis",
        "primaConsulenzaSpecNum",
        "secondaConsulenzaSpecDis",
        "secondaConsulenzaSpecNum",
        "esito",
        "prestazionePrincipaleErogata",
        "prestazioneSecondariaErogata",
        "diagnosiPrincipaleUscita",
        "diagnosiSecondariaUscita",
        "dataUscita",
        "oraUscita"
    })
    public static class OBI {

        @XmlElement(name = "ProblemaClinicoPrincipale")
        @CsvBindByPosition(position = 42)
        protected String problemaClinicoPrincipale;
        @XmlElement(name = "DataIngresso")
        @XmlSchemaType(name = "date")
        @CsvBindByPosition(position = 43)
        @CsvDate("yyyy-MM-dd")
        protected Date dataIngresso;
        @XmlElement(name = "OraIngresso")
        @CsvBindByPosition(position = 44)
        protected String oraIngresso;
        @XmlElement(name = "PrimaConsulenzaSpecDis")
        @CsvBindByPosition(position = 45)
        protected String primaConsulenzaSpecDis;
        @XmlElement(name = "PrimaConsulenzaSpecNum")
        @CsvBindByPosition(position = 46)
        protected String primaConsulenzaSpecNum;
        @XmlElement(name = "SecondaConsulenzaSpecDis")
        @CsvBindByPosition(position = 47)
        protected String secondaConsulenzaSpecDis;
        @XmlElement(name = "SecondaConsulenzaSpecNum")
        @CsvBindByPosition(position = 48)
        protected String secondaConsulenzaSpecNum;
        @XmlElement(name = "Esito")
        @CsvBindByPosition(position = 49)
        protected String esito;
        @XmlElement(name = "PrestazionePrincipaleErogata")
        @CsvBindByPosition(position = 50)
        protected String prestazionePrincipaleErogata;
        @XmlElement(name = "PrestazioneSecondariaErogata")
//        @CsvBindAndSplitByPosition(position=51, elementType = String.class, splitOn = "[|]")
        @CsvBindAndSplitByPosition(position=51, elementType = String.class, writeDelimiter = "|")
        protected List<String> prestazioneSecondariaErogata;
        @XmlElement(name = "DiagnosiPrincipaleUscita")
        @CsvBindByPosition(position = 52)
        protected String diagnosiPrincipaleUscita;
        @XmlElement(name = "DiagnosiSecondariaUscita")
        @CsvBindAndSplitByPosition(position=53, elementType = String.class, writeDelimiter = "|")
        protected List<String> diagnosiSecondariaUscita;
        @XmlElement(name = "DataUscita")
        @XmlSchemaType(name = "date")
        @CsvBindByPosition(position = 54)
        @CsvDate("yyyy-MM-dd")
        protected Date dataUscita;
        @XmlElement(name = "OraUscita")
        @CsvBindByPosition(position = 55)
        protected String oraUscita;

        /**
         * Recupera il valore della propriet� problemaClinicoPrincipale.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getProblemaClinicoPrincipale() {
            return problemaClinicoPrincipale;
        }

        /**
         * Imposta il valore della propriet� problemaClinicoPrincipale.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setProblemaClinicoPrincipale(String value) {
        	if (StringUtils.isBlank(value))
        		value = null;
            this.problemaClinicoPrincipale = value;
        }

        /**
         * Recupera il valore della propriet� dataIngresso.
         * 
         * @return
         *     possible object is
         *     {@link Date }
         *     
         */
        public Date getDataIngresso() {
            return dataIngresso;
        }

        /**
         * Imposta il valore della propriet� dataIngresso.
         * 
         * @param value
         *     allowed object is
         *     {@link Date }
         *     
         */
        public void setDataIngresso(Date value) {
            this.dataIngresso = value;
        }

        /**
         * Recupera il valore della propriet� oraIngresso.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOraIngresso() {
            return oraIngresso;
        }

        /**
         * Imposta il valore della propriet� oraIngresso.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOraIngresso(String value) {
        	if (StringUtils.isBlank(value))
        		value = null;
            this.oraIngresso = value;
        }

        /**
         * Recupera il valore della propriet� primaConsulenzaSpecDis.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPrimaConsulenzaSpecDis() {
            return primaConsulenzaSpecDis;
        }

        /**
         * Imposta il valore della propriet� primaConsulenzaSpecDis.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPrimaConsulenzaSpecDis(String value) {
        	if (StringUtils.isBlank(value))
        		value = null;
            this.primaConsulenzaSpecDis = value;
        }

        /**
         * Recupera il valore della propriet� primaConsulenzaSpecNum.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPrimaConsulenzaSpecNum() {
            return primaConsulenzaSpecNum;
        }

        /**
         * Imposta il valore della propriet� primaConsulenzaSpecNum.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPrimaConsulenzaSpecNum(String value) {
        	if (StringUtils.isBlank(value))
        		value = null;
            this.primaConsulenzaSpecNum = value;
        }

        /**
         * Recupera il valore della propriet� secondaConsulenzaSpecDis.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSecondaConsulenzaSpecDis() {
            return secondaConsulenzaSpecDis;
        }

        /**
         * Imposta il valore della propriet� secondaConsulenzaSpecDis.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSecondaConsulenzaSpecDis(String value) {
        	if (StringUtils.isBlank(value))
        		value = null;
            this.secondaConsulenzaSpecDis = value;
        }

        /**
         * Recupera il valore della propriet� secondaConsulenzaSpecNum.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSecondaConsulenzaSpecNum() {
            return secondaConsulenzaSpecNum;
        }

        /**
         * Imposta il valore della propriet� secondaConsulenzaSpecNum.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSecondaConsulenzaSpecNum(String value) {
        	if (StringUtils.isBlank(value))
        		value = null;
            this.secondaConsulenzaSpecNum = value;
        }

        /**
         * Recupera il valore della propriet� esito.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEsito() {
            return esito;
        }

        /**
         * Imposta il valore della propriet� esito.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEsito(String value) {
        	if (StringUtils.isBlank(value))
        		value = null;
            this.esito = value;
        }

        /**
         * Recupera il valore della propriet� prestazionePrincipaleErogata.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPrestazionePrincipaleErogata() {
            return prestazionePrincipaleErogata;
        }

        /**
         * Imposta il valore della propriet� prestazionePrincipaleErogata.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPrestazionePrincipaleErogata(String value) {
        	if (StringUtils.isBlank(value))
        		value = null;
            this.prestazionePrincipaleErogata = value;
        }

        /**
         * Gets the value of the prestazioneSecondariaErogata property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the prestazioneSecondariaErogata property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPrestazioneSecondariaErogata().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getPrestazioneSecondariaErogata() {
            if (prestazioneSecondariaErogata == null) {
                prestazioneSecondariaErogata = new ArrayList<>();
            }
            return this.prestazioneSecondariaErogata;
        }
        
        public void setPrestazioneSecondariaErogata(List<String> value) {        	
				if (!Utilities.checkList(value))
					value = null;
			
            this.prestazioneSecondariaErogata = value;
        }

        /**
         * Recupera il valore della propriet� diagnosiPrincipaleUscita.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDiagnosiPrincipaleUscita() {
            return diagnosiPrincipaleUscita;
        }

        /**
         * Imposta il valore della propriet� diagnosiPrincipaleUscita.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDiagnosiPrincipaleUscita(String value) {
        	if (StringUtils.isBlank(value))
        		value = null;
            this.diagnosiPrincipaleUscita = value;
        }

        /**
         * Gets the value of the diagnosiSecondariaUscita property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the diagnosiSecondariaUscita property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDiagnosiSecondariaUscita().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getDiagnosiSecondariaUscita() {
            if (diagnosiSecondariaUscita == null) {
                diagnosiSecondariaUscita = new ArrayList<>();
            }
            return this.diagnosiSecondariaUscita;
        }
        
        public void setDiagnosiSecondariaUscita(List<String> value) {
        	
				if (!Utilities.checkList(value))
					value = null;
			
        	
            this.diagnosiSecondariaUscita = value;
        }

        /**
         * Recupera il valore della propriet� dataUscita.
         * 
         * @return
         *     possible object is
         *     {@link Date }
         *     
         */
        public Date getDataUscita() {
            return dataUscita;
        }

        /**
         * Imposta il valore della propriet� dataUscita.
         * 
         * @param value
         *     allowed object is
         *     {@link Date }
         *     
         */
        public void setDataUscita(Date value) {
            this.dataUscita = value;
        }

        /**
         * Recupera il valore della propriet� oraUscita.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOraUscita() {
            return oraUscita;
        }

        /**
         * Imposta il valore della propriet� oraUscita.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOraUscita(String value) {
        	if (StringUtils.isBlank(value))
        		value = null;
            this.oraUscita = value;
        }

    }

}
