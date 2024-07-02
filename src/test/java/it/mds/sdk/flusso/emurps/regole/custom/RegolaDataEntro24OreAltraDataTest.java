/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.flusso.emurps.regole.custom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import it.mds.sdk.gestoreesiti.modelli.Esito;
import it.mds.sdk.libreriaregole.dtos.RecordDtoGenerico;
import it.mds.sdk.libreriaregole.exception.ValidazioneImpossibileException;
import it.mds.sdk.libreriaregole.regole.beans.Parametri;

@ExtendWith(MockitoExtension.class)
class RegolaDataEntro24OreAltraDataTest {
	@Mock
	RecordDtoGenerico recordMockito;

	@Test
	void validaCostruttore() {
		RegolaDataEntro24OreAltraData regola = new RegolaDataEntro24OreAltraData();
		assertTrue(regola instanceof RegolaDataEntro24OreAltraData);
	}

	@Test
	void validaKO() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
		Map<String, String> parametri = new HashMap<String, String>();
//        parametri.put("nomeCampoValidatore", "assistitoDimissioneData");
		parametri.put("startDate", "assistitoPrestazioniPresaInCaricoData");
		parametri.put("startOra", "assistitoPrestazioniPresaInCaricoOra");

		parametri.put("endDate", "entrataData");
		parametri.put("endOra", "entrataOra");

		Parametri parametriTest = new Parametri();
		parametriTest.setParametriMap(parametri);

		RegolaDataEntro24OreAltraData regola = new RegolaDataEntro24OreAltraData("RegolaDataEntro24OreAltraData",
				"2066", "descrizioneErrore", parametriTest);
//		Mockito.when(recordMockito.getCampo("startDate")).thenReturn("2022-12-12");
//		Mockito.when(recordMockito.getCampo("startOra")).thenReturn("13:00");
//
//		Mockito.when(recordMockito.getCampo("endDate")).thenReturn("2022-12-12");
//		Mockito.when(recordMockito.getCampo("endOra")).thenReturn("13:00");

		Mockito.when(recordMockito.getCampo("assistitoPrestazioniPresaInCaricoData")).thenReturn("2022-12-12");
		Mockito.when(recordMockito.getCampo("assistitoPrestazioniPresaInCaricoOra")).thenReturn("12:00");
		
		Mockito.when(recordMockito.getCampo("entrataData")).thenReturn("2022-12-10");
		Mockito.when(recordMockito.getCampo("entrataOra")).thenReturn("12:00");
		
		List<Esito> result = regola.valida("dataRecord", recordMockito);
		for (Esito e : result) {
			assertFalse(e.isValoreEsito());
			assertEquals("2066", e.getErroriValidazione().get(0).getCodice());
		}
	}
	
	@Test
	void validaOK() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
		Map<String, String> parametri = new HashMap<String, String>();
//        parametri.put("nomeCampoValidatore", "assistitoDimissioneData");
		parametri.put("startDate", "assistitoPrestazioniPresaInCaricoData");
		parametri.put("startOra", "assistitoPrestazioniPresaInCaricoOra");

		parametri.put("endDate", "entrataData");
		parametri.put("endOra", "entrataOra");

		Parametri parametriTest = new Parametri();
		parametriTest.setParametriMap(parametri);

		RegolaDataEntro24OreAltraData regola = new RegolaDataEntro24OreAltraData("RegolaDataEntro24OreAltraData",
				"2066", "descrizioneErrore", parametriTest);
//		Mockito.when(recordMockito.getCampo("startDate")).thenReturn("2022-12-12");
//		Mockito.when(recordMockito.getCampo("startOra")).thenReturn("13:00");
//
//		Mockito.when(recordMockito.getCampo("endDate")).thenReturn("2022-12-12");
//		Mockito.when(recordMockito.getCampo("endOra")).thenReturn("13:00");

		Mockito.when(recordMockito.getCampo("assistitoPrestazioniPresaInCaricoData")).thenReturn("2022-12-12");
		Mockito.when(recordMockito.getCampo("assistitoPrestazioniPresaInCaricoOra")).thenReturn("12:00");
		
		Mockito.when(recordMockito.getCampo("entrataData")).thenReturn("2022-12-12");
		Mockito.when(recordMockito.getCampo("entrataOra")).thenReturn("12:00");
		
		List<Esito> result = regola.valida("dataRecord", recordMockito);
		for (Esito e : result) {
			assertTrue(e.isValoreEsito());
		}
	}

	@Test
	void testRegolaDataExceptionKO() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		Map<String, String> parametri = new HashMap<String, String>();
		parametri.put("startDate", "assistitoPrestazioniPresaInCaricoData");
		parametri.put("startOra", "assistitoPrestazioniPresaInCaricoOra");

		parametri.put("endDate", "entrataData");
		parametri.put("endOra", "entrataOra");

		Parametri parametriTest = new Parametri();
		parametriTest.setParametriMap(parametri);

		RegolaDataEntro24OreAltraData regola = new RegolaDataEntro24OreAltraData("RegolaDataEntro24OreAltraData",
				"2066", "descrizioneErrore", parametriTest);
//		Mockito.when(recordMockito.getCampo("startDate")).thenReturn("2022-12-12");
//		Mockito.when(recordMockito.getCampo("startOra")).thenReturn("13:00");
//
//		Mockito.when(recordMockito.getCampo("endDate")).thenReturn("2022-12-12");
//		Mockito.when(recordMockito.getCampo("endOra")).thenReturn("13:00");

		Mockito.when(recordMockito.getCampo("assistitoPrestazioniPresaInCaricoData")).thenReturn("2022-12-12");
		Mockito.when(recordMockito.getCampo("assistitoPrestazioniPresaInCaricoOra")).thenReturn("12:00");

		Mockito.when(recordMockito.getCampo("entrataData")).thenReturn("2022-12-10");
		Mockito.when(recordMockito.getCampo("entrataOra")).thenReturn("12:00");
		
		List<Esito> result = regola.valida("dataRecord", recordMockito);
		for (Esito e : result) {
			assertFalse(e.isValoreEsito());
			assertEquals("2066", e.getErroriValidazione().get(0).getCodice());
		}
	}

//	@Test
//	void testException() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
//		RegolaDataEntro24OreAltraData regola = new RegolaDataEntro24OreAltraData("RegolaDataEntro24OreAltraData",
//				"2066", "descrizioneErrore", null);
//		BDDMockito.willThrow(new IllegalAccessException()).given(recordMockito).getCampo("startDate");
//		assertThrows(ValidazioneImpossibileException.class, () -> regola.valida("startDate", recordMockito));
//	}
}