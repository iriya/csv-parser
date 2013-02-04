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
