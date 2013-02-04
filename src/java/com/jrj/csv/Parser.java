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

import java.nio.CharBuffer;

/**
 * Csv Parser.
 * <p>
 * Using state machine to parse csv format. While parsing, State are stored inside the machine, and fields are
 * added to probable row of the given 'CsvDocument'.
 * </p>
 * @author iriyadays
 *
 */
class Parser {
	
	public static final char QUOTE = '"';
	public static final char SPACE = ' ';
	public static final char TAB = '\t';
	public static final char COMMA = ',';
	public static final char LINE_FEED = '\n';
	public static final char CARRIAGE_RETURN = '\r';
	
	enum State {
		FIELD_START,
		NORMAL,
		QUOTED,
		POST_QUOTED
	}
	
	private static final int DEFAULT_SIZE = 256;
	CharBuffer buffer;
	CsvDocument doc;
	State parseState = State.FIELD_START;
	char separator;
	
	public Parser(char separator, CsvDocument doc) {
		this.doc = doc;
		this.separator = separator;
		buffer = CharBuffer.allocate(DEFAULT_SIZE);
	}
	
	public CharBuffer getBuffer() {
		return buffer;
	}
	
	public void parse() {
		while(buffer.remaining() > 0) {
			char next = buffer.get();
			
			switch(parseState) {
			case FIELD_START: 
				// any whitespace or '\n' '\r' before a field is ignore
				if(next == SPACE || next == TAB || next == LINE_FEED || next == CARRIAGE_RETURN);
				// quote field
				else if(next == QUOTE) parseState = State.QUOTED;
				// create empty field, if separator immediately after field
				else if(next == separator) {
					doc.newEmptyField();
				}
				// normal field
				else {
					doc.push(next);
					parseState = State.NORMAL;
				}
				break;
				
			case NORMAL: 
				// complete normal field
				if(next == separator) {
					doc.newField(false);
					parseState = State.FIELD_START;
				}
				// complete normal field, and create new row for next
				else if(next == LINE_FEED || next == CARRIAGE_RETURN) {
					doc.newField(true);
					parseState = State.FIELD_START;
					
				}
				//// DOESN'T EXPECTED
				else if(next == QUOTE) {
					throw new IllegalTokenException(next, buffer.toString());
				}
				// continue normal field
				else doc.push(next);
				break;
				
			case QUOTED: 
				// find a quote pair
				if(next == QUOTE) parseState = State.POST_QUOTED;
				// else accept any except quote
				else doc.push(next);
				break;
				
			case POST_QUOTED: 
				// in a post quote, check if it is an escape or a field end
				if(next == QUOTE) {
					doc.push(next);
					parseState = State.QUOTED;
				}
				else if(next == separator) {
					doc.newField(false);
					parseState = State.FIELD_START;
				}
				else if(next == LINE_FEED || next == CARRIAGE_RETURN) {
					doc.newField(true);
					parseState = State.FIELD_START;
				}
				break;
				
			default: break;
			}
		} // while
		
		// stay current State, and prepare for next
		buffer.clear();
	}
}
