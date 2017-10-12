////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////                                                                        ////
////  Card class                                                            ////
////                                                                        ////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

public class Card {
  int n;      // Number
  int s;      // Suits
  boolean a;  // Available
  Card() { n = -1; s = -1; a = false; }

  Card(int _n, int _s) {
    n = _n;
    s = _s; 
    a = false;
  }

  Card(String txt) {
    char c1 = txt.charAt(0);
    char c2 = txt.charAt(1);
    if      (c1 == 'A') n = 13; 
    else if (c1 == 'K') n = 12; 
    else if (c1 == 'Q') n = 11; 
    else if (c1 == 'J') n = 10; 
    else if (c1 == 'T') n = 9; 
    else if (c1 == '9') n = 8; 
    else if (c1 == '8') n = 7; 
    else if (c1 == '7') n = 6; 
    else if (c1 == '6') n = 5; 
    else if (c1 == '5') n = 4; 
    else if (c1 == '4') n = 3; 
    else if (c1 == '3') n = 2; 
    else if (c1 == '2') n = 1; 
    else {
      System.out.printf("ERROR: unkown card (%s)\n",txt);
    }

    if      (c2 == 'h') s = 1; 
    else if (c2 == 's') s = 2; 
    else if (c2 == 'd') s = 3; 
    else if (c2 == 'c') s = 4; 
    else {
      System.out.printf("ERROR: unkown card (%s)\n",txt);
    }
  }

  public void clear() { n = -1; s = -1; a = false; }

  public void print() {
    if (n < 0)   System.out.printf("--");
    else if (n < 9)   System.out.printf("%d",n+1);    
    else if (n == 9)  System.out.printf("T");    
    else if (n == 10) System.out.printf("J");    
    else if (n == 11) System.out.printf("Q");    
    else if (n == 12) System.out.printf("K");    
    else if (n == 13) System.out.printf("A");    

    if (s == 1) System.out.printf("h");    
    else if (s == 2) System.out.printf("s");    
    else if (s == 3) System.out.printf("d");    
    else if (s == 4) System.out.printf("c");    
  }
  
  public String getName() {
    String ret = "##";
    if (n < 0)   ret = "--";
    else if (n < 9)   ret = String.format("%d",n+1);    
    else if (n == 9)  ret = "T";    
    else if (n == 10) ret = "J";    
    else if (n == 11) ret = "Q";    
    else if (n == 12) ret = "K";    
    else if (n == 13) ret = "A";    

    if (s == 1)      ret +="h";    
    else if (s == 2) ret +="s";    
    else if (s == 3) ret +="d";    
    else if (s == 4) ret +="c";    

   return ret;
    
  }
}
