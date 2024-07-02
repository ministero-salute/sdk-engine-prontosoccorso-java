package it.mds.sdk.flusso.emurps.regole.custom.adhoc;

import java.lang.reflect.InvocationTargetException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

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
@XmlDiscriminatorValue("RegolaEmurPSErogatoreCodIst")
public class RegolaEmurPSErogatoreCodIst extends RegolaGenerica {
	
	public RegolaEmurPSErogatoreCodIst(String nome, String codErrore, String desErrore, Parametri parametri) {
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
			
			// CODICE REGIONE INVIANTE - MITTENTE GAF - NECESSARIO PER CONTROLLO 1902
			// GLI HSP HANNO I PRIMI 3 CARATTERI DELLA REGIONE - NON SERVE LA CONCATENAZIONE - USO L'ANAGRAFICA SENZA CONCATENAZIONE
			/*
			 * IF (Codice Regione Inviante) <> (Codice Regione Erogazione) THEN
				 1902
				IF PRESENTE IN ANAGRAFICA HSP.11/11bis (condivisa)(Erogatore - Codice Istituto)#Codice Regione Erogazione THEN
				  NULL; 
				ELSE
				  IF nvl(ACCESSO-TIPO TRASMISSIONE, 'C') != 'C' THEN
				     1300
				  END IF;
				END IF; 
			 */
			
			String erogatoreCodiceIstituto = (recordDtoGenerico
					.getCampo("erogatoreCodiceIstituto") != null
							? (String) recordDtoGenerico.getCampo("erogatoreCodiceIstituto")
							: null);	
			
			String erogatoreCodiceReg = (recordDtoGenerico
					.getCampo("erogatoreCodiceIstituto") != null
							? ((String) recordDtoGenerico.getCampo("erogatoreCodiceIstituto")).substring(0,3)
							: null);	

			String tipoTrasmissione = (recordDtoGenerico
					.getCampo("tipoTrasmissione") != null
							? (String) recordDtoGenerico.getCampo("tipoTrasmissione")
							: "C");
			
			String codiceRegioneInviante = (recordDtoGenerico.getCampiInput().getCodiceRegioneInput() != null
					? (String) recordDtoGenerico.getCampiInput().getCodiceRegioneInput()
					: "");
			
			
			
			String codErr1301 = "1300";
			String desErr1301 = "Non appartenenza al dominio di riferimento per un campo chiave";
			
			String codErr1902 = "1902";
			String desErr1902 = "Incongruenza tra codice regione inviante (il campo \"codice regione di erogazione\" è diverso dal codice della regione inviante i dati)";
			
			if (!codiceRegioneInviante.equals(erogatoreCodiceReg)) {
				listaEsiti.add(creaEsitoKO(nomeCampo, codErr1902, desErr1902));
				return listaEsiti;
			}
			// TODOs: FB - ANOMALIA - COMMENTATA
//			else {
//				List<String> listaAnagHsp11EBis = Utilities.getValoriAnagrafica(recordDtoGenerico,"ANAG_EMUR_HSP11_E_BIS");
//
//				if (!listaAnagHsp11EBis.contains(erogatoreCodiceIstituto)) {
//					if (!"C".equalsIgnoreCase(tipoTrasmissione)) {
//						listaEsiti.add(creaEsitoKO(nomeCampo, codErr1301, desErr1301));
//						return listaEsiti;
//					}
//					
//				}
//			}
			
			listaEsiti.add(creaEsitoOk(nomeCampo));			
			
		} catch (InvocationTargetException | NoSuchMethodException |IllegalAccessException  e) {
			log.error("Non è possibile validare la regola RegolaEmurPSErogatoreCodIst " + nomeCampo, e);
			throw new ValidazioneImpossibileException("Non è possibile validare la regola RegolaEmurPSErogatoreCodIst " + nomeCampo );
		}catch (DateTimeParseException dataE) {
			listaEsiti.add(creaEsitoOk(nomeCampo));
		}
		return listaEsiti;
	}

}
