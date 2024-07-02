package it.mds.sdk.flusso.emurps.regole.custom.adhoc;

import java.lang.reflect.InvocationTargetException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorValue;

import it.mds.sdk.flusso.emurps.parser.regole.Utilities;
import it.mds.sdk.gestoreesiti.modelli.Esito;
import it.mds.sdk.libreriaregole.dtos.RecordDtoGenerico;
import it.mds.sdk.libreriaregole.exception.ValidazioneImpossibileException;
import it.mds.sdk.libreriaregole.regole.beans.Parametri;
import it.mds.sdk.libreriaregole.regole.beans.RegolaGenerica;
import it.mds.sdk.libreriaregole.regole.catalogo.RegolaListaValoriAmmessi;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@XmlDiscriminatorValue("RegolaEmurPSPrestPrinc")
public class RegolaEmurPSTrauma extends RegolaGenerica {
	
	public RegolaEmurPSTrauma(String nome, String codErrore, String desErrore, Parametri parametri) {
		super(nome, codErrore, desErrore, parametri);
	}

	/**
	 *
	 * @param nomeCampo campo da validare
	 * @param recordDtoGenerico DTO del record del flusso
	 * @return lista di {@link it.mds.sdk.gestoreesiti.modelli.Esito}
	 */
	@Override
	public List<Esito> valida(String nomeCampo, RecordDtoGenerico recordDtoGenerico) {
		List<Esito> listaEsiti = new ArrayList<>();
		try {
			
			
			String problemaPrincipale = (recordDtoGenerico
					.getCampo("problemaPrincipale") != null
							? (String) recordDtoGenerico.getCampo("problemaPrincipale")
							: null);
			
			
			if ("10".equals(problemaPrincipale)) {
				
				Parametri parametri = new Parametri();
				// prima regola
				parametri.getParametriMap().put("listaValoriAmmessi", "1|2|3|4|5|6|7|9");
				RegolaListaValoriAmmessi regolaListaValoriAmmessi = new RegolaListaValoriAmmessi(
						"regolaListaValoriAmmessi", this.getCodErrore(), this.getDesErrore(), parametri);
				
				List<Esito> lst = regolaListaValoriAmmessi.valida(nomeCampo, recordDtoGenerico);
				Utilities.addErrore(listaEsiti,lst);
				if (Utilities.verificaEsitoOK(listaEsiti)) 
					listaEsiti.clear();
					
			}
			
			if (Utilities.verificaEsitoOK(listaEsiti)) {
				listaEsiti.clear();
				listaEsiti.add(creaEsitoOk(nomeCampo));	
			}				
			
			
		} catch (InvocationTargetException | NoSuchMethodException |IllegalAccessException  e) {
			log.error("Non è possibile validare la regola RegolaEmurPSPrestPrinc " + nomeCampo, e);
			throw new ValidazioneImpossibileException("Non è possibile validare la regola RegolaEmurPSPrestPrinc " + nomeCampo );
		}catch (DateTimeParseException dataE) {
			listaEsiti.add(creaEsitoOk(nomeCampo));
		}
		return listaEsiti;
	}
}
