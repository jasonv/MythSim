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
import javax.swing.text.*;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.util.regex.*;
import com.jasonv.mythsim.swing.*;

/**
 * The register window.
 * @author Jason Vroustouris
 */
public class RegisterFrame extends MythPlugInFrame {
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

        int last_clock = 0;
        int second_to_last_clock = 0;
        JScrollPane jScrollPane = new JScrollPane(jTextPane);
        DefaultStyledDocument logDoc = new DefaultStyledDocument();
        SimpleAttributeSet logAttrib = new SimpleAttributeSet();
        Highlighter hl = jTextPane.getHighlighter();
        EditorKit ek = jTextPane.getEditorKit();

        public RegisterFrame (MythSimSwing a, JFrame owner) {
                super("Registers",	  // title
                        true,	  // resizable
                        true,   // closable
                        true,   // maximizable
                        true,   // iconifiable
                        400,		  // set size x
                        200,		  // set size y
                        0,		  // set location x
                        300,		  // set location y
                        a,
                        owner);  // MythSim object
                jTextPane.setDocument(logDoc);
                jTextPane.setEditable(false);
                jTextPane.setPreferredSize(new Dimension(10,10));
                jTextPane.setFont(new Font("Courier", Font.PLAIN, 12));

                getContentPane().add(jScrollPane);
        }
        public void setup() {
                jTextPane.setText("");
        }
        public void step() {
                String temp = "";
                int clock = ms.getStatus(MythSim.CLOCK);
                if (clock==0) {
                        jTextPane.setText("");
                } else {
                        if (clock == last_clock) {
                          return;
                        }
                        else if (clock != last_clock+1) {
                                temp += "Skipping... \n";
                        }
                }

                second_to_last_clock = last_clock;
                last_clock = clock;

                int alu_sel = ms.getStatus(MythSim.ALU_SEL);
                temp += "r0:" + ms.getStatus(MythSim.R_0) + "\t";
                temp += "r1:" + ms.getStatus(MythSim.R_1) + "\t";
                temp += "r2:" + ms.getStatus(MythSim.R_2) + "\t";
                temp += "r3:" + ms.getStatus(MythSim.R_3) + "\t";
                temp += "r4:" + ms.getStatus(MythSim.R_4) + "\t";
                temp += "r5:" + ms.getStatus(MythSim.R_5) + "\t";
                temp += "r6:" + ms.getStatus(MythSim.R_6) + "\t";
                temp += "r7:" + ms.getStatus(MythSim.R_7) + "\t";
                temp += "mar:" + ms.getStatus(MythSim.MAR) + "\t";
                temp += "mdr:" + ms.getStatus(MythSim.MDR) + "\t";
                temp += "ir0:" + ms.getStatus(MythSim.IR_0) + "\t";
                temp += "ir1:" + ms.getStatus(MythSim.IR_1) + "\t";
                temp += "[" + ms.getStatus(MythSim.CLOCK) + "] \n";
    /*for (int i=0; i<MythSimSwing.STATUS_ARRAY_LENGTH; i++) {
      temp += ms.name(i) + ":" + ms.getStatus(i) + "\t";
    }
    temp += "\n";*/

                appendToScreen(temp);
        }

        public void appendToScreen(String text) {
                try {
                        int doc_length = logDoc.getLength();
                        logDoc.insertString(doc_length, text, logAttrib);
                        doc_length = logDoc.getLength();
                        hl.removeAllHighlights();
                        hl.addHighlight(doc_length-text.length(),doc_length,new DefaultHighlighter.DefaultHighlightPainter(this.SELECTION_COLOR_REGISTERS));

                } catch (BadLocationException ble) {
                    System.err.println("Couldn't insert initial text.");
                }
        }


        private String bit8(int v) {
                boolean a[] = MythSim.int2bit(v);
                String temp = "";
                if (a[7]) temp+="1"; else temp+='0';
                if (a[6]) temp+="1"; else temp+='0';
                if (a[5]) temp+="1"; else temp+='0';
                if (a[4]) temp+="1"; else temp+='0';
                temp+=" ";
                if (a[3]) temp+="1"; else temp+='0';
                if (a[2]) temp+="1"; else temp+='0';
                if (a[1]) temp+="1"; else temp+='0';
                if (a[0]) temp+="1"; else temp+='0';
                temp += "  (" + v + ") ";
                return temp;
        }
        private String bit8not(int v) {
                boolean a[] = MythSim.int2bit(v);
                String temp = "";
                if (a[7]) temp+="0"; else temp+='1';
                if (a[6]) temp+="0"; else temp+='1';
                if (a[5]) temp+="0"; else temp+='1';
                if (a[4]) temp+="0"; else temp+='1';
                temp+=" ";
                if (a[3]) temp+="0"; else temp+='1';
                if (a[2]) temp+="0"; else temp+='1';
                if (a[1]) temp+="0"; else temp+='1';
                if (a[0]) temp+="0"; else temp+='1';
                temp += "  (" + v + ") ";
                return temp;
        }


}
