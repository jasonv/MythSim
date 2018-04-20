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

package org.mythsim.core;
import java.io.*;
import java.util.*;
%%
%class MemParser
%line
%ignorecase
%{


/* **********************************************
* Public Functions
********************************************** */

public void parse() throws java.io.IOException {
	for (int i=0; i<256; i++) {
		memory[i]=-1;
	}

	yylex();
}


public boolean isValid() {
	return ERROR_MESSAGE_TOTAL == 0;
}

public String errorMessages() {
	String temp = "---------------------------------------------\n" +
	"Errors in Memory (mem) File\n" +
	"---------------------------------------------\n" +
	ERROR_MESSAGES;
	/*if (!ERROR_DEFAULT_MESSAGE.equals("")) {
		temp += "Line ?: Syntax Error:" + ERROR_DEFAULT_MESSAGE + "\n";
	}*/
	temp += "---------------------------------------------\n" +
	ERROR_MESSAGE_TOTAL + " errors.  \n" +
	"---------------------------------------------\n";
	return temp;
}

public int[] getMemoryArray() {
	return memory;
}

/* **********************************************
* Private Data
********************************************** */

	int ERROR_MESSAGE_TOTAL = 0;
	String ERROR_MESSAGES = "";
	String ERROR_DEFAULT_MESSAGE = "";
	int memory[] = new int[256];
	int size = 0;
	int counter = 0;
	int memory_address = -1;

/* **********************************************
* Private Functions - Called By FLEX Code
********************************************** */

	/** Parse value (after ":") */
	int parseValue(String a) {
		if (a.length() == 8) {
			return string2int(a);
		} else {
			return Integer.parseInt(a);
		}
	}

	/** Convert an 8bit binary string to a int. */
	int string2int(String a) {
		String b = a.replaceAll(" ","");
		if (b.length() == 8) {
			int t = 0;
			char c[] = b.toCharArray();
			if (c[0]=='1') t -= 128;
			if (c[1]=='1') t += 64;
			if (c[2]=='1') t += 32;
			if (c[3]=='1') t += 16;
			if (c[4]=='1') t += 8;
			if (c[5]=='1') t += 4;
			if (c[6]=='1') t += 2;
			if (c[7]=='1') t += 1;
			return t;
		} else {
			return -129;
		}
	}


private void setNOTHING() {
}
private void setADDRESS() {
	String temp = yytext();
	temp = temp.replaceAll(":","");
	int addr = Integer.parseInt(temp);
	if (addr < 0 || addr > 255) {
		addError("Address " + addr + " out of range. (0-255)");
		memory_address = 0;
	} else {
		memory_address = addr;
	}
	//System.out.print(yytext());
}
private void setERROR() {
	addError("Syntax Error: " + yytext());
}
private void setBINARY_BYTE() {
	memory[memory_address] = string2int(yytext());
	//System.out.println(new Integer(memory[memory_address]));
}
private void setDECIMAL_BYTE() {
	int value = Integer.parseInt(yytext());
	if (value < -128 || value > 255) {
		addError("Value " + value + " out of range. (-128 -> 255)");
	} else {
		memory[MythSim.tc2normal(memory_address)] = MythSim.normal2tc(value);
	}
	//System.out.println(new Integer(memory[memory_address]));
}

private void setENDLINE() {
	//System.out.println("<ENDLINE>");
}

private void addError(String m) {
	ERROR_MESSAGE_TOTAL++;
	ERROR_MESSAGES += ("    Line " + (yyline+1) +
	": " + m + "\n");
}
private void addError(int l,String m) {
	ERROR_MESSAGE_TOTAL++;
	ERROR_MESSAGES += ("    Line " + l +
	": " + m + "\n");
}

%}

S = [ \t]+

%%

[0-9]*:									{setADDRESS();}
([01][ ]*){8} {setBINARY_BYTE();}
[0-9-]* {setDECIMAL_BYTE();}
(.[\n\r]*)*%		{setNOTHING();}
[/][/][^\n]*\n	    {setNOTHING();} 	/* comments */
[ \t]+			    {setNOTHING();}
,				    {setNOTHING();}
[\r\n]			    {setNOTHING();}
.			        {setERROR();}

