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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RegolaEmurPSOBIOraUscitaTest {
    @Mock
    RecordDtoGenerico recordMockito;
    @Mock
    GestoreAnagrafica gestoreAnagrafica = Mockito.mock(GestoreAnagrafica.class);
    TabellaAnagrafica tabellaAnagrafica = Mockito.mock(TabellaAnagrafica.class);
    CampiInputBean campiInputBean = Mockito.mock(CampiInputBean.class);
    Parametri parametriTest;
    List<String> listaNazioniCeu;

    RegolaEmurPSOBIOraUscita regola;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
        Map<String, String> parametri = new HashMap<>();
        parametri.put("parametroCampoCondizionante", "10");
        parametri.put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");

        parametri.put("campoPrimaData","campoPrimaData");
        parametri.put("campoPrimaOra","campoPrimaOra");
        parametri.put("operatoreLogico","operatoreLogico");
        parametri.put("campoSecondaData","campoSecondaData");
        parametri.put("campoSecondaOra","campoSecondaOra");

        parametriTest = new Parametri();
        parametriTest.setParametriMap(parametri);

        regola = spy(new RegolaEmurPSOBIOraUscita("TEST", "TEST", "TEST", parametriTest));
    }

    @Test
    void costruttoreVuoto() {
        RegolaEmurPSOBIOraUscita regola = new RegolaEmurPSOBIOraUscita();
        assertTrue(regola instanceof RegolaEmurPSOBIOraUscita);
    }

    @Test
    void validaKO() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedRegistryException, RegistryNotFoundException {

        Mockito.when(recordMockito.getCampo("assistitoDimissioneEsitoTrattamento")).thenReturn("10");
        Mockito.when(recordMockito.getCampo("OBIOraUscita")).thenReturn("10");

        campiInputBean.setAnnoRiferimentoInput("2021");
        campiInputBean.setCodiceRegioneInput("030");
        campiInputBean.setPeriodoRiferimentoInput("Z01");
        Mockito.when(recordMockito.getCampiInput()).thenReturn(campiInputBean);
        Mockito.when(recordMockito.getCampiInput().getAnnoRiferimentoInput()).thenReturn("2021");
        Mockito.when(recordMockito.getCampiInput().getPeriodoRiferimentoInput() ).thenReturn("Z01");

        List<Esito> result = regola.valida("OBIOraUscita", recordMockito);
        for (Esito e : result) {
            assertFalse(e.isValoreEsito());
        }
    }

    @Test
    void validaKO2() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedRegistryException, RegistryNotFoundException {

        Mockito.when(recordMockito.getCampo("assistitoDimissioneEsitoTrattamento")).thenReturn("10");
        Mockito.when(recordMockito.getCampo("OBIOraUscita")).thenReturn("1");

        Mockito.when(recordMockito.getCampo("assistitoOBIDataUscita")).thenReturn("2022-01-05");
        Mockito.when(recordMockito.getCampo("assistitoOBIOraUscita")).thenReturn("12:00");
        Mockito.when(recordMockito.getCampo("operatoreLogico")).thenReturn("minore");
        Mockito.when(recordMockito.getCampo("assistitoOBIDataIngresso")).thenReturn("2022-01-02");
        Mockito.when(recordMockito.getCampo("assistitoOBIOraIngresso")).thenReturn("13:00");

        campiInputBean.setAnnoRiferimentoInput("2021");
        campiInputBean.setCodiceRegioneInput("030");
        campiInputBean.setPeriodoRiferimentoInput("Z01");
        Mockito.when(recordMockito.getCampiInput()).thenReturn(campiInputBean);
        Mockito.when(recordMockito.getCampiInput().getAnnoRiferimentoInput()).thenReturn("2021");
        Mockito.when(recordMockito.getCampiInput().getPeriodoRiferimentoInput() ).thenReturn("Z01");


        List<Esito> result = regola.valida("OBIOraUscita", recordMockito);
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