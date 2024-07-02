package it.mds.sdk.flusso.emurps.regole.custom.adhoc;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

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
@XmlDiscriminatorValue("RegolaEmurPSDataEntrata")
public class RegolaEmurPSDataEntrata extends RegolaGenerica {
	
	public static final DateTimeFormatter formatterEntrataPS = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public RegolaEmurPSDataEntrata(String nome, String codErrore, String desErrore, Parametri parametri) {
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
			
			String codErr2057 = "2057";
			String desErr2057 = "Data di dimissione valorizzata e data di entrata al PS che non rispetta i valori attesi";
						
			
			String codErr1001 = "1001";
			String desErr1001 = "Data di dimissione non valorizzata e data di entrata al PS che non rispetta i valori attesi.";
			
			String dataEntrata = (recordDtoGenerico.getCampo(nomeCampo) != null
					? (String) recordDtoGenerico.getCampo(nomeCampo)
					: null);
			
			String assistitoDimissioneData = (recordDtoGenerico.getCampo("assistitoDimissioneData") != null
					? (String) recordDtoGenerico.getCampo("assistitoDimissioneData")
					: null);
						
			String annoRif = (recordDtoGenerico.getCampiInput().getAnnoRiferimentoInput() != null
					? (String) recordDtoGenerico.getCampiInput().getAnnoRiferimentoInput()
					: null);
			
			String periodoRif = (recordDtoGenerico.getCampiInput().getPeriodoRiferimentoInput() != null
					? (String) recordDtoGenerico.getCampiInput().getPeriodoRiferimentoInput().substring(1,3)
					: null);
			
			
			
			LocalDate ldDataPeriodoRifMesePrec = LocalDate.parse(annoRif+"-"+periodoRif+"-01", formatterEntrataPS);			
			ldDataPeriodoRifMesePrec = ldDataPeriodoRifMesePrec.minusMonths(1);
			
			LocalDate ldDataPeriodoRifLastDay = LocalDate.parse(annoRif+"-"+periodoRif+"-01", formatterEntrataPS);			
			ldDataPeriodoRifLastDay = ldDataPeriodoRifLastDay.with(TemporalAdjusters.lastDayOfMonth());
			
			LocalDate ldDataPeriodoRifGiornoPrec = LocalDate.parse(annoRif+"-"+periodoRif+"-01", formatterEntrataPS);			
			ldDataPeriodoRifGiornoPrec = ldDataPeriodoRifGiornoPrec.minusDays(1);
			/*
			 * Se la “data di dimissione” non è valorizzata, la data di entrata deve essere compresa nel periodo di
			 *  riferimento delle informazioni o uguale alla data inizio periodo di rilevazione – 1 giorno. 
			 */
			if (dataEntrata != null) {
				LocalDate ldDataEntrata = LocalDate.parse(dataEntrata, formatterEntrataPS);				
				
				if (assistitoDimissioneData != null) {
					
					if (ldDataEntrata.isBefore(ldDataPeriodoRifMesePrec) || ldDataEntrata.isAfter(ldDataPeriodoRifLastDay)) {
						listaEsiti.add(creaEsitoKO(nomeCampo,codErr2057,desErr2057));
						return listaEsiti;
					}
					
				}else {
					if ( (ldDataEntrata.isBefore(ldDataPeriodoRifGiornoPrec) || ldDataEntrata.isAfter(ldDataPeriodoRifLastDay))) {
						listaEsiti.add(creaEsitoKO(nomeCampo,codErr1001,desErr1001));
						return listaEsiti;
					}
				}
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
