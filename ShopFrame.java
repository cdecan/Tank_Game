/** 
 * this template can be used for a start menu
 * for your final project
 **/


//Imports
import javax.swing.*;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;

class ShopFrame extends JFrame { 
  static boolean redOwned = true, greenOwned, blueOwned, yellowOwned;
  
  JFrame thisFrame;
  
  //Constructor - this runs first
  ShopFrame() { 
    super("Shop");
    this.thisFrame = this; //lol  
    
    //configure the window
    this.setSize(400,700);    
    this.setLocationRelativeTo(null); //start the frame in the center of the screen
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    this.setResizable (false);
    
    //Create a Panel for stuff
    JPanel mainPanel = new JPanel();
    mainPanel.setBackground(Color.GREEN);
    
    mainPanel.setLayout(new GridLayout(3,1));
    
    //Create a JButton for the centerPanel
    JButton returnButton = new JButton("RETURN");
    returnButton.addActionListener(new ButtonListener());
    returnButton.setBackground(Color.WHITE);
    
    //Create a tutorial label
    //JLabel helpLabel = new JLabel("THIS IS THE SHOP");
    
    //create buy buttons and equip buttons
    JButton[] buyButton = {new JButton("BUY"),new JButton("BUY"),new JButton("BUY"),new JButton("BUY")};
    JButton[] equipButton = {new JButton("EQUIP"),new JButton("EQUIP"),new JButton("EQUIP"),new JButton("EQUIP")};
    
    
    String[] color = new String[4];
    color[0] = "RED";
    color[1] = "GREEN";
    color[2] = "BLUE";
    color[3] = "YELLOW";
    
    RedTank red = new RedTank();
    GreenTank green = new GreenTank();
    BlueTank blue = new BlueTank();
    YellowTank yellow = new YellowTank();
    
  
    //Create Images
    
    
    //mainPanel.add(helpLabel);
   // mainPanel.add(returnButton);
    for(int i = 0;i<4;i++){
      mainPanel.add(buyButton[i]);
    }
    mainPanel.add(red);
    mainPanel.add(blue);
    mainPanel.add(green);
    mainPanel.add(yellow);
    for(int o = 0;o<4;o++){
      mainPanel.add(equipButton[o]);
    }    
    
    
    //add the main panel to the frame
    this.add(mainPanel);
    
    //Start the app
    this.setVisible(true);
  }
  
  //This is an inner class that is used to detect a button press
  class ButtonListener implements ActionListener {  //this is the required class definition
    public void actionPerformed(ActionEvent event)  {  
      System.out.println("returning to thing");
        thisFrame.dispose();
        new StartingFrame(); //create a new FunkyFrame (another file that extends JFrame)
      
    }
    
  }
  class RedTank extends JComponent{
      RedTank(){
      }
      public void paintComponent(Graphics g){
      super.paintComponent(g);
      setDoubleBuffered(true);
      g.setColor(Color.BLACK);
      g.fillOval(10,10,50,100);
      g.setColor(Color.RED);
      g.fillOval(15,15,40,90);
      
      }
    }
    class GreenTank extends JComponent{
      public void paintComponent(Graphics g){
      super.paintComponent(g);
      setDoubleBuffered(true);
      g.setColor(Color.BLACK);
      g.fillOval(10,10,50,100);
      g.setColor(Color.GREEN);
      g.fillOval(15,15,40,90);      
      }
    }
    class BlueTank extends JComponent{
      public void paintComponent(Graphics g){
      super.paintComponent(g);
      setDoubleBuffered(true);
      g.setColor(Color.BLACK);
      g.fillOval(10,10,50,100);
      g.setColor(Color.BLUE);
      g.fillOval(15,15,40,90);      
    }
    }
    class YellowTank extends JComponent{
      public void paintComponent(Graphics g){
      super.paintComponent(g);
      setDoubleBuffered(true);
      g.setColor(Color.BLACK);
      g.fillOval(10,10,50,100);
      g.setColor(Color.YELLOW);
      g.fillOval(15,15,40,90);      
    }
    }
  
  
}