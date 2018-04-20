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

package com.jasonv.mythsim.swing;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

/**
 * The menu bar at the top of the frame.
 * @author Jason Vroustouris
 */
public class MythMenuBar extends JMenuBar {
	MythSimSwing ms = null;
	public MythMenuBar(MythSimSwing mythSim)
	{
		ms = mythSim;

		// File Menu
		JMenu fileMenu = new JMenu("File");
		fileMenu.add(ms.createJMenuItem(MythSimSwing.OPEN_ACTION));
		JMenuItem reloadJMenuItem = ms.createJMenuItem(MythSimSwing.RELOAD_ACTION);
		reloadJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F7,0));
		fileMenu.add(reloadJMenuItem);
		fileMenu.add(new AbstractAction("Exit") {
			public void actionPerformed(ActionEvent a) {
				System.exit(0);
			}
		});
		add(fileMenu);

		// Simulate Menu
		JMenu simulateMenu = new JMenu("Run");
                JMenuItem runMenuItem = ms.createJMenuItem(MythSimSwing.RUN_ACTION);
                runMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5,0));
                simulateMenu.add(runMenuItem);
		simulateMenu.add(ms.createJMenuItem(MythSimSwing.RESET_ACTION));
                simulateMenu.addSeparator();
		simulateMenu.add(ms.createJMenuItem(MythSimSwing.MINUS100_ACTION));
		simulateMenu.add(ms.createJMenuItem(MythSimSwing.MINUS10_ACTION));
		simulateMenu.add(ms.createJMenuItem(MythSimSwing.MINUS1_ACTION));
		simulateMenu.add(ms.createJMenuItem(MythSimSwing.PLUS1_ACTION));
		simulateMenu.add(ms.createJMenuItem(MythSimSwing.PLUS10_ACTION));
		simulateMenu.add(ms.createJMenuItem(MythSimSwing.PLUS100_ACTION));
                simulateMenu.addSeparator();
                simulateMenu.add(ms.createJMenuItem(MythSimSwing.LAST_ACTION));
		simulateMenu.add(ms.createJMenuItem(MythSimSwing.NEXT_ACTION));
		add(simulateMenu);

		// Plugin Menu
		JMenu windowMenu = ms.getPluginJMenu();
		add(windowMenu);

		// Help Menu
		JMenu helpMenu = new JMenu("Help");
		helpMenu.add(new AbstractAction("About") {
			public void actionPerformed(ActionEvent a) {
				MainAboutJDialog majd = new MainAboutJDialog(new JFrame());
			}
		});
		add(helpMenu);
	}
}