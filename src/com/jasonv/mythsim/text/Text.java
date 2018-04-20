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

package com.jasonv.mythsim.text;

import com.jasonv.mythsim.core.*;
/**
 * Printing Functions.
 * @author Jason Vroustouris
 */
public class Text {
	static public final String[] columnNames = {"ADDR",
	"r<k>write","Asel","Bsel","RIsel","RJsel","RKsel",
	"Cin","alusel","mdrsel","marsel","resultsel","ir0sel",
    "ir1sel","read","write","indexsel","cond","addrT","addrF"};






/*	static void ustore(int a[][]) {
		System.out.println("                                           r");
		System.out.println("                                           e               i");
		System.out.println("                                           s               n");
		System.out.println("                                   a   m m u               d");
		System.out.println("                         r r r     l   d a l   r r         e");
		System.out.println("                   a b   i j k     u   r r t   0 1     w   x");
		System.out.println("                                                     r r     c");
		System.out.println("                   s s   s s s   c s   s s s   s s   e i   s o");
		System.out.println("       r<k>write   e e   e e e   i e   e e e   e e   a t   e n   addr addr");
		System.out.println("addr   7654 3210   l l   l l l   n l   l l l   l l   d e   l d   true false");
		System.out.println("---------------------------------------------------------------------------");
		for (int i=0; i<a.length; i++) {
			if (i % 5 == 0 && i != 0) System.out.println("");
			if (i < 10) System.out.print("0");
			if (i < 100) System.out.print("0");
			if (i < 1000) System.out.print("0");
			System.out.print(i);
			System.out.print("   ");
			System.out.print(a[i][ControlWord.R7_WRITE]);
			System.out.print(a[i][ControlWord.R6_WRITE]);
			System.out.print(a[i][ControlWord.R5_WRITE]);
			System.out.print(a[i][ControlWord.R4_WRITE]);
			System.out.print(" ");
			System.out.print(a[i][ControlWord.R3_WRITE]);
			System.out.print(a[i][ControlWord.R2_WRITE]);
			System.out.print(a[i][ControlWord.R1_WRITE]);
			System.out.print(a[i][ControlWord.R0_WRITE]);
			System.out.print("   ");
			System.out.print(a[i][ControlWord.A_SEL]);
			System.out.print(" ");
			System.out.print(a[i][ControlWord.B_SEL]);
			System.out.print("   ");
			System.out.print(a[i][ControlWord.RI_SEL]);
			System.out.print(" ");
			System.out.print(a[i][ControlWord.RJ_SEL]);
			System.out.print(" ");
			System.out.print(a[i][ControlWord.RK_SEL]);
			System.out.print("   ");
			System.out.print(a[i][ControlWord.C_IN]);
			System.out.print(" ");
			System.out.print(a[i][ControlWord.ALU_SEL]);
			System.out.print("   ");
			System.out.print(a[i][ControlWord.MDR_SEL]);
			System.out.print(" ");
			System.out.print(a[i][ControlWord.MAR_SEL]);
			System.out.print(" ");
			System.out.print(a[i][ControlWord.RESULT_SEL]);
			System.out.print("   ");
			System.out.print(a[i][ControlWord.IR0_SEL]);
			System.out.print(" ");
			System.out.print(a[i][ControlWord.IR1_SEL]);
			System.out.print(" ");
			System.out.print("  ");
			System.out.print(a[i][ControlWord.READ]);
			System.out.print(" ");
			System.out.print(a[i][ControlWord.WRITE]);
			System.out.print("   ");
			System.out.print(a[i][ControlWord.INDEX_SEL]);
			System.out.print(" ");
			System.out.print(a[i][ControlWord.COND]);
			System.out.print("   ");
			if (a[i][ControlWord.ADDRESS_TRUE] < 10) System.out.print("0");
			if (a[i][ControlWord.ADDRESS_TRUE] < 100) System.out.print("0");
			if (a[i][ControlWord.ADDRESS_TRUE] < 1000) System.out.print("0");
			System.out.print(a[i][ControlWord.ADDRESS_TRUE]);
			System.out.print(" ");
			if (a[i][ControlWord.ADDRESS_FALSE] < 10) System.out.print("0");
			if (a[i][ControlWord.ADDRESS_FALSE] < 100) System.out.print("0");
			if (a[i][ControlWord.ADDRESS_FALSE] < 1000) System.out.print("0");
			System.out.print(a[i][ControlWord.ADDRESS_FALSE]);
			System.out.println("");
		}

	}*/
	public static void memory(int a[]) {
		for (int i=0; i<a.length; i++) {
			System.out.println(i + ": " + a[i]);
		}
	}
	public static void registers(int a[]) {
		System.out.print("  r0: "   + a[MythSim.R_0]);
		System.out.print("  r4: "   + a[MythSim.R_4]);
		System.out.println(" ir0: " + a[MythSim.IR_0]);

		System.out.print("  r1: "   + a[MythSim.R_1]);
		System.out.print("  r5: "   + a[MythSim.R_5]);
		System.out.println(" ir1: " + a[MythSim.IR_1]);

		System.out.print("  r2: "   + a[MythSim.R_2]);
		System.out.print("  r6: "   + a[MythSim.R_6]);
		System.out.println(" mdr: " + a[MythSim.MDR]);

		System.out.print("  r3: "   + a[MythSim.R_3]);
		System.out.print("  r7: "   + a[MythSim.R_7]);
		System.out.println(" mar: " + a[MythSim.MAR]);
	}

	public static void small(int a[]) {

		System.out.print  (" CLK:"  + tci(a[MythSim.CLOCK]));
		System.out.print  ("ADDR:" + tci(a[MythSim.CURRENT_ADDRESS]));
		System.out.println(" ir0:" + tci(a[MythSim.IR_0]));

		System.out.print  ("  r0:" + tci(a[MythSim.R_0]));
		System.out.print  ("  r4:" + tci(a[MythSim.R_4]));
		System.out.println(" ir0:" + tci(a[MythSim.IR_0]));

		System.out.print  ("  r1:" + tci(a[MythSim.R_1]));
		System.out.print  ("  r5:" + tci(a[MythSim.R_5]));
		System.out.println(" ir1:" + tci(a[MythSim.IR_1]));

		System.out.print  ("  r2:" + tci(a[MythSim.R_2]));
		System.out.print  ("  r6:" + tci(a[MythSim.R_6]));
		System.out.println(" mdr:" + tci(a[MythSim.MDR]));

		System.out.print  ("  r3:" + tci(a[MythSim.R_3]));
		System.out.print  ("  r7:" + tci(a[MythSim.R_7]));
		System.out.println(" mar:" + tci(a[MythSim.MAR]));
		for (int i=18; i<=43; i++) {
			System.out.println(MythSim.name(i) + ":" + a[i]);
		}


		System.out.println("");


	}

	public static String tci(int i) {
		String temp = ("" + i);
		if (i < 0) {
			if (i > -10) temp += " ";
			if (i > -100) temp += " ";
		} else {
			temp += " ";
			if (i < 10) temp += " ";
			if (i < 100) temp += " ";
		}
		return temp;
	}

	public static String address(int value) {
		String temp = "";
		if (value < 10) temp+="0";
		if (value < 100) temp+="0";
		if (value < 1000) temp+="0";
		temp += value;
		return temp;
	}


	public static String binString(int i) {
		String temp = new String();
		int a = i;
		if (a < 0)   {temp+="1"; a+=128;} else {temp+="0";}
		if (a >= 64) {temp+="1"; a-=64; } else {temp+="0";}
		if (a >= 32) {temp+="1"; a-=32; } else {temp+="0";}
		if (a >= 16) {temp+="1"; a-=16; } else {temp+="0";}
		temp+=" ";
		if (a >= 8)  {temp+="1"; a-=8;  } else {temp+="0";}
		if (a >= 4)  {temp+="1"; a-=4;  } else {temp+="0";}
		if (a >= 2)  {temp+="1"; a-=2;  } else {temp+="0";}
		if (a >= 1)  {temp+="1"; a-=1;  } else {temp+="0";}
		if (a == 0)  {return temp;} else {return "(invalid " + i + ")";}
	}

	public static String int2bin(int i) {
		String temp = new String();
		int a = i;
		if (a < 0)   {temp+="1"; a+=128;} else {temp+="0";}
		if (a >= 64) {temp+="1"; a-=64; } else {temp+="0";}
		if (a >= 32) {temp+="1"; a-=32; } else {temp+="0";}
		if (a >= 16) {temp+="1"; a-=16; } else {temp+="0";}
		temp+=" ";
		if (a >= 8)  {temp+="1"; a-=8;  } else {temp+="0";}
		if (a >= 4)  {temp+="1"; a-=4;  } else {temp+="0";}
		if (a >= 2)  {temp+="1"; a-=2;  } else {temp+="0";}
		if (a >= 1)  {temp+="1"; a-=1;  } else {temp+="0";}
		if (a == 0)  {return temp;} else {return "(invalid " + i + ")";}
	}


}
