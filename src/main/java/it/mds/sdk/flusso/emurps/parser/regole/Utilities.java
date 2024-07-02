/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.flusso.emurps.parser.regole;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import it.mds.sdk.anagrafiche.client.exceptions.MalformedRegistryException;
import it.mds.sdk.anagrafiche.client.exceptions.RegistryNotFoundException;
import it.mds.sdk.connettore.anagrafiche.gestore.anagrafica.GestoreAnagrafica;
import it.mds.sdk.connettore.anagrafiche.tabella.RecordAnagrafica;
import it.mds.sdk.gestoreesiti.modelli.ErroreValidazione;
import it.mds.sdk.gestoreesiti.modelli.Esito;
import it.mds.sdk.libreriaregole.dtos.RecordDtoGenerico;

public final class Utilities {
	
//	public static final DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	public static final DateTimeFormatter formatterDataOra = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); 
    
	public static List<String> getValoriAnagrafica(RecordDtoGenerico recordDtoGenerico,String nomeTabella, String valoreDaEstrarre) throws MalformedRegistryException, RegistryNotFoundException {
	
		String annoRif = (recordDtoGenerico.getCampiInput().getAnnoRiferimentoInput() != null
				? (String) recordDtoGenerico.getCampiInput().getAnnoRiferimentoInput()
				: null);
		LocalDateTime ldDataRif = LocalDateTime.parse(annoRif+"-01-01 00:00", formatterDataOra);

		GestoreAnagrafica gestoreAnagrafica = new GestoreAnagrafica();
		List<RecordAnagrafica> listaValori = null;
		if (StringUtils.isBlank(valoreDaEstrarre))
			listaValori = gestoreAnagrafica.richiediAnagrafica(nomeTabella, false)			
				.getRecordsAnagrafica();
		else
			listaValori = gestoreAnagrafica.richiediAnagrafica(nomeTabella, valoreDaEstrarre, false)			
			.getRecordsAnagrafica();
		
		// recupero il dominio dei valori validi
		List<String> listaDominio;

		listaDominio = listaValori.stream()
				.filter(ra -> (ra.getValidoDa() != null && ra.getValidoA() != null
						&& (ra.getValidoDa().compareTo(ldDataRif)
								* ldDataRif.compareTo(ra.getValidoA())) >= 0)
						|| (ra.getValidoDa() == null && ra.getValidoA() == null))
				.map(RecordAnagrafica::getDato).collect(Collectors.toList());
		return listaDominio;
	}
	
	public static List<String> getValoriAnagrafica(RecordDtoGenerico recordDtoGenerico,String nomeTabella) throws MalformedRegistryException, RegistryNotFoundException {
		
		String annoRif = (recordDtoGenerico.getCampiInput().getAnnoRiferimentoInput() != null
				? (String) recordDtoGenerico.getCampiInput().getAnnoRiferimentoInput()
				: null);
		LocalDateTime ldDataRif = LocalDateTime.parse(annoRif+"-01-01 00:00", formatterDataOra);			
		
		GestoreAnagrafica gestoreAnagrafica = new GestoreAnagrafica();
		
		List<RecordAnagrafica> listaValori = gestoreAnagrafica.richiediAnagrafica(nomeTabella, false)			
			.getRecordsAnagrafica();
		
		// recupero il dominio dei valori validi
		List<String> listaDominio;

		listaDominio = listaValori.stream()
				.filter(ra -> (ra.getValidoDa() != null && ra.getValidoA() != null
						&& (ra.getValidoDa().compareTo(ldDataRif)
								* ldDataRif.compareTo(ra.getValidoA())) >= 0)
						|| (ra.getValidoDa() == null && ra.getValidoA() == null))
				.map(RecordAnagrafica::getDato).collect(Collectors.toList());
		return listaDominio;
	}
	
    public static boolean verificaEsitoOK(List<Esito> lstCond) {
		Iterator it = lstCond.iterator();
		while (it.hasNext()) {
			Esito esito = (Esito) it.next();
			if (!esito.isValoreEsito())
				return false;
		}

		return true;
	}

    public static void addErrore(List<Esito> listaEsiti, List<Esito> lstCond) {
		//Iterator it = lstCond.iterator();
    	Esito esitoInterno=null;
    	List<ErroreValidazione> erroriValidazioneInterni=null;
    	
    	if (lstCond.size()>0) {
    		esitoInterno = (Esito) lstCond.get(0);
    		erroriValidazioneInterni = esitoInterno.getErroriValidazione();
    	}
		if (listaEsiti.size()>0) {
			List<ErroreValidazione> erroriValidazione = listaEsiti.get(0).getErroriValidazione();
			if(erroriValidazioneInterni != null) {
				erroriValidazione.addAll(erroriValidazioneInterni);
				if (erroriValidazioneInterni.size()>0)
					listaEsiti.get(0).setValoreEsito(false);
			}
		}else {			
			listaEsiti.add(esitoInterno);
		}
	}
    
    public static boolean checkList(List<String> lst) {
    	if (lst!= null) {
    		Iterator it = lst.iterator();
    		while (it.hasNext()) {
				String str = (String) it.next();
				if (!StringUtils.isBlank(str))
					return true;
			}
    	}
        return false;            
    }
        
}
