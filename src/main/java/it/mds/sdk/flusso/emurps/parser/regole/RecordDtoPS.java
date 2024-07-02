/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.flusso.emurps.parser.regole;

import java.util.List;

import com.opencsv.bean.CsvBindAndSplitByPosition;
import com.opencsv.bean.CsvBindByPosition;

import it.mds.sdk.flusso.emurps.parser.regole.split.TextDiagnosiSecondaria;
import it.mds.sdk.flusso.emurps.parser.regole.split.TextPrestazioneSecondaria;
import it.mds.sdk.flusso.emurps.parser.regole.split.TextPrestazioneSecondariaErogata;
import it.mds.sdk.flusso.emurps.parser.regole.split.TextPrestazioneSecondariaUscita;
import it.mds.sdk.flusso.emurps.tracciato.bean.output.DiagnosiSecondaria;
import it.mds.sdk.flusso.emurps.tracciato.bean.output.PrestazioneSecondaria;
import it.mds.sdk.libreriaregole.dtos.RecordDtoGenerico;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordDtoPS extends RecordDtoGenerico {
	// Identificativo~Erogatore.CodiceIstituto~Entrata.Data~Entrata.Ora~modalitaArrivo~CentraleOperativa.Identificativo~CentraleOperativa.CodiceMissione~responsabileInvio~istitutoProvenienza~problemaPrincipale~trauma~triage~Assistito.CUNI~Assistito.ValiditaCI~Assistito.TipologiaCI~Assistito.CodiceIstituzioneTEAM~Assistito.DatiAnagrafici.Eta.Nascita.Anno~Assistito.DatiAnagrafici.Eta.Nascita.Mese~Assistito.DatiAnagrafici.Genere~Assistito.DatiAnagrafici.Cittadinanza~Assistito.DatiAnagrafici.Residenza.Regione~Assistito.DatiAnagrafici.Residenza.Comune~Assistito.DatiAnagrafici.Residenza.ASL~Assistito.DatiAnagrafici.Residenza.StatoEstero~Assistito.Prestazioni.PresaInCarico.Data~Assistito.Prestazioni.PresaInCarico.Ora~Assistito.Prestazioni.Diagnosi.DiagnosiPrincipale~Assistito.Prestazioni.Diagnosi.DiagnosiSecondaria~Assistito.Prestazioni.Prestazione.PrestazionePrincipale~Assistito.Prestazioni.Prestazione.PrestazioneSecondaria~Assistito.Dimissione.EsitoTrattamento~Assistito.Dimissione.DataDest~Assistito.Dimissione.OraDest~Assistito.Dimissione.Data~Assistito.Dimissione.Ora~Assistito.Dimissione.Referto~Assistito.Dimissione.LivelloAppropriatezzaAccesso~Assistito.Dimissione.Ricovero.Progressivo~Assistito.Dimissione.Ricovero.CodiceSpecialitaReparto~Assistito.Dimissione.Trasferimento.Motivo~Assistito.Dimissione.Trasferimento.Istituto~Assistito.OBI.ProblemaClinicoPrincipale~Assistito.OBI.DataIngresso~Assistito.OBI.OraIngresso~Assistito.OBI.PrimaConsulenzaSpecDis~Assistito.OBI.PrimaConsulenzaSpecNum~Assistito.OBI.SecondaConsulenzaSpecDis~Assistito.OBI.SecondaConsulenzaSpecNum~Assistito.OBI.Esito~Assistito.OBI.PrestazionePrincipaleErogata~Assistito.OBI.PrestazioneSecondariaErogata~Assistito.OBI.DiagnosiPrincipaleUscita~Assistito.OBI.DiagnosiSecondariaUscita~Assistito.OBI.DataUscita~Assistito.OBI.OraUscita~Importo.RegimeErogazione~Importo.Lordo~Importo.Ticket~Importo.PosizioneAssistitoTicket~Importo.CodiceEsenzione~tipoTrasmissione

	@CsvBindByPosition(position = 0)
	private String Identificativo;
	@CsvBindByPosition(position = 1)
	private String erogatoreCodiceIstituto;
	@CsvBindByPosition(position = 2)
	private String entrataData;
	@CsvBindByPosition(position = 3)
	private String entrataOra;
	@CsvBindByPosition(position = 4)
	private String modalitaArrivo;
	@CsvBindByPosition(position = 5)
	private String centraleOperativaIdentificativo;
	@CsvBindByPosition(position = 6)
	private String centraleOperativaCodiceMissione;
	@CsvBindByPosition(position = 7)
	private String responsabileInvio;
	@CsvBindByPosition(position = 8)
	private String istitutoProvenienza;
	@CsvBindByPosition(position = 9)
	private String problemaPrincipale;
	@CsvBindByPosition(position = 10)
	private String trauma;
	@CsvBindByPosition(position = 11)
	private String triage;
	@CsvBindByPosition(position = 12)
	private String assistitoCUNI;
	@CsvBindByPosition(position = 13)
	private String assistitoValiditaCI;
	@CsvBindByPosition(position = 14)
	private String assistitoTipologiaCI;
	@CsvBindByPosition(position = 15)
	private String assistitoCodiceIstituzioneTEAM;
	@CsvBindByPosition(position = 16)
	private String assistitoDatiAnagraficiEtaNascitaAnno;
	@CsvBindByPosition(position = 17)
	private String assistitoDatiAnagraficiEtaNascitaMese;
	@CsvBindByPosition(position = 18)
	private String assistitoDatiAnagraficiEtaPresunta;
	@CsvBindByPosition(position = 19)
	private String assistitoDatiAnagraficiGenere;
	@CsvBindByPosition(position = 20)
	private String assistitoDatiAnagraficiCittadinanza;
	@CsvBindByPosition(position = 21)
	private String assistitoDatiAnagraficiResidenzaRegione;
	@CsvBindByPosition(position = 22)
	private String assistitoDatiAnagraficiResidenzaComune;
	@CsvBindByPosition(position = 23)
	private String assistitoDatiAnagraficiResidenzaASL;
	@CsvBindByPosition(position = 24)
	private String assistitoDatiAnagraficiResidenzaStatoEstero;
	@CsvBindByPosition(position = 25)
	private String assistitoPrestazioniPresaInCaricoData;
	@CsvBindByPosition(position = 26)
	private String assistitoPrestazioniPresaInCaricoOra;
	@CsvBindByPosition(position = 27)
	private String assistitoPrestazioniDiagnosiDiagnosiPrincipale;
	@CsvBindAndSplitByPosition(position=28, elementType = DiagnosiSecondaria.class, converter = TextDiagnosiSecondaria.class, splitOn = "[|]")
	private List<DiagnosiSecondaria> assistitoPrestazioniDiagnosiDiagnosiSecondaria;
	@CsvBindByPosition(position = 29)
	private String assistitoPrestazioniPrestazionePrestazionePrincipale;
//	@CsvBindByPosition(position = 30)
	@CsvBindAndSplitByPosition(position=30, elementType = PrestazioneSecondaria.class, converter = TextPrestazioneSecondaria.class, splitOn = "[|]")
    private List<PrestazioneSecondaria> assistitoPrestazioniPrestazionePrestazioneSecondaria;
//	private String assistitoPrestazioniPrestazionePrestazioneSecondaria;
	@CsvBindByPosition(position = 31)
	private String assistitoDimissioneEsitoTrattamento;
	@CsvBindByPosition(position = 32)
	private String assistitoDimissioneDataDest;
	@CsvBindByPosition(position = 33)
	private String assistitoDimissioneOraDest;
	@CsvBindByPosition(position = 34)
	private String assistitoDimissioneData;
	@CsvBindByPosition(position = 35)
	private String assistitoDimissioneOra;
	@CsvBindByPosition(position = 36)
	private String assistitoDimissioneReferto;
	@CsvBindByPosition(position = 37)
	private String assistitoDimissioneLivelloAppropriatezzaAccesso;
	@CsvBindByPosition(position = 38)
	private String assistitoDimissioneRicoveroProgressivo;
	@CsvBindByPosition(position = 39)
	private String assistitoDimissioneRicoveroCodiceSpecialitaReparto;
	@CsvBindByPosition(position = 40)
	private String assistitoDimissioneTrasferimentoMotivo;
	@CsvBindByPosition(position = 41)
	private String assistitoDimissioneTrasferimentoIstituto;
	@CsvBindByPosition(position = 42)
	private String assistitoOBIProblemaClinicoPrincipale;
	@CsvBindByPosition(position = 43)
	private String assistitoOBIDataIngresso;
	@CsvBindByPosition(position = 44)
	private String assistitoOBIOraIngresso;
	@CsvBindByPosition(position = 45)
	private String assistitoOBIPrimaConsulenzaSpecDis;
	@CsvBindByPosition(position = 46)
	private String assistitoOBIPrimaConsulenzaSpecNum;
	@CsvBindByPosition(position = 47)
	private String assistitoOBISecondaConsulenzaSpecDis;
	@CsvBindByPosition(position = 48)
	private String assistitoOBISecondaConsulenzaSpecNum;
	@CsvBindByPosition(position = 49)
	private String assistitoOBIEsito;
	@CsvBindByPosition(position = 50)
	private String assistitoOBIPrestazionePrincipaleErogata;
	@CsvBindAndSplitByPosition(position=51, elementType = String.class, converter = TextPrestazioneSecondariaErogata.class, splitOn = "[|]")
	private List<String> assistitoOBIPrestazioneSecondariaErogata;
	@CsvBindByPosition(position = 52)
	private String assistitoOBIDiagnosiPrincipaleUscita;	
	@CsvBindAndSplitByPosition(position=53, elementType = String.class, converter = TextPrestazioneSecondariaUscita.class, splitOn = "[|]")
	private List<String> assistitoOBIDiagnosiSecondariaUscita;
	@CsvBindByPosition(position = 54)
	private String assistitoOBIDataUscita;
	@CsvBindByPosition(position = 55)
	private String assistitoOBIOraUscita;
	@CsvBindByPosition(position = 56)
	private String importoRegimeErogazione;
	@CsvBindByPosition(position = 57)
	private String importoLordo;
	@CsvBindByPosition(position = 58)
	private String importoTicket;
	@CsvBindByPosition(position = 59)
	private String importoPosizioneAssistitoTicket;
	@CsvBindByPosition(position = 60)
	private String importoCodiceEsenzione;
	@CsvBindByPosition(position = 61)
	private String tipoTrasmissione;

}
