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
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
/**
 * Base class for all Plugins.
 * @author Jason Vroustouris
 */
public class MythPlugInFrame extends JDialog implements ActionListener {
	MythSimSwing ms = null;
	private JCheckBoxMenuItem jCheckBoxMenuItem = new JCheckBoxMenuItem();
        public Color SELECTION_COLOR_UCODE = new Color(255,200,200);
        public Color SELECTION_COLOR_MEM = new Color(200,255,200);
        public Color SELECTION_COLOR_REGISTERS = new Color(200,200,255);
	PluginAction action = new PluginAction();
	public MythPlugInFrame(String aa,	  // title
							boolean ab,	  // resizable
							boolean ac,   // closable
							boolean ad,   // maximizable
							boolean ae,   // iconifiable
							int ba,		  // set size x
							int bb,		  // set size y
							int ca,		  // set location x
							int cb,		  // set location y
							MythSimSwing da,
							JFrame owner) { // MythSim object
		//super(aa,ab,ac,ad,ae);
		super(owner,aa);
		addWindowListener(new WindowEventHandler());
		action.putValue(Action.NAME,aa);
		//setEnabled(false);
		ms = da;
        super.setSize(ba,bb);
        super.setLocation(ca,cb);
		super.hide();
	}
	public void setup() {
	}
	public void step() {
		super.repaint();
	}
	public void repaint() {
		super.repaint();
	}
	public Action getAction() {
		return action;
	}

	public void setEnabled(boolean value) {
		action.setEnabled(value);
		super.setEnabled(value);
	}

	/** remove all highlights */
	public void clean() {
	}
	public void setJCheckBoxMenuItem(JCheckBoxMenuItem a) {
		jCheckBoxMenuItem = a;
	}
	public void actionPerformed(ActionEvent a) {
		jCheckBoxMenuItem = (JCheckBoxMenuItem)a.getSource();
		setVisible(jCheckBoxMenuItem.getState());
	}

	private class PluginAction extends AbstractAction {
		public void actionPerformed(ActionEvent a) {
		}
	}

	class WindowEventHandler extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
			jCheckBoxMenuItem.setState(false);
        }
	}

}
