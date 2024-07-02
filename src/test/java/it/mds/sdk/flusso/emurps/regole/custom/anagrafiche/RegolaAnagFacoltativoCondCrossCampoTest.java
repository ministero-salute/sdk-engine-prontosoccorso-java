package it.mds.sdk.flusso.emurps.regole.custom.anagrafiche;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.mds.sdk.connettore.anagrafiche.tabella.RecordAnagrafica;
import it.mds.sdk.flusso.emurps.parser.regole.Utilities;
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
import it.mds.sdk.connettore.anagrafiche.tabella.TabellaAnagrafica;
import it.mds.sdk.gestoreesiti.modelli.Esito;
import it.mds.sdk.libreriaregole.dtos.CampiInputBean;
import it.mds.sdk.libreriaregole.dtos.RecordDtoGenerico;
import it.mds.sdk.libreriaregole.exception.ValidazioneImpossibileException;
import it.mds.sdk.libreriaregole.regole.beans.Parametri;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RegolaAnagFacoltativoCondCrossCampoTest {

	 @Mock
	    RecordDtoGenerico recordMockito;
	    @Mock
	    GestoreAnagrafica gestoreAnagrafica = Mockito.mock(GestoreAnagrafica.class);
	    TabellaAnagrafica tabellaAnagrafica = Mockito.mock(TabellaAnagrafica.class);
	    CampiInputBean campiInputBean = Mockito.mock(CampiInputBean.class);
	    Parametri parametriTest;

	private static MockedStatic<Utilities> utilities;
		RegolaAnagFacoltativoCondCrossCampo regola;

	    @BeforeEach
	    void init(){
	        MockitoAnnotations.openMocks(this);
			utilities = mockStatic(Utilities.class);
	        Map<String, String> parametri = new HashMap<>();
	        parametri.put("valoreDipendente", "1");
			parametri.put("valoreDipendente", "2");
			parametri.put("campoDipendente", "importoPosizioneAssistitoTicket");
			parametri.put("nomeTabella", "ANAG_EMUR_ESENZIONI");
	        parametriTest = new Parametri();
	        parametriTest.setParametriMap(parametri);

	        regola = spy(new RegolaAnagFacoltativoCondCrossCampo("TEST", "TEST", "TEST", parametriTest));
	    }

	@AfterEach
	void close(){
		utilities.close();
	}

	    @Test
	    void costruttoreVuoto() {
	    	RegolaAnagFacoltativoCondCrossCampo regola = new RegolaAnagFacoltativoCondCrossCampo();
	        assertTrue(regola instanceof RegolaAnagFacoltativoCondCrossCampo);
	    }
	    
	    @Test
	    void validaOK() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedRegistryException, RegistryNotFoundException {
	    	Mockito.when(recordMockito.getCampo("importoPosizioneAssistitoTicket")).thenReturn(null);

			List<String> lista = new ArrayList<>();lista.add("99");
			utilities.when(() -> Utilities.getValoriAnagrafica(any(), eq("ANAG_EMUR_ESENZIONI"),anyString())).thenReturn(lista);

	        List<Esito> result = regola.valida("importoPosizioneAssistitoTicket", recordMockito);
	        for (Esito e : result) {
	        	 assertTrue(e.isValoreEsito());
		         //assertEquals("9999", e.getErroriValidazione().get(0).getCodice());
	        }
	    }

	    @Test
	    void validaKOException() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
	    {
	        Mockito.when(recordMockito.getCampo(any())).thenThrow(new IllegalAccessException());
	        assertThrows(ValidazioneImpossibileException.class,()->regola.valida("codStruttura", recordMockito));
	    }
}