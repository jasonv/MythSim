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
import javax.swing.*;
import javax.swing.*;
import javax.swing.text.*;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.util.regex.*;

/**
 * The Ucode File Window.
 * @author Jason Vroustouris
 */
public class MicrocodeSourceFrame extends MythPlugInFrame {
	int marker[];
	int codeMarker[];
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
	public MicrocodeSourceFrame(MythSimSwing a, JFrame owner) {
		super("Ucode File",	  // title
			true,	  // resizable
			true,   // closable
			true,   // maximizable
			true,   // iconifiable
			600,		  // set size x
			300,		  // set size y
			0,		  // set location x
			300,		  // set location y
			a,
			owner);  // MythSim object
		getContentPane().add(jScrollPane);
		jTextPane.setEditable(false);
                jTextPane.setFont(new Font("Courier", Font.PLAIN, 12));
		jTextPane.setPreferredSize(new Dimension(10,10));
	}
	public void setup() {
		try {
			jTextPane.setText(ms.getMicrocodeSource());
			String a = jTextPane.getText();
			String aa = jTextPane.getText();
			String ls = System.getProperty("line.separator");
			a = a.replaceAll(ls,"\n");
			String b[] = a.split(ls);
			int currentLoc = 0;
			//codeString = a.split(";");
                        codeString = this.splitControlStrings(a);
			//System.out.println(codeString.length);
			codeMarker = new int[codeString.length];
			for (int i=0; i<codeString.length; i++) {
				codeString[i]+=";";
				codeMarker[i]=currentLoc;
				currentLoc+=(codeString[i].length());
			}

		} catch (Throwable t) {
			jTextPane.setText("Ucode file not found.");
		}
	}

        /** 2002-11-12: HACK to fix ";" in comment bug. **/
        private String[] splitControlStrings(String a) {
          String temp[] = new String[1000];
          int currentCommand = 0;
          temp[0] = "";
          int scan = 0;
          int comment = 0;
          for (int i = 0; i < a.length(); i++) {
            if (scan == 0) {
              if (a.charAt(i) == ';') {
                currentCommand++;
                temp[currentCommand] = "";
              }
              else if (a.charAt(i) == '/' && a.charAt(i + 1) == '/') {
                temp[currentCommand] += a.charAt(i);
                scan = 1;
              }
              else {
                temp[currentCommand] += a.charAt(i);
              }
            } else {
              temp[currentCommand] += a.charAt(i);
              if (a.charAt(i) == '\n') {
                scan = 0;
              }
            }
          }
          return temp;
        }

	public void step() {
		setAddress(ms.getStatus(MythSimSwing.CURRENT_ADDRESS));
	}

        public void setAddress(int a) {
          selectAddress(a,this.SELECTION_COLOR_UCODE);
        }
  /* FOR TESTING
  public void setAddressTester(int a) {
    for (int i=0; i<=a; i++) {
      switch(i % 4) {
        case 0:
          selectAddress(i, new Color(250, 200, 200));
          break;
        case 1:
          selectAddress(i, new Color(250, 250, 200));
          break;
        case 2:
          selectAddress(i, new Color(200, 250, 200));
          break;
        case 3:
          selectAddress(i, new Color(200, 200, 250));
          break;
      }
    }
  }
*/

  void selectAddress(int a, Color b) {
          int hStart = 0;
          int hStop = 0;
          String s = codeString[a];
          char sa[] = s.toCharArray();
          int scan = 0;
          int comment = 0;
          for (int i = 0; i < sa.length; i++) {
            if (scan == 0) {
              if (sa[i] == ';') {
                hStop=i+1;
              }
              else if (sa[i] == '/' && sa[i + 1] == '/') {
                scan = 1;
              }
            }
            else {
              if (sa[i] == '\n') {
                scan = 0;
                hStart = i;
              }
            }
          }
          try {
                  hl.removeAllHighlights();
                  hl.addHighlight(codeMarker[a]+hStart,codeMarker[a]+hStop,new DefaultHighlighter.DefaultHighlightPainter(b));
                  jTextPane.select(codeMarker[a]+hStart,codeMarker[a]+hStop);
          } catch (Throwable t) {
          }
  }
  /* TESTING VERSION
  void selectAddress(int a, Color b) {
          int hStart = 0;
          int hStop = 0;
          String s = codeString[a];
          char sa[] = s.toCharArray();
          //TESTING int scan = 0;
          int scan = 0;
          int comment = 0;
          for (int i=0; i<sa.length; i++) {
                  if (scan == 0) {
                          switch(sa[i]) {
                                  case '/':
                                          comment++;
                                          if (comment==2) {
                                                  scan = 1;
                                          }
                                          //TESTING
                                          break;
                                  case ';': hStop=i+1; break;
                                  default: comment=0; break;
                          }
                  } else {
                          if (sa[i] == '\n') {
                                  scan = 0;
                                  hStart = i;
                          }
                  }
          }
          try {
                  //TESTING hl.removeAllHighlights();
                  //hl.addHighlight(codeMarker[a]+hStart,codeMarker[a]+hStop,new DefaultHighlighter.DefaultHighlightPainter(b));
                  hl.addHighlight(codeMarker[a],codeMarker[a+1],new DefaultHighlighter.DefaultHighlightPainter(b));
                  jTextPane.select(codeMarker[a]+hStart,codeMarker[a]+hStop);
          } catch (Throwable t) {
          }
  } */


}