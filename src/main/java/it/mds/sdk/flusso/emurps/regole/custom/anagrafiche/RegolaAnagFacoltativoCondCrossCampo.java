package it.mds.sdk.flusso.emurps.regole.custom.anagrafiche;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorValue;

import it.mds.sdk.anagrafiche.client.exceptions.MalformedRegistryException;
import it.mds.sdk.anagrafiche.client.exceptions.RegistryNotFoundException;
import it.mds.sdk.connettore.anagrafiche.gestore.anagrafica.GestoreAnagrafica;
import it.mds.sdk.connettore.anagrafiche.tabella.RecordAnagrafica;
import it.mds.sdk.gestoreesiti.modelli.Esito;
import it.mds.sdk.libreriaregole.dtos.RecordDtoGenerico;
import it.mds.sdk.libreriaregole.exception.ValidazioneImpossibileException;
import it.mds.sdk.libreriaregole.regole.beans.Parametri;
import it.mds.sdk.libreriaregole.regole.beans.RegolaGenerica;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@XmlDiscriminatorValue("regolaAnagFacoltativoCondCrossCampo")
public class RegolaAnagFacoltativoCondCrossCampo
		extends RegolaGenerica{


	public RegolaAnagFacoltativoCondCrossCampo(String nomeRegola, String codErrore, String desErrore, Parametri parametri) {
		super(nomeRegola, codErrore, desErrore, parametri);
	}

	/**
	 *
	 * Il campo in ingresso può essere inserito solo se un altro campo assume un determinato valore "valoreDipendente"
	 *
	 *
	 * @param nomeCampo campo da validare
	 * @param recordDtoGenerico DTO del record del flusso
	 * @return lista di {@link Esito}
	 */
	@Override
	public List<Esito> valida(String nomeCampo, RecordDtoGenerico recordDtoGenerico) {
		List<Esito> listaEsiti = new ArrayList<>();

		try {

			String valoreCampo = ((String) recordDtoGenerico.getCampo(nomeCampo) != null
					? (String) recordDtoGenerico.getCampo(nomeCampo)
					: null);
			
			String nomeCampoConfronto = this.getParametri().getParametriMap().get("campoDipendente");
			String valoreCampoDipendente = String.valueOf(recordDtoGenerico.getCampo(nomeCampoConfronto));
			String valoreConfronto = this.getParametri().getParametriMap().get("valoreDipendente");
			String nomeTabella = this.getParametri().getParametriMap().get("nomeTabella");
			String assistitoDatiAnagraficiResidenzaRegione = (recordDtoGenerico
					.getCampo("assistitoDatiAnagraficiResidenzaRegione") != null
							? (String) recordDtoGenerico.getCampo("assistitoDatiAnagraficiResidenzaRegione")
							: null);
			
			if(valoreCampoDipendente!= null && !valoreCampoDipendente.equals(valoreConfronto)){
				if (valoreCampo!=null) {					
					eseguiRegolaAnagrafica(nomeCampo, recordDtoGenerico, listaEsiti,nomeTabella, valoreCampo, assistitoDatiAnagraficiResidenzaRegione);
				} else {
					listaEsiti.add(creaEsitoOk(nomeCampo));
				}
			}else{
				listaEsiti.add(creaEsitoOk(nomeCampo));
			}

		} catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException | MalformedRegistryException | RegistryNotFoundException e) {
			log.error("Impossibile validare la regola dominio valori anagrafica per il campo " + nomeCampo, e );
			throw new ValidazioneImpossibileException("Impossibile validare la regola dominio valori anagrafica per il campo " + nomeCampo );
		}
		return listaEsiti;
	}

	private void eseguiRegolaAnagrafica(String nomeCampo, RecordDtoGenerico recordDtoGenerico, List<Esito> listaEsiti,
			String nomeTabella, String valoreCampo, String assistitoDatiAnagraficiResidenzaRegione) throws IllegalAccessException, NoSuchMethodException,
			InvocationTargetException, MalformedRegistryException, RegistryNotFoundException {

		GestoreAnagrafica gestoreAnagrafica = new GestoreAnagrafica();
		List<RecordAnagrafica> listaValori = gestoreAnagrafica.richiediAnagrafica(nomeTabella,false)
				.getRecordsAnagrafica();
		// recupero il dominio dei valori validi
		List<String> listaDominio;

		listaDominio = listaValori.stream()
				.filter(ra -> (ra.getValidoDa() != null && ra.getValidoA() != null
						&& (ra.getValidoDa().compareTo(LocalDateTime.now())
								* LocalDateTime.now().compareTo(ra.getValidoA())) >= 0)
						|| (ra.getValidoDa() == null && ra.getValidoA() == null))
				.map(RecordAnagrafica::getDato).collect(Collectors.toList());
		if (listaDominio.contains(assistitoDatiAnagraficiResidenzaRegione + "#" + valoreCampo) ||
				listaDominio.contains("999#" + valoreCampo)) {
			listaEsiti.add(creaEsitoOk(nomeCampo));
		} else {
			listaEsiti.add(creaEsitoKO(nomeCampo, this.getCodErrore(), this.getDesErrore()));
		}
	}

	public GestoreAnagrafica getGestoreAnagrafica() {
		return new GestoreAnagrafica();
	}

}
