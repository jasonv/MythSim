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
 * The ALU Window.
 * @author Jason Vroustouris
 */
public class ALUFrame extends MythPlugInFrame {
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

	public ALUFrame (MythSimSwing a, JFrame owner) {
          super("ALU",  // title
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
				temp += " Skipping... \n";
			}
		}

		second_to_last_clock = last_clock;
		last_clock = clock;

		int alu_sel = ms.getStatus(MythSim.ALU_SEL);
		String Reg_A_Num = "" + ms.getStatus(MythSim.VA_SEL_BUS);
		String Reg_B_Num = "" + ms.getStatus(MythSim.VB_SEL_BUS);
		String Reg_A_Val = bit8(ms.getRegA());
		String Reg_B_Val = bit8(ms.getRegB());
		String Reg_B_Val_Not = bit8not(ms.getRegB());
		String Cin_Val = bit8(ms.getStatus(MythSim.C_IN));
                temp +=" ALU_BUS:";
		temp += "\t" + ms.getStatus(MythSim.C_OUT) + " " + bit8(ms.getStatus(MythSim.ALU_BUS));
		if (ms.getStatus(MythSim.V)==1) {
			temp += " V";
		}

                temp += "\n -------------------------------- \n";
                String result_temp = "";
                if (ms.getStatus(MythSim.VR0_WRITE_BUS)==1) result_temp += "=>R0";
                if (ms.getStatus(MythSim.VR1_WRITE_BUS)==1) result_temp += "=>R1";
                if (ms.getStatus(MythSim.VR2_WRITE_BUS)==1) result_temp += "=>R2";
                if (ms.getStatus(MythSim.VR3_WRITE_BUS)==1) result_temp += "=>R3";
                if (ms.getStatus(MythSim.R4_WRITE)==1) result_temp += "=>R4";
                if (ms.getStatus(MythSim.R5_WRITE)==1) result_temp += "=>R5";
                if (ms.getStatus(MythSim.R6_WRITE)==1) result_temp += "=>R6";
                if (ms.getStatus(MythSim.R7_WRITE)==1) result_temp += "=>R7";
                if (result_temp.length() != 0) {

                  if (ms.getStatus(MythSim.RESULT_SEL) == 0)
                    temp += " ALU_BUS=>RESULT_BUS";
                  if (ms.getStatus(MythSim.RESULT_SEL) == 1)
                    temp += " MDR=>RESULT_BUS";
                  if (ms.getStatus(MythSim.RESULT_SEL) == 2)
                    temp += " IR_CONST4=>RESULT_BUS";
                  if (ms.getStatus(MythSim.RESULT_SEL) == 3)
                    temp += " IR_CONST8=>RESULT_BUS";
                  temp += result_temp + " ";
                }

                if (ms.getStatus(MythSim.MAR_SEL)==1) temp += "\n ALU_BUS=>MAR ";
                if (ms.getStatus(MythSim.MDR_SEL)==1) temp += "\n ALU_BUS=>MDR ";
                if (ms.getStatus(MythSim.MDR_SEL)==2) temp += "\n DATA_BUS=>MDR ";
                if (ms.getStatus(MythSim.IR0_SEL)==1) temp += "\n DATA_BUS=>IR0 ";
                if (ms.getStatus(MythSim.IR1_SEL)==1) temp += "\n DATA_BUS=>IR1 ";
                temp+="\n -------------------------------- ";
                temp += "\n " + MythSim.funcName(alu_sel);

		temp += " \n";
		switch(alu_sel) {
			case MythSimSwing.ALU_NOT:
				temp += " r" + Reg_A_Num + ":\t  " + Reg_A_Val + " \n";
			break;
			case MythSimSwing.ALU_OR:
			case MythSimSwing.ALU_AND:
			case MythSimSwing.ALU_XOR:
				temp += " r" + Reg_A_Num + ":\t  " + Reg_A_Val + " \n";
				temp += " r" + Reg_B_Num + ":\t  " + Reg_B_Val + " \n";
			break;
			case MythSimSwing.ALU_ADD:
				temp += " r" + Reg_A_Num + ":\t  " + Reg_A_Val + " \n";
				temp += " r" + Reg_B_Num + ":\t  " + Reg_B_Val + " \n";
				temp += " c_in:\t  " + Cin_Val + " \n";
			break;
			case MythSimSwing.ALU_ADDA:
				temp += " r" + Reg_A_Num + ":\t  " + Reg_A_Val + " \n";
				temp += " c_in:\t  " + Cin_Val + " \n";
			break;
			case MythSimSwing.ALU_SUB:
				temp += " r" + Reg_A_Num + ":\t  " + Reg_A_Val + " \n";
				temp += " r" + Reg_B_Num + "':\t  " + Reg_B_Val_Not + " \n";
				temp += " c_in:\t  " + Cin_Val + " \n";
			break;
			case MythSimSwing.ALU_SUBA:
				temp += " r" + Reg_A_Num + ":\t  " + Reg_A_Val + " \n";
				temp += "\t  1111 1111 \n";
				temp += " c_in:\t  " + Cin_Val + " \n";
			break;
			default:
			break;
		}
                temp+=" ================================ ";
                temp += "[" + clock + "] \n";
		//jTextPane.setText(temp);
		appendToScreen(temp);
	}

	public void appendToScreen(String text) {
		try {
			int doc_length = logDoc.getLength();
			logDoc.insertString(doc_length, text, logAttrib);
			doc_length = logDoc.getLength();
			hl.removeAllHighlights();
			hl.addHighlight(doc_length-text.length(),doc_length,new DefaultHighlighter.DefaultHighlightPainter(new Color(200,200,200)));

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