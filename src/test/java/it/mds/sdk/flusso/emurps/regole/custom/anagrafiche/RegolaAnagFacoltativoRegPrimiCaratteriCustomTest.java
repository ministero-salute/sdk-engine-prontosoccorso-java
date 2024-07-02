package it.mds.sdk.flusso.emurps.regole.custom.anagrafiche;

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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RegolaAnagFacoltativoRegPrimiCaratteriCustomTest {
    @Mock
    RecordDtoGenerico recordMockito;
    @Mock
    GestoreAnagrafica gestoreAnagrafica = Mockito.mock(GestoreAnagrafica.class);
    TabellaAnagrafica tabellaAnagrafica = Mockito.mock(TabellaAnagrafica.class);
    CampiInputBean campiInputBean = Mockito.mock(CampiInputBean.class);
    Parametri parametriTest;
    private static MockedStatic<Utilities> utilities;

    RegolaAnagFacoltativoRegPrimiCaratteriCustom regola;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
        utilities = mockStatic(Utilities.class);
        Map<String, String> parametri = new HashMap<>();
        parametri.put("valoreDipendente", "1");
        parametri.put("campoDipendente", "importoPosizioneAssistitoTicket");
        parametri.put("nomeTabella", "ANAG_EMUR_REGIONI");
        parametriTest = new Parametri();
        parametriTest.setParametriMap(parametri);

        regola = spy(new RegolaAnagFacoltativoRegPrimiCaratteriCustom("TEST", "TEST", "TEST", parametriTest));
    }

    @AfterEach
    void close(){
        utilities.close();
    }
    @Test
    void costruttoreVuoto() {
        RegolaAnagFacoltativoRegPrimiCaratteriCustom regola = new RegolaAnagFacoltativoRegPrimiCaratteriCustom();
        assertTrue(regola instanceof RegolaAnagFacoltativoRegPrimiCaratteriCustom);
    }

    @Test
    void validaOk() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedRegistryException, RegistryNotFoundException {
        Mockito.when(recordMockito.getCampo("modalitaArrivo")).thenReturn("1");
        Mockito.when(recordMockito.getCampo("responsabileInvio")).thenReturn("5");
        Mockito.when(recordMockito.getCampo("importoPosizioneAssistitoTicket")).thenReturn("010");

        List<String> lista = new ArrayList<>();lista.add("010");
        utilities.when(() -> Utilities.getValoriAnagrafica(any(), eq("ANAG_EMUR_REGIONI"),anyString())).thenReturn(lista);

        List<Esito> result = regola.valida("importoPosizioneAssistitoTicket", recordMockito);
        for (Esito e : result) {
            assertTrue(e.isValoreEsito());
        }
    }

    @Test
    void validaOk2() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedRegistryException, RegistryNotFoundException {
        Mockito.when(recordMockito.getCampo("modalitaArrivo")).thenReturn("1");
        Mockito.when(recordMockito.getCampo("responsabileInvio")).thenReturn("5");
        Mockito.when(recordMockito.getCampo("importoPosizioneAssistitoTicket")).thenReturn("");

        List<String> lista = new ArrayList<>();lista.add("120");
        utilities.when(() -> Utilities.getValoriAnagrafica(any(), eq("ANAG_EMUR_REGIONI"),anyString())).thenReturn(lista);

        List<Esito> result = regola.valida("importoPosizioneAssistitoTicket", recordMockito);
        for (Esito e : result) {
            assertTrue(e.isValoreEsito());
        }
    }
    @Test
    void validaKOException() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        Mockito.when(recordMockito.getCampo(any())).thenThrow(new IllegalAccessException());
        assertThrows(ValidazioneImpossibileException.class,()->regola.valida("codStruttura", recordMockito));
    }
}

