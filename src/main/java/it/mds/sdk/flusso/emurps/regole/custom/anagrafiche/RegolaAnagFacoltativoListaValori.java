package it.mds.sdk.flusso.emurps.regole.custom.anagrafiche;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import it.mds.sdk.connettore.anagrafiche.tabella.TabellaAnagrafica;
import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorValue;

import it.mds.sdk.anagrafiche.client.exceptions.MalformedRegistryException;
import it.mds.sdk.anagrafiche.client.exceptions.RegistryNotFoundException;
import it.mds.sdk.connettore.anagrafiche.gestore.anagrafica.GestoreAnagrafica;
import it.mds.sdk.connettore.anagrafiche.tabella.RecordAnagrafica;
import it.mds.sdk.flusso.emurps.parser.regole.Utilities;
import it.mds.sdk.flusso.emurps.tracciato.bean.output.DiagnosiSecondaria;
import it.mds.sdk.flusso.emurps.tracciato.bean.output.PrestazioneSecondaria;
import it.mds.sdk.gestoreesiti.modelli.Esito;
import it.mds.sdk.libreriaregole.dtos.RecordDtoGenerico;
import it.mds.sdk.libreriaregole.exception.ValidazioneImpossibileException;
import it.mds.sdk.libreriaregole.regole.beans.Parametri;
import it.mds.sdk.libreriaregole.regole.beans.RegolaGenerica;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@XmlDiscriminatorValue("RegolaAnagFacoltativoListaValori")
public class RegolaAnagFacoltativoListaValori extends RegolaGenerica{

	public RegolaAnagFacoltativoListaValori(String nome, String codErrore, String desErrore, Parametri parametri) {
		super(nome, codErrore, desErrore, parametri);
	}

	
	/**
	 *
	 * Controlla che il valore del campo in input "nomeCampo"  sia contenuto in un dominio di valori di anagrafica
	 * @param nomeCampo campo da validare
	 * @param recordDtoGenerico DTO del record del flusso
	 * @return lista di {@link Esito}
	 */
	@Override
	public List<Esito> valida(String nomeCampo, RecordDtoGenerico recordDtoGenerico) {
		List<Esito> listaEsiti = new ArrayList<>();
		try {
//			String campoDaValidare = String.valueOf(recordDtoGenerico.getCampo(nomeCampo));
			String nomeTabella = this.getParametri().getParametriMap().get("nomeTabella");

//			if ("assistitoPrestazioniDiagnosiDiagnosiSecondaria".equalsIgnoreCase(nomeCampo)) {
//				System.out.println("ddd");
//			}
			//GestoreAnagrafica gestoreAnagrafica = new GestoreAnagrafica();
			//TabellaAnagrafica tabellaAnagrafica = gestoreAnagrafica.richiediAnagrafica(nomeTabella,false);
//			List<RecordAnagrafica> listaValori = tabellaAnagrafica.getRecordsAnagrafica();
//			//recupero il dominio dei valori validi
//			List<String> listaDominio;
//			
//			listaDominio =listaValori
//					  .stream()
//					  .filter(ra -> (ra.getValidoDa()!= null && ra.getValidoA()!=null && (ra.getValidoDa().compareTo(LocalDateTime.now()) * LocalDateTime.now().compareTo(ra.getValidoA())) >= 0)
//							  		|| (ra.getValidoDa()==null && ra.getValidoA()==null) )
//					  .map(RecordAnagrafica::getDato)
//					  .collect(Collectors.toList());
			
			List<String> listaDominio = Utilities.getValoriAnagrafica(recordDtoGenerico,nomeTabella);
			
			Iterator it1 = listaDominio.iterator();
			
//			while (it1.hasNext()) {
//				Object obj = (Object) it1.next();
//				if (obj instanceof DiagnosiSecondaria) {
//					if (((DiagnosiSecondaria)obj).getValue().equals("460"))
//						System.out.println("460");
//				}
//				if (obj instanceof PrestazioneSecondaria) {
//					if (((PrestazioneSecondaria)obj).getValue().equals("460"))
//						System.out.println("460");					
//				}
//				
//			}
			
			ArrayList<String> listaValoriTracciato = new ArrayList<String>();	
			
			if (recordDtoGenerico.getCampo(nomeCampo) instanceof List) {
				listaValoriTracciato = (ArrayList<String>) recordDtoGenerico.getCampo(nomeCampo);
        	}if (recordDtoGenerico.getCampo(nomeCampo) instanceof String) {
        		String valoreCampo = (String) recordDtoGenerico.getCampo(nomeCampo);
        		listaValoriTracciato.add(valoreCampo);
        	}
        	
        	ArrayList<Object>lstValoriDaValidare1 = new ArrayList<Object>();			
			
			Iterator it = listaValoriTracciato.iterator();
			while (it.hasNext()) {
				Object obj = (Object) it.next();
				if (obj instanceof DiagnosiSecondaria) {
					lstValoriDaValidare1.add(((DiagnosiSecondaria)obj).getValue());	
				}
				if (obj instanceof PrestazioneSecondaria) {
					lstValoriDaValidare1.add(((PrestazioneSecondaria)obj).getValue());	
				}
				
			}
				
			if (listaValoriTracciato != null && listaValoriTracciato.size() > 0) {				
				if(!lstContains(lstValoriDaValidare1, listaDominio)){
					listaEsiti.add(creaEsitoKO(nomeCampo,this.getCodErrore(),this.getDesErrore()));
					return listaEsiti;
				}
			}

				
			listaEsiti.add(creaEsitoOk(nomeCampo));
			
			
		} catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException | MalformedRegistryException | RegistryNotFoundException e) {
			log.error("Impossibile validare la regola dominio valori anagrafica per il campo " + nomeCampo, e );
			throw new ValidazioneImpossibileException("Impossibile validare la regola dominio valori anagrafica per il campo " + nomeCampo );
		}
		return listaEsiti;

	}
	
	// return true se tutti i valori sono contenuti in anagrafica
		// return false se almeno un valore non è contenuto in anagrafica
		private boolean lstContains(ArrayList<Object> lstDaControllare, List<String> lstAnagrafica) {
			boolean ret = false;
			lstDaControllare.removeAll(lstAnagrafica);			
			
			if (lstDaControllare.size() == 0)
				ret = true;
			
			return ret;
		}

	public GestoreAnagrafica getGestoreAnagrafica() {
		return new GestoreAnagrafica();
	}

}
