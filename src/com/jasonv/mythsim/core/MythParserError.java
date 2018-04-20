/*
 * MythSim
 *
 * Copyright (C) 2002-2004 Jason Vroustouris <jasonv@jasonv.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.jasonv.mythsim.core;

/**
 * An exception for the parser.
 * @author Jason Vroustouris
 */
public class MythParserError extends Exception {
	String message = "(error not defined)";
	String file = "nd";
	int line=-1;
	public MythParserError() {}
	public MythParserError(String _message) {
		message = _message;
	}

	public MythParserError(String _file, int _line, String _message) {
		file = _file;
		line = _line;
		message = _message;
	}
	public String message() {
		//return "" + file + ":" + line + ":" + message;
		return message;
	}
}
