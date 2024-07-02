/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.flusso.emurps.parser.regole;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.mds.sdk.flusso.emurps.regole.custom.RegolaDataEntro24OreAltraData;
import it.mds.sdk.flusso.emurps.regole.custom.RegolaDiversitaAnnoCrossCampoFacoltativo;
import it.mds.sdk.flusso.emurps.regole.custom.RegolaDueLunghezzeAlternative;
import it.mds.sdk.flusso.emurps.regole.custom.RegolaDueLunghezzeAlternativeFacoltativo;
import it.mds.sdk.flusso.emurps.regole.custom.RegolaIncoerenzaAlternanzaCrossCampo;
import it.mds.sdk.flusso.emurps.regole.custom.RegolaIncoerenzaCrossCampoUguaglianzaCustom;
import it.mds.sdk.flusso.emurps.regole.custom.RegolaIncongruenzaCrossDueCampi;
import it.mds.sdk.flusso.emurps.regole.custom.RegolaIncongruenzaCrossDueCampiUguaglianza;
import it.mds.sdk.flusso.emurps.regole.custom.RegolaMinMaxLunghezzaCampoFacoltativo;
import it.mds.sdk.flusso.emurps.regole.custom.RegolaObbligatorietaAlternataCrossCampo;
import it.mds.sdk.flusso.emurps.regole.custom.RegolaObbligatorietaCondizionataMultiCampo;
import it.mds.sdk.flusso.emurps.regole.custom.RegolaObbligatorietaCondizionataMultiCampoDiverso;
import it.mds.sdk.flusso.emurps.regole.custom.RegolaObbligatorietaCondizionataValoreCampoVuoto;
import it.mds.sdk.flusso.emurps.regole.custom.RegolaRangeOccorrenzeAmmesse;
import it.mds.sdk.flusso.emurps.regole.custom.adhoc.RegolaEmurPSASLResidenza;
import it.mds.sdk.flusso.emurps.regole.custom.adhoc.RegolaEmurPSComuneResidenza;
import it.mds.sdk.flusso.emurps.regole.custom.adhoc.RegolaEmurPSDataDest;
import it.mds.sdk.flusso.emurps.regole.custom.adhoc.RegolaEmurPSDataDimissione;
import it.mds.sdk.flusso.emurps.regole.custom.adhoc.RegolaEmurPSDataEntrata;
import it.mds.sdk.flusso.emurps.regole.custom.adhoc.RegolaEmurPSDimissioneOraDest;
import it.mds.sdk.flusso.emurps.regole.custom.adhoc.RegolaEmurPSErogatoreCodIst;
import it.mds.sdk.flusso.emurps.regole.custom.adhoc.RegolaEmurPSLivelloAppropriatezza;
import it.mds.sdk.flusso.emurps.regole.custom.adhoc.RegolaEmurPSMeseObbligatorietaCondizionataCampo;
import it.mds.sdk.flusso.emurps.regole.custom.adhoc.RegolaEmurPSMesePresenzaCondizionataCampo;
import it.mds.sdk.flusso.emurps.regole.custom.adhoc.RegolaEmurPSOBIDataIngresso;
import it.mds.sdk.flusso.emurps.regole.custom.adhoc.RegolaEmurPSOBIDataUscita;
import it.mds.sdk.flusso.emurps.regole.custom.adhoc.RegolaEmurPSOBIOraIngresso;
import it.mds.sdk.flusso.emurps.regole.custom.adhoc.RegolaEmurPSOBIOraUscita;
import it.mds.sdk.flusso.emurps.regole.custom.adhoc.RegolaEmurPSRegioneResidenza;
import it.mds.sdk.flusso.emurps.regole.custom.adhoc.RegolaEmurPSRicoveroProgressivo;
import it.mds.sdk.flusso.emurps.regole.custom.adhoc.RegolaEmurPSStatoEsteroResidenza;
import it.mds.sdk.flusso.emurps.regole.custom.adhoc.RegolaEsitoTrattamento;
import it.mds.sdk.flusso.emurps.regole.custom.adhoc.RegolaIncongruenzaCrossDueCampiList;
import it.mds.sdk.flusso.emurps.regole.custom.anagrafiche.RegolaAnagFacoltativoCondCrossCampo;
import it.mds.sdk.flusso.emurps.regole.custom.anagrafiche.RegolaAnagFacoltativoRegPrimiCaratteriCustom;
import it.mds.sdk.flusso.emurps.regole.custom.anagrafiche.RegolaAnagTEAMCustom;
import it.mds.sdk.libreriaregole.regole.beans.Campo;
import it.mds.sdk.libreriaregole.regole.beans.Parametri;
import it.mds.sdk.libreriaregole.regole.beans.RegolaGenerica;
import it.mds.sdk.libreriaregole.regole.beans.RegoleFlusso;

public class CreazioneRegoleCustomEmur {
	
	public static RegoleFlusso creaRegoleEmurPs(RegoleFlusso regoleFlusso) {
		// aggiungi regole custom
		List<RegolaGenerica> regoleErogatoreCodiceIstituto = new ArrayList<RegolaGenerica>();
        List<RegolaGenerica> regoleCOIdentificativo = new ArrayList<RegolaGenerica>();
        List<RegolaGenerica> regoleCentraleOperativaCodiceMissione = new ArrayList<RegolaGenerica>();
        List<RegolaGenerica> regoleIstitutoProvenienza = new ArrayList<RegolaGenerica>();
        List<RegolaGenerica> regolePresaInCaricoData = new ArrayList<RegolaGenerica>();
//        List<RegolaGenerica> regoletriage = new ArrayList<RegolaGenerica>();
        List<RegolaGenerica> regoleEsitoTrattamento = new ArrayList<RegolaGenerica>();
        List<RegolaGenerica> regoleDimissioneDataDest = new ArrayList<RegolaGenerica>();
        List<RegolaGenerica> regoleDimissioneOraDest = new ArrayList<RegolaGenerica>();
        List<RegolaGenerica> regoleDimissioneData = new ArrayList<RegolaGenerica>();
        List<RegolaGenerica> regoleDimissioneOra = new ArrayList<RegolaGenerica>();
        List<RegolaGenerica> regoleLivApproAccesso = new ArrayList<RegolaGenerica>();
//        List<RegolaGenerica> regoleRicCodiceSpecialitaRep = new ArrayList<RegolaGenerica>();
        List<RegolaGenerica> regoleRicProgressico = new ArrayList<RegolaGenerica>();
        List<RegolaGenerica> regoleTrasferimentoMotivo = new ArrayList<RegolaGenerica>();
        List<RegolaGenerica> regoleTrasferimentoIstit = new ArrayList<RegolaGenerica>();
//        List<RegolaGenerica> regoleRegimeErogazione = new ArrayList<RegolaGenerica>();        
        List<RegolaGenerica> regoleImportoLordo = new ArrayList<RegolaGenerica>();
        List<RegolaGenerica> regoleImportoTicket = new ArrayList<RegolaGenerica>();
//        List<RegolaGenerica> regolePosizioneAssistitoTicket = new ArrayList<RegolaGenerica>();
//        List<RegolaGenerica> regoleImportoCodiceEsenzione = new ArrayList<RegolaGenerica>();
//        List<RegolaGenerica> regoleImportoCodiceIstituzioneTEAM = new ArrayList<RegolaGenerica>();
        List<RegolaGenerica> regoleEntrataData = new ArrayList<RegolaGenerica>();
//        List<RegolaGenerica> regoleTrauma = new ArrayList<RegolaGenerica>();
        List<RegolaGenerica> regolePresaInCaricoOra = new ArrayList<RegolaGenerica>();
        List<RegolaGenerica> regoleDatiAnagraficiEtaNascitaAnno = new ArrayList<RegolaGenerica>();
//        List<RegolaGenerica> regoleDatiAnagraficiEtaNascitaMese = new ArrayList<RegolaGenerica>();
        List<RegolaGenerica> regoleDatiAnagraficiEtaPresunta = new ArrayList<RegolaGenerica>();
        List<RegolaGenerica> regolePrestazioniDiagnosiDiagnosiPrincipale = new ArrayList<RegolaGenerica>();
        List<RegolaGenerica> regolePrestazioniDiagnosiDiagnosiSecondaria = new ArrayList<RegolaGenerica>();
        List<RegolaGenerica> regolePrestazionePrincipale = new ArrayList<RegolaGenerica>();
        List<RegolaGenerica> regolePrestazioneSecondaria = new ArrayList<RegolaGenerica>();
        List<RegolaGenerica> regoleOBIProblemaClinicoPrincipale = new ArrayList<RegolaGenerica>();
        List<RegolaGenerica> regoleOBIDataIngresso = new ArrayList<RegolaGenerica>();
        List<RegolaGenerica> regoleOBIOraIngresso = new ArrayList<RegolaGenerica>();
        List<RegolaGenerica> regoleOBIPrimaConsulenzaSpecDis = new ArrayList<RegolaGenerica>();
        List<RegolaGenerica> regoleOBIPrimaConsulenzaSpecNum = new ArrayList<RegolaGenerica>();
        List<RegolaGenerica> regoleOBISecondaConsulenzaSpecDis = new ArrayList<RegolaGenerica>();
        List<RegolaGenerica> regoleOBISecondaConsulenzaSpecNum = new ArrayList<RegolaGenerica>();
        List<RegolaGenerica> regoleOBIEsito = new ArrayList<RegolaGenerica>();
        List<RegolaGenerica> regoleOBIPrestazionePrincipaleErogata = new ArrayList<RegolaGenerica>();
        List<RegolaGenerica> regoleOBIPrestazioneSecondariaErogata = new ArrayList<RegolaGenerica>();
        List<RegolaGenerica> regoleOBIDiagnosiPrincipaleUscita = new ArrayList<RegolaGenerica>();
        List<RegolaGenerica> regoleOBIDiagnosiSecondariaUscita = new ArrayList<RegolaGenerica>();
        List<RegolaGenerica> regoleOBIDataUscita = new ArrayList<RegolaGenerica>();
        List<RegolaGenerica> regoleOBIOraUscita = new ArrayList<RegolaGenerica>();
//        List<RegolaGenerica> regoleResidenzaASL = new ArrayList<RegolaGenerica>();
        List<RegolaGenerica> regoleResidenzaComune = new ArrayList<RegolaGenerica>();     
        List<RegolaGenerica> regoleResidenzaStatoEstero = new ArrayList<RegolaGenerica>();     
//        List<RegolaGenerica> regoleAssistitoCUNI = new ArrayList<RegolaGenerica>();     
        List<RegolaGenerica> regoleDatiAnagraficiResidenzaRegione = new ArrayList<RegolaGenerica>();     
        
        
        Campo erogatoreCodiceIstituto = creaRegolaErogatoreCodIstituto(regoleErogatoreCodiceIstituto, "erogatoreCodiceIstituto","104");
        Campo entrataData = creaRegolaCustomEntrataData(regoleEntrataData, "entrataData");
        Campo centraleOpIdentificativo = creaRegolaCentraleOpIdentificativo(regoleCOIdentificativo, "centraleOperativaIdentificativo");
        Campo centraleOperativaCodiceMissione = creaRegolaCentraleOperativaCodiceMissione(regoleCentraleOperativaCodiceMissione, "centraleOperativaCodiceMissione");
        Campo istitutoProvenienza = creaRegolaIstitutoProvenienza(regoleIstitutoProvenienza, "istitutoProvenienza","103");
     // TODOs: FB - ANOMALIA - COMMENTATA
//        Campo trauma = creaRegolaIncongruenzaTrauma(regoleTrauma,"trauma");
        Campo presaInCaricoData = creaRegolaPresaInCaricoData(regolePresaInCaricoData, "assistitoPrestazioniPresaInCaricoData");
        Campo presaInCaricoOra = creaRegolaPresaInCaricoOra(regolePresaInCaricoOra, "assistitoPrestazioniPresaInCaricoOra");
        // controllo eliminato perchè non riportato sul documento
//        Campo triage = creaRegolatriageCrossCampo(regoletriage, "triage");
        Campo esitoTrattamento = creaRegolaEsitoTrattamentoCrossCampo(regoleEsitoTrattamento, "assistitoDimissioneEsitoTrattamento");
        Campo dimissioneDataDest = creaRegolaDimissioneDataDest(regoleDimissioneDataDest, "assistitoDimissioneDataDest");
        Campo dimissioneOraDest = creaRegolaDimissioneOraDest(regoleDimissioneOraDest, "assistitoDimissioneOraDest");
        Campo dimissioneData = creaRegolaDimissioneData(regoleDimissioneData, "assistitoDimissioneData");
        Campo dimissioneOra = creaRegolaDimissioneOra(regoleDimissioneOra, "assistitoDimissioneOra");
        Campo livApproAccesso = creaRegolaLivelloAppropriatezzaAccesso(regoleLivApproAccesso, "assistitoDimissioneLivelloAppropriatezzaAccesso");
        // TODOs: FB - ANOMALIA - COMMENTATA - 1103 - assistitoDimissioneRicoveroCodiceSpecialitaReparto
        // TODOs: FB - ANOMALIA - COMMENTATA - 2069 - assistitoDimissioneRicoveroCodiceSpecialitaReparto
        // Scommentare chiamata e if per aggiunta regole
//        Campo ricoveroCodiceSpecialita = creaRegolaObbligatorioSeEsitoDue(regoleRicCodiceSpecialitaRep, "assistitoDimissioneRicoveroCodiceSpecialitaReparto");
        Campo ricoveroProgressivo = creaRegolaObbligatorioSeEsitoDueRicProgress(regoleRicProgressico, "assistitoDimissioneRicoveroProgressivo");
        Campo trasferimentoMotivo = creaRegolaTrasferimentoMotivo(regoleTrasferimentoMotivo, "assistitoDimissioneTrasferimentoMotivo");
        Campo trasferimentoIstituto = creaRegolaTrasferimentoIstituto(regoleTrasferimentoIstit, "assistitoDimissioneTrasferimentoIstituto");
        // TODOs: FB - ANOMALIA - COMMENTATA - 1103 - importoRegimeErogazione
        // TODOs: FB - ANOMALIA - COMMENTATA - 2089 - importoRegimeErogazione
//        Campo importoRegimeErogazione = creaRegolaImportoRegimeErogazione(regoleRegimeErogazione, "importoRegimeErogazione");
        Campo importoLordo = creaRegolaImportoLordo(regoleImportoLordo, "importoLordo");
        Campo importoTicket = creaRegolaObbligatorioPosAssistitoUno(regoleImportoTicket, "importoTicket");
     // TODOs: FB - ANOMALIA - COMMENTATA
        //Campo posizioneAssistitoTicket = creaRegolaImportoAssistitoTicket(regolePosizioneAssistitoTicket, "importoPosizioneAssistitoTicket");
        // TODOs: FB - ANOMALIA - COMMENTATA - 1302 - importoCodiceEsenzione
        // TODOs: FB - ANOMALIA - COMMENTATA - 20076 - importoCodiceEsenzione
//        Campo importoCodiceEsenzione = creaRegolaFacoltativoCondizCrossCampo(regoleImportoCodiceEsenzione, "importoCodiceEsenzione");
        // TODOs: FB - ANOMALIA - COMMENTATA - 2003 - assistitoCodiceIstituzioneTEAM
        // TODOs: FB - ANOMALIA - COMMENTATA - 2004 - assistitoCodiceIstituzioneTEAM
        // TODOs: FB - ANOMALIA - COMMENTATA - 2005 - assistitoCodiceIstituzioneTEAM
//        Campo assistitoCodiceIstituzioneTEAM = creaRegolaCodiceIstituzioneTEAM(regoleImportoCodiceIstituzioneTEAM, "assistitoCodiceIstituzioneTEAM");
        Campo assistitoDatiAnagraficiEtaNascitaAnno = creaRegolaDatiAnagraficiEtaNascitaAnno(regoleDatiAnagraficiEtaNascitaAnno, "assistitoDatiAnagraficiEtaNascitaAnno");
        // TODOs: FB - ANOMALIA - COMMENTATA - 1103 - assistitoDatiAnagraficiEtaNascitaMese
        // TODOs: FB - ANOMALIA - COMMENTATA - 3003 - assistitoDatiAnagraficiEtaNascitaMese
//        Campo assistitoDatiAnagraficiEtaNascitaMese = creaRegolaDatiAnagraficiEtaNascitaMese(regoleDatiAnagraficiEtaNascitaMese, "assistitoDatiAnagraficiEtaNascitaMese");
        Campo assistitoDatiAnagraficiEtaPresunta = creaRegolaDatiAnagraficiEtaPresunta(regoleDatiAnagraficiEtaPresunta, "assistitoDatiAnagraficiEtaPresunta");
        Campo assistitoPrestazioniDiagnosiDiagnosiPrincipale = creaRegolaPrestazioniDiagnosiDiagnosiPrincipale(regolePrestazioniDiagnosiDiagnosiPrincipale, "assistitoPrestazioniDiagnosiDiagnosiPrincipale");
        Campo assistitoPrestazioniDiagnosiDiagnosiSecondaria = creaRegolaPrestazioniDiagnosiDiagnosiSecondaria(regolePrestazioniDiagnosiDiagnosiSecondaria, "assistitoPrestazioniDiagnosiDiagnosiSecondaria");
        Campo assistitoPrestazionePrincipale = creaRegolaPrestazionePrincipale(regolePrestazionePrincipale, "assistitoPrestazioniPrestazionePrestazionePrincipale");
        Campo assistitoPrestazioneSecondaria = creaRegolaPrestazioneSecondaria(regolePrestazioneSecondaria, "assistitoPrestazioniPrestazionePrestazioneSecondaria");
        Campo assistitoOBIProblemaClinicoPrincipale = creaRegolaOBIProblemaClinicoPrincipale(regoleOBIProblemaClinicoPrincipale, "assistitoOBIProblemaClinicoPrincipale");
        Campo assistitoOBIDataIngresso = creaRegolaOBIDataIngresso(regoleOBIDataIngresso, "assistitoOBIDataIngresso");
        Campo assistitoOBIOraIngresso = creaRegolaOBIOraIngresso(regoleOBIOraIngresso, "assistitoOBIOraIngresso");
        Campo assistitoOBIPrimaConsulenzaSpecDis = creaRegolaOBIPrimaConsulenzaSpecDis(regoleOBIPrimaConsulenzaSpecDis, "assistitoOBIPrimaConsulenzaSpecDis");
        Campo assistitoOBIPrimaConsulenzaSpecNum = creaRegolaOBIPrimaConsulenzaSpecNum(regoleOBIPrimaConsulenzaSpecNum, "assistitoOBIPrimaConsulenzaSpecNum");
        Campo assistitoOBISecondaConsulenzaSpecDis = creaRegolaOBISecondaConsulenzaSpecDis(regoleOBISecondaConsulenzaSpecDis, "assistitoOBISecondaConsulenzaSpecDis");
        Campo assistitoOBISecondaConsulenzaSpecNum = creaRegolaOBISecondaConsulenzaSpecNum(regoleOBISecondaConsulenzaSpecNum, "assistitoOBISecondaConsulenzaSpecNum");
        Campo assistitoOBIEsito = creaRegolaOBIEsito(regoleOBIEsito, "assistitoOBIEsito");
        Campo assistitoOBIPrestazionePrincipaleErogata = creaRegolaOBIPrestazionePrincipaleErogata(regoleOBIPrestazionePrincipaleErogata, "assistitoOBIPrestazionePrincipaleErogata");
        Campo assistitoOBIPrestazioneSecondariaErogata = creaRegolaOBIPrestazioneSecondariaErogata(regoleOBIPrestazioneSecondariaErogata, "assistitoOBIPrestazioneSecondariaErogata");
        Campo assistitoOBIDiagnosiPrincipaleUscita = creaRegolaOBIDiagnosiPrincipaleUscita(regoleOBIDiagnosiPrincipaleUscita, "assistitoOBIDiagnosiPrincipaleUscita");
        Campo assistitoOBIDiagnosiSecondariaUscita = creaRegolaOBIDiagnosiSecondariaUscita(regoleOBIDiagnosiSecondariaUscita, "assistitoOBIDiagnosiSecondariaUscita");
        Campo assistitoOBIDataUscita = creaRegolaOBIDataUscita(regoleOBIDataUscita, "assistitoOBIDataUscita");
        Campo assistitoOBIOraUscita = creaRegolaOBIOraUscita(regoleOBIOraUscita, "assistitoOBIOraUscita");
        // TODOs: FB - ANOMALIA - COMMENTATA - 1302 - assistitoDatiAnagraficiResidenzaASL
        // TODOs: FB - ANOMALIA - COMMENTATA - 20077 - assistitoDatiAnagraficiResidenzaASL
//        Campo assistitoDatiAnagraficiResidenzaASL = creaRegolaResidenzaASL(regoleResidenzaASL, "assistitoDatiAnagraficiResidenzaASL");
        Campo assistitoDatiAnagraficiResidenzaComune = creaRegolaResidenzaComune(regoleResidenzaComune, "assistitoDatiAnagraficiResidenzaComune");
        Campo assistitoDatiAnagraficiResidenzaStatoEstero = creaRegolaResidenzaStatoEstero(regoleResidenzaStatoEstero, "assistitoDatiAnagraficiResidenzaStatoEstero");
        Campo assistitoDatiAnagraficiResidenzaRegione = creaRegolaDatiAnagraficiResidenzaRegione(regoleDatiAnagraficiResidenzaRegione, "assistitoDatiAnagraficiResidenzaRegione");

        
        // VERIFICARE IL CHECKSUM
        //        Campo assistitoCUNI = creaRegolaAssistitoCUNI(regoleAssistitoCUNI, "assistitoCUNI");
        
        
        
        
        List<Campo> campi = regoleFlusso.getCampi();
        
        for (Iterator<Campo> i = campi.iterator(); i.hasNext();) {
            Campo itemCampo = i.next();
            
            if (itemCampo.getNomeCampo().equals(erogatoreCodiceIstituto.getNomeCampo())){ 
            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
            	lstRegole.addAll(lstRegole.size(), erogatoreCodiceIstituto.getRegole());
            }
            if (itemCampo.getNomeCampo().equals(entrataData.getNomeCampo())){ 
            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
            	lstRegole.addAll(lstRegole.size(), entrataData.getRegole());
            }
            if (itemCampo.getNomeCampo().equals(centraleOpIdentificativo.getNomeCampo())){ 
            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
            	lstRegole.addAll(lstRegole.size(), centraleOpIdentificativo.getRegole());
            }             
//            if (itemCampo.getNomeCampo().equals(trauma.getNomeCampo())){ 
//            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
//            	lstRegole.addAll(lstRegole.size(), trauma.getRegole());
//            }
            if (itemCampo.getNomeCampo().equals(centraleOperativaCodiceMissione.getNomeCampo())){ 
            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
            	lstRegole.addAll(lstRegole.size(), centraleOperativaCodiceMissione.getRegole());
            }                      
            if (itemCampo.getNomeCampo().equals(istitutoProvenienza.getNomeCampo())){ 
            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
            	if (lstRegole == null)
            		lstRegole = new ArrayList<RegolaGenerica>();
            	            	
            	lstRegole.addAll(lstRegole.size(), istitutoProvenienza.getRegole());
            	itemCampo.setRegole(lstRegole);
            }
            if (itemCampo.getNomeCampo().equals(presaInCaricoData.getNomeCampo())){ 
            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
            	lstRegole.addAll(lstRegole.size(), presaInCaricoData.getRegole());
            }
            if (itemCampo.getNomeCampo().equals(presaInCaricoOra.getNomeCampo())){ 
            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
            	lstRegole.addAll(lstRegole.size(), presaInCaricoOra.getRegole());
            }            
//            if (itemCampo.getNomeCampo().equals(triage.getNomeCampo())){ 
//            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
//            	lstRegole.addAll(lstRegole.size(), triage.getRegole());
//            }
            if (itemCampo.getNomeCampo().equals(esitoTrattamento.getNomeCampo())){ 
            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
            	lstRegole.addAll(lstRegole.size(), esitoTrattamento.getRegole());
            }
            if (itemCampo.getNomeCampo().equals(dimissioneDataDest.getNomeCampo())){ 
            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
            	lstRegole.addAll(lstRegole.size(), dimissioneDataDest.getRegole());
            }
            if (itemCampo.getNomeCampo().equals(dimissioneOraDest.getNomeCampo())){ 
            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
            	lstRegole.addAll(lstRegole.size(), dimissioneOraDest.getRegole());
            }
            if (itemCampo.getNomeCampo().equals(dimissioneData.getNomeCampo())){ 
            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
            	lstRegole.addAll(lstRegole.size(), dimissioneData.getRegole());
            }
            if (itemCampo.getNomeCampo().equals(dimissioneOra.getNomeCampo())){ 
            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
            	lstRegole.addAll(lstRegole.size(), dimissioneOra.getRegole());
            }
            if (itemCampo.getNomeCampo().equals(livApproAccesso.getNomeCampo())){ 
            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
            	lstRegole.addAll(lstRegole.size(), livApproAccesso.getRegole());
            }
            // REGOLE ANOMALIA
//            if (itemCampo.getNomeCampo().equals(ricoveroCodiceSpecialita.getNomeCampo())){ 
//            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
//            	lstRegole.addAll(lstRegole.size(), ricoveroCodiceSpecialita.getRegole());
//            }
            if (itemCampo.getNomeCampo().equals(ricoveroProgressivo.getNomeCampo())){ 
            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
            	lstRegole.addAll(lstRegole.size(), ricoveroProgressivo.getRegole());
            }
            if (itemCampo.getNomeCampo().equals(trasferimentoMotivo.getNomeCampo())){ 
            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
            	lstRegole.addAll(lstRegole.size(), trasferimentoMotivo.getRegole());
            }
            
            if (itemCampo.getNomeCampo().equals(trasferimentoIstituto.getNomeCampo())){ 
            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
            		lstRegole = new ArrayList<RegolaGenerica>();
            	            	
            	lstRegole.addAll(lstRegole.size(), trasferimentoIstituto.getRegole());
            	itemCampo.setRegole(lstRegole);
            }
            // REGOLE ANOMALIA
//            if (itemCampo.getNomeCampo().equals(importoRegimeErogazione.getNomeCampo())){ 
//            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
//            	lstRegole.addAll(lstRegole.size(), importoRegimeErogazione.getRegole());
//            }
            if (itemCampo.getNomeCampo().equals(importoLordo.getNomeCampo())){ 
            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
            	lstRegole.addAll(lstRegole.size(), importoLordo.getRegole());
            }
            if (itemCampo.getNomeCampo().equals(importoTicket.getNomeCampo())){ 
            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
            	lstRegole.addAll(lstRegole.size(), importoTicket.getRegole());
            }
//            if (itemCampo.getNomeCampo().equals(posizioneAssistitoTicket.getNomeCampo())){ 
//            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
//            	lstRegole.addAll(lstRegole.size(), posizioneAssistitoTicket.getRegole());
//            }
            // REGOLE ANOMALIA
//            if (itemCampo.getNomeCampo().equals(importoCodiceEsenzione.getNomeCampo())){ 
//            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
//            	lstRegole.addAll(lstRegole.size(), importoCodiceEsenzione.getRegole());
//            }
            if (itemCampo.getNomeCampo().equals(assistitoDatiAnagraficiEtaNascitaAnno.getNomeCampo())){ 
            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
            	lstRegole.addAll(lstRegole.size(), assistitoDatiAnagraficiEtaNascitaAnno.getRegole());
            }
         // REGOLE ANOMALIA
//            if (itemCampo.getNomeCampo().equals(assistitoDatiAnagraficiEtaNascitaMese.getNomeCampo())){ 
//            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
//            	lstRegole.addAll(lstRegole.size(), assistitoDatiAnagraficiEtaNascitaMese.getRegole());
//            }
            if (itemCampo.getNomeCampo().equals(assistitoDatiAnagraficiEtaPresunta.getNomeCampo())){ 
            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
            	lstRegole.addAll(lstRegole.size(), assistitoDatiAnagraficiEtaPresunta.getRegole());
            }
            if (itemCampo.getNomeCampo().equals(assistitoPrestazioniDiagnosiDiagnosiPrincipale.getNomeCampo())){ 
            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
            	if (lstRegole == null)
            		lstRegole = new ArrayList<RegolaGenerica>();
            	            	
            	lstRegole.addAll(lstRegole.size(), assistitoPrestazioniDiagnosiDiagnosiPrincipale.getRegole());
            	itemCampo.setRegole(lstRegole);
            }
            if (itemCampo.getNomeCampo().equals(assistitoPrestazioniDiagnosiDiagnosiSecondaria.getNomeCampo())){ 
            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
            	if (lstRegole == null)
            		lstRegole = new ArrayList<RegolaGenerica>();
            	            	
            	lstRegole.addAll(lstRegole.size(), assistitoPrestazioniDiagnosiDiagnosiSecondaria.getRegole());
            	itemCampo.setRegole(lstRegole);
            }
            if (itemCampo.getNomeCampo().equals(assistitoPrestazionePrincipale.getNomeCampo())){ 
            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
            	if (lstRegole == null)
            		lstRegole = new ArrayList<RegolaGenerica>();
            	            	
            	lstRegole.addAll(lstRegole.size(), assistitoPrestazionePrincipale.getRegole());
            	itemCampo.setRegole(lstRegole);
            }
            if (itemCampo.getNomeCampo().equals(assistitoPrestazioneSecondaria.getNomeCampo())){ 
            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
            	if (lstRegole == null)
            		lstRegole = new ArrayList<RegolaGenerica>();
            	            	
            	lstRegole.addAll(lstRegole.size(), assistitoPrestazioneSecondaria.getRegole());
            	itemCampo.setRegole(lstRegole);
            }
            if (itemCampo.getNomeCampo().equals(assistitoOBIProblemaClinicoPrincipale.getNomeCampo())){ 
            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
            	if (lstRegole == null)
            		lstRegole = new ArrayList<RegolaGenerica>();
            	            	
            	lstRegole.addAll(lstRegole.size(), assistitoOBIProblemaClinicoPrincipale.getRegole());
            	itemCampo.setRegole(lstRegole);
            }
            if (itemCampo.getNomeCampo().equals(assistitoOBIDataIngresso.getNomeCampo())){ 
            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
            	if (lstRegole == null)
            		lstRegole = new ArrayList<RegolaGenerica>();
            	            	
            	lstRegole.addAll(lstRegole.size(), assistitoOBIDataIngresso.getRegole());
            	itemCampo.setRegole(lstRegole);
            }
            if (itemCampo.getNomeCampo().equals(assistitoOBIOraIngresso.getNomeCampo())){ 
            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
            	if (lstRegole == null)
            		lstRegole = new ArrayList<RegolaGenerica>();
            	            	
            	lstRegole.addAll(lstRegole.size(), assistitoOBIOraIngresso.getRegole());
            	itemCampo.setRegole(lstRegole);
            }
            if (itemCampo.getNomeCampo().equals(assistitoOBIPrimaConsulenzaSpecDis.getNomeCampo())){ 
            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
            	if (lstRegole == null)
            		lstRegole = new ArrayList<RegolaGenerica>();
            	            	
            	lstRegole.addAll(lstRegole.size(), assistitoOBIPrimaConsulenzaSpecDis.getRegole());
            	itemCampo.setRegole(lstRegole);
            }
            if (itemCampo.getNomeCampo().equals(assistitoOBIPrimaConsulenzaSpecNum.getNomeCampo())){ 
            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
            	if (lstRegole == null)
            		lstRegole = new ArrayList<RegolaGenerica>();
            	            	
            	lstRegole.addAll(lstRegole.size(), assistitoOBIPrimaConsulenzaSpecNum.getRegole());
            	itemCampo.setRegole(lstRegole);
            }
            if (itemCampo.getNomeCampo().equals(assistitoOBISecondaConsulenzaSpecDis.getNomeCampo())){ 
            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
            	if (lstRegole == null)
            		lstRegole = new ArrayList<RegolaGenerica>();
            	            	
            	lstRegole.addAll(lstRegole.size(), assistitoOBISecondaConsulenzaSpecDis.getRegole());
            	itemCampo.setRegole(lstRegole);
            }
            if (itemCampo.getNomeCampo().equals(assistitoOBISecondaConsulenzaSpecNum.getNomeCampo())){ 
            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
            	if (lstRegole == null)
            		lstRegole = new ArrayList<RegolaGenerica>();
            	            	
            	lstRegole.addAll(lstRegole.size(), assistitoOBISecondaConsulenzaSpecNum.getRegole());
            	itemCampo.setRegole(lstRegole);
            }
            if (itemCampo.getNomeCampo().equals(assistitoOBIEsito.getNomeCampo())){ 
            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
            	if (lstRegole == null)
            		lstRegole = new ArrayList<RegolaGenerica>();
            	            	
            	lstRegole.addAll(lstRegole.size(), assistitoOBIEsito.getRegole());
            	itemCampo.setRegole(lstRegole);
            }
            if (itemCampo.getNomeCampo().equals(assistitoOBIPrestazionePrincipaleErogata.getNomeCampo())){ 
            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
            	if (lstRegole == null)
            		lstRegole = new ArrayList<RegolaGenerica>();
            	            	
            	lstRegole.addAll(lstRegole.size(), assistitoOBIPrestazionePrincipaleErogata.getRegole());
            	itemCampo.setRegole(lstRegole);
            }
            if (itemCampo.getNomeCampo().equals(assistitoOBIPrestazioneSecondariaErogata.getNomeCampo())){ 
            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
            	if (lstRegole == null)
            		lstRegole = new ArrayList<RegolaGenerica>();
            	            	
            	lstRegole.addAll(lstRegole.size(), assistitoOBIPrestazioneSecondariaErogata.getRegole());
            	itemCampo.setRegole(lstRegole);
            }
            if (itemCampo.getNomeCampo().equals(assistitoOBIDiagnosiPrincipaleUscita.getNomeCampo())){ 
            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
            	if (lstRegole == null)
            		lstRegole = new ArrayList<RegolaGenerica>();
            	            	
            	lstRegole.addAll(lstRegole.size(), assistitoOBIDiagnosiPrincipaleUscita.getRegole());
            	itemCampo.setRegole(lstRegole);
            }
            if (itemCampo.getNomeCampo().equals(assistitoOBIDiagnosiSecondariaUscita.getNomeCampo())){ 
            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
            	if (lstRegole == null)
            		lstRegole = new ArrayList<RegolaGenerica>();
            	            	
            	lstRegole.addAll(lstRegole.size(), assistitoOBIDiagnosiSecondariaUscita.getRegole());
            	itemCampo.setRegole(lstRegole);
            }
            if (itemCampo.getNomeCampo().equals(assistitoOBIDataUscita.getNomeCampo())){ 
            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
            	if (lstRegole == null)
            		lstRegole = new ArrayList<RegolaGenerica>();
            	            	
            	lstRegole.addAll(lstRegole.size(), assistitoOBIDataUscita.getRegole());
            	itemCampo.setRegole(lstRegole);
            }
            if (itemCampo.getNomeCampo().equals(assistitoOBIOraUscita.getNomeCampo())){ 
            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
            	if (lstRegole == null)
            		lstRegole = new ArrayList<RegolaGenerica>();
            	            	
            	lstRegole.addAll(lstRegole.size(), assistitoOBIOraUscita.getRegole());
            	itemCampo.setRegole(lstRegole);
            }
         // REGOLE ANOMALIA
//            if (itemCampo.getNomeCampo().equals(assistitoCodiceIstituzioneTEAM.getNomeCampo())){ 
//            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
//            	if (lstRegole == null)
//            		lstRegole = new ArrayList<RegolaGenerica>();
//            	            	
//            	lstRegole.addAll(lstRegole.size(), assistitoCodiceIstituzioneTEAM.getRegole());
//            	itemCampo.setRegole(lstRegole);
//            }
         // REGOLE ANOMALIA
//            if (itemCampo.getNomeCampo().equals(assistitoDatiAnagraficiResidenzaASL.getNomeCampo())){ 
//            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
//            	if (lstRegole == null)
//            		lstRegole = new ArrayList<RegolaGenerica>();
//            	            	
//            	lstRegole.addAll(lstRegole.size(), assistitoDatiAnagraficiResidenzaASL.getRegole());
//            	itemCampo.setRegole(lstRegole);
//            }
            if (itemCampo.getNomeCampo().equals(assistitoDatiAnagraficiResidenzaComune.getNomeCampo())){ 
            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
            	if (lstRegole == null)
            		lstRegole = new ArrayList<RegolaGenerica>();
            	            	
            	lstRegole.addAll(lstRegole.size(), assistitoDatiAnagraficiResidenzaComune.getRegole());
            	itemCampo.setRegole(lstRegole);
            }
            if (itemCampo.getNomeCampo().equals(assistitoDatiAnagraficiResidenzaStatoEstero.getNomeCampo())){ 
            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
            	if (lstRegole == null)
            		lstRegole = new ArrayList<RegolaGenerica>();
            	            	
            	lstRegole.addAll(lstRegole.size(), assistitoDatiAnagraficiResidenzaStatoEstero.getRegole());
            	itemCampo.setRegole(lstRegole);
            }
            if (itemCampo.getNomeCampo().equals(assistitoDatiAnagraficiResidenzaRegione.getNomeCampo())){ 
            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
            	if (lstRegole == null)
            		lstRegole = new ArrayList<RegolaGenerica>();
            	            	
            	lstRegole.addAll(lstRegole.size(), assistitoDatiAnagraficiResidenzaRegione.getRegole());
            	itemCampo.setRegole(lstRegole);
            }
            
//            if (itemCampo.getNomeCampo().equals(assistitoCUNI.getNomeCampo())){ 
//            	List<RegolaGenerica>  lstRegole = itemCampo.getRegole();
//            	if (lstRegole == null)
//            		lstRegole = new ArrayList<RegolaGenerica>();
//            	            	
//            	lstRegole.addAll(lstRegole.size(), assistitoCUNI.getRegole());
//            	itemCampo.setRegole(lstRegole);
//            }
            
        }
        
        regoleFlusso =  RegoleFlusso.builder()
				.withCampi(campi)
				.build();
        
        return regoleFlusso;
	}
	
	
	private static Campo creaRegolaIstitutoProvenienza (List<RegolaGenerica>  regoleCampo, String nomeCampo, String codErr) {

		Parametri parametri = new Parametri();
		parametri.getParametriMap().put("size1", "6");
		parametri.getParametriMap().put("size2", "8");

		RegolaDueLunghezzeAlternative regolaDueLunghezzeAlternative = new RegolaDueLunghezzeAlternative(
				"RegolaDueLunghezzeAlternative", codErr, null, parametri);

		// regola adhoc
		// TODOs: FB - ANOMALIA - COMMENTATA
//		parametri = new Parametri();
//		RegolaEmurPSIstitutoProvenienza regolaEmurPSIstitutoProvenienza = new RegolaEmurPSIstitutoProvenienza(
//				"regolaEmurPSIstitutoProvenienza", null, null, parametri);

		//aggiungo regole
		regoleCampo.add(regolaDueLunghezzeAlternative);
//		regoleCampo.add(regolaEmurPSIstitutoProvenienza);
		// creo campo
		return new Campo(regoleCampo, nomeCampo);
				
	}
	
	private static Campo creaRegolaErogatoreCodIstituto (List<RegolaGenerica>  regoleCampo, String nomeCampo, String codErr) {

		Parametri parametri = new Parametri();
		parametri.getParametriMap().put("size1", "6");
		parametri.getParametriMap().put("size2", "8");

		RegolaDueLunghezzeAlternative regolaDueLunghezzeAlternative = new RegolaDueLunghezzeAlternative(
				"RegolaDueLunghezzeAlternative", codErr, null, parametri);

		// regola adhoc
		parametri = new Parametri();
		RegolaEmurPSErogatoreCodIst regolaEmurPSErogatoreCodIst = new RegolaEmurPSErogatoreCodIst(
				"regolaEmurPSErogatoreCodIst", null, null, parametri);

		//aggiungo regole
		regoleCampo.add(regolaDueLunghezzeAlternative);
		regoleCampo.add(regolaEmurPSErogatoreCodIst);
		// creo campo
		return new Campo(regoleCampo, nomeCampo);
				
	}
	
	
	
//	private static Campo creaRegolaIncongruenzaTrauma (List<RegolaGenerica>  regoleCampo, String nomeCampo) {
//
//		Parametri parametri = new Parametri();
//		// prima regola		
//		parametri.getParametriMap().put("parametroCampoCondizionante", "10");
//		parametri.getParametriMap().put("nomeCampoCondizionante", "problemaPrincipale");
//
//		RegolaIncongruenzaCrossDueCampi regolaIncongruenzaCrossDueCampi = new RegolaIncongruenzaCrossDueCampi(
//				"regolaIncongruenzaCrossDueCampi", "2065", null, parametri);
//
//		 parametri = new Parametri();
//			// prima regola
//			parametri.getParametriMap().put("parametroCampoCondizionante", "10");
//			parametri.getParametriMap().put("nomeCampoCondizionante", "problemaPrincipale");
//
//			RegolaEmurPSTrauma regolaEmurPSTrauma = new RegolaEmurPSTrauma(
//					"regolaEmurPSTrauma", "1103", "Non appartenenza al dominio di riferimento per un campo ad obbligatorietà condizionata", parametri);
//			
//		// aggiungo regole
//		regoleCampo.add(regolaIncongruenzaCrossDueCampi);
//		regoleCampo.add(regolaEmurPSTrauma);
//		// creo campo
//		return new Campo(regoleCampo, nomeCampo);
//	
//	}
	
	
	private static Campo creaRegolaCustomEntrataData (List<RegolaGenerica>  regoleCampo, String nomeCampo) {

		Parametri parametri = new Parametri();
		parametri.getParametriMap().put("campoDipendente", "Identificativo");

		RegolaDiversitaAnnoCrossCampoFacoltativo regolaDiversitaAnnoCrossCampoFacoltativo = new RegolaDiversitaAnnoCrossCampoFacoltativo(
				"regolaDiversitaAnnoCrossCampoFacoltativo", "2058", null, parametri);

		/*
		 * Se la “data di dimissione” è valorizzata, la data di entrata  dovrà essere
		 * compresa nel periodo di riferimento delle informazioni o nel periodo di
		 * riferimento delle informazioni -1 mese.
		 * 
		 * codErr: 2057
		 */
		parametri = new Parametri();
		RegolaEmurPSDataEntrata regolaEmurPSDataEntrata = new RegolaEmurPSDataEntrata("regolaEmurPSDataEntrata", null,
				null, parametri);
		
		//aggiungo regole
		regoleCampo.add(regolaDiversitaAnnoCrossCampoFacoltativo);
		regoleCampo.add(regolaEmurPSDataEntrata);
		
		// creo campo
		return new Campo(regoleCampo, nomeCampo);
				
	}
	
	
	private static Campo creaRegolaCentraleOpIdentificativo (List<RegolaGenerica>  regoleCampo, String nomeCampo) {

		// TODOs: FB - ANOMALIA - COMMENTATA
//		// prima regola
//		Parametri parametri = new Parametri();
//		parametri.getParametriMap().put("operatoreLogico", "and");
//
//		parametri.getParametriMap().put("parametroCampoCondizionante", "1|4|6");
//		parametri.getParametriMap().put("nomeCampoCondizionante", "modalitaArrivo");
//
//		parametri.getParametriMap().put("parametroCampoCondizionante2", "5");
//		parametri.getParametriMap().put("nomeCampoCondizionante2", "responsabileInvio");
//
//		RegolaObbligatorietaCondizionataMultiCampo regolaObbligatorietaCondizionataMultiCampo = new RegolaObbligatorietaCondizionataMultiCampo(
//				"regolaObbligatorietaCondizionataMultiCampo", "1103", "Il campo è obbligatorio", parametri);

		// TODOs: FB - ANOMALIA - COMMENTATA
//		Parametri parametri = new Parametri();
//		parametri.getParametriMap().put("operatoreLogico", "or");
//
//		parametri.getParametriMap().put("parametroCampoCondizionante", "1|4|6");
//		parametri.getParametriMap().put("nomeCampoCondizionante", "modalitaArrivo");
//
//		parametri.getParametriMap().put("parametroCampoCondizionante2", "5");
//		parametri.getParametriMap().put("nomeCampoCondizionante2", "responsabileInvio");
//		RegolaIncongruenzaCrossDueCampi regolaIncongruenzaCrossDueCampi = new RegolaIncongruenzaCrossDueCampi(
//				"regolaIncongruenzaCrossDueCampi", "2060", null, parametri);

		// TERZA REGOLA
		Parametri parametri = new Parametri();
		parametri.getParametriMap().put("nomeTabella", "ANAG_EMUR_REGIONI");

		RegolaAnagFacoltativoRegPrimiCaratteriCustom regolaAnagFacoltativoRegPrimiCaratteriCustom = new RegolaAnagFacoltativoRegPrimiCaratteriCustom(
				"regolaDominioRegioniPrimiCaratteriCustom", "2032",
				"La regione della CO non appartiene alla tabella di riferimento", parametri);

		// aggiungo regole
//		regoleCampo.add(regolaObbligatorietaCondizionataMultiCampo);
//		regoleCampo.add(regolaIncongruenzaCrossDueCampi);
		regoleCampo.add(regolaAnagFacoltativoRegPrimiCaratteriCustom);

		// creo campo
		return new Campo(regoleCampo, nomeCampo);
				
	}
	
	private static Campo creaRegolaCentraleOperativaCodiceMissione (List<RegolaGenerica>  regoleCampo, String nomeCampo) {

		// regola zero
		// TODOs: FB - ANOMALIA - COMMENTATA
//		Parametri parametri = new Parametri();
//		parametri.getParametriMap().put("parametroCampoCondizionante", "1|4|6");
//		parametri.getParametriMap().put("nomeCampoCondizionante", "modalitaArrivo");
//
//		parametri.getParametriMap().put("parametroCampoCondizionante2", "5");
//		parametri.getParametriMap().put("nomeCampoCondizionante2", "responsabileInvio");
//
//		RegolaObbligatorietaCondizionataMultiCampo regolaObbligatorietaCondiz = new RegolaObbligatorietaCondizionataMultiCampo(
//				"regolaObbligatorietaCondizionataMultiCampo", "1103", "Il campo è obbligatorio", parametri);

		// TODOs: FB - ANOMALIA - COMMENTATA
//		// prima regola
//		parametri = new Parametri();
//		parametri.getParametriMap().put("parametroCampoCondizionante", "1|4|6");
//		parametri.getParametriMap().put("nomeCampoCondizionante", "modalitaArrivo");
//
//		RegolaIncongruenzaCrossDueCampi regolaIncongruenzaCrossDueCampi = new RegolaIncongruenzaCrossDueCampi(
//				"regolaIncongruenzaCrossDueCampi", "2061", null, parametri);

		// seconda regola
		Parametri parametri = new Parametri();
		parametri.getParametriMap().put("parametroCampoCondizionante", "5");
		parametri.getParametriMap().put("nomeCampoCondizionante", "responsabileInvio");

		RegolaIncongruenzaCrossDueCampi regolaIncongruenzaCrossDueCampi2 = new RegolaIncongruenzaCrossDueCampi(
				"regolaIncongruenzaCrossDueCampi", "2062", null, parametri);

		// terza regola
		parametri = new Parametri();
		parametri.getParametriMap().put("valoreSuccessivo", "true");
		parametri.getParametriMap().put("campoDipendente", "entrataData");

		RegolaDiversitaAnnoCrossCampoFacoltativo regolaControlloAnnoEAnnoSuccessivo = new RegolaDiversitaAnnoCrossCampoFacoltativo(
				"RegolaDiversitaAnnoCrossCampoFacoltativo", "2063", null, parametri);

		// quarta regola
		parametri = new Parametri();
		parametri.getParametriMap().put("valoreSuccessivo", "true");
		parametri.getParametriMap().put("campoDipendente", "Identificativo");

		RegolaDiversitaAnnoCrossCampoFacoltativo regolaControlloAnnoEAnnoSuccessivoIdentificativo = new RegolaDiversitaAnnoCrossCampoFacoltativo(
				"RegolaDiversitaAnnoCrossCampoFacoltativo", "2107", null, parametri);

		// quinta regola
		parametri = new Parametri();
		parametri.getParametriMap().put("valoreSuccessivo", "false");
		parametri.getParametriMap().put("campoDipendente", "entrataData");

		RegolaDiversitaAnnoCrossCampoFacoltativo regolaControlloAnnoEntrataData = new RegolaDiversitaAnnoCrossCampoFacoltativo(
				"RegolaDiversitaAnnoCrossCampoFacoltativo", "2108", null, parametri);
				
		//aggiungo regole
//			regoleCampo.add(regolaObbligatorietaCondiz);
//			regoleCampo.add(regolaIncongruenzaCrossDueCampi);
			regoleCampo.add(regolaIncongruenzaCrossDueCampi2);
			regoleCampo.add(regolaControlloAnnoEAnnoSuccessivo);
			regoleCampo.add(regolaControlloAnnoEAnnoSuccessivoIdentificativo);
			regoleCampo.add(regolaControlloAnnoEntrataData);
			
		// creo campo
		return new Campo(regoleCampo, nomeCampo);
				
	}
	
	private static Campo creaRegolaPresaInCaricoData (List<RegolaGenerica>  regoleCampo, String nomeCampo) {

		// prima regola
		Parametri parametri = new Parametri();
		parametri.getParametriMap().put("parametroCampoCondizionante","6");
		parametri.getParametriMap().put("nomeCampoCondizionante","assistitoDimissioneEsitoTrattamento");
		
		RegolaObbligatorietaCondizionataMultiCampoDiverso regolaObbligatorietaCondizionataMultiCampoDiverso = new RegolaObbligatorietaCondizionataMultiCampoDiverso("regolaObbligatorietaCondizionataMultiCampoDiverso", "1103", "Il campo è obbligatorio", parametri);

		// seconda regola
		parametri = new Parametri();
		parametri.getParametriMap().put("startDate", "assistitoPrestazioniPresaInCaricoData");
		parametri.getParametriMap().put("startOra", "assistitoPrestazioniPresaInCaricoOra");

		parametri.getParametriMap().put("endDate", "entrataData");
		parametri.getParametriMap().put("endOra", "entrataOra");

		RegolaDataEntro24OreAltraData regolaDataEntro24OreAltraData = new RegolaDataEntro24OreAltraData(
				"regolaDataEntro24OreAltraData", "2066", null, parametri);
		
		// terza regola
		parametri = new Parametri();
		parametri.getParametriMap().put("parametroCampoCondizionante","6");
		parametri.getParametriMap().put("nomeCampoCondizionante","assistitoDimissioneEsitoTrattamento");
		
		RegolaIncongruenzaCrossDueCampiUguaglianza regolaIncoerenzaCrossCampoUguaglianza = new RegolaIncongruenzaCrossDueCampiUguaglianza("regolaIncongruenzaCrossDueCampiUguaglianza", "2112",null,parametri);

		
		
		//aggiungo regole
		regoleCampo.add(regolaObbligatorietaCondizionataMultiCampoDiverso);
		regoleCampo.add(regolaIncoerenzaCrossCampoUguaglianza);
		regoleCampo.add(regolaDataEntro24OreAltraData);
		// creo campo
		return new Campo(regoleCampo, nomeCampo);
				
	}
	
	private static Campo creaRegolaPresaInCaricoOra (List<RegolaGenerica>  regoleCampo, String nomeCampo) {

		// prima regola
		Parametri parametri = new Parametri();
		parametri.getParametriMap().put("parametroCampoCondizionante","6");
		parametri.getParametriMap().put("nomeCampoCondizionante","assistitoDimissioneEsitoTrattamento");
		
		RegolaObbligatorietaCondizionataMultiCampoDiverso regolaObbligatorietaCondizionataMultiCampoDiverso = new RegolaObbligatorietaCondizionataMultiCampoDiverso("regolaObbligatorietaCondizionataMultiCampoDiverso", "1103", "Il campo è obbligatorio", parametri);

		
		// seconda regola
		parametri = new Parametri();
		parametri.getParametriMap().put("parametroCampoCondizionante","6");
		parametri.getParametriMap().put("nomeCampoCondizionante","assistitoDimissioneEsitoTrattamento");
		
		RegolaIncongruenzaCrossDueCampiUguaglianza regolaIncongruenzaCrossDueCampiUguaglianza = new RegolaIncongruenzaCrossDueCampiUguaglianza("regolaIncongruenzaCrossDueCampiUguaglianza", "2113", "Incongruenza tra Esito Trattamento e Ora Presa in carico", parametri);

		
		//aggiungo regole
		regoleCampo.add(regolaObbligatorietaCondizionataMultiCampoDiverso);
		regoleCampo.add(regolaIncongruenzaCrossDueCampiUguaglianza);
		// creo campo
		return new Campo(regoleCampo, nomeCampo);
				
	}

	
	private static Campo creaRegolaEsitoTrattamentoCrossCampo (List<RegolaGenerica>  regoleCampo, String nomeCampo) {

		// prima regola
//		Parametri parametri = new Parametri();
//		parametri.getParametriMap().put("valoreConfronto", "9");
//		parametri.getParametriMap().put("nomeCampoCoerente", "triage");
//		parametri.getParametriMap().put("listaValoriCoerenti", "N");
//
//		RegolaIncoerenzaCrossCampoUguaglianzaCustom regolaIncoerenzaCrossCampoTriage = new RegolaIncoerenzaCrossCampoUguaglianzaCustom(
//				"RegolaIncoerenzaCrossCampoUguaglianza", "2067", null, parametri);
//
//		// seconda regola
//		parametri = new Parametri();
//		parametri.getParametriMap().put("valoreConfronto", "9");
//		parametri.getParametriMap().put("nomeCampoCoerente", "assistitoDimissioneLivelloAppropriatezzaAccesso");
//		parametri.getParametriMap().put("listaValoriCoerenti", "N");
//
//		RegolaIncoerenzaCrossCampoUguaglianzaCustom regolaIncoerenzaCrossCampoLivAppAccesso = new RegolaIncoerenzaCrossCampoUguaglianzaCustom(
//				"RegolaIncoerenzaCrossCampoUguaglianza", "2078", null, parametri);

		// prima regola
		Parametri parametri = new Parametri();		
		RegolaEsitoTrattamento RegolaEsitoTrattamento = new RegolaEsitoTrattamento(
				"RegolaEsitoTrattamento", null, null, parametri);
		
		
		// aggiungo regole
		regoleCampo.add(RegolaEsitoTrattamento);		
		// creo campo
		return new Campo(regoleCampo, nomeCampo);
				
	}
	
	private static Campo creaRegolaDimissioneDataDest (List<RegolaGenerica>  regoleCampo, String nomeCampo) {

		// prima regola
		// TODOs: FB - ANOMALIA - COMMENTATA
//		Parametri parametri = new Parametri();
//		parametri.getParametriMap().put("parametroCampoCondizionante", "1|2|3|10|11");
//		parametri.getParametriMap().put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");
//
//		RegolaObbligatorietaCondizionataMultiCampo regolaObbligatorietaCondizionataMultiCampo = new RegolaObbligatorietaCondizionataMultiCampo(
//				"regolaObbligatorietaCondizionataMultiCampo", "1103", "Il campo è obbligatorio", parametri);

		
		Parametri parametri = new Parametri();
		RegolaEmurPSDataDest regolaEmurPSDataDest = new RegolaEmurPSDataDest(
				"regolaEmurPSDataDest", null, null, parametri);

		
		// aggiungo regole
//		regoleCampo.add(regolaObbligatorietaCondizionataMultiCampo);
		regoleCampo.add(regolaEmurPSDataDest);
		// creo campo
		return new Campo(regoleCampo, nomeCampo);
		
	}
	
	private static Campo creaRegolaDimissioneOraDest (List<RegolaGenerica>  regoleCampo, String nomeCampo) {

				
		// regola adhoc - algoritmo plsql
		Parametri parametri = new Parametri();
		RegolaEmurPSDimissioneOraDest regolaEmurPSDimissioneOraDest = new RegolaEmurPSDimissioneOraDest(
				"regolaEmurPSDimissioneOraDest", null, null, parametri);
		
		
		//aggiungo regole
		regoleCampo.add(regolaEmurPSDimissioneOraDest);
		// creo campo
		return new Campo(regoleCampo, nomeCampo);
				
	}
	
	private static Campo creaRegolaDimissioneData (List<RegolaGenerica>  regoleCampo, String nomeCampo) {

		// prima regola
		Parametri parametri = new Parametri();
		parametri.getParametriMap().put("parametroCampoCondizionante", "6|7");
		parametri.getParametriMap().put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");

		RegolaObbligatorietaCondizionataMultiCampoDiverso regolaObbligatorietaCondizionataMultiCampoDiverso = new RegolaObbligatorietaCondizionataMultiCampoDiverso(
				"regolaObbligatorietaCondizionataMultiCampoDiverso", "1103", "Il campo è obbligatorio", parametri);

		// regola condizionata
		parametri = new Parametri();
		RegolaEmurPSDataDimissione regolaEmurPSDataDimissione = new RegolaEmurPSDataDimissione(
				"regolaEmurPSDataDimissione", null, null, parametri);

//		//aggiungo regole
		regoleCampo.add(regolaObbligatorietaCondizionataMultiCampoDiverso);
		regoleCampo.add(regolaEmurPSDataDimissione);
			//aggiungo regole
		
		// creo campo
		return new Campo(regoleCampo, nomeCampo);
				
	}
	

	private static Campo creaRegolaImportoLordo (List<RegolaGenerica>  regoleCampo, String nomeCampo) {

		// prima regola
		Parametri parametri = new Parametri();
		parametri.getParametriMap().put("parametroCampoCondizionante","6|7");
		parametri.getParametriMap().put("nomeCampoCondizionante","assistitoDimissioneEsitoTrattamento");
		
		RegolaObbligatorietaCondizionataMultiCampoDiverso regolaObbligatorietaCondizionataMultiCampoDiverso = new RegolaObbligatorietaCondizionataMultiCampoDiverso("regolaObbligatorietaCondizionataMultiCampoDiverso", "1103", "Il campo è obbligatorio", parametri);

		
		// seconda regola
		parametri = new Parametri();
		parametri.getParametriMap().put("parametroCampoCondizionante","6|7");
		parametri.getParametriMap().put("nomeCampoCondizionante","assistitoDimissioneEsitoTrattamento");
		
		RegolaIncongruenzaCrossDueCampiUguaglianza regolaObbligatorietaCondizionataMultiCampo = new RegolaIncongruenzaCrossDueCampiUguaglianza("regolaIncongruenzaCrossDueCampiUguaglianza", "1118",null,parametri);

		
		//aggiungo regole
		regoleCampo.add(regolaObbligatorietaCondizionataMultiCampoDiverso);
		regoleCampo.add(regolaObbligatorietaCondizionataMultiCampo);
		// creo campo
		return new Campo(regoleCampo, nomeCampo);
				
	}

	private static Campo creaRegolaDimissioneOra (List<RegolaGenerica>  regoleCampo, String nomeCampo) {

		// prima regola
		// TODOs: FB - ANOMALIA - COMMENTATA
//		Parametri parametri = new Parametri();
//		parametri.getParametriMap().put("parametroCampoCondizionante", "6|7");
//		parametri.getParametriMap().put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");
//
//		RegolaObbligatorietaCondizionataMultiCampoDiverso regolaObbligatorietaCondizionataMultiCampoDiverso = new RegolaObbligatorietaCondizionataMultiCampoDiverso(
//				"regolaObbligatorietaCondizionataMultiCampoDiverso", "1103", "Il campo è obbligatorio", parametri);

		// seconda regola
		Parametri parametri = new Parametri();
		parametri.getParametriMap().put("parametroCampoCondizionante", "6|7");
		parametri.getParametriMap().put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");
		
		RegolaIncongruenzaCrossDueCampiUguaglianza regolaIncongruenzaCrossDueCampiUguaglianza = new RegolaIncongruenzaCrossDueCampiUguaglianza(
				"regolaIncongruenzaCrossDueCampiUguaglianza", "2083", null, parametri);

//		// regola anagrafica adhoc
		// TODOs: FB - ANOMALIA - COMMENTATA
//		parametri = new Parametri();
//		parametri.getParametriMap().put("parametroCampoCondizionante", "1|2|3|4|5|6|7|8|9|10|11");
//		parametri.getParametriMap().put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");
//
//		RegolaIncongruenzaCrossDueCampi regolaIncongruenzaCrossDueCampi2 = new RegolaIncongruenzaCrossDueCampi(
//				"regolaIncongruenzaCrossDueCampi", "2084", null, parametri);
		
		//aggiungo regole
//		regoleCampo.add(regolaObbligatorietaCondizionataMultiCampoDiverso);
		regoleCampo.add(regolaIncongruenzaCrossDueCampiUguaglianza);
//		regoleCampo.add(regolaIncongruenzaCrossDueCampi2);
		// creo campo
		return new Campo(regoleCampo, nomeCampo);
				
	}
	
	private static Campo creaRegolaLivelloAppropriatezzaAccesso (List<RegolaGenerica>  regoleCampo, String nomeCampo) {

		// CodErr - 2086 - 1103
		Parametri parametri = new Parametri();		
		RegolaEmurPSLivelloAppropriatezza regolaEmurPSLivelloAppropriatezza = new RegolaEmurPSLivelloAppropriatezza(
				"regolaEmurPSLivelloAppropriatezza", null, null, parametri);
		
		//aggiungo regole
		regoleCampo.add(regolaEmurPSLivelloAppropriatezza);
		// creo campo
		return new Campo(regoleCampo, nomeCampo);
				
	}

	private static Campo creaRegolaObbligatorioSeEsitoDueRicProgress (List<RegolaGenerica>  regoleCampo, String nomeCampo) {

		// primo controllo
		// TODOs: FB - ANOMALIA - COMMENTATA
//		Parametri parametri = new Parametri();
//		parametri.getParametriMap().put("parametroCampoCondizionante", "2");
//		parametri.getParametriMap().put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");
//
//		RegolaObbligatorietaCondizionataMultiCampo regolaObbligatorietaCondizionataMultiCampo = new RegolaObbligatorietaCondizionataMultiCampo(
//				"regolaObbligatorietaCondizionataMultiCampo", "1103", "Il campo è obbligatorio", parametri);

		// seconda regola -
		// TODOs: FB - ANOMALIA - COMMENTATA
//		parametri = new Parametri();
//		parametri.getParametriMap().put("parametroCampoCondizionante", "2");
//		parametri.getParametriMap().put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");
//
//		RegolaIncongruenzaCrossDueCampi regolaIncongruenzaCrossDueCampi = new RegolaIncongruenzaCrossDueCampi(
//				"regolaIncongruenzaCrossDueCampi", "2070", "Incongruenza tra progressivo SDO e esito trattamento", parametri);
		
		
		// regola adhoc
		Parametri parametri = new Parametri();
		RegolaEmurPSRicoveroProgressivo regolaEmurPSRicoveroProgressivo = new RegolaEmurPSRicoveroProgressivo(
				"regolaEmurPSRicoveroProgressivo", null, null, parametri);
		
		
		//aggiungo regole
//		regoleCampo.add(regolaObbligatorietaCondizionataMultiCampo);
//		regoleCampo.add(regolaIncongruenzaCrossDueCampi);
		regoleCampo.add(regolaEmurPSRicoveroProgressivo);
		// creo campo
		return new Campo(regoleCampo, nomeCampo);
				
	}
	
	
	
	private static Campo creaRegolaTrasferimentoMotivo (List<RegolaGenerica>  regoleCampo, String nomeCampo) {

		Parametri parametri = new Parametri();

		parametri.getParametriMap().put("operatoreLogico", "OR");

		parametri.getParametriMap().put("parametroCampoCondizionante", "3");
		parametri.getParametriMap().put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");

		parametri.getParametriMap().put("parametroCampoCondizionante2", "3");
		parametri.getParametriMap().put("nomeCampoCondizionante2", "assistitoOBIEsito");

		RegolaObbligatorietaCondizionataMultiCampo regolaObbligatorietaCondizionataMultiCampo = new RegolaObbligatorietaCondizionataMultiCampo(
				"regolaObbligatorietaCondizionataMultiCampo", "1103", "Il campo è obbligatorio", parametri);
		
		
		// 
		parametri = new Parametri();

		parametri.getParametriMap().put("operatoreLogico", "and");

		parametri.getParametriMap().put("parametroCampoCondizionante", "3");
		parametri.getParametriMap().put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");

		parametri.getParametriMap().put("parametroCampoCondizionante2", "3");
		parametri.getParametriMap().put("nomeCampoCondizionante2", "assistitoOBIEsito");

		RegolaIncongruenzaCrossDueCampi regolaIncongruenzaCrossDueCampi = new RegolaIncongruenzaCrossDueCampi(
				"regolaIncongruenzaCrossDueCampi", "2072", "Il motivo trasferimento non è congruente con l'esito trattamento", parametri);
		
		
		//aggiungo regole
		regoleCampo.add(regolaObbligatorietaCondizionataMultiCampo);
		regoleCampo.add(regolaIncongruenzaCrossDueCampi);
		// creo campo
		return new Campo(regoleCampo, nomeCampo);
				
	}
	
	private static Campo creaRegolaTrasferimentoIstituto (List<RegolaGenerica>  regoleCampo, String nomeCampo) {

		// primo controllo
		Parametri parametri = new Parametri();
		parametri.getParametriMap().put("operatoreLogico", "OR");
		parametri.getParametriMap().put("parametroCampoCondizionante", "3");
		parametri.getParametriMap().put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");
		parametri.getParametriMap().put("parametroCampoCondizionante2", "3");
		parametri.getParametriMap().put("nomeCampoCondizionante2", "assistitoOBIEsito");

		RegolaObbligatorietaCondizionataMultiCampo regolaObbligatorietaCondizionataMultiCampo = new RegolaObbligatorietaCondizionataMultiCampo(
				"regolaObbligatorietaCondizionataMultiCampo", "1103", "Il campo è obbligatorio", parametri);

		// secondo controllo
		// TODOs: FB - ANOMALIA - COMMENTATA
//		parametri = new Parametri();
//		parametri.getParametriMap().put("operatoreLogico", "AND");
//		parametri.getParametriMap().put("parametroCampoCondizionante", "3");
//		parametri.getParametriMap().put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");
//		parametri.getParametriMap().put("parametroCampoCondizionante2", "3");
//		parametri.getParametriMap().put("nomeCampoCondizionante2", "assistitoOBIEsito");
//
//		RegolaIncongruenzaCrossDueCampi regolaIncongruenzaCrossDueCampi = new RegolaIncongruenzaCrossDueCampi(
//				"regolaIncongruenzaCrossDueCampi", "2073", null, parametri);

		// terza regola
		parametri = new Parametri();
		parametri.getParametriMap().put("size1", "6");
		parametri.getParametriMap().put("size2", "8");

		RegolaDueLunghezzeAlternativeFacoltativo regolaDueLunghezzeAlternativeFacoltativo = new RegolaDueLunghezzeAlternativeFacoltativo(
				"regolaDueLunghezzeAlternativeFacoltativo", "103", null, parametri);

		// regola adhoc
		// TODOs: FB - ANOMALIA - COMMENTATA - 1301
//		parametri = new Parametri();
//		RegolaEmurPSIstitutoTrasferimento regolaEmurPSIstitutoTrasferimento = new RegolaEmurPSIstitutoTrasferimento(
//				"regolaEmurPSIstitutoTrasferimento", null, null, parametri);
		
		
		//aggiungo regole
		regoleCampo.add(regolaObbligatorietaCondizionataMultiCampo);
//		regoleCampo.add(regolaIncongruenzaCrossDueCampi);
		regoleCampo.add(regolaDueLunghezzeAlternativeFacoltativo);
//		regoleCampo.add(regolaEmurPSIstitutoTrasferimento);
		// creo campo
		return new Campo(regoleCampo, nomeCampo);
				
	}
	
	
    private static Campo creaRegolaObbligatorioPosAssistitoUno (List<RegolaGenerica>  regoleCampo, String nomeCampo) {

		Parametri parametri = new Parametri();

		parametri.getParametriMap().put("parametroCampoCondizionante", "1");
		parametri.getParametriMap().put("nomeCampoCondizionante", "importoPosizioneAssistitoTicket");

		RegolaObbligatorietaCondizionataMultiCampo regolaObbligatorietaCondizionataMultiCampo = new RegolaObbligatorietaCondizionataMultiCampo(
				"regolaObbligatorietaCondizionataMultiCampo", "1103", "Il campo è obbligatorio", parametri);

		// seconda regola
		parametri = new Parametri();
		parametri.getParametriMap().put("parametroCampoCondizionante", "1");
		parametri.getParametriMap().put("nomeCampoCondizionante", "importoPosizioneAssistitoTicket");

		RegolaIncongruenzaCrossDueCampi regolaIncongruenzaCrossDueCampi = new RegolaIncongruenzaCrossDueCampi(
				"regolaIncongruenzaCrossDueCampi", "1120", null, parametri);

		//aggiungo regole
		regoleCampo.add(regolaObbligatorietaCondizionataMultiCampo);
		regoleCampo.add(regolaIncongruenzaCrossDueCampi);
		// creo campo
		return new Campo(regoleCampo, nomeCampo);
				
	}

    private static Campo creaRegolaDatiAnagraficiEtaNascitaAnno (List<RegolaGenerica>  regoleCampo, String nomeCampo) {
    	
		// prima regola
		Parametri parametri = new Parametri();
		parametri.getParametriMap().put("nomeCampoCondizionante", "assistitoDatiAnagraficiEtaPresunta");

		RegolaObbligatorietaCondizionataValoreCampoVuoto regolaObbligatorietaCondizionataValoreCampoVuoto = new RegolaObbligatorietaCondizionataValoreCampoVuoto(
				"regolaObbligatorietaCondizionataValoreCampoVuoto", "1103",
				"Mancata valorizzazione di un campo ad obbligatorietà condizionata", parametri);

		// seconda regola
		parametri = new Parametri();
		parametri.getParametriMap().put("nomeCampoCoerente", "assistitoDatiAnagraficiEtaPresunta");

		RegolaIncoerenzaAlternanzaCrossCampo regolaIncoerenzaAlternanzaCrossCampo = new RegolaIncoerenzaAlternanzaCrossCampo(
				"regolaIncoerenzaAlternanzaCrossCampo", "20061",
				"Incongruenza tra anno nascita assistito e età presunta: non possono essere valorizzate entrambe",
				parametri);

		// terza regola
		RegolaObbligatorietaAlternataCrossCampo regolaObbligatorietaAlternataCrossCampo = new RegolaObbligatorietaAlternataCrossCampo(
				"regolaObbligatorietaAlternataCrossCampo", "20062", "Anno Nascita e Età Presunta entrambe nulle",
				parametri);

		// TODOs: FB - ANOMALIA - COMMENTATA
//		parametri = new Parametri();
//		parametri.getParametriMap().put("nomeCampoValidatore", "entrataData");
//		String desErrore = "L'anno di nascita non può essere superiore alla data di arrivo in PS";
//		RegolaAnnoPosterioreCrossCampo regolaAnnoPosterioreCrossCampo = new RegolaAnnoPosterioreCrossCampo(
//				"RegolaAnnoPosterioreCrossCampo", "3005", desErrore, parametri);

		// aggiungo regole
		regoleCampo.add(regolaObbligatorietaCondizionataValoreCampoVuoto);
		regoleCampo.add(regolaIncoerenzaAlternanzaCrossCampo);
		regoleCampo.add(regolaObbligatorietaAlternataCrossCampo);
//		regoleCampo.add(regolaAnnoPosterioreCrossCampo);
//		regoleCampo.add(regolaIncongruenzaCrossDueCampiUguaglianza);
		// creo campo
		return new Campo(regoleCampo, nomeCampo);
	
	}

	private static Campo creaRegolaDatiAnagraficiEtaPresunta(List<RegolaGenerica> regoleCampo, String nomeCampo) {
		// prima regola
		Parametri parametri = new Parametri();
		parametri.getParametriMap().put("nomeCampoCondizionante", "assistitoDatiAnagraficiEtaNascitaAnno");
		RegolaObbligatorietaCondizionataValoreCampoVuoto regolaObbligatorietaCondizionataValoreCampoVuoto = new RegolaObbligatorietaCondizionataValoreCampoVuoto(
				"regolaObbligatorietaCondizionataValoreCampoVuoto", "1103",
				"Mancata valorizzazione di un campo ad obbligatorietà condizionata", parametri);

		// seconda regola
		parametri = new Parametri();
		parametri.getParametriMap().put("nomeCampoCoerente", "assistitoDatiAnagraficiEtaNascitaAnno");		
		RegolaIncoerenzaAlternanzaCrossCampo regolaIncoerenzaAlternanzaCrossCampo = new RegolaIncoerenzaAlternanzaCrossCampo(
				"regolaIncoerenzaAlternanzaCrossCampo", "20061",
				"Incongruenza tra anno nascita assistito e età presunta: non possono essere valorizzate entrambe.", parametri);
		
		// aggiungo regole
		regoleCampo.add(regolaObbligatorietaCondizionataValoreCampoVuoto);
		regoleCampo.add(regolaIncoerenzaAlternanzaCrossCampo);
		// creo campo
		return new Campo(regoleCampo, nomeCampo);

	}
	
	private static Campo creaRegolaPrestazioniDiagnosiDiagnosiPrincipale(List<RegolaGenerica> regoleCampo,
			String nomeCampo) {
		// prima regola
		Parametri parametri = new Parametri();
		parametri.getParametriMap().put("minLunghezza", "3");
		parametri.getParametriMap().put("maxLunghezza", "6");
		parametri.getParametriMap().put("facoltativo", "true");

		RegolaMinMaxLunghezzaCampoFacoltativo regolaMinMaxLunghezzaCampoFacoltativo = new RegolaMinMaxLunghezzaCampoFacoltativo(
				"regolaMinMaxLunghezzaCampoFacoltativo", "106",
				"Lunghezza non conforme a quella attesa per un campo ad obbligatorietà condizionata", parametri);

		// seconda regola
		// TODOs: FB - ANOMALIA - COMMENTATA
//		parametri = new Parametri();
//		parametri.getParametriMap().put("parametroCampoCondizionante", "6|7");
//		parametri.getParametriMap().put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");
//
//		RegolaObbligatorietaCondizionataMultiCampoDiverso regolaObbligatorietaCondizionataMultiCampoDiverso = new RegolaObbligatorietaCondizionataMultiCampoDiverso(
//				"regolaObbligatorietaCondizionataMultiCampoDiverso", "1103",
//				"Mancata valorizzazione di un campo ad obbligatorietà condizionata", parametri);

		// terza regola
		// TODOs: FB - ANOMALIA - COMMENTATA
//		parametri = new Parametri();
//		parametri.getParametriMap().put("parametroCampoCondizionante", "6|7");
//		parametri.getParametriMap().put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");
//
//		RegolaIncongruenzaCrossDueCampiUguaglianza regolaIncoerenzaCrossCampoUguaglianza = new RegolaIncongruenzaCrossDueCampiUguaglianza(
//				"regolaIncongruenzaCrossDueCampiUguaglianza", "2101", null, parametri);

		// aggiungo regole
		regoleCampo.add(regolaMinMaxLunghezzaCampoFacoltativo);
//		regoleCampo.add(regolaObbligatorietaCondizionataMultiCampoDiverso);
//		regoleCampo.add(regolaIncoerenzaCrossCampoUguaglianza);
		// creo campo
		return new Campo(regoleCampo, nomeCampo);

	}
	
	private static Campo creaRegolaPrestazioniDiagnosiDiagnosiSecondaria(List<RegolaGenerica> regoleCampo, String nomeCampo) {
		// prima regola
		Parametri parametri = new Parametri();
		parametri.getParametriMap().put("minLunghezza", "3");
		parametri.getParametriMap().put("maxLunghezza", "6");
		parametri.getParametriMap().put("facoltativo", "true");

		RegolaMinMaxLunghezzaCampoFacoltativo regolaMinMaxLunghezzaCampoFacoltativo = new RegolaMinMaxLunghezzaCampoFacoltativo(
				"regolaMinMaxLunghezzaCampoFacoltativo", "107",
				"Lunghezza non conforme a quella attesa per un campo facoltativo", parametri);

		// seconda regola
		parametri = new Parametri();
		parametri.getParametriMap().put("maxLength", "4");		
		RegolaRangeOccorrenzeAmmesse regolaRangeOccorrenzeAmmesse = new RegolaRangeOccorrenzeAmmesse(
				"regolaRangeOccorrenzeAmmesse", "107",
				"possono essere inserite al massimo 4 diagnosi secondarie", parametri);

		//  regola anagrafica lista valori
		// TODOs: FB - ANOMALIA - COMMENTATA
//		parametri = new Parametri();
//		parametri.getParametriMap().put("nomeTabella", "ANAG_EMUR_DIAGNOSI");		
//		RegolaAnagFacoltativoListaValori regolaAnagFacoltativoListaValori = new RegolaAnagFacoltativoListaValori(
//				"regolaAnagFacoltativoListaValori", "1302",
//				"Non appartenenza al dominio di riferimento per un campo facoltativo", parametri);


		// aggiungo regole
		regoleCampo.add(regolaMinMaxLunghezzaCampoFacoltativo);
		regoleCampo.add(regolaRangeOccorrenzeAmmesse);
//		regoleCampo.add(regolaAnagFacoltativoListaValori);
		// creo campo
		return new Campo(regoleCampo, nomeCampo);

	}
	
	private static Campo creaRegolaPrestazionePrincipale(List<RegolaGenerica> regoleCampo, String nomeCampo) {
		// prima regola
		Parametri parametri = new Parametri();
		parametri.getParametriMap().put("minLunghezza", "3");
		parametri.getParametriMap().put("maxLunghezza", "9");
		parametri.getParametriMap().put("facoltativo", "true");

		RegolaMinMaxLunghezzaCampoFacoltativo regolaMinMaxLunghezzaCampoFacoltativo = new RegolaMinMaxLunghezzaCampoFacoltativo(
				"regolaMinMaxLunghezzaCampoFacoltativo", "106",
				"Lunghezza non conforme a quella attesa per un campo ad obbligatorietà condizionata", parametri);
				

		// CAMBIO LE CONDIZIONE SOLO CON 6 -> INCONGRUENZA TRA I DUE DOCUMENTI
		// seconda regola
		// TODOs: FB - ANOMALIA - COMMENTATA
//		parametri = new Parametri();
//		parametri.getParametriMap().put("parametroCampoCondizionante", "6|7");
//		parametri.getParametriMap().put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");
//
//		RegolaObbligatorietaCondizionataMultiCampoDiverso regolaObbligatorietaCondizionataMultiCampoDiverso = new RegolaObbligatorietaCondizionataMultiCampoDiverso(
//				"regolaObbligatorietaCondizionataMultiCampoDiverso", "1103",
//				"Mancata valorizzazione di un campo ad obbligatorietà condizionata", parametri);

		// terza regola
		// TODOs: FB - ANOMALIA - COMMENTATA
//		parametri = new Parametri();
//		parametri.getParametriMap().put("parametroCampoCondizionante", "6");
//		parametri.getParametriMap().put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");
//
//		RegolaIncongruenzaCrossDueCampiUguaglianza regolaIncoerenzaCrossCampoUguaglianza = new RegolaIncongruenzaCrossDueCampiUguaglianza(
//				"regolaIncongruenzaCrossDueCampiUguaglianza", "2104", null, parametri);


		// regola adhoc
		// TODOs: FB - ANOMALIA - COMMENTATA
//		parametri = new Parametri();
//		RegolaEmurPSPrestPrinc regolaEmurPSPrestPrinc = new RegolaEmurPSPrestPrinc(
//				"regolaEmurPSPrestPrinc", null, null, parametri);
		
		// aggiungo regole
		regoleCampo.add(regolaMinMaxLunghezzaCampoFacoltativo);
//		regoleCampo.add(regolaObbligatorietaCondizionataMultiCampoDiverso);
//		regoleCampo.add(regolaIncoerenzaCrossCampoUguaglianza);
//		regoleCampo.add(regolaEmurPSPrestPrinc);
		// creo campo
		return new Campo(regoleCampo, nomeCampo);

	}
	
	private static Campo creaRegolaPrestazioneSecondaria(List<RegolaGenerica> regoleCampo, String nomeCampo) {
		// prima regola
		Parametri parametri = new Parametri();
		parametri.getParametriMap().put("minLunghezza", "3");
		parametri.getParametriMap().put("maxLunghezza", "9");
		parametri.getParametriMap().put("facoltativo", "true");

		RegolaMinMaxLunghezzaCampoFacoltativo regolaMinMaxLunghezzaCampoFacoltativo = new RegolaMinMaxLunghezzaCampoFacoltativo(
				"regolaMinMaxLunghezzaCampoFacoltativo", "107",
				"Lunghezza non conforme a quella attesa per un campo facoltativo", parametri);

		// seconda regola
		parametri = new Parametri();
		parametri.getParametriMap().put("maxLength", "4");
		
		RegolaRangeOccorrenzeAmmesse regolaRangeOccorrenzeAmmesse = new RegolaRangeOccorrenzeAmmesse(
				"regolaRangeOccorrenzeAmmesse", "107",
				"(Possono essere inserite al massimo 4 prestazioni secondarie", parametri);

		// regola adhoc
		// TODOs: FB - ANOMALIA - COMMENTATA
//		parametri = new Parametri();
//		RegolaEmurPSPrestSec regolaEmurPSPrestSec = new RegolaEmurPSPrestSec(
//				"regolaEmurPSPrestSec", null, null, parametri);
		
		
		// aggiungo regole
		regoleCampo.add(regolaMinMaxLunghezzaCampoFacoltativo);
		regoleCampo.add(regolaRangeOccorrenzeAmmesse);
//		regoleCampo.add(regolaEmurPSPrestSec);
//		regoleCampo.add(regolaAnagFacoltativoListaValori);
		// creo campo
		return new Campo(regoleCampo, nomeCampo);

	}
	
	private static Campo creaRegolaOBIProblemaClinicoPrincipale(List<RegolaGenerica> regoleCampo, String nomeCampo) {
		// prima regola
		Parametri parametri = new Parametri();
		parametri.getParametriMap().put("minLunghezza", "3");
		parametri.getParametriMap().put("maxLunghezza", "9");
		parametri.getParametriMap().put("facoltativo", "true");

		RegolaMinMaxLunghezzaCampoFacoltativo regolaMinMaxLunghezzaCampoFacoltativo = new RegolaMinMaxLunghezzaCampoFacoltativo(
				"regolaMinMaxLunghezzaCampoFacoltativo", "106",
				"Lunghezza non conforme a quella attesa per un campo ad obbligatorietà condizionata", parametri);

		// seconda regola
		// TODOs: FB - ANOMALIA - COMMENTATA
//		parametri = new Parametri();
//		parametri.getParametriMap().put("parametroCampoCondizionante", "10");
//		parametri.getParametriMap().put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");
//
//		RegolaObbligatorietaCondizionataMultiCampo regolaObbligatorietaCondizionataMultiCampo = new RegolaObbligatorietaCondizionataMultiCampo(
//				"regolaObbligatorietaCondizionataMultiCampo", "1103",
//				"Mancata valorizzazione di un campo ad obbligatorietà condizionata", parametri);

		// terza regola
		parametri = new Parametri();
		parametri.getParametriMap().put("parametroCampoCondizionante", "10");
		parametri.getParametriMap().put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");

		RegolaIncongruenzaCrossDueCampi regolaIncongruenzaCrossDueCampi = new RegolaIncongruenzaCrossDueCampi(
				"regolaIncongruenzaCrossDueCampi", "1121", null, parametri);

		// regola adhoc - regola 1301 e 1007
		// TODOs: FB - ANOMALIA - COMMENTATA
		// TODOs: FB - ANOMALIA - COMMENTATA
//		parametri = new Parametri();
//		RegolaEmurPSProbClinicoPrinc regolaEmurPSProbClinicoPrinc = new RegolaEmurPSProbClinicoPrinc(
//				"regolaEmurPSProbClinicoPrinc", null, null, parametri);

		// aggiungo regole
		regoleCampo.add(regolaMinMaxLunghezzaCampoFacoltativo);
//		regoleCampo.add(regolaObbligatorietaCondizionataMultiCampo);
		regoleCampo.add(regolaIncongruenzaCrossDueCampi);
//		regoleCampo.add(regolaEmurPSProbClinicoPrinc);
		// creo campo
		return new Campo(regoleCampo, nomeCampo);

	}
	
	private static Campo creaRegolaOBIDataIngresso(List<RegolaGenerica> regoleCampo, String nomeCampo) {
		// prima regola
		
		Parametri parametri = new Parametri();
		parametri.getParametriMap().put("parametroCampoCondizionante", "10");
		parametri.getParametriMap().put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");

		RegolaObbligatorietaCondizionataMultiCampo regolaObbligatorietaCondizionataMultiCampo = new RegolaObbligatorietaCondizionataMultiCampo(
				"regolaObbligatorietaCondizionataMultiCampo", "1103",
				"Mancata valorizzazione di un campo ad obbligatorietà condizionata", parametri);

		
		// controllo condizionato
		parametri = new Parametri();
		parametri.getParametriMap().put("parametroCampoCondizionante", "10");
		parametri.getParametriMap().put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");

		RegolaEmurPSOBIDataIngresso regolaEmurPSOBIDataIngresso = new RegolaEmurPSOBIDataIngresso(
				"regolaEmurPSOBIDataIngresso", null,
				null, parametri);

		
		// aggiungo regole
		regoleCampo.add(regolaObbligatorietaCondizionataMultiCampo);
		regoleCampo.add(regolaEmurPSOBIDataIngresso);
		

		
		
		// creo campo
		return new Campo(regoleCampo, nomeCampo);

	}
	
	private static Campo creaRegolaOBIOraIngresso(List<RegolaGenerica> regoleCampo, String nomeCampo) {
		// prima regola
		
		Parametri parametri = new Parametri();
		parametri.getParametriMap().put("parametroCampoCondizionante", "10");
		parametri.getParametriMap().put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");

		RegolaObbligatorietaCondizionataMultiCampo regolaObbligatorietaCondizionataMultiCampo = new RegolaObbligatorietaCondizionataMultiCampo(
				"regolaObbligatorietaCondizionataMultiCampo", "1103",
				"Mancata valorizzazione di un campo ad obbligatorietà condizionata", parametri);

		 parametri = new Parametri();
			parametri.getParametriMap().put("parametroCampoCondizionante", "10");
			parametri.getParametriMap().put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");

			RegolaEmurPSOBIOraIngresso regolaEmurPSOBIOraIngresso = new RegolaEmurPSOBIOraIngresso(
					"regolaEmurPSOBIOraIngresso", null,
					null, parametri);

		
		// aggiungo regole
		regoleCampo.add(regolaObbligatorietaCondizionataMultiCampo);
		regoleCampo.add(regolaEmurPSOBIOraIngresso);
		
		// creo campo
		return new Campo(regoleCampo, nomeCampo);

	}
	
	private static Campo creaRegolaOBIPrimaConsulenzaSpecDis(List<RegolaGenerica> regoleCampo, String nomeCampo) {
		
		// prima regola
		Parametri parametri = new Parametri();
		parametri = new Parametri();
		parametri.getParametriMap().put("parametroCampoCondizionante", "10");
		parametri.getParametriMap().put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");

		RegolaIncongruenzaCrossDueCampi regolaIncongruenzaCrossDueCampi = new RegolaIncongruenzaCrossDueCampi(
				"regolaIncongruenzaCrossDueCampi", "1125", null, parametri);

		
//		parametri = new Parametri();
		// TODOs: FB - ANOMALIA - COMMENTATA
//		parametri.getParametriMap().put("parametroCampoCondizionante", "10");
//		parametri.getParametriMap().put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");
//		parametri.getParametriMap().put("nomeTabella", "ANAG_EMUR_DISCIPLINE");
//		
//		RegolaEmurPSAnagCondizionato regolaEmurPSAnagCondizionato = new RegolaEmurPSAnagCondizionato(
//				"regolaEmurPSAnagCondizionato", "1302",
//				"Non appartenenza al dominio di riferimento per un campo facoltativo", parametri);
		
		
		// aggiungo regole
		regoleCampo.add(regolaIncongruenzaCrossDueCampi);
//		regoleCampo.add(regolaEmurPSAnagCondizionato);
		// creo campo
		return new Campo(regoleCampo, nomeCampo);

	}
	
	private static Campo creaRegolaOBIPrimaConsulenzaSpecNum(List<RegolaGenerica> regoleCampo, String nomeCampo) {
		
		// prima regola
		Parametri parametri = new Parametri();
		parametri = new Parametri();
		parametri.getParametriMap().put("parametroCampoCondizionante", "10");
		parametri.getParametriMap().put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");

		RegolaIncongruenzaCrossDueCampi regolaIncongruenzaCrossDueCampi = new RegolaIncongruenzaCrossDueCampi(
				"regolaIncongruenzaCrossDueCampi", "1126", null, parametri);

		// aggiungo regole
		regoleCampo.add(regolaIncongruenzaCrossDueCampi);
		// creo campo
		return new Campo(regoleCampo, nomeCampo);

	}
	
	private static Campo creaRegolaOBISecondaConsulenzaSpecDis(List<RegolaGenerica> regoleCampo, String nomeCampo) {
		
		// prima regola
		Parametri parametri = new Parametri();
		parametri = new Parametri();
		parametri.getParametriMap().put("parametroCampoCondizionante", "10");
		parametri.getParametriMap().put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");

		RegolaIncongruenzaCrossDueCampi regolaIncongruenzaCrossDueCampi = new RegolaIncongruenzaCrossDueCampi(
				"regolaIncongruenzaCrossDueCampi", "1127", null, parametri);

		
//		parametri = new Parametri();
		// TODOs: FB - ANOMALIA - COMMENTATA
//		parametri.getParametriMap().put("parametroCampoCondizionante", "10");
//		parametri.getParametriMap().put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");
//		parametri.getParametriMap().put("nomeTabella", "ANAG_EMUR_DISCIPLINE");
//		
//		RegolaEmurPSAnagCondizionato regolaEmurPSAnagCondizionato = new RegolaEmurPSAnagCondizionato(
//				"regolaEmurPSAnagCondizionato", "1302",
//				"Non appartenenza al dominio di riferimento per un campo facoltativo", parametri);
		
		
		// aggiungo regole
		regoleCampo.add(regolaIncongruenzaCrossDueCampi);
//		regoleCampo.add(regolaEmurPSAnagCondizionato);
		// creo campo
		return new Campo(regoleCampo, nomeCampo);

	}
	
	private static Campo creaRegolaOBISecondaConsulenzaSpecNum(List<RegolaGenerica> regoleCampo, String nomeCampo) {
		
		// prima regola
		Parametri parametri = new Parametri();
		parametri = new Parametri();
		parametri.getParametriMap().put("parametroCampoCondizionante", "10");
		parametri.getParametriMap().put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");

		RegolaIncongruenzaCrossDueCampi regolaIncongruenzaCrossDueCampi = new RegolaIncongruenzaCrossDueCampi(
				"regolaIncongruenzaCrossDueCampi", "1128", null, parametri);

		// aggiungo regole
		regoleCampo.add(regolaIncongruenzaCrossDueCampi);
		// creo campo
		return new Campo(regoleCampo, nomeCampo);

	}
	
	private static Campo creaRegolaOBIEsito(List<RegolaGenerica> regoleCampo, String nomeCampo) {
		
		// prima regola
		Parametri parametri = new Parametri();
		parametri.getParametriMap().put("parametroCampoCondizionante", "10");
		parametri.getParametriMap().put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");

		RegolaObbligatorietaCondizionataMultiCampo regolaObbligatorietaCondizionataMultiCampo = new RegolaObbligatorietaCondizionataMultiCampo(
				"regolaObbligatorietaCondizionataMultiCampo", "1103",
				"Mancata valorizzazione di un campo ad obbligatorietà condizionata", parametri);
		
		// seconda regola
		parametri = new Parametri();
		parametri = new Parametri();
		parametri.getParametriMap().put("parametroCampoCondizionante", "10");
		parametri.getParametriMap().put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");

		RegolaIncongruenzaCrossDueCampi regolaIncongruenzaCrossDueCampi = new RegolaIncongruenzaCrossDueCampi(
				"regolaIncongruenzaCrossDueCampi", "1129", null, parametri);

		// aggiungo regole
		regoleCampo.add(regolaObbligatorietaCondizionataMultiCampo);
		regoleCampo.add(regolaIncongruenzaCrossDueCampi);		
		// creo campo
		return new Campo(regoleCampo, nomeCampo);

	}
	
	private static Campo creaRegolaOBIPrestazionePrincipaleErogata(List<RegolaGenerica> regoleCampo, String nomeCampo) {
		
		// prima regola  
		// TODOs: FB - ANOMALIA - COMMENTATA
//		Parametri parametri = new Parametri();
//		parametri.getParametriMap().put("parametroCampoCondizionante", "10");
//		parametri.getParametriMap().put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");
//
//		RegolaObbligatorietaCondizionataMultiCampo regolaObbligatorietaCondizionataMultiCampo = new RegolaObbligatorietaCondizionataMultiCampo(
//				"regolaObbligatorietaCondizionataMultiCampo", "1103",
//				"Mancata valorizzazione di un campo ad obbligatorietà condizionata", parametri);
		
		// seconda regola
		Parametri parametri = new Parametri();
		parametri = new Parametri();
		parametri.getParametriMap().put("parametroCampoCondizionante", "10");
		parametri.getParametriMap().put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");

		RegolaIncongruenzaCrossDueCampi regolaIncongruenzaCrossDueCampi = new RegolaIncongruenzaCrossDueCampi(
				"regolaIncongruenzaCrossDueCampi", "1130", null, parametri);
		
//		// regola adhoc
		// TODOs: FB - ANOMALIA - COMMENTATA
//		parametri = new Parametri();		
//		RegolaEmurPSPrestPrincOBI regolaEmurPSPrestPrincOBI = new RegolaEmurPSPrestPrincOBI("regolaEmurPSPrestPrincOBI", null , null, parametri);

		
		// aggiungo regole
//		regoleCampo.add(regolaObbligatorietaCondizionataMultiCampo);
		regoleCampo.add(regolaIncongruenzaCrossDueCampi);
//		regoleCampo.add(regolaEmurPSPrestPrincOBI);
		// creo campo
		return new Campo(regoleCampo, nomeCampo);

	}
	
	private static Campo creaRegolaOBIPrestazioneSecondariaErogata(List<RegolaGenerica> regoleCampo, String nomeCampo) {
		
		// prima regola
		Parametri parametri = new Parametri();
		parametri = new Parametri();
		parametri = new Parametri();
		parametri.getParametriMap().put("parametroCampoCondizionante", "10");
		parametri.getParametriMap().put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");

		RegolaIncongruenzaCrossDueCampiList regolaIncongruenzaCrossDueCampiList = new RegolaIncongruenzaCrossDueCampiList(
				"regolaIncongruenzaCrossDueCampiList", "1131", null, parametri);
		
		// seconda regola
		parametri = new Parametri();
		parametri.getParametriMap().put("minLunghezza", "3");
		parametri.getParametriMap().put("maxLunghezza", "9");
		parametri.getParametriMap().put("facoltativo", "true");

		RegolaMinMaxLunghezzaCampoFacoltativo regolaMinMaxLunghezzaCampoFacoltativo = new RegolaMinMaxLunghezzaCampoFacoltativo(
				"regolaMinMaxLunghezzaCampoFacoltativo", "107",
				"Lunghezza non conforme a quella attesa per un campo facoltativo", parametri);

		// terza regola
		parametri.getParametriMap().put("maxLength", "4");		
		RegolaRangeOccorrenzeAmmesse regolaRangeOccorrenzeAmmesse = new RegolaRangeOccorrenzeAmmesse(
				"regolaRangeOccorrenzeAmmesse", "107",
				"Possono essere inserite al massimo 4 prestazioni secondarie", parametri);
		
		
		// regola adhoc
		// TODOs: FB - ANOMALIA - COMMENTATA
//		parametri = new Parametri();		
//		RegolaEmurPSPrestSecOBI regolaEmurPSPrestSecOBI = new RegolaEmurPSPrestSecOBI("regolaEmurPSPrestSecOBI", null , null, parametri);

		
		// aggiungo regole
		regoleCampo.add(regolaIncongruenzaCrossDueCampiList);
		regoleCampo.add(regolaMinMaxLunghezzaCampoFacoltativo);
		regoleCampo.add(regolaRangeOccorrenzeAmmesse);
//		regoleCampo.add(regolaEmurPSPrestSecOBI);
		
		// creo campo
		return new Campo(regoleCampo, nomeCampo);

	}
	
	private static Campo creaRegolaOBIDiagnosiPrincipaleUscita(List<RegolaGenerica> regoleCampo, String nomeCampo) {
		
		// prima regola
		// TODOs: FB - ANOMALIA - COMMENTATA
//		Parametri parametri = new Parametri();
//		parametri.getParametriMap().put("parametroCampoCondizionante", "10");
//		parametri.getParametriMap().put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");
//
//		RegolaObbligatorietaCondizionataMultiCampo regolaObbligatorietaCondizionataMultiCampo = new RegolaObbligatorietaCondizionataMultiCampo(
//				"regolaObbligatorietaCondizionataMultiCampo", "1103",
//				"Mancata valorizzazione di un campo ad obbligatorietà condizionata", parametri);
		
				
		// seconda regola
		Parametri parametri = new Parametri();
		parametri.getParametriMap().put("parametroCampoCondizionante", "10");
		parametri.getParametriMap().put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");

		RegolaIncongruenzaCrossDueCampi regolaIncongruenzaCrossDueCampi = new RegolaIncongruenzaCrossDueCampi(
				"regolaIncongruenzaCrossDueCampi", "1132", null, parametri);
		
		// terza regola
		parametri = new Parametri();
		parametri.getParametriMap().put("minLunghezza", "3");
		parametri.getParametriMap().put("maxLunghezza", "6");
		parametri.getParametriMap().put("facoltativo", "true");

		RegolaMinMaxLunghezzaCampoFacoltativo regolaMinMaxLunghezzaCampoFacoltativo = new RegolaMinMaxLunghezzaCampoFacoltativo(
				"regolaMinMaxLunghezzaCampoFacoltativo", "106",
				"Lunghezza non conforme a quella attesa per un campo ad obbligatorietà condizionata", parametri);
		
		// regola adhoc anagrafica diagnosi - se esito trattamento = 10
		// TODOs: FB - ANOMALIA - COMMENTATA
//		parametri = new Parametri();
//		RegolaOBIDiagnosiPrincUscita regolaOBIDiagnosiPrincUscita = new RegolaOBIDiagnosiPrincUscita("regolaOBIDiagnosiPrincUscita", null , null, parametri);

		
		// aggiungo regole
//		regoleCampo.add(regolaObbligatorietaCondizionataMultiCampo);
		regoleCampo.add(regolaIncongruenzaCrossDueCampi);
		regoleCampo.add(regolaMinMaxLunghezzaCampoFacoltativo);
//		regoleCampo.add(regolaOBIDiagnosiPrincUscita);
		// creo campo
		return new Campo(regoleCampo, nomeCampo);

	}
	
	private static Campo creaRegolaOBIDiagnosiSecondariaUscita(List<RegolaGenerica> regoleCampo, String nomeCampo) {
		
		// prima regola
		Parametri parametri = new Parametri();
		parametri.getParametriMap().put("parametroCampoCondizionante", "10");
		parametri.getParametriMap().put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");

		RegolaIncongruenzaCrossDueCampiList regolaIncongruenzaCrossDueCampi = new RegolaIncongruenzaCrossDueCampiList(
				"regolaIncongruenzaCrossDueCampi", "1133", null, parametri);

		// seconda regola
		parametri = new Parametri();
		parametri.getParametriMap().put("minLunghezza", "3");
		parametri.getParametriMap().put("maxLunghezza", "6");
		parametri.getParametriMap().put("facoltativo", "true");

		RegolaMinMaxLunghezzaCampoFacoltativo regolaMinMaxLunghezzaCampoFacoltativo = new RegolaMinMaxLunghezzaCampoFacoltativo(
				"regolaMinMaxLunghezzaCampoFacoltativo", "107",
				"Lunghezza non conforme a quella attesa per un campo facoltativo.", parametri);

		// terza regola
		parametri.getParametriMap().put("maxLength", "4");

		RegolaRangeOccorrenzeAmmesse regolaRangeOccorrenzeAmmesse = new RegolaRangeOccorrenzeAmmesse(
				"regolaRangeOccorrenzeAmmesse", "107", "Possono essere inserite al massimo 4 diagnosi secondarie",
				parametri);
			
		// TODOs: FB - ANOMALIA - COMMENTATA
//		parametri = new Parametri();
//		parametri.getParametriMap().put("parametroCampoCondizionante", "10");
//		parametri.getParametriMap().put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");
//		parametri.getParametriMap().put("nomeTabella", "ANAG_EMUR_DIAGNOSI");
//		
//		RegolaEmurPSAnagCondizionato regolaEmurPSAnagCondizionato = new RegolaEmurPSAnagCondizionato(
//				"regolaEmurPSAnagCondizionato", "1302",
//				"Non appartenenza al dominio di riferimento per un campo facoltativo", parametri);
//		assistitoOBIDiagnosiSecondariaUscita fabio -> 1302 anagrafica solo se esito tratt = 10
		
		// aggiungo regole
		regoleCampo.add(regolaIncongruenzaCrossDueCampi);
		regoleCampo.add(regolaMinMaxLunghezzaCampoFacoltativo);
		regoleCampo.add(regolaRangeOccorrenzeAmmesse);
//		regoleCampo.add(regolaEmurPSAnagCondizionato);
		// creo campo
		return new Campo(regoleCampo, nomeCampo);

	}
	
	private static Campo creaRegolaOBIDataUscita(List<RegolaGenerica> regoleCampo, String nomeCampo) {
		
		// prima regola
		Parametri parametri = new Parametri();
		parametri.getParametriMap().put("parametroCampoCondizionante", "10");
		parametri.getParametriMap().put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");

		RegolaObbligatorietaCondizionataMultiCampo regolaObbligatorietaCondizionataMultiCampo = new RegolaObbligatorietaCondizionataMultiCampo(
				"regolaObbligatorietaCondizionataMultiCampo", "1103",
				"Mancata valorizzazione di un campo ad obbligatorietà condizionata", parametri);
		
		
		parametri = new Parametri();
		parametri.getParametriMap().put("parametroCampoCondizionante", "10");
		parametri.getParametriMap().put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");

		RegolaEmurPSOBIDataUscita regolaEmurPSOBIDataUscita = new RegolaEmurPSOBIDataUscita(
				"regolaEmurPSOBIDataUscita", null,null, parametri);
		
			
			// aggiunta regola
			regoleCampo.add(regolaObbligatorietaCondizionataMultiCampo);
			regoleCampo.add(regolaEmurPSOBIDataUscita);	
				
		
		
		// creo campo
		return new Campo(regoleCampo, nomeCampo);

	}
	
	private static Campo creaRegolaOBIOraUscita(List<RegolaGenerica> regoleCampo, String nomeCampo) {
		
		// prima regola
		Parametri parametri = new Parametri();
		parametri.getParametriMap().put("parametroCampoCondizionante", "10");
		parametri.getParametriMap().put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");

		RegolaObbligatorietaCondizionataMultiCampo regolaObbligatorietaCondizionataMultiCampo = new RegolaObbligatorietaCondizionataMultiCampo(
				"regolaObbligatorietaCondizionataMultiCampo", "1103",
				"Mancata valorizzazione di un campo ad obbligatorietà condizionata", parametri);
		
//		Regola condizionata da esito trattamento 10
		parametri = new Parametri();
		parametri.getParametriMap().put("parametroCampoCondizionante", "10");
		parametri.getParametriMap().put("nomeCampoCondizionante", "assistitoDimissioneEsitoTrattamento");

		RegolaEmurPSOBIOraUscita regolaEmurPSOBIOraUscita = new RegolaEmurPSOBIOraUscita(
				"regolaObbligatorietaCondizionataMultiCampo", "1103",
				"Mancata valorizzazione di un campo ad obbligatorietà condizionata", parametri);
		
		// aggiungo regole
		regoleCampo.add(regolaObbligatorietaCondizionataMultiCampo);
		regoleCampo.add(regolaEmurPSOBIOraUscita);
		
		
		// creo campo
		return new Campo(regoleCampo, nomeCampo);

	}

	private static Campo creaRegolaResidenzaComune(List<RegolaGenerica> regoleCampo, String nomeCampo) {
		
		// prima regola - regola errore 1301 e 20077
		Parametri parametri = new Parametri();
		RegolaEmurPSComuneResidenza regolaEmurPSComuneResidenza = new RegolaEmurPSComuneResidenza(
				"regolaEmurPSComuneResidenza", null,
				null, parametri);
		
			// aggiunta regola
			regoleCampo.add(regolaEmurPSComuneResidenza);
				
		// creo campo
		return new Campo(regoleCampo, nomeCampo);

	}
	
	private static Campo creaRegolaResidenzaStatoEstero(List<RegolaGenerica> regoleCampo, String nomeCampo) {
		
		// prima regola - regola errore 1301 e 20077 e 20073 E 2007
		Parametri parametri = new Parametri();
		RegolaEmurPSStatoEsteroResidenza regolaEmurPSStatoEsteroResidenza = new RegolaEmurPSStatoEsteroResidenza(
				"regolaEmurPSComuneResidenza", null,
				null, parametri);
		
			// aggiunta regola
			regoleCampo.add(regolaEmurPSStatoEsteroResidenza);
				
		// creo campo
		return new Campo(regoleCampo, nomeCampo);

	}
	
	private static Campo creaRegolaDatiAnagraficiResidenzaRegione(List<RegolaGenerica> regoleCampo, String nomeCampo) {
		
		// prima regola - regola errore  20077 
		Parametri parametri = new Parametri();
		RegolaEmurPSRegioneResidenza regolaEmurPSRegioneResidenza = new RegolaEmurPSRegioneResidenza(
				"regolaEmurPSRegioneResidenza", null,
				null, parametri);
		
			// aggiunta regola
			regoleCampo.add(regolaEmurPSRegioneResidenza);
				
		// creo campo
		return new Campo(regoleCampo, nomeCampo);

	}
	
	
//	private static Campo creaRegolaAssistitoCUNI(List<RegolaGenerica> regoleCampo, String nomeCampo) {
//		
//		// prima regola - checksum
//		Parametri parametri = new Parametri();
//		RegolaEmurPSCUNI regolaEmurPSCUNI = new RegolaEmurPSCUNI(
//				"regolaEmurPSCUNI", null,
//				null, parametri);
//		
//		// aggiunta regola
//		regoleCampo.add(regolaEmurPSCUNI);
//			
//		// creo campo
//		return new Campo(regoleCampo, nomeCampo);
//
//	}
	
	
	
}
