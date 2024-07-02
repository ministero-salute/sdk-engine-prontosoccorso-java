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
class RegolaIncongruenzaCrossDueCampiUguaglianzaTest {

	@Mock
	RecordDtoGenerico recordMockito;
	
	@Test
	void validaCostruttore() {
		RegolaIncongruenzaCrossDueCampiUguaglianza regola = new RegolaIncongruenzaCrossDueCampiUguaglianza();
		assertTrue(regola instanceof RegolaIncongruenzaCrossDueCampiUguaglianza);
	}
	@Test
    void validaKO() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Map<String, String> parametri = new HashMap<String, String>();
        
		parametri.put("parametroCampoCondizionante", "6|7");
		parametri.put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");
        
        Parametri parametriTest = new Parametri();
        parametriTest.setParametriMap(parametri);

        RegolaIncongruenzaCrossDueCampiUguaglianza regola = new RegolaIncongruenzaCrossDueCampiUguaglianza("RegolaIncongruenzaCrossDueCampiUguaglianza", "9999", "descrizioneErrore",parametriTest);
        Mockito.when(recordMockito.getCampo("campoRecord")).thenReturn("9");
        Mockito.when(recordMockito.getCampo("assistitoDimissioneEsitoTrattamento")).thenReturn("6");
        
        List<Esito> result = regola.valida("campoRecord", recordMockito);
        for (Esito e : result) {
            assertFalse(e.isValoreEsito());
            assertEquals("9999", e.getErroriValidazione().get(0).getCodice());
        }
    }
	
	@Test
    void validaOK() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Map<String, String> parametri = new HashMap<String, String>();
        
		parametri.put("parametroCampoCondizionante", "6|7");
		parametri.put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");
        
        Parametri parametriTest = new Parametri();
        parametriTest.setParametriMap(parametri);

        RegolaIncongruenzaCrossDueCampiUguaglianza regola = new RegolaIncongruenzaCrossDueCampiUguaglianza("RegolaIncongruenzaCrossDueCampiUguaglianza", "9999", "descrizioneErrore",parametriTest);
        Mockito.when(recordMockito.getCampo("campoRecord")).thenReturn("6");
        Mockito.when(recordMockito.getCampo("assistitoDimissioneEsitoTrattamento")).thenReturn("10");
        
        List<Esito> result = regola.valida("campoRecord", recordMockito);
        for (Esito e : result) {
            assertTrue(e.isValoreEsito());
        }
    }
}