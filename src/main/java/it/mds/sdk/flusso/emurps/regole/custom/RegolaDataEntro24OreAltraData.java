/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.flusso.emurps.regole.custom;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
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
@XmlDiscriminatorValue("RegolaDataEntro24OreAltraData")
public class RegolaDataEntro24OreAltraData extends RegolaGenerica{


	public RegolaDataEntro24OreAltraData(String nome, String codErrore, String desErrore, Parametri parametri) {
		super(nome, codErrore, desErrore, parametri);
	}

	/**
	 *
	 * Controlla che la data passata in ingresso sia compresa tra startDate e ora ed endDatee e ora estremi inclusi
	 * @param nomeCampo campo da validare
	 * @param recordDtoGenerico DTO del record del flusso
	 * @return lista di {@link it.mds.sdk.gestoreesiti.modelli.Esito}
	 */
	@Override
	public List<Esito> valida(String nomeCampo, RecordDtoGenerico recordDtoGenerico) {
		List<Esito> listaEsiti = new ArrayList<>();
		try {
			String startDateStr = this.getParametri().getParametriMap().get("startDate");
			String startOraStr = this.getParametri().getParametriMap().get("startOra");
			
			String endDateStr = this.getParametri().getParametriMap().get("endDate");
			String endOraStr = this.getParametri().getParametriMap().get("endOra");
			
			// data presa in carico
			String strDate = (recordDtoGenerico.getCampo(startDateStr) != null ? (String)recordDtoGenerico.getCampo(startDateStr) : null);
			String strDateOra = (recordDtoGenerico.getCampo(startOraStr) != null ? (String)recordDtoGenerico.getCampo(startOraStr) : null);
			
//			String strDate = (String) recordDtoGenerico.getCampo(startDateStr);
//			String strDateOra = (String) recordDtoGenerico.getCampo(startOraStr);
			
			// accesso al PS - > Entrata Data
			String strEndDate = (recordDtoGenerico.getCampo(endDateStr) != null ? (String)recordDtoGenerico.getCampo(endDateStr) : null);
			String strEndDateOra = (recordDtoGenerico.getCampo(endOraStr) != null ? (String)recordDtoGenerico.getCampo(endOraStr) : null);
			
//			String strEndDate = (String) recordDtoGenerico.getCampo(endDateStr);
//			String strEndDateOra = (String) recordDtoGenerico.getCampo(endOraStr);
			
			if (!StringUtils.isBlank(strDate) && !StringUtils.isBlank(strDateOra) && 
					!StringUtils.isBlank(strEndDate) && !StringUtils.isBlank(strEndDateOra) ) {
			
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); 
				LocalDateTime campoDaValidare = LocalDateTime.parse(strDate + " " + strDateOra, formatter);
				
				LocalDateTime campoDiConfronto = LocalDateTime.parse(strEndDate + " " + strEndDateOra, formatter);
				
				LocalDateTime campoDiConfrontoPiu24Ore = campoDiConfronto.plusDays(1);
				
				if (campoDaValidare.isBefore(campoDiConfronto) || campoDaValidare.isAfter(campoDiConfrontoPiu24Ore))
					listaEsiti.add(creaEsitoKO(nomeCampo,this.getCodErrore(),"Il campo " + startDateStr + " non è congruente con il campo " + endDateStr));					
				else
					listaEsiti.add(creaEsitoOk(nomeCampo));
				
			}else
				listaEsiti.add(creaEsitoOk(nomeCampo));

		} catch (InvocationTargetException | NoSuchMethodException |IllegalAccessException  e) {
			log.error("Non è possibile validare la regola di intervallo date del campo " + nomeCampo, e);
			throw new ValidazioneImpossibileException("Non è possibile validare la regola di intervallo date del campo "  + nomeCampo );
		} catch (DateTimeParseException dataE) {
			listaEsiti.add(creaEsitoOk(nomeCampo));
		}
		return listaEsiti;
	}

}

	