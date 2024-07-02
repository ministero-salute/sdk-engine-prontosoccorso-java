package it.mds.sdk.flusso.emurps.regole.custom.adhoc;

import java.lang.reflect.InvocationTargetException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorValue;

import it.mds.sdk.flusso.emurps.parser.regole.Utilities;
import it.mds.sdk.flusso.emurps.regole.custom.RegolaDataPosterioreOUguale;
import it.mds.sdk.flusso.emurps.regole.custom.RegolaIncongruenzaCrossDueCampi;
import it.mds.sdk.gestoreesiti.modelli.Esito;
import it.mds.sdk.libreriaregole.dtos.RecordDtoGenerico;
import it.mds.sdk.libreriaregole.exception.ValidazioneImpossibileException;
import it.mds.sdk.libreriaregole.regole.beans.Parametri;
import it.mds.sdk.libreriaregole.regole.beans.RegolaGenerica;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@XmlDiscriminatorValue("RegolaEmurPSOBIDataIngresso")
public class RegolaEmurPSOBIDataIngresso extends RegolaGenerica {
	
	private static final String ASSISTITO_DIMISSIONE_ESITO_TRATTAMENTO = "assistitoDimissioneEsitoTrattamento";
	public static final DateTimeFormatter formatterAnnoNascita = DateTimeFormatter.ofPattern("yyyy");
	public static final DateTimeFormatter formatterEntrataPS = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public RegolaEmurPSOBIDataIngresso(String nome, String codErrore, String desErrore, Parametri parametri) {
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
					.getCampo(ASSISTITO_DIMISSIONE_ESITO_TRATTAMENTO) != null
							? ((String) recordDtoGenerico.getCampo(ASSISTITO_DIMISSIONE_ESITO_TRATTAMENTO))
							: null);
			
			Parametri parametri = new Parametri();
			parametri.getParametriMap().put("parametroCampoCondizionante", "10");
			parametri.getParametriMap().put("nomeCampoCondizionante", ASSISTITO_DIMISSIONE_ESITO_TRATTAMENTO);

			RegolaIncongruenzaCrossDueCampi regolaIncongruenzaCrossDueCampi = new RegolaIncongruenzaCrossDueCampi(
					"regolaIncongruenzaCrossDueCampi", "1122", null, parametri);

			List<Esito> lstCond = regolaIncongruenzaCrossDueCampi.valida(nomeCampo, recordDtoGenerico);		
			Utilities.addErrore(listaEsiti,lstCond);
			if (Utilities.verificaEsitoOK(listaEsiti)) 
				listaEsiti.clear();
			
			
			if ("10".equals(assistitoDimissioneEsitoTrattamento)) {
				
				parametri = new Parametri();
				parametri.getParametriMap().put("nomeCampoValidatore", "assistitoPrestazioniPresaInCaricoData");
				String desErrore = "Il campo " + nomeCampo
						+ " deve essere maggiore o uguale al campo assistitoPrestazioniPresaInCaricoData";
				RegolaDataPosterioreOUguale regolaDataPosterioreOUguale = new RegolaDataPosterioreOUguale(
						"regolaDataPosterioreOUguale", "1008", desErrore, parametri);
				
				List<Esito> lst = regolaDataPosterioreOUguale.valida(nomeCampo, recordDtoGenerico);		
				Utilities.addErrore(listaEsiti,lst);
				if (Utilities.verificaEsitoOK(listaEsiti)) {
					listaEsiti.clear();
					listaEsiti.add(creaEsitoOk(nomeCampo));
				}
								
			}else {
			
				if (Utilities.verificaEsitoOK(listaEsiti)) {
					listaEsiti.clear();
					listaEsiti.add(creaEsitoOk(nomeCampo));
				}
			}
		} catch (InvocationTargetException | NoSuchMethodException |IllegalAccessException  e) {
				log.error("Non è possibile validare la regola RegolaEmurPSMesePresenzaCondizionataCampo " + nomeCampo, e);
			throw new ValidazioneImpossibileException("Non è possibile validare la regola RegolaEmurPSMesePresenzaCondizionataCampo " + nomeCampo );
		}catch (DateTimeParseException dataE) {
			listaEsiti.add(creaEsitoOk(nomeCampo));
		}
		return listaEsiti;
	}
}
