package it.mds.sdk.flusso.emurps.regole.custom.adhoc;

import java.lang.reflect.InvocationTargetException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorValue;

import it.mds.sdk.anagrafiche.client.exceptions.MalformedRegistryException;
import it.mds.sdk.anagrafiche.client.exceptions.RegistryNotFoundException;
import it.mds.sdk.flusso.emurps.parser.regole.Utilities;
import it.mds.sdk.gestoreesiti.modelli.Esito;
import it.mds.sdk.libreriaregole.dtos.RecordDtoGenerico;
import it.mds.sdk.libreriaregole.exception.ValidazioneImpossibileException;
import it.mds.sdk.libreriaregole.regole.beans.Parametri;
import it.mds.sdk.libreriaregole.regole.beans.RegolaGenerica;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@XmlDiscriminatorValue("RegolaOBIDiagnosiPrincUscita")
public class RegolaOBIDiagnosiPrincUscita extends RegolaGenerica {
	
	public RegolaOBIDiagnosiPrincUscita(String nome, String codErrore, String desErrore, Parametri parametri) {
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
			
			String assistitoDimissioneEsitoTrattamento = (recordDtoGenerico
					.getCampo("assistitoDimissioneEsitoTrattamento") != null
							? (String) recordDtoGenerico.getCampo("assistitoDimissioneEsitoTrattamento")
							: null);
			String assistitoOBIDiagnosiPrincipaleUscita = (recordDtoGenerico
					.getCampo("assistitoOBIDiagnosiPrincipaleUscita") != null
							? (String) recordDtoGenerico.getCampo("assistitoOBIDiagnosiPrincipaleUscita")
							: null);
			
			
			List<String> listaAnagDiagnosi = Utilities.getValoriAnagrafica(recordDtoGenerico,"ANAG_EMUR_DIAGNOSI",assistitoOBIDiagnosiPrincipaleUscita);

			String codErr1301 = "1301";
			String desErr1301 = "Non appartenenza al dominio di riferimento per un campo obbligatorio o ad obbligatorietà condizionata";
			
			
			if ("10".equals(assistitoDimissioneEsitoTrattamento)) {
				if (!listaAnagDiagnosi.contains(assistitoOBIDiagnosiPrincipaleUscita)) {
					listaEsiti.add(creaEsitoKO(nomeCampo, codErr1301, desErr1301));
					return listaEsiti;
				}
			}
			
			
			listaEsiti.add(creaEsitoOk(nomeCampo));			
				
			
		} catch (InvocationTargetException | NoSuchMethodException |IllegalAccessException | MalformedRegistryException |RegistryNotFoundException e) {
			log.error("Non è possibile validare la regola RegolaOBIDiagnosiPrincUscita " + nomeCampo, e);
			throw new ValidazioneImpossibileException("Non è possibile validare la regola RegolaOBIDiagnosiPrincUscita " + nomeCampo );
		}catch (DateTimeParseException dataE) {
			listaEsiti.add(creaEsitoOk(nomeCampo));
		}
		return listaEsiti;
	}

}
