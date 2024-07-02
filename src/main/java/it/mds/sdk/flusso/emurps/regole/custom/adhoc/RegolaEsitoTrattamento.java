package it.mds.sdk.flusso.emurps.regole.custom.adhoc;

import java.lang.reflect.InvocationTargetException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorValue;

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
@XmlDiscriminatorValue("RegolaEsitoTrattamento")
public class RegolaEsitoTrattamento extends RegolaGenerica {
	
	public RegolaEsitoTrattamento(String nome, String codErrore, String desErrore, Parametri parametri) {
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
		List<Esito> listaEsiti2067 = new ArrayList<>();
		List<Esito> listaEsiti2078 = new ArrayList<>();
		try {
			
			String assistitoDimissioneEsitoTrattamento = (recordDtoGenerico
					.getCampo("assistitoDimissioneEsitoTrattamento") != null
							? (String) recordDtoGenerico.getCampo("assistitoDimissioneEsitoTrattamento")
							: null);
			String assistitoDimissioneLivelloAppropriatezzaAccesso = (recordDtoGenerico
					.getCampo("assistitoDimissioneLivelloAppropriatezzaAccesso") != null
							? (String) recordDtoGenerico.getCampo("assistitoDimissioneLivelloAppropriatezzaAccesso")
							: null);
			
			String triage = (recordDtoGenerico
					.getCampo("triage") != null
							? (String) recordDtoGenerico.getCampo("triage")
							: null);
			
			
			String codErr2067 = "2067";
			String desErr2067 = "Il campo assistitoDimissioneEsitoTrattamento é incoerente con assistitoDimissioneLivelloAppropriatezzaAccesso";
			String codErr2078 = "2078";
			String desErr2078 = "Il campo assistitoDimissioneEsitoTrattamento é incoerente con assistitoDimissioneLivelloAppropriatezzaAccesso";
			
			if ("N".equals(triage) && !"9".equals(assistitoDimissioneEsitoTrattamento)) {
				listaEsiti2067.add(creaEsitoKO(nomeCampo, codErr2067, desErr2067));
				Utilities.addErrore(listaEsiti,listaEsiti2067);
				if (Utilities.verificaEsitoOK(listaEsiti)) 
					listaEsiti.clear();
			}
			
			if ("9".equals(assistitoDimissioneEsitoTrattamento) && !"N".equals(assistitoDimissioneLivelloAppropriatezzaAccesso)) {
				listaEsiti2078.add(creaEsitoKO(nomeCampo, codErr2078, desErr2078));
				Utilities.addErrore(listaEsiti,listaEsiti2078);
				if (Utilities.verificaEsitoOK(listaEsiti)) 
					listaEsiti.clear();
			}
			
			if (Utilities.verificaEsitoOK(listaEsiti)) {
				listaEsiti.clear();
				listaEsiti.add(creaEsitoOk(nomeCampo));
			}	
				
			
		} catch (InvocationTargetException | NoSuchMethodException |IllegalAccessException e) {
			log.error("Non è possibile validare la regola RegolaEsitoTrattamento " + nomeCampo, e);
			throw new ValidazioneImpossibileException("Non è possibile validare la regola RegolaEsitoTrattamento " + nomeCampo );
		}catch (DateTimeParseException dataE) {
			listaEsiti.add(creaEsitoOk(nomeCampo));
		}
		return listaEsiti;
	}
	


}
