//
// Questo file � stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.3.0 
// Vedere <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Qualsiasi modifica a questo file andr� persa durante la ricompilazione dello schema di origine. 
// Generato il: 2022.04.12 alle 10:57:37 AM CEST 
//


/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.flusso.emurps.tracciato.bean.output;

import javax.xml.namespace.QName;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.eng.sdk.validation.ps.model package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _NascitaAnno_QNAME = new QName("http://flussi.mds.it/flsProSoc", "Anno");
    private final static QName _NascitaMese_QNAME = new QName("http://flussi.mds.it/flsProSoc", "Mese");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.eng.sdk.validation.ps.model
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Assistito }
     * 
     */
    public Assistito createAssistito() {
        return new Assistito();
    }

    /**
     * Create an instance of {@link Accesso }
     * 
     */
    public Accesso createAccesso() {
        return new Accesso();
    }

    /**
     * Create an instance of {@link Erogatore }
     * 
     */
    public Erogatore createErogatore() {
        return new Erogatore();
    }

    /**
     * Create an instance of {@link Entrata }
     * 
     */
    public Entrata createEntrata() {
        return new Entrata();
    }

    /**
     * Create an instance of {@link CentraleOperativa }
     * 
     */
    public CentraleOperativa createCentraleOperativa() {
        return new CentraleOperativa();
    }

    /**
     * Create an instance of {@link DatiAnagrafici }
     * 
     */
    public DatiAnagrafici createDatiAnagrafici() {
        return new DatiAnagrafici();
    }

    /**
     * Create an instance of {@link Eta }
     * 
     */
    public Eta createEta() {
        return new Eta();
    }

    /**
     * Create an instance of {@link Nascita }
     * 
     */
    public Nascita createNascita() {
        return new Nascita();
    }

    /**
     * Create an instance of {@link Residenza }
     * 
     */
    public Residenza createResidenza() {
        return new Residenza();
    }

    /**
     * Create an instance of {@link Prestazioni }
     * 
     */
    public Prestazioni createPrestazioni() {
        return new Prestazioni();
    }

    /**
     * Create an instance of {@link PresaInCarico }
     * 
     */
    public PresaInCarico createPresaInCarico() {
        return new PresaInCarico();
    }

    /**
     * Create an instance of {@link Diagnosi }
     * 
     */
    public Diagnosi createDiagnosi() {
        return new Diagnosi();
    }

    /**
     * Create an instance of {@link DiagnosiPrincipale }
     * 
     */
    public DiagnosiPrincipale createDiagnosiPrincipale() {
        return new DiagnosiPrincipale();
    }

    /**
     * Create an instance of {@link DiagnosiSecondaria }
     * 
     */
    public DiagnosiSecondaria createDiagnosiSecondaria() {
        return new DiagnosiSecondaria();
    }

    /**
     * Create an instance of {@link Prestazione }
     * 
     */
    public Prestazione createPrestazione() {
        return new Prestazione();
    }

    /**
     * Create an instance of {@link PrestazionePrincipale }
     * 
     */
    public PrestazionePrincipale createPrestazionePrincipale() {
        return new PrestazionePrincipale();
    }

    /**
     * Create an instance of {@link PrestazioneSecondaria }
     * 
     */
    public PrestazioneSecondaria createPrestazioneSecondaria() {
        return new PrestazioneSecondaria();
    }

    /**
     * Create an instance of {@link Dimissione }
     * 
     */
    public Dimissione createDimissione() {
        return new Dimissione();
    }

    /**
     * Create an instance of {@link Ricovero }
     * 
     */
    public Ricovero createRicovero() {
        return new Ricovero();
    }

    /**
     * Create an instance of {@link Trasferimento }
     * 
     */
    public Trasferimento createTrasferimento() {
        return new Trasferimento();
    }

    /**
     * Create an instance of {@link Assistito.OBI }
     * 
     */
    public Assistito.OBI createAssistitoOBI() {
        return new Assistito.OBI();
    }

    /**
     * Create an instance of {@link Importo }
     * 
     */
    public Importo createImporto() {
        return new Importo();
    }

    /**
     * Create an instance of {@link FlsProSoc }
     * 
     */
    public FlsProSoc createFlsProSoc() {
        return new FlsProSoc();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://flussi.mds.it/flsProSoc", name = "Anno", scope = Nascita.class)
    public JAXBElement<String> createNascitaAnno(String value) {
        return new JAXBElement<String>(_NascitaAnno_QNAME, String.class, Nascita.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://flussi.mds.it/flsProSoc", name = "Mese", scope = Nascita.class)
    public JAXBElement<String> createNascitaMese(String value) {
        return new JAXBElement<String>(_NascitaMese_QNAME, String.class, Nascita.class, value);
    }

}
