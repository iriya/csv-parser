/* 
 * Copyright (c) 2013 JRJ.COM.
 * 
 * This software is licensed to you under the GNU General Public License,
 * version 2 (GPLv2). There is NO WARRANTY for this software, express or
 * implied, including the implied warranties of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. You should have received a copy of GPLv2
 * along with this software; if not, see
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.txt.
 */
package com.jrj.csv;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.CharBuffer;

/**
 * CSV format file parser.
 * <p>
 * Default, using: 
 * {@link #parse()} or {@link #parse(char)} to return a {@link CsvDocument} for the parsed file.
 * and then deal with the content like: 
 * <pre>
 * CsvReader reader = new CsvReader("~/1120.csv");
 * CsvDocument doc = csvReader.parse();
 * int rows = doc.size();
 * // get first row
 * List<String> row = doc.getRow(0);
 * // get first col
 * String name = row.get(0);
 * ... 
 * </pre>
 * </p>
 * <p>
 * For file that using specified encoding, also supported with passing encoding as second parameter:
 * {@link #CsvReader(String, String)} or {@link #CsvReader(InputStream, String)}
 * </p>
 * @author iriyadays
 *
 */
public class CsvReader {
	InputStreamReader reader;
	
	/**
	 * Default constructor
	 * @param filename file name of csv
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public CsvReader(String filename) throws FileNotFoundException, UnsupportedEncodingException {
		this(filename, System.getProperty("file.encoding"));
	}
	
	/**
	 * Constructor with file name and encoding
	 * @param filename file name of csv
	 * @param encode encoding of csv
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public CsvReader(String filename, String encode) throws FileNotFoundException, UnsupportedEncodingException {
		this(new FileInputStream(filename), encode);
	}
	
	/**
	 * Constructor with InputStream
	 * @param in InputStream of csv
	 * @throws UnsupportedEncodingException
	 */
	public CsvReader(InputStream in) throws UnsupportedEncodingException {
		this(in, System.getProperty("file.encoding"));
		System.out.println(System.getProperty("file.encoding"));
	}
	
	/**
	 * Constructor with InputStream and encoding
	 * @param in InputStream of csv
	 * @param encode encoding of csv
	 * @throws UnsupportedEncodingException
	 */
	public CsvReader(InputStream in, String encode) throws UnsupportedEncodingException {
		reader = new InputStreamReader(in, encode);
	}
	
	/**
	 * Parsing csv with default separator ',' (Comma)
	 * @return
	 */
	public CsvDocument parse() {
		return parse(Parser.COMMA);
	}
	
	/**
	 * Parsing csv with the specified separator, like ';' and etc.
	 * @param separator the separator char of each field
	 * @return
	 */
	public CsvDocument parse(char separator) {
		CsvDocument doc = new CsvDocument();
		Parser parser = new Parser(separator, doc);
		try {
			int readed = 0;
			CharBuffer buffer = parser.getBuffer();
			
			while( (readed = reader.read(buffer)) != -1) {
				buffer.flip();
				parser.parse();
			}
			return doc;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
