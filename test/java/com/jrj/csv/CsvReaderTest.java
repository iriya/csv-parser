package com.jrj.csv;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class CsvReaderTest {
	
	CsvReader csvReader;

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testParseChar() throws ArrayIndexOutOfBoundsException, IllegalArgumentException, IOException {
		InputStream in = getClass().getClassLoader().getResourceAsStream("test.csv");
		csvReader = new CsvReader(in, "ISO-8859-1");
		
		CsvDocument doc = csvReader.parse(';');
		assertEquals(2, doc.size());
		List<String> row = doc.getRow(0);
		assertEquals(5, row.size());
		assertEquals("a", row.get(0));
		assertEquals("b", row.get(1));
		assertEquals("e", row.get(4));
		row = doc.getRow(1);
		assertEquals("3\"77", row.get(1));
		assertEquals("我", new String(row.get(2).getBytes("ISO-8859-1"), "GBK"));
	}

}
