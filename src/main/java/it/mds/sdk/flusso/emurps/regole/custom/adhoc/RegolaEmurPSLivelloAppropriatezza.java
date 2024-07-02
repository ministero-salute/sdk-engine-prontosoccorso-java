package it.mds.sdk.flusso.emurps.regole.custom.adhoc;

import java.lang.reflect.InvocationTargetException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
@XmlDiscriminatorValue("RegolaEmurPSLivelloAppropriatezza")
public class RegolaEmurPSLivelloAppropriatezza extends RegolaGenerica {
	
	public RegolaEmurPSLivelloAppropriatezza(String nome, String codErrore, String desErrore, Parametri parametri) {
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
			
			String assistitoDimissioneEsitoTrattamento = (recordDtoGenerico
					.getCampo("assistitoDimissioneEsitoTrattamento") != null
							? (String) recordDtoGenerico.getCampo("assistitoDimissioneEsitoTrattamento")
							: null);
			String assistitoOBIEsito = (recordDtoGenerico
					.getCampo("assistitoOBIEsito") != null
							? (String) recordDtoGenerico.getCampo("assistitoOBIEsito")
							: null);
			String assistitoDimissioneLivelloAppropriatezzaAccesso = (recordDtoGenerico
					.getCampo("assistitoDimissioneLivelloAppropriatezzaAccesso") != null
							? ((String) recordDtoGenerico.getCampo("assistitoDimissioneLivelloAppropriatezzaAccesso")).toUpperCase()
							: "");
			
			String codErr2086 = "2086";
			String desErr2086 = "Incongruenza tra Livello Appropriatezza e Esito Trattamento";
			
			String codErr1103 = "1103";
			String desErr1103 = "Il campo è obbligatorio";
			
			
			if (!"10".equals(assistitoDimissioneEsitoTrattamento)) {
				
				if (!"6".equals(assistitoDimissioneEsitoTrattamento) &&
						!"7".equals(assistitoDimissioneEsitoTrattamento)) {
					
					if (StringUtils.isBlank(assistitoDimissioneLivelloAppropriatezzaAccesso)) 	{					
						listaEsiti.add(creaEsitoKO(nomeCampo, codErr1103, desErr1103));
						return listaEsiti;
					}
				}else if (!StringUtils.isBlank(assistitoDimissioneLivelloAppropriatezzaAccesso)) {
					listaEsiti.add(creaEsitoKO(nomeCampo, codErr2086, desErr2086));
					return listaEsiti;
				}
			}else {
				if (!"7".equals(assistitoOBIEsito)) {
					if (StringUtils.isBlank(assistitoDimissioneLivelloAppropriatezzaAccesso)) 	{					
						listaEsiti.add(creaEsitoKO(nomeCampo, codErr1103, desErr1103));
						return listaEsiti;
					}
				}
			}
			
			
			 if (Utilities.verificaEsitoOK(listaEsiti)) {
	        		listaEsiti.clear();
	        		listaEsiti.add(creaEsitoOk(nomeCampo));	
	        	}
			
		} catch (InvocationTargetException | NoSuchMethodException |IllegalAccessException e) {
			log.error("Non è possibile validare la regola RegolaEmurPSLivelloAppropriatezza " + nomeCampo, e);
			throw new ValidazioneImpossibileException("Non è possibile validare la regola RegolaEmurPSLivelloAppropriatezza " + nomeCampo );
		}catch (DateTimeParseException dataE) {
			listaEsiti.add(creaEsitoOk(nomeCampo));
		}
		return listaEsiti;
	}
}
