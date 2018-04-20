package com.jasonv.mythsim.swing;


import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * A simple Java Console for your application (Swing version)
 * Requires Java 1.1.5 or higher
 * Disclaimer the use of this source is at your own risk.
 * Permision to use and distribute into your own applications
 * RJHM van den Bergh , rvdb@comweb.nl
 * http://alpha.comweb.nl/java/Console/Console.html
 * Modified to work with JTextPanel
 * @author RJHM van den Bergh
 * @author Jason Vroustouris
 */
public class Console
    extends WindowAdapter
    implements WindowListener, ActionListener, Runnable {
  private MainJFrame frame;
  private JTextPane textArea;
  private Thread reader;
  private Thread reader2;
  private boolean quit;

  private final PipedInputStream pin = new PipedInputStream();
  private final PipedInputStream pin2 = new PipedInputStream();

  Thread errorThrower; // just for testing (Throws an Exception at this Console

  public Console(MainJFrame f) {
    // create all components and add them
    frame = f;
    //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    //Dimension frameSize = new Dimension( (int) (screenSize.width / 2),
    //                                    (int) (screenSize.height / 2));
    //int x = (int) (frameSize.width / 2);
    //int y = (int) (frameSize.height / 2);
    //frame.setBounds(x, y, frameSize.width, frameSize.height);

    // Size Frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = new Dimension( (int) (530), (int) (220));
    int x = (int) (screenSize.width / 2 - frameSize.width / 2);
    int y = (int) (screenSize.height / 2 - frameSize.height / 2);
    frame.setBounds(x, y, frameSize.width, frameSize.height);

    textArea = frame.getJTextPane();
    textArea.setEditable(false);
    JButton button = new JButton("clear");

    //frame.getContentPane().setLayout(new BorderLayout());
    //frame.getContentPane().add(new JScrollPane(textArea),BorderLayout.CENTER);
    //frame.getContentPane().add(button,BorderLayout.SOUTH);
    frame.setVisible(true);

    frame.addWindowListener(this);
    button.addActionListener(this);

    try {
      PipedOutputStream pout = new PipedOutputStream(this.pin);
      System.setOut(new PrintStream(pout, true));
    }
    catch (java.io.IOException io) {
      frame.appendToLog("Couldn't redirect STDOUT to this console\n" +
                        io.getMessage());
    }
    catch (SecurityException se) {
      frame.appendToLog("Couldn't redirect STDOUT to this console\n" +
                        se.getMessage());
    }

    try {
      PipedOutputStream pout2 = new PipedOutputStream(this.pin2);
      System.setErr(new PrintStream(pout2, true));
    }
    catch (java.io.IOException io) {
      frame.appendToLog("Couldn't redirect STDERR to this console\n" +
                        io.getMessage());
    }
    catch (SecurityException se) {
      frame.appendToLog("Couldn't redirect STDERR to this console\n" +
                        se.getMessage());
    }

    quit = false; // signals the Threads that they should exit

    // Starting two seperate threads to read from the PipedInputStreams
    //
    reader = new Thread(this);
    reader.setDaemon(true);
    reader.start();
    //
    reader2 = new Thread(this);
    reader2.setDaemon(true);
    reader2.start();
  }

  public synchronized void windowClosed(WindowEvent evt) {
    quit = true;
    this.notifyAll(); // stop all threads
    try {
      reader.join(1000);
      pin.close();
    }
    catch (Exception e) {}
    try {
      reader2.join(1000);
      pin2.close();
    }
    catch (Exception e) {}
    System.exit(0);
  }

  public synchronized void windowClosing(WindowEvent evt) {
    frame.setVisible(false); // default behaviour of JFrame
    frame.dispose();
  }

  public synchronized void actionPerformed(ActionEvent evt) {
    textArea.setText("");
  }

  public synchronized void run() {
    try {
      while (Thread.currentThread() == reader) {
        try {
          this.wait(100);
        }
        catch (InterruptedException ie) {}
        if (pin.available() != 0) {
          String input = this.readLine(pin);
          frame.appendToLog(input);
        }
        if (quit) {
          return;
        }
      }

      while (Thread.currentThread() == reader2) {
        try {
          this.wait(100);
        }
        catch (InterruptedException ie) {}
        if (pin2.available() != 0) {
          String input = this.readLine(pin2);
          frame.appendToLog(input);
        }
        if (quit) {
          return;
        }
      }
    }
    catch (Exception e) {
      frame.appendToLog("\nConsole reports an Internal error.");
      frame.appendToLog("The error is: " + e);
    }

    // just for testing (Throw a Nullpointer after 1 second)
    if (Thread.currentThread() == errorThrower) {
      try {
        this.wait(1000);
      }
      catch (InterruptedException ie) {}
      throw new NullPointerException("Application test: throwing an NullPointerException It should arrive at the console");
    }

  }

  public synchronized String readLine(PipedInputStream in) throws IOException {
    String input = "";
    do {
      int available = in.available();
      if (available == 0) {
        break;
      }
      byte b[] = new byte[available];
      in.read(b);
      input = input + new String(b, 0, b.length);
    }
    while (!input.endsWith("\n") && !input.endsWith("\r\n") && !quit);
    return input;
  }

  public static void main(String[] arg) {
  }
}