////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////                                                                        ////
////  Deck class                                                            ////
////                                                                        ////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
import java.util.*;

public class Deck {
  final int TotCard;
  int n;  // Number of cards not used
  Card[] cards;
  Card nullCard;
  Random generator;

  Deck() { 
    nullCard = new Card(-1,-1);
    long seed = System.currentTimeMillis();
    generator = new Random(seed);
    TotCard = 52; 
    cards = new Card[TotCard];
    int ind = 0;
    for (int j=1; j<5; j++) {
      for (int i=1; i<14; i++) {
        cards[ind] = new Card(i,j);
        ind++;
      }
    }
    n = TotCard;
  }

  void reset() {
    for (int i=0; i<TotCard; i++) {
      cards[i].a = false;
    }
    n = TotCard;
  }

  Card getCard() {
    if (n == 0) {
      System.out.format("ERROR: there is no more available cards in the Deck\n");
    }
    while (true) {
      //int i = (int)(Math.random()*TotCard);
      int i = (int)(generator.nextDouble()*TotCard);
      if (!cards[i].a) {
        n--;
        cards[i].a = true;
        return cards[i];
      }
    }
  }

  Card getCard(int _n, int _s) {
    if (n == 0) {
      System.out.format("ERROR: there is no more available cards in the Deck\n");
    }
    for (int i=0; i<10000; i++) {
      //int i = (int)(Math.random()*TotCard);
      int ind = (int)(generator.nextDouble()*TotCard);
      if (!cards[ind].a) {
        boolean valid = true;
        if (_n != -1) {
          if (_n != cards[ind].n) valid = false;
        }
        if (_s != -1) {
          if (_s != cards[ind].s) valid = false;
        }

        if (valid) {
          n--;
          cards[ind].a = true;
          return cards[ind];
        }
      }
    }
    return nullCard;
  }
}
