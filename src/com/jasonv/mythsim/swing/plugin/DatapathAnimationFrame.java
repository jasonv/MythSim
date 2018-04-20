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

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.jasonv.mythsim.swing.*;
import com.jasonv.mythsim.text.Text;

/**
 * An Animated Datapath Window (Beta).
 * @author Jason Vroustouris
 * @todo Change screen to black and wires to white.
 * @todo Finish and test animation.
 */
public class DatapathAnimationFrame
    extends MythPlugInFrame {
  static final int UP = 0;
  static final int RIGHT = 1;
  static final int DOWN = 2;
  static final int LEFT = 3;
  static final int HORIZONTAL = 4;
  static final int VERTICAL = 5;
  static final int HORIZONTAL_C = 6;
  static final int VERTICAL_C = 7;
  public final int[] s = ms.getStatus();
  public final int[] p = ms.getPreviousStatus();
  int displayBase = 2;

  MythMap map = null;
  public DatapathAnimationFrame(MythSimSwing a, JFrame owner) {
    super("Datapath Animation (beta)",
          false, //resizable
          true, //closable
          false, //maximizable
          true, //iconifiable
          475,
          425,
          0,
          300,
          a,
          owner);
    map = new MythMap();
    map.setSize(460, 390);
    /*for (int i = 0; i < MythSimSwing.LENGTH; i++) {
      p[i] = s[i];
      s[i] = ms.getStatus(i);
         }*/
    setResizable(false);
    setContentPane(map);
  }

  public void setup() {
  }

  public void step() {
    /*for (int i = 0; i < MythSimSwing.LENGTH; i++) {
      p[i] = s[i];
      s[i] = ms.getStatus(i);
         }
         if (s[MythSimSwing.CLOCK] == 0) {
      for (int i = 0; i < MythSimSwing.LENGTH; i++) {
        p[i] = 0;
      }
         }*/
    repaint();
  }

  public void setBase(int a) {
    displayBase = a;
    map.repaint();
  }

  boolean hasChanged(int index) {
    //return p[index] != s[index];
    if (index >= 0 && index <= 3) {
      return p[MythSimSwing.VR0_WRITE_BUS + index] == 1;
    }
    if (index >= 4 && index <= 7) {
      return p[MythSimSwing.R0_WRITE + index] == 1;
    }
    if (index == MythSimSwing.MAR) {
      return p[MythSimSwing.MAR_SEL] == 1;
    }
    if (index == MythSimSwing.MDR) {
      return p[MythSimSwing.MDR_SEL] == 1;
    }
    if (index == MythSimSwing.IR_0) {
      return p[MythSimSwing.IR0_SEL] == 1;
    }
    if (index == MythSimSwing.IR_1) {
      return p[MythSimSwing.IR1_SEL] == 1;
    }
    return false;
  }

  void redActive(Graphics g, int index) {
    g.setColor(Color.LIGHT_GRAY);
    if (index >= MythSimSwing.R0_WRITE && index < MythSimSwing.R0_WRITE_SET) {
      if (s[index + (MythSimSwing.CONTROL_WORD_LENGTH / 2)] == 1) {
        g.setColor(Color.RED);
        return;
      }
    }
    g.setColor(Color.LIGHT_GRAY);
  }

  void redActive(Graphics g, int index, int index2) {
    g.setColor(Color.LIGHT_GRAY);
    if (index >= MythSimSwing.R0_WRITE && index < MythSimSwing.R0_WRITE_SET) {
      if (s[index + (MythSimSwing.CONTROL_WORD_LENGTH / 2)] == 1) {
        g.setColor(Color.RED);
        return;
      }
    }
    if (index2 >= MythSimSwing.R0_WRITE && index2 < MythSimSwing.R0_WRITE_SET) {
      if (s[index2 + (MythSimSwing.CONTROL_WORD_LENGTH / 2)] == 1) {
        g.setColor(Color.RED);
        return;
      }
    }
    g.setColor(Color.LIGHT_GRAY);
  }

  /* Draw an Arrow*/
  void line(Graphics g, int x, int y, int direction, int length) {
    //g.setColor(Color.LIGHT_GRAY);
    int xpts[] = new int[4];
    int ypts[] = new int[4];
    switch (direction) {
      case UP:
        xpts[0] = x - 3;
        ypts[0] = y + 3;
        xpts[1] = x;
        ypts[1] = y;
        xpts[2] = x + 3;
        ypts[2] = y + 3;
        xpts[3] = x - 3;
        ypts[3] = y + 3;
        g.fillPolygon(xpts, ypts, 4);
        g.drawPolygon(xpts, ypts, 4);
        g.drawLine(x, y, x, y + length);
        break;
      case RIGHT:
        xpts[0] = x - 3;
        ypts[0] = y + 3;
        xpts[1] = x;
        ypts[1] = y;
        xpts[2] = x - 3;
        ypts[2] = y - 3;
        xpts[3] = x - 3;
        ypts[3] = y + 3;
        g.fillPolygon(xpts, ypts, 4);
        g.drawPolygon(xpts, ypts, 4);
        g.drawLine(x, y, x - 3, y + 3);
        g.drawLine(x, y, x - 3, y - 3);
        g.drawLine(x, y, x - length, y);
        break;
      case DOWN:
        xpts[0] = x + 3;
        ypts[0] = y - 3;
        xpts[1] = x;
        ypts[1] = y;
        xpts[2] = x - 3;
        ypts[2] = y - 3;
        xpts[3] = x + 3;
        ypts[3] = y - 3;
        g.fillPolygon(xpts, ypts, 4);
        g.drawPolygon(xpts, ypts, 4);
        g.drawLine(x, y, x + 3, y - 3);
        g.drawLine(x, y, x - 3, y - 3);
        g.drawLine(x, y, x, y - length);
        break;
      case LEFT:
        xpts[0] = x + 3;
        ypts[0] = y + 3;
        xpts[1] = x;
        ypts[1] = y;
        xpts[2] = x + 3;
        ypts[2] = y - 3;
        xpts[3] = x + 3;
        ypts[3] = y + 3;
        g.fillPolygon(xpts, ypts, 4);
        g.drawPolygon(xpts, ypts, 4);
        g.drawLine(x, y, x + 3, y + 3);
        g.drawLine(x, y, x + 3, y - 3);
        g.drawLine(x, y, x + length, y);
        break;
      case HORIZONTAL:
        g.drawLine(x, y, x + length, y);
        break;
      case VERTICAL:
        g.drawLine(x, y, x, y + length);
        break;
      case HORIZONTAL_C:
        g.drawLine(x - length / 2, y, x + length / 2, y);
        break;
      case VERTICAL_C:
        g.drawLine(x, y - length / 2, x, y + length / 2);
        break;
      default:
        break;
    }
    //g.setColor(Color.LIGHT_GRAY);
    return;
  }

  void line(Graphics g, int x, int y, int direction, int length, int value,
            boolean show) {
    switch (direction) {
      case RIGHT:
        line(g, x, y, direction, length);
        if (show) {
          g.drawString(MythSimSwing.name(value), x - length + 10, y + 12);
        }
        g.drawString("" + Text.binString(s[value]), x - length + 10, y - 2);
        break;
      case HORIZONTAL:
        line(g, x, y, direction, length);
        if (show) {
          g.drawString(MythSimSwing.name(value), x - length + 10, y + 12);
        }
        g.drawString("" + Text.binString(s[value]), x - length + 10, y - 2);
        break;
      default:
        break;
    }
  }

  void downInBus(Graphics g, int x, int y, int value) {
    line(g, x, y, DOWN, 20);
    g.drawString(MythSimSwing.name(value), x + 6, y - 6);
    g.drawString("" + s[value], x - 3, y - 22);
  }

  void downOutBus(Graphics g, int x, int y, int value) {
    line(g, x, y, DOWN, 20);
    g.drawString(MythSimSwing.name(value), x + 6, y - 6);
    g.drawString("" + s[value], x - 3, y + 12);
  }

  void rightBus(Graphics g, int x, int y, int value) {
    line(g, x, y, RIGHT, 80);
    g.drawString(MythSimSwing.name(value), x - 70, y + 12);
    g.drawString("" + Text.binString(s[value]), x - 70, y - 2);
  }

  void horizontalBus(Graphics g, int x, int y, int value) {
    line(g, x, y, HORIZONTAL, -80);
    g.drawString(MythSimSwing.name(value), x - 70, y + 12);
    g.drawString("" + Text.binString(s[value]), x - 70, y - 2);
  }

  void downInRightBus(Graphics g, int x, int y, int value) {
    line(g, x - 15, y, VERTICAL, -15);
    line(g, x, y, RIGHT, 15);
    g.drawString("" + s[value], x - 12, y - 3);
    g.drawString(MythSimSwing.name(value), x - 25, y - 20);
  }

  void downInLeftBus(Graphics g, int x, int y, int value) {
    line(g, x + 15, y, VERTICAL, -15);
    line(g, x, y, LEFT, 15);
    g.drawString("" + s[value], x + 6, y - 3);
    g.drawString(MythSimSwing.name(value), x + 5, y - 20);
  }

  void upInRightBus(Graphics g, int x, int y, int l, int value) {
    if (s[value] == 1) {
      g.setColor(Color.RED);
    }
    line(g, x, y, UP, 10);
    g.drawString("" + s[value], x - 3, y + 22);
    //g.drawLine(x,y+24,x,y+24+(l*12));
    g.drawLine(x, y + 24, x, y + 14 + (l * 12));
    //g.drawString(MythSimSwing.name(value),x+8,y+(l*12)+24);
    g.drawString(MythSimSwing.name(value), x, y + (l * 12) + 24);
    g.setColor(Color.LIGHT_GRAY);
  }

  void upInLeftBus(Graphics g, int x, int y, int l, int value) {
    if (s[value] == 1) {
      g.setColor(Color.RED);
    }
    line(g, x, y, UP, 10);
    g.drawString("" + s[value], x - 3, y + 22);
    //g.drawLine(x,y+24,x,y+24+(l*12));
    g.drawLine(x, y + 24, x, y + 14 + (l * 12));
    //g.drawString(MythSimSwing.name(value),x-50,y+(l*12)+24);
    g.drawString(MythSimSwing.name(value), x - 42, y + (l * 12) + 24);
    g.setColor(Color.LIGHT_GRAY);
  }

  void regBox(Graphics g, int x, int y, int value) {
    g.drawRect(x, y - 15, 70, 15);
    if (hasChanged(value)) {
      g.setColor(Color.BLUE);
    }
    switch (displayBase) {
      //case 2:  g.drawString("" + Text.binString(s[value]),x+6,y-2); break;
      //case 10: g.drawString("" + s[value],x+6,y-2); break;
      case 2:
        g.drawString("" + Text.binString(s[value]), x + 2, y - 2);
        break;
      case 10:
        g.drawString("" + s[value], x + 2, y - 2);
        break;
      default:
        break;
    }
    g.setColor(Color.LIGHT_GRAY);
    g.drawString(MythSimSwing.name(value), x + 2, y + 15);
  }

  void reg(Graphics g, int x, int y, int value, String a) {
    if (hasChanged(value)) {
      g.setColor(Color.BLUE);
    }
    switch (displayBase) {
      case 2:
        g.drawString(a + Text.binString(s[value]), x, y);
        break;
      case 10:
        g.drawString(a + s[value], x, y);
        break;
      default:
        break;
    }
    g.setColor(Color.LIGHT_GRAY);
  }

  void regBoxRight(Graphics g, int x, int y, int value) {
    int width = 62;
    int height = -15;
    int x1 = x;
    int x2 = x + width;
    int y1 = y;
    int y2 = y + height;
    g.drawRect(x1, y2, width, height);
    g.drawString("" + Text.binString(s[value]), x + 2, y - 2);
    g.drawString(MythSimSwing.name(value), x + 2, y + 15);
    g.drawLine(x2, y2 + height / 2, x2 + 20, y2 + height);
  }

  void memRight(Graphics g, int x, int y, int value) {
    line(g, x, y, RIGHT, 60);
    g.drawString(MythSimSwing.name(value), x - 50, y - 2);
    g.drawString("" + s[value], x - 15, y - 2);
  }

  void memLeft(Graphics g, int x, int y, int value) {
    line(g, x, y, LEFT, 60);
    g.drawString(MythSimSwing.name(value), x + 10, y - 2);
    g.drawString("" + s[value], x + 45, y - 2);
  }

  void grid(Graphics g) {
    for (int m = 0; m < 500; m += 10) {
      if (m % 50 == 0) {
        g.setColor(new Color(180, 180, 255));
      }
      else {
        g.setColor(new Color(180, 180, 180));
      }
      g.drawLine(0, m, 600, m);
      g.drawLine(m, 0, m, 600);
    }
  }

  void registerfile(Graphics g, int x, int y) {
    int width = 100;
    int height = 125;

    int x1 = x; // 90
    int x2 = x + (width / 2); // 140
    int x3 = x + width; // 190

    int y1 = y; // 40
    int y2 = y + (height / 2); // 102
    int y3 = y + height; // 165


    // VA_SEL
    if (s[MythSimSwing.A_SEL_SET] == 1 || s[MythSimSwing.RJ_SEL_SET] == 1) {
      g.setColor(Color.RED);
    }
    downInBus(g, x2 - 40, y1, MythSimSwing.VA_SEL_BUS);
    g.setColor(Color.LIGHT_GRAY);

    // VB_SEL
    if (s[MythSimSwing.B_SEL_SET] == 1 || s[MythSimSwing.RK_SEL_SET] == 1) {
      g.setColor(Color.RED);
    }
    downInBus(g, x2 + 10, y1, MythSimSwing.VB_SEL_BUS);
    g.setColor(Color.LIGHT_GRAY);

    // A and B busses on right side of register file
    if (p[MythSimSwing.A_SEL_SET] == 1 || p[MythSimSwing.RJ_SEL_SET] == 1) {
      g.setColor(Color.BLUE);
    }
    line(g, x3 + 80, y1 + 25, RIGHT, 80, MythSimSwing.A_BUS, true);
    g.setColor(Color.LIGHT_GRAY);
    if (p[MythSimSwing.B_SEL_SET] == 1 || p[MythSimSwing.RK_SEL_SET] == 1) {
      g.setColor(Color.BLUE);
    }
    line(g, x3 + 80, y1 + 100, RIGHT, 80, MythSimSwing.B_BUS, true);
    g.setColor(Color.LIGHT_GRAY);

    // Write Lines on Bottom of Register File
    upInLeftBus(g, x2 - 40, y3, 1, MythSimSwing.VR0_WRITE_BUS);
    upInLeftBus(g, x2 - 30, y3, 2, MythSimSwing.VR1_WRITE_BUS);
    upInLeftBus(g, x2 - 20, y3, 3, MythSimSwing.VR2_WRITE_BUS);
    upInLeftBus(g, x2 - 10, y3, 4, MythSimSwing.VR3_WRITE_BUS);
    upInRightBus(g, x2 + 10, y3, 4, MythSimSwing.R4_WRITE);
    upInRightBus(g, x2 + 20, y3, 3, MythSimSwing.R5_WRITE);
    upInRightBus(g, x2 + 30, y3, 2, MythSimSwing.R6_WRITE);
    upInRightBus(g, x2 + 40, y3, 1, MythSimSwing.R7_WRITE);

    // Numbers
    reg(g, x1 + 2, y1 + 40, MythSimSwing.R_0, "R0: ");
    reg(g, x1 + 2, y1 + 40 + 12, MythSimSwing.R_1, "R1: ");
    reg(g, x1 + 2, y1 + 40 + 23, MythSimSwing.R_2, "R2: ");
    reg(g, x1 + 2, y1 + 40 + 34, MythSimSwing.R_3, "R3: ");
    reg(g, x1 + 2, y1 + 40 + 45, MythSimSwing.R_4, "R4: ");
    reg(g, x1 + 2, y1 + 40 + 56, MythSimSwing.R_5, "R5: ");
    reg(g, x1 + 2, y1 + 40 + 67, MythSimSwing.R_6, "R6: ");
    reg(g, x1 + 2, y1 + 40 + 78, MythSimSwing.R_7, "R7: ");

    // Register File Box
    g.setColor(Color.BLACK);
    g.drawRect(x1, y1, width, height);
    g.drawString("Registers", x1 + 10, y1 + 20);
    g.setColor(Color.LIGHT_GRAY);
  }

  void alu(Graphics g, int x, int y) {
    int width = 100;
    int height = 125;

    int x1 = x; // 90
    int x2 = x + (width / 2); // 140
    int x3 = x + width; // 190

    int y1 = y; // 40
    int y2 = y + (height / 2); // 102
    int y3 = y + height; // 165

    // Register File Title
    g.setColor(Color.BLACK);
    g.drawString("ALU", 310, 110);
    g.drawRect(270, 40, 100, 125);
    g.setColor(Color.BLUE);
    g.drawString(MythSimSwing.funcName(p[MythSimSwing.ALU_SEL]),330,60);
    g.drawString("" + p[MythSimSwing.C_IN],290,60);
    g.setColor(Color.LIGHT_GRAY);


    // ALU

    if (s[MythSimSwing.C_IN_SET] == 1) {
      g.setColor(Color.RED);
    }
    downInBus(g, 280, 40, MythSimSwing.C_IN);
    g.setColor(Color.LIGHT_GRAY);
    if (s[MythSimSwing.ALU_SEL_SET] == 1) {
      g.setColor(Color.RED);
    }
    downInBus(g, 280 + 50, 40, MythSimSwing.ALU_SEL);
    g.drawString("(" + MythSimSwing.funcName(s[MythSimSwing.ALU_SEL]) + ")",
                 338, 18);
    g.setColor(Color.LIGHT_GRAY);
    downOutBus(g, 270 + 5, 40 + 125 + 20, MythSimSwing.C_OUT);
    downOutBus(g, 270 + 50, 40 + 125 + 20, MythSimSwing.M_7);
    downOutBus(g, 270 + 95, 40 + 125 + 20, MythSimSwing.V);

  }

  class MythMap
      extends JPanel {
    MythMap() {}

    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      setBackground(Color.WHITE);
      //grid(g);

      g.setColor(Color.LIGHT_GRAY);
      //Write the clock in the top right hand corner.
      //g.drawString("Clock:" + s[MythSimSwing.CLOCK],20,20);
      registerfile(g, 90, 40);
      alu(g, 270, 40);

      // Write Back
      // MUX
      g.drawLine(40, 280 - 10, 40, 280 + 10);
      g.drawLine(50, 280 - 20, 50, 280 + 20);
      g.drawLine(40, 280 - 10, 50, 280 - 20);
      g.drawLine(40, 280 + 10, 50, 280 + 20);
      if (s[MythSimSwing.RESULT_SEL_SET] == 1) {
        g.setColor(Color.RED);
      }
      downInBus(g, 40 + 5, 280 - 15, MythSimSwing.RESULT_SEL);
      g.setColor(Color.LIGHT_GRAY);

      int baseline = 0;

      // IR1
      baseline = 340;
      if (s[MythSimSwing.IR1_SEL_SET] == 1) {
        g.setColor(Color.RED);
      }
      downInRightBus(g, 60, baseline - 7, MythSimSwing.IR1_SEL);
      g.setColor(Color.LIGHT_GRAY);
      regBox(g, 60, baseline, MythSimSwing.IR_1);

      // IR0
      if (s[MythSimSwing.IR0_SEL_SET] == 1) {
        g.setColor(Color.RED);
      }
      downInLeftBus(g, 200, baseline - 7, MythSimSwing.IR0_SEL);
      g.setColor(Color.LIGHT_GRAY);
      regBox(g, 130, baseline, MythSimSwing.IR_0);

      // MDR
      if (s[MythSimSwing.MDR_SEL_SET] == 1) {
        g.setColor(Color.RED);
      }
      downInLeftBus(g, 305, baseline - 7, MythSimSwing.MDR_SEL);
      g.setColor(Color.LIGHT_GRAY);
      regBox(g, 235, baseline, MythSimSwing.MDR);

      // MAR
      if (s[MythSimSwing.MAR_SEL_SET] == 1) {
        g.setColor(Color.RED);
      }
      downInLeftBus(g, 405, baseline - 7, MythSimSwing.MAR_SEL);
      g.setColor(Color.LIGHT_GRAY);
      regBox(g, 335, baseline, MythSimSwing.MAR);

      // IR0, IR1 and MDR
      line(g, 370, baseline + 40, HORIZONTAL, -275);
      line(g, 450, baseline + 40, RIGHT, 80, MythSimSwing.MEMORY_BUS, false);
      line(g, 95, baseline, UP, 40);
      line(g, 165, baseline, UP, 40);
      line(g, 285, baseline, UP, 40);
      if(p[MythSimSwing.IR1_SEL] == 1) {
        g.setColor(Color.BLUE);
        line(g, 95, baseline, UP, 40);
        line(g, 370, baseline + 40, HORIZONTAL, -275);
        line(g, 450, baseline + 40, RIGHT, 80, MythSimSwing.MEMORY_BUS, false);
        g.setColor(Color.LIGHT_GRAY);
      }
      if(p[MythSimSwing.IR0_SEL] == 1) {
        g.setColor(Color.BLUE);
        line(g, 165, baseline, UP, 40);
        line(g, 370, baseline + 40, HORIZONTAL, -205);
        line(g, 450, baseline + 40, RIGHT, 80, MythSimSwing.MEMORY_BUS, false);
        g.setColor(Color.LIGHT_GRAY);
      }
      if(p[MythSimSwing.MDR_SEL] == 1) {
        g.setColor(Color.BLUE);
        line(g, 285, baseline, UP, 40);
        line(g, 370, baseline + 40, HORIZONTAL, -85);
        line(g, 450, baseline + 40, RIGHT, 80, MythSimSwing.MEMORY_BUS, false);
        g.setColor(Color.LIGHT_GRAY);
      }

      // MAR

      line(g, 370, baseline, VERTICAL, 20);
      line(g, 450, 360, RIGHT, 65);
      line(g, 450, baseline + 20, RIGHT, 80, MythSimSwing.MAR, false);
      if(s[MythSimSwing.READ] == 1 || s[MythSimSwing.WRITE] == 1) {
        g.setColor(Color.RED);
        line(g, 370, baseline, VERTICAL, 20);
        line(g, 450, 360, RIGHT, 65);
        line(g, 450, baseline + 20, RIGHT, 80, MythSimSwing.MAR, false);
        g.setColor(Color.LIGHT_GRAY);
      }

      // ====================================================================
      // WRITE BACK
      // ====================================================================
      // Result Bus
      baseline = 280;

      g.setColor(Color.LIGHT_GRAY);
      line(g, 10, 110, VERTICAL, 170);
      line(g, 40, 280, HORIZONTAL, -30);
      line(g, 90, 110, RIGHT, 80, MythSimSwing.RESULT_BUS, true);

      // 1 ALU to Memory Interface
      g.setColor(Color.LIGHT_GRAY);
      horizontalBus(g, 370 + 80, 110, MythSimSwing.ALU_BUS);
      line(g, 450, 110, VERTICAL, 155);
      line(g, 50, baseline - 15, LEFT, 400);
      line(g, 280, baseline + 45, DOWN, 60);
      line(g, 370, baseline + 45, DOWN, 60);
      g.setColor(Color.LIGHT_GRAY);

      // 2
      line(g, 50, baseline - 5, LEFT, 210);
      line(g, 260, baseline + 45, VERTICAL, -50);

// 3
      line(g, 50, baseline + 5, LEFT, 132);
      line(g, 182, baseline + 35, VERTICAL, -30);
      line(g, 170, baseline + 35, HORIZONTAL, 25);

// 4
      line(g, 50, baseline + 15, LEFT, 115);
      line(g, 165, baseline + 40, VERTICAL, -25);
      line(g, 165, baseline + 40, HORIZONTAL_C, 70);


      g.setColor(Color.LIGHT_GRAY);
      if (
          p[MythSimSwing.VR0_WRITE_BUS] == 1 ||
          p[MythSimSwing.VR1_WRITE_BUS] == 1 ||
          p[MythSimSwing.VR2_WRITE_BUS] == 1 ||
          p[MythSimSwing.VR3_WRITE_BUS] == 1 ||
          p[MythSimSwing.R4_WRITE] == 1 ||
          p[MythSimSwing.R5_WRITE] == 1 ||
          p[MythSimSwing.R6_WRITE] == 1 ||
          p[MythSimSwing.R7_WRITE] == 1
          ) {
        g.setColor(Color.BLUE);
        line(g, 10, 110, VERTICAL, 170);
        line(g, 40, 280, HORIZONTAL, -30);
        line(g, 90, 110, RIGHT, 80, MythSimSwing.RESULT_BUS, true);
        baseline = 280;
        switch (p[MythSimSwing.RESULT_SEL]) {
          case MythSimSwing.RESULT_ALU:
            horizontalBus(g, 370 + 80, 110, MythSimSwing.ALU_BUS);
            line(g, 450, 110, VERTICAL, 155);
            line(g, 50, baseline - 15, LEFT, 400);
            break;
          case MythSimSwing.RESULT_MDR:
            line(g, 50, baseline - 5, LEFT, 210);
            line(g, 260, baseline + 45, VERTICAL, -50);
            break;
          case MythSimSwing.RESULT_IR_CONST4:
            line(g, 50, baseline + 5, LEFT, 132);
            line(g, 182, baseline + 35, VERTICAL, -30);
            line(g, 170, baseline + 35, HORIZONTAL, 25);
            break;
          case MythSimSwing.RESULT_IR_CONST8:
            line(g, 50, baseline + 15, LEFT, 115);
            line(g, 165, baseline + 40, VERTICAL, -25);
            line(g, 165, baseline + 40, HORIZONTAL_C, 70);
            break;
            default:
              System.out.print("This should never happen.");
              break;
        }
        g.setColor(Color.LIGHT_GRAY);
      }

// 1
      if (p[MythSimSwing.MAR_SEL] == 1) {
        g.setColor(Color.BLUE);
        horizontalBus(g, 370 + 80, 110, MythSimSwing.ALU_BUS);
  line(g, 450, 110, VERTICAL, 155);
//line(g, 50, baseline - 15, LEFT, 400);
  g.drawLine(370, baseline - 15, 370 + 80, baseline - 15);
//line(g, 370, baseline - 15, LEFT, 80);
  line(g, 370, baseline + 45, DOWN, 60);
  g.setColor(Color.LIGHT_GRAY);

      }



      // Memory Lines
      if (s[MythSimSwing.READ_SET] == 1) {
        g.setColor(Color.RED);
      }
      memRight(g, 450, 40, MythSimSwing.READ);
      g.setColor(Color.LIGHT_GRAY);
      if (s[MythSimSwing.WRITE_SET] == 1) {
        g.setColor(Color.RED);
      }
      memRight(g, 450, 60, MythSimSwing.WRITE);
      g.setColor(Color.LIGHT_GRAY);
      memLeft(g, 390, 80, MythSimSwing.WAIT);

    }
  }

}
