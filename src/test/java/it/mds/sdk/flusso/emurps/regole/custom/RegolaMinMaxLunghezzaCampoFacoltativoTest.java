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
class RegolaMinMaxLunghezzaCampoFacoltativoTest {

	@Mock
	RecordDtoGenerico recordMockito;
	
	@Test
	void validaCostruttore() {
		RegolaMinMaxLunghezzaCampoFacoltativo regola = new RegolaMinMaxLunghezzaCampoFacoltativo();
		assertTrue(regola instanceof RegolaMinMaxLunghezzaCampoFacoltativo);
	}
	@Test
    void validaKO() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Map<String, String> parametri = new HashMap<String, String>();
        
        parametri.put("minLunghezza", "3");
		parametri.put("maxLunghezza", "6");
		parametri.put("facoltativo", "true");
        
        Parametri parametriTest = new Parametri();
        parametriTest.setParametriMap(parametri);

        ArrayList<String> valori = new ArrayList<>();
        valori.add("1");
        valori.add("2");
        RegolaMinMaxLunghezzaCampoFacoltativo regola = new RegolaMinMaxLunghezzaCampoFacoltativo("RegolaMinMaxLunghezzaCampoFacoltativo", "9999", "descrizioneErrore",parametriTest);
        Mockito.when(recordMockito.getCampo("campoRecord")).thenReturn(valori);
        
        List<Esito> result = regola.valida("campoRecord", recordMockito);
        for (Esito e : result) {
            assertFalse(e.isValoreEsito());
            assertEquals("9999", e.getErroriValidazione().get(0).getCodice());
        }
    }
	@Test
    void validaKO2() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Map<String, String> parametri = new HashMap<String, String>();
        
        parametri.put("minLunghezza", "3");
		parametri.put("maxLunghezza", "6");
		parametri.put("facoltativo", "false");
        
        Parametri parametriTest = new Parametri();
        parametriTest.setParametriMap(parametri);

        RegolaMinMaxLunghezzaCampoFacoltativo regola = new RegolaMinMaxLunghezzaCampoFacoltativo("RegolaMinMaxLunghezzaCampoFacoltativo", "9999", "descrizioneErrore",parametriTest);
        Mockito.when(recordMockito.getCampo("campoRecord")).thenReturn("1");
        
        List<Esito> result = regola.valida("campoRecord", recordMockito);
        for (Esito e : result) {
            assertFalse(e.isValoreEsito());
            assertEquals("9999", e.getErroriValidazione().get(0).getCodice());
        }
    }
	
	@Test
    void validaOK() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Map<String, String> parametri = new HashMap<String, String>();
        
        parametri.put("minLunghezza", "3");
		parametri.put("maxLunghezza", "6");
		parametri.put("facoltativo", "true");
        
        Parametri parametriTest = new Parametri();
        parametriTest.setParametriMap(parametri);

        RegolaMinMaxLunghezzaCampoFacoltativo regola = new RegolaMinMaxLunghezzaCampoFacoltativo("RegolaMinMaxLunghezzaCampoFacoltativo", "9999", "descrizioneErrore",parametriTest);
        Mockito.when(recordMockito.getCampo("campoRecord")).thenReturn("123456");
        
        List<Esito> result = regola.valida("campoRecord", recordMockito);
        for (Esito e : result) {
            assertTrue(e.isValoreEsito());
        }
    }	
}