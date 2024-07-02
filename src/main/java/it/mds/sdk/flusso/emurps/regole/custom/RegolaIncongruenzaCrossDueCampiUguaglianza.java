/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.flusso.emurps.regole.custom;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
@XmlDiscriminatorValue("regolaIncongruenzaCrossDueCampiUguaglianza")
public class RegolaIncongruenzaCrossDueCampiUguaglianza extends RegolaGenerica{


	public RegolaIncongruenzaCrossDueCampiUguaglianza(String nomeRegola, String codErrore, String desErrore, Parametri parametri) {
		super(nomeRegola, codErrore, desErrore, parametri);
	}

	/**
	 * verifica incongruenza valorizzazione campo. Il campo in input se valorizzato e i valori di uno o due campi non hanno valori 
	 * previsti si segnala l'incongruenza
	 *
	 *  Esempio : Identificativo Centrale operativa valorizzata per modalità di arrivo  diversa da 1, 4, 6 o responsabile invio diverso da 5 - in questo caso scatta l'incongruenza
	 *
	 * @param nomeCampo il nome del campo da validare
	 * @param recordDtoGenerico DTO del record di un flusso
	 * @return ritorna una lista di {@link it.mds.sdk.gestoreesiti.modelli.Esito}
	 */
	@Override
	public List<Esito> valida(String nomeCampo, RecordDtoGenerico recordDtoGenerico) {
		List<Esito> listaEsiti = new ArrayList<>();
		String nomeCampoCondizionante = null;
		String nomeCampoCondizionante2 = null;
		
		try {
			String valoreCampo = ((String) recordDtoGenerico.getCampo(nomeCampo) != null
					? (String) recordDtoGenerico.getCampo(nomeCampo)
					: null);
			
			nomeCampoCondizionante = this.getParametri().getParametriMap().get("nomeCampoCondizionante");
			nomeCampoCondizionante2 = this.getParametri().getParametriMap().get("nomeCampoCondizionante2");
			
			String valoreCampoCondizionante = String.valueOf(recordDtoGenerico.getCampo(nomeCampoCondizionante));
			String valoreCampoCondizionante2 = (nomeCampoCondizionante2 != null) ? String.valueOf(recordDtoGenerico.getCampo(nomeCampoCondizionante2)) : "";
			
			List<String> listaValoriCondizionanti = Arrays.stream(this.getParametri().getParametriMap().
					get("parametroCampoCondizionante").split("\\|")).collect(Collectors.toList());
			
			List<String> listaValoriCondizionanti2 = (this.getParametri().getParametriMap().get("parametroCampoCondizionante2") != null) 
					? Arrays.stream(this.getParametri().getParametriMap().
							get("parametroCampoCondizionante2").split("\\|")).collect(Collectors.toList()) : new ArrayList<>();
					
			if (StringUtils.isBlank(valoreCampoCondizionante2)) {
				if(listaValoriCondizionanti.contains(valoreCampoCondizionante) && 
						valoreCampo!=null) {
								listaEsiti.add(creaEsitoKO(nomeCampo,this.getCodErrore(),"Incongruenza tra " + nomeCampo + " e " + nomeCampoCondizionante));
					}else{
						listaEsiti.add(creaEsitoOk(nomeCampo));
					}
			}else {
				if(listaValoriCondizionanti.contains(valoreCampoCondizionante) || 
						listaValoriCondizionanti2.contains(valoreCampoCondizionante2) && 
						valoreCampo!=null) {
					listaEsiti.add(creaEsitoKO(nomeCampo,this.getCodErrore(),"Incongruenza tra " + nomeCampo + " e i campi " + nomeCampoCondizionante + " - " + nomeCampoCondizionante2));
					}else{
						listaEsiti.add(creaEsitoOk(nomeCampo));
					}
				
			}
			
			

		} catch (InvocationTargetException | NoSuchMethodException |IllegalAccessException  e) {
			log.error("Impossibile validare Incongruenza del campo " + nomeCampo + " ai campi " + nomeCampoCondizionante + " e " + nomeCampoCondizionante2, e );
			throw new ValidazioneImpossibileException("Impossibile validare Incongruenza condizionata del campo " + nomeCampo + " ai campi " + nomeCampoCondizionante + " e " + nomeCampoCondizionante2 );
		}
		return listaEsiti;
	}
	

}
