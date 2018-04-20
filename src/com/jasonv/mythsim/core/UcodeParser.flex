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
%class UcodeParser
%line
%ignorecase
%{

/* **********************************************
* Control Line Constants
********************************************** */

	public static final int R0_WRITE          = 0;
	public static final int R1_WRITE          = 1;
	public static final int R2_WRITE          = 2;
	public static final int R3_WRITE          = 3;
	public static final int R4_WRITE          = 4;
	public static final int R5_WRITE          = 5;
	public static final int R6_WRITE          = 6;
	public static final int R7_WRITE          = 7;
	public static final int A_SEL             = 8;
	public static final int B_SEL             = 9;
	public static final int RI_SEL            = 10;
	public static final int RJ_SEL            = 11;
	public static final int RK_SEL            = 12;
	public static final int C_IN              = 13;
	public static final int ALU_SEL           = 14;
	public static final int MDR_SEL           = 15;
	public static final int MAR_SEL           = 16;
	public static final int RESULT_SEL        = 17;
	public static final int IR0_SEL           = 18;
	public static final int IR1_SEL           = 19;
	public static final int READ              = 20;
	public static final int WRITE             = 21;
	public static final int INDEX_SEL         = 22;
	public static final int COND              = 23;
	public static final int ADDRESS_TRUE      = 24;
	public static final int ADDRESS_FALSE     = 25;
	public static final int ADDRESS           = 26;
	public static final int R0_WRITE_SET      = 27;
	public static final int R1_WRITE_SET      = 28;
	public static final int R2_WRITE_SET      = 29;
	public static final int R3_WRITE_SET      = 30;
	public static final int R4_WRITE_SET      = 31;
	public static final int R5_WRITE_SET      = 32;
	public static final int R6_WRITE_SET      = 33;
	public static final int R7_WRITE_SET      = 34;
	public static final int A_SEL_SET         = 35;
	public static final int B_SEL_SET         = 36;
	public static final int RI_SEL_SET        = 37;
	public static final int RJ_SEL_SET        = 38;
	public static final int RK_SEL_SET        = 39;
	public static final int C_IN_SET          = 40;
	public static final int ALU_SEL_SET       = 41;
	public static final int MDR_SEL_SET       = 42;
	public static final int MAR_SEL_SET       = 43;
	public static final int RESULT_SEL_SET    = 44;
	public static final int IR0_SEL_SET       = 45;
	public static final int IR1_SEL_SET       = 46;
	public static final int READ_SET          = 47;
	public static final int WRITE_SET         = 48;
	public static final int INDEX_SEL_SET     = 49;
	public static final int COND_SET          = 50;
	public static final int ADDRESS_TRUE_SET  = 51;
	public static final int ADDRESS_FALSE_SET = 52;
	public static final int ADDRESS_SET       = 53;
	public static final int CONTROL_WORD_LENGTH = 54;

/* **********************************************
* Extended Control Line Constants
********************************************** */

	public static final int	LABEL		  = 27;
	public static final int GOTO_OPCODE	  = 28;
	public static final int GOTO_LABEL	  = 29;
	public static final int IF_THEN		  = 30;
	public static final int IF_THEN_ELSE  = 31;
	public static final int INIT		  = 32;
	public static final int	SEMICOLON	  = 33;
	public static final int	RESOLVE		  = 34;
	public static final int	NOSEMICOLON   = 35;
	public static final int	NOTHING		  = 36;
	public static final int	ERROR		  = 37;
	public static final int	BREAK		  = 38;

/* **********************************************
* Control Line Constant String Array
********************************************** */

	public static final String ControlLine_Name[] = {"R0_WRITE",
	"R1_WRITE", "R2_WRITE", "R3_WRITE", "R4_WRITE",
	"R5_WRITE", "R6_WRITE", "R7_WRITE", "A_SEL", "B_SEL",
	"RI_SEL","RJ_SEL","RK_SEL","C_IN","ALU_SEL","MDR_SEL","MAR_SEL",
	"RESULT_SEL","IR0_SEL","IR1_SEL","READ","WRITE","INDEX_SEL",
	"COND","ADDRESS_TRUE","ADDRESS_FALSE","ADDRESS","LABEL",
	"GOTO_OPCODE","GOTO_LABEL","IF_THEN","IF_THEN_ELSE","INIT","SEMICOLON",
	"RESOLVE","NOSEMICOLON","NOTHING","ERROR","BREAK"};

/* **********************************************
* Public Functions
********************************************** */

public void parse() throws java.io.IOException {
	yylex();
	// Resolve Addresses
	String label[] = new String[ucodememory.size()];
	for (int i=0; i<ucodememory.size(); i++) {
		label[i] = ((ControlWord)ucodememory.get(i)).label();
	}
	for (int i=0; i<ucodememory.size(); i++) {
		ControlWord cw = ((ControlWord)ucodememory.get(i));
		if (cw.resolve_addr(label)) {}
		if (cw.getTrueLabel().equals(cw.getFalseLabel())) {
			if(!cw.validTrueLabel()) {
				addError(cw.getLineNumber(),cw.getTrueLabel() + " not found.");
			} else {
				if(!cw.validFalseLabel()) {
					addError(cw.getLineNumber(),cw.getFalseLabel() + " not found.");
				}
			}
		} else {
			if(!cw.validTrueLabel()) {
				addError(cw.getLineNumber(),cw.getTrueLabel() + " not found.");
			}
			if(!cw.validFalseLabel()) {
				addError(cw.getLineNumber(),cw.getFalseLabel() + " not found.");
			}
		}
	}
}

public Vector getUcodeVector() {
	System.out.println("getUcodeVector");
	return ucodememory;
}

public boolean isValid() {
	return ERROR_MESSAGE_TOTAL == 0;
}

public String errorMessages() {
	String temp = "---------------------------------------------\n" +
	"Errors in Microcode (ucode) File\n" +
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


public void ustore() {
	ControlWord.printHead();
	for (int i=0; i<ucodememory.size(); i++) {
		if (i % 5 == 0 && i != 0) System.out.println("");
		((ControlWord)ucodememory.get(i)).print();
	}
}

/* **********************************************
* Private Data
********************************************** */

	int ControlLine[] = new int[39];
	boolean ControlLine_Set[] = new boolean[ControlLine.length];
	boolean Branch_Set = false;
	int ERROR_MESSAGE_TOTAL = 0;
	String ERROR_MESSAGES = "";
	String ERROR_DEFAULT_MESSAGE = "";
	ControlWord cw = new ControlWord();
	Vector ucodememory = new Vector();

/* **********************************************
* Private Functions - Called By FLEX Code
********************************************** */

private void set(int index, int value)
{
	if (ControlLine_Set[index]) {
		addError(ControlLine_Name[index] +
		" already defined on this line.");
	} else {
		cw.set(index,value);
		ControlLine_Set[index] = true;
	}
}

private void setSEMICOLON() {
	cw.setAddress(ucodememory.size());
	cw.setLineNumber(yyline+1);
	ucodememory.add(cw);
	cw = new ControlWord();
	for (int i=0; i<ControlLine_Set.length; i++) {
		ControlLine_Set[i] = false;
	}
	Branch_Set = false;
}

private String getLabel(int i) {
	String temp = yytext();
	temp.replaceAll("  "," ");
	String temp2[] = temp.split(" ");
	if (i<temp2.length) {
		return temp2[i];
	} else {
		addError("Goto has no target label.");
		return "n/a";
	}
}

private void setERROR() {
	addError("Syntax Error: " + yytext());
	return;
}

private void setBREAK() {
	return;
}

private void setNOTHING() {
	return;
}

private void addDefaultError() {
	ERROR_DEFAULT_MESSAGE += yytext();
	return;
}

private void setLABEL() {
	if (ControlLine_Set[LABEL]) {
		addError("A label was already defined on this line.");
	} else {
		ControlLine_Set[LABEL] = true;
		String label = yytext();
		label = label.replaceAll(":","");
		cw.setLabel(label);
	}
}

private void setGOTO_OPCODE() {
	if (Branch_Set) {
		Branch_Set = true;
		addError("GOTO already defined on this line.");
	} else {
		//addError("GOTO_OPCODE on this line.");
		cw.set(ControlWord.INDEX_SEL,1);
	}
}

private void setGOTO_LABEL() {
	if (Branch_Set) {
		addError("GOTO already defined on this line.");
	} else {
		//addError("GOTO_LABEL on this line.");
		Branch_Set = true;
		String label = getLabel(1);
		cw.setTrueLabel(label);
		cw.setFalseLabel(label);
	}
}

private void setIF_THEN(int i) {
	if (Branch_Set) {
		addError("GOTO already defined on this line.");
	} else {
		//addError("IF_THEN on this line.");
		Branch_Set = true;
		cw.setTrueLabel(getLabel(4));
		cw.set(ControlWord.COND,i);
	}
}

private void setIF_THEN_ELSE(int i) {
	if (Branch_Set) {
		addError("GOTO already defined on this line.");
	} else {
		//addError("IF_THEN_ELSE on this line.");
		Branch_Set = true;
		cw.setTrueLabel(getLabel(4));
		cw.setFalseLabel(getLabel(7));
		cw.set(ControlWord.COND,i);
	}
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

EQUALS = [ \t]*[=][ \t]*
LABEL = ([_a-zA-Z0-9]+[_a-zA-Z0-9.]*)|(opcode\[[0-9]+\])
S = [ \t]+

%%

{LABEL}:									{setLABEL();}

r0_write									{set(R0_WRITE, 1);}
r1_write									{set(R1_WRITE, 1);}
r2_write									{set(R2_WRITE, 1);}
r3_write									{set(R3_WRITE, 1);}
r4_write									{set(R4_WRITE, 1);}
r5_write									{set(R5_WRITE, 1);}
r6_write									{set(R6_WRITE, 1);}
r7_write									{set(R7_WRITE, 1);}
a_sel{EQUALS}0								{set(A_SEL, 0);}
a_sel{EQUALS}1								{set(A_SEL, 1);}
a_sel{EQUALS}2								{set(A_SEL, 2);}
a_sel{EQUALS}3								{set(A_SEL, 3);}
a_sel{EQUALS}4								{set(A_SEL, 4);}
a_sel{EQUALS}5								{set(A_SEL, 5);}
a_sel{EQUALS}6								{set(A_SEL, 6);}
a_sel{EQUALS}7								{set(A_SEL, 7);}
b_sel{EQUALS}0								{set(B_SEL, 0);}
b_sel{EQUALS}1								{set(B_SEL, 1);}
b_sel{EQUALS}2								{set(B_SEL, 2);}
b_sel{EQUALS}3								{set(B_SEL, 3);}
b_sel{EQUALS}4								{set(B_SEL, 4);}
b_sel{EQUALS}5								{set(B_SEL, 5);}
b_sel{EQUALS}6								{set(B_SEL, 6);}
b_sel{EQUALS}7								{set(B_SEL, 7);}
ri_sel										{set(RI_SEL, 1);}
rj_sel										{set(RJ_SEL, 1);}
rk_sel										{set(RK_SEL, 1);}
c_in										{set(C_IN, 1);}
alu_sel{EQUALS}(0|NOT)						{set(ALU_SEL, 0);}
alu_sel{EQUALS}(1|OR)						{set(ALU_SEL, 1);}
alu_sel{EQUALS}(2|AND)						{set(ALU_SEL, 2);}
alu_sel{EQUALS}(3|XOR)						{set(ALU_SEL, 3);}
alu_sel{EQUALS}(4|ADD)						{set(ALU_SEL, 4);}
alu_sel{EQUALS}(5|SUB)						{set(ALU_SEL, 5);}
alu_sel{EQUALS}(6|ADDA)						{set(ALU_SEL, 6);}
alu_sel{EQUALS}(7|SUBA)						{set(ALU_SEL, 7);}
mdr_sel{EQUALS}(0|HOLD)						{set(MDR_SEL, 0);}
mdr_sel{EQUALS}(1|LOAD_ALU)					{set(MDR_SEL, 1);}
mdr_sel{EQUALS}(2|LOAD_MEM)					{set(MDR_SEL, 2);}
mar_sel{EQUALS}(0|HOLD)						{set(MAR_SEL, 0);}
mar_sel{EQUALS}(1|LOAD)						{set(MAR_SEL, 1);}
result_sel{EQUALS}(0|ALU)					{set(RESULT_SEL, 0);}
result_sel{EQUALS}(1|MDR)					{set(RESULT_SEL, 1);}
result_sel{EQUALS}(2|IR_CONST4)				{set(RESULT_SEL, 2);}
result_sel{EQUALS}(3|IR_CONST8)				{set(RESULT_SEL, 3);}
ir0_sel{EQUALS}(0|HOLD)						{set(IR0_SEL, 0);}
ir0_sel{EQUALS}(1|LOAD)						{set(IR0_SEL, 1);}
ir1_sel{EQUALS}(0|HOLD)						{set(IR1_SEL, 0);}
ir1_sel{EQUALS}(1|LOAD)						{set(IR1_SEL, 1);}
read										{set(READ, 1);}
write										{set(WRITE, 1);}

goto{S}opcode\[IR_OPCODE\]					{setGOTO_OPCODE();}
goto{S}{LABEL} 								{setGOTO_LABEL();}

if{S}m_7{S}then{S}goto{S}{LABEL}{S}endif	{setIF_THEN(0);}
if{S}c_out{S}then{S}goto{S}{LABEL}{S}endif	{setIF_THEN(1);}
if{S}v{S}then{S}goto{S}{LABEL}{S}endif		{setIF_THEN(2);}
if{S}wait{S}then{S}goto{S}{LABEL}{S}endif	{setIF_THEN(3);}

if{S}m_7{S}then{S}goto{S}{LABEL}{S}else{S}goto{S}{LABEL}{S}endif	{setIF_THEN_ELSE(0);}
if{S}c_out{S}then{S}goto{S}{LABEL}{S}else{S}goto{S}{LABEL}{S}endif  {setIF_THEN_ELSE(1);}
if{S}v{S}then{S}goto{S}{LABEL}{S}else{S}goto{S}{LABEL}{S}endif		{setIF_THEN_ELSE(2);}
if{S}wait{S}then{S}goto{S}{LABEL}{S}else{S}goto{S}{LABEL}{S}endif	{setIF_THEN_ELSE(3);}

[bB][ \t]*;		{setBREAK();}
;[ \t]*[bB]		{setBREAK();}
;				{setSEMICOLON();}

[/][/][^\n]*\n	{setNOTHING();} 	/* comments */

[ \t]+			{setNOTHING();}
[\r]			{setNOTHING();}
[\n]			{setNOTHING();}
,				{setNOTHING();}

.			    {setERROR();}

