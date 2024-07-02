package it.mds.sdk.flusso.emurps.regole.custom.adhoc;

import java.lang.reflect.InvocationTargetException;
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
@XmlDiscriminatorValue("RegolaEmurPSMeseObbligatorietaCondizionataCampo")
public class RegolaEmurPSMeseObbligatorietaCondizionataCampo extends RegolaGenerica {
	
	public static final DateTimeFormatter formatterAnnoNascita = DateTimeFormatter.ofPattern("yyyy");
	public static final DateTimeFormatter formatterEntrataPS = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public RegolaEmurPSMeseObbligatorietaCondizionataCampo(String nome, String codErrore, String desErrore, Parametri parametri) {
		super(nome, codErrore, desErrore, parametri);
	}

	/**
	 *
	 * Mancata valorizzazione di un campo ad obbligatorietà condizionata.
	 * @param nomeCampo campo da validare
	 * @param recordDtoGenerico DTO del record del flusso
	 * @return lista di {@link it.mds.sdk.gestoreesiti.modelli.Esito}
	 */
	@Override
	public List<Esito> valida(String nomeCampo, RecordDtoGenerico recordDtoGenerico) {
		List<Esito> listaEsiti = new ArrayList<>();
		try {
			String campoMeseNascita = (recordDtoGenerico.getCampo(nomeCampo) != null ? (String)recordDtoGenerico.getCampo(nomeCampo) : null);
			
			String entrataData = (String)recordDtoGenerico.getCampo("entrataData");
			String assistitoDatiAnagraficiEtaNascitaAnno = (String)recordDtoGenerico.getCampo("assistitoDatiAnagraficiEtaNascitaAnno");
			
			Year annoEntrataPS, annoNascita ;
			annoEntrataPS = (entrataData != null ? Year.parse(entrataData,formatterEntrataPS) : Year.parse(""));
			annoNascita = (assistitoDatiAnagraficiEtaNascitaAnno != null ? Year.parse(assistitoDatiAnagraficiEtaNascitaAnno, formatterAnnoNascita) : null);
			
			if(StringUtils.isBlank(campoMeseNascita) && annoNascita != null && annoNascita.compareTo(annoEntrataPS) >= 0){						
				listaEsiti.add(creaEsitoKO(nomeCampo,this.getCodErrore(),this.getDesErrore()));
			}else{
				listaEsiti.add(creaEsitoOk(nomeCampo));
			}
			
			
		} catch (InvocationTargetException | NoSuchMethodException |IllegalAccessException  e) {
			log.error("Non è possibile validare la regola RegolaEmurPSMeseObbligatorietaCondizionataCampo " + nomeCampo, e);
			throw new ValidazioneImpossibileException("Non è possibile validare la regola RegolaEmurPSMeseObbligatorietaCondizionataCampo " + nomeCampo );
		}catch (DateTimeParseException dataE) {
			listaEsiti.add(creaEsitoOk(nomeCampo));
		}
		return listaEsiti;
	}

}