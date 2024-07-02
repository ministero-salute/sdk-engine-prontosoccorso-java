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
class RegolaDataAntecedenteOUgualeTest {

    @Mock
    RecordDtoGenerico recordMockito;

    @Test
    void validaCostruttore() {
        RegolaDataAntecedenteOUguale regola = new RegolaDataAntecedenteOUguale();
        assertTrue(regola instanceof RegolaDataAntecedenteOUguale);
    }

    @Test
    void validaKO() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Map<String, String> parametri = new HashMap<String, String>();
        parametri.put("nomeCampoValidatore", "assistitoDimissioneData");

        Parametri parametriTest = new Parametri();
        parametriTest.setParametriMap(parametri);

        RegolaDataAntecedenteOUguale regola = new RegolaDataAntecedenteOUguale("RegolaDataAntecedenteOUguale", "1003", "descrizioneErrore",parametriTest);
        Mockito.when(recordMockito.getCampo("dataRecord")).thenReturn("2080-01-25");
        Mockito.when(recordMockito.getCampo("assistitoDimissioneData")).thenReturn("2000-01-25");
        List<Esito> result = regola.valida("dataRecord", recordMockito);
        for (Esito e : result) {
            assertFalse(e.isValoreEsito());
            assertEquals("1003", e.getErroriValidazione().get(0).getCodice());
        }
    }
    @Test
    void validaOK() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Map<String, String> parametri = new HashMap<String, String>();	
        
        parametri.put("nomeCampoValidatore", "assistitoDimissioneData");
        Parametri parametriTest = new Parametri();
        parametriTest.setParametriMap(parametri);

        RegolaDataAntecedenteOUguale regola = new RegolaDataAntecedenteOUguale("RegolaDataAntecedenteOUguale", "1003", "descrizioneErrore",parametriTest);
        Mockito.when(recordMockito.getCampo("dataRecord")).thenReturn(null);
        Mockito.when(recordMockito.getCampo("dataRecord")).thenReturn("2000-01-25");
        List<Esito> result = regola.valida("dataRecord", recordMockito);
        for (Esito e : result) {
            assertTrue(e.isValoreEsito());
        }
    }
    @Test
    void validaOKDataDaComparareisNotAfter() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Map<String, String> parametri = new HashMap<String, String>();
        parametri.put("nomeCampoValidatore", "assistitoDimissioneData");

        Parametri parametriTest = new Parametri();
        parametriTest.setParametriMap(parametri);

        RegolaDataAntecedenteOUguale regola = new RegolaDataAntecedenteOUguale("RegolaDataAntecedenteOUguale", "1003", "descrizioneErrore",parametriTest);
        Mockito.when(recordMockito.getCampo("dataRecord")).thenReturn("2000-01-25");
        Mockito.when(recordMockito.getCampo("assistitoDimissioneData")).thenReturn("2080-01-25");
        List<Esito> result = regola.valida("dataRecord", recordMockito);
        for (Esito e : result) {
            assertTrue(e.isValoreEsito());
        }
    }
//    @Test
//    void testException() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
//        RegolaDataAntecedenteOUguale regola = new RegolaDataAntecedenteOUguale("RegolaDataAntecedenteOUguale", "1003", "descrizioneErrore", null);
//        BDDMockito.willThrow(new IllegalAccessException()).given(recordMockito).getCampo("dataRecord");
//        assertThrows(ValidazioneImpossibileException.class,() -> regola.valida("dataRecord", recordMockito));
//    }
}