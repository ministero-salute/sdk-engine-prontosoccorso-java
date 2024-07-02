/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.flusso.emurps.regole.custom;

import java.lang.reflect.InvocationTargetException;
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
@XmlDiscriminatorValue("RegolaFacoltativoCondizionataCrossCampo")
public class RegolaFacoltativoCondizionataCrossCampo
		extends RegolaGenerica{


	public RegolaFacoltativoCondizionataCrossCampo(String nomeRegola, String codErrore, String desErrore, Parametri parametri) {
		super(nomeRegola, codErrore, desErrore, parametri);
	}

	/**
	 *
	 * Il campo in ingresso può essere inserito solo se un altro campo assume un determinato valore "valoreDipendente"
	 *
	 *
	 * @param nomeCampo campo da validare
	 * @param recordDtoGenerico DTO del record del flusso
	 * @return lista di {@link Esito}
	 */
	@Override
	public List<Esito> valida(String nomeCampo, RecordDtoGenerico recordDtoGenerico) {
		List<Esito> listaEsiti = new ArrayList<>();

		try {

			String valoreCampo = ((String) recordDtoGenerico.getCampo(nomeCampo) != null
					? (String) recordDtoGenerico.getCampo(nomeCampo)
					: null);
			
			String nomeCampoConfronto = this.getParametri().getParametriMap().get("campoDipendente");
			String valoreCampoDipendente = String.valueOf(recordDtoGenerico.getCampo(nomeCampoConfronto));
			String valoreConfronto = this.getParametri().getParametriMap().get("valoreDipendente");
			
			if(valoreCampoDipendente!= null && !valoreCampoDipendente.equals(valoreConfronto)){
				if (valoreCampo!=null) {					
					listaEsiti.add(creaEsitoKO(nomeCampo,this.getCodErrore(),this.getDesErrore()));

				} else {
					listaEsiti.add(creaEsitoOk(nomeCampo));
				}
			}else{
				listaEsiti.add(creaEsitoOk(nomeCampo));
			}

		} catch (InvocationTargetException | NoSuchMethodException |IllegalAccessException  e) {
			throw new ValidazioneImpossibileException("Impossibile validare la regola RegolaFacoltativoCondizionataCrossCampo per il campo "  + nomeCampo );
		}
		return listaEsiti;
	}

}
