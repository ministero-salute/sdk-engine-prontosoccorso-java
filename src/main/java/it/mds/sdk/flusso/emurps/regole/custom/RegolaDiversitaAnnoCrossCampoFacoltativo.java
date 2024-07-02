/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.flusso.emurps.regole.custom;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
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
@XmlDiscriminatorValue("RegolaDiversitaAnnoCrossCampoFacoltativo")
public class RegolaDiversitaAnnoCrossCampoFacoltativo extends RegolaGenerica {

    public RegolaDiversitaAnnoCrossCampoFacoltativo(String nomeRegola, String codErrore, String desErrore, Parametri parametri) {
        super(nomeRegola, codErrore, desErrore, parametri);
    }

    /**
     * Controlla Se il campo anno Entrata Data è uguale all'anno di Accesso Identificativo (substring)  (opzionale -> + 1 sul secondo parametro)
     *
     * @param nomeCampo         campo da validare
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
        	
        	String valoreSuccessivo = ("true".equalsIgnoreCase(this.getParametri().getParametriMap().get("valoreSuccessivo"))) ? "true" : "false" ;
        	
            String nomeCampoConfronto = this.getParametri().getParametriMap().get("campoDipendente");
            String valoreCampoDipendente = String.valueOf(recordDtoGenerico.getCampo(nomeCampoConfronto));
//            String valoreConfronto = this.getParametri().getParametriMap().get("valoreDipendente");

            if (valoreCampo != null && valoreCampoDipendente != null) {
            	
            	int intValoreDipendente=0;
            	if (valoreCampoDipendente.length() > 4 && NumberUtils.isParsable(valoreCampoDipendente.substring(0,4)))
            		intValoreDipendente = Integer.parseInt(valoreCampoDipendente.substring(0,4));
            		
            	int intValoreCampo=1;
            	if (valoreCampo.length() > 4 && NumberUtils.isParsable(String.valueOf(valoreCampo).substring(0,4)))
            		intValoreCampo = Integer.parseInt(String.valueOf(valoreCampo).substring(0,4));
            	
            	
                if (intValoreCampo == intValoreDipendente || intValoreCampo+1 == intValoreDipendente) {
                	listaEsiti.add(creaEsitoOk(nomeCampo));
                } else {
                	
                	String concatMsg = " deve essere uguale all'anno del campo "+ nomeCampoConfronto;
                	if ("true".equalsIgnoreCase(valoreSuccessivo))
                		concatMsg = " deve essere uguale all'anno o all'anno + 1 del campo "+ nomeCampoConfronto;
                	                   
                    listaEsiti.add(creaEsitoKO(nomeCampo,this.getCodErrore(),"L'anno contenuto nel campo " + nomeCampo + concatMsg));
                }
            } else {
            	listaEsiti.add(creaEsitoOk(nomeCampo));
            }


        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            log.error("Impossibile validare la regola RegolaDiversitaAnnoCrossCampoFacoltativo per il campo " + nomeCampo, e);
            throw new ValidazioneImpossibileException("Impossibile validare la regola RegolaDiversitaAnnoCrossCampoFacoltativo per il campo " + nomeCampo);
        }
        return listaEsiti;
    }
}
