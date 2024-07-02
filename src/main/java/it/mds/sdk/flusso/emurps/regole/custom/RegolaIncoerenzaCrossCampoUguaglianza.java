/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.flusso.emurps.regole.custom;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
@XmlDiscriminatorValue("RegolaIncoerenzaCrossCampoUguaglianza")
public class RegolaIncoerenzaCrossCampoUguaglianza extends RegolaGenerica{


	public RegolaIncoerenzaCrossCampoUguaglianza(String nomeRegola, String codErrore, String desErrore, Parametri parametri) {
		super(nomeRegola, codErrore, desErrore, parametri);
	}

	/**
	 *
	 * verifica la coerenza tra il valore di due campi del DTO in questo modo :
	 * Se il campo in input (nomeCampo) é uguale ad un valore prefissato verifica che un altro campo dello stesso DTO (parametro campoDipendente ) abbia un valore specifico (parametro listaValoriCoerenti)
	 *Esempio:  se triage Infermieristico = N, allora Esito Trattamento = 9. Se Esito Trattamento =9, allora triage Medico = N
	 * @param nomeCampo campo da validare
	 * @param recordDtoGenerico DTO del record del flusso
	 * @return lista di {@link Esito}
	 */
	@Override
	public List<Esito> valida(String nomeCampo, RecordDtoGenerico recordDtoGenerico) {
		List<Esito> listaEsiti = new ArrayList<>();
		try {
			String valoreConfronto = this.getParametri().getParametriMap().get("valoreConfronto");
			String nomeCampoConfronto = this.getParametri().getParametriMap().get("nomeCampoCoerente");
			
			if (recordDtoGenerico.getCampo(nomeCampo)!= null && valoreConfronto.equals(String.valueOf(recordDtoGenerico.getCampo(nomeCampo)))) {
				String valoreCampoDipendente = String.valueOf(recordDtoGenerico.getCampo(nomeCampoConfronto));
				List<String> listaValoriConfronto = Arrays.stream(this.getParametri().getParametriMap().get("listaValoriCoerenti")
						.split("\\|")).collect(Collectors.toList());
				if(valoreCampoDipendente != null && listaValoriConfronto.contains(valoreCampoDipendente)){
					listaEsiti.add(Esito.builder()
							.withCampo(nomeCampo)
							.withValoreEsito(true)
							.build());
				}else{
					listaEsiti.add(creaEsitoKO(nomeCampo,this.getCodErrore(),"Il campo " + nomeCampo + " é incoerente con " + nomeCampoConfronto));
				}

			} else {
				listaEsiti.add(creaEsitoOk(nomeCampo));
			}
		} catch (InvocationTargetException | NoSuchMethodException |IllegalAccessException  e) {
			log.error("Impossibile validare la regola IncoerenzaCrossCampo per il campo " + nomeCampo, e );
			throw new ValidazioneImpossibileException("Impossibile validare la regola IncoerenzaCrossCampo per il campo "  + nomeCampo );
		}
		return listaEsiti;
	}

}

