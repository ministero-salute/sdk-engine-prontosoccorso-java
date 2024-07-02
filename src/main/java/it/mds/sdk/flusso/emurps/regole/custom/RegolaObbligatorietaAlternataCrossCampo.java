/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.flusso.emurps.regole.custom;

import java.lang.reflect.InvocationTargetException;
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
@XmlDiscriminatorValue("regolaObbligatorietaAlternataCrossCampo")
public class RegolaObbligatorietaAlternataCrossCampo extends RegolaGenerica{

	public RegolaObbligatorietaAlternataCrossCampo(String nome, String codErrore, String desErrore, Parametri parametri) {
		super(nome, codErrore, desErrore, parametri);
	}

	/**
	 *
	 * verifica obbligatorietà tra il valore di due campi del DTO in questo modo :
	 * Segnalazione se i due campi sono non valorizzati 
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
			
			String nomeCampoConfronto = (this.getParametri().getParametriMap().get("nomeCampoCoerente") != null
					? this.getParametri().getParametriMap().get("nomeCampoCoerente") : null);
					
			
			String valoreCampoDipendente = (recordDtoGenerico.getCampo(nomeCampoConfronto) != null
					? (String)recordDtoGenerico.getCampo(nomeCampoConfronto)
					: null);

			if (StringUtils.isBlank(valoreCampo) && StringUtils.isBlank(valoreCampoDipendente)) {
				listaEsiti.add(creaEsitoKO(nomeCampo,this.getCodErrore(),this.getDesErrore()));
			}else{
				listaEsiti.add(creaEsitoOk(nomeCampo));
			}
		} catch (InvocationTargetException | NoSuchMethodException |IllegalAccessException  e) {
			log.error("Impossibile validare la regola regolaObbligatorietaAlternataCrossCampo per il campo " + nomeCampo, e );
			throw new ValidazioneImpossibileException("Impossibile validare la regola regolaObbligatorietaAlternataCrossCampo per il campo "  + nomeCampo );
		}
		return listaEsiti;
	}

}
