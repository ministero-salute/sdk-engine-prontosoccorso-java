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
@XmlDiscriminatorValue("regolaObbligatorietaCondizionataValoreCampoVuoto")
public class RegolaObbligatorietaCondizionataValoreCampoVuoto extends RegolaGenerica{

	public RegolaObbligatorietaCondizionataValoreCampoVuoto(String nome, String codErrore, String desErrore, Parametri parametri) {
		super(nome, codErrore, desErrore, parametri);
	}

	/**
	 * verifica che il campo in input sia presente(obbligatorio) se un altro campo del DTO non è presente.
	 *
	 *  Esempio : dataTrasferimentoResidenza obbligatoria se modalitaTramissione  non presente
	 *
	 * @param nomeCampo il nome del campo da validare
	 * @param recordDtoGenerico DTO del record di un flusso
	 * @return ritorna una lista di {@link it.mds.sdk.gestoreesiti.modelli.Esito}
	 */
	@Override
	public List<Esito> valida(String nomeCampo, RecordDtoGenerico recordDtoGenerico) {
		List<Esito> listaEsiti = new ArrayList<>();
		String nomeCampoCondizionante = null;
		try {
			
			nomeCampoCondizionante = this.getParametri().getParametriMap().get("nomeCampoCondizionante");//nel nostro esempio modalitaTrasmissione
			String valoreCampoCondizionante = (recordDtoGenerico.getCampo(nomeCampoCondizionante) != null ? String.valueOf(recordDtoGenerico.getCampo(nomeCampoCondizionante)) : null);//nel nostro esempio il valore che ha effettivamente modalitaTrasmissione all'interno del record DTO

			if(valoreCampoCondizionante == null && recordDtoGenerico.getCampo(nomeCampo)==null) {
				listaEsiti.add(creaEsitoKO(nomeCampo,this.getCodErrore(),this.getDesErrore()));
			}else{
				listaEsiti.add(creaEsitoOk(nomeCampo));
			}

		} catch (InvocationTargetException | NoSuchMethodException |IllegalAccessException  e) {
			log.error("Impossibile validare Obbligatorietà condizionata del campo " + nomeCampo + " al campo " + nomeCampoCondizionante, e );
			throw new ValidazioneImpossibileException("Impossibile validare Obbligatorietà condizionata del campo " + nomeCampo + " al campo " + nomeCampoCondizionante );
		}
		return listaEsiti;
	}
	

}
