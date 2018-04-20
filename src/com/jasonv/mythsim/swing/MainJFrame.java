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
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import com.jasonv.mythsim.swing.*;
import java.awt.event.WindowEvent;

/**
 * The Main Window (Frame).
 *
 * This holds the menu bar, control buttons and message log.
 * @author Jason Vroustouris
 */
public class MainJFrame
    extends JFrame
    implements WindowListener {

  JLabel statusLine = new JLabel("");
  JTextPane logScreen = new JTextPane();
  DefaultStyledDocument logDoc = new DefaultStyledDocument();
  SimpleAttributeSet logAttrib = new SimpleAttributeSet();
  private Console console = new Console(this);
  private MythSimSwing ms = new MythSimSwing(this);

  /**
   * Creates and displays the MainJFrame.
   */
  public MainJFrame() {
    this.setTitle(MythSimSwing.version_title);
    // Set up Frame Size
    //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    //Dimension frameSize = new Dimension( (int) (screenSize.width / 2),
    //                                    (int) (screenSize.height / 2));
    //int x = (int) (frameSize.width / 2);
    //int y = (int) (frameSize.height / 2);
    //setBounds(x, y, frameSize.width, frameSize.height);

    this.addWindowListener(this);
    logScreen.setDocument(logDoc);
    logScreen.setEditable(false);
    logScreen.setFont(new Font("Courier", Font.PLAIN, 12));

    try {
      ClassLoader cl = this.getClass().getClassLoader();
      URL url = cl.getResource("images/ms.gif");
      setIconImage(new ImageIcon(url).getImage());
    }
    catch (Exception e) {
      System.out.println("Not Found: images/ms.gif");
    }
    setBackground(Color.WHITE);
    //setSize(500, 400);
    setJMenuBar(new MythMenuBar(ms));

    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(BorderLayout.NORTH, new MainJToolBar(ms));
    getContentPane().add(BorderLayout.CENTER, new JScrollPane(logScreen));
    getContentPane().add(BorderLayout.SOUTH, statusLine);
    step();


    show();


  }

  public JTextPane getJTextPane() {
    return logScreen;
  }

  /*
   * Adds a message to the message log.
   * @param text message to add
   */

  public void appendToLog(String text) {
    try {
      logDoc.insertString(logDoc.getLength(), text, logAttrib);
    }
    catch (BadLocationException ble) {
      System.err.println("Couldn't insert initial text.");
    }
  }

  /**
   * Clears the message log.
   */
  public void clean() {
    logScreen.setText("");
    step();
  }

  /**
   * Sets the title of the frame.
   * @param text
   */
  public void setTitleLine(String text) {
    setTitle(MythSimSwing.version_title + " - " + text);
  }

  /**
   * Updates this window.
   */
  public void step() {
    switch (ms.getMode()) {
      case MythSimSwing.NO_FILES_MODE:

        statusLine.setText(
            " Go to File and then Open to select a mem and ucode file.");
        break;
      case MythSimSwing.INVALID_FILES_MODE:
        statusLine.setText(" Parse Error. 1) Edit you ucode and/or mem file. 2) Save it. 3) Then press reload.");
        break;
      case MythSimSwing.VALID_FILES_MODE:
        statusLine.setText("  Clock: " + ms.getStatus(MythSimSwing.CLOCK) +
                           "     Current Memory Address: " +
                           ms.tc2normal(ms.getStatus(MythSimSwing.MAR)) +
                           "     Current Ucode Address: " +
                           ms.getStatus(MythSimSwing.CURRENT_ADDRESS)
                           );
        break;
      default:
        break;
    }
  }

  public void windowOpened(WindowEvent windowEvent) {
  }

  public void windowClosing(WindowEvent windowEvent) {
    System.exit(0);
  }

  public void windowClosed(WindowEvent windowEvent) {
  }

  public void windowIconified(WindowEvent windowEvent) {
  }

  public void windowDeiconified(WindowEvent windowEvent) {
  }

  public void windowActivated(WindowEvent windowEvent) {
  }

  public void windowDeactivated(WindowEvent windowEvent) {
  }
}