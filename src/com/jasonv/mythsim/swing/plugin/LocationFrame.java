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
 * Shows the locations of all the windows on the screen (Debugging tool).
 * @author Jason Vroustouris
 */
public class LocationFrame extends MythPlugInFrame {
	MythPlugInFrame plugin[] = null;
	public LocationFrame(MythSimSwing a, JFrame owner) {
		super("Location Frame",	  // title
			true,	  // resizable
			true,   // closable
			true,   // maximizable
			true,   // iconifiable
			100,		  // set size x
			100,		  // set size y
			0,		  // set location x
			300,		  // set location y
			a,
			owner);  // MythSim object
		//getContentPane().add();
	}
	public void setup() {
	}
	public void setup(MythPlugInFrame a[]) {
		plugin = a;
	}
	public void step() {
		getContentPane().removeAll();
		getContentPane().setLayout(new GridLayout(plugin.length+1,5));
		getContentPane().add(new JLabel("Plugin"));
		getContentPane().add(new JLabel("Width"));
		getContentPane().add(new JLabel("Height"));
		getContentPane().add(new JLabel("X"));
		getContentPane().add(new JLabel("Y"));
		for (int i=0; i<plugin.length; i++) {
			getContentPane().add(new JLabel(" " + plugin[i].getTitle()));
			getContentPane().add(new JLabel(" " + plugin[i].getWidth()));
			getContentPane().add(new JLabel(" " + plugin[i].getHeight()));
			getContentPane().add(new JLabel(" " + plugin[i].getX()));
			getContentPane().add(new JLabel(" " + plugin[i].getY()));
		}
		getContentPane().repaint();
		setVisible(true);
	}


}