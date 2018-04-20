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
 * A template for new plugins.
 * @author Jason Vroustouris
 */
public class PluginFrame extends MythPlugInFrame {
	public PluginFrame(MythSimSwing a, JFrame owner) {
		super("(Plugin)",	  // title
			true,	  // resizable
			true,   // closable
			true,   // maximizable
			true,   // iconifiable
			100,		  // set size x
			100,		  // set size y
			100,		  // set location x
			100,		  // set location y
			a,
			owner);  // MythSim object
		//getContentPane().add();
	}
	public void setup() {
	}
	public void step() {
	}


}