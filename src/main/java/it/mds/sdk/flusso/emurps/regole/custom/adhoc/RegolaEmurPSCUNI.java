package it.mds.sdk.flusso.emurps.regole.custom.adhoc;

import java.lang.reflect.InvocationTargetException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

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
@XmlDiscriminatorValue("RegolaEmurPSCUNI")
public class RegolaEmurPSCUNI extends RegolaGenerica {
	
	public RegolaEmurPSCUNI(String nome, String codErrore, String desErrore, Parametri parametri) {
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
			
			
			String assistitoCUNI = (recordDtoGenerico
					.getCampo("assistitoCUNI") != null
							? (String) recordDtoGenerico.getCampo("assistitoCUNI")
							: "");
			
			byte[] bytes = assistitoCUNI.getBytes();
			long checkSum = getCRC32Checksum(bytes);
			
			
			String codErr = "1301";
			String desErr = "Non appartenenza al dominio di riferimento per un campo obbligatorio o a obbligatorietà condizionata";
			
			
//			listaEsiti.add(creaEsitoKO(nomeCampo, codErr, desErr));
			listaEsiti.add(creaEsitoOk(nomeCampo));	
			
			
		} catch (InvocationTargetException | NoSuchMethodException |IllegalAccessException e) {
			log.error("Non è possibile validare la regola RegolaEmurPSCUNI " + nomeCampo, e);
			throw new ValidazioneImpossibileException("Non è possibile validare la regola RegolaEmurPSCUNI " + nomeCampo );
		}catch (DateTimeParseException dataE) {
			listaEsiti.add(creaEsitoOk(nomeCampo));
		}
		return listaEsiti;
	}
	
	public static long getCRC32Checksum(byte[] bytes) {
	    Checksum crc32 = new CRC32();
	    crc32.update(bytes, 0, bytes.length);
	    return crc32.getValue();
	}

}
