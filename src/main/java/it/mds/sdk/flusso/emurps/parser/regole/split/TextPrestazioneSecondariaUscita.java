/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.flusso.emurps.parser.regole.split;

import com.opencsv.bean.AbstractCsvConverter;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

public class TextPrestazioneSecondariaUscita extends AbstractCsvConverter {

	@Override
	public Object convertToRead(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
		
		return s;
	}

	@Override
	public String convertToWrite(Object value) throws CsvDataTypeMismatchException {
		
		
		return String.valueOf(value);
	}
}