/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.flusso.emurps.parser.regole.split;

import com.opencsv.bean.AbstractCsvConverter;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

import it.mds.sdk.flusso.emurps.tracciato.bean.output.PrestazioneSecondaria;

public class TextPrestazioneSecondaria extends AbstractCsvConverter {

	@Override
	public Object convertToRead(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
		PrestazioneSecondaria prestazioneSecondaria = new PrestazioneSecondaria();
		prestazioneSecondaria.setValue(s);
//		List<PrestazioneSecondaria> lstPrestazioneSecondaria = new ArrayList<PrestazioneSecondaria>();
		
//		StringTokenizer st = new StringTokenizer(s,"|");
//		Iterator it = st.asIterator();
//		while (it.hasNext()) {
//			String valore = (String) it.next();
//			prestazioneSecondaria.setValue(valore);
//			lstPrestazioneSecondaria.add(prestazioneSecondaria);
//			prestazioneSecondaria = new PrestazioneSecondaria();
//		}
		
		
		return prestazioneSecondaria;
	}

	@Override
	public String convertToWrite(Object value) throws CsvDataTypeMismatchException {
		PrestazioneSecondaria prestSecondaria = (PrestazioneSecondaria)value;
//		Iterator<PrestazioneSecondaria> it = lstPrestazioneSec.iterator();
//		StringBuffer ret = new StringBuffer();
//		while (it.hasNext()) {
//			PrestazioneSecondaria prestazioneSecondaria = (PrestazioneSecondaria) it.next();
//			ret.append(prestazioneSecondaria.getValue());			
//			ret.append("|");
//		}
		
		return prestSecondaria.getValue();
	}
}