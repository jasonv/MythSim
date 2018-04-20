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
 * The Open Dialog.
 * @author Jason Vroustouris
 */
public class FileJDialog
    extends JDialog
    implements ActionListener {

  /** Reference to mythsim object. **/
  private MythSimSwing ms;
  private JButton ucodeJButton = new JButton("...");
  private JButton memJButton = new JButton("...");
  private JButton okJButton = new JButton("Ok");
  private JButton cancelJButton = new JButton("Cancel");
  private JTextField ucodeJTextField = new JTextField();
  private JTextField memJTextField = new JTextField();
  private File ucodeFile = null;
  private File memFile = null;
  private boolean filesValid = false;
  /** Create and display About Dialog. **/
  public FileJDialog(MythSimSwing _ms) {
    super(_ms.f, "Open", true);
    ms = _ms;    // Set up Frame Size
    filesValid = false;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = new Dimension( (int) (500),
                                        (int) (100));
    int x = (int) (screenSize.width / 2 - 250);
    int y = (int) (screenSize.height / 2 - 50);
    setBounds(x, y, frameSize.width, frameSize.height);
    ucodeJButton.addActionListener(this);
    memJButton.addActionListener(this);
    okJButton.addActionListener(this);
    cancelJButton.addActionListener(this);
    ucodeFile = ms.getMicrocodeFile();
    memFile = ms.getMemoryFile();
    ucodeJTextField.setText(ucodeFile.toString());
    memJTextField.setText(memFile.toString());
    ucodeJTextField.setEnabled(false);
    memJTextField.setEnabled(false);

    getContentPane().setLayout(new BorderLayout());
// Center Panel
    JPanel centerPanel = new JPanel(new BorderLayout());
    JPanel centerWestPanel = new JPanel(new GridLayout(2, 1));
    JPanel centerCenterPanel = new JPanel(new GridLayout(2, 1));
    JPanel centerEastPanel = new JPanel(new GridLayout(2, 1));
    centerWestPanel.add(new JLabel(" mem file:     "));
    centerWestPanel.add(new JLabel(" ucode file:     "));
    centerCenterPanel.add(memJTextField);
    centerCenterPanel.add(ucodeJTextField);
    centerEastPanel.add(memJButton);
    centerEastPanel.add(ucodeJButton);
    centerPanel.add(centerWestPanel, BorderLayout.WEST);
    centerPanel.add(centerCenterPanel, BorderLayout.CENTER);
    centerPanel.add(centerEastPanel, BorderLayout.EAST);
    getContentPane().add(centerPanel, BorderLayout.CENTER);

// South Panel
    JPanel southPanel = new JPanel(new GridLayout(1, 2));
    southPanel.add(okJButton);
    southPanel.add(cancelJButton);
    getContentPane().add(southPanel, BorderLayout.SOUTH);
    show();

  }

  public boolean isValid() {
    return filesValid;
  }

  public void selectMemoryFile() {
    JFileChooser chooserMemory = new JFileChooser(memFile);
    chooserMemory.setDialogTitle("Select mem file.");
    chooserMemory.setCurrentDirectory(ms.currentDirectory);
    //chooserMemory.setCurrentDirectory(memFile);
    switch (chooserMemory.showOpenDialog(this)) {
      case JFileChooser.APPROVE_OPTION:
        memFile = chooserMemory.getSelectedFile();
        this.memJTextField.setText(memFile.toString());
        break;
      default:
        dispose();
    }
    ms.currentDirectory = chooserMemory.getCurrentDirectory();
  }

  public void selectUcodeFile() {
    JFileChooser chooserMicrocode = new JFileChooser(ucodeFile);
    chooserMicrocode.setDialogTitle("Open ucode file.");
    //if (ms.currentDirectory != null) {
    chooserMicrocode.setCurrentDirectory(ms.currentDirectory);
    //}
    //chooserMicrocode.setCurrentDirectory(ucodeFile);
    switch (chooserMicrocode.showOpenDialog(this)) {
      case JFileChooser.APPROVE_OPTION:
        ucodeFile = chooserMicrocode.getSelectedFile();
        this.ucodeJTextField.setText(ucodeFile.toString());
        break;
      default:
        dispose();
    }
    ms.currentDirectory = chooserMicrocode.getCurrentDirectory();
  }


  /** Handel ActionEvents from the OK JButton **/
  public void actionPerformed(ActionEvent a) {
    if (a.getSource() == this.ucodeJButton) {
      selectUcodeFile();
    }
    if (a.getSource() == this.memJButton) {
      selectMemoryFile();
    }
    if (a.getSource() == this.okJButton) {
      try {
        ms.setFileMicrocode(ucodeFile);
      }
      catch (FileNotFoundException ex) {
        JOptionPane.showMessageDialog(ms.f, "Ucode file not found.");
        return;
      }
      try {
        ms.setFileMemory(memFile);
      }
      catch (FileNotFoundException ex) {
        JOptionPane.showMessageDialog(ms.f, "Memory file not found.");
        return;
      }
      filesValid = true;
      hide();
    }
    if (a.getSource() == this.cancelJButton) {
      ms.setMode(MythSimSwing.NO_FILES_MODE);
      filesValid = false;
      hide();
    }
  }

}