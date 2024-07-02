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
class RegolaAnagTEAMCustomTest {
   
	@Mock
    RecordDtoGenerico recordMockito;
    @Mock
    GestoreAnagrafica gestoreAnagrafica = Mockito.mock(GestoreAnagrafica.class);
    TabellaAnagrafica tabellaAnagrafica = Mockito.mock(TabellaAnagrafica.class);
    CampiInputBean campiInputBean = Mockito.mock(CampiInputBean.class);
    Parametri parametriTest;
    List<String> listaNazioniCeu;
    private static MockedStatic<Utilities> utilities;
    RegolaAnagTEAMCustom regola;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
        utilities = mockStatic(Utilities.class);
        Map<String, String> parametri = new HashMap<>();
       // parametri.put("valoreDipendente", "1");
       // parametri.put("campoDipendente", "assistitoDatiAnagraficiResidenzaStatoEstero");
        parametri.put("nomeTabella1", "ANAG_EMUR_NAZIONI");
        parametri.put("nomeTabella2", "ANAG_EMUR_SOLO_NAZIONI");
        parametriTest = new Parametri();
        parametriTest.setParametriMap(parametri);

        regola = spy(new RegolaAnagTEAMCustom("TEST", "TEST", "TEST", parametriTest));
    }

    @AfterEach
    void close(){
        utilities.close();
    }
    @Test
    void costruttoreVuoto() {
        RegolaAnagTEAMCustom regola = new RegolaAnagTEAMCustom();
        assertTrue(regola instanceof RegolaAnagTEAMCustom);
    }

    @Test
    void validaOk() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedRegistryException, RegistryNotFoundException {
        Mockito.when(recordMockito.getCampo("assistitoDatiAnagraficiResidenzaStatoEstero")).thenReturn("");
        List<String> lista = new ArrayList<>();lista.add("IT");
        List<String> lista2 = new ArrayList<>();lista.add("IT");
        utilities.when(() -> Utilities.getValoriAnagrafica(any(), eq("ANAG_EMUR_NAZIONI"),anyString())).thenReturn(lista);
        utilities.when(() -> Utilities.getValoriAnagrafica(any(), eq("ANAG_EMUR_SOLO_NAZIONI"),anyString())).thenReturn(lista2);

        List<Esito> result = regola.valida("assistitoDatiAnagraficiResidenzaStatoEstero", recordMockito);
        for (Esito e : result) {
            assertTrue(e.isValoreEsito());
        }
    }
    @Test
    void validaKO() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedRegistryException, RegistryNotFoundException {
        Mockito.when(recordMockito.getCampo("assistitoDatiAnagraficiResidenzaStatoEstero")).thenReturn("EU");
        List<String> lista = new ArrayList<>();lista.add("IT");
        List<String> lista2 = new ArrayList<>();lista.add("IT");
        utilities.when(() -> Utilities.getValoriAnagrafica(any(), eq("ANAG_EMUR_NAZIONI"),anyString())).thenReturn(lista);
        utilities.when(() -> Utilities.getValoriAnagrafica(any(), eq("ANAG_EMUR_SOLO_NAZIONI"),anyString())).thenReturn(lista2);

        doReturn(tabellaAnagrafica).when(gestoreAnagrafica).richiediAnagrafica(any(), any(), anyBoolean());

        List<Esito> result = regola.valida("assistitoDatiAnagraficiResidenzaStatoEstero", recordMockito);
        for (Esito e : result) {
            assertFalse(e.isValoreEsito());
        }
    }
    @Test
    void validaOk3() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedRegistryException, RegistryNotFoundException {
        Mockito.when(recordMockito.getCampo("assistitoDatiAnagraficiResidenzaStatoEstero")).thenReturn("EU");
        List<String> lista = new ArrayList<>();lista.add("IT");
        List<String> lista2 = new ArrayList<>();lista.add("IT");
        utilities.when(() -> Utilities.getValoriAnagrafica(any(), eq("ANAG_EMUR_NAZIONI"),anyString())).thenReturn(lista);
        utilities.when(() -> Utilities.getValoriAnagrafica(any(), eq("ANAG_EMUR_SOLO_NAZIONI"),anyString())).thenReturn(lista2);

        List<Esito> result = regola.valida("campo", recordMockito);
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