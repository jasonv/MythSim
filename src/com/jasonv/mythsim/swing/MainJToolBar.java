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
import java.awt.*;
/**
 * The tool bar below the menu bar.
 * @author Jason Vroustouris
 */
public class MainJToolBar extends JPanel {
	public MainJToolBar(MythSimSwing ms) {
		super(new GridLayout(1,11));
		add(ms.createJButton(MythSimSwing.RELOAD_ACTION));
		add(ms.createJButton(MythSimSwing.RESET_ACTION));
		add(ms.createJButton(MythSimSwing.LAST_ACTION));
		add(ms.createJButton(MythSimSwing.MINUS100_ACTION));
		add(ms.createJButton(MythSimSwing.MINUS10_ACTION));
		add(ms.createJButton(MythSimSwing.MINUS1_ACTION));
		add(ms.createJButton(MythSimSwing.PLUS1_ACTION));
		add(ms.createJButton(MythSimSwing.PLUS10_ACTION));
		add(ms.createJButton(MythSimSwing.PLUS100_ACTION));
		add(ms.createJButton(MythSimSwing.NEXT_ACTION));
		add(ms.createJButton(MythSimSwing.RUN_ACTION));
	}
}