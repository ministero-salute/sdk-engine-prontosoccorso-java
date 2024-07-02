/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.flusso.emurps.regole.custom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import it.mds.sdk.gestoreesiti.modelli.Esito;
import it.mds.sdk.libreriaregole.dtos.RecordDtoGenerico;
import it.mds.sdk.libreriaregole.regole.beans.Parametri;

@ExtendWith(MockitoExtension.class)
class RegolaDataPosterioreOUgualeTest {

	@Mock
	RecordDtoGenerico recordMockito;
	
	@Test
	void validaCostruttore() {
		RegolaDataPosterioreOUguale regola = new RegolaDataPosterioreOUguale();
		assertTrue(regola instanceof RegolaDataPosterioreOUguale);
	}
	
	@Test
    void validaOK() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Map<String, String> parametri = new HashMap<String, String>();
        parametri.put("nomeCampoValidatore", "assistitoDimissioneData");

        Parametri parametriTest = new Parametri();
        parametriTest.setParametriMap(parametri);

        RegolaDataPosterioreOUguale regola = new RegolaDataPosterioreOUguale("RegolaDataPosterioreOUguale", "9999", "descrizioneErrore",parametriTest);
        Mockito.when(recordMockito.getCampo("assistitoDimissioneData")).thenReturn("2022-01-25");
        Mockito.when(recordMockito.getCampo("anno")).thenReturn("2022");
        
        List<Esito> result = regola.valida("anno", recordMockito);
        for (Esito e : result) {
            assertTrue(e.isValoreEsito());
        }
    }
	
	   @Test
	    void validaKO() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
	        Map<String, String> parametri = new HashMap<String, String>();
	        parametri.put("nomeCampoValidatore", "assistitoDimissioneData");

	        Parametri parametriTest = new Parametri();
	        parametriTest.setParametriMap(parametri);

	        RegolaDataPosterioreOUguale regola = new RegolaDataPosterioreOUguale("RegolaDataPosterioreOUguale", "9999", "descrizioneErrore",parametriTest);
	        Mockito.when(recordMockito.getCampo("assistitoDimissioneData")).thenReturn("2022-01-25");
	        Mockito.when(recordMockito.getCampo("anno")).thenReturn("2022-01-23");
	        
	        List<Esito> result = regola.valida("anno", recordMockito);
	        for (Esito e : result) {
	            assertFalse(e.isValoreEsito());
	            assertEquals("9999", e.getErroriValidazione().get(0).getCodice());
	        }
	    }
}