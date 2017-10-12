import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;


class RangePanel extends JPanel implements ActionListener, ChangeListener {

  // Ranges:
  JButton[][] btn_hands;
  boolean[][] hand_sel;
  float range;
  JLabel lbl_range;
  JSlider sld_range;

  
  JButton btn_clear;
  JButton btn_toppairs;
  JButton btn_midpairs;
  JButton btn_lowpairs;

  JButton btn_connect1_s;
  JButton btn_connect2_s;
  JButton btn_connect1_o;
  JButton btn_connect2_o;

  JButton btn_acextop_s;
  JButton btn_acextop_o;
  JButton btn_acexmid_s;
  JButton btn_acexmid_o;
  JButton btn_acexlow_s;
  JButton btn_acexlow_o;

  boolean act_toppairs;
  boolean act_midpairs;
  boolean act_lowpairs;
  boolean act_connect1_s;
  boolean act_connect2_s;
  boolean act_connect1_o;
  boolean act_connect2_o;

  boolean act_acextop_s;
  boolean act_acextop_o;
  boolean act_acexmid_s;
  boolean act_acexmid_o;
  boolean act_acexlow_s;
  boolean act_acexlow_o;

  Color color_on;
  Color color_off;
  Color color_offB;

  HandsRanking ranking;

  RangePanel() {

    this.setLayout(null);
   
    String[] card_names = {"A","K","Q","J","T","9","8","7","6","5","4","3","2"};

    color_on   = new Color(255,244,0);
    color_off  = new Color(100,100,100);
    color_offB = new Color(200,200,200);;
    btn_hands = new JButton[13][13];
    hand_sel  = new boolean[13][13];
    ranking = new HandsRanking();
  
    for (int i=0; i<13; i++) {
      for (int j=0; j<13; j++) {
        int x = 5+i*35;
        int y = 60+j*35;
        String txt = card_names[i] +  card_names[j]; 
        if (i > j ) txt = card_names[j] + card_names[i] + "s";
        if (i < j ) txt = card_names[i] + card_names[j] + "o";
        btn_hands[i][j] = new JButton(txt);
        //btn_hands[i][j].setFont(new Font("Arial", Font.PLAIN, 8));
        btn_hands[i][j].setMargin(new Insets(0, 0, 0, 0));
        btn_hands[i][j].setBounds(x,y,35,35);
        btn_hands[i][j].addActionListener(this);
        this.add(btn_hands[i][j]);
        btn_hands[i][j].setBackground(color_off);
        hand_sel[i][j] = false;
      }
    }

    lbl_range = new JLabel("Range: " + String.format("%.1f",range)+"%"
                           ,SwingConstants.CENTER);
    lbl_range.setFont(new Font("Arial", Font.PLAIN, 18));
    lbl_range.setBounds(0,5,460,25);
    this.add(lbl_range);

    sld_range = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
    sld_range.setBounds(5,30,460,30);
    sld_range.addChangeListener(this);
  
    this.add(sld_range);
  
    btn_clear = new JButton("Clear");
    btn_clear.setBackground(color_offB); 
    btn_clear.setBounds(470,60,110,25);
    btn_clear.addActionListener(this);
    this.add(btn_clear);

    act_toppairs = false;
    btn_toppairs = new JButton("Top pairs");
    btn_toppairs.setBackground(color_offB);
    btn_toppairs.setBounds(470,120,110,25);
    btn_toppairs.addActionListener(this);
    this.add(btn_toppairs);

    act_midpairs = false;
    btn_midpairs = new JButton("Mid pairs");
    btn_midpairs.setBackground(color_offB);
    btn_midpairs.setBounds(470,150,110,25);
    btn_midpairs.addActionListener(this);
    this.add(btn_midpairs);

    act_lowpairs = false;
    btn_lowpairs = new JButton("Low pairs");
    btn_lowpairs.setBackground(color_offB);
    btn_lowpairs.setBounds(470,180,110,25);
    btn_lowpairs.addActionListener(this);
    this.add(btn_lowpairs);

    act_connect1_s = false;
    btn_connect1_s = new JButton("Conn 1 S");
    btn_connect1_s.setBackground(color_offB);
    btn_connect1_s.setBounds(470,210,110,25);
    btn_connect1_s.addActionListener(this);
    this.add(btn_connect1_s);

    act_connect2_s = false;
    btn_connect2_s = new JButton("Conn 2 S");
    btn_connect2_s.setBackground(color_offB);
    btn_connect2_s.setBounds(470,240,110,25);
    btn_connect2_s.addActionListener(this);
    this.add(btn_connect2_s);

    act_connect1_o = false;
    btn_connect1_o = new JButton("Conn 1 O");
    btn_connect1_o.setBackground(color_offB);
    btn_connect1_o.setBounds(470,270,110,25);
    btn_connect1_o.addActionListener(this);
    this.add(btn_connect1_o);

    act_connect2_o = false;
    btn_connect2_o = new JButton("Conn 2 O");
    btn_connect2_o.setBackground(color_offB);
    btn_connect2_o.setBounds(470,300,110,25);
    btn_connect2_o.addActionListener(this);
    this.add(btn_connect2_o);

    act_acextop_s = false;
    btn_acextop_s = new JButton("AXs high ");
    btn_acextop_s.setBackground(color_offB);
    btn_acextop_s.setBounds(470,330,110,25);
    btn_acextop_s.addActionListener(this);
    this.add(btn_acextop_s);

    act_acextop_o = false;
    btn_acextop_o = new JButton("AXo high");
    btn_acextop_o.setBackground(color_offB);
    btn_acextop_o.setBounds(470,360,110,25);
    btn_acextop_o.addActionListener(this);
    this.add(btn_acextop_o);


    act_acexmid_s = false;
    btn_acexmid_s = new JButton("AXs mid");
    btn_acexmid_s.setBackground(color_offB);
    btn_acexmid_s.setBounds(470,390,110,25);
    btn_acexmid_s.addActionListener(this);
    this.add(btn_acexmid_s);

    act_acexmid_o = false;
    btn_acexmid_o = new JButton("AXo mid");
    btn_acexmid_o.setBackground(color_offB);
    btn_acexmid_o.setBounds(470,420,110,25);
    btn_acexmid_o.addActionListener(this);
    this.add(btn_acexmid_o);

    act_acexlow_s = false;
    btn_acexlow_s = new JButton("AXs low");
    btn_acexlow_s.setBackground(color_offB);
    btn_acexlow_s.setBounds(470,450,110,25);
    btn_acexlow_s.addActionListener(this);
    this.add(btn_acexlow_s);

    act_acexlow_o = false;
    btn_acexlow_o = new JButton("AXo low");
    btn_acexlow_o.setBackground(color_offB);
    btn_acexlow_o.setBounds(470,480,110,25);
    btn_acexlow_o.addActionListener(this);
    this.add(btn_acexlow_o);

    ranking.init();

    //
    ////////////////////////////////////////////////////////////////////

  }

  ////////////////////////////////////////////////////////////////////////
  void pulseCard(int i, int j) {
    int fac;
    if (hand_sel[i][j] == false) {
      btn_hands[i][j].setBackground(color_on);
      hand_sel[i][j] = true;
      fac = 1;
      ranking.selectHand(i,j);
    } else {
      btn_hands[i][j].setBackground(color_off);
      hand_sel[i][j] = false;
      fac = -1;
    }
    float val = 0;
    if (i == j) {
      val = 12.0f/(52*51); 
    } else if (i > j) {
      val = 8.0f/(52*51);
    } else if (i < j) {
      val = 24.0f/(52*51);
    }

    range += val*fac*100;
    if (range < 0) range = 0;
    lbl_range.setText("Range: " + String.format("%.2f",range)+"%");
  }

  void cardON(int i, int j) {
    int fac = 0;
    if (hand_sel[i][j] == false) {
      btn_hands[i][j].setBackground(color_on);
      hand_sel[i][j] = true;
      fac = 1;
    } 
    float val = 0;
    if (i == j) {
      val = 12.0f/(52*51); 
    } else if (i > j) {
      val = 8.0f/(52*51);
    } else if (i < j) {
      val = 24.0f/(52*51);
    }

    range += val*fac*100;
    lbl_range.setText("Range: " + String.format("%.2f",range)+"%");
  }

  void cardOFF(int i, int j) {
    int fac = 0;
    if (hand_sel[i][j] == true) {
      btn_hands[i][j].setBackground(color_off);
      hand_sel[i][j] = false;
      fac = -1;
    } 
    float val = 0;
    if (i == j) {
      val = 12.0f/(52*51); 
    } else if (i > j) {
      val = 8.0f/(52*51);
    } else if (i < j) {
      val = 24.0f/(52*51);
    }

    range += val*fac*100;
    if (range < 0) range = 0;
    lbl_range.setText("Range: " + String.format("%.2f",range)+"%");
  }

  void cleanRange() {
      for (int i=0; i<13; i++) 
        for (int j=0; j<13; j++) cardOFF(i,j);
      btn_toppairs.setBackground(color_offB); act_toppairs = false;
      btn_midpairs.setBackground(color_offB); act_midpairs = false;
      btn_lowpairs.setBackground(color_offB); act_lowpairs = false;
      btn_connect1_s.setBackground(color_offB); act_connect2_s = false;
      btn_connect2_s.setBackground(color_offB); act_connect1_s = false;
      btn_connect1_o.setBackground(color_offB); act_connect1_o = false;
      btn_connect2_o.setBackground(color_offB); act_connect2_o = false;
      btn_acextop_s.setBackground(color_offB); act_acextop_s = false;
      btn_acextop_o.setBackground(color_offB); act_acextop_o = false;
      btn_acexmid_s.setBackground(color_offB); act_acexmid_s = false;
      btn_acexmid_o.setBackground(color_offB); act_acexmid_o = false;
      btn_acexlow_s.setBackground(color_offB); act_acexlow_s = false;
      btn_acexlow_o.setBackground(color_offB); act_acexlow_o = false;
  }

  ////////////////////////////////////////////////////////////////////////
  @Override
  public void actionPerformed(ActionEvent e) {
    for (int i=0; i<13; i++) 
      for (int j=0; j<13; j++) 
        if (e.getSource()==btn_hands[i][j]) pulseCard(i,j);

    if (e.getSource()==btn_clear) {
      range = 0.0f;
      cleanRange(); 
      sld_range.setValue(0);

    } else if (e.getSource()==btn_toppairs) {
      if (act_toppairs) {
        btn_toppairs.setBackground(color_offB);
        for (int i=0; i<4; i++) cardOFF(i,i);
        act_toppairs = false;
      } else {
        btn_toppairs.setBackground(color_on);
        for (int i=0; i<4; i++) cardON(i,i);
        act_toppairs = true;
      }
    } else if (e.getSource()==btn_midpairs) {
      if (act_midpairs) {
        btn_midpairs.setBackground(color_offB);
        for (int i=4; i<8; i++) cardOFF(i,i);
        act_midpairs = false;
      } else {
        btn_midpairs.setBackground(color_on);
        for (int i=4; i<8; i++) cardON(i,i);
        act_midpairs = true;
      }
    } else if (e.getSource()==btn_lowpairs) {
      if (act_lowpairs) {
        btn_lowpairs.setBackground(color_offB);
        for (int i=8; i<13; i++) cardOFF(i,i);
        act_lowpairs = false;
      } else {
        btn_lowpairs.setBackground(color_on);
        for (int i=8; i<13; i++) cardON(i,i);
        act_lowpairs = true;
      }
    } else if (e.getSource()==btn_connect1_s) {
      if (act_connect1_s) {
        btn_connect1_s.setBackground(color_offB);
        for (int i=2; i<13; i++) cardOFF(i,i-1);
        act_connect1_s = false;
      } else {
        btn_connect1_s.setBackground(color_on);
        for (int i=2; i<13; i++) cardON(i,i-1);
        act_connect1_s = true;
      }
    } else if (e.getSource()==btn_connect2_s) {
      if (act_connect2_s) {
        btn_connect2_s.setBackground(color_offB);
        for (int i=3; i<13; i++) cardOFF(i,i-2);
        act_connect2_s = false;
      } else {
        btn_connect2_s.setBackground(color_on);
        for (int i=3; i<13; i++) cardON(i,i-2);
        act_connect2_s = true;
      }
    } else if (e.getSource()==btn_connect1_o) {
      if (act_connect1_o) {
        btn_connect1_o.setBackground(color_offB);
        for (int i=1; i<12; i++) cardOFF(i,i+1);
        act_connect1_o = false;
      } else {
        btn_connect1_o.setBackground(color_on);
        for (int i=1; i<12; i++) cardON(i,i+1);
        act_connect1_o = true;
      }
    } else if (e.getSource()==btn_connect2_o) {
      if (act_connect2_o) {
        btn_connect2_o.setBackground(color_offB);
        for (int i=1; i<11; i++) cardOFF(i,i+2);
        act_connect2_o = false;
      } else {
        btn_connect2_o.setBackground(color_on);
        for (int i=1; i<11; i++) cardON(i,i+2);
        act_connect2_o = true;
      }
    } else if (e.getSource()==btn_acextop_s) {
      if (act_acextop_s) {
        btn_acextop_s.setBackground(color_offB);
        for (int i=1; i<5; i++) cardOFF(i,0);
        act_acextop_s = false;
      } else {
        btn_acextop_s.setBackground(color_on);
        for (int i=1; i<5; i++) cardON(i,0);
        act_acextop_s = true;
      }
    } else if (e.getSource()==btn_acextop_o) {
      if (act_acextop_o) {
        btn_acextop_o.setBackground(color_offB);
        for (int i=1; i<5; i++) cardOFF(0,i);
        act_acextop_o = false;
      } else {
        btn_acextop_o.setBackground(color_on);
        for (int i=1; i<5; i++) cardON(0,i);
        act_acextop_o = true;
      }
    } else if (e.getSource()==btn_acexmid_s) {
      if (act_acexmid_s) {
        btn_acexmid_s.setBackground(color_offB);
        for (int i=5; i<9; i++) cardOFF(i,0);
        act_acexmid_s = false;
      } else {
        btn_acexmid_s.setBackground(color_on);
        for (int i=5; i<9; i++) cardON(i,0);
        act_acexmid_s = true;
      }
    } else if (e.getSource()==btn_acexmid_o) {
      if (act_acexmid_o) {
        btn_acexmid_o.setBackground(color_offB);
        for (int i=5; i<9; i++) cardOFF(0,i);
        act_acexmid_o = false;
      } else {
        btn_acexmid_o.setBackground(color_on);
        for (int i=5; i<9; i++) cardON(0,i);
        act_acexmid_o = true;
      }
    } else if (e.getSource()==btn_acexlow_s) {
      if (act_acexlow_s) {
        btn_acexlow_s.setBackground(color_offB);
        for (int i=9; i<13; i++) cardOFF(i,0);
        act_acexlow_s = false;
      } else {
        btn_acexlow_s.setBackground(color_on);
        for (int i=9; i<13; i++) cardON(i,0);
        act_acexlow_s = true;
      }
    } else if (e.getSource()==btn_acexlow_o) {
      if (act_acexlow_o) {
        btn_acexlow_o.setBackground(color_offB);
        for (int i=9; i<13; i++) cardOFF(0,i);
        act_acexlow_o = false;
      } else {
        btn_acexlow_o.setBackground(color_on);
        for (int i=9; i<13; i++) cardON(0,i);
        act_acexlow_o = true;
      }
    } 

  }

  @Override
  public void stateChanged(ChangeEvent e) {

    cleanRange();
    range = sld_range.getValue();
    lbl_range.setText("Range: " + String.format("%.2f",range)+"%");
    int nhv = ranking.getNHprob(range);
    for (int i=0; i<nhv; i++) {
      ChartHand h = ranking.getHandProb(i);
      btn_hands[h.i][h.j].setBackground(color_on);
      hand_sel[h.i][h.j] = true;
    }
  }

}





