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
import it.mds.sdk.flusso.emurps.regole.custom.RegolaMinMaxLunghezzaCampoFacoltativo;
import it.mds.sdk.gestoreesiti.modelli.Esito;
import it.mds.sdk.libreriaregole.dtos.RecordDtoGenerico;
import it.mds.sdk.libreriaregole.exception.ValidazioneImpossibileException;
import it.mds.sdk.libreriaregole.regole.beans.Parametri;
import it.mds.sdk.libreriaregole.regole.beans.RegolaGenerica;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@XmlDiscriminatorValue("RegolaEmurPSPrestPrincOBI")
public class RegolaEmurPSPrestPrincOBI extends RegolaGenerica {
	
	public RegolaEmurPSPrestPrincOBI(String nome, String codErrore, String desErrore, Parametri parametri) {
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
			String assistitoOBIPrestazionePrincipaleErogata = (recordDtoGenerico
					.getCampo("assistitoOBIPrestazionePrincipaleErogata") != null
							? (String) recordDtoGenerico.getCampo("assistitoOBIPrestazionePrincipaleErogata")
							: null);
			
			String assistitoDatiAnagraficiResidenzaRegione = (recordDtoGenerico
					.getCampo("assistitoDatiAnagraficiResidenzaRegione") != null
							? (String) recordDtoGenerico.getCampo("assistitoDatiAnagraficiResidenzaRegione")
							: null);
			
			
			List<String> listaInterventi = Utilities.getValoriAnagrafica(recordDtoGenerico,"ANAG_EMUR_INTERVENTI",assistitoOBIPrestazionePrincipaleErogata.toUpperCase());
			List<String> listaPrestazioniNaz = Utilities.getValoriAnagrafica(recordDtoGenerico,"ANAG_EMUR_PRESTAZIONI_NAZIONALE",assistitoOBIPrestazionePrincipaleErogata.toUpperCase());
			List<String> listaPrestazioniReg = Utilities.getValoriAnagrafica(recordDtoGenerico,"ANAG_EMUR_PRESTAZIONI_REGIONALI",assistitoDatiAnagraficiResidenzaRegione+"#"+assistitoOBIPrestazionePrincipaleErogata.toUpperCase());

			String codErr = "1301";
			String desErr = "Non appartenenza al dominio di riferimento per un campo obbligatorio o a obbligatorietà condizionata";
			
			if ("10".equals(assistitoDimissioneEsitoTrattamento)) {
				
				// terza regola
				Parametri parametri = new Parametri();
				parametri.getParametriMap().put("minLunghezza", "3");
				parametri.getParametriMap().put("maxLunghezza", "9");
				parametri.getParametriMap().put("facoltativo", "true");
				
				RegolaMinMaxLunghezzaCampoFacoltativo regolaMinMaxLunghezzaCampoFacoltativo = new RegolaMinMaxLunghezzaCampoFacoltativo(
						"regolaMinMaxLunghezzaCampoFacoltativo", "106",
						"Lunghezza non conforme a quella attesa per un campo ad obbligatorietà condizionata.", parametri);
				
				List<Esito> lstCond = regolaMinMaxLunghezzaCampoFacoltativo.valida(nomeCampo, recordDtoGenerico);		
				Utilities.addErrore(listaEsiti,lstCond);
				if (Utilities.verificaEsitoOK(listaEsiti)) 
					listaEsiti.clear();
				
				if (!StringUtils.isBlank(assistitoOBIPrestazionePrincipaleErogata)) {
					
					if (!listaInterventi.contains(assistitoOBIPrestazionePrincipaleErogata.toUpperCase())) {
						if (!listaPrestazioniNaz.contains(assistitoOBIPrestazionePrincipaleErogata.toUpperCase())) {
							if (!listaPrestazioniReg.contains(assistitoDatiAnagraficiResidenzaRegione+"#"+assistitoOBIPrestazionePrincipaleErogata.toUpperCase())) {								
									listaEsiti.add(creaEsitoKO(nomeCampo, codErr, desErr));
									return listaEsiti;								
							}
						}
					}
				}
			}
			if (Utilities.verificaEsitoOK(listaEsiti)) {
				listaEsiti.clear();
				listaEsiti.add(creaEsitoOk(nomeCampo));	
			}
			
		} catch (InvocationTargetException | NoSuchMethodException |IllegalAccessException | MalformedRegistryException |RegistryNotFoundException e) {
			log.error("Non è possibile validare la regola RegolaEmurPSPrestPrincOBI " + nomeCampo, e);
			throw new ValidazioneImpossibileException("Non è possibile validare la regola RegolaEmurPSPrestPrincOBI " + nomeCampo );
		}catch (DateTimeParseException dataE) {
			listaEsiti.add(creaEsitoOk(nomeCampo));
		}
		return listaEsiti;
	}

}
