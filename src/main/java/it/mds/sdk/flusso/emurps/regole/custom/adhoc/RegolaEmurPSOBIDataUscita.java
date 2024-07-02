package it.mds.sdk.flusso.emurps.regole.custom.adhoc;

import java.lang.reflect.InvocationTargetException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorValue;

import it.mds.sdk.flusso.emurps.parser.regole.Utilities;
import it.mds.sdk.flusso.emurps.regole.custom.RegolaDataOraConfrontoAltraDataOra;
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
@XmlDiscriminatorValue("RegolaEmurPSOBIDataUscita")
public class RegolaEmurPSOBIDataUscita extends RegolaGenerica {
	
	public static final DateTimeFormatter formatterAnnoNascita = DateTimeFormatter.ofPattern("yyyy");
	public static final DateTimeFormatter formatterEntrataPS = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public RegolaEmurPSOBIDataUscita(String nome, String codErrore, String desErrore, Parametri parametri) {
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
			String valCampo = (recordDtoGenerico.getCampo(nomeCampo) != null ? (String)recordDtoGenerico.getCampo(nomeCampo) : null);
			String esitoTrattamento = (recordDtoGenerico.getCampo("assistitoDimissioneEsitoTrattamento") != null ? (String)recordDtoGenerico.getCampo("assistitoDimissioneEsitoTrattamento") : null);
			
			
			Parametri parametri = new Parametri();
			parametri.getParametriMap().put("parametroCampoCondizionante", "10");
			parametri.getParametriMap().put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");

			RegolaIncongruenzaCrossDueCampi regolaIncongruenzaCrossDueCampi = new RegolaIncongruenzaCrossDueCampi(
					"regolaIncongruenzaCrossDueCampi", "1134", null, parametri);
			
			List<Esito> lstCond = regolaIncongruenzaCrossDueCampi.valida(nomeCampo, recordDtoGenerico);		
			Utilities.addErrore(listaEsiti,lstCond);
			if (Utilities.verificaEsitoOK(listaEsiti)) 
				listaEsiti.clear();
			
			if ("10".equals(esitoTrattamento)) {
				parametri = new Parametri();
				parametri.getParametriMap().put("campoPrimaData", "assistitoOBIDataUscita");
				parametri.getParametriMap().put("operatoreLogico", "minore");
				parametri.getParametriMap().put("campoSecondaData", "assistitoOBIDataIngresso");
		
				String desErrore = "La data di uscita dall'OBI non è congruente con la data di entrata in OBI Inoltre "
						+ "La data di uscita dall'OBI deve coincidere con la data di uscita dal PS";
				
				RegolaDataOraConfrontoAltraDataOra regolaDataOraConfrontoAltraDataOra = new RegolaDataOraConfrontoAltraDataOra(
						"regolaDataOraConfrontoAltraDataOra", "1136", desErrore, parametri);
			
				
				List<Esito> lst = regolaDataOraConfrontoAltraDataOra.valida(nomeCampo, recordDtoGenerico);		
				Utilities.addErrore(listaEsiti,lst);
				if (Utilities.verificaEsitoOK(listaEsiti)) 
					listaEsiti.clear();
				
				// QUESTI CONTROLLI ANDREBBERO ESEGUITI SOLO SE LA REGOLA PRECEDENTE NON GENERA ERRORE
				if (Utilities.verificaEsitoOK(lst)) {
						parametri = new Parametri();
						parametri.getParametriMap().put("campoPrimaData", "assistitoOBIDataUscita");
						parametri.getParametriMap().put("operatoreLogico", "diverso");
						parametri.getParametriMap().put("campoSecondaData", "assistitoDimissioneData");
			
						RegolaDataOraConfrontoAltraDataOra regolaDataOraConfrontoAltraDataOra2 = new RegolaDataOraConfrontoAltraDataOra(
								"regolaDataOraConfrontoAltraDataOra", "1136", desErrore, parametri);
						
						List<Esito> lst2 = regolaDataOraConfrontoAltraDataOra2.valida(nomeCampo, recordDtoGenerico);		
						Utilities.addErrore(listaEsiti,lst2);
						if (Utilities.verificaEsitoOK(listaEsiti)) 
							listaEsiti.clear();
				}
			}
			
			if (Utilities.verificaEsitoOK(listaEsiti)) { 
				listaEsiti.clear();
				listaEsiti.add(creaEsitoOk(nomeCampo));
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
