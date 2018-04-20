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
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import com.jasonv.mythsim.core.*;
import com.jasonv.mythsim.swing.*;
import com.jasonv.mythsim.swing.plugin.*;

/**
 * Swing interface to the simulator.
 * @author Jason Vroustouris
 */
public class MythSimSwing
    extends MythSim {
  public static final int OPEN_ACTION = 0;
  public static final int RELOAD_ACTION = 1;
  public static final int RESET_ACTION = 2;
  public static final int LAST_ACTION = 3;
  public static final int MINUS100_ACTION = 4;
  public static final int MINUS10_ACTION = 5;
  public static final int MINUS1_ACTION = 6;
  public static final int PLUS1_ACTION = 7;
  public static final int PLUS10_ACTION = 8;
  public static final int PLUS100_ACTION = 9;
  public static final int NEXT_ACTION = 10;
  public static final int RUN_ACTION = 11;
  public static final int EXIT_ACTION = 12;

  private Action mainAction[] = new MainAction[13];

  JCheckBoxMenuItem pmi[] = null;
  MythPlugInFrame plugin[] = new MythPlugInFrame[7];
  File currentDirectory = null;
  MainJFrame f = null;
  JMenu pluginJMenu = new JMenu("Window");



  /* ******************************************************
   Constructors
   ****************************************************** */
  public MythSimSwing(MainJFrame _f) {
    super();
    f = _f;
    plugin[0] = new ALUFrame(this, f);
    plugin[1] = new DatapathFrame(this, f);
    plugin[2] = new MemoryFrame(this, f);
    plugin[3] = new MemorySourceFrame(this, f);
    plugin[4] = new RegisterFrame(this, f);
    plugin[5] = new MicrostoreFrame(this, f);
    plugin[6] = new MicrocodeSourceFrame(this, f);
    //plugin[4] = new LocationFrame(this,f);
    //((LocationFrame)plugin[4]).setup(plugin);

    pmi = new JCheckBoxMenuItem[plugin.length];

    for (int i = 0; i < plugin.length; i++) {
      //pmi[i] = new JCheckBoxMenuItem(plugin[i].getTitle());
      pmi[i] = new JCheckBoxMenuItem(plugin[i].getAction());
      plugin[i].setJCheckBoxMenuItem(pmi[i]);

      pmi[i].addActionListener(plugin[i]);
      pmi[i].setState(plugin[i].isVisible());
      pluginJMenu.add(pmi[i]);
    }
    mainAction[OPEN_ACTION] = new MainAction(OPEN_ACTION, "Open",
                                             "Open source files.");
    mainAction[RELOAD_ACTION] = new MainAction(RELOAD_ACTION, "Reload",
                                               "Reload source files.",
                                               "images/reload.gif");
    mainAction[RESET_ACTION] = new MainAction(RESET_ACTION, "Restart",
                                              "Start again at address zero.",
                                              "images/reset.gif");
    mainAction[LAST_ACTION] = new MainAction(LAST_ACTION, "Previous",
                                             "Step back to previous opcode.",
                                             "images/last.gif");
    mainAction[MINUS100_ACTION] = new MainAction(MINUS100_ACTION, "-100",
                                                 "Step back 100 clock cycles.",
                                                 "images/minus100.gif");
    mainAction[MINUS10_ACTION] = new MainAction(MINUS10_ACTION, "-10",
                                                "Step back 10 clock cycles.",
                                                "images/minus10.gif");
    mainAction[MINUS1_ACTION] = new MainAction(MINUS1_ACTION, "-1",
                                               "Step back 1 clock cycle.",
                                               "images/minus1.gif");
    mainAction[PLUS1_ACTION] = new MainAction(PLUS1_ACTION, "+1",
                                              "Step forward 1 clock cycle.",
                                              "images/plus1.gif");
    mainAction[PLUS10_ACTION] = new MainAction(PLUS10_ACTION, "+10",
                                               "Step forward 10 clock cycles.",
                                               "images/plus10.gif");
    mainAction[PLUS100_ACTION] = new MainAction(PLUS100_ACTION, "+100",
        "Step forward 100 clock cycles.", "images/plus100.gif");
    mainAction[NEXT_ACTION] = new MainAction(NEXT_ACTION, "Next",
                                             "Step forward to next opcode.",
                                             "images/next.gif");
    mainAction[RUN_ACTION] = new MainAction(RUN_ACTION, "Run",
                                            "Run until end.",
                                            "images/run.gif");
    mainAction[EXIT_ACTION] = new MainAction(EXIT_ACTION, "Exit",
                                             "Exit the simulator.");
    setMode(NO_FILES_MODE);
  }

  /* ******************************************************
   Action Functions
   ****************************************************** */
  /** OPEN_ACTION */
  public void open() throws Exception {
    FileJDialog fileJDialog = new FileJDialog(this);
    if (fileJDialog.isValid()) {
      reload();
      stepPlugins();
    }
    fileJDialog.dispose();
  }

  /** RELOAD_ACTION */
  public void reload() throws Exception {
    f.clean();
    System.out.print("Loading Source Files...\n");
    System.out.print("Mem File: " + getMemoryFilePath() +
                     "\n");
    System.out.print("Ucode File: " + getMicrocodeFilePath() +
                     "\n");
    f.setTitleLine(" " + getMemoryFileName() + " - " + getMicrocodeFileName());
    super.parse();
    System.out.print("Parsing...\n");
    setMode(VALID_FILES_MODE);
    boot();
    System.out.print("Started.\n");
  }

  /** RESET_ACTION */
  public void reset() throws Exception {
    f.clean();
    super.boot();
    stepPlugins();
    System.out.print("Started.\n");
  }

  /** LAST_ACTION */
  public void last() throws MythError {
    super.last();
    stepPlugins();
  }

  /** MINUS100_ACTION, MINUS10_ACTION, MINUS1_ACTION,
      PLUS1_ACTION, PLUS10_ACTION, PLUS100_ACTION */
  public void step(int x) throws MythError {
    super.step(x);
    stepPlugins();
  }

  /** NEXT_ACTION */
  public void next() throws MythError {
    super.next();
    stepPlugins();
  }

  /** RUN_ACTION */
  public void run() throws MythError {
    System.out.print("Running.\n");
    super.run();
    stepPlugins();
  }

  /** EXIT_ACTION */
  public void exit_app() {
    System.out.print("Exiting...\n");
    System.exit(0);
  }

  /* ******************************************************
   Action Class
   ****************************************************** */
  /** Most actions in the swing interface go thorough this system. */
  class MainAction
      extends AbstractAction {
    int actionNumber = 0;
    public MainAction(int an, String title, String description) {
      super(title);
      actionNumber = an;
      putValue(SHORT_DESCRIPTION, description);
    }

    public MainAction(int an, String title, String description, ImageIcon img) {
      super(title, img);
      actionNumber = an;
      putValue(SHORT_DESCRIPTION, description);
    }

    public MainAction(int an, String title, String description, ImageIcon img,
                      Integer mnemonic) {
      super(title, img);
      actionNumber = an;
      putValue(SHORT_DESCRIPTION, description);
      putValue(ACCELERATOR_KEY, mnemonic);
    }

    public MainAction(int an, String title, String description, String img) {
      super(title);
      try {
        ClassLoader cl = this.getClass().getClassLoader();
        URL url = null;
        url = cl.getResource(img);
        putValue(SMALL_ICON, new ImageIcon(url));
        actionNumber = an;
        putValue(SHORT_DESCRIPTION, description);
      }
      catch (Exception ex) {
        System.out.println("Not found: " + img);
      }
    }

    public MainAction(int an, String title, String description, String img,
                      Integer mnemonic) {
      super(title);
      try {
        ClassLoader cl = this.getClass().getClassLoader();
        URL url = cl.getResource(img);
        putValue(SMALL_ICON, new ImageIcon(url));
        actionNumber = an;
        putValue(SHORT_DESCRIPTION, description);
        putValue(ACCELERATOR_KEY, mnemonic);
      }
      catch (Exception ex) {
        System.out.println("Not found: " + img);
      }
    }

    public void actionPerformed(ActionEvent e) {
      try {
        switch (actionNumber) {
          case OPEN_ACTION:
            open();
            break;
          case RELOAD_ACTION:
            reload();
            break;
          case RESET_ACTION:
            reset();
            break;
          case LAST_ACTION:
            last();
            break;
          case MINUS100_ACTION:
            step( -100);
            break;
          case MINUS10_ACTION:
            step( -10);
            break;
          case MINUS1_ACTION:
            step( -1);
            break;
          case PLUS1_ACTION:
            step(1);
            break;
          case PLUS10_ACTION:
            step(10);
            break;
          case PLUS100_ACTION:
            step(100);
            break;
          case NEXT_ACTION:
            next();
            break;
          case RUN_ACTION:
            run();
            break;
          case EXIT_ACTION:
            exit_app();
            break;
          default:
            break;
        }
      }
      catch (MythError me) {
        //System.out.print("MythError: " + me.toString() + "\n");
        //me.printStackTrace();
        System.out.print(me.message() + "\n");
        stepPlugins();
      }
      catch (MythParserError mpe) {
        setMode(INVALID_FILES_MODE);
        f.step();
        System.out.print(mpe.message() + "\n");
        mpe.printStackTrace();
      }
      catch (Exception ee) {
        System.out.print("Internal Error: ");
        ee.printStackTrace();
      }
    }
  }

  /* ******************************************************
   Action Support Functions
   ****************************************************** */

  /** Freezes buttons and menus based on a current mode value. */
  public void setMode(int _mode) {
    mode = _mode;
    switch (mode) {
      case NO_FILES_MODE:
        mainAction[RELOAD_ACTION].setEnabled(false);
        mainAction[RESET_ACTION].setEnabled(false);
        mainAction[LAST_ACTION].setEnabled(false);
        mainAction[MINUS100_ACTION].setEnabled(false);
        mainAction[MINUS10_ACTION].setEnabled(false);
        mainAction[MINUS1_ACTION].setEnabled(false);
        mainAction[PLUS1_ACTION].setEnabled(false);
        mainAction[PLUS10_ACTION].setEnabled(false);
        mainAction[PLUS100_ACTION].setEnabled(false);
        mainAction[NEXT_ACTION].setEnabled(false);
        mainAction[RUN_ACTION].setEnabled(false);
        for (int i = 0; i < plugin.length; i++) {
          plugin[i].setEnabled(false);
        }

        break;
      case INVALID_FILES_MODE:
        mainAction[RELOAD_ACTION].setEnabled(true);
        mainAction[RESET_ACTION].setEnabled(false);
        mainAction[LAST_ACTION].setEnabled(false);
        mainAction[MINUS100_ACTION].setEnabled(false);
        mainAction[MINUS10_ACTION].setEnabled(false);
        mainAction[MINUS1_ACTION].setEnabled(false);
        mainAction[PLUS1_ACTION].setEnabled(false);
        mainAction[PLUS10_ACTION].setEnabled(false);
        mainAction[PLUS100_ACTION].setEnabled(false);
        mainAction[NEXT_ACTION].setEnabled(false);
        mainAction[RUN_ACTION].setEnabled(false);
        for (int i = 0; i < plugin.length; i++) {
          plugin[i].setEnabled(false);
        }
        break;
      case VALID_FILES_MODE:
        mainAction[RELOAD_ACTION].setEnabled(true);
        mainAction[RESET_ACTION].setEnabled(true);
        mainAction[LAST_ACTION].setEnabled(true);
        mainAction[MINUS100_ACTION].setEnabled(true);
        mainAction[MINUS10_ACTION].setEnabled(true);
        mainAction[MINUS1_ACTION].setEnabled(true);
        mainAction[PLUS1_ACTION].setEnabled(true);
        mainAction[PLUS10_ACTION].setEnabled(true);
        mainAction[PLUS100_ACTION].setEnabled(true);
        mainAction[NEXT_ACTION].setEnabled(true);
        mainAction[RUN_ACTION].setEnabled(true);
        for (int i = 0; i < plugin.length; i++) {
          plugin[i].setEnabled(true);
        }
        break;
      default:
        break;
    }
  }

  public int getMode() {
    return mode;
  }

  public Action getAction(int index) {
    return mainAction[index];
  }

  public JMenuItem createJMenuItem(int index) {
    return new JMenuItem(mainAction[index]);
  }

  public JButton createJButton(int index) {
    JButton temp = new JButton(mainAction[index]);
    temp.setText("");
    return temp;
  }

  /** @return A JMenu will plugin in it. */
  public JMenu getPluginJMenu() {
    return pluginJMenu;
  }

  /** Setup each of the plugins. */
  public void setupPlugins() {
    for (int i = 0; i < plugin.length; i++) {
      plugin[i].setup();
    }
    f.step();
  }

  /** Step each of the plugins. */
  public void stepPlugins() {
    for (int i = 0; i < plugin.length; i++) {
      plugin[i].step();
    }
    f.step();
  }

  /** Boot comptuer. */
  public void boot() throws MythError {
    super.boot();
    setupPlugins();
    stepPlugins();
  }

}