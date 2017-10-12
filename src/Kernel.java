////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
import java.awt.*;
import javax.swing.*;

public class Kernel {
  static Deck        deck;
  static Card[]      board;
  static Card[][]    players;
  static Card[]      hand;
  static HandValue[] value;

  static int NP;
  static int NG;
  static int ncb;;
  static int[] ncp;

  static int WIN[];
  static int LOS[];
  static int DRA[];

  ////////////////////////////////////////////////
  ///  Constructor                                
  Kernel(Card[] tab_card, Card[][] pla_card, int[] pla, int np, int ng ) {
    // Initialization
    deck    = new Deck();          // All cards
    board   = new Card[5];       // Common cards
    players = new Card[10][2];   // Players cards
    hand    = new Card[7];        // Commons cards + player cards
    value   = new HandValue[10];

    NP = np;   // Number of players
    NG = ng;
    ncp = new int[10];

    WIN = new int[np];
    LOS = new int[np];
    DRA = new int[np];
    for (int i=0; i<np; i++){WIN[i] = LOS[i] = DRA[i];}
      
    // Loop over games
    for (int g=0; g<ng; g++){
      // Reset desk and cards
      for (int i=0; i<5; i++)  board[i] = null;
      for (int i=0; i<10; i++)  players[i][0] = players[i][1] = null;
      deck.reset();

      for (int i=0; i<10; i++) ncp[i] = 0;
      ncb = 0;

      // Set all the cards that are given
      for (int i=0; i<5; i++)  
        if(tab_card[i] != null) {
          board[i] = deck.getCard(tab_card[i].n, tab_card[i].s);
	  ncb++;
        }

      for (int i=0; i<np; i++) {
        if (pla_card[pla[i]][0] != null) {
          players[i][0] = deck.getCard(pla_card[pla[i]][0].n, 
			              pla_card[pla[i]][0].s);
	  ncp[i]++;
        }
        if (pla_card[pla[i]][1] != null) {
          players[i][1] = deck.getCard(pla_card[pla[i]][1].n, 
			              pla_card[pla[i]][1].s); 
	  ncp[i]++;
        }
      }
      //showTable();

      // Set the rest of the cards:
      for (int i=0; i<5; i++)  
        if(board[i] == null) { board[i] = deck.getCard(-1,-1); ncb++; }

      for (int i=0; i<np; i++) {
        if (players[i][0] == null) { 
          players[i][0] = deck.getCard(-1,-1); ncp[i]++;
	}
        if (players[i][1] == null) { 
          players[i][1] = deck.getCard(-1,-1); ncp[i]++;
	}
      }
      //showTable();
      for (int i=0; i<np; i++) evaluateHand(i);
      winner(); 
    }
  }

  // Show the cards in the board
  public static void showTable() {
    System.out.printf("--------------------\n");
    for (int i=0; i<ncb; i++) {
      board[i].print();
      System.out.printf(" ");
    } 
    for (int i=ncb; i<5; i++) {
      System.out.printf("-- ");
    }
    System.out.printf("\n");
    System.out.printf("--------------------\n");
    for (int i=0; i<NP; i++) {
      System.out.printf("Player %d: ",i);
      for (int j=0; j<ncp[i]; j++) {
        players[i][j].print();
        System.out.printf(" ");
      }
      System.out.printf("\n");
    }
  }

  // Evaluate the best hand of the player p
  public static void evaluateHand(int p) {
    if (ncb == 5 && ncp[p] == 2) {
      hand[0] = board[0];
      hand[1] = board[1];
      hand[2] = board[2];
      hand[3] = board[3];
      hand[4] = board[4];
      hand[5] = players[p][0];
      hand[6] = players[p][1];
      value[p] = new HandValue(hand);
    }
  }

  public static void winner() {
    int[] win = new int[10];
    for (int i=0; i<10; i++) win[0] = -1;
    int nt = 1;
    win[0] = 0;
    for (int i=1; i<NP; i++) {
      if (value[i].type > value[win[0]].type) {
        nt     = 1;
        win[0] = i; 
      } else if (value[i].type == value[win[0]].type) {
        if (value[i].val > value[win[0]].val) {
          nt  = 1;
          win[0] = i;
        } else if (value[i].val == value[win[0]].val) {
          win[nt] = i;
          nt++; 
        }
      } 
    }

    if (nt > 1) {
      for (int i=0; i<nt; i++) DRA[i]++;
    } else {
      for (int i=0; i<NP; i++) {
        if (win[0] == i) WIN[i]++;
	else LOS[i]++;
      }
	     
    }
  }

  public static float getWins(int i) {
     return ((float)WIN[i])/NG;
  }
  public static float getLoses(int i) {
     return ((float)LOS[i])/NG;
  }
  public static float getDraw(int i) {
     return ((float)DRA[i])/NG;
  }

}

