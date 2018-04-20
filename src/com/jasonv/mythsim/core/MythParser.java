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
import java.util.*;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import com.jasonv.mythsim.text.*;

/**
 * Interface to parsers.
 * @author Jason Vroustouris
 */
public class MythParser {
  String memorySource = "";
  String microcodeSource = "";
  //Vector ucodememory = new Vector();
  Vector ucodememory = null;
  int ucodeSize = 0;
  int ucodeCounter = 0;
  int clock = 0;
  int startSelect = 0;

  int memory[] = new int[256];
  int size = 0;
  int counter = 0;
  MythSim ms;

// Microcode Parser Functions

  /** @return microcode source file in string */
  public String getMicrocodeSource() {
    return microcodeSource;
  }

  public int[][] toUcodeIntArray() {
    ControlWord cw = new ControlWord();
    int mpa[][] = new int[ucodememory.size()][MythSim.CONTROL_WORD_LENGTH];
    for (int i = 0; i < ucodeSize; i++) {
      cw = ( (ControlWord) ucodememory.get(i));
      for (int j = 0; j < MythSim.CONTROL_WORD_LENGTH; j++) {
        mpa[i][j] = cw.get(j);
      }
    }
    return mpa;
  }

  public Integer[][] toIntegerArray() {
    ControlWord cw = new ControlWord();
    Integer mpa[][] = new Integer[ucodememory.size()][27];
    for (int i = 0; i < ucodeSize; i++) {
      cw = ( (ControlWord) ucodememory.get(i));
      for (int j = 0; j < 26; j++) {
        mpa[i][j] = new Integer(cw.get(j));
      }
    }
    return mpa;
  }

  public Object[][] toUcodeObjectArray() {
    ControlWord cw = new ControlWord();
    Object mpa[][] = new Object[ucodememory.size()][27];
    for (int i = 0; i < ucodeSize; i++) {
      mpa[i][0] = new String(Text.address(i));
      cw = ( (ControlWord) ucodememory.get(i));
      String temp = "";
      temp += cw.get(7);
      temp += cw.get(6);
      temp += cw.get(5);
      temp += cw.get(4);
      temp += cw.get(3);
      temp += cw.get(2);
      temp += cw.get(1);
      temp += cw.get(0);
      mpa[i][1] = new String(temp);
      for (int j = 8; j < 26; j++) {
        mpa[i][j - 6] = new Integer(cw.get(j));
      }
    }
    return mpa;
  }

  public Object[][] toCodeArray() {
    ControlWord cw = new ControlWord();
    Object mpa[][] = new Object[ucodememory.size()][1];
    for (int i = 0; i < ucodeSize; i++) {
      cw = ( (ControlWord) ucodememory.get(i));
      mpa[i][0] = new String(cw.line());
    }
    return mpa;
  }

  public MythParser() {};

  /** parse microcode and memory source files. */
  public MythParser(File ucodeFile, File memFile, MythSim _ms) throws Exception {
    ms = _ms;
    FileReader fr = null;
    try {
      // Microcode Parser
      fr = new FileReader(ucodeFile);

      UcodeParser ucodeParser = new UcodeParser(fr);
      ucodeParser.parse();
      if (!ucodeParser.isValid()) {
        throw new MythParserError(ucodeParser.errorMessages());
      }
      ucodememory = ucodeParser.getUcodeVector();
      ucodeSize = ucodememory.size();
      //ustore();
      fr.close();
      fr = new FileReader(ucodeFile);
      BufferedReader br = new BufferedReader(fr);
      String line = br.readLine();
      while (line != null) {
        microcodeSource += line + "\n";
        line = br.readLine();
      }
      fr.close();

      // Memory Parser
      for (int i = 0; i < 256; i++) {
        memory[i] = -1;
      }

      fr = new FileReader(memFile);
      MemParser memParser = new MemParser(fr);
      memParser.parse();
      if (!memParser.isValid()) {
        throw new MythParserError(memParser.errorMessages());
      }

      fr.close();

      fr = new FileReader(memFile);
      br = new BufferedReader(fr);
      line = br.readLine();
      while (line != null) {
        memorySource += line + "\n";
        line = br.readLine();
      }
      memory = memParser.getMemoryArray();
      fr.close();

    }

    catch (Exception e) {
      fr.close();
      throw e;
    }

  }

  public void ustore() {
    ControlWord.printHead();
    for (int i = 0; i < ucodeSize; i++) {
      if (i % 5 == 0 && i != 0) {
        System.out.println("");
      }
      ( (ControlWord) ucodememory.get(i)).print();
    }
  }

// Memory Parser Functions

  /** @return memory source file in string */
  public String getMemorySource() {
    return memorySource;
  }

  public boolean notDone() {
    return counter < size;
  }

  public int[] toMemIntArray() {
    return memory;
  }

  public String[] toStringArray() {
    String temp[] = new String[256];
    for (int i = 0; i < 256; i++) {
      temp[i] = Text.int2bin(memory[i]);
    }
    return temp;
  }

  public Object[][] toMemObjectArray() {
    Object temp[][] = new Object[256][2];
    for (int i = 0; i < 256; i++) {
      temp[i][0] = "" + i;
      temp[i][1] = Text.int2bin(memory[i]);
    }
    return temp;
  }

  /** Parse address (before ":") */
  int parseAddress(String a) {
    return Integer.parseInt(a);
  }

  /** Parse value (after ":") */
  int parseValue(String a) {
    if (a.length() == 8) {
      return string2int(a);
    }
    else {
      return Integer.parseInt(a);
    }
  }

  /** Convert an 8bit binary string to a int. */
  int string2int(String a) {
    String b = a.replaceAll(" ", "");
    if (b.length() == 8) {
      int t = 0;
      char c[] = b.toCharArray();
      if (c[0] == '1') {
        t -= 128;
      }
      if (c[1] == '1') {
        t += 64;
      }
      if (c[2] == '1') {
        t += 32;
      }
      if (c[3] == '1') {
        t += 16;
      }
      if (c[4] == '1') {
        t += 8;
      }
      if (c[5] == '1') {
        t += 4;
      }
      if (c[6] == '1') {
        t += 2;
      }
      if (c[7] == '1') {
        t += 1;
      }
      return t;
    }
    else {
      return -129;
    }
  }

}