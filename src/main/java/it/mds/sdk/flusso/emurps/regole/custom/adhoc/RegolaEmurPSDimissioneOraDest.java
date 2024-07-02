package it.mds.sdk.flusso.emurps.regole.custom.adhoc;

import java.lang.reflect.InvocationTargetException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorValue;

import it.mds.sdk.gestoreesiti.modelli.Esito;
import it.mds.sdk.libreriaregole.dtos.RecordDtoGenerico;
import it.mds.sdk.libreriaregole.exception.ValidazioneImpossibileException;
import it.mds.sdk.libreriaregole.regole.beans.Parametri;
import it.mds.sdk.libreriaregole.regole.beans.RegolaGenerica;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@XmlDiscriminatorValue("RegolaEmurPSDimissioneOraDest")
public class RegolaEmurPSDimissioneOraDest extends RegolaGenerica {
	
	public static final DateTimeFormatter formatterAnnoNascita = DateTimeFormatter.ofPattern("yyyy");
	public static final DateTimeFormatter formatterEntrataPS = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public RegolaEmurPSDimissioneOraDest(String nome, String codErrore, String desErrore, Parametri parametri) {
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
			
			String assistitoDimissioneOraDest = (recordDtoGenerico
					.getCampo("assistitoDimissioneOraDest") != null
							? (String) recordDtoGenerico.getCampo("assistitoDimissioneOraDest")
							: null);
			
			
			String codErr1 = "1103";
			String desErr1 = "Mancata valorizzazione di un campo ad obbligatorietà condizionata";
			String codErr2 = "1004";
			String desErr2 = "Incongruenza tra Esito Trattamento e ora di Destinazione";
//			String codErr3 = "1005";
//			String desErr3 = "L’ora di destinazione non è congruente con l'esito del trattamento (esito trattamento non valorizzato correttamente)";
			
			if ( "1".equals(assistitoDimissioneEsitoTrattamento) ||
					"2".equals(assistitoDimissioneEsitoTrattamento) ||
					"3".equals(assistitoDimissioneEsitoTrattamento) ||
					"10".equals(assistitoDimissioneEsitoTrattamento) ||
					"11".equals(assistitoDimissioneEsitoTrattamento)) {
				if (StringUtils.isBlank(assistitoDimissioneOraDest)) {
					listaEsiti.add(creaEsitoKO(nomeCampo, codErr1, desErr1));
					return listaEsiti;
				}
			}else {
				if ( "4".equals(assistitoDimissioneEsitoTrattamento) ||
						"5".equals(assistitoDimissioneEsitoTrattamento) ||
						"6".equals(assistitoDimissioneEsitoTrattamento) ||
						"7".equals(assistitoDimissioneEsitoTrattamento) ||
						"8".equals(assistitoDimissioneEsitoTrattamento) ||
						"9".equals(assistitoDimissioneEsitoTrattamento)) {
					if (!StringUtils.isBlank(assistitoDimissioneOraDest)) {
						listaEsiti.add(creaEsitoKO(nomeCampo, codErr2, desErr2));
						return listaEsiti;
					}
				}
				// TODOs: FB - ANOMALIA - COMMENTATA
//				else {
//					listaEsiti.add(creaEsitoKO(nomeCampo, codErr3, desErr3));
//					return listaEsiti;
//				}
			}
			
			listaEsiti.add(creaEsitoOk(nomeCampo));	
			
		} catch (InvocationTargetException | NoSuchMethodException |IllegalAccessException  e) {
				log.error("Non è possibile validare la regola RegolaEmurPSMesePresenzaCondizionataCampo " + nomeCampo, e);
			throw new ValidazioneImpossibileException("Non è possibile validare la regola RegolaEmurPSMesePresenzaCondizionataCampo " + nomeCampo );
		}catch (DateTimeParseException dataE) {
			listaEsiti.add(creaEsitoOk(nomeCampo));
		}
		return listaEsiti;
	}

	
	

}
