package it.mds.sdk.flusso.emurps.regole.custom.adhoc;

import java.lang.reflect.InvocationTargetException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
@XmlDiscriminatorValue("RegolaEmurPSRegioneResidenza")
public class RegolaEmurPSRegioneResidenza extends RegolaGenerica {
	private static final String COMUNE_999999 = "999999"; 
	private static final String REGIONE_999 = "999"; 
	
	public RegolaEmurPSRegioneResidenza(String nome, String codErrore, String desErrore, Parametri parametri) {
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
//							? ((String) recordDtoGenerico.getCampo("assistitoDatiAnagraficiResidenzaStatoEstero")).toUpperCase(Locale.ROOT)
//							: null);
			
//			String codErr20077 = "20077";
//			String desErr20077 = "Incongruenza tra Stato Estero di residenza e Regione/ Comune/ ASL di residenza";
		
			String codErr20074 = "20074";
			String desErr20074 = "Incongruenza tra Regione Residenza e Comune Residenza";
			
			String codErr114 = "114";
			String desErr114 = "Non appartenenza al dominio di riferimento per un campo obbligatorio";
			
			
			// 20074
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
					List<String> listaComuniMRA = Utilities.getValoriAnagrafica(recordDtoGenerico,"ANAG_EMUR_REGIONI",assistitoDatiAnagraficiResidenzaRegione);
					if (!listaComuniMRA.contains(assistitoDatiAnagraficiResidenzaRegione)) {
						listaEsiti.add(creaEsitoKO(nomeCampo, codErr114, desErr114));
						return listaEsiti;
					}
				}
			}
			// TODOs: FB - ANOMALIA - COMMENTATA - 20077
//			if (!StringUtils.isBlank(assistitoDatiAnagraficiResidenzaRegione)) {
//				if (REGIONE_999.equals(assistitoDatiAnagraficiResidenzaRegione)) {
//					if(COMUNE_999999.equals(assistitoDatiAnagraficiResidenzaComune)) {
//						if (!StringUtils.isBlank(assistitoDatiAnagraficiResidenzaStatoEstero)) {
//							if ("IT".equals(assistitoDatiAnagraficiResidenzaStatoEstero)) {
//								listaEsiti.add(creaEsitoKO(nomeCampo, codErr20077, desErr20077));
//								return listaEsiti;
//							}
//						}else {
//							listaEsiti.add(creaEsitoKO(nomeCampo, codErr20077, desErr20077));
//							return listaEsiti;
//						}
//					}
//				}else {
//					if(!COMUNE_999999.equals(assistitoDatiAnagraficiResidenzaComune)) {
//						if (!StringUtils.isBlank(assistitoDatiAnagraficiResidenzaStatoEstero)) {
//							listaEsiti.add(creaEsitoKO(nomeCampo, codErr20077, desErr20077));
//							return listaEsiti;
//						}
//					}
//				}
//			}
				
			listaEsiti.add(creaEsitoOk(nomeCampo));	
     
			
		} catch (InvocationTargetException | NoSuchMethodException |IllegalAccessException | MalformedRegistryException  |RegistryNotFoundException  e) {
			log.error("Non è possibile validare la regola RegolaEmurPSRegioneResidenza " + nomeCampo, e);
			throw new ValidazioneImpossibileException("Non è possibile validare la regola RegolaEmurPSRegioneResidenza " + nomeCampo );
		}catch (DateTimeParseException dataE) {
			listaEsiti.add(creaEsitoOk(nomeCampo));
		}
		return listaEsiti;
	}


}
