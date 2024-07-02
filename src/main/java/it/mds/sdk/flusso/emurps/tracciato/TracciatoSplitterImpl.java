/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.flusso.emurps.tracciato;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.xml.sax.SAXException;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import it.mds.sdk.flusso.emurps.parser.regole.RecordDtoPS;
import it.mds.sdk.flusso.emurps.parser.regole.conf.ConfigurazioneFlussoPS;
import it.mds.sdk.flusso.emurps.tracciato.bean.output.Accesso;
import it.mds.sdk.flusso.emurps.tracciato.bean.output.Assistito;
import it.mds.sdk.flusso.emurps.tracciato.bean.output.DatiAnagrafici;
import it.mds.sdk.flusso.emurps.tracciato.bean.output.Diagnosi;
import it.mds.sdk.flusso.emurps.tracciato.bean.output.DiagnosiSecondaria;
import it.mds.sdk.flusso.emurps.tracciato.bean.output.Dimissione;
import it.mds.sdk.flusso.emurps.tracciato.bean.output.Eta;
import it.mds.sdk.flusso.emurps.tracciato.bean.output.FlsProSoc;
import it.mds.sdk.flusso.emurps.tracciato.bean.output.ObjectFactory;
import it.mds.sdk.flusso.emurps.tracciato.bean.output.Prestazione;
import it.mds.sdk.flusso.emurps.tracciato.bean.output.PrestazioneSecondaria;
import it.mds.sdk.flusso.emurps.tracciato.bean.output.Prestazioni;
import it.mds.sdk.libreriaregole.tracciato.TracciatoSplitter;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("tracciatoSplitter")
public class TracciatoSplitterImpl implements TracciatoSplitter<RecordDtoPS> {
    
	private static final String ERR_MSG = "Errore nella generazione del tracciato xml di output";
	private static final String XML_FILENAME_TEMPLATE = "SDK_PS1_PS1_%s_%s.xml" ;

	@Override
    @Deprecated
    public List<Path> dividiTracciato(Path tracciato) {
        return null;
    }

    public static byte[] getBeansAsByteArray(final List<RecordDtoPS> beans) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        OutputStreamWriter streamWriter = new OutputStreamWriter(stream);
        CSVWriter writer = new CSVWriter(streamWriter);
        
        StatefulBeanToCsv<RecordDtoPS> beanToCsv = new StatefulBeanToCsvBuilder<RecordDtoPS>(writer).build();
        try {
			beanToCsv.write(beans);
			streamWriter.flush();
		} catch (CsvDataTypeMismatchException e) {
			log.error(ERR_MSG);
		} catch (CsvRequiredFieldEmptyException e) {
			log.error(ERR_MSG);
		} catch (IOException e) {
			log.error(ERR_MSG);
		}finally {
			try {
				writer.close();
			} catch (IOException e) {
				log.error(ERR_MSG);
			}
			try {
				streamWriter.close();
			} catch (IOException e) {
				log.error(ERR_MSG);
			}
			try {
				stream.close();
			} catch (IOException e) {
				log.error(ERR_MSG);
			}
			
			
		}
       
        
        return stream.toByteArray();
    }
    
    @Override
    public List<Path> dividiTracciato(List<RecordDtoPS> records, String idRun) {
    	
    	
        FlsProSoc flsProSoc = creaFlsProSoc(records);
    	
       ConfigurazioneFlussoPS conf = new ConfigurazioneFlussoPS();
        
        FileOutputStream file = null;
        OutputStreamWriter streamWriter = null;

		String fileName = String.format(XML_FILENAME_TEMPLATE,records.get(0).getCampiInput().getPeriodoRiferimentoInput(), idRun);
		Path xml = Path.of(conf.getXmlOutput().getPercorso(),fileName);
        
		try {
			// Per Verifica XSD
			URL fileXsd = this.getClass().getClassLoader().getResource("EMUR-PS.xsd");
			log.debug("URL dell'XSD per la validazione idrun {} : {}", idRun, fileXsd);
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = schemaFactory.newSchema(fileXsd);

			jakarta.xml.bind.JAXBContext jaxbContext = JAXBContextFactory.createContext(new Class[] { FlsProSoc.class },
					null);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, StandardCharsets.UTF_8.name());
			file = new FileOutputStream(conf.getXmlOutput().getPercorso() + "SDK_PS1_PS1_"
					+ records.get(0).getCampiInput().getPeriodoRiferimentoInput() + "_" + idRun + ".xml");
			streamWriter = new OutputStreamWriter(file);
			jaxbMarshaller.setSchema(schema);
			jaxbMarshaller.marshal(flsProSoc, streamWriter);
			streamWriter.close();

		} catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
			log.error("[{}].dividiTracciato  - records[{}]  - idRun[{}] -" + e.getMessage(), this.getClass().getName(),
					e);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Impossibile validare il csv in ingresso. message: " + e.getMessage());
		} catch (JAXBException | IOException | SAXException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (file != null)
					file.close();
			} catch (IOException e1) {
				log.error(ERR_MSG);
			} finally {
				try {
					if (streamWriter != null)
						streamWriter.close();
				} catch (IOException e) {
					log.error(ERR_MSG);
				} 
			}
		}
        
        return List.of(xml);
        
    }

	public FlsProSoc creaFlsProSoc(List<RecordDtoPS> records) {
		
		/* Doppio passaggio. Riportare gli accettati in csv e caricare 
    	 * i bean di jaxb in automatico (con opencsv)
    	 */
		FlsProSoc flsProSoc = new FlsProSoc(); 
    	if (records != null) {
	    	
	    	InputStream is = new ByteArrayInputStream(getBeansAsByteArray(records));
			InputStreamReader isr = new InputStreamReader(is);
			CSVReader rr = new CSVReader(isr);
			
			try {
				
				List<Accesso> dirList = new CsvToBeanBuilder<Accesso>(rr)
				        .withType(Accesso.class)
				        .withSeparator('~')
				        .withSkipLines(1)   //Salta la prima riga del file CSV
				        .withFieldAsNull(CSVReaderNullFieldIndicator.BOTH)
				        .build()
				        .parse();
				
				flsProSoc.addAllContent(dirList);
				// Manage tag NULL
				cleanTagCentraleOperativa(flsProSoc);
				cleanTagRicovero(flsProSoc);
				cleanTagTrasferimento(flsProSoc);
				cleanTagNascita(flsProSoc);    	
				cleanTagDiagnosi(flsProSoc);
				cleanTagPrestazioni(flsProSoc);
				cleanTagOBI(flsProSoc);
			} catch (IllegalStateException e) {
				log.error("Error:",e);
			}finally {
				try {
					rr.close();
				} catch (IOException e) {
					log.error(ERR_MSG);
				} finally {
					try {
						isr.close();
					} catch (IOException e) {
						log.error(ERR_MSG);
					} finally {
						try {
							is.close();
						} catch (IOException e) {
							log.error(ERR_MSG);
						}
					}
				}
			}
    	}
		return flsProSoc;
	}

    public FlsProSoc creaFlsProSoc(List<RecordDtoPS> records, FlsProSoc flsProSoc) {

        if (flsProSoc == null)
        	flsProSoc = creaFlsProSoc(records);

        	return flsProSoc;
    }
    
    private void cleanTagCentraleOperativa(FlsProSoc flsProSoc) {
		// set null oggetto Centrale Operativa se campi null
    	Stream<Accesso> filtroAccesso = flsProSoc.getContent().stream().filter(
    								a -> (a.getCentraleOperativa() != null && 
    								StringUtils.isBlank(a.getCentraleOperativa().getCodiceMissione()) &&
    								StringUtils.isBlank(a.getCentraleOperativa().getIdentificativo()) ) );
    	Stream<Accesso> streamCentraleOperativa = filtroAccesso.map(m -> { 
    		m.setCentraleOperativa(null);	return m; });
    	
    	streamCentraleOperativa.collect(Collectors.toList());
    	streamCentraleOperativa.close();
    	filtroAccesso.close();
		
	}
    
    private void cleanTagRicovero(FlsProSoc flsProSoc) {
		// set null oggetto 
    	Stream<Accesso> filtroAccesso = flsProSoc.getContent().stream().filter(
    								a -> (a.getAssistito() != null && 
    										a.getAssistito().getDimissione() != null  &&
    										a.getAssistito().getDimissione().getRicovero() != null) &&
		    								StringUtils.isBlank(a.getAssistito().getDimissione().getRicovero().getProgressivo()) &&
		    								StringUtils.isBlank(a.getAssistito().getDimissione().getRicovero().getCodiceSpecialitaReparto()));
    	Stream<Dimissione> streamRicovero = filtroAccesso.map(Accesso::getAssistito).map(Assistito::getDimissione)
    			.map(m -> { m.setRicovero(null);	return m; });    			
    	
    	streamRicovero.collect(Collectors.toList());
    	streamRicovero.close();
    	filtroAccesso.close();
	}
    
    private void cleanTagTrasferimento(FlsProSoc flsProSoc) {
		// set null oggetto 
    	Stream<Accesso> filtroAccesso = flsProSoc.getContent().stream().filter(
    								a -> (a.getAssistito() != null && 
    										a.getAssistito().getDimissione() != null  &&
    										a.getAssistito().getDimissione().getTrasferimento() != null) &&
		    								StringUtils.isBlank(a.getAssistito().getDimissione().getTrasferimento().getMotivo()) &&
		    								StringUtils.isBlank(a.getAssistito().getDimissione().getTrasferimento().getIstituto()));
    	Stream<Dimissione> streamTrasferimento = filtroAccesso.map(Accesso::getAssistito).map(Assistito::getDimissione)
    			.map(m -> { m.setTrasferimento(null);	return m; });    			
    	
    	streamTrasferimento.collect(Collectors.toList());
    	streamTrasferimento.close();
    	filtroAccesso.close();
	}
    
	private void cleanTagNascita(FlsProSoc flsProSoc) {
		// set null oggetto Nascita se anno e mese non valorizzati
    	Stream<Accesso> filtroAccesso = flsProSoc.getContent().stream().filter(
    								a -> (a.getAssistito() != null && 
    										a.getAssistito().getDatiAnagrafici() != null && 
    										a.getAssistito().getDatiAnagrafici().getEta() != null &&
    										a.getAssistito().getDatiAnagrafici().getEta().getNascita() != null &&
    										a.getAssistito().getDatiAnagrafici().getEta().getNascita().getAnno()==null &&
    										a.getAssistito().getDatiAnagrafici().getEta().getNascita().getMese()==null) );
    	Stream<Eta> streamNascita = filtroAccesso.map(Accesso::getAssistito).map(Assistito::getDatiAnagrafici)
    			.map(DatiAnagrafici::getEta).map(m -> { m.setNascita(null);	return m; });
    	
    	streamNascita.collect(Collectors.toList());
    	streamNascita.close();
    	filtroAccesso.close();
	}
	
	private void cleanTagDiagnosi(FlsProSoc flsProSoc) {
		// set null oggetto DiagnosiSecondaria se vuoto
		
		// diagnosiSecondaria
    	Stream<Accesso> filtroAccesso = flsProSoc.getContent().stream().filter(
    								a -> (a.getAssistito() != null && 
    										a.getAssistito().getPrestazioni() != null &&
    										a.getAssistito().getPrestazioni().getDiagnosi() != null &&
    										a.getAssistito().getPrestazioni().getDiagnosi().getDiagnosiSecondaria() != null) );
    	Stream<Diagnosi> streamDiagnosi = filtroAccesso.map(Accesso::getAssistito).map(Assistito::getPrestazioni)
    			.map(Prestazioni::getDiagnosi).map(m -> { 
    												if (m.getDiagnosiSecondaria() != null && 
    														!m.getDiagnosiSecondaria().isEmpty()) {
    													boolean vuoto = false;
    													Iterator<DiagnosiSecondaria> it = m.getDiagnosiSecondaria().iterator();
    													while (it.hasNext()) {
															DiagnosiSecondaria diagnosiSecondaria = it.next();
															
															if (StringUtils.isEmpty(diagnosiSecondaria.getValue())){
																	vuoto = true;
																	break;
															}
														}
    													if (vuoto)
    														m.setDiagnosiSecondaria(null);
    												}
    												
    												return m; 
    												});
    	
    	streamDiagnosi.collect(Collectors.toList());
    	streamDiagnosi.close();
    	filtroAccesso.close();

    	// DiagnosiPrincipale
    	Stream<Accesso> filtroAccesso2 = flsProSoc.getContent().stream().filter(
				a -> (a.getAssistito() != null && 
						a.getAssistito().getPrestazioni() != null &&
						a.getAssistito().getPrestazioni().getDiagnosi() != null &&
						a.getAssistito().getPrestazioni().getDiagnosi().getDiagnosiPrincipale() != null) );
    	Stream<Diagnosi> streamDiagnosi2 = filtroAccesso2.map(Accesso::getAssistito).map(Assistito::getPrestazioni)
    			.map(Prestazioni::getDiagnosi).map(m -> { 
    												if (m.getDiagnosiPrincipale() != null && 
    													 StringUtils.isEmpty(m.getDiagnosiPrincipale().getValue())) {
    													m.setDiagnosiPrincipale(null);
    												}
    												
    												return m; 
    												});
    	streamDiagnosi2.collect(Collectors.toList());
    	streamDiagnosi2.close();
    	filtroAccesso2.close();
    	
    	// Diagnosi
    	Stream<Accesso> filtroAccesso3 = flsProSoc.getContent().stream().filter(
				a -> (a.getAssistito() != null && 
						a.getAssistito().getPrestazioni() != null &&
						a.getAssistito().getPrestazioni().getDiagnosi() != null &&
						a.getAssistito().getPrestazioni().getDiagnosi().getDiagnosiPrincipale() == null &&
						a.getAssistito().getPrestazioni().getDiagnosi().getDiagnosiSecondaria() == null ) );
    	Stream<Prestazioni> streamDiagnosi3 = filtroAccesso3.map(Accesso::getAssistito).map(Assistito::getPrestazioni).map(m -> { 
    													m.setDiagnosi(null);
    												return m; 
    												});
    	streamDiagnosi3.collect(Collectors.toList());
    	streamDiagnosi3.close();
    	filtroAccesso3.close();
	}
	
	private void cleanTagPrestazioni(FlsProSoc flsProSoc) {
		// set null oggetto 
		
		//prestazioneSecondaria
    	Stream<Accesso> filtroAccesso = flsProSoc.getContent().stream().filter(
    								a -> (a.getAssistito() != null && 
    										a.getAssistito().getPrestazioni() != null &&
    										a.getAssistito().getPrestazioni().getPrestazione() != null &&
    										a.getAssistito().getPrestazioni().getPrestazione().getPrestazioneSecondaria() != null) );
    	Stream<Prestazione> streamPrestazione = filtroAccesso.map(Accesso::getAssistito).map(Assistito::getPrestazioni)
    			.map(Prestazioni::getPrestazione).map(m -> { 
    												if (m.getPrestazioneSecondaria() != null && 
    														!m.getPrestazioneSecondaria().isEmpty()) {
    													boolean vuoto = false;
    													Iterator<PrestazioneSecondaria> it = m.getPrestazioneSecondaria().iterator();
    													while (it.hasNext()) {
															PrestazioneSecondaria prestazioneSecondaria = it.next();
															
															if (StringUtils.isEmpty(prestazioneSecondaria.getValue())){
																	vuoto = true;
																	break;
															}
														}
    													if (vuoto)
    														m.setPrestazioneSecondaria(null);
    												}
    												
    												return m; 
    												});
    	
    	streamPrestazione.collect(Collectors.toList());
    	streamPrestazione.close();
    	filtroAccesso.close();
    	
    	// prestazionePrincipale
    	Stream<Accesso> filtroAccesso2 = flsProSoc.getContent().stream().filter(
				a -> (a.getAssistito() != null && 
						a.getAssistito().getPrestazioni() != null &&
						a.getAssistito().getPrestazioni().getPrestazione() != null &&
						a.getAssistito().getPrestazioni().getPrestazione().getPrestazionePrincipale() != null) );
		Stream<Prestazione> streamPrestazione2 = filtroAccesso2.map(Accesso::getAssistito).map(Assistito::getPrestazioni)
		.map(Prestazioni::getPrestazione).map(m -> { 
												if (m.getPrestazionePrincipale() != null && 
														 StringUtils.isEmpty(m.getPrestazionePrincipale().getValue())) {
														m.setPrestazionePrincipale(null);
													}
										
										return m; 
										});
		
		streamPrestazione2.collect(Collectors.toList());
		streamPrestazione2.close();
		filtroAccesso2.close();
		
		// Prestazione
    	Stream<Accesso> filtroAccesso3 = flsProSoc.getContent().stream().filter(
				a -> (a.getAssistito() != null && 
						a.getAssistito().getPrestazioni() != null &&
						a.getAssistito().getPrestazioni().getPrestazione() != null &&
						a.getAssistito().getPrestazioni().getPrestazione().getPrestazionePrincipale() == null &&
						a.getAssistito().getPrestazioni().getPrestazione().getPrestazioneSecondaria() == null ) );
    	Stream<Prestazioni> streamDiagnosi3 = filtroAccesso3.map(Accesso::getAssistito).map(Assistito::getPrestazioni).map(m -> { 
    													m.setPrestazione(null);
    												return m; 
    												});
    	streamDiagnosi3.collect(Collectors.toList());
    	streamDiagnosi3.close();
    	filtroAccesso3.close();
    	
    	
    	// Prestazione
    	Stream<Accesso> filtroAccesso4 = flsProSoc.getContent().stream().filter(
				a -> (a.getAssistito() != null && 
						a.getAssistito().getPrestazioni() != null &&						
						a.getAssistito().getPrestazioni().getPresaInCarico() != null &&
						StringUtils.isBlank(a.getAssistito().getPrestazioni().getPresaInCarico().getOra()) &&
						a.getAssistito().getPrestazioni().getPresaInCarico().getData() == null) );
    	Stream<Prestazioni> streamDiagnosi4 = filtroAccesso4.map(Accesso::getAssistito).map(Assistito::getPrestazioni).map(m -> { 
    													m.setPresaInCarico(null);
    												return m; 
    												});
    	streamDiagnosi4.collect(Collectors.toList());
    	streamDiagnosi4.close();
    	filtroAccesso4.close();
	}
	
	
	
	private void cleanTagOBI(FlsProSoc flsProSoc) {
		// set null oggetto 
		
		//prestazioneSecondaria
    	Stream<Accesso> filtroAccesso = flsProSoc.getContent().stream().filter(
    								a -> (a.getAssistito() != null
    										&& a.getAssistito().getOBI() != null
    										&& a.getAssistito().getOBI().getProblemaClinicoPrincipale() == null
    										&& a.getAssistito().getOBI().getDataIngresso() == null
    										&& a.getAssistito().getOBI().getOraIngresso() == null
    										&& a.getAssistito().getOBI().getPrimaConsulenzaSpecDis() == null
    										&& a.getAssistito().getOBI().getPrimaConsulenzaSpecNum() == null
    										&& a.getAssistito().getOBI().getSecondaConsulenzaSpecDis() == null
    										&& a.getAssistito().getOBI().getSecondaConsulenzaSpecNum() == null
    										&& a.getAssistito().getOBI().getEsito() == null
    										&& a.getAssistito().getOBI().getPrestazionePrincipaleErogata() == null
    										&& a.getAssistito().getOBI().getPrestazioneSecondariaErogata().isEmpty()
    										&& a.getAssistito().getOBI().getDiagnosiPrincipaleUscita() == null
    										&& a.getAssistito().getOBI().getDiagnosiSecondariaUscita().isEmpty()
    										&& a.getAssistito().getOBI().getDataUscita() == null
    										&& a.getAssistito().getOBI().getOraUscita() == null
    										));
    	Stream<Assistito> streamPrestazione = filtroAccesso.map(Accesso::getAssistito).map(m -> { 
			m.setOBI(null);
		return m; 
		});
    	
    	
    	streamPrestazione.collect(Collectors.toList());
    	streamPrestazione.close();
    	filtroAccesso.close();
    	
	}
	
    public ConfigurazioneFlussoPS getConfigurazione() {
        return new ConfigurazioneFlussoPS();
    }

	
}
