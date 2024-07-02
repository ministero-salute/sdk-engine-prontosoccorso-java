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
class RegolaRangeOccorrenzeAmmesseTest {

	@Mock
	RecordDtoGenerico recordMockito;
	
	@Test
	void validaCostruttore() {
		RegolaRangeOccorrenzeAmmesse regola = new RegolaRangeOccorrenzeAmmesse();
		assertTrue(regola instanceof RegolaRangeOccorrenzeAmmesse);
	}
	
	@Test
    void validaOK() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Map<String, String> parametri = new HashMap<String, String>();
        
		parametri.put("maxLength", "4");
        
        Parametri parametriTest = new Parametri();
        parametriTest.setParametriMap(parametri);

        RegolaRangeOccorrenzeAmmesse regola = new RegolaRangeOccorrenzeAmmesse("RegolaRangeOccorrenzeAmmesse", "9999", "descrizioneErrore",parametriTest);
        Mockito.when(recordMockito.getCampo("campoRecord")).thenReturn(null);
        
        List<Esito> result = regola.valida("campoRecord", recordMockito);
        for (Esito e : result) {
            assertTrue(e.isValoreEsito());
        }
    }
	@Test
    void validaKO() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Map<String, String> parametri = new HashMap<String, String>();
        
		parametri.put("maxLength", "4");
        
        Parametri parametriTest = new Parametri();
        parametriTest.setParametriMap(parametri);

        ArrayList<String> valori = new ArrayList<>();
        valori.add("12345");
        valori.add("223456");
        valori.add("223456");
        valori.add("223456");
        valori.add("223456");
        
        RegolaRangeOccorrenzeAmmesse regola = new RegolaRangeOccorrenzeAmmesse("RegolaRangeOccorrenzeAmmesse", "9999", "descrizioneErrore",parametriTest);
        Mockito.when(recordMockito.getCampo("campoRecord")).thenReturn(valori);
        
        List<Esito> result = regola.valida("campoRecord", recordMockito);
        for (Esito e : result) {
            assertFalse(e.isValoreEsito());
            assertEquals("9999", e.getErroriValidazione().get(0).getCodice());
        }
    }
}