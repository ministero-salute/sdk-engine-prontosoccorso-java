/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.flusso.emurps.regole.custom.adhoc;

import it.mds.sdk.anagrafiche.client.exceptions.MalformedRegistryException;
import it.mds.sdk.anagrafiche.client.exceptions.RegistryNotFoundException;
import it.mds.sdk.connettore.anagrafiche.gestore.anagrafica.GestoreAnagrafica;
import it.mds.sdk.connettore.anagrafiche.tabella.TabellaAnagrafica;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RegolaEmurPSDimissioneOraDestTest {
    @Mock
    RecordDtoGenerico recordMockito;
    @Mock
    GestoreAnagrafica gestoreAnagrafica = Mockito.mock(GestoreAnagrafica.class);
    TabellaAnagrafica tabellaAnagrafica = Mockito.mock(TabellaAnagrafica.class);
    CampiInputBean campiInputBean = Mockito.mock(CampiInputBean.class);
    Parametri parametriTest;
    List<String> listaNazioniCeu;

    RegolaEmurPSDimissioneOraDest regola;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
        Map<String, String> parametri = new HashMap<>();

        parametriTest = new Parametri();
        parametriTest.setParametriMap(parametri);

        regola = spy(new RegolaEmurPSDimissioneOraDest("TEST", "TEST", "TEST", parametriTest));
    }

    @Test
    void validaOK() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedRegistryException, RegistryNotFoundException {

        Mockito.when(recordMockito.getCampo("assistitoDimissioneEsitoTrattamento")).thenReturn("2");
        Mockito.when(recordMockito.getCampo("assistitoDimissioneOraDest")).thenReturn("12:00");

        campiInputBean.setAnnoRiferimentoInput("2021");
        campiInputBean.setCodiceRegioneInput("030");
        campiInputBean.setPeriodoRiferimentoInput("Z01");
        Mockito.when(recordMockito.getCampiInput()).thenReturn(campiInputBean);
        Mockito.when(recordMockito.getCampiInput().getAnnoRiferimentoInput()).thenReturn("2021");
        Mockito.when(recordMockito.getCampiInput().getPeriodoRiferimentoInput() ).thenReturn("Z01");

        List<Esito> result = regola.valida("dataEntrata", recordMockito);
        for (Esito e : result) {
            assertTrue(e.isValoreEsito());
        }
    }

    @Test
    void validaOK2() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedRegistryException, RegistryNotFoundException {

        Mockito.when(recordMockito.getCampo("assistitoDimissioneEsitoTrattamento")).thenReturn("3");
        Mockito.when(recordMockito.getCampo("assistitoDimissioneOraDest")).thenReturn("12:00");

        campiInputBean.setAnnoRiferimentoInput("2021");
        campiInputBean.setCodiceRegioneInput("030");
        campiInputBean.setPeriodoRiferimentoInput("Z01");
        Mockito.when(recordMockito.getCampiInput()).thenReturn(campiInputBean);
        Mockito.when(recordMockito.getCampiInput().getAnnoRiferimentoInput()).thenReturn("2021");
        Mockito.when(recordMockito.getCampiInput().getPeriodoRiferimentoInput() ).thenReturn("Z01");

        List<Esito> result = regola.valida("dataEntrata", recordMockito);
        for (Esito e : result) {
            assertTrue(e.isValoreEsito());
        }
    }

    @Test
    void validaOK3() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedRegistryException, RegistryNotFoundException {

        Mockito.when(recordMockito.getCampo("assistitoDimissioneEsitoTrattamento")).thenReturn("10");
        Mockito.when(recordMockito.getCampo("assistitoDimissioneOraDest")).thenReturn("12:00");

        campiInputBean.setAnnoRiferimentoInput("2021");
        campiInputBean.setCodiceRegioneInput("030");
        campiInputBean.setPeriodoRiferimentoInput("Z01");
        Mockito.when(recordMockito.getCampiInput()).thenReturn(campiInputBean);
        Mockito.when(recordMockito.getCampiInput().getAnnoRiferimentoInput()).thenReturn("2021");
        Mockito.when(recordMockito.getCampiInput().getPeriodoRiferimentoInput() ).thenReturn("Z01");

        List<Esito> result = regola.valida("dataEntrata", recordMockito);
        for (Esito e : result) {
            assertTrue(e.isValoreEsito());
        }
    }

    @Test
    void validaOK4() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedRegistryException, RegistryNotFoundException {

        Mockito.when(recordMockito.getCampo("assistitoDimissioneEsitoTrattamento")).thenReturn("11");
        Mockito.when(recordMockito.getCampo("assistitoDimissioneOraDest")).thenReturn("12:00");

        campiInputBean.setAnnoRiferimentoInput("2021");
        campiInputBean.setCodiceRegioneInput("030");
        campiInputBean.setPeriodoRiferimentoInput("Z01");
        Mockito.when(recordMockito.getCampiInput()).thenReturn(campiInputBean);
        Mockito.when(recordMockito.getCampiInput().getAnnoRiferimentoInput()).thenReturn("2021");
        Mockito.when(recordMockito.getCampiInput().getPeriodoRiferimentoInput() ).thenReturn("Z01");

        List<Esito> result = regola.valida("dataEntrata", recordMockito);
        for (Esito e : result) {
            assertTrue(e.isValoreEsito());
        }
    }

    @Test
    void validaKO2() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedRegistryException, RegistryNotFoundException {

        Mockito.when(recordMockito.getCampo("assistitoDimissioneEsitoTrattamento")).thenReturn("4");
        Mockito.when(recordMockito.getCampo("assistitoDimissioneOraDest")).thenReturn("12:00");

        campiInputBean.setAnnoRiferimentoInput("2021");
        campiInputBean.setCodiceRegioneInput("030");
        campiInputBean.setPeriodoRiferimentoInput("Z01");
        Mockito.when(recordMockito.getCampiInput()).thenReturn(campiInputBean);
        Mockito.when(recordMockito.getCampiInput().getAnnoRiferimentoInput()).thenReturn("2021");
        Mockito.when(recordMockito.getCampiInput().getPeriodoRiferimentoInput() ).thenReturn("Z01");

        List<Esito> result = regola.valida("dataEntrata", recordMockito);
        for (Esito e : result) {
            assertFalse(e.isValoreEsito());
        }
    }

    @Test
    void validaKO3() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedRegistryException, RegistryNotFoundException {

        Mockito.when(recordMockito.getCampo("assistitoDimissioneEsitoTrattamento")).thenReturn("5");
        Mockito.when(recordMockito.getCampo("assistitoDimissioneOraDest")).thenReturn("12:00");

        campiInputBean.setAnnoRiferimentoInput("2021");
        campiInputBean.setCodiceRegioneInput("030");
        campiInputBean.setPeriodoRiferimentoInput("Z01");
        Mockito.when(recordMockito.getCampiInput()).thenReturn(campiInputBean);
        Mockito.when(recordMockito.getCampiInput().getAnnoRiferimentoInput()).thenReturn("2021");
        Mockito.when(recordMockito.getCampiInput().getPeriodoRiferimentoInput() ).thenReturn("Z01");

        List<Esito> result = regola.valida("dataEntrata", recordMockito);
        for (Esito e : result) {
            assertFalse(e.isValoreEsito());
        }
    }

    @Test
    void validaKO4() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedRegistryException, RegistryNotFoundException {

        Mockito.when(recordMockito.getCampo("assistitoDimissioneEsitoTrattamento")).thenReturn("6");
        Mockito.when(recordMockito.getCampo("assistitoDimissioneOraDest")).thenReturn("12:00");

        campiInputBean.setAnnoRiferimentoInput("2021");
        campiInputBean.setCodiceRegioneInput("030");
        campiInputBean.setPeriodoRiferimentoInput("Z01");
        Mockito.when(recordMockito.getCampiInput()).thenReturn(campiInputBean);
        Mockito.when(recordMockito.getCampiInput().getAnnoRiferimentoInput()).thenReturn("2021");
        Mockito.when(recordMockito.getCampiInput().getPeriodoRiferimentoInput() ).thenReturn("Z01");

        List<Esito> result = regola.valida("dataEntrata", recordMockito);
        for (Esito e : result) {
            assertFalse(e.isValoreEsito());
        }
    }

    @Test
    void validaKO5() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedRegistryException, RegistryNotFoundException {

        Mockito.when(recordMockito.getCampo("assistitoDimissioneEsitoTrattamento")).thenReturn("7");
        Mockito.when(recordMockito.getCampo("assistitoDimissioneOraDest")).thenReturn("12:00");

        campiInputBean.setAnnoRiferimentoInput("2021");
        campiInputBean.setCodiceRegioneInput("030");
        campiInputBean.setPeriodoRiferimentoInput("Z01");
        Mockito.when(recordMockito.getCampiInput()).thenReturn(campiInputBean);
        Mockito.when(recordMockito.getCampiInput().getAnnoRiferimentoInput()).thenReturn("2021");
        Mockito.when(recordMockito.getCampiInput().getPeriodoRiferimentoInput() ).thenReturn("Z01");

        List<Esito> result = regola.valida("dataEntrata", recordMockito);
        for (Esito e : result) {
            assertFalse(e.isValoreEsito());
        }
    }

    @Test
    void validaKO6() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedRegistryException, RegistryNotFoundException {

        Mockito.when(recordMockito.getCampo("assistitoDimissioneEsitoTrattamento")).thenReturn("8");
        Mockito.when(recordMockito.getCampo("assistitoDimissioneOraDest")).thenReturn("12:00");

        campiInputBean.setAnnoRiferimentoInput("2021");
        campiInputBean.setCodiceRegioneInput("030");
        campiInputBean.setPeriodoRiferimentoInput("Z01");
        Mockito.when(recordMockito.getCampiInput()).thenReturn(campiInputBean);
        Mockito.when(recordMockito.getCampiInput().getAnnoRiferimentoInput()).thenReturn("2021");
        Mockito.when(recordMockito.getCampiInput().getPeriodoRiferimentoInput() ).thenReturn("Z01");

        List<Esito> result = regola.valida("dataEntrata", recordMockito);
        for (Esito e : result) {
            assertFalse(e.isValoreEsito());
        }
    }

    @Test
    void validaKO7() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedRegistryException, RegistryNotFoundException {

        Mockito.when(recordMockito.getCampo("assistitoDimissioneEsitoTrattamento")).thenReturn("9");
        Mockito.when(recordMockito.getCampo("assistitoDimissioneOraDest")).thenReturn("12:00");

        campiInputBean.setAnnoRiferimentoInput("2021");
        campiInputBean.setCodiceRegioneInput("030");
        campiInputBean.setPeriodoRiferimentoInput("Z01");
        Mockito.when(recordMockito.getCampiInput()).thenReturn(campiInputBean);
        Mockito.when(recordMockito.getCampiInput().getAnnoRiferimentoInput()).thenReturn("2021");
        Mockito.when(recordMockito.getCampiInput().getPeriodoRiferimentoInput() ).thenReturn("Z01");

        List<Esito> result = regola.valida("dataEntrata", recordMockito);
        for (Esito e : result) {
            assertFalse(e.isValoreEsito());
        }
    }

    @Test
    void validaKO() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedRegistryException, RegistryNotFoundException {

        Mockito.when(recordMockito.getCampo("assistitoDimissioneEsitoTrattamento")).thenReturn("11");
        Mockito.when(recordMockito.getCampo("assistitoDimissioneOraDest")).thenReturn("");

        campiInputBean.setAnnoRiferimentoInput("2021");
        campiInputBean.setCodiceRegioneInput("030");
        campiInputBean.setPeriodoRiferimentoInput("Z01");
        Mockito.when(recordMockito.getCampiInput()).thenReturn(campiInputBean);
        Mockito.when(recordMockito.getCampiInput().getAnnoRiferimentoInput()).thenReturn("2021");
        Mockito.when(recordMockito.getCampiInput().getPeriodoRiferimentoInput() ).thenReturn("Z01");

        List<Esito> result = regola.valida("dataEntrata", recordMockito);
        for (Esito e : result) {
            assertFalse(e.isValoreEsito());
        }
    }


    @Test
    void costruttoreVuoto() {
        RegolaEmurPSDimissioneOraDest regola = new RegolaEmurPSDimissioneOraDest();
        assertTrue(regola instanceof RegolaEmurPSDimissioneOraDest);
    }

    @Test
    void validaKOException() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        Mockito.when(recordMockito.getCampo(any())).thenThrow(new IllegalAccessException());
        assertThrows(ValidazioneImpossibileException.class,()->regola.valida("codStruttura", recordMockito));
    }
}