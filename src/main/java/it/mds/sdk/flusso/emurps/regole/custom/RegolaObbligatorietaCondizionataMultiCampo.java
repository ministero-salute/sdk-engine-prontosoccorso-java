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
@XmlDiscriminatorValue("regolaObbligatorietaCondizionataMultiCampo")
public class RegolaObbligatorietaCondizionataMultiCampo extends RegolaGenerica{


	public RegolaObbligatorietaCondizionataMultiCampo(String nomeRegola, String codErrore, String desErrore, Parametri parametri) {
		super(nomeRegola, codErrore, desErrore, parametri);
	}

	/**
	 * verifica che il campo in input sia presente(obbligatorio) se altri 2 campi assumono un certo valore.
	 *
	 *  Esempio : centraleOperativaIdentificativo obbligatoria se modalitaInvio  uguale a 1-4-6 e se responsabileInvio = 5
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
		String operatoreLogico = null;
		try {
			
			String valoreCampo = ((String) recordDtoGenerico.getCampo(nomeCampo) != null
					? (String) recordDtoGenerico.getCampo(nomeCampo)
					: null);
			
			operatoreLogico = (this.getParametri().getParametriMap().get("operatoreLogico") != null)
					? this.getParametri().getParametriMap().get("operatoreLogico")
					: "and";

			nomeCampoCondizionante = this.getParametri().getParametriMap().get("nomeCampoCondizionante");
			nomeCampoCondizionante2 = this.getParametri().getParametriMap().get("nomeCampoCondizionante2");

			String valoreCampoCondizionante = (nomeCampoCondizionante != null)
					? (String)recordDtoGenerico.getCampo(nomeCampoCondizionante) : "";
								
			String valoreCampoCondizionante2 = (nomeCampoCondizionante2 != null)
					? (String)recordDtoGenerico.getCampo(nomeCampoCondizionante2) : "";

			List<String> listaValoriCondizionanti = Arrays
					.stream(this.getParametri().getParametriMap().get("parametroCampoCondizionante").split("\\|"))
					.collect(Collectors.toList());

			List<String> listaValoriCondizionanti2 = (this.getParametri().getParametriMap()
					.get("parametroCampoCondizionante2") != null) ? Arrays.stream(
							this.getParametri().getParametriMap().get("parametroCampoCondizionante2").split("\\|"))
							.collect(Collectors.toList()) : new ArrayList<>();

			if (StringUtils.isBlank(valoreCampoCondizionante2)) {
				if (listaValoriCondizionanti.contains(valoreCampoCondizionante)
						&& valoreCampo == null) {
					listaEsiti.add(creaEsitoKO(nomeCampo, this.getCodErrore(), this.getDesErrore()));
				} else {
					listaEsiti.add(creaEsitoOk(nomeCampo));
				}
			} else {
				if ("and".equalsIgnoreCase(operatoreLogico)) {
					if (listaValoriCondizionanti.contains(valoreCampoCondizionante)
							&& listaValoriCondizionanti2.contains(valoreCampoCondizionante2)
							&& valoreCampo == null) {
						listaEsiti.add(creaEsitoKO(nomeCampo, this.getCodErrore(), this.getDesErrore()));
					} else {
						listaEsiti.add(creaEsitoOk(nomeCampo));
					}
				} else {
					if ((listaValoriCondizionanti.contains(valoreCampoCondizionante)
							|| listaValoriCondizionanti2.contains(valoreCampoCondizionante2))
							&& valoreCampo == null) {
						listaEsiti.add(creaEsitoKO(nomeCampo, this.getCodErrore(), this.getDesErrore()));
					} else {
						listaEsiti.add(creaEsitoOk(nomeCampo));
					}
				}
			}
			
		} catch (InvocationTargetException | NoSuchMethodException |IllegalAccessException  e) {
			log.error("Impossibile validare Obbligatorietà condizionata del campo " + nomeCampo + " ai campi " + nomeCampoCondizionante + " e " + nomeCampoCondizionante2, e );
			throw new ValidazioneImpossibileException("Impossibile validare Obbligatorietà condizionata del campo " + nomeCampo + " ai campi " + nomeCampoCondizionante + " e " + nomeCampoCondizionante2 );
		}
		return listaEsiti;
	}
	

}
