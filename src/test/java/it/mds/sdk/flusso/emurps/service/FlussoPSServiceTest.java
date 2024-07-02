/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.flusso.emurps.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import it.mds.sdk.connettoremds.ConnettoreMds;
import it.mds.sdk.connettoremds.exception.ConnettoreMdsException;
import it.mds.sdk.connettoremds.gaf.webservices.bean.ArrayOfUploadEsito;
import it.mds.sdk.connettoremds.gaf.webservices.bean.ResponseUploadFile;
import it.mds.sdk.flusso.emurps.parser.regole.ParserTracciatoImpl;
import it.mds.sdk.flusso.emurps.parser.regole.RecordDtoPS;
import it.mds.sdk.flusso.emurps.parser.regole.conf.ConfigurazioneFlussoPS;
import it.mds.sdk.flusso.emurps.tracciato.TracciatoSplitterImpl;
import it.mds.sdk.flusso.emurps.tracciato.bean.output.FlsProSoc;
import it.mds.sdk.gestoreesiti.GestoreRunLog;
import it.mds.sdk.gestoreesiti.conf.Configurazione;
import it.mds.sdk.gestoreesiti.modelli.InfoRun;
import it.mds.sdk.gestoreesiti.modelli.ModalitaOperativa;
import it.mds.sdk.gestoreesiti.validazioneXSD.MainTester;
import it.mds.sdk.gestorefile.GestoreFile;
import it.mds.sdk.gestorefile.exception.XSDNonSupportedException;
import it.mds.sdk.gestorefile.factory.GestoreFileFactory;
import it.mds.sdk.libreriaregole.dtos.RecordDtoGenerico;
import it.mds.sdk.libreriaregole.gestorevalidazione.BloccoValidazione;
import it.mds.sdk.libreriaregole.regole.beans.Campo;
import it.mds.sdk.libreriaregole.regole.beans.Parametri;
import it.mds.sdk.libreriaregole.regole.beans.RegolaGenerica;
import it.mds.sdk.libreriaregole.regole.beans.RegoleFlusso;
import it.mds.sdk.libreriaregole.regole.catalogo.input.RegolaCheckCampiInput;
import it.mds.sdk.libreriaregole.validator.ValidationEngine;
import lombok.extern.slf4j.Slf4j;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Slf4j
@MockitoSettings(strictness = Strictness.LENIENT)
public class FlussoPSServiceTest {
    private final String EMURPS_CSV = "EMURPS_FileTest.csv";
    private final int dimensioneBlocco = 250000;

    private final String idClient = "1";
    private final String periodoRiferimento = "Z04";
    private final String annoRfierimento = "2022";
    private final String codiceRegione = "110";
    @Autowired
    ConfigurazioneFlussoPS configurazioneFlusso;
    ParserTracciatoImpl parser = Mockito.mock(ParserTracciatoImpl.class);
    @InjectMocks
    @Spy
    private FlussoPSService flusso;
    //  private GestoreRunLog gestoreRunLog;
    @Mock
    private RegoleFlusso regoleFlusso;
    @Spy
    private ConfigurazioneFlussoPS conf;
    ;
    private TracciatoSplitterImpl tracciatoSplitter = Mockito.mock(TracciatoSplitterImpl.class);
    private GestoreFile gestoreFileInt2 = Mockito.mock(GestoreFile.class);
    private GestoreFile gestoreFileInt = Mockito.mock(GestoreFile.class);
    private ConnettoreMds connettoreMds = Mockito.mock(ConnettoreMds.class);
    private GestoreRunLog grl = Mockito.mock(GestoreRunLog.class);
    //    @Mock
    private ValidationEngine validationEngine = Mockito.mock(ValidationEngine.class);
    private FlsProSoc flsProSoc = Mockito.mock(FlsProSoc.class);
    private String idRun = "999";
    private BloccoValidazione bloccoValidazione;
    private File file;

    private Configurazione config = new Configurazione();

    private final String percorso = String.format("%s/ESITO_%s.json", config.getEsito().getPercorso(), idRun);
    private final String percorsoTemp = String.format("%s/ESITO_%s_TEMP.json", config.getEsito().getPercorso(), idRun);
    private RecordDtoPS recordDtoPS;
    private ParserTracciatoImpl parserTracciato = Mockito.mock(ParserTracciatoImpl.class);

    private InfoRun infoRun;
    private MockedStatic<GestoreFileFactory> mockedStatic;
    /*
      TODO:
       **************** ATTENZIONE ******************
       Prima di lanciare i test assicurarsi
       di aver settato correttamente i parametri
       nel file di properties.
       **********************************************
     */

    @BeforeEach
    void init() {
        log.warn("\n*** ATTENZIONE ***\n" +
                "ATTENZIONE: I test runnati necessitano di proprietà settate nel file di properties.\n " +
                "Non settare le proprietà potrebbe causare il fallimento di alcuni test.\n" +
                "******************\n"
        );
        MockitoAnnotations.openMocks(this);
        // Create Parametri

        Properties prop = loadPropertiesFromFile("config-flusso-test.properties");
        this.idRun = prop.getProperty("test.idrun");

        Map<String, String> parametriMap = new HashMap<>();
        parametriMap.put("parametroInput", "codiceRegioneInput");
        Parametri parametri = new Parametri();
        parametri.setParametriMap(parametriMap);
        // Create RegolaGenerica
        RegolaGenerica regolaGenerica = new RegolaCheckCampiInput();
        regolaGenerica.setCodErrore("B01");
        regolaGenerica.setDesErrore("Non appartenenza al dominio di riferimento");
        regolaGenerica.setParametri(parametri);
        // Create Campo
        Campo campo = new Campo(List.of(regolaGenerica), "codiceRegione");
        // Creazione RegoleFlusso
        regoleFlusso = new RegoleFlusso(List.of(campo));
        //    gestoreRunLog = new GestoreRunLog(new GestoreFileCSVImpl(), Progressivo.creaProgressivo(Progressivo.Fonte.FILE));

        recordDtoPS = new RecordDtoPS();

        bloccoValidazione = new BloccoValidazione();
        bloccoValidazione.setNumeroRecord(2);
        bloccoValidazione.setScartati(1);

        file = new File(conf.getFlusso().getPercorso() + EMURPS_CSV);

        infoRun = new InfoRun(
                null, null, null, null, null,
                null, null, null, null, null, null,
                1, 1, 1, null, null, null, null,
                null, null, null, null, null, null, null, "nomeFile",
                null, null, null
        );
        mockedStatic = mockStatic(GestoreFileFactory.class);
    }

    @Test
    void validazioneBlocchiTestModalitaT_KO() {
        File file = Mockito.mock(File.class);

        GestoreFile gestoreFile = Mockito.mock(GestoreFile.class);
        doReturn(configurazioneFlusso.getFlusso()).when(conf).getFlusso();
        doReturn(configurazioneFlusso.getXmlOutput()).when(conf).getXmlOutput();
        List<RecordDtoGenerico> list = List.of(recordDtoPS);

        doReturn(file).when(flusso).getfileFromPath(any());

        given(tracciatoSplitter.creaFlsProSoc(any(), any())).willReturn(flsProSoc);
        when(flusso.getParserTracciatoImpl()).thenReturn(parserTracciato);
        given(parserTracciato.parseTracciatoBlocco(any(), anyInt(), anyInt())).willReturn(list);
        mockedStatic.when(() -> GestoreFileFactory.getGestoreFile("XML")).thenReturn(gestoreFile);

        willThrow(new XSDNonSupportedException()).given(gestoreFile).scriviDtoFragment(any(), any(), any());

        given(validationEngine.startValidaFlussoBlocco(list, regoleFlusso, null, grl, 0)).willReturn(bloccoValidazione);
        given(validationEngine.formatJsonEsiti(percorso, percorsoTemp)).willReturn(true);
        given(validationEngine.formatXmlOutput(any(), any(), any())).willReturn(false);

        given(grl.getRun(any())).willReturn(infoRun);
        given(grl.cambiaStatoRun(any(), any())).willReturn(infoRun);
        given(grl.updateRun(any())).willReturn(infoRun);

        bloccoValidazione.setRecordList(List.of(recordDtoPS));

        Assertions.assertThrows(RuntimeException.class,
                () -> this.flusso.validazioneBlocchi(
                        dimensioneBlocco,
                        EMURPS_CSV,
                        regoleFlusso,
                        idRun,
                        idClient,
                        ModalitaOperativa.T,
                        periodoRiferimento,
                        annoRfierimento,
                        codiceRegione,
                        grl
                ));
    }

//    @Test
//    void validazioneBlocchiTestModalitaT_KO2() {
//
//        GestoreFile gestoreFile = Mockito.mock(GestoreFile.class);
//        when(spy(conf.getFlusso())).thenReturn(configurazioneFlusso.getFlusso());
//        when(conf.getXmlOutput()).thenReturn(configurazioneFlusso.getXmlOutput());
//        List<RecordDtoGenerico> list = List.of(recordDtoPS);
//
//        given(tracciatoSplitter.creaFlsProSoc(any(), any())).willReturn(flsProSoc);
//        when(flusso.getParserTracciatoImpl()).thenReturn(parserTracciato);
//        given(parserTracciato.parseTracciatoBlocco(any(), anyInt(), anyInt())).willReturn(list);
//
//        mockedStatic.when(() -> GestoreFileFactory.getGestoreFile("XML")).thenReturn(gestoreFile);
//        doNothing().when(gestoreFile).scriviDtoFragment(any(), any(), any());
//
//        given(validationEngine.startValidaFlussoBlocco(list, regoleFlusso, "999", grl, 0)).willReturn(bloccoValidazione);
//        given(validationEngine.formatJsonEsiti(anyString(), anyString())).willReturn(true);
//        given(validationEngine.formatXmlOutput(any(), any(), any())).willReturn(false);
//
//        given(grl.getRun(any())).willReturn(infoRun);
//        given(grl.cambiaStatoRun(any(), any())).willReturn(infoRun);
//        given(grl.updateRun(any())).willReturn(infoRun);
//
//        bloccoValidazione.setRecordList(List.of(recordDtoPS));
//        
//        this.flusso.validazioneBlocchi(
//                dimensioneBlocco,
//                EMURPS_CSV,
//                regoleFlusso,
//                idRun,
//                idClient,
//                ModalitaOperativa.T,
//                periodoRiferimento,
//                annoRfierimento,
//                codiceRegione,
//                grl
//        );
//    }

    @Test
    void validazioneBlocchiTestModalitaT_OK() {

        GestoreFile gestoreFile = Mockito.mock(GestoreFile.class);
        MainTester mainTester = Mockito.mock(MainTester.class);

        when(conf.getFlusso()).thenReturn(configurazioneFlusso.getFlusso());
        when(conf.getXmlOutput()).thenReturn(configurazioneFlusso.getXmlOutput());
        List<RecordDtoGenerico> list = List.of(recordDtoPS);
        given(parser.parseTracciatoBlocco(any(), anyInt(), anyInt())).willReturn(list);

        bloccoValidazione.setRecordList(Collections.emptyList());

        given(parserTracciato.parseTracciatoBlocco(any(), anyInt(), anyInt())).willReturn(list);

        given(validationEngine.startValidaFlussoBlocco(list, regoleFlusso, "999", grl, 0)).willReturn(bloccoValidazione);
        given(validationEngine.formatJsonEsiti(anyString(), anyString())).willReturn(true);
        given(validationEngine.formatXmlOutput(any(), any(), any())).willReturn(false);
        given(validationEngine.validateXML(eq(null), anyString())).willReturn(true);

        mockedStatic.when(() -> GestoreFileFactory.getGestoreFile("XML")).thenReturn(gestoreFile);
        doNothing().when(gestoreFile).scriviDtoFragment(any(), any(), any());

        given(grl.getRun(any())).willReturn(infoRun);
        given(grl.cambiaStatoRun(any(), any())).willReturn(infoRun);
        given(grl.updateRun(any())).willReturn(infoRun);

        bloccoValidazione.setRecordList(List.of(recordDtoPS));

        when(flusso.getParserTracciatoImpl()).thenReturn(parserTracciato);
        when(flusso.getMainTester()).thenReturn(mainTester);
        when(mainTester.xmlValidationAgainstXSD(any(), any())).thenReturn(true);

        this.flusso.validazioneBlocchi(
                dimensioneBlocco,
                EMURPS_CSV,
                regoleFlusso,
                idRun,
                idClient,
                ModalitaOperativa.T,
                periodoRiferimento,
                annoRfierimento,
                codiceRegione,
                grl
        );
    }

    @Test
    void validazioneBlocchiTestModalitaP_OK() {

        doReturn(configurazioneFlusso.getFlusso()).when(conf).getFlusso();
        when(conf.getXmlOutput()).thenReturn(configurazioneFlusso.getXmlOutput());
        List<RecordDtoGenerico> list = List.of(recordDtoPS);
        given(parser.parseTracciatoBlocco(any(), anyInt(), anyInt())).willReturn(list);

        bloccoValidazione.setRecordList(List.of(recordDtoPS));

        given(validationEngine.startValidaFlussoBlocco(anyList(), any(), anyString(), any(), anyInt())).willReturn(bloccoValidazione);
        given(validationEngine.formatJsonEsiti(any(), any())).willReturn(true);
        given(validationEngine.puliziaFileSism(any(), any(), any())).willReturn("mocked_filename");
        given(validationEngine.validateXML(eq(null), anyString())).willReturn(true);

        willDoNothing().given(flusso).inviaTracciatoMds(any(), any(), any(), any(), any());

        given(tracciatoSplitter.creaFlsProSoc(any(), any())).willReturn(flsProSoc);
        mockedStatic.when(() -> GestoreFileFactory.getGestoreFile("XML")).thenReturn(gestoreFileInt2);

//        willDoNothing().given(gestoreFileInt2).scriviDtoFragment(eq(dataroot), any(), any());

        given(grl.getRun(any())).willReturn(infoRun);
        given(grl.cambiaStatoRun(any(), any())).willReturn(infoRun);
        given(grl.updateRun(any())).willReturn(infoRun);

        this.flusso.validazioneBlocchi(
                dimensioneBlocco,
                EMURPS_CSV,
                regoleFlusso,
                idRun,
                idClient,
                ModalitaOperativa.P,
                periodoRiferimento,
                annoRfierimento,
                codiceRegione,
                grl
        );
    }

    @Test
    void inviaTracciatoMdsTest_KOMinistero() throws ConnettoreMdsException {
        String nomeFileXml = conf.getXmlOutput().getPercorso() + "SDK_PS1_PS1_" + periodoRiferimento + "_" + idRun +
                ".xml";
        given(grl.getRun(any())).willReturn(infoRun);
        ResponseUploadFile responseUploadFile = new ResponseUploadFile();
        responseUploadFile.setErrorCode("x");
        given(connettoreMds.invioTracciati(any(), any(), any(), any(), any())).willReturn(responseUploadFile);
        given(grl.cambiaStatoRun(any(), any())).willReturn(infoRun);
        flusso.inviaTracciatoMds(
                idRun,
                nomeFileXml,
                grl,
                periodoRiferimento,
                annoRfierimento
        );
    }

    @Test
    void inviaTracciatoMdsTest_ErrorCodeNull() throws ConnettoreMdsException {
        String nomeFileXml = conf.getXmlOutput().getPercorso() + "SDK_PS1_PS1_" + periodoRiferimento + "_" + idRun +
                ".xml";
        given(grl.getRun(any())).willReturn(infoRun);
        ResponseUploadFile responseUploadFile = new ResponseUploadFile();
        responseUploadFile.setErrorCode(null);
        given(connettoreMds.invioTracciati(any(), any(), any(), any(), any())).willReturn(responseUploadFile);
        given(grl.cambiaStatoRun(any(), any())).willReturn(infoRun);
        flusso.inviaTracciatoMds(
                idRun,
                nomeFileXml,
                grl,
                periodoRiferimento,
                annoRfierimento
        );
    }

    @Test
    void inviaTracciatoMdsTest_ListaEsitiNotNull() throws ConnettoreMdsException {
        String nomeFileXml = conf.getXmlOutput().getPercorso() + "SDK_PS1_PS1_" + periodoRiferimento + "_" + idRun +
                ".xml";
        given(grl.getRun(any())).willReturn(infoRun);
        ResponseUploadFile responseUploadFile = new ResponseUploadFile();
        ArrayOfUploadEsito arr = Mockito.mock(ArrayOfUploadEsito.class);

        responseUploadFile.setListaEsitiUpload(arr);
        responseUploadFile.setErrorCode(null);
        given(connettoreMds.invioTracciati(any(), any(), any(), any(), any())).willReturn(responseUploadFile);
        given(grl.cambiaStatoRun(any(), any())).willReturn(infoRun);
        flusso.inviaTracciatoMds(
                idRun,
                nomeFileXml,
                grl,
                periodoRiferimento,
                annoRfierimento
        );
    }

    @Test
    void inviaTracciatoMdsTestOk() throws ConnettoreMdsException, IOException {
        Path path = Mockito.mock(Path.class);
        String nomeFileXml = conf.getXmlOutput().getPercorso() + "SDK_PS1_PS1_" + periodoRiferimento + "_" + idRun +
                ".xml";
        given(grl.getRun(any())).willReturn(infoRun);
        ResponseUploadFile responseUploadFile = new ResponseUploadFile();
        ArrayOfUploadEsito arr = Mockito.mock(ArrayOfUploadEsito.class);

        responseUploadFile.setListaEsitiUpload(arr);
        responseUploadFile.setErrorCode("");
        given(connettoreMds.invioTracciati(any(), any(), any(), any(), any())).willReturn(responseUploadFile);
        given(grl.cambiaStatoRun(any(), any())).willReturn(infoRun);

        doNothing().when(flusso).fileCopy(any(), any());

        flusso.inviaTracciatoMds(
                idRun,
                nomeFileXml,
                grl,
                periodoRiferimento,
                annoRfierimento
        );

    }

    private Properties loadPropertiesFromFile(String fileName) {
        Properties prop = new Properties();
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream stream = loader.getResourceAsStream(fileName);
            prop.load(stream);
            stream.close();
        } catch (Exception e) {
            String msg = String.format("Failed to load file '%s' - %s - %s", fileName, e.getClass().getName(),
                    e.getMessage());
            System.out.println(msg);
        }
        return prop;
    }

    @AfterEach
    void closeStaticMocks() {
        mockedStatic.close();
    }
}