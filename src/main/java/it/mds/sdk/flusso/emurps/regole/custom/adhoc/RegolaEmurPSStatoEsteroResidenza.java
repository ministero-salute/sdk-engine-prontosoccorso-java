package it.mds.sdk.flusso.emurps.regole.custom.adhoc;

import java.lang.reflect.InvocationTargetException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorValue;

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
@XmlDiscriminatorValue("RegolaEmurPSStatoEsteroResidenza")
public class RegolaEmurPSStatoEsteroResidenza extends RegolaGenerica {
	
	public RegolaEmurPSStatoEsteroResidenza(String nome, String codErrore, String desErrore, Parametri parametri) {
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
			String assistitoDatiAnagraficiResidenzaStatoEstero = (recordDtoGenerico
					.getCampo("assistitoDatiAnagraficiResidenzaStatoEstero") != null
							? ((String) recordDtoGenerico.getCampo("assistitoDatiAnagraficiResidenzaStatoEstero"))
							: "");
			
//			String codErr1 = "1301";
//			String desErr1 = "Non appartenenza al dominio di riferimento per un campo obbligatorio o ad obbligatorietà condizionata";
//			String codErr2 = "20073";
//			String desErr2 = "Il campo non può essere valorizzato con IT";
//			String codErr3 = "20077";
//			String desErr3 = "Incongruenza tra Stato estero di residenza e Regione/comune di residenza";
			
			String codErr = "2007";
			String desErr = "Incongruenza tra Stato estero di residenza e Regione/comune di residenza";
			
			
			if (StringUtils.isBlank(assistitoDatiAnagraficiResidenzaStatoEstero)) {
				if ("999".equals(assistitoDatiAnagraficiResidenzaRegione) ||
						"999999".equals(assistitoDatiAnagraficiResidenzaComune)) {
					listaEsiti.add(creaEsitoKO(nomeCampo, codErr, desErr));
				}
			} 
			
			// TODOs: FB - ANOMALIA - COMMENTATA - 1301
			// TODOs: FB - ANOMALIA - COMMENTATA - 20073
			// TODOs: FB - ANOMALIA - COMMENTATA - 20077
//			if (!StringUtils.isBlank(assistitoDatiAnagraficiResidenzaStatoEstero)) {
//				List<String> listaNazioni = Utilities.getValoriAnagrafica(recordDtoGenerico,"ANAG_EMUR_SOLO_NAZIONI");
//				if ("ZZ".equals(assistitoDatiAnagraficiResidenzaStatoEstero)) {
//					listaEsiti.add(creaEsitoKO(nomeCampo, codErr1, desErr1));
//					return listaEsiti;
//				}else if(listaNazioni.contains(assistitoDatiAnagraficiResidenzaStatoEstero)) {
//					if ("IT".equals(assistitoDatiAnagraficiResidenzaStatoEstero)) {
//						listaEsiti.add(creaEsitoKO(nomeCampo, codErr2, desErr2));
//						return listaEsiti;
//					}else if ("999".equals(assistitoDatiAnagraficiResidenzaRegione) && 
//							"999999".equals(assistitoDatiAnagraficiResidenzaComune)) {
//						listaEsiti.add(creaEsitoOk(nomeCampo));	
//						return listaEsiti;
//					}else {
//						listaEsiti.add(creaEsitoKO(nomeCampo, codErr3, desErr3));
//					}
//				}else {
//					listaEsiti.add(creaEsitoKO(nomeCampo, codErr1, desErr1));
//					return listaEsiti;
//				}
//			}
			
			 if (Utilities.verificaEsitoOK(listaEsiti)) {
	        		listaEsiti.clear();
	        		listaEsiti.add(creaEsitoOk(nomeCampo));	
	        	}
			
		} catch (InvocationTargetException | NoSuchMethodException |IllegalAccessException e) {
			log.error("Non è possibile validare la regola RegolaEmurPSStatoEsteroResidenza " + nomeCampo, e);
			throw new ValidazioneImpossibileException("Non è possibile validare la regola RegolaEmurPSStatoEsteroResidenza " + nomeCampo );
		}catch (DateTimeParseException dataE) {
			listaEsiti.add(creaEsitoOk(nomeCampo));
		}
		return listaEsiti;
	}


}
