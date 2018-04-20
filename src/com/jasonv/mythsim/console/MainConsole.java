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

package com.jasonv.mythsim.console;


import java.io.*;
import com.jasonv.mythsim.core.*;

/**
 * Starting point for command line version (Pre Alpha).
 * @author Jason Vroustouris
 */
public class MainConsole {
  private MythSimConsole ms = new MythSimConsole();

  public MainConsole() {
    try {
      ms.setFileMicrocode(new File("sample.ucode"));
      ms.setFileMemory(new File("sample.mem"));
    }
    catch (FileNotFoundException ex2) {
      System.out.print(ex2);
    }

    try {
      ms.parse();
      ms.boot();
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      String command = "";

      while (!command.equalsIgnoreCase("quit")) {
        System.out.print("Prompt:");
        command = br.readLine();
        if (command.equalsIgnoreCase("s")) {
          ms.step();
        }
        if (command.equalsIgnoreCase("r")) {
          ms.print_registers();
        }
      }
    }
    catch (MythError ex1) {
      System.out.print(ex1.message());
      ex1.printStackTrace();
    }
    catch (IOException ioe) {
      System.out.println("IO error.");
      System.exit(1);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    System.out.println("Exiting...");
    System.exit(0);
  }
}