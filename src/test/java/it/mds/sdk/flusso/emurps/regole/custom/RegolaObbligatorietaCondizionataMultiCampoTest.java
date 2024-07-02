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
class RegolaObbligatorietaCondizionataMultiCampoTest {

	@Mock
	RecordDtoGenerico recordMockito;
	
	@Test
	void validaCostruttore() {
		RegolaObbligatorietaCondizionataMultiCampo regola = new RegolaObbligatorietaCondizionataMultiCampo();
		assertTrue(regola instanceof RegolaObbligatorietaCondizionataMultiCampo);
	}
	
	@Test
    void validaKO() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Map<String, String> parametri = new HashMap<String, String>();
        
        parametri.put("parametroCampoCondizionante", "1");
		parametri.put("nomeCampoCondizionante", "importoPosizioneAssistitoTicket");
        
        Parametri parametriTest = new Parametri();
        parametriTest.setParametriMap(parametri);

        RegolaObbligatorietaCondizionataMultiCampo regola = new RegolaObbligatorietaCondizionataMultiCampo("RegolaObbligatorietaCondizionataMultiCampo", "9999", "descrizioneErrore",parametriTest);
        Mockito.when(recordMockito.getCampo("campoRecord")).thenReturn(null);
        Mockito.when(recordMockito.getCampo("importoPosizioneAssistitoTicket")).thenReturn("1");
        
        List<Esito> result = regola.valida("campoRecord", recordMockito);
        for (Esito e : result) {
            assertFalse(e.isValoreEsito());
            assertEquals("9999", e.getErroriValidazione().get(0).getCodice());
        }
    }
	@Test
    void validaKO2() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Map<String, String> parametri = new HashMap<String, String>();
        
        parametri.put("parametroCampoCondizionante", "1");
		parametri.put("nomeCampoCondizionante", "importoPosizioneAssistitoTicket");
		parametri.put("nomeCampoCondizionante2", "campoCondizionante2");
		parametri.put("parametroCampoCondizionante2", "9|10");
		
        Parametri parametriTest = new Parametri();
        parametriTest.setParametriMap(parametri);

        RegolaObbligatorietaCondizionataMultiCampo regola = new RegolaObbligatorietaCondizionataMultiCampo("RegolaObbligatorietaCondizionataMultiCampo", "9999", "descrizioneErrore",parametriTest);
        Mockito.when(recordMockito.getCampo("campoRecord")).thenReturn(null);
        Mockito.when(recordMockito.getCampo("importoPosizioneAssistitoTicket")).thenReturn("1");
        Mockito.when(recordMockito.getCampo("campoCondizionante2")).thenReturn("9");
        
        List<Esito> result = regola.valida("campoRecord", recordMockito);
        for (Esito e : result) {
            assertFalse(e.isValoreEsito());
            assertEquals("9999", e.getErroriValidazione().get(0).getCodice());
        }
    }
	@Test
    void validaOK() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Map<String, String> parametri = new HashMap<String, String>();
        
        parametri.put("parametroCampoCondizionante", "1");
		parametri.put("nomeCampoCondizionante", "importoPosizioneAssistitoTicket");
        
        Parametri parametriTest = new Parametri();
        parametriTest.setParametriMap(parametri);

        RegolaObbligatorietaCondizionataMultiCampo regola = new RegolaObbligatorietaCondizionataMultiCampo("RegolaObbligatorietaCondizionataMultiCampo", "9999", "descrizioneErrore",parametriTest);
        Mockito.when(recordMockito.getCampo("campoRecord")).thenReturn("");
        Mockito.when(recordMockito.getCampo("importoPosizioneAssistitoTicket")).thenReturn("");
        
        List<Esito> result = regola.valida("campoRecord", recordMockito);
        for (Esito e : result) {
            assertTrue(e.isValoreEsito());
        }
    }
}