package it.mds.sdk.flusso.emurps.regole.custom.anagrafiche;

import java.lang.reflect.InvocationTargetException;
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
@XmlDiscriminatorValue("regolaAnagTEAMCustom")
public class RegolaAnagTEAMCustom extends RegolaGenerica{

	public RegolaAnagTEAMCustom(String nome, String codErrore, String desErrore, Parametri parametri) {
		super(nome, codErrore, desErrore, parametri);
	}

	/**
	 * 
	 *  2003,Incongruenza tra Stato Estero di residenza e tessera TEAM. Se stato estero di residenza non comunitario 
	 *  		non deve essere valorizzato il codice TEAM
	 *  
	 *  2004,Incongruenza tra Stato Estero di residenza e tessera TEAM. Se stato estero di residenza non comunitario
	 *  		non deve essere valorizzato il codice TEAM
	 *  
	 *  2005, Incongruenza tra Stato Estero di residenza e tessera TEAM. Codice TEAM valorizzato con residenza non 
	 *  		appartenente al dominio di riferimento
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
			
			String campoDaValidare = (recordDtoGenerico.getCampo(nomeCampo) != null ? (String)recordDtoGenerico.getCampo(nomeCampo) : null);
			String tabellaNazioniNazCeu = this.getParametri().getParametriMap().get("nomeTabella1");
			String tabellaNazioni = this.getParametri().getParametriMap().get("nomeTabella2");
			String valoreCampoResidenzaStatoEstero = (recordDtoGenerico.getCampo("assistitoDatiAnagraficiResidenzaStatoEstero") != null ? 
					String.valueOf(recordDtoGenerico.getCampo("assistitoDatiAnagraficiResidenzaStatoEstero")) : null);
					
			List<String> listaNazioniCeu = Utilities.getValoriAnagrafica(recordDtoGenerico,tabellaNazioniNazCeu);
			List<String> listaNazioni = Utilities.getValoriAnagrafica(recordDtoGenerico,tabellaNazioni);

			
			if (!StringUtils.isBlank(valoreCampoResidenzaStatoEstero)
					&& listaNazioni.contains(valoreCampoResidenzaStatoEstero)
					&& listaNazioniCeu.contains(valoreCampoResidenzaStatoEstero + "#EU")
					&& StringUtils.isBlank(campoDaValidare)) {
				listaEsiti.add(creaEsitoKO(nomeCampo, "2003",
						"Incongruenza tra Stato Estero di residenza e tessera TEAM. Se stato estero di residenza non comunitario "
								+ "non deve essere valorizzato il codice TEAM"));
			} else if (!StringUtils.isBlank(valoreCampoResidenzaStatoEstero)
					&& listaNazioni.contains(valoreCampoResidenzaStatoEstero)
					&& listaNazioniCeu.contains(valoreCampoResidenzaStatoEstero + "#XE")
					&& !StringUtils.isBlank(campoDaValidare)) {
				listaEsiti.add(creaEsitoKO(nomeCampo, "2004",
						"Incongruenza tra Stato Estero di residenza e tessera TEAM. Se stato estero di residenza non comunitario "
						+ "non deve essere valorizzato il codice TEAM"));
			} else if (
					// CASO 1
					(!StringUtils.isBlank(valoreCampoResidenzaStatoEstero)
					&& listaNazioni.contains(valoreCampoResidenzaStatoEstero)
					&& listaNazioniCeu.contains(valoreCampoResidenzaStatoEstero + "#IT"))
					|| // CASO 2
					(
							(StringUtils.isBlank(valoreCampoResidenzaStatoEstero)
							|| !listaNazioni.contains(valoreCampoResidenzaStatoEstero))
							&& !StringUtils.isBlank(campoDaValidare))
					) {
				
				listaEsiti.add(creaEsitoKO(nomeCampo, "2005",
						"Incongruenza tra Stato Estero di residenza e tessera TEAM. Codice TEAM valorizzato con residenza non "
						+ "appartenente al dominio di riferimento"));
			} else
				listaEsiti.add(creaEsitoOk(nomeCampo));
			
			
		} catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException | MalformedRegistryException | RegistryNotFoundException e) {
			log.error("Impossibile validare la regola dominio valori anagrafica per il campo " + nomeCampo, e );
			throw new ValidazioneImpossibileException("Impossibile validare la regola dominio valori anagrafica per il campo " + nomeCampo );
		}
		return listaEsiti;

	}


}
