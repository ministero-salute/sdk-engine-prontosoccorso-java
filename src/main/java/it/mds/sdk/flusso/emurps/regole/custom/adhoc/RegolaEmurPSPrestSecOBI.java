package it.mds.sdk.flusso.emurps.regole.custom.adhoc;

import java.lang.reflect.InvocationTargetException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorValue;

import it.mds.sdk.anagrafiche.client.exceptions.MalformedRegistryException;
import it.mds.sdk.anagrafiche.client.exceptions.RegistryNotFoundException;
import it.mds.sdk.flusso.emurps.parser.regole.Utilities;
import it.mds.sdk.gestoreesiti.modelli.Esito;
import it.mds.sdk.libreriaregole.dtos.RecordDtoGenerico;
import it.mds.sdk.libreriaregole.exception.ValidazioneImpossibileException;
import it.mds.sdk.libreriaregole.regole.beans.Parametri;
import it.mds.sdk.libreriaregole.regole.beans.RegolaGenerica;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@XmlDiscriminatorValue("RegolaEmurPSPrestSecOBI")
public class RegolaEmurPSPrestSecOBI extends RegolaGenerica {
	
	public RegolaEmurPSPrestSecOBI(String nome, String codErrore, String desErrore, Parametri parametri) {
		super(nome, codErrore, desErrore, parametri);
	}

	/**
	 *
	 * @param nomeCampo campo da validare
	 * @param recordDtoGenerico DTO del record del flusso
	 * @return lista di {@link it.mds.sdk.gestoreesiti.modelli.Esito}
	 */
	@Override
	public List<Esito> valida(String nomeCampo, RecordDtoGenerico recordDtoGenerico) {
		List<Esito> listaEsiti = new ArrayList<>();
		try {
			String assistitoDatiAnagraficiResidenzaRegione = (recordDtoGenerico
					.getCampo("assistitoDatiAnagraficiResidenzaRegione") != null
							? (String) recordDtoGenerico.getCampo("assistitoDatiAnagraficiResidenzaRegione")
							: null);
			
			ArrayList<Object> assistitoOBIPrestazioneSecondariaErogata = new ArrayList<>();
			ArrayList<Object> assistitoOBIPrestazioneSecondariaErogataReg = new ArrayList<>();
        	
        	if (recordDtoGenerico.getCampo(nomeCampo) instanceof List) {
        		assistitoOBIPrestazioneSecondariaErogata = (ArrayList<Object>) recordDtoGenerico.getCampo(nomeCampo);        		
        		Iterator it = assistitoOBIPrestazioneSecondariaErogata.iterator();
        		while (it.hasNext()) {
					Object valPrestSecErogata = (Object) it.next();
					assistitoOBIPrestazioneSecondariaErogataReg.add(assistitoDatiAnagraficiResidenzaRegione+"#"+valPrestSecErogata);
				}
        	}if (recordDtoGenerico.getCampo(nomeCampo) instanceof String) {
        		String valoreCampo = (String) recordDtoGenerico.getCampo(nomeCampo);
        		assistitoOBIPrestazioneSecondariaErogata = new ArrayList<>();
        		assistitoOBIPrestazioneSecondariaErogata.add(valoreCampo);
        		assistitoOBIPrestazioneSecondariaErogataReg.add(assistitoDatiAnagraficiResidenzaRegione+"#"+valoreCampo);
        	}
			
        	if (assistitoOBIPrestazioneSecondariaErogata != null && assistitoOBIPrestazioneSecondariaErogata.size()>0) {
        	
				List<String> listaInterventi = Utilities.getValoriAnagrafica(recordDtoGenerico,"ANAG_EMUR_INTERVENTI");
				List<String> listaPrestazioniNaz = Utilities.getValoriAnagrafica(recordDtoGenerico,"ANAG_EMUR_PRESTAZIONI_NAZIONALE");
				List<String> listaPrestazioniReg = Utilities.getValoriAnagrafica(recordDtoGenerico,"ANAG_EMUR_PRESTAZIONI_REGIONALI");
	
				String codErr = "1302";
				String desErr = "Non appartenenza al dominio di riferimento per un campo facoltativo";
				
				ArrayList<Object>lstObiPrestSecErog1 = new ArrayList<>();
				ArrayList<Object>lstObiPrestSecErog2 = new ArrayList<>();
				ArrayList<Object>lstObiPrestSecErog3 = new ArrayList<>();
				
				lstObiPrestSecErog1.addAll(assistitoOBIPrestazioneSecondariaErogata);
				lstObiPrestSecErog2.addAll(assistitoOBIPrestazioneSecondariaErogata);
				lstObiPrestSecErog3.addAll(assistitoOBIPrestazioneSecondariaErogata);
				
					if (assistitoOBIPrestazioneSecondariaErogata != null) {
						
						if (!lstContains(lstObiPrestSecErog1, listaInterventi)) {
							if (!lstContains(lstObiPrestSecErog2,listaPrestazioniNaz)) {
								if (!lstContains(lstObiPrestSecErog3,listaPrestazioniReg)) {
									listaEsiti.add(creaEsitoKO(nomeCampo, codErr, desErr));
									return listaEsiti;
								}
							}
						}
					}
        	}	
			listaEsiti.add(creaEsitoOk(nomeCampo));	
			
		} catch (InvocationTargetException | NoSuchMethodException |IllegalAccessException | MalformedRegistryException |RegistryNotFoundException e) {
			log.error("Non è possibile validare la regola RegolaEmurPSPrestSecOBI " + nomeCampo, e);
			throw new ValidazioneImpossibileException("Non è possibile validare la regola RegolaEmurPSPrestSecOBI " + nomeCampo );
		}catch (DateTimeParseException dataE) {
			listaEsiti.add(creaEsitoOk(nomeCampo));
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

}
