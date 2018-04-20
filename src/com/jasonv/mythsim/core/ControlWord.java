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
 * A Control Word.
 * @author Jason Vroustouris
 */
public class ControlWord {

  public static final int R0_WRITE = 0;
  public static final int R1_WRITE = 1;
  public static final int R2_WRITE = 2;
  public static final int R3_WRITE = 3;
  public static final int R4_WRITE = 4;
  public static final int R5_WRITE = 5;
  public static final int R6_WRITE = 6;
  public static final int R7_WRITE = 7;
  public static final int A_SEL = 8;
  public static final int B_SEL = 9;
  public static final int RI_SEL = 10;
  public static final int RJ_SEL = 11;
  public static final int RK_SEL = 12;
  public static final int C_IN = 13;
  public static final int ALU_SEL = 14;
  public static final int MDR_SEL = 15;
  public static final int MAR_SEL = 16;
  public static final int RESULT_SEL = 17;
  public static final int IR0_SEL = 18;
  public static final int IR1_SEL = 19;
  public static final int READ = 20;
  public static final int WRITE = 21;
  public static final int INDEX_SEL = 22;
  public static final int COND = 23;
  public static final int ADDRESS_TRUE = 24;
  public static final int ADDRESS_FALSE = 25;
  public static final int ADDRESS = 26;
  public static final int R0_WRITE_SET = 27;
  public static final int R1_WRITE_SET = 28;
  public static final int R2_WRITE_SET = 29;
  public static final int R3_WRITE_SET = 30;
  public static final int R4_WRITE_SET = 31;
  public static final int R5_WRITE_SET = 32;
  public static final int R6_WRITE_SET = 33;
  public static final int R7_WRITE_SET = 34;
  public static final int A_SEL_SET = 35;
  public static final int B_SEL_SET = 36;
  public static final int RI_SEL_SET = 37;
  public static final int RJ_SEL_SET = 38;
  public static final int RK_SEL_SET = 39;
  public static final int C_IN_SET = 40;
  public static final int ALU_SEL_SET = 41;
  public static final int MDR_SEL_SET = 42;
  public static final int MAR_SEL_SET = 43;
  public static final int RESULT_SEL_SET = 44;
  public static final int IR0_SEL_SET = 45;
  public static final int IR1_SEL_SET = 46;
  public static final int READ_SET = 47;
  public static final int WRITE_SET = 48;
  public static final int INDEX_SEL_SET = 49;
  public static final int COND_SET = 50;
  public static final int ADDRESS_TRUE_SET = 51;
  public static final int ADDRESS_FALSE_SET = 52;
  public static final int ADDRESS_SET = 53;
  public static final int CONTROL_WORD_LENGTH = 54;

  int ControlLine[] = new int[CONTROL_WORD_LENGTH];
  boolean ControlLine_Set[] = new boolean[ControlLine.length];
  String _original_line = "";

  public int get(int index) {
    return ControlLine[index];
  }

  public boolean isSet(int index) {
    return (ControlLine[index] == 1);
  }

  public void set(int index) throws MythParserError {
    set(index, 1);
  }

  public void print(int index) {
    System.out.print(ControlLine[index]);
  }

  public int[] toArray() {
    return ControlLine;
  }

  public String line() {
    return _original_line;
  }

//*****************************************************************************
// EXTENDED INORMATION
//*****************************************************************************

    public static final String alu_value[] = {
        "NOT", "OR", "AND", "XOR", "ADD", "SUB", "ADDA", "SUBA"};
  public static final String mar_value[] = {
      "HOLD", "LOAD"};
  public static final String mdr_value[] = {
      "HOLD", "LOAD_ALU", "LOAD_MEM"};
  public static final String result_value[] = {
      "ALU", "MDR", "IR_CONST4", "IR_CONST8"};
  public static final String ir_value[] = {
      "HOLD", "LOAD"};
  public static final String cond_value[] = {
      "M_7", "C_OUT", "V", "WAIT"};
  public static final String control_line_value[] = {
      "R0_WRITE",
      "R1_WRITE", "R2_WRITE", "R3_WRITE", "R4_WRITE",
      "R5_WRITE", "R6_WRITE", "R7_WRITE", "A_SEL", "B_SEL",
      "RI_SEL", "RJ_SEL", "RK_SEL", "C_IN", "ALU_SEL", "MDR_SEL", "MAR_SEL",
      "RESULT_SEL", "IR0_SEL", "IR1_SEL", "READ", "WRITE", "INDEX_SEL",
      "COND", "ADDRESS_TRUE", "ADDRESS_FALSE", "ADDRESS"};

  /* original line from ucode file */
  String original_line = "";

  public void setAddress(int a) {
    _address = a;
  }

  public void setLabel(String s) {
    _label = s;
  }

  public void setTrueLabel(String s) {
    _address_true_string = s;
  }

  public void setFalseLabel(String s) {
    _address_false_string = s;
  }

  public void setLineNumber(int i) {
    _line_number = i;
  }

  public int getLineNumber() {
    return _line_number;
  }

  public String getTrueLabel() {
    return _address_true_string;
  }

  public String getFalseLabel() {
    return _address_false_string;
  }

  /* addressing values*/
  public String address() {
    String temp = "";
    if (_address < 10) {
      temp += "0";
    }
    if (_address < 100) {
      temp += "0";
    }
    if (_address < 1000) {
      temp += "0";
    }
    temp += _address;
    return temp;
  }

  public void print(String a) {
    System.out.print(a);
  }

  public void printAddress() {
    System.out.print(address());
  }

  int _address = 0;

  public String label() {
    return _label;
  }

  String _label = "";
  boolean _true_label_found = false;
  boolean _false_label_found = false;
  int _line_number = -1;
  String _address_string = "";
  String _address_true_string = "";
  String _address_true_offset_string = "";
  String _address_false_string = "";
  String _address_false_offset_string = "";

  /** creates an empty control word */
  public ControlWord() {
    for (int i = 0; i < ControlLine.length; i++) {
      ControlLine[i] = 0;
    }
  }

  /*public ControlWord(int a, String t) throws Exception {
   _address = a;
   _original_line = t;
   for (int i=0; i<ControlLine.length; i++) {
    ControlLine[i] = 0;
    ControlLine_Set[i] = false;
   }
   String array[] = t.split("[,:]");
   _label = array[0].toUpperCase().trim();
   String nospace = "";
   for (int i=0; i<array.length; i++) {
    nospace = array[i].replaceAll(" ","");
    nospace = nospace.toUpperCase();
    nospace = nospace.trim();
    //System.out.println(nospace);
    if (nospace.equalsIgnoreCase("ri_sel")) set(RI_SEL);
    if (nospace.equalsIgnoreCase("rj_sel")) set(RJ_SEL);
    if (nospace.equalsIgnoreCase("rk_sel")) set(RK_SEL);
    if (nospace.equalsIgnoreCase("a_sel=0")) set(A_SEL,0);
    if (nospace.equalsIgnoreCase("a_sel=1")) set(A_SEL,1);
    if (nospace.equalsIgnoreCase("a_sel=2")) set(A_SEL,2);
    if (nospace.equalsIgnoreCase("a_sel=3")) set(A_SEL,3);
    if (nospace.equalsIgnoreCase("a_sel=4")) set(A_SEL,4);
    if (nospace.equalsIgnoreCase("a_sel=5")) set(A_SEL,5);
    if (nospace.equalsIgnoreCase("a_sel=6")) set(A_SEL,6);
    if (nospace.equalsIgnoreCase("a_sel=7")) set(A_SEL,7);
    if (nospace.equalsIgnoreCase("a_sel=8")) throw new MythParserError("ucode",11,"this is a test");
    // B_SEL check
    for (int j=0; j<8; j++) {
     if (nospace.equalsIgnoreCase("b_sel=" + j)) {
      set(B_SEL,j);
     }
    }
    // C_IN check
    if (nospace.equalsIgnoreCase("c_in")) {
     set(C_IN);
    }
    // R_WRITE check
    for (int j=0; j<8; j++) {
     if (nospace.equalsIgnoreCase("r" + j + "_write")) {
      set(R0_WRITE+j);
     }
    }
    // ALU_SEL check
    for (int j=0; j<8; j++) {
     if (nospace.equalsIgnoreCase("alu_sel=" + alu_value[j])) {
      //System.out.println("alu_sel:" + alu_value[j] + "," + j + "," + nospace);
      set(ALU_SEL,j);
     }
    }
    // MAR_SEL check
    for (int j=0; j<2; j++) {
     if (nospace.equalsIgnoreCase("mar_sel=" + mar_value[j])) {
      set(MAR_SEL,j);
     }
    }
    // MDR_SEL check
    for (int j=0; j<3; j++) {
     if (nospace.equalsIgnoreCase("mdr_sel=" + mdr_value[j])) {
      set(MDR_SEL,j);
     }
    }
    // RESULT_SEL check
    for (int j=0; j<4; j++) {
     if (nospace.equalsIgnoreCase("result_sel=" + result_value[j])) {
      set(RESULT_SEL,j);
     }
    }
    if (nospace.equalsIgnoreCase("ir0_sel=LOAD")) {set(IR0_SEL);}
    if (nospace.equalsIgnoreCase("ir1_sel=LOAD")) {set(IR1_SEL);}
    if (nospace.equalsIgnoreCase("read")) {set(READ);}
    if (nospace.equalsIgnoreCase("write")) {set(WRITE);}
    if (nospace.equalsIgnoreCase("index_sel")) {set(INDEX_SEL);}
    // COND check
    for (int j=0; j<4; j++) {
     //System.out.println("cond " + j + "," + cond_value[j] + "," + nospace);
     if (nospace.startsWith("IF" + cond_value[j])) {
      //System.out.println("cond set to " + j);
      set(COND,j);
     }
    }
    // GOTO check
    if (nospace.startsWith("GOTO")) {
     String line[] = nospace.split("GOTO");
     String address_array[] = new String[1];
     if (line.length == 2) {
      address_array = line[1].split("\\[|\\]");
      if (address_array.length == 1) {
       _address_true_string = address_array[0].trim();
       _address_false_string = address_array[0].trim();
      }
      if (address_array.length == 2) {
       //_address_true_string = address_array[0].trim();
       _address_true_string = line[1].trim();
       //_address_false_string = address_array[0].trim();
       _address_false_string = line[1].trim();
       _address_true_offset_string = address_array[1];
       _address_false_offset_string = address_array[1];
       if (_address_true_offset_string.equalsIgnoreCase("IR_OPCODE")) {
        set(INDEX_SEL);
       }
       //System.out.println("goto:" + nospace + "," + _address_true_offset_string);
      }
     }
    }
    // If check
    if (nospace.startsWith("IF")) {
     String line[] = nospace.split("THENGOTO|ELSEGOTO|ENDIF") ;
     String address_array[] = new String[1];
     // if [0] then goto [1] endif;
     if (line.length == 2) {
      address_array = line[1].split("\\[|\\]");
      if (address_array.length == 1) {
       _address_true_string = address_array[0].trim();
      }
      if (address_array.length == 2) {
       //_address_true_string = address_array[0].trim();
       _address_true_string = line[1].trim();
       _address_true_offset_string = address_array[1];
      }
     }
     // if [0] then goto [1] else goto [2] endif;
     if (line.length == 3) {
      address_array = line[1].split("\\[|\\]");
      if (address_array.length == 1) {
       _address_true_string = address_array[0].trim();
      }
      if (address_array.length == 2) {
       //_address_true_string = address_array[0].trim();
       _address_true_string = line[1].trim();
       _address_true_offset_string = address_array[1];
      }
      address_array = line[2].split("\\[|\\]");
      if (address_array.length == 1) {
       _address_false_string = address_array[0].trim();
      }
      if (address_array.length == 2) {
       //_address_false_string = address_array[0].trim();
       _address_false_string = line[2].trim();
       _address_false_offset_string = address_array[1];
      }
     }
    }
   }
   //System.out.println("==========");
   //printHead();
   //print();
   //System.out.println(t);
    }*/

  public void set(int index, int value) {
    ControlLine[index] = value;
    ControlLine[R0_WRITE_SET+index] = 1;
  }

  public static void printHead() {
    System.out.println("                                           r");
    System.out.println(
        "                                           e               i");
    System.out.println(
        "                                           s               n");
    System.out.println(
        "                                   a   m m u               d");
    System.out.println(
        "                         r r r     l   d a l   r r         e");
    System.out.println(
        "                   a b   i j k     u   r r t   0 1     w   x");
    System.out.println(
        "                                                     r r     c");
    System.out.println(
        "                   s s   s s s   c s   s s s   s s   e i   s o");
    System.out.println(
        "       r<k>write   e e   e e e   i e   e e e   e e   a t   e n   addr addr");
    System.out.println(
        "addr   7654 3210   l l   l l l   n l   l l l   l l   d e   l d   true false");
    System.out.println(
        "---------------------------------------------------------------------------");
  }

  public void print() {
    printAddress();
    print("   ");
    print(R7_WRITE);
    print(R6_WRITE);
    print(R5_WRITE);
    print(R4_WRITE);
    print(" ");
    print(R3_WRITE);
    print(R2_WRITE);
    print(R1_WRITE);
    print(R0_WRITE);
    print("   ");
    print(A_SEL);
    print(" ");
    print(B_SEL);
    print("   ");
    print(RI_SEL);
    print(" ");
    print(RJ_SEL);
    print(" ");
    print(RK_SEL);
    print("   ");
    print(C_IN);
    print(" ");
    print(ALU_SEL);
    print("   ");
    print(MDR_SEL);
    print(" ");
    print(MAR_SEL);
    print(" ");
    print(RESULT_SEL);
    print("   ");
    print(IR0_SEL);
    print(" ");
    print(IR1_SEL);
    print("   ");
    print(READ);
    print(" ");
    print(WRITE);
    print("   ");
    print(INDEX_SEL);
    print(" ");
    print(COND);
    print("   ");
    if (get(ADDRESS_TRUE) < 10) {
      System.out.print("0");
    }
    if (get(ADDRESS_TRUE) < 100) {
      System.out.print("0");
    }
    if (get(ADDRESS_TRUE) < 1000) {
      System.out.print("0");
    }
    print(ADDRESS_TRUE);
    System.out.print(" ");
    if (get(ADDRESS_FALSE) < 10) {
      System.out.print("0");
    }
    if (get(ADDRESS_FALSE) < 100) {
      System.out.print("0");
    }
    if (get(ADDRESS_FALSE) < 1000) {
      System.out.print("0");
    }
    print(ADDRESS_FALSE);
    print("\n");
    System.out.println("(" + _label + "," + _address_true_string + "," +
                       _address_false_string + ")");
  }

  public void print_addr() {
    System.out.print("l=" + _label);
    System.out.print(" at=" + _address_true_string);
    System.out.print(" ato=" + _address_true_offset_string);
    System.out.print(" af=" + _address_false_string);
    System.out.print(" afo=" + _address_false_offset_string);
    System.out.println("");
  }

  public boolean resolve_addr(String a[]) {
    //System.out.print("ra");
    for (int i = 0; i < a.length; i++) {
      //System.out.println(a[i] + "," + _address_true_string + "," + _address_false_string);
      if (a[i].equalsIgnoreCase(_address_true_string)) {
        //System.out.println("true_address");
        ControlLine[ADDRESS_TRUE] = i;
        _true_label_found = true;
      }
      if (a[i].equalsIgnoreCase(_address_false_string)) {
        //System.out.println("false_address");
        ControlLine[ADDRESS_FALSE] = i;
        _false_label_found = true;
      }
    }
    if (_address_true_string.equals("")) {
      ControlLine[ADDRESS_TRUE] = _address + 1;
      _true_label_found = true;
    }
    if (_address_false_string.equals("")) {
      ControlLine[ADDRESS_FALSE] = _address + 1;
      _false_label_found = true;
    }
    //System.out.println("(" + _address + "," + ControlLine[ADDRESS_TRUE] + "," + ControlLine[ADDRESS_FALSE] + ")");
    if (_true_label_found && _false_label_found) {
      return true;
    }
    else {
      return false;
    }
  }

  public boolean validTrueLabel() {
    return _true_label_found;
  }

  public boolean validFalseLabel() {
    return _false_label_found;
  }
}