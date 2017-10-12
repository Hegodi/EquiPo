import java.util.Comparator;
import java.util.Arrays;

public class HandValue {
  int    type;  /* 0 -> High cards
                   1 -> one pair
                   2 -> two pairs 
                   3 -> three of a kind
                   4 -> Straight
                   5 -> Flush
                   6 -> Full house
                   7 -> Poker
                   8 -> Straight flush
                */
  int val;   // Only in case of two hands of the same type 
  int[] bestHand = new int[5]; // Index of the cards of the best hands
  int[] index    = new int[5];
  int numflush;       // Number of pairs
  int[] indFlush = new int[5]; // Index of the cards of the best hands
  int numstraight;       // Number of pairs
  int[] indStraight = new int[5]; // Index of the cards of the best hands
  int numstraflush;       // Number of pairs
  int[] indStraFlush = new int[5]; // Index of the cards of the best hands

  int numpairs;       // Number of pairs
  int[][] pairs = new int[3][2];    // Index of the pairs
  int numlegs;        // Number of three of a king
  int[][] legs  = new int[2][3];     // Index of the pairs
  int numpoker;       // Number of pokers
  int[] poker   = new int[4];       // Index of poker
 
  // For sorting the hands
  static private Comparator<Card> compByVal;
  static {
    compByVal = new Comparator<Card>(){
            @Override
            public int compare(Card c1, Card c2){
                // Java 7 has an Integer#compare function
                float v1 = (float)(1./c1.n);
                float v2 = (float)(1./c2.n);
                return Float.compare(v1,v2);
                //return Integer.compare(c1.n, c2.n);
                // For Java < 7, use 
            }
    };
  }

  public HandValue() { 
    numpairs = 0;
    numlegs  = 0;
    numpoker = 0;
    numflush = 0;
    numstraight  = 0;
    numstraflush = 0;
    type = -1; val = -1; 
  }

  // hand must have 7 elements
  public HandValue(Card[] hand) {
    evaluate(hand);
  }

  // Evaluate the best possible hand
  public void evaluate(Card[] hand) {

    //System.out.printf("   -----   \n");
    Arrays.sort(hand, compByVal); 

    //for (int i=0; i<7; i++) {
      //hand[i].print();
      //System.out.printf(" ");
    //}
    //System.out.printf("\n");

    int num;

    /////////////////////////////////////////////////////////////////
    //
    // Check for straigth flush
    //
    indStraFlush[0] = 0;
    numstraflush = 0;
    num = 1;
    for (int i=1; i<7; i++) {
      if (hand[i-1].n == hand[i].n) continue;
      if (hand[i-1].n == hand[i].n+1 && hand[i].s == hand[indStraFlush[0]].s) {
        indStraFlush[num] = i;
        num++;
        if (num == 5) {
          numstraflush = 1;
          break;
        }
      } else {
       indStraFlush[0] = i;
       num = 1;
      }
    }
    if (numstraflush==0) { // Check if there is straight that ends in .. 2 A
      if (hand[0].n == 13) {
        num = 4;
        indStraFlush[num] = 0;
        int pn = 0;
        int ps = hand[0].s;
        for (int i=6; i>=1; i--) {
          if (pn == hand[i].n) continue;
          if (pn == hand[i].n-1 && ps == hand[i].s) {
            num--;
            indStraFlush[num] = i;
            if (num == 0) {
              numstraflush = 1;
              break;
            }
          } else break;

          pn = hand[i].n;
          ps = hand[i].s;
        } 
      }
    }

    /////////////////////////////////////////////////////////////////
    //
    // Check for pairs, 3 of a kind and poker
    //
    for (int i=0; i<7; i++) {
      Card c0 = hand[i];
      index[0] = i;
      num = 1;
      boolean done = false;
      for (int k=0; k<i; k++) {
        if (hand[i].n == hand[k].n) {
          done=true;
          break;
        }
      }
      if (done) continue;
      for (int j=i+1; j<7; j++) {
        if (hand[j].n == hand[i].n) {
          index[num] = j;
          num++;
        }
      }
      if (num == 2) {
        pairs[numpairs][0] = index[0];
        pairs[numpairs][1] = index[1];
        numpairs++;
      } else if (num == 3) {
        legs[numlegs][0] = index[0];
        legs[numlegs][1] = index[1];
        legs[numlegs][2] = index[2];
        numlegs++;

      } else if (num == 4) {
        poker[0] = index[0];
        poker[1] = index[1];
        poker[2] = index[2];
        poker[3] = index[3];
        numpoker++;
      }
    }


    /////////////////////////////////////////////////////////////////
    //
    // Check for flush:
    //
    numflush = 0;
    for (int i=0; i<7; i++) {
      Card c0 = hand[i];
      indFlush[0] = i;
      num = 1;
      for (int j=i+1; j<7; j++) {
        Card c1 = hand[j]; 
        if (c0.s == c1.s) {
          indFlush[num] = j;
          num++;
          if (num == 5) {
           numflush = 1;
           break;
          }
        }
      }
      if (numflush == 1) break;
    }

    /////////////////////////////////////////////////////////////////
    //
    // Check for straigth
    //
    indStraight[0] = 0;
    numstraight = 0;
    num = 1;
    for (int i=1; i<7; i++) {
      if (hand[i-1].n == hand[i].n) continue;
      if (hand[i-1].n == hand[i].n+1) {
        indStraight[num] = i;
        num++;
        if (num == 5) {
          numstraight = 1;
          break;
        }
      } else {
       indStraight[0] = i;
       num = 1;
      }
    }
    if (numstraight==0) { // Check if there is straight that ends in .. 2 A
      if (hand[0].n == 13) {
        num = 4;
        indStraight[num] = 0;
        int pn = 0;
        for (int i=6; i>=1; i--) {
          if (pn == hand[i].n) continue;
          if (pn == hand[i].n-1) {
            num--;
            indStraight[num] = i;
            if (num == 0) {
              numstraight = 1;
              break;
            }
          } else break;

          pn = hand[i].n;
        } 
      }
    }
    
   
    ///////////////////////////////////////////////////////////////
   if (1 == numstraflush) {
     type = 8;
     for (int i=0; i<5; i++) bestHand[i] = indStraFlush[i];
     val = hand[indStraFlush[0]].n;
     //System.out.printf("Best hand: Straight Flush (%d)\n",val);

   } else if (numpoker > 0) {
     type = 7;
     for (int i=0; i<4; i++) bestHand[i] = poker[i];
     for (int i=0; i<7; i++) {
       if (hand[i].n != hand[poker[0]].n) {
         bestHand[4] = i;
         break;
       }
     }
     val = 15*hand[bestHand[0]].n + hand[bestHand[4]].n;
     //System.out.printf("Best hand: Poker (%d)\n",val);
     
   } else if (numpairs >0 && numlegs > 0)  {
     type = 6;
     bestHand[0] = legs[0][0];
     bestHand[1] = legs[0][1];
     bestHand[2] = legs[0][2];
     bestHand[3] = pairs[0][0]; 
     bestHand[4] = pairs[0][1]; 
     val = 15*hand[bestHand[0]].n + hand[bestHand[3]].n;
     //System.out.printf("Best hand: Full House (%d)\n",val);

   } else if (numflush > 0) {
     type = 5;
     for (int i=0; i<5; i++) bestHand[i] = indFlush[i];
     val = hand[indFlush[0]].n;
     //System.out.printf("Best hand: Flush (%d)\n",val);

   } else if (numstraight > 0) {
     type = 4;
     for (int i=0; i<5; i++) bestHand[i] = indStraight[i];
     val = hand[indStraight[0]].n;
     //System.out.printf("Best hand: Straight (%d)\n",val);

   } else if (numlegs > 0) {
     type = 3;
     bestHand[0] = legs[0][0];
     bestHand[1] = legs[0][1];
     bestHand[2] = legs[0][2];
     int nl = 3;
     for (int i=0; i<7; i++) {
       if (hand[i].n != hand[legs[0][0]].n) {
         bestHand[nl] = i;
         nl++;
         if (nl == 5) break;
       }
     }
     val = 15*15*hand[bestHand[0]].n + 15*hand[bestHand[3]].n + 
                                          hand[bestHand[4]].n;
     //System.out.printf("Best hand: Three of a kind (%d)\n",val);


   } else if (numpairs > 1) {
     type = 2;
     bestHand[0] = pairs[0][0];
     bestHand[1] = pairs[0][1];
     bestHand[2] = pairs[1][0];
     bestHand[3] = pairs[1][1];
     for (int i=0; i<7; i++)  
       if (hand[i].n != hand[pairs[0][0]].n && hand[i].n != hand[pairs[1][0]].n) {
         bestHand[4] = i; 
         break;
       }

     val = 15*15*hand[bestHand[0]].n + 15*hand[bestHand[2]].n + 
                                          hand[bestHand[4]].n;
     //System.out.printf("Best hand: Two pairs (%d)\n",val);

   } else if (numpairs == 1) {
     type = 1;
     bestHand[0] = pairs[0][0];
     //bestHand[1] = pairs[0][1];
     int nl = 2;
     for (int i=0; i<7; i++) {
       if (hand[i].n != hand[pairs[0][0]].n) {
         bestHand[nl] = i;
         nl++;
         if (nl == 5) break;
       }
     }
     val = 15*15*hand[bestHand[0]].n + 15*hand[bestHand[3]].n + 
                                          hand[bestHand[4]].n;
     //System.out.printf("Best hand: One pair (%d)\n",val);


   } else {
     type = 0;

     for (int i=0; i<5; i++) bestHand[i] = i;
     val = 15*15*15*15*hand[0].n + 15*15*15*hand[1].n + 15*15*hand[2].n + 
           15*hand[3].n + hand[4].n;
     //System.out.printf("Best hand: High card (%d)\n",val);
   }

   //System.out.printf("  ");
   //for (int i=0; i<5; i++) {
     //hand[bestHand[i]].print();
     //System.out.printf(" ");
    //}
   //System.out.printf(" \n\n");

  }


}
