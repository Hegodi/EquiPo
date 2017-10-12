////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////                                                                        ////
////  Ranking o initial hands                                               ////
////                                                                        ////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
import java.util.*;

class ChartHand {
  int i,j;
  ChartHand(int _i, int _j){
    i = _i;
    j = _j;
  }

}

class HandRat implements Comparable<HandRat> {
  String name;
  Card cards[];
  int n;         // Number of hands
  double val;    // Value
  double prob;   
  int i,j;       // Indeces in the RangePanel

  HandRat(String _name, String s1, String s2, int _n, double _val) {
    cards = new Card[2];
    cards[0] = new Card(s1);
    cards[1] = new Card(s2);
    name = _name;
    n    = _n;
    val  = _val; 
  }

  void print() {
    System.out.printf("%4.1f - ",val);
    cards[0].print();
    System.out.printf(" ");
    cards[1].print();
    System.out.printf(" (%d) : %.6f\n",n, prob);
  }
  @Override
  public int compareTo(HandRat o) {
      if (this.val > o.val) return -1;
      else return 1;
  }
}

public class HandsRanking {
  final int NH;
  HandRat hands[];
  HandRat handsSelected[];  // Hands selected 
  int nhsel;                // Number of hands selected
  Random generator;
  String numbers[]  = {"2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A"};
  int norm;

  HandsRanking() { 
    norm = 1;
    long seed = System.currentTimeMillis();
    generator = new Random(seed);
    NH = 169;
    hands = new HandRat[NH];
    handsSelected = new HandRat[NH];
    nhsel = 0;
  }

  void init() {
    int nh = 0;
    int nth = 0;
    for (int i=12; i>=0; i--) {
      for (int j=i; j>=0; j--) {
         if (i != j) {
           // Suited 
           String name = numbers[i]+numbers[j]+"s";
           String ca1 = numbers[i]+"h";
           String ca2 = numbers[j]+"h";
           int n = 1;
           double val = value(i,j,true);
           hands[nh] = new HandRat(name, ca1, ca2, 8, val);
	   hands[nh].i =12-j;
	   hands[nh].j =12-i;
           nth += 8;
           nh++;
           // Off suited 
           name = numbers[i]+numbers[j]+"o";
           ca1 = numbers[i]+"h";
           ca2 = numbers[j]+"s";
           n = 1;
           val = value(i,j,false);
           hands[nh] = new HandRat(name, ca1, ca2, 24, val);
	   hands[nh].i =12-i;
	   hands[nh].j =12-j;
           nh++;
           nth += 24;
        } else {
           String name = numbers[i]+numbers[j];
           String ca1 = numbers[i]+"h";
           String ca2 = numbers[j]+"s";
           int n = 1;
           double val = value(i,j,false);
           hands[nh] = new HandRat(name, ca1, ca2, 12, val);
	   hands[nh].i =12-j;
	   hands[nh].j =12-i;
           nh++;
           nth += 12;
        }
      }
    }
    if (nh != NH) {
      System.out.printf("ERROR: wrong number of hands in ranking\n");
    }

    Arrays.sort(hands);

    double pp = 0;
    for (int i=0; i<NH; i++) {
      double p = hands[i].n/(1.0*nth);
      pp += p;
      hands[i].prob = pp;
    } 


  }

  double value(int i, int j, boolean suit) {
    double val = 0;
    int n1 = i+2;
    int n2 = j+2;
    // Chen formula
    if (norm == 1) {
      double v1 = 0;
      double v2 = 0;

      if (n1<11) v1 = n1/2.0;
      else if (n1 == 11) v1 =  6;
      else if (n1 == 12) v1 =  7;
      else if (n1 == 13) v1 =  8;
      else if (n1 == 14) v1 = 10;

      if (n2<11) v2 = n2/2.0;
      else if (n2 == 11) v2 =  6;
      else if (n2 == 12) v2 =  7;
      else if (n2 == 13) v2 =  8;
      else if (n2 == 14) v2 = 10;

      if (i == j) {

        val = v1*2;
        if (val < 5) val = 5;
      } else {

        if (v1 > v2) val = v1;
        else val = v2;

        if (suit) val += 2;

        int dd = Math.abs(i-j);
        if (dd >= 5) val -= 5;
        else val -= dd;
      }
    }
    return val;
  }

  void selectHand(String hnd) {
     for (int i=0; i<NH; i++) {
       if (hands[i].name.equals(hnd)) {
          handsSelected[nhsel] = hands[i];
	  nhsel++;
	  break;
       }
     }
  }

  void selectHand(int i, int j) {
     String hnd;
     if      (i > j) hnd = numbers[12-j] + numbers[12-i] + "s";
     else if (i < j) hnd = numbers[12-i] + numbers[12-j] + "o";
     else  hnd = numbers[12-j] + numbers[12-i];
     selectHand(hnd);
  }

  void show() {
    for (int i=0; i<NH; i++) {
      hands[i].print();
    }
  }

  int getNHprob(double val) {
    double prob = val/100.0;
    for (int i=0; i<NH; i++) {
      if (hands[i].prob >= prob) return i;
    }
    return NH;
  }

  ChartHand getHandProb(int ind) {
    int i = hands[ind].i;
    int j = hands[ind].j;
    ChartHand ret = new ChartHand(i,j);
    return ret;
  }

  void showSel() {
    for (int i=0; i<nhsel; i++) {
      handsSelected[i].print();
    }
  }

}
