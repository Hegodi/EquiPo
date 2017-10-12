import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.TitledBorder;



//import javax.swing.JFrame;

//import javax.swing.JButton;

class Window extends JFrame {
//class Window extends JFrame implements ActionListener {

  // Ranges:
  JTabbedPane mainPanel;


  Window() {

    this.setSize(600,580);
    //this.setLayout(null);
   
    String[] card_names = {"A","K","Q","J","T","9","8","7","6","5","4","3","2"};
    String[] suit_names = {"h","c","s","d"};

    mainPanel   = new JTabbedPane();
    RangePanel rangePanel = new RangePanel();
    EquityPanel equityPanel = new EquityPanel(rangePanel);

    mainPanel.addTab("Equity",equityPanel);
    mainPanel.addTab("Ranges",rangePanel);
    this.add(mainPanel);
    this.setVisible(true);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setResizable(false);

  }
}

