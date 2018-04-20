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
import com.jasonv.mythsim.core.*;
import javax.swing.*;
import javax.swing.*;
import javax.swing.text.*;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.util.regex.*;
import com.jasonv.mythsim.swing.*;

/**
 * The Memory Window.
 * @author Jason Vroustouris
 */
public class MemoryFrame extends MythPlugInFrame {
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

	public MemoryFrame(MythSimSwing a, JFrame owner) {
		super("Memory",	  // title
			true,	  // resizable
			true,   // closable
			true,   // maximizable
			true,   // iconifiable
			500,		  // set size x
			300,		  // set size y
			0,		  // set location x
			300,		  // set location y
			a,
			owner);  // MythSim object
		jTextPane.setEditable(false);
		jTextPane.setPreferredSize(new Dimension(100,100));

		getContentPane().add(jScrollPane);
		step();
	}
	public void setup() {
		for (int i=0; i<256; i++) {
			codeMarker[i] = 0;
			codeLength[i] = 5;
		}
		step();
	}
	public void step() {
		String temp = "Address\t(low,high)\tlow\thigh\n";
		temp += "-----------------------------------------\n";
		int ir0=0;
		int ir1=0;
		for (int i=0; i<256; i+=2) {
			ir1=ms.getStatus(MythSim.MAIN_MEMORY+i);
			ir0=ms.getStatus(MythSim.MAIN_MEMORY+i+1);
			temp += i + ":";
			temp += "\t";
			temp += "(" + ir1 + "," + ir0 + ")";
			temp += "\t";
			temp += instruction16bit(ir0,ir1);
			temp += "\n";
		}
		jTextPane.setText(temp);
		setAddress(ms.tc2normal(ms.getStatus(MythSim.MAR)));
	}

	/** Select a give address in the source */
	public void setAddress(int a) {
		selectAddress(a,this.SELECTION_COLOR_MEM);
	}
	public static String instruction16bit(int ir1, int ir0) {
		boolean a[] = MythSim.int2bit(ir1);
		boolean b[] = MythSim.int2bit(ir0);
		String temp = "";
                if (b[7]) temp+="1"; else temp+='0';
                if (b[6]) temp+="1"; else temp+='0';
                if (b[5]) temp+="1"; else temp+='0';
                if (b[4]) temp+="1"; else temp+='0';
                if (b[3]) temp+="1"; else temp+='0';
                if (b[2]) temp+="1"; else temp+='0';
                if (b[1]) temp+="1"; else temp+='0';
                if (b[0]) temp+="1"; else temp+='0';
                temp+="\t";

		if (a[7]) temp+="1"; else temp+='0';
		if (a[6]) temp+="1"; else temp+='0';
		if (a[5]) temp+="1"; else temp+='0';
		if (a[4]) temp+="1"; else temp+='0';
		if (a[3]) temp+="1"; else temp+='0';
		if (a[2]) temp+="1"; else temp+='0';
		if (a[1]) temp+="1"; else temp+='0';
		if (a[0]) temp+="1"; else temp+='0';
		temp+="\t";

		/*switch(opcode(ir1)) {
			case 0:
				temp+="no-op";
			break;
			case 1:
				temp+="add ";
				temp+="r"+ ri(ir1);
				temp+=", r"+ rj(ir0);
				temp+=", r"+ rk(ir0);
			break;
			case 2:
				temp+="li ";
				temp+="r"+ ri(ir1);
				temp+=", "+ ir0;
			break;
			case 3:
				temp+="bz r3";
				temp+=", "+ ir0;
			break;
			case 4:
				temp+="bzn r3";
				temp+=", "+ ir0;
			break;
			case 5:
				temp+="jump ";
				temp+=ir0;
			break;
			case 6:
				temp+="move ";
				temp+="r"+ ri(ir1);
				temp+=", r"+ rj(ir0);
			break;
			case 7:
				temp+="sb ";
				temp+="r"+ rk(ir0);
				temp+=", r"+ rj(ir0);
			break;
			case 8:
				temp+="lb ";
				temp+="r"+ ri(ir1);
				temp+=", r"+ rj(ir0);
			break;
			case 9:
				temp+="stop";
			break;
		}*/
		return temp;
	}

	void selectAddress(int a, Color b) {
		String file = jTextPane.getText();
		String ls = System.getProperty("line.separator");
		file = file.replaceAll(ls,"\n");
		String line[] = file.split("\n");
		int lineMarker[] = new int[line.length+1];
		int codeMarker[] = new int[256];
		for (int i=0; i<256; i++) codeMarker[i]=0;
		int currentMarker = 0;
		for (int i=0; i<line.length; i++) {
			lineMarker[i] = currentMarker;
			String pair[] = line[i].split(":");
			if (pair.length == 2) {
				int addr = Integer.parseInt(pair[0]);
				codeMarker[addr]=i;
				codeMarker[addr+1]=i;
			}
			currentMarker += line[i].length() + 1;
		}

                // BAD HACK to FIX Last Line Selection
                if (lineMarker[codeMarker[a]+1] == 0) {
                  lineMarker[codeMarker[a]+1] = file.length();
                }


		try {
                  //System.out.print("(-" + lineMarker[codeMarker[a]] + "," + lineMarker[codeMarker[a]+1] + ")");
			hl.removeAllHighlights();
			hl.addHighlight(lineMarker[codeMarker[a]],lineMarker[codeMarker[a]+1],new DefaultHighlighter.DefaultHighlightPainter(b));
			jTextPane.select(lineMarker[codeMarker[a]],lineMarker[codeMarker[a]+1]);
		} catch (Throwable t) {
                  t.printStackTrace();
		}
	}

}