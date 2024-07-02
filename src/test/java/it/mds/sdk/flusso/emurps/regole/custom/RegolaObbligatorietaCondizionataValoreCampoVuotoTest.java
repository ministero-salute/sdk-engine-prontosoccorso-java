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
class RegolaObbligatorietaCondizionataValoreCampoVuotoTest {

	@Mock
	RecordDtoGenerico recordMockito;
	
	@Test
	void validaCostruttore() {
		RegolaObbligatorietaCondizionataValoreCampoVuoto regola = new RegolaObbligatorietaCondizionataValoreCampoVuoto();
		assertTrue(regola instanceof RegolaObbligatorietaCondizionataValoreCampoVuoto);
	}
	
	@Test
    void validaKO() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Map<String, String> parametri = new HashMap<String, String>();
        
        parametri.put("nomeCampoCondizionante", "assistitoDatiAnagraficiEtaPresunta");
        
        Parametri parametriTest = new Parametri();
        parametriTest.setParametriMap(parametri);

        RegolaObbligatorietaCondizionataValoreCampoVuoto regola = new RegolaObbligatorietaCondizionataValoreCampoVuoto("RegolaObbligatorietaCondizionataValoreCampoVuoto", "9999", "descrizioneErrore",parametriTest);
        Mockito.when(recordMockito.getCampo("campoRecord")).thenReturn(null);
        Mockito.when(recordMockito.getCampo("assistitoDatiAnagraficiEtaPresunta")).thenReturn(null);
        
        List<Esito> result = regola.valida("campoRecord", recordMockito);
        for (Esito e : result) {
            assertFalse(e.isValoreEsito());
            assertEquals("9999", e.getErroriValidazione().get(0).getCodice());
        }
    }
	
	@Test
    void validaOK() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Map<String, String> parametri = new HashMap<String, String>();
        
        parametri.put("nomeCampoCondizionante", "assistitoDatiAnagraficiEtaPresunta");
        
        Parametri parametriTest = new Parametri();
        parametriTest.setParametriMap(parametri);

        RegolaObbligatorietaCondizionataValoreCampoVuoto regola = new RegolaObbligatorietaCondizionataValoreCampoVuoto("RegolaObbligatorietaCondizionataValoreCampoVuoto", "9999", "descrizioneErrore",parametriTest);
      //  Mockito.when(recordMockito.getCampo("campoRecord")).thenReturn("55");
        Mockito.when(recordMockito.getCampo("assistitoDatiAnagraficiEtaPresunta")).thenReturn("55");
        
        List<Esito> result = regola.valida("campoRecord", recordMockito);
        for (Esito e : result) {
            assertTrue(e.isValoreEsito());
        }
    }
	
}