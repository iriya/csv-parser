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

import java.util.ArrayList;
import java.util.List;

/**
 * Holder for parsed CSV.
 * <p><ul>
 * <li>{@link #size()} return the row size.</li>
 * <li>{@link #getRow(int)} return the specify positon of the row. (each col is an element of the string array list)</li>
 * </ul>
 * </p>
 * @author iriyadays
 *
 */
public class CsvDocument {
	int currentRow = -1;
	List<Row> rowList = new ArrayList<Row>();
	StringBuffer buffer = new StringBuffer();
	
	/*
	 * add an empty field to current row
	 */
	protected void newEmptyField() {
		Row row = getCurrentRow();
		row.add("");
	}
	
	/*
	 * add char to current field
	 */
	protected void push(char next) {
		buffer.append(next);
	}
	
	/*
	 * clear the field buffer
	 */
	protected void clear() {
		buffer = new StringBuffer();
	}
	
	/*
	 * add a new field to current row, move to next row if 'incrRowNext' is true
	 */
	protected void newField(boolean incrRowNext) {
		Row row = getCurrentRow();
		row.add(buffer.toString());
		clear();
		if(incrRowNext) currentRow++;
	}
	
	/**
	 * Get Row size of the CSV
	 * @return row size
	 */
	public int size() {
		return rowList.size();
	}
	
	/**
	 * Get Row of 'index'
	 * @param index index of row
	 * @return content of this row, each col is a string
	 */
	public List<String> getRow(int index) {
		return rowList.get(index).colList;
	}
	
	/*
	 * check if current row is valid, and return current row
	 */
	private Row getCurrentRow() {
		checkRow();
		return rowList.get(currentRow);
	}
	
	/*
	 * if row was not created, create it
	 */
	private void checkRow() {
		if(currentRow < 0) currentRow = 0;
		while(rowList.size() <= currentRow) rowList.add(new Row());
	}
	
	// holder
	final class Row {
		List<String> colList = new ArrayList<String>();
		
		public void add(String col) {
			colList.add(col);
		}
	}
	
}
