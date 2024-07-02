/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.flusso.emurps.regole.custom;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
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
class RegolaDueLunghezzeAlternativeFacoltativoTest {

	@Mock
	RecordDtoGenerico recordMockito;
	
	@Test
	void validaCostruttore() {
		RegolaDueLunghezzeAlternativeFacoltativo regola = new RegolaDueLunghezzeAlternativeFacoltativo();
		assertTrue(regola instanceof RegolaDueLunghezzeAlternativeFacoltativo);
	}
	
	@Test
    void validaKO() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Map<String, String> parametri = new HashMap<String, String>();
        parametri.put("facoltativo", "true");
        parametri.put("campoDipendente", "campoLunghezza");

        parametri.put("size1", "6");
		parametri.put("size2", "8");
		
        Parametri parametriTest = new Parametri();
        parametriTest.setParametriMap(parametri);

        RegolaDueLunghezzeAlternativeFacoltativo regola = new RegolaDueLunghezzeAlternativeFacoltativo("RegolaDueLunghezzeAlternativeFacoltativo", "9999", "descrizioneErrore",parametriTest);
        Mockito.when(recordMockito.getCampo("campoLunghezza")).thenReturn("11");
        
        List<Esito> result = regola.valida("campoLunghezza", recordMockito);
        for (Esito e : result) {
            assertFalse(e.isValoreEsito());
            assertEquals("9999", e.getErroriValidazione().get(0).getCodice());
        }
    }
	
	 @Test
	    void validaOK() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
	        Map<String, String> parametri = new HashMap<String, String>();
	        parametri.put("facoltativo", "true");
	        parametri.put("campoDipendente", "campoLunghezza");

	        parametri.put("size1", "6");
			parametri.put("size2", "8");
			
	        Parametri parametriTest = new Parametri();
	        parametriTest.setParametriMap(parametri);

	        RegolaDueLunghezzeAlternativeFacoltativo regola = new RegolaDueLunghezzeAlternativeFacoltativo("RegolaDueLunghezzeAlternativeFacoltativo", "9999", "descrizioneErrore",parametriTest);
	        Mockito.when(recordMockito.getCampo("campoLunghezza")).thenReturn("testo123");
	        
	        List<Esito> result = regola.valida("campoLunghezza", recordMockito);
	        for (Esito e : result) {
	            assertTrue(e.isValoreEsito());
	        }
	    }
	 
	 @Test
	    void validaListOK() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
		 
		 ArrayList<String> lstParam = new ArrayList<>();
		 lstParam.add("123456");
		 lstParam.add("12345678");
		 
	        Map<String, String> parametri = new HashMap<String, String>();
	        parametri.put("facoltativo", "true");
	        parametri.put("campoDipendente", "campoLunghezza");

	        parametri.put("size1", "6");
			parametri.put("size2", "8");
			
	        Parametri parametriTest = new Parametri();
	        parametriTest.setParametriMap(parametri);

	        RegolaDueLunghezzeAlternativeFacoltativo regola = new RegolaDueLunghezzeAlternativeFacoltativo("RegolaDueLunghezzeAlternativeFacoltativo", "9999", "descrizioneErrore",parametriTest);
	        Mockito.when(recordMockito.getCampo("campoLunghezza")).thenReturn(lstParam);
	        
	        List<Esito> result = regola.valida("campoLunghezza", recordMockito);
	        for (Esito e : result) {
	            assertTrue(e.isValoreEsito());
	        }
	    }
}