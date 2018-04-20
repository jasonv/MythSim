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
import javax.swing.*;
import javax.swing.text.*;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.util.regex.*;
import com.jasonv.mythsim.swing.*;

/**
 * The Memory File Window.
 * @author Jason Vroustouris
 */
public class MemorySourceFrame extends MythPlugInFrame {
	int marker[];
	int codeMarker[] = new int[256];
	int codeLength[] = new int[256];
	String codeString[];
	JTextPane jTextPane = new JTextPane(){
		public void setSize(Dimension d){
			if (d.width < getParent().getSize().width)
				d.width = getParent().getSize().width;
			super.setSize(d);
		}
		public boolean getScrollableTracksViewportWidth(){
			return false;
		}
	};

	JScrollPane jScrollPane = new JScrollPane(jTextPane);
	Highlighter hl = jTextPane.getHighlighter();
	EditorKit ek = jTextPane.getEditorKit();

	public MemorySourceFrame(MythSimSwing a, JFrame owner) {
		super("Memory File",	  // title
			true,	  // resizable
			true,   // closable
			true,   // maximizable
			true,   // iconifiable
			200,		  // set size x
			300,		  // set size y
			0,		  // set location x
			300,		  // set location y
			a,
			owner);  // MythSim object
		jTextPane.setEditable(false);
                jTextPane.setFont(new Font("Courier", Font.PLAIN, 12));
                jTextPane.setPreferredSize(new Dimension(10,10));
                setContentPane(jScrollPane);
	}
	public void setup() {
		for (int i=0; i<256; i++) {
			codeMarker[i] = 0;
			codeLength[i] = 5;
		}
		jTextPane.setText(ms.getMemorySource());
	}
	public void step() {
		setAddress(ms.tc2normal(ms.getStatus(MythSimSwing.MAR)));
	}

	/** Select a give address in the source */
	public void setAddress(int a) {
		selectAddress(a,this.SELECTION_COLOR_MEM);
	}

	void selectAddress(int a, Color b) {
		String file = jTextPane.getText();
		String ls = System.getProperty("line.separator");
		file = file.replaceAll(ls,"\n");
		String line[] = file.split("\n");
		int lineMarker[] = new int[line.length];
		int codeMarker[] = new int[256];
		for (int i=0; i<256; i++) codeMarker[i]=0;
		int currentMarker = 0;
		for (int i=0; i<line.length; i++) {
			lineMarker[i] = currentMarker;
			String pair[] = line[i].split(":");
			if (pair.length == 2) {
				int addr = Integer.parseInt(pair[0]);
				codeMarker[addr]=i;
			}
			currentMarker += line[i].length() + 1;
		}
		try {
			hl.removeAllHighlights();
			hl.addHighlight(lineMarker[codeMarker[a]],lineMarker[codeMarker[a]+1],new DefaultHighlighter.DefaultHighlightPainter(b));
			jTextPane.select(lineMarker[codeMarker[a]],lineMarker[codeMarker[a]+1]);
		} catch (Throwable t) {
		}
	}
}