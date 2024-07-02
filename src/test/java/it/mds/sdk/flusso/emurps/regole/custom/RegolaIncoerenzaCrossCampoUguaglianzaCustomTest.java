/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.flusso.emurps.regole.custom;

import static org.junit.jupiter.api.Assertions.*;

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
class RegolaIncoerenzaCrossCampoUguaglianzaCustomTest {

	@Mock
	RecordDtoGenerico recordMockito;
	
	@Test
	void validaCostruttore() {
		RegolaIncoerenzaCrossCampoUguaglianzaCustom regola = new RegolaIncoerenzaCrossCampoUguaglianzaCustom();
		assertTrue(regola instanceof RegolaIncoerenzaCrossCampoUguaglianzaCustom);
	}
	
	@Test
    void validaKO() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Map<String, String> parametri = new HashMap<String, String>();
        
        parametri.put("nomeCampoCoerente", "campo");
        parametri.put("valoreConfronto", "valore1");
        parametri.put("listaValoriCoerenti", "valore1");
        
        Parametri parametriTest = new Parametri();
        parametriTest.setParametriMap(parametri);

        RegolaIncoerenzaCrossCampoUguaglianzaCustom regola = new RegolaIncoerenzaCrossCampoUguaglianzaCustom("RegolaIncoerenzaCrossCampoUguaglianzaCustom", "9999", "descrizioneErrore",parametriTest);
        Mockito.when(recordMockito.getCampo("campo")).thenReturn("valore1");
        Mockito.when(recordMockito.getCampo("campoRecord")).thenReturn("valore2");
        
        List<Esito> result = regola.valida("campoRecord", recordMockito);
        for (Esito e : result) {
            assertFalse(e.isValoreEsito());
            assertEquals("9999", e.getErroriValidazione().get(0).getCodice());
        }
    }
	
	@Test
    void validaOK() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Map<String, String> parametri = new HashMap<String, String>();
        
        parametri.put("nomeCampoCoerente", "campo");
        parametri.put("valoreConfronto", "valore1");
        parametri.put("listaValoriCoerenti", "valore1");
        
        Parametri parametriTest = new Parametri();
        parametriTest.setParametriMap(parametri);

        RegolaIncoerenzaCrossCampoUguaglianzaCustom regola = new RegolaIncoerenzaCrossCampoUguaglianzaCustom("RegolaIncoerenzaCrossCampoUguaglianzaCustom", "9999", "descrizioneErrore",parametriTest);
        Mockito.when(recordMockito.getCampo("campo")).thenReturn("valore1");
        Mockito.when(recordMockito.getCampo("campoRecord")).thenReturn("valore1");
        
        List<Esito> result = regola.valida("campoRecord", recordMockito);
        for (Esito e : result) {
            assertTrue(e.isValoreEsito());
        }
    }
}