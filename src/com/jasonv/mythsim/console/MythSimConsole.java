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


import com.jasonv.mythsim.core.MythSim;

/**
 * Starting point for command line version (Pre Alpha).
 * @author Jason Vroustouris
 */

public class MythSimConsole
    extends MythSim {
  public MythSimConsole() {
    super();
  }

  public void print_registers() {
    int s[] = getStatus();

    System.out.print("  r0: " + s[MythSim.R_0]);
    System.out.print("  r4: " + s[MythSim.R_4]);
    System.out.println(" ir0: " + s[MythSim.IR_0]);

    System.out.print("  r1: " + s[MythSim.R_1]);
    System.out.print("  r5: " + s[MythSim.R_5]);
    System.out.println(" ir1: " + s[MythSim.IR_1]);

    System.out.print("  r2: " + s[MythSim.R_2]);
    System.out.print("  r6: " + s[MythSim.R_6]);
    System.out.println(" mdr: " + s[MythSim.MDR]);

    System.out.print("  r3: " + s[MythSim.R_3]);
    System.out.print("  r7: " + s[MythSim.R_7]);
    System.out.println(" mar: " + s[MythSim.MAR]);
  }

}