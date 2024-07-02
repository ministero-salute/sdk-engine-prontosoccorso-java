/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.flusso.emurps.regole.custom.adhoc;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
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

import it.mds.sdk.anagrafiche.client.exceptions.MalformedRegistryException;
import it.mds.sdk.anagrafiche.client.exceptions.RegistryNotFoundException;
import it.mds.sdk.connettore.anagrafiche.gestore.anagrafica.GestoreAnagrafica;
import it.mds.sdk.connettore.anagrafiche.tabella.RecordAnagrafica;
import it.mds.sdk.connettore.anagrafiche.tabella.TabellaAnagrafica;
import it.mds.sdk.flusso.emurps.parser.regole.Utilities;
import it.mds.sdk.gestoreesiti.modelli.Esito;
import it.mds.sdk.libreriaregole.dtos.CampiInputBean;
import it.mds.sdk.libreriaregole.dtos.RecordDtoGenerico;
import it.mds.sdk.libreriaregole.exception.ValidazioneImpossibileException;
import it.mds.sdk.libreriaregole.regole.beans.Parametri;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RegolaEmurPSASLResidenzaTest {
    @Mock
    RecordDtoGenerico recordMockito;
    @Mock
    GestoreAnagrafica gestoreAnagrafica = Mockito.mock(GestoreAnagrafica.class);
    TabellaAnagrafica tabellaAnagrafica = Mockito.mock(TabellaAnagrafica.class);
    CampiInputBean campiInputBean = Mockito.mock(CampiInputBean.class);
    Parametri parametriTest;
    private static MockedStatic<Utilities> utilities;

    RegolaEmurPSASLResidenza regola;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
        utilities = mockStatic(Utilities.class);
        Map<String, String> parametri = new HashMap<>();
        parametri.put("valoreDipendente", "1");
        parametri.put("campoDipendente", "importoPosizioneAssistitoTicket");
        parametri.put("nomeTabella", "ANAG_EMUR_ESENZIONI");
        parametriTest = new Parametri();
        parametriTest.setParametriMap(parametri);

        regola = spy(new RegolaEmurPSASLResidenza("TEST", "TEST", "TEST", parametriTest));
    }


    @Test
    void costruttoreVuoto() {
        RegolaEmurPSASLResidenza regola = new RegolaEmurPSASLResidenza();
        utilities.close();
        assertTrue(regola instanceof RegolaEmurPSASLResidenza);

    }

    @Test
    void validaOK() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedRegistryException, RegistryNotFoundException {
        Mockito.when(recordMockito.getCampo("assistitoDatiAnagraficiResidenzaASL")).thenReturn("201");
        Mockito.when(recordMockito.getCampo("assistitoDatiAnagraficiResidenzaComune")).thenReturn("001001");
        Mockito.when(recordMockito.getCampo("assistitoDatiAnagraficiResidenzaRegione")).thenReturn("120");
        Mockito.when(recordMockito.getCampo("assistitoDatiAnagraficiResidenzaStatoEstero")).thenReturn("");

        List<String> lista = new ArrayList<>();lista.add("IT");
        List<String> lista2 = new ArrayList<>();lista2.add("120");
        List<String> lista3 = new ArrayList<>();lista3.add("001001#120");
        List<String> lista4 = new ArrayList<>();lista4.add("120#201");

        utilities.when(() -> Utilities.getValoriAnagrafica(any(), eq("ANAG_EMUR_SOLO_NAZIONI"),anyString())).thenReturn(lista);
        utilities.when(() -> Utilities.getValoriAnagrafica(any(), eq("ANAG_EMUR_REGIONI"),anyString())).thenReturn(lista2);
        utilities.when(() -> Utilities.getValoriAnagrafica(any(), eq("ANAG_EMUR_COMUNI_MRA"),anyString())).thenReturn(lista3);
        utilities.when(() -> Utilities.getValoriAnagrafica(any(), eq("ANAG_EMUR_AZIENDA_MRA"),anyString())).thenReturn(lista4);

        List<Esito> result = regola.valida("importoPosizioneAssistitoTicket", recordMockito);
        utilities.close();
        for (Esito e : result) {
            assertTrue(e.isValoreEsito());
        }

    }

    @Test
    void validaKO3() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedRegistryException, RegistryNotFoundException {
        Mockito.when(recordMockito.getCampo("assistitoDatiAnagraficiResidenzaASL")).thenReturn("");
        Mockito.when(recordMockito.getCampo("assistitoDatiAnagraficiResidenzaComune")).thenReturn("001001");
        Mockito.when(recordMockito.getCampo("assistitoDatiAnagraficiResidenzaRegione")).thenReturn("120");
        Mockito.when(recordMockito.getCampo("assistitoDatiAnagraficiResidenzaStatoEstero")).thenReturn("");

        List<String> lista = new ArrayList<>();lista.add("IT");
        List<String> lista2 = new ArrayList<>();lista2.add("120");
        List<String> lista3 = new ArrayList<>();lista3.add("001001#120");
        List<String> lista4 = new ArrayList<>();lista4.add("120#201");

        utilities.when(() -> Utilities.getValoriAnagrafica(any(), eq("ANAG_EMUR_SOLO_NAZIONI"),anyString())).thenReturn(lista);
        utilities.when(() -> Utilities.getValoriAnagrafica(any(), eq("ANAG_EMUR_REGIONI"),anyString())).thenReturn(lista2);
        utilities.when(() -> Utilities.getValoriAnagrafica(any(), eq("ANAG_EMUR_COMUNI_MRA"),anyString())).thenReturn(lista3);
        utilities.when(() -> Utilities.getValoriAnagrafica(any(), eq("ANAG_EMUR_AZIENDA_MRA"),anyString())).thenReturn(lista4);

        List<Esito> result = regola.valida("importoPosizioneAssistitoTicket", recordMockito);
        utilities.close();
        for (Esito e : result) {
            assertFalse(e.isValoreEsito());
        }

    }
    @Test
    void validaOK2() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedRegistryException, RegistryNotFoundException {
        Mockito.when(recordMockito.getCampo("assistitoDatiAnagraficiResidenzaASL")).thenReturn("999");
        Mockito.when(recordMockito.getCampo("assistitoDatiAnagraficiResidenzaComune")).thenReturn("999999");
        Mockito.when(recordMockito.getCampo("assistitoDatiAnagraficiResidenzaRegione")).thenReturn("999");
        Mockito.when(recordMockito.getCampo("assistitoDatiAnagraficiResidenzaStatoEstero")).thenReturn("EN");

        List<String> lista = new ArrayList<>();lista.add("EN");
        List<String> lista2 = new ArrayList<>();lista2.add("120");
        List<String> lista3 = new ArrayList<>();lista3.add("001001#120");
        List<String> lista4 = new ArrayList<>();lista4.add("120#201");

        utilities.when(() -> Utilities.getValoriAnagrafica(any(), eq("ANAG_EMUR_SOLO_NAZIONI"),anyString())).thenReturn(lista);
        utilities.when(() -> Utilities.getValoriAnagrafica(any(), eq("ANAG_EMUR_REGIONI"),anyString())).thenReturn(lista2);
        utilities.when(() -> Utilities.getValoriAnagrafica(any(), eq("ANAG_EMUR_COMUNI_MRA"),anyString())).thenReturn(lista3);
        utilities.when(() -> Utilities.getValoriAnagrafica(any(), eq("ANAG_EMUR_AZIENDA_MRA"),anyString())).thenReturn(lista4);

        List<Esito> result = regola.valida("importoPosizioneAssistitoTicket", recordMockito);
        utilities.close();
        for (Esito e : result) {
            assertTrue(e.isValoreEsito());
        }

    }

    @Test
    void validaKO() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedRegistryException, RegistryNotFoundException {
        Mockito.when(recordMockito.getCampo("assistitoDatiAnagraficiResidenzaASL")).thenReturn("201");
        Mockito.when(recordMockito.getCampo("assistitoDatiAnagraficiResidenzaComune")).thenReturn("001");
        Mockito.when(recordMockito.getCampo("assistitoDatiAnagraficiResidenzaRegione")).thenReturn("120");
        Mockito.when(recordMockito.getCampo("assistitoDatiAnagraficiResidenzaStatoEstero")).thenReturn("IT");


        Mockito.when(recordMockito.getCampo("valoreCondizionante")).thenReturn("vc");

        List<String> lista = new ArrayList<>();lista.add("IT");
        List<String> lista2 = new ArrayList<>();lista2.add("120");
        List<String> lista3 = new ArrayList<>();lista3.add("001006");
        List<String> lista4 = new ArrayList<>();lista4.add("201");

        utilities.when(() -> Utilities.getValoriAnagrafica(any(), eq("ANAG_EMUR_SOLO_NAZIONI"),anyString())).thenReturn(lista);
        utilities.when(() -> Utilities.getValoriAnagrafica(any(), eq("ANAG_EMUR_REGIONI"),anyString())).thenReturn(lista2);
        utilities.when(() -> Utilities.getValoriAnagrafica(any(), eq("ANAG_EMUR_COMUNI_MRA"),anyString())).thenReturn(lista3);
        utilities.when(() -> Utilities.getValoriAnagrafica(any(), eq("ANAG_EMUR_AZIENDA_MRA"),anyString())).thenReturn(lista4);
        
        List<Esito> result = regola.valida("importoPosizioneAssistitoTicket", recordMockito);
        utilities.close();
        for (Esito e : result) {
            assertFalse(e.isValoreEsito());
        }

    }

    @Test
    void validaKO2() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedRegistryException, RegistryNotFoundException {
        Mockito.when(recordMockito.getCampo("assistitoDatiAnagraficiResidenzaASL")).thenReturn("201");
        Mockito.when(recordMockito.getCampo("assistitoDatiAnagraficiResidenzaComune")).thenReturn("001");
        Mockito.when(recordMockito.getCampo("assistitoDatiAnagraficiResidenzaRegione")).thenReturn("120");
        Mockito.when(recordMockito.getCampo("assistitoDatiAnagraficiResidenzaStatoEstero")).thenReturn("");

        List<String> lista = new ArrayList<>();lista.add("IT");
        List<String> lista2 = new ArrayList<>();lista2.add("120");
        List<String> lista3 = new ArrayList<>();lista3.add("001006");
        List<String> lista4 = new ArrayList<>();lista4.add("201");

        utilities.when(() -> Utilities.getValoriAnagrafica(any(), eq("ANAG_EMUR_SOLO_NAZIONI"),anyString())).thenReturn(lista);
        utilities.when(() -> Utilities.getValoriAnagrafica(any(), eq("ANAG_EMUR_REGIONI"),anyString())).thenReturn(lista2);
        utilities.when(() -> Utilities.getValoriAnagrafica(any(), eq("ANAG_EMUR_COMUNI_MRA"),anyString())).thenReturn(lista3);
        utilities.when(() -> Utilities.getValoriAnagrafica(any(), eq("ANAG_EMUR_AZIENDA_MRA"),anyString())).thenReturn(lista4);

        List<Esito> result = regola.valida("importoPosizioneAssistitoTicket", recordMockito);
        utilities.close();
        for (Esito e : result) {
            assertFalse(e.isValoreEsito());
        }

    }

    @Test
    void validaKOException() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        Mockito.when(recordMockito.getCampo(any())).thenThrow(new IllegalAccessException());
        utilities.close();
        assertThrows(ValidazioneImpossibileException.class,()->regola.valida("codStruttura", recordMockito));
    }
}