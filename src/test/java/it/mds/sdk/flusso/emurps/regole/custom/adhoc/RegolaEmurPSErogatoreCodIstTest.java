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
class RegolaEmurPSErogatoreCodIstTest {
    @Mock
    RecordDtoGenerico recordMockito;
    @Mock
    GestoreAnagrafica gestoreAnagrafica = Mockito.mock(GestoreAnagrafica.class);
    TabellaAnagrafica tabellaAnagrafica = Mockito.mock(TabellaAnagrafica.class);
    CampiInputBean campiInputBean = Mockito.mock(CampiInputBean.class);
    Parametri parametriTest;
    List<String> listaNazioniCeu;

    RegolaEmurPSErogatoreCodIst regola;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
        Map<String, String> parametri = new HashMap<>();

        parametriTest = new Parametri();
        parametriTest.setParametriMap(parametri);

        regola = spy(new RegolaEmurPSErogatoreCodIst("TEST", "TEST", "TEST", parametriTest));
    }

    @Test
    void costruttoreVuoto() {
        RegolaEmurPSErogatoreCodIst regola = new RegolaEmurPSErogatoreCodIst();
        assertTrue(regola instanceof RegolaEmurPSErogatoreCodIst);
    }

    @Test
    void validaKO() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedRegistryException, RegistryNotFoundException {

        Mockito.when(recordMockito.getCampo("erogatoreCodiceIstituto")).thenReturn("120201");
        Mockito.when(((String)recordMockito.getCampo("erogatoreCodiceIstituto")).substring(0,3)).thenReturn("120");
        Mockito.when(recordMockito.getCampo("tipoTrasmissione")).thenReturn("C");

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
    void validaKOException() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        Mockito.when(recordMockito.getCampo(any())).thenThrow(new IllegalAccessException());
        assertThrows(ValidazioneImpossibileException.class,()->regola.valida("codStruttura", recordMockito));
    }
}