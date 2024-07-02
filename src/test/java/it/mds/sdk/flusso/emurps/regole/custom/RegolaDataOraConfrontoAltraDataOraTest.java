/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.flusso.emurps.regole.custom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import it.mds.sdk.gestoreesiti.modelli.Esito;
import it.mds.sdk.libreriaregole.dtos.RecordDtoGenerico;
import it.mds.sdk.libreriaregole.regole.beans.Parametri;

@ExtendWith(MockitoExtension.class)
class RegolaDataOraConfrontoAltraDataOraTest {
	@Mock
	RecordDtoGenerico recordMockito;

	@Test
	void validaCostruttore() {
		RegolaDataOraConfrontoAltraDataOra regola = new RegolaDataOraConfrontoAltraDataOra();
		assertTrue(regola instanceof RegolaDataOraConfrontoAltraDataOra);
	}
	
	@Test
	void validaKO() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
		Map<String, String> parametri = new HashMap<String, String>();

		
		parametri.put("campoPrimaData", "assistitoOBIDataUscita");
		//parametri.put("campoPrimaOra", "assistitoOBIOraUscita");
		parametri.put("operatoreLogico", "minore");
		parametri.put("campoSecondaData", "assistitoOBIDataIngresso");
	//	parametri.put("campoSecondaOra", "assistitoOBIOraIngresso");


		Parametri parametriTest = new Parametri();
		parametriTest.setParametriMap(parametri);

		RegolaDataOraConfrontoAltraDataOra regola = new RegolaDataOraConfrontoAltraDataOra("RegolaDataOraConfrontoAltraDataOra",
				"1136", "descrizioneErrore", parametriTest);
//		Mockito.when(recordMockito.getCampo("campoPrimaData")).thenReturn("2022-12-12");
		//Mockito.when(recordMockito.getCampo("campoPrimaOra")).thenReturn("13:00");

//		Mockito.when(recordMockito.getCampo("campoSecondaData")).thenReturn("2022-12-12");
		//Mockito.when(recordMockito.getCampo("campoSecondaOra")).thenReturn("13:00");

//		Mockito.when(recordMockito.getCampo("operatoreLogico")).thenReturn("minore");
		
		Mockito.when(recordMockito.getCampo("assistitoOBIDataUscita")).thenReturn("2022-12-12");
		Mockito.when(recordMockito.getCampo("assistitoOBIDataIngresso")).thenReturn("2022-12-12");

		
		List<Esito> result = regola.valida("dataRecord", recordMockito);
		for (Esito e : result) {
			assertTrue(e.isValoreEsito());
		}
	}
	
	@Test
	void validaOK() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
		Map<String, String> parametri = new HashMap<String, String>();

		
		parametri.put("campoPrimaData", "assistitoOBIDataUscita");
		//parametri.put("campoPrimaOra", "assistitoOBIOraUscita");
		parametri.put("operatoreLogico", "minore");
		parametri.put("campoSecondaData", "assistitoOBIDataIngresso");
	//	parametri.put("campoSecondaOra", "assistitoOBIOraIngresso");


		Parametri parametriTest = new Parametri();
		parametriTest.setParametriMap(parametri);

		RegolaDataOraConfrontoAltraDataOra regola = new RegolaDataOraConfrontoAltraDataOra("RegolaDataOraConfrontoAltraDataOra",
				"1136", "descrizioneErrore", parametriTest);
//		Mockito.when(recordMockito.getCampo("campoPrimaData")).thenReturn("2022-12-12");
		//Mockito.when(recordMockito.getCampo("campoPrimaOra")).thenReturn("13:00");

//		Mockito.when(recordMockito.getCampo("campoSecondaData")).thenReturn("2022-12-12");
		//Mockito.when(recordMockito.getCampo("campoSecondaOra")).thenReturn("13:00");

//		Mockito.when(recordMockito.getCampo("operatoreLogico")).thenReturn("minore");
		
		Mockito.when(recordMockito.getCampo("assistitoOBIDataUscita")).thenReturn("2022-12-11");
		Mockito.when(recordMockito.getCampo("assistitoOBIDataIngresso")).thenReturn("2022-12-12");

		
		List<Esito> result = regola.valida("dataRecord", recordMockito);
		for (Esito e : result) {
			assertFalse(e.isValoreEsito());
			assertEquals("1136", e.getErroriValidazione().get(0).getCodice());
		}
	}
	
}