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
@XmlDiscriminatorValue("RegolaEmurPSPrestSec")
public class RegolaEmurPSPrestSec extends RegolaGenerica {
	
	public RegolaEmurPSPrestSec(String nome, String codErrore, String desErrore, Parametri parametri) {
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
			
			ArrayList<Object> listaValoriCampoTracciato = new ArrayList<Object>();
        	
        	if (recordDtoGenerico.getCampo(nomeCampo) instanceof List) {
        		listaValoriCampoTracciato = (ArrayList<Object>) recordDtoGenerico.getCampo(nomeCampo);
        	}if (recordDtoGenerico.getCampo(nomeCampo) instanceof String) {
        		String valoreCampo = (String) recordDtoGenerico.getCampo(nomeCampo);
        		listaValoriCampoTracciato.add(valoreCampo);
        	}
			
//			String assistitoPrestazioniPrestazionePrestazioneSecondaria = (recordDtoGenerico
//					.getCampo("assistitoPrestazioniPrestazionePrestazioneSecondaria") != null
//							? (String) recordDtoGenerico.getCampo("assistitoPrestazioniPrestazionePrestazioneSecondaria")
//							: null);
        	
        	String assistitoDatiAnagraficiResidenzaRegione = (recordDtoGenerico
					.getCampo("assistitoDatiAnagraficiResidenzaRegione") != null
							? (String) recordDtoGenerico.getCampo("assistitoDatiAnagraficiResidenzaRegione")
							: null);
			
			List<String> listaInterventi = Utilities.getValoriAnagrafica(recordDtoGenerico,"ANAG_EMUR_INTERVENTI");
			List<String> listaPrestazioniNaz = Utilities.getValoriAnagrafica(recordDtoGenerico,"ANAG_EMUR_PRESTAZIONI_NAZIONALE");
			List<String> listaPrestazioniReg = Utilities.getValoriAnagrafica(recordDtoGenerico,"ANAG_EMUR_PRESTAZIONI_REGIONALI");

			String codErr = "1302";
			String desErr = "Non appartenenza al dominio di riferimento per un campo facoltativo";
			
			
			ArrayList<Object>lstObiPrestSec = new ArrayList<Object>();
			ArrayList<Object>lstObiPrestSec2 = new ArrayList<Object>();
			ArrayList<Object>lstObiPrestSec3 = new ArrayList<Object>();
			
			Iterator it = listaValoriCampoTracciato.iterator();
			while (it.hasNext()) {
				Object obj = (Object) it.next();
				if (obj instanceof DiagnosiSecondaria) {
					lstObiPrestSec.add(((DiagnosiSecondaria)obj).getValue());
					lstObiPrestSec2.add(((DiagnosiSecondaria)obj).getValue());
					lstObiPrestSec3.add(((DiagnosiSecondaria)obj).getValue());
				}
				if (obj instanceof PrestazioneSecondaria) {
					lstObiPrestSec.add(((PrestazioneSecondaria)obj).getValue());
					lstObiPrestSec2.add(((PrestazioneSecondaria)obj).getValue());
					lstObiPrestSec3.add(assistitoDatiAnagraficiResidenzaRegione+"#"+((PrestazioneSecondaria)obj).getValue());
				}
				
			}					
			
			if (listaValoriCampoTracciato != null && listaValoriCampoTracciato.size() > 0) {
				
				if (!lstContains(lstObiPrestSec, listaInterventi)) {
					if (!lstContains(lstObiPrestSec2,listaPrestazioniNaz)) {
						if (!lstContains(lstObiPrestSec3,listaPrestazioniReg)) {
							listaEsiti.add(creaEsitoKO(nomeCampo, codErr, desErr));
							return listaEsiti;
						}
					}
				}
			}
					
			listaEsiti.add(creaEsitoOk(nomeCampo));	
			
			
		} catch (InvocationTargetException | NoSuchMethodException |IllegalAccessException | MalformedRegistryException |RegistryNotFoundException e) {
			log.error("Non è possibile validare la regola RegolaEmurPSPrestSec " + nomeCampo, e);
			throw new ValidazioneImpossibileException("Non è possibile validare la regola RegolaEmurPSPrestSec " + nomeCampo );
		}catch (DateTimeParseException dataE) {
			listaEsiti.add(creaEsitoOk(nomeCampo));
		}
		return listaEsiti;
	}
	
	private boolean lstContains(ArrayList<Object> lstDaControllare, List<String> lstAnagrafica) {
		boolean ret = false;
		lstDaControllare.removeAll(lstAnagrafica);			
		
		if (lstDaControllare.size() == 0)
			ret = true;
		
		return ret;
	}
	
}
