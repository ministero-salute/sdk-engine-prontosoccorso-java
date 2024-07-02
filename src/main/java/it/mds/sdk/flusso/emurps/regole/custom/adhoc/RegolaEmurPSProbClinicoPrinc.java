package it.mds.sdk.flusso.emurps.regole.custom.adhoc;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.math.NumberUtils;
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
@XmlDiscriminatorValue("RegolaEmurPSProbClinicoPrinc")
public class RegolaEmurPSProbClinicoPrinc extends RegolaGenerica {
	
	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
	
	public RegolaEmurPSProbClinicoPrinc(String nome, String codErrore, String desErrore, Parametri parametri) {
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
							? ((String) recordDtoGenerico.getCampo("assistitoDimissioneEsitoTrattamento"))
							: null);
			
			String assistitoOBIProblemaClinicoPrincipale = (recordDtoGenerico
					.getCampo("assistitoOBIProblemaClinicoPrincipale") != null
							? ((String) recordDtoGenerico.getCampo("assistitoOBIProblemaClinicoPrincipale"))
							: null);
			
			String assistitoDatiAnagraficiEtaNascitaAnno = (recordDtoGenerico
					.getCampo("assistitoDatiAnagraficiEtaNascitaAnno") != null
							? (String) recordDtoGenerico.getCampo("assistitoDatiAnagraficiEtaNascitaAnno")
							: null);
			
			List<String> listaQuadroClinico = Utilities.getValoriAnagrafica(recordDtoGenerico,"ANAG_EMUR_QUADRO_CLINICO",assistitoOBIProblemaClinicoPrincipale);
			List<String> listaQuadroClinicoMag = Utilities.getValoriAnagrafica(recordDtoGenerico,"ANAG_EMUR_QUADRO_CLINICO_MAG",assistitoOBIProblemaClinicoPrincipale);
			List<String> listaQuadroClinicoMin = Utilities.getValoriAnagrafica(recordDtoGenerico,"ANAG_EMUR_QUADRO_CLINICO_MIN",assistitoOBIProblemaClinicoPrincipale);

			
			String codErr1 = "1301";
			String desErr1 = "Non appartenenza al dominio di riferimento per un campo obbligatorio o a obbligatorietà condizionata";
			
			String codErr2 = "1007";
			String desErr2 = "L’età del paziente non è congruente con il problema clinico principale indicato";
			
			long annoNascitaAss = (NumberUtils.isParsable(assistitoDatiAnagraficiEtaNascitaAnno) ? Long.parseLong(assistitoDatiAnagraficiEtaNascitaAnno) : 0);
			 LocalDate currentDate = LocalDate.now();
			 int currentYear = currentDate.getYear() ;
			
			 if ("10".equals(assistitoDimissioneEsitoTrattamento)) {
					if (listaQuadroClinico.contains(assistitoOBIProblemaClinicoPrincipale)) {
						if ( currentYear - annoNascitaAss >= 18 ) {
//							 IF NON PRESENTE ANAGRAFICA Quadro clinico MAG-MIN(diagnosi) per chiave (cod_tip_paz='A' ed 'E')(TRIM(upper(OBI Problema clinico principale))) then
							if (!listaQuadroClinicoMag.contains(assistitoOBIProblemaClinicoPrincipale)) {
								listaEsiti.add(creaEsitoKO(nomeCampo, codErr2, desErr2));
								return listaEsiti;
							}
							
						}else { // paziente < 18 anni
//							IF NON PRESENTE ANAGRAFICA  Quadro clinicoMAG-MIN(diagnosi) per chiave (cod_tip_paz='A' ,'E','P') (TRIM(upper(OBI Problema clinico principale))) then
							if (!listaQuadroClinicoMin.contains(assistitoOBIProblemaClinicoPrincipale)) {
								listaEsiti.add(creaEsitoKO(nomeCampo, codErr2, desErr2));
								return listaEsiti;
							}
						}
					}else {
						listaEsiti.add(creaEsitoKO(nomeCampo, codErr1, desErr1));
						return listaEsiti;
					}
			 }
			 
			 if (Utilities.verificaEsitoOK(listaEsiti)) {
	        		listaEsiti.clear();
	        		listaEsiti.add(creaEsitoOk(nomeCampo));	
	        	}
			
			
		} catch (InvocationTargetException | NoSuchMethodException |IllegalAccessException | MalformedRegistryException |RegistryNotFoundException e) {
			log.error("Non è possibile validare la regola RegolaEmurPSProbClinicoPrinc " + nomeCampo, e);
			throw new ValidazioneImpossibileException("Non è possibile validare la regola RegolaEmurPSProbClinicoPrinc " + nomeCampo );
		}catch (DateTimeParseException dataE) {
			listaEsiti.add(creaEsitoOk(nomeCampo));
		}
		return listaEsiti;
	}

}
