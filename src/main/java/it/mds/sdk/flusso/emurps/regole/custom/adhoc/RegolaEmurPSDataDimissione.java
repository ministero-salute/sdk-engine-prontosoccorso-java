package it.mds.sdk.flusso.emurps.regole.custom.adhoc;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorValue;

import it.mds.sdk.flusso.emurps.parser.regole.Utilities;
import it.mds.sdk.flusso.emurps.regole.custom.RegolaDataPosterioreOUguale;
import it.mds.sdk.flusso.emurps.regole.custom.RegolaIncongruenzaCrossDueCampiUguaglianza;
import it.mds.sdk.gestoreesiti.modelli.Esito;
import it.mds.sdk.libreriaregole.dtos.RecordDtoGenerico;
import it.mds.sdk.libreriaregole.exception.ValidazioneImpossibileException;
import it.mds.sdk.libreriaregole.regole.beans.Parametri;
import it.mds.sdk.libreriaregole.regole.beans.RegolaGenerica;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@XmlDiscriminatorValue("RegolaEmurPSDataDimissione")
public class RegolaEmurPSDataDimissione extends RegolaGenerica {
	
	public static final DateTimeFormatter formatterAnnoNascita = DateTimeFormatter.ofPattern("yyyy");
	public static final DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public RegolaEmurPSDataDimissione(String nome, String codErrore, String desErrore, Parametri parametri) {
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
			String assistitoDimissioneData = (recordDtoGenerico.getCampo(nomeCampo) != null
					? (String) recordDtoGenerico.getCampo(nomeCampo)
					: null);
			String esitoTrattamento = (recordDtoGenerico.getCampo("assistitoDimissioneEsitoTrattamento") != null
					? (String) recordDtoGenerico.getCampo("assistitoDimissioneEsitoTrattamento")
					: null);
			
			String annoRif = (recordDtoGenerico.getCampiInput().getAnnoRiferimentoInput() != null
					? (String) recordDtoGenerico.getCampiInput().getAnnoRiferimentoInput()
					: null);
			
			String periodoRif = (recordDtoGenerico.getCampiInput().getPeriodoRiferimentoInput() != null
					? (String) recordDtoGenerico.getCampiInput().getPeriodoRiferimentoInput().substring(1,3)
					: null);
					
			
			
			// seconda regola
			Parametri parametri = new Parametri();
			parametri.getParametriMap().put("parametroCampoCondizionante", "6|7");
			parametri.getParametriMap().put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");

			RegolaIncongruenzaCrossDueCampiUguaglianza regolaIncongruenzaCrossDueCampiUguaglianza = new RegolaIncongruenzaCrossDueCampiUguaglianza(
					"regolaIncongruenzaCrossDueCampi", "2080", null, parametri);

			List<Esito> lstCond = regolaIncongruenzaCrossDueCampiUguaglianza.valida(nomeCampo, recordDtoGenerico);
			Utilities.addErrore(listaEsiti,lstCond);
			if (Utilities.verificaEsitoOK(listaEsiti)) 
				listaEsiti.clear();
			
			if (!"6".equals(esitoTrattamento) && !"7".equals(esitoTrattamento) ) {
				
				parametri = new Parametri();
				parametri.getParametriMap().put("nomeCampoValidatore", "assistitoPrestazioniPresaInCaricoData");

				RegolaDataPosterioreOUguale regolaDataPosteriore = new RegolaDataPosterioreOUguale(
						"regolaDataPosteriore", "3007", "Incongruenza tra Data dimissione e Data di entrata", parametri);

				List<Esito> lst1 = regolaDataPosteriore.valida(nomeCampo, recordDtoGenerico);
				Utilities.addErrore(listaEsiti,lst1);
				if (Utilities.verificaEsitoOK(listaEsiti)) 
					listaEsiti.clear();
				
				// terza regola - 24 ore
				// TODOs: FB - ANOMALIA - COMMENTATA
//				parametri = new Parametri();
//				parametri.getParametriMap().put("startDate", "assistitoDimissioneData");
//				parametri.getParametriMap().put("startOra", "assistitoDimissioneOra");
//
//				parametri.getParametriMap().put("endDate", "assistitoPrestazioniPresaInCaricoData");
//				parametri.getParametriMap().put("endOra", "assistitoPrestazioniPresaInCaricoOra");
//
//				RegolaDataEntro24OreAltraData regolaDataEntro24OreAltraData = new RegolaDataEntro24OreAltraData(
//						"regolaDataEntro24OreAltraData", "2068", null, parametri);
//				List<Esito> lst = regolaDataEntro24OreAltraData.valida(nomeCampo, recordDtoGenerico);
//				Utilities.addErrore(listaEsiti, lst);
//				if (Utilities.verificaEsitoOK(listaEsiti)) 
//					listaEsiti.clear();
				
			}
			
			// codErr - 1002 - verifica dataDimissione con i dati in input del gaf (anno e periodo di riferimento)
			verificaDataPeriodoRiferimento(nomeCampo, listaEsiti, assistitoDimissioneData, annoRif, periodoRif);
			
			if (Utilities.verificaEsitoOK(listaEsiti)) {
				listaEsiti.clear();
				listaEsiti.add(creaEsitoOk(nomeCampo));
			}
				
			
		} catch (InvocationTargetException | NoSuchMethodException |IllegalAccessException  e) {
				log.error("Non è possibile validare la regola RegolaEmurPSMesePresenzaCondizionataCampo " + nomeCampo, e);
			throw new ValidazioneImpossibileException("Non è possibile validare la regola RegolaEmurPSMesePresenzaCondizionataCampo " + nomeCampo );
		}catch (DateTimeParseException dataE) {
//			Esito esito = creaEsitoKO(nomeCampo,"999","Formato data non corretto");
//			List<Esito> lst = new ArrayList<Esito>();
//			lst.add(esito);
//			Utilities.addErrore(listaEsiti, lst);
		}
		return listaEsiti;
	}

	private void verificaDataPeriodoRiferimento(String nomeCampo, List<Esito> listaEsiti,
			String assistitoDimissioneData, String annoRif, String periodoRif) {
		
		if (assistitoDimissioneData != null && periodoRif != null && annoRif != null ) {
		
			LocalDate ldAssistitoDimissioneData = LocalDate.parse(assistitoDimissioneData, formatterData);
			
			LocalDate ldDataPeriodoRifFirstDay = LocalDate.parse(annoRif+"-"+periodoRif+"-01", formatterData);			
//			ldDataPeriodoRifFirstDay = ldDataPeriodoRifFirstDay.with(TemporalAdjusters.firstDayOfMonth());
			
			LocalDate ldDataPeriodoRifLastDay = LocalDate.parse(annoRif+"-"+periodoRif+"-01", formatterData);			
			ldDataPeriodoRifLastDay = ldDataPeriodoRifLastDay.with(TemporalAdjusters.lastDayOfMonth());
			
			
			if (ldAssistitoDimissioneData.isBefore(ldDataPeriodoRifFirstDay) || ldAssistitoDimissioneData.isAfter(ldDataPeriodoRifLastDay)) {
				Esito esito = creaEsitoKO(nomeCampo,"1002","Data di dimissione non compresa nel periodo di riferimento delle informazioni");
				List<Esito> lst = new ArrayList<Esito>();
				lst.add(esito);
				Utilities.addErrore(listaEsiti, lst);
			}
		}
	}

}
