package it.mds.sdk.flusso.emurps.regole.custom.adhoc;

import java.lang.reflect.InvocationTargetException;
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
@XmlDiscriminatorValue("RegolaEmurPSRicoveroProgressivo")
public class RegolaEmurPSRicoveroProgressivo extends RegolaGenerica {
	
	public RegolaEmurPSRicoveroProgressivo(String nome, String codErrore, String desErrore, Parametri parametri) {
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
			
			String assistitoDimissioneRicoveroProgressivo = (recordDtoGenerico
					.getCampo("assistitoDimissioneRicoveroProgressivo") != null
							? (String) recordDtoGenerico.getCampo("assistitoDimissioneRicoveroProgressivo")
							: null);
			String assistitoDimissioneData = (recordDtoGenerico
					.getCampo("assistitoDimissioneData") != null
							? (String) recordDtoGenerico.getCampo("assistitoDimissioneData")
							: null);
			
			
			String codErr = "2071";
			String desErr = "L’anno indicato nel progressivo SDO non è congruente con la data dimissione (Anno progressivo SDO uguale Anno di dimissione oppure Anno progressivo SDO meno 1)";
			
			
				if (!StringUtils.isBlank(assistitoDimissioneRicoveroProgressivo) &&
						!StringUtils.isBlank(assistitoDimissioneData)) {
					
					String prgDimissione = "";
					if (assistitoDimissioneRicoveroProgressivo.length() > 2)
						prgDimissione = assistitoDimissioneRicoveroProgressivo.substring(0,2);
					
					String annoDimissione = "";
					if (assistitoDimissioneData.length() > 4)
						annoDimissione = assistitoDimissioneData.substring(2,4);
					
				if (!StringUtils.isNumeric(prgDimissione) ||
						!StringUtils.isNumeric(annoDimissione)) {
					prgDimissione = "99";
					annoDimissione = "00";
				}
					
				int res = Integer.parseInt(prgDimissione)-Integer.parseInt(annoDimissione);
					
					if (res != 0 && res != 1) {
						listaEsiti.add(creaEsitoKO(nomeCampo, codErr, desErr));
						return listaEsiti;
					}					
				}
				
			
			
			listaEsiti.add(creaEsitoOk(nomeCampo));	
			
			
        	
			
		} catch (InvocationTargetException | NoSuchMethodException |IllegalAccessException e) {
			log.error("Non è possibile validare la regola RegolaEmurPSRicoveroProgressivo " + nomeCampo, e);
			throw new ValidazioneImpossibileException("Non è possibile validare la regola RegolaEmurPSRicoveroProgressivo " + nomeCampo );
		}catch (DateTimeParseException dataE) {
			listaEsiti.add(creaEsitoOk(nomeCampo));
		}
		return listaEsiti;
	}
	

}
