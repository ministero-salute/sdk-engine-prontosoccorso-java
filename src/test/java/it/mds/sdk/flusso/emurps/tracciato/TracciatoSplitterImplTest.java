/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.flusso.emurps.tracciato;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.xml.sax.SAXException;

import it.mds.sdk.flusso.emurps.parser.regole.RecordDtoPS;
import it.mds.sdk.flusso.emurps.parser.regole.conf.ConfigurazioneFlussoPS;
import it.mds.sdk.flusso.emurps.tracciato.bean.output.FlsProSoc;
import it.mds.sdk.flusso.emurps.tracciato.bean.output.ObjectFactory;
import it.mds.sdk.gestorefile.GestoreFile;
import it.mds.sdk.gestorefile.factory.GestoreFileFactory;
import it.mds.sdk.libreriaregole.dtos.CampiInputBean;
import jakarta.xml.bind.JAXBException;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@MockitoSettings(strictness = Strictness.LENIENT)
public class TracciatoSplitterImplTest {
    @InjectMocks
    @Spy
    private TracciatoSplitterImpl tracciatoSplitter;
    private ConfigurazioneFlussoPS configurazione = Mockito.mock(ConfigurazioneFlussoPS.class);
    private ObjectFactory objectFactory = Mockito.mock(ObjectFactory.class);
    private FlsProSoc flsProSoc = Mockito.mock(FlsProSoc.class);
//    private FlsProSoc.AziendaSanitariaRiferimento asl = Mockito.mock(FlsProSoc.AziendaSanitariaRiferimento.class);
    private ConfigurazioneFlussoPS.XmlOutput xmlOutput = Mockito.mock(ConfigurazioneFlussoPS.XmlOutput.class);
    private MockedStatic<GestoreFileFactory> gestore;
    private GestoreFile gestoreFile = Mockito.mock(GestoreFile.class);
//    private FlsProSoc.AziendaSanitariaRiferimento aziendaSanitariaRiferimento = Mockito.mock(FlsProSoc.AziendaSanitariaRiferimento.class);
//    private List<FlsProSoc.AziendaSanitariaRiferimento> aziendaSanitariaRiferimentoList = new ArrayList<>();
//    private FlsProSoc.AziendaSanitariaRiferimento.DSM dsm = Mockito.mock(FlsProSoc.AziendaSanitariaRiferimento.DSM.class);
//    private List<FlsProSoc.AziendaSanitariaRiferimento.DSM> listDsm = new ArrayList<>();
//    private FlsProSoc.AziendaSanitariaRiferimento.DSM.Assistito assistito = Mockito.mock(FlsProSoc.AziendaSanitariaRiferimento.DSM.Assistito.class);
    private RecordDtoPS r = new RecordDtoPS();
    List<RecordDtoPS> records = new ArrayList<>();

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        gestore = mockStatic(GestoreFileFactory.class);
        initMockedRecord(r);
        records.add(r);
    }

//    @Test
//    void dividiTracciatoTest() throws JAXBException, IOException, SAXException {
//
//        when(tracciatoSplitter.getConfigurazione()).thenReturn(configurazione);
//        when(objectFactory.createFlsProSoc()).thenReturn(flsProSoc);
////        when(flsProSoc.getAziendaSanitariaRiferimento()).thenReturn(List.of(asl));
//        when(configurazione.getXmlOutput()).thenReturn(xmlOutput);
//        when(xmlOutput.getPercorso()).thenReturn("percorso");
//        gestore.when(() -> GestoreFileFactory.getGestoreFile("XML")).thenReturn(gestoreFile);
//        doNothing().when(gestoreFile).scriviDto(any(), any(), any());
//        
//        
//        Assertions.assertEquals(
//                List.of(Path.of("percorsoSDK_PS1_PS1_Z04_999.xml")),
//                this.tracciatoSplitter.dividiTracciato(records, "999")
//        );
//
//    }

//    @Test
//    void dividiTracciatoTestOk2() throws JAXBException, IOException, SAXException {
//        when(tracciatoSplitter.getConfigurazione()).thenReturn(configurazione);
//        when(objectFactory.createFlsProSoc()).thenReturn(flsProSoc);
////        when(flsProSoc.getAziendaSanitariaRiferimento()).thenReturn(List.of(asl));
//
//        when(configurazione.getXmlOutput()).thenReturn(xmlOutput);
//        when(xmlOutput.getPercorso()).thenReturn("percorso");
//        gestore.when(() -> GestoreFileFactory.getGestoreFile("XML")).thenReturn(gestoreFile);
//        doNothing().when(gestoreFile).scriviDto(any(), any(), any());
//
////        doReturn(null).when(tracciatoSplitter).getCurrentAsl(any(), any());
////        doReturn(null).when(tracciatoSplitter).getCurrentDSM(any(), any());
//        Assertions.assertEquals(
//                List.of(Path.of("percorsoSDK_PS1_PS1_Z04_100.xml")),
//                this.tracciatoSplitter.dividiTracciato(records, "100")
//        );
//
//    }

//    @Test
//    void getCurrentDsmTest() {
//        var list = List.of(dsm);
//        when(asl.getDSM()).thenReturn(list);
//        var c = tracciatoSplitter.getCurrentDSM(asl, r);
//    }

//    @Test
//    void getCurrentAslTest() {
//        var list = List.of(asl);
//
//        when(flsProSoc.getAziendaSanitariaRiferimento()).thenReturn(list);
//        var c = tracciatoSplitter.getCurrentAsl(flsProSoc, r);
//    }

//    @Test
//    void creaPrestazioniSanitarieTest() {
//        var list = List.of(asl);
//        var c = tracciatoSplitter.creaTerritorialeContatti(records, null);
//    }

    @AfterEach
    void closeMocks() {
        gestore.close();
    }

    private void initMockedRecord(RecordDtoPS r) {
        CampiInputBean campiInputBean = new CampiInputBean();
        campiInputBean.setPeriodoRiferimentoInput("Z04");
        campiInputBean.setAnnoRiferimentoInput("2022");
        campiInputBean.setCodiceRegioneInput("110");
        r.setCampiInput(campiInputBean);
        r.setErogatoreCodiceIstituto("111111");
        r.setIdentificativo("12345678");
        records.add(r);
    }

}