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
  * An exception for the simulator.
  * @author Jason Vroustouris
  */
 public class MythError extends Exception {
	String _title = "";
	String _message = "(error not defined)";
	boolean _pop_up = false;
	int _line_number=-1;
	public MythError() {}
	public MythError(String a) {
		_message = a;
	}
	public MythError(int a, int s[], int b) {
		switch(a) {
			case 0:
				_message = "Stopped.";	break;
			case 1:
				_message = "Stopped. Ucode address " + b;
				_message += " past end of program at line ";
				_message += s[MythSim.CURRENT_ADDRESS] + ".";
			break;
			case 5:
				_message = "Stopped. Memory address out of range at line ";
				_message += s[MythSim.CURRENT_ADDRESS];
				_message += "Attemped to access memory address " + b + ".";
			break;
			default:
				_message = "(error code not defined)";
			break;
		}
	}
	public String message() {
		return _message;
	}
}
