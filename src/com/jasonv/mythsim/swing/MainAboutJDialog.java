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
import java.awt.event.*;
import com.jasonv.mythsim.swing.*;
import java.io.*;

/**
 * The About Dialog.
 * @author Jason Vroustouris
 */
class MainAboutJDialog
    extends JDialog
    implements ActionListener {
  /** Create and display About Dialog. **/
  public MainAboutJDialog(JFrame f) {
    super(f, "About MythSim", true);
    //setSize(500, 300);
    //setLocation(100, 100);
    // Size Frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = new Dimension( (int) (450), (int) (200));
    int x = (int) (screenSize.width / 2 - frameSize.width / 2);
    int y = (int) (screenSize.height / 2 - frameSize.height / 2);
    setBounds(x, y, frameSize.width, frameSize.height);

    String text = MythSimSwing.version_title + " - https://github.com/jasonv/MythSim\n" +
        "\n" +
        "Copyright (C) 2002-2018 Jason Vroustouris\n" +
        "\n" +
        "This program is free software; you can redistribute it and/or modify\n" +
        "it under the terms of the GNU General Public License as published by\n" +
        "the Free Software Foundation; either version 2 of the License, or\n" +
        "(at your option) any later version.\n" +
        "\n" +
        "This program is distributed in the hope that it will be useful,\n" +
        "but WITHOUT ANY WARRANTY; without even the implied warranty of\n" +
        "MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n" +
        "GNU General Public License for more details.\n" +
        "\n" +
        "You should have received a copy of the GNU General Public License\n" +
        "along with this program; if not, write to the Free Software\n" +
        "Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA\n" +
        "\n" +
        "" +
        "MythSim 3.0: A Mythical Simulator for Real Students\n" +
        "CS398 Undergraduate Research\n" +
        "for Professor Mitch Theys\n" +
        "by Jason Vroustouris\n" +
        "University of Illinois at Chicago\n" +
        "Department of Computer Science\n" +
        "December 19, 2003\n" +
        "\n" +
        "Mythsim 1.0: A Detailed Computer Architecture Simulator\n" +
        "EECS 597 Master's Project\n" +
        "for Professor Jon Solworth\n" +
        "by Rolf Hettelsater\n" +
        "University of Illinois at Chicago\n" +
        "Department of Electrical Engineering and Computer Science\n" +
        "September 26, 1997\n";

    JTextPane jTextPane = new JTextPane();
    jTextPane.setText(text);
    jTextPane.setEditable(false);
    jTextPane.setSelectionColor(Color.WHITE);
    jTextPane.select(0, 1);
    JButton jButton = new JButton("Ok");
    jButton.addActionListener(this);
    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(new JScrollPane(jTextPane), BorderLayout.CENTER);
    getContentPane().add(jButton, BorderLayout.SOUTH);
    show();

  }

  /** Handel ActionEvents from the OK JButton **/
  public void actionPerformed(ActionEvent a) {
    dispose();
  }
}