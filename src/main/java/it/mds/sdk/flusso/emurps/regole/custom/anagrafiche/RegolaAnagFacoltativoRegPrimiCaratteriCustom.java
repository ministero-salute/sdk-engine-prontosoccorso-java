package it.mds.sdk.flusso.emurps.regole.custom.anagrafiche;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import it.mds.sdk.flusso.emurps.parser.regole.Utilities;
import org.apache.commons.lang3.StringUtils;
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
@XmlDiscriminatorValue("regolaDominioValoriAnagrafica")
public class RegolaAnagFacoltativoRegPrimiCaratteriCustom extends RegolaGenerica{

	public RegolaAnagFacoltativoRegPrimiCaratteriCustom(String nome, String codErrore, String desErrore, Parametri parametri) {
		super(nome, codErrore, desErrore, parametri);
	}

	/**
	 * 	    IF TRIM(ID Centrale Operativa – Identificativo) IS NOT NULL E
	 * 		NON   PRESENTE ANAGRAFICA Regioni (MRA - condivisa) PER CHIAVE(substr(TRIM(upper(ID Centrale Operativa – Identificativo)), 1, 3)) 
	 * 		ALLORA 2032 	 
	 * 
	 */
	/**
	 *
	 * Controlla che il valore del campo in input "nomeCampo"  sia contenuto in un dominio di valori di anagrafica
	 * @param nomeCampo campo da validare
	 * @param recordDtoGenerico DTO del record del flusso
	 * @return lista di {@link Esito}
	 */
	@Override
	public List<Esito> valida(String nomeCampo, RecordDtoGenerico recordDtoGenerico) {
		List<Esito> listaEsiti = new ArrayList<>();
		try {
			String campoDaValidare = String.valueOf(recordDtoGenerico.getCampo(nomeCampo));
			String nomeTabella = this.getParametri().getParametriMap().get("nomeTabella");

			String modalitaArrivo = String.valueOf(recordDtoGenerico.getCampo("modalitaArrivo"));
			String responsabileInvio = String.valueOf(recordDtoGenerico.getCampo("responsabileInvio"));
			
		
			if ( ("1".equals(modalitaArrivo) || "4".equals(modalitaArrivo) || "6".equals(modalitaArrivo)) 
					&& "5".equals(responsabileInvio) ){
			
				if (StringUtils.isBlank(campoDaValidare )) {
					listaEsiti.add(creaEsitoOk(nomeCampo));
					return listaEsiti;
				}else { // se valorizzato
					if (campoDaValidare.length() >= 3) {
						campoDaValidare = campoDaValidare.substring(0,3);
						List<String> listaDominio = getAnagrafica(recordDtoGenerico,campoDaValidare, nomeTabella);
						if(listaDominio.contains(campoDaValidare)){
							listaEsiti.add(creaEsitoOk(nomeCampo));
							return listaEsiti;
						}else{
							listaEsiti.add(creaEsitoKO(nomeCampo,this.getCodErrore(),this.getDesErrore()));
							return listaEsiti;
						}
					}
					
				}
			}
			
			listaEsiti.add(creaEsitoOk(nomeCampo));
			
		} catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException | MalformedRegistryException | RegistryNotFoundException e) {
			log.error("Impossibile validare la regola dominio valori anagrafica per il campo " + nomeCampo, e );
			throw new ValidazioneImpossibileException("Impossibile validare la regola dominio valori anagrafica per il campo " + nomeCampo );
		}
		return listaEsiti;

	}

	private List<String> getAnagrafica(RecordDtoGenerico recordDtoGenerico,String campoDaValidare, String nomeTabella)
			throws MalformedRegistryException, RegistryNotFoundException {
	//	GestoreAnagrafica gestoreAnagrafica = new GestoreAnagrafica();
		//List<RecordAnagrafica> listaValori = gestoreAnagrafica.richiediAnagrafica(nomeTabella, campoDaValidare,false).getRecordsAnagrafica();
		List<String> listaDominio = Utilities.getValoriAnagrafica(recordDtoGenerico,nomeTabella,campoDaValidare);

		//recupero il dominio dei valori validi
		//List<String> listaDominio;
		
		//listaDominio =listaValori
		//		  .stream()
		//		  .filter(ra -> (ra.getValidoDa()!= null && ra.getValidoA()!=null && (ra.getValidoDa().compareTo(LocalDateTime.now()) * LocalDateTime.now().compareTo(ra.getValidoA())) >= 0)
		//				  		|| (ra.getValidoDa()==null && ra.getValidoA()==null) )
		//		  .map(RecordAnagrafica::getDato)
		//		  .collect(Collectors.toList());
		return listaDominio;
	}


}
