import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.TitledBorder;



//import javax.swing.JFrame;

//import javax.swing.JButton;

class EquityPanel extends JPanel implements ActionListener {

  JPanel panelCards;
  JPanel panelBoard;
  JPanel panelPlayers;
  JPanel panelResults;
  JPanel panelButtons;

  JButton btn_reset;
  JButton btn_start;

  JButton[] btn_board;
  boolean[] sel_board;
  JTextField txt_games;

  JButton[][] btn_players;
  JButton[] btn_ranges;
  JCheckBox[] ckb_players;
  JLabel[][] lbl_rates;

  JButton[][] btn_cards;
  JButton btn_anycard;
  boolean[][] sel_cards;

  boolean selectingBoard;
  boolean selectingPlayer;

  int[] PlayerSel;
  int BoardSel;

  Card[] cards_board;
  Card[][] cards_players;
  Card[][] cards_all;

  Color color_on;
  Color color_off;
  Color color_offB;
  Color color_free;

  Color[] color_suit;
  Color color_cardSel;

  Kernel kernel;
  RangePanel rangepanel;

  ///////////////////////////////////////////////////////////////////
  // Constructor
  EquityPanel(RangePanel _rangepanel) {

    rangepanel = _rangepanel;
    this.setLayout(null);
   
    cards_board = new Card[5];
    cards_players = new Card[10][2];
    cards_all = new Card[4][13];

    /*
    for (int i=0; i<5; i++) cards_board[i] = new Card();
    for (int i=0; i<10; i++) {
      cards_players[i][0] = new Card();
      cards_players[i][1] = new Card();
    }
    */

    //String[] card_names = {"A","K","Q","J","T","9","8","7","6","5","4","3","2"};
    //String[] suit_names = {"h","c","s","d"};

    color_on   = new Color(255,244,0);
    color_off  = new Color(100,100,100);
    color_offB = new Color(200,200,200);;
    color_free = new Color(250,250,250);;
    color_suit = new Color[4];
      color_suit[0] = new Color(200,0,0);
      color_suit[1] = new Color(0,200,0);
      color_suit[2] = new Color(0,0,0);
      color_suit[3] = new Color(0,0,200);
    color_cardSel = new Color(100,100,100);;

    panelButtons = new JPanel();
    {
      panelButtons.setLayout(null);
      panelButtons.setBounds(310,5,275,75);
   
      JLabel lbl = new JLabel("Number of games:");
      lbl.setBounds(0,10,130,25);
      panelButtons.add(lbl);

      txt_games = new JTextField("1000");
      txt_games.setBounds(130,10,60,25);
      panelButtons.add(txt_games);

      btn_start = new JButton("Calculate");
      btn_start.setMargin(new Insets(0, 0, 0, 0));
      btn_start.setBounds(195,10,80,25);
      btn_start.addActionListener(this);
      panelButtons.add(btn_start);

      btn_reset = new JButton("Reset");
      btn_reset.setBounds(10,45,75,25);
      btn_reset.addActionListener(this);
      panelButtons.add(btn_reset);


    }
    this.add(panelButtons);

    panelBoard = new JPanel();
    {
      panelBoard.setLayout(null);
      panelBoard.setBounds(5,5,300,75);
      TitledBorder border = new TitledBorder("Board");
      border.setTitleJustification(TitledBorder.CENTER);
      border.setTitlePosition(TitledBorder.TOP);
      panelBoard.setBorder(border);


      btn_board = new JButton[5];
      sel_board = new boolean[5];
      for (int i=0; i<5; i++) {
        btn_board[i] = new JButton("--");
        btn_board[i].addActionListener(this);
        btn_board[i].setMargin(new Insets(0, 0, 0, 0));
        btn_board[i].setBackground(color_free);
        cards_board[i] = null;
        panelBoard.add(btn_board[i]);
      }
      btn_board[0].setBounds(20  ,18,35,45);
      btn_board[1].setBounds(70  ,18,35,45);
      btn_board[2].setBounds(120 ,18,35,45);
      btn_board[3].setBounds(180 ,18,35,45);
      btn_board[4].setBounds(240 ,18,35,45);
    }
    this.add(panelBoard);
    
    PlayerSel = new int[2];
    panelPlayers = new JPanel();
    {
      panelPlayers.setLayout(null);
      panelPlayers.setBounds(5,85,200,430);
      TitledBorder border = new TitledBorder("Players");
      border.setTitleJustification(TitledBorder.CENTER);
      border.setTitlePosition(TitledBorder.TOP);
      panelPlayers.setBorder(border);

      btn_players = new JButton[10][2];
      btn_ranges = new JButton[10];
      ckb_players = new JCheckBox[10];
      lbl_rates = new JLabel[10][3];
      for (int i=0; i<10; i++) {
        int x0 = 60;

        for (int j=0; j<3; j++) {
          lbl_rates[i][j] = new JLabel("");
          lbl_rates[i][j].setBounds(200+j*70,25+i*40,215,25);
          panelPlayers.add(lbl_rates[i][j]);
          lbl_rates[i][j].setVisible(false);
        }
        lbl_rates[i][0].setText("Wins:");
        lbl_rates[i][1].setText("Draw:");
        lbl_rates[i][2].setText("Loses:");

        for (int j=0; j<2; j++) {
          cards_players[i][j] = null;
          btn_players[i][j] = new JButton("--");
          btn_players[i][j].addActionListener(this);
          btn_players[i][j].setMargin(new Insets(0, 0, 0, 0));
          btn_players[i][j].setBackground(color_free);
          btn_players[i][j].setBounds(x0 + j*30,15+i*40,28,39);
          panelPlayers.add(btn_players[i][j]);
        }
        btn_ranges[i] = new JButton("Range");
        btn_ranges[i].addActionListener(this);
        btn_ranges[i].setMargin(new Insets(0, 0, 0, 0));
        btn_ranges[i].setBackground(color_free);
        btn_ranges[i].setBounds(x0 + 70,25+i*40,60,25);
        panelPlayers.add(btn_ranges[i]);

        ckb_players[i] = new JCheckBox("P" + String.format("%2d",i+1)+":");
        ckb_players[i].setBounds(5,25+i*40,55,25);
        ckb_players[i].addActionListener(this);
        setEnabledPlayer(i,false) ;
        panelPlayers.add(ckb_players[i]);
      }
      setEnabledPlayer(0,true) ;
      setEnabledPlayer(1,true) ;
      ckb_players[0].setSelected(true);
      ckb_players[1].setSelected(true);
    }
    this.add(panelPlayers);
    
    panelResults = new JPanel();
    {
      panelResults.setLayout(null);
      panelResults.setBounds(210,85,225,430);
      TitledBorder border = new TitledBorder("Results");
      border.setTitleJustification(TitledBorder.CENTER);
      border.setTitlePosition(TitledBorder.TOP);
      panelResults.setBorder(border);
      for (int i=0; i<10; i++) {
        int x0 = 60;

        for (int j=0; j<3; j++) {
          lbl_rates[i][j] = new JLabel("");
          lbl_rates[i][j].setBounds(10+j*65,25+i*40,60,25);
          panelResults.add(lbl_rates[i][j]);
          lbl_rates[i][j].setVisible(false);
        }
        lbl_rates[i][0].setText("W:");
        lbl_rates[i][1].setText("D:");
        lbl_rates[i][2].setText("L:");
      }
    }
    this.add(panelResults);


    panelCards = new JPanel();
    {
      panelCards.setLayout(null);
      panelCards.setBounds(435,85,150,430);
      TitledBorder border = new TitledBorder("Cards");
      border.setTitleJustification(TitledBorder.CENTER);
      border.setTitlePosition(TitledBorder.TOP);
      panelCards.setBorder(border);

      btn_cards = new JButton[4][13];
      sel_cards = new boolean[4][13];
      Deck deck = new Deck();
      for (int i=0; i<4; i++) {
        for (int j=0; j<13; j++) {
          cards_all[i][j] = deck.getCard(13-j,i+1);
          btn_cards[i][j] = new JButton(cards_all[i][j].getName());
          btn_cards[i][j].addActionListener(this);
          btn_cards[i][j].setForeground(color_suit[i]);
          btn_cards[i][j].setMargin(new Insets(0, 0, 0, 0));
          btn_cards[i][j].setBackground(color_free);
          btn_cards[i][j].setBounds(17+i*30,15+j*29,28,28);
          sel_cards[i][j] = false;
          panelCards.add(btn_cards[i][j]);
        }
      }
      btn_anycard   = new JButton("Any card");
      btn_anycard.setBackground(color_free);
      btn_anycard.setMargin(new Insets(0, 0, 0, 0));
      btn_anycard.addActionListener(this);
      btn_anycard.setBounds(25,395,100,20);
      panelCards.add(btn_anycard); 
    
      setEnabledPanel(panelCards,false);
    }
    this.add(panelCards);
    //
    ////////////////////////////////////////////////////////////////////
  }
  ////////////////////////////////////////////////////////////////////////

  void setEnabledPanel(JPanel panel, boolean state) {
    panel.setEnabled(state);
    Component[] components = panel.getComponents();
    for(int i = 0; i < components.length; i++) {
       components[i].setEnabled(state);
    }
  }

  void setEnabledPlayer(int i, boolean state) {
    btn_players[i][0].setEnabled(state);
    btn_players[i][1].setEnabled(state);
    btn_ranges[i].setEnabled(state);
    if (state) ckb_players[i].setForeground(Color.BLUE);
    else {
      ckb_players[i].setForeground(color_offB);
      for (int j=0; j<2; j++) {
        if (btn_players[i][j].getText() != "--") {
          btn_players[i][j].setText("--");
          int n = 13 - cards_players[i][j].n;  
          int s = cards_players[i][j].s - 1;
          btn_cards[s][n].setBackground(color_free);
          sel_cards[s][n] = false;
        }
      }
    }
  
  }

  void checkPlayers() {
    for (int i=0; i<10; i++) {
      if (ckb_players[i].isSelected()) setEnabledPlayer(i,true);
      else setEnabledPlayer(i,false);
    }
  }


  ////////////////////////////////////////////////////////////////////////
  @Override
  public void actionPerformed(ActionEvent e) {

    // Card selection
    for (int i=0; i<4; i++) {
        for (int j=0; j<13; j++) {
          if (e.getSource()==btn_cards[i][j]) {
            if (!sel_cards[i][j] ) {
              btn_cards[i][j].setBackground(color_cardSel);
              sel_cards[i][j] = true;
              if (selectingBoard) {
                selectingBoard = false;
                cards_board[BoardSel] = cards_all[i][j]; 
                btn_board[BoardSel].setText(cards_all[i][j].getName());
                btn_board[BoardSel].setBackground(color_free);
                BoardSel = -1;
              } else if (selectingPlayer) {
                selectingPlayer = false;
                cards_players[PlayerSel[0]][PlayerSel[1]]= cards_all[i][j];
                btn_players[PlayerSel[0]][PlayerSel[1]].setText(cards_all[i][j].getName());
                btn_players[PlayerSel[0]][PlayerSel[1]].setBackground(color_free);
                PlayerSel[0] = PlayerSel[1] = -1; 
              }
              setEnabledPanel(panelCards,false);
              setEnabledPanel(panelBoard, true);
              setEnabledPanel(panelButtons, true); 
              setEnabledPanel(panelPlayers, true); checkPlayers();
            }
          } 
        }
    }
    if (e.getSource()==btn_anycard) {
      if (selectingBoard) {
        selectingBoard = false;
        cards_board[BoardSel] = null;
        btn_board[BoardSel].setText("--");
        btn_board[BoardSel].setBackground(color_free);
        BoardSel = -1;
      } else if (selectingPlayer) {
        selectingPlayer = false;
        cards_players[PlayerSel[0]][PlayerSel[1]]=null;
        btn_players[PlayerSel[0]][PlayerSel[1]].setText("--");
        btn_players[PlayerSel[0]][PlayerSel[1]].setBackground(color_free);
        PlayerSel[0] = PlayerSel[1] = -1; 
      }
      setEnabledPanel(panelCards,false);
      setEnabledPanel(panelButtons, true); 
      setEnabledPanel(panelBoard, true);
      setEnabledPanel(panelPlayers, true); checkPlayers();
    }

    // Board selections
    for (int i=0; i<5; i++) {
      if (e.getSource()==btn_board[i]) {
        selectingBoard = true;
        btn_board[i].setBackground(color_on);
        setEnabledPanel(panelBoard, false);
        setEnabledPanel(panelButtons, false); 
        setEnabledPanel(panelPlayers, false); 

        BoardSel = i;
        setEnabledPanel(panelCards,true);
        if (btn_board[i].getText() != "--") {
          btn_board[i].setText("--");
          int n = 13 - cards_board[i].n;
          int s = cards_board[i].s - 1;
          btn_cards[s][n].setBackground(color_free);
          sel_cards[s][n] = false;
        }

      }
    }

    // Players
    for (int i=0; i<10; i++) {
      if (e.getSource() == ckb_players[i]) {
        if (ckb_players[i].isSelected()) setEnabledPlayer(i,true);
        else setEnabledPlayer(i,false);
      }    

      for (int j=0; j<2; j++) {
        if (e.getSource()==btn_players[i][j]) {
          selectingPlayer = true;
          btn_players[i][j].setBackground(color_on);
          PlayerSel[0] = i;
          PlayerSel[1] = j;
          setEnabledPanel(panelCards,true);
          setEnabledPanel(panelPlayers, false); //checkPlayers();
          setEnabledPanel(panelBoard, false);
          setEnabledPanel(panelButtons, false); 
          if (btn_players[i][j].getText() != "--") {
            btn_players[i][j].setText("--");
            int n = 13 - cards_players[i][j].n;  
            int s = cards_players[i][j].s - 1;
            btn_cards[s][n].setBackground(color_free);
            sel_cards[s][n] = false;
          }
        } 
      }
    }

    // Reset
    if (e.getSource()==btn_reset) {
      // Board
      for (int i=0; i<5; i++) {
        btn_board[i].setText("--");
        btn_board[i].setBackground(color_free);
        cards_board[i] = null;
      }
      // Cards:
      for (int i=0; i<4; i++) 
        for (int j=0; j<13; j++) {
          btn_cards[i][j].setBackground(color_free);
          sel_cards[i][j] = false; 
        }
      // Players
      for (int i=0; i<10; i++) {
	lbl_rates[i][0].setVisible(false);
	lbl_rates[i][1].setVisible(false);
	lbl_rates[i][2].setVisible(false);
        for (int j=0; j<2; j++) {
          btn_players[i][j].setText("--");
          btn_players[i][j].setBackground(color_free);
          cards_players[i][j] = null;
        }
      }
      setEnabledPanel(panelPlayers, true); checkPlayers();
      setEnabledPanel(panelBoard, true);
      setEnabledPanel(panelCards, false);
    }
     
    // Calculation
    if (e.getSource()==btn_start) {
      setEnabledPanel(panelCards,false);
      setEnabledPanel(panelPlayers, false); //checkPlayers();
      setEnabledPanel(panelBoard, false);

      int np = 0;
      int[] pla = new int[10];
      for (int i=0; i<10; i++) {
        if (ckb_players[i].isSelected()) {
          pla[np] = i; np++;
        }
      }

      String txt = txt_games.getText();
      int ng = Integer.parseInt(txt);
      kernel = new Kernel(cards_board, cards_players, pla, np, ng);
      int ind = 0;
      for (int i=0; i<10; i++) {
        if (ckb_players[i].isSelected()) {
          lbl_rates[i][0].setText("W: "+String.format("%.1f",100*kernel.getWins(ind))); lbl_rates[i][0].setVisible(true);
          lbl_rates[i][1].setText("D: "+String.format("%.1f",100*kernel.getDraw(ind))); lbl_rates[i][1].setVisible(true);
          lbl_rates[i][2].setText("L: "+String.format("%.1f",100*kernel.getLoses(ind))); lbl_rates[i][2].setVisible(true);
	  ind++;
        }
      }
    }

  }
 
  

}





