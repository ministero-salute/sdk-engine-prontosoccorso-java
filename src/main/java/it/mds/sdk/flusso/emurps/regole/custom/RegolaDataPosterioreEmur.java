/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.flusso.emurps.regole.custom;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
@XmlDiscriminatorValue("regolaDataPosterioreOUguale")
public class RegolaDataPosterioreEmur extends RegolaGenerica {
	
	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public RegolaDataPosterioreEmur(String nome, String codErrore, String desErrore, Parametri parametri) {
		super(nome, codErrore, desErrore, parametri);
	}

	/**
	 *
	 * Controlla che il valore del campo passato in ingresso(una data) sia posteriore a un'altra data all'interno dello stesso DTO (se presente).
	 * nomeCampoValidatore é il parametro che contiene il nome del record del DTO con cui voglio comparare il dato in ingresso
	 * @param nomeCampo campo da validare
	 * @param recordDtoGenerico DTO del record del flusso
	 * @return lista di {@link it.mds.sdk.gestoreesiti.modelli.Esito}
	 */
	@Override
	public List<Esito> valida(String nomeCampo, RecordDtoGenerico recordDtoGenerico) {
		List<Esito> listaEsiti = new ArrayList<>();
		try {
			String dataDaComparareString = (String)recordDtoGenerico.getCampo(nomeCampo);
			String nomeCampoValidatore = this.getParametri().getParametriMap().get("nomeCampoValidatore");
			String dataValidatoreString = (String)recordDtoGenerico.getCampo(nomeCampoValidatore);
			
			if(dataValidatoreString != null){
				LocalDate dataDaComparare ;
				if(dataDaComparareString!= null ) {
					dataDaComparare = LocalDate.parse(dataDaComparareString, formatter);
					LocalDate dataValidatore = LocalDate.parse(dataValidatoreString, formatter);
					if(dataDaComparare.isAfter(dataValidatore) || dataDaComparare.isEqual(dataValidatore)){
						listaEsiti.add(creaEsitoOk(nomeCampo));
					}else{
						listaEsiti.add(creaEsitoKO(nomeCampo,this.getCodErrore(),this.getDesErrore()));
									//.withDescrizione("Il campo " + nomeCampo + " deve essere posteriore al campo  " + nomeCampoValidatore)
					}
				}else {
					listaEsiti.add(creaEsitoKO(nomeCampo,this.getCodErrore(),this.getDesErrore()));
							//.withDescrizione("Il campo  " + nomeCampo + " non presente. Non é possibile validare questa regola")
				}
			}else{
				listaEsiti.add(creaEsitoOk(nomeCampo));
			}
		} catch (InvocationTargetException | NoSuchMethodException |IllegalAccessException  e) {
			log.error("Non è possibile validare la regola di data posteriore del campo " + nomeCampo, e);
			throw new ValidazioneImpossibileException("Non è possibile validare la regola di data posteriore del campo " + nomeCampo );
		} catch (DateTimeParseException dataE) {
			listaEsiti.add(creaEsitoOk(nomeCampo));
		}
		return listaEsiti;
	}

}
