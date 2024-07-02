/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.flusso.emurps.parser.regole.split;

import com.opencsv.bean.AbstractCsvConverter;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

import it.mds.sdk.flusso.emurps.tracciato.bean.output.DiagnosiSecondaria;

public class TextDiagnosiSecondaria extends AbstractCsvConverter {

	@Override
	public Object convertToRead(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
		DiagnosiSecondaria prestazioneSecondaria = new DiagnosiSecondaria();
		prestazioneSecondaria.setValue(s);
		
		return prestazioneSecondaria;
	}

	@Override
	public String convertToWrite(Object value) throws CsvDataTypeMismatchException {
		DiagnosiSecondaria prestSecondaria = (DiagnosiSecondaria)value;
		
		return prestSecondaria.getValue();
	}
}