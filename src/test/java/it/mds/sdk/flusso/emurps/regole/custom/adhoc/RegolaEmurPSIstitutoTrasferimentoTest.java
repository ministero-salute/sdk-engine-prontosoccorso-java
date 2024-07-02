/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.flusso.emurps.regole.custom.adhoc;

import it.mds.sdk.anagrafiche.client.exceptions.MalformedRegistryException;
import it.mds.sdk.anagrafiche.client.exceptions.RegistryNotFoundException;
import it.mds.sdk.connettore.anagrafiche.gestore.anagrafica.GestoreAnagrafica;
import it.mds.sdk.connettore.anagrafiche.tabella.TabellaAnagrafica;
import it.mds.sdk.flusso.emurps.parser.regole.Utilities;
import it.mds.sdk.gestoreesiti.modelli.Esito;
import it.mds.sdk.libreriaregole.dtos.CampiInputBean;
import it.mds.sdk.libreriaregole.dtos.RecordDtoGenerico;
import it.mds.sdk.libreriaregole.exception.ValidazioneImpossibileException;
import it.mds.sdk.libreriaregole.regole.beans.Parametri;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.spy;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RegolaEmurPSIstitutoTrasferimentoTest {
    @Mock
    RecordDtoGenerico recordMockito;
    @Mock
    GestoreAnagrafica gestoreAnagrafica = Mockito.mock(GestoreAnagrafica.class);
    Parametri parametriTest;
    List<String> listaNazioniCeu;

    private static MockedStatic<Utilities> utilities;
    RegolaEmurPSIstitutoTrasferimento regola;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
        utilities = mockStatic(Utilities.class);
        Map<String, String> parametri = new HashMap<>();
        parametriTest = new Parametri();
        parametriTest.setParametriMap(parametri);

        regola = spy(new RegolaEmurPSIstitutoTrasferimento("TEST", "TEST", "TEST", parametriTest));
    }

    @AfterEach
    void close(){
        utilities.close();
    }
    @Test
    void costruttoreVuoto() {
        RegolaEmurPSIstitutoTrasferimento regola = new RegolaEmurPSIstitutoTrasferimento();
        assertTrue(regola instanceof RegolaEmurPSIstitutoTrasferimento);
    }

    @Test
    void validaKO() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedRegistryException, RegistryNotFoundException {

        Mockito.when(recordMockito.getCampo("assistitoDimissioneEsitoTrattamento")).thenReturn("3");
        Mockito.when(recordMockito.getCampo("assistitoDimissioneTrasferimentoIstituto")).thenReturn("120201");
        Mockito.when(recordMockito.getCampo("assistitoOBIEsito")).thenReturn("3");

        List<String> lista = new ArrayList<>();lista.add("120001");
        utilities.when(() -> Utilities.getValoriAnagrafica(any(), eq("ANAG_EMUR_HSP11_E_BIS"),anyString())).thenReturn(lista);

        List<Esito> result = regola.valida("dataEntrata", recordMockito);
        for (Esito e : result) {
            assertFalse(e.isValoreEsito());
        }
    }

    @Test
    void validaKOException() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        Mockito.when(recordMockito.getCampo(any())).thenThrow(new IllegalAccessException());
        assertThrows(ValidazioneImpossibileException.class,()->regola.valida("codStruttura", recordMockito));
    }
}