package it.mds.sdk.flusso.emurps.regole.custom.adhoc;

import java.lang.reflect.InvocationTargetException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorValue;

import it.mds.sdk.anagrafiche.client.exceptions.MalformedRegistryException;
import it.mds.sdk.anagrafiche.client.exceptions.RegistryNotFoundException;
import it.mds.sdk.connettore.anagrafiche.gestore.anagrafica.GestoreAnagrafica;
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
@XmlDiscriminatorValue("RegolaEmurPSASLResidenza")
public class RegolaEmurPSASLResidenza extends RegolaGenerica {

	public RegolaEmurPSASLResidenza(String nome, String codErrore, String desErrore, Parametri parametri) {
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
			
			String assistitoDatiAnagraficiResidenzaASL = (recordDtoGenerico
					.getCampo("assistitoDatiAnagraficiResidenzaASL") != null
							? (String) recordDtoGenerico.getCampo("assistitoDatiAnagraficiResidenzaASL")
							: null);
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
							? ((String) recordDtoGenerico.getCampo("assistitoDatiAnagraficiResidenzaStatoEstero")).toUpperCase(Locale.ROOT)
							: null);
			
			List<String> listaNazioni = Utilities.getValoriAnagrafica(recordDtoGenerico,"ANAG_EMUR_SOLO_NAZIONI",assistitoDatiAnagraficiResidenzaStatoEstero);
			List<String> listaRegioni = Utilities.getValoriAnagrafica(recordDtoGenerico,"ANAG_EMUR_REGIONI",assistitoDatiAnagraficiResidenzaRegione);
			List<String> listaComuni = Utilities.getValoriAnagrafica(recordDtoGenerico,"ANAG_EMUR_COMUNI_MRA",assistitoDatiAnagraficiResidenzaComune+"#"+assistitoDatiAnagraficiResidenzaRegione);
			List<String> listaAziende = Utilities.getValoriAnagrafica(recordDtoGenerico,"ANAG_EMUR_AZIENDA_MRA",assistitoDatiAnagraficiResidenzaRegione + "#"	+ assistitoDatiAnagraficiResidenzaASL);

			String codErr20077 = "20077";
			String desErr20077 = "Incongruenza tra ASL Residenza e Comune Residenza";
			String codErr1302 = "1302";
			String desErr1302 = "Non appartenenza al dominio di riferimento per un campo facoltativo";
			
			if (!StringUtils.isBlank(assistitoDatiAnagraficiResidenzaASL)) {

				if ("999999".equals(assistitoDatiAnagraficiResidenzaComune)
						&& "999".equals(assistitoDatiAnagraficiResidenzaRegione)
						&& !StringUtils.isBlank(assistitoDatiAnagraficiResidenzaStatoEstero)
						&& !"IT".equals(assistitoDatiAnagraficiResidenzaStatoEstero)
						&& listaNazioni.contains(assistitoDatiAnagraficiResidenzaStatoEstero)) {
					if ("999".equals(assistitoDatiAnagraficiResidenzaASL)) {
						listaEsiti.add(creaEsitoOk(nomeCampo));						
					}
				} else if (!StringUtils.isBlank(assistitoDatiAnagraficiResidenzaComune)
						&& !"999999".equals(assistitoDatiAnagraficiResidenzaComune)
						&& !StringUtils.isBlank(assistitoDatiAnagraficiResidenzaRegione)
						&& !"999".equals(assistitoDatiAnagraficiResidenzaRegione)
						&& listaRegioni.contains(assistitoDatiAnagraficiResidenzaRegione)
						&& StringUtils.isBlank(assistitoDatiAnagraficiResidenzaStatoEstero)) {

					if (listaComuni.contains(assistitoDatiAnagraficiResidenzaComune+"#"+assistitoDatiAnagraficiResidenzaRegione)) { // DEVO CONCATENARE LA REGIONE?

						// creg := Comuni (MRA - condivisa)(residenza comune);
						// if( nvl(TRIM(creg), 'aaa') =
						// nvl(TRIM(assistitoDatiAnagraficiResidenzaRegione), 'bbb') THEN
//						if (assistitoDatiAnagraficiResidenzaComune.equalsIgnoreCase(assistitoDatiAnagraficiResidenzaRegione)) {

							if (listaAziende.contains(assistitoDatiAnagraficiResidenzaRegione + "#"	+ assistitoDatiAnagraficiResidenzaASL)) {
								// PRESENTE ANAGRAFICA AZIENDA(MRA - CONDIVISA)( residenza regione || residenza
								// asl) THEN
								listaEsiti.add(creaEsitoOk(nomeCampo));
								return listaEsiti;
							} else if ("999".equals(assistitoDatiAnagraficiResidenzaASL)) {
								listaEsiti.add(creaEsitoKO(nomeCampo, codErr20077, desErr20077));
								return listaEsiti;
							} else {
								listaEsiti.add(creaEsitoKO(nomeCampo, codErr1302, desErr1302));
								return listaEsiti;
							}
//						}
					} else if (listaAziende.contains(
							assistitoDatiAnagraficiResidenzaRegione + "#" + assistitoDatiAnagraficiResidenzaASL)) {
						// PRESENTE ANAGRAFICA AZIENDA(MRA - CONDIVISA)(residenza regione || residenza
						// asl) THEN
						listaEsiti.add(creaEsitoKO(nomeCampo, codErr20077, desErr20077));
						return listaEsiti;
					} else {
						listaEsiti.add(creaEsitoKO(nomeCampo, codErr1302, desErr1302));
						return listaEsiti;
					}

				} else if (listaAziende.contains(
						assistitoDatiAnagraficiResidenzaRegione + "#" + assistitoDatiAnagraficiResidenzaASL)) {
					// PRESENTE ANAGRAFICA AZIENDA(MRA - CONDIVISA)(residenza regione || residenza
					// asl) THEN
					listaEsiti.add(creaEsitoKO(nomeCampo, codErr20077, desErr20077));
					return listaEsiti;
				} else {
					listaEsiti.add(creaEsitoKO(nomeCampo, codErr1302, desErr1302));
					return listaEsiti;
				}
			} else if (listaAziende
					.contains(assistitoDatiAnagraficiResidenzaRegione + "#" + assistitoDatiAnagraficiResidenzaASL)) {
				// PRESENTE ANAGRAFICA AZIENDA(MRA - CONDIVISA)(residenza regione || residenza
				// asl) THEN
				listaEsiti.add(creaEsitoKO(nomeCampo, codErr20077, desErr20077));
				return listaEsiti;
			} else {
				listaEsiti.add(creaEsitoKO(nomeCampo, codErr1302, desErr1302));
				return listaEsiti;
			}
			
			if (Utilities.verificaEsitoOK(listaEsiti)) {
				listaEsiti.clear();
				listaEsiti.add(creaEsitoOk(nomeCampo));
			}
			
		} catch (InvocationTargetException | NoSuchMethodException |IllegalAccessException | MalformedRegistryException |RegistryNotFoundException e) {
			log.error("Non è possibile validare la regola RegolaEmurPSASLResidenza " + nomeCampo, e);
			throw new ValidazioneImpossibileException("Non è possibile validare la regola RegolaEmurPSASLResidenza " + nomeCampo );
		}catch (DateTimeParseException dataE) {
			listaEsiti.add(creaEsitoOk(nomeCampo));
		}
		return listaEsiti;
	}
}
