package it.mds.sdk.flusso.emurps.regole.custom.adhoc;

import java.lang.reflect.InvocationTargetException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
@XmlDiscriminatorValue("RegolaEmurPSPrestPrinc")
public class RegolaEmurPSPrestPrinc extends RegolaGenerica {
	
	public RegolaEmurPSPrestPrinc(String nome, String codErrore, String desErrore, Parametri parametri) {
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
			String assistitoPrestazioniPrestazionePrestazionePrincipale = (recordDtoGenerico
					.getCampo("assistitoPrestazioniPrestazionePrestazionePrincipale") != null
							? (String) recordDtoGenerico.getCampo("assistitoPrestazioniPrestazionePrestazionePrincipale")
							: "");
		
			String assistitoDatiAnagraficiResidenzaRegione = (recordDtoGenerico
					.getCampo("assistitoDatiAnagraficiResidenzaRegione") != null
							? (String) recordDtoGenerico.getCampo("assistitoDatiAnagraficiResidenzaRegione")
							: null);
			
			List<String> listaInterventi = Utilities.getValoriAnagrafica(recordDtoGenerico,"ANAG_EMUR_INTERVENTI",assistitoPrestazioniPrestazionePrestazionePrincipale.toUpperCase());
			List<String> listaPrestazioniNaz = Utilities.getValoriAnagrafica(recordDtoGenerico,"ANAG_EMUR_PRESTAZIONI_NAZIONALE",assistitoPrestazioniPrestazionePrestazionePrincipale.toUpperCase());
			List<String> listaPrestazioniReg = Utilities.getValoriAnagrafica(recordDtoGenerico,"ANAG_EMUR_PRESTAZIONI_REGIONALI",assistitoDatiAnagraficiResidenzaRegione+"#"+assistitoPrestazioniPrestazionePrestazionePrincipale.toUpperCase());

			String codErr = "1301";
			String desErr = "Non appartenenza al dominio di riferimento per un campo obbligatorio o a obbligatorietà condizionata";
			
			if (!"6".equals(assistitoDimissioneEsitoTrattamento) && !"7".equals(assistitoDimissioneEsitoTrattamento)) {
				if (!StringUtils.isBlank(assistitoPrestazioniPrestazionePrestazionePrincipale)) {
					
					if (!listaInterventi.contains(assistitoPrestazioniPrestazionePrestazionePrincipale.toUpperCase())) {
						if (!listaPrestazioniNaz.contains(assistitoPrestazioniPrestazionePrestazionePrincipale.toUpperCase())) {
							if (!listaPrestazioniReg.contains(assistitoDatiAnagraficiResidenzaRegione+"#"+assistitoPrestazioniPrestazionePrestazionePrincipale.toUpperCase())) {
								listaEsiti.add(creaEsitoKO(nomeCampo, codErr, desErr));
								return listaEsiti;
							}
						}
					}
				}
					
			}
			
			listaEsiti.add(creaEsitoOk(nomeCampo));	
			
			
		} catch (InvocationTargetException | NoSuchMethodException |IllegalAccessException | MalformedRegistryException |RegistryNotFoundException e) {
			log.error("Non è possibile validare la regola RegolaEmurPSPrestPrinc " + nomeCampo, e);
			throw new ValidazioneImpossibileException("Non è possibile validare la regola RegolaEmurPSPrestPrinc " + nomeCampo );
		}catch (DateTimeParseException dataE) {
			listaEsiti.add(creaEsitoOk(nomeCampo));
		}
		return listaEsiti;
	}

}
