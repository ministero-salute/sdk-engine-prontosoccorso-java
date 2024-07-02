/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.flusso.emurps.regole.custom;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorValue;

import it.mds.sdk.flusso.emurps.tracciato.bean.output.DiagnosiSecondaria;
import it.mds.sdk.flusso.emurps.tracciato.bean.output.PrestazioneSecondaria;
import it.mds.sdk.gestoreesiti.modelli.Esito;
import it.mds.sdk.libreriaregole.dtos.RecordDtoGenerico;
import it.mds.sdk.libreriaregole.exception.ValidazioneImpossibileException;
import it.mds.sdk.libreriaregole.regole.beans.Parametri;
import it.mds.sdk.libreriaregole.regole.beans.RegolaGenerica;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@XmlDiscriminatorValue("RegolaDueLunghezzeAlternativeFacoltativo")
public class RegolaDueLunghezzeAlternativeFacoltativo extends RegolaGenerica {

    public RegolaDueLunghezzeAlternativeFacoltativo(String nomeRegola, String codErrore, String desErrore, Parametri parametri) {
        super(nomeRegola, codErrore, desErrore, parametri);
    }

    /**
     * Verifica che la lunghezza del campo sia esattamente una delle due indicate
     *
     * @param nomeCampo         campo da validare
     * @param recordDtoGenerico DTO del record del flusso
     * @return lista di {@link it.mds.sdk.gestoreesiti.modelli.Esito}
     */

    @Override
    public List<Esito> valida(String nomeCampo, RecordDtoGenerico recordDtoGenerico) {
        List<Esito> listaEsiti = new ArrayList<>();
        try {
        	
        	
        	String daValidare = null;
        	List<Object> lst = null;
        	
        	if (recordDtoGenerico.getCampo(nomeCampo) == null) {
        		listaEsiti.add(creaEsitoOk(nomeCampo));
        		return listaEsiti;
        	
        	}else if (recordDtoGenerico.getCampo(nomeCampo) instanceof String) {
        		daValidare = (String) recordDtoGenerico.getCampo(nomeCampo);
        		eseguiRegolaCustom(nomeCampo, listaEsiti, daValidare);
        	}else if (recordDtoGenerico.getCampo(nomeCampo) instanceof List) {
        		lst = (List<Object>) recordDtoGenerico.getCampo(nomeCampo);
        	
        	if (lst != null && lst.size()<=0)
        		listaEsiti.add(creaEsitoKO(nomeCampo,this.getCodErrore(),"La lunghezza del campo errata"));
        	else {
    
	        	Iterator it = lst.iterator();
		        	while (it.hasNext()) {
						Object object = (Object) it.next();
						
						if (object instanceof DiagnosiSecondaria) {
							daValidare = ((DiagnosiSecondaria) object).getValue();
							eseguiRegolaCustom(nomeCampo, listaEsiti, daValidare);
						}else if (object instanceof PrestazioneSecondaria) {
							daValidare = ((PrestazioneSecondaria) object).getValue();
							eseguiRegolaCustom(nomeCampo, listaEsiti, daValidare);
						}else if (object instanceof String) {
							daValidare = String.valueOf(object);
							eseguiRegolaCustom(nomeCampo, listaEsiti, daValidare);
						}else
							listaEsiti.add(creaEsitoOk(nomeCampo));
					}
        		}
        	}
            
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException | NumberFormatException e) {
            log.error("Non è possibile validare la regola lunghezza del campo " + nomeCampo, e);
            throw new ValidazioneImpossibileException("Non è possibile validare la regola lunghezza del campo " + nomeCampo);
        }
        
        return listaEsiti;
    }

	private void eseguiRegolaCustom(String nomeCampo, List<Esito> listaEsiti, String daValidare) {
		// la variabile lunghezza credo debba contenere la lunghezza del campo letto dal record
		Integer lunghezza = StringUtils.isNotBlank(daValidare) ? daValidare.length(): 0;
		Integer lunghezza1 = Integer.parseInt(this.getParametri().getParametriMap().get("size1"));
		Integer lunghezza2 = Integer.parseInt(this.getParametri().getParametriMap().get("size2"));
		String facoltativo = (!StringUtils.isBlank(this.getParametri().getParametriMap().get("facoltativo"))
				? String.valueOf(this.getParametri().getParametriMap().get("facoltativo"))
				: null);
		
		boolean bFacoltativo = false;
		if ("true".equalsIgnoreCase(facoltativo))
			bFacoltativo = true;
		
		if (daValidare == null && bFacoltativo) {
			listaEsiti.add(creaEsitoOk(nomeCampo));
		}else {
		    if ((lunghezza == lunghezza1) || (lunghezza == lunghezza2) && (daValidare != null)) {
		    	listaEsiti.add(creaEsitoOk(nomeCampo));
		    } else {            	
		        listaEsiti.add(creaEsitoKO(nomeCampo,this.getCodErrore(),"La lunghezza del campo " + nomeCampo +
		                " deve essere uguale a "
		                + lunghezza1 + " o " + lunghezza2));
		    }
		}
	}
}
