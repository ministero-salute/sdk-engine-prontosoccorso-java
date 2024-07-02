/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.flusso.emurps.regole.custom.adhoc;

import it.mds.sdk.anagrafiche.client.exceptions.MalformedRegistryException;
import it.mds.sdk.anagrafiche.client.exceptions.RegistryNotFoundException;
import it.mds.sdk.connettore.anagrafiche.gestore.anagrafica.GestoreAnagrafica;
import it.mds.sdk.connettore.anagrafiche.tabella.TabellaAnagrafica;
import it.mds.sdk.flusso.emurps.parser.regole.Utilities;
import it.mds.sdk.flusso.emurps.tracciato.bean.output.DiagnosiSecondaria;
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
class RegolaEmurPSPrestSecOBITest {
    @Mock
    RecordDtoGenerico recordMockito;
    @Mock
    GestoreAnagrafica gestoreAnagrafica = Mockito.mock(GestoreAnagrafica.class);
    TabellaAnagrafica tabellaAnagrafica = Mockito.mock(TabellaAnagrafica.class);
    CampiInputBean campiInputBean = Mockito.mock(CampiInputBean.class);
    Parametri parametriTest;
    List<String> listaNazioniCeu;

    private static MockedStatic<Utilities> utilities;
    RegolaEmurPSPrestSecOBI regola;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
        utilities = mockStatic(Utilities.class);
        Map<String, String> parametri = new HashMap<>();
        parametri.put("parametroCampoCondizionante", "10");
        parametri.put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");
        parametriTest = new Parametri();
        parametriTest.setParametriMap(parametri);

        regola = spy(new RegolaEmurPSPrestSecOBI("TEST", "TEST", "TEST", parametriTest));
    }

    @AfterEach
    void close(){
        utilities.close();
    }
    @Test
    void costruttoreVuoto() {
        RegolaEmurPSPrestSecOBI regola = new RegolaEmurPSPrestSecOBI();
        assertTrue(regola instanceof RegolaEmurPSPrestSecOBI);
    }

    @Test
    void validaKO() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedRegistryException, RegistryNotFoundException {

        Mockito.when(recordMockito.getCampo("campoTracciato")).thenReturn("10");
        Mockito.when(recordMockito.getCampo("assistitoDatiAnagraficiResidenzaRegione")).thenReturn("120");

        List<String> lista = new ArrayList<>();lista.add("99999");
        List<String> lista2 = new ArrayList<>();lista2.add("999999");
        List<String> lista3 = new ArrayList<>();lista3.add("999999");
        utilities.when(() -> Utilities.getValoriAnagrafica(any(), eq("ANAG_EMUR_INTERVENTI"),anyString())).thenReturn(lista);
        utilities.when(() -> Utilities.getValoriAnagrafica(any(), eq("ANAG_EMUR_PRESTAZIONI_NAZIONALE"),anyString())).thenReturn(lista2);
        utilities.when(() -> Utilities.getValoriAnagrafica(any(), eq("ANAG_EMUR_PRESTAZIONI_REGIONALI"),anyString())).thenReturn(lista3);

        List<Esito> result = regola.valida("campoTracciato", recordMockito);
        for (Esito e : result) {
            assertFalse(e.isValoreEsito());
        }
    }

    @Test
    void validaKO2() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedRegistryException, RegistryNotFoundException {
        DiagnosiSecondaria diagnosiSecondaria = new DiagnosiSecondaria();
        diagnosiSecondaria.setValue("val");
        ArrayList<DiagnosiSecondaria> lstCampo = new ArrayList<>();
        lstCampo.add(diagnosiSecondaria);

        Mockito.when(recordMockito.getCampo("campoTracciato")).thenReturn(lstCampo);
        Mockito.when(recordMockito.getCampo("assistitoDatiAnagraficiResidenzaRegione")).thenReturn("120");

        List<String> lista = new ArrayList<>();lista.add("99999");
        List<String> lista2 = new ArrayList<>();lista2.add("999999");
        List<String> lista3 = new ArrayList<>();lista3.add("999999");
        utilities.when(() -> Utilities.getValoriAnagrafica(any(), eq("ANAG_EMUR_INTERVENTI"),anyString())).thenReturn(lista);
        utilities.when(() -> Utilities.getValoriAnagrafica(any(), eq("ANAG_EMUR_PRESTAZIONI_NAZIONALE"),anyString())).thenReturn(lista2);
        utilities.when(() -> Utilities.getValoriAnagrafica(any(), eq("ANAG_EMUR_PRESTAZIONI_REGIONALI"),anyString())).thenReturn(lista3);

        List<Esito> result = regola.valida("campoTracciato", recordMockito);
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