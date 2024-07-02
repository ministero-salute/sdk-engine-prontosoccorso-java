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
@XmlDiscriminatorValue("RegolaEmurPSComuneResidenza")
public class RegolaEmurPSComuneResidenza extends RegolaGenerica {
	private static final String COMUNE_999999 = "999999"; 
	private static final String REGIONE_999 = "999"; 
	
	public RegolaEmurPSComuneResidenza(String nome, String codErrore, String desErrore, Parametri parametri) {
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
			
			String assistitoDatiAnagraficiResidenzaComune = (recordDtoGenerico
					.getCampo("assistitoDatiAnagraficiResidenzaComune") != null
							? (String) recordDtoGenerico.getCampo("assistitoDatiAnagraficiResidenzaComune")
							: null);
			String assistitoDatiAnagraficiResidenzaRegione = (recordDtoGenerico
					.getCampo("assistitoDatiAnagraficiResidenzaRegione") != null
							? (String) recordDtoGenerico.getCampo("assistitoDatiAnagraficiResidenzaRegione")
							: null);
//			String assistitoDatiAnagraficiResidenzaStatoEstero = (recordDtoGenerico
//					.getCampo("assistitoDatiAnagraficiResidenzaStatoEstero") != null
//							? (String) recordDtoGenerico.getCampo("assistitoDatiAnagraficiResidenzaStatoEstero")
//							: null);
			
			
			
//			String codErr20077 = "20077";
//			String desErr20077 = "Incongruenza tra Stato Estero di residenza e Regione/ Comune /ASL di residenza.";
//			String codErr1301 = "1301";
//			String desErr1301 = "non appartenenza al dominio di riferimento per un campo obbligatorio o ad obbligatorietà condizionata";
			
			String codErr20074 = "20074";
			String desErr20074 = "Incongruenza tra Regione Residenza e Comune Residenza";			
			
			if (COMUNE_999999.equals(assistitoDatiAnagraficiResidenzaComune) ) {
				if (!REGIONE_999.equals(assistitoDatiAnagraficiResidenzaRegione)) {
					listaEsiti.add(creaEsitoKO(nomeCampo, codErr20074, desErr20074));
					return listaEsiti;
				}
			}else {
				if (REGIONE_999.equals(assistitoDatiAnagraficiResidenzaRegione)) {			
					listaEsiti.add(creaEsitoKO(nomeCampo, codErr20074, desErr20074));
					return listaEsiti;
				}else {
					// verifica anagrafica
					List<String> listaComuniMRA = Utilities.getValoriAnagrafica(recordDtoGenerico,"ANAG_EMUR_COMUNI_MRA",assistitoDatiAnagraficiResidenzaComune+"#"+assistitoDatiAnagraficiResidenzaRegione);
					if (!listaComuniMRA.contains(assistitoDatiAnagraficiResidenzaComune+"#"+assistitoDatiAnagraficiResidenzaRegione)) {
						listaEsiti.add(creaEsitoKO(nomeCampo, codErr20074, desErr20074));
						return listaEsiti;
					}
				}
			}
			
			// TODOs: FB - ANOMALIA - COMMENTATA - 20077
			// TODOs: FB - ANOMALIA - COMMENTATA - 1301
//			if ("999999".equals(assistitoDatiAnagraficiResidenzaComune)) {
//				if ("999".equals(assistitoDatiAnagraficiResidenzaRegione)) {
//					if (!StringUtils.isBlank(assistitoDatiAnagraficiResidenzaStatoEstero)) {
//						if ("IT".equals(assistitoDatiAnagraficiResidenzaStatoEstero)) {
//							listaEsiti.add(creaEsitoKO(nomeCampo, codErr20077, desErr20077));
//							return listaEsiti;
//						}
//					}else {
//						listaEsiti.add(creaEsitoKO(nomeCampo, codErr20077, desErr20077));
//						return listaEsiti;
//					}
//				}
//			}else if (!"999".equals(assistitoDatiAnagraficiResidenzaRegione)) {
//				if (StringUtils.isBlank(assistitoDatiAnagraficiResidenzaStatoEstero)) {
//					List<String> listaComuniConStatoEstero = Utilities.getValoriAnagrafica(recordDtoGenerico,"ANAG_EMUR_COMUNE_CON_ESTERO");					
//					if (!listaComuniConStatoEstero.contains(assistitoDatiAnagraficiResidenzaComune+"#"+assistitoDatiAnagraficiResidenzaRegione)) {
//						listaEsiti.add(creaEsitoKO(nomeCampo, codErr1301, desErr1301));
//						return listaEsiti;
//					}
//				}else {
//					listaEsiti.add(creaEsitoKO(nomeCampo, codErr20077, desErr20077));
//					return listaEsiti;
//				}
//			}
			
			listaEsiti.add(creaEsitoOk(nomeCampo));
				
			
		} catch (InvocationTargetException | NoSuchMethodException |IllegalAccessException | MalformedRegistryException |RegistryNotFoundException e) {
			log.error("Non è possibile validare la regola RegolaEmurPSComuneResidenza " + nomeCampo, e);
			throw new ValidazioneImpossibileException("Non è possibile validare la regola RegolaEmurPSComuneResidenza " + nomeCampo );
		}catch (DateTimeParseException dataE) {
			listaEsiti.add(creaEsitoOk(nomeCampo));
		}
		return listaEsiti;
	}
}
