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

package com.jasonv.mythsim.swing.plugin;
import com.jasonv.mythsim.swing.*;
import com.jasonv.mythsim.text.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.table.*;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.util.regex.*;

/**
 * The ucode window.
 * @author Jason Vroustouris
 * @todo Scroll the window to follow the selection.
 */
public class MicrostoreFrame extends MythPlugInFrame {
	JTable jTable = null;
	JScrollPane jScrollPane = null;
	public MicrostoreFrame(MythSimSwing a, JFrame owner) {
		super("Ucode",	  // title
			true,	  // resizable mu = new Character((char)956)
			true,   // closable
			true,   // maximizable
			true,   // iconifiable
			600,		  // set size x
			300,		  // set size y
			0,		  // set location x
			300,		  // set location y
			a,
			owner);  // MythSim object
		//getContentPane().add();
	}
	public void setup() {
		// Ustore Table

		jTable = new JTable(ms.getUstore(), Text.columnNames);
		jTable.setEnabled(false);
		jScrollPane = new JScrollPane(jTable);
		jTable.getTableHeader().setFont(new Font("jasonfont",Font.PLAIN,10));
                jTable.setSelectionBackground(this.SELECTION_COLOR_UCODE);
		getContentPane().add(jScrollPane);
	}
	public void step() {
		int temp = ms.getStatus(MythSimSwing.CURRENT_ADDRESS);
		jTable.setRowSelectionInterval(temp,temp);
                jTable.setLocation(0,temp);
	}


}
