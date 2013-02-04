package com.jrj.csv;

/**
 * Exception for NOT EXPECTED TOKEN while parsing.
 * @author iriyadays
 *
 */
public class IllegalTokenException extends IllegalArgumentException {

	public IllegalTokenException(char next, String ref) {
		super("Illegal token: " + next + " while parsing " + ref);
	}
}
