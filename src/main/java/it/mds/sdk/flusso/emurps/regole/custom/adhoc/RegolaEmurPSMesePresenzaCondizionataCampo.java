package it.mds.sdk.flusso.emurps.regole.custom.adhoc;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.Period;
import java.time.Year;
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
@XmlDiscriminatorValue("RegolaEmurPSMesePresenzaCondizionataCampo")
public class RegolaEmurPSMesePresenzaCondizionataCampo extends RegolaGenerica {
	
	public static final DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	public RegolaEmurPSMesePresenzaCondizionataCampo(String nome, String codErrore, String desErrore, Parametri parametri) {
		super(nome, codErrore, desErrore, parametri);
	}

	/**
	 *
	 * I mesi devono essere presenti solo se l'assistito è di età inferiore all'anno.
	 * @param nomeCampo campo da validare
	 * @param recordDtoGenerico DTO del record del flusso
	 * @return lista di {@link it.mds.sdk.gestoreesiti.modelli.Esito}
	 */
	@Override
	public List<Esito> valida(String nomeCampo, RecordDtoGenerico recordDtoGenerico) {
		List<Esito> listaEsiti = new ArrayList<>();
		try {
			String campoMeseNascita = (recordDtoGenerico.getCampo(nomeCampo) != null
					? (String) recordDtoGenerico.getCampo(nomeCampo)
					: null);

			
			String assistitoDatiAnagraficiEtaNascitaAnno = (String) recordDtoGenerico
					.getCampo("assistitoDatiAnagraficiEtaNascitaAnno") != null
							? (String) recordDtoGenerico.getCampo("assistitoDatiAnagraficiEtaNascitaAnno")
									: "";

			if (!StringUtils.isBlank(campoMeseNascita)) {
				
				Year annoNascita = Year.parse(assistitoDatiAnagraficiEtaNascitaAnno);
				
				Period diff = Period.between(LocalDate.parse(annoNascita+"-"+campoMeseNascita+"-01", formatterData),
						LocalDate.now());
				int numeroAnni = diff.getYears();
				numeroAnni = (numeroAnni > 0) ? (numeroAnni * 12) : 0;
				int numeroMesi = diff.getMonths()+numeroAnni;
				
				
				if (numeroMesi > 12)
					listaEsiti.add(creaEsitoKO(nomeCampo, this.getCodErrore(), this.getDesErrore()));
				else
					listaEsiti.add(creaEsitoOk(nomeCampo));
			}else {
				listaEsiti.add(creaEsitoOk(nomeCampo));
			}
		} catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
			log.error("Non è possibile validare la regola RegolaEmurPSMesePresenzaCondizionataCampo " + nomeCampo, e);
			throw new ValidazioneImpossibileException(
					"Non è possibile validare la regola RegolaEmurPSMesePresenzaCondizionataCampo " + nomeCampo);
		} catch (DateTimeParseException dataE) {
			listaEsiti.add(creaEsitoKO(nomeCampo, this.getCodErrore(), "Formato anno non corretto"));
		}
		return listaEsiti;
	}

}
