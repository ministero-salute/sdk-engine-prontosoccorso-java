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
@XmlDiscriminatorValue("RegolaDataOraConfrontoAltraDataOra")
public class RegolaDataOraConfrontoAltraDataOra extends RegolaGenerica{

	DateTimeFormatter formatterDataOra = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); 
	
	public RegolaDataOraConfrontoAltraDataOra(String nome, String codErrore, String desErrore, Parametri parametri) {
		super(nome, codErrore, desErrore, parametri);
	}

	/**
	 *
	 * Controlla che la data e ora passata in ingresso con una seconda data e ora sempre passata in input
	 * @param nomeCampo campo da validare
	 * @param recordDtoGenerico DTO del record del flusso
	 * @return lista di {@link it.mds.sdk.gestoreesiti.modelli.Esito}
	 */
	@Override
	public List<Esito> valida(String nomeCampo, RecordDtoGenerico recordDtoGenerico) {
		List<Esito> listaEsiti = new ArrayList<>();
		try {
			String campoPrimaData = this.getParametri().getParametriMap().get("campoPrimaData");
			String campoPrimaOra = this.getParametri().getParametriMap().get("campoPrimaOra");
			String operatoreLogico = this.getParametri().getParametriMap().get("operatoreLogico");
			String campoSecondaData = this.getParametri().getParametriMap().get("campoSecondaData");
			String campoSecondaOra = this.getParametri().getParametriMap().get("campoSecondaOra");
			
			// prima data
			String strPrimaData = (campoPrimaData != null ? (String) recordDtoGenerico.getCampo(campoPrimaData) : null);
			String strPrimaOra = (campoPrimaOra != null ? (String) recordDtoGenerico.getCampo(campoPrimaOra) : null);
			
			// seconda data
			String strSecondaData = (campoSecondaData != null ? (String) recordDtoGenerico.getCampo(campoSecondaData) : null);
			String strSecondaOra = (campoSecondaOra != null ? (String) recordDtoGenerico.getCampo(campoSecondaOra) : null);
			
			if (StringUtils.isBlank(strPrimaData) || StringUtils.isBlank(strSecondaData) ) {
				listaEsiti.add(creaEsitoKO(nomeCampo,this.getCodErrore(),this.getDesErrore()));
			}else {
				
				
				if (StringUtils.isBlank(strPrimaOra) ) 
					strPrimaOra = "00:00";
				
				if (StringUtils.isBlank(strSecondaOra)) 
					strSecondaOra = "00:00";
				
				LocalDateTime campoDaValidare = LocalDateTime.parse(strPrimaData + " " + strPrimaOra, formatterDataOra);
				LocalDateTime campoDiConfronto = LocalDateTime.parse(strSecondaData + " " + strSecondaOra, formatterDataOra);
			
				if ("minoreUguale".equalsIgnoreCase(operatoreLogico) && (campoDaValidare.isBefore(campoDiConfronto) || campoDaValidare.compareTo(campoDiConfronto) == 0) )
					listaEsiti.add(creaEsitoKO(nomeCampo,this.getCodErrore(),this.getDesErrore()));
				else if ("maggioreUguale".equalsIgnoreCase(operatoreLogico) && (campoDaValidare.isAfter(campoDiConfronto) || campoDaValidare.compareTo(campoDiConfronto) == 0) )
					listaEsiti.add(creaEsitoKO(nomeCampo,this.getCodErrore(),this.getDesErrore()));
				else if ("maggiore".equalsIgnoreCase(operatoreLogico) && campoDaValidare.isAfter(campoDiConfronto) )
					listaEsiti.add(creaEsitoKO(nomeCampo,this.getCodErrore(),this.getDesErrore()));
				else if ("minore".equalsIgnoreCase(operatoreLogico) && campoDaValidare.isBefore(campoDiConfronto) )
					listaEsiti.add(creaEsitoKO(nomeCampo,this.getCodErrore(),this.getDesErrore()));
				else if ("uguale".equalsIgnoreCase(operatoreLogico) && campoDaValidare.compareTo(campoDiConfronto) == 0 )
					listaEsiti.add(creaEsitoKO(nomeCampo,this.getCodErrore(),this.getDesErrore()));
				else if ("diverso".equalsIgnoreCase(operatoreLogico) && campoDaValidare.compareTo(campoDiConfronto) < 0 )
					listaEsiti.add(creaEsitoKO(nomeCampo,this.getCodErrore(),this.getDesErrore()));
				else
					listaEsiti.add(creaEsitoOk(nomeCampo));
			
			
			}

		} catch (InvocationTargetException | NoSuchMethodException |IllegalAccessException  e) {
			log.error("Non è possibile validare la regola RegolaDataOraConfrontoAltraDataOra del campo " + nomeCampo, e);
			throw new ValidazioneImpossibileException("Non è possibile validare la regola RegolaDataOraConfrontoAltraDataOra del campo "  + nomeCampo );
		}catch (DateTimeParseException dataE) {
			listaEsiti.add(creaEsitoOk(nomeCampo));
		}
		return listaEsiti;
	}

}

	