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
@XmlDiscriminatorValue("regolaRangeOccorrenzeAmmesse")
public class RegolaRangeOccorrenzeAmmesse extends RegolaGenerica {

    public RegolaRangeOccorrenzeAmmesse(String nomeRegola, String codErrore, String desErrore, Parametri parametri) {
        super(nomeRegola, codErrore, desErrore, parametri);
    }

    /**
     * Verifica che la lunghezza della lista di dati sia compresa tra due numeri
     *
     * @param nomeCampo         campo da validare
     * @param recordDtoGenerico DTO del record del flusso
     * @return lista di {@link it.mds.sdk.gestoreesiti.modelli.Esito}
     */

    @Override
    public List<Esito> valida(String nomeCampo, RecordDtoGenerico recordDtoGenerico) {
        List<Esito> listaEsiti = new ArrayList<>();
        try {
        	
        	List<Object> lst = null;
        	
        	if (recordDtoGenerico.getCampo(nomeCampo) instanceof List) {
        		lst = (List<Object>) recordDtoGenerico.getCampo(nomeCampo);
        	
        	Integer maxLength = Integer.parseInt(this.getParametri().getParametriMap().get("maxLength"));
        	
        	if (lst != null && lst.size() > 0 ) {        	
		        	if (lst.size() > maxLength) {
		        		  listaEsiti.add(creaEsitoKO(nomeCampo,this.getCodErrore(),this.getDesErrore()));
		        		  return listaEsiti;
		        	}
	        	}        	
        	}
        	
        		listaEsiti.add(creaEsitoOk(nomeCampo));
            
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException | NumberFormatException e) {
            log.error("Non è possibile validare la regola lunghezza del campo " + nomeCampo, e);
            throw new ValidazioneImpossibileException("Non è possibile validare la regola lunghezza del campo " + nomeCampo);
        }
        return listaEsiti;
    }

}
