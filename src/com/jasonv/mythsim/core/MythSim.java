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

import java.io.*;

/**
 * The CPU Simulator.
 * @author Jason Vroustouris
 * @todo Change next opcode into a breakpoint system.
 */
public class MythSim {

  /** The curent name and version number. */
  public static final String version_title = "MythSim 3.1.1";

  /**
   * Creates a MythSim simulator.
   */
  public MythSim() {
    System.out.print(
        version_title + " - https://github.com/jasonv/MythSim\n" +
        "\n" +
        "Copyright (C) 2002-2018 Jason Vroustouris\n" +
        "\n" +
        "This program is free software; you can redistribute it and/or modify\n" +
        "it under the terms of the GNU General Public License as published by\n" +
        "the Free Software Foundation; either version 2 of the License, or\n" +
        "(at your option) any later version.\n" +
        "\n" +
        "This program is distributed in the hope that it will be useful,\n" +
        "but WITHOUT ANY WARRANTY; without even the implied warranty of\n" +
        "MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n" +
        "GNU General Public License for more details.\n" +
        "\n" +
        "You should have received a copy of the GNU General Public License\n" +
        "along with this program; if not, write to the Free Software\n" +
        "Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA\n");

  }
  /* ******************************************************
   Setup (constructors and variables)
   ****************************************************** */

  /** The status array. */
  int s[] = new int[MythSim.LENGTH];

  /** The previous status array. */
  int p[] = new int[MythSim.LENGTH];

  /** The message string. */
  String message = "";

  /** The micoprogram in array form. */
  int _ucode[][] = new int[1][MythSim.CONTROL_WORD_LENGTH];

  /** The memory source file. */
  File memoryFile = new File(".");
  String memorySource = "";

  /** The microcode source file. */
  File microcodeFile = new File(".");
  String microcodeSource = "";

  /** Sets the Microcode File */
  public void setFileMicrocode(File file) throws FileNotFoundException {
    microcodeFile = file;
    FileReader fr = new FileReader(microcodeFile);
    try {
      fr.close();
    }
    catch (IOException ex) {
      System.out.print("Microcode file cant be closed.");
    }
  }

  /** Sets the Memory File */
  public void setFileMemory(File file) throws FileNotFoundException {
    memoryFile = file;
    FileReader fr = new FileReader(memoryFile);
    try {
      fr.close();
    }
    catch (IOException ex) {
      System.out.print("Memory file cant be closed.");
    }
  }

  MythParser mp = new MythParser();

  /* ******************************************************
   Infomation
   ****************************************************** */

  /** @return memory source file in string */
  public String getMemorySource() {
    return memorySource;
  }

  /** @return microcode source file in string */
  public String getMicrocodeSource() {
    return microcodeSource;
  }

  /** @return memory source file path */
  public File getMemoryFile() {
    return memoryFile;
  }

  /** @return microcode source file path */
  public File getMicrocodeFile() {
    return microcodeFile;
  }

  /** @return memory source file path */
  public String getMemoryFilePath() {
    return memoryFile.getAbsolutePath();
  }

  /** @return microcode source file path */
  public String getMicrocodeFilePath() {
    return microcodeFile.getAbsolutePath();
  }

  /** @return memory source file name */
  public String getMemoryFileName() {
    return memoryFile.getName();
  }

  /** @return microcode source file name */
  public String getMicrocodeFileName() {
    return microcodeFile.getName();
  }

  /* ******************************************************
   Status Array (constants and functions)
   ****************************************************** */
  /* ======================================================
   Registers
   ===================================================== */
  /**
   * <b>Register:</b> 0.
   * <p>Location in the status array for the value in Register 0.</p>
   * */
  public static final int R_0 = 0;
  /**
   * <b>Register:</b> 1.
   * <p>Location in the status array for the value in Register 1.</p>
   * */
  public static final int R_1 = 1;
  /**
   * <b>Register:</b> 2.
   * <p>Location in the status array for the value in Register 2.</p>
   * */
  public static final int R_2 = 2;
  /**
   * <b>Register:</b> 3.
   * <p>Location in the status array for the value in Register 3.</p>
   * */
  public static final int R_3 = 3;
  /**
   * <b>Register:</b> 4.
   * <p>Location in the status array for the value in Register 4.</p>
   * */
  public static final int R_4 = 4;
  /**
   * <b>Register:</b> 5.
   * <p>Location in the status array for the value in Register 5.</p>
   * */
  public static final int R_5 = 5;
  /**
   * <b>Register:</b> 6.
   * <p>Location in the status array for the value in Register 6.</p>
   * */
  public static final int R_6 = 6;
  /**
   * <b>Register:</b> 7.
   * <p>Location in the status array for the value in Register 7.</p>
   * */
  public static final int R_7 = 7;
  /**
   * Register: Instruction Register 0.
   * <p>Location in the status array for the value in
   * Instruction Register 0.</p>
   * */
  public static final int IR_0 = 8;
  /**
   * Register: Instruction Register 1.
   * <p>Location in the status array for the value in Instruction
   * Register 1.</p>
   * */
  public static final int IR_1 = 9;
  /**
   * Register: Memory Data Register.
   * <p>Location in the status array for the value in Memory Data
   * Register.</p>
   * */
  public static final int MDR = 10;
  /**
   * Register: Memory Address Register.
   * <p>Location in the status array for the value in Memory Address
   * Register.</p>
   * */
  public static final int MAR = 11;
  /* ======================================================
   Busses
   ===================================================== */
  /**
   * <b>Bus:</b> ALU (Arithmetic Logic Unit) Output Bus.
   * <p>Location in the status array for the value in alu_bus.
   * Register.</p>
   * */
  public static final int ALU_BUS = 12;
  /**
   * <b>Bus:</b> ALU (Arithmetic Logic Unit) Input A Bus.
   * <p>Location in the status array for the value in a_bus.
   * Register.</p>
   * */
  public static final int A_BUS = 13;
  /**
   * <b>Bus:</b> ALU (Arithmetic Logic Unit) Input B Bus.
   * <p>Location in the status array for the value in b_bus.
   * Register.</p>
   * */
  public static final int B_BUS = 14;
  /**
   * <b>Bus:</b> Result Bus.
   * <p>Location in the status array for the value in result_bus.
   * Register.</p>
   * */
  public static final int RESULT_BUS = 15;
  /**
   * <b>Bus:</b> Write Bus.
   * <p>Location in the status array for the value in write_bus.
   * Register.</p>
   * */
  public static final int WRITE_BUS = 16;
  /**
   * <b>Bus:</b> Memory Bus.
   * <p>Location in the status array for the value in mem_bus.
   * Register.</p>
   * */
  public static final int MEMORY_BUS = 17;
  /* ======================================================
   Control Signals
   ===================================================== */
  /**
   * <b>Control Signal:</b> Stores the result_bus value into r_0.
   * <p>Stores the index of this control signal.</p>
   * */
  public static final int R0_WRITE = 18;
  /**
   * <b>Control Signal:</b> Stores the result_bus value into r_1.
   * <p>Stores the index of this control signal.</p>
   * */
  public static final int R1_WRITE = 19;
  /**
   * <b>Control Signal:</b> Stores the result_bus value into r_2.
   * <p>Stores the index of this control signal.</p>
   * */
  public static final int R2_WRITE = 20;
  /**
   * <b>Control Signal:</b> Stores the result_bus value into r_3.
   * <p>Stores the index of this control signal.</p>
   * */
  public static final int R3_WRITE = 21;
  /**
   * <b>Control Signal:</b> Stores the result_bus value into r_4.
   * <p>Stores the index of this control signal.</p>
   * */
  public static final int R4_WRITE = 22;
  /**
   * <b>Control Signal:</b> Stores the result_bus value into r_5.
   * <p>Stores the index of this control signal.</p>
   * */
  public static final int R5_WRITE = 23;
  /**
   * <b>Control Signal:</b> Stores the result_bus value into r_6.
   * <p>Stores the index of this control signal.</p>
   * */
  public static final int R6_WRITE = 24;
  /**
   * <b>Control Signal:</b> Stores the result_bus value into r_7.
   * <p>Stores the index of this control signal.</p>
   * */
  public static final int R7_WRITE = 25;
  /**
   * <b>Control Signal:</b> Places the value in register n(0-7) on the a_bus.
   * <p>Stores the index of this control signal.</p>
   * */
  public static final int A_SEL = 26;
  /**
   * <b>Control Signal:</b> Places the value in register n(0-7) on the b_bus.
   * <p>Stores the index of this control signal.</p>
   * */
  public static final int B_SEL = 27;
  /**
   * <b>Control Signal:</b> Stores result_bus value in the register defined in ir_ri.
   * <p>Stores the index of this control signal.</p>
   * */
  public static final int RI_SEL = 28;
  /**
   * <b>Control Signal:</b> Places the value in register defined by ir_rj on the a_bus.
   * <p>Stores the index of this control signal.</p>
   * */
  public static final int RJ_SEL = 29;
  /**
   * <b>Control Signal:</b> Places the value in register defined by ir_rk on the a_bus.
   * <p>Stores the index of this control signal.</p>
   * */
  public static final int RK_SEL = 30;
  /**
   * <b>Control Signal:</b> Sets the c_in to 1 for use by ALU operations.
   * <p>Stores the status array index of this control signal.</p>
   */
  public static final int C_IN = 31;
  /**
   * <b>Control Signal:</b> Sets the ALU operation.
   * <p>Stores the status array index of this control signal.</p>
   * @see #ALU_NOT
   * @see #ALU_OR
   * @see #ALU_AND
   * @see #ALU_XOR
   * @see #ALU_ADD
   * @see #ALU_SUB
   * @see #ALU_ADDA
   * @see #ALU_SUBA
   */
  public static final int ALU_SEL = 32;
  /**
   * <b>Control Signal:</b> Sets Memory Data Register (MAR).
   * <p>Stores the status array index of this control signal.</p>
   * @see #MDR
   */
  public static final int MDR_SEL = 33;
  /**
   * <b>Control Signal:</b> Sets Memory Address Register (MDR).
   * <p>Stores the status array index of this control signal.</p>
   * @see #MAR
   */
  public static final int MAR_SEL = 34;
  /**
   * <b>Control Signal:</b> Selects value for result_bus.
   * <p>Stores the status array index of this control signal.</p>
   * @see #RESULT_ALU
   * @see #RESULT_MDR
   * @see #RESULT_IR_CONST4
   * @see #RESULT_IR_CONST8
   */
  public static final int RESULT_SEL = 35;
  /**
   * <b>Control Signal:</b> Sets Instruction Register 0 (ir_0).
   * <p>Stores the status array index of this control signal.</p>
   * @see #IR_0
   */
  public static final int IR0_SEL = 36;
  /**
   * <b>Control Signal:</b> Sets Instruction Register 1 (ir_1).
   * <p>Wires: 1<br>
   * Values: 0 to 1 </p>
   * <p>Stores the status array index of this control signal.</p>
   * @see #IR_1
   */
  public static final int IR1_SEL = 37;
  /**
   * <b>Control Signal:</b> Read from memory.
   * <p>Wires: 1<br>
   * Values: 0 to 1 </p>
   * <p>Stores the status array index of this control signal.</p>
   */
  public static final int READ = 38;
  /**
   * <b>Control Signal:</b> Write to memory.
   * <p>Wires: 1<br>
   * Values: 0 to 1 </p>
   * <p>Stores the status array index of this control signal.</p>
   */
  public static final int WRITE = 39;
  /**
   * <b>Control Signal:</b> Add the opcode to the next address.
   * <p>Wires: 1<br>
   * Values: 0 to 1 </p>
   * <p>Stores the status array index of this control signal.</p>
   */
  public static final int INDEX_SEL = 40;
  /**
   * <b>Control Signal:</b> Select the argument for the if statement.
   * <p>Wires: 2<br>
   * Values: 0 to 3 </p>
   * <p>Stores the status array index of this control signal.</p>
   * @see #COND_M7
   * @see #COND_C_OUT
   * @see #COND_V
   * @see #COND_WAIT
   */
  public static final int COND = 41;
  /**
   * <b>Control Signal:</b>.
   * <p>Stores the status array index of this control signal.</p>
   */
  public static final int ADDRESS_TRUE = 42;
  /**
   * <b>Control Signal:</b> .
   * <p>Stores the status array index of this control signal.</p>
   */
  public static final int ADDRESS_FALSE = 43;
  /**
   * <b>Control Signal: (not used) </b> .
   * <p>Use CURRENT_ADDESS </p>
   */
  public static final int ADDRESS = 44;

  public static final int R0_WRITE_SET = 45;
  public static final int R1_WRITE_SET = 46;
  public static final int R2_WRITE_SET = 47;
  public static final int R3_WRITE_SET = 48;
  public static final int R4_WRITE_SET = 49;
  public static final int R5_WRITE_SET = 50;
  public static final int R6_WRITE_SET = 51;
  public static final int R7_WRITE_SET = 52;
  public static final int A_SEL_SET = 53;
  public static final int B_SEL_SET = 54;
  public static final int RI_SEL_SET = 55;
  public static final int RJ_SEL_SET = 56;
  public static final int RK_SEL_SET = 57;
  public static final int C_IN_SET = 58;
  public static final int ALU_SEL_SET = 59;
  public static final int MDR_SEL_SET = 60;
  public static final int MAR_SEL_SET = 61;
  public static final int RESULT_SEL_SET = 62;
  public static final int IR0_SEL_SET = 63;
  public static final int IR1_SEL_SET = 64;
  public static final int READ_SET = 65;
  public static final int WRITE_SET = 66;
  public static final int INDEX_SEL_SET = 67;
  public static final int COND_SET = 68;
  public static final int ADDRESS_TRUE_SET = 69;
  public static final int ADDRESS_FALSE_SET = 70;
  public static final int ADDRESS_SET = 71;

  /* Status Signals */
  /**
   * <b>Status Signal:</b> Carry bit from the ALU.
   * <p>Stores the status array index of this status signal.</p>
   */
  public static final int C_OUT = 72;
  /**
   * <b>Status Signal:</b> Most significant bit from alu_bus.
   * <p>Stores the status array index of this status signal.</p>
   */
  public static final int M_7 = 73;
  /**
   * <b>Status Signal:</b> Overflow.
   * <p>Stores the status array index of this status signal.</p>
   */
  public static final int V = 74;
  /**
   * <b>Status Signal:</b> Wait.
   * <p>Stores the status array index of this status signal.</p>
   */
  public static final int WAIT = 75;

  public static final int CURRENT_ADDRESS = 76;
  /**
   * The system clock.
   */
  public static final int CLOCK = 77;
  public static final int LOCKED = 78;

  /* ======================================================
   Virtual Buses
   ===================================================== */
  /**
   * <b>Virtual Bus:</b> Places the value in register n(0-7) on the a_bus.
   * <p>Wires: 3<br>
   * Values: 0 to 7 </p>
   * <p>Stores the status array index of this control signal.</p>
   */
  public static final int VA_SEL_BUS = 79;
  /**
   * <b>Virtual Bus:</b> Places the value in register n(0-7) on the b_bus.
   * <p>Wires: 3<br>
   * Values: 0 to 7 </p>
   * <p>Stores the status array index of this control signal.</p>
   */
  public static final int VB_SEL_BUS = 80;
  /**
   * <b>Virtual Control Signal:</b> Stores the result_bus value into r_0.
   * <p>Wires: 1<br>
   * Values: 0 or 1 </p>
   * <p>Stores the status array index of this control signal.</p>
   */
  public static final int VR0_WRITE_BUS = 81;
  /**
   * <b>Virtual Control Signal:</b> Stores the result_bus value into r_1.
   * <p>Wires: 1<br>
   * Values: 0 or 1 </p>
   * <p>Stores the status array index of this control signal.</p>
   */
  public static final int VR1_WRITE_BUS = 82;
  /**
   * <b>Virtual Control Signal:</b> Stores the result_bus value into r_2.
   * <p>Wires: 1<br>
   * Values: 0 or 1 </p>
   * <p>Stores the status array index of this control signal.</p>
   */
  public static final int VR2_WRITE_BUS = 83;
  /**
   * <b>Virtual Control Signal:</b> Stores the result_bus value into r_3.
   * <p>Wires: 1<br>
   * Values: 0 or 1 </p>
   * <p>Stores the status array index of this control signal.</p>
   */
  public static final int VR3_WRITE_BUS = 84;
  public static final int STATUS_ARRAY_LENGTH = 85;

  /* Main Values */
  public static final int MAIN_MEMORY = 256;

  /* Lengths */
  public static final int LENGTH = 512;
  public static final int CONTROL_WORD_START = 18;
  public static final int CONTROL_WORD_END = 71;
  public static final int CONTROL_WORD_LENGTH = 54;

  /* ======================================================
   File Modes
   ===================================================== */
  public static final int NO_FILES_MODE = 0;
  public static final int INVALID_FILES_MODE = 1;
  public static final int VALID_FILES_MODE = 2;
  protected int mode = NO_FILES_MODE;

  /* Functions for Geting Status Array Values */
  /** @return the current status array */
  public int[] getStatus() {
    return s;
  }

  /** @return the current status array */
  public int[] getPreviousStatus() {
    return p;
  }

  /** @return the current status array element n */
  public int getStatus(int index) {
    return s[index];
  }

  /** @return the diplay name of status array element n */
  public static String name(int i) {
    return displayNames[i];
  }

  /** @return the official name of status array element n */
  public static String funcName(int i) {
    if (i >= 0 && i <= 7) {
      return aluFuncs[i];
    }
    else {
      return "n/a";
    }
  }

  /** @return true when current_address is zero*/
  public boolean ucodeZero() {
    return s[MythSim.INDEX_SEL] == 1;
  }

  /** @return the value of a location in memory */
  private int getMem(int index) throws MythError {
    if (index > 255 || index < 0) {
      throw new MythError(5, s, index);
    }
    return s[MythSim.MAIN_MEMORY + index];
  }

  /** set the value of a location in memory */
  private void setMem(int index, int value) throws MythError {
    if (index > 255 || index < 0) {
      throw new MythError(5, s, index);
    }
    s[MythSim.MAIN_MEMORY + index] = value;
  }

  /** official names of status array elements */
  private static final String[] lineNames = {
      "R_0",
      "R_1", "R_2", "R_3", "R_4", "R_5", "R_6", "R_7",
      "IR_0", "IR_1",
      "MDR", "MAR",
      "ALU_BUS", "A_BUS", "B_BUS", "RESULT_BUS", "WRITE_BUS", "MEMORY_BUS",
      "R0_WRITE", "R1_WRITE", "R2_WRITE", "R3_WRITE",
      "R4_WRITE", "R5_WRITE", "R6_WRITE", "R7_WRITE",
      "A_SEL", "B_SEL",
      "RI_SEL", "RJ_SEL", "RK_SEL",
      "C_IN",
      "ALU_SEL", "MDR_SEL", "MAR_SEL", "RESULT_SEL", "IR0_SEL", "IR1_SEL",
      "READ", "WRITE", "INDEX_SEL", "COND", "ADDRESS_TRUE", "ADDRESS_FALSE",
      "ADDRESS",
      "R0_WRITE_SET", "R1_WRITE_SET", "R2_WRITE_SET", "R3_WRITE_SET",
      "R4_WRITE_SET", "R5_WRITE_SET", "R6_WRITE_SET", "R7_WRITE_SET",
      "A_SEL_SET", "B_SEL_SET",
      "RI_SEL_SET", "RJ_SEL_SET", "RK_SEL_SET",
      "C_IN_SET",
      "ALU_SEL_SET", "MDR_SEL_SET", "MAR_SEL_SET", "RESULT_SEL_SET",
      "IR0_SEL_SET", "IR1_SEL_SET",
      "READ_SET", "WRITE_SET", "INDEX_SEL_SET", "COND_SET", "ADDRESS_TRUE_SET",
      "ADDRESS_FALSE_SET", "ADDRESS_SET",
      "C_OUT", "M_7", "V", "WAIT", "CURRENT_ADDRESS", "CLOCK", "STOPPED",
      "VA_SEL_BUS",
      "VB_SEL_BUS", "VR0_WRITE_BUS", "VR1_WRITE_BUS", "VR2_WRITE_BUS",
      "VR3_WRITE_BUS"};

  /** display names of status array elements */
  private static final String[] displayNames = {
      "R_0",
      "R_1", "R_2", "R_3", "R_4", "R_5", "R_6", "R_7",
      "ir0", "ir1",
      "mdr", "mar",
      "ALUbus",
      "Abus", "Bbus",
      "result", "WRITE_BUS", "MEMORY_BUS",
      "R0_WRITE", "R1_WRITE", "R2_WRITE", "R3_WRITE", "r4write",
      "r5write", "r6write", "r7write", "A_SEL",
      "B_SEL", "RI_SEL", "RJ_SEL", "RK_SEL",
      "Cin", "ALUsel", "MDRsel", "MARsel", "ResultSel",
      "ir0sel", "ir1sel",
      "read", "write", "INDEX_SEL", "COND",
      "ADDRESS_TRUE", "ADDRESS_FALSE", "ADDRESS",
      "R0_WRITE_set", "R1_WRITE_set", "R2_WRITE_set", "R3_WRITE_set",
      "r4write_set",
      "r5write_set", "r6write_set", "r7write_set", "A_SEL_set",
      "B_SEL_set", "RI_SEL_set", "RJ_SEL_set", "RK_SEL_set",
      "Cin_set", "ALUsel_set", "MDRsel_set", "MARsel_set", "ResultSel_set",
      "ir0sel_set", "ir1sel_set",
      "read_set", "write_set", "INDEX_SEL_set", "COND_set",
      "ADDRESS_TRUE_set", "ADDRESS_FALSE_set", "ADDRESS_set",
      "Cout", "M7", "v", "wait",
      "CURRENT_ADDRESS", "CLOCK", "STOPPED", "vAsel", "vBsel",
      "vr0write", "vr1write", "vr2write", "vr3write"};

  public int getRegA() {
    if (s[VA_SEL_BUS] >= 0 && s[VA_SEL_BUS] <= 7) {
      return s[s[VA_SEL_BUS]];
    }
    else {
      return -1;
    }
  }

  public int getRegB() {
    if (s[VB_SEL_BUS] >= 0 && s[VB_SEL_BUS] <= 7) {
      return s[s[VB_SEL_BUS]];
    }
    else {
      return -1;
    }
  }

  /* ******************************************************
   ALU Constants
   ****************************************************** */

  public static final String[] aluFuncs = {
      "NOT",
      "OR", "AND", "XOR", "ADD", "SUB", "ADDA", "SUBA"};

  /**
   * <b>ALU Operation:</b> NOT.
   * <p>NOT(a_bus) => alu_bus</p>
   * @see #ALU_SEL
   */
  public static final int ALU_NOT = 0;
  /**
   * <b>ALU Operation:</b> OR.
   * <p>OR(a_bus,b_bus) => alu_bus</p>
   * @see #ALU_SEL
   */
  public static final int ALU_OR = 1;
  /**
   * <b>ALU Operation:</b> AND.
   * <p>AND(a_bus,b_bus) => alu_bus</p>
   * @see #ALU_SEL
   */
  public static final int ALU_AND = 2;
  /**
   * <b>ALU Operation:</b> XOR.
   * <p>XOR(a_bus,b_bus) => alu_bus</p>
   * @see #ALU_SEL
   */
  public static final int ALU_XOR = 3;
  /**
   * <b>ALU Operation:</b> ADD.
   * <p>a_bus + b_bus + c_in => alu_bus</p>
   * @see #ALU_SEL
   */
  public static final int ALU_ADD = 4;
  /**
   * <b>ALU Operation:</b> SUB.
   * <p>a_bus + NOT(b_bus) + c_in => alu_bus</p>
   * @see #ALU_SEL
   */
  public static final int ALU_SUB = 5;
  /**
   * <b>ALU Operation:</b> ADDA.
   * <p>a_bus + c_in => alu_bus</p>
   * @see #ALU_SEL
   */
  public static final int ALU_ADDA = 6;
  /**
   * <b>ALU Operation:</b> SUBA.
   * <p>a_bus - 1 + c_in => alu_bus</p>
   * @see #ALU_SEL
   */
  public static final int ALU_SUBA = 7;

  /* ******************************************************
    Result Options
   ****************************************************** */
  /**
   * <b>Result Option:</b> Place the alu_bus value on the result_bus.
   * @see #RESULT_SEL
   */
  public static final int RESULT_ALU = 0;
  /**
   * <b>Result Option:</b> Place the MDR value on the result_bus.
   * @see #RESULT_SEL
   */
  public static final int RESULT_MDR = 1;
  /**
   * <b>Result Option:</b> Place the ir_const4 value on the result_bus.
   * @see #RESULT_SEL
   */
  public static final int RESULT_IR_CONST4 = 2;
  /**
   * <b>Result Option:</b> Place the ir_const8 value on the result_bus.
   * @see #RESULT_SEL
   */
  public static final int RESULT_IR_CONST8 = 3;

  /* ******************************************************
   Cond Options
   ****************************************************** */
  /**
   * <b>Cond Option:</b> Uses most significant bit from alu_bus.
   * @see #COND
   */
  public static final int COND_M7 = 0;
  /**
   * <b>Cond Option:</b> Uses carry bit from the alu_bus.
   * @see #COND
   * @see #C_OUT
   */
  public static final int COND_C_OUT = 1;
  /**
   * <b>Cond Option:</b> Uses overflow bit from ALU.
   * @see #COND
   */
  public static final int COND_V = 2;
  /**
   * <b>Cond Option:</b> Uses wait signal from Main Memory.
   * @see #COND
   */
  public static final int COND_WAIT = 3;

  /* ******************************************************
   Operations
   ****************************************************** */

  /** Parse ucode and mem files. */
  public void parse() throws Exception {
    try {
      mp = new MythParser(microcodeFile, memoryFile, this);
      memorySource = mp.getMemorySource();
      microcodeSource = mp.getMicrocodeSource();
    }
    catch (java.io.FileNotFoundException ex) {
      mode = NO_FILES_MODE;
      throw ex;
    }
    catch (Exception ex) {
      throw ex;
    }
  }

  /** Step forward N time to next opcode */
  public void step(int a) throws MythError {
    if (a == 0) {
      return;
    }
    if (a > 0) {
      for (int i = 0; i < a; i++) {
        step();
      }
      return;
    }
    if (a < 0) {
      int target = s[CLOCK] + a;
      //System.out.println(">:>:" + target);
      boot();
      if (target > 0) {
        for (int i = 0; i < target; i++) {
          step();
        }
      }
      return;
    }
  }

  /** Steps forward to next opcode. */
  public void next() throws MythError {
    step();
    while (ucodeZero() != true) {
      step();
    }
  }

  /** Steps backward to last opcode. */
  public void last() throws MythError {
    step( -1);
    while (ucodeZero() != true && s[MythSim.CURRENT_ADDRESS] != 0) {
      step( -1);
    }
  }

  /** Runs comptuer. */
  public void run() throws MythError {
    for (int i = 0; i < 1000; i++) {
      step();
    }
    throw new MythError(
        "Paused. (Press run again for the next 1000 clock cycles.)");
  }

  /** Boots comptuer. */
  public void boot() throws MythError {
    if (mode == NO_FILES_MODE) {
      System.out.println("Attempted to boot in no files mode.");
      return;
    }
    if (mode == INVALID_FILES_MODE) {
      System.out.println("Attempted to boot in invalid files mode.");
      return;
    }
    _ucode = mp.toUcodeIntArray();
    //for (int i = 0; i < 3; i++) {
    //  System.out.print("a_sel:" + _ucode[i][ControlWord.A_SEL] + ",");
    //  System.out.print("b_sel:" + _ucode[i][ControlWord.B_SEL] + ",");
    //  System.out.println("c_in:" + _ucode[i][ControlWord.C_IN]);
    //}
    int temp[] = mp.toMemIntArray();
    for (int i = 0; i < 256; i++) {
      s[i] = 0;
    }
    for (int i = 0; i < 256; i++) {
      s[MythSim.MAIN_MEMORY + i] = temp[i];
    }
    s[MythSim.CLOCK] = -1;
    step();
  }

  /** Step One Clock Cycle*/
  public void step() throws MythError {
    if (s[MythSim.LOCKED] == 1) {
      return;
    }
    // Copy previous array.
    if (s[MythSim.CLOCK] == -1) {
      for (int i = 0; i < MythSim.LENGTH; i++) {
        p[i] = 0;
      }
    } else {
      for (int i = 0; i < MythSim.LENGTH; i++) {
        p[i] = s[i];
      }
    }

    s[MythSim.WAIT] = 0;

    s[MythSim.A_BUS] = s[MythSim.R_0 + s[MythSim.VA_SEL_BUS]];
    s[MythSim.B_BUS] = s[MythSim.R_0 + s[MythSim.VB_SEL_BUS]];

    switch (s[MythSim.ALU_SEL]) {
      case ALU_NOT:
        s[MythSim.ALU_BUS] = not(s[MythSim.A_BUS]);
        break;
      case ALU_OR:
        s[MythSim.ALU_BUS] = or(s[MythSim.A_BUS], s[MythSim.B_BUS]);
        break;
      case ALU_AND:
        s[MythSim.ALU_BUS] = and(s[MythSim.A_BUS], s[MythSim.B_BUS]);
        break;
      case ALU_XOR:
        s[MythSim.ALU_BUS] = xor(s[MythSim.A_BUS], s[MythSim.B_BUS]);
        break;
      case ALU_ADD:
        s[MythSim.ALU_BUS] = s[MythSim.A_BUS] + s[MythSim.B_BUS] +
            s[MythSim.C_IN];
        break;
      case ALU_SUB:
        s[MythSim.ALU_BUS] = s[MythSim.A_BUS] + not(s[MythSim.B_BUS]) +
            s[MythSim.C_IN];
        break;
      case ALU_ADDA:
        s[MythSim.ALU_BUS] = s[MythSim.A_BUS] + s[MythSim.C_IN];
        break;
      case ALU_SUBA:
        s[MythSim.ALU_BUS] = s[MythSim.A_BUS] - 1 + s[MythSim.C_IN];
        break;
      default:
        break;
    }

    s[MythSim.C_OUT] = carry(s[MythSim.ALU_SEL], s[MythSim.A_BUS],
                             s[MythSim.B_BUS], s[MythSim.C_IN]);

    s[MythSim.V] = 0;
    if (s[MythSim.ALU_BUS] > 127) {
      System.out.println("Overflow:" + s[MythSim.ALU_BUS]);
      s[MythSim.V] = 1;
      s[MythSim.ALU_BUS] -= 256;
    }
    if (s[MythSim.ALU_BUS] < -128) {
      System.out.println("Underflow:" + s[MythSim.ALU_BUS]);
      s[MythSim.V] = 1;
      s[MythSim.ALU_BUS] += 256;
    }

    s[MythSim.M_7] = m7(s[MythSim.ALU_BUS]);

    switch (s[MythSim.WRITE]) {
      case 0:
        break;
        //case 1: setMem(s[MythSim.MAR],tc2normal(s[MythSim.MDR])); break;
        /* 3.01) Reed Class Bug that blocked last 128 bytes of memory.
             case 1: setMem(tc2normal(s[MythSim.MAR]),tc2normal(s[MythSim.MDR])); break;*/
      /* 3.02) Reed Class Bug that messed up memory display.*/
      case 1:
        setMem(tc2normal(s[MythSim.MAR]), s[MythSim.MDR]);
        break;
      default:
        break;
    }
    switch (s[MythSim.READ]) {
      case 0:
        break;
      case 1:
        s[MythSim.MEMORY_BUS] = getMem(tc2normal(s[MythSim.MAR]));
        break;
      default:
        break;
    }
    switch (s[MythSim.MDR_SEL]) {
      case 0:
        break;
      case 1:
        s[MythSim.MDR] = s[MythSim.ALU_BUS];
        break;
      case 2:
        s[MythSim.MDR] = s[MythSim.MEMORY_BUS];
        break;
      default:
        break;
    }
    switch (s[MythSim.MAR_SEL]) {
      case 0:
        break;
      case 1:
        s[MythSim.MAR] = s[MythSim.ALU_BUS];
        break;
      default:
        break;
    }
    switch (s[MythSim.IR0_SEL]) {
      case 0:
        break;
      case 1:
        s[MythSim.IR_0] = s[MythSim.MEMORY_BUS];
        break;
      default:
        break;
    }
    switch (s[MythSim.IR1_SEL]) {
      case 0:
        break;
      case 1:
        s[MythSim.IR_1] = s[MythSim.MEMORY_BUS];
        break;
      default:
        break;
    }
    switch (s[MythSim.RESULT_SEL]) {
      case RESULT_ALU:
        s[MythSim.RESULT_BUS] = s[MythSim.ALU_BUS];
        break;
      case RESULT_MDR:
        s[MythSim.RESULT_BUS] = s[MythSim.MDR];
        break;
      case RESULT_IR_CONST4:
        s[MythSim.RESULT_BUS] = lownibble(s[MythSim.IR_0]);
        break;
      case RESULT_IR_CONST8:
        s[MythSim.RESULT_BUS] = s[MythSim.IR_0];
        break;
      default:
        break;
    }

    switch (s[MythSim.VR0_WRITE_BUS]) {
      case 0:
        break;
      case 1:
        s[MythSim.R_0] = s[MythSim.RESULT_BUS];
        break;
      default:
        break;
    }
    switch (s[MythSim.VR1_WRITE_BUS]) {
      case 0:
        break;
      case 1:
        s[MythSim.R_1] = s[MythSim.RESULT_BUS];
        break;
      default:
        break;
    }
    switch (s[MythSim.VR2_WRITE_BUS]) {
      case 0:
        break;
      case 1:
        s[MythSim.R_2] = s[MythSim.RESULT_BUS];
        break;
      default:
        break;
    }
    switch (s[MythSim.VR3_WRITE_BUS]) {
      case 0:
        break;
      case 1:
        s[MythSim.R_3] = s[MythSim.RESULT_BUS];
        break;
      default:
        break;
    }
    switch (s[MythSim.R4_WRITE]) {
      case 0:
        break;
      case 1:
        s[MythSim.R_4] = s[MythSim.RESULT_BUS];
        break;
      default:
        break;
    }
    switch (s[MythSim.R5_WRITE]) {
      case 0:
        break;
      case 1:
        s[MythSim.R_5] = s[MythSim.RESULT_BUS];
        break;
      default:
        break;
    }
    switch (s[MythSim.R6_WRITE]) {
      case 0:
        break;
      case 1:
        s[MythSim.R_6] = s[MythSim.RESULT_BUS];
        break;
      default:
        break;
    }
    switch (s[MythSim.R7_WRITE]) {
      case 0:
        break;
      case 1:
        s[MythSim.R_7] = s[MythSim.RESULT_BUS];
        break;
      default:
        break;
    }

    /* Infinite Loop Check*/
    if (
        (s[MythSim.CURRENT_ADDRESS] == s[MythSim.ADDRESS_TRUE]) &&
        (s[MythSim.CURRENT_ADDRESS] == s[MythSim.ADDRESS_FALSE]) &&
        (s[MythSim.CLOCK] != -1)
        ) {
      throw new MythError(0, s, 0);
    }

    s[MythSim.CLOCK]++;
    int selection = 0;
    int next = 0;
    switch (s[MythSim.COND]) {
      case COND_M7:
        selection = s[MythSim.M_7];
        break;
      case COND_C_OUT:
        selection = s[MythSim.C_OUT];
        break;
      case COND_V:
        selection = s[MythSim.V];
        break;
      case COND_WAIT:
        selection = s[MythSim.WAIT];
        break;
      default:
        break;
    }
    switch (selection) {
      case 0:
        next = s[MythSim.ADDRESS_FALSE];
        break;
      case 1:
        next = s[MythSim.ADDRESS_TRUE];
        break;
      default:
        break;
    }
    switch (s[MythSim.INDEX_SEL]) {
      case 0:
        break;
      case 1:
        next += opcode(s[MythSim.IR_1]);
        //System.out.println("--" + next + "--" + opcode(s[MythSim.IR_1]) + "--");
        break;
      default:
        break;
    }
    if (next >= _ucode.length) {
      s[MythSim.CLOCK]--;
      //_notDone = false;
      throw new MythError("Stopped.");
    }
    s[MythSim.CURRENT_ADDRESS] = next;
    //for (int i = 0; i <= 25; i++) {
    //  s[i + 18] = _ucode[s[MythSim.CURRENT_ADDRESS]][i];
    //}
    for (int i = 0; i < CONTROL_WORD_LENGTH; i++) {
      s[i + CONTROL_WORD_START] = _ucode[s[MythSim.CURRENT_ADDRESS]][i];
    }
    switch (s[MythSim.RJ_SEL]) {
      case 0:
        s[MythSim.VA_SEL_BUS] = s[MythSim.A_SEL];
        break;
      case 1:
        s[MythSim.VA_SEL_BUS] = rj(s[MythSim.IR_0]);
        break;
      default:
        break;
    }
    switch (s[MythSim.RK_SEL]) {
      case 0:
        s[MythSim.VB_SEL_BUS] = s[MythSim.B_SEL];
        break;
      case 1:
        s[MythSim.VB_SEL_BUS] = rk(s[MythSim.IR_0]);
        break;
      default:
        break;
    }

    s[MythSim.VR0_WRITE_BUS] = s[MythSim.R0_WRITE];
    s[MythSim.VR1_WRITE_BUS] = s[MythSim.R1_WRITE];
    s[MythSim.VR2_WRITE_BUS] = s[MythSim.R2_WRITE];
    s[MythSim.VR3_WRITE_BUS] = s[MythSim.R3_WRITE];
    if (s[MythSim.RI_SEL] == 0) {
    }
    else if (s[MythSim.RI_SEL] == 1) {
      switch (ri(s[MythSim.IR_1])) {
        case 0:
          s[MythSim.VR0_WRITE_BUS] = 1;
          break;
        case 1:
          s[MythSim.VR1_WRITE_BUS] = 1;
          break;
        case 2:
          s[MythSim.VR2_WRITE_BUS] = 1;
          break;
        case 3:
          s[MythSim.VR3_WRITE_BUS] = 1;
          break;
        default:
          break;
      }
    }
    else {
    }

  }

  /* ******************************************************
   Logic Functions
   ****************************************************** */

  public static boolean NOT(boolean a) {
    return a == false;
  }

  public static boolean AND(boolean a, boolean b) {
    return a && b;
  }

  public static boolean OR(boolean a, boolean b) {
    return a || b;
  }

  public static boolean XOR(boolean a, boolean b) {
    return (a && !b) || (!a && b);
  }

  /* CONVERSION */
  public static boolean[] int2bit(int a) {
    boolean temp[] = new boolean[8];
    int value = a;
    if (a < 0) {
      temp[7] = true;
      a += 128;
    }
    else {
      temp[7] = false;
    }
    if (a >= 64) {
      temp[6] = true;
      a -= 64;
    }
    else {
      temp[6] = false;
    } //
    if (a >= 32) {
      temp[5] = true;
      a -= 32;
    }
    else {
      temp[5] = false;
    }
    if (a >= 16) {
      temp[4] = true;
      a -= 16;
    }
    else {
      temp[4] = false;
    }
    if (a >= 8) {
      temp[3] = true;
      a -= 8;
    }
    else {
      temp[3] = false;
    }
    if (a >= 4) {
      temp[2] = true;
      a -= 4;
    }
    else {
      temp[2] = false;
    }
    if (a >= 2) {
      temp[1] = true;
      a -= 2;
    }
    else {
      temp[1] = false;
    }
    if (a >= 1) {
      temp[0] = true;
      a -= 1;
    }
    else {
      temp[0] = false;
    }
    if (a == 0) {
      return temp;
    }
    else {
      return temp;
    }
  }

  public static int bit2int(boolean a[]) {
    if (a.length == 8) {
      int temp = 0;
      if (a[7]) {
        temp -= 128;
      }
      if (a[6]) {
        temp += 64;
      }
      if (a[5]) {
        temp += 32;
      }
      if (a[4]) {
        temp += 16;
      }
      if (a[3]) {
        temp += 8;
      }
      if (a[2]) {
        temp += 4;
      }
      if (a[1]) {
        temp += 2;
      }
      if (a[0]) {
        temp += 1;
      }
      return temp;
    }
    else {
      return -129;
    }
  }

  public static int tc2normal(int a) {
    int b;
    if (a >= 0) {
      b = a;
    }
    else {
      b = 256 + a;
    }
    //System.out.print("tc2normal:(" + a + "," + b + ")\n");
    return b;
  }

  public static int normal2tc(int a) {
    int b;
    if (a >= 128) {
      b = a - 256;
    }
    else {
      b = a;
    }
    //System.out.print("normal2tc:(" + a + "," + b + ")\n");
    return b;
  }

  /* ******************************************************
   Partition Functions
   ****************************************************** */

  public static int ri(int a) {
    boolean bit[] = int2bit(a);
    int temp = 0;
    if (bit[1]) {
      temp += 2;
    }
    if (bit[0]) {
      temp += 1;
    }
    return temp;
  }

  public static int rj(int a) {
    boolean bit[] = int2bit(a);
    int temp = 0;
    if (bit[7]) {
      temp += 2;
    }
    if (bit[6]) {
      temp += 1;
    }
    return temp;
  }

  public static int rk(int a) {
    boolean bit[] = int2bit(a);
    int temp = 0;
    if (bit[5]) {
      temp += 2;
    }
    if (bit[4]) {
      temp += 1;
    }
    return temp;
  }

  public static int opcode(int a) {
    boolean bit[] = int2bit(a);
    int temp = 0;
    if (bit[7]) {
      temp += 32;
    }
    if (bit[6]) {
      temp += 16;
    }
    if (bit[5]) {
      temp += 8;
    }
    if (bit[4]) {
      temp += 4;
    }
    if (bit[3]) {
      temp += 2;
    }
    if (bit[2]) {
      temp += 1;
    }
    return temp;
  }

  /* Rename to irconst4*/
  public static int lownibble(int a) {
    boolean bit[] = int2bit(a);
    int temp = 0;
    if (bit[3]) {
      temp += 128;
    }
    if (bit[3]) {
      temp += 64;
    }
    if (bit[3]) {
      temp += 32;
    }
    if (bit[3]) {
      temp += 16;

    }
    if (bit[3]) {
      temp += 8;
    }
    if (bit[2]) {
      temp += 4;
    }
    if (bit[1]) {
      temp += 2;
    }
    if (bit[0]) {
      temp += 1;
    }
    return temp;
  }

  /* ORIGINAL FUNCTION
           public static int lownibble(int a) {
          boolean bit[] = int2bit(a);
          int temp = 0;
          if (bit[3]) temp += 8;
          if (bit[2]) temp += 4;
          if (bit[1]) temp += 2;
          if (bit[0]) temp += 1;
          return temp;
           }*/

  public static int m7(int a) {
    boolean bit[] = int2bit(a);
    if (bit[7]) {
      return 1;
    }
    return 0;
  }

  public static int normal(int a) {
    boolean bit[] = int2bit(a);
    int temp = 0;
    if (bit[7]) {
      temp += 128;
    }
    if (bit[6]) {
      temp += 64;
    }
    if (bit[5]) {
      temp += 32;
    }
    if (bit[4]) {
      temp += 16;
    }
    if (bit[3]) {
      temp += 8;
    }
    if (bit[2]) {
      temp += 4;
    }
    if (bit[1]) {
      temp += 2;
    }
    if (bit[0]) {
      temp += 1;
      //System.out.println("Normal=" + temp);
    }
    return temp;
  }

  /* ******************************************************
   Display Functions
   ****************************************************** */
  public static String[] opcodeTags = {
      "no-op",
      "add",
      "li",
      "bz",
      "bzn",
      "jump",
      "move",
      "sb",
      "lb",
      "stop"
  };

  /* ******************************************************
   ALU Operations
   ****************************************************** */

  public static int not(int x) {
    boolean a[] = int2bit(x);
    for (int i = 0; i <= 7; i++) {
      a[i] = (a[i] == false);
    }
    return bit2int(a);
  }

  public static int or(int x, int y) {
    boolean a[] = int2bit(x);
    boolean b[] = int2bit(y);
    for (int i = 0; i <= 7; i++) {
      a[i] = (a[i] || b[i]);
    }
    return bit2int(a);
  }

  public static int and(int x, int y) {
    boolean a[] = int2bit(x);
    boolean b[] = int2bit(y);
    for (int i = 0; i <= 7; i++) {
      a[i] = (a[i] && b[i]);
    }
    return bit2int(a);
  }

  public static int xor(int x, int y) {
    boolean a[] = int2bit(x);
    boolean b[] = int2bit(y);
    for (int i = 0; i <= 7; i++) {
      a[i] = ( (a[i] == true && b[i] == false) || (a[i] == false && b[i] == true));
    }
    return bit2int(a);
  }

  public static int overflow(int x) {
    if (x > 127) {
      return 1;
    }
    if (x < -128) {
      return 1;
    }
    return 0;
  }

  public static int fix(int x) {
    if (x > 127) {
      return x - 256;
    }
    if (x < -128) {
      return x + 256;
    }
    return x;
  }

  public static int carry(int x, int y) {
    if ( (x < 0) && (y < 0)) {
      return 1;
    }
    return 0;
  }

  /* */
  public static int carry(int alu_sel, int a_bus, int b_bus, int c_in) {
    //System.out.print("Carry: ");
    int a = normal(a_bus);
    int b = normal(b_bus);
    int c = c_in;
    int r = 0;
    //System.out.print(a + "," + b + "," + c);
    //System.out.print(a_bus + "," + b_bus + "," + c_in);
    //(ms 1.0) case ALU_SUB: if ((a+not(b)+c) <0  ) {r=1;} break;
    //(ms 1.0) case ALU_SUBA: if ((a-1     +c) <0  ) {r=1;} break;
    switch (alu_sel) {
      case ALU_ADD:
        if ( (a + b + c) > 255) {
          r = 1;
        }
        break;
      case ALU_SUB:
        if ( (a + not(b) + c) > 255) {
          r = 1;
        }
        break;
      case ALU_ADDA:
        if ( (a + c) > 255) {
          r = 1;
        }
        break;
      case ALU_SUBA:
        if ( (a + 255 + c) > 255) {
          r = 1;
        }
        break;
      default:
        r = 0;
    }
    //System.out.println("");
    return r;
  }

  /* ******************************************************
   Other Functions
   ****************************************************** */

  public static int[] empty() {
    int temp[] = new int[LENGTH];
    for (int i = 0; i < LENGTH; i++) {
      temp[i] = 0;
    }
    return temp;
  }

  public Object[][] getUstore() {
    return mp.toUcodeObjectArray();
  }

  public Object[][] getUcode() {
    return mp.toCodeArray();
  }

  public Object[][] getMemory() {
    return mp.toMemObjectArray();
  }

}
